package com.unixkitty.boomstick.block;

import com.unixkitty.boomstick.entity.Boom;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LeverBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;

import javax.annotation.Nullable;

public class BoomStickBlock extends LeverBlock
{

    public BoomStickBlock()
    {
        super(BlockBehaviour.Properties.copy(Blocks.LEVER));
    }

    public BlockState pull(BlockState blockState, Level level, BlockPos pos)
    {
        blockState = blockState.cycle(POWERED);
        level.setBlock(pos, blockState, 3);
        this.commenceShenanigans(blockState, level, pos);
        return blockState;
    }

    private void commenceShenanigans(BlockState blockState, Level level, BlockPos pos)
    {
        level.updateNeighborsAt(pos, this);

        BlockPos affectedPos = pos.relative(getConnectedDirection(blockState).getOpposite());

        //Lever will only trigger blocks that have have half of Obsidian's explosion resistance
        if (level.getBlockState(affectedPos).getBlock().getExplosionResistance() < Blocks.OBSIDIAN.getExplosionResistance() / 2)
        {
            explode(level, affectedPos, null);

            level.setBlock(affectedPos, Blocks.AIR.defaultBlockState(), 11);
        }

        level.updateNeighborsAt(affectedPos, this);
    }

    private static void explode(Level level, BlockPos pos, @Nullable LivingEntity livingEntity)
    {
        if (!level.isClientSide)
        {
            Boom suspiciouslyExplosiveEntity = new Boom(level, pos);

            level.addFreshEntity(suspiciouslyExplosiveEntity);
            level.playSound(null, suspiciouslyExplosiveEntity.getX(), suspiciouslyExplosiveEntity.getY(), suspiciouslyExplosiveEntity.getZ(), SoundEvents.TNT_PRIMED, SoundSource.BLOCKS, 1.0F, 1.0F);
            level.gameEvent(livingEntity, GameEvent.PRIME_FUSE, pos);
        }
    }

    //---------------------------------------
    // Disable lever redstone functionality
    //---------------------------------------

    @Override
    public int getSignal(BlockState pBlockState, BlockGetter pBlockAccess, BlockPos pPos, Direction pSide)
    {
        return 0;
    }

    @Override
    public int getDirectSignal(BlockState pBlockState, BlockGetter pBlockAccess, BlockPos pPos, Direction pSide)
    {
        return 0;
    }

    @Override
    public boolean isSignalSource(BlockState pState)
    {
        return false;
    }
}
