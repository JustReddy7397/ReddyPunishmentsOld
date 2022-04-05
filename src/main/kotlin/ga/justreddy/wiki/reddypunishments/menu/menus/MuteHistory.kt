package ga.justreddy.wiki.reddypunishments.menu.menus

import com.cryptomorin.xseries.XMaterial
import ga.justreddy.wiki.reddypunishments.helper.history.MuteHistory
import ga.justreddy.wiki.reddypunishments.helper.moderation.BanHelper
import ga.justreddy.wiki.reddypunishments.helper.moderation.MuteHelper
import ga.justreddy.wiki.reddypunishments.menu.PaginatedChestMenu
import ga.justreddy.wiki.reddypunishments.utils.Utils
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent

class MuteHistory(private val name: String) : PaginatedChestMenu(Utils.format("&aMute History of &l$name"), 27) {

    override fun handleMenu(e: InventoryClickEvent) {
        val p = e.whoClicked as Player
        if (e.currentItem!!.type == XMaterial.LIME_DYE.parseMaterial() && e.currentItem!!.itemMeta!!
                .displayName.equals(Utils.format("&aLeft"), ignoreCase = true)
        ) {
            if (page == 0) {
                p.sendMessage(Utils.format("&cYou are already on the first page."))
            } else {
                page -= 1
                super.open(p)
            }
        } else if (e.currentItem!!.type == XMaterial.LIME_DYE.parseMaterial() && e.currentItem!!.itemMeta!!
                .displayName.equals(Utils.format("&aRight"), ignoreCase = true)
        ) {
            if ((index + 1) >= MuteHelper.getHelper().getHistoryForPlayer(getUuid(name)).size) {
                p.sendMessage(Utils.format("&cYou are on the last page."))
            } else {
                page += 1
                super.open(p)
            }
        } else if (e.currentItem!!.type == XMaterial.BARRIER.parseMaterial()) {
            SelectMenu(name).open(p)
        }
    }

    override fun setMenuItems(player: Player) {
        addMenuBorder()
        val list = MuteHelper.getHelper().getHistoryForPlayer(getUuid(name));
        for (i in 0 until maxItemsPerPage) {
            index = maxItemsPerPage * page + i
            if (index >= list.size) break

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
                "&7Active: &a${if (list[index].isMuted()) "Yes" else "No"}"
            )
            /*           lore.add(
                           ""
                       )
                       lore.add(
                           "&4Click to edit this punishment"
                       )*/
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