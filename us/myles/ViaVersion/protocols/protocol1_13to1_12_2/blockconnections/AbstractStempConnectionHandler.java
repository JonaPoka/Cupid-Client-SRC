package us.myles.ViaVersion.protocols.protocol1_13to1_12_2.blockconnections;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import us.myles.ViaVersion.api.data.UserConnection;
import us.myles.ViaVersion.api.minecraft.BlockFace;
import us.myles.ViaVersion.api.minecraft.Position;

public abstract class AbstractStempConnectionHandler extends ConnectionHandler {
  private static final BlockFace[] BLOCK_FACES = new BlockFace[] { BlockFace.EAST, BlockFace.NORTH, BlockFace.SOUTH, BlockFace.WEST };
  
  private final int baseStateId;
  
  private final Set<Integer> blockId = new HashSet<>();
  
  private final Map<BlockFace, Integer> stemps = new HashMap<>();
  
  protected AbstractStempConnectionHandler(String baseStateId) {
    this.baseStateId = ConnectionData.getId(baseStateId);
  }
  
  public ConnectionData.ConnectorInitAction getInitAction(String blockId, String toKey) {
    AbstractStempConnectionHandler handler = this;
    return blockData -> {
        if (blockData.getSavedBlockStateId() == this.baseStateId || blockId.equals(blockData.getMinecraftKey())) {
          if (blockData.getSavedBlockStateId() != this.baseStateId)
            handler.blockId.add(Integer.valueOf(blockData.getSavedBlockStateId())); 
          ConnectionData.connectionHandlerMap.put(blockData.getSavedBlockStateId(), handler);
        } 
        if (blockData.getMinecraftKey().equals(toKey)) {
          String facing = blockData.getValue("facing").toUpperCase(Locale.ROOT);
          this.stemps.put(BlockFace.valueOf(facing), Integer.valueOf(blockData.getSavedBlockStateId()));
        } 
      };
  }
  
  public int connect(UserConnection user, Position position, int blockState) {
    if (blockState != this.baseStateId)
      return blockState; 
    for (BlockFace blockFace : BLOCK_FACES) {
      if (this.blockId.contains(Integer.valueOf(getBlockData(user, position.getRelative(blockFace)))))
        return ((Integer)this.stemps.get(blockFace)).intValue(); 
    } 
    return this.baseStateId;
  }
}


/* Location:              C:\Users\Joona\Downloads\Cupid.jar\\us\myles\ViaVersion\protocols\protocol1_13to1_12_2\blockconnections\AbstractStempConnectionHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */