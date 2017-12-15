package fr.bretzel.oldpower.block;

import fr.bretzel.oldpower.item.ItemBlockBase;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;

public class BlockBasalt extends BlockBase {

    public BlockBasalt(String unlocalizedName) {
        super(unlocalizedName, Material.ROCK);
    }

    @Override
    public String getTileEntityName() {
        return null;
    }

    @Override
    public Class<? extends TileEntity> getTileEntity() {
        return null;
    }

    @Override
    public int hasSubType() {
        return 0;
    }

    @Override
    public Class<? extends ItemBlockBase> getItemBlock() {
        return null;
    }
}
