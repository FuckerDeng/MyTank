package com.df.tank.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

public class TankClient {
    private Channel channel = null;

    public  void run() {
        new Thread(()->{
            EventLoopGroup bossGroup = new NioEventLoopGroup(); // (1)
            try {
                Bootstrap b = new Bootstrap(); // (2)
                b.group(bossGroup)
                        .channel(NioSocketChannel.class) // (3)
                        .handler(new ChannelInitializer<SocketChannel>() { // (4)
                            @Override
                            public void initChannel(SocketChannel ch) throws Exception {
                                ch.pipeline().addLast(new ClientHandler());
                            }
                        });
                // Bind and start to accept incoming connections.
                ChannelFuture f = b.connect(new InetSocketAddress(8888)).sync(); // (7)
                f.addListener(new ChannelFutureListener() {
                    @Override
                    public void operationComplete(ChannelFuture channelFuture) throws Exception {

                        if(channelFuture.isSuccess()){
                            System.out.println("连接成功！");
                            channel = channelFuture.channel();
                        }else{
                            System.out.println("连接失败！");
                        }
                    }
                });
                f.sync();

                // Wait until the server socket is closed.
                // In this example, this does not happen, but you can do that to gracefully
                // shut down your server.
                f.channel().closeFuture().sync();
            } catch (InterruptedException e) {
                e.printStackTrace();

            }finally {
                bossGroup.shutdownGracefully();
            }
        }).start();

    }

    public void sendMsg(String msg){
        if(channel ==null){
            System.out.println("服务端未连接，无法发送消息！");
            return;
        }
        ByteBuf byteBuf = Unpooled.buffer().writeBytes(msg.getBytes());
        channel.writeAndFlush(byteBuf);
    }

    public void closeClient() {
        channel.writeAndFlush(Unpooled.buffer().writeBytes("_bye".getBytes()));
        channel.close();
        System.exit(0);
    }

    class  ClientHandler extends ChannelInboundHandlerAdapter {
        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            System.out.println("开始发送消息");
            //要发送butebuf类型数据服务器才能正确收到
            ctx.writeAndFlush(Unpooled.buffer().writeBytes("hello".getBytes("utf-8")));
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            cause.printStackTrace();
            ctx.close();
        }
    }
}
