package us.myles.ViaVersion.protocols.protocol1_13to1_12_2.providers.blockentities;

import us.myles.ViaVersion.api.data.UserConnection;
import us.myles.ViaVersion.protocols.protocol1_13to1_12_2.data.EntityNameRewriter;
import us.myles.ViaVersion.protocols.protocol1_13to1_12_2.providers.BlockEntityProvider;
import us.myles.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import us.myles.viaversion.libs.opennbt.tag.builtin.StringTag;

public class SpawnerHandler implements BlockEntityProvider.BlockEntityHandler {
  public int transform(UserConnection user, CompoundTag tag) {
    if (tag.contains("SpawnData") && tag.get("SpawnData") instanceof CompoundTag) {
      CompoundTag data = (CompoundTag)tag.get("SpawnData");
      if (data.contains("id") && data.get("id") instanceof StringTag) {
        StringTag s = (StringTag)data.get("id");
        s.setValue(EntityNameRewriter.rewrite(s.getValue()));
      } 
    } 
    return -1;
  }
}


/* Location:              C:\Users\Joona\Downloads\Cupid.jar\\us\myles\ViaVersion\protocols\protocol1_13to1_12_2\providers\blockentities\SpawnerHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */