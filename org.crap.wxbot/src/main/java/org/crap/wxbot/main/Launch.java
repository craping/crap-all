package org.crap.wxbot.main;

import org.crap.wxbot.handle.MsgHandler;

import cn.zhouyafeng.itchat4j.Wechat;

public class Launch {
	
	public static void main(String[] args) {
		String qrPath = "E:/itchat4j/login"; // 保存登陆二维码图片的路径，这里需要在本地新建目录
		Wechat wechat = new Wechat(new MsgHandler(), qrPath); // 【注入】
		wechat.start(); // 启动服务，会在qrPath下生成一张二维码图片，扫描即可登陆，注意，二维码图片如果超过一定时间未扫描会过期，过期时会自动更新，所以你可能需要重新打开图片
	}
}
