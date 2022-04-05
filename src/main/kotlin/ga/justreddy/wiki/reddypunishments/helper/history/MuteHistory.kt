package ga.justreddy.wiki.reddypunishments.helper.history

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class MuteHistory(
    uuid: String,
    name: String,
    moderator: String,
    time: String,
    silent: Boolean,
    reason: String,
    date: Long,
    active: Boolean
) {

    private val uuid: String
    private val name: String
    private val moderator: String
    private val time: String
    private val silent: Boolean
    private val reason: String
    private val date: Long
    private val active: Boolean

    init {
        this.uuid = uuid
        this.name = name
        this.moderator = moderator
        this.time = time
        this.silent = silent
        this.reason = reason
        this.date = date
        this.active = active
    }

    fun getUuid(): String {
        return uuid
    }

    fun getName(): String {
        return name
    }

    fun getModerator(): String {
        return moderator
    }

    fun getTime(): String {
        return time
    }

    fun isSilent(): Boolean {
        return silent
    }

    fun getReason(): String {
        return reason
    }

    fun getDate(): Long {
        return date
    }

    fun getFormattedDate(): String {
        val formatter: DateFormat = SimpleDateFormat("dd/MM/yyy - HH:mm:ss")
        formatter.timeZone = TimeZone.getTimeZone("UTC")
        return formatter.format(date)
    }


    fun isMuted(): Boolean {
        return active
    }
}