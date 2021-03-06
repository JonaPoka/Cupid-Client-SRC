package us.myles.viaversion.libs.bungeecordchat.chat;

import java.lang.reflect.Type;
import java.util.List;
import us.myles.viaversion.libs.bungeecordchat.api.chat.BaseComponent;
import us.myles.viaversion.libs.bungeecordchat.api.chat.TextComponent;
import us.myles.viaversion.libs.gson.JsonDeserializationContext;
import us.myles.viaversion.libs.gson.JsonDeserializer;
import us.myles.viaversion.libs.gson.JsonElement;
import us.myles.viaversion.libs.gson.JsonObject;
import us.myles.viaversion.libs.gson.JsonParseException;
import us.myles.viaversion.libs.gson.JsonSerializationContext;
import us.myles.viaversion.libs.gson.JsonSerializer;

public class TextComponentSerializer extends BaseComponentSerializer implements JsonSerializer<TextComponent>, JsonDeserializer<TextComponent> {
  public TextComponent deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
    TextComponent component = new TextComponent();
    JsonObject object = json.getAsJsonObject();
    if (!object.has("text"))
      throw new JsonParseException("Could not parse JSON: missing 'text' property"); 
    component.setText(object.get("text").getAsString());
    deserialize(object, (BaseComponent)component, context);
    return component;
  }
  
  public JsonElement serialize(TextComponent src, Type typeOfSrc, JsonSerializationContext context) {
    List<BaseComponent> extra = src.getExtra();
    JsonObject object = new JsonObject();
    object.addProperty("text", src.getText());
    if (src.hasFormatting() || (extra != null && !extra.isEmpty()))
      serialize(object, (BaseComponent)src, context); 
    return (JsonElement)object;
  }
}


/* Location:              C:\Users\Joona\Downloads\Cupid.jar\\us\myles\viaversion\libs\bungeecordchat\chat\TextComponentSerializer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */