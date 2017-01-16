package fr.bretzel.oldpower.network;

import fr.bretzel.oldpower.Logger;
import fr.bretzel.oldpower.client.render.RenderDebugScreen;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class TPSNetwork implements IMessage {

    private double ms = 00.00D;

    public TPSNetwork() {
    }

    public TPSNetwork(Double ms) {
        this.ms = ms;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        ms = Double.valueOf(ByteBufUtils.readUTF8String(buf)); // this class is very useful in general for writing more complex objects
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, String.valueOf(ms));
    }

    public static class Handler implements IMessageHandler<TPSNetwork, IMessage> {

        @Override
        public IMessage onMessage(TPSNetwork tpsNetwork, MessageContext ctx) {
            Logger.info("MS RECEVIED: " + tpsNetwork.ms);
            RenderDebugScreen.MS = tpsNetwork.ms;
            return null; // no response in this case
        }
    }

}
