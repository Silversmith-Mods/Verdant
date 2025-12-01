package com.ordana.verdant.network;

import net.mehvahdjukaar.moonlight.api.platform.network.NetworkHelper;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public class NetworkHandler {

    public static void init() {
        NetworkHelper.addNetworkRegistration(registerMessagesEvent -> {
            registerMessagesEvent.registerClientBound(new CustomPacketPayload.TypeAndCodec<>(SendCustomParticlesPacket.TYPE, SendCustomParticlesPacket.STREAM_CODEC));
        }, 1);
    }


}