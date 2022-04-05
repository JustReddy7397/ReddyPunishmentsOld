package ga.justreddy.wiki.reddypunishments.helper.moderation

import ga.justreddy.wiki.reddypunishments.formatsFile
import ga.justreddy.wiki.reddypunishments.helper.database.databaseHelper
import ga.justreddy.wiki.reddypunishments.helper.history.BanHistory
import ga.justreddy.wiki.reddypunishments.helper.history.MuteHistory
import ga.justreddy.wiki.reddypunishments.messagesFile
import ga.justreddy.wiki.reddypunishments.plugin
import ga.justreddy.wiki.reddypunishments.utils.Utils
import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.sql.ResultSet
import java.sql.SQLException
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

private var muteHelper: MuteHelper = MuteHelper()
class MuteHelper {

    fun mute(target: OfflinePlayer, moderator: CommandSender, stringTime: String, time: Long, silent: Boolean, reason: String) {
        var end = 0L
        end = if (time == -1L) {
            -1L
        } else {
            val current = System.currentTimeMillis()
            val millis: Long = time * 1000L
            current + millis
        }

        if(isMuted(target.uniqueId.toString())) {
            moderator.sendMessage(
                Utils.format(
                    messagesFile.config!!.getString("already-muted")
                        .replace("%player%", target.name)
                        .replace("%prefix%", messagesFile.config!!.getString("prefix"))
                )
            )
            return
        }

        if(plugin.isMongoConnected) {
            if(plugin.isBungecoordEnabled) {
                TODO("Make bungeecord function")
            }
        }else{
            if(plugin.isBungecoordEnabled) {
                TODO("Make bungeecord function")
            }else{
                databaseHelper.update("INSERT INTO mutes (UUID, MODERATOR, NAME, END, REASON, DATE, ACTIVE) VALUES(" +
                        "'${target.uniqueId}', '${moderator.name}', '${target.name}', '$time', '$reason', '${Date().time}', 'true')")
                if (Bukkit.getPlayer(target.name) != null) {
                    Bukkit.getPlayer(target.name).sendMessage(
                        Utils.format(
                            formatsFile.config!!.getString("mute.mute-screen-format")
                        .replace("%reason%", getReason(target.uniqueId.toString()))
                        .replace("%time%", getRemainingTime(target.uniqueId.toString()))
                        .replace("%banner%", getModerator(target.uniqueId.toString()))
                        .replace("%date%", getDate(target.uniqueId.toString()))));
                }

            }

        }
        if(silent) {
            for (player: Player in Bukkit.getOnlinePlayers()){
                if(!player.hasPermission("reddypunishments.bypass.silent")) continue
                if(end == -1L) {
                    player.sendMessage(
                        Utils.format(
                        messagesFile.config!!.getString("silent")
                            .replace("%player%", target.name)
                            .replace("%moderator%", moderator.name)
                            .replace("%type%", "muted")
                            .replace("%prefix%", messagesFile.config!!.getString("prefix"))
                    ))
                }else{
                    player.sendMessage(
                        Utils.format(
                        messagesFile.config!!.getString("silent-temp")
                            .replace("%player%", target.name)
                            .replace("%moderator%", moderator.name)
                            .replace("%type%", "muted")
                            .replace("%time%", stringTime)
                            .replace("%prefix%", messagesFile.config!!.getString("prefix"))
                    ))
                }
            }
        }else{
            if(end == -1L) {
                for (player: Player in Bukkit.getOnlinePlayers()){
                    player.sendMessage(
                        Utils.format(
                        messagesFile.config!!.getString("global")
                            .replace("%player%", target.name)
                            .replace("%moderator%", moderator.name)
                            .replace("%type%", "muted")
                            .replace("%prefix%", messagesFile.config!!.getString("prefix"))
                    ))
                }
            }else{
                for (player: Player in Bukkit.getOnlinePlayers()) {
                    player.sendMessage(
                        Utils.format(
                        messagesFile.config!!.getString("global-temp")
                            .replace("%player%", target.name)
                            .replace("%moderator%", moderator.name)
                            .replace("%type%", "muted")
                            .replace("%time%", stringTime)
                            .replace("%prefix%", messagesFile.config!!.getString("prefix"))
                    ))
                }
            }
        }
    }


    fun mute(target: OfflinePlayer, moderator: CommandSender, stringTime: String, time: Long, reason: String) {
        mute(target, moderator, stringTime, time, false, reason)
    }


    fun unmute(target: OfflinePlayer, moderator: CommandSender, silent: Boolean) {
        if(!isMuted(target.uniqueId.toString())) {
            moderator.sendMessage(
                Utils.format(
                    messagesFile.config!!.getString("not-muted")
                        .replace("%player%", target.name)
                        .replace("%prefix%", messagesFile.config!!.getString("prefix"))
                )
            )
            return
        }
        if (plugin.isMongoConnected) {
            TODO("Make method")
        } else {
            val rs: ResultSet = databaseHelper.getResult("SELECT * FROM mutes WHERE UUID='${target.uniqueId}' AND ACTIVE='true'") as ResultSet
            while (rs.next()){
                if(rs.getString("ACTIVE").equals("true", ignoreCase = true)) {
                    databaseHelper.update("UPDATE mutes SET ACTIVE='false' WHERE UUID='${target.uniqueId}'")
                }
                if(silent) {
                    if(plugin.isBungecoordEnabled) {
                        TODO("Method")
                        return
                    }

                    for(player: Player in Bukkit.getOnlinePlayers()) {
                        if(!player.hasPermission("reddypunishments.bypass.silent")) continue
                        player.sendMessage(
                            Utils.format(
                            messagesFile.config!!.getString("unmute-silent")
                                .replace("%player%", target.name)
                                .replace("%moderator%", moderator.name)
                                .replace("%prefix%", messagesFile.config!!.getString("prefix"))

                        ))
                    }

                }else{
                    for(player: Player in Bukkit.getOnlinePlayers()) {
                        player.sendMessage(
                            Utils.format(
                            messagesFile.config!!.getString("unmute-global")
                                .replace("%player%", target.name)
                                .replace("%moderator%", moderator.name)
                                .replace("%prefix%", messagesFile.config!!.getString("prefix"))
                        ))
                    }
                }
            }
        }

    }

    fun getMutedPlayers(): List<String> {
        val list = mutableListOf<String>()

        val rs: ResultSet = databaseHelper.getResult("SELECT * FROM mutes WHERE ACTIVE='true'") as ResultSet

        while (rs.next()) {
            list.add(rs.getString("NAME"))
        }

        return list
    }

    fun getDate(uuid: String): String {
        val rs: ResultSet = databaseHelper.getResult("SELECT * FROM mutes WHERE UUID='$uuid' AND ACTIVE='true'") as ResultSet

        if (rs.next()) {
            val time = rs.getLong("DATE")
            val formatter: DateFormat = SimpleDateFormat("dd/MM/yyy - HH:mm:ss")
            formatter.timeZone = TimeZone.getTimeZone("UTC")
            return formatter.format(time)
        }
        return ""
    }

    fun getReason(uuid: String): String {

        val rs: ResultSet = databaseHelper.getResult("SELECT * FROM mutes WHERE UUID='$uuid' AND ACTIVE='true'") as ResultSet

        if (rs.next()) {
            return rs.getString("REASON")
        }

        return ""
    }

    fun getModerator(uuid: String): String {

        val rs: ResultSet = databaseHelper.getResult("SELECT * FROM mutes WHERE UUID='$uuid' AND ACTIVE='true'") as ResultSet

        if (rs.next()) {
            return rs.getString("MODERATOR")
        }

        return ""
    }

    fun getHistoryForPlayer(uuid: String): List<MuteHistory> {
        val list = mutableListOf<MuteHistory>()
        val rs: ResultSet = databaseHelper.getResult("SELECT * FROM mutes WHERE UUID='$uuid'") as ResultSet
        println(rs.metaData.columnCount)
        while (rs.next()) {
            list.add(
                MuteHistory(
                    uuid,
                    rs.getString("NAME"),
                    rs.getString("MODERATOR"),
                    rs.getString("END"),
                    false,
                    rs.getString("REASON"),
                    rs.getLong("DATE"),
                    rs.getString("ACTIVE").equals("true", ignoreCase = true)
                )
            )
        }

        return list
    }

    fun getEnd(uuid: String): Long {

        val rs: ResultSet = databaseHelper.getResult("SELECT * FROM mutes WHERE UUID='$uuid' AND ACTIVE='true'") as ResultSet
        try {
            if (rs.next()) {
                return rs.getLong("END")
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        }
        return 0
    }

    fun isMuted(uuid: String) : Boolean {

        val rs: ResultSet = databaseHelper.getResult("SELECT * FROM mutes WHERE UUID='$uuid' AND ACTIVE='true'") as ResultSet

        try{
            return rs.next()
        }catch (e: SQLException) {
            e.printStackTrace()
        }

        return false
    }

    fun getRemainingTime(uuid: String): String {
        val current = System.currentTimeMillis()
        val end: Long = getEnd(uuid)
        if (end == -1L) {
            return Utils.format(
                formatsFile.config!!
                    .getString("mute.permanent-time")
            )
        }
        var millies = end - current
        var seconds = 0L
        var minutes = 0L
        var hours = 0L
        var days = 0L
        while (millies > 1000L) {
            millies -= 1000L
            ++seconds
        }
        while (seconds > 60L) {
            seconds -= 60L
            ++minutes
        }
        while (minutes > 60L) {
            minutes -= 60L
            ++hours
        }
        while (hours > 24L) {
            hours -= 24L
            ++days
        }

        return Utils.format(
            formatsFile.config!!
                .getString("mute.tempmute-time").replace("%days%", days.toString())
                .replace("%hours%", hours.toString()).replace("%minutes%", minutes.toString())
                .replace("%seconds%", seconds.toString())
        )
    }
    companion object {
        fun getHelper() : MuteHelper {
            return muteHelper
        }
    }

}