/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ftpclient;

/**
 *
 * @author Har 主要功能：连接服务器数据端口、发送文件名、保存下载的文件或上传文件，文件传输完成后关闭数据连接。 该程序有3个方法：
 * （1）构造方法，FileDataClient(String ip,String port)，主要功能是向服务器的数据端口请求连接；
 * （2）文件下载方法fileGet(fileName)[见附录代码]。
 * 主要功能是准备当地磁盘空文件，向服务器发送文件名（基于字符串输出流操作），然后接收网络文件数据并保存当地磁盘（基于字节流操作），关闭数据套接字。
 */
import java.io.*;
import java.net.*;
import javax.swing.JFileChooser;

public class FileDataClient {
    private Socket dataSocket = null;
    private PrintWriter pw;
    private BufferedReader br;
    public FileDataClient(String ip, String port) throws IOException {
        dataSocket = new Socket(ip, Integer.parseInt(port)-1);
        //主动向服务器发起连接,实现TCP中三次握手的过程。
        //若不成功(网络问题,地址错误,服务器资源紧张等),抛出错误，其错误信息交由调用者处理。
        //若成功,做下面两件事情。   
        OutputStream socketOut = dataSocket.getOutputStream();
        pw = new PrintWriter(new OutputStreamWriter(socketOut, "GB2312"), true);
        //得到网络输出字节流地址,并装饰成网络输出字符流
        InputStream socketIn = dataSocket.getInputStream();
        br = new BufferedReader(new InputStreamReader(socketIn, "GB2312"));
        //得到网络输入字节流地址,并装饰成网络输入字符流
    }

    public String fileGet(String fileName) throws IOException {
        boolean flag = false;

        if (dataSocket != null) {//自定义变量dataSocket
            byte[] buff = new byte[1024 * 2];//用来缓冲接收的字节数据

            //(1)文件保存对话框.
            JFileChooser chooser = new JFileChooser();
            File saveFile = new File(fileName);
            chooser.setSelectedFile(saveFile);
            int stat = chooser.showSaveDialog(null);
            if (stat == JFileChooser.APPROVE_OPTION) {
                saveFile = chooser.getSelectedFile();
            } else {
                saveFile = null;
            }

            if (saveFile != null) {
                FileOutputStream fileOut = new FileOutputStream(saveFile); //新建本地空文件.

                InputStream socketIn = dataSocket.getInputStream();//网络字节输入流
                OutputStream socketOut = dataSocket.getOutputStream();//网络字节数出流

                //(2)发送请求的文件名，字符串读写功能
                PrintWriter pw = new PrintWriter(new OutputStreamWriter(socketOut, "GB2312"), true);
                pw.println(fileName);

                //(3)接收服务器的数据文件，字节读写功能
                int len = socketIn.read(buff);//读一块到缓冲区.
                while (len != -1) {
                    fileOut.write(buff, 0, len);//写一块到文件.
                    len = socketIn.read(buff);
                    flag = true;
                }
                //(4)文件传输完毕，关闭数据套接字。
                fileOut.close();
                //JOptionPane.showMessageDialog(null, "文件接收完毕.");
                dataSocket.close();
                if (flag) {
                    return "文件下载成功.";
                } else {
                    saveFile.delete();
                    return "文件名错误或文件下载失败.";
                }
            } else {
                dataSocket.close();
                return "本地文件创建失败.";
            }
        } else {
            return "服务器连接失败.";
        }
    }

}
