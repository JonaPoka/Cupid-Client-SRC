package us.myles.viaversion.libs.gson.internal;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import us.myles.viaversion.libs.gson.ExclusionStrategy;
import us.myles.viaversion.libs.gson.FieldAttributes;
import us.myles.viaversion.libs.gson.Gson;
import us.myles.viaversion.libs.gson.TypeAdapter;
import us.myles.viaversion.libs.gson.TypeAdapterFactory;
import us.myles.viaversion.libs.gson.annotations.Expose;
import us.myles.viaversion.libs.gson.annotations.Since;
import us.myles.viaversion.libs.gson.annotations.Until;
import us.myles.viaversion.libs.gson.reflect.TypeToken;
import us.myles.viaversion.libs.gson.stream.JsonReader;
import us.myles.viaversion.libs.gson.stream.JsonWriter;

public final class Excluder implements TypeAdapterFactory, Cloneable {
  private static final double IGNORE_VERSIONS = -1.0D;
  
  public static final Excluder DEFAULT = new Excluder();
  
  private double version = -1.0D;
  
  private int modifiers = 136;
  
  private boolean serializeInnerClasses = true;
  
  private boolean requireExpose;
  
  private List<ExclusionStrategy> serializationStrategies = Collections.emptyList();
  
  private List<ExclusionStrategy> deserializationStrategies = Collections.emptyList();
  
  protected Excluder clone() {
    try {
      return (Excluder)super.clone();
    } catch (CloneNotSupportedException e) {
      throw new AssertionError(e);
    } 
  }
  
  public Excluder withVersion(double ignoreVersionsAfter) {
    Excluder result = clone();
    result.version = ignoreVersionsAfter;
    return result;
  }
  
  public Excluder withModifiers(int... modifiers) {
    Excluder result = clone();
    result.modifiers = 0;
    for (int modifier : modifiers)
      result.modifiers |= modifier; 
    return result;
  }
  
  public Excluder disableInnerClassSerialization() {
    Excluder result = clone();
    result.serializeInnerClasses = false;
    return result;
  }
  
  public Excluder excludeFieldsWithoutExposeAnnotation() {
    Excluder result = clone();
    result.requireExpose = true;
    return result;
  }
  
  public Excluder withExclusionStrategy(ExclusionStrategy exclusionStrategy, boolean serialization, boolean deserialization) {
    Excluder result = clone();
    if (serialization) {
      result.serializationStrategies = new ArrayList<ExclusionStrategy>(this.serializationStrategies);
      result.serializationStrategies.add(exclusionStrategy);
    } 
    if (deserialization) {
      result.deserializationStrategies = new ArrayList<ExclusionStrategy>(this.deserializationStrategies);
      result.deserializationStrategies.add(exclusionStrategy);
    } 
    return result;
  }
  
  public <T> TypeAdapter<T> create(final Gson gson, final TypeToken<T> type) {
    Class<?> rawType = type.getRawType();
    boolean excludeClass = excludeClassChecks(rawType);
    final boolean skipSerialize = (excludeClass || excludeClassInStrategy(rawType, true));
    final boolean skipDeserialize = (excludeClass || excludeClassInStrategy(rawType, false));
    if (!skipSerialize && !skipDeserialize)
      return null; 
    return new TypeAdapter<T>() {
        private TypeAdapter<T> delegate;
        
        public T read(JsonReader in) throws IOException {
          if (skipDeserialize) {
            in.skipValue();
            return null;
          } 
          return (T)delegate().read(in);
        }
        
        public void write(JsonWriter out, T value) throws IOException {
          if (skipSerialize) {
            out.nullValue();
            return;
          } 
          delegate().write(out, value);
        }
        
        private TypeAdapter<T> delegate() {
          TypeAdapter<T> d = this.delegate;
          return (d != null) ? 
            d : (
            this.delegate = gson.getDelegateAdapter(Excluder.this, type));
        }
      };
  }
  
  public boolean excludeField(Field field, boolean serialize) {
    if ((this.modifiers & field.getModifiers()) != 0)
      return true; 
    if (this.version != -1.0D && 
      !isValidVersion(field.<Since>getAnnotation(Since.class), field.<Until>getAnnotation(Until.class)))
      return true; 
    if (field.isSynthetic())
      return true; 
    if (this.requireExpose) {
      Expose annotation = field.<Expose>getAnnotation(Expose.class);
      if (annotation == null || (serialize ? !annotation.serialize() : !annotation.deserialize()))
        return true; 
    } 
    if (!this.serializeInnerClasses && isInnerClass(field.getType()))
      return true; 
    if (isAnonymousOrLocal(field.getType()))
      return true; 
    List<ExclusionStrategy> list = serialize ? this.serializationStrategies : this.deserializationStrategies;
    if (!list.isEmpty()) {
      FieldAttributes fieldAttributes = new FieldAttributes(field);
      for (ExclusionStrategy exclusionStrategy : list) {
        if (exclusionStrategy.shouldSkipField(fieldAttributes))
          return true; 
      } 
    } 
    return false;
  }
  
  private boolean excludeClassChecks(Class<?> clazz) {
    if (this.version != -1.0D && !isValidVersion(clazz.<Since>getAnnotation(Since.class), clazz.<Until>getAnnotation(Until.class)))
      return true; 
    if (!this.serializeInnerClasses && isInnerClass(clazz))
      return true; 
    if (isAnonymousOrLocal(clazz))
      return true; 
    return false;
  }
  
  public boolean excludeClass(Class<?> clazz, boolean serialize) {
    return (excludeClassChecks(clazz) || 
      excludeClassInStrategy(clazz, serialize));
  }
  
  private boolean excludeClassInStrategy(Class<?> clazz, boolean serialize) {
    List<ExclusionStrategy> list = serialize ? this.serializationStrategies : this.deserializationStrategies;
    for (ExclusionStrategy exclusionStrategy : list) {
      if (exclusionStrategy.shouldSkipClass(clazz))
        return true; 
    } 
    return false;
  }
  
  private boolean isAnonymousOrLocal(Class<?> clazz) {
    return (!Enum.class.isAssignableFrom(clazz) && (clazz
      .isAnonymousClass() || clazz.isLocalClass()));
  }
  
  private boolean isInnerClass(Class<?> clazz) {
    return (clazz.isMemberClass() && !isStatic(clazz));
  }
  
  private boolean isStatic(Class<?> clazz) {
    return ((clazz.getModifiers() & 0x8) != 0);
  }
  
  private boolean isValidVersion(Since since, Until until) {
    return (isValidSince(since) && isValidUntil(until));
  }
  
  private boolean isValidSince(Since annotation) {
    if (annotation != null) {
      double annotationVersion = annotation.value();
      if (annotationVersion > this.version)
        return false; 
    } 
    return true;
  }
  
  private boolean isValidUntil(Until annotation) {
    if (annotation != null) {
      double annotationVersion = annotation.value();
      if (annotationVersion <= this.version)
        return false; 
    } 
    return true;
  }
}


/* Location:              C:\Users\Joona\Downloads\Cupid.jar\\us\myles\viaversion\libs\gson\internal\Excluder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */