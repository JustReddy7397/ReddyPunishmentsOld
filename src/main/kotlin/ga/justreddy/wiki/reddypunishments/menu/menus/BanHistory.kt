package ga.justreddy.wiki.reddypunishments.menu.menus

import com.cryptomorin.xseries.XMaterial
import ga.justreddy.wiki.reddypunishments.helper.moderation.BanHelper
import ga.justreddy.wiki.reddypunishments.menu.PaginatedChestMenu
import ga.justreddy.wiki.reddypunishments.utils.Utils
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent

class BanHistory(private val name: String) : PaginatedChestMenu("Ban History of $name", 27) {

    private var count: Int = 0

    override fun handleMenu(e: InventoryClickEvent) {
        val p = e.whoClicked as Player
        if (e.currentItem!!.type == XMaterial.LIME_DYE.parseMaterial() && e.currentItem!!.itemMeta!!
                .displayName.equals(Utils.format("&aLeft"), ignoreCase = true)
        ) {
            if (page == 0) {
                p.sendMessage("You are already on the first page.")
            } else {
                page -= 1
                super.open(p)
            }
        } else if (e.currentItem!!.type == XMaterial.LIME_DYE.parseMaterial() && e.currentItem!!.itemMeta!!
                .displayName.equals(Utils.format("&aRight"), ignoreCase = true)
        ) {
            if ((index + 1) >= BanHelper.getHelper().getHistoryForPlayer(getUuid(name)).size) {
                p.sendMessage("You are on the last page.")
            } else {
                page += 1
                super.open(p)
            }
        } else if (e.currentItem!!.type == XMaterial.BARRIER.parseMaterial()) {
            p.closeInventory()
        }
    }

    override fun setMenuItems(player: Player) {
        addMenuBorder()
        val list = BanHelper.getHelper().getHistoryForPlayer(getUuid(name));
        for (i in 0 until maxItemsPerPage) {
            index = maxItemsPerPage * page + i
            if(index >= list.size) break

            val wool = XMaterial.RED_WOOL.parseItem()!!
            val meta = wool.itemMeta!!
            meta.displayName = Utils.format("&e${list[index].getFormattedDate()}")
            val lore = mutableListOf<String>()
            lore.add(
                "&4${Utils.LORE_LINE}"
            )
            lore.add(
                "&7Moderator: &a${list[index].getModerator()}"
            )
            lore.add(
                "&7Reason: &a${list[index].getReason()}"
            )
            lore.add(
                "&7Active: &a${if(list[index].isBanned()) "Yes" else "No"}"
            )
            lore.add(
                "&4${Utils.LORE_LINE}"
            )
            meta.lore = Utils.formatList(lore)
            wool.itemMeta = meta
            inv.addItem(wool)
        }
    }

    private fun getUuid(name: String): String {
        return Bukkit.getOfflinePlayer(name).uniqueId.toString()
    }
}