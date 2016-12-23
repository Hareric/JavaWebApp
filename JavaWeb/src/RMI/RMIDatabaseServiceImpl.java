/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package RMI;

import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;
import database.*;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;

//实现记录插入的方法
/**
 *
 * @author Administrator
 */
interface RMIDatabaseService extends Remote {

    //定义一个远程方法，在给定的数据库中插入一条记录，并返回插入的结果.
    public String insert(String no, String name, int age,
            String sClass) throws RemoteException, SQLException;
}

public class RMIDatabaseServiceImpl extends UnicastRemoteObject
        implements RMIDatabaseService {

    public RMIDatabaseServiceImpl() throws RemoteException {
    }

    public String insert(String no, String name, int age, String sClass)
            throws RemoteException {

        DbHelper dh = Connector.getInstance();
        String sName_b = null, sClass_b = null;
        try {
            sName_b = new String(name.getBytes("GB2312"), "ISO-8859-1");
            sClass_b = new String(sClass.getBytes("GB2312"), "ISO-8859-1");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(RMIDatabaseServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        dh.executeUpdate("insert into students(NO,NAME,AGE,CLASS) values(?,?,?,?);", no, sName_b, age, sClass_b);
//        dh.showTable("students");
        return "插入成功";


    }
}
