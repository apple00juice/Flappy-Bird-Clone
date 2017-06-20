package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.imageio.ImageIO;
import javax.swing.Action;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Control.Control;

public class ViewMenu extends JPanel {

	public JButton startbutton;

	public JButton exitbutton;

	private JLabel title;

	Control c;
	View v;

	private float buttonWidth;
	private float buttonHeight;

	public ViewMenu(Control c, View v) {
		this.c = c;
		this.v = v;

		setLayout(null);

		buttonWidth = c.getWidth() / 3;
		buttonHeight = c.getWidth() / 10;

		title = new JLabel("Flappy Birds");
		title.setFont(new Font("Arial", 10, 100));
		title.setForeground(Color.red);
		this.add(title);

		startbutton = new JButton("Start");
		startbutton.setBackground(Color.white);
		startbutton.setForeground(Color.ORANGE);
		startbutton.setEnabled(true);
		this.add(startbutton);
		startbutton.addActionListener(c);

		exitbutton = new JButton("Exit");
		exitbutton.setBackground(Color.white);
		exitbutton.setForeground(Color.ORANGE);
		exitbutton.setEnabled(true);
		this.add(exitbutton);
		exitbutton.addActionListener(c);

	}

	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());

		float scale = v.scale;

		title.setFont(new Font("Arial", 10, (int) (100 * scale)));

		title.setBounds((int) ((getWidth() - title.getPreferredSize().getWidth())/2), (int)(getHeight() *0.1),
				(int)title.getPreferredSize().getWidth(), (int)title.getPreferredSize().getHeight());

		startbutton.setFont(new Font("Arial", 10, (int) (50 * scale)));
		startbutton.setBounds((int) (getWidth() - buttonWidth * scale) / 2, getHeight() / 2,
				(int) (buttonWidth * scale), (int) (buttonHeight * scale));

		exitbutton.setFont(new Font("Arial", 10, (int) (50 * scale)));
		exitbutton.setBounds((int) (getWidth() - buttonWidth * scale) / 2, getHeight() / 4 * 3,
				(int) (buttonWidth * scale), (int) (buttonHeight * scale));

	}

}
