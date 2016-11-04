package fr.bretzel.oldpower.network;

import fr.bretzel.oldpower.api.network.INetwork;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class TpsNetWork extends INetwork {

    public static double MS = 00.00D;

    @Override
    public void fromBytes(ByteBuf buf) {
        MS = buf.readDouble();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeDouble(MS);
    }

    @Override
    public IMessage onMessage(IMessage message, MessageContext ctx) {
        return null;
    }
}
