package kare.ssu;

import kare.ssu.utils.ChainCreator;
import kare.ssu.utils.CraftingChain;
import kare.ssu.utils.RecipeQueryReloadListener;
import net.fabricmc.api.ModInitializer;
import net.minecraft.server.packs.resources.Resource;

import java.util.List;

public class RecipeQuery implements ModInitializer {
    public static RecipeQuery INSTANCE;
    private List<CraftingChain> chains;

    @Override
    public void onInitialize() {
        INSTANCE = this;
        RecipeQueryReloadListener.register();
    }

    public void reloadChains(Resource resource) {
        chains = ChainCreator.createChains(resource);
    }

    public List<CraftingChain> getChains() {
        return chains;
    }
}
