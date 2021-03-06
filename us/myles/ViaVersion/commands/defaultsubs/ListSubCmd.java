package us.myles.ViaVersion.commands.defaultsubs;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import us.myles.ViaVersion.api.Via;
import us.myles.ViaVersion.api.command.ViaCommandSender;
import us.myles.ViaVersion.api.command.ViaSubCommand;
import us.myles.ViaVersion.api.protocol.ProtocolVersion;

public class ListSubCmd extends ViaSubCommand {
  public String name() {
    return "list";
  }
  
  public String description() {
    return "Shows lists of the versions from logged in players";
  }
  
  public String usage() {
    return "list";
  }
  
  public boolean execute(ViaCommandSender sender, String[] args) {
    Map<ProtocolVersion, Set<String>> playerVersions = new TreeMap<>(new Comparator<ProtocolVersion>() {
          public int compare(ProtocolVersion o1, ProtocolVersion o2) {
            return ProtocolVersion.getIndex(o2) - ProtocolVersion.getIndex(o1);
          }
        });
    for (ViaCommandSender p : Via.getPlatform().getOnlinePlayers()) {
      int playerVersion = Via.getAPI().getPlayerVersion(p.getUUID());
      ProtocolVersion key = ProtocolVersion.getProtocol(playerVersion);
      if (!playerVersions.containsKey(key))
        playerVersions.put(key, new HashSet<>()); 
      ((Set<String>)playerVersions.get(key)).add(p.getName());
    } 
    for (Map.Entry<ProtocolVersion, Set<String>> entry : playerVersions.entrySet()) {
      sendMessage(sender, "&8[&6%s&8] (&7%d&8): &b%s", new Object[] { ((ProtocolVersion)entry.getKey()).getName(), Integer.valueOf(((Set)entry.getValue()).size()), entry.getValue() });
    } 
    playerVersions.clear();
    return true;
  }
}


/* Location:              C:\Users\Joona\Downloads\Cupid.jar\\us\myles\ViaVersion\commands\defaultsubs\ListSubCmd.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */