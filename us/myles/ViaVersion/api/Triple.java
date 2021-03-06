package us.myles.ViaVersion.api;

import java.util.Objects;
import org.jetbrains.annotations.Nullable;

public class Triple<A, B, C> {
  private final A first;
  
  private final B second;
  
  private final C third;
  
  public Triple(@Nullable A first, @Nullable B second, @Nullable C third) {
    this.first = first;
    this.second = second;
    this.third = third;
  }
  
  @Nullable
  public A getFirst() {
    return this.first;
  }
  
  @Nullable
  public B getSecond() {
    return this.second;
  }
  
  @Nullable
  public C getThird() {
    return this.third;
  }
  
  public String toString() {
    return "Triple{" + this.first + ", " + this.second + ", " + this.third + '}';
  }
  
  public boolean equals(Object o) {
    if (this == o)
      return true; 
    if (o == null || getClass() != o.getClass())
      return false; 
    Triple<?, ?, ?> triple = (Triple<?, ?, ?>)o;
    if (!Objects.equals(this.first, triple.first))
      return false; 
    if (!Objects.equals(this.second, triple.second))
      return false; 
    return Objects.equals(this.third, triple.third);
  }
  
  public int hashCode() {
    int result = (this.first != null) ? this.first.hashCode() : 0;
    result = 31 * result + ((this.second != null) ? this.second.hashCode() : 0);
    result = 31 * result + ((this.third != null) ? this.third.hashCode() : 0);
    return result;
  }
}


/* Location:              C:\Users\Joona\Downloads\Cupid.jar\\us\myles\ViaVersion\api\Triple.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */