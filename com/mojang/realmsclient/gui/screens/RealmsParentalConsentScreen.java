package com.mojang.realmsclient.gui.screens;

import com.mojang.realmsclient.gui.RealmsConstants;
import com.mojang.realmsclient.util.RealmsUtil;
import net.minecraft.realms.Realms;
import net.minecraft.realms.RealmsButton;
import net.minecraft.realms.RealmsScreen;
import org.lwjgl.opengl.GL11;

public class RealmsParentalConsentScreen extends RealmsScreen {
  private final RealmsScreen nextScreen;
  
  private static final int BUTTON_BACK_ID = 0;
  
  private static final int BUTTON_OK_ID = 1;
  
  private final String line1 = "Recently, Mojang was acquired by Microsoft. Microsoft implements";
  
  private final String line2 = "certain procedures to help protect children and their privacy,";
  
  private final String line3 = "including complying with the Children’s Online Privacy Protection Act (COPPA)";
  
  private final String line4 = "You may need to obtain parental consent before accessing your Realms account.";
  
  private boolean onLink = false;
  
  public RealmsParentalConsentScreen(RealmsScreen nextScreen) {
    this.nextScreen = nextScreen;
  }
  
  public void init() {
    buttonsClear();
    buttonsAdd(newButton(1, width() / 2 - 100, RealmsConstants.row(11), 200, 20, "Go to accounts page"));
    buttonsAdd(newButton(0, width() / 2 - 100, RealmsConstants.row(13), 200, 20, "Back"));
  }
  
  public void tick() {
    super.tick();
  }
  
  public void buttonClicked(RealmsButton button) {
    switch (button.id()) {
      case 1:
        RealmsUtil.browseTo("https://accounts.mojang.com/me/verify/" + Realms.getUUID());
        return;
      case 0:
        Realms.setScreen(this.nextScreen);
        return;
    } 
  }
  
  public void mouseClicked(int x, int y, int buttonNum) {
    if (this.onLink)
      RealmsUtil.browseTo("http://www.ftc.gov/enforcement/rules/rulemaking-regulatory-reform-proceedings/childrens-online-privacy-protection-rule"); 
  }
  
  public void render(int xm, int ym, float a) {
    renderBackground();
    drawCenteredString("Recently, Mojang was acquired by Microsoft. Microsoft implements", width() / 2, 30, 16777215);
    drawCenteredString("certain procedures to help protect children and their privacy,", width() / 2, 45, 16777215);
    drawCenteredString("including complying with the Children’s Online Privacy Protection Act (COPPA)", width() / 2, 60, 16777215);
    drawCenteredString("You may need to obtain parental consent before accessing your Realms account.", width() / 2, 120, 16777215);
    renderLink(xm, ym);
    super.render(xm, ym, a);
  }
  
  private void renderLink(int xm, int ym) {
    String text = getLocalizedString("Read more about COPPA");
    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    GL11.glPushMatrix();
    int textWidth = fontWidth(text);
    int leftPadding = width() / 2 - textWidth / 2;
    int topPadding = 75;
    int x1 = leftPadding;
    int x2 = x1 + textWidth + 1;
    int y1 = topPadding;
    int y2 = y1 + fontLineHeight();
    GL11.glTranslatef(x1, y1, 0.0F);
    if (x1 <= xm && xm <= x2 && y1 <= ym && ym <= y2) {
      this.onLink = true;
      drawString(text, 0, 0, 7107012);
    } else {
      this.onLink = false;
      drawString(text, 0, 0, 3368635);
    } 
    GL11.glPopMatrix();
  }
}


/* Location:              C:\Users\Joona\Downloads\Cupid.jar!\com\mojang\realmsclient\gui\screens\RealmsParentalConsentScreen.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */