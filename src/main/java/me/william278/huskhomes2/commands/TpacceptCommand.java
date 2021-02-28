package me.william278.huskhomes2.commands;

import me.william278.huskhomes2.teleport.TeleportRequestHandler;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TpacceptCommand extends CommandBase {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            TeleportRequestHandler.replyTpRequest(p, true);
            return true;
        }
        return false;
    }
}
