package fr.bretzel.oldpower.world;

import fr.bretzel.oldpower.Logger;
import fr.bretzel.oldpower.util.CommonRegistry;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class WorldGenVolcano implements IWorldGenerator {

    private static final int MAX_RADIUS = 200;
    private double CHANCE = 0.025;

    private HashMap<BlockPos, Integer> hashMap = new HashMap<>();

    public static ArrayList<Object> canReplaceList = new ArrayList<>();

    public static ArrayList<Block> logToCharredLog = new ArrayList<>();
    public static ArrayList<Block> sandToTephra = new ArrayList<>();

    public WorldGenVolcano() {

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
        canReplaceList.add(CommonRegistry.blockBasalt);
        canReplaceList.add(CommonRegistry.blockCharredLog);

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

    }

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        if (world.isRemote || !world.provider.isSurfaceWorld())
            return;

        if (random.nextDouble() <= CHANCE) {
            int middleX = chunkX * 16 + random.nextInt(16);
            int middleZ = chunkZ * 16 + random.nextInt(16);
            int baseY = world.getHeight(middleX, middleZ) + 40 + random.nextInt(50);

            if (world.getBlockState(new BlockPos(middleX, 10, middleZ)).getMaterial() == Material.LAVA) {

                ArrayList<Pos>[] poss = getPositions(world, middleX, middleZ, chunkGenerator);

                if (poss == null) {
                    Logger.info("Canceled Volcano generation !");
                    return;
                }

                Logger.info("Genererate new Volcano at: X: %d, Y:%d , Z: %d", middleX, baseY, middleZ);

                boolean first = true;

                for (int dist = 0; dist < poss.length; dist++) {

                    ArrayList<Pos> posArrayList = poss[dist];
                    boolean isFinished = true;

                    int countFirst = 0;

                    for (Pos p : posArrayList) {

                        int worldHeight = world.getHeight(p.x + middleX, p.z + middleZ) - 1;
                        int posHeight = first ? baseY : getNewVolcanoHeight(worldHeight, p, random, dist);
                        BlockPos posReplace = new BlockPos(p.x + middleX, posHeight, p.z + middleZ);

                        if (worldHeight >= 0 && (posHeight > worldHeight || canReplace(world, posReplace))) {

                            hashMap.put(new BlockPos(p.x, 0, p.z), posHeight);

                            if (!first) {
                                for (int i = posHeight; i > 0 && (i <= baseY && (i > worldHeight) || canReplace(world, new BlockPos(p.x + middleX, i, p.z + middleZ))); i--) {
                                    BlockPos pos = new BlockPos(p.x + middleX, i, p.z + middleZ);

                                    Block b = (world.isAirBlock(new BlockPos(pos).up()) || world.getBlockState(new BlockPos(pos.up())).getMaterial() == Material.WATER)
                                            ? random.nextFloat() <= 0.15 ? Blocks.MAGMA : CommonRegistry.blockBasalt : CommonRegistry.blockBasalt;

                                    setBlock(pos, Blocks.AIR, world);
                                    setBlock(pos, b, world);



                                    BlockPos bpob = new BlockPos(middleX, posHeight, middleZ);

                                    if (countFirst >= 1 && random.nextFloat() <= 0.15) {
                                        bpob.up((random.nextFloat() <= 0.15) ? 1 : 2);
                                    } else {
                                        if (world.getBlockState(bpob) != Blocks.BEDROCK.getDefaultState())
                                            setBlock(bpob, Blocks.LAVA, world);
                                    }

                                    /*for (int x = -17; x <= 17; x++) {
                                        for (int z = -17; z <= 17; z++) {
                                            BlockPos postion = new BlockPos(p.x + middleX + x, i - 1, p.z + middleZ + z);
                                            IBlockState state = world.getBlockState(postion);
                                            Block block = state.getBlock();

                                            if (state.getMaterial() == Material.LEAVES || state.getMaterial() == Material.PLANTS || state.getMaterial() == Material.VINE)
                                                setBlock(postion, Blocks.AIR, world);

                                            if (logToCharredLog.contains(block))
                                                setBlock(postion, CommonRegistry.blockCharredLog.getStateFromMeta(block.getMetaFromState(state)), world);
                                            if (sandToTephra.contains(block))
                                                setBlock(postion, CommonRegistry.blockTephra, world);
                                        }
                                    }*/

                                    countFirst++;
                                }
                            }
                            isFinished = false;
                        }
                        first = false;
                    }
                    if (isFinished) {
                        hashMap.clear();
                        break;
                    }
                }
            }
        }
    }

    public boolean canReplace(World world, BlockPos pos) {
        if (world.isAirBlock(pos))
            return true;

        IBlockState state = world.getBlockState(pos);
        Material material = state.getMaterial();
        return canReplaceList.contains(material) || canReplaceList.contains(state.getBlock());
    }

    private void setBlock(BlockPos pos, Block block, World world) {
        world.getBlockState(pos);
        world.setBlockState(pos, block.getDefaultState());
    }

    private void setBlock(BlockPos pos, IBlockState block, World world) {
        world.getBlockState(pos);
        world.setBlockState(pos, block);
    }


    public ArrayList<Pos>[] getPositions(World world, int midleX, int midleZ, IChunkGenerator generator) {
        ArrayList<Pos>[] distMap = new ArrayList[MAX_RADIUS];
        for (int x = -MAX_RADIUS; x <= MAX_RADIUS; x++) {
            for (int z = -MAX_RADIUS; z <= MAX_RADIUS; z++) {
                BlockPos pos = new BlockPos(midleX + x, world.rand.nextInt(60) + 50, midleZ + z);
                if (generator.isInsideStructure(world, "Village", pos) ||
                        generator.isInsideStructure(world, "Stronghold", pos)) {
                    return null;
                }

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

    private int getNewVolcanoHeight(int worldHeight, Pos requestedPos, Random rand, int distFromCenter) {

        int neighborCount = 0;
        int totalHeight = 0;
        for (int x = requestedPos.x - 1; x <= requestedPos.x + 1; x++) {
            for (int z = requestedPos.z - 1; z <= requestedPos.z + 1; z++) {
                Integer neighborHeight = hashMap.get(new BlockPos(x, 0, z));
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