package net.gensokyoreimagined.megClientMod.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record BulkEntityDataPayload(FriendlyByteBuf data) implements CustomPacketPayload {

    public static final byte PACKET_TYPE_BULK_DATA = 0x00;

    public static final int FIELD_TRANSLATION = 0;
    public static final int FIELD_LEFT_ROTATION = 1;
    public static final int FIELD_SCALE = 2;
    public static final int FIELD_RIGHT_ROTATION = 3;
    public static final int FIELD_TRANSFORM_DURATION = 4;
    public static final int FIELD_GLOW_DATA = 5;
    public static final int FIELD_BRIGHTNESS = 6;
    public static final int FIELD_RENDER_DATA = 7;

    public static final CustomPacketPayload.Type<BulkEntityDataPayload> TYPE =
            new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath("modelengine", "bulk_data"));

    public static final StreamCodec<FriendlyByteBuf, BulkEntityDataPayload> STREAM_CODEC =
            CustomPacketPayload.codec(BulkEntityDataPayload::write, BulkEntityDataPayload::read);

    private void write(FriendlyByteBuf buf) {
        buf.writeBytes(data.copy());
    }

    private static BulkEntityDataPayload read(FriendlyByteBuf buf) {
        return new BulkEntityDataPayload(new FriendlyByteBuf(buf.readBytes(buf.readableBytes())));
    }

    @Override
    public Type<BulkEntityDataPayload> type() {
        return TYPE;
    }
}
