package com.wu.netty;

import org.jboss.netty.channel.*;

/**
 * Created by wuxinbo on 2016/10/16.
 */
public class EchoServerHandler extends SimpleChannelHandler{

    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
        super.messageReceived(ctx, e);
        System.out.println("recive message from client "+e.getRemoteAddress());
        System.out.println("recive message : "+e.getMessage());
        e.getChannel().write("hello client "+ e.getRemoteAddress());
    }

    @Override
    public void channelDisconnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        super.channelDisconnected(ctx, e);
        System.out.println("链接已经断开");
    }
}
