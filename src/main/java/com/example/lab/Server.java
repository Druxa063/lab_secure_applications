package com.example.lab;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class Server {
    public static void main(String[] args) {
        SSLServerSocketFactory factory=(SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
        try(SSLServerSocket sslserversocket = ((SSLServerSocket) factory.createServerSocket(1234))) {
            System.out.println("Server is running");
            SSLSocket sslsocket=(SSLSocket) sslserversocket.accept();
            String filename = "src/main/resources/spider-man.png_copy.png";
            InputStream inputStream = sslsocket.getInputStream();
            Files.copy(inputStream, Paths.get(filename), StandardCopyOption.REPLACE_EXISTING);
            inputStream.close();
            sslserversocket.close();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }
}
