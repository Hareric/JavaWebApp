/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package RMI;

import java.io.UnsupportedEncodingException;
import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Administrator
 */
public class RMIServerServiceImpl extends UnicastRemoteObject implements RMIServerService {

    //默认构造方法
    public RMIServerServiceImpl() throws RemoteException {
    }

//    public String echo(String msg) throws RemoteException {
//
//        return "来自RMI服务器的：" + msg;
//
//    }
//    
//
//    @Override
//    public String echo(String yourNo, byte[] yourName) throws RemoteException {
//        String name = null;
//        try {
//            name = new String(yourName, "GB2312");
//        } catch (UnsupportedEncodingException ex) {
//            Logger.getLogger(RMIServerServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return yourNo + " " + name;
//    }
    public String send(String msg) throws RemoteException {
        return "20141002419 Server's RMI" + msg + "\r\n";
    }

    public Date getTime() throws RemoteException {
        return new Date();
    }

    @Override
    public String send(String yourNo, byte[] yourName) throws RemoteException {
        try {
            return yourNo + new String(yourName,"UTF-8");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(RMIServerServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            
        }
        return "Error";
        
    }
}
