package kare.ssu.client.compat;

import com.operationpotato.itemlist.api.HoveredItemManager;
import com.operationpotato.itemlist.api.Plugin;
import kare.ssu.client.RecipeQueryClient;

public class SkyblockItemList implements Plugin {
    @Override
    public void registerHoveredItems(HoveredItemManager hoveredItemManager) {
        hoveredItemManager.addConsumer(RecipeQueryClient::handleKeyEvent);
    }
}
