package optifine;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiOptionButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;

public class GuiPerformanceSettingsOF extends GuiScreen {
  private GuiScreen prevScreen;
  
  protected String title;
  
  private GameSettings settings;
  
  private static GameSettings.Options[] enumOptions = new GameSettings.Options[] { GameSettings.Options.SMOOTH_FPS, GameSettings.Options.SMOOTH_WORLD, GameSettings.Options.FAST_RENDER, GameSettings.Options.FAST_MATH, GameSettings.Options.CHUNK_UPDATES, GameSettings.Options.CHUNK_UPDATES_DYNAMIC, GameSettings.Options.LAZY_CHUNK_LOADING };
  
  private TooltipManager tooltipManager = new TooltipManager(this);
  
  public GuiPerformanceSettingsOF(GuiScreen p_i52_1_, GameSettings p_i52_2_) {
    this.prevScreen = p_i52_1_;
    this.settings = p_i52_2_;
  }
  
  public void initGui() {
    this.title = I18n.format("of.options.performanceTitle", new Object[0]);
    this.buttonList.clear();
    for (int i = 0; i < enumOptions.length; i++) {
      GameSettings.Options gamesettings$options = enumOptions[i];
      int j = this.width / 2 - 155 + i % 2 * 160;
      int k = this.height / 6 + 21 * i / 2 - 12;
      if (!gamesettings$options.getEnumFloat()) {
        this.buttonList.add(new GuiOptionButtonOF(gamesettings$options.returnEnumOrdinal(), j, k, gamesettings$options, this.settings.getKeyBinding(gamesettings$options)));
      } else {
        this.buttonList.add(new GuiOptionSliderOF(gamesettings$options.returnEnumOrdinal(), j, k, gamesettings$options));
      } 
    } 
    this.buttonList.add(new GuiButton(200, this.width / 2 - 100, this.height / 6 + 168 + 11, I18n.format("gui.done", new Object[0])));
  }
  
  protected void actionPerformed(GuiButton button) {
    if (button.enabled) {
      if (button.id < 200 && button instanceof GuiOptionButton) {
        this.settings.setOptionValue(((GuiOptionButton)button).returnEnumOptions(), 1);
        button.displayString = this.settings.getKeyBinding(GameSettings.Options.getEnumOptions(button.id));
      } 
      if (button.id == 200) {
        this.mc.gameSettings.saveOptions();
        this.mc.displayGuiScreen(this.prevScreen);
      } 
    } 
  }
  
  public void drawScreen(int mouseX, int mouseY, float partialTicks) {
    drawDefaultBackground();
    drawCenteredString(this.fontRendererObj, this.title, this.width / 2, 15, 16777215);
    super.drawScreen(mouseX, mouseY, partialTicks);
    this.tooltipManager.drawTooltips(mouseX, mouseY, this.buttonList);
  }
}


/* Location:              C:\Users\Joona\Downloads\Cupid.jar!\optifine\GuiPerformanceSettingsOF.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */