package tests;

import javax.mail.*;
import java.io.IOException;
import java.util.Properties;

public class ReadEmail {

    public static void main(String args[]) throws MessagingException, IOException {
        String host = "localhost";
        String username = "test";
        String password = "test";

        Properties properties = new Properties();
        properties.put("mail.store.host",host);

        Session session = Session.getDefaultInstance(properties,null);

        Store store = session.getStore("pop3");
        store.connect(host,username,password);

        Folder folder = store.getFolder("INBOX");
        folder.open(Folder.READ_ONLY);

        Message message = folder.getMessage(1);
        System.out.println(message.getFrom()[0]);
        System.out.println(message.getSubject());
        System.out.println(message.getContent());

        folder.close(false);
        store.close();


    }

}
