package ga.justreddy.wiki.reddypunishments.helper.moderation

import ga.justreddy.wiki.reddypunishments.plugin
import org.bukkit.OfflinePlayer
import org.bukkit.entity.Player
import java.util.*

private lateinit var banHelper: BanHelper

class BanHelper {

    fun ban(target: OfflinePlayer, moderator: Player, time: Long, reason: String, date: Date) {

    }

    fun unban() {

        if (plugin.isMongoConnected) {

        }else{

        }

    }


    companion object {
        fun getHelper(): BanHelper {
            if (banHelper == null) banHelper = BanHelper()
            return banHelper
        }
    }

}