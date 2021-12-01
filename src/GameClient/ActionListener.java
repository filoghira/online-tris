package GameClient;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

class ActionListener implements java.awt.event.ActionListener {

    JButton[][] b;
    Socket s;
    JFrame f;
    char player;

    public ActionListener(JButton[][] b, JFrame f) {
        this.b = b;
        this.f = f;
    }

    public void start() {
        try {
            s = new Socket(InetAddress.getByName("localhost"), 8083);
            PrintWriter pw = new PrintWriter(
                    s.getOutputStream(),
                    true
            );

            // Send the message
            pw.println("GAMEREQUEST");
            pw.println("");

            BufferedReader br = new BufferedReader(
                    new InputStreamReader(
                            s.getInputStream()
                    )
            );

            String input;
            while ((((input = br.readLine()) != null) && !input.equals(""))) {
                if (input.startsWith("START ")) {
                    player = input.charAt(6);
                    if (player == 'o') {
                        waitPlayer();
                    }
                }
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton b = (JButton) e.getSource();
        int r = b.getActionCommand().charAt(0) - '0';
        int c = b.getActionCommand().charAt(1) - '0';

        try {
            PrintWriter pw = new PrintWriter(
                    s.getOutputStream(),
                    true
            );

            // Send the message
            pw.println("MOVE " + r + " " + c);
            pw.println("");
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        f.setEnabled(false);
        waitPlayer();
    }

    public void waitPlayer() {
        BufferedReader br;
        try {
            br = new BufferedReader(
                    new InputStreamReader(
                            s.getInputStream()
                    )
            );

            String input;
            while ((((input = br.readLine()) != null) && !input.equals(""))) {
                if (input.startsWith("MOVE ")) {
                    f.setEnabled(true);
                    int r = input.charAt(5) - '0';
                    int c = input.charAt(6) - '0';
                    b[r][c].setText(player + "");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
