package us.myles.ViaVersion.api.minecraft.metadata.types;

import us.myles.ViaVersion.api.minecraft.metadata.MetaType;
import us.myles.ViaVersion.api.type.Type;
import us.myles.ViaVersion.api.type.types.version.Types1_13_2;

public enum MetaType1_13_2 implements MetaType {
  Byte(0, Type.BYTE),
  VarInt(1, (Type)Type.VAR_INT),
  Float(2, (Type)Type.FLOAT),
  String(3, Type.STRING),
  Chat(4, Type.COMPONENT),
  OptChat(5, Type.OPTIONAL_COMPONENT),
  Slot(6, Type.FLAT_VAR_INT_ITEM),
  Boolean(7, Type.BOOLEAN),
  Vector3F(8, Type.ROTATION),
  Position(9, Type.POSITION),
  OptPosition(10, Type.OPTIONAL_POSITION),
  Direction(11, (Type)Type.VAR_INT),
  OptUUID(12, Type.OPTIONAL_UUID),
  BlockID(13, (Type)Type.VAR_INT),
  NBTTag(14, Type.NBT),
  PARTICLE(15, Types1_13_2.PARTICLE),
  Discontinued(99, null);
  
  private final int typeID;
  
  private final Type type;
  
  MetaType1_13_2(int typeID, Type type) {
    this.typeID = typeID;
    this.type = type;
  }
  
  public static MetaType1_13_2 byId(int id) {
    return values()[id];
  }
  
  public int getTypeID() {
    return this.typeID;
  }
  
  public Type getType() {
    return this.type;
  }
}


/* Location:              C:\Users\Joona\Downloads\Cupid.jar\\us\myles\ViaVersion\api\minecraft\metadata\types\MetaType1_13_2.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */