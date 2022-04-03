package ga.justreddy.wiki.reddypunishments.helper

import ga.justreddy.wiki.reddypunishments.plugin
import java.io.IOException
import java.net.URL
import java.util.*


class UpdateCheckerHelper(resourceId: Int) {

    private var resourceId = 0

    var latestVersion: String? = null

    /**
     * Creates a new update checker instance and caches the latest version from Spigot. Should be
     * called asynchronously.
     *
     * @param resourceId The resource ID on SpigotMC
     */

    init {
        this.resourceId = resourceId
        latestVersion = retrieveVersionFromSpigot()
    }

    /**
     * Checks if this plugin version is the same as the one on Spigot.
     *
     * @return The update check result
     */
    fun getResult(): Result {
        if (latestVersion == null) {
            return Result.ERROR
        }
        return if (plugin.getPluginDescription().equals(latestVersion)) {
            Result.UP_TO_DATE
        } else Result.OUTDATED
    }

    private fun retrieveVersionFromSpigot(): String? {
        try {
            URL(
                "https://api.spigotmc.org/legacy/update.php?resource=$resourceId"
            ).openStream().use { inputStream ->
                Scanner(inputStream).use { scanner ->
                    if (scanner.hasNext()) {
                        return scanner.next()
                    }
                }
            }
        } catch (ex: IOException) {
            return null
        }
        return null
    }

    // The result of the update check
    enum class Result {
        /**
         * The plugin version matches the one on the resource.
         */
        UP_TO_DATE,
        /**
         * The plugin version does not match the one on the resource.
         */
        OUTDATED,
        /**
         * There was an error whilst checking for updates.
         */
        ERROR
    }

}