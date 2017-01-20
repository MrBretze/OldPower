package fr.bretzel.oldpower.world;

import fr.bretzel.oldpower.util.BlockCoord;
import fr.bretzel.oldpower.util.CommonRegistry;
import fr.bretzel.oldpower.util.Util;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class VolcanoHelper {

    //Tries to make sure that it doesn't generate in really odd places
    public static boolean canGenHere(World world, BlockPos b) {
        // Roughly check if there's sufficient open space above ground
        if (getComposition(world, b, 32) > 0.25)
            return false;
            //Roughly check if there's sufficient solid space below ground
        else if (getComposition(world, b, -32) < 0.65)
            return false;
        return true;
    }

    //Returns percentage of blocks which are solid
    public static float getComposition(World world, BlockPos b, int yDistance) {
        BlockPos c = new BlockPos(b.getX(), b.getY(), b.getZ());
        int solidCount = 0;
        int nonSolidCount = 0;
        while (c.getY() < (b.getY() + yDistance)) {
            if (world.getBlockState(c).isOpaqueCube())
                solidCount++;
            else
                nonSolidCount++;
            for (BlockPos n : Util.getAdjacent(c)) {
                if (world.getBlockState(n).isOpaqueCube())
                    solidCount++;
                else
                    nonSolidCount++;
            }
            if (yDistance > 0)
                c.up();
            else
                c.down();
        }
        return solidCount / ((float) nonSolidCount + (float) solidCount);
    }

    public static int getHighestSolidOrFillBlock(World world, int x, int z) {
        int y = 255;
        BlockPos pos = new BlockPos(x, y, z);
        Block b = world.getBlockState(pos).getBlock();
        while (y > 0 && !world.getBlockState(pos).isOpaqueCube() && b != Blocks.LAVA && b != Blocks.FLOWING_LAVA && !b.isWood(world, pos)) {
            y--;
            b = world.getBlockState(pos).getBlock();
        }
        return y;
    }

    public static boolean isVolcanicBlock(Block b) {
        return b == CommonRegistry.blockBasalt || b == CommonRegistry.blockTephra || b == CommonRegistry.blockCharredLog;
    }

    public static boolean isVolcanicFill(Block b) {
        return b == Blocks.OBSIDIAN || b == Blocks.LAVA || b == Blocks.FLOWING_LAVA;
    }

    public static void setVolcanoBlock(World world, BlockPos coord) {
        Random rand = world.rand;
        for (BlockPos t : Util.getAdjacent(coord)) {
            if (world.isAirBlock(t) && world.getBlockState(t).getBlock().isWood(world, t)) {
                charTree(world, t);
            }
        }
        if (world.getBlockState(coord).getBlock() != Blocks.BEDROCK)
            world.setBlockState(coord, CommonRegistry.blockBasalt.getDefaultState());
    }

    public static void setVolcanoTephra(World world, BlockPos coord) {
        Random rand = world.rand;
        for (BlockPos t : Util.getAdjacent(coord)) {
            if (!world.isAirBlock(t) && world.getBlockState(t).getBlock().isWood(world, t))
                charTree(world, t);
        }
        if (world.getBlockState(coord).getBlock() != Blocks.BEDROCK)
            world.setBlockState(coord, CommonRegistry.blockTephra.getDefaultState());
    }

    public static void setVolcanoFill(World world, BlockPos coord, boolean active) {
        if (world.getBlockState(coord).getBlock() != Blocks.BEDROCK)
            world.setBlockState(coord, active ? Blocks.LAVA.getDefaultState() : Blocks.OBSIDIAN.getDefaultState());
    }

    public static boolean isReplaceable(World world, BlockPos coord) {
        return (coord.getY() > 0 && coord.getY() < 256) && (world.isAirBlock(coord) || (world.getBlockState(coord).isOpaqueCube() && !isVolcanicFill(world.getBlockState(coord).getBlock())));
    }

    public static List<BlockPos> getOpenSides(World world, BlockPos coord) {
        ArrayList<BlockPos> open = new ArrayList<>();
        for (BlockPos t : Util.getAdjacent(coord)) {
            if (t.getY() > coord.getY())
                continue;
            else if (isReplaceable(world, t))
                open.add(t);
            else if (isVolcanicFill(world.getBlockState(coord).getBlock())) {
                for (BlockPos n : Util.getAdjacent(coord)) {
                    if (n != t && n.getY() <= t.getY() && !open.contains(n) && isReplaceable(world, n))
                        open.add(n);
                }
            }
        }
        return open;
    }

    public static BlockCoord snapToHighest(World world, BlockCoord b) {
        b.setY(getHighestSolidOrFillBlock(world, b.getY(), b.getZ()));
        return b;
    }

    public static BlockPos get2dINVERSEDistributedCoord(World world, BlockPos origin, int maxDistance) {
        Random rand = world.rand;
        while (true) {
            double distance = rand.nextDouble() * maxDistance;
            if (distance > 0 && (rand.nextDouble() * maxDistance) <= (maxDistance / 8) + (maxDistance / distance)) {
                BlockCoord b = new BlockCoord(origin);
                double xDistance = rand.nextDouble() * distance * (rand.nextBoolean() ? -1 : 1);
                double zDistance = Math.sqrt(Math.pow(distance, 2) - Math.pow(xDistance, 2)) * (rand.nextBoolean() ? -1 : 1);
                b.setX((int) (xDistance > 0 ? Math.floor(xDistance) : Math.ceil(xDistance)));
                b.setZ((int) (zDistance > 0 ? Math.floor(zDistance) : Math.ceil(zDistance)));
                return b.toBlockPos();
            }
        }
    }

    public static BlockPos get2dSINDistributedCoord(World world, BlockPos origin, int maxDistance) {
        Random rand = world.rand;
        while (true) {
            double distance = rand.nextDouble() * maxDistance;
            double point = (2.0D + Math.sin((distance + (rand.nextDouble() * 3.0D)) / (maxDistance / 4)));
            if (Double.isNaN(point))
                point = 1.0D;
            if (distance > 0 && (rand.nextDouble() * 3.0D) <= point) {
                BlockCoord b = new BlockCoord(origin);
                double xDistance = rand.nextDouble() * distance * (rand.nextBoolean() ? -1 : 1);
                double zDistance = Math.sqrt(Math.pow(distance, 2) - Math.pow(xDistance, 2)) * (rand.nextBoolean() ? -1 : 1);
                b.setX((int) (xDistance > 0 ? Math.floor(xDistance) : Math.ceil(xDistance)));
                b.setZ((int) (zDistance > 0 ? Math.floor(zDistance) : Math.ceil(zDistance)));
                return b.toBlockPos();
            }
        }
    }

    public static void charTree(World world, BlockPos start) {
        world.setBlockState(start, CommonRegistry.blockCharredLog.getDefaultState());
        BlockCoord coord = new BlockCoord(start);
        for (BlockCoord b : coord.getRadiusBlocks(2)) {
            BlockPos pos = b.toBlockPos();
            if (world.getBlockState(pos).getBlock().isWood(world, pos)) {
                charTree(world, pos);
            } else if (world.getBlockState(pos).getBlock().isLeaves(world.getBlockState(pos), world, pos)) {
                world.setBlockState(pos, Blocks.AIR.getDefaultState());
            }
        }
    }
}
