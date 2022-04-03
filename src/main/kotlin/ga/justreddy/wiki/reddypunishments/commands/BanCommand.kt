package ga.justreddy.wiki.reddypunishments.commands

import com.github.helpfuldeer.commandlib.BaseCommand
import com.github.helpfuldeer.commandlib.SuperCommand
import ga.justreddy.wiki.reddypunishments.messagesFile
import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

@SuperCommand(
    name = "ban",
    description = "Ban a player",
    syntax = "/ban <player> [reason]",
    permission = "reddypunishments.command.ban",
    playersOnly = false,
)
class BanCommand : BaseCommand() {

    override fun run(sender: CommandSender, args: Array<out String>) {

        try{
            val player: Player? = Bukkit.getPlayer(args[0]);
            sender.sendMessage(player?.name)
        }catch (e: IndexOutOfBoundsException) {
            tellInvalidArguments(sender, messagesFile.config?.getString("incorrect-usage"))
        }



    }



}