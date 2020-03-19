package de.vcc.webAPI;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class VolumeControlCenter extends JFrame {

	private static final long serialVersionUID = -1591923397885371426L;
	private static final int PORT = 12000;
	public static int clientCount = 0;

	private Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
	private int height = screen.height;
	private int width = screen.width;
	private JPanel contentPane;
	private static JLabel lblClients, lblAddress;
	private ServerSocket ss;

	public static void main(String[] args) {
		new VolumeControlCenter();
	}

	public VolumeControlCenter() {
		setSize(400, 400);
		setLocation(width / 2 - 200, height / 2 - 200);
		setTitle("Volume Control Center");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);

		setVisible(true);

		loadComponents();

		setupSocket();

		try {
			ss.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	private void loadComponents() {
		lblAddress = new JLabel("IP Address: ");
		lblAddress.setBounds(10, 150, 500, 10);
		contentPane.add(lblAddress);
		
		lblClients = new JLabel(clientCount + " Clients connected!");
		lblClients.setBounds(10, 200, 1400, 10);
		contentPane.add(lblClients);
	}

	private void setupSocket() {

		ss = null;
		Socket so = null;

		try {
			ss = new ServerSocket(PORT);
		} catch (IOException e) {
		}

		while (true) {
			try {
				so = ss.accept();
				lblAddress.setText("IP Address: " + ss.getInetAddress().toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
			new APIThread(so).start();
			;
		}
	}

	public static void updateClientCount() {
		lblClients.setText(clientCount + " Clients connected!");
	}
}