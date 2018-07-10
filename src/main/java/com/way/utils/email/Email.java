package com.way.utils.email;

import java.util.Date;

/**
 * 
 * @author ASNPHT6 Way Liang
 * @Description: TODO(邮件实体 对应表tinc_email_receive)
 * @date 2018年2月8日
 */
public class Email {

	private long id;// 主键自增id

	private String messageId;// 邮件消息id

	private String emailSubject;// 邮件主题

	private String emailSender;// 邮件发送人 只要地址

	private String emailRecipients;// 邮件收件人 只要地址 有可能是多个

	private String emailCc;// 邮件抄送人 只要地址 可能为空 有可能是多个

	private String emailContentPlain;// 邮件纯文本 提供到微信推送前端

	private String emailContentHtml;// 邮件html文本 提供到h5前端

	private String hasAttachment;// 是否有附件 Y/N

	private Date receiveTime;// 邮件在邮件服务器接收时间

	private Date sentTime;// 邮件发送时间

	private Date createTime;// 记录创建时间 即本系统收件并存入数据库的时间

	private String recordStatus;// 记录状态 A/D 逻辑删除
	
	private String incidentId;//

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public String getEmailSubject() {
		return emailSubject;
	}

	public void setEmailSubject(String emailSubject) {
		this.emailSubject = emailSubject;
	}

	public String getEmailSender() {
		return emailSender;
	}

	public void setEmailSender(String emailSender) {
		this.emailSender = emailSender;
	}

	public String getEmailRecipients() {
		return emailRecipients;
	}

	public void setEmailRecipients(String emailRecipients) {
		this.emailRecipients = emailRecipients;
	}

	public String getEmailCc() {
		return emailCc;
	}

	public void setEmailCc(String emailCc) {
		this.emailCc = emailCc;
	}

	public String getEmailContentPlain() {
		return emailContentPlain;
	}

	public void setEmailContentPlain(String emailContentPlain) {
		this.emailContentPlain = emailContentPlain;
	}

	public String getEmailContentHtml() {
		return emailContentHtml;
	}

	public void setEmailContentHtml(String emailContentHtml) {
		this.emailContentHtml = emailContentHtml;
	}

	public String getHasAttachment() {
		return hasAttachment;
	}

	public void setHasAttachment(String hasAttachment) {
		this.hasAttachment = hasAttachment;
	}

	public Date getReceiveTime() {
		return receiveTime;
	}

	public void setReceiveTime(Date receiveTime) {
		this.receiveTime = receiveTime;
	}

	public Date getSentTime() {
		return sentTime;
	}

	public void setSentTime(Date sentTime) {
		this.sentTime = sentTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getRecordStatus() {
		return recordStatus;
	}

	public void setRecordStatus(String recordStatus) {
		this.recordStatus = recordStatus;
	}

	public String getIncidentId() {
		return incidentId;
	}

	public void setIncidentId(String incidentId) {
		this.incidentId = incidentId;
	}

	@Override
	public String toString() {
		return "Email [id=" + id + ", messageId=" + messageId
				+ ", emailSubject=" + emailSubject + ", emailSender="
				+ emailSender + ", emailRecipients=" + emailRecipients
				+ ", emailCc=" + emailCc + ", emailContentPlain="
				+ emailContentPlain + ", emailContentHtml=" + emailContentHtml
				+ ", hasAttachment=" + hasAttachment + ", receiveTime="
				+ receiveTime + ", sentTime=" + sentTime + ", createTime="
				+ createTime + ", recordStatus=" + recordStatus
				+ ", incidentId=" + incidentId + "]";
	}

}
