package to.joe.j2mc.antidrop.command;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import to.joe.j2mc.antidrop.J2MC_Antidrop;
import to.joe.j2mc.core.command.MasterCommand;

public class ToggleDropsCommand extends MasterCommand<J2MC_Antidrop> {

    public ToggleDropsCommand(J2MC_Antidrop plugin) {
        super(plugin);
    }

    @Override
    public void exec(CommandSender sender, String commandName, String[] args, Player player, boolean isPlayer) {
        if (!isPlayer) {
            sender.sendMessage(ChatColor.RED + "Only players may use this command");
            return;
        }
        String playerName = player.getName();
        if (this.plugin.dropsDisabled.contains(playerName)) {
            this.plugin.dropsDisabled.remove(playerName);
            sender.sendMessage(ChatColor.GOLD + "You have " + ChatColor.GREEN + "enabled " + ChatColor.GOLD + "drops");
        } else {
            this.plugin.dropsDisabled.add(playerName);
            sender.sendMessage(ChatColor.GOLD + "You have " + ChatColor.RED + "disabled " + ChatColor.GOLD + "drops");
        }
    }
}
