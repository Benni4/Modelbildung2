package edu.hm.sim.inseldorf;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTextPane;
import javax.swing.JToolBar;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class Application {

	private JFrame frmInseldorf;
	private JProgressBar ServerAuslastung;
	private JLabel lblSever;
	private JLabel lblQueue;
	private JPanel QueueSeverVisualisation;
	// Leinwand
	private Canvas canvas;
	// anzahl an gezeichneten leuten in schlange
	private int next = 0;
	private int current = 0;

	private ChartPanel chartPanel;

	private JPanel ParameterPanal;

	// Simulation
	private Simulation sim;
	private boolean running;

	// Parameter Fields for the simulation
	private JSlider slider;
	private JSpinner spinner;
	private JSpinner spinner_1;

	// Console
	private JTextPane txtpnConsole;

	// Thread updateRate fuer GUI
	private int updateRate = 500;

	// Daten sammler aus dem die Plot daten entnommen werden
	private DataCollector dataCollector;

	// XY daten für die Plots
	private XYSeries xySeries;

	// pause und Start button fuer label aktuallisierung
	private JButton startButton;

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
		updateGUI();

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

		QueueSeverVisualisation = new JPanel();
		QueueSeverVisualisation.setBackground(Color.WHITE);
		QueueSeverVisualisation.setBounds(38, 208, 507, 347);
		frmInseldorf.getContentPane().add(QueueSeverVisualisation);
		QueueSeverVisualisation.setLayout(null);

		lblQueue = new JLabel("Queue");
		lblQueue.setForeground(Color.BLUE);
		lblQueue.setBounds(7, 7, 242, 93);
		QueueSeverVisualisation.add(lblQueue);
		lblQueue.setFont(new Font("Arial Black", Font.PLAIN, 20));
		lblQueue.setHorizontalAlignment(SwingConstants.CENTER);

		lblSever = new JLabel("Server");
		lblSever.setForeground(Color.BLUE);
		lblSever.setBounds(253, 7, 247, 93);
		QueueSeverVisualisation.add(lblSever);
		lblSever.setHorizontalAlignment(SwingConstants.CENTER);
		lblSever.setFont(new Font("Arial Black", Font.PLAIN, 20));
		lblSever.setHorizontalTextPosition(SwingConstants.CENTER);

		// Warteschlangen visualisierung TODO

		canvas = new Canvas() {
			private static final long serialVersionUID = 1L;

			@Override
			public void paint(Graphics g) {

				int xmax = 300;
				int ymax = 200;
				int index = 0;

				boolean draw = false;
				int curX = 0;
				int curY = 0;

				for (int y = 0; y < ymax; y += 10) {
					for (int x = 0; x < xmax; x += 10) {
						index++;
						if (index >= current && index <= next) {
							if (y % 20 == 10) {

								curX = x;
								curY = y;
								draw = true;

							} else {

								curX = xmax - x - 10;
								curY = y;
								draw = true;

							}
						} else if (index >= next && index <= current) {
							if (y % 2 == 1) {
								curX = x;
								curY = y;
								draw = true;

							} else {
								curX = xmax - x - 10;
								curY = y;
								draw = true;

							}
						}
						if (draw) {
							g.drawRect(curX, curY, 10, 10);
							g.fillRect(curX, curY, 10, 10);
							draw = false;
						}
					}

				}
				current = next;
			}
		};
		canvas.setBackground(Color.LIGHT_GRAY);
		canvas.setBounds(7, 104, 300, 200);
		QueueSeverVisualisation.add(canvas);

		ServerAuslastung = new JProgressBar();
		ServerAuslastung.setValue(5);
		ServerAuslastung.setStringPainted(true);
		ServerAuslastung.setBounds(340, 116, 100, 100);
		QueueSeverVisualisation.add(ServerAuslastung);
		ServerAuslastung.setBackground(Color.LIGHT_GRAY);
		ServerAuslastung.setForeground(Color.RED);
		ServerAuslastung.setFont(new Font("Tahoma", Font.PLAIN, 20));
		ServerAuslastung.setMaximum(13);
		ServerAuslastung
				.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		ServerAuslastung.setOrientation(SwingConstants.VERTICAL);

		// xy plot Chart fuer visualisierung

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

		startButton = new JButton("Start");
		startButton.setForeground(Color.BLUE);
		startButton.setBackground(Color.WHITE);
		toolBar.add(startButton);
		startButton.setFont(new Font("Arial Black", Font.PLAIN, 20));

		// Starting an simulation
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int spVal = (int) spinner.getValue();
				int spVal_1 = (int) spinner_1.getValue();
				double slVal = (double) (slider.getValue() / 1000);

				String message = "";

				if (!running) {
					if (spVal == 0 || spVal_1 == 0) {

						message = "Eingabe von 0 ist nicht erlaubt\n ändern sie bitte die werte";

					}

					message = "Simualtion läuft";
					sim = new Simulation(slVal, spVal, spVal_1, true);
					sim.start();
					dataCollector = sim.getCollector();
					startButton.setText("Pause");
					running = true;

				} else {
					message = "Simualtion pausiert";
					sim.pause();
					startButton.setText("Start");
					running = false;

				}

				System.out.println(message);
				txtpnConsole.setText(message);

			}
		});

		JButton resetButton = new JButton("Zurücksetzten");
		resetButton.setForeground(Color.BLUE);
		resetButton.setBackground(Color.WHITE);
		toolBar.add(resetButton);
		resetButton.setFont(new Font("Arial Black", Font.PLAIN, 20));

		// Zurücksetzten der Simulation
		resetButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {

				String message = "Simulation stopped";
				startButton.setText("Start");
				running = false;

				sim.reset();
				txtpnConsole.setText(message);
				System.out.println(message);
			}

		});

		JPanel SliderTool = new JPanel();
		SliderTool.setForeground(Color.WHITE);
		SliderTool.setBackground(Color.WHITE);
		toolBar.add(SliderTool);
		SliderTool.setLayout(new GridLayout(2, 1, 10, 4));

		JLabel SliderToolLBL = new JLabel("Simulation Time Regulator");
		SliderToolLBL.setForeground(Color.BLUE);
		SliderToolLBL.setBackground(Color.WHITE);
		SliderToolLBL.setFont(new Font("Arial Black", Font.PLAIN, 16));
		SliderToolLBL.setHorizontalAlignment(SwingConstants.CENTER);
		SliderTool.add(SliderToolLBL);

		slider = new JSlider(SwingConstants.HORIZONTAL, 1, 100000, 1000);
		slider.setSnapToTicks(true);
		slider.setPaintTicks(true);
		slider.setMinorTickSpacing(10000);
		slider.setPaintLabels(true);
		slider.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		slider.setForeground(Color.BLUE);
		slider.setBackground(Color.WHITE);

		Hashtable<Integer, JLabel> labels = new Hashtable<Integer, JLabel>();

		JLabel sliderLBL = new JLabel("1 \"Echtzeit\"");
		JLabel sliderLBL2 = new JLabel("100000 mal schneller");
		sliderLBL.setFont(new Font("Arial Black", Font.PLAIN, 10));
		sliderLBL2.setFont(new Font("Arial Black", Font.PLAIN, 10));
		sliderLBL.setForeground(Color.BLUE);
		sliderLBL2.setForeground(Color.BLUE);

		labels.put(1, sliderLBL);
		labels.put(100000, sliderLBL2);

		slider.setLabelTable(labels);
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
		spinner.setModel(new SpinnerNumberModel(new Integer(1000), null, null,
				new Integer(1)));
		ParameterPanal.add(spinner);

		JTextPane txtpnMittlereBedienzeit = new JTextPane();
		txtpnMittlereBedienzeit.setEditable(false);
		txtpnMittlereBedienzeit.setForeground(Color.BLUE);
		txtpnMittlereBedienzeit.setText("mittlere Bedienzeit");
		txtpnMittlereBedienzeit.setBackground(Color.WHITE);
		ParameterPanal.add(txtpnMittlereBedienzeit);

		spinner_1 = new JSpinner();
		spinner_1.setModel(new SpinnerNumberModel(new Integer(100), null, null,
				new Integer(1)));
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
				"mittlere Warteschlangenlaenge");
		btnMittlereWarteschlangenlnge.setForeground(Color.BLUE);
		ChartChoosePanel.add(btnMittlereWarteschlangenlnge);

		btnMittlereWarteschlangenlnge.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				String seriesName = "Länge der Schlange zur Zeit";

				XYSeries xySeries = new XYSeries("seriesName");

				for (int i = 0; i < 100; i++) {
					xySeries.add(i, i / 2.3);
				}

				XYSeriesCollection dataset = new XYSeriesCollection();
				dataset.addSeries(xySeries);

				xySeries = new XYSeries("konstanten plot");
				xySeries.add(0, 5);
				xySeries.add(200, 5);

				dataset.addSeries(xySeries);

				String middleQueueLength = "mittlere Warteschlangenlaenge";

				drawPlot(dataset, middleQueueLength);

			}
		});

		JButton btnMittlereAnzahlVon = new JButton(
				"mittlere Anzahl von Kunden im Shop");
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

	}

	private void updateGUI() {

		// TODO Alles verbinden zu einem Thread der sich alle Daten holt
		// und dann diese in alle im selben bearbeiten
		// TODO Nur eine Update Methode

		Thread thread = new Thread() {
			public void run() {

				for (int i = 0; i < 10; i++) {

					try {
						this.sleep(updateRate);
						next = i;
						canvas.repaint();

					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}

			}

		};

		thread.start();

	}

	private void drawClients(int next) {

	}

	/**
	 * Erzeugt den plot für das PlotFeld und setzt ihn anschließend fest
	 */
	private void drawPlot(XYSeriesCollection dataset, String plotname) {

		JFreeChart chart = ChartFactory.createXYLineChart(plotname, "x", "y",
				dataset, PlotOrientation.VERTICAL, true, true, false);

		final XYPlot plot = chart.getXYPlot();
		plot.setBackgroundPaint(Color.lightGray);

		plot.setDomainGridlinePaint(Color.white);
		plot.setRangeGridlinePaint(Color.white);

		// final XYLineAndShapeRenderer renderer = new
		// XYLineAndShapeRenderer();
		// renderer.setSeriesLinesVisible(0, false);
		// renderer.setSeriesShapesVisible(1, false);
		// plot.setRenderer(renderer);

		chartPanel.setChart(chart);

	}

}
