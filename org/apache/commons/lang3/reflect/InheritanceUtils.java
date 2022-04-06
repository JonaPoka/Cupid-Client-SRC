package org.apache.commons.lang3.reflect;

import org.apache.commons.lang3.BooleanUtils;

public class InheritanceUtils {
  public static int distance(Class<?> child, Class<?> parent) {
    if (child == null || parent == null)
      return -1; 
    if (child.equals(parent))
      return 0; 
    Class<?> cParent = child.getSuperclass();
    int d = BooleanUtils.toInteger(parent.equals(cParent));
    if (d == 1)
      return d; 
    d += distance(cParent, parent);
    return (d > 0) ? (d + 1) : -1;
  }
}


/* Location:              C:\Users\Joona\Downloads\Cupid.jar!\org\apache\commons\lang3\reflect\InheritanceUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */