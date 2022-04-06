package us.myles.viaversion.libs.javassist.bytecode.stackmap;

public interface TypeTag {
  public static final String TOP_TYPE = "*top*";
  
  public static final TypeData.BasicType TOP = new TypeData.BasicType("*top*", 0, ' ');
  
  public static final TypeData.BasicType INTEGER = new TypeData.BasicType("int", 1, 'I');
  
  public static final TypeData.BasicType FLOAT = new TypeData.BasicType("float", 2, 'F');
  
  public static final TypeData.BasicType DOUBLE = new TypeData.BasicType("double", 3, 'D');
  
  public static final TypeData.BasicType LONG = new TypeData.BasicType("long", 4, 'J');
}


/* Location:              C:\Users\Joona\Downloads\Cupid.jar\\us\myles\viaversion\libs\javassist\bytecode\stackmap\TypeTag.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */