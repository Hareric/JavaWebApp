/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package scanner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 *
 * @author Administrator
 */
public class Scanner {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        try {
            Socket socket = new Socket("222.201.101.15", 15060);
            //主动向服务器发起连接,实现TCP中三次握手的过程。
            //若不成功(网络问题,地址错误,服务器资源紧张等),抛出错误，其错误信息交由调用者处理。
            //若成功,做下面两件事情。   
            OutputStream socketOut = socket.getOutputStream();
            PrintWriter pw = new PrintWriter(new OutputStreamWriter(socketOut, "GB2312"), true);
            //得到网络输出字节流地址,并装饰成网络输出字符流
            InputStream socketIn = socket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(socketIn, "GB2312"));
            //得到网络输入字节流地址,并装饰成网络输入字符流

            pw.println("20141002419 陈文达");
            String msg;
            try {
                msg = br.readLine();
                System.out.print(msg);
            } catch (IOException ex) {
                msg = null;
            }
        } catch (Exception e) {
            System.out.print("fadfa");
        }
    }
}
