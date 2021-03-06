package us.myles.viaversion.libs.fastutil;

public final class SafeMath {
  public static char safeIntToChar(int value) {
    if (value < 0 || 65535 < value)
      throw new IllegalArgumentException(value + " can't be represented as char"); 
    return (char)value;
  }
  
  public static byte safeIntToByte(int value) {
    if (value < -128 || 127 < value)
      throw new IllegalArgumentException(value + " can't be represented as byte (out of range)"); 
    return (byte)value;
  }
  
  public static short safeIntToShort(int value) {
    if (value < -32768 || 32767 < value)
      throw new IllegalArgumentException(value + " can't be represented as short (out of range)"); 
    return (short)value;
  }
  
  public static int safeLongToInt(long value) {
    if (value < -2147483648L || 2147483647L < value)
      throw new IllegalArgumentException(value + " can't be represented as int (out of range)"); 
    return (int)value;
  }
  
  public static float safeDoubleToFloat(double value) {
    if (Double.isNaN(value))
      return Float.NaN; 
    if (Double.isInfinite(value))
      return (value < 0.0D) ? Float.NEGATIVE_INFINITY : Float.POSITIVE_INFINITY; 
    if (value < 1.401298464324817E-45D || 3.4028234663852886E38D < value)
      throw new IllegalArgumentException(value + " can't be represented as float (out of range)"); 
    float floatValue = (float)value;
    if (floatValue != value)
      throw new IllegalArgumentException(value + " can't be represented as float (imprecise)"); 
    return floatValue;
  }
}


/* Location:              C:\Users\Joona\Downloads\Cupid.jar\\us\myles\viaversion\libs\fastutil\SafeMath.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */