package us.myles.ViaVersion.protocols.protocol1_9to1_8.types;

import io.netty.buffer.ByteBuf;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.logging.Level;
import us.myles.ViaVersion.api.Via;
import us.myles.ViaVersion.api.minecraft.chunks.Chunk;
import us.myles.ViaVersion.api.minecraft.chunks.Chunk1_8;
import us.myles.ViaVersion.api.minecraft.chunks.ChunkSection;
import us.myles.ViaVersion.api.type.PartialType;
import us.myles.ViaVersion.api.type.Type;
import us.myles.ViaVersion.api.type.types.minecraft.BaseChunkType;
import us.myles.ViaVersion.api.type.types.version.Types1_8;
import us.myles.ViaVersion.api.type.types.version.Types1_9;
import us.myles.ViaVersion.protocols.protocol1_10to1_9_3.Protocol1_10To1_9_3_4;
import us.myles.ViaVersion.protocols.protocol1_9to1_8.storage.ClientChunks;

public class Chunk1_9to1_8Type extends PartialType<Chunk, ClientChunks> {
  public static final int SECTION_COUNT = 16;
  
  private static final int SECTION_SIZE = 16;
  
  private static final int BIOME_DATA_LENGTH = 256;
  
  public Chunk1_9to1_8Type(ClientChunks chunks) {
    super(chunks, Chunk.class);
  }
  
  private static long toLong(int msw, int lsw) {
    return (msw << 32L) + lsw - -2147483648L;
  }
  
  public Class<? extends Type> getBaseClass() {
    return (Class)BaseChunkType.class;
  }
  
  public Chunk read(ByteBuf input, ClientChunks param) throws Exception {
    boolean replacePistons = (param.getUser().getProtocolInfo().getPipeline().contains(Protocol1_10To1_9_3_4.class) && Via.getConfig().isReplacePistons());
    int replacementId = Via.getConfig().getPistonReplacementId();
    int chunkX = input.readInt();
    int chunkZ = input.readInt();
    long chunkHash = toLong(chunkX, chunkZ);
    boolean fullChunk = (input.readByte() != 0);
    int bitmask = input.readUnsignedShort();
    int dataLength = Type.VAR_INT.readPrimitive(input);
    BitSet usedSections = new BitSet(16);
    ChunkSection[] sections = new ChunkSection[16];
    int[] biomeData = null;
    for (int i = 0; i < 16; i++) {
      if ((bitmask & 1 << i) != 0)
        usedSections.set(i); 
    } 
    int sectionCount = usedSections.cardinality();
    boolean isBulkPacket = param.getBulkChunks().remove(Long.valueOf(chunkHash));
    if (sectionCount == 0 && fullChunk && !isBulkPacket && param.getLoadedChunks().contains(Long.valueOf(chunkHash))) {
      param.getLoadedChunks().remove(Long.valueOf(chunkHash));
      return (Chunk)new Chunk1_8(chunkX, chunkZ);
    } 
    int startIndex = input.readerIndex();
    param.getLoadedChunks().add(Long.valueOf(chunkHash));
    int j;
    for (j = 0; j < 16; j++) {
      if (usedSections.get(j)) {
        ChunkSection section = (ChunkSection)Types1_8.CHUNK_SECTION.read(input);
        sections[j] = section;
        if (replacePistons)
          section.replacePaletteEntry(36, replacementId); 
      } 
    } 
    for (j = 0; j < 16; j++) {
      if (usedSections.get(j))
        sections[j].readBlockLight(input); 
    } 
    int bytesLeft = dataLength - input.readerIndex() - startIndex;
    if (bytesLeft >= 2048)
      for (int k = 0; k < 16; k++) {
        if (usedSections.get(k)) {
          sections[k].readSkyLight(input);
          bytesLeft -= 2048;
        } 
      }  
    if (bytesLeft >= 256) {
      biomeData = new int[256];
      for (int k = 0; k < 256; k++)
        biomeData[k] = input.readByte() & 0xFF; 
      bytesLeft -= 256;
    } 
    if (bytesLeft > 0)
      Via.getPlatform().getLogger().log(Level.WARNING, bytesLeft + " Bytes left after reading chunks! (" + fullChunk + ")"); 
    return (Chunk)new Chunk1_8(chunkX, chunkZ, fullChunk, bitmask, sections, biomeData, new ArrayList());
  }
  
  public void write(ByteBuf output, ClientChunks param, Chunk input) throws Exception {
    if (!(input instanceof Chunk1_8))
      throw new Exception("Incompatible chunk, " + input.getClass()); 
    Chunk1_8 chunk = (Chunk1_8)input;
    output.writeInt(chunk.getX());
    output.writeInt(chunk.getZ());
    if (chunk.isUnloadPacket())
      return; 
    output.writeByte(chunk.isFullChunk() ? 1 : 0);
    Type.VAR_INT.writePrimitive(output, chunk.getBitmask());
    ByteBuf buf = output.alloc().buffer();
    try {
      for (int i = 0; i < 16; i++) {
        ChunkSection section = chunk.getSections()[i];
        if (section != null) {
          Types1_9.CHUNK_SECTION.write(buf, section);
          section.writeBlockLight(buf);
          if (section.hasSkyLight())
            section.writeSkyLight(buf); 
        } 
      } 
      buf.readerIndex(0);
      Type.VAR_INT.writePrimitive(output, buf.readableBytes() + (chunk.hasBiomeData() ? 256 : 0));
      output.writeBytes(buf);
    } finally {
      buf.release();
    } 
    if (chunk.hasBiomeData())
      for (int biome : chunk.getBiomeData())
        output.writeByte((byte)biome);  
  }
}


/* Location:              C:\Users\Joona\Downloads\Cupid.jar\\us\myles\ViaVersion\protocols\protocol1_9to1_8\types\Chunk1_9to1_8Type.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */