package ga.justreddy.wiki.reddypunishments.events

import ga.justreddy.wiki.reddypunishments.formatsFile
import ga.justreddy.wiki.reddypunishments.helper.database.databaseHelper
import ga.justreddy.wiki.reddypunishments.helper.moderation.BanHelper
import ga.justreddy.wiki.reddypunishments.utils.Utils
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerLoginEvent
import java.sql.ResultSet

class EventManager : Listener {
    
    @EventHandler
    fun onPlayerLogin(e: PlayerLoginEvent) {

        val player = e.player
        println(BanHelper.getHelper().isBanned(player.uniqueId.toString()))
        if(BanHelper.getHelper().isBanned(player.uniqueId.toString())){
            val current: Long = System.currentTimeMillis()
            val end: Long = BanHelper.getHelper().getEnd(player.uniqueId.toString())
            println("$end | $current | ${current < end}")
            if((current < end) || end == -1L){
                e.disallow(PlayerLoginEvent.Result.KICK_BANNED,
                    Utils.format(
                        formatsFile.config!!.getString("ban.ban-screen-format")
                        .replace("%reason%", BanHelper.getHelper().getReason(player.uniqueId.toString()))
                        .replace("%time%", BanHelper.getHelper().getRemainingTime(player.uniqueId.toString()))
                        .replace("%banner%", BanHelper.getHelper().getModerator(player.uniqueId.toString()))))
            }
        }
    }

    @EventHandler
    fun onPlayerJoin(e: PlayerJoinEvent) {
        val player = e.player
        if (BanHelper.getHelper().isBanned(player.uniqueId.toString())) {
            val rs: ResultSet =
                databaseHelper.getResult("SELECT * FROM bans WHERE UUID='${player.uniqueId}'") as ResultSet
            while (rs.next()) {
                if (rs.getString("ACTIVE").equals("true", ignoreCase = true)) {
                    databaseHelper.update("UPDATE bans SET ACTIVE='false' WHERE UUID='${player.uniqueId}'")
                }
            }
        }
    }
    
}