package dbStatsCore.ASM;

import java.util.HashMap;
import java.util.Iterator;

import net.minecraft.launchwrapper.IClassTransformer;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.TypeInsnNode;
import org.objectweb.asm.tree.VarInsnNode;

public class DSClassTransformer implements IClassTransformer {
	
	private static final HashMap<String, String> obfuscatedValues;
	static {
		obfuscatedValues = new HashMap<String, String>();
		
		//Class Files
		obfuscatedValues.put("WorldJavaClass", "abw");
		obfuscatedValues.put("EntityPlayerJavaClass","uf");
		obfuscatedValues.put("ItemStackJavaClass","ye");
		obfuscatedValues.put("BlockJavaClass","aqz");
		obfuscatedValues.put("ItemInWorldManagerJavaClass","jw");
		obfuscatedValues.put("EntityPlayerMPJavaClass","jv");
		obfuscatedValues.put("SlotJavaClass","we");
		obfuscatedValues.put("ContainerJavaClass", "uy");
		
		//Fields
		obfuscatedValues.put("WorldField","a");
		obfuscatedValues.put("EntityPlayerField","b");
		obfuscatedValues.put("InventoryItemStacksField", "b");
		obfuscatedValues.put("StackSizeField", "b");
		obfuscatedValues.put("ItemIdField", "d");
		
		//Secondary class files
		obfuscatedValues.put("ItemInWorldManager","jw");
		obfuscatedValues.put("Block","aqz");
		obfuscatedValues.put("ItemStack","ye");
		obfuscatedValues.put("Container","uy");
		
		//Methods
		obfuscatedValues.put("BlockPlace","a");
		obfuscatedValues.put("BlockBreak","d");
		obfuscatedValues.put("ItemCraft","a");
		obfuscatedValues.put("SlotClick","a");
		obfuscatedValues.put("CanTakeStack", "a");
		obfuscatedValues.put("GetItemStack", "o");
		obfuscatedValues.put("OnPickupFromSlot", "a");
		obfuscatedValues.put("GetMaxStackSize", "e");
		obfuscatedValues.put("GetHasSubtypes", "h");
		obfuscatedValues.put("ActivateBlockOrUseItem", "a");
	}
	
	private static final HashMap<String, String> nonObfuscatedValues;
	static
	{
		nonObfuscatedValues = new HashMap<String, String>();
		//Class files
		nonObfuscatedValues.put("WorldJavaClass", "net/minecraft/world/World");
		nonObfuscatedValues.put("EntityPlayerJavaClass", "net/minecraft/entity/player/EntityPlayer");
		nonObfuscatedValues.put("ItemStackJavaClass","net/minecraft/item/ItemStack");
		nonObfuscatedValues.put("BlockJavaClass","net/minecraft/block/Block");
		nonObfuscatedValues.put("ItemInWorldManagerJavaClass","net/minecraft/item/ItemInWorldManager");
		nonObfuscatedValues.put("EntityPlayerMPJavaClass","net/minecraft/entity/player/EntityPlayerMP");
		nonObfuscatedValues.put("SlotJavaClass","net/minecraft/inventory/Slot");
		nonObfuscatedValues.put("ContainerJavaClass", "net/minecraft/inventory/Container");
		
		//Fields
		nonObfuscatedValues.put("WorldField","theWorld");
		nonObfuscatedValues.put("EntityPlayerField","thisPlayerMP");
		nonObfuscatedValues.put("InventoryItemStacksField", "inventoryItemStacks");
		nonObfuscatedValues.put("StackSizeField", "stackSize");
		nonObfuscatedValues.put("ItemIdField", "itemID");
		
		//Secondary class files
		nonObfuscatedValues.put("ItemInWorldManager","net.minecraft.item.ItemInWorldManager");
		nonObfuscatedValues.put("Block","net.minecraft.block.Block");
		nonObfuscatedValues.put("ItemStack","net.minecraft.item.ItemStack");
		nonObfuscatedValues.put("Container","net.minecraft.inventory.Container");
		
		//Methods
		nonObfuscatedValues.put("BlockPlace","tryPlaceItemIntoWorld");
		nonObfuscatedValues.put("BlockBreak","removeBlock");
		nonObfuscatedValues.put("ItemCraft","onCrafting");
		nonObfuscatedValues.put("SlotClick","slotClick");
		nonObfuscatedValues.put("CanTakeStack", "canTakeStack");
		nonObfuscatedValues.put("GetItemStack", "getItemStack");
		nonObfuscatedValues.put("OnPickupFromSlot", "onPickupFromSlot");
		nonObfuscatedValues.put("GetMaxStackSize", "getMaxStackSize");
		nonObfuscatedValues.put("GetHasSubtypes", "getHasSubtypes");
		nonObfuscatedValues.put("ActivateBlockOrUseItem", "activateBlockOrUseItem");
	}
	
	private static String GetObfuscationValue(String key, boolean obfuscated)
	{
		return obfuscated ? obfuscatedValues.get(key) : nonObfuscatedValues.get(key);
	}
	
	@Override
	public byte[] transform(String arg0, String arg1, byte[] arg2) {
		if (arg0.equals(GetObfuscationValue("ItemInWorldManager", true)))
		{
			System.out.println("[DbStatsCore] Inside Obfuscated ItemInWorldManager About to Patch: " + arg0);
			return patchItemInWorldManager(arg0, arg2, true);
		}
		if (arg0.equals(GetObfuscationValue("ItemInWorldManager", false)))
		{
			System.out.println("[DbStatsCore] Inside ItemInWorldManager About to patch: " + arg0);
			return patchItemInWorldManager(arg0, arg2, false);
		}

		if (arg0.equals(GetObfuscationValue("ItemStack", true)))
		{
			System.out.println("[DbStatsCore] Inside Obfuscated ItemStack About to Patch: " + arg0);
			return patchItemStack(arg0, arg2, true);
		}
		if (arg0.equals(GetObfuscationValue("ItemStack", false)))
		{
			System.out.println("[DbStatsCore] Inside ItemStack About to Patch: " + arg0);
			return patchItemStack(arg0, arg2, false);
		}
		
		if (arg0.equals(GetObfuscationValue("Container", true)))
		{
			System.out.println("[DbStatsCore] Inside Obfuscated Container About to Patch: " + arg0);
			return patchContainer(arg0, arg2, true);
		}
		if (arg0.equals(GetObfuscationValue("Container", false)))
		{
			System.out.println("DbStatsCore] Inside Container About to Patch: " + arg0);
			return patchContainer(arg0, arg2, false);
		}
		
		return arg2;
	}
	
