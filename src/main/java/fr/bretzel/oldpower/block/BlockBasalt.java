package fr.bretzel.oldpower.block;

import fr.bretzel.oldpower.item.ItemBlockBase;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

import java.util.Random;

public class BlockBasalt extends BlockBase {

    public BlockBasalt(String unlocalizedName) {
        super(unlocalizedName, Material.ROCK);
        this.setResistance(60);
        this.setHardness(4);
        this.setHarvestLevel("Pickaxe", 1);
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return new ItemStack(this).getItem();
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
