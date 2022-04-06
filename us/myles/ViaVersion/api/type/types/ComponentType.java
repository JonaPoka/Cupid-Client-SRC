package us.myles.ViaVersion.api.type.types;

import io.netty.buffer.ByteBuf;
import us.myles.ViaVersion.api.Via;
import us.myles.ViaVersion.api.type.Type;
import us.myles.ViaVersion.util.GsonUtil;
import us.myles.viaversion.libs.gson.JsonElement;
import us.myles.viaversion.libs.gson.JsonSyntaxException;

public class ComponentType extends Type<JsonElement> {
  private static final StringType STRING_TAG = new StringType(262144);
  
  public ComponentType() {
    super(JsonElement.class);
  }
  
  public JsonElement read(ByteBuf buffer) throws Exception {
    String s = STRING_TAG.read(buffer);
    try {
      return GsonUtil.getJsonParser().parse(s);
    } catch (JsonSyntaxException e) {
      Via.getPlatform().getLogger().severe("Error when trying to parse json: " + s);
      throw e;
    } 
  }
  
  public void write(ByteBuf buffer, JsonElement object) throws Exception {
    STRING_TAG.write(buffer, object.toString());
  }
}


/* Location:              C:\Users\Joona\Downloads\Cupid.jar\\us\myles\ViaVersion\api\type\types\ComponentType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */