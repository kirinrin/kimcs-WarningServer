package me.kirinrin.java.KiMCS;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.taobao.api.ApiException;

import lombok.extern.slf4j.Slf4j;

/**
 * http://27.17.33.22/bpgem/weixindiqiao/sendmessage_list.do?method=sendlist&phonenumlist=18627283312&msg=
 * curl -X POST http://localhost:9099/sendSMS
 * 
 * @author Kirinrin
 *
 */
@Slf4j
@RestController
public class TransferController {
	@Autowired
	private SMSService service;
	@Autowired
	private EmailService emailService;

	/**
	 * curl -X POST --data "name=test&timestamp=22de32&msg=23ceeesss"
	 * http://localhost:9099/sendSMS
	 */
	@PostMapping("sendSMS")
	public ResponseEntity<String> sendSMSMessage(@RequestParam Map<String, String> allRequestParams) {
		String jsonString;
		String result = "";
		try {
			jsonString = new ObjectMapper().writeValueAsString(allRequestParams);
			jsonString = jsonString.replaceAll("10.200.0", "");
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			log.info("发送报警给 {} \n{} ", LocalDateTime.now().format(formatter), allRequestParams);
			result = service.send(jsonString);
		} catch (JsonProcessingException e) {
			log.error("接口参数转接异常", e);
			result = e.getMessage();
		} catch (ApiException ex) {
			log.error("调用淘宝大于短信接口异常", ex);
			result = ex.getMessage();
		}
		return ResponseEntity.ok(result);
	}

	@PostMapping("sendEmail")
	public ResponseEntity<String> sendEmailMessage(@RequestParam Map<String, String> allRequestParams) {
		String jsonString;
		String result;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		log.info("发送报警给 {} \n{} ", LocalDateTime.now().format(formatter), allRequestParams);
		result = emailService.send(allRequestParams);
		return ResponseEntity.ok(result);
	}
}
