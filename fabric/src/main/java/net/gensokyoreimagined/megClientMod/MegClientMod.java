package net.gensokyoreimagined.megClientMod;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.gensokyoreimagined.megClientMod.network.BulkDataHandler;
import net.gensokyoreimagined.megClientMod.network.BulkEntityDataPayload;

public class MegClientMod implements ModInitializer {

    @Override
    public void onInitialize() {
        Constants.LOG.info("ModelEngine Client Mod initializing on Fabric");
        CommonClass.init();

        // Register the custom payload type so the client knows how to decode it
        PayloadTypeRegistry.playS2C().register(
                BulkEntityDataPayload.TYPE,
                BulkEntityDataPayload.STREAM_CODEC
        );

        // Register the handler — Fabric calls this on the network thread,
        // so BulkDataHandler.handle() schedules work to the client main thread
        ClientPlayNetworking.registerGlobalReceiver(
                BulkEntityDataPayload.TYPE,
                (payload, context) -> BulkDataHandler.handle(payload)
        );

        Constants.LOG.info("Registered modelengine:bulk_data channel");
    }
}
