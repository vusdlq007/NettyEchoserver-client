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
  //bossGroup 클라이언트의 연결을 수락하는 부모 스레드 그룹
    //NioEventLoopGroup(인수) 스레드 그룹 내에서 생성할 최대 스레드 수 1이므로 단일 스레드

    EventLoopGroup workerGroup = new NioEventLoopGroup();
//연결된 클라이언트 소켓으로부터 데이터 입출력(I/O) 및 이벤트처리를 담당하는 자식 쓰레드 그룹
//생성자에 인수가 없으므로 CPU 코어 수에 따른 쓰레드의 수가 설정된다.


     
    try{
      ServerBootstrap b = new ServerBootstrap();
      b.group(bossGroup, workerGroup)
        .channel(NioServerSocketChannel.class)
        .childHandler(new ChannelInitializer <SocketChannel>() {
          @Override
          protected void initChannel(SocketChannel ch) {
            ChannelPipeline p = ch.pipeline();
            p.addLast(new EchoSeverHandler()); //1. 접속된 클라이언트로부터 수신된 데이터를 처리할 핸들러 지정
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
