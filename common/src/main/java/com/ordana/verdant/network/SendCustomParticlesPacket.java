package com.ordana.verdant.network;

import com.mojang.serialization.Codec;
import com.ordana.verdant.Verdant;
import io.netty.buffer.ByteBuf;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.mehvahdjukaar.moonlight.api.platform.network.Message;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Locale;

public record SendCustomParticlesPacket(EventType eventType, BlockPos pos, int extraData) implements Message {

    public static Type<SendCustomParticlesPacket> TYPE = new Type<>(Verdant.res("send_particles"));

    public static final StreamCodec<RegistryFriendlyByteBuf, SendCustomParticlesPacket> STREAM_CODEC = StreamCodec.composite(
            EventType.STREAM_CODEC,
            SendCustomParticlesPacket::eventType,
            BlockPos.STREAM_CODEC,
            SendCustomParticlesPacket::pos,
            ByteBufCodecs.INT,
            SendCustomParticlesPacket::extraData,
            SendCustomParticlesPacket::new
    );

    @Override
    public void write(RegistryFriendlyByteBuf buf) {
        buf.writeInt(this.extraData);
        buf.writeByte(eventType.ordinal());
        buf.writeBlockPos(pos);
    }

    @Override
    public void handle(Context context) {
        clientStuff(eventType, pos, extraData);
    }

    @Environment(EnvType.CLIENT)
    public void clientStuff( EventType type, BlockPos pos, int extraData) {
        Player player = Minecraft.getInstance().player;
        var level = player.level();
        if (type == EventType.DECAY_LEAVES) {
            BlockState state = Block.stateById(extraData);
            var leafParticle = new BlockParticleOption(ParticleTypes.BLOCK, state);
            int color = Minecraft.getInstance().getBlockColors().getColor(state, level, pos, 0);

            //add more than one?
            for (int i = 0; i < 20; i++) {
                double d = pos.getX() + level.random.nextDouble();
                double e = pos.getY() - 0.05;
                double f = pos.getZ() + level.random.nextDouble();
                level.addParticle(leafParticle, d, e, f, 0.0, color, 0.0);
            }

            level.playSound(player, pos, SoundEvents.AZALEA_LEAVES_BREAK, SoundSource.BLOCKS, 1.0f, 1.0f);
        }
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public enum EventType implements StringRepresentable {
        DECAY_LEAVES;

        public static final Codec<EventType> CODEC = StringRepresentable.fromEnum(EventType::values);
        public static final StreamCodec<ByteBuf, EventType> STREAM_CODEC = ByteBufCodecs.fromCodec(CODEC);

        @Override
        public String getSerializedName() {
            return this.name().toLowerCase(Locale.ROOT);
        }
    }
}