import java.nio.charset.Charset;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
 
public class EchoSeverHandler extends ChannelInboundHandlerAdapter{
  //1. �Էµ� �����͸� ó���ϴ� �̺�Ʈ �ڵ鷯 ���
 
	@Override
	  public void channelActive(ChannelHandlerContext ctx){
	    // ���� ä���� ���� Ȱ��ȭ �Ǿ����� ����
	     
	    String sendMessage = "Server Hi!";
	     
	    ByteBuf messageBuffer = Unpooled.buffer();
	    messageBuffer.writeBytes(sendMessage.getBytes());
	     
	    StringBuilder builder = new StringBuilder();
	    builder.append("������ ���ڿ� [");
	    builder.append(sendMessage);
	    builder.append("]");
	     
	    System.out.println(builder.toString());
	    ctx.writeAndFlush(messageBuffer);
	}
	
  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg)  {
    //2. ������ ���� �̺�Ʈ ó�� �޼���. Ŭ���̾�Ʈ�κ��� �������� ������ �̷�������� ��Ƽ�� �ڵ����� ȣ���ϴ� �̺�Ʈ �޼ҵ�
     
    String readMessage = ((ByteBuf) msg).toString(Charset.defaultCharset());
    //3. ���ŵ� �����͸� ������ �ִ� ��Ƽ�� ����Ʈ ���� ��ü�� ���� ���ڿ� ��ü�� �о�´�.
    System.out.println("������ ���ڿ� ["+readMessage +"]");
     
//    ctx.write(msg);
    //���ۿ� msg ����
    //4.ctx�� ä�� ���������ο� ���� �̺�Ʈ�� ó���Ѵ�. ���⼭�� ������ ����� Ŭ���̾�Ʈ ä�η� �Է¹��� �����͸� �״�� �����Ѵ�.
     
  }
   
  @Override
  public void channelReadComplete(ChannelHandlerContext ctx){
    //6. channelRead �̺�Ʈ�� ó���� �Ϸ�� �� �ڵ����� ����Ǵ� �̺�Ʈ �޼���
 
    ctx.flush();
    //���� ����� Ŭ���̾�Ʈ���� ����
    //7. ä�� ������ ���ο� ����� ���۸� ����
  }
   
 
  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    cause.printStackTrace();
    ctx.close();
  }
}

