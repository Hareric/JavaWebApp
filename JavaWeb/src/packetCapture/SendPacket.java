/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package packetCapture;

import java.io.IOException;
import java.net.InetAddress;
import jpcap.JpcapCaptor;
import jpcap.JpcapSender;
import jpcap.NetworkInterface;
import jpcap.packet.EthernetPacket;
import jpcap.packet.IPPacket;
import jpcap.packet.TCPPacket;

/**
 *
 * @author Administrator
 */
public class SendPacket {

    public static void sendTcpPacket(int num) throws java.io.IOException {
        NetworkInterface[] devices = JpcapCaptor.getDeviceList();
        JpcapSender sender = JpcapSender.openDevice(devices[3]);
        TCPPacket tcp = new TCPPacket(8000, 80, 56, 78, false, false, false, false, true, false, true, true, 20, 10);
        tcp.setIPv4Parameter(0, false, false, false, 0, false, false, false, 0, 1010101, 100, IPPacket.IPPROTO_TCP,
                InetAddress.getByName("192.168.207.67"), InetAddress.getByName("222.201.101.15"));
        tcp.data = ("20141002419 陈文达").getBytes("GB2312");
        EthernetPacket ether = new EthernetPacket();
        ether.frametype = EthernetPacket.ETHERTYPE_IP;
        //MAC地址要转换成十进制，ipconfig /all 查看本机的MAC地址.
        //源地址是自己机器的MAC地址。
        ether.src_mac = new byte[]{(byte) 68, (byte) 55, (byte) 230, (byte) 197, (byte) 218, (byte) 87};
        //arp -a,查看默认网关（其IP一般是.1或.254的机器）的MAC地址，用默认网关作//为下一跳转发该包.
        ether.dst_mac = new byte[]{(byte) 0, (byte) 17, (byte) 93, (byte) 156, (byte) 148, (byte) 00};
        tcp.datalink = ether;
        for(int i=0; i<num; i++){
            sender.sendPacket(tcp);
        }
    }
    //44 37 E6 C5 DA 57
    public static void  main(String args[]) throws IOException{
       sendTcpPacket(2);
    }
}
