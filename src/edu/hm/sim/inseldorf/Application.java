package edu.hm.sim.inseldorf;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTextPane;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class Application {

	private JFrame frmInseldorf;
	private JProgressBar progressBar;
	private JProgressBar progressBar_1;
	private JLabel lblSever;
	private JLabel lblNewLabel;
	private JPanel panel;
	private Canvas canvas;

	private ChartPanel chartPanel;

	private int xClientsPosition = 0;
	private int yClientsPosition = 0;

	private double stepwidthY;
	private double stepwidthX;
	private double lengthX;
	private double lengthY;

	public static int n = 10;

	private int currentDrawedClients;
	private JPanel ParameterPanal;
	private JLabel label;
	private JLabel label_1;

	// Simulation
	private Simulation sim;

	// Parameter Fields for the simulation
	private JSlider slider;
	private JSpinner spinner;
	private JSpinner spinner_1;

	// Console
	private JTextPane txtpnConsole;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					Application window = new Application();
					window.frmInseldorf.setVisible(true);

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
		frmInseldorf = new JFrame();
		frmInseldorf.setTitle("Inseldorf GUI");
		frmInseldorf.setBounds(100, 100, 1371, 713);
		frmInseldorf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmInseldorf.getContentPane().setLayout(null);

		panel = new JPanel();
		panel.setBounds(45, 329, 507, 196);
		frmInseldorf.getContentPane().add(panel);
		panel.setLayout(new GridLayout(0, 2, 0, 0));

		lblNewLabel = new JLabel("Queue");
		panel.add(lblNewLabel);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);

		lblSever = new JLabel("Server");
		panel.add(lblSever);
		lblSever.setHorizontalAlignment(SwingConstants.CENTER);
		lblSever.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblSever.setHorizontalTextPosition(SwingConstants.CENTER);

		label = new JLabel("");
		panel.add(label);

		progressBar_1 = new JProgressBar();
		panel.add(progressBar_1);
		progressBar_1.setBackground(Color.ORANGE);
		progressBar_1.setForeground(Color.RED);
		progressBar_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		progressBar_1.setMaximum(4);
		progressBar_1
				.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		progressBar_1.setOrientation(SwingConstants.VERTICAL);

		progressBar = new JProgressBar();
		panel.add(progressBar);
		progressBar.setFont(new Font("Tahoma", Font.PLAIN, 20));
		progressBar.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		progressBar.setStringPainted(true);
		progressBar
				.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

		label_1 = new JLabel("");
		panel.add(label_1);

		// Warte schlagen visualisierung TODO

		canvas = new Canvas() {
			private static final long serialVersionUID = 1L;

			@Override
			public void paint(Graphics g) {

				// value of current amount of clients in queue from scheduler
				final int valueClients = 2;

				int toDrawOrDeleteClients = currentDrawedClients - valueClients;

				if (toDrawOrDeleteClients > 0) {
					for (int i = 0; i < toDrawOrDeleteClients; i++) {
						g.drawOval(xClientsPosition, yClientsPosition, 10, 10);

					}

				} else if (toDrawOrDeleteClients < 0) {

				}

			}

		};
		canvas.setBounds(68, 172, 213, 103);

		Rectangle bounds = canvas.getBounds();
		lengthX = bounds.getWidth() - bounds.getX();
		lengthY = bounds.getHeight() - bounds.getY();

		stepwidthX = lengthX / n;
		stepwidthY = lengthY / n;

		xClientsPosition = (int) (bounds.getWidth() + bounds.getX() - stepwidthX / 2);
		yClientsPosition = (int) (bounds.getY() - stepwidthY / 2);

		frmInseldorf.getContentPane().add(canvas);

		// xy plot Chart für visualisierung

		// XYSeriesCollection defaultDataset =new XYSeriesCollection();
		// ChartFactory.createXYLineChart(
		// "1. PLot in Java", "x", "y",defaultDataset,
		// PlotOrientation.VERTICAL, true, true, false)

		chartPanel = new ChartPanel((JFreeChart) null);
		chartPanel.setBounds(886, 149, 430, 420);

		chartPanel.setBorder(new LineBorder(Color.black));
		chartPanel.setBackground(Color.gray);
		frmInseldorf.getContentPane().add(chartPanel);

		JToolBar toolBar = new JToolBar();
		toolBar.setFont(new Font("Segoe UI", Font.PLAIN, 18));
		toolBar.setBackground(Color.WHITE);
		toolBar.setBounds(0, 0, 1006, 89);
		frmInseldorf.getContentPane().add(toolBar);

		JButton btnNewButton = new JButton("Play");
		btnNewButton.setForeground(Color.BLUE);
		btnNewButton.setBackground(Color.WHITE);
		toolBar.add(btnNewButton);
		btnNewButton.setFont(new Font("Arial Black", Font.PLAIN, 20));

		JButton btnNewButton_1 = new JButton("Reset");
		btnNewButton_1.setForeground(Color.BLUE);
		btnNewButton_1.setBackground(Color.WHITE);
		toolBar.add(btnNewButton_1);
		btnNewButton_1.setFont(new Font("Arial Black", Font.PLAIN, 20));

		JPanel SliderTool = new JPanel();
		SliderTool.setForeground(Color.WHITE);
		SliderTool.setBackground(Color.WHITE);
		toolBar.add(SliderTool);
		SliderTool.setLayout(new GridLayout(2, 1, 10, 4));

		JLabel SliderToolLBL = new JLabel("Simulation Time regulator");
		SliderToolLBL.setForeground(Color.BLUE);
		SliderToolLBL.setBackground(Color.WHITE);
		SliderToolLBL.setFont(new Font("Arial Black", Font.PLAIN, 16));
		SliderToolLBL.setHorizontalAlignment(SwingConstants.CENTER);
		SliderTool.add(SliderToolLBL);

		slider = new JSlider();
		slider.setMinimum(1);
		slider.setPaintTicks(true);
		slider.setForeground(Color.BLUE);
		slider.setBackground(Color.WHITE);
		SliderTool.add(slider);

		ParameterPanal = new JPanel();
		toolBar.add(ParameterPanal);
		ParameterPanal.setLayout(new GridLayout(2, 2, 10, 8));

		JTextPane txtpnMittlereAnkunftszeit = new JTextPane();
		txtpnMittlereAnkunftszeit.setEditable(false);
		txtpnMittlereAnkunftszeit.setForeground(Color.BLUE);
		txtpnMittlereAnkunftszeit.setBackground(Color.WHITE);
		txtpnMittlereAnkunftszeit.setText("mittlere Ankunftszeit");
		ParameterPanal.add(txtpnMittlereAnkunftszeit);

		spinner = new JSpinner();
		ParameterPanal.add(spinner);

		JTextPane txtpnMittlereBedienzeit = new JTextPane();
		txtpnMittlereBedienzeit.setEditable(false);
		txtpnMittlereBedienzeit.setForeground(Color.BLUE);
		txtpnMittlereBedienzeit.setText("mittlere Bedienzeit");
		txtpnMittlereBedienzeit.setBackground(Color.WHITE);
		ParameterPanal.add(txtpnMittlereBedienzeit);

		spinner_1 = new JSpinner();
		ParameterPanal.add(spinner_1);

		JPanel OutputPanel = new JPanel();
		toolBar.add(OutputPanel);
		OutputPanel.setLayout(new GridLayout(2, 1, 0, 0));

		JLabel lblConsole = new JLabel("Console");
		lblConsole.setForeground(Color.BLUE);
		lblConsole.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblConsole.setHorizontalAlignment(SwingConstants.CENTER);
		OutputPanel.add(lblConsole);

		txtpnConsole = new JTextPane();
		txtpnConsole.setFont(new Font("Tahoma", Font.PLAIN, 12));
		OutputPanel.add(txtpnConsole);

		JPanel ChartChoosePanel = new JPanel();
		ChartChoosePanel.setBounds(612, 149, 259, 420);
		frmInseldorf.getContentPane().add(ChartChoosePanel);
		ChartChoosePanel.setLayout(new GridLayout(5, 1, 10, 5));

		JButton btnMittlereWarteschlangenlnge = new JButton(
				"mittlere Warteschlangenl\u00E4nge");
		btnMittlereWarteschlangenlnge.setForeground(Color.BLUE);
		ChartChoosePanel.add(btnMittlereWarteschlangenlnge);

		btnMittlereWarteschlangenlnge.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				XYSeries xySeries = new XYSeries("xySeries");

				for (int i = 0; i < 100; i++) {
					xySeries.add(i, i / 2.3);
				}

				XYSeriesCollection dataset = new XYSeriesCollection();
				dataset.addSeries(xySeries);

				JFreeChart chart = ChartFactory.createXYLineChart(
						"mittlere Warteschlangenlänge", "x", "y", dataset,
						PlotOrientation.VERTICAL, true, true, false);

				final XYPlot plot = chart.getXYPlot();
				plot.setBackgroundPaint(Color.lightGray);
				

				plot.setDomainGridlinePaint(Color.white);
				plot.setRangeGridlinePaint(Color.white);
				

//				final XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
//				renderer.setSeriesLinesVisible(0, false);
//				renderer.setSeriesShapesVisible(1, false);
//				plot.setRenderer(renderer);

				chartPanel.setChart(chart);

			}
		});

		JButton btnMittlereAnzahlVon = new JButton(
				"mittlere Anzahl von Kunden im Shop");
		btnMittlereAnzahlVon.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnMittlereAnzahlVon.setForeground(Color.BLUE);
		ChartChoosePanel.add(btnMittlereAnzahlVon);

		JButton btnMittlereAnstehzeit = new JButton("mittlere Anstehzeit");
		btnMittlereAnstehzeit.setForeground(Color.BLUE);
		ChartChoosePanel.add(btnMittlereAnstehzeit);

		JButton btnMittlereVerweildauerIm = new JButton(
				"mittlere Verweildauer im Shop");
		btnMittlereVerweildauerIm.setForeground(Color.BLUE);
		ChartChoosePanel.add(btnMittlereVerweildauerIm);

		JButton btnmittlereServerauslastung = new JButton(
				"mittlere Serverauslastung");
		btnmittlereServerauslastung.setForeground(Color.BLUE);
		ChartChoosePanel.add(btnmittlereServerauslastung);

		// Starting an simulation
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int spVal = (int) spinner.getValue();
				int spVal_1 = (int) spinner_1.getValue();
				double slVal = (double) slider.getValue();

				String message = "";

				if (spVal == 0 || spVal_1 == 0) {

					message = "No allowed arguments\n enter some non zero value";

				} else {
					message = "Simualtion is running";
					sim = new Simulation(slVal, spVal, spVal_1);
					sim.start();
				}

				System.out.println(message);
				txtpnConsole.setText(message);

			}
		});

		btnNewButton_1.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent arg0) {

				String message = "Simulation stopped";
				sim.interrupt();
				txtpnConsole.setText(message);
			}

		});

	}

	private void updateProgressBar() {

		// TODO Alles verbinden zu einem Thread der sich alle Daten holt
		// und dann diese in alle im selben bearbeiten
		// TODO Nur eine Update Methode

		Thread thread = new Thread() {
			public void run() {
				for (int i = 0; i <= 100; i++) {

					try {
						this.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					progressBar.setValue(i);
					progressBar.repaint();

					progressBar_1.setValue(i % 5);
					progressBar_1.repaint();

				}

			}
		};

		thread.start();

	}

	private void drawClient() {

		Thread thread = new Thread() {
			public void run() {
				while (true) {

					canvas.repaint();

				}

			}

		};
		// Start Thread

	}
}
