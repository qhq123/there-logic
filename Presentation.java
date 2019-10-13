package threelogic;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import org.jfree.data.time.Month;


public class Presentation extends JFrame {
	static String host;
    static int port;
    static Socket socket;
    static OutputStream outputStream;
	JButton button0;
	JButton button1;
	JButton button2;
	JButton button3;
	JButton button4;
	JTextArea textArea1;
	JTextArea textArea2;
	static JTextArea textArea3;
	JTextArea textArea4;
	JTextArea textArea5;
	JTextArea textArea6;
	JLabel label1;
	JLabel label2;
	JLabel label3;
	JLabel label4;
	static String chartyear;
	static String date;
	static org.jfree.data.time.Month[] month;
	static String[] maxprice;
	static String[] minprice;
	Presentation() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, UnknownHostException, IOException
	{
		host = "127.0.0.1";
	    port = 55533;
		
		this.setTitle("Server");
		this.setBounds(400,200,400,300);
		button0=new JButton("确定");
		button1=new JButton("maxprice/元");
		button2=new JButton("minprice/元");
		button3=new JButton("aveprice/元");
		button4=new JButton("max/minprice折线图");
		textArea1=new JTextArea();
		textArea2=new JTextArea();
		textArea3=new JTextArea();
		textArea4=new JTextArea();
		textArea5=new JTextArea();
		textArea6=new JTextArea();
		label1=new JLabel("年");
		label2=new JLabel("月");
		label3=new JLabel("日");
		label4=new JLabel("日期范围:1999-11-10 至2016-06-08");
		this.add(button0);
		this.add(button1);
		this.add(button2);
		this.add(button3);
		this.add(button4);
		this.add(textArea1);
		this.add(textArea2);
		this.add(textArea3);
		this.add(textArea4);
		this.add(textArea5);
		this.add(textArea6);
		this.add(label1);
		this.add(label2);
		this.add(label3);
		this.add(label4);
		this.setLayout(new BorderLayout());
		button1.setBounds(40, 110, 105, 30);
		button2.setBounds(150, 110, 100, 30);
		button3.setBounds(255, 110, 100, 30);
		button4.setBounds(40, 190, 155, 30);
		textArea1.setBounds(40, 145, 105, 20);
		textArea2.setBounds(150, 145, 100, 20);
		textArea3.setBounds(255, 145, 100, 20);
		textArea4.setBounds(100, 40, 40, 20);
		textArea5.setBounds(160, 40, 20, 20);
		textArea6.setBounds(200, 40, 20, 20);
		label1.setBounds(140, 40, 20, 20);
		label2.setBounds(180, 40, 20, 20);
		label3.setBounds(220, 40, 20, 20);
		button0.setBounds(250, 40, 60, 20);
		label4.setBounds(80, 60, 200, 20);
		this.setVisible(true);
		button0.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				receivedate();
				textArea1.setText("");
				textArea2.setText("");
				textArea3.setText("");
			}
		});
		button1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				receivedate();
				try {
				socket = new Socket(host, port);
				outputStream = socket.getOutputStream();
				outputStream.write(("max "+date).getBytes("UTF-8"));
			    socket.shutdownOutput();
			    InputStream inputStream = socket.getInputStream();
			    byte[] bytes = new byte[1024];
			    int len;
			    StringBuilder sb = new StringBuilder();
			    while ((len = inputStream.read(bytes)) != -1) {
			      sb.append(new String(bytes, 0, len,"UTF-8"));
			    }
			    textArea1.setText(sb.toString());
			    inputStream.close();
			    outputStream.close();
			    socket.close();
				} catch (IOException e1) {
					// TODO 自动生成的 catch 块
					e1.printStackTrace();
				}
			}
		});
		button2.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				receivedate();
				try {
					socket = new Socket(host, port);
					outputStream = socket.getOutputStream();
					outputStream.write(("min "+date).getBytes("UTF-8"));
				    socket.shutdownOutput();
				    InputStream inputStream = socket.getInputStream();
				    byte[] bytes = new byte[1024];
				    int len;
				    StringBuilder sb = new StringBuilder();
				    while ((len = inputStream.read(bytes)) != -1) {
				      sb.append(new String(bytes, 0, len,"UTF-8"));
				    }
				    textArea2.setText(sb.toString());
				    inputStream.close();
				    outputStream.close();
				    socket.close();
					} catch (IOException e1) {
						// TODO 自动生成的 catch 块
						e1.printStackTrace();
					}
			}
		});
		button3.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				receivedate();
				try {
					socket = new Socket(host, port);
					outputStream = socket.getOutputStream();
					outputStream.write(("ave "+date).getBytes("UTF-8"));
				    socket.shutdownOutput();
				    InputStream inputStream = socket.getInputStream();
				    byte[] bytes = new byte[1024];
				    int len;
				    StringBuilder sb = new StringBuilder();
				    while ((len = inputStream.read(bytes)) != -1) {
				      sb.append(new String(bytes, 0, len,"UTF-8"));
				    }
				    textArea3.setText(sb.toString());
				    inputStream.close();
				    outputStream.close();
				    socket.close();
					} catch (IOException e1) {
						// TODO 自动生成的 catch 块
						e1.printStackTrace();
					}
			}
		});
		button4.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				chartyear=textArea4.getText();
				try {
					geteverymonthmaxmin();
				} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException | IOException e2) {
					// TODO 自动生成的 catch 块
					e2.printStackTrace();
				}
				JFrame frame=new JFrame("Java数据统计图");
		        try {
					frame.add(new TimeSeriesChart().getChartPanel());
				} catch (InstantiationException e1) {
					// TODO 自动生成的 catch 块
					e1.printStackTrace();
				} catch (IllegalAccessException e1) {
					// TODO 自动生成的 catch 块
					e1.printStackTrace();
				} catch (ClassNotFoundException e1) {
					// TODO 自动生成的 catch 块
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO 自动生成的 catch 块
					e1.printStackTrace();
				}
		    	frame.setBounds(50, 50, 1200, 600);
		    	frame.setVisible(true);
			}
		});
	}
	static String makedate(String year,String month,String day)
	{
		return year+"-"+month+"-"+day;
		
	}
	void receivedate()
	{
		String year=textArea4.getText();
		String month=textArea5.getText();
		String day=textArea6.getText();
		if(year.equals(""))
		{
			JOptionPane.showMessageDialog(null, "年份不能为空！", "alert", JOptionPane.ERROR_MESSAGE);
		}
		else if(month.equals(""))
		{
			JOptionPane.showMessageDialog(null, "月份不能为空！", "alert", JOptionPane.ERROR_MESSAGE);
		}
		else if(day.equals(""))
		{
			JOptionPane.showMessageDialog(null, "日期不能为空！", "alert", JOptionPane.ERROR_MESSAGE);
		}
		String date1=year+month+day;
		if(date1.length()==8&&(date1.compareTo("19991110")<0||date1.compareTo("20160608")>0))
		{
			JOptionPane.showMessageDialog(null, "日期不在可查询范围", "alert", JOptionPane.ERROR_MESSAGE);
		}
		date=makedate(year, month, day);
	}
	public static void geteverymonthmaxmin() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, UnknownHostException, IOException
	{
		month=new org.jfree.data.time.Month[12];
		maxprice=new String[12];
		minprice=new String[12];
		String year=chartyear;
		
		socket = new Socket(host, port);
		outputStream = socket.getOutputStream();
		StringBuilder s=new StringBuilder();
		for(int i=0;i<12;i++)
		{
			java.text.DecimalFormat format = new java.text.DecimalFormat("00"); 
			String month1=format.format(i+1);
			month[i]=new Month(Integer.parseInt(month1),Integer.parseInt(year));
            int j=28; 
			String day=format.format(j);
			String date=makedate(year, month1, day);
			s.append(date+" ");
		}
		outputStream.write(("yearmm "+s.toString()).getBytes("UTF-8"));
	    socket.shutdownOutput();
	    InputStream inputStream = socket.getInputStream();
	    byte[] bytes = new byte[1024];
	    int len;
	    StringBuilder sb = new StringBuilder();
	    while ((len = inputStream.read(bytes)) != -1) {
	      sb.append(new String(bytes, 0, len,"UTF-8"));
	    }
	    String []yearmm=sb.toString().split(" ");
	    for(int i=0,m=0;i<yearmm.length/2;i++,m++)
	    {
	    	maxprice[m]=yearmm[i];
	    }
	    for(int i=yearmm.length/2,m=0;i<yearmm.length;i++,m++)
	    {
	    	minprice[m]=yearmm[i];
	    }
	    inputStream.close();
	    outputStream.close();
	    socket.close();
	}
	public static void main(String[] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, UnknownHostException, IOException {
		// TODO 自动生成的方法存根
		Presentation p=new Presentation();
	}
}
