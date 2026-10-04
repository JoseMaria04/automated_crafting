package net.sssubtlety.automated_crafting;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.redstone.Orientation;
import net.minecraft.world.phys.BlockHitResult;
import org.jspecify.annotations.Nullable;

import static net.sssubtlety.automated_crafting.AutomatedCrafting.LOGGER;

public class AutoCrafterBlock extends BaseEntityBlock {
	public static final Identifier ID = AutomatedCrafting.id("auto_crafter");
	public static final BooleanProperty POWERED = BooleanProperty.create("powered");
	public static final BooleanProperty ACTIVATED = BooleanProperty.create("activated");

	public AutoCrafterBlock(BlockBehaviour.Properties properties) {
		super(properties);
		this.registerDefaultState(this.stateDefinition.any().setValue(POWERED, false).setValue(ACTIVATED, false));
	}

	protected boolean isPowered(Level level, BlockPos pos) {
		return level.hasNeighborSignal(pos) || FeatureControl.isQuasiConnective() && level.hasNeighborSignal(pos.above());
	}

	@Override
	public AutoCrafterBlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new AutoCrafterBlockEntity(pos, state);
	}

	@Override
	protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
		if (!level.isClientSide()) {
			if (level.getBlockEntity(pos) instanceof AutoCrafterBlockEntity blockEntity) player.openMenu(blockEntity);
			else LOGGER.error("AutoCrafterBlock found block entity that was not an AutoCrafterBlockEntity.");
		}

		return InteractionResult.SUCCESS;
	}

	@Override
	protected void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, @Nullable Orientation orientation, boolean movedByPiston) {
		updatePowered(state, level, pos);
	}

	private void updatePowered(BlockState state, Level level, BlockPos pos) {
		final boolean isPowered = isPowered(level, pos);
		final boolean wasPowered = state.getValue(POWERED);

		if (isPowered) {
			if (!wasPowered) {
				// powering on
				level.setBlock(pos, state.setValue(POWERED, true).setValue(ACTIVATED, true), Block.UPDATE_CLIENTS);
				if (level instanceof ServerLevel) level.scheduleTick(pos, this, 2);
			}
		} else if (wasPowered || state.getValue(ACTIVATED)) {
			level.setBlock(pos, state.setValue(POWERED, false).setValue(ACTIVATED, false), Block.UPDATE_CLIENTS);
		}
	}

	@Override
	protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
		if (!FeatureControl.shouldCraftContinuously() && state.getValue(ACTIVATED)) {
			level.setBlock(pos, state.setValue(ACTIVATED, false), Block.UPDATE_CLIENTS);
		}

		if (level.getBlockEntity(pos) instanceof AutoCrafterBlockEntity blockEntity) {
			blockEntity.tryCraft();
			level.updateNeighborsAt(pos, this);
		}
	}

	@Override
	protected boolean hasAnalogOutputSignal(BlockState state) {
		return true;
	}

	@Override
	protected int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos, Direction direction) {
		return level.getBlockEntity(pos) instanceof AutoCrafterBlockEntity blockEntity ? blockEntity.getComparatorOutput() : 0;
	}

	@Override
	protected boolean isSignalSource(BlockState state) {
		return FeatureControl.shouldRedirectRedstone();
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(POWERED, ACTIVATED);
	}

	@Override
	public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity by, ItemStack itemStack) {
		updatePowered(state, level, pos);
	}
}
