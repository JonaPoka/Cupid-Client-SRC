package net.minecraft.block.state.pattern;

import com.google.common.base.Objects;
import com.google.common.base.Predicate;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.LoadingCache;
import net.minecraft.block.state.BlockWorldState;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3i;
import net.minecraft.world.World;

public class BlockPattern {
  private final Predicate<BlockWorldState>[][][] blockMatches;
  
  private final int fingerLength;
  
  private final int thumbLength;
  
  private final int palmLength;
  
  public BlockPattern(Predicate<BlockWorldState>[][][] predicatesIn) {
    this.blockMatches = predicatesIn;
    this.fingerLength = predicatesIn.length;
    if (this.fingerLength > 0) {
      this.thumbLength = (predicatesIn[0]).length;
      if (this.thumbLength > 0) {
        this.palmLength = (predicatesIn[0][0]).length;
      } else {
        this.palmLength = 0;
      } 
    } else {
      this.thumbLength = 0;
      this.palmLength = 0;
    } 
  }
  
  public int getThumbLength() {
    return this.thumbLength;
  }
  
  public int getPalmLength() {
    return this.palmLength;
  }
  
  private PatternHelper checkPatternAt(BlockPos pos, EnumFacing finger, EnumFacing thumb, LoadingCache<BlockPos, BlockWorldState> lcache) {
    for (int i = 0; i < this.palmLength; i++) {
      for (int j = 0; j < this.thumbLength; j++) {
        for (int k = 0; k < this.fingerLength; k++) {
          if (!this.blockMatches[k][j][i].apply(lcache.getUnchecked(translateOffset(pos, finger, thumb, i, j, k))))
            return null; 
        } 
      } 
    } 
    return new PatternHelper(pos, finger, thumb, lcache, this.palmLength, this.thumbLength, this.fingerLength);
  }
  
  public PatternHelper match(World worldIn, BlockPos pos) {
    LoadingCache<BlockPos, BlockWorldState> loadingcache = func_181627_a(worldIn, false);
    int i = Math.max(Math.max(this.palmLength, this.thumbLength), this.fingerLength);
    for (BlockPos blockpos : BlockPos.getAllInBox(pos, pos.add(i - 1, i - 1, i - 1))) {
      for (EnumFacing enumfacing : EnumFacing.values()) {
        for (EnumFacing enumfacing1 : EnumFacing.values()) {
          if (enumfacing1 != enumfacing && enumfacing1 != enumfacing.getOpposite()) {
            PatternHelper blockpattern$patternhelper = checkPatternAt(blockpos, enumfacing, enumfacing1, loadingcache);
            if (blockpattern$patternhelper != null)
              return blockpattern$patternhelper; 
          } 
        } 
      } 
    } 
    return null;
  }
  
  public static LoadingCache<BlockPos, BlockWorldState> func_181627_a(World p_181627_0_, boolean p_181627_1_) {
    return CacheBuilder.newBuilder().build(new CacheLoader(p_181627_0_, p_181627_1_));
  }
  
  protected static BlockPos translateOffset(BlockPos pos, EnumFacing finger, EnumFacing thumb, int palmOffset, int thumbOffset, int fingerOffset) {
    if (finger != thumb && finger != thumb.getOpposite()) {
      Vec3i vec3i = new Vec3i(finger.getFrontOffsetX(), finger.getFrontOffsetY(), finger.getFrontOffsetZ());
      Vec3i vec3i1 = new Vec3i(thumb.getFrontOffsetX(), thumb.getFrontOffsetY(), thumb.getFrontOffsetZ());
      Vec3i vec3i2 = vec3i.crossProduct(vec3i1);
      return pos.add(vec3i1.getX() * -thumbOffset + vec3i2.getX() * palmOffset + vec3i.getX() * fingerOffset, vec3i1.getY() * -thumbOffset + vec3i2.getY() * palmOffset + vec3i.getY() * fingerOffset, vec3i1.getZ() * -thumbOffset + vec3i2.getZ() * palmOffset + vec3i.getZ() * fingerOffset);
    } 
    throw new IllegalArgumentException("Invalid forwards & up combination");
  }
  
  static class CacheLoader extends com.google.common.cache.CacheLoader<BlockPos, BlockWorldState> {
    private final World world;
    
    private final boolean field_181626_b;
    
    public CacheLoader(World p_i46460_1_, boolean p_i46460_2_) {
      this.world = p_i46460_1_;
      this.field_181626_b = p_i46460_2_;
    }
    
    public BlockWorldState load(BlockPos p_load_1_) throws Exception {
      return new BlockWorldState(this.world, p_load_1_, this.field_181626_b);
    }
  }
  
  public static class PatternHelper {
    private final BlockPos pos;
    
    private final EnumFacing finger;
    
    private final EnumFacing thumb;
    
    private final LoadingCache<BlockPos, BlockWorldState> lcache;
    
    private final int field_181120_e;
    
    private final int field_181121_f;
    
    private final int field_181122_g;
    
    public PatternHelper(BlockPos p_i46378_1_, EnumFacing p_i46378_2_, EnumFacing p_i46378_3_, LoadingCache<BlockPos, BlockWorldState> p_i46378_4_, int p_i46378_5_, int p_i46378_6_, int p_i46378_7_) {
      this.pos = p_i46378_1_;
      this.finger = p_i46378_2_;
      this.thumb = p_i46378_3_;
      this.lcache = p_i46378_4_;
      this.field_181120_e = p_i46378_5_;
      this.field_181121_f = p_i46378_6_;
      this.field_181122_g = p_i46378_7_;
    }
    
    public BlockPos func_181117_a() {
      return this.pos;
    }
    
    public EnumFacing getFinger() {
      return this.finger;
    }
    
    public EnumFacing getThumb() {
      return this.thumb;
    }
    
    public int func_181118_d() {
      return this.field_181120_e;
    }
    
    public int func_181119_e() {
      return this.field_181121_f;
    }
    
    public BlockWorldState translateOffset(int palmOffset, int thumbOffset, int fingerOffset) {
      return (BlockWorldState)this.lcache.getUnchecked(BlockPattern.translateOffset(this.pos, getFinger(), getThumb(), palmOffset, thumbOffset, fingerOffset));
    }
    
    public String toString() {
      return Objects.toStringHelper(this).add("up", this.thumb).add("forwards", this.finger).add("frontTopLeft", this.pos).toString();
    }
  }
}


/* Location:              C:\Users\Joona\Downloads\Cupid.jar!\net\minecraft\block\state\pattern\BlockPattern.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */