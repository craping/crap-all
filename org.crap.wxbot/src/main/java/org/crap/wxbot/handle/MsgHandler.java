package org.crap.wxbot.handle;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.log4j.Logger;
import org.crap.wxbot.bean.PayInfo;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.alibaba.fastjson.JSONObject;

import cn.zhouyafeng.itchat4j.api.MessageTools;
import cn.zhouyafeng.itchat4j.api.WechatTools;
import cn.zhouyafeng.itchat4j.beans.BaseMsg;
import cn.zhouyafeng.itchat4j.beans.RecommendInfo;
import cn.zhouyafeng.itchat4j.core.Core;
import cn.zhouyafeng.itchat4j.face.IMsgHandlerFace;
import cn.zhouyafeng.itchat4j.utils.enums.MsgTypeEnum;
import cn.zhouyafeng.itchat4j.utils.tools.DownloadTools;

/**
 * 简单示例程序，收到文本信息自动回复原信息，收到图片、语音、小视频后根据路径自动保存
 * 
 * @author https://github.com/yaphone
 * @date 创建时间：2017年4月25日 上午12:18:09
 * @version 1.0
 *
 */
public class MsgHandler implements IMsgHandlerFace {
	Logger LOG = Logger.getLogger(MsgHandler.class);

	@Override
	public String textMsgHandle(BaseMsg msg) {
		// String docFilePath = "E:/itchat4j/pic/1.jpg"; // 这里是需要发送的文件的路径
		if (!msg.isGroupMsg()) { // 群消息不处理
			// String userId = msg.getString("FromUserName");
			// MessageTools.sendFileMsgByUserId(userId, docFilePath); // 发送文件
			// MessageTools.sendPicMsgByUserId(userId, docFilePath);
			String text = msg.getText(); // 发送文本消息，也可调用MessageTools.sendFileMsgByUserId(userId,text);
			LOG.info(text);
			if (text.equals("111")) {
				WechatTools.logout();
			}
			if (text.equals("222")) {
				WechatTools.remarkNameByNickName("yaphone", "Hello");
			}
			if (text.equals("333")) { // 测试群列表
				System.out.print(WechatTools.getGroupNickNameList());
				System.out.print(WechatTools.getGroupIdList());
				System.out.print(Core.getInstance().getGroupMemeberMap());
			}
			return text;
		}
		return null;
	}

	@Override
	public String picMsgHandle(BaseMsg msg) {
		if (msg.isGroupMsg())
			return null;
		
		String fileName = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date());// 这里使用收到图片的时间作为文件名
		String picPath = "E://itchat4j/pic" + File.separator + fileName + ".jpg"; // 调用此方法来保存图片
		DownloadTools.getDownloadFn(msg, MsgTypeEnum.PIC.getType(), picPath); // 保存图片的路径
		return "图片保存成功";
	}

	@Override
	public String voiceMsgHandle(BaseMsg msg) {
		if (msg.isGroupMsg())
			return null;
		
		String fileName = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date());
		String voicePath = "E://itchat4j/voice" + File.separator + fileName + ".mp3";
		DownloadTools.getDownloadFn(msg, MsgTypeEnum.VOICE.getType(), voicePath);
		return "声音保存成功";
	}

	@Override
	public String viedoMsgHandle(BaseMsg msg) {
		if (msg.isGroupMsg())
			return null;
		
		String fileName = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date());
		String viedoPath = "E://itchat4j/viedo" + File.separator + fileName + ".mp4";
		DownloadTools.getDownloadFn(msg, MsgTypeEnum.VIEDO.getType(), viedoPath);
		return "视频保存成功";
	}

	@Override
	public String nameCardMsgHandle(BaseMsg msg) {
		if (msg.isGroupMsg())
			return null;
		
		return "收到名片消息";
	}

	@Override
	public void sysMsgHandle(BaseMsg msg) { // 收到系统消息
		String text = msg.getContent();
		LOG.info(text);
	}

	@Override
	public String verifyAddFriendMsgHandle(BaseMsg msg) {
		MessageTools.addFriend(msg, true); // 同意好友请求，false为不接受好友请求
		RecommendInfo recommendInfo = msg.getRecommendInfo();
		String nickName = recommendInfo.getNickName();
		String province = recommendInfo.getProvince();
		String city = recommendInfo.getCity();
		String text = "你好，来自" + province + city + "的" + nickName + "， 欢迎添加我为好友！";
		return text;
	}

	@Override
	public String mediaMsgHandle(BaseMsg msg) {
		if (msg.isGroupMsg())
			return null;
		
		String fileName = msg.getFileName();
		String filePath = "E://itchat4j/file" + File.separator + fileName; // 这里是需要保存收到的文件路径，文件可以是任何格式如PDF，WORD，EXCEL等。
		DownloadTools.getDownloadFn(msg, MsgTypeEnum.MEDIA.getType(), filePath);
		return "文件" + fileName + "保存成功";
	}

	@Override
	public String transferMsgHandle(BaseMsg msg) {
		if (msg.isGroupMsg())
			return null;
		
		String xml = msg.getContent().replace("&lt;", "<").replace("&gt;", ">");
		PayInfo payInfo = new PayInfo();
		try {
			SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
			parser.parse(new InputSource(new StringReader(xml)), new DefaultHandler() {
				
				private String currentTag = null;
				@Override
				public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
					super.startElement(uri, localName, qName, attributes);
					currentTag = qName;
				}
				@Override
				public void endElement(String uri, String localName, String qName) throws SAXException {
					super.endElement(uri, localName, qName);
					currentTag = "";
				}
				
				@Override
				public void characters(char[] ch, int start, int length) throws SAXException {
					super.characters(ch, start, length);
					String value = new String(ch, start, length);
					switch (currentTag) {
						case "paysubtype":
							payInfo.setPaysubtype(Integer.parseInt(value));
							break;
						case "feedesc":
							payInfo.setFeed(new BigDecimal(value.replace("￥", "")));
							break;
						case "transcationid":
							payInfo.setTranscationId(value);
							break;
						case "transferid":
							payInfo.setTransferId(value);
							break;
						case "invalidtime":
							payInfo.setInvalidTime(new Date(Long.parseLong(value) * 1000));
							break;
						case "begintransfertime":
							payInfo.setBeginTransferTime(new Date(Long.parseLong(value) * 1000));
							break;
						case "effectivedate":
							payInfo.setEffectiveDate(Integer.parseInt(value));
							break;
						case "pay_memo":
							payInfo.setPayMemo(value);
							break;
					}
				}
			});
			
			System.out.println(JSONObject.toJSON(payInfo));
		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}
		
		String nickName = msg.getFromUserName();
		JSONObject userJson = Core.getInstance().getUserInfoMap().get(msg.getFromUserName());
		if(userJson != null)
			nickName = userJson.getString("NickName");
		return String.format("转账用户:[%s]\n转账金额:[¥%s]\n交易单号:[%s]\n到期时间:[%4$tF %4$tT]\n转账说明:[%5$s]", 
				nickName, payInfo.getFeed(), payInfo.getTransferId(), payInfo.getInvalidTime(), payInfo.getPayMemo());
	}

	
	public static void main(String[] args) {

	}
}
