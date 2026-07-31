package kare.ssu.mixins;

import kare.ssu.client.RecipeQueryClient;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractContainerScreen.class)
public abstract class ScreenMixin {
    @Shadow @Nullable protected Slot hoveredSlot;

    @Inject(method = "keyPressed", at = @At("HEAD"))
    private void keyPressed(KeyEvent keyEvent, CallbackInfoReturnable<Boolean> cir) {
        if(hoveredSlot != null && hoveredSlot.hasItem()) {
            Screen screen = (Screen) (Object) this;
            ItemStack stack = hoveredSlot.getItem();

            RecipeQueryClient.handleKeyEvent(screen, stack, keyEvent);
        }
    }
}