package GameServer;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private static final int PORT = 8083;
    private List<ServerThread> clients = new ArrayList<>();
    private List<ServerThread[]> rooms = new ArrayList<>();
    private ServerThread waiting;

    public static void main(String[] args) {
        try {
            new Server().start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void start() throws IOException {
        ServerSocket server = new ServerSocket(PORT);
        try {
            while (true) {
                Socket s = server.accept();
                clients.add(new ServerThread(s, this));
                clients.get(clients.size()-1).start();
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public ServerThread addCouple(ServerThread self) throws IOException {
        if(waiting == null) {
            waiting = self;
            self.player = 'x';
            return null;
        } else {
            rooms.add(new ServerThread[]{waiting, self});
            self.player = 'o';
            ServerThread temp = waiting;
            waiting.ready(self);
            waiting = null;
            return temp;
        }
    }


}

