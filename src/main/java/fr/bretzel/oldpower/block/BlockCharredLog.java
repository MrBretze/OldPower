package fr.bretzel.oldpower.block;

import fr.bretzel.oldpower.OldPower;
import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.Random;

public class BlockCharredLog extends BlockRotatedPillar {

    private String unlocalizedName = "";

    public BlockCharredLog(String unlocalizedName) {
        super(Material.WOOD);
        this.unlocalizedName = unlocalizedName;
        setSoundType(SoundType.WOOD);
        setCreativeTab(OldPower.tabs);

        setHardness(1);
        setResistance(5);
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        ItemStack stack = new ItemStack(Items.COAL, 1);
        stack.setItemDamage(1);
        stack.getItem().setDamage(stack, 1);

        //TODO Drop a CharCoal ? !

        return stack.getItem();
    }

    @Override
    public int quantityDropped(IBlockState state, int fortune, Random random) {
        return fortune + random.nextInt(4 + fortune / 2);
    }

    public String getUnlocalizedName() {
        return "tile." + OldPower.MODID + "." + unlocalizedName;
    }
}
