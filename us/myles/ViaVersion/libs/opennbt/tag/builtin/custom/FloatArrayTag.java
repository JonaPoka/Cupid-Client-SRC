package us.myles.viaversion.libs.opennbt.tag.builtin.custom;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import us.myles.viaversion.libs.opennbt.tag.builtin.Tag;

public class FloatArrayTag extends Tag {
  private float[] value;
  
  public FloatArrayTag(String name) {
    this(name, new float[0]);
  }
  
  public FloatArrayTag(String name, float[] value) {
    super(name);
    this.value = value;
  }
  
  public float[] getValue() {
    return (float[])this.value.clone();
  }
  
  public void setValue(float[] value) {
    if (value == null)
      return; 
    this.value = (float[])value.clone();
  }
  
  public float getValue(int index) {
    return this.value[index];
  }
  
  public void setValue(int index, float value) {
    this.value[index] = value;
  }
  
  public int length() {
    return this.value.length;
  }
  
  public void read(DataInput in) throws IOException {
    this.value = new float[in.readInt()];
    for (int index = 0; index < this.value.length; index++)
      this.value[index] = in.readFloat(); 
  }
  
  public void write(DataOutput out) throws IOException {
    out.writeInt(this.value.length);
    for (int index = 0; index < this.value.length; index++)
      out.writeFloat(this.value[index]); 
  }
  
  public FloatArrayTag clone() {
    return new FloatArrayTag(getName(), getValue());
  }
}


/* Location:              C:\Users\Joona\Downloads\Cupid.jar\\us\myles\viaversion\libs\opennbt\tag\builtin\custom\FloatArrayTag.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */