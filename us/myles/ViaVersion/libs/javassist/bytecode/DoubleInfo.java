package us.myles.viaversion.libs.javassist.bytecode;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

class DoubleInfo extends ConstInfo {
  static final int tag = 6;
  
  double value;
  
  public DoubleInfo(double d, int index) {
    super(index);
    this.value = d;
  }
  
  public DoubleInfo(DataInputStream in, int index) throws IOException {
    super(index);
    this.value = in.readDouble();
  }
  
  public int hashCode() {
    long v = Double.doubleToLongBits(this.value);
    return (int)(v ^ v >>> 32L);
  }
  
  public boolean equals(Object obj) {
    return (obj instanceof DoubleInfo && ((DoubleInfo)obj).value == this.value);
  }
  
  public int getTag() {
    return 6;
  }
  
  public int copy(ConstPool src, ConstPool dest, Map<String, String> map) {
    return dest.addDoubleInfo(this.value);
  }
  
  public void write(DataOutputStream out) throws IOException {
    out.writeByte(6);
    out.writeDouble(this.value);
  }
  
  public void print(PrintWriter out) {
    out.print("Double ");
    out.println(this.value);
  }
}


/* Location:              C:\Users\Joona\Downloads\Cupid.jar\\us\myles\viaversion\libs\javassist\bytecode\DoubleInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */