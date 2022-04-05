package ga.justreddy.wiki.reddypunishments

import com.github.helpfuldeer.commandlib.CommandLib
import ga.justreddy.wiki.reddypunishments.events.EventManager
import ga.justreddy.wiki.reddypunishments.helper.PluginHelper
import ga.justreddy.wiki.reddypunishments.helper.UpdateCheckerHelper
import ga.justreddy.wiki.reddypunishments.helper.config.ConfigHelper
import ga.justreddy.wiki.reddypunishments.helper.database.DatabaseHelper
import ga.justreddy.wiki.reddypunishments.helper.database.MongoHelper
import ga.justreddy.wiki.reddypunishments.helper.database.databaseHelper
import ga.justreddy.wiki.reddypunishments.helper.dependency.DependencyHelper
import ga.justreddy.wiki.reddypunishments.menu.MenuEvent
import ga.justreddy.wiki.reddypunishments.tasks.ModerationTask
import ga.justreddy.wiki.reddypunishments.utils.Utils
import org.bukkit.configuration.InvalidConfigurationException
import java.io.IOException
import java.util.*


lateinit var plugin: ReddyPunishments
lateinit var formatsFile: ConfigHelper
lateinit var messagesFile: ConfigHelper
lateinit var settingsFile: ConfigHelper
lateinit var databaseFile: ConfigHelper

class ReddyPunishments : PluginHelper() {

    private val FORMATS_VERSION = 1
    private val MESSAGES_VERSION = 1
    private val SETTINGS_VERSION = 1
    private val DATABASE_VERSION = 1

    var isMongoConnected: Boolean = false
    var isBungecoordEnabled: Boolean = false

    private val databaseTypes = arrayOf("mongodb", "sql", "mysql")

    override fun onLoad() {
        DependencyHelper()
    }

    override fun onEnable() {
        plugin = this;
        val startTime: Long = System.currentTimeMillis()

        logger.info("Loading commands...")
        CommandLib(this, "ga.justreddy.wiki.reddypunishments.commands")

        logger.info("Loading files...")
        if(!loadFiles()) return

        logger.info("Loading database...")
        if(!registerDatabase()) return

        logger.info("Checking for updates...")
        checkUpdates()

        Utils.sendConsole(
            "&aReddyPunishments v${getPluginVersion()} by ${getPluginAuthor()} enabled in ${(System.currentTimeMillis() - startTime)}ms."
        )
        server.pluginManager.registerEvents(MenuEvent(), this)
        server.pluginManager.registerEvents(EventManager(), this)
        ModerationTask().runTaskTimerAsynchronously(this, 0, 20L)
    }


    override fun onDisable() {
        if(databaseHelper.isConnected()) {
            databaseHelper.closeConnection()
        }
    }

    private fun loadFiles(): Boolean {
        var currentlyLoading = "configuration files"
        try {
            currentlyLoading = "formats.yml"
            formatsFile = ConfigHelper(currentlyLoading)
            if (formatsFile.isOutdated(FORMATS_VERSION)) {
                Utils.error(null, "Outdated formats.yml file.", true);
                return false;
            }
            logger.info("Loaded file: $currentlyLoading")

            currentlyLoading = "messages.yml"
            messagesFile = ConfigHelper(currentlyLoading)
            if (messagesFile.isOutdated(MESSAGES_VERSION)) {
                Utils.error(null, "Outdated messages.yml file.", true)
                return false
            }

            logger.info("Loaded file: $currentlyLoading")

            currentlyLoading = "settings.yml"
            settingsFile = ConfigHelper(currentlyLoading)
            if (settingsFile.isOutdated(SETTINGS_VERSION)) {
                Utils.error(null, "Outdated settings.yml file.", true);
                return false;
            }

            logger.info("Loaded file: $currentlyLoading")

            currentlyLoading = "database.yml"
            databaseFile = ConfigHelper(currentlyLoading)
            if (databaseFile.isOutdated(DATABASE_VERSION)) {
                Utils.error(null, "Outdated database.yml file.", true);
                return false;
            }

            logger.info("Loaded file: $currentlyLoading")

        } catch (ex: IOException) {
            Utils.error(ex, "Failed to load $currentlyLoading.", true)
            return false
        } catch (ex: InvalidConfigurationException) {
            Utils.error(ex, "Failed to load $currentlyLoading.", true)
            return false
        }
        return true
    }

    private fun registerDatabase() : Boolean {

        val type: String = databaseFile.config?.getString("storage")?.lowercase()!!
        val match = Arrays.stream(databaseTypes).anyMatch(type::contains)
        if (!match) {
            Utils.error(null, "Invalid database provided!", true)
            return false
        }

        var connectionString = "empty"

        when(databaseFile.config!!.getString("storage")) {
            "sql" -> {
                val databaseHelper = DatabaseHelper()
                databaseHelper.connectH2()
                connectionString = "SQL"
            }
            "mysql" -> {
                val databaseHelper = DatabaseHelper()
                databaseHelper.connectMySQL(
                    databaseFile.config!!.getString("mysql.host"),
                    databaseFile.config!!.getString("mysql.username"),
                    databaseFile.config!!.getString("mysql.password"),
                    databaseFile.config!!.getString("mysql.database"),
                    databaseFile.config!!.getInt("mysql.port"),
                )
                connectionString = "MySQL"
            }
            "mongodb" -> {
                MongoHelper(databaseFile.config!!.getString("mongodb.uri")!!)
                connectionString = "MongoDB"
                isMongoConnected = true
            }
        }

        logger.info("Successfully connected to the $connectionString database")

        return true

    }


    private fun checkUpdates() {

        server.scheduler.runTaskAsynchronously(this, Runnable {
            // TODO
            val updateChecker = UpdateCheckerHelper(0)

            if(updateChecker.getResult() == UpdateCheckerHelper.Result.OUTDATED) {
                Utils.sendConsole(
                    "&2%line%",
                    "&aA newer version of ${getPluginName()} is available!",
                    "&aCurrent: ${getPluginVersion()}",
                    "&aNew: ${updateChecker.latestVersion}",
                    "&aGet the update here: https://spigotmc.org",
                    "&2%line%"
                    )
                return@Runnable
            }

            if (updateChecker.getResult() == UpdateCheckerHelper.Result.ERROR) {
                Utils.error(null, "Failed to check for updates. You can ignore this.", false);
            }

        })

    }

    fun reloadFiles() {
        formatsFile.reload()
        messagesFile.reload()
        settingsFile.reload()
    }

    override fun getPluginVersion(): String {
        return description.version
    }

    override fun getPluginName(): String {
        return description.name
    }

    override fun getPluginDescription(): String? {
        return description.description
    }

    override fun getPluginAuthor(): String? {
        return description.authors[0]
    }


}