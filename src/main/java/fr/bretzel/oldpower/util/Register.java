package fr.bretzel.oldpower.util;

import fr.bretzel.oldpower.OldPower;
import fr.bretzel.oldpower.block.BlockBase;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class Register {

    public static void registerBlock(Block block) {

        if (block instanceof BlockBase) {
            registerBlockBase((BlockBase) block);
        } else {
            block.setRegistryName(OldPower.MODID, block.getUnlocalizedName().substring(6 + OldPower.MODID.length()));

            GameRegistry.register(block);
            GameRegistry.register(new ItemBlock(block), block.getRegistryName());
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
            GameRegistry.register(blockBase);
            GameRegistry.register(new ItemBlock(blockBase), blockBase.getRegistryName());
            return;
        } else {
            GameRegistry.register(blockBase);
            try {
                GameRegistry.register(blockBase.getItemBlock().getConstructor(BlockBase.class).newInstance(blockBase).setRegistryName(blockBase.getRegistryName()));
                return;
            } catch (Exception e) {
                try {
                    GameRegistry.register(blockBase.getItemBlock().getConstructor(Block.class).newInstance(blockBase).setRegistryName(blockBase.getRegistryName()));
                    return;
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        }

        GameRegistry.register(blockBase);
    }
}
