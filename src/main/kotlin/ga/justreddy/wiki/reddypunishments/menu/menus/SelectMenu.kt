package ga.justreddy.wiki.reddypunishments.menu.menus

import com.cryptomorin.xseries.XMaterial
import ga.justreddy.wiki.reddypunishments.menu.ChestMenu
import ga.justreddy.wiki.reddypunishments.utils.Utils
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent

class SelectMenu(private val name: String) : ChestMenu(Utils.format("&cChoose an option"), 27) {

    override fun handleMenu(e: InventoryClickEvent) {
        if (e.currentItem.type == XMaterial.RED_WOOL.parseMaterial() && e.currentItem.itemMeta.displayName.contains(
                Utils.format("&aView the mute history")
            )
        ) {
            MuteHistory(name).open(e.whoClicked as Player)
        } else if (e.currentItem.type == XMaterial.RED_WOOL.parseMaterial() && e.currentItem.itemMeta.displayName.contains(
                Utils.format("&aView the ban history")
            )
        ) {
            BanHistory(name).open(e.whoClicked as Player)
        }
    }

    override fun setMenuItems(player: Player) {
        setFillerGlass()
        val mute = XMaterial.RED_WOOL.parseItem()!!
        val meta = mute.itemMeta!!
        meta.displayName = Utils.format("&aView the mute history of $name")
        mute.itemMeta = meta
        val ban = XMaterial.RED_WOOL.parseItem()!!
        val metaa = mute.itemMeta!!
        metaa.displayName = Utils.format("&aView the ban history of $name")
        ban.itemMeta = metaa
        inv.setItem(12, mute)
        inv.setItem(14, ban)
    }
}