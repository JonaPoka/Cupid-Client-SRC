package us.myles.viaversion.libs.gson;

import java.io.Reader;
import java.io.StringReader;
import java.util.Iterator;
import java.util.NoSuchElementException;
import us.myles.viaversion.libs.gson.internal.Streams;
import us.myles.viaversion.libs.gson.stream.JsonReader;
import us.myles.viaversion.libs.gson.stream.JsonToken;

public final class JsonStreamParser implements Iterator<JsonElement> {
  private final JsonReader parser;
  
  private final Object lock;
  
  public JsonStreamParser(String json) {
    this(new StringReader(json));
  }
  
  public JsonStreamParser(Reader reader) {
    this.parser = new JsonReader(reader);
    this.parser.setLenient(true);
    this.lock = new Object();
  }
  
  public JsonElement next() throws JsonParseException {
    if (!hasNext())
      throw new NoSuchElementException(); 
    try {
      return Streams.parse(this.parser);
    } catch (StackOverflowError e) {
      throw new JsonParseException("Failed parsing JSON source to Json", e);
    } catch (OutOfMemoryError e) {
      throw new JsonParseException("Failed parsing JSON source to Json", e);
    } catch (JsonParseException e) {
      throw (e.getCause() instanceof java.io.EOFException) ? new NoSuchElementException() : e;
    } 
  }
  
  public boolean hasNext() {
    synchronized (this.lock) {
      return (this.parser.peek() != JsonToken.END_DOCUMENT);
    } 
  }
  
  public void remove() {
    throw new UnsupportedOperationException();
  }
}


/* Location:              C:\Users\Joona\Downloads\Cupid.jar\\us\myles\viaversion\libs\gson\JsonStreamParser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */