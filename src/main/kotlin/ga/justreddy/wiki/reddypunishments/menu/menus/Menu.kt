package ga.justreddy.wiki.reddypunishments.menu.menus

import com.cryptomorin.xseries.XMaterial
import ga.justreddy.wiki.reddypunishments.helper.database.databaseHelper
import ga.justreddy.wiki.reddypunishments.helper.moderation.BanHelper
import ga.justreddy.wiki.reddypunishments.menu.PaginatedChestMenu
import ga.justreddy.wiki.reddypunishments.utils.Utils
import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.SkullMeta
import java.sql.ResultSet
import java.util.*

class Menu : PaginatedChestMenu("hi", 27) {

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
            if ((index + 1) >= BanHelper.getHelper().getBannedPlayers().size) {
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
        for (i in 0 until maxItemsPerPage) {
            index = maxItemsPerPage * page + i;
            if (index >= BanHelper.getHelper().getBannedPlayers().size) break
            if (BanHelper.getHelper().getBannedPlayers()[index].isEmpty()) continue
            val item: ItemStack = XMaterial.PLAYER_HEAD.parseItem()!!
            val meta = item.itemMeta as SkullMeta
            meta.owner = BanHelper.getHelper().getBannedPlayers()[index]
            meta.displayName = BanHelper.getHelper().getBannedPlayers()[index]
            val lore = mutableListOf<String>()
            lore.add(
                "&8Date: &e${
                    BanHelper.getHelper().getDate(getUuid(BanHelper.getHelper().getBannedPlayers()[index]))
                }"
            )
            lore.add(
                "&8Moderator: &e${
                    BanHelper.getHelper().getModerator(getUuid(BanHelper.getHelper().getBannedPlayers()[index]))
                }"
            )
            lore.add(
                "&8Reason: &e${
                    BanHelper.getHelper().getReason(getUuid(BanHelper.getHelper().getBannedPlayers()[index]))
                }"
            )
            meta.lore = Utils.formatList(lore)
            item.itemMeta = meta
            inv.addItem(item)
        }


        val rs: ResultSet = databaseHelper.getResult("SELECT * FROM bans WHERE UUID='${player.uniqueId}'") as ResultSet
        while (rs.next()) {

        }
    }

    private fun getUuid(name: String): String {
        return Bukkit.getOfflinePlayer(name).uniqueId.toString()
    }


}