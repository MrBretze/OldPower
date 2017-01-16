package fr.bretzel.oldpower.block;

import fr.bretzel.oldpower.OldPower;
import fr.bretzel.oldpower.util.RGBColor;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;

public class BlockTephra extends BlockFalling {

    private String unlocalizedName = "";

    public BlockTephra(String unlocalizedName) {
        super();
        this.unlocalizedName = unlocalizedName;
        setSoundType(SoundType.SAND);
        setCreativeTab(OldPower.tabs);
    }

    public String getUnlocalizedName() {
        return "tile." + OldPower.MODID + "." + unlocalizedName;
    }

    @Override
    public int getDustColor(IBlockState p_189876_1_) {
        return RGBColor.Color.BLACK.getHex();
    }
}
