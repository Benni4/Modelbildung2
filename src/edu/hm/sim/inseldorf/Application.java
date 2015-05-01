package edu.hm.sim.inseldorf;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;

public class Application {

	private JFrame frame;
	private JProgressBar progressBar;
	private JProgressBar progressBar_1;
	private JLabel lblSever;
	private JLabel lblNewLabel;
	private JPanel panel;
	private Canvas canvas;
	private Graphics2D g2D;

	private int xClientsPosition = 0;
	private int yClientsPosition = 0;

	private double stepwidthY;
	private double stepwidthX ;
	private double lengthX;
	private double lengthY ;

	public static int n = 10;

	private int currentDrawedClients;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Application window = new Application();
					window.frame.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Application() {

		initialize();
		updateProgressBar();

	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1146, 611);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		panel = new JPanel();
		panel.setBounds(197, 362, 507, 196);
		frame.getContentPane().add(panel);
		panel.setLayout(null);

		lblNewLabel = new JLabel("Queue");
		lblNewLabel.setBounds(55, 13, 161, 44);
		panel.add(lblNewLabel);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);

		lblSever = new JLabel("Server");
		lblSever.setBounds(383, 7, 45, 20);
		panel.add(lblSever);
		lblSever.setHorizontalAlignment(SwingConstants.CENTER);
		lblSever.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblSever.setHorizontalTextPosition(SwingConstants.CENTER);

		progressBar_1 = new JProgressBar();
		progressBar_1.setBounds(345, 34, 126, 146);
		panel.add(progressBar_1);
		progressBar_1.setBackground(Color.ORANGE);
		progressBar_1.setForeground(Color.RED);
		progressBar_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		progressBar_1.setMaximum(4);
		progressBar_1.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		progressBar_1.setOrientation(SwingConstants.VERTICAL);

		progressBar= new JProgressBar();
		progressBar.setBounds(548, 51, 283, 69);
		frame.getContentPane().add(progressBar);
		progressBar.setFont(new Font("Tahoma", Font.PLAIN, 20));
		progressBar.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		progressBar.setStringPainted(true);
		progressBar.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(99, 16, 150, 40);
		frame.getContentPane().add(menuBar);

		JButton btnNewButton = new JButton("Play");
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		menuBar.add(btnNewButton);

		JButton btnNewButton_1 = new JButton("Reset");
		btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		menuBar.add(btnNewButton_1);


		canvas = new Canvas() {
			private static final long serialVersionUID = 1L;

			@Override
			public void paint(Graphics g){


				// value of current amount of clients in queue from scheduler
				final int valueClients  =2;


				int toDrawOrDeleteClients  = currentDrawedClients - valueClients;

				if(toDrawOrDeleteClients > 0){
					for(int i = 0; i <toDrawOrDeleteClients;i++){
						g.drawOval(xClientsPosition, yClientsPosition, 10, 10);


					}

				}
				else if (toDrawOrDeleteClients <0 ){

				}




			}

		};
		canvas.setBounds(99, 125, 223, 231);

		Rectangle bounds = canvas.getBounds();
		lengthX = bounds.getWidth()-bounds.getX();
		lengthY = bounds.getHeight()-bounds.getY();

		stepwidthX = lengthX/n;
		stepwidthY = lengthY/n;

		xClientsPosition = (int) (bounds.getWidth()+ bounds.getX()-stepwidthX/2);
		yClientsPosition = (int) (bounds.getY() - stepwidthY/2);

		frame.getContentPane().add(canvas);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
	}

	private void updateProgressBar(){



		Thread thread = new Thread(){
			public void run(){
				for(int i  =0 ;i<= 100;i++){

					try {
						this.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					progressBar.setValue(i);
					progressBar.repaint();


					progressBar_1.setValue(i%5);
					progressBar_1.repaint();


				}

			}
			};

			thread.start();


	}


	private void drawClient(){




		Thread thread = new Thread(){
			public void run(){while(true){

				canvas.repaint();





			}


			}};
			}



	}

