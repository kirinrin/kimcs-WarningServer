package me.kirinrin.java.KiMCS;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

/**
 * 邮箱接口
 * 
 * @author Kirinrin
 *
 */
@Slf4j
@Service
public class EmailService {
	@Autowired
    private JavaMailSender mailSender;
	private String[] sendToEmails;
	private String fromEmail;
	public EmailService(@Value("${system-admin-mail}") String[] emails, @Value("${from-mail}")String fromEmail) {
		log.info("邮件服务初始化");
		this.sendToEmails = emails;
		this.fromEmail = fromEmail;
	}

	public String send(Map<String, String> allRequestParams) {
		log.info("发送告警邮件|{}",allRequestParams);
		String subject = String.format("%s 程序告警通知", allRequestParams.get("name"));
		for (String toAddr : sendToEmails) {
			SimpleMailMessage message = new SimpleMailMessage();
	        message.setFrom(fromEmail);
	        message.setTo(toAddr);
	        message.setSubject(subject);
	        message.setText(String.format("程序名：%s\n时间：%s\n消息：%s", allRequestParams.get("name"), allRequestParams.get("timestamp"), allRequestParams.get("msg")));

	        try {
	            mailSender.send(message);
	            log.info("简单邮件已经发送。|", toAddr);
	        } catch (Exception e) {
	            log.error("发送简单邮件时发生异常！|", toAddr, e);
	            return e.getMessage();
	        }
		}
		return "success";
	}

}
