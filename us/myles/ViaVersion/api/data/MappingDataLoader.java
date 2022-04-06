package us.myles.ViaVersion.api.data;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.jetbrains.annotations.Nullable;
import us.myles.ViaVersion.api.Via;
import us.myles.ViaVersion.util.GsonUtil;
import us.myles.ViaVersion.util.Int2IntBiMap;
import us.myles.viaversion.libs.fastutil.objects.Object2IntMap;
import us.myles.viaversion.libs.fastutil.objects.Object2IntOpenHashMap;
import us.myles.viaversion.libs.gson.JsonArray;
import us.myles.viaversion.libs.gson.JsonElement;
import us.myles.viaversion.libs.gson.JsonObject;
import us.myles.viaversion.libs.gson.JsonSyntaxException;

public class MappingDataLoader {
  private static final Map<String, JsonObject> MAPPINGS_CACHE = new ConcurrentHashMap<>();
  
  private static boolean cacheJsonMappings;
  
  public static boolean isCacheJsonMappings() {
    return cacheJsonMappings;
  }
  
  public static void enableMappingsCache() {
    cacheJsonMappings = true;
  }
  
  public static Map<String, JsonObject> getMappingsCache() {
    return MAPPINGS_CACHE;
  }
  
  @Nullable
  public static JsonObject loadFromDataDir(String name) {
    File file = new File(Via.getPlatform().getDataFolder(), name);
    if (!file.exists())
      return loadData(name); 
    try (FileReader reader = new FileReader(file)) {
      return (JsonObject)GsonUtil.getGson().fromJson(reader, JsonObject.class);
    } catch (JsonSyntaxException e) {
      Via.getPlatform().getLogger().warning(name + " is badly formatted!");
      e.printStackTrace();
    } catch (IOException|us.myles.viaversion.libs.gson.JsonIOException e) {
      e.printStackTrace();
    } 
    return null;
  }
  
  @Nullable
  public static JsonObject loadData(String name) {
    return loadData(name, false);
  }
  
  @Nullable
  public static JsonObject loadData(String name, boolean cacheIfEnabled) {
    if (cacheJsonMappings) {
      JsonObject cached = MAPPINGS_CACHE.get(name);
      if (cached != null)
        return cached; 
    } 
    InputStream stream = getResource(name);
    if (stream == null)
      return null; 
    InputStreamReader reader = new InputStreamReader(stream);
    try {
      JsonObject object = (JsonObject)GsonUtil.getGson().fromJson(reader, JsonObject.class);
      if (cacheIfEnabled && cacheJsonMappings)
        MAPPINGS_CACHE.put(name, object); 
      return object;
    } finally {
      try {
        reader.close();
      } catch (IOException iOException) {}
    } 
  }
  
  public static void mapIdentifiers(Int2IntBiMap output, JsonObject oldIdentifiers, JsonObject newIdentifiers, @Nullable JsonObject diffIdentifiers) {
    Object2IntMap<String> newIdentifierMap = indexedObjectToMap(newIdentifiers);
    for (Map.Entry<String, JsonElement> entry : (Iterable<Map.Entry<String, JsonElement>>)oldIdentifiers.entrySet()) {
      int value = mapIdentifierEntry(entry, newIdentifierMap, diffIdentifiers);
      if (value != -1)
        output.put(Integer.parseInt(entry.getKey()), value); 
    } 
  }
  
  public static void mapIdentifiers(short[] output, JsonObject oldIdentifiers, JsonObject newIdentifiers) {
    mapIdentifiers(output, oldIdentifiers, newIdentifiers, (JsonObject)null);
  }
  
  public static void mapIdentifiers(short[] output, JsonObject oldIdentifiers, JsonObject newIdentifiers, @Nullable JsonObject diffIdentifiers) {
    Object2IntMap<String> newIdentifierMap = indexedObjectToMap(newIdentifiers);
    for (Map.Entry<String, JsonElement> entry : (Iterable<Map.Entry<String, JsonElement>>)oldIdentifiers.entrySet()) {
      int value = mapIdentifierEntry(entry, newIdentifierMap, diffIdentifiers);
      if (value != -1)
        output[Integer.parseInt((String)entry.getKey())] = (short)value; 
    } 
  }
  
  private static int mapIdentifierEntry(Map.Entry<String, JsonElement> entry, Object2IntMap newIdentifierMap, @Nullable JsonObject diffIdentifiers) {
    int value = newIdentifierMap.getInt(((JsonElement)entry.getValue()).getAsString());
    if (value == -1) {
      if (diffIdentifiers != null) {
        JsonElement diffElement = diffIdentifiers.get(entry.getKey());
        if (diffElement != null)
          value = newIdentifierMap.getInt(diffElement.getAsString()); 
      } 
      if (value == -1) {
        if (!Via.getConfig().isSuppressConversionWarnings() || Via.getManager().isDebug())
          Via.getPlatform().getLogger().warning("No key for " + entry.getValue() + " :( "); 
        return -1;
      } 
    } 
    return value;
  }
  
  public static void mapIdentifiers(short[] output, JsonArray oldIdentifiers, JsonArray newIdentifiers, boolean warnOnMissing) {
    mapIdentifiers(output, oldIdentifiers, newIdentifiers, null, warnOnMissing);
  }
  
  public static void mapIdentifiers(short[] output, JsonArray oldIdentifiers, JsonArray newIdentifiers, @Nullable JsonObject diffIdentifiers, boolean warnOnMissing) {
    Object2IntMap<String> newIdentifierMap = arrayToMap(newIdentifiers);
    for (int i = 0; i < oldIdentifiers.size(); i++) {
      JsonElement oldIdentifier = oldIdentifiers.get(i);
      int mappedId = newIdentifierMap.getInt(oldIdentifier.getAsString());
      if (mappedId == -1) {
        if (diffIdentifiers != null) {
          JsonElement diffElement = diffIdentifiers.get(oldIdentifier.getAsString());
          if (diffElement != null) {
            String mappedName = diffElement.getAsString();
            if (mappedName.isEmpty())
              continue; 
            mappedId = newIdentifierMap.getInt(mappedName);
          } 
        } 
        if (mappedId == -1) {
          if ((warnOnMissing && !Via.getConfig().isSuppressConversionWarnings()) || Via.getManager().isDebug())
            Via.getPlatform().getLogger().warning("No key for " + oldIdentifier + " :( "); 
          continue;
        } 
      } 
      output[i] = (short)mappedId;
      continue;
    } 
  }
  
  public static Object2IntMap<String> indexedObjectToMap(JsonObject object) {
    Object2IntOpenHashMap object2IntOpenHashMap = new Object2IntOpenHashMap(object.size(), 1.0F);
    object2IntOpenHashMap.defaultReturnValue(-1);
    for (Map.Entry<String, JsonElement> entry : (Iterable<Map.Entry<String, JsonElement>>)object.entrySet())
      object2IntOpenHashMap.put(((JsonElement)entry.getValue()).getAsString(), Integer.parseInt(entry.getKey())); 
    return (Object2IntMap<String>)object2IntOpenHashMap;
  }
  
  public static Object2IntMap<String> arrayToMap(JsonArray array) {
    Object2IntOpenHashMap object2IntOpenHashMap = new Object2IntOpenHashMap(array.size(), 1.0F);
    object2IntOpenHashMap.defaultReturnValue(-1);
    for (int i = 0; i < array.size(); i++)
      object2IntOpenHashMap.put(array.get(i).getAsString(), i); 
    return (Object2IntMap<String>)object2IntOpenHashMap;
  }
  
  @Nullable
  public static InputStream getResource(String name) {
    return MappingDataLoader.class.getClassLoader().getResourceAsStream("assets/viaversion/data/" + name);
  }
}


/* Location:              C:\Users\Joona\Downloads\Cupid.jar\\us\myles\ViaVersion\api\data\MappingDataLoader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */