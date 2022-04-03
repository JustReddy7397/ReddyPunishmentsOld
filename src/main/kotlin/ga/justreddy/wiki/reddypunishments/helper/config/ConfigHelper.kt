package ga.justreddy.wiki.reddypunishments.helper.config

import ga.justreddy.wiki.reddypunishments.plugin
import org.bukkit.configuration.InvalidConfigurationException
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File
import java.io.IOException


class ConfigHelper(private val name: String) {

    /**
     * The name of the key that stores an integer value, which is the current configuration file
     * version.
     */
    private val VERSION_KEY = "config-version"

    /**
     * The system file of the config.
     */

    private var file: File? = null
    get() = field

    /**
     * The actual configuration object which is what you use for modifying and accessing the config.
     */

    var config: FileConfiguration? = null

    /**
     * Loads a YAML configuration file. If the file does not exist, a new file will be copied from the
     * project's resources folder.
     *
     * @param name The name of the config, ending in .yml
     * @throws InvalidConfigurationException If there was a formatting/parsing error in the config
     * @throws IOException                   If the file could not be created and/or loaded
     */
    init {
        val completeName = if (name.endsWith(".yml")) name else "$name.yml"
        val configFile = File(plugin.dataFolder, completeName)
        if (!configFile.exists()) {
            configFile.getParentFile().mkdirs()
            plugin.saveResource(completeName, false)
        }
        file = configFile
        config = YamlConfiguration.loadConfiguration(file!!)
        reload()
    }


    /**
     * Reloads the configuration file.
     *
     * @throws InvalidConfigurationException If there was a formatting/parsing error in the config
     * @throws IOException                   If the file could not be reloaded
     */
    @Throws(InvalidConfigurationException::class, IOException::class)
    fun reload() {
        config?.load(file!!)
    }

    /**
     * Saves the configuration file (after making edits).
     *
     * @throws IOException If the file could not be saved
     */
    @Throws(IOException::class)
    fun save() {
        config?.save(file!!)
    }

    /**
     * Checks if the config version integer equals or is less than the current version.
     *
     * @param currentVersion The expected value of the config version
     * @return True if the config is outdated, false otherwise
     */
    fun isOutdated(currentVersion: Int): Boolean {
        return config!!.getInt(VERSION_KEY, -1) < currentVersion
    }

}