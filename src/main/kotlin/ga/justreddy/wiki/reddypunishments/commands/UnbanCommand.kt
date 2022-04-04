package ga.justreddy.wiki.reddypunishments.commands

import com.github.helpfuldeer.commandlib.BaseCommand
import com.github.helpfuldeer.commandlib.SuperCommand
import ga.justreddy.wiki.reddypunishments.helper.moderation.BanHelper
import ga.justreddy.wiki.reddypunishments.messagesFile
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender

@SuperCommand(
    name = "unban",
    description = "Unban a player",
    syntax = "/unban <player> [-s (silent)]",
    permission = "reddypunishments.command.unban"
)
class UnbanCommand : BaseCommand() {

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

            BanHelper.getHelper().unban(player, sender, silent)

        }catch (e: IndexOutOfBoundsException) {
            tellInvalidArguments(sender, messagesFile.config?.getString("incorrect-usage"))
        }
    }

}