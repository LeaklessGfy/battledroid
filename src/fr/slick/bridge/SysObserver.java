package fr.slick.bridge;

import fr.battledroid.core.player.Player;
import fr.battledroid.core.player.PlayerObserver;
import fr.battledroid.core.player.item.Item;

import java.util.Objects;

public final class SysObserver implements PlayerObserver {
    private final Player subject;

    public SysObserver(Player subject) {
        this.subject = Objects.requireNonNull(subject);
    }

    @Override
    public void updateHealth(double health) {
        System.out.println("New health " + health);
    }

    @Override
    public void updateDefense(int defense) {
        System.out.println("New defense " + defense);
    }

    @Override
    public void updateSpeed(int speed) {
        System.out.println("New speed " + speed);
    }

    @Override
    public void updateItem(Item item, boolean isNewItem) {
        System.out.println("New item " + item);
    }
}
