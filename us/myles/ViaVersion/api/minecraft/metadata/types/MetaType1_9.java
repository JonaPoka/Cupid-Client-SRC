package us.myles.ViaVersion.api.minecraft.metadata.types;

import us.myles.ViaVersion.api.minecraft.metadata.MetaType;
import us.myles.ViaVersion.api.type.Type;

public enum MetaType1_9 implements MetaType {
  Byte(0, Type.BYTE),
  VarInt(1, (Type)Type.VAR_INT),
  Float(2, (Type)Type.FLOAT),
  String(3, Type.STRING),
  Chat(4, Type.COMPONENT),
  Slot(5, Type.ITEM),
  Boolean(6, Type.BOOLEAN),
  Vector3F(7, Type.ROTATION),
  Position(8, Type.POSITION),
  OptPosition(9, Type.OPTIONAL_POSITION),
  Direction(10, (Type)Type.VAR_INT),
  OptUUID(11, Type.OPTIONAL_UUID),
  BlockID(12, (Type)Type.VAR_INT),
  Discontinued(99, null);
  
  private final int typeID;
  
  private final Type type;
  
  MetaType1_9(int typeID, Type type) {
    this.typeID = typeID;
    this.type = type;
  }
  
  public static MetaType1_9 byId(int id) {
    return values()[id];
  }
  
  public int getTypeID() {
    return this.typeID;
  }
  
  public Type getType() {
    return this.type;
  }
}


/* Location:              C:\Users\Joona\Downloads\Cupid.jar\\us\myles\ViaVersion\api\minecraft\metadata\types\MetaType1_9.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */