package threelogic;

import java.awt.Font;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.Month;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;


public class TimeSeriesChart {
	ChartPanel frame1;
	public TimeSeriesChart() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException 
	{		
		XYDataset xydataset = createDataset();		
		JFreeChart jfreechart = ChartFactory.createTimeSeriesChart("日期-价格折线图", "日期", "价格/元",xydataset, true, true, true);		
		XYPlot xyplot = (XYPlot) jfreechart.getPlot();		
		DateAxis dateaxis = (DateAxis) xyplot.getDomainAxis();        
		dateaxis.setDateFormatOverride(new SimpleDateFormat("MMM-yyyy"));  
		frame1=new ChartPanel(jfreechart,true);        
		dateaxis.setLabelFont(new Font("黑体",Font.BOLD,14));       
		dateaxis.setTickLabelFont(new Font("宋体",Font.BOLD,10));   
		ValueAxis rangeAxis=xyplot.getRangeAxis();//获取柱状      
		rangeAxis.setLabelFont(new Font("黑体",Font.BOLD,14));        
		jfreechart.getLegend().setItemFont(new Font("黑体", Font.BOLD, 10));        
		jfreechart.getTitle().setFont(new Font("宋体",Font.BOLD,20));
		} 	 
	private static XYDataset createDataset() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException 
	{  //这个数据集有点多，但都不难理解	       
		TimeSeries timeseries = new TimeSeries("maxprice",org.jfree.data.time.Month.class);	        
		for(int i=0;i<12;i++) 
		{
			if(Presentation.maxprice[i]!=null&&!Presentation.maxprice[i].equals("null"))
			timeseries.add(Presentation.month[i], Double.parseDouble(Presentation.maxprice[i]) );	   
		}
		TimeSeries timeseries1 = new TimeSeries("minprice",org.jfree.data.time.Month.class);	        
		for(int i=0;i<12;i++)
		{
			if(Presentation.minprice[i]!=null&&!Presentation.minprice[i].equals("null"))
			timeseries1.add(Presentation.month[i], Double.parseDouble(Presentation.minprice[i]));	
		}
		TimeSeriesCollection timeseriescollection = new TimeSeriesCollection();	        
		timeseriescollection.addSeries(timeseries);	        
		timeseriescollection.addSeries(timeseries1);	        
		return timeseriescollection;	    
	}	 
	public ChartPanel getChartPanel()
	{	    	
		return frame1;	    		   
	}

}
