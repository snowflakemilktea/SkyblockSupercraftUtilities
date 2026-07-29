package kare.ssu.client.utils;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;

public class Feedback {
    private static void sendSystemMessage(final String message, final ChatFormatting color) {
        final Minecraft client = Minecraft.getInstance();
        if (client.player != null) {
            client.player.sendSystemMessage(
                    Component.literal("[SSU] ").withStyle(ChatFormatting.GOLD).append(
                            Component.literal(message).withStyle(color)));
        }
    }
    private static void sendSoundEffect(final SoundEvent sound, final float pitch, final float volume) {
        final Minecraft client = Minecraft.getInstance();
        client.getSoundManager().play(SimpleSoundInstance.forUI(sound, pitch, volume));
    }

    public static void sendErrorMessage(final String message) {
        Feedback.sendSoundEffect(SoundEvents.ENDERMAN_TELEPORT, 1.0f, 0.5f);
        Feedback.sendSystemMessage(message, ChatFormatting.RED);
    }
}
