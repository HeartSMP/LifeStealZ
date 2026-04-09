package com.zetaplugins.lifestealz.listeners;

import com.zetaplugins.zetacore.annotations.AutoRegisterListener;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import com.zetaplugins.lifestealz.LifeStealZ;
import com.zetaplugins.lifestealz.storage.PlayerData;

import java.util.List;

@AutoRegisterListener
public final class WorldSwitchListener implements Listener {
    private final LifeStealZ plugin;

    public WorldSwitchListener(LifeStealZ plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onWorldSwitch(PlayerChangedWorldEvent event) {
        Player player = event.getPlayer();
        World fromWorld = event.getFrom();
        World toWorld = player.getLocation().getWorld();

        List<String> worldWhitelist = plugin.getConfig().getStringList("worlds");

        if (worldWhitelist.contains(toWorld.getName())) {
            handleWhitelistedWorld(player, fromWorld, worldWhitelist);
        } else {
            LifeStealZ.setMaxHealth(player, 20.0);
        }
    }

    private void handleWhitelistedWorld(Player player, World fromWorld, List<String> worldWhitelist) {
        PlayerData playerData = plugin.getStorage().load(player.getUniqueId());
        LifeStealZ.setMaxHealth(player, playerData.getMaxHealth());

        if (!worldWhitelist.contains(fromWorld.getName())) {
            player.setHealth(playerData.getMaxHealth());
        }
    }
}