package kare.ssu.mixins;

import kare.ssu.client.RecipeQueryClient;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.inventory.Slot;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractContainerScreen.class)
public abstract class ScreenMixin {
    @Shadow @Nullable protected Slot hoveredSlot;

    @Shadow public abstract void onClose();

    @Inject(method = "keyPressed", at = @At("HEAD"))
    private void keyPressed(int i, int j, int k, CallbackInfoReturnable<Boolean> cir) {
        if (RecipeQueryClient.queryKey.matches(i, j) && hoveredSlot != null) {
            this.onClose();
            RecipeQueryClient.onRecipeQueryKeyPressed(hoveredSlot);
        } else if (RecipeQueryClient.enchantedKey.matches(i, j) && hoveredSlot != null) {
            this.onClose();
            RecipeQueryClient.onViewEnchanted(hoveredSlot);
        }
    }


}