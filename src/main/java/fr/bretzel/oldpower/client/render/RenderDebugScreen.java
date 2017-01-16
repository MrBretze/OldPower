package fr.bretzel.oldpower.client.render;

import fr.bretzel.oldpower.Logger;
import fr.bretzel.oldpower.OldPower;
import fr.bretzel.oldpower.network.TPSNetwork;
import fr.bretzel.oldpower.util.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RenderDebugScreen {

    public static List<UUID> players = new ArrayList<>();
    public static double MS = 00.00D;
    public static double TPS = 00.00D;
    public static long lastSend = 0;

    static {
        lastSend = System.currentTimeMillis();
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onDebugRender(RenderGameOverlayEvent.Text event) {
        if (Minecraft.getMinecraft().gameSettings.showDebugInfo) {
            event.getLeft().add("");
            event.getLeft().add("TPS: " + getColorTPS(TPS) + " (" + new DecimalFormat("###.###").format(MS) + " MS)");
        }
    }

    @SubscribeEvent
    public void onTickEvent(TickEvent.PlayerTickEvent event) {
        long sec = ((System.currentTimeMillis() - lastSend) / 1000);
        if (event.player != null && event.player.getServer() != null && sec >= 1) {
            EntityPlayer pl = event.player;
            if (event.side == Side.SERVER) {
                double ms = Util.mean(pl.getServer().tickTimeArray) * 1.0E-6D;
                OldPower.networkWrapper.sendTo(new TPSNetwork(ms), (EntityPlayerMP) pl);
                lastSend = System.currentTimeMillis();
            }
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
}
