package us.myles.ViaVersion.api.type.types.minecraft;

import io.netty.buffer.ByteBuf;
import us.myles.ViaVersion.api.minecraft.Position;
import us.myles.ViaVersion.api.type.Type;

public class OptPosition1_14Type extends Type<Position> {
  public OptPosition1_14Type() {
    super(Position.class);
  }
  
  public Position read(ByteBuf buffer) throws Exception {
    boolean present = buffer.readBoolean();
    if (!present)
      return null; 
    return (Position)Type.POSITION1_14.read(buffer);
  }
  
  public void write(ByteBuf buffer, Position object) throws Exception {
    buffer.writeBoolean((object != null));
    if (object != null)
      Type.POSITION1_14.write(buffer, object); 
  }
}


/* Location:              C:\Users\Joona\Downloads\Cupid.jar\\us\myles\ViaVersion\api\type\types\minecraft\OptPosition1_14Type.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */