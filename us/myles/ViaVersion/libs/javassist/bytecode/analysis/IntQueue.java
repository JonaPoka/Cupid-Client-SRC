package us.myles.viaversion.libs.javassist.bytecode.analysis;

import java.util.NoSuchElementException;

class IntQueue {
  private Entry head;
  
  private Entry tail;
  
  private static class Entry {
    private Entry next;
    
    private int value;
    
    private Entry(int value) {
      this.value = value;
    }
  }
  
  void add(int value) {
    Entry entry = new Entry(value);
    if (this.tail != null)
      this.tail.next = entry; 
    this.tail = entry;
    if (this.head == null)
      this.head = entry; 
  }
  
  boolean isEmpty() {
    return (this.head == null);
  }
  
  int take() {
    if (this.head == null)
      throw new NoSuchElementException(); 
    int value = this.head.value;
    this.head = this.head.next;
    if (this.head == null)
      this.tail = null; 
    return value;
  }
}


/* Location:              C:\Users\Joona\Downloads\Cupid.jar\\us\myles\viaversion\libs\javassist\bytecode\analysis\IntQueue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */