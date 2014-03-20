package dbStatsCore.ASM;

import dbStats.API.Events.ItemCrafted;
import dbStats.API.Events.PickupFromSlot;
import dbStats.API.Events.PlayerBlockBreak;
import dbStats.API.Events.PlayerBlockPlace;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

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
	
	public static boolean onPickupFromSlot(int clickMethod, Slot slot, EntityPlayer player, ItemStack item, Container container)
	{
		PickupFromSlot ev = new PickupFromSlot(slot, player, item, container, clickMethod);
		MinecraftForge.EVENT_BUS.post(ev);
		return !ev.isCanceled();
	}
}