	private byte[] patchContainer(String name, byte[] bytes, boolean obfuscated)
	{
		System.out.println("[DbStatsCore] Patching Container...");

		ClassNode classNode = new ClassNode();
		ClassReader classReader = new ClassReader(bytes);
		classReader.accept(classNode, 0);

        for (MethodNode m : classNode.methods) {
            if ((m.name.equals(GetObfuscationValue("SlotClick", obfuscated))) && (m.desc.equals("(IIIL" + GetObfuscationValue("EntityPlayerJavaClass", obfuscated)
                    + ";)L" + GetObfuscationValue("ItemStackJavaClass", obfuscated) + ";"))) {

                int offsetClickEmptyHand;
                int offsetClickHeldItem;

                int offset;
                for (offset = 0; offset < m.instructions.size(); offset++) {
                    if (m.instructions.get(offset).getOpcode() == Opcodes.ALOAD && ((VarInsnNode) m.instructions.get(offset)).var == 7) {
                        if (m.instructions.get(offset + 1).getOpcode() == 198) {
                            if (m.instructions.get(offset + 2).getOpcode() == Opcodes.ALOAD && ((VarInsnNode) m.instructions.get(offset + 2)).var == 7) {
                                if (m.instructions.get(offset + 3).getOpcode() == Opcodes.ALOAD && ((VarInsnNode) m.instructions.get(offset + 3)).var == 4) {
                                    if (m.instructions.get(offset + 4).getOpcode() == Opcodes.INVOKEVIRTUAL && ((MethodInsnNode) m.instructions.get(offset + 4)).name.equals(GetObfuscationValue("CanTakeStack", obfuscated))) {
                                        if (m.instructions.get(offset + 5).getOpcode() == Opcodes.IFEQ) {
                                            System.out.println("[DbStatsCore] Patching 1");
                                            InsnList toInject = new InsnList();
                                            toInject.add(new VarInsnNode(25, 7));    //ALOAD 7 -> Slot2
                                            toInject.add(new VarInsnNode(25, 4));    //ALOAD 4 -> par4EntityPlayer
                                            toInject.add(new VarInsnNode(25, 0));    //ALOAD 0 -> this (container)
                                            toInject.add(new FieldInsnNode(180, GetObfuscationValue("ContainerJavaClass", obfuscated), GetObfuscationValue("InventoryItemStacksField", obfuscated),
                                                    "Ljava/util/List;"));    //(GETFIELD, "net/minecraft/inventory/Container", "inventoryItemStacks", "Ljava/util/List;");
                                            toInject.add(new VarInsnNode(21, 1));    //Iload 1 -> par1
                                            toInject.add(new MethodInsnNode(185, "java/util/List", "get", "(I)Ljava/lang/Object;"));    //INVOKEINTERFACE, "java/util/List", "get", "(I)Ljava/lang/Object;");
                                            toInject.add(new TypeInsnNode(192, GetObfuscationValue("ItemStackJavaClass", obfuscated)));    //mv.visitTypeInsn(CHECKCAST, "net/minecraft/item/ItemStack");
                                            toInject.add(new MethodInsnNode(184, "dbStatsCore/ASM/DbStatsEventFactory", "onPickupFromSlot", "(L" + GetObfuscationValue("SlotJavaClass", obfuscated)
                                                    + ";L" + GetObfuscationValue("EntityPlayerJavaClass", obfuscated) + ";L" + GetObfuscationValue("ItemStackJavaClass", obfuscated) + ";)Z"));
                                            toInject.add(new InsnNode(87));    //Pop stack

                                            m.instructions.insert(m.instructions.get(offset + 5), toInject);
                                        }
                                    }
                                }
                            }
                        }
                    } else if (m.instructions.get(offset).getOpcode() == Opcodes.GETFIELD && ((FieldInsnNode) m.instructions.get(offset)).name.equals(GetObfuscationValue("StackSizeField", obfuscated)))    //GetField
                    {
                        if (m.instructions.get(offset + 1).getOpcode() == Opcodes.ICONST_1)    //ICONST_1
                        {
                            if (m.instructions.get(offset + 2).getOpcode() == Opcodes.IADD)    //IADD
                            {
                                if (m.instructions.get(offset + 3).getOpcode() == Opcodes.ICONST_2)    //ICONST_2
                                {
                                    if (m.instructions.get(offset + 4).getOpcode() == Opcodes.IDIV)    //IDIV
                                    {
                                        if (m.instructions.get(offset + 7).getOpcode() == Opcodes.ISTORE)    //ISTORE (skip label and f_same1 *+7)
                                        {
                                            for (offsetClickEmptyHand = offset + 7; offsetClickEmptyHand < m.instructions.size(); offsetClickEmptyHand++) {
                                                if (m.instructions.get(offsetClickEmptyHand).getOpcode() == Opcodes.ALOAD) {
                                                    offsetClickEmptyHand++;
                                                    if (m.instructions.get(offsetClickEmptyHand).getOpcode() == Opcodes.ALOAD) {
                                                        offsetClickEmptyHand++;
                                                        if (m.instructions.get(offsetClickEmptyHand).getOpcode() == Opcodes.ALOAD) {
                                                            offsetClickEmptyHand++;
                                                            if (m.instructions.get(offsetClickEmptyHand).getOpcode() == Opcodes.INVOKEVIRTUAL
                                                                    && ((MethodInsnNode) m.instructions.get(offsetClickEmptyHand)).name.equals(GetObfuscationValue("GetItemStack", obfuscated))) {
                                                                offsetClickEmptyHand++;
                                                                if (m.instructions.get(offsetClickEmptyHand).getOpcode() == Opcodes.INVOKEVIRTUAL
                                                                        && ((MethodInsnNode) m.instructions.get(offsetClickEmptyHand)).name.equals(GetObfuscationValue("OnPickupFromSlot", obfuscated))) {
                                                                    System.out.println("[DbStatsCore] Patching 2");
                                                                    InsnList toInject = new InsnList();
                                                                    toInject.add(new VarInsnNode(25, 7));    //ALOAD 7 -> Slot2
                                                                    toInject.add(new VarInsnNode(25, 4));    //ALOAD 4 -> par4EntityPlayer
                                                                    toInject.add(new VarInsnNode(25, 0));    //ALOAD 0 -> this (container)
                                                                    toInject.add(new FieldInsnNode(180, GetObfuscationValue("ContainerJavaClass", obfuscated), GetObfuscationValue("InventoryItemStacksField", obfuscated),
                                                                            "Ljava/util/List;"));    //(GETFIELD, "net/minecraft/inventory/Container", "inventoryItemStacks", "Ljava/util/List;");
                                                                    toInject.add(new VarInsnNode(21, 1));    //Iload 1 -> par1
                                                                    toInject.add(new MethodInsnNode(185, "java/util/List", "get", "(I)Ljava/lang/Object;"));    //INVOKEINTERFACE, "java/util/List", "get", "(I)Ljava/lang/Object;");
                                                                    toInject.add(new TypeInsnNode(192, GetObfuscationValue("ItemStackJavaClass", obfuscated)));    //mv.visitTypeInsn(CHECKCAST, "net/minecraft/item/ItemStack");
                                                                    toInject.add(new MethodInsnNode(184, "dbStatsCore/ASM/DbStatsEventFactory", "onPickupFromSlot", "(L" + GetObfuscationValue("SlotJavaClass", obfuscated)
                                                                            + ";L" + GetObfuscationValue("EntityPlayerJavaClass", obfuscated) + ";L" + GetObfuscationValue("ItemStackJavaClass", obfuscated) + ";)Z"));
                                                                    toInject.add(new InsnNode(87));    //Pop stack

                                                                    m.instructions.insert(m.instructions.get(offsetClickEmptyHand), toInject);
                                                                    break;
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } else if (m.instructions.get(offset).getOpcode() == Opcodes.ALOAD) {
                        if (m.instructions.get(offset + 1).getOpcode() == Opcodes.GETFIELD && ((FieldInsnNode) m.instructions.get(offset + 1)).name.equals(GetObfuscationValue("ItemIdField", obfuscated))) {
//							System.out.println("[DbStatsCore] Patching 3.1");
                            if (m.instructions.get(offset + 2).getOpcode() == Opcodes.ALOAD) {
                                if (m.instructions.get(offset + 3).getOpcode() == Opcodes.GETFIELD && ((FieldInsnNode) m.instructions.get(offset + 3)).name.equals(GetObfuscationValue("ItemIdField", obfuscated))) {
//									System.out.println("[DbStatsCore] Patching 3.2");
                                    if (m.instructions.get(offset + 4).getOpcode() == Opcodes.IF_ICMPNE) {
                                        if (m.instructions.get(offset + 5).getOpcode() == Opcodes.ALOAD) {
                                            if (m.instructions.get(offset + 6).getOpcode() == Opcodes.INVOKEVIRTUAL && ((MethodInsnNode) m.instructions.get(offset + 6)).name.equals(GetObfuscationValue("GetMaxStackSize", obfuscated))) {
//												System.out.println("[DbStatsCore] Patching 3.3");
                                                if (m.instructions.get(offset + 7).getOpcode() == Opcodes.ICONST_1) {
                                                    if (m.instructions.get(offset + 8).getOpcode() == Opcodes.IF_ICMPLE) {
                                                        if (m.instructions.get(offset + 9).getOpcode() == Opcodes.ALOAD) {
                                                            if (m.instructions.get(offset + 10).getOpcode() == Opcodes.INVOKEVIRTUAL && ((MethodInsnNode) m.instructions.get(offset + 10)).name.equals(GetObfuscationValue("GetHasSubtypes", obfuscated))) {
//																System.out.println("[DbStatsCore] Patching 3.4");
                                                                //Find onPickupFromSlot
                                                                for (offsetClickHeldItem = offset + 10; offsetClickHeldItem < m.instructions.size(); offsetClickHeldItem++) {
                                                                    if (m.instructions.get(offsetClickHeldItem).getOpcode() == Opcodes.ALOAD) {
                                                                        offsetClickHeldItem++;
                                                                        if (m.instructions.get(offsetClickHeldItem).getOpcode() == Opcodes.ALOAD) {
                                                                            offsetClickHeldItem++;
                                                                            if (m.instructions.get(offsetClickHeldItem).getOpcode() == Opcodes.ALOAD) {
                                                                                offsetClickHeldItem++;
                                                                                if (m.instructions.get(offsetClickHeldItem).getOpcode() == Opcodes.INVOKEVIRTUAL
                                                                                        && ((MethodInsnNode) m.instructions.get(offsetClickHeldItem)).name.equals(GetObfuscationValue("GetItemStack", obfuscated))) {
                                                                                    offsetClickHeldItem++;
                                                                                    if (m.instructions.get(offsetClickHeldItem).getOpcode() == Opcodes.INVOKEVIRTUAL
                                                                                            && ((MethodInsnNode) m.instructions.get(offsetClickHeldItem)).name.equals(GetObfuscationValue("OnPickupFromSlot", obfuscated))) {
                                                                                        System.out.println("[DbStatsCore] Patching 3");
                                                                                        InsnList toInject = new InsnList();
                                                                                        toInject.add(new VarInsnNode(25, 7));    //ALOAD 7 -> Slot2
                                                                                        toInject.add(new VarInsnNode(25, 4));    //ALOAD 4 -> par4EntityPlayer
                                                                                        toInject.add(new VarInsnNode(25, 0));    //ALOAD 0 -> this (container)
                                                                                        toInject.add(new FieldInsnNode(180, GetObfuscationValue("ContainerJavaClass", obfuscated), GetObfuscationValue("InventoryItemStacksField", obfuscated),
                                                                                                "Ljava/util/List;"));    //(GETFIELD, "net/minecraft/inventory/Container", "inventoryItemStacks", "Ljava/util/List;");
                                                                                        toInject.add(new VarInsnNode(21, 1));    //Iload 1 -> par1
                                                                                        toInject.add(new MethodInsnNode(185, "java/util/List", "get", "(I)Ljava/lang/Object;"));    //INVOKEINTERFACE, "java/util/List", "get", "(I)Ljava/lang/Object;");
                                                                                        toInject.add(new TypeInsnNode(192, GetObfuscationValue("ItemStackJavaClass", obfuscated)));    //mv.visitTypeInsn(CHECKCAST, "net/minecraft/item/ItemStack");
                                                                                        toInject.add(new MethodInsnNode(184, "dbStatsCore/ASM/DbStatsEventFactory", "onPickupFromSlot", "(L" + GetObfuscationValue("SlotJavaClass", obfuscated)
                                                                                                + ";L" + GetObfuscationValue("EntityPlayerJavaClass", obfuscated) + ";L" + GetObfuscationValue("ItemStackJavaClass", obfuscated) + ";)Z"));
                                                                                        toInject.add(new InsnNode(87));    //Pop stack

                                                                                        m.instructions.insert(m.instructions.get(offsetClickHeldItem), toInject);
                                                                                        break;
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                break;
            }
        }

		ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
		classNode.accept(writer);
		
		System.out.println("[DbStatsCore] Patching Container Complete!");
		return writer.toByteArray();
	}
	
	private byte[] patchItemStack(String name, byte[] bytes, boolean obfuscated) {
		System.out.println("[DbStatsCore] Patching ItemStack...");

		ClassNode classNode = new ClassNode();
		ClassReader classReader = new ClassReader(bytes);
		classReader.accept(classNode, 0);

		Iterator<?> methods = classNode.methods.iterator();
		while (methods.hasNext()) {
			MethodNode m = (MethodNode) methods.next();
			
			if ((m.name.equals(GetObfuscationValue("ItemCraft", obfuscated))) && (m.desc.equals("(L" + GetObfuscationValue("WorldJavaClass", obfuscated)
					+ ";L" + GetObfuscationValue("EntityPlayerJavaClass", obfuscated) + ";I)V"))) {
				System.out.println("[DbStatsCore] Patching 1");

				LabelNode lmm1Node = new LabelNode(new Label());
				LabelNode lmm2Node = new LabelNode(new Label());
				
				int offset = 1;
				while (m.instructions.get(offset).getOpcode() != 177)
				{
					offset++;
				}

				InsnList toInject = new InsnList();

				toInject.add(new VarInsnNode(25, 2));
				toInject.add(new VarInsnNode(25, 0));
				toInject.add(new VarInsnNode(21, 3));
				toInject.add(new MethodInsnNode(184, "dbStatsCore/ASM/DbStatsEventFactory", "onItemCrafted", "(L" + GetObfuscationValue("EntityPlayerJavaClass", obfuscated) 
						+ ";L" + GetObfuscationValue("ItemStackJavaClass", obfuscated) + ";I)Z"));
				toInject.add(lmm2Node);
				toInject.add(lmm1Node);

				m.instructions.insertBefore(m.instructions.get(offset), toInject);
//				System.out.println("[DbStatsCore] Patching ItemStack itemCrafting Complete!");
				break;
			}
		}

		ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
		classNode.accept(writer);
		
		System.out.println("[DbStatsCore] Patching ItemStack Complete!");
		return writer.toByteArray();
	}

	private byte[] patchItemInWorldManager(String name, byte[] bytes, boolean obfuscated) {

		ClassNode classNode = new ClassNode();
		ClassReader classReader = new ClassReader(bytes);
		classReader.accept(classNode, 0);
		
		boolean bothDone = false;

        for (MethodNode m : classNode.methods) {
            if (m.name.equals(GetObfuscationValue("BlockBreak", obfuscated)) && m.desc.equals("(III)Z")) {
                System.out.println("[DbStatsCore] Patching 1");

                int offset = 1;
                while (m.instructions.get(offset).getOpcode() != 198)//IFNULL
                {
                    offset++;
                }
                while (m.instructions.get(offset).getOpcode() != 182)//ALOAD
                {
                    offset++;
                }
                offset += 2;

                int tempOffset = offset;
                for (int i = tempOffset; i < m.instructions.size(); i++) {
                    if (m.instructions.get(i).getOpcode() == 184 && ((MethodInsnNode) m.instructions.get(i)).name.equals("onBlockBreak")) {
                        System.out.println("[DbStatsCore] ItemInWorldManager blockBreak already Patched!");
                        if (bothDone) {
                            break;
                        } else {
                            bothDone = true;
                        }
                    }
                }

                System.out.println("[DbStatsCore] Patching 1.1");

                InsnList toInject = new InsnList();

                toInject.add(new VarInsnNode(25, 0));
                toInject.add(new FieldInsnNode(180, GetObfuscationValue("ItemInWorldManagerJavaClass", obfuscated),
                        GetObfuscationValue("WorldField", obfuscated), "L" + GetObfuscationValue("WorldJavaClass", obfuscated) + ";"));
                toInject.add(new VarInsnNode(21, 1));
                toInject.add(new VarInsnNode(21, 2));
                toInject.add(new VarInsnNode(21, 3));
                toInject.add(new VarInsnNode(25, 4));
                toInject.add(new VarInsnNode(21, 5));
                toInject.add(new VarInsnNode(25, 0));
                toInject.add(new FieldInsnNode(180, GetObfuscationValue("ItemInWorldManagerJavaClass", obfuscated),
                        GetObfuscationValue("EntityPlayerField", obfuscated), "L" + GetObfuscationValue("EntityPlayerMPJavaClass", obfuscated) + ";"));
                toInject.add(new MethodInsnNode(184, "dbStatsCore/ASM/DbStatsEventFactory", "onBlockBreak", "(L" + GetObfuscationValue("WorldJavaClass", obfuscated) + ";IIIL"
                        + GetObfuscationValue("BlockJavaClass", obfuscated) + ";IL" + GetObfuscationValue("EntityPlayerJavaClass", obfuscated) + ";)Z"));

                m.instructions.insertBefore(m.instructions.get(offset), toInject);
//				System.out.println("[DbStatsCore] Patching ItemInWorldManager blockBreak Complete!");
                if (bothDone) {
                    break;
                } else {
                    bothDone = true;
                }
            } else if (m.name.equals(GetObfuscationValue("ActivateBlockOrUseItem", obfuscated)) && m.desc.equals("(L" + GetObfuscationValue("EntityPlayerJavaClass", obfuscated) + ";L" + GetObfuscationValue("WorldJavaClass", obfuscated) + ";L"
                    + GetObfuscationValue("ItemStackJavaClass", obfuscated) + ";IIIIFFF)Z")) {
                System.out.println("[DbStatsCore] Patching 2");

                int offset;
                for (offset = 1; offset < m.instructions.size(); offset++) {
                    if (m.instructions.get(offset).getOpcode() == 182 && ((MethodInsnNode) m.instructions.get(offset)).name.equals(GetObfuscationValue("BlockPlace", obfuscated)))    //INVOKEVIRTUAL "tryPlaceItemIntoWorld"
                    {
                        System.out.println("[DbStatsCore] Patching 2.1");
                        break;
                    }
                }
                offset++;    //ISTORE

                if (offset >= m.instructions.size()) {
                    System.out.println("[DbStatsCore] Error in ItemInWorldManager, Failed on blockPlace patch!");
                    break;
                }

                int tempOffset = offset;
                for (int i = tempOffset; i < m.instructions.size(); i++) {
                    if (m.instructions.get(i).getOpcode() == 184 && ((MethodInsnNode) m.instructions.get(i)).name.equals("onBlockPlace")) {
                        System.out.println("[DbStatsCore] ItemInWorldManager blockPlace already Patched!");
                        if (bothDone) {
                            break;
                        } else {
                            bothDone = true;
                        }
                    }
                }

                System.out.println("[DbStatsCore] Patching 2.2");

                InsnList toInject = new InsnList();
                toInject.add(new VarInsnNode(25, 2));
                toInject.add(new VarInsnNode(21, 4));
                toInject.add(new VarInsnNode(21, 5));
                toInject.add(new VarInsnNode(21, 6));
                toInject.add(new VarInsnNode(25, 1));
                toInject.add(new VarInsnNode(25, 3));

                //No Idea why this is?? The locals stack is different when obfuscated?
                if (obfuscated) {
                    toInject.add(new VarInsnNode(21, 13));
                } else {
                    toInject.add(new VarInsnNode(21, 15));
                }
                toInject.add(new MethodInsnNode(184, "dbStatsCore/ASM/DbStatsEventFactory", "onBlockPlace", "(L" + GetObfuscationValue("WorldJavaClass", obfuscated)
                        + ";IIIL" + GetObfuscationValue("EntityPlayerJavaClass", obfuscated) + ";L" + GetObfuscationValue("ItemStackJavaClass", obfuscated) + ";Z)Z"));
                toInject.add(new InsnNode(87));    //POP stack

                m.instructions.insert(m.instructions.get(offset), toInject);
                //System.out.println("[DbStatsCore] Patching ItemInWorldManager blockPlace Complete!" + Integer.toString(offset));
                if (bothDone) {
                    break;
                } else {
                    bothDone = true;
                }
            }
        }

		ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
		classNode.accept(writer);
		System.out.println("[DbStatsCore] Patching ItemInWorldManager Complete!");
		return writer.toByteArray();
	}
}
