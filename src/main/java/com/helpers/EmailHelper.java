package com.helpers;

import com.ApplicationManager;
import com.base.HelperBase;

import javax.mail.*;
import java.util.Properties;

public class EmailHelper extends HelperBase{

    public EmailHelper(ApplicationManager applicationManager) {
        super(applicationManager);
    }

    public String getNewMail(String user, String password) {
        Properties props = System.getProperties();
        // conn with server
        Session session = Session.getDefaultInstance(props);
        try {
            // open mail store
            Store store = session.getStore("pop3");
            store.connect(manager.getProperty("mail.server.host"), user, password);
            // access to inbox READ_WRITE
            Folder folder = store.getDefaultFolder().getFolder("INBOX");
            folder.open(Folder.READ_WRITE);

            if (folder.getMessageCount() != 1) {
                return null;
            }

            Message message = folder.getMessage(1);
            // mark as delete
            message.setFlag(Flags.Flag.DELETED, true);
            // get email content
            String msg = (String) message.getContent();
            // close folder
            folder.close(true);
            // close store
            store.close();

            return msg;

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
