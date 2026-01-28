package kare.ssu.utils;

import net.minecraft.server.packs.resources.Resource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ChainCreator {
    public static List<CraftingChain> createChains(Resource res) {
        List<CraftingChain> chainList = new ArrayList<>();

        try (var br = res.openAsReader()) {
            br.lines().forEach(line -> {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("--"))
                    return;

                var items = line.trim().split("\\s->\\s");
                chainList.add(new CraftingChain(items));
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return chainList;
    }
}
