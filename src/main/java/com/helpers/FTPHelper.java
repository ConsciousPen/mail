package com.helpers;

import com.ApplicationManager;
import com.base.HelperBase;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;

public class FTPHelper extends HelperBase {

    public static Logger log = Logger.getLogger(FTPHelper.class.getName());

    private FTPClient ftp;

    public FTPHelper(ApplicationManager manager) {
        super(manager);
    }

    private void initFtpConnection(){
        String ftpServer = manager.getProperty("ftp.host");
        String login = manager.getProperty("ftp.login");
        String password = manager.getProperty("ftp.password");
        String appPath = manager.getProperty("ftp.path");

        ftp = new FTPClient();

        try{
            ftp.connect(ftpServer);
            ftp.login(login,password);
            log.info("Successful connection to" + ftpServer + ftp.getReplyString());
            ftp.changeWorkingDirectory(appPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void closeFtpConnection(){
        try{
            ftp.disconnect();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void installConfig(){
        String config = manager.getProperty("ftp.config");
        initFtpConnection();
        try{
            boolean backupPresence = false;
            FTPFile[] files = ftp.listFiles();

            for(int i = 0; i< files.length; i++){
                if(files[i].getName().equals(config+".bak")){
                    backupPresence = true;
                    log.info(config+".bak"+" present");
                    break;
                }
            }
            if(!backupPresence){
                ftp.rename(config,config+".bak");
            }
            InputStream in = this.getClass().getResourceAsStream("/"+config);
            ftp.storeFile(config,in);

        } catch (IOException e) {
            e.printStackTrace();
        }
        closeFtpConnection();
    }

    public void restoreConfig(){
        String config = manager.getProperty("ftp.config");
        initFtpConnection();

        try{
            boolean backupPresence = false;
            FTPFile[] files = ftp.listFiles();

            for(int i = 0; i < files.length; i++){
                if(files[i].getName().equals(config+".bak")){
                    backupPresence = true;
                    break;
                }
            }
            if(backupPresence){
                ftp.deleteFile(config);
                ftp.rename(config+".bak",config);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        closeFtpConnection();
    }
}
