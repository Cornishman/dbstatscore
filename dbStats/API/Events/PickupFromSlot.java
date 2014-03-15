package dbStats.API.Events;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.Cancelable;
import net.minecraftforge.event.Event;

@Cancelable
public class PickupFromSlot extends Event {
	public final Slot slot;
	public final EntityPlayer player;
	public final ItemStack itemStack;
    public final Container container;
    public final int clickMethod;
	
	public PickupFromSlot(Slot slot, EntityPlayer player, ItemStack itemStack, Container container, int clickMethod)
	{
		this.slot = slot;
		this.player = player;
		this.itemStack = itemStack;
        this.container = container;
        this.clickMethod = clickMethod;
	}
}
