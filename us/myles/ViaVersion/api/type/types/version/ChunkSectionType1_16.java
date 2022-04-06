package us.myles.ViaVersion.api.type.types.version;

import io.netty.buffer.ByteBuf;
import us.myles.ViaVersion.api.minecraft.chunks.ChunkSection;
import us.myles.ViaVersion.api.type.Type;
import us.myles.ViaVersion.util.CompactArrayUtil;

public class ChunkSectionType1_16 extends Type<ChunkSection> {
  private static final int GLOBAL_PALETTE = 15;
  
  public ChunkSectionType1_16() {
    super("Chunk Section Type", ChunkSection.class);
  }
  
  public ChunkSection read(ByteBuf buffer) throws Exception {
    ChunkSection chunkSection;
    int bitsPerBlock = buffer.readUnsignedByte();
    int originalBitsPerBlock = bitsPerBlock;
    if (bitsPerBlock == 0 || bitsPerBlock > 8)
      bitsPerBlock = 15; 
    if (bitsPerBlock != 15) {
      int paletteLength = Type.VAR_INT.readPrimitive(buffer);
      chunkSection = new ChunkSection(paletteLength);
      for (int i = 0; i < paletteLength; i++)
        chunkSection.addPaletteEntry(Type.VAR_INT.readPrimitive(buffer)); 
    } else {
      chunkSection = new ChunkSection();
    } 
    long[] blockData = new long[Type.VAR_INT.readPrimitive(buffer)];
    if (blockData.length > 0) {
      char valuesPerLong = (char)(64 / bitsPerBlock);
      int expectedLength = (4096 + valuesPerLong - 1) / valuesPerLong;
      if (blockData.length != expectedLength)
        throw new IllegalStateException("Block data length (" + blockData.length + ") does not match expected length (" + expectedLength + ")! bitsPerBlock=" + bitsPerBlock + ", originalBitsPerBlock=" + originalBitsPerBlock); 
      for (int i = 0; i < blockData.length; i++)
        blockData[i] = buffer.readLong(); 
      CompactArrayUtil.iterateCompactArrayWithPadding(bitsPerBlock, 4096, blockData, (bitsPerBlock == 15) ? chunkSection::setFlatBlock : chunkSection::setPaletteIndex);
    } 
    return chunkSection;
  }
  
  public void write(ByteBuf buffer, ChunkSection chunkSection) throws Exception {
    int bitsPerBlock = 4;
    while (chunkSection.getPaletteSize() > 1 << bitsPerBlock)
      bitsPerBlock++; 
    if (bitsPerBlock > 8)
      bitsPerBlock = 15; 
    buffer.writeByte(bitsPerBlock);
    if (bitsPerBlock != 15) {
      Type.VAR_INT.writePrimitive(buffer, chunkSection.getPaletteSize());
      for (int i = 0; i < chunkSection.getPaletteSize(); i++)
        Type.VAR_INT.writePrimitive(buffer, chunkSection.getPaletteEntry(i)); 
    } 
    long[] data = CompactArrayUtil.createCompactArrayWithPadding(bitsPerBlock, 4096, (bitsPerBlock == 15) ? chunkSection::getFlatBlock : chunkSection::getPaletteIndex);
    Type.VAR_INT.writePrimitive(buffer, data.length);
    for (long l : data)
      buffer.writeLong(l); 
  }
}


/* Location:              C:\Users\Joona\Downloads\Cupid.jar\\us\myles\ViaVersion\api\type\types\version\ChunkSectionType1_16.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */