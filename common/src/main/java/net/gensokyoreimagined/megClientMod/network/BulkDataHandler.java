package net.gensokyoreimagined.megClientMod.network;

import net.gensokyoreimagined.megClientMod.Constants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Display;
import net.minecraft.world.entity.Entity;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.ArrayList;

public final class BulkDataHandler {

    private BulkDataHandler() {}

    public static void handle(BulkEntityDataPayload payload) {
        var mc = Minecraft.getInstance();
        var buf = payload.data();
        var entries = decode(buf);
        mc.execute(() -> apply(mc.level, entries));
    }

    private record BoneEntry(int entityId, byte bitmask, Object[] fields) {}

    private static BoneEntry[] decode(FriendlyByteBuf buf) {
        byte packetType = buf.readByte();
        if (packetType != BulkEntityDataPayload.PACKET_TYPE_BULK_DATA) {
            Constants.LOG.warn("Unknown bulk data packet type: {}", packetType);
            return new BoneEntry[0];
        }

        int count = buf.readVarInt();
        var entries = new BoneEntry[count];

        for (int i = 0; i < count; i++) {
            int entityId = buf.readVarInt();
            byte bitmask = buf.readByte();
            var fields = new Object[8];

            if (hasBit(bitmask, BulkEntityDataPayload.FIELD_TRANSLATION)) {
                fields[BulkEntityDataPayload.FIELD_TRANSLATION] = buf.readVector3f();
            }
            if (hasBit(bitmask, BulkEntityDataPayload.FIELD_LEFT_ROTATION)) {
                fields[BulkEntityDataPayload.FIELD_LEFT_ROTATION] = buf.readQuaternion();
            }
            if (hasBit(bitmask, BulkEntityDataPayload.FIELD_SCALE)) {
                fields[BulkEntityDataPayload.FIELD_SCALE] = buf.readVector3f();
            }
            if (hasBit(bitmask, BulkEntityDataPayload.FIELD_RIGHT_ROTATION)) {
                fields[BulkEntityDataPayload.FIELD_RIGHT_ROTATION] = buf.readQuaternion();
            }
            if (hasBit(bitmask, BulkEntityDataPayload.FIELD_TRANSFORM_DURATION)) {
                fields[BulkEntityDataPayload.FIELD_TRANSFORM_DURATION] = buf.readVarInt();
            }
            if (hasBit(bitmask, BulkEntityDataPayload.FIELD_GLOW_DATA)) {
                fields[BulkEntityDataPayload.FIELD_GLOW_DATA] = new GlowData(buf.readByte(), buf.readInt());
            }
            if (hasBit(bitmask, BulkEntityDataPayload.FIELD_BRIGHTNESS)) {
                fields[BulkEntityDataPayload.FIELD_BRIGHTNESS] = buf.readInt();
            }
            if (hasBit(bitmask, BulkEntityDataPayload.FIELD_RENDER_DATA)) {
                fields[BulkEntityDataPayload.FIELD_RENDER_DATA] = new RenderData(buf.readByte(), buf.readFloat(), buf.readByte());
            }

            entries[i] = new BoneEntry(entityId, bitmask, fields);
        }

        return entries;
    }

    private static void apply(ClientLevel level, BoneEntry[] entries) {
        if (level == null) return;

        for (var entry : entries) {
            Entity entity = level.getEntity(entry.entityId);
            if (!(entity instanceof Display)) continue;

            var dataValues = new ArrayList<SynchedEntityData.DataValue<?>>();
            byte bitmask = entry.bitmask;
            boolean hasTransform = (bitmask & 0x0F) != 0;

            if (hasTransform) {
                dataValues.add(DisplayFields.interpolationDelay(0));
            }

            if (hasBit(bitmask, BulkEntityDataPayload.FIELD_TRANSLATION)) {
                dataValues.add(DisplayFields.translation((Vector3f) entry.fields[BulkEntityDataPayload.FIELD_TRANSLATION]));
            }
            if (hasBit(bitmask, BulkEntityDataPayload.FIELD_LEFT_ROTATION)) {
                dataValues.add(DisplayFields.leftRotation((Quaternionf) entry.fields[BulkEntityDataPayload.FIELD_LEFT_ROTATION]));
            }
            if (hasBit(bitmask, BulkEntityDataPayload.FIELD_SCALE)) {
                dataValues.add(DisplayFields.scale((Vector3f) entry.fields[BulkEntityDataPayload.FIELD_SCALE]));
            }
            if (hasBit(bitmask, BulkEntityDataPayload.FIELD_RIGHT_ROTATION)) {
                dataValues.add(DisplayFields.rightRotation((Quaternionf) entry.fields[BulkEntityDataPayload.FIELD_RIGHT_ROTATION]));
            }
            if (hasBit(bitmask, BulkEntityDataPayload.FIELD_TRANSFORM_DURATION)) {
                dataValues.add(DisplayFields.transformDuration((int) entry.fields[BulkEntityDataPayload.FIELD_TRANSFORM_DURATION]));
            }
            if (hasBit(bitmask, BulkEntityDataPayload.FIELD_GLOW_DATA)) {
                var glow = (GlowData) entry.fields[BulkEntityDataPayload.FIELD_GLOW_DATA];
                dataValues.add(DisplayFields.sharedData(glow.sharedData()));
                dataValues.add(DisplayFields.glowColor(glow.glowColor()));
            }
            if (hasBit(bitmask, BulkEntityDataPayload.FIELD_BRIGHTNESS)) {
                dataValues.add(DisplayFields.brightness((int) entry.fields[BulkEntityDataPayload.FIELD_BRIGHTNESS]));
            }
            if (hasBit(bitmask, BulkEntityDataPayload.FIELD_RENDER_DATA)) {
                var render = (RenderData) entry.fields[BulkEntityDataPayload.FIELD_RENDER_DATA];
                dataValues.add(DisplayFields.billboard(render.billboard()));
                dataValues.add(DisplayFields.viewRange(render.viewRange()));
                dataValues.add(DisplayFields.displayType(render.displayType()));
            }

            if (!dataValues.isEmpty()) {
                entity.getEntityData().assignValues(dataValues);
            }
        }
    }

    private static boolean hasBit(byte bitmask, int bit) {
        return (bitmask & (1 << bit)) != 0;
    }

    private record GlowData(byte sharedData, int glowColor) {}
    private record RenderData(byte billboard, float viewRange, byte displayType) {}
}
