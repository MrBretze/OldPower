package fr.bretzel.oldpower.tiles;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;

import fr.bretzel.oldpower.render.RenderDecorativeLamp;
import fr.bretzel.oldpower.render.RenderLamp;

public class TileDecoLamp extends TileEntity {

    @Override
    public boolean shouldRenderInPass(int pass) {
        RenderDecorativeLamp.pass = pass;
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
