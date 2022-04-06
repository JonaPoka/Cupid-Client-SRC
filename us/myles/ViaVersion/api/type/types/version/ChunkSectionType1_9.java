package us.myles.ViaVersion.api.type.types.version;

import io.netty.buffer.ByteBuf;
import us.myles.ViaVersion.api.minecraft.chunks.ChunkSection;
import us.myles.ViaVersion.api.type.Type;
import us.myles.ViaVersion.util.CompactArrayUtil;

public class ChunkSectionType1_9 extends Type<ChunkSection> {
  private static final int GLOBAL_PALETTE = 13;
  
  public ChunkSectionType1_9() {
    super("Chunk Section Type", ChunkSection.class);
  }
  
  public ChunkSection read(ByteBuf buffer) throws Exception {
    int bitsPerBlock = buffer.readUnsignedByte();
    int originalBitsPerBlock = bitsPerBlock;
    if (bitsPerBlock == 0)
      bitsPerBlock = 13; 
    if (bitsPerBlock < 4)
      bitsPerBlock = 4; 
    if (bitsPerBlock > 8)
      bitsPerBlock = 13; 
    int paletteLength = Type.VAR_INT.readPrimitive(buffer);
    ChunkSection chunkSection = (bitsPerBlock != 13) ? new ChunkSection(paletteLength) : new ChunkSection();
    for (int i = 0; i < paletteLength; i++) {
      if (bitsPerBlock != 13) {
        chunkSection.addPaletteEntry(Type.VAR_INT.readPrimitive(buffer));
      } else {
        Type.VAR_INT.readPrimitive(buffer);
      } 
    } 
    long[] blockData = new long[Type.VAR_INT.readPrimitive(buffer)];
    if (blockData.length > 0) {
      int expectedLength = (int)Math.ceil((4096 * bitsPerBlock) / 64.0D);
      if (blockData.length != expectedLength)
        throw new IllegalStateException("Block data length (" + blockData.length + ") does not match expected length (" + expectedLength + ")! bitsPerBlock=" + bitsPerBlock + ", originalBitsPerBlock=" + originalBitsPerBlock); 
      for (int j = 0; j < blockData.length; j++)
        blockData[j] = buffer.readLong(); 
      CompactArrayUtil.iterateCompactArray(bitsPerBlock, 4096, blockData, (bitsPerBlock == 13) ? chunkSection::setFlatBlock : chunkSection::setPaletteIndex);
    } 
    return chunkSection;
  }
  
  public void write(ByteBuf buffer, ChunkSection chunkSection) throws Exception {
    int bitsPerBlock = 4;
    while (chunkSection.getPaletteSize() > 1 << bitsPerBlock)
      bitsPerBlock++; 
    if (bitsPerBlock > 8)
      bitsPerBlock = 13; 
    long maxEntryValue = (1L << bitsPerBlock) - 1L;
    buffer.writeByte(bitsPerBlock);
    if (bitsPerBlock != 13) {
      Type.VAR_INT.writePrimitive(buffer, chunkSection.getPaletteSize());
      for (int i = 0; i < chunkSection.getPaletteSize(); i++)
        Type.VAR_INT.writePrimitive(buffer, chunkSection.getPaletteEntry(i)); 
    } else {
      Type.VAR_INT.writePrimitive(buffer, 0);
    } 
    long[] data = CompactArrayUtil.createCompactArray(bitsPerBlock, 4096, (bitsPerBlock == 13) ? chunkSection::getFlatBlock : chunkSection::getPaletteIndex);
    Type.VAR_INT.writePrimitive(buffer, data.length);
    for (long l : data)
      buffer.writeLong(l); 
  }
}


/* Location:              C:\Users\Joona\Downloads\Cupid.jar\\us\myles\ViaVersion\api\type\types\version\ChunkSectionType1_9.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */