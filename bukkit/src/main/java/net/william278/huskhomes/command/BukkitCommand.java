package net.william278.huskhomes.command;

import net.william278.huskhomes.HuskHomes;
import net.william278.huskhomes.player.BukkitPlayer;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

/**
 * Bukkit executor that implements and executes {@link CommandBase}s
 */
public class BukkitCommand implements CommandExecutor, TabExecutor {

    /**
     * The {@link CommandBase} that will be executed
     */
    private final CommandBase command;

    /**
     * The implementing plugin
     */
    private final HuskHomes plugin;

    public BukkitCommand(@NotNull CommandBase command, @NotNull HuskHomes implementor) {
        this.command = command;
        this.plugin = implementor;
    }

    /**
     * Registers a {@link PluginCommand} to this implementation
     *
     * @param pluginCommand {@link PluginCommand} to register
     */
    public void register(@NotNull PluginCommand pluginCommand) {
        try {
            pluginCommand.setPermission(command.permission);
            pluginCommand.setDescription(command.getDescription());
            pluginCommand.setExecutor(this);
            pluginCommand.setTabCompleter(this);
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command,
                             @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player player) {
            this.command.onExecute(BukkitPlayer.adapt(player), args);
        } else {
            if (this.command instanceof ConsoleExecutable consoleExecutable) {
                consoleExecutable.onConsoleExecute(args);
            } else {
                plugin.getLocales().getLocale("error_in_game_only").
                        ifPresent(locale -> sender.spigot().sendMessage(locale.toComponent()));
            }
        }
        return true;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command,
                                      @NotNull String alias, @NotNull String[] args) {
        if (this.command instanceof TabCompletable tabCompletable) {
            return tabCompletable.onTabComplete(args, (sender instanceof Player ? BukkitPlayer.adapt((Player) sender) : null));
        }
        return Collections.emptyList();
    }
}
