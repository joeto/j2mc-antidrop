package to.joe.j2mc.antidrop;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerEggThrowEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class J2MC_Antidrop extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onItemEvent(ItemSpawnEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onEggThrow(PlayerEggThrowEvent event) {
        if (!event.getPlayer().hasPermission("j2mc.core.admin")) {
            event.setHatching(false);
        }
    }

    @EventHandler
    public void spawn(CreatureSpawnEvent event) {
        SpawnReason reason = event.getSpawnReason();
        if (reason == SpawnReason.BUILD_IRONGOLEM || reason == SpawnReason.BUILD_SNOWMAN) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void death(PlayerDeathEvent event) {
        event.setDeathMessage(null);
    }

    @EventHandler
    public void onDispense(BlockDispenseEvent event) {
        Material type = event.getItem().getType();
        if (type == Material.LAVA_BUCKET || type == Material.WATER_BUCKET || type == Material.LAVA || type == Material.WATER) {
            event.setCancelled(true);
        }
    }

}
