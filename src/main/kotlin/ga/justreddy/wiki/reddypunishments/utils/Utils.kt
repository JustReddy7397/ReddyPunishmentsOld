package ga.justreddy.wiki.reddypunishments.utils

import ga.justreddy.wiki.reddypunishments.plugin
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable
import java.util.*


class Utils {

     companion object {

         val CHAT_LINE: String = "&m-----------------------------------------------------"
         val CONSOLE_LINE: String = "*-----------------------------------------------------*"

        fun format(@NotNull msg: String) : String {
            return ChatColor.translateAlternateColorCodes('&', msg);
        }

        fun formatList(@Nullable list: MutableList<String>) : MutableList<String> {
            val lines: MutableList<String> = ArrayList()

            for(line: String in list) {
                lines.add(format(line))
            }
            return lines
        }

         fun sendMessage(player: Player, message: String) {
             player.sendMessage(format(message.replace("%line%", CHAT_LINE)))
         }

         fun sendMessage(player: Player, vararg message: String) {
             for(line: String in message) {
                 sendMessage(player, line)
             }
         }

         fun sendConsole(message: String) {
             Bukkit.getConsoleSender().sendMessage(format(message).replace("%line%", CONSOLE_LINE))
         }

         fun sendConsole(vararg message: String) {
             for(line: String in message) {
                 sendConsole(line)
             }
         }

         fun error(throwable: Throwable?, description: String, disable: Boolean) {
             if (throwable != null) throwable.printStackTrace()

             sendConsole(
                 "&4%line%",
                 "&cAn internal error has occurred in ${plugin.description.name}!",
                 "&cContact the plugin author if you cannot fix this error.",
                 "&cDescription: &6$description",
                 "&4%line%"
                 )

             if (disable && Bukkit.getPluginManager().isPluginEnabled(plugin)) {
                 Bukkit.getPluginManager().disablePlugin(plugin);
             }

         }

    }

}