package us.myles.viaversion.libs.javassist.bytecode.annotation;

import java.io.IOException;
import java.lang.reflect.Method;
import us.myles.viaversion.libs.javassist.ClassPool;
import us.myles.viaversion.libs.javassist.bytecode.ConstPool;

public class DoubleMemberValue extends MemberValue {
  int valueIndex;
  
  public DoubleMemberValue(int index, ConstPool cp) {
    super('D', cp);
    this.valueIndex = index;
  }
  
  public DoubleMemberValue(double d, ConstPool cp) {
    super('D', cp);
    setValue(d);
  }
  
  public DoubleMemberValue(ConstPool cp) {
    super('D', cp);
    setValue(0.0D);
  }
  
  Object getValue(ClassLoader cl, ClassPool cp, Method m) {
    return Double.valueOf(getValue());
  }
  
  Class<?> getType(ClassLoader cl) {
    return double.class;
  }
  
  public double getValue() {
    return this.cp.getDoubleInfo(this.valueIndex);
  }
  
  public void setValue(double newValue) {
    this.valueIndex = this.cp.addDoubleInfo(newValue);
  }
  
  public String toString() {
    return Double.toString(getValue());
  }
  
  public void write(AnnotationsWriter writer) throws IOException {
    writer.constValueIndex(getValue());
  }
  
  public void accept(MemberValueVisitor visitor) {
    visitor.visitDoubleMemberValue(this);
  }
}


/* Location:              C:\Users\Joona\Downloads\Cupid.jar\\us\myles\viaversion\libs\javassist\bytecode\annotation\DoubleMemberValue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */