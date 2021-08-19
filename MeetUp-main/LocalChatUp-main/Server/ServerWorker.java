package Server;

import java.io.*;
import java.net.Socket;
import java.util.List;

public class ServerWorker extends Thread{
    private final Socket socket;
    private final Server server;
    private OutputStream outputStream;
    public ServerWorker(Server server, Socket socket) {
        this.server = server;
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            handleClientSocket();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void handleClientSocket() throws IOException {
        InputStream inputStream = socket.getInputStream();
        this.outputStream = socket.getOutputStream();

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        String stopConnection = "quit";
        while ((line = bufferedReader.readLine()) != null) {
            if(line.equalsIgnoreCase(stopConnection)) {
                server.removeWorker(this);
                socket.close();
                break;
            }
            List<ServerWorker> workerList = server.getWorkerList();
            for(ServerWorker serverWorker : workerList){
                serverWorker.send(line);
            }

        }

    }

    private void send(String line) {
        if(line != null){
            try {
                outputStream.write(line.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
