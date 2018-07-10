package com.way.utils.email;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Flags.Flag;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import com.sun.mail.imap.IMAPBodyPart;
import com.sun.mail.imap.IMAPMessage;

public class EmailUtil {

	/**
	 * 获得邮件主题
	 * 
	 * @param msg
	 *            邮件内容
	 * @return 解码后的邮件主题
	 */
	public static String getSubject(MimeMessage msg) throws UnsupportedEncodingException, MessagingException {
		return MimeUtility.decodeText(msg.getSubject());
	}

	/**
	 * 获得邮件发件人
	 * 
	 * @param msg
	 *            邮件内容
	 * @return 姓名 <Email地址>
	 * @throws MessagingException
	 * @throws UnsupportedEncodingException
	 */
	public static String getFrom(MimeMessage msg) throws MessagingException, UnsupportedEncodingException {
		String from = "";
		Address[] froms = msg.getFrom();
		if (froms.length < 1){
			return "have not from address";
		}
		InternetAddress address = (InternetAddress) froms[0];
		String person = address.getPersonal();
		if (person != null) {
			person = MimeUtility.decodeText(person) + " ";
		} else {
			person = "";
		}
		from = person + "<" + address.getAddress() + ">";

		return from;
	}

	/**
	 * 获得邮件发件人地址 只有地址
	 * 
	 * @param msg
	 * @return
	 * @throws MessagingException
	 * @throws UnsupportedEncodingException
	 */
	public static String getFromAddr(MimeMessage msg) throws MessagingException, UnsupportedEncodingException {
		String from = "";
		Address[] froms = msg.getFrom();
		if (froms.length < 1){
			return "have not from address";
		}
		InternetAddress address = (InternetAddress) froms[0];
		from = address.getAddress();
		return from;
	}

	/**
	 * 根据收件人类型，获取邮件收件人、抄送和密送地址。如果收件人类型为空，则获得所有的收件人
	 * <p>
	 * Message.RecipientType.TO 收件人
	 * </p>
	 * <p>
	 * Message.RecipientType.CC 抄送
	 * </p>
	 * <p>
	 * Message.RecipientType.BCC 密送
	 * </p>
	 * 
	 * @param msg
	 *            邮件内容
	 * @param type
	 *            收件人类型
	 * @return 收件人1 <邮件地址1>, 收件人2 <邮件地址2>, ...
	 * @throws MessagingException
	 */
	public static String getReceiveAddress(MimeMessage msg, Message.RecipientType type) throws MessagingException {
		StringBuffer receiveAddress = new StringBuffer();
		Address[] addresss = null;
		if (type == null) {
			addresss = msg.getAllRecipients();
		} else {
			addresss = msg.getRecipients(type);
		}

		if (addresss == null || addresss.length < 1){
			return "have not receive address";
		}
		for (Address address : addresss) {
			InternetAddress internetAddress = (InternetAddress) address;
			receiveAddress.append(internetAddress.toUnicodeString()).append(";");
		}

		receiveAddress.deleteCharAt(receiveAddress.length() - 1); // 删除最后一个逗号

		return receiveAddress.toString();
	}

	/**
	 * 获得所有收件人的地址 只有地址
	 * 
	 * @param msg
	 * @return
	 * @throws MessagingException
	 */
	public static String getAllToAddr(MimeMessage msg) throws MessagingException {
		StringBuffer receiveAddress = new StringBuffer();
		Address[] addresss = null;
		addresss = msg.getRecipients(Message.RecipientType.TO);

		if (addresss == null || addresss.length < 1){
			return "have not receive address";
		}
		for (Address address : addresss) {
			InternetAddress internetAddress = (InternetAddress) address;
			receiveAddress.append(internetAddress.getAddress()).append(";");
		}

		receiveAddress.deleteCharAt(receiveAddress.length() - 1); // 删除最后一个逗号

		return receiveAddress.toString();
	}

	/**
	 * 获得所有cc的地址 只有地址
	 * 
	 * @param msg
	 * @return
	 * @throws MessagingException
	 */
	public static String getAllCcAddr(MimeMessage msg) throws MessagingException {
		StringBuffer receiveAddress = new StringBuffer();
		Address[] addresss = null;
		addresss = msg.getRecipients(Message.RecipientType.CC);

		if (addresss != null && addresss.length > 0) {
			for (Address address : addresss) {
				InternetAddress internetAddress = (InternetAddress) address;
				receiveAddress.append(internetAddress.getAddress()).append(";");
			}

			receiveAddress.deleteCharAt(receiveAddress.length() - 1); // 删除最后一个逗号

			return receiveAddress.toString();
		} else {
			return "";
		}

	}

	/**
	 * 获得邮件发送时间
	 * 
	 * @param msg
	 *            邮件内容
	 * @return yyyy年mm月dd日 星期X HH:mm
	 * @throws MessagingException
	 */
	public static String getSentDate(MimeMessage msg, String pattern) throws MessagingException {
		Date receivedDate = msg.getSentDate();
		if (receivedDate == null){
			return "no received date";
		}
		if (pattern == null || "".equals(pattern)){
			pattern = "yyyy-MM-dd HH:mm:ss.SSS";
		}
		return new SimpleDateFormat(pattern).format(receivedDate);
	}

	/**
	 * 判断邮件中是否包含附件
	 * 
	 * @param msg
	 *            邮件内容
	 * @return 邮件中存在附件返回true，不存在返回false
	 * @throws MessagingException
	 * @throws IOException
	 */
	public static boolean isContainAttachment(Part part) throws MessagingException, IOException {
		boolean flag = false;
		if (part.isMimeType("multipart/*")) {
			MimeMultipart multipart = (MimeMultipart) part.getContent();
			int partCount = multipart.getCount();
			for (int i = 0; i < partCount; i++) {
				BodyPart bodyPart = multipart.getBodyPart(i);
				String disp = bodyPart.getDisposition();
				if (disp != null && (disp.equalsIgnoreCase(Part.ATTACHMENT) || disp.equalsIgnoreCase(Part.INLINE))) {
					flag = true;
				} else if (bodyPart.isMimeType("multipart/*")) {
					flag = isContainAttachment(bodyPart);
				} else {
					String contentType = bodyPart.getContentType();
					if (contentType.indexOf("application") != -1) {
						flag = true;
					}

					if (contentType.indexOf("name") != -1) {
						flag = true;
					}
				}

				if (flag){
					break;
				}
			}
		} else if (part.isMimeType("message/rfc822")) {
			flag = isContainAttachment((Part) part.getContent());
		}
		return flag;
	}

	/**
	 * 判断邮件是否已读
	 * 
	 * @param msg
	 *            邮件内容
	 * @return 如果邮件已读返回true,否则返回false
	 * @throws MessagingException
	 */
	public static boolean isSeen(MimeMessage msg) throws MessagingException {
		return msg.getFlags().contains(Flags.Flag.SEEN);
	}

	/**
	 * 判断邮件是否需要阅读回执
	 * 
	 * @param msg
	 *            邮件内容
	 * @return 需要回执返回true,否则返回false
	 * @throws MessagingException
	 */
	public static boolean isReplySign(MimeMessage msg) throws MessagingException {
		boolean replySign = false;
		String[] headers = msg.getHeader("Disposition-Notification-To");
		if (headers != null){
			replySign = true;
		}
		return replySign;
	}

	/**
	 * 获得邮件的优先级
	 * 
	 * @param msg
	 *            邮件内容
	 * @return 1(High):紧急 3:普通(Normal) 5:低(Low)
	 * @throws MessagingException
	 */
	public static String getPriority(MimeMessage msg) throws MessagingException {
		String priority = "普通";
		String[] headers = msg.getHeader("X-Priority");
		if (headers != null) {
			String headerPriority = headers[0];
			if (headerPriority.indexOf("1") != -1 || headerPriority.indexOf("High") != -1){
				priority = "紧急";
			}else if (headerPriority.indexOf("5") != -1 || headerPriority.indexOf("Low") != -1){
				priority = "低";
			}else{
				priority = "普通";
			}
		}
		return priority;
	}

	/**
	 * 获得邮件纯文本内容
	 * 
	 * @param part
	 *            邮件体
	 * @param content
	 *            存储邮件文本内容的字符串
	 * @throws MessagingException
	 * @throws IOException
	 */
	public static void getMailTextContent(Part part, StringBuffer content) throws MessagingException, IOException {
		// 如果是文本类型的附件，通过getContent方法可以取到文本内容，但这不是我们需要的结果，所以在这里要做判断
		boolean isContainTextAttach = part.getContentType().indexOf("name") > 0;
		// System.out.println(isContainTextAttach);
		// System.out.println(part.getContentType());
		// text/html 或 text/plain 如果用text/*的话 会有文本+html文本
		// if (part.isMimeType("text/*") && !isContainTextAttach) {
		// if (part.isMimeType("text/html") && !isContainTextAttach) {//收取带样式的文本
		// html
		if (part.isMimeType("text/plain") && !isContainTextAttach) {// 收取不带样式的纯文本
			content.append(part.getContent().toString());
		} else if (part.isMimeType("message/rfc822")) {
			getMailTextContent((Part) part.getContent(), content);
		} else if (part.isMimeType("multipart/*")) {
			Multipart multipart = (Multipart) part.getContent();
			int partCount = multipart.getCount();
			for (int i = 0; i < partCount; i++) {
				BodyPart bodyPart = multipart.getBodyPart(i);
				getMailTextContent(bodyPart, content);
			}
		}
	}

	/**
	 * 获得邮件html文本内容
	 * 
	 * @param part
	 *            邮件体
	 * @param content
	 *            存储邮件文本内容的字符串
	 * @throws MessagingException
	 * @throws IOException
	 */
	public static void getMailHtmlContent(Part p,Map<String, String> imgs,  StringBuffer sb) throws MessagingException, IOException {
		if (p.isMimeType("text/plain")) {// 检查内容是否为纯文本
        } else if (p.isMimeType("text/html")) {// 检查内容是否为html
            sb.append(p.getContent());
        } else if (p.isMimeType("multipart/*")) {// 检查内容是否含有附件
            Multipart mp = (Multipart) p.getContent();
            int count = mp.getCount();
            for (int i = 0; i < count; i++) {
            	getMailHtmlContent(mp.getBodyPart(i), imgs, sb);
            }
        } else if (p.isMimeType("message/rfc822")) {// 检查内容是否含有嵌套消息
        	getMailHtmlContent((Part)p.getContent(), imgs,  sb);
        } else if (p.isMimeType("image/*")) {// 检查内容是否为内嵌图片
        	System.out.println(p.getContentType());
            InputStream in = p.getInputStream();
            ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
            int ch;
            while ((ch = in.read()) != -1) {   
                swapStream.write(ch);   
            }
            
            byte[] bArray = swapStream.toByteArray();
            
            String contentID = ((IMAPBodyPart)p).getContentID();
            String file4base64 = Base64Util.base64EncodeStr(bArray);
            file4base64 = "data:image/jpg;base64,"+file4base64;
            imgs.put(contentID, file4base64);
            // 文件下载结束
        } else {
        }
	}

	/**
	 * 保存附件
	 * 
	 * @param part
	 *            邮件中多个组合体中的其中一个组合体
	 * @param destDir
	 *            附件保存目录
	 * @throws UnsupportedEncodingException
	 * @throws MessagingException
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static void saveAttachment(Part part, String destDir)
			throws UnsupportedEncodingException, MessagingException, FileNotFoundException, IOException {
		if (part.isMimeType("multipart/*")) {
			Multipart multipart = (Multipart) part.getContent(); // 复杂体邮件
			// 复杂体邮件包含多个邮件体
			int partCount = multipart.getCount();
			for (int i = 0; i < partCount; i++) {
				// 获得复杂体邮件中其中一个邮件体
				BodyPart bodyPart = multipart.getBodyPart(i);
				// 某一个邮件体也有可能是由多个邮件体组成的复杂体
				String disp = bodyPart.getDisposition();
				if (disp != null && (disp.equalsIgnoreCase(Part.ATTACHMENT) || disp.equalsIgnoreCase(Part.INLINE))) {
					InputStream is = bodyPart.getInputStream();
					saveFile(is, destDir, decodeText(bodyPart.getFileName()));
				} else if (bodyPart.isMimeType("multipart/*")) {
					saveAttachment(bodyPart, destDir);
				} else {
					String contentType = bodyPart.getContentType();
					if (contentType.indexOf("name") != -1 || contentType.indexOf("application") != -1) {
						saveFile(bodyPart.getInputStream(), destDir, decodeText(bodyPart.getFileName()));
					}
				}
			}
		} else if (part.isMimeType("message/rfc822")) {
			saveAttachment((Part) part.getContent(), destDir);
		}
	}

	/**
	 * 读取输入流中的数据保存至指定目录
	 * 
	 * @param is
	 *            输入流
	 * @param fileName
	 *            文件名
	 * @param destDir
	 *            文件存储目录
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private static void saveFile(InputStream is, String destDir, String fileName)
			throws FileNotFoundException, IOException {
		BufferedInputStream bis = new BufferedInputStream(is);
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(new File(destDir + fileName)));
		int len = -1;
		while ((len = bis.read()) != -1) {
			bos.write(len);
			bos.flush();
		}
		bos.close();
		bis.close();
	}

	/**
	 * 文本解码
	 * 
	 * @param encodeText
	 *            解码MimeUtility.encodeText(String text)方法编码后的文本
	 * @return 解码后的文本
	 * @throws UnsupportedEncodingException
	 */
	public static String decodeText(String encodeText) throws UnsupportedEncodingException {
		if (encodeText == null || "".equals(encodeText)) {
			return "";
		} else {
			return MimeUtility.decodeText(encodeText);
		}
	}

	/**
	 * 
	 * 邮件设为已读
	 * 
	 * @param messages
	 * @throws MessagingException
	 */
	public static void setEmailReaded(MimeMessage message) throws MessagingException {
		IMAPMessage msg = (IMAPMessage) message;
		msg.setFlag(Flag.SEEN, true); // 设置未读标志
										// 第二个参数如果设置为true，则将修改反馈给服务器。false则不反馈给服务器
	}

	/*	
	*//**
		 * 遍历所有邮件内容
		 * 
		 * @param messages
		 * @throws MessagingException
		 * @throws IOException
		 */
	/*
	 * public static void readAllEmails(Message[] messages) throws
	 * MessagingException, IOException { System.out .println(
	 * "------------------------开始解析邮件----------------------------------" );
	 * 
	 * // 解析邮件 for (Message message : messages) { IMAPMessage msg =
	 * (IMAPMessage) message; String subject =
	 * MimeUtility.decodeText(msg.getSubject());
	 * 
	 * System.out.println("邮件主题:[" + subject + "]正在读取");
	 * 
	 * BufferedReader reader = new BufferedReader(new InputStreamReader(
	 * System.in));
	 * 
	 * String answer = reader.readLine();
	 * 
	 * if ("yes".equalsIgnoreCase(answer)) { parseMessage(msg); // 解析邮件 //
	 * 第二个参数如果设置为true，则将修改反馈给服务器。false则不反馈给服务器 // msg.setFlag(Flag.SEEN, true);
	 * //设置已读标志 新浪不用设置 因为一遍历邮件内容就会把邮件设为已读 } } }
	 *//**
		 * 遍历所有未读邮件内容
		 * 
		 * @param messages
		 * @throws MessagingException
		 * @throws IOException
		 */
	/*
	 * public static void readAllUnreadedEmails(Message[] messages) throws
	 * MessagingException, IOException { System.out .println(
	 * "------------------------开始解析邮件----------------------------------" ); //
	 * 解析邮件 for (Message message : messages) { IMAPMessage msg = (IMAPMessage)
	 * message; if(!isSeen(msg)){ String subject =
	 * MimeUtility.decodeText(msg.getSubject()); parseMessage(msg); // 解析邮件 //
	 * 第二个参数如果设置为true，则将修改反馈给服务器。false则不反馈给服务器 msg.setFlag(Flag.SEEN, true);
	 * //设置已读标志 新浪不用设置 因为一遍历邮件内容就会把邮件设为已读 } } }
	 */

	/**
	 * 把一封邮件设为删除
	 * 
	 * @param message
	 * @throws MessagingException
	 */
	public static void deleteEmail(MimeMessage message) throws MessagingException {
		IMAPMessage msg = (IMAPMessage) message;
		msg.setFlag(Flag.DELETED, true); // 设置未读标志
											// 第二个参数如果设置为true，则将修改反馈给服务器。false则不反馈给服务器
	}
}
