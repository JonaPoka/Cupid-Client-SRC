package us.myles.viaversion.libs.gson.internal.bind;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Collection;
import us.myles.viaversion.libs.gson.Gson;
import us.myles.viaversion.libs.gson.TypeAdapter;
import us.myles.viaversion.libs.gson.TypeAdapterFactory;
import us.myles.viaversion.libs.gson.internal.;
import us.myles.viaversion.libs.gson.internal.ConstructorConstructor;
import us.myles.viaversion.libs.gson.internal.ObjectConstructor;
import us.myles.viaversion.libs.gson.reflect.TypeToken;
import us.myles.viaversion.libs.gson.stream.JsonReader;
import us.myles.viaversion.libs.gson.stream.JsonToken;
import us.myles.viaversion.libs.gson.stream.JsonWriter;

public final class CollectionTypeAdapterFactory implements TypeAdapterFactory {
  private final ConstructorConstructor constructorConstructor;
  
  public CollectionTypeAdapterFactory(ConstructorConstructor constructorConstructor) {
    this.constructorConstructor = constructorConstructor;
  }
  
  public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
    Type type = typeToken.getType();
    Class<? super T> rawType = typeToken.getRawType();
    if (!Collection.class.isAssignableFrom(rawType))
      return null; 
    Type elementType = .Gson.Types.getCollectionElementType(type, rawType);
    TypeAdapter<?> elementTypeAdapter = gson.getAdapter(TypeToken.get(elementType));
    ObjectConstructor<T> constructor = this.constructorConstructor.get(typeToken);
    TypeAdapter<T> result = new Adapter(gson, elementType, elementTypeAdapter, (ObjectConstructor)constructor);
    return result;
  }
  
  private static final class Adapter<E> extends TypeAdapter<Collection<E>> {
    private final TypeAdapter<E> elementTypeAdapter;
    
    private final ObjectConstructor<? extends Collection<E>> constructor;
    
    public Adapter(Gson context, Type elementType, TypeAdapter<E> elementTypeAdapter, ObjectConstructor<? extends Collection<E>> constructor) {
      this.elementTypeAdapter = new TypeAdapterRuntimeTypeWrapper<E>(context, elementTypeAdapter, elementType);
      this.constructor = constructor;
    }
    
    public Collection<E> read(JsonReader in) throws IOException {
      if (in.peek() == JsonToken.NULL) {
        in.nextNull();
        return null;
      } 
      Collection<E> collection = (Collection<E>)this.constructor.construct();
      in.beginArray();
      while (in.hasNext()) {
        E instance = (E)this.elementTypeAdapter.read(in);
        collection.add(instance);
      } 
      in.endArray();
      return collection;
    }
    
    public void write(JsonWriter out, Collection<E> collection) throws IOException {
      if (collection == null) {
        out.nullValue();
        return;
      } 
      out.beginArray();
      for (E element : collection)
        this.elementTypeAdapter.write(out, element); 
      out.endArray();
    }
  }
}


/* Location:              C:\Users\Joona\Downloads\Cupid.jar\\us\myles\viaversion\libs\gson\internal\bind\CollectionTypeAdapterFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */