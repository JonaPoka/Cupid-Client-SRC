package us.myles.viaversion.libs.gson;

import java.lang.reflect.Type;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import us.myles.viaversion.libs.gson.internal.;
import us.myles.viaversion.libs.gson.internal.Excluder;
import us.myles.viaversion.libs.gson.internal.bind.TreeTypeAdapter;
import us.myles.viaversion.libs.gson.internal.bind.TypeAdapters;
import us.myles.viaversion.libs.gson.reflect.TypeToken;

public final class GsonBuilder {
  private Excluder excluder = Excluder.DEFAULT;
  
  private LongSerializationPolicy longSerializationPolicy = LongSerializationPolicy.DEFAULT;
  
  private FieldNamingStrategy fieldNamingPolicy = FieldNamingPolicy.IDENTITY;
  
  private final Map<Type, InstanceCreator<?>> instanceCreators = new HashMap<Type, InstanceCreator<?>>();
  
  private final List<TypeAdapterFactory> factories = new ArrayList<TypeAdapterFactory>();
  
  private final List<TypeAdapterFactory> hierarchyFactories = new ArrayList<TypeAdapterFactory>();
  
  private boolean serializeNulls = false;
  
  private String datePattern;
  
  private int dateStyle = 2;
  
  private int timeStyle = 2;
  
  private boolean complexMapKeySerialization = false;
  
  private boolean serializeSpecialFloatingPointValues = false;
  
  private boolean escapeHtmlChars = true;
  
  private boolean prettyPrinting = false;
  
  private boolean generateNonExecutableJson = false;
  
  private boolean lenient = false;
  
  public GsonBuilder() {}
  
  GsonBuilder(Gson gson) {
    this.excluder = gson.excluder;
    this.fieldNamingPolicy = gson.fieldNamingStrategy;
    this.instanceCreators.putAll(gson.instanceCreators);
    this.serializeNulls = gson.serializeNulls;
    this.complexMapKeySerialization = gson.complexMapKeySerialization;
    this.generateNonExecutableJson = gson.generateNonExecutableJson;
    this.escapeHtmlChars = gson.htmlSafe;
    this.prettyPrinting = gson.prettyPrinting;
    this.lenient = gson.lenient;
    this.serializeSpecialFloatingPointValues = gson.serializeSpecialFloatingPointValues;
    this.longSerializationPolicy = gson.longSerializationPolicy;
    this.datePattern = gson.datePattern;
    this.dateStyle = gson.dateStyle;
    this.timeStyle = gson.timeStyle;
    this.factories.addAll(gson.builderFactories);
    this.hierarchyFactories.addAll(gson.builderHierarchyFactories);
  }
  
  public GsonBuilder setVersion(double ignoreVersionsAfter) {
    this.excluder = this.excluder.withVersion(ignoreVersionsAfter);
    return this;
  }
  
  public GsonBuilder excludeFieldsWithModifiers(int... modifiers) {
    this.excluder = this.excluder.withModifiers(modifiers);
    return this;
  }
  
  public GsonBuilder generateNonExecutableJson() {
    this.generateNonExecutableJson = true;
    return this;
  }
  
  public GsonBuilder excludeFieldsWithoutExposeAnnotation() {
    this.excluder = this.excluder.excludeFieldsWithoutExposeAnnotation();
    return this;
  }
  
  public GsonBuilder serializeNulls() {
    this.serializeNulls = true;
    return this;
  }
  
  public GsonBuilder enableComplexMapKeySerialization() {
    this.complexMapKeySerialization = true;
    return this;
  }
  
  public GsonBuilder disableInnerClassSerialization() {
    this.excluder = this.excluder.disableInnerClassSerialization();
    return this;
  }
  
  public GsonBuilder setLongSerializationPolicy(LongSerializationPolicy serializationPolicy) {
    this.longSerializationPolicy = serializationPolicy;
    return this;
  }
  
  public GsonBuilder setFieldNamingPolicy(FieldNamingPolicy namingConvention) {
    this.fieldNamingPolicy = namingConvention;
    return this;
  }
  
  public GsonBuilder setFieldNamingStrategy(FieldNamingStrategy fieldNamingStrategy) {
    this.fieldNamingPolicy = fieldNamingStrategy;
    return this;
  }
  
  public GsonBuilder setExclusionStrategies(ExclusionStrategy... strategies) {
    for (ExclusionStrategy strategy : strategies)
      this.excluder = this.excluder.withExclusionStrategy(strategy, true, true); 
    return this;
  }
  
  public GsonBuilder addSerializationExclusionStrategy(ExclusionStrategy strategy) {
    this.excluder = this.excluder.withExclusionStrategy(strategy, true, false);
    return this;
  }
  
  public GsonBuilder addDeserializationExclusionStrategy(ExclusionStrategy strategy) {
    this.excluder = this.excluder.withExclusionStrategy(strategy, false, true);
    return this;
  }
  
  public GsonBuilder setPrettyPrinting() {
    this.prettyPrinting = true;
    return this;
  }
  
  public GsonBuilder setLenient() {
    this.lenient = true;
    return this;
  }
  
  public GsonBuilder disableHtmlEscaping() {
    this.escapeHtmlChars = false;
    return this;
  }
  
  public GsonBuilder setDateFormat(String pattern) {
    this.datePattern = pattern;
    return this;
  }
  
  public GsonBuilder setDateFormat(int style) {
    this.dateStyle = style;
    this.datePattern = null;
    return this;
  }
  
  public GsonBuilder setDateFormat(int dateStyle, int timeStyle) {
    this.dateStyle = dateStyle;
    this.timeStyle = timeStyle;
    this.datePattern = null;
    return this;
  }
  
  public GsonBuilder registerTypeAdapter(Type type, Object typeAdapter) {
    .Gson.Preconditions.checkArgument((typeAdapter instanceof JsonSerializer || typeAdapter instanceof JsonDeserializer || typeAdapter instanceof InstanceCreator || typeAdapter instanceof TypeAdapter));
    if (typeAdapter instanceof InstanceCreator)
      this.instanceCreators.put(type, (InstanceCreator)typeAdapter); 
    if (typeAdapter instanceof JsonSerializer || typeAdapter instanceof JsonDeserializer) {
      TypeToken<?> typeToken = TypeToken.get(type);
      this.factories.add(TreeTypeAdapter.newFactoryWithMatchRawType(typeToken, typeAdapter));
    } 
    if (typeAdapter instanceof TypeAdapter)
      this.factories.add(TypeAdapters.newFactory(TypeToken.get(type), (TypeAdapter)typeAdapter)); 
    return this;
  }
  
  public GsonBuilder registerTypeAdapterFactory(TypeAdapterFactory factory) {
    this.factories.add(factory);
    return this;
  }
  
  public GsonBuilder registerTypeHierarchyAdapter(Class<?> baseType, Object typeAdapter) {
    .Gson.Preconditions.checkArgument((typeAdapter instanceof JsonSerializer || typeAdapter instanceof JsonDeserializer || typeAdapter instanceof TypeAdapter));
    if (typeAdapter instanceof JsonDeserializer || typeAdapter instanceof JsonSerializer)
      this.hierarchyFactories.add(TreeTypeAdapter.newTypeHierarchyFactory(baseType, typeAdapter)); 
    if (typeAdapter instanceof TypeAdapter)
      this.factories.add(TypeAdapters.newTypeHierarchyFactory(baseType, (TypeAdapter)typeAdapter)); 
    return this;
  }
  
  public GsonBuilder serializeSpecialFloatingPointValues() {
    this.serializeSpecialFloatingPointValues = true;
    return this;
  }
  
  public Gson create() {
    List<TypeAdapterFactory> factories = new ArrayList<TypeAdapterFactory>(this.factories.size() + this.hierarchyFactories.size() + 3);
    factories.addAll(this.factories);
    Collections.reverse(factories);
    List<TypeAdapterFactory> hierarchyFactories = new ArrayList<TypeAdapterFactory>(this.hierarchyFactories);
    Collections.reverse(hierarchyFactories);
    factories.addAll(hierarchyFactories);
    addTypeAdaptersForDate(this.datePattern, this.dateStyle, this.timeStyle, factories);
    return new Gson(this.excluder, this.fieldNamingPolicy, this.instanceCreators, this.serializeNulls, this.complexMapKeySerialization, this.generateNonExecutableJson, this.escapeHtmlChars, this.prettyPrinting, this.lenient, this.serializeSpecialFloatingPointValues, this.longSerializationPolicy, this.datePattern, this.dateStyle, this.timeStyle, this.factories, this.hierarchyFactories, factories);
  }
  
  private void addTypeAdaptersForDate(String datePattern, int dateStyle, int timeStyle, List<TypeAdapterFactory> factories) {
    DefaultDateTypeAdapter dateTypeAdapter;
    TypeAdapter<Timestamp> timestampTypeAdapter;
    TypeAdapter<Date> javaSqlDateTypeAdapter;
    if (datePattern != null && !"".equals(datePattern.trim())) {
      dateTypeAdapter = new DefaultDateTypeAdapter(Date.class, datePattern);
      timestampTypeAdapter = new DefaultDateTypeAdapter((Class)Timestamp.class, datePattern);
      javaSqlDateTypeAdapter = new DefaultDateTypeAdapter((Class)Date.class, datePattern);
    } else if (dateStyle != 2 && timeStyle != 2) {
      dateTypeAdapter = new DefaultDateTypeAdapter(Date.class, dateStyle, timeStyle);
      timestampTypeAdapter = new DefaultDateTypeAdapter((Class)Timestamp.class, dateStyle, timeStyle);
      javaSqlDateTypeAdapter = new DefaultDateTypeAdapter((Class)Date.class, dateStyle, timeStyle);
    } else {
      return;
    } 
    factories.add(TypeAdapters.newFactory(Date.class, dateTypeAdapter));
    factories.add(TypeAdapters.newFactory(Timestamp.class, timestampTypeAdapter));
    factories.add(TypeAdapters.newFactory(Date.class, javaSqlDateTypeAdapter));
  }
}


/* Location:              C:\Users\Joona\Downloads\Cupid.jar\\us\myles\viaversion\libs\gson\GsonBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */