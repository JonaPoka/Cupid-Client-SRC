package us.myles.viaversion.libs.opennbt.tag.builtin.custom;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import us.myles.viaversion.libs.opennbt.tag.builtin.Tag;

public class DoubleArrayTag extends Tag {
  private double[] value;
  
  public DoubleArrayTag(String name) {
    this(name, new double[0]);
  }
  
  public DoubleArrayTag(String name, double[] value) {
    super(name);
    this.value = value;
  }
  
  public double[] getValue() {
    return (double[])this.value.clone();
  }
  
  public void setValue(double[] value) {
    if (value == null)
      return; 
    this.value = (double[])value.clone();
  }
  
  public double getValue(int index) {
    return this.value[index];
  }
  
  public void setValue(int index, double value) {
    this.value[index] = value;
  }
  
  public int length() {
    return this.value.length;
  }
  
  public void read(DataInput in) throws IOException {
    this.value = new double[in.readInt()];
    for (int index = 0; index < this.value.length; index++)
      this.value[index] = in.readDouble(); 
  }
  
  public void write(DataOutput out) throws IOException {
    out.writeInt(this.value.length);
    for (int index = 0; index < this.value.length; index++)
      out.writeDouble(this.value[index]); 
  }
  
  public DoubleArrayTag clone() {
    return new DoubleArrayTag(getName(), getValue());
  }
}


/* Location:              C:\Users\Joona\Downloads\Cupid.jar\\us\myles\viaversion\libs\opennbt\tag\builtin\custom\DoubleArrayTag.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */