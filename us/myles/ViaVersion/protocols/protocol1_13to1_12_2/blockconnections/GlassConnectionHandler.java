package us.myles.ViaVersion.protocols.protocol1_13to1_12_2.blockconnections;

import java.util.ArrayList;
import java.util.List;
import us.myles.ViaVersion.api.data.UserConnection;
import us.myles.ViaVersion.api.minecraft.Position;
import us.myles.ViaVersion.protocols.base.ProtocolInfo;

public class GlassConnectionHandler extends AbstractFenceConnectionHandler {
  static List<ConnectionData.ConnectorInitAction> init() {
    List<ConnectionData.ConnectorInitAction> actions = new ArrayList<>(18);
    actions.add((new GlassConnectionHandler("paneConnections")).getInitAction("minecraft:white_stained_glass_pane"));
    actions.add((new GlassConnectionHandler("paneConnections")).getInitAction("minecraft:orange_stained_glass_pane"));
    actions.add((new GlassConnectionHandler("paneConnections")).getInitAction("minecraft:magenta_stained_glass_pane"));
    actions.add((new GlassConnectionHandler("paneConnections")).getInitAction("minecraft:light_blue_stained_glass_pane"));
    actions.add((new GlassConnectionHandler("paneConnections")).getInitAction("minecraft:yellow_stained_glass_pane"));
    actions.add((new GlassConnectionHandler("paneConnections")).getInitAction("minecraft:lime_stained_glass_pane"));
    actions.add((new GlassConnectionHandler("paneConnections")).getInitAction("minecraft:pink_stained_glass_pane"));
    actions.add((new GlassConnectionHandler("paneConnections")).getInitAction("minecraft:gray_stained_glass_pane"));
    actions.add((new GlassConnectionHandler("paneConnections")).getInitAction("minecraft:light_gray_stained_glass_pane"));
    actions.add((new GlassConnectionHandler("paneConnections")).getInitAction("minecraft:cyan_stained_glass_pane"));
    actions.add((new GlassConnectionHandler("paneConnections")).getInitAction("minecraft:purple_stained_glass_pane"));
    actions.add((new GlassConnectionHandler("paneConnections")).getInitAction("minecraft:blue_stained_glass_pane"));
    actions.add((new GlassConnectionHandler("paneConnections")).getInitAction("minecraft:brown_stained_glass_pane"));
    actions.add((new GlassConnectionHandler("paneConnections")).getInitAction("minecraft:green_stained_glass_pane"));
    actions.add((new GlassConnectionHandler("paneConnections")).getInitAction("minecraft:red_stained_glass_pane"));
    actions.add((new GlassConnectionHandler("paneConnections")).getInitAction("minecraft:black_stained_glass_pane"));
    actions.add((new GlassConnectionHandler("paneConnections")).getInitAction("minecraft:glass_pane"));
    actions.add((new GlassConnectionHandler("paneConnections")).getInitAction("minecraft:iron_bars"));
    return actions;
  }
  
  public GlassConnectionHandler(String blockConnections) {
    super(blockConnections);
  }
  
  protected byte getStates(UserConnection user, Position position, int blockState) {
    byte states = super.getStates(user, position, blockState);
    if (states != 0)
      return states; 
    ProtocolInfo protocolInfo = user.getProtocolInfo();
    return (protocolInfo.getServerProtocolVersion() <= 47 && protocolInfo
      .getServerProtocolVersion() != -1) ? 15 : states;
  }
}


/* Location:              C:\Users\Joona\Downloads\Cupid.jar\\us\myles\ViaVersion\protocols\protocol1_13to1_12_2\blockconnections\GlassConnectionHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */