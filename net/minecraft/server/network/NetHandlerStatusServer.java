package net.minecraft.server.network;

import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.status.INetHandlerStatusServer;
import net.minecraft.network.status.client.C00PacketServerQuery;
import net.minecraft.network.status.client.C01PacketPing;
import net.minecraft.network.status.server.S00PacketServerInfo;
import net.minecraft.network.status.server.S01PacketPong;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;

public class NetHandlerStatusServer implements INetHandlerStatusServer {
  private static final IChatComponent field_183007_a = (IChatComponent)new ChatComponentText("Status request has been handled.");
  
  private final MinecraftServer server;
  
  private final NetworkManager networkManager;
  
  private boolean field_183008_d;
  
  public NetHandlerStatusServer(MinecraftServer serverIn, NetworkManager netManager) {
    this.server = serverIn;
    this.networkManager = netManager;
  }
  
  public void onDisconnect(IChatComponent reason) {}
  
  public void processServerQuery(C00PacketServerQuery packetIn) {
    if (this.field_183008_d) {
      this.networkManager.closeChannel(field_183007_a);
    } else {
      this.field_183008_d = true;
      this.networkManager.sendPacket((Packet)new S00PacketServerInfo(this.server.getServerStatusResponse()));
    } 
  }
  
  public void processPing(C01PacketPing packetIn) {
    this.networkManager.sendPacket((Packet)new S01PacketPong(packetIn.getClientTime()));
    this.networkManager.closeChannel(field_183007_a);
  }
}


/* Location:              C:\Users\Joona\Downloads\Cupid.jar!\net\minecraft\server\network\NetHandlerStatusServer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */