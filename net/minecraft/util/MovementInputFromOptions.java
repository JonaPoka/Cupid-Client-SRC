package net.minecraft.util;

import net.minecraft.client.settings.GameSettings;

public class MovementInputFromOptions extends MovementInput {
  private final GameSettings gameSettings;
  
  public MovementInputFromOptions(GameSettings gameSettingsIn) {
    this.gameSettings = gameSettingsIn;
  }
  
  public void updatePlayerMoveState() {
    this.moveStrafe = 0.0F;
    this.moveForward = 0.0F;
    if (this.gameSettings.keyBindForward.isKeyDown())
      this.moveForward++; 
    if (this.gameSettings.keyBindBack.isKeyDown())
      this.moveForward--; 
    if (this.gameSettings.keyBindLeft.isKeyDown())
      this.moveStrafe++; 
    if (this.gameSettings.keyBindRight.isKeyDown())
      this.moveStrafe--; 
    this.jump = this.gameSettings.keyBindJump.isKeyDown();
    this.sneak = this.gameSettings.keyBindSneak.isKeyDown();
    if (this.sneak) {
      this.moveStrafe = (float)(this.moveStrafe * 0.3D);
      this.moveForward = (float)(this.moveForward * 0.3D);
    } 
  }
}


/* Location:              C:\Users\Joona\Downloads\Cupid.jar!\net\minecraf\\util\MovementInputFromOptions.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */