package me.kirinrin.java.KiMCS;

import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.taobao.api.ApiException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class KiMcsWarningServerApplicationTests {
	@Autowired
	private SMSService service;
	
	@Test
	public void sendSMSOverTaobaoDayu() {
		try {
			service.send( "{name:'蒋鑫',timestamp:'xxx',msg:'测试阿里的短信接口'}");
		} catch (ApiException e) {
			fail(e.getMessage());
		}
	}

}
