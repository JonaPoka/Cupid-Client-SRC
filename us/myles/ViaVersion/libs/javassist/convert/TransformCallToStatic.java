package us.myles.viaversion.libs.javassist.convert;

import us.myles.viaversion.libs.javassist.CtMethod;
import us.myles.viaversion.libs.javassist.bytecode.CodeIterator;
import us.myles.viaversion.libs.javassist.bytecode.ConstPool;
import us.myles.viaversion.libs.javassist.bytecode.Descriptor;

public class TransformCallToStatic extends TransformCall {
  public TransformCallToStatic(Transformer next, CtMethod origMethod, CtMethod substMethod) {
    super(next, origMethod, substMethod);
    this.methodDescriptor = origMethod.getMethodInfo2().getDescriptor();
  }
  
  protected int match(int c, int pos, CodeIterator iterator, int typedesc, ConstPool cp) {
    if (this.newIndex == 0) {
      String desc = Descriptor.insertParameter(this.classname, this.methodDescriptor);
      int nt = cp.addNameAndTypeInfo(this.newMethodname, desc);
      int ci = cp.addClassInfo(this.newClassname);
      this.newIndex = cp.addMethodrefInfo(ci, nt);
      this.constPool = cp;
    } 
    iterator.writeByte(184, pos);
    iterator.write16bit(this.newIndex, pos + 1);
    return pos;
  }
}


/* Location:              C:\Users\Joona\Downloads\Cupid.jar\\us\myles\viaversion\libs\javassist\convert\TransformCallToStatic.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */