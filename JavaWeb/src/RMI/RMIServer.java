/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package RMI;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

/**
 *
 * @author Administrator
 */
public class RMIServer {

    public static void main(String args[]) {

        try {
            //第一步注册并监听1099端口
            LocateRegistry.createRegistry(1099);
            //第二步实例化服务器的远程对象
            RMIServerService service = new RMIServerServiceImpl();
            RMIDatabaseService database = new RMIDatabaseServiceImpl();
            //第三部以助记符号来发布远程对象
            Naming.rebind("RMIService", service);
            Naming.rebind("RMIDatabase", database);
            System.out.println("服务器注册了2个RMIService对象");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
