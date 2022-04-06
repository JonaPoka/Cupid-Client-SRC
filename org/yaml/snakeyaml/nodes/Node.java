package org.yaml.snakeyaml.nodes;

import org.yaml.snakeyaml.error.Mark;

public abstract class Node {
  private Tag tag;
  
  private Mark startMark;
  
  protected Mark endMark;
  
  private Class<? extends Object> type;
  
  private boolean twoStepsConstruction;
  
  private String anchor;
  
  protected boolean resolved;
  
  protected Boolean useClassConstructor;
  
  public Node(Tag tag, Mark startMark, Mark endMark) {
    setTag(tag);
    this.startMark = startMark;
    this.endMark = endMark;
    this.type = Object.class;
    this.twoStepsConstruction = false;
    this.resolved = true;
    this.useClassConstructor = null;
  }
  
  public Tag getTag() {
    return this.tag;
  }
  
  public Mark getEndMark() {
    return this.endMark;
  }
  
  public abstract NodeId getNodeId();
  
  public Mark getStartMark() {
    return this.startMark;
  }
  
  public void setTag(Tag tag) {
    if (tag == null)
      throw new NullPointerException("tag in a Node is required."); 
    this.tag = tag;
  }
  
  public final boolean equals(Object obj) {
    return super.equals(obj);
  }
  
  public Class<? extends Object> getType() {
    return this.type;
  }
  
  public void setType(Class<? extends Object> type) {
    if (!type.isAssignableFrom(this.type))
      this.type = type; 
  }
  
  public void setTwoStepsConstruction(boolean twoStepsConstruction) {
    this.twoStepsConstruction = twoStepsConstruction;
  }
  
  public boolean isTwoStepsConstruction() {
    return this.twoStepsConstruction;
  }
  
  public final int hashCode() {
    return super.hashCode();
  }
  
  public boolean useClassConstructor() {
    if (this.useClassConstructor == null) {
      if (!this.tag.isSecondary() && this.resolved && !Object.class.equals(this.type) && !this.tag.equals(Tag.NULL))
        return true; 
      if (this.tag.isCompatible(getType()))
        return true; 
      return false;
    } 
    return this.useClassConstructor.booleanValue();
  }
  
  public void setUseClassConstructor(Boolean useClassConstructor) {
    this.useClassConstructor = useClassConstructor;
  }
  
  @Deprecated
  public boolean isResolved() {
    return this.resolved;
  }
  
  public String getAnchor() {
    return this.anchor;
  }
  
  public void setAnchor(String anchor) {
    this.anchor = anchor;
  }
}


/* Location:              C:\Users\Joona\Downloads\Cupid.jar!\org\yaml\snakeyaml\nodes\Node.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */