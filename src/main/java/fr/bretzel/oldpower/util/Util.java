package fr.bretzel.oldpower.util;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import org.lwjgl.opengl.GL11;

public class Util {

    public static BlockPos getRelative(BlockPos pos, EnumFacing facing) {
        return pos.add(facing.getDirectionVec());
    }

    public static void addVertex(double x, double y, double z) {
        GlStateManager.glVertex3f((float) x, (float) y, (float) z);
    }

    public static void drawColoredCube(Vec3DCube cube, double r, double g, double b, double a, boolean... renderFaces) {

        GL11.glColor4d(r, g, b, a);

        // Top side
        if (renderFaces[0]) {
            GL11.glNormal3d(0, 1, 0);
            addVertex(cube.getMin().getX(), cube.getMax().getY(), cube.getMax().getZ());
            addVertex(cube.getMax().getX(), cube.getMax().getY(), cube.getMax().getZ());
            addVertex(cube.getMax().getX(), cube.getMax().getY(), cube.getMin().getZ());
            addVertex(cube.getMin().getX(), cube.getMax().getY(), cube.getMin().getZ());
        }

        // Bottom side
        if (renderFaces[1]) {
            GL11.glNormal3d(0, -1, 0);
            addVertex(cube.getMax().getX(), cube.getMin().getY(), cube.getMax().getZ());
            addVertex(cube.getMin().getX(), cube.getMin().getY(), cube.getMax().getZ());
            addVertex(cube.getMin().getX(), cube.getMin().getY(), cube.getMin().getZ());
            addVertex(cube.getMax().getX(), cube.getMin().getY(), cube.getMin().getZ());
        }

        // Draw west side:
        if (renderFaces[2]) {
            GL11.glNormal3d(-1, 0, 0);
            addVertex(cube.getMin().getX(), cube.getMin().getY(), cube.getMax().getZ());
            addVertex(cube.getMin().getX(), cube.getMax().getY(), cube.getMax().getZ());
            addVertex(cube.getMin().getX(), cube.getMax().getY(), cube.getMin().getZ());
            addVertex(cube.getMin().getX(), cube.getMin().getY(), cube.getMin().getZ());
        }

        // Draw east side:
        if (renderFaces[3]) {
            GL11.glNormal3d(1, 0, 0);
            addVertex(cube.getMax().getX(), cube.getMin().getY(), cube.getMin().getZ());
            addVertex(cube.getMax().getX(), cube.getMax().getY(), cube.getMin().getZ());
            addVertex(cube.getMax().getX(), cube.getMax().getY(), cube.getMax().getZ());
            addVertex(cube.getMax().getX(), cube.getMin().getY(), cube.getMax().getZ());
        }

        // Draw north side
        if (renderFaces[4]) {
            GL11.glNormal3d(0, 0, -1);
            addVertex(cube.getMin().getX(), cube.getMin().getY(), cube.getMin().getZ());
            addVertex(cube.getMin().getX(), cube.getMax().getY(), cube.getMin().getZ());
            addVertex(cube.getMax().getX(), cube.getMax().getY(), cube.getMin().getZ());
            addVertex(cube.getMax().getX(), cube.getMin().getY(), cube.getMin().getZ());
        }

        // Draw south side
        if (renderFaces[5]) {
            GL11.glNormal3d(0, 0, 1);
            addVertex(cube.getMin().getX(), cube.getMin().getY(), cube.getMax().getZ());
            addVertex(cube.getMax().getX(), cube.getMin().getY(), cube.getMax().getZ());
            addVertex(cube.getMax().getX(), cube.getMax().getY(), cube.getMax().getZ());
            addVertex(cube.getMin().getX(), cube.getMax().getY(), cube.getMax().getZ());
        }

        GL11.glColor4d(1, 1, 1, 1);
    }

    public static long mean(long[] values) {
        long sum = 0l;
        for (long v : values) {
            sum += v;
        }
        return sum / values.length;
    }

    public static BlockPos[] getAdjacent(BlockPos pos) {
        int i = 0;
        BlockPos[] adjacent = new BlockPos[6];
        for (EnumFacing facing : EnumFacing.VALUES) {
            adjacent[i] = getRelative(pos, facing);
            i++;
        }
        return adjacent;
    }
}
