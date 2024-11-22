package kare.ssu.client;

import com.mojang.blaze3d.platform.InputConstants;
import kare.ssu.RecipeQuery;
import net.minecraft.ChatFormatting;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.Slot;
import org.lwjgl.glfw.GLFW;

import java.util.Map;
import java.util.Objects;

public class RecipeQueryClient implements ClientModInitializer {
    private static Minecraft client;

    public static KeyMapping queryKey;
    public static KeyMapping enchantedKey;

    private static final Map<String, String> replacements = Map.ofEntries(
            Map.entry("Raw Porkchop", "Pork"),
            Map.entry("White Wool", "Wool"),
            Map.entry("Rabbit's Foot", "Rabbit Foot"),
            Map.entry("Wheat", "Hay"),
            Map.entry("Iron Ingot", "Iron"),
            Map.entry("Gold Ingot", "Gold"),
            Map.entry("Nether Quartz", "Quartz")
    );

    public static void onRecipeQueryKeyPressed(Slot slot) {
        if (slot == null)
            return;
        String itemString = doSubstitution(slot.getItem().getDisplayName().getString().replace("[", "").replace("]", ""));
        assert client.player != null;
        client.player.connection.sendCommand("recipe " + itemString.toLowerCase());
    }

    public static void onViewEnchanted(Slot slot) {
        if (slot == null)
            return;
        assert client.player != null;
        var customData = slot.getItem().get(DataComponents.CUSTOM_DATA);
        if (customData == null) {
            client.player.sendSystemMessage(Component.literal("This item has no data and cannot be checked.").withStyle(ChatFormatting.BOLD, ChatFormatting.RED, ChatFormatting.ITALIC));
            return;
        }
        var ID = Objects.requireNonNull(customData).copyTag().get("id");
        if (ID == null) {
            client.player.sendSystemMessage(Component.literal("This item has no ID and cannot be checked.").withStyle(ChatFormatting.BOLD, ChatFormatting.RED, ChatFormatting.ITALIC));
            return;
        }
        String id_string = ID.toString().replace("\"", "");
        var chains = RecipeQuery.INSTANCE.getChains();

        if (id_string.contains("GEM")) {
            var split = id_string.split("_", 2);
            var gem_string = split.length > 1 ? split[1] : "";
            var chain_string = split[0];

            for (var chain : chains) {
                if (chain.isInChain(chain_string) && chain.getNext(chain_string) != null) {
                    client.player.connection.sendCommand("viewrecipe " + chain.getNext(chain_string) + "_" + gem_string);
                    return;
                }
            }
        } else {
            for (var chain : chains) {
                if (chain.isInChain(id_string) && chain.getNext(id_string) != null) {
                    client.player.connection.sendCommand("viewrecipe " + chain.getNext(id_string));
                    return;
                }
            }
        }

        client.player.sendSystemMessage(Component.literal("This item has no enchanted/upgraded version.").withStyle(ChatFormatting.BOLD, ChatFormatting.RED, ChatFormatting.ITALIC));
    }



    public static String doSubstitution(String input) {
        if (input.contains("Gemstone")) {
            var splitString = input.split("\\s", 3);
            if (splitString.length == 3) {
                return splitString[2];
            } else {
                return input;
            }
        }

        return replacements.getOrDefault(input, input);
    }


    @Override
    public void onInitializeClient() {
        client = Minecraft.getInstance();
        // The translation key of the keybinding's name
        // The type of the keybinding, KEYSYM for keyboard, MOUSE for mouse.
        // The keycode of the key
        // The translation key of the keybinding's category.
         queryKey = KeyBindingHelper.registerKeyBinding(new KeyMapping(
                "key.recipequery.query", // The translation key of the keybinding's name
                InputConstants.Type.KEYSYM, // The type of the keybinding, KEYSYM for keyboard, MOUSE for mouse.
                GLFW.GLFW_KEY_R, // The keycode of the key
                "category.recipequery.keys" // The translation key of the keybinding's category.
        ));

        enchantedKey = KeyBindingHelper.registerKeyBinding(new KeyMapping(
                "key.recipequery.viewenchanted", // The translation key of the keybinding's name
                InputConstants.Type.KEYSYM, // The type of the keybinding, KEYSYM for keyboard, MOUSE for mouse.
                GLFW.GLFW_KEY_Y, // The keycode of the key
                "category.recipequery.keys" // The translation key of the keybinding's category.
        ));

    }
}

