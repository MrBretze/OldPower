package fr.bretzel.oldpower.world;

import com.google.common.collect.Lists;

import fr.bretzel.oldpower.Logger;
import fr.bretzel.oldpower.util.CommonRegistry;
import fr.bretzel.oldpower.util.LongHashMap;
import fr.bretzel.oldpower.util.Util;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.ArrayList;
import java.util.Random;

public class WorldGenVolcano implements IWorldGenerator {

    private static final int MAX_RADIUS = 200;
    private double CHANCE = 0.011;

    private LongHashMap<Integer> hashMap = new LongHashMap<Integer>();

    public static ArrayList<Object> canReplaceList = Lists.newArrayList();
    public static ArrayList<Block> logToCharredLog = Lists.newArrayList();
    public static ArrayList<Block> sandToTephra = Lists.newArrayList();
    public static ArrayList<Integer> disabledWorld = Lists.newArrayList();
    public static ArrayList<String> disabledStructure = Lists.newArrayList();

    static {
        /**
         * Material
         */
        canReplaceList.add(Material.WOOD);
        canReplaceList.add(Material.CACTUS);
        canReplaceList.add(Material.LEAVES);
        canReplaceList.add(Material.PLANTS);
        canReplaceList.add(Material.VINE);
        canReplaceList.add(Material.WATER);
        canReplaceList.add(Material.LAVA);
        canReplaceList.add(Material.GRASS);

        /**
         * Blocks
         */
        canReplaceList.add(Blocks.SAND);
        canReplaceList.add(Blocks.GRAVEL);
        canReplaceList.add(Blocks.WATER);
        canReplaceList.add(Blocks.FLOWING_WATER);
        canReplaceList.add(Blocks.LOG);
        canReplaceList.add(Blocks.LOG2);
        canReplaceList.add(Blocks.LEAVES);
        canReplaceList.add(Blocks.LEAVES2);
        canReplaceList.add(Blocks.MAGMA);
        canReplaceList.add(Blocks.BROWN_MUSHROOM_BLOCK);
        canReplaceList.add(Blocks.RED_MUSHROOM_BLOCK);
        canReplaceList.add(Blocks.TALLGRASS);
        canReplaceList.add(Blocks.DOUBLE_PLANT);
        canReplaceList.add(Blocks.CLAY);
        canReplaceList.add(CommonRegistry.blockBasalt);
        canReplaceList.add(CommonRegistry.blockCharredLog);
        canReplaceList.add(CommonRegistry.blockTephra);

        /**
         * Consume Log to charred Log
         */
        logToCharredLog.add(Blocks.LOG);
        logToCharredLog.add(Blocks.LOG2);

        /**
         * Replace sand And gravel to tephra
         */
        sandToTephra.add(Blocks.SAND);
        sandToTephra.add(Blocks.GRAVEL);
        sandToTephra.add(Blocks.GRASS_PATH);

        /**
         * Structure Disabled
         */
        disabledStructure.add("Monument");
        disabledStructure.add("Mansion");
        disabledStructure.add("Stronghold");
        disabledStructure.add("Village");

        /**
         * World Disabled
         */
        disabledWorld.add(DimensionType.THE_END.getId());
        disabledWorld.add(DimensionType.NETHER.getId());
    }

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        if (world.isRemote || !world.provider.isSurfaceWorld() && !disabledWorld.contains(world.provider.getDimension()))
            return;

        int middleX = chunkX * 16 + random.nextInt(16);
        int middleZ = chunkZ * 16 + random.nextInt(16);

