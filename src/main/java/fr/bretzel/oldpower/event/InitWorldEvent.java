package fr.bretzel.oldpower.event;

import fr.bretzel.oldpower.world.WorldGenVolcano;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Iterator;

public class InitWorldEvent {

    @SubscribeEvent
    public void onWorldLoadEvent(WorldEvent.Load event) {
        NBTTagCompound compound = event.getWorld().getWorldInfo().getDimensionData(0);
        if (compound.hasKey("oldpower")) {
            if (compound.hasKey("volcano")) {
                initVolcano(compound.getCompoundTag("oldpower").getTagList("volcano", 8));
            }
        }
    }

    @SubscribeEvent
    public void onWorldSaveEvent(WorldEvent.Save event) {
        NBTTagCompound compound = event.getWorld().getWorldInfo().getDimensionData(0).getCompoundTag("oldpower");

        //saveVolcano(compound);

        //event.getWorld().getWorldInfo().getAdditionalProperty()
    }

    private void initVolcano(NBTTagList list) {
        Iterator<NBTBase> lists = list.iterator();

        while (lists.hasNext()) {
            NBTBase base = lists.next();
            if (base instanceof NBTTagString) {
                String[] s = ((NBTTagString) base).getString().split(":");
                BlockPos pos = new BlockPos(Integer.valueOf(s[0]), Integer.valueOf(s[1]), Integer.valueOf(s[2]));
                //WorldGenVolcano.volcanoPlaced.add(pos);
            }
        }
    }

    /*private void saveVolcano(NBTTagCompound compound) {
        if (compound.hasKey())
    }*/
}
