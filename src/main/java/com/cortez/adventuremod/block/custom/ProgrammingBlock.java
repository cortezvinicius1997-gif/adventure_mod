package com.cortez.adventuremod.block.custom;


import com.cortez.adventuremod.block.entity.custom.ProgrammingBlockEntity;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Stream;

public class ProgrammingBlock extends BlockWithEntity implements BlockEntityProvider
{
    public static final MapCodec<ProgrammingBlock> CODEC = ProgrammingBlock.createCodec(ProgrammingBlock::new);
    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;

    private static final VoxelShape SHAPE_NORTH = VoxelShapes.union(
            // Base (plataforma inferior - 4 pixels de altura)
            Block.createCuboidShape(0, 0, 0, 16, 4, 16),
            // Plataforma superior da base
            Block.createCuboidShape(3, 4, 4, 13, 5, 13),
            // Tela principal (painel traseiro)
            Block.createCuboidShape(1, 5, 8, 15, 16, 14),
            // Suporte direito da tela
            Block.createCuboidShape(13.5, 5, 14, 15, 16, 15),
            // Suporte esquerdo da tela
            Block.createCuboidShape(1, 5, 14, 2.5, 16, 15),
            // Barra superior da tela
            Block.createCuboidShape(2.5, 14.5, 14, 13.5, 16, 15),
            // Barra inferior da tela
            Block.createCuboidShape(2.5, 5, 14, 13.5, 6.5, 15),
            // Parte frontal (display/console)
            Block.createCuboidShape(2, 5, 1, 14, 13, 8)
    );

    private static final VoxelShape SHAPE_SOUTH = VoxelShapes.union(
            Block.createCuboidShape(0, 0, 0, 16, 4, 16),
            Block.createCuboidShape(3, 4, 3, 13, 5, 12),
            Block.createCuboidShape(1, 5, 2, 15, 16, 8),
            Block.createCuboidShape(1, 5, 1, 2.5, 16, 2),
            Block.createCuboidShape(13.5, 5, 1, 15, 16, 2),
            Block.createCuboidShape(2.5, 14.5, 1, 13.5, 16, 2),
            Block.createCuboidShape(2.5, 5, 1, 13.5, 6.5, 2),
            Block.createCuboidShape(2, 5, 8, 14, 13, 15)
    );

    private static final VoxelShape SHAPE_EAST = VoxelShapes.union(
            // Base (plataforma inferior - 4 pixels de altura)
            Block.createCuboidShape(0, 0, 0, 16, 4, 16),
            // Plataforma superior da base
            Block.createCuboidShape(3, 4, 3, 12, 5, 13),
            // Tela principal (painel traseiro)
            Block.createCuboidShape(2, 5, 1, 8, 16, 15),
            // Suporte direito da tela
            Block.createCuboidShape(1, 5, 13.5, 2, 16, 15),
            // Suporte esquerdo da tela
            Block.createCuboidShape(1, 5, 1, 2, 16, 2.5),
            // Barra superior da tela
            Block.createCuboidShape(1, 14.5, 2.5, 2, 16, 13.5),
            // Barra inferior da tela
            Block.createCuboidShape(1, 5, 2.5, 2, 6.5, 13.5),
            // Parte frontal (display/console)
            Block.createCuboidShape(8, 5, 2, 15, 13, 14)
    );

    private static final VoxelShape SHAPE_WEST = VoxelShapes.union(
            // Base (plataforma inferior - 4 pixels de altura)
            Block.createCuboidShape(0, 0, 0, 16, 4, 16),
            // Plataforma superior da base
            Block.createCuboidShape(4, 4, 3, 13, 5, 13),
            // Tela principal (painel traseiro)
            Block.createCuboidShape(8, 5, 1, 14, 16, 15),
            // Suporte direito da tela
            Block.createCuboidShape(14, 5, 1, 15, 16, 2.5),
            // Suporte esquerdo da tela
            Block.createCuboidShape(14, 5, 13.5, 15, 16, 15),
            // Barra superior da tela
            Block.createCuboidShape(14, 14.5, 2.5, 15, 16, 13.5),
            // Barra inferior da tela
            Block.createCuboidShape(14, 5, 2.5, 15, 6.5, 13.5),
            // Parte frontal (display/console)
            Block.createCuboidShape(1, 5, 2, 8, 13, 14)
    );

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return switch (state.get(FACING)) {
            case NORTH -> SHAPE_NORTH;
            case SOUTH -> SHAPE_SOUTH;
            case EAST -> SHAPE_EAST;
            case WEST -> SHAPE_WEST;
            default -> SHAPE_NORTH;
        };
    }

    public ProgrammingBlock(Settings settings) {
        super(settings);
    }

    @Override
    public @Nullable BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing());
    }

    @Override
    protected BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    @Override
    protected BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    protected BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return CODEC;
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit)
    {
        if (!world.isClient()){
            NamedScreenHandlerFactory screenHandlerFactory = ((ProgrammingBlockEntity) world.getBlockEntity(pos));

            if (screenHandlerFactory != null){
                player.openHandledScreen(screenHandlerFactory);
            }
        }
        return ActionResult.SUCCESS;
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new ProgrammingBlockEntity(pos, state);
    }


}
