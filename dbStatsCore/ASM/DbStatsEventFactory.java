package dbStatsCore.ASM;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import dbStats.API.Events.ItemCrafted;
import dbStats.API.Events.PickupFromSlot;
import dbStats.API.Events.PlayerBlockBreak;
import dbStats.API.Events.PlayerBlockPlace;

public class DbStatsEventFactory {

	public static boolean onBlockBreak(World world, int x, int y, int z, Block block, int metadata, EntityPlayer player)
	{
		PlayerBlockBreak ev = new PlayerBlockBreak(world, x, y, z, player);
		MinecraftForge.EVENT_BUS.post(ev);
		return !ev.isCanceled();
	}
	
	public static boolean onBlockPlace(World world, int x, int y, int z, EntityPlayer player, ItemStack itemStack, boolean result)
	{
		if (result) {
		    PlayerBlockPlace ev = new PlayerBlockPlace(itemStack, player, world, x, y, z);
		    MinecraftForge.EVENT_BUS.post(ev);
		    return !ev.isCanceled();
		}
		return false;
    }
	
	public static boolean onItemCrafted(EntityPlayer player, ItemStack item, int amount)
	{
		ItemCrafted ev = new ItemCrafted(player, item, amount);
		MinecraftForge.EVENT_BUS.post(ev);
		return !ev.isCanceled();
	}
	
	public static boolean onPickupFromSlot(Slot slot, EntityPlayer player, ItemStack item)
	{
        //TODO : Add object that made the call (this) to here (Allows for better mod integration... looking at you Forestry!)
		PickupFromSlot ev = new PickupFromSlot(slot, player, item);
		MinecraftForge.EVENT_BUS.post(ev);
		return !ev.isCanceled();
	}
}
