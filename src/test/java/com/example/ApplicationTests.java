package com.example;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.mail.internet.MimeMessage;
import java.io.File;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ApplicationTests {

	@Autowired
	private JavaMailSender mailSender;

	@Test
	public void contextLoads() {
	}

	@Test
	public void sendSimpleEmail(){
		SimpleMailMessage message = new SimpleMailMessage();

		message.setFrom("1485157242@qq.com");//发送者.
		message.setTo("714994230@qq.com");//接收者.
		message.setSubject("测试邮件（邮件主题）");//邮件主题.
		message.setText("这是邮件内容");//邮件内容.

		mailSender.send(message);//发送邮件
	}

	/**
	 * 邮件中使用静态资源.
	 * @throws Exception
	 */
	@Test
	public void sendInlineMail() throws Exception {
		MimeMessage mimeMessage = mailSender.createMimeMessage();

		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
		//基本设置.
		helper.setFrom("1485157242@qq.com");//发送者.
		helper.setTo("714994230@qq.com");//接收者.
		helper.setSubject("测试静态资源（邮件主题）");//邮件主题.
		// 邮件内容，第二个参数指定发送的是HTML格式
		//说明：嵌入图片<img src='cid:head'/>，其中cid:是固定的写法，而head是一个contentId。
		helper.setText("<body>这是图片：<img src='cid:head' /></body>", true);

		FileSystemResource file = new FileSystemResource(new File("D:/heng.jpg"));
		helper.addInline("head",file);

		mailSender.send(mimeMessage);

	}
}
