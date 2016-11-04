package fr.bretzel.oldpower.client.render;

import fr.bretzel.oldpower.Logger;
import fr.bretzel.oldpower.OldPower;
import fr.bretzel.oldpower.network.TpsNetWork;
import fr.bretzel.oldpower.util.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class RenderDebugScreen {

    private static final DecimalFormat df;
    private static final DecimalFormatSymbols dfs;

    static {
        df = new DecimalFormat("###.###");
        dfs = DecimalFormatSymbols.getInstance();
        dfs.setDecimalSeparator('.');
        df.setDecimalFormatSymbols(dfs);
    }

    @SubscribeEvent
    public void onDebugRender(RenderGameOverlayEvent.Text event) {
        if (Minecraft.getMinecraft().gameSettings.showDebugInfo) {
            event.getLeft().add("");
            String stTPS = String.valueOf(df.format(Math.min(1000.0 / TpsNetWork.MS, 20)));
            double tps = Double.parseDouble(stTPS);
            event.getLeft().add("TPS: " + getColorTPS(tps) + " (" + df.format(TpsNetWork.MS) + " MS)");
        }
    }

    private String getColorTPS(double i) {

        if (i >= 15.5)
            return TextFormatting.GREEN + String.valueOf(i) + TextFormatting.RESET;

        if (i <= 15.5 && i >= 10.5)
            return TextFormatting.GOLD + String.valueOf(i) + TextFormatting.RESET;

        if (i <= 10.5 && i >= 5.5)
            return TextFormatting.RED + String.valueOf(i) + TextFormatting.RESET;

        if (i <= 5.5)
            return TextFormatting.DARK_RED + String.valueOf(i) + TextFormatting.RESET;

        Logger.info(i);
        return TextFormatting.WHITE + String.valueOf(i);
    }

    @SubscribeEvent
    public void onTickEvent(TickEvent.PlayerTickEvent event) {
        if (event.player != null && event.player.getServer() != null && OldPower.isClientSide) {
            EntityPlayer pl = event.player;
            if (pl instanceof EntityPlayerMP) {
                EntityPlayerMP playerMP = (EntityPlayerMP) pl;
                TpsNetWork.MS = Util.mean(playerMP.mcServer.tickTimeArray) * 1.0E-6D;
            }
        }
    }
}
