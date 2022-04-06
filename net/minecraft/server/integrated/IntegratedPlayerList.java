package net.minecraft.server.integrated;

import com.mojang.authlib.GameProfile;
import java.net.SocketAddress;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.ServerConfigurationManager;

public class IntegratedPlayerList extends ServerConfigurationManager {
  private NBTTagCompound hostPlayerData;
  
  public IntegratedPlayerList(IntegratedServer p_i1314_1_) {
    super(p_i1314_1_);
    setViewDistance(10);
  }
  
  protected void writePlayerData(EntityPlayerMP playerIn) {
    if (playerIn.getName().equals(getServerInstance().getServerOwner())) {
      this.hostPlayerData = new NBTTagCompound();
      playerIn.writeToNBT(this.hostPlayerData);
    } 
    super.writePlayerData(playerIn);
  }
  
  public String allowUserToConnect(SocketAddress address, GameProfile profile) {
    return (profile.getName().equalsIgnoreCase(getServerInstance().getServerOwner()) && getPlayerByUsername(profile.getName()) != null) ? "That name is already taken." : super.allowUserToConnect(address, profile);
  }
  
  public IntegratedServer getServerInstance() {
    return (IntegratedServer)super.getServerInstance();
  }
  
  public NBTTagCompound getHostPlayerData() {
    return this.hostPlayerData;
  }
}


/* Location:              C:\Users\Joona\Downloads\Cupid.jar!\net\minecraft\server\integrated\IntegratedPlayerList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */