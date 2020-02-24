package com.example.lab;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ProxyServer {
    public static void main(String[] args) {
        proxyServer();
    }

    private static void proxyServer() {
        SSLServerSocketFactory factory=(SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
        try(SSLServerSocket sslserversocket = ((SSLServerSocket) factory.createServerSocket(4321))) {
            System.out.println("ProxyServer is running");
            SSLSocket sslsocket=(SSLSocket) sslserversocket.accept();

            proxyClient(sslsocket.getInputStream());
            sslserversocket.close();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }

    private static void proxyClient(InputStream inputStream)  {
        try {
            SSLSocketFactory factory=(SSLSocketFactory) SSLSocketFactory.getDefault();
            SSLSocket sslsocket=(SSLSocket) factory.createSocket("127.0.0.1",1234);

            OutputStream outputStream = sslsocket.getOutputStream();
            copy(inputStream, outputStream);
            inputStream.close();
            outputStream.flush();
            outputStream.close();
            sslsocket.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private static void copy(InputStream source, OutputStream sink) throws IOException {
        byte[] buf = new byte[8192];
        int n;
        while ((n = source.read(buf)) > 0) {
            sink.write(buf, 0, n);
        }
    }
}
