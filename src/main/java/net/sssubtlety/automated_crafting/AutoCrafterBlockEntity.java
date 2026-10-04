package net.sssubtlety.automated_crafting;

import net.fabricmc.fabric.api.menu.v1.ExtendedMenuProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.Containers;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jspecify.annotations.Nullable;

import java.util.Optional;

/**
 * Inventory layout (unchanged from previous versions so saved auto crafters load correctly):
 * slots 0-8 are the template (ghost items), 9-17 the input grid and 18 the output.
 */
public class AutoCrafterBlockEntity extends BaseContainerBlockEntity implements WorldlyContainer, ExtendedMenuProvider<Boolean> {
	public static final int GRID_WIDTH = 3;
	public static final int GRID_HEIGHT = 3;
	public static final int GRID_SIZE = GRID_WIDTH * GRID_HEIGHT;
	public static final int TEMPLATE_START = 0;
	public static final int INPUT_START = GRID_SIZE;
	public static final int OUTPUT_SLOT = GRID_SIZE * 2;
	public static final int INVENTORY_SIZE = OUTPUT_SLOT + 1;
	public static final int MAX_INPUT_STACK_SIZE = 1;

	public static final Validator validator = new Validator();
	public static final Component NAME = Component.translatable("block.automated_crafting.auto_crafter");

	// output first, then the input grid
	private static final int[] AVAILABLE_SLOTS;

	static {
		AVAILABLE_SLOTS = new int[GRID_SIZE + 1];
		AVAILABLE_SLOTS[0] = OUTPUT_SLOT;
		for (int i = 0; i < GRID_SIZE; i++) AVAILABLE_SLOTS[i + 1] = INPUT_START + i;
	}

	public static boolean templateMatches(ItemStack inputStack, ItemStack templateStack) {
		return ItemStack.isSameItemSameComponents(templateStack, inputStack);
	}

	public static boolean isInput(int slot) {
		return slot >= INPUT_START && slot < OUTPUT_SLOT;
	}

	public static boolean isTemplate(int slot) {
		return slot >= TEMPLATE_START && slot < INPUT_START;
	}

	private NonNullList<ItemStack> items = NonNullList.withSize(INVENTORY_SIZE, ItemStack.EMPTY);
	private final Validator.Validation validation = validator.getValidation();
	private @Nullable RecipeHolder<CraftingRecipe> recipeCache;

	public AutoCrafterBlockEntity(BlockPos pos, BlockState state) {
		super(Registrar.BLOCK_ENTITY_TYPE, pos, state);
	}

	// --- persistence ---

	@Override
	protected void loadAdditional(ValueInput input) {
		super.loadAdditional(input);
		this.items = NonNullList.withSize(INVENTORY_SIZE, ItemStack.EMPTY);
		ContainerHelper.loadAllItems(input, this.items);
		this.recipeCache = null;
	}

	@Override
	protected void saveAdditional(ValueOutput output) {
		super.saveAdditional(output);
		ContainerHelper.saveAllItems(output, this.items);
	}

	@Override
	protected NonNullList<ItemStack> getItems() {
		return items;
	}

	@Override
	protected void setItems(NonNullList<ItemStack> items) {
		this.items = items;
	}

	// --- crafting ---

	private CraftingInput.Positioned positionedInput() {
		return CraftingInput.ofPositioned(GRID_WIDTH, GRID_HEIGHT, items.subList(INPUT_START, OUTPUT_SLOT));
	}

	public void tryCraft() {
		if (!(level instanceof ServerLevel serverLevel)) return;

		final CraftingInput.Positioned positioned = positionedInput();
		final CraftingInput input = positioned.input();
		final Optional<RecipeHolder<CraftingRecipe>> optRecipe = getRecipe(serverLevel, input);
		if (optRecipe.isEmpty()) {
			tryPlayFailSound();
			return;
		}

		final CraftingRecipe recipe = optRecipe.get().value();
		final ItemStack result = recipe.assemble(input);
		final OutputAction outputAction = checkOutput(result);
		if (result.isEmpty() || outputAction == OutputAction.FAIL) {
			tryPlayFailSound();
			return;
		}

		final NonNullList<ItemStack> remainders = recipe.getRemainingItems(input);
		for (int y = 0; y < input.height(); y++) {
			for (int x = 0; x < input.width(); x++) {
				final int slot = INPUT_START + x + positioned.left() + (y + positioned.top()) * GRID_WIDTH;
				final ItemStack remainder = remainders.get(x + y * input.width());
				final ItemStack stack = items.get(slot);
				if (!stack.isEmpty()) stack.shrink(1);
				if (!remainder.isEmpty()) items.set(slot, remainder);
				else if (stack.isEmpty()) items.set(slot, ItemStack.EMPTY);
			}
		}

		result.onCraftedBySystem(serverLevel);
		if (outputAction == OutputAction.SET) items.set(OUTPUT_SLOT, result);
		else items.get(OUTPUT_SLOT).grow(result.getCount());

		setChanged();
	}

	private Optional<RecipeHolder<CraftingRecipe>> getRecipe(ServerLevel level, CraftingInput input) {
		if (FeatureControl.isSimpleMode() && inputMismatchesTemplate()) return Optional.empty();
		if (input.isEmpty()) return Optional.empty();

		if (recipeCache == null || validation.invalid() || !recipeCache.value().matches(input, level)) {
			recipeCache = level.recipeAccess().getRecipeFor(RecipeType.CRAFTING, input, level).orElse(null);
		}

		return Optional.ofNullable(recipeCache);
	}

	private boolean inputMismatchesTemplate() {
		for (int i = 0; i < GRID_SIZE; i++) {
			if (!templateMatches(items.get(INPUT_START + i), items.get(TEMPLATE_START + i))) return true;
		}

		return false;
	}

	private OutputAction checkOutput(ItemStack result) {
		final ItemStack output = items.get(OUTPUT_SLOT);
		if (output.isEmpty()) return OutputAction.SET;
		else if (
			ItemStack.isSameItemSameComponents(result, output) &&
			output.getMaxStackSize() >= output.getCount() + result.getCount()
		) return OutputAction.INCREMENT;
		else return OutputAction.FAIL;
	}

	private void tryPlayFailSound() {
		if (level != null && !FeatureControl.shouldCraftContinuously()) level.levelEvent(1001, worldPosition, 0);
	}

	private void tryCraftContinuously() {
		if (
			FeatureControl.shouldCraftContinuously() &&
			level != null && !level.isClientSide() &&
			getBlockState().getValue(AutoCrafterBlock.POWERED)
		) tryCraft();
	}

	public int getComparatorOutput() {
		if (FeatureControl.shouldComparatorReadOutput() && !items.get(OUTPUT_SLOT).isEmpty()) return 15;
		int occupied = 0;
		for (int i = INPUT_START; i < OUTPUT_SLOT; i++) if (!items.get(i).isEmpty()) occupied++;
		return occupied;
	}

	/** Drops inputs and output, but not the template, which only holds ghost items. */
	@Override
	public void preRemoveSideEffects(BlockPos pos, BlockState state) {
		if (level == null) return;
		for (int i = INPUT_START; i < INVENTORY_SIZE; i++) {
			Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(), items.get(i));
		}
	}

	// --- container ---

	@Override
	public int getContainerSize() {
		return INVENTORY_SIZE;
	}

	@Override
	public int getMaxStackSize() {
		return MAX_INPUT_STACK_SIZE;
	}

	@Override
	public boolean isEmpty() {
		for (int i = INPUT_START; i < INVENTORY_SIZE; i++) if (!items.get(i).isEmpty()) return false;
		return true;
	}

	@Override
	public ItemStack getItem(int slot) {
		return items.get(slot);
	}

	@Override
	public ItemStack removeItem(int slot, int count) {
		final ItemStack removed = ContainerHelper.removeItem(items, slot, count);
		if (!removed.isEmpty()) {
			setChanged();
			tryCraftContinuously();
		}
		return removed;
	}

	@Override
	public ItemStack removeItemNoUpdate(int slot) {
		return ContainerHelper.takeItem(items, slot);
	}

	@Override
	public void setItem(int slot, ItemStack stack) {
		setItemWithoutCrafting(slot, stack);
		tryCraftContinuously();
	}

	public void setItemWithoutCrafting(int slot, ItemStack stack) {
		items.set(slot, stack);
		setChanged();
	}

	@Override
	public boolean canPlaceItem(int slot, ItemStack stack) {
		if (!isInput(slot) || !items.get(slot).isEmpty()) return false;
		return !FeatureControl.isSimpleMode() || templateMatches(stack, items.get(slot - INPUT_START));
	}

	@Override
	public boolean stillValid(Player player) {
		return Container.stillValidBlockEntity(this, player);
	}

	@Override
	public void clearContent() {
		items.clear();
	}

	// --- automation ---

	@Override
	public int[] getSlotsForFace(Direction direction) {
		return AVAILABLE_SLOTS;
	}

	@Override
	public boolean canPlaceItemThroughFace(int slot, ItemStack stack, @Nullable Direction direction) {
		return isInput(slot);
	}

	@Override
	public boolean canTakeItemThroughFace(int slot, ItemStack stack, Direction direction) {
		if (slot == OUTPUT_SLOT || !FeatureControl.isSimpleMode()) return true;
		return isInput(slot) && !templateMatches(stack, items.get(slot - INPUT_START));
	}

	// --- menu ---

	@Override
	protected Component getDefaultName() {
		return NAME;
	}

	@Override
	protected AbstractContainerMenu createMenu(int containerId, Inventory inventory) {
		return new AutoCrafterMenu(containerId, inventory, this, FeatureControl.isSimpleMode());
	}

	@Override
	public Boolean getScreenOpeningData(ServerPlayer player) {
		return FeatureControl.isSimpleMode();
	}

	private enum OutputAction {
		FAIL, SET, INCREMENT
	}
}
