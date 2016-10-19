package GroupTalk;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;

public class GroupServer {

    private static Set<Socket> Members; // 在线成员集合
    private int port = 8013;
    private ServerSocket serverSocket;
    private ExecutorService executorService;  //线程池
    private final int POOL_SIZE = 15;  //单个CPU时线程池中工作线程的数目

    public GroupServer() throws IOException {
        Members = new HashSet<Socket>();
        serverSocket = new ServerSocket(port);//启动服务器

        //创建线程池
        //Runtime的availableProcessors()方法返回当前系统的CPU的数目
        //系统的CPU越多，线程池中工作线程的数目也越多
        executorService = Executors.newFixedThreadPool(
                Runtime.getRuntime().availableProcessors() * POOL_SIZE);
        System.out.println("多用户服务器启动");
    }

    //在GroupServer主类中定义群组消息发送方法。
    public static void sendToAllMembers(String msg) throws IOException {
        PrintWriter pw;
        Socket tempSocket;
        OutputStream socketOut;
        Iterator it = Members.iterator();
        while (it.hasNext()) {//遍历在线成员set集合.
            tempSocket = (Socket) it.next(); //取出一个成员
            try{
            socketOut = tempSocket.getOutputStream();//得到输出流
            }catch(SocketException e){
                removeMember(tempSocket);
                continue;
            }
            //装饰成字符流
            pw = new PrintWriter(new OutputStreamWriter(socketOut, "GB2312"), true);
            pw.println(msg);//发送聊天信息给成员
        }
        System.out.println(Members.size());
    }//end while群组发送结束。

    //在GroupServer主类中定义组员退出方法。
    public static void removeMember(Socket socket) {
        Members.remove(socket);//删除一个成员
    }
  

    public void service() {
        while (true) {
            Socket socket = null;
            try {
                socket = serverSocket.accept(); //监听客户请求, 阻塞语句.
                //接受一个客户请求,从线程池中拿出一个线程专门处理该客户.
                GroupServer.Members.add(socket);  // 将新添加的用户添加进用户集合
                executorService.execute(new Handler(socket));
            } catch (IOException e) {
            }
        }
    }

    public static void main(String args[]) throws IOException {
        new GroupServer().service();
    }
}

//定义内部类，实现线程接口
class Handler implements Runnable {

    private Socket socket;

    public Handler(Socket socket) {
        this.socket = socket;
    }

    private PrintWriter getWriter(Socket socket) throws IOException {
        OutputStream socketOut = socket.getOutputStream();
        return new PrintWriter(new OutputStreamWriter(socketOut, "GB2312"), true);
    }

    private BufferedReader getReader(Socket socket) throws IOException {
        InputStream socketIn = socket.getInputStream();
        return new BufferedReader(new InputStreamReader(socketIn, "GB2312"));
    }

    public void run() {//覆盖线程体
        try {
            System.out.println("New connection accepted " + socket.getInetAddress());
            BufferedReader br = getReader(socket);//字节装饰成字符流
            //PrintWriter pw = getWriter(socket); //字节装饰成字符流
            String msg = null;
            while ((msg = br.readLine()) != null) {
                //pw.println("From ThreadServer:" + msg);//send to client.
                GroupServer.sendToAllMembers(msg);  // send to all menbers
                if (msg.contains("bye".subSequence(0, 2))) {
                    System.out.println(socket.getInetAddress() + ":" + "Exit");
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (socket != null) {
                    socket.close();
                }
            } catch (IOException e) {
            }
        }
    }

}
