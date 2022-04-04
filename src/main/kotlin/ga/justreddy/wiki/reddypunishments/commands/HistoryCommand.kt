package ga.justreddy.wiki.reddypunishments.commands

import com.github.helpfuldeer.commandlib.BaseCommand
import com.github.helpfuldeer.commandlib.SuperCommand
import ga.justreddy.wiki.reddypunishments.menu.menus.BanHistory
import ga.justreddy.wiki.reddypunishments.menu.menus.Menu
import ga.justreddy.wiki.reddypunishments.messagesFile
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
            BanHistory(name).open(player)
        }catch (ex: IndexOutOfBoundsException) {
            tellInvalidArguments(player, messagesFile.config?.getString("incorrect-usage"))
        }
    }

}