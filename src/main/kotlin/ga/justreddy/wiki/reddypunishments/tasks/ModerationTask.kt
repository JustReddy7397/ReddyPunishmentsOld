package ga.justreddy.wiki.reddypunishments.tasks

import ga.justreddy.wiki.reddypunishments.helper.moderation.BanHelper
import ga.justreddy.wiki.reddypunishments.helper.moderation.MuteHelper
import ga.justreddy.wiki.reddypunishments.plugin
import org.bukkit.Bukkit
import org.bukkit.scheduler.BukkitRunnable

class ModerationTask : BukkitRunnable() {

    override fun run() {


        if (plugin.isMongoConnected) {

        } else {
            if (plugin.isBungecoordEnabled) {
                TODO("Think of a method on how to do this because i'm an idiot")
            }

            // Ban Stuff
            val banList = BanHelper.getHelper().getBannedPlayers()
            for (i in banList) {
                val current = System.currentTimeMillis()
                val end = BanHelper.getHelper().getEnd(Bukkit.getOfflinePlayer(i).uniqueId.toString())
                if (end < current) {
                    BanHelper.getHelper().unban(Bukkit.getOfflinePlayer(i), Bukkit.getConsoleSender(), true)
                }
            }

            // Mute Stuff
            val muteList = MuteHelper.getHelper().getMutedPlayers()
            for (i in muteList) {
                val current = System.currentTimeMillis()
                val end = MuteHelper.getHelper().getEnd(Bukkit.getOfflinePlayer(i).uniqueId.toString())
                if (end < current) {
                    MuteHelper.getHelper().unmute(Bukkit.getOfflinePlayer(i), Bukkit.getConsoleSender(), true)
                }
            }

        }

    }

}