package backend;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.Minute;

import java.io.File;
import java.io.IOException;
import java.time.ZoneId;
import java.util.List;

public class ChartGeneration {

    public static void generateHealthTrendsCharts(int patientId, String outputDirectory) throws IOException {
        List<Vitals> vitalsList = VitalsDAO.getVitalsByPatientId(patientId);

        generateHeartRateChart(vitalsList, outputDirectory + "/HeartRateTrend_Patient" + patientId + ".png");
        generateBloodPressureChart(vitalsList, outputDirectory + "/BloodPressureTrend_Patient" + patientId + ".png");
        generateOxygenLevelChart(vitalsList, outputDirectory + "/OxygenLevelTrend_Patient" + patientId + ".png");

        System.out.println("Health trend charts generated for patient ID: " + patientId);
    }

    private static void generateHeartRateChart(List<Vitals> vitalsList, String filePath) throws IOException {
        TimeSeries series = new TimeSeries("Heart Rate");

        for (Vitals v : vitalsList) {
            if (v.getDateTime() != null) {
                series.addOrUpdate(
                    new Minute(java.util.Date.from(v.getDateTime().atZone(ZoneId.systemDefault()).toInstant())),
                    v.getHeartRate()
                );
            }
        }

        JFreeChart chart = ChartFactory.createTimeSeriesChart(
            "Heart Rate Over Time", "Time", "BPM",
            new TimeSeriesCollection(series), true, true, false
        );

        ChartUtils.saveChartAsPNG(new File(filePath), chart, 800, 600);
    }

    private static void generateBloodPressureChart(List<Vitals> vitalsList, String filePath) throws IOException {
        TimeSeries systolicSeries = new TimeSeries("Systolic");
        TimeSeries diastolicSeries = new TimeSeries("Diastolic");

        for (Vitals v : vitalsList) {
            if (v.getDateTime() != null) {
                Minute minute = new Minute(java.util.Date.from(v.getDateTime().atZone(ZoneId.systemDefault()).toInstant()));
                systolicSeries.addOrUpdate(minute, v.getSystolicBloodPressure());
                diastolicSeries.addOrUpdate(minute, v.getDiastolicBloodPressure());
            }
        }

        TimeSeriesCollection dataset = new TimeSeriesCollection();
        dataset.addSeries(systolicSeries);
        dataset.addSeries(diastolicSeries);

        JFreeChart chart = ChartFactory.createTimeSeriesChart(
            "Blood Pressure Over Time", "Time", "mmHg",
            dataset, true, true, false
        );

        ChartUtils.saveChartAsPNG(new File(filePath), chart, 800, 600);
    }

    private static void generateOxygenLevelChart(List<Vitals> vitalsList, String filePath) throws IOException {
        TimeSeries series = new TimeSeries("Oxygen Level");

        for (Vitals v : vitalsList) {
            if (v.getDateTime() != null) {
                series.addOrUpdate(
                    new Minute(java.util.Date.from(v.getDateTime().atZone(ZoneId.systemDefault()).toInstant())),
                    v.getOxygenLevel()
                );
            }
        }

        JFreeChart chart = ChartFactory.createTimeSeriesChart(
            "Oxygen Level Over Time", "Time", "%",
            new TimeSeriesCollection(series), true, true, false
        );

        ChartUtils.saveChartAsPNG(new File(filePath), chart, 800, 600);
    }
}
