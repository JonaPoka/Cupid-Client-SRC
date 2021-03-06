package us.myles.ViaVersion.api.minecraft;

public class Position {
  private final int x;
  
  private final short y;
  
  private final int z;
  
  public Position(int x, short y, int z) {
    this.x = x;
    this.y = y;
    this.z = z;
  }
  
  public Position(Position toCopy) {
    this(toCopy.getX(), toCopy.getY(), toCopy.getZ());
  }
  
  public Position getRelative(BlockFace face) {
    return new Position(this.x + face.getModX(), (short)(this.y + face.getModY()), this.z + face.getModZ());
  }
  
  public int getX() {
    return this.x;
  }
  
  public short getY() {
    return this.y;
  }
  
  public int getZ() {
    return this.z;
  }
  
  public boolean equals(Object o) {
    if (this == o)
      return true; 
    if (o == null || getClass() != o.getClass())
      return false; 
    Position position = (Position)o;
    if (this.x != position.x)
      return false; 
    if (this.y != position.y)
      return false; 
    return (this.z == position.z);
  }
  
  public int hashCode() {
    int result = this.x;
    result = 31 * result + this.y;
    result = 31 * result + this.z;
    return result;
  }
  
  public String toString() {
    return "Position{x=" + this.x + ", y=" + this.y + ", z=" + this.z + '}';
  }
}


/* Location:              C:\Users\Joona\Downloads\Cupid.jar\\us\myles\ViaVersion\api\minecraft\Position.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */