package graphs;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.ui.ApplicationFrame;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class Picture extends ApplicationFrame {
    private static final long serialVersionUID = 1L;
    private static int counter = 0;

    public Picture(String title) {
        super(title);
    }

    public void graph(List<Point> points1, List<Point> points2) {
        XYDataset dataset = generateDataset(points1, points2);
        JFreeChart chart = ChartFactory.createXYLineChart(
                "Graph",
                "X",
                "Y",
                dataset,
                PlotOrientation.VERTICAL,
                false,
                false,
                false
        );

        chart.setBackgroundPaint(Color.GREEN);
        chart.getXYPlot().setOutlinePaint(Color.BLACK);

        ChartPanel panel = new ChartPanel(chart);
        panel.setFillZoomRectangle(false);
        panel.setDomainZoomable(true);
        panel.setRangeZoomable(true);
        panel.setBackground(Color.GREEN);
        pack();
        setSize(800, 600);
        setLocationRelativeTo(null);
        setContentPane(panel);

        // Добавление точек пересечения с осью OX
        XYSeries zeroLine = new XYSeries("Zero");
        double position = 0;
        for (Point point : points1) {
            if (point.getY() * position < 0) {
                zeroLine.add(point.getX(), (Double) 0.0);
            }

            position = point.getY();

        }
        XYDataset zeroDataset = new XYSeriesCollection(zeroLine);
        chart.getXYPlot().setDataset(1, zeroDataset);
        chart.getXYPlot().setRenderer(1, new XYLineAndShapeRenderer(false, true));

        setVisible(true);
    }

    private XYDataset generateDataset(List<Point> points1, List<Point> points2) {
        XYSeriesCollection dataset = new XYSeriesCollection();
        if (points1 != null) {
            XYSeries series = new XYSeries("Series " + counter++, false);
            for (Point point : points1) {
                series.add(point.getX(), point.getY());
            }

            dataset.addSeries(series);
        }
        if (points2 != null) {
            XYSeries series = new XYSeries("Series " + counter++, false);
            for (Point point : points2) {
                series.add(point.getX(), point.getY());
            }

            dataset.addSeries(series);
        }

        return dataset;
    }
}
