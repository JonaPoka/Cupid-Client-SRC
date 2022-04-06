package us.myles.viaversion.libs.gson.internal.bind;

import java.io.IOException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import us.myles.viaversion.libs.gson.Gson;
import us.myles.viaversion.libs.gson.JsonSyntaxException;
import us.myles.viaversion.libs.gson.TypeAdapter;
import us.myles.viaversion.libs.gson.TypeAdapterFactory;
import us.myles.viaversion.libs.gson.reflect.TypeToken;
import us.myles.viaversion.libs.gson.stream.JsonReader;
import us.myles.viaversion.libs.gson.stream.JsonToken;
import us.myles.viaversion.libs.gson.stream.JsonWriter;

public final class SqlDateTypeAdapter extends TypeAdapter<Date> {
  public static final TypeAdapterFactory FACTORY = new TypeAdapterFactory() {
      public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
        return (typeToken.getRawType() == Date.class) ? 
          new SqlDateTypeAdapter() : null;
      }
    };
  
  private final DateFormat format = new SimpleDateFormat("MMM d, yyyy");
  
  public synchronized Date read(JsonReader in) throws IOException {
    if (in.peek() == JsonToken.NULL) {
      in.nextNull();
      return null;
    } 
    try {
      long utilDate = this.format.parse(in.nextString()).getTime();
      return new Date(utilDate);
    } catch (ParseException e) {
      throw new JsonSyntaxException(e);
    } 
  }
  
  public synchronized void write(JsonWriter out, Date value) throws IOException {
    out.value((value == null) ? null : this.format.format(value));
  }
}


/* Location:              C:\Users\Joona\Downloads\Cupid.jar\\us\myles\viaversion\libs\gson\internal\bind\SqlDateTypeAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */