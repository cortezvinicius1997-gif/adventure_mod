package com.cortez.adventuremod.mixin;

import com.cortez.adventuremod.util.IEntityDataSaver;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public class ItemStackMixin {

    @Inject(method = "finishUsing", at = @At("RETURN"))
    private void afterFinishUsing(World world, LivingEntity user, CallbackInfoReturnable<ItemStack> cir) {
        ItemStack self = (ItemStack)(Object)this;

        var foodData = self.get(DataComponentTypes.FOOD);

        if (foodData != null && user != null && !world.isClient()) {
            Item item = self.getItem();

            if (item == Items.APPLE) applyThirst(user, 0.3F, 0);
            else if (item == Items.MELON_SLICE) applyThirst(user, 0.0F, 0);
            else if (item == Items.SWEET_BERRIES) applyThirst(user, 0.1F, 0);
            else if (item == Items.GLOW_BERRIES) applyThirst(user, 0.1F, 0);
            else if (item == Items.CHORUS_FRUIT) applyThirst(user, 0.2F, 0);

            else if (item == Items.CARROT) applyThirst(user, 0.3F, 0);
            else if (item == Items.GOLDEN_CARROT) applyThirst(user, 0.2F, 0);
            else if (item == Items.BEETROOT) applyThirst(user, 0.3F, 0);

            else if (item == Items.POTATO) applyThirst(user, 0.5F, 0);
            else if (item == Items.BAKED_POTATO) applyThirst(user, 0.7F, 1);
            else if (item == Items.POISONOUS_POTATO) applyThirst(user, 0.5F, 1);

            else if (item == Items.DRIED_KELP) applyThirst(user, 1.3F, 1);

            else if (item == Items.BREAD) applyThirst(user, 1.0F, 1);
            else if (item == Items.COOKIE) applyThirst(user, 1.2F, 1);
            else if (item == Items.PUMPKIN_PIE) applyThirst(user, 0.8F, 1);

            else if (item == Items.BEEF) applyThirst(user, 0.8F, 1);
            else if (item == Items.COOKED_BEEF) applyThirst(user, 1.4F, 2);

            else if (item == Items.PORKCHOP) applyThirst(user, 0.8F, 1);
            else if (item == Items.COOKED_PORKCHOP) applyThirst(user, 1.4F, 2);

            else if (item == Items.MUTTON) applyThirst(user, 0.9F, 1);
            else if (item == Items.COOKED_MUTTON) applyThirst(user, 1.3F, 2);

            else if (item == Items.CHICKEN) applyThirst(user, 0.9F, 1);
            else if (item == Items.COOKED_CHICKEN) applyThirst(user, 1.3F, 2);

            else if (item == Items.RABBIT) applyThirst(user, 0.8F, 1);
            else if (item == Items.COOKED_RABBIT) applyThirst(user, 1.2F, 2);

            else if (item == Items.COD) applyThirst(user, 0.7F, 1);
            else if (item == Items.COOKED_COD) applyThirst(user, 1.1F, 2);

            else if (item == Items.SALMON) applyThirst(user, 0.7F, 1);
            else if (item == Items.COOKED_SALMON) applyThirst(user, 1.1F, 2);

            else if (item == Items.PUFFERFISH) applyThirst(user, 1.5F, 2);
            else if (item == Items.TROPICAL_FISH) applyThirst(user, 0.8F, 1);

            else if (item == Items.SPIDER_EYE) applyThirst(user, 1.0F, 2);

            else if (item == Items.MUSHROOM_STEW) applyThirst(user, 0.0F, 0);
            else if (item == Items.BEETROOT_SOUP) applyThirst(user, 0.0F, 0);
            else if (item == Items.RABBIT_STEW) applyThirst(user, 0.0F, 0);

            else if (item == Items.HONEY_BOTTLE) applyThirst(user, -0.5F, -1);
        }
    }

    private void applyThirst(LivingEntity user, float exhaustionAmount, int thirstLoss) {
        IEntityDataSaver data = (IEntityDataSaver) user;
        var nbt = data.getPersistentData();

        float exhaustion = nbt.getFloat("thirst_exhaustion");
        exhaustion += exhaustionAmount;
        nbt.putFloat("thirst_exhaustion", exhaustion);

        int thirst = nbt.getInt("thirst");
        thirst -= thirstLoss;
        if (thirst < 0) thirst = 0;
        nbt.putInt("thirst", thirst);
    }
}
