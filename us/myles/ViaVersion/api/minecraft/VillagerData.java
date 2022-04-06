package us.myles.ViaVersion.api.minecraft;

public class VillagerData {
  private final int type;
  
  private final int profession;
  
  private final int level;
  
  public VillagerData(int type, int profession, int level) {
    this.type = type;
    this.profession = profession;
    this.level = level;
  }
  
  public int getType() {
    return this.type;
  }
  
  public int getProfession() {
    return this.profession;
  }
  
  public int getLevel() {
    return this.level;
  }
}


/* Location:              C:\Users\Joona\Downloads\Cupid.jar\\us\myles\ViaVersion\api\minecraft\VillagerData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */