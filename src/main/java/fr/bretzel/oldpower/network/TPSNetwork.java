package fr.bretzel.oldpower.network;

import fr.bretzel.oldpower.client.render.RenderDebugScreen;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class TPSNetwork implements IMessage {

    private double ms = 00.00D;
    private double tps = 00.00D;

    private static final DecimalFormat df;
    private static final DecimalFormatSymbols dfs;

    static {
        df = new DecimalFormat("###.###");
        dfs = DecimalFormatSymbols.getInstance();
        dfs.setDecimalSeparator('.');
        df.setDecimalFormatSymbols(dfs);
    }

    public TPSNetwork() {
    }

    public TPSNetwork(double ms) {
        this.ms = ms;
        this.tps = Double.valueOf(String.valueOf(df.format(Math.min(1000.0 / ms, 20))));
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        ms = Double.valueOf(ByteBufUtils.readUTF8String(buf));
        tps = Double.valueOf(ByteBufUtils.readUTF8String(buf));
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, String.valueOf(ms));
        ByteBufUtils.writeUTF8String(buf, String.valueOf(tps));
    }

    public static class Handler implements IMessageHandler<TPSNetwork, IMessage> {

        @Override
        public IMessage onMessage(TPSNetwork tpsNetwork, MessageContext ctx) {
            RenderDebugScreen.MS = tpsNetwork.ms;
            RenderDebugScreen.TPS = tpsNetwork.tps;
            return null;
        }
    }

}
