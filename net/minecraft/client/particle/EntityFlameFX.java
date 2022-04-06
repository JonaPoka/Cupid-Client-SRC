package net.minecraft.client.particle;

import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityFlameFX extends EntityFX {
  private float flameScale;
  
  protected EntityFlameFX(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn) {
    super(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
    this.motionX = this.motionX * 0.009999999776482582D + xSpeedIn;
    this.motionY = this.motionY * 0.009999999776482582D + ySpeedIn;
    this.motionZ = this.motionZ * 0.009999999776482582D + zSpeedIn;
    this.posX += ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.05F);
    this.posY += ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.05F);
    this.posZ += ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.05F);
    this.flameScale = this.particleScale;
    this.particleRed = this.particleGreen = this.particleBlue = 1.0F;
    this.particleMaxAge = (int)(8.0D / (Math.random() * 0.8D + 0.2D)) + 4;
    this.noClip = true;
    setParticleTextureIndex(48);
  }
  
  public void renderParticle(WorldRenderer worldRendererIn, Entity entityIn, float partialTicks, float p_180434_4_, float p_180434_5_, float p_180434_6_, float p_180434_7_, float p_180434_8_) {
    float f = (this.particleAge + partialTicks) / this.particleMaxAge;
    this.particleScale = this.flameScale * (1.0F - f * f * 0.5F);
    super.renderParticle(worldRendererIn, entityIn, partialTicks, p_180434_4_, p_180434_5_, p_180434_6_, p_180434_7_, p_180434_8_);
  }
  
  public int getBrightnessForRender(float partialTicks) {
    float f = (this.particleAge + partialTicks) / this.particleMaxAge;
    f = MathHelper.clamp_float(f, 0.0F, 1.0F);
    int i = super.getBrightnessForRender(partialTicks);
    int j = i & 0xFF;
    int k = i >> 16 & 0xFF;
    j += (int)(f * 15.0F * 16.0F);
    if (j > 240)
      j = 240; 
    return j | k << 16;
  }
  
  public float getBrightness(float partialTicks) {
    float f = (this.particleAge + partialTicks) / this.particleMaxAge;
    f = MathHelper.clamp_float(f, 0.0F, 1.0F);
    float f1 = super.getBrightness(partialTicks);
    return f1 * f + 1.0F - f;
  }
  
  public void onUpdate() {
    this.prevPosX = this.posX;
    this.prevPosY = this.posY;
    this.prevPosZ = this.posZ;
    if (this.particleAge++ >= this.particleMaxAge)
      setDead(); 
    moveEntity(this.motionX, this.motionY, this.motionZ);
    this.motionX *= 0.9599999785423279D;
    this.motionY *= 0.9599999785423279D;
    this.motionZ *= 0.9599999785423279D;
    if (this.onGround) {
      this.motionX *= 0.699999988079071D;
      this.motionZ *= 0.699999988079071D;
    } 
  }
  
  public static class Factory implements IParticleFactory {
    public EntityFX getEntityFX(int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int... p_178902_15_) {
      return new EntityFlameFX(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
    }
  }
}


/* Location:              C:\Users\Joona\Downloads\Cupid.jar!\net\minecraft\client\particle\EntityFlameFX.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */