package ga.justreddy.wiki.reddypunishments.menu

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent


class MenuEvent : Listener {

    @EventHandler
    fun onMenuClick(e: InventoryClickEvent) {
        val holder = e.inventory.holder
        if (holder is ChestMenu) {
            e.isCancelled = true
            if (e.currentItem == null) {
                return
            }
            holder.handleMenu(e)
        }
    }

}