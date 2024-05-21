package net.sssubtlety.automated_crafting.mixin;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import net.minecraft.datafixer.TypeReferences;
import net.minecraft.datafixer.schema.Schema3682;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;
import java.util.function.Supplier;

import static net.sssubtlety.automated_crafting.AutomatedCrafting.NAMESPACE;

@Mixin(Schema3682.class)
public class Schema3682Mixin {
    @Inject(method = "registerBlockEntities", at = @At("RETURN"))
    private void registerAutomatedCraftingTableFixer(
        Schema schema, CallbackInfoReturnable<Map<String, Supplier<TypeTemplate>>> cir
    ) {
        schema.register(
            cir.getReturnValue(),
            NAMESPACE + ":auto_crafter_entity",
            () -> DSL.optionalFields(
                "Items",
                DSL.list(TypeReferences.ITEM_STACK.in(schema))
            )
        );
    }
}
