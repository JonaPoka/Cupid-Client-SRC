package us.myles.ViaVersion.api.type.types.minecraft;

import io.netty.buffer.ByteBuf;
import us.myles.ViaVersion.api.minecraft.item.Item;
import us.myles.ViaVersion.api.type.Type;
import us.myles.viaversion.libs.opennbt.tag.builtin.CompoundTag;

public class ItemType extends BaseItemType {
  public ItemType() {
    super("Item");
  }
  
  public Item read(ByteBuf buffer) throws Exception {
    short id = buffer.readShort();
    if (id < 0)
      return null; 
    Item item = new Item();
    item.setIdentifier(id);
    item.setAmount(buffer.readByte());
    item.setData(buffer.readShort());
    item.setTag((CompoundTag)Type.NBT.read(buffer));
    return item;
  }
  
  public void write(ByteBuf buffer, Item object) throws Exception {
    if (object == null) {
      buffer.writeShort(-1);
    } else {
      buffer.writeShort(object.getIdentifier());
      buffer.writeByte(object.getAmount());
      buffer.writeShort(object.getData());
      Type.NBT.write(buffer, object.getTag());
    } 
  }
}


/* Location:              C:\Users\Joona\Downloads\Cupid.jar\\us\myles\ViaVersion\api\type\types\minecraft\ItemType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */