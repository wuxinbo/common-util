package com.wu.netty;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.*;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.handler.codec.string.StringDecoder;
import org.jboss.netty.handler.codec.string.StringEncoder;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

/**
 * netty demo
 * Created by wuxinbo on 2016/10/16.
 */
public class NettyServer {

    /**
     * server 入口
     * @param args
     */
    public static void main (String[] args){

        ServerBootstrap bootstrap =new ServerBootstrap();
        bootstrap.setFactory(new NioServerSocketChannelFactory(Executors.newCachedThreadPool(),Executors.newCachedThreadPool()));
        bootstrap.setPipelineFactory(new ChannelPipelineFactory() {

            public ChannelPipeline getPipeline() throws Exception {
                return Channels.pipeline(new StringDecoder(),new StringEncoder(),new EchoServerHandler());
            }
        });
        Channel bind = bootstrap.bind(new InetSocketAddress("127.0.0.1", 8088));
        System.out.println(bind.getLocalAddress().toString());
        System.out.println("server is started!");
    }
}

