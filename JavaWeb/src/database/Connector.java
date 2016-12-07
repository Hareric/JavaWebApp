/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

/**
 * 读取配置文件 生成数据库连接
 *
 * @author Eric_Chan
 *
 */
public class Connector {

    private static DbHelper connector = null;

    public void loadConfig() {
        // 读取数据库配置文件
//        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("dbConfig.properties");
//        Properties p = new Properties();
//        try {
//            p.load(inputStream);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        String ipAddress = p.getProperty("ip");
//        int port = Integer.parseInt(p.getProperty("port"));
//        String user = p.getProperty("user");
//        String pwd = p.getProperty("pwd");
//        String dbName = p.getProperty("dbName");
        String ipAddress = "222.201.101.15";
        int port = 3306;
        String user = "myuser";
        String pwd = "mysql";
        String dbName = "studentdb1";
        // 根据提供的参数连接数据库
        Connector.connector = DbHelper.getInstance();
        Connector.connector.connSQL(user, pwd, dbName, ipAddress, port);
    }

    public static DbHelper getInstance() {
        if (Connector.connector == null) {
            new Connector().loadConfig();
        }
        return Connector.connector;
    }

    public static void main(String args[]) throws UnsupportedEncodingException {
        
        DbHelper db = Connector.getInstance();
        String sName=new String("姓名".getBytes("GB2312"),"ISO-8859-1");
        String sClass=new String("班级".getBytes("GB2312"),"ISO-8859-1");
//        db.executeUpdate("insert into students(NO,NAME,AGE,CLASS,IP) values(?,?,?,?,?);","20141002419",sName, 18, sClass,"192.168.207.75");
        db.showTable("students");

    }
}
