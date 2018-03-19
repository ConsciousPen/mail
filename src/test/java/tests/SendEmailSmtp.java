package tests;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class SendEmailSmtp {
    public static void main(String args[]) throws Exception{
        String host = System.getProperty("mail.server.host");
        String sender = "vp@lala.aaa";
        String receiver = "test@localhost";
        String subject = "greetings";

        Properties p = new Properties();
        p.put("mail.smtp.host",host);

        Session session = Session.getInstance(p,null);

        Message message = new MimeMessage(session);
        // describe message
        message.setFrom(new InternetAddress(sender));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(receiver));
        message.setSubject(subject);
        message.setText("Hello! How are yo?");

        Transport.send(message);
    }
}
