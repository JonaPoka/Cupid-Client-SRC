package us.myles.ViaVersion.exception;

import us.myles.ViaVersion.api.Via;

public class CancelException extends Exception {
  public static final CancelException CACHED = new CancelException("This packet is supposed to be cancelled; If you have debug enabled, you can ignore these") {
      public Throwable fillInStackTrace() {
        return this;
      }
    };
  
  public CancelException() {}
  
  public CancelException(String message) {
    super(message);
  }
  
  public CancelException(String message, Throwable cause) {
    super(message, cause);
  }
  
  public CancelException(Throwable cause) {
    super(cause);
  }
  
  public CancelException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
  
  public static CancelException generate() {
    return Via.getManager().isDebug() ? new CancelException() : CACHED;
  }
}


/* Location:              C:\Users\Joona\Downloads\Cupid.jar\\us\myles\ViaVersion\exception\CancelException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */