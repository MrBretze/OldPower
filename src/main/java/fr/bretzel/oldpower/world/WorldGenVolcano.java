package fr.bretzel.oldpower.world;

import fr.bretzel.oldpower.Logger;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.Random;

public class WorldGenVolcano implements IWorldGenerator {

    private double chance = 0.025;

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
        if (random.nextDouble() < chance) {
            Logger.info("Genererate new Volcano at: " + chunkX + ", " + chunkZ);
        }
    }
}
