package ga.justreddy.wiki.reddypunishments.menu

import com.cryptomorin.xseries.XMaterial
import ga.justreddy.wiki.reddypunishments.utils.Utils
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta

abstract class PaginatedChestMenu(menuName: String, size: Int) : ChestMenu(menuName, size) {

    protected var page: Int = 0

    protected var index: Int = 0

    protected var maxItemsPerPage: Int = 0

    private val menuName: String
    private val size: Int

    init {
        this.menuName = menuName
        this.size = size
    }

    fun addMenuBorder() {
        val left = XMaterial.LIME_DYE.parseItem()!!
        val meta: ItemMeta = left.itemMeta!!
        meta.setDisplayName(Utils.format("&aLeft"));
        left.itemMeta = meta;
        val right = XMaterial.LIME_DYE.parseItem()!!
        val metaa = right.itemMeta!!
        metaa.setDisplayName(Utils.format("&aRight"))
        right.itemMeta = metaa
        val back = XMaterial.BARRIER.parseItem()!!
        val metaaa = back.itemMeta!!
        metaaa.setDisplayName(Utils.format("&cBack"))
        back.itemMeta = metaaa
        when(size) {
            9 -> {
                Utils.error(null, "The paginated menu is too small, min size is 18", false)
                return
            }
            18 -> {
                inv.setItem(0, FILLER_GLASS)
                inv.setItem(8, FILLER_GLASS)
                maxItemsPerPage = 7
                inv.setItem(12, left)
                inv.setItem(14, right)
                inv.setItem(13, back)
                for(i in 9 until 18) {
                    if (inv.getItem(i) == null) {
                        inv.setItem(i, FILLER_GLASS)
                    }
                }
                return
            }
            27 -> {
                // Seven because the row above the middle one will get glassed too!
                maxItemsPerPage = 7
                inv.setItem(17, FILLER_GLASS)
                inv.setItem(9, FILLER_GLASS)
                inv.setItem(21, left)
                inv.setItem(22, back)
                inv.setItem(23, right)
                for (i in 0 until 9) {
                    if (inv.getItem(i) == null) {
                        inv.setItem(i, FILLER_GLASS)
                    }
                }
                for (i in 18 until 27){
                    if (inv.getItem(i) == null) {
                        inv.setItem(i, FILLER_GLASS)
                    }
                }
                return
            }
            36 -> {

            }
        }


    }

}