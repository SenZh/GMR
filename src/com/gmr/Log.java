package com.gmr;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Log {
		private File file=null;		
		public Log(String path)
		{
			file=new File(path);
		}
		public Log()
		{
			
		}
		public void setFile(String path)
		{
			file=new File(path);
					
		}
		public void WriteLog(String content)
		{
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
			try {
				FileWriter fo=new FileWriter(file,true);
				fo.write("["+df.format(new Date())+"]  "+content+"\r\n");
				fo.close();
			} catch (IOException e) {
			
				e.printStackTrace();
			}
	    }
}
