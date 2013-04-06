package to.joe.j2mc.antidrop;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerEggThrowEvent;
import org.bukkit.plugin.java.JavaPlugin;

import to.joe.j2mc.antidrop.command.ToggleDropsCommand;

public class J2MC_Antidrop extends JavaPlugin implements Listener {

    private Map<Location, Material> blockBreaks;
    public Set<String> dropsDisabled;

    @Override
    public void onEnable() {
        this.blockBreaks = new HashMap<Location, Material>();
        this.dropsDisabled = new HashSet<String>();
        this.getCommand("toggledrops").setExecutor(new ToggleDropsCommand(this));
        this.getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler(ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent event) {
        if (this.dropsDisabled.contains(event.getPlayer().getName())) {
            this.blockBreaks.put(event.getBlock().getLocation(), event.getBlock().getType());
        }
    }

    @EventHandler
    public void onItemSpawnEvent(ItemSpawnEvent event) {
        Location spawnLoc = event.getLocation();
        Iterator<Entry<Location, Material>> blockBreakIterator = this.blockBreaks.entrySet().iterator();
        while (blockBreakIterator.hasNext()) {
            Entry<Location, Material> blockBreak = blockBreakIterator.next();
            if (blockBreak.getValue() == event.getEntity().getItemStack().getType() && spawnLoc.distanceSquared(blockBreak.getKey()) < 2) {
                event.setCancelled(true);
                blockBreakIterator.remove();
            }
        }
    }

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent event) {
        if (this.dropsDisabled.contains(event.getPlayer().getName())) {
            event.getItemDrop().remove();
        }
    }

    @EventHandler
    public void onEggThrow(PlayerEggThrowEvent event) {
        if (!event.getPlayer().hasPermission("j2mc.core.admin")) {
            event.setHatching(false);
        }
    }

    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        SpawnReason reason = event.getSpawnReason();
        if (reason == SpawnReason.BUILD_IRONGOLEM || reason == SpawnReason.BUILD_SNOWMAN) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        if (this.dropsDisabled.contains(event.getEntity().getName())) {
            event.getDrops().clear();
        }
        event.setDeathMessage(null);
    }

    @EventHandler
    public void onBlockDispense(BlockDispenseEvent event) {
        Material type = event.getItem().getType();
        if (type == Material.LAVA_BUCKET || type == Material.WATER_BUCKET || type == Material.LAVA || type == Material.WATER) {
            event.setCancelled(true);
        }
    }

}
