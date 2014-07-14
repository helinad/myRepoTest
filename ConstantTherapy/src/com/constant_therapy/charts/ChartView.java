package com.constant_therapy.charts;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import org.afree.chart.AFreeChart;
import org.afree.chart.axis.DateAxis;
import org.afree.chart.axis.DateTickMarkPosition;
import org.afree.chart.axis.DateTickUnit;
import org.afree.chart.axis.DateTickUnitType;
import org.afree.chart.axis.NumberAxis;
import org.afree.chart.axis.NumberTickUnit;
import org.afree.chart.axis.ValueAxis;
import org.afree.chart.plot.DatasetRenderingOrder;
import org.afree.chart.plot.PlotOrientation;
import org.afree.chart.plot.XYPlot;
import org.afree.chart.renderer.xy.StandardXYBarPainter;
import org.afree.chart.renderer.xy.XYBarRenderer;
import org.afree.chart.renderer.xy.XYItemRenderer;
import org.afree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.afree.data.time.Day;
import org.afree.data.time.TimePeriodAnchor;
import org.afree.data.time.TimeSeries;
import org.afree.data.time.TimeSeriesCollection;
import org.afree.data.xy.IntervalXYDataset;
import org.afree.data.xy.XYDataset;
import org.afree.graphics.GradientColor;
import org.afree.graphics.SolidColor;
import org.afree.graphics.geom.Font;
import org.afree.ui.RectangleInsets;

import com.constant_therapy.constantTherapy.R;
import com.constant_therapy.util.Constants;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;

import com.constant_therapy.constantTherapy.R;
import com.constant_therapy.util.Constants;

public class ChartView extends ChartViewControl {
	private static ArrayList<Integer> lineChart = new ArrayList<Integer>();
	private static ArrayList<Integer> barChart = new ArrayList<Integer>();
	private static ArrayList<String> Xaxis = new ArrayList<String>();
	private static int sealValue;
	private static Boolean isAccuracy;
	private static Context _context;

	/**
	 * constructor
	 * 
	 * @param context
	 */
	public ChartView(Context context, ArrayList<Integer> line,
			ArrayList<Integer> bar, ArrayList<String> xax, int round,
			Boolean isAccurcy) {
		super(context);
		lineChart = line;
		barChart = bar;
		Xaxis = xax;
		sealValue = round;
		isAccuracy = isAccurcy;
		_context = context;
		final AFreeChart chart = createChart();
		chart.setBackgroundPaintType(new SolidColor(Color.WHITE));
		chart.setBorderPaintType(new SolidColor(Color.WHITE));
		chart.setPadding(new RectangleInsets(3.0, 3.0, 3.0, 3.0));
		setChart(chart);
	}

