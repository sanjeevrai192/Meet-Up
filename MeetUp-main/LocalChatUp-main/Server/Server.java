package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server extends Thread{
    private final int serverPort;
    private ArrayList<ServerWorker> workerList = new ArrayList<>();

    public Server(int serverPort){
        this.serverPort = serverPort;
    }

    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(serverPort);
            while(true){
                System.out.println("About to accept client connection...");
                Socket socket = serverSocket.accept();
                System.out.println("Connection accepted from " + socket);
                ServerWorker serverWorker = new ServerWorker(this, socket);
                workerList.add(serverWorker);
                serverWorker.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<ServerWorker> getWorkerList() {
        return workerList;
    }
    public void removeWorker(ServerWorker serverWorker) {
        workerList.remove(serverWorker);
    }
}
