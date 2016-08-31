package fr.bretzel.oldpower.block;

import fr.bretzel.oldpower.api.LampType;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.item.EnumDyeColor;

import fr.bretzel.oldpower.OldPower;

public class BlockDecorativeLamp extends BlockLamp {

    public static final PropertyEnum<EnumDyeColor> COLOR = PropertyEnum.create("lampcolor", EnumDyeColor.class);

    public BlockDecorativeLamp(String unlocalizedName) {
        super(unlocalizedName, LampType.LAMP_DECORATIVE);
        setCreativeTab(OldPower.tabs);
        setLightLevel(1.0F);
        this.setDefaultState(this.blockState.getBaseState().withProperty(COLOR, EnumDyeColor.WHITE));
    }
}
