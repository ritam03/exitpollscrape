import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ExitPollGUI extends JFrame {
    private JComboBox<String> sourceComboBox;
    private ChartPanel chartPanel;
    private JLabel dataLabel;
    private List<PollData> pollDataList;

    public ExitPollGUI(List<PollData> pollDataList) {
        this.pollDataList = pollDataList;

        setTitle("Exit Poll Results 2024");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        sourceComboBox = new JComboBox<>();
        for (PollData pd : pollDataList) {
            sourceComboBox.addItem(pd.getSource());
        }
        sourceComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateChart();
            }
        });

        add(sourceComboBox, BorderLayout.NORTH);

        chartPanel = new ChartPanel(null);
        chartPanel.setPreferredSize(new Dimension(800, 400));
        add(chartPanel, BorderLayout.CENTER);

        dataLabel = new JLabel("", SwingConstants.CENTER);
        dataLabel.setPreferredSize(new Dimension(800, 200));
        add(dataLabel, BorderLayout.SOUTH);

        // Initialize with the first source
        sourceComboBox.setSelectedIndex(0);
        updateChart();
    }

    private void updateChart() {
        String selectedSource = (String) sourceComboBox.getSelectedItem();
        for (PollData pd : pollDataList) {
            if (pd.getSource().equals(selectedSource)) {
                DefaultPieDataset dataset = createDataset(pd);
                JFreeChart chart = createChart(dataset);
                chartPanel.setChart(chart);
                updateDataLabel(pd);
                break;
            }
        }
    }

    private DefaultPieDataset createDataset(PollData pollData) {
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("NDA", pollData.getNdaSeats());
        dataset.setValue("INDIA", pollData.getIndiaSeats());
        dataset.setValue("OTH", pollData.getOthSeats());

        return dataset;
    }

    private JFreeChart createChart(DefaultPieDataset dataset) {
        JFreeChart chart = ChartFactory.createPieChart(
                "Exit Poll Results 2024 - " + (String) sourceComboBox.getSelectedItem(),
                dataset,
                true, true, false
        );

        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setSectionPaint("NDA", new Color(255, 165, 0)); // Orange
        plot.setSectionPaint("INDIA", new Color(0, 102, 204)); // Blue
        plot.setSectionPaint("OTH", new Color(102, 102, 102)); // Grey
        plot.setSimpleLabels(true);

        return chart;
    }

    private void updateDataLabel(PollData pollData) {
        StringBuilder labelContent = new StringBuilder();
        labelContent.append("<html><center>");
        labelContent.append("Source: ").append(pollData.getSource()).append("<br>");
        labelContent.append("NDA: ").append(pollData.getOriginalNdaSeats());
        if (pollData.getOriginalNdaSeats().contains("-")) {
            labelContent.append(" (avg: ").append(pollData.getNdaSeats()).append(")");
        }
        labelContent.append("<br>");
        labelContent.append("I.N.D.I.A: ").append(pollData.getOriginalIndiaSeats());
        if (pollData.getOriginalIndiaSeats().contains("-")) {
            labelContent.append(" (avg: ").append(pollData.getIndiaSeats()).append(")");
        }
        labelContent.append("<br>");
        labelContent.append("OTH: ").append(pollData.getOriginalOthSeats());
        if (pollData.getOriginalOthSeats().contains("-")) {
            labelContent.append(" (avg: ").append(pollData.getOthSeats()).append(")");
        }
        labelContent.append("</center></html>");

        dataLabel.setText(labelContent.toString());
    }

    public static void main(String[] args) {
        List<PollData> pollDataList = ExitPollScraper.scrapeExitPollData();
        SwingUtilities.invokeLater(() -> {
            ExitPollGUI example = new ExitPollGUI(pollDataList);
            example.setVisible(true);
        });
    }
}
