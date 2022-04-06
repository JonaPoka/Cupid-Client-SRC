package net.minecraft.block;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public abstract class BlockContainer extends Block implements ITileEntityProvider {
  protected BlockContainer(Material materialIn) {
    this(materialIn, materialIn.getMaterialMapColor());
  }
  
  protected BlockContainer(Material p_i46402_1_, MapColor p_i46402_2_) {
    super(p_i46402_1_, p_i46402_2_);
    this.isBlockContainer = true;
  }
  
  protected boolean func_181086_a(World p_181086_1_, BlockPos p_181086_2_, EnumFacing p_181086_3_) {
    return (p_181086_1_.getBlockState(p_181086_2_.offset(p_181086_3_)).getBlock().getMaterial() == Material.cactus);
  }
  
  protected boolean func_181087_e(World p_181087_1_, BlockPos p_181087_2_) {
    return (func_181086_a(p_181087_1_, p_181087_2_, EnumFacing.NORTH) || func_181086_a(p_181087_1_, p_181087_2_, EnumFacing.SOUTH) || func_181086_a(p_181087_1_, p_181087_2_, EnumFacing.WEST) || func_181086_a(p_181087_1_, p_181087_2_, EnumFacing.EAST));
  }
  
  public int getRenderType() {
    return -1;
  }
  
  public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
    super.breakBlock(worldIn, pos, state);
    worldIn.removeTileEntity(pos);
  }
  
  public boolean onBlockEventReceived(World worldIn, BlockPos pos, IBlockState state, int eventID, int eventParam) {
    super.onBlockEventReceived(worldIn, pos, state, eventID, eventParam);
    TileEntity tileentity = worldIn.getTileEntity(pos);
    return (tileentity == null) ? false : tileentity.receiveClientEvent(eventID, eventParam);
  }
}


/* Location:              C:\Users\Joona\Downloads\Cupid.jar!\net\minecraft\block\BlockContainer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */