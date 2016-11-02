package fr.bretzel.oldpower.client.render;

import fr.bretzel.oldpower.api.LampType;
import fr.bretzel.oldpower.api.block.ILamp;
import fr.bretzel.oldpower.block.BlockDecorativeLamp;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.lwjgl.opengl.GL11;

import fr.bretzel.oldpower.block.BlockLamp;
import fr.bretzel.oldpower.tiles.TileLamp;
import fr.bretzel.oldpower.util.RGBColor;
import fr.bretzel.oldpower.util.Util;
import fr.bretzel.oldpower.util.Vec3DCube;
import fr.bretzel.oldpower.util.Vec3d;

@SideOnly(Side.CLIENT)
public class RenderLamp extends TileEntitySpecialRenderer<TileLamp> {

    public static int pass;

    public RenderLamp() {
    }

    @Override
    public void renderTileEntityAt(TileLamp te, double x, double y, double z, float partialTicks, int destroyStage) {

        if (!(te.getBlockType() instanceof BlockLamp)) {
            return;
        }

        BlockLamp lamp = (BlockLamp) te.getBlockType();

        if (pass != 0 && getWorld().getBlockState(te.getPos()).getProperties().size() > 0 && isPowered(lamp)) {
            GlStateManager.pushMatrix();
            GlStateManager.translate(x, y, z);
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
            GlStateManager.disableTexture2D();
            GlStateManager.disableLighting();
            //GlStateManager.disableCull();
            GlStateManager.glBegin(GL11.GL_QUADS);

            double powerDivision = 15 / 18D;

            Vec3DCube cube = new Vec3DCube(-0.5, -0.5, -0.5, 0.5, 0.5, 0.5).expand(0.8 / 16D);

            //0 = up | 1 = down | 2 = west | 3 = east | 4 = north | 5 = south
            boolean[] drawfaces = new boolean[]{true, true, true, true, true, true};

            Vec3d vector = new Vec3d(te);

            for (EnumFacing enumFacing : EnumFacing.VALUES) {
                Vec3d vec3d = vector.getRelative(enumFacing);
                Block block = vec3d.getBlock(getWorld());
                if (block instanceof BlockLamp || block instanceof BlockDecorativeLamp) {
                    ILamp l = (ILamp) block;

                    if (enumFacing == EnumFacing.UP && isPowered(l)) {
                        drawfaces[0] = false;
                        cube.getMax().setY(0.5);
                    } else if (enumFacing == EnumFacing.DOWN && isPowered(l)) {
                        drawfaces[1] = false;
                        cube.getMin().setY(-0.5);
                    } else if (enumFacing == EnumFacing.WEST && isPowered(l)) {
                        drawfaces[2] = false;
                        cube.getMin().setX(-0.5);
                    } else if (enumFacing == EnumFacing.EAST && isPowered(l)) {
                        drawfaces[3] = false;
                        cube.getMax().setX(0.5);
                    } else if (enumFacing == EnumFacing.NORTH && isPowered(l)) {
                        drawfaces[4] = false;
                        cube.getMin().setZ(-0.5);
                    } else if (enumFacing == EnumFacing.SOUTH && isPowered(l)) {
                        drawfaces[5] = false;
                        cube.getMax().setZ(0.5);
                    }
                }
            }

            cube.getMin().add(0.5, 0.5, 0.5);
            cube.getMax().add(0.5, 0.5, 0.5);

            RGBColor rgbColor = RGBColor.getRGB(getWorld().getBlockState(te.getPos()).getValue(BlockLamp.COLOR));

            int color = rgbColor.getHex(getWorld().getBlockState(te.getPos()).getValue(BlockLamp.COLOR));

            int redMask = 0xFF0000, greenMask = 0xFF00, blueMask = 0xFF;
            int r = (color & redMask) >> 16;
            int g = (color & greenMask) >> 8;
            int b = (color & blueMask);

            Util.drawColoredCube(cube, r / 256D, g / 256D, b / 256D, powerDivision * 0.725D, drawfaces);

            GlStateManager.glEnd();
            GlStateManager.enableCull();
            GlStateManager.enableLighting();
            GlStateManager.enableTexture2D();
            GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            //GlStateManager.alphaFunc(GL11.GL_GREATER, 0.1F);
            GlStateManager.disableBlend();
            GlStateManager.translate(-x, -y, -z);
            GlStateManager.popMatrix();
        }
    }

    public static boolean isPowered(ILamp blockLamp) {
        return blockLamp.getLampType() == LampType.LAMP_LIT || blockLamp.getLampType() == LampType.LAMP_DECORATIVE;
    }
}
