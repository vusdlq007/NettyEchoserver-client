import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
 
public class EchoServer {
 
  public static void main(String[] args) throws Exception {
 
    EventLoopGroup bossGroup = new NioEventLoopGroup(1);
  //bossGroup Ŭ���̾�Ʈ�� ������ �����ϴ� �θ� ������ �׷�
    //NioEventLoopGroup(�μ�) ������ �׷� ������ ������ �ִ� ������ �� 1�̹Ƿ� ���� ������

    EventLoopGroup workerGroup = new NioEventLoopGroup();
//����� Ŭ���̾�Ʈ �������κ��� ������ �����(I/O) �� �̺�Ʈó���� ����ϴ� �ڽ� ������ �׷�
//�����ڿ� �μ��� �����Ƿ� CPU �ھ� ���� ���� �������� ���� �����ȴ�.


     
    try{
      ServerBootstrap b = new ServerBootstrap();
      b.group(bossGroup, workerGroup)
        .channel(NioServerSocketChannel.class)
        .childHandler(new ChannelInitializer <SocketChannel>() {
          @Override
          protected void initChannel(SocketChannel ch) {
            ChannelPipeline p = ch.pipeline();
            p.addLast(new EchoSeverHandler()); //1. ���ӵ� Ŭ���̾�Ʈ�κ��� ���ŵ� �����͸� ó���� �ڵ鷯 ����
          }
        });
       
        ChannelFuture f = b.bind(8889).sync();
        f.channel().closeFuture().sync();
       
    } finally {
      workerGroup.shutdownGracefully();
      bossGroup.shutdownGracefully();
    }
 
  }
 
}
