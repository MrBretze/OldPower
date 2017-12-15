package fr.bretzel.oldpower.block;

import fr.bretzel.oldpower.OldPower;
import fr.bretzel.oldpower.item.ItemBlockBase;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;

public abstract class BlockBase extends Block {

    private String unlocalizedName;

    public BlockBase(String unlocalizedName) {
        super(Material.GROUND);
        this.unlocalizedName = unlocalizedName;
        setCreativeTab(OldPower.tabs);
    }

    public BlockBase(String unlocalizedName, Material material) {
        super(material);
        this.unlocalizedName = unlocalizedName;
        setCreativeTab(OldPower.tabs);
    }

    @Override
    public String getUnlocalizedName() {
        return "tile." + OldPower.MODID  + "." + unlocalizedName;
    }

    public String getOnlyUnlocalizedName() {
        return unlocalizedName;
    }

    public abstract String getTileEntityName();

    public abstract Class<? extends TileEntity> getTileEntity();

    public abstract int hasSubType();

    public String getSubTypeName(int damage) {
        return unlocalizedName;
    }

    public abstract Class<? extends ItemBlockBase> getItemBlock();
}
