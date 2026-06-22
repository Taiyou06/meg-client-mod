package net.gensokyoreimagined.megClientMod;

//? if neoforge {
/*import net.gensokyoreimagined.megClientMod.network.BulkDataHandler;
import net.gensokyoreimagined.megClientMod.network.BulkEntityDataPayload;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.payload.MinecraftRegisterPayload;
import net.neoforged.neoforge.network.payload.MinecraftUnregisterPayload;

import java.util.Set;

@Mod(value = "megclientmod", dist = Dist.CLIENT)
public class MegClientModNeoForge {

    public MegClientModNeoForge(IEventBus modBus) {
        Constants.LOG.info("ModelEngine Client Mod initializing on NeoForge");
        modBus.addListener(MegClientModNeoForge::registerPayloads);
        NeoForge.EVENT_BUS.addListener(MegClientModNeoForge::onLoggingIn);
    }

    private static void registerPayloads(RegisterPayloadHandlersEvent event) {
        // optional() lets the client receive this channel from a non-NeoForge (Paper) server.
        event.registrar("1")
                .optional()
                .playToClient(
                        BulkEntityDataPayload.TYPE,
                        BulkEntityDataPayload.STREAM_CODEC,
                        (payload, context) -> BulkDataHandler.handle(payload)
                );
    }

    private static void onLoggingIn(ClientPlayerNetworkEvent.LoggingIn event) {
        // NeoForge announces the channel during configuration, but Paper only fires
        // PlayerRegisterChannelEvent in the play phase, and the config registration carries into
        // play, so unregister then register here to force a fresh add that fires the event.
        var conn = Minecraft.getInstance().getConnection();
        if (conn == null)
            return;
        var channels = Set.of(BulkEntityDataPayload.TYPE.id());
        conn.send(new MinecraftUnregisterPayload(channels));
        conn.send(new MinecraftRegisterPayload(channels));
    }
}
*///?}
