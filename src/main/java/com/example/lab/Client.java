package com.example.lab;

import javax.imageio.ImageIO;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Client {
    public static void main(String[] args) throws URISyntaxException {
        try {
            SSLSocketFactory factory=(SSLSocketFactory) SSLSocketFactory.getDefault();
            SSLSocket sslsocket=(SSLSocket) factory.createSocket("127.0.0.1",4321);

            String filename = "spider-man.png";
            URL resource = Client.class.getClassLoader().getResource(filename);
            OutputStream outputStream = sslsocket.getOutputStream();
            Files.copy(Paths.get(resource.toURI()), outputStream);
//            BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(filename));
//            DataOutputStream os = new DataOutputStream(sslsocket.getOutputStream());
//            int c;
//            while ((c = bufferedInputStream.read()) != -1) {
//                os.write(c);
//            }
//            bufferedInputStream.close();
//            os.flush();
//            os.close();
            outputStream.flush();
            outputStream.close();
            sslsocket.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
