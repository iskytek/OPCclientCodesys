
package utgard;

import org.openscada.opc.lib.da.DataCallback;
import org.openscada.opc.lib.da.Item;
import org.openscada.opc.lib.da.ItemState;


public class DataItem implements DataCallback {

	private Item item;
	private ItemState itemstate;
 

	@Override
	public void changed(Item item, ItemState itemstate) {
		this.item = item;
		this.itemstate = itemstate;
	}
	
	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public ItemState getItemstate() {
		return itemstate;
	}

	public void setItemstate(ItemState itemstate) {
		this.itemstate = itemstate;
	}
}
