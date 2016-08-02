package fr.bretzel.oldpower.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;

import java.util.List;

import fr.bretzel.oldpower.OldPower;
import fr.bretzel.oldpower.item.ItemBlockBase;

public abstract class BlockBase extends Block {

    private String unlocalizedName = "";

    public BlockBase(String unlocalizedName) {
        super(Material.GROUND);
        this.unlocalizedName = unlocalizedName;
    }

    @Override
    public String getUnlocalizedName() {
        return "tile." + OldPower.MODID  + "." + unlocalizedName;
    }

    public abstract String getTileEntityName();

    public abstract Class<? extends TileEntity> getTileEntity();

    public abstract int hasSubType();

    public String getSubTypeName(int damage) {
        return unlocalizedName;
    }

    public abstract Class<? extends ItemBlockBase> getItemBlock();
}