        if (random.nextDouble() <= CHANCE && world.getHeight(middleX, middleZ) <= 90) {

            boolean isMegaVolcano = random.nextFloat() <= 0.15;

            ArrayList<BlockPos> topBlockClear = Lists.newArrayList();

            int baseY = isMegaVolcano ? world.getHeight(middleX, middleZ) + 50 + random.nextInt(70) : world.getHeight(middleX, middleZ) + 25 + random.nextInt(40);

            if (isInSideStructure(world, new BlockPos(middleX, baseY, middleZ)))
                return;

            int maxYPosed = 0;

            if (world.getBlockState(new BlockPos(middleX, 10, middleZ)).getMaterial() == Material.LAVA) {

                ArrayList<Pos>[] poss = getPositions();

                if (poss == null) {
                    return;
                }

                Logger.info("Genererate new Volcano at: X: %d, Y:%d , Z: %d", middleX, baseY, middleZ);

                boolean first = true;

                ArrayList<BlockPos> megaVolcanoClear = Lists.newArrayList();

                for (int dist = 0; dist < poss.length; dist++) {

                    ArrayList<Pos> posArrayList = poss[dist];
                    boolean isFinished = true;


                    for (Pos p : posArrayList) {

                        int worldHeight = world.getHeight(p.x + middleX, p.z + middleZ) - 1;
                        int posHeight = first ? baseY : getNewVolcanoHeight(worldHeight, p, random, dist);
                        BlockPos posReplace = new BlockPos(p.x + middleX, posHeight, p.z + middleZ);

                        if (worldHeight >= 0 && (posHeight > worldHeight || canReplace(world, posReplace))) {
                            hashMap.add(Util.chunkXZ2Int(p.x, p.z), posHeight);
                            if (!first) {
                                for (int i = posHeight; i > 0 && canReplace(world, new BlockPos(p.x + middleX, i, p.z + middleZ)); i--) {
                                    BlockPos to = new BlockPos(p.x + middleX, i, p.z + middleZ);
                                    setBlock(to, random.nextFloat() <= 0.10 ? Blocks.MAGMA : CommonRegistry.blockBasalt, world);
                                    if (i == posHeight) {
                                        topBlockClear.add(to);
                                        maxYPosed = maxYPosed > i ? maxYPosed : i;
                                    }
                                }
                            }
                            isFinished = false;
                        }
                        first = false;
                    }
                    if (isFinished) {
                        hashMap = new LongHashMap<>();
                        break;
                    }
                }

                if (isMegaVolcano) {
                    BlockPos middle = new BlockPos(middleX, maxYPosed + 50, middleZ);
                    megaVolcanoClear.add(middle);
                    int maxDistance = random.nextInt(4) + 10;
                    for (int x = -10; x <= 10; x++) {
                        for (int z = -10; z <= 10; z++) {
                            if (Util.distance(middle, middle.add(x, 0, z)) < maxDistance) {
                                megaVolcanoClear.add(middle.add(x, 0, z));
                            }
                        }
                    }

                    for (BlockPos pos : megaVolcanoClear) {
                        int y = 10 - random.nextInt(3);
                        while (pos.getY() > y) {
                            if (pos.getY() > (maxYPosed / 1.2 + 10)) {
                                world.setBlockToAir(pos);
                            } else {
                                world.setBlockState(pos, Blocks.LAVA.getDefaultState());
                                lavaReplacementForBlocks(pos, world);
                            }
                            pos = pos.down();
                        }
                    }
                } else {
                    BlockPos middle = new BlockPos(middleX, maxYPosed + 50, middleZ);
                    while (middle.getY() >= 10) {
                        if (hasAdjacentBlock(middle, world)) {
                            if (world.getBlockState(middle.up()).getMaterial() == Material.AIR && random.nextFloat() <= 0.30) {
                                world.setBlockState(middle.up(), Blocks.LAVA.getDefaultState());
                            }
                            world.setBlockState(middle, Blocks.LAVA.getDefaultState());
                            lavaReplacementForBlocks(middle, world);
                        }
                        middle = middle.down();
                    }
                }

                for (BlockPos position : topBlockClear) {
                    for (int x = -20; x <= 20; x++) {
                        for (int z = -20; z <= 20; z++) {
                            BlockPos p = new BlockPos(position.getX() + x, position.getY(), position.getZ() + z);

                            IBlockState state = world.getBlockState(p);
                            Block block = state.getBlock();

                            if (state.getMaterial() == Material.LEAVES || state.getMaterial() == Material.PLANTS || state.getMaterial() == Material.VINE)
                                world.setBlockToAir(p);

                            if (WorldGenVolcano.logToCharredLog.contains(block))
                                world.setBlockState(p, CommonRegistry.blockCharredLog.getStateFromMeta(block.getMetaFromState(state)));

                            if (WorldGenVolcano.sandToTephra.contains(block))
                                world.setBlockState(p, CommonRegistry.blockTephra.getDefaultState());
                        }
                    }
                }

            }
        }
    }

    public void lavaReplacementForBlocks(BlockPos pos, World world) {
        if (world.getBlockState(new BlockPos(pos).west()).getMaterial() == Material.AIR || world.getBlockState(new BlockPos(pos).west()).getMaterial() == Material.WATER)
            setBlock(pos.add(EnumFacing.WEST.getDirectionVec()), CommonRegistry.blockBasalt, world);
        if (world.getBlockState(new BlockPos(pos).east()).getMaterial() == Material.AIR || world.getBlockState(new BlockPos(pos).east()).getMaterial() == Material.WATER)
            setBlock(pos.add(EnumFacing.EAST.getDirectionVec()), CommonRegistry.blockBasalt, world);
        if (world.getBlockState(new BlockPos(pos).north()).getMaterial() == Material.AIR || world.getBlockState(new BlockPos(pos).north()).getMaterial() == Material.WATER)
            setBlock(pos.add(EnumFacing.NORTH.getDirectionVec()), CommonRegistry.blockBasalt, world);
        if (world.getBlockState(new BlockPos(pos).south()).getMaterial() == Material.AIR || world.getBlockState(new BlockPos(pos).south()).getMaterial() == Material.WATER)
            setBlock(pos.add(EnumFacing.SOUTH.getDirectionVec()), CommonRegistry.blockBasalt, world);
    }

    public boolean hasAdjacentBlock(BlockPos pos, World world) {
        if (world.getBlockState(new BlockPos(pos).west()).getMaterial() != Material.AIR)
            return true;
        if (world.getBlockState(new BlockPos(pos).east()).getMaterial() != Material.AIR)
            return true;
        if (world.getBlockState(new BlockPos(pos).north()).getMaterial() != Material.AIR)
            return true;
        if (world.getBlockState(new BlockPos(pos).south()).getMaterial() != Material.AIR)
            return true;
        return false;
    }

    public boolean isExposedToSurfaceBlock(BlockPos pos, World world) {
        if (world.getBlockState(new BlockPos(pos).up()).getMaterial() == Material.AIR || world.getBlockState(new BlockPos(pos).up()).getMaterial() == Material.WATER)
            return true;
        if (world.getBlockState(new BlockPos(pos).west()).getMaterial() == Material.AIR || world.getBlockState(new BlockPos(pos).west()).getMaterial() == Material.WATER)
            return true;
        if (world.getBlockState(new BlockPos(pos).east()).getMaterial() == Material.AIR || world.getBlockState(new BlockPos(pos).east()).getMaterial() == Material.WATER)
            return true;
        if (world.getBlockState(new BlockPos(pos).north()).getMaterial() == Material.AIR || world.getBlockState(new BlockPos(pos).north()).getMaterial() == Material.WATER)
            return true;
        if (world.getBlockState(new BlockPos(pos).south()).getMaterial() == Material.AIR || world.getBlockState(new BlockPos(pos).south()).getMaterial() == Material.WATER)
            return true;
        return false;
    }

    public boolean canReplace(World world, BlockPos pos) {
        if (world.isAirBlock(pos))
            return true;

        IBlockState state = world.getBlockState(pos);
        Material material = state.getMaterial();
        return canReplaceList.contains(material) || canReplaceList.contains(state.getBlock());
    }

    private void setBlock(BlockPos pos, Block block, World world) {
        world.setBlockState(pos, block.getDefaultState());
    }

    private void setBlock(BlockPos pos, IBlockState block, World world) {
        world.setBlockState(pos, block);
    }

    public boolean isInSideStructure(World world, BlockPos pos) {
        for (String s : disabledStructure) {
            BlockPos p = world.findNearestStructure(s, pos, false);
            if (p != null) {
                if (Util.distance(pos, p) <= 250) {
                    Logger.info("Canceled Volcano generation, the structure " + s + " is in range of volcano !");
                    return true;
                }
            }
        }
        return false;
    }

    public ArrayList<Pos>[] getPositions() {
        ArrayList[] distMap = new ArrayList[MAX_RADIUS];
        for (int x = -MAX_RADIUS; x <= MAX_RADIUS; x++) {
            for (int z = -MAX_RADIUS; z <= MAX_RADIUS; z++) {
                int dist = (int) Math.sqrt(x * x + z * z);

                if (dist < MAX_RADIUS) {
                    ArrayList distList = distMap[dist];
                    if (distList == null) {
                        distList = new ArrayList<>();
                        distMap[dist] = distList;
                    }
                    distList.add(new Pos(x, z));
                }
            }
        }
        return distMap;
    }

    private int getNewVolcanoHeight(int worldHeight, Pos requestedPos, Random rand, int distFromCenter) {

        int neighborCount = 0;
        int totalHeight = 0;
        for (int x = requestedPos.x - 1; x <= requestedPos.x + 1; x++) {
            for (int z = requestedPos.z - 1; z <= requestedPos.z + 1; z++) {
                Integer neighborHeight = hashMap.getValueByKey(Util.chunkXZ2Int(x, z));
                if (neighborHeight != null) {
                    neighborCount++;
                    totalHeight += neighborHeight;
                }
            }
        }
        if (neighborCount != 0) {
            double avgHeight = (double) totalHeight / neighborCount;
            if ((int) avgHeight < worldHeight + 2 && rand.nextInt(5) != 0)
                return (int) avgHeight - 2;
            // Formula that defines how fast the volcano descends. Using a square function to make it steeper at the top, and added randomness.
            int blocksDown;
            if (distFromCenter < 2) {
                blocksDown = 0;
            } else if (distFromCenter == 2) {
                blocksDown = rand.nextInt(2);
            } else {
                blocksDown = (int) (Math.pow(avgHeight - worldHeight + 1, 1.2) * 0.005D + (rand.nextDouble() - 0.5) * 3 + 0.4D);
            }
            if (blocksDown < 0)
                blocksDown = 0;

            return (int) avgHeight - blocksDown;
        } else {
            return -1;
        }
    }

    private class Pos {

        public final int x, z;

        public Pos(int x, int z) {

            this.x = x;
            this.z = z;
        }

        @Override
        public boolean equals(Object object) {
            if (object instanceof Pos) {
                Pos pos = (Pos) object;
                return pos.x == x && pos.z == z;
            } else {
                return false;
            }
        }

        @Override
        public int hashCode() {
            return (x << 13) + z;
        }
    }
}