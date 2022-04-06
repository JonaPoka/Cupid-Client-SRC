package us.myles.viaversion.libs.javassist.bytecode;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

class MethodHandleInfo extends ConstInfo {
  static final int tag = 15;
  
  int refKind;
  
  int refIndex;
  
  public MethodHandleInfo(int kind, int referenceIndex, int index) {
    super(index);
    this.refKind = kind;
    this.refIndex = referenceIndex;
  }
  
  public MethodHandleInfo(DataInputStream in, int index) throws IOException {
    super(index);
    this.refKind = in.readUnsignedByte();
    this.refIndex = in.readUnsignedShort();
  }
  
  public int hashCode() {
    return this.refKind << 16 ^ this.refIndex;
  }
  
  public boolean equals(Object obj) {
    if (obj instanceof MethodHandleInfo) {
      MethodHandleInfo mh = (MethodHandleInfo)obj;
      return (mh.refKind == this.refKind && mh.refIndex == this.refIndex);
    } 
    return false;
  }
  
  public int getTag() {
    return 15;
  }
  
  public int copy(ConstPool src, ConstPool dest, Map<String, String> map) {
    return dest.addMethodHandleInfo(this.refKind, src
        .getItem(this.refIndex).copy(src, dest, map));
  }
  
  public void write(DataOutputStream out) throws IOException {
    out.writeByte(15);
    out.writeByte(this.refKind);
    out.writeShort(this.refIndex);
  }
  
  public void print(PrintWriter out) {
    out.print("MethodHandle #");
    out.print(this.refKind);
    out.print(", index #");
    out.println(this.refIndex);
  }
}


/* Location:              C:\Users\Joona\Downloads\Cupid.jar\\us\myles\viaversion\libs\javassist\bytecode\MethodHandleInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */