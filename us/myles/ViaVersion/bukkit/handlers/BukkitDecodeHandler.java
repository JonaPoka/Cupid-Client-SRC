package us.myles.ViaVersion.bukkit.handlers;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import us.myles.ViaVersion.api.Via;
import us.myles.ViaVersion.api.data.UserConnection;
import us.myles.ViaVersion.bukkit.util.NMSUtil;
import us.myles.ViaVersion.exception.CancelCodecException;
import us.myles.ViaVersion.exception.CancelDecoderException;
import us.myles.ViaVersion.exception.InformativeException;
import us.myles.ViaVersion.packets.State;
import us.myles.ViaVersion.util.PipelineUtil;

public class BukkitDecodeHandler extends ByteToMessageDecoder {
  private final ByteToMessageDecoder minecraftDecoder;
  
  private final UserConnection info;
  
  public BukkitDecodeHandler(UserConnection info, ByteToMessageDecoder minecraftDecoder) {
    this.info = info;
    this.minecraftDecoder = minecraftDecoder;
  }
  
  protected void decode(ChannelHandlerContext ctx, ByteBuf bytebuf, List<Object> list) throws Exception {
    if (!this.info.checkIncomingPacket()) {
      bytebuf.clear();
      throw CancelDecoderException.generate(null);
    } 
    ByteBuf transformedBuf = null;
    try {
      if (this.info.shouldTransformPacket()) {
        transformedBuf = ctx.alloc().buffer().writeBytes(bytebuf);
        this.info.transformIncoming(transformedBuf, CancelDecoderException::generate);
      } 
      try {
        list.addAll(PipelineUtil.callDecode(this.minecraftDecoder, ctx, (transformedBuf == null) ? bytebuf : transformedBuf));
      } catch (InvocationTargetException e) {
        if (e.getCause() instanceof Exception)
          throw (Exception)e.getCause(); 
        if (e.getCause() instanceof Error)
          throw (Error)e.getCause(); 
      } 
    } finally {
      if (transformedBuf != null)
        transformedBuf.release(); 
    } 
  }
  
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    if (PipelineUtil.containsCause(cause, CancelCodecException.class))
      return; 
    super.exceptionCaught(ctx, cause);
    if (!NMSUtil.isDebugPropertySet() && PipelineUtil.containsCause(cause, InformativeException.class) && (this.info
      .getProtocolInfo().getState() != State.HANDSHAKE || Via.getManager().isDebug()))
      cause.printStackTrace(); 
  }
}


/* Location:              C:\Users\Joona\Downloads\Cupid.jar\\us\myles\ViaVersion\bukkit\handlers\BukkitDecodeHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */