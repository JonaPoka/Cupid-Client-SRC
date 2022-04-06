package org.apache.logging.log4j.core.layout;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.util.KeyValuePair;
import org.apache.logging.log4j.message.StructuredDataId;

@Plugin(name = "LoggerFields", category = "Core", printObject = true)
public final class LoggerFields {
  private final Map<String, String> map;
  
  private final String sdId;
  
  private final String enterpriseId;
  
  private final boolean discardIfAllFieldsAreEmpty;
  
  private LoggerFields(Map<String, String> map, String sdId, String enterpriseId, boolean discardIfAllFieldsAreEmpty) {
    this.sdId = sdId;
    this.enterpriseId = enterpriseId;
    this.map = Collections.unmodifiableMap(map);
    this.discardIfAllFieldsAreEmpty = discardIfAllFieldsAreEmpty;
  }
  
  public Map<String, String> getMap() {
    return this.map;
  }
  
  public String toString() {
    return this.map.toString();
  }
  
  @PluginFactory
  public static LoggerFields createLoggerFields(@PluginElement("LoggerFields") KeyValuePair[] keyValuePairs, @PluginAttribute("sdId") String sdId, @PluginAttribute("enterpriseId") String enterpriseId, @PluginAttribute("discardIfAllFieldsAreEmpty") boolean discardIfAllFieldsAreEmpty) {
    Map<String, String> map = new HashMap<>();
    for (KeyValuePair keyValuePair : keyValuePairs)
      map.put(keyValuePair.getKey(), keyValuePair.getValue()); 
    return new LoggerFields(map, sdId, enterpriseId, discardIfAllFieldsAreEmpty);
  }
  
  public StructuredDataId getSdId() {
    if (this.enterpriseId == null || this.sdId == null)
      return null; 
    int eId = Integer.parseInt(this.enterpriseId);
    return new StructuredDataId(this.sdId, eId, null, null);
  }
  
  public boolean getDiscardIfAllFieldsAreEmpty() {
    return this.discardIfAllFieldsAreEmpty;
  }
}


/* Location:              C:\Users\Joona\Downloads\Cupid.jar!\org\apache\logging\log4j\core\layout\LoggerFields.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */