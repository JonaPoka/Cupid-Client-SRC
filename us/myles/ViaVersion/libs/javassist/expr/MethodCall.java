package us.myles.viaversion.libs.javassist.expr;

import us.myles.viaversion.libs.javassist.CannotCompileException;
import us.myles.viaversion.libs.javassist.ClassPool;
import us.myles.viaversion.libs.javassist.CtBehavior;
import us.myles.viaversion.libs.javassist.CtClass;
import us.myles.viaversion.libs.javassist.CtMethod;
import us.myles.viaversion.libs.javassist.NotFoundException;
import us.myles.viaversion.libs.javassist.bytecode.BadBytecode;
import us.myles.viaversion.libs.javassist.bytecode.Bytecode;
import us.myles.viaversion.libs.javassist.bytecode.CodeAttribute;
import us.myles.viaversion.libs.javassist.bytecode.CodeIterator;
import us.myles.viaversion.libs.javassist.bytecode.ConstPool;
import us.myles.viaversion.libs.javassist.bytecode.Descriptor;
import us.myles.viaversion.libs.javassist.bytecode.MethodInfo;
import us.myles.viaversion.libs.javassist.compiler.CompileError;
import us.myles.viaversion.libs.javassist.compiler.Javac;

public class MethodCall extends Expr {
  protected MethodCall(int pos, CodeIterator i, CtClass declaring, MethodInfo m) {
    super(pos, i, declaring, m);
  }
  
  private int getNameAndType(ConstPool cp) {
    int pos = this.currentPos;
    int c = this.iterator.byteAt(pos);
    int index = this.iterator.u16bitAt(pos + 1);
    if (c == 185)
      return cp.getInterfaceMethodrefNameAndType(index); 
    return cp.getMethodrefNameAndType(index);
  }
  
  public CtBehavior where() {
    return super.where();
  }
  
  public int getLineNumber() {
    return super.getLineNumber();
  }
  
  public String getFileName() {
    return super.getFileName();
  }
  
  protected CtClass getCtClass() throws NotFoundException {
    return this.thisClass.getClassPool().get(getClassName());
  }
  
  public String getClassName() {
    String cname;
    ConstPool cp = getConstPool();
    int pos = this.currentPos;
    int c = this.iterator.byteAt(pos);
    int index = this.iterator.u16bitAt(pos + 1);
    if (c == 185) {
      cname = cp.getInterfaceMethodrefClassName(index);
    } else {
      cname = cp.getMethodrefClassName(index);
    } 
    if (cname.charAt(0) == '[')
      cname = Descriptor.toClassName(cname); 
    return cname;
  }
  
  public String getMethodName() {
    ConstPool cp = getConstPool();
    int nt = getNameAndType(cp);
    return cp.getUtf8Info(cp.getNameAndTypeName(nt));
  }
  
  public CtMethod getMethod() throws NotFoundException {
    return getCtClass().getMethod(getMethodName(), getSignature());
  }
  
  public String getSignature() {
    ConstPool cp = getConstPool();
    int nt = getNameAndType(cp);
    return cp.getUtf8Info(cp.getNameAndTypeDescriptor(nt));
  }
  
  public CtClass[] mayThrow() {
    return super.mayThrow();
  }
  
  public boolean isSuper() {
    return (this.iterator.byteAt(this.currentPos) == 183 && 
      !where().getDeclaringClass().getName().equals(getClassName()));
  }
  
  public void replace(String statement) throws CannotCompileException {
    String classname, methodname, signature;
    int opcodeSize;
    this.thisClass.getClassFile();
    ConstPool constPool = getConstPool();
    int pos = this.currentPos;
    int index = this.iterator.u16bitAt(pos + 1);
    int c = this.iterator.byteAt(pos);
    if (c == 185) {
      opcodeSize = 5;
      classname = constPool.getInterfaceMethodrefClassName(index);
      methodname = constPool.getInterfaceMethodrefName(index);
      signature = constPool.getInterfaceMethodrefType(index);
    } else if (c == 184 || c == 183 || c == 182) {
      opcodeSize = 3;
      classname = constPool.getMethodrefClassName(index);
      methodname = constPool.getMethodrefName(index);
      signature = constPool.getMethodrefType(index);
    } else {
      throw new CannotCompileException("not method invocation");
    } 
    Javac jc = new Javac(this.thisClass);
    ClassPool cp = this.thisClass.getClassPool();
    CodeAttribute ca = this.iterator.get();
    try {
      CtClass[] params = Descriptor.getParameterTypes(signature, cp);
      CtClass retType = Descriptor.getReturnType(signature, cp);
      int paramVar = ca.getMaxLocals();
      jc.recordParams(classname, params, true, paramVar, 
          withinStatic());
      int retVar = jc.recordReturnType(retType, true);
      if (c == 184) {
        jc.recordStaticProceed(classname, methodname);
      } else if (c == 183) {
        jc.recordSpecialProceed("$0", classname, methodname, signature, index);
      } else {
        jc.recordProceed("$0", methodname);
      } 
      checkResultValue(retType, statement);
      Bytecode bytecode = jc.getBytecode();
      storeStack(params, (c == 184), paramVar, bytecode);
      jc.recordLocalVariables(ca, pos);
      if (retType != CtClass.voidType) {
        bytecode.addConstZero(retType);
        bytecode.addStore(retVar, retType);
      } 
      jc.compileStmnt(statement);
      if (retType != CtClass.voidType)
        bytecode.addLoad(retVar, retType); 
      replace0(pos, bytecode, opcodeSize);
    } catch (CompileError e) {
      throw new CannotCompileException(e);
    } catch (NotFoundException e) {
      throw new CannotCompileException(e);
    } catch (BadBytecode e) {
      throw new CannotCompileException("broken method");
    } 
  }
}


/* Location:              C:\Users\Joona\Downloads\Cupid.jar\\us\myles\viaversion\libs\javassist\expr\MethodCall.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */