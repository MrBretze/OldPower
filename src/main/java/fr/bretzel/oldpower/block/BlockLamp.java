package fr.bretzel.oldpower.block;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;

import fr.bretzel.oldpower.OldPower;
import fr.bretzel.oldpower.item.ItemBlockBase;
import fr.bretzel.oldpower.item.ItemBlockLamp;
import fr.bretzel.oldpower.tiles.TileLamp;

public class BlockLamp extends BlockBase implements ITileEntityProvider {

    public static final PropertyEnum<EnumDyeColor> COLOR = PropertyEnum.create("lampcolor", EnumDyeColor.class);

    public boolean isPowered = false;

    public BlockLamp(String unlocalizedName, boolean lit) {
        super(unlocalizedName);
        if (!lit)
            setCreativeTab(OldPower.tabs);
        this.isPowered = lit;
        this.setDefaultState(this.blockState.getBaseState().withProperty(COLOR, EnumDyeColor.WHITE));
    }

    @Override
    public int hasSubType() {
        return EnumDyeColor.values().length;
    }

    @Override
    public String getSubTypeName(int metadata) {
        return getOnlyUnlocalizedName() + "." + EnumDyeColor.byMetadata(metadata);
    }

    @Override
    public Class<? extends ItemBlockBase> getItemBlock() {
        return ItemBlockLamp.class;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, COLOR);
    }

    @Override
    public MapColor getMapColor(IBlockState state) {
        return state.getValue(COLOR).getMapColor();
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(COLOR, EnumDyeColor.byMetadata(meta));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(COLOR).getMetadata();
    }

    @Override
    public int damageDropped(IBlockState state) {
        return state.getValue(COLOR).getMetadata();
    }

    @Override
    public void getSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> list) {
        for (EnumDyeColor color : EnumDyeColor.values()) {
            list.add(new ItemStack(itemIn, 1, color.getMetadata()));
        }
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
        return new ItemStack(Item.getItemFromBlock(this), 1, state.getValue(COLOR).getMetadata());
    }

    @Override
    public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
        return isPowered ? 15 : 0;
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileLamp();
    }

    @Override
    public Class<? extends TileEntity> getTileEntity() {
        return TileLamp.class;
    }

    @Override
    public String getTileEntityName() {
        return "tile.oldpower." + getOnlyUnlocalizedName();
    }

    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn) {
        if (isPowered && !worldIn.isBlockPowered(pos)) {

        }
    }
}
