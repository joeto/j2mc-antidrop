package to.joe.j2mc.antidrop;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class J2MC_Antidrop extends JavaPlugin implements Listener{
    
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
    public void onDropEvent(PlayerDropItemEvent event){
        if(!event.getPlayer().hasPermission("j2mc.core.admin")){
            event.setCancelled(true);
        }
    }
    
}
