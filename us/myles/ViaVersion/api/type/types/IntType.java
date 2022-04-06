package us.myles.ViaVersion.api.type.types;

import io.netty.buffer.ByteBuf;
import us.myles.ViaVersion.api.type.Type;
import us.myles.ViaVersion.api.type.TypeConverter;

public class IntType extends Type<Integer> implements TypeConverter<Integer> {
  public IntType() {
    super(Integer.class);
  }
  
  public Integer read(ByteBuf buffer) {
    return Integer.valueOf(buffer.readInt());
  }
  
  public void write(ByteBuf buffer, Integer object) {
    buffer.writeInt(object.intValue());
  }
  
  public Integer from(Object o) {
    if (o instanceof Number)
      return Integer.valueOf(((Number)o).intValue()); 
    if (o instanceof Boolean)
      return Integer.valueOf(((Boolean)o).booleanValue() ? 1 : 0); 
    return (Integer)o;
  }
}


/* Location:              C:\Users\Joona\Downloads\Cupid.jar\\us\myles\ViaVersion\api\type\types\IntType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */