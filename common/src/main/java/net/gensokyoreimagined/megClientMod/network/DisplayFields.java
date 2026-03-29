package net.gensokyoreimagined.megClientMod.network;

import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public final class DisplayFields {

    public static final int SHARED_DATA_ID = 0;
    public static final int INTERPOLATION_DELAY_ID = 8;
    public static final int TRANSFORM_DURATION_ID = 9;
    public static final int TRANSLATION_ID = 11;
    public static final int SCALE_ID = 12;
    public static final int LEFT_ROTATION_ID = 13;
    public static final int RIGHT_ROTATION_ID = 14;
    public static final int BILLBOARD_ID = 15;
    public static final int BRIGHTNESS_ID = 16;
    public static final int VIEW_RANGE_ID = 17;
    public static final int GLOW_COLOR_ID = 22;
    public static final int DISPLAY_TYPE_ID = 24;

    public static SynchedEntityData.DataValue<Byte> sharedData(byte value) {
        return new SynchedEntityData.DataValue<>(SHARED_DATA_ID, EntityDataSerializers.BYTE, value);
    }

    public static SynchedEntityData.DataValue<Integer> interpolationDelay(int value) {
        return new SynchedEntityData.DataValue<>(INTERPOLATION_DELAY_ID, EntityDataSerializers.INT, value);
    }

    public static SynchedEntityData.DataValue<Integer> transformDuration(int value) {
        return new SynchedEntityData.DataValue<>(TRANSFORM_DURATION_ID, EntityDataSerializers.INT, value);
    }

    public static SynchedEntityData.DataValue<Vector3f> translation(Vector3f value) {
        return new SynchedEntityData.DataValue<>(TRANSLATION_ID, EntityDataSerializers.VECTOR3, value);
    }

    public static SynchedEntityData.DataValue<Vector3f> scale(Vector3f value) {
        return new SynchedEntityData.DataValue<>(SCALE_ID, EntityDataSerializers.VECTOR3, value);
    }

    public static SynchedEntityData.DataValue<Quaternionf> leftRotation(Quaternionf value) {
        return new SynchedEntityData.DataValue<>(LEFT_ROTATION_ID, EntityDataSerializers.QUATERNION, value);
    }

    public static SynchedEntityData.DataValue<Quaternionf> rightRotation(Quaternionf value) {
        return new SynchedEntityData.DataValue<>(RIGHT_ROTATION_ID, EntityDataSerializers.QUATERNION, value);
    }

    public static SynchedEntityData.DataValue<Byte> billboard(byte value) {
        return new SynchedEntityData.DataValue<>(BILLBOARD_ID, EntityDataSerializers.BYTE, value);
    }

    public static SynchedEntityData.DataValue<Integer> brightness(int value) {
        return new SynchedEntityData.DataValue<>(BRIGHTNESS_ID, EntityDataSerializers.INT, value);
    }

    public static SynchedEntityData.DataValue<Float> viewRange(float value) {
        return new SynchedEntityData.DataValue<>(VIEW_RANGE_ID, EntityDataSerializers.FLOAT, value);
    }

    public static SynchedEntityData.DataValue<Integer> glowColor(int value) {
        return new SynchedEntityData.DataValue<>(GLOW_COLOR_ID, EntityDataSerializers.INT, value);
    }

    public static SynchedEntityData.DataValue<Byte> displayType(byte value) {
        return new SynchedEntityData.DataValue<>(DISPLAY_TYPE_ID, EntityDataSerializers.BYTE, value);
    }

    private DisplayFields() {}
}
