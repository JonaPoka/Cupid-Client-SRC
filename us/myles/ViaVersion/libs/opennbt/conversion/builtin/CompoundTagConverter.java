package us.myles.viaversion.libs.opennbt.conversion.builtin;

import java.util.HashMap;
import java.util.Map;
import us.myles.viaversion.libs.opennbt.conversion.ConverterRegistry;
import us.myles.viaversion.libs.opennbt.conversion.TagConverter;
import us.myles.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import us.myles.viaversion.libs.opennbt.tag.builtin.Tag;

public class CompoundTagConverter implements TagConverter<CompoundTag, Map> {
  public Map convert(CompoundTag tag) {
    Map<String, Object> ret = new HashMap<>();
    Map<String, Tag> tags = tag.getValue();
    for (String name : tags.keySet()) {
      Tag t = tags.get(name);
      ret.put(t.getName(), ConverterRegistry.convertToValue(t));
    } 
    return ret;
  }
  
  public CompoundTag convert(String name, Map value) {
    Map<String, Tag> tags = new HashMap<>();
    for (Object na : value.keySet()) {
      String n = (String)na;
      tags.put(n, ConverterRegistry.convertToTag(n, value.get(n)));
    } 
    return new CompoundTag(name, tags);
  }
}


/* Location:              C:\Users\Joona\Downloads\Cupid.jar\\us\myles\viaversion\libs\opennbt\conversion\builtin\CompoundTagConverter.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */