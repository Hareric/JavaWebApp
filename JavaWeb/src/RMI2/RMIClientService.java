/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package RMI2;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author Administrator
 */

public interface RMIClientService extends Remote{  
   //刷新聊天信息的方法，该方法供服务器调用
   public void showMessageToClient(String msg)throws RemoteException;

}
