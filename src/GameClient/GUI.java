package GameClient;

import javax.swing.JButton;
import javax.swing.JFrame;

public class GUI {

	public static void main(String[] args) {
		final int dim = 80;

		JButton[][] arr = new JButton[3][3];

		JFrame f = new JFrame("Gioco tris");
		ActionListener gestoreEventi = new ActionListener(arr, f);
		f.setBounds(100, 200, 4 * dim, 4 * dim);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setLayout(null);

		for (int r = 0; r < 3; r++) {
			for (int c = 0; c < 3; c++) {
				arr[r][c] = new JButton();
				arr[r][c].setBounds(c * dim, r * dim, dim, dim);
				f.add(arr[r][c]);
				arr[r][c].setActionCommand(r + "" + c);
				arr[r][c].addActionListener(gestoreEventi);
			}
		}

		f.setVisible(true);
		f.setEnabled(false);
		gestoreEventi.start();
	}
}

