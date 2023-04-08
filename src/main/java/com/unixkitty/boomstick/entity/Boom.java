package com.unixkitty.boomstick.entity;

import com.unixkitty.boomstick.init.ModRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Optional;

public class Boom extends PrimedTnt
{
    private static final EntityDataAccessor<Optional<BlockState>> DATA_BLOCKSTATE_ID = SynchedEntityData.defineId(Boom.class, EntityDataSerializers.BLOCK_STATE);

    public Boom(EntityType<? extends PrimedTnt> entityType, Level level)
    {
        super(entityType, level);
    }

    public Boom(Level level, BlockPos pos)
    {
        this(ModRegistry.BOOM.get(), level);

        double x = (double) pos.getX() + 0.5D;
        double y = pos.getY();
        double z = (double) pos.getZ() + 0.5D;

        this.setPos(x, y, z);
        double d0 = level.random.nextDouble() * (double) ((float) Math.PI * 2F);
        this.setDeltaMovement(-Math.sin(d0) * 0.02D, 0.2F, -Math.cos(d0) * 0.02D);
        this.setFuse(80);
        this.setDataBlockstate(level.getBlockState(pos));
        this.xo = x;
        this.yo = y;
        this.zo = z;
    }

    @Override
    protected void defineSynchedData()
    {
        super.defineSynchedData();
        this.entityData.define(DATA_BLOCKSTATE_ID, Optional.of(Blocks.TNT.defaultBlockState()));
    }

    public void setDataBlockstate(BlockState blockstate)
    {
        this.entityData.set(DATA_BLOCKSTATE_ID, Optional.of(blockstate));
    }

    public Optional<BlockState> getDataBlockState()
    {
        return this.entityData.get(DATA_BLOCKSTATE_ID);
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag)
    {
        super.addAdditionalSaveData(tag);
        tag.put("ExplodedBlock", NbtUtils.writeBlockState(this.getDataBlockState().orElse(Blocks.TNT.defaultBlockState())));
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag tag)
    {
        super.readAdditionalSaveData(tag);
        this.setDataBlockstate(NbtUtils.readBlockState(tag.getCompound("ExplodedBlock")));
    }
}
