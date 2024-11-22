package kare.ssu.utils;

import java.util.Arrays;
import java.util.List;

public class CraftingChain {
    private final List<String> chain;

    public CraftingChain(String... items) {
        chain = Arrays.stream(items).toList();
    }

    public boolean isInChain(String item) {
        return chain.contains(item);
    }

    public String getNext(String item) {
        if (!isInChain(item)) {
            return null;
        }
        int index = chain.indexOf(item);
        if (index == chain.size() - 1) {
            return null;
        }

        return chain.get(index + 1);
    }
}
