package io.netty.channel.socket;

import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelConfig;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelOption;
import io.netty.channel.DefaultChannelConfig;
import io.netty.channel.MessageSizeEstimator;
import io.netty.channel.RecvByteBufAllocator;
import io.netty.util.NetUtil;
import java.net.ServerSocket;
import java.net.SocketException;
import java.util.Map;

public class DefaultServerSocketChannelConfig extends DefaultChannelConfig implements ServerSocketChannelConfig {
  protected final ServerSocket javaSocket;
  
  private volatile int backlog = NetUtil.SOMAXCONN;
  
  public DefaultServerSocketChannelConfig(ServerSocketChannel channel, ServerSocket javaSocket) {
    super((Channel)channel);
    if (javaSocket == null)
      throw new NullPointerException("javaSocket"); 
    this.javaSocket = javaSocket;
  }
  
  public Map<ChannelOption<?>, Object> getOptions() {
    return getOptions(super.getOptions(), new ChannelOption[] { ChannelOption.SO_RCVBUF, ChannelOption.SO_REUSEADDR, ChannelOption.SO_BACKLOG });
  }
  
  public <T> T getOption(ChannelOption<T> option) {
    if (option == ChannelOption.SO_RCVBUF)
      return (T)Integer.valueOf(getReceiveBufferSize()); 
    if (option == ChannelOption.SO_REUSEADDR)
      return (T)Boolean.valueOf(isReuseAddress()); 
    if (option == ChannelOption.SO_BACKLOG)
      return (T)Integer.valueOf(getBacklog()); 
    return (T)super.getOption(option);
  }
  
  public <T> boolean setOption(ChannelOption<T> option, T value) {
    validate(option, value);
    if (option == ChannelOption.SO_RCVBUF) {
      setReceiveBufferSize(((Integer)value).intValue());
    } else if (option == ChannelOption.SO_REUSEADDR) {
      setReuseAddress(((Boolean)value).booleanValue());
    } else if (option == ChannelOption.SO_BACKLOG) {
      setBacklog(((Integer)value).intValue());
    } else {
      return super.setOption(option, value);
    } 
    return true;
  }
  
  public boolean isReuseAddress() {
    try {
      return this.javaSocket.getReuseAddress();
    } catch (SocketException e) {
      throw new ChannelException(e);
    } 
  }
  
  public ServerSocketChannelConfig setReuseAddress(boolean reuseAddress) {
    try {
      this.javaSocket.setReuseAddress(reuseAddress);
    } catch (SocketException e) {
      throw new ChannelException(e);
    } 
    return this;
  }
  
  public int getReceiveBufferSize() {
    try {
      return this.javaSocket.getReceiveBufferSize();
    } catch (SocketException e) {
      throw new ChannelException(e);
    } 
  }
  
  public ServerSocketChannelConfig setReceiveBufferSize(int receiveBufferSize) {
    try {
      this.javaSocket.setReceiveBufferSize(receiveBufferSize);
    } catch (SocketException e) {
      throw new ChannelException(e);
    } 
    return this;
  }
  
  public ServerSocketChannelConfig setPerformancePreferences(int connectionTime, int latency, int bandwidth) {
    this.javaSocket.setPerformancePreferences(connectionTime, latency, bandwidth);
    return this;
  }
  
  public int getBacklog() {
    return this.backlog;
  }
  
  public ServerSocketChannelConfig setBacklog(int backlog) {
    if (backlog < 0)
      throw new IllegalArgumentException("backlog: " + backlog); 
    this.backlog = backlog;
    return this;
  }
  
  public ServerSocketChannelConfig setConnectTimeoutMillis(int connectTimeoutMillis) {
    super.setConnectTimeoutMillis(connectTimeoutMillis);
    return this;
  }
  
  public ServerSocketChannelConfig setMaxMessagesPerRead(int maxMessagesPerRead) {
    super.setMaxMessagesPerRead(maxMessagesPerRead);
    return this;
  }
  
  public ServerSocketChannelConfig setWriteSpinCount(int writeSpinCount) {
    super.setWriteSpinCount(writeSpinCount);
    return this;
  }
  
  public ServerSocketChannelConfig setAllocator(ByteBufAllocator allocator) {
    super.setAllocator(allocator);
    return this;
  }
  
  public ServerSocketChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator allocator) {
    super.setRecvByteBufAllocator(allocator);
    return this;
  }
  
  public ServerSocketChannelConfig setAutoRead(boolean autoRead) {
    super.setAutoRead(autoRead);
    return this;
  }
  
  public ServerSocketChannelConfig setWriteBufferHighWaterMark(int writeBufferHighWaterMark) {
    super.setWriteBufferHighWaterMark(writeBufferHighWaterMark);
    return this;
  }
  
  public ServerSocketChannelConfig setWriteBufferLowWaterMark(int writeBufferLowWaterMark) {
    super.setWriteBufferLowWaterMark(writeBufferLowWaterMark);
    return this;
  }
  
  public ServerSocketChannelConfig setMessageSizeEstimator(MessageSizeEstimator estimator) {
    super.setMessageSizeEstimator(estimator);
    return this;
  }
}


/* Location:              C:\Users\Joona\Downloads\Cupid.jar!\io\netty\channel\socket\DefaultServerSocketChannelConfig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */