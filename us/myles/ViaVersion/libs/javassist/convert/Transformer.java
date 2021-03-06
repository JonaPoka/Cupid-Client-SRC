package us.myles.viaversion.libs.javassist.convert;

import us.myles.viaversion.libs.javassist.CannotCompileException;
import us.myles.viaversion.libs.javassist.CtClass;
import us.myles.viaversion.libs.javassist.bytecode.BadBytecode;
import us.myles.viaversion.libs.javassist.bytecode.CodeAttribute;
import us.myles.viaversion.libs.javassist.bytecode.CodeIterator;
import us.myles.viaversion.libs.javassist.bytecode.ConstPool;
import us.myles.viaversion.libs.javassist.bytecode.MethodInfo;
import us.myles.viaversion.libs.javassist.bytecode.Opcode;

public abstract class Transformer implements Opcode {
  private Transformer next;
  
  public Transformer(Transformer t) {
    this.next = t;
  }
  
  public Transformer getNext() {
    return this.next;
  }
  
  public void initialize(ConstPool cp, CodeAttribute attr) {}
  
  public void initialize(ConstPool cp, CtClass clazz, MethodInfo minfo) throws CannotCompileException {
    initialize(cp, minfo.getCodeAttribute());
  }
  
  public void clean() {}
  
  public abstract int transform(CtClass paramCtClass, int paramInt, CodeIterator paramCodeIterator, ConstPool paramConstPool) throws CannotCompileException, BadBytecode;
  
  public int extraLocals() {
    return 0;
  }
  
  public int extraStack() {
    return 0;
  }
}


/* Location:              C:\Users\Joona\Downloads\Cupid.jar\\us\myles\viaversion\libs\javassist\convert\Transformer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */