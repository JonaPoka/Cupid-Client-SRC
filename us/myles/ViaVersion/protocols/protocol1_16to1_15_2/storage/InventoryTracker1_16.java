package us.myles.ViaVersion.protocols.protocol1_16to1_15_2.storage;

import us.myles.ViaVersion.api.data.StoredObject;
import us.myles.ViaVersion.api.data.UserConnection;

public class InventoryTracker1_16 extends StoredObject {
  private short inventory = -1;
  
  public InventoryTracker1_16(UserConnection user) {
    super(user);
  }
  
  public short getInventory() {
    return this.inventory;
  }
  
  public void setInventory(short inventory) {
    this.inventory = inventory;
  }
}


/* Location:              C:\Users\Joona\Downloads\Cupid.jar\\us\myles\ViaVersion\protocols\protocol1_16to1_15_2\storage\InventoryTracker1_16.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */