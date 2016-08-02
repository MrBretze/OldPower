package fr.bretzel.oldpower.util;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.lang.reflect.InvocationTargetException;

import fr.bretzel.oldpower.OldPower;
import fr.bretzel.oldpower.block.BlockBase;

public class Register {

    public static void registerBlock(BlockBase blockBase) {

        if (blockBase instanceof ITileEntityProvider && blockBase.getTileEntity() != null) {
            try {
                GameRegistry.registerTileEntity(blockBase.getTileEntity(), blockBase.getTileEntityName());
            } catch (IllegalArgumentException e) {}
        }

        if (blockBase.getRegistryName() == null || blockBase.getRegistryName().getResourceDomain().isEmpty()) {
            blockBase.setRegistryName(blockBase.getUnlocalizedName().substring(6 + OldPower.MODID.length()));
        }

        if (blockBase.hasSubType() <= 0) {
            GameRegistry.register(blockBase);
        } else if (blockBase.getItemBlock() != null){
            GameRegistry.register(blockBase);
            try {
                GameRegistry.register(blockBase.getItemBlock().getConstructor(Block.class).newInstance(blockBase).setRegistryName(blockBase.getRegistryName()));
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        } else {
            throw new NullPointerException("The key: " + blockBase + " cant not be registered !");
        }
    }
}
