package fr.battledroid.core.player.item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import fr.battledroid.core.utils.Utils;
import fr.battledroid.core.function.BiConsumer;
import fr.battledroid.core.function.Consumer;

public class Inventory implements Iterable<Item> {
    private final HashMap<Item, Integer> items = new HashMap<>();

    public int size() {
        return items.size();
    }

    /**
     * Add an item to the inventory.
     *
     * @param item
     * @return true if the item was not in the inventory, false otherwise
     */
    public boolean add(Item item) {
        boolean isFirstItem = true;
        Utils.requireNonNull(item);
        int currentValue = 0;
        if (items.containsKey(item)) {
            currentValue = items.get(item);
            isFirstItem = false;
        }
        items.put(item, currentValue + 1);
        return isFirstItem;
    }

    /**
     * Remove an item from the inventory.
     *
     * @param item
     * @return true if this was the last unit of this item, false otherwise
     */
    public boolean remove(Item item) {
        if (!items.containsKey(item)) {
            throw new IllegalStateException("The item is not in the inventory");
        }

        int currentValue = items.get(item);
        if (currentValue == 1) {
            items.remove(item);
            return true;
        }
        items.put(item, currentValue - 1);
        return false;
    }

    public int getQuantity(Item item) {
        if (items.containsKey(item)) {
            return items.get(item);
        }
        return 0;
    }

    public void forEach(BiConsumer<Item, Integer> consumer) {
        for (Map.Entry<Item, Integer> e : items.entrySet()) {
            consumer.accept(e.getKey(), e.getValue());
        }
    }

    public List<Item> asList() {
        return new ArrayList<>(items.keySet());
    }

    @Override
    public Iterator<Item> iterator() {
        return items.keySet().iterator();
    }

    // This is not the real forEach because action.accept() is not available in API 14
    public void forEach(Consumer<? super Item> action) {
        for (Item item : items.keySet()) {
            action.accept(item);
        }
    }
}
