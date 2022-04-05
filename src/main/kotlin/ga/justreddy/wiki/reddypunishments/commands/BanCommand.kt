package ga.justreddy.wiki.reddypunishments.commands

import com.github.helpfuldeer.commandlib.BaseCommand
import com.github.helpfuldeer.commandlib.SuperCommand
import ga.justreddy.wiki.reddypunishments.ReddyPunishments
import ga.justreddy.wiki.reddypunishments.helper.moderation.BanHelper
import ga.justreddy.wiki.reddypunishments.messagesFile
import ga.justreddy.wiki.reddypunishments.plugin
import ga.justreddy.wiki.reddypunishments.utils.Utils
import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
import org.bukkit.command.CommandSender
import java.util.*

@SuperCommand(
    name = "ban",
    description = "Ban a player",
    syntax = "/ban <player> [-s (silent)] [reason]",
    permission = "reddypunishments.command.ban",
    playersOnly = false
)
class BanCommand : BaseCommand() {

    override fun run(sender: CommandSender, args: Array<out String>) {

        try{

            val player: OfflinePlayer = Bukkit.getOfflinePlayer(args[0])
            var reason = "No Reason Given"
            var silent = false
            for (i in 1 until args.size) {
                reason = reason.replace("No Reason Given", "") + args[i] + " "
            }

            if(reason.contains("-s")){
                reason = reason.replace("-s ", "")
                silent = true
            }

            if(silent) {
                BanHelper.getHelper().ban(player, sender, "0", -1L, true, reason)
            }else{
                BanHelper.getHelper().ban(player, sender, "0", -1L, reason)
            }

        }catch (e: IndexOutOfBoundsException) {
            sender.sendMessage(tellInvalidArguments())
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