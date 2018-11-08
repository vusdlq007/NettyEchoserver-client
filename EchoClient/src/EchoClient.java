import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
 
public final class EchoClient {
 
  public static void main(String[] args) throws Exception{
    EventLoopGroup  group = new NioEventLoopGroup();
 
    try {
      Bootstrap b = new Bootstrap();
      b.group(group)//1. �̺�Ʈ �����׷� ����(������ �ٸ��� 1���� - ����� ä���� �ϳ��� ����)
        .channel(NioSocketChannel.class)//2. ä���� ��������(NIO ����ä�η� ����)
        .handler(new ChannelInitializer<SocketChannel>() {//3.ä�������� ���� ������ �Ϲ� ��Ĺä�� Ŭ���� SocketChannel ����
          
          protected void initChannel(SocketChannel ch) throws Exception {
            ChannelPipeline p = ch.pipeline();
            p.addLast(new EchoClientHandler());
             
          }
        });
        ChannelFuture f = b.connect("localhost",8889).sync(); //4. �񵿱� ����� �޼ҵ� connect ȣ��
        //4.1 connect() : �޼����� ȣ������ �񵿱� �޼����� ó�� ��� Ȯ��,
        //4.2.ChannelFuture ��ü�� sync �޼���� ChannelFuture ��ü�� ��û�� �Ϸ�ɶ����� ���, ��, ��û�� �����ϸ� ���ܸ� ������.
         
        f.channel().closeFuture().sync();
    }
    finally {
      group.shutdownGracefully();
    }
     
  }
 
}

