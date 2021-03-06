package us.myles.ViaVersion.bungee.listeners;

import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import us.myles.ViaVersion.api.Via;
import us.myles.ViaVersion.update.UpdateUtil;

public class UpdateListener implements Listener {
  @EventHandler
  public void onJoin(PostLoginEvent e) {
    if (e.getPlayer().hasPermission("viaversion.update") && 
      Via.getConfig().isCheckForUpdates())
      UpdateUtil.sendUpdateMessage(e.getPlayer().getUniqueId()); 
  }
}


/* Location:              C:\Users\Joona\Downloads\Cupid.jar\\us\myles\ViaVersion\bungee\listeners\UpdateListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */