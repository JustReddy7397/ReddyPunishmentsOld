package ga.justreddy.wiki.reddypunishments.menu

import com.cryptomorin.xseries.XMaterial
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder
import org.bukkit.inventory.ItemStack




abstract class ChestMenu(menuName: String, size: Int) : InventoryHolder {

    protected lateinit var inv: Inventory

    protected val FILLER_GLASS: ItemStack = XMaterial.BLACK_STAINED_GLASS_PANE.parseItem()!!

    private val menuName: String
    private val size: Int

    init {
        this.menuName = menuName
        this.size = size
    }

    abstract fun handleMenu(e: InventoryClickEvent)

    abstract fun setMenuItems(player: Player)

    fun open(player: Player) {

        inv = Bukkit.createInventory(this, size, menuName);

        setMenuItems(player)

        player.openInventory(inv)

    }


    override fun getInventory(): Inventory {
        return inv
    }

    fun setFillerGlass() {
        for (i in 0 until size) {
            if (inventory.getItem(i) == null) {
                inventory.setItem(i, FILLER_GLASS)
            }
        }
    }

}