package fr.bretzel.oldpower.block;

import net.minecraft.item.EnumDyeColor;
import net.minecraft.tileentity.TileEntity;

import fr.bretzel.oldpower.item.ItemBlockBase;
import fr.bretzel.oldpower.item.ItemBlockLamp;
import fr.bretzel.oldpower.tiles.TileDecoLamp;

public class BlockDecorativeLamp extends BlockBase {


    public BlockDecorativeLamp(String unlocalizedName) {
        super(unlocalizedName);
    }

    @Override
    public String getTileEntityName() {
        return "tile.oldpower.decorative_lamps";
    }

    @Override
    public Class<? extends TileEntity> getTileEntity() {
        return TileDecoLamp.class;
    }

    @Override
    public int hasSubType() {
        return EnumDyeColor.values().length;
    }

    @Override
    public Class<? extends ItemBlockBase> getItemBlock() {
        return ItemBlockLamp.class;
    }
}
