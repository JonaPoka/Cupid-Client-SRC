package us.myles.viaversion.libs.gson.internal.bind;

import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import us.myles.viaversion.libs.gson.Gson;
import us.myles.viaversion.libs.gson.TypeAdapter;
import us.myles.viaversion.libs.gson.TypeAdapterFactory;
import us.myles.viaversion.libs.gson.internal.;
import us.myles.viaversion.libs.gson.reflect.TypeToken;
import us.myles.viaversion.libs.gson.stream.JsonReader;
import us.myles.viaversion.libs.gson.stream.JsonToken;
import us.myles.viaversion.libs.gson.stream.JsonWriter;

public final class ArrayTypeAdapter<E> extends TypeAdapter<Object> {
  public static final TypeAdapterFactory FACTORY = new TypeAdapterFactory() {
      public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
        Type type = typeToken.getType();
        if (!(type instanceof java.lang.reflect.GenericArrayType) && (!(type instanceof Class) || !((Class)type).isArray()))
          return null; 
        Type componentType = .Gson.Types.getArrayComponentType(type);
        TypeAdapter<?> componentTypeAdapter = gson.getAdapter(TypeToken.get(componentType));
        return new ArrayTypeAdapter(gson, componentTypeAdapter, 
            .Gson.Types.getRawType(componentType));
      }
    };
  
  private final Class<E> componentType;
  
  private final TypeAdapter<E> componentTypeAdapter;
  
  public ArrayTypeAdapter(Gson context, TypeAdapter<E> componentTypeAdapter, Class<E> componentType) {
    this.componentTypeAdapter = new TypeAdapterRuntimeTypeWrapper<E>(context, componentTypeAdapter, componentType);
    this.componentType = componentType;
  }
  
  public Object read(JsonReader in) throws IOException {
    if (in.peek() == JsonToken.NULL) {
      in.nextNull();
      return null;
    } 
    List<E> list = new ArrayList<E>();
    in.beginArray();
    while (in.hasNext()) {
      E instance = (E)this.componentTypeAdapter.read(in);
      list.add(instance);
    } 
    in.endArray();
    int size = list.size();
    Object array = Array.newInstance(this.componentType, size);
    for (int i = 0; i < size; i++)
      Array.set(array, i, list.get(i)); 
    return array;
  }
  
  public void write(JsonWriter out, Object array) throws IOException {
    if (array == null) {
      out.nullValue();
      return;
    } 
    out.beginArray();
    for (int i = 0, length = Array.getLength(array); i < length; i++) {
      E value = (E)Array.get(array, i);
      this.componentTypeAdapter.write(out, value);
    } 
    out.endArray();
  }
}


/* Location:              C:\Users\Joona\Downloads\Cupid.jar\\us\myles\viaversion\libs\gson\internal\bind\ArrayTypeAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */