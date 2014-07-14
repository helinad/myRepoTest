/* ===========================================================
 * AFreeChart : a free chart library for Android(tm) platform.
 *              (based on JFreeChart and JCommon)
 * ===========================================================
 *
 * (C) Copyright 2010, by ICOMSYSTECH Co.,Ltd.
 * (C) Copyright 2000-2008, by Object Refinery Limited and Contributors.
 *
 * Project Info:
 *    AFreeChart: http://code.google.com/p/afreechart/
 *    JFreeChart: http://www.jfree.org/jfreechart/index.html
 *    JCommon   : http://www.jfree.org/jcommon/index.html
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * [Android is a trademark of Google Inc.]
 *
 * -----------------
 * OverlaidXYPlotDemo02View.java
 * -----------------
 * (C) Copyright 2011, by ICOMSYSTECH Co.,Ltd.
 *
 * Original Author:  Yamakami Souichirou (for ICOMSYSTECH Co.,Ltd);
 * Contributor(s):   -;
 *
 * Changes
 * -------
 * 18-Oct-2011 : Added new sample code (SY);
 */

package com.constant_therapy.charts;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.afree.chart.AFreeChart;
import org.afree.chart.axis.DateAxis;
import org.afree.chart.axis.DateTickMarkPosition;
import org.afree.chart.axis.DateTickUnit;
import org.afree.chart.axis.DateTickUnitType;
import org.afree.chart.axis.NumberAxis;
import org.afree.chart.axis.NumberTickUnit;
import org.afree.chart.axis.ValueAxis;
import org.afree.chart.event.ChartProgressEvent;
import org.afree.chart.event.ChartProgressListener;
import org.afree.chart.event.PlotChangeEvent;
import org.afree.chart.plot.DatasetRenderingOrder;
import org.afree.chart.plot.PlotOrientation;
import org.afree.chart.plot.XYPlot;
import org.afree.chart.renderer.xy.StandardXYBarPainter;
import org.afree.chart.renderer.xy.XYBarRenderer;
import org.afree.chart.renderer.xy.XYItemRenderer;
import org.afree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.afree.data.general.DatasetChangeEvent;
import org.afree.data.general.DatasetChangeListener;
import org.afree.data.time.Day;
import org.afree.data.time.TimePeriodAnchor;
import org.afree.data.time.TimeSeries;
import org.afree.data.time.TimeSeriesCollection;
import org.afree.data.xy.IntervalXYDataset;
import org.afree.data.xy.XYDataset;
import org.afree.data.xy.XYSeriesCollection;
import org.afree.graphics.GradientColor;
import org.afree.graphics.SolidColor;
import org.afree.graphics.geom.Font;
import org.afree.ui.RectangleInsets;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;

import com.constant_therapy.constantTherapy.R;
import com.constant_therapy.util.Constants;

/**
 * MultiChartView
 */
public class MultiChartView extends MultiChartViewControl {
	private static ArrayList<Integer> lineChart = new ArrayList<Integer>();
	private static ArrayList<Integer> barChart = new ArrayList<Integer>();
	private static ArrayList<String> Xaxis = new ArrayList<String>();
	private static int sealValue;
	private static Context _context;

	/**
	 * constructor
	 * 
	 * @param context
	 */
	public MultiChartView(Context context, ArrayList<Integer> line,
			ArrayList<Integer> bar, ArrayList<String> xax, int round) {
		super(context);
		lineChart = line;
		barChart = bar;
		Xaxis = xax;
		sealValue = round;
		_context = context;
		final AFreeChart chart = createChart();
		chart.setBackgroundPaintType(new SolidColor(_context.getResources()
				.getColor(R.color.bg_chart)));
		chart.setBorderPaintType(new SolidColor(_context.getResources()
				.getColor(R.color.bg_chart)));
		chart.setPadding(new RectangleInsets(2.0, 5.0, 2.0, 2.0));
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
		Font cFont = new Font("", Typeface.NORMAL, Constants.MAINCHART_LABEL);
		Font cFont1 = new Font("", Typeface.NORMAL,
				Constants.MAINCHART_TICKLABEL);

		final DateAxis domainAxis = new DateAxis("");
		domainAxis.setTickMarkPosition(DateTickMarkPosition.MIDDLE);
		ValueAxis rangeAxis = new NumberAxis("Accuracy");
		domainAxis.setLabelFont(cFont);
		rangeAxis.setLabelFont(cFont);
		domainAxis.setTickLabelFont(cFont1);
		rangeAxis.setTickLabelFont(cFont1);

		// create plot...
		IntervalXYDataset data1 = createDataset1();
		XYItemRenderer renderer1 = new XYBarRenderer(0.20);

		GradientColor gp0 = new GradientColor(_context.getResources().getColor(
				R.color.chart_start_blue), _context.getResources().getColor(
				R.color.chart_end_blue));

		renderer1.setSeriesPaintType(0, gp0);

		XYPlot plot = new XYPlot(data1, domainAxis, rangeAxis, renderer1);

		// create subplot 2...
		XYDataset data2A = createDataset2A();
		plot.setDataset(1, data2A);

		XYBarRenderer rend = (XYBarRenderer) plot.getRenderer();
		rend.setBarPainter(new StandardXYBarPainter());
		rend.setShadowVisible(false);

		XYItemRenderer renderer2A = new XYLineAndShapeRenderer(true, false);
		renderer2A.setSeriesPaintType(0, new SolidColor(Color.BLACK));
		renderer2A.setSeriesOutlineStroke(20, 20f);
		plot.setRenderer(1, renderer2A);
		plot.getRenderer(1).setSeriesStroke(0, 2.0f);
		plot.setRangeGridlineStroke(20f);
		plot.setDomainGridlineStroke(20f);

		plot.setDomainGridlinesVisible(false);

		plot.setBackgroundPaintType(new SolidColor(_context.getResources()
				.getColor(R.color.bg_chart)));
		plot.setRangeGridlinesVisible(false);

		plot.setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);

		plot.setOrientation(PlotOrientation.VERTICAL);
		NumberAxis yAxis = (NumberAxis) plot.getRangeAxis();
		yAxis.setRange(0, 100);
		yAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		yAxis.setTickUnit(new NumberTickUnit(20));
		yAxis.setNumberFormatOverride(new DecimalFormat() {
			public StringBuffer format(double number, StringBuffer toAppendTo,
					FieldPosition pos) {
				return toAppendTo.append((int) number).append("%   ");
			}
		});
		yAxis.setAutoRange(false);
	
		 domainAxis.setMinimumDate(new Date(Xaxis.get(0)));
		 domainAxis.setMaximumDate(new Date(Xaxis.get(Xaxis.size() -1)));
		 minimumDate = new Date(Xaxis.get(0));
		 maximumDate = new Date(Xaxis.get(Xaxis.size() -1));
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

}
