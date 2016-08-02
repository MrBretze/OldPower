package fr.bretzel.oldpower.tiles;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.EnumSkyBlock;

import fr.bretzel.oldpower.block.BlockLamp;
import fr.bretzel.oldpower.proxy.CommonProxy;
import fr.bretzel.oldpower.render.RenderLamp;

public class TileLamp extends TileEntity {

    public TileLamp() {
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        return super.writeToNBT(compound);
    }

    @Override
    public NBTTagCompound serializeNBT() {
        return super.serializeNBT();
    }

    @Override
    public boolean shouldRenderInPass(int pass) {
        RenderLamp.pass = pass;
        return true;
    }


    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        return INFINITE_EXTENT_AABB;
    }

    @Override
    public boolean canRenderBreaking() {
        return true;
    }

    @Override
    public double getMaxRenderDistanceSquared() {
        return 65536.0D;
    }
}
