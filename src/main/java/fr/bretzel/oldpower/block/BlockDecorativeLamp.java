package fr.bretzel.oldpower.block;

import net.minecraft.tileentity.TileEntity;

import fr.bretzel.oldpower.item.ItemBlockBase;

public class BlockDecorativeLamp extends BlockBase {


    public BlockDecorativeLamp(String unlocalizedName) {
        super(unlocalizedName);
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
