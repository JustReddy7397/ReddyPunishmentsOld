package ga.justreddy.wiki.reddypunishments.commands

import com.github.helpfuldeer.commandlib.BaseCommand
import com.github.helpfuldeer.commandlib.SuperCommand
import ga.justreddy.wiki.reddypunishments.helper.moderation.BanHelper
import ga.justreddy.wiki.reddypunishments.helper.moderation.MuteHelper
import ga.justreddy.wiki.reddypunishments.messagesFile
import ga.justreddy.wiki.reddypunishments.utils.Utils
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender

@SuperCommand(
    name = "unmute",
    description = "Unmute a player",
    syntax = "/unmute <player> [-s (silent)]",
    permission = "reddypunishments.command.unmute"
)
class UnmuteCommand : BaseCommand() {

    override fun run(sender: CommandSender, args: Array<out String>) {
        try{
            val player = Bukkit.getOfflinePlayer(args[0])
            var silent = false

            // This will never get used
            var placeholderMessage = ""
            for (i in 1 until args.size) {
                placeholderMessage = placeholderMessage + args[i] + " "
            }

            if(placeholderMessage.contains("-s")){
                placeholderMessage = placeholderMessage.replace("-s ", "")
                silent = true
            }

            MuteHelper.getHelper().unmute(player, sender, silent)

        }catch (e: IndexOutOfBoundsException) {
            sender.sendMessage(tellInvalidArguments())
        }
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