	private static int getDate(String time) {
		final DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		final Calendar c = Calendar.getInstance();
		try {
			c.setTime(df.parse(time));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return c.get(Calendar.DAY_OF_MONTH);

	}

	private static int getMonth(String time) {
		final DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		final Calendar c = Calendar.getInstance();
		try {
			c.setTime(df.parse(time));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return c.get(Calendar.MONTH) + 1;

	}

	private static int getYear(String time) {

		final DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		final Calendar c = Calendar.getInstance();
		try {
			c.setTime(df.parse(time));

		} catch (ParseException e) {
			e.printStackTrace();
		}

		return c.get(Calendar.YEAR);

	}

	/**
	 * Creates an overlaid chart.
	 * 
	 * @return The chart.
	 */
	@SuppressWarnings({ "serial", "deprecation" })
	private static AFreeChart createChart() {
		/*
		 * Font cFont = new Font("",Typeface.NORMAL, Constants.SUBCHART_LABEL);
		 * Font cFont1 = new Font("",Typeface.NORMAL,
		 * Constants.SUBCHART_TICKLABEL);
		 */

		Font cFont = new Font("", Typeface.NORMAL, Constants.MAINCHART_LABEL);
		Font cFont1 = new Font("", Typeface.NORMAL,
				Constants.MAINCHART_TICKLABEL);

		DateAxis domainAxis = new DateAxis("");
		domainAxis.setTickMarkPosition(DateTickMarkPosition.MIDDLE);
		ValueAxis rangeAxis;
		if (isAccuracy) {
			rangeAxis = new NumberAxis("Accuracy");
		} else {
			rangeAxis = new NumberAxis("Latency");
		}

		domainAxis.setLabelFont(cFont);
		rangeAxis.setLabelFont(cFont);
		domainAxis.setTickLabelFont(cFont1);
		rangeAxis.setTickLabelFont(cFont1);
		// create plot...
		IntervalXYDataset data1 = createDataset1();
		XYItemRenderer renderer1 = new XYBarRenderer(0.20);

		if (isAccuracy) {
			GradientColor gp0 = new GradientColor(Color.WHITE, _context
					.getResources().getColor(R.color.chart_start_green));
			renderer1.setSeriesPaintType(0, gp0);
		} else {
			GradientColor gp0 = new GradientColor(Color.WHITE, _context
					.getResources().getColor(R.color.chart_start_orange));
			renderer1.setSeriesPaintType(0, gp0);
		}
		XYPlot plot = new XYPlot(data1, domainAxis, rangeAxis, renderer1);
		XYBarRenderer rend = (XYBarRenderer) plot.getRenderer();
		rend.setBarPainter(new StandardXYBarPainter());
		rend.setShadowVisible(false);

		// create subplot 2...
		XYDataset data2A = createDataset2A();
		plot.setDataset(1, data2A);
		XYItemRenderer renderer2A = new XYLineAndShapeRenderer(true, false);
		renderer2A.setSeriesPaintType(0, new SolidColor(Color.BLACK));

		plot.setRenderer(1, renderer2A);
		plot.getRenderer(1).setSeriesStroke(0, 2.0f);

		plot.setDomainGridlinesVisible(false);

		plot.setBackgroundPaintType(new SolidColor(Color.WHITE));
		plot.setRangeGridlinesVisible(false);

		plot.setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);

		plot.setOrientation(PlotOrientation.VERTICAL);
		if (isAccuracy) {
			NumberAxis yAxis = (NumberAxis) plot.getRangeAxis();
			yAxis.setRange(0, 100);
			yAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
			yAxis.setTickUnit(new NumberTickUnit(20));
			yAxis.setNumberFormatOverride(new DecimalFormat() {
				public StringBuffer format(double number,
						StringBuffer toAppendTo, FieldPosition pos) {
					return toAppendTo.append((int) number).append("%   ");
				}
			});
			yAxis.setAutoRange(false);
		} else {
			Collections.sort(barChart);
			if (barChart.get(barChart.size() - 1) == 0) {
				NumberAxis yAxis = (NumberAxis) plot.getRangeAxis();
				yAxis.setRange(0, 5);
				yAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
				yAxis.setTickUnit(new NumberTickUnit(1));
				yAxis.setNumberFormatOverride(new DecimalFormat() {
					public StringBuffer format(double number,
							StringBuffer toAppendTo, FieldPosition pos) {
						return toAppendTo.append((int) number).append(" sec");
					}
				});
				yAxis.setAutoRange(false);
			} else {
				NumberAxis yAxis = (NumberAxis) plot.getRangeAxis();
				yAxis.setRange(0, barChart.get(barChart.size() - 1));
				yAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
				yAxis.setTickUnit(new NumberTickUnit(barChart.get(barChart
						.size() - 1) / 5));
				yAxis.setNumberFormatOverride(new DecimalFormat() {
					public StringBuffer format(double number,
							StringBuffer toAppendTo, FieldPosition pos) {
						return toAppendTo.append((int) number).append(" sec");
					}
				});
				yAxis.setAutoRange(false);
			}

		}

		domainAxis = (DateAxis) plot.getDomainAxis();
		Collections.sort(Xaxis, new compareDate());
		

		domainAxis.setMinimumDate(new Date(Xaxis.get(0)));
		domainAxis.setMaximumDate(new Date(Xaxis.get(Xaxis.size() - 1)));
		minimumDate = new Date(Xaxis.get(0));
		maximumDate = new Date(Xaxis.get(Xaxis.size() - 1));
		gapValue = sealValue;
		domainAxis.setTickUnit(new DateTickUnit(DateTickUnitType.DAY,
				sealValue, new SimpleDateFormat("MM/dd")));

		AFreeChart chart = new AFreeChart("", AFreeChart.DEFAULT_TITLE_FONT,
				plot, false);

		return chart;
	}

	/**
	 * Creates a sample dataset.
	 * 
	 * @return The dataset.
	 */
	private static IntervalXYDataset createDataset1() {

		TimeSeries series1 = new TimeSeries("");

		for (int i = 0; i < barChart.size(); i++) {
			series1.add(new Day(getDate(Xaxis.get(i)), getMonth(Xaxis.get(i)),
					getYear(Xaxis.get(i))), barChart.get(i));

		}

		TimeSeriesCollection result = new TimeSeriesCollection(series1);
		return result;
	}

	/**
	 * Creates a sample dataset.
	 * 
	 * @return The dataset.
	 */
	private static XYDataset createDataset2A() {

		TimeSeries series2 = new TimeSeries("");

		for (int i = 0; i < lineChart.size(); i++) {
			series2.add(new Day(getDate(Xaxis.get(i)), getMonth(Xaxis.get(i)),
					getYear(Xaxis.get(i))), lineChart.get(i));
		}

		TimeSeriesCollection result = new TimeSeriesCollection(series2);
		result.setXPosition(TimePeriodAnchor.MIDDLE);
		return result;
	}

	private static String addDate(String dt) {

		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		Calendar c = Calendar.getInstance();
		try {
			c.setTime(sdf.parse(dt));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		c.add(Calendar.DATE, 2); // number of days to add
		return dt = sdf.format(c.getTime());
	}

	private static class compareDate implements Comparator<String> {

		DateFormat f = new SimpleDateFormat("MM/dd/yyyy");

		@Override
		public int compare(String o1, String o2) {
			try {
				return f.parse(o1).compareTo(f.parse(o2));
			} catch (ParseException e) {
				throw new IllegalArgumentException(e);
			}
		}
	}

	protected static void domainAxisZoom(DateAxis domainAxis) {
		if (Xaxis.size() > 1) {

			if (domainAxis.getUpperBound() < 1.30) {

				domainAxis.setRange(new Date(Xaxis.get(0)),
						new Date(Xaxis.get(Xaxis.size() - 1)));

				// domainAxis.setRange(new Date(Xaxis.get(0)), new
				// Date(Xaxis.get(Xaxis.size() -1)));

			}
		} else {
			if (domainAxis.getUpperBound() < 1.30) {

				domainAxis.setRange(new Date(Xaxis.get(0)), new Date(
						addDate(Xaxis.get(0))));

				// domainAxis.setRange(new Date(Xaxis.get(0)), new
				// Date(Xaxis.get(Xaxis.size() -1)));

			}

		}
	}
}
