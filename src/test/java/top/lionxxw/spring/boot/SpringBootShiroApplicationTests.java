package top.lionxxw.spring.boot;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Package top.lionxxw.spring.boot
 * Project shiro_example
 *
 * Author lionxxw
 * Created on 2017/8/8 17:04
 * version 1.0.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootShiroApplicationTests {
    @Autowired
    private JavaMailSender mailSender;

    private String from_email = "xxx@163.com";
    private String to_email = "xxx@qq.com";

    /**
     * 修改application.properties的用户，才能发送。
     */
    @Test
    public void sendSimpleEmail() {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(from_email);//发送者.
        message.setTo(to_email);//接收者.
        message.setSubject("测试邮件（邮件主题）");//邮件主题.
        message.setText("这是邮件内容");//邮件内容.

        mailSender.send(message);//发送邮件
    }

    /**
     * 测试发送附件.(这里发送图片.)
     *
     * @throws MessagingException
     */
    @Test
    public void sendAttachmentsEmail() throws MessagingException {
        //这个是javax.mail.internet.MimeMessage下的，不要搞错了。
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

        //基本设置.
        helper.setFrom(from_email);//发送者.
        helper.setTo(to_email);//接收者.
        helper.setSubject("测试附件（邮件主题）");//邮件主题.
        helper.setText("这是邮件内容（有附件哦.）");//邮件内容.

        //org.springframework.core.io.FileSystemResource下的:
        //附件1,获取文件对象.
        FileSystemResource file1 = new FileSystemResource(new File("C:\\Users\\lionxxw\\Pictures\\头像\\金木.jpeg"));
        //添加附件，这里第一个参数是在邮件中显示的名称，也可以直接是head.jpg，但是一定要有文件后缀，不然就无法显示图片了。
        helper.addAttachment("头像1.jpg", file1);
        //附件2
        FileSystemResource file2 = new FileSystemResource(new File("C:\\Users\\lionxxw\\Pictures\\头像\\xiao.gif"));
        helper.addAttachment("头像2.jpg", file2);

        mailSender.send(mimeMessage);
    }

    /**
     * 邮件中使用静态资源.
     * 163邮箱报错：
     * •554 DT:SPM 发送的邮件内容包含了未被许可的信息，或被系统识别为垃圾邮件。请检查是否有用户发送病毒或者垃圾邮件；
     * @throws Exception
     */
    @Test
    public void sendInlineMail() throws Exception {
        MimeMessage mimeMessage = mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        //基本设置.
        helper.setFrom(from_email);//发送者.
        helper.setTo(to_email);//接收者.
        helper.setSubject("测试静态资源（邮件主题）");//邮件主题.
        // 邮件内容，第二个参数指定发送的是HTML格式
        //说明：嵌入图片<img src='cid:head'/>，其中cid:是固定的写法，而aaa是一个contentId。
        helper.setText("<body>这是图片：<img src='cid:head' /></body>", true);

        FileSystemResource file = new FileSystemResource(new File("C:\\Users\\lionxxw\\Pictures\\头像\\金木.jpeg"));
        helper.addInline("head", file);

        mailSender.send(mimeMessage);

    }

    /**
     * 模板邮件；
     * @throws Exception
     */
    @Test
    public void sendTemplateMail() throws Exception {

        MimeMessage mimeMessage = mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        //基本设置.
        helper.setFrom(from_email);//发送者.
        helper.setTo(to_email);//接收者.
        helper.setSubject("模板邮件（邮件主题）");//邮件主题.

        Map<String, Object> model = new HashMap<String, Object>();
        model.put("username", "Lionxxw");

        Configuration cfg = new Configuration(Configuration.VERSION_2_3_23);
        // 设定去哪里读取相应的ftl模板
        cfg.setClassForTemplateLoading(this.getClass(), "/emails");
        // 在模板文件目录中寻找名称为name的模板文件
        Template template   = cfg.getTemplate("hello.html");

        String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
        helper.setText(html, true);

        mailSender.send(mimeMessage);
    }
}
