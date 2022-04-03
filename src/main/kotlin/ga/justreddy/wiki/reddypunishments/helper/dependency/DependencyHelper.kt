package ga.justreddy.wiki.reddypunishments.helper.dependency

import ga.justreddy.wiki.reddypunishments.ReddyPunishments
import pluginlib.PluginLib

class DependencyHelper {

    private val COMMANDLIB: PluginLib = PluginLib
        .builder()
        .jitpack()
        .groupId("com.github.HelpfulDeer")
        .artifactId("CommandLib")
        .version("1.2")
        .build()


    init {
        COMMANDLIB.load(ReddyPunishments::class.java)
    }

}