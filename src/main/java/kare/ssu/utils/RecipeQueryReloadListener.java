package kare.ssu.utils;

import kare.ssu.RecipeQuery;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.ResourceManager;

public class RecipeQueryReloadListener implements SimpleSynchronousResourceReloadListener {
    public static void register() {
        ResourceManagerHelper.get(PackType.CLIENT_RESOURCES).registerReloadListener(new RecipeQueryReloadListener());
    }

    @Override
    public ResourceLocation getFabricId() {
        return ResourceLocation.fromNamespaceAndPath("ssu", "data/recipe_chains");
    }

    @Override
    public void onResourceManagerReload(ResourceManager resourceManager) {
        resourceManager.getResource(getFabricId()).ifPresent(resource -> RecipeQuery.INSTANCE.reloadChains(resource));
    }
}
