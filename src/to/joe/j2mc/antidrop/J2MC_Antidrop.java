package to.joe.j2mc.antidrop;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerEggThrowEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionType;

public class J2MC_Antidrop extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(this, this);
        this.getLogger().info("Anti drops module enabled");
    }

    @Override
    public void onDisable() {
        this.getLogger().info("Anti drops module disabled");
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

        if (event.getItem().getType().equals(Material.POTION) && (event.getItem().getDurability() != 0)) {
            Potion potion = Potion.fromItemStack(event.getItem());

            if (potion.getType().equals(PotionType.INVISIBILITY) && potion.isSplash()) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onConsumption(PlayerItemConsumeEvent event) {
        Player player = event.getPlayer();

        if (event.getItem().getType().equals(Material.POTION) && (event.getItem().getDurability() != 0)) {
            Potion potion = Potion.fromItemStack(event.getItem());

            if (potion.getType().equals(PotionType.INVISIBILITY)) {
                player.sendMessage(ChatColor.RED + "Invisibility potions are disabled.");
                event.setCancelled(true);
            }
        }
    }

}
