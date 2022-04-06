package io.netty.util.internal.logging;

class FormattingTuple {
  static final FormattingTuple NULL = new FormattingTuple(null);
  
  private final String message;
  
  private final Throwable throwable;
  
  private final Object[] argArray;
  
  FormattingTuple(String message) {
    this(message, null, null);
  }
  
  FormattingTuple(String message, Object[] argArray, Throwable throwable) {
    this.message = message;
    this.throwable = throwable;
    if (throwable == null) {
      this.argArray = argArray;
    } else {
      this.argArray = trimmedCopy(argArray);
    } 
  }
  
  static Object[] trimmedCopy(Object[] argArray) {
    if (argArray == null || argArray.length == 0)
      throw new IllegalStateException("non-sensical empty or null argument array"); 
    int trimemdLen = argArray.length - 1;
    Object[] trimmed = new Object[trimemdLen];
    System.arraycopy(argArray, 0, trimmed, 0, trimemdLen);
    return trimmed;
  }
  
  public String getMessage() {
    return this.message;
  }
  
  public Object[] getArgArray() {
    return this.argArray;
  }
  
  public Throwable getThrowable() {
    return this.throwable;
  }
}


/* Location:              C:\Users\Joona\Downloads\Cupid.jar!\io\nett\\util\internal\logging\FormattingTuple.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */