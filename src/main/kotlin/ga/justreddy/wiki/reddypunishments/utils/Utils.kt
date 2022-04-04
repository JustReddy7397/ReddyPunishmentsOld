package ga.justreddy.wiki.reddypunishments.utils

import ga.justreddy.wiki.reddypunishments.plugin
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable
import java.util.*


class Utils {

     companion object {

         val CHAT_LINE: String = "&m-----------------------------------------------------"
         val CONSOLE_LINE: String = "*-----------------------------------------------------*"
         val LORE_LINE: String = "&m--------------------------"

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

         fun getDurationMS(time: String): Long {
             var ms: Long = 0
             if (time.lowercase(Locale.getDefault()).contains("s")) ms =
                 time.replace("s", "").toLong() * 1000 + System.currentTimeMillis()
             if (time.lowercase(Locale.getDefault()).contains("m") && !time.lowercase(Locale.getDefault())
                     .contains("o")
             ) ms = time.replace("m", "").toLong() * 1000 * 60 + System.currentTimeMillis()
             if (time.lowercase(Locale.getDefault()).contains("h")) ms =
                 time.replace("h", "").toLong() * 1000 * 60 * 60 + System.currentTimeMillis()
             if (time.lowercase(Locale.getDefault()).contains("d")) ms =
                 time.replace("d", "").toLong() * 1000 * 60 * 60 * 24 + System.currentTimeMillis()
             if (time.lowercase(Locale.getDefault()).contains("w")) ms =
                 time.replace("w", "").toLong() * 1000 * 60 * 60 * 24 * 7 + System.currentTimeMillis()
             if (time.lowercase(Locale.getDefault()).contains("m") && time.lowercase(Locale.getDefault())
                     .contains("o")
             ) ms = time.replace("mo", "").toLong() * 1000 * 60 * 60 * 24 * 30 + System.currentTimeMillis()
             if (time.lowercase(Locale.getDefault()).contains("y")) ms =
                 time.replace("y", "").toLong() * 1000 * 60 * 60 * 24 * 7 * 52 + System.currentTimeMillis()
             return ms
         }

         fun getDurationString(time: String): String {
             var str = "1 day"
             if (time.lowercase(Locale.getDefault()).contains("s")) {
                 str = time.replace("s", "") + " second"
                 if (time.replace("s", "").toInt() > 1) str += "s"
             }
             if (time.lowercase(Locale.getDefault()).contains("m") && !time.lowercase(Locale.getDefault())
                     .contains("o")
             ) {
                 str = time.replace("m", "") + " minute"
                 if (time.replace("m", "").toInt() > 1) str += "s"
             }
             if (time.lowercase(Locale.getDefault()).contains("h")) {
                 str = time.replace("h", "") + " hour"
                 if (time.replace("h", "").toInt() > 1) str += "s"
             }
             if (time.lowercase(Locale.getDefault()).contains("d")) {
                 str = time.replace("d", "") + " day"
                 if (time.replace("d", "").toInt() > 1) str += "s"
             }
             if (time.lowercase(Locale.getDefault()).contains("w")) {
                 str = time.replace("w", "") + " week"
                 if (time.replace("w", "").toInt() > 1) str += "s"
             }
             if (time.lowercase(Locale.getDefault()).contains("m") && time.lowercase(Locale.getDefault())
                     .contains("o")
             ) {
                 str = time.replace("mo", "") + " month"
                 if (time.replace("mo", "").toInt() > 1) str += "s"
             }
             if (time.lowercase(Locale.getDefault()).contains("y")) {
                 str = time.replace("y", "") + " year"
                 if (time.replace("y", "").toInt() > 1) str += "s"
             }
             return str
         }

    }

}