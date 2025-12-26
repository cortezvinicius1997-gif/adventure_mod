package com.cortez.adventuremod.mixin;

import com.cortez.adventuremod.util.GetNBTData;
import com.cortez.adventuremod.util.IEntityDataSaver;
import com.mojang.datafixers.util.Either;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Unit;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(ServerPlayerEntity.class)
public class SleepHandlerMixin {

    @Inject(method = "trySleep", at = @At("RETURN"))
    private void onTrySleep(BlockPos pos, CallbackInfoReturnable<Either<PlayerEntity.SleepFailureReason, Unit>> cir) {

        // Se o retorno contém o lado direito (Unit), o sono foi aceito com sucesso
        Either<PlayerEntity.SleepFailureReason, Unit> result = cir.getReturnValue();
        if (result == null) return; // por segurança
        if (result.right().isEmpty()) {
            return; // não foi possível dormir
        }

        // Aqui o jogador realmente deitou e o sleep foi bem sucedido
        ServerPlayerEntity player = (ServerPlayerEntity) (Object) this;

        int y = pos.getY();

        IEntityDataSaver data = (IEntityDataSaver) player;

        GetNBTData getNBTData = new GetNBTData(data);

        int acclimatization = getNBTData.getInt("acclimatization").orElse(0);

        if (y >= 128 && y < 191) {
            if (acclimatization < 1) {
                data.getPersistentData().putInt("acclimatization", 1);
            }
        } else if (y >= 191 && y < 270) {
            if (acclimatization < 2) {
                data.getPersistentData().putInt("acclimatization", acclimatization + 1);
            }
        } else if(y >= 270){
            if (acclimatization < 3) {
                data.getPersistentData().putInt("acclimatization", acclimatization + 1);
            }
        } else if (y < 128) {
            if (acclimatization > 0) {
                data.getPersistentData().putInt("acclimatization", acclimatization - 1);
            }
        }
    }
}

