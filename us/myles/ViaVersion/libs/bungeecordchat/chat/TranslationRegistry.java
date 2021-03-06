package us.myles.viaversion.libs.bungeecordchat.chat;

import com.google.common.base.Charsets;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import us.myles.viaversion.libs.gson.Gson;
import us.myles.viaversion.libs.gson.JsonElement;
import us.myles.viaversion.libs.gson.JsonObject;

public final class TranslationRegistry {
  public boolean equals(Object o) {
    if (o == this)
      return true; 
    if (!(o instanceof TranslationRegistry))
      return false; 
    TranslationRegistry other = (TranslationRegistry)o;
    Object<TranslationProvider> this$providers = (Object<TranslationProvider>)getProviders(), other$providers = (Object<TranslationProvider>)other.getProviders();
    return !((this$providers == null) ? (other$providers != null) : !this$providers.equals(other$providers));
  }
  
  public int hashCode() {
    int PRIME = 59;
    result = 1;
    Object<TranslationProvider> $providers = (Object<TranslationProvider>)getProviders();
    return result * 59 + (($providers == null) ? 43 : $providers.hashCode());
  }
  
  public String toString() {
    return "TranslationRegistry(providers=" + getProviders() + ")";
  }
  
  private TranslationRegistry() {
    this.providers = new LinkedList<>();
  }
  
  public static final TranslationRegistry INSTANCE = new TranslationRegistry();
  
  private final List<TranslationProvider> providers;
  
  public List<TranslationProvider> getProviders() {
    return this.providers;
  }
  
  static {
    try {
      INSTANCE.addProvider(new JsonProvider("/assets/minecraft/lang/en_us.json"));
    } catch (Exception exception) {}
    try {
      INSTANCE.addProvider(new JsonProvider("/mojang-translations/en_us.json"));
    } catch (Exception exception) {}
    try {
      INSTANCE.addProvider(new ResourceBundleProvider("mojang-translations/en_US"));
    } catch (Exception exception) {}
  }
  
  private void addProvider(TranslationProvider provider) {
    this.providers.add(provider);
  }
  
  public String translate(String s) {
    for (TranslationProvider provider : this.providers) {
      String translation = provider.translate(s);
      if (translation != null)
        return translation; 
    } 
    return s;
  }
  
  private static interface TranslationProvider {
    String translate(String param1String);
  }
  
  private static class ResourceBundleProvider implements TranslationProvider {
    private final ResourceBundle bundle;
    
    public boolean equals(Object o) {
      if (o == this)
        return true; 
      if (!(o instanceof ResourceBundleProvider))
        return false; 
      ResourceBundleProvider other = (ResourceBundleProvider)o;
      if (!other.canEqual(this))
        return false; 
      Object this$bundle = getBundle(), other$bundle = other.getBundle();
      return !((this$bundle == null) ? (other$bundle != null) : !this$bundle.equals(other$bundle));
    }
    
    protected boolean canEqual(Object other) {
      return other instanceof ResourceBundleProvider;
    }
    
    public int hashCode() {
      int PRIME = 59;
      result = 1;
      Object $bundle = getBundle();
      return result * 59 + (($bundle == null) ? 43 : $bundle.hashCode());
    }
    
    public String toString() {
      return "TranslationRegistry.ResourceBundleProvider(bundle=" + getBundle() + ")";
    }
    
    public ResourceBundle getBundle() {
      return this.bundle;
    }
    
    public ResourceBundleProvider(String bundlePath) {
      this.bundle = ResourceBundle.getBundle(bundlePath);
    }
    
    public String translate(String s) {
      return this.bundle.containsKey(s) ? this.bundle.getString(s) : null;
    }
  }
  
  private static class JsonProvider implements TranslationProvider {
    public boolean equals(Object o) {
      if (o == this)
        return true; 
      if (!(o instanceof JsonProvider))
        return false; 
      JsonProvider other = (JsonProvider)o;
      if (!other.canEqual(this))
        return false; 
      Object<String, String> this$translations = (Object<String, String>)getTranslations(), other$translations = (Object<String, String>)other.getTranslations();
      return !((this$translations == null) ? (other$translations != null) : !this$translations.equals(other$translations));
    }
    
    protected boolean canEqual(Object other) {
      return other instanceof JsonProvider;
    }
    
    public int hashCode() {
      int PRIME = 59;
      result = 1;
      Object<String, String> $translations = (Object<String, String>)getTranslations();
      return result * 59 + (($translations == null) ? 43 : $translations.hashCode());
    }
    
    public String toString() {
      return "TranslationRegistry.JsonProvider()";
    }
    
    private final Map<String, String> translations = new HashMap<>();
    
    public Map<String, String> getTranslations() {
      return this.translations;
    }
    
    public JsonProvider(String resourcePath) throws IOException {
      try (InputStreamReader rd = new InputStreamReader(JsonProvider.class.getResourceAsStream(resourcePath), Charsets.UTF_8)) {
        JsonObject obj = (JsonObject)(new Gson()).fromJson(rd, JsonObject.class);
        for (Map.Entry<String, JsonElement> entries : (Iterable<Map.Entry<String, JsonElement>>)obj.entrySet())
          this.translations.put(entries.getKey(), ((JsonElement)entries.getValue()).getAsString()); 
      } 
    }
    
    public String translate(String s) {
      return this.translations.get(s);
    }
  }
}


/* Location:              C:\Users\Joona\Downloads\Cupid.jar\\us\myles\viaversion\libs\bungeecordchat\chat\TranslationRegistry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */