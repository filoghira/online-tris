package GameServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

class ServerThread extends Thread {
    private Socket socket;
    private ServerThread partner;
    private Server s;
    char player;

    public ServerThread(Socket socket, Server s) {
        this.socket = socket;
        this.s = s;
    }

    @Override
    public void run() {
        super.run();
        try {
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(
                            socket.getInputStream()
                    )
            );

            String input;
            while (!(input = br.readLine()).equals("")) {
                if (partner == null && input.startsWith("GAMEREQUEST")) {
                    partner = s.addCouple(this);
                    if (partner != null)
                        ready(partner);
                } else if (input.startsWith("MOVE ")) {
                    partner.move(input);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void ready(ServerThread partner) throws IOException {
        this.partner = partner;

        // Prepare the message
        PrintWriter pw = new PrintWriter(
                socket.getOutputStream(),
                true
        );

        // Send the message
        pw.println("START " + player);
        pw.println("");
    }

    void move(String move) throws IOException {
        // Prepare the message
        PrintWriter pw = new PrintWriter(
                socket.getOutputStream(),
                true
        );

        // Send the message
        pw.println(move);
        pw.println("");
    }
}
