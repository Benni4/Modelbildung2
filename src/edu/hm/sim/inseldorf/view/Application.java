package edu.hm.sim.inseldorf.view;

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
import org.jfree.data.xy.XYSeriesCollection;

import edu.hm.sim.inseldorf.controller.Simulation;

public class Application {

	public static final int defaultToPlot = 0x00;
	public static final int mdlQueueLenght = 0x01;
	public static final int mdlAmountOfCounters = 0x02;
	public static final int mdlQueueWaitingTime = 0x03;
	public static final int mdlTimeInShop = 0x04;
	public static final int mdlServerUtilization = 0x05;

	private JFrame frmInseldorf;
	private JProgressBar serverAuslastung;
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
	private int updateRate = 1000;

	// XY daten für die Plots
	private PlotDataListener plotDataListener;

	// pause und Start button fuer label aktuallisierung
	private JButton startButton;

	private int toPlot = defaultToPlot;

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

		serverAuslastung = new JProgressBar();
		serverAuslastung.setValue(0);
		serverAuslastung.setStringPainted(true);
		serverAuslastung.setBounds(340, 116, 100, 100);
		QueueSeverVisualisation.add(serverAuslastung);
		serverAuslastung.setBackground(Color.LIGHT_GRAY);
		serverAuslastung.setForeground(Color.RED);
		serverAuslastung.setFont(new Font("Tahoma", Font.PLAIN, 20));
		serverAuslastung.setMaximum(100);
		serverAuslastung
				.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		serverAuslastung.setOrientation(SwingConstants.VERTICAL);

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
				int slVal = (int) (slider.getValue());

				String message = "";

				if (!running) {
					if (spVal == 0 || spVal_1 == 0) {

						message = "Eingabe von 0 ist nicht erlaubt\n ändern sie bitte die Werte";

					}

					message = "Simualtion läuft";
					sim = new Simulation(slVal, spVal, spVal_1);
					plotDataListener = new PlotDataListener();
					sim.addListener(plotDataListener);
					sim.start();
					updateGUI();

					startButton.setText("Pause");
					running = true;

				}
				// else {
				// message = "Simualtion pausiert";
				// sim.pause();
				// startButton.setText("Start");
				// running = false;
				//
				// }

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

				String message = "Simulation gestoppt";

				startButton.setText("Start");
				running = false;

				txtpnConsole.setText(message);
				System.out.println(message);
			}

		});

		JPanel SliderTool = new JPanel();
		SliderTool.setForeground(Color.WHITE);
		SliderTool.setBackground(Color.WHITE);
		toolBar.add(SliderTool);
		SliderTool.setLayout(new GridLayout(2, 1, 10, 4));

		JLabel SliderToolLBL = new JLabel("Simulations Zeit Regulator");
		SliderToolLBL.setForeground(Color.BLUE);
		SliderToolLBL.setBackground(Color.WHITE);
		SliderToolLBL.setFont(new Font("Arial Black", Font.PLAIN, 16));
		SliderToolLBL.setHorizontalAlignment(SwingConstants.CENTER);
		SliderTool.add(SliderToolLBL);

		slider = new JSlider(SwingConstants.HORIZONTAL, 500, 1500, 1100);
		slider.setSnapToTicks(true);
		slider.setMajorTickSpacing(200);
		slider.setPaintTicks(true);
		slider.setMinorTickSpacing(200);
		slider.setPaintLabels(true);
		slider.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		slider.setForeground(Color.BLUE);
		slider.setBackground(Color.WHITE);

		// Hashtable<Integer, JLabel> labels = new Hashtable<Integer, JLabel>();
		//
		// JLabel sliderLBL = new JLabel("1 \"Echtzeit\"");
		// JLabel sliderLBL2 = new JLabel("100000 mal schneller");
		// sliderLBL.setFont(new Font("Arial Black", Font.PLAIN, 10));
		// sliderLBL2.setFont(new Font("Arial Black", Font.PLAIN, 10));
		// sliderLBL.setForeground(Color.BLUE);
		// sliderLBL2.setForeground(Color.BLUE);
		//
		// labels.put(1, sliderLBL);
		// labels.put(100000, sliderLBL2);
		//
		// slider.setLabelTable(labels);
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
				"mittlere Warteschlangenlänge");
		btnMittlereWarteschlangenlnge.setForeground(Color.BLUE);
		ChartChoosePanel.add(btnMittlereWarteschlangenlnge);

		btnMittlereWarteschlangenlnge.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				toPlot = mdlQueueLenght;
				updateXY();

			}
		});

		JButton btnMittlereAnzahlVon = new JButton(
				"mittlere Anzahl von Kunden im Shop");
		btnMittlereAnzahlVon.setForeground(Color.BLUE);
		ChartChoosePanel.add(btnMittlereAnzahlVon);

		btnMittlereAnzahlVon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				toPlot = mdlAmountOfCounters;
				updateXY();

			}
		});

		JButton btnMittlereAnstehzeit = new JButton("mittlere Anstehzeit");
		btnMittlereAnstehzeit.setForeground(Color.BLUE);
		ChartChoosePanel.add(btnMittlereAnstehzeit);

		btnMittlereAnstehzeit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				toPlot = mdlQueueWaitingTime;

				updateXY();

			}
		});

		JButton btnMittlereVerweildauerIm = new JButton(
				"mittlere Verweildauer im Shop");

		btnMittlereVerweildauerIm.setForeground(Color.BLUE);
		ChartChoosePanel.add(btnMittlereVerweildauerIm);

		btnMittlereVerweildauerIm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				toPlot = mdlTimeInShop;

				updateXY();

			}
		});

		JButton btnmittlereServerauslastung = new JButton(
				"mittlere Serverauslastung");
		btnmittlereServerauslastung.setForeground(Color.BLUE);
		ChartChoosePanel.add(btnmittlereServerauslastung);

		btnmittlereServerauslastung.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				toPlot = mdlServerUtilization;

				updateXY();

			}
		});

	}

	private void updateGUI() {

		// TODO Alles verbinden zu einem Thread der sich alle Daten holt
		// und dann diese in alle im selben bearbeiten
		// TODO Nur eine Update Methode

		Thread thread = new Thread() {
			@SuppressWarnings("static-access")
			public void run() {

				while (true) {

					try {
						this.sleep(updateRate);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					serverAuslastung.setValue((int) (plotDataListener
							.getCurrentServerUtilization() * 100));
					next = plotDataListener.getCurrentNbrOfClientsInQueue();
					canvas.repaint();

				}
			}

		};

		thread.start();

	}

	/**
	 * Erzeugt den plot für das PlotFeld und setzt ihn anschließend fest
	 */
	private void drawPlot(XYSeriesCollection dataset, String plotname,
			String yaxis) {

		JFreeChart chart = ChartFactory.createXYLineChart(plotname,
				"Zeit in s", yaxis, dataset, PlotOrientation.VERTICAL, true,
				true, false);

		final XYPlot plot = chart.getXYPlot();
		plot.setBackgroundPaint(Color.lightGray);

		plot.setDomainGridlinePaint(Color.white);
		plot.setRangeGridlinePaint(Color.white);

		chartPanel.setChart(chart);
		chartPanel.repaint();

	}

	private void updateXY() {

		String plottitel;

		switch (toPlot) {
		case mdlQueueLenght:
			plottitel = "mittlere Warteschlangenlänge";

			drawPlot(plotDataListener.getQueueLenghtToTime(), plottitel,
					"in Personen");

			break;
		case mdlAmountOfCounters:

			plottitel = "mittlere Anzahl von Kunden im Shop";
			drawPlot(plotDataListener.getMdlAmountOfCountersToTime(),
					plottitel, "in Personen");

			break;
		case mdlQueueWaitingTime:
			plottitel = "mittlere Anstehzeit";
			drawPlot(plotDataListener.getWaitingTimeToTime(), plottitel,
					"Zeit in s");

			break;
		case mdlTimeInShop:
			plottitel = "mittlere Verweildauer im Shop";
			drawPlot(plotDataListener.getMdlTimeInShopToTime(), plottitel,
					"Zeit in s");

			break;
		case mdlServerUtilization:
			plottitel = "mittlere Serverauslastung";
			drawPlot(plotDataListener.getMdlServerUtilizationToTime(), plottitel,
					"in %");

			break;

		default:
			break;
		}

	}

}
