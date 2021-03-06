package us.myles.viaversion.libs.javassist.tools;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.PrintWriter;
import us.myles.viaversion.libs.javassist.bytecode.ClassFile;
import us.myles.viaversion.libs.javassist.bytecode.ClassFilePrinter;

public class Dump {
  public static void main(String[] args) throws Exception {
    if (args.length != 1) {
      System.err.println("Usage: java Dump <class file name>");
      return;
    } 
    DataInputStream in = new DataInputStream(new FileInputStream(args[0]));
    ClassFile w = new ClassFile(in);
    PrintWriter out = new PrintWriter(System.out, true);
    out.println("*** constant pool ***");
    w.getConstPool().print(out);
    out.println();
    out.println("*** members ***");
    ClassFilePrinter.print(w, out);
  }
}


/* Location:              C:\Users\Joona\Downloads\Cupid.jar\\us\myles\viaversion\libs\javassist\tools\Dump.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */