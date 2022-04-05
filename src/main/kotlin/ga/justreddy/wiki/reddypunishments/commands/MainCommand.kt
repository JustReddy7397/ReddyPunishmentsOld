package ga.justreddy.wiki.reddypunishments.commands

import com.github.helpfuldeer.commandlib.BaseCommand
import com.github.helpfuldeer.commandlib.SuperCommand
import ga.justreddy.wiki.reddypunishments.messagesFile
import ga.justreddy.wiki.reddypunishments.plugin
import ga.justreddy.wiki.reddypunishments.utils.Utils
import org.bukkit.command.CommandSender

@SuperCommand(
    name = "reddypunishments",
    description = "Main Command",
    syntax = "/reddypunishments [reload]",
    playersOnly = false,
    permission = "reddypunishments.command.main"
)
class MainCommand : BaseCommand() {

    override fun run(sender: CommandSender, args: Array<out String>) {
        try {
            when (args[0]) {
                "reload" -> runReload(sender, args)
                else -> {
                    Utils.sendMessage(
                        sender,
                        "&4%line%",
                        "&c/ban <player> [-s (silent)] [reason] &7- &4Bans a player",
                        "&c/tempban <player> <time> [-s (silent)] [reason] &7- &4Tempbans a player",
                        "&c/unban <player> [-s (silent)] &7- &4Unbans a player",
                        "&c/history <name> &7- &4Check the history of a player",
                        "&4%line%"
                    )
                }
            }
        } catch (e: IndexOutOfBoundsException) {
            Utils.sendMessage(
                sender,
                "&4%line%",
                "&c/ban <player> [-s (silent)] [reason] &7- &4Bans a player",
                "&c/tempban <player> <time> [-s (silent)] [reason] &7- &4Tempbans a player",
                "&c/unban <player> [-s (silent)] &7- &4Unbans a player",
                "&c/history <name> &7- &4Check the history of a player",
                "&4%line%"
            )
        }

    }

    private fun runReload(sender: CommandSender, args: Array<out String>) {
        if (!sender.hasPermission("reddypunishments.command.reload")) {
            sender.sendMessage(tellInvalidPerms())
            return
        }
        plugin.reloadFiles()
        sender.sendMessage(
            Utils.format(
                messagesFile.config!!.getString("reload").replace("%prefix%", messagesFile.config!!.getString("prefix"))
            )
        )
    }

    override fun tellInvalidArguments(): String {
        return Utils.format(messagesFile.config?.getString("incorrect-usage")!!.replace("%prefix%", messagesFile.config!!.getString("prefix")).replace("%syntax%", superCommand.syntax))
    }

    override fun tellInvalidPerms(): String {
        return Utils.format(
            messagesFile.config!!.getString("invalid-permission")!!
                .replace("%prefix%", messagesFile.config!!.getString("prefix"))
        )
    }

    override fun tellPlayersOnly(): String {
        return Utils.format(
            messagesFile.config!!.getString("players-only")!!
                .replace("%prefix%", messagesFile.config!!.getString("prefix"))
        )
    }
}
