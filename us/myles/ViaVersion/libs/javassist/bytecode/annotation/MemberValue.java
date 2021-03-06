package us.myles.viaversion.libs.javassist.bytecode.annotation;

import java.io.IOException;
import java.lang.reflect.Method;
import us.myles.viaversion.libs.javassist.ClassPool;
import us.myles.viaversion.libs.javassist.bytecode.ConstPool;
import us.myles.viaversion.libs.javassist.bytecode.Descriptor;

public abstract class MemberValue {
  ConstPool cp;
  
  char tag;
  
  MemberValue(char tag, ConstPool cp) {
    this.cp = cp;
    this.tag = tag;
  }
  
  abstract Object getValue(ClassLoader paramClassLoader, ClassPool paramClassPool, Method paramMethod) throws ClassNotFoundException;
  
  abstract Class<?> getType(ClassLoader paramClassLoader) throws ClassNotFoundException;
  
  static Class<?> loadClass(ClassLoader cl, String classname) throws ClassNotFoundException, NoSuchClassError {
    try {
      return Class.forName(convertFromArray(classname), true, cl);
    } catch (LinkageError e) {
      throw new NoSuchClassError(classname, e);
    } 
  }
  
  private static String convertFromArray(String classname) {
    int index = classname.indexOf("[]");
    if (index != -1) {
      String rawType = classname.substring(0, index);
      StringBuffer sb = new StringBuffer(Descriptor.of(rawType));
      while (index != -1) {
        sb.insert(0, "[");
        index = classname.indexOf("[]", index + 1);
      } 
      return sb.toString().replace('/', '.');
    } 
    return classname;
  }
  
  public abstract void accept(MemberValueVisitor paramMemberValueVisitor);
  
  public abstract void write(AnnotationsWriter paramAnnotationsWriter) throws IOException;
}


/* Location:              C:\Users\Joona\Downloads\Cupid.jar\\us\myles\viaversion\libs\javassist\bytecode\annotation\MemberValue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */