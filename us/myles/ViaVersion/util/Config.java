package us.myles.ViaVersion.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;
import org.jetbrains.annotations.Nullable;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.BaseConstructor;
import org.yaml.snakeyaml.representer.Representer;
import us.myles.ViaVersion.api.configuration.ConfigurationProvider;

public abstract class Config implements ConfigurationProvider {
  private static final ThreadLocal<Yaml> YAML;
  
  static {
    YAML = ThreadLocal.withInitial(() -> {
          DumperOptions options = new DumperOptions();
          options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
          options.setPrettyFlow(false);
          options.setIndent(2);
          return new Yaml((BaseConstructor)new YamlConstructor(), new Representer(), options);
        });
  }
  
  private final CommentStore commentStore = new CommentStore('.', 2);
  
  private final File configFile;
  
  private Map<String, Object> config;
  
  public Config(File configFile) {
    this.configFile = configFile;
  }
  
  public synchronized Map<String, Object> loadConfig(File location) {
    List<String> unsupported = getUnsupportedOptions();
    URL jarConfigFile = getDefaultConfigURL();
    try {
      this.commentStore.storeComments(jarConfigFile.openStream());
      for (String option : unsupported) {
        List<String> comments = this.commentStore.header(option);
        if (comments != null)
          comments.clear(); 
      } 
    } catch (IOException e) {
      e.printStackTrace();
    } 
    Map<String, Object> config = null;
    if (location.exists())
      try (FileInputStream input = new FileInputStream(location)) {
        config = (Map<String, Object>)((Yaml)YAML.get()).load(input);
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      }  
    if (config == null)
      config = new HashMap<>(); 
    Map<String, Object> defaults = config;
    try (InputStream stream = jarConfigFile.openStream()) {
      defaults = (Map<String, Object>)((Yaml)YAML.get()).load(stream);
      for (String option : unsupported)
        defaults.remove(option); 
      for (Map.Entry<String, Object> entry : config.entrySet()) {
        if (defaults.containsKey(entry.getKey()) && !unsupported.contains(entry.getKey()))
          defaults.put(entry.getKey(), entry.getValue()); 
      } 
    } catch (IOException e) {
      e.printStackTrace();
    } 
    handleConfig(defaults);
    saveConfig(location, defaults);
    return defaults;
  }
  
  public synchronized void saveConfig(File location, Map<String, Object> config) {
    try {
      this.commentStore.writeComments(((Yaml)YAML.get()).dump(config), location);
    } catch (IOException e) {
      e.printStackTrace();
    } 
  }
  
  public void set(String path, Object value) {
    this.config.put(path, value);
  }
  
  public void saveConfig() {
    this.configFile.getParentFile().mkdirs();
    saveConfig(this.configFile, this.config);
  }
  
  public void reloadConfig() {
    this.configFile.getParentFile().mkdirs();
    this.config = new ConcurrentSkipListMap<>(loadConfig(this.configFile));
  }
  
  public Map<String, Object> getValues() {
    return this.config;
  }
  
  @Nullable
  public <T> T get(String key, Class<T> clazz, T def) {
    Object o = this.config.get(key);
    if (o != null)
      return (T)o; 
    return def;
  }
  
  public boolean getBoolean(String key, boolean def) {
    Object o = this.config.get(key);
    if (o != null)
      return ((Boolean)o).booleanValue(); 
    return def;
  }
  
  @Nullable
  public String getString(String key, @Nullable String def) {
    Object o = this.config.get(key);
    if (o != null)
      return (String)o; 
    return def;
  }
  
  public int getInt(String key, int def) {
    Object o = this.config.get(key);
    if (o != null) {
      if (o instanceof Number)
        return ((Number)o).intValue(); 
      return def;
    } 
    return def;
  }
  
  public double getDouble(String key, double def) {
    Object o = this.config.get(key);
    if (o != null) {
      if (o instanceof Number)
        return ((Number)o).doubleValue(); 
      return def;
    } 
    return def;
  }
  
  public List<Integer> getIntegerList(String key) {
    Object o = this.config.get(key);
    if (o != null)
      return (List<Integer>)o; 
    return new ArrayList<>();
  }
  
  public abstract URL getDefaultConfigURL();
  
  protected abstract void handleConfig(Map<String, Object> paramMap);
  
  public abstract List<String> getUnsupportedOptions();
}


/* Location:              C:\Users\Joona\Downloads\Cupid.jar\\us\myles\ViaVersio\\util\Config.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */