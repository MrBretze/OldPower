package fr.bretzel.oldpower.tiles;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;

import fr.bretzel.oldpower.client.render.RenderLamp;

public class TileLamp extends TileEntity {

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
