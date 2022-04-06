package us.myles.ViaVersion.protocols.protocol1_13to1_12_2.blockconnections;

import java.util.HashMap;
import java.util.Map;
import us.myles.ViaVersion.api.minecraft.BlockFace;

public class BlockData {
  private final Map<String, boolean[]> connectData = (Map)new HashMap<>();
  
  public void put(String key, boolean[] booleans) {
    this.connectData.put(key, booleans);
  }
  
  public boolean connectsTo(String blockConnection, BlockFace face, boolean pre1_12AbstractFence) {
    boolean[] booleans = null;
    if (pre1_12AbstractFence)
      booleans = this.connectData.get("allFalseIfStairPre1_12"); 
    if (booleans == null)
      booleans = this.connectData.get(blockConnection); 
    return (booleans != null && booleans[face.ordinal()]);
  }
}


/* Location:              C:\Users\Joona\Downloads\Cupid.jar\\us\myles\ViaVersion\protocols\protocol1_13to1_12_2\blockconnections\BlockData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */