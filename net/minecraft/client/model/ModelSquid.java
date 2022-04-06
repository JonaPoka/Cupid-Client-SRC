package net.minecraft.client.model;

import net.minecraft.entity.Entity;

public class ModelSquid extends ModelBase {
  ModelRenderer squidBody;
  
  ModelRenderer[] squidTentacles = new ModelRenderer[8];
  
  public ModelSquid() {
    int i = -16;
    this.squidBody = new ModelRenderer(this, 0, 0);
    this.squidBody.addBox(-6.0F, -8.0F, -6.0F, 12, 16, 12);
    this.squidBody.rotationPointY += (24 + i);
    for (int j = 0; j < this.squidTentacles.length; j++) {
      this.squidTentacles[j] = new ModelRenderer(this, 48, 0);
      double d0 = j * Math.PI * 2.0D / this.squidTentacles.length;
      float f = (float)Math.cos(d0) * 5.0F;
      float f1 = (float)Math.sin(d0) * 5.0F;
      this.squidTentacles[j].addBox(-1.0F, 0.0F, -1.0F, 2, 18, 2);
      (this.squidTentacles[j]).rotationPointX = f;
      (this.squidTentacles[j]).rotationPointZ = f1;
      (this.squidTentacles[j]).rotationPointY = (31 + i);
      d0 = j * Math.PI * -2.0D / this.squidTentacles.length + 1.5707963267948966D;
      (this.squidTentacles[j]).rotateAngleY = (float)d0;
    } 
  }
  
  public void setRotationAngles(float p_78087_1_, float p_78087_2_, float p_78087_3_, float p_78087_4_, float p_78087_5_, float p_78087_6_, Entity entityIn) {
    for (ModelRenderer modelrenderer : this.squidTentacles)
      modelrenderer.rotateAngleX = p_78087_3_; 
  }
  
  public void render(Entity entityIn, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_, float p_78088_6_, float scale) {
    setRotationAngles(p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, scale, entityIn);
    this.squidBody.render(scale);
    for (int i = 0; i < this.squidTentacles.length; i++)
      this.squidTentacles[i].render(scale); 
  }
}


/* Location:              C:\Users\Joona\Downloads\Cupid.jar!\net\minecraft\client\model\ModelSquid.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */