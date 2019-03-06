package me.kirinrin.java.KiMCS;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;

import lombok.extern.slf4j.Slf4j;

/**
 * 淘宝短信接口
 * @author Kirinrin
 *
 */
@Slf4j
@Service
public class SMSService {

	private String url;
	private String appkey;
	private String secret;
	private String signName;

	private String adminPhone;

	public SMSService(@Value("${system-admin}") String[] smsPhoneNumber, @Value("${taobao-url}") String url,
			@Value("${taobao-app-key}") String appkey, @Value("${taobao-app-secret}") String secret,
			@Value("${taobao-sign-name}") String sign) {
		log.info("短信服务初始化");

		adminPhone = generatPhoneNumber(smsPhoneNumber);

		log.info("管理员手机号: {}", adminPhone);

		log.info("淘宝大于接口配置: url-{}  appkey-{} secret-{}, sign-{}", url, appkey, secret, sign);

		this.url = url;
		this.appkey = appkey;
		this.secret = secret;
		this.signName = sign;

	}

	private String generatPhoneNumber(String[] smsPhoneNumber) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < smsPhoneNumber.length; i++) {
			String string = smsPhoneNumber[i];
			sb.append(string);
			if ((i + 1) < smsPhoneNumber.length) {
				sb.append(",");
			}
		}
		return sb.toString();
	}

	public String send(String jsonString) throws ApiException {

		TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
		AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
		req.setExtend("");
		req.setSmsType("normal");
		req.setSmsFreeSignName(signName);
		req.setSmsParamString(jsonString);
		req.setRecNum(adminPhone);
		req.setSmsTemplateCode("SMS_66060386");
		AlibabaAliqinFcSmsNumSendResponse rsp;
		rsp = client.execute(req);
		log.info("发送短信结果：{}", rsp.getBody());
		return rsp.getBody();
	}
}
