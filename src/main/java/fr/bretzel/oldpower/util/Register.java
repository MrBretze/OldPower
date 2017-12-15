package fr.bretzel.oldpower.util;

import fr.bretzel.oldpower.OldPower;
import fr.bretzel.oldpower.block.BlockBase;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class Register {

    public static void registerBlock(Block block) {

        if (block instanceof BlockBase) {
            registerBlockBase((BlockBase) block);
        } else {
            block.setRegistryName(OldPower.MODID, block.getUnlocalizedName().substring(6 + OldPower.MODID.length()));

            ForgeRegistries.BLOCKS.register(block);
            ForgeRegistries.ITEMS.register(new ItemBlock(block).setRegistryName(OldPower.MODID, block.getUnlocalizedName().substring(6 + OldPower.MODID.length())));
        }
    }

    private static void registerBlockBase(BlockBase blockBase) {

        if (blockBase instanceof ITileEntityProvider && blockBase.getTileEntity() != null) {
            try {
                GameRegistry.registerTileEntity(blockBase.getTileEntity(), blockBase.getTileEntityName());
            } catch (IllegalArgumentException e) {}
        }

        if (blockBase.getRegistryName() == null || blockBase.getRegistryName().getResourcePath().isEmpty()) {
            blockBase.setRegistryName(blockBase.getUnlocalizedName().substring(6 + OldPower.MODID.length()));
        }

        if (blockBase.hasSubType() <= 0) {
            ForgeRegistries.BLOCKS.register(blockBase);
            ForgeRegistries.ITEMS.register(new ItemBlock(blockBase).setRegistryName(OldPower.MODID, blockBase.getUnlocalizedName().substring(6 + OldPower.MODID.length())));
            return;
        } else {
            ForgeRegistries.BLOCKS.register(blockBase);
            try {
                ForgeRegistries.ITEMS.register(blockBase.getItemBlock().getConstructor(BlockBase.class).newInstance(blockBase).setRegistryName(blockBase.getRegistryName()));
                return;
            } catch (Exception e) {
                try {
                    ForgeRegistries.ITEMS.register(blockBase.getItemBlock().getConstructor(Block.class).newInstance(blockBase).setRegistryName(blockBase.getRegistryName()));
                    return;
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        }

        ForgeRegistries.BLOCKS.register(blockBase);
    }
}
