package net.sssubtlety.automated_crafting.mixin;

import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.templates.TypeTemplate;
import net.minecraft.util.datafix.schemas.V1458;
import net.minecraft.util.datafix.schemas.V1460;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;
import java.util.function.Supplier;

import static net.sssubtlety.automated_crafting.AutomatedCrafting.NAMESPACE;

/**
 * Registers the auto crafter's block entity with the data fixer so the items stored in auto crafters from
 * older worlds (e.g. 1.20.1) get converted to the item component format introduced in 1.20.5.
 * Without this, stacks would be read with the new codec directly and lose their counts and data.
 * It must be registered in the schema that first defines namespaced block entities (1460): every later schema
 * inherits it, so the data is typed from the start. Registering in a later schema leaves old data untyped.
 */
@Mixin(V1460.class)
public class V1460Mixin {
	@Inject(method = "registerBlockEntities", at = @At("RETURN"))
	private void automated_crafting$registerAutoCrafter(Schema schema, CallbackInfoReturnable<Map<String, Supplier<TypeTemplate>>> cir) {
		schema.register(cir.getReturnValue(), NAMESPACE + ":auto_crafter_entity", () -> V1458.nameableInventory(schema));
	}
}
