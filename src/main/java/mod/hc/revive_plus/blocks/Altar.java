package mod.hc.revive_plus.blocks;

import mod.hc.revive_plus.KnockablePlayer;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class Altar extends BlockWithEntity {

    /** From SlabBlock */
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 8.0, 16.0);
    }

    @Override
    public boolean canReplace(BlockState state, ItemPlacementContext context) {
        return false;
    }

    public Altar(Settings settings) {
        super(settings);
    }

    public AltarBlockEntity getEntity(World world, BlockPos pos) {
        BlockEntity entity = world.getBlockEntity(pos);
        if (entity instanceof AltarBlockEntity altarEntity) {
            return altarEntity;
        }
        return null;
    }

    @Override
    public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
        super.onSteppedOn(world, pos, state, entity);
        AltarBlockEntity altarEntity = getEntity(world, pos);
        boolean setKnocked = false;
        if (entity instanceof KnockablePlayer knockedPlayer && entity instanceof PlayerEntity player) {
            if (knockedPlayer.isKnocked()) {
                altarEntity.setKnocked(player);
                setKnocked = true;
            }
        }
        if(!setKnocked && entity instanceof LivingEntity player){
            altarEntity.setSacrifice(player);
        }
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new AltarBlockEntity(pos, state);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, ModBlocks.REVIVE_ALTAR_ENTITY, AltarBlockEntity::tick);
    }
}
