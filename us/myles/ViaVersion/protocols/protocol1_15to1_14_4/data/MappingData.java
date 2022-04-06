package us.myles.ViaVersion.protocols.protocol1_15to1_14_4.data;

import org.jetbrains.annotations.Nullable;
import us.myles.ViaVersion.api.data.MappingData;
import us.myles.ViaVersion.api.data.Mappings;
import us.myles.viaversion.libs.gson.JsonObject;

public class MappingData extends MappingData {
  public MappingData() {
    super("1.14", "1.15", true);
  }
  
  protected Mappings loadFromArray(JsonObject oldMappings, JsonObject newMappings, @Nullable JsonObject diffMappings, String key) {
    if (!key.equals("sounds"))
      return super.loadFromArray(oldMappings, newMappings, diffMappings, key); 
    return new Mappings(oldMappings.getAsJsonArray(key), newMappings.getAsJsonArray(key), false);
  }
}


/* Location:              C:\Users\Joona\Downloads\Cupid.jar\\us\myles\ViaVersion\protocols\protocol1_15to1_14_4\data\MappingData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */