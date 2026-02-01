package com.michionlion.pathslab.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class DirtPathSlabBlock extends SlabBlock {
    private static final VoxelShape BOTTOM_SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 7.0D, 16.0D);
    private static final VoxelShape TOP_SHAPE = Block.box(0.0D, 8.0D, 0.0D, 16.0D, 15.0D, 16.0D);
    private static final VoxelShape DOUBLE_SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 15.0D, 16.0D);

    public DirtPathSlabBlock(Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getShape(
            BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return shapeFor(state);
    }

    @Override
    public VoxelShape getCollisionShape(
            BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return shapeFor(state);
    }

    @Override
    public VoxelShape getOcclusionShape(BlockState state) {
        return shapeFor(state);
    }

    @Override
    public VoxelShape getVisualShape(
            BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return shapeFor(state);
    }

    @Override
    public VoxelShape getInteractionShape(BlockState state, BlockGetter level, BlockPos pos) {
        return shapeFor(state);
    }

    private static VoxelShape shapeFor(BlockState state) {
        SlabType type = state.getValue(SlabBlock.TYPE);
        if (type == SlabType.TOP) {
            return TOP_SHAPE;
        }
        if (type == SlabType.DOUBLE) {
            return DOUBLE_SHAPE;
        }
        return BOTTOM_SHAPE;
    }
}
