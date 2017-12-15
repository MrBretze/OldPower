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

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        if (world.isRemote)
            return;
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

                                    setBlock(pos, b, world);

                                    BlockPos bpob = new BlockPos(middleX, posHeight, middleZ);

                                    if (countFirst >= 1 && random.nextFloat() <= 0.15) {
                                        bpob.up((random.nextFloat() <= 0.15) ? 1 : 2);
                                    } else {
                                        if (world.getBlockState(bpob) != Blocks.BEDROCK.getDefaultState())
                                            setBlock(bpob, Blocks.LAVA, world);
                                    }

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

    public boolean canReplace(World world, int x, int y, int z) {
        return canReplace(world, new BlockPos(x, y, z));
    }

    public boolean canReplace(World world, BlockPos pos) {
        if (world.isAirBlock(pos))
            return true;

        IBlockState state = world.getBlockState(pos);
        Material material = state.getMaterial();
        return (state == Blocks.BEDROCK.getDefaultState() || material == Material.WATER || material == Material.ROCK || material == Material.WOOD);
    }

    private void setBlock(BlockPos pos, Block block, World world) {
        world.getBlockState(pos);
        world.setBlockState(pos, block.getDefaultState());
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