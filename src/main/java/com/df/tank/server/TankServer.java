package com.df.tank.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.ReferenceCountUtil;

import java.nio.ByteBuffer;

public class TankServer {
    public static void main(String[] args) {
        EventLoopGroup bossGroup = new NioEventLoopGroup(); // (1)
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap(); // (2)
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class) // (3)
                    .childHandler(new ChannelInitializer<SocketChannel>() { // (4)
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new DiscardServerHandler());
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128)          // (5)
                    .childOption(ChannelOption.SO_KEEPALIVE, true); // (6)

            // Bind and start to accept incoming connections.
            ChannelFuture f = b.bind(8888).sync(); // (7)
            System.out.println("服务器已启动！");
            // Wait until the server socket is closed.
            // In this example, this does not happen, but you can do that to gracefully
            // shut down your server.
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }

    }

    static class DiscardServerHandler extends ChannelInboundHandlerAdapter {

        @Override
        public void channelRead(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
            ByteBuf byteBuffer = (ByteBuf) o;
            byte[] bytes = new byte[byteBuffer.readableBytes()];
            System.out.println(bytes.length);
            byteBuffer.getBytes(byteBuffer.readableBytes(),bytes);
            String msg = new String(bytes,"utf-8");


            System.out.println("收到消息："+msg);
            if(msg.equals("_bye")){//关闭连接
                channelHandlerContext.close();
            }
            if(byteBuffer !=null){
                ReferenceCountUtil.release(byteBuffer);
            }

        }



        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            System.out.println("客户端已下线");
            ctx.close();
        }
    }
}


