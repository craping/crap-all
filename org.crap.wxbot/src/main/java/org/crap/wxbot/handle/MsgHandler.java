package org.crap.wxbot.handle;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.crap.wxbot.KeyWord;
import org.crap.wxbot.bean.PayInfo;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.zhouyafeng.itchat4j.api.MessageTools;
import cn.zhouyafeng.itchat4j.api.WechatTools;
import cn.zhouyafeng.itchat4j.beans.BaseMsg;
import cn.zhouyafeng.itchat4j.beans.RecommendInfo;
import cn.zhouyafeng.itchat4j.core.Core;
import cn.zhouyafeng.itchat4j.face.IMsgHandlerFace;
import cn.zhouyafeng.itchat4j.utils.Config;
import cn.zhouyafeng.itchat4j.utils.MyHttpClient;
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

	Logger logger = Logger.getLogger("TulingRobot");
	MyHttpClient myHttpClient = Core.getInstance().getMyHttpClient();
	String url = "http://openapi.tuling123.com/openapi/api/v2";
	String apiKey = "69add9e3d03d4194b2a2e1d22ca5c8b8";
	
	@Override
	public String textMsgHandle(BaseMsg msg) {
		String text = msg.getText();
		LOG.info(text);
		if (!msg.isGroupMsg()) {// 个人消息处理
			
			if (text.equals("111")) {
				WechatTools.logout();
			}
			
			//刷新keyword
			if (text.equals("222")) {
				KeyWord.load();
				return "配置更新成功";
			}
			if (text.equals("333")) { // 测试群列表
//				System.out.print(WechatTools.getGroupNickNameList());
//				System.out.print(WechatTools.getGroupIdList());
//				System.out.print(Core.getInstance().getGroupMemeberMap());
			}
			if(msg.getFromUserName().equals(Core.getInstance().getUserName())) {
				MessageTools.sendMsgById(text, "@cb57c68bc5345ac3a087e18b6f903760");
			}
			
			Set<Object> keys = KeyWord.KEYWORD.keySet();
			for (Object object : keys) {
				String key = object.toString();
				if(text.contains(key))
					return KeyWord.KEYWORD.getProperty(key);
			}
		} else {// 群消息处理
			Set<Object> keys = KeyWord.KEYWORD_GROUP.keySet();
			for (Object object : keys) {
				String key = object.toString();
				if(text.contains(key))
					return KeyWord.KEYWORD_GROUP.getProperty(key);
			}
		}
		
		String result = null;
		/*String paramStr = "{\"reqType\":0,\"perception\": {\"inputText\": {\"text\": \""+text+"\"}},\"userInfo\": {\"apiKey\": \"69add9e3d03d4194b2a2e1d22ca5c8b8\",\"userId\": \"359415\"}}";
		try {
			HttpEntity entity = myHttpClient.doPost(url, paramStr);
			result = EntityUtils.toString(entity, "UTF-8");
			JSONObject obj = JSON.parseObject(result);
			
			if(obj.containsKey("results")) {
				JSONArray results = obj.getJSONArray("results");
				for (int i = 0; i < results.size(); i++) {
					JSONObject r = results.getJSONObject(i);
					if("text".equals(r.getString("resultType")))
						result = r.getJSONObject("values").getString("text");
				} 
			} else {
				result = "处理有误";
			}
		} catch (Exception e) {
			logger.info(e.getMessage());
		}*/
		return result;
	}

	@Override
	public String picMsgHandle(BaseMsg msg) {
		if (msg.isGroupMsg())
			return null;
		
		String fileName = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date());// 这里使用收到图片的时间作为文件名
		String picPath = Config.picDir+"/pic" + File.separator + fileName + ".jpg"; // 调用此方法来保存图片
		DownloadTools.getDownloadFn(msg, MsgTypeEnum.PIC.getType(), picPath); // 保存图片的路径
		return "图片保存成功";
	}

	@Override
	public String voiceMsgHandle(BaseMsg msg) {
		if (msg.isGroupMsg())
			return null;
		
		String fileName = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date());
		String voicePath = Config.picDir+"/voice" + File.separator + fileName + ".mp3";
		DownloadTools.getDownloadFn(msg, MsgTypeEnum.VOICE.getType(), voicePath);
		
		if(msg.getFromUserName().equals(Core.getInstance().getUserName())) {
			MessageTools.sendFileMsgByUserId("@cb57c68bc5345ac3a087e18b6f903760", voicePath);
		}
		
		return "声音保存成功";
	}

	@Override
	public String viedoMsgHandle(BaseMsg msg) {
		if (msg.isGroupMsg())
			return null;
		
		String fileName = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date());
		String viedoPath = Config.picDir+"/viedo" + File.separator + fileName + ".mp4";
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
		String filePath = Config.picDir+"/file" + File.separator + fileName; // 这里是需要保存收到的文件路径，文件可以是任何格式如PDF，WORD，EXCEL等。
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
