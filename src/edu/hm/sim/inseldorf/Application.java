package edu.hm.sim.inseldorf;

import java.awt.EventQueue;

import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;

import javax.swing.JFrame;

import java.awt.CardLayout;

import javax.swing.JPanel;
import javax.swing.JToolBar;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.time.Clock;

import javax.swing.Box;
import javax.swing.JEditorPane;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;

import java.awt.event.ContainerAdapter;
import java.awt.event.ContainerEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.Dimension;
import java.awt.ComponentOrientation;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JSeparator;
import java.awt.Button;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;

public class Application {

	private JFrame frame;
	private JProgressBar progressBar;
	private JProgressBar progressBar_1;
	private JLabel lblSever;
	private JLabel lblNewLabel;
	private JSlider slider;

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
		frame.setBounds(100, 100, 762, 611);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		lblNewLabel = new JLabel("Queue");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(59, 251, 200, 50);
		frame.getContentPane().add(lblNewLabel);
		
		progressBar= new JProgressBar();
		progressBar.setFont(new Font("Tahoma", Font.PLAIN, 20));
		progressBar.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		progressBar.setStringPainted(true);
		progressBar.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		progressBar.setBounds(59, 318, 268, 86);
		frame.getContentPane().add(progressBar );
		
		lblSever = new JLabel("Server");
		lblSever.setHorizontalAlignment(SwingConstants.CENTER);
		lblSever.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblSever.setHorizontalTextPosition(SwingConstants.CENTER);
		lblSever.setBounds(392, 161, 200, 78);
		frame.getContentPane().add(lblSever);
		
		progressBar_1 = new JProgressBar();
		progressBar_1.setBackground(Color.ORANGE);
		progressBar_1.setForeground(Color.RED);
		progressBar_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		progressBar_1.setMaximum(4);
		progressBar_1.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		progressBar_1.setOrientation(SwingConstants.VERTICAL);
		progressBar_1.setBounds(392, 251, 202, 192);
		frame.getContentPane().add(progressBar_1);
		
		slider = new JSlider();
		slider.setBounds(392, 95, 200, 50);
		frame.getContentPane().add(slider);
		
		JButton btnNewButton = new JButton("Play");
		btnNewButton.setIcon(new ImageIcon("C:\\Users\\Stefan\\git\\REPO\\Modelbildung2\\resources\\Step-Forward-Pressed-icon.png"));
		btnNewButton.setBounds(102, 20, 150, 133);
		frame.getContentPane().add(btnNewButton);
		
		JLabel lblNewLabel_1 = new JLabel("Simulation Time Factor");
		lblNewLabel_1.setBounds(392, 29, 200, 50);
		frame.getContentPane().add(lblNewLabel_1);
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
}
