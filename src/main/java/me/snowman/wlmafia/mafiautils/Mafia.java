package me.snowman.wlmafia.mafiautils;

import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Mafia {

    private String name;
    private List<UUID> players = new ArrayList<>();
    private List<ItemStack> deposit = new ArrayList<>();
    private List<UUID> waiting = new ArrayList<>();
    private List<UUID> chat = new ArrayList<>();
    private UUID owner;

    public Mafia(String name, UUID owner) {
        this.name = name;
        this.owner = owner;
    }

    public String getName() {
        return this.name;
    }

    public List<UUID> getPlayers() {
        return this.players;
    }

    public List<ItemStack> getDeposit() {
        return this.deposit;
    }

    public List<UUID> getWaiting() {
        return this.waiting;
    }

    public List<UUID> getChat() {
        return this.chat;
    }

    public UUID getOwner() {
        return owner;
    }
}
