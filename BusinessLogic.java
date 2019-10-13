package threelogic;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BusinessLogic {
	String date;
	static String []data1;
    static String []data2;
    static String []data3;
    static String []data4;
	BusinessLogic() throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {

		
		int rowcount=DataBaseAccess.rowcount;
		data1=new String[rowcount];
        data2=new String[rowcount];
        data3=new String[rowcount];
        data4=new String[rowcount];
        for(int i=0;i<rowcount;i++)
        	data1[i]=DataBaseAccess.data[i*4];
        for(int i=0;i<rowcount;i++)
        	data2[i]=DataBaseAccess.data[i*4+1];
        for(int i=0;i<rowcount;i++)
        	data3[i]=DataBaseAccess.data[i*4+2];
        for(int i=0;i<rowcount;i++)
        	data4[i]=DataBaseAccess.data[i*4+3];
	}
	static String getmaxprice(String date)
	{
		int i = Arrays.binarySearch(data1, date);
		if(i<0) return null;
		return data2[i];
	}
	static String getminprice(String date)
	{
		int i = Arrays.binarySearch(data1, date);  
		if(i<0) return null;
		return data3[i];
	}
	static String getaveprice(String date)
	{
		int i = Arrays.binarySearch(data1, date);  
		if(i<0) return null;
		return data4[i];
	}
	public static void main(String args[]) throws Exception {
		DbConnection con=new DbConnection();
		DataBaseAccess d=new DataBaseAccess();
		BusinessLogic b=new BusinessLogic();
		
		int port = 55533;
	    ServerSocket server = new ServerSocket(port);
	    System.out.println("server将一直等待连接的到来");
	    ExecutorService threadPool = Executors.newFixedThreadPool(100);
	    while(true){
	    	Socket socket = server.accept();
	      Runnable runnable=()->{
		try {
	      InputStream inputStream = socket.getInputStream();
	      byte[] bytes = new byte[1024];
	      int len;
	      StringBuilder rec = new StringBuilder();
	      StringBuilder sen = new StringBuilder();
	      while ((len = inputStream.read(bytes)) != -1) {
	        rec.append(new String(bytes, 0, len, "UTF-8"));
	      }
	      String[] get=rec.toString().split(" ");
	      if(get[0].equals("max"))
	      {
	    	  String maxprice=getmaxprice(get[1]);
	    	  sen.append(maxprice);
	      }
	      else if(get[0].equals("min"))
	      {
	    	  String maxprice=getminprice(get[1]);
	    	  sen.append(maxprice);
	      }
	      else if(get[0].equals("ave"))
	      {
	    	  String maxprice=getaveprice(get[1]);
	    	  sen.append(maxprice);
	      }
	      else if(get[0].equals("yearmm"))
	      {
	    	  for(int i=1;i<get.length;i++)
	    	  {
	    		  String maxprice=getmaxprice(get[i]);
	    		  sen.append(maxprice+" ");
	    	  }
	    	  for(int i=1;i<get.length;i++)
	    	  {
	    		  String minprice=getminprice(get[i]);
	    		  sen.append(minprice+" ");
	    	  }
	      }
	      
	      OutputStream outputStream = socket.getOutputStream();
	      outputStream.write(sen.toString().getBytes("UTF-8"));
	      inputStream.close();
	      socket.close();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	      };
	      threadPool.submit(runnable);
	    }
	  }
}
