package fr.bretzel.oldpower.block;

import fr.bretzel.oldpower.api.LampType;
import fr.bretzel.oldpower.api.block.ILamp;
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
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

import fr.bretzel.oldpower.OldPower;
import fr.bretzel.oldpower.item.ItemBlockBase;
import fr.bretzel.oldpower.item.ItemBlockLamp;
import fr.bretzel.oldpower.tiles.TileLamp;
import fr.bretzel.oldpower.util.CommonRegistry;

public class BlockLamp extends BlockBase implements ITileEntityProvider, ILamp {

    public static final PropertyEnum<EnumDyeColor> COLOR = PropertyEnum.create("lampcolor", EnumDyeColor.class);

    private LampType type;

    public BlockLamp(String unlocalizedName, LampType type) {
        super(unlocalizedName);
        if (type == LampType.LAMP || type == LampType.LAMP_DECORATIVE)
            setCreativeTab(OldPower.tabs);
        this.type = type;
        this.setDefaultState(this.blockState.getBaseState().withProperty(COLOR, EnumDyeColor.WHITE));

        if (type == LampType.LAMP_LIT || type == LampType.LAMP_DECORATIVE)
            setLightLevel(1.0F);
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
    public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> list) {
        if (getLampType() == LampType.LAMP || getLampType() == LampType.LAMP_DECORATIVE)
            for (EnumDyeColor color : EnumDyeColor.values())
                list.add(new ItemStack(this, 1, color.getMetadata()));
    }

    /* @Override
    public void getSubBlocks(Item itemIn, CreativeTabs tab, NonNullList<ItemStack> list) {
        if (getLampType() == LampType.LAMP || getLampType() == LampType.LAMP_DECORATIVE)
            for (EnumDyeColor color : EnumDyeColor.values())
                list.add(new ItemStack(itemIn, 1, color.getMetadata()));
    }*/

    @Override
    public MapColor getMapColor(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        return MapColor.getBlockColor(state.getValue(COLOR));
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
        return new ItemStack(Item.getItemFromBlock(this), 1, state.getValue(COLOR).getMetadata());
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
    public void onBlockAdded(World world, BlockPos pos, IBlockState blockState) {
        if(!world.isRemote) {
            if(getLampType() == LampType.LAMP_LIT && !world.isBlockPowered(pos)) {
                world.setBlockState(pos, CommonRegistry.blockLamp.getDefaultState().withProperty(COLOR, EnumDyeColor.byMetadata(getMetaFromState(blockState))), 2);
            } else if(getLampType() == LampType.LAMP && world.isBlockPowered(pos)) {
                world.setBlockState(pos, CommonRegistry.blockLitLamp.getDefaultState().withProperty(COLOR, EnumDyeColor.byMetadata(getMetaFromState(blockState))), 2);
            }
        }
    }

    @Override
    public void neighborChanged(IBlockState state, World world, BlockPos pos, Block blockIn, BlockPos fromPos) {
        if (!world.isRemote) {
            if (getLampType() == LampType.LAMP_LIT && !world.isBlockPowered(pos)) {
                world.scheduleUpdate(pos, this, 4);
            } else if (getLampType() == LampType.LAMP && world.isBlockPowered(pos)) {
                world.setBlockState(pos, CommonRegistry.blockLitLamp.getDefaultState().withProperty(COLOR, EnumDyeColor.byMetadata(getMetaFromState(state))), 2);
            }
        }
    }

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState blockState, Random random) {
        if(!world.isRemote) {
            if(getLampType() == LampType.LAMP_LIT && !world.isBlockPowered(pos)) {
                world.setBlockState(pos, CommonRegistry.blockLamp.getDefaultState().withProperty(COLOR, EnumDyeColor.byMetadata(getMetaFromState(blockState))), 2);
            }
        }
    }

    @Override
    public LampType getLampType() {
        return type;
    }
}
