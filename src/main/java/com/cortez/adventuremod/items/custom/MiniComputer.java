package com.cortez.adventuremod.items.custom;

import com.cortez.adventuremod.AdventureMod;
import com.cortez.adventuremod.lua.AdventureLua;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.List;

public class MiniComputer extends Item
{
    public MiniComputer(Settings settings) {
        super(settings);
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        NbtComponent customData = stack.get(DataComponentTypes.CUSTOM_DATA);
        return customData != null;
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        NbtComponent customData = stack.get(DataComponentTypes.CUSTOM_DATA);



        if(customData != null){
            NbtCompound nbt = customData.copyNbt();
            if (nbt.contains("program_description")){
                tooltip.add(Text.literal("Adventure Mod").formatted(Formatting.BLUE));
                tooltip.add(Text.literal(nbt.getString("program_description")).styled(style ->
                        style.withColor(0xFF5555))); // Cor em hexadecimal
            }

            if (nbt.contains("program_name")) {
                Text customName = Text.literal(nbt.getString("program_name"))
                        .styled(style -> style.withColor(0x55FF55));

                stack.set(DataComponentTypes.CUSTOM_NAME, customName);
            }
        }

        super.appendTooltip(stack, context, tooltip, type);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient()) {
            ItemStack stack = user.getStackInHand(hand);

            // Recupera o NBT do item
            NbtComponent customData = stack.get(DataComponentTypes.CUSTOM_DATA);

            String code = null; // Código padrão

            if (customData != null) {
                NbtCompound nbt = customData.copyNbt();

                // Verifica se existe o código salvo
                if (nbt.contains("program_code")) {
                    code = nbt.getString("program_code");
                    AdventureMod.LOGGER.info("Código recuperado do NBT: " + code);
                    AdventureLua adventureLua = new AdventureLua((ServerWorld) world, (ServerPlayerEntity) user);
                    adventureLua.execLua(code);
                } else {
                    AdventureMod.LOGGER.warn("Nenhum código encontrado.");
                }
            } else {
                AdventureMod.LOGGER.warn("Nenhum código encontrado.");
            }


        }

        return TypedActionResult.success(user.getStackInHand(hand));

    }
}
