package fr.bretzel.oldpower.world;

import fr.bretzel.oldpower.Logger;
import fr.bretzel.oldpower.util.CommonRegistry;
import fr.bretzel.oldpower.util.Util;

import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class WorldGenVolcano implements IWorldGenerator {

    private static final int MAX_RADIUS = 200;
    private double CHANCE = 0.025;

    private HashMap<Integer, Integer> hashMap = new HashMap<>();

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        if (random.nextDouble() <= CHANCE) {
            int middleX = chunkX * 16 + random.nextInt(16);
            int middleZ = chunkZ * 16 + random.nextInt(16);
            int baseY = world.getHeight(middleX, middleZ) + 40 + random.nextInt(50);

            if (world.getBlockState(new BlockPos(middleX, 10, middleZ)).getMaterial() == Material.LAVA) {

                ArrayList<Pos>[] poss = getPositions();

                Logger.info("Genererate new Volcano at: X: %d, Y:%d , Z: %d", middleX, baseY, middleZ);

                boolean first = true;

                for (int dist = 0; dist < poss.length; dist++) {
                    ArrayList<Pos> posArrayList = poss[dist];
                    boolean isFinished = true;
                    for (Pos p : posArrayList) {
                        int worldHeight = world.getHeight(p.x + middleX, p.z + middleZ) - 1;
                        int posHeight = first ? baseY : getNewVolcHeight(worldHeight, p, random, dist);

                        if (worldHeight >= 0 && (posHeight > worldHeight)) {

                            hashMap.put(Util.chunkXZ2Int(p.x, p.z), posHeight);

                            if (!first) {
                                for (int i = posHeight; i > 0 && (i > worldHeight || canReplace(world, new BlockPos(p.x + middleX, posHeight, p.z + middleZ))); i--) {
                                    BlockPos blockPos = new BlockPos(p.x + middleX, i, p.z + middleZ);
                                    setBasalt(blockPos, world);
                                }

                                for (int i = posHeight + 1; i < baseY; i++) {
                                    if (canReplace(world, new BlockPos(p.x + middleX, i, p.z + middleZ))
                                            && world.getBlockState(new BlockPos(p.x + middleX, i, p.z + middleZ)).getMaterial() != Material.WATER)
                                        world.setBlockState(new BlockPos(p.x + middleX, i, p.z + middleZ), Blocks.AIR.getDefaultState(), 2);
                                }
                            }
                            isFinished = false;
                        }
                        first = false;
                    }
                    if (isFinished)
                        break;
                }
            }
        }
    }

    public boolean canReplace(World world, BlockPos pos) {
        if (world.isAirBlock(pos))
            return true;

        Material material = world.getBlockState(pos).getMaterial();
        return (material == Material.WOOD || material == Material.AIR || material == Material.CACTUS || material == Material.LEAVES ||
                material == Material.PLANTS || material == Material.WATER || material == Material.VINE);
    }

    public void setBasalt(BlockPos pos, World world) {
        world.setBlockState(pos, CommonRegistry.blockBasalt.getDefaultState(), 2);
    }

    public ArrayList<Pos>[] getPositions() {
        ArrayList<Pos>[] distMap = new ArrayList[MAX_RADIUS];
        for (int x = -MAX_RADIUS; x <= MAX_RADIUS; x++) {
            for (int z = -MAX_RADIUS; z <= MAX_RADIUS; z++) {
                int dist = (int) Math.sqrt(x * x + z * z);
                if (dist < MAX_RADIUS) {
                    ArrayList<Pos> distList = distMap[dist];
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

    public int getNewVolcHeight(int baseHeight, Pos pos, Random random, int distFromCenter) {
        int neighborCount = 0;
        int totalHeight = 0;

        for (int x = pos.x - 1; x <= pos.x + 1; x++) {
            for (int z = pos.z - 1; z <= pos.z + 1; z++) {
                Integer neighborHeight = hashMap.get(Util.chunkXZ2Int(x, z));
                if (neighborHeight != null) {
                    neighborCount++;
                    totalHeight += neighborHeight;
                }
            }
        }

        if (neighborCount != 0 ) {
            double avgHeight = totalHeight / neighborCount;

            if (avgHeight < baseHeight + 2 && random.nextInt(5) != 0) {
                return (int) (avgHeight - 2);
            }

            int blocksDown;

            if (distFromCenter < 2) {
                blocksDown = 0;
            } else if (distFromCenter == 2) {
                blocksDown = random.nextInt(2);
            } else {
                blocksDown = (int) (Math.pow(avgHeight - baseHeight + 1, 1.5) * 0.005D + (random.nextDouble() - 0.5) * 3 + 0.4D);
            }

            if (blocksDown < 0) {
                blocksDown = 0;
            }

            return (int) (avgHeight - blocksDown);
        }

        return -1;
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

    /*private double chance = 0.0025;
    // magnitude bigger than an average volcano radius.
    private final LongHashMap volcanoMap = new LongHashMap();

    private int MAX_RADIUS = 400;

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        if (random.nextDouble() < chance) {
            int x = chunkX * 16 + random.nextInt(16);
            int z = chunkZ * 16 + random.nextInt(16);
            int y = world.getHeight(x, z) + 40 + random.nextInt(50);

            Logger.info("Genererate new Volcano at: X:" + x + ", Y" + y + ", Z:" + z);

            List<Pos>[] distMap = calculateDistMap();

            boolean first = true;

            for (int dist = 0; dist < distMap.length; dist++) {
                // Loop through every XZ position of the volcano, in order of how close the positions are
                // from the center. The volcano will be generated from the center to the edge.
                List<Pos> distList = distMap[dist];
                boolean isFinished = true;// Will stay true as long as there were still blocks being generated at this distance from the volcano.
                for (Pos p : distList) {
                    int worldHeight = world.getHeight(p.x + x, p.z + z) - 1;
                    int posHeight = first ? y : getNewVolcanoHeight(worldHeight, p, random, dist);
                    if (posHeight >= 0 && (posHeight > worldHeight || canReplace(world, new BlockPos(p.x + x, posHeight, p.z + z)))) { // If the calculated
                        // desired volcano
                        // height is higher
                        // than the world
                        // height, generate.
                        volcanoMap.add(Util.chunkXZ2Int(p.x, p.z), posHeight);
                        if (!first) {
                            for (int i = posHeight; i > 0 && (i > worldHeight || canReplace(world, new BlockPos(p.x + x, i, p.z + z))); i--) {
                                setBlock(new BlockPos(p.x + x, i, p.z + z), random.nextBoolean() ? random.nextBoolean() ?
                                        CommonRegistry.blockTephra : Blocks.MAGMA : CommonRegistry.blockBasalt, world);
                            }
                            for (int i = posHeight + 1; i < y; i++) {
                                if (canReplace(world, new BlockPos(p.x + x, i, p.z + z))
                                        && world.getBlockState(new BlockPos(p.x + x, i, p.z + z)).getMaterial() != Material.WATER)
                                    setBlock(new BlockPos(p.x + x, i, p.z + z), Blocks.AIR, world);
                            }
                        }
                        isFinished = false;
                    }
                    first = false;
                }
                if (isFinished)
                    break;
            }

            generateLava(world, new BlockPos(x, y, z), random);
        }
    }

    private boolean canReplace(World world, BlockPos pos) {

        if (world.isAirBlock(pos))
            return true;
        Block block = world.getBlockState(pos).getBlock();
        Material material = block.getMaterial(world.getBlockState(pos));
        return material == Material.WOOD || material == Material.CACTUS || material == Material.LEAVES || material == Material.PLANTS
                || material == Material.VINE || block == Blocks.WATER || block == Blocks.FLOWING_WATER;
    }

    private void generateLava(World world, BlockPos pos, Random rand) {
        if (rand.nextDouble() < 0.5) {
            world.setBlockState(pos, Blocks.LAVA.getDefaultState(), 1);
        } else {
            world.setBlockState(copy(pos).up(), Blocks.LAVA.getDefaultState());
        }
        for (int y = 0; y <= 10; y++) {
            pos.down();
            if (world.getBlockState(pos) != Blocks.BEDROCK) {
                setBlock(copy(pos).add(1, 0, 0), rand.nextBoolean() ? Blocks.MAGMA : CommonRegistry.blockBasalt, world);
                setBlock(copy(pos).subtract(new Vec3i(1, 0, 0)), rand.nextBoolean() ? Blocks.MAGMA : CommonRegistry.blockBasalt, world);
                setBlock(copy(pos).add(0, 0, 1), rand.nextBoolean() ? Blocks.MAGMA : CommonRegistry.blockBasalt, world);
                setBlock(copy(pos).subtract(new Vec3i(0, 0, 1)), rand.nextBoolean() ? Blocks.MAGMA : CommonRegistry.blockBasalt, world);
                setBlockAndNotifyAdequately(world, pos, Blocks.LAVA.getDefaultState());
            }
        }
    }

    private void setBlock(BlockPos pos, Block block, World world) {
        world.setBlockState(pos, block.getDefaultState());
    }

    private BlockPos copy(BlockPos pos) {
        return new BlockPos(pos.getX(), pos.getY(), pos.getZ());
    }


    /**
     * Saves an array of relative Positions with distance to origin. The index is the distance, the element the positions with that distance to the
     * origin.
     *
    @SuppressWarnings("unchecked")
    private List<Pos>[] calculateDistMap() {

        List<Pos>[] distMap = new List[MAX_RADIUS];
        for (int x = -MAX_RADIUS; x <= MAX_RADIUS; x++) {
            for (int z = -MAX_RADIUS; z <= MAX_RADIUS; z++) {
                int dist = (int) Math.sqrt(x * x + z * z);
                if (dist < MAX_RADIUS) {
                    List<Pos> distList = distMap[dist];
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

    /**
     * Calculates a height for the requested position. It looks at the adjacent (already generated) volcano heights, takes the average, and blends in
     * a bit of randomness. If there are no neighbors this is the first volcano block generated, meaning it's the center, meaning it should get the
     * max height.
     *
     * @param worldHeight    Terrain height
     * @param requestedPos   New volcano position
     * @param rand
     * @param distFromCenter
     * @return
     */
    /*
    private int getNewVolcanoHeight(int worldHeight, Pos requestedPos, Random rand, int distFromCenter) {

        int neighborCount = 0;
        int totalHeight = 0;
        for (int x = requestedPos.x - 1; x <= requestedPos.x + 1; x++) {
            for (int z = requestedPos.z - 1; z <= requestedPos.z + 1; z++) {
                Integer neighborHeight = (Integer) volcanoMap.getValueByKey(Util.chunkXZ2Int(x, z));
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
            int newHeight = (int) avgHeight - blocksDown;
            return newHeight;
        } else {
            return -1;
        }
    }

    private void setBlockAndNotifyAdequately(World worldIn, BlockPos pos, IBlockState state) {
        worldIn.setBlockState(pos, state, 2);
    }

    private static class Pos {

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
    }*/

}