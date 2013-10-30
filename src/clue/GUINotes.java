package clue;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JDialog;

public class GUINotes extends JDialog {
	private JButton button1, button2;
	public GUINotes(){
		setTitle("Notes");
		setSize(200, 100);
		setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
		button1 = new JButton("Login");
		button2 = new JButton("Say hello");
		// Experiment with different locations!
		add(button1, BorderLayout.CENTER);
		add(button2, BorderLayout.SOUTH);
	}
}
