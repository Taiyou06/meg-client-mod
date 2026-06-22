package net.gensokyoreimagined.megClientMod;

//? if fabric {
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.gensokyoreimagined.megClientMod.network.BulkDataHandler;
import net.gensokyoreimagined.megClientMod.network.BulkEntityDataPayload;

public class MegClientModFabric implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        Constants.LOG.info("ModelEngine Client Mod initializing on Fabric");

        // Fabric renamed playS2C() -> clientboundPlay() in 26.x.
        //? if >=26.1 {
        PayloadTypeRegistry.clientboundPlay().register(
                BulkEntityDataPayload.TYPE,
                BulkEntityDataPayload.STREAM_CODEC
        );
        //?} else {
        /*PayloadTypeRegistry.playS2C().register(
                BulkEntityDataPayload.TYPE,
                BulkEntityDataPayload.STREAM_CODEC
        );
        *///?}

        ClientPlayNetworking.registerGlobalReceiver(
                BulkEntityDataPayload.TYPE,
                (payload, context) -> BulkDataHandler.handle(payload)
        );
    }
}
//?}
