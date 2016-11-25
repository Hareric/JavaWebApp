/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socketudp;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Date;

/**
 *
 * @author Har
 */
public class UDPServer {
    private final int port = 6006;
    private final DatagramSocket socket;
    public UDPServer() throws IOException{
        socket = new DatagramSocket(port);
        System.out.printf("服务器成功启动, 端口为:%d\n", this.port);
    }
    
    public String echo (String msg){
        return "20141002419 陈文达:" + new Date().toString() + msg;
    }
    
    public void service(){
        while(true){
            try{
                DatagramPacket packet = new DatagramPacket(new byte[512], 512);
                socket.receive(packet);
                String msg = new String(packet.getData(), 0, packet.getLength(), "GB2312");
                System.out.println(packet.getAddress()+":"+packet.getPort()+">"+msg);
                packet.setData(echo(msg).getBytes("GB2312"));
                socket.send(packet);
            }
            catch(IOException e){
               
            }
        }
    }
    public static void main(String args[]) throws IOException{
        
        new UDPServer().service();
    }
}
