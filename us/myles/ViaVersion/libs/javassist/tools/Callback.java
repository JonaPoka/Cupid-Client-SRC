package us.myles.viaversion.libs.javassist.tools;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import us.myles.viaversion.libs.javassist.CannotCompileException;
import us.myles.viaversion.libs.javassist.CtBehavior;

public abstract class Callback {
  public static Map<String, Callback> callbacks = new HashMap<>();
  
  private final String sourceCode;
  
  public Callback(String src) {
    String uuid = UUID.randomUUID().toString();
    callbacks.put(uuid, this);
    this.sourceCode = "((javassist.tools.Callback) javassist.tools.Callback.callbacks.get(\"" + uuid + "\")).result(new Object[]{" + src + "});";
  }
  
  public abstract void result(Object[] paramArrayOfObject);
  
  public String toString() {
    return sourceCode();
  }
  
  public String sourceCode() {
    return this.sourceCode;
  }
  
  public static void insertBefore(CtBehavior behavior, Callback callback) throws CannotCompileException {
    behavior.insertBefore(callback.toString());
  }
  
  public static void insertAfter(CtBehavior behavior, Callback callback) throws CannotCompileException {
    behavior.insertAfter(callback.toString(), false);
  }
  
  public static void insertAfter(CtBehavior behavior, Callback callback, boolean asFinally) throws CannotCompileException {
    behavior.insertAfter(callback.toString(), asFinally);
  }
  
  public static int insertAt(CtBehavior behavior, Callback callback, int lineNum) throws CannotCompileException {
    return behavior.insertAt(lineNum, callback.toString());
  }
}


/* Location:              C:\Users\Joona\Downloads\Cupid.jar\\us\myles\viaversion\libs\javassist\tools\Callback.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */