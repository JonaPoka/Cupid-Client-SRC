package us.myles.viaversion.libs.fastutil.ints;

import java.io.Serializable;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.IntPredicate;
import us.myles.viaversion.libs.fastutil.BigArrays;
import us.myles.viaversion.libs.fastutil.bytes.ByteIterator;
import us.myles.viaversion.libs.fastutil.shorts.ShortIterator;

public final class IntIterators {
  public static class EmptyIterator implements IntListIterator, Serializable, Cloneable {
    private static final long serialVersionUID = -7046029254386353129L;
    
    public boolean hasNext() {
      return false;
    }
    
    public boolean hasPrevious() {
      return false;
    }
    
    public int nextInt() {
      throw new NoSuchElementException();
    }
    
    public int previousInt() {
      throw new NoSuchElementException();
    }
    
    public int nextIndex() {
      return 0;
    }
    
    public int previousIndex() {
      return -1;
    }
    
    public int skip(int n) {
      return 0;
    }
    
    public int back(int n) {
      return 0;
    }
    
    public Object clone() {
      return IntIterators.EMPTY_ITERATOR;
    }
    
    private Object readResolve() {
      return IntIterators.EMPTY_ITERATOR;
    }
  }
  
  public static final EmptyIterator EMPTY_ITERATOR = new EmptyIterator();
  
  private static class SingletonIterator implements IntListIterator {
    private final int element;
    
    private int curr;
    
    public SingletonIterator(int element) {
      this.element = element;
    }
    
    public boolean hasNext() {
      return (this.curr == 0);
    }
    
    public boolean hasPrevious() {
      return (this.curr == 1);
    }
    
    public int nextInt() {
      if (!hasNext())
        throw new NoSuchElementException(); 
      this.curr = 1;
      return this.element;
    }
    
    public int previousInt() {
      if (!hasPrevious())
        throw new NoSuchElementException(); 
      this.curr = 0;
      return this.element;
    }
    
    public int nextIndex() {
      return this.curr;
    }
    
    public int previousIndex() {
      return this.curr - 1;
    }
  }
  
  public static IntListIterator singleton(int element) {
    return new SingletonIterator(element);
  }
  
  private static class ArrayIterator implements IntListIterator {
    private final int[] array;
    
    private final int offset;
    
    private final int length;
    
    private int curr;
    
    public ArrayIterator(int[] array, int offset, int length) {
      this.array = array;
      this.offset = offset;
      this.length = length;
    }
    
    public boolean hasNext() {
      return (this.curr < this.length);
    }
    
    public boolean hasPrevious() {
      return (this.curr > 0);
    }
    
    public int nextInt() {
      if (!hasNext())
        throw new NoSuchElementException(); 
      return this.array[this.offset + this.curr++];
    }
    
    public int previousInt() {
      if (!hasPrevious())
        throw new NoSuchElementException(); 
      return this.array[this.offset + --this.curr];
    }
    
    public int skip(int n) {
      if (n <= this.length - this.curr) {
        this.curr += n;
        return n;
      } 
      n = this.length - this.curr;
      this.curr = this.length;
      return n;
    }
    
    public int back(int n) {
      if (n <= this.curr) {
        this.curr -= n;
        return n;
      } 
      n = this.curr;
      this.curr = 0;
      return n;
    }
    
    public int nextIndex() {
      return this.curr;
    }
    
    public int previousIndex() {
      return this.curr - 1;
    }
  }
  
  public static IntListIterator wrap(int[] array, int offset, int length) {
    IntArrays.ensureOffsetLength(array, offset, length);
    return new ArrayIterator(array, offset, length);
  }
  
  public static IntListIterator wrap(int[] array) {
    return new ArrayIterator(array, 0, array.length);
  }
  
  public static int unwrap(IntIterator i, int[] array, int offset, int max) {
    if (max < 0)
      throw new IllegalArgumentException("The maximum number of elements (" + max + ") is negative"); 
    if (offset < 0 || offset + max > array.length)
      throw new IllegalArgumentException(); 
    int j = max;
    while (j-- != 0 && i.hasNext())
      array[offset++] = i.nextInt(); 
    return max - j - 1;
  }
  
  public static int unwrap(IntIterator i, int[] array) {
    return unwrap(i, array, 0, array.length);
  }
  
  public static int[] unwrap(IntIterator i, int max) {
    if (max < 0)
      throw new IllegalArgumentException("The maximum number of elements (" + max + ") is negative"); 
    int[] array = new int[16];
    int j = 0;
    while (max-- != 0 && i.hasNext()) {
      if (j == array.length)
        array = IntArrays.grow(array, j + 1); 
      array[j++] = i.nextInt();
    } 
    return IntArrays.trim(array, j);
  }
  
  public static int[] unwrap(IntIterator i) {
    return unwrap(i, 2147483647);
  }
  
  public static long unwrap(IntIterator i, int[][] array, long offset, long max) {
    // Byte code:
    //   0: lload #4
    //   2: lconst_0
    //   3: lcmp
    //   4: ifge -> 40
    //   7: new java/lang/IllegalArgumentException
    //   10: dup
    //   11: new java/lang/StringBuilder
    //   14: dup
    //   15: invokespecial <init> : ()V
    //   18: ldc 'The maximum number of elements ('
    //   20: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   23: lload #4
    //   25: invokevirtual append : (J)Ljava/lang/StringBuilder;
    //   28: ldc ') is negative'
    //   30: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   33: invokevirtual toString : ()Ljava/lang/String;
    //   36: invokespecial <init> : (Ljava/lang/String;)V
    //   39: athrow
    //   40: lload_2
    //   41: lconst_0
    //   42: lcmp
    //   43: iflt -> 58
    //   46: lload_2
    //   47: lload #4
    //   49: ladd
    //   50: aload_1
    //   51: invokestatic length : ([[I)J
    //   54: lcmp
    //   55: ifle -> 66
    //   58: new java/lang/IllegalArgumentException
    //   61: dup
    //   62: invokespecial <init> : ()V
    //   65: athrow
    //   66: lload #4
    //   68: lstore #6
    //   70: lload #6
    //   72: dup2
    //   73: lconst_1
    //   74: lsub
    //   75: lstore #6
    //   77: lconst_0
    //   78: lcmp
    //   79: ifeq -> 109
    //   82: aload_0
    //   83: invokeinterface hasNext : ()Z
    //   88: ifeq -> 109
    //   91: aload_1
    //   92: lload_2
    //   93: dup2
    //   94: lconst_1
    //   95: ladd
    //   96: lstore_2
    //   97: aload_0
    //   98: invokeinterface nextInt : ()I
    //   103: invokestatic set : ([[IJI)V
    //   106: goto -> 70
    //   109: lload #4
    //   111: lload #6
    //   113: lsub
    //   114: lconst_1
    //   115: lsub
    //   116: lreturn
    // Line number table:
    //   Java source line number -> byte code offset
    //   #349	-> 0
    //   #350	-> 7
    //   #351	-> 40
    //   #352	-> 58
    //   #353	-> 66
    //   #354	-> 70
    //   #355	-> 91
    //   #356	-> 109
    // Local variable table:
    //   start	length	slot	name	descriptor
    //   0	117	0	i	Lus/myles/viaversion/libs/fastutil/ints/IntIterator;
    //   0	117	1	array	[[I
    //   0	117	2	offset	J
    //   0	117	4	max	J
    //   70	47	6	j	J
  }
  
  public static long unwrap(IntIterator i, int[][] array) {
    return unwrap(i, array, 0L, BigArrays.length(array));
  }
  
  public static int unwrap(IntIterator i, IntCollection c, int max) {
    if (max < 0)
      throw new IllegalArgumentException("The maximum number of elements (" + max + ") is negative"); 
    int j = max;
    while (j-- != 0 && i.hasNext())
      c.add(i.nextInt()); 
    return max - j - 1;
  }
  
  public static int[][] unwrapBig(IntIterator i, long max) {
    // Byte code:
    //   0: lload_1
    //   1: lconst_0
    //   2: lcmp
    //   3: ifge -> 38
    //   6: new java/lang/IllegalArgumentException
    //   9: dup
    //   10: new java/lang/StringBuilder
    //   13: dup
    //   14: invokespecial <init> : ()V
    //   17: ldc 'The maximum number of elements ('
    //   19: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   22: lload_1
    //   23: invokevirtual append : (J)Ljava/lang/StringBuilder;
    //   26: ldc ') is negative'
    //   28: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   31: invokevirtual toString : ()Ljava/lang/String;
    //   34: invokespecial <init> : (Ljava/lang/String;)V
    //   37: athrow
    //   38: ldc2_w 16
    //   41: invokestatic newBigArray : (J)[[I
    //   44: astore_3
    //   45: lconst_0
    //   46: lstore #4
    //   48: lload_1
    //   49: dup2
    //   50: lconst_1
    //   51: lsub
    //   52: lstore_1
    //   53: lconst_0
    //   54: lcmp
    //   55: ifeq -> 106
    //   58: aload_0
    //   59: invokeinterface hasNext : ()Z
    //   64: ifeq -> 106
    //   67: lload #4
    //   69: aload_3
    //   70: invokestatic length : ([[I)J
    //   73: lcmp
    //   74: ifne -> 86
    //   77: aload_3
    //   78: lload #4
    //   80: lconst_1
    //   81: ladd
    //   82: invokestatic grow : ([[IJ)[[I
    //   85: astore_3
    //   86: aload_3
    //   87: lload #4
    //   89: dup2
    //   90: lconst_1
    //   91: ladd
    //   92: lstore #4
    //   94: aload_0
    //   95: invokeinterface nextInt : ()I
    //   100: invokestatic set : ([[IJI)V
    //   103: goto -> 48
    //   106: aload_3
    //   107: lload #4
    //   109: invokestatic trim : ([[IJ)[[I
    //   112: areturn
    // Line number table:
    //   Java source line number -> byte code offset
    //   #423	-> 0
    //   #424	-> 6
    //   #425	-> 38
    //   #426	-> 45
    //   #427	-> 48
    //   #428	-> 67
    //   #429	-> 77
    //   #430	-> 86
    //   #432	-> 106
    // Local variable table:
    //   start	length	slot	name	descriptor
    //   0	113	0	i	Lus/myles/viaversion/libs/fastutil/ints/IntIterator;
    //   0	113	1	max	J
    //   45	68	3	array	[[I
    //   48	65	4	j	J
  }
  
  public static int[][] unwrapBig(IntIterator i) {
    return unwrapBig(i, Long.MAX_VALUE);
  }
  
  public static long unwrap(IntIterator i, IntCollection c) {
    long n = 0L;
    while (i.hasNext()) {
      c.add(i.nextInt());
      n++;
    } 
    return n;
  }
  
  public static int pour(IntIterator i, IntCollection s, int max) {
    if (max < 0)
      throw new IllegalArgumentException("The maximum number of elements (" + max + ") is negative"); 
    int j = max;
    while (j-- != 0 && i.hasNext())
      s.add(i.nextInt()); 
    return max - j - 1;
  }
  
  public static int pour(IntIterator i, IntCollection s) {
    return pour(i, s, 2147483647);
  }
  
  public static IntList pour(IntIterator i, int max) {
    IntArrayList l = new IntArrayList();
    pour(i, l, max);
    l.trim();
    return l;
  }
  
  public static IntList pour(IntIterator i) {
    return pour(i, 2147483647);
  }
  
  private static class IteratorWrapper implements IntIterator {
    final Iterator<Integer> i;
    
    public IteratorWrapper(Iterator<Integer> i) {
      this.i = i;
    }
    
    public boolean hasNext() {
      return this.i.hasNext();
    }
    
    public void remove() {
      this.i.remove();
    }
    
    public int nextInt() {
      return ((Integer)this.i.next()).intValue();
    }
  }
  
  public static IntIterator asIntIterator(Iterator<Integer> i) {
    if (i instanceof IntIterator)
      return (IntIterator)i; 
    return new IteratorWrapper(i);
  }
  
  private static class ListIteratorWrapper implements IntListIterator {
    final ListIterator<Integer> i;
    
    public ListIteratorWrapper(ListIterator<Integer> i) {
      this.i = i;
    }
    
    public boolean hasNext() {
      return this.i.hasNext();
    }
    
    public boolean hasPrevious() {
      return this.i.hasPrevious();
    }
    
    public int nextIndex() {
      return this.i.nextIndex();
    }
    
    public int previousIndex() {
      return this.i.previousIndex();
    }
    
    public void set(int k) {
      this.i.set(Integer.valueOf(k));
    }
    
    public void add(int k) {
      this.i.add(Integer.valueOf(k));
    }
    
    public void remove() {
      this.i.remove();
    }
    
    public int nextInt() {
      return ((Integer)this.i.next()).intValue();
    }
    
    public int previousInt() {
      return ((Integer)this.i.previous()).intValue();
    }
  }
  
  public static IntListIterator asIntIterator(ListIterator<Integer> i) {
    if (i instanceof IntListIterator)
      return (IntListIterator)i; 
    return new ListIteratorWrapper(i);
  }
  
  public static boolean any(IntIterator iterator, IntPredicate predicate) {
    return (indexOf(iterator, predicate) != -1);
  }
  
  public static boolean all(IntIterator iterator, IntPredicate predicate) {
    Objects.requireNonNull(predicate);
    while (true) {
      if (!iterator.hasNext())
        return true; 
      if (!predicate.test(iterator.nextInt()))
        return false; 
    } 
  }
  
  public static int indexOf(IntIterator iterator, IntPredicate predicate) {
    Objects.requireNonNull(predicate);
    for (int i = 0; iterator.hasNext(); i++) {
      if (predicate.test(iterator.nextInt()))
        return i; 
    } 
    return -1;
  }
  
  private static class IntervalIterator implements IntListIterator {
    private final int from;
    
    private final int to;
    
    int curr;
    
    public IntervalIterator(int from, int to) {
      this.from = this.curr = from;
      this.to = to;
    }
    
    public boolean hasNext() {
      return (this.curr < this.to);
    }
    
    public boolean hasPrevious() {
      return (this.curr > this.from);
    }
    
    public int nextInt() {
      if (!hasNext())
        throw new NoSuchElementException(); 
      return this.curr++;
    }
    
    public int previousInt() {
      if (!hasPrevious())
        throw new NoSuchElementException(); 
      return --this.curr;
    }
    
    public int nextIndex() {
      return this.curr - this.from;
    }
    
    public int previousIndex() {
      return this.curr - this.from - 1;
    }
    
    public int skip(int n) {
      if (this.curr + n <= this.to) {
        this.curr += n;
        return n;
      } 
      n = this.to - this.curr;
      this.curr = this.to;
      return n;
    }
    
    public int back(int n) {
      if (this.curr - n >= this.from) {
        this.curr -= n;
        return n;
      } 
      n = this.curr - this.from;
      this.curr = this.from;
      return n;
    }
  }
  
  public static IntListIterator fromTo(int from, int to) {
    return new IntervalIterator(from, to);
  }
  
  private static class IteratorConcatenator implements IntIterator {
    final IntIterator[] a;
    
    int offset;
    
    int length;
    
    int lastOffset = -1;
    
    public IteratorConcatenator(IntIterator[] a, int offset, int length) {
      this.a = a;
      this.offset = offset;
      this.length = length;
      advance();
    }
    
    private void advance() {
      while (this.length != 0 && 
        !this.a[this.offset].hasNext()) {
        this.length--;
        this.offset++;
      } 
    }
    
    public boolean hasNext() {
      return (this.length > 0);
    }
    
    public int nextInt() {
      if (!hasNext())
        throw new NoSuchElementException(); 
      int next = this.a[this.lastOffset = this.offset].nextInt();
      advance();
      return next;
    }
    
    public void remove() {
      if (this.lastOffset == -1)
        throw new IllegalStateException(); 
      this.a[this.lastOffset].remove();
    }
    
    public int skip(int n) {
      this.lastOffset = -1;
      int skipped = 0;
      while (skipped < n && this.length != 0) {
        skipped += this.a[this.offset].skip(n - skipped);
        if (this.a[this.offset].hasNext())
          break; 
        this.length--;
        this.offset++;
      } 
      return skipped;
    }
  }
  
  public static IntIterator concat(IntIterator[] a) {
    return concat(a, 0, a.length);
  }
  
  public static IntIterator concat(IntIterator[] a, int offset, int length) {
    return new IteratorConcatenator(a, offset, length);
  }
  
  public static IntIterator unmodifiable(IntIterator i) {
    return (IntIterator)new UnmodifiableIterator(i);
  }
  
  public static IntBidirectionalIterator unmodifiable(IntBidirectionalIterator i) {
    return (IntBidirectionalIterator)new UnmodifiableBidirectionalIterator(i);
  }
  
  public static IntListIterator unmodifiable(IntListIterator i) {
    return (IntListIterator)new UnmodifiableListIterator(i);
  }
  
  public static IntIterator wrap(ByteIterator iterator) {
    return (IntIterator)new ByteIteratorWrapper(iterator);
  }
  
  public static IntIterator wrap(ShortIterator iterator) {
    return (IntIterator)new ShortIteratorWrapper(iterator);
  }
  
  public static class IntIterators {}
  
  public static class IntIterators {}
  
  public static class IntIterators {}
  
  protected static class IntIterators {}
  
  protected static class IntIterators {}
}


/* Location:              C:\Users\Joona\Downloads\Cupid.jar\\us\myles\viaversion\libs\fastutil\ints\IntIterators.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */