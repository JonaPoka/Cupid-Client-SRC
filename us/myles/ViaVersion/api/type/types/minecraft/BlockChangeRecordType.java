package us.myles.ViaVersion.api.type.types.minecraft;

import io.netty.buffer.ByteBuf;
import us.myles.ViaVersion.api.minecraft.BlockChangeRecord;
import us.myles.ViaVersion.api.minecraft.BlockChangeRecord1_8;
import us.myles.ViaVersion.api.type.Type;

public class BlockChangeRecordType extends Type<BlockChangeRecord> {
  public BlockChangeRecordType() {
    super(BlockChangeRecord.class);
  }
  
  public BlockChangeRecord read(ByteBuf buffer) throws Exception {
    short position = Type.SHORT.readPrimitive(buffer);
    int blockId = Type.VAR_INT.readPrimitive(buffer);
    return (BlockChangeRecord)new BlockChangeRecord1_8(position >> 12 & 0xF, position & 0xFF, position >> 8 & 0xF, blockId);
  }
  
  public void write(ByteBuf buffer, BlockChangeRecord object) throws Exception {
    Type.SHORT.writePrimitive(buffer, (short)(object.getSectionX() << 12 | object.getSectionZ() << 8 | object.getY()));
    Type.VAR_INT.writePrimitive(buffer, object.getBlockId());
  }
}


/* Location:              C:\Users\Joona\Downloads\Cupid.jar\\us\myles\ViaVersion\api\type\types\minecraft\BlockChangeRecordType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */