package optifine;

public class ReflectorClass {
  private String targetClassName;
  
  private boolean checked;
  
  private Class targetClass;
  
  public ReflectorClass(String p_i81_1_) {
    this(p_i81_1_, false);
  }
  
  public ReflectorClass(String p_i82_1_, boolean p_i82_2_) {
    this.targetClassName = null;
    this.checked = false;
    this.targetClass = null;
    this.targetClassName = p_i82_1_;
    if (!p_i82_2_)
      Class clazz = getTargetClass(); 
  }
  
  public ReflectorClass(Class p_i83_1_) {
    this.targetClassName = null;
    this.checked = false;
    this.targetClass = null;
    this.targetClass = p_i83_1_;
    this.targetClassName = p_i83_1_.getName();
    this.checked = true;
  }
  
  public Class getTargetClass() {
    if (this.checked)
      return this.targetClass; 
    this.checked = true;
    try {
      this.targetClass = Class.forName(this.targetClassName);
    } catch (ClassNotFoundException var2) {
      Config.log("(Reflector) Class not present: " + this.targetClassName);
    } catch (Throwable throwable) {
      throwable.printStackTrace();
    } 
    return this.targetClass;
  }
  
  public boolean exists() {
    return (getTargetClass() != null);
  }
  
  public String getTargetClassName() {
    return this.targetClassName;
  }
  
  public boolean isInstance(Object p_isInstance_1_) {
    return (getTargetClass() == null) ? false : getTargetClass().isInstance(p_isInstance_1_);
  }
  
  public ReflectorField makeField(String p_makeField_1_) {
    return new ReflectorField(this, p_makeField_1_);
  }
  
  public ReflectorMethod makeMethod(String p_makeMethod_1_) {
    return new ReflectorMethod(this, p_makeMethod_1_);
  }
  
  public ReflectorMethod makeMethod(String p_makeMethod_1_, Class[] p_makeMethod_2_) {
    return new ReflectorMethod(this, p_makeMethod_1_, p_makeMethod_2_);
  }
  
  public ReflectorMethod makeMethod(String p_makeMethod_1_, Class[] p_makeMethod_2_, boolean p_makeMethod_3_) {
    return new ReflectorMethod(this, p_makeMethod_1_, p_makeMethod_2_, p_makeMethod_3_);
  }
}


/* Location:              C:\Users\Joona\Downloads\Cupid.jar!\optifine\ReflectorClass.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */