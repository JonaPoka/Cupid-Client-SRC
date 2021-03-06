package net.minecraft.client.gui;

import net.minecraft.util.IProgressUpdate;

public class GuiScreenWorking extends GuiScreen implements IProgressUpdate {
  private String field_146591_a = "";
  
  private String field_146589_f = "";
  
  private int progress;
  
  private boolean doneWorking;
  
  public void displaySavingString(String message) {
    resetProgressAndMessage(message);
  }
  
  public void resetProgressAndMessage(String message) {
    this.field_146591_a = message;
    displayLoadingString("Working...");
  }
  
  public void displayLoadingString(String message) {
    this.field_146589_f = message;
    setLoadingProgress(0);
  }
  
  public void setLoadingProgress(int progress) {
    this.progress = progress;
  }
  
  public void setDoneWorking() {
    this.doneWorking = true;
  }
  
  public void drawScreen(int mouseX, int mouseY, float partialTicks) {
    if (this.doneWorking) {
      if (!this.mc.func_181540_al())
        this.mc.displayGuiScreen((GuiScreen)null); 
    } else {
      drawDefaultBackground();
      drawCenteredString(this.fontRendererObj, this.field_146591_a, this.width / 2, 70, 16777215);
      drawCenteredString(this.fontRendererObj, this.field_146589_f + " " + this.progress + "%", this.width / 2, 90, 16777215);
      super.drawScreen(mouseX, mouseY, partialTicks);
    } 
  }
}


/* Location:              C:\Users\Joona\Downloads\Cupid.jar!\net\minecraft\client\gui\GuiScreenWorking.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */