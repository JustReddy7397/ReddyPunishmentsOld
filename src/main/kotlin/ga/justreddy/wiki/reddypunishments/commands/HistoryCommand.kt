package ga.justreddy.wiki.reddypunishments.commands

import com.github.helpfuldeer.commandlib.BaseCommand
import com.github.helpfuldeer.commandlib.SuperCommand
import ga.justreddy.wiki.reddypunishments.menu.menus.BanHistory
import ga.justreddy.wiki.reddypunishments.menu.menus.SelectMenu
import ga.justreddy.wiki.reddypunishments.messagesFile
import ga.justreddy.wiki.reddypunishments.utils.Utils
import org.bukkit.entity.Player


@SuperCommand(
    name = "history",
    description = "Check the history of someone",
    syntax = "/history <name>",
    playersOnly = true,
    permission = "reddypunishments.command.history"
)
class HistoryCommand : BaseCommand() {

    override fun run(player: Player, args: Array<out String>) {
        try{
            val name = args[0]
            SelectMenu(name).open(player)
        }catch (e: IndexOutOfBoundsException) {
            player.sendMessage(tellInvalidArguments())
        }
    }

    override fun tellInvalidArguments(): String {
        return Utils.format(messagesFile.config?.getString("incorrect-usage")!!.replace("%prefix%", messagesFile.config!!.getString("prefix")).replace("%syntax%", superCommand.syntax))
    }
    override fun tellInvalidPerms(): String {
        return Utils.format(messagesFile.config!!.getString("invalid-permission")!!.replace("%prefix%", messagesFile.config!!.getString("prefix")))
    }

    override fun tellPlayersOnly(): String {
        return Utils.format(messagesFile.config!!.getString("players-only")!!.replace("%prefix%", messagesFile.config!!.getString("prefix")))
    }

}