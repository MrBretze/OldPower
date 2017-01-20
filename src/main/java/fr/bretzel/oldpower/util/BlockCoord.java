package fr.bretzel.oldpower.util;

import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.List;

public class BlockCoord {

    private int x, y, z = 0;

    public BlockCoord(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public BlockCoord(BlockPos pos) {
        this(pos.getX(), pos.getY(), pos.getZ());
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public boolean equals(int x, int y, int z) {
        return this.x == x && this.y == y && this.z == z;
    }

    @Override
    public int hashCode() {
        int hash = 9;
        hash = 71 * hash + this.x;
        hash = 71 * hash + this.y;
        hash = 71 * hash + this.z;
        return hash;
    }

    public void copy(BlockCoord other) {
        this.x = other.x;
        this.y = other.y;
        this.z = other.z;
    }

    public int compareTo(Object o) {
        if (o instanceof BlockCoord) {
            BlockCoord c = (BlockCoord) o;
            return compareTo(c.getX(), c.getY(), c.getZ());
        }
        return 0;
    }

    public BlockCoord copy() {
        return new BlockCoord(this.x, this.y, this.z);
    }

    public int compareTo(int x, int y, int z) {
        if (this.x < x) {
            return -1;
        } else if (this.x > x) {
            return 1;
        } else if (this.y < y) {
            return -1;
        } else if (this.y > y) {
            return 1;
        } else if (this.z < z) {
            return -1;
        } else if (this.z > z) {
            return 1;
        } else {
            return 0;
        }
    }

    public int getChunkX() {
        return x >> 4;
    }

    public int getChunkZ() {
        return z >> 4;
    }

    public static BlockPos toBlockPos(BlockCoord blockCoord) {
        return blockCoord.toBlockPos();
    }

    public BlockCoord add(BlockPos pos) {
        return add(new BlockCoord(pos));
    }

    public BlockCoord add(BlockCoord t) {
        return add(t.getX(), t.getY(), t.getZ());
    }

    public BlockCoord add(int i, int j, int k) {
        this.x += i;
        this.y += j;
        this.z += k;
        return this;
    }

    public BlockCoord sub(BlockPos t) {
        return add(new BlockCoord(t));
    }

    public BlockCoord sub(BlockCoord t) {
        return add(t.getX(), t.getY(), t.getZ());
    }

    public BlockCoord sub(int i, int j, int k) {
        this.x -= i;
        this.y -= j;
        this.z -= k;
        return this;
    }

    public BlockPos toBlockPos() {
        return new BlockPos(getX(), getY(), getZ());
    }

    public List<BlockCoord> getRadiusBlocks(int radius) {
        List<BlockCoord> matches = new ArrayList<>((int) Math.pow(2 * radius, 3));
        BlockCoord c = this.copy();

        int minX = c.x - radius;
        int maxX = c.x + radius + 1;
        int minY = c.y - radius;
        int maxY = c.y + radius + 1;
        int minZ = c.z - radius;
        int maxZ = c.z + radius + 1;

        for (int x = minX; x < maxX; x++) {
            for (int y = minY; y < maxY; y++) {
                for (int z = minZ; z < maxZ; z++) {
                    BlockCoord t = new BlockCoord(x, y, z);
                    matches.add(t);
                }
            }
        }

        return matches;
    }
}
