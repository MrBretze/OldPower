package fr.bretzel.oldpower.world;

import fr.bretzel.oldpower.Logger;
import fr.bretzel.oldpower.util.CommonRegistry;
import fr.bretzel.oldpower.util.LongHashMap;
import fr.bretzel.oldpower.util.Util;

import javafx.geometry.Pos;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.ArrayList;
import java.util.Random;

public class WorldGenVolcano implements IWorldGenerator {

    private static final int MAX_RADIUS = 200;
    private double CHANCE = 0.025;

    private LongHashMap hashMap = new LongHashMap();

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

                        if (worldHeight >= 0 && (posHeight > worldHeight || canReplace(world, new BlockPos(p.x + middleX, posHeight, p.z + middleZ)))) {

                            hashMap.add(Util.chunkXZ2Int(p.x, p.z), posHeight);

                            if (!first) {
                                for (int i = posHeight; i > 0 && (i <= baseY && (i > worldHeight) || canReplace(world, new BlockPos(p.x + middleX, i, p.z + middleZ))); i--) {
                                    setBlock(new BlockPos(p.x + middleX, i, p.z + middleZ), CommonRegistry.blockBasalt, world);
                                }
                            }
                            isFinished = false;
                        }
                        first = false;
                    }
                    if (isFinished) {
                        hashMap = new LongHashMap();
                        break;
                    }
                }
            }
        }
    }


    public boolean canReplace(World world, BlockPos pos) {
        if (world.isAirBlock(pos))
            return true;
        Block block = world.getBlockState(pos).getBlock();
        Material material = block.getDefaultState().getMaterial();
        return (material == Material.WOOD || material == Material.AIR || material == Material.CACTUS || material == Material.LEAVES ||
                material == Material.PLANTS || material == Material.VINE || material == Material.WATER|| block == Blocks.WATER ||
                block == Blocks.FLOWING_WATER || material == Material.ROCK || material == Material.GROUND);
    }

    private void setBlock(BlockPos pos, Block block, World world) {
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

    public int getNewVolcHeight(int baseHeight, Pos pos, Random random, int distFromCenter) {
        int neighborCount = 0;
        int totalHeight = 0;

        for (int x = pos.x - 1; x <= pos.x + 1; x++) {
            for (int z = pos.z - 1; z <= pos.z + 1; z++) {
                Integer neighborHeight = (Integer) hashMap.getValueByKey(Util.chunkXZ2Int(x, z));
                if (neighborHeight != null) {
                    neighborCount++;
                    totalHeight += neighborHeight;
                }
            }
        }

        if (neighborCount != 0) {
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
                blocksDown = (int) (Math.pow(avgHeight - baseHeight + 1, 1.2) * 0.005D + (random.nextDouble() - 0.5) * 3 + 0.4D);
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
}