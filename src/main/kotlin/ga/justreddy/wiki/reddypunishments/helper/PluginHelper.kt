package ga.justreddy.wiki.reddypunishments.helper

import pluginlib.DependentJavaPlugin

abstract class PluginHelper : DependentJavaPlugin() {

    override fun onEnable() {}

    override fun onDisable() {}

    abstract fun getPluginVersion() : String

    abstract fun getPluginName() : String

    abstract fun getPluginDescription() : String?

    abstract fun getPluginAuthor() : String?

}