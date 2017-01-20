package fr.bretzel.oldpower.block;

import fr.bretzel.oldpower.OldPower;
import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockCharredLog extends BlockRotatedPillar {

    private String unlocalizedName = "";

    public BlockCharredLog(String unlocalizedName) {
        super(Material.WOOD);
        this.unlocalizedName = unlocalizedName;
        setSoundType(SoundType.WOOD);
        setCreativeTab(OldPower.tabs);
    }

    public String getUnlocalizedName() {
        return "tile." + OldPower.MODID + "." + unlocalizedName;
    }
}
