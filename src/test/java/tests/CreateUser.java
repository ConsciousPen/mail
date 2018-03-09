package tests;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class CreateUser {
    public static void main(String args[]) throws Exception{
        Socket socket = new Socket(
                System.getProperty("mail.server.host"), Integer.parseInt(System.getProperty("mail.server.port")));

        PrintStream printStream = new PrintStream(socket.getOutputStream());

        printStream.println(System.getProperty("mail.server.admin.login"));
        printStream.println(System.getProperty("mail.server.admin.password"));
        printStream.println("adduser test test");
        printStream.println("listusers");
        printStream.println("quit");

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        String line = null;

        while((line = bufferedReader.readLine()) != null){
            System.out.println(line);
        }

        printStream.close();
        bufferedReader.close();
        socket.close();
    }
}
