package backend;


import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class VitalsChartGenerator {

    public void generateVitalsChart(List<VitalSign> vitals, String filePath) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (VitalSign vital : vitals) {
            String time = vital.getTimestamp().toString();
            dataset.addValue(vital.getHeartRate(), "Heart Rate", time);
            dataset.addValue(vital.getTemperature(), "Temperature", time);
            // Add other vitals as needed
        }

        JFreeChart lineChart = ChartFactory.createLineChart(
                "Patient Vitals Over Time",
                "Time",
                "Value",
                dataset
        );

        try {
            ChartUtils.saveChartAsPNG(new File(filePath), lineChart, 800, 600);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}