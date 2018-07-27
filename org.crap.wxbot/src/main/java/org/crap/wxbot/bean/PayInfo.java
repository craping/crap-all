package org.crap.wxbot.bean;

import java.math.BigDecimal;
import java.util.Date;

public class PayInfo {

	//支付子类型
	private int paysubtype;
	//金额
	private BigDecimal feed;
	//事务ID
	private String transcationId;
	//交易ID
	private String transferId;
	//到期时间
	private Date invalidTime;
	//转账时间
	private Date beginTransferTime;
	//有效天数
	private int effectiveDate;
	//转账说明
	private String payMemo;
	
	public int getPaysubtype() {
		return paysubtype;
	}
	public void setPaysubtype(int paysubtype) {
		this.paysubtype = paysubtype;
	}
	public BigDecimal getFeed() {
		return feed;
	}
	public void setFeed(BigDecimal feed) {
		this.feed = feed;
	}
	public String getTranscationId() {
		return transcationId;
	}
	public void setTranscationId(String transcationId) {
		this.transcationId = transcationId;
	}
	public String getTransferId() {
		return transferId;
	}
	public void setTransferId(String transferId) {
		this.transferId = transferId;
	}
	public Date getInvalidTime() {
		return invalidTime;
	}
	public void setInvalidTime(Date invalidTime) {
		this.invalidTime = invalidTime;
	}
	public Date getBeginTransferTime() {
		return beginTransferTime;
	}
	public void setBeginTransferTime(Date beginTransferTime) {
		this.beginTransferTime = beginTransferTime;
	}
	public int getEffectiveDate() {
		return effectiveDate;
	}
	public void setEffectiveDate(int effectiveDate) {
		this.effectiveDate = effectiveDate;
	}
	public String getPayMemo() {
		return payMemo;
	}
	public void setPayMemo(String payMemo) {
		this.payMemo = payMemo;
	}
}
