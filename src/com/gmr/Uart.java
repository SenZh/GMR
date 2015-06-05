package com.gmr;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.TooManyListenersException;

import javax.comm.CommPortIdentifier;
import javax.comm.NoSuchPortException;
import javax.comm.PortInUseException;
import javax.comm.SerialPort;
import javax.comm.SerialPortEvent;
import javax.comm.SerialPortEventListener;
import javax.comm.UnsupportedCommOperationException;
import javax.swing.DefaultComboBoxModel;
public class Uart {
	private CommPortIdentifier portId;
	private SerialPort uartPort;;
	private final int rate=115200;
	private final int stopbits=SerialPort.STOPBITS_1;
	private final int databits=8;
	private final int parity=0;
	private gui window=null;
	private InputStream input;
	private BufferedInputStream bufferedInputStream;
	private OutputStream output;
	private DataPro datapro;
	private DataRecord record=null;
	private WaveUI waveui;
	public void ClearRecordDataNumber()
	{
		datapro.ClearDataNumber();
		window.ClearNodeData();
	}
	public void setDataRecord(DataRecord datare)
	{
		record=datare;
	}
	public void setGui(gui win,WaveUI ui){
		window=win;
		datapro=new DataPro();
		datapro.setWindow(window);
		datapro.setCanvas(ui);
		datapro.setDataRecord(record);
	}
	public void listPort()
	{
		DefaultComboBoxModel<String> mymodel=new DefaultComboBoxModel<String>(); 
		@SuppressWarnings("unchecked")
		Enumeration<CommPortIdentifier> en = CommPortIdentifier.getPortIdentifiers();
	    while (en.hasMoreElements()) {
	        portId = (CommPortIdentifier) en.nextElement();
	        if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
	            mymodel.addElement(portId.getName());
	        }
	    }
	    if(mymodel.getSize()>0)
	    window.comboBox.setModel(mymodel);
	}
	public void open()
	{
		
	try {
		portId=CommPortIdentifier.getPortIdentifier(window.getPort());
		uartPort=(SerialPort) portId.open("GMR", 2000);
		uartPort.setSerialPortParams(rate, databits, stopbits, parity);
		
		
	} catch (NoSuchPortException | PortInUseException | UnsupportedCommOperationException e) {
		
		e.printStackTrace();
	} 
	try {
		input=uartPort.getInputStream();
		bufferedInputStream=new BufferedInputStream(input);
		output=uartPort.getOutputStream();
		uartPort.notifyOnDataAvailable(true);
		uartPort.addEventListener(new SerialPortEventListener() {
		public void serialEvent(SerialPortEvent arg0) {
				switch (arg0.getEventType()) {
				     case SerialPortEvent.BI:
			        case SerialPortEvent.OE:
			        case SerialPortEvent.FE:
			        case SerialPortEvent.PE:
			        case SerialPortEvent.CD:
			        case SerialPortEvent.CTS:
			        case SerialPortEvent.DSR:
			        case SerialPortEvent.RI:
			        case SerialPortEvent.OUTPUT_BUFFER_EMPTY:
			         break;
			        case SerialPortEvent.DATA_AVAILABLE:
			        byte[] buffer=new byte[1024];
			        int numbytes=0;				
					try {
						if(input.available()>0)
						{
							numbytes=bufferedInputStream.read(buffer);
						}
				//	System.out.print(new String(buffer,0,numbytes));
				//		System.out.print("\n");
					datapro.packData(buffer, numbytes);

						
					} catch (IOException e) {	
						e.printStackTrace();
					}
					
					break;
				}
			}
		});
	} catch (IOException | TooManyListenersException e) {
		e.printStackTrace();
	}
	

	
	}
	public void close()
	{
		uartPort.removeEventListener();
		uartPort.close();
		uartPort=null;
		portId=null;
	}
}
class DataPro{
	private final byte START=64;
	private final byte END=35;
	private final byte x=120;
	private final byte y=121;
	private final byte z=122;
	private gui win;
	private byte[] data=new byte[1024]; //
	private int length=0;
	private boolean isstart=false;
	private int start=0;
	private int end=0;
	private DataRecord record=null;
	private Log log=new Log();
	private DataNumber reconum=new DataNumber();
	private DeviceIDRecord DevID=new DeviceIDRecord();
	private Graph canvas;
	public void ClearDataNumber()
	{
		reconum.Clear();
		DevID.Clear();
		win.setNode1Name("Node1");
		win.setNode2Name("Node2");
		win.setNode3Name("Node3");
		
	}
	public void setDataRecord(DataRecord da)
	{
		record=da;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");//设置日期格式
		log.setFile(""+df.format(new Date())+".log");
	}
	public void setCanvas(WaveUI ui)
	{
		canvas=ui.getCanvas();
	}
	public void setWindow(gui w)
	{
		win=w;
		reconum.setLabel(win.getDataNumberLabel());
		
	}
	public void packData(byte[] indata,int inlength)
	{
		int cursor=0;
		if(!isstart)
		for(int i=0;i<inlength;i++)  //判断是否报包含帧开始头
		{
			if(indata[i]==START)
			{
				cursor=i;
				isstart=true;
				break;
			}
		}
		if(isstart)           //开始分析打包数据
		{
			
			for(int i=cursor;i<inlength;i++)   //计算数据中有几个帧头
			{
				if(indata[i]==START)
					start++;
			}
			for(int i=cursor;i<inlength;i++)  //复制数据到data[]，并判断帧头帧尾数量是否相同
			{
				data[length+i-cursor]=indata[i];
				if(indata[i]==END)
					end++;
				if(end==start)    //帧头帧尾数量相同，结束循环，丢弃之后的数据，更新数据长度
				{
					length+=i-cursor+1;
					break;
				}
				if(length>300)  //当数据长度大于1000的时候，丢弃所有的数据，重新开始
				{
					start=0;
					end=0;
					log.WriteLog("数据溢出："+new String(data,0,length)); //记录出错日志
					length=0;
					isstart=false;
					return;
				}
				
			}
			if(start>end)  //帧头帧尾数量不同，更新数据长度,等待下一包数据
				length+=inlength-cursor;
			else if(start<end)
				log.WriteLog("数据帧尾大于帧头："+new String(data,0,length)); //记录出错日志
			else if (start==end)  //数量相同，开始对数据进行解析   数据格式：第0位为帧头，第1位为帧长度，第2位为校验位，最后一位为帧尾，其余为数据位
			{
				final int frame=start;
				int frame_cursor=0;
				int framelen;
				byte[] buff;
				start=0;
				end=0;
				length=0;
				isstart=false;
				for(int i=0;i<frame;i++)
				{
					framelen=data[frame_cursor+1];	
					buff=new byte[framelen];
					System.arraycopy(data,frame_cursor, buff, 0, framelen);
					frame_cursor+=framelen;
					if(buff[0]==START&&buff[framelen-1]==END)
					{
					//	System.out.println(new String(buff, 3, framelen-4)+"  frame:"+frame+"  framelen:"+framelen);
						DataAutoPro(buff);
					}
					else if(buff[0]!=START)
					{
						log.WriteLog("数据帧头出错：计算帧长度"+framelen+" 第"+i+"帧 "+" 共"+frame+"帧 "+"数据："+new String(buff));  //记录出错日志
					}
					else if(buff[framelen-1]!=END)
						log.WriteLog("数据帧尾出错：计算帧长度"+framelen+" 第"+i+"帧 "+" 共"+frame+"帧 "+"数据："+new String(buff));  //记录出错日志
					else if(framelen!=buff[1])
					{
						log.WriteLog("数据帧长度出错：计算帧长度"+framelen+" 帧长度"+buff[1]+"  第"+i+"帧 "+" 共"+frame+"帧 "+"数据："+new String(buff));  //记录出错日志
					}
				
				}
			}
				
		}
			
	}
	
	private void DataAutoPro(byte[] indata) //帧数据处理
	{
		if(indata[1]==10)   //数据位为10，为ADC信息 ,第0位为帧头，第1位为帧长度，第2为校验位，第3位为标志位，第4-8位为数据位，第9位为帧尾 Version 1.0
		{
			byte flag=indata[3];
			boolean ex=false;
			byte parity=0;
			String str=new String(indata, 4, 5);
			for(int i=4;i<9;i++)  //判断五位数据是否全部为0-9的字符
			{
				if(indata[i]<48||indata[i]>57)
				{
					ex=true;
					log.WriteLog("数据字符不全为数字："+new String(indata,4,5));
				}
				parity+=indata[i]-48;
			}
			if(parity!=indata[2])
			{
				ex=true;
				log.WriteLog("数据校验出错：校验帧 :"+indata[2]+"  本地校验值："+parity+" 数据："+new String(indata,4,5));
			//	System.out.println("parity is wrong！ parity:"+indata[2]+"  local:" +parity+"  data:"+str);
				
			}
			
			if(!ex)   //如果数据有效
			{
				int intdata=Integer.parseInt(str);
				float data=(float) (intdata*3.3/1024);
				String floatstr=Float.toString(data);
				String outstr;
				if(floatstr.length()>=5)
					outstr=floatstr.substring(0, 5);
				else
					 outstr=floatstr;
				reconum.update();
			switch(flag)
			{
			case x:
//				win.label_x.setText(outstr);
			//	record.RecordX(data);
//				record.Record1("X",data);
				break;
			case y:
//				win.label_y.setText(outstr);
			//	record.RecordY(data);
//				record.Record1("Y",data);
				break;
			case z:
//				win.label_z.setText(outstr);
			//	record.RecordZ(data);
		//		record.Record1("Z",data);
				break;
			}
			}
		}
		else if(indata[1]==15&&(indata[3]==x||indata[3]==y||indata[3]==z)) //ADC 传输协议1.1，加入了deviceID Version 1.1
		{
			byte cmd=indata[3];  //命令位
			byte parity=0;  //校验位缓存
			for(int i=4;i<14;i++)
				parity+=indata[i]-48;
			if(parity==indata[2])
			{
				int int_data=Integer.parseInt(new String(indata, 9, 5));
				float float_data=(float) (int_data*3.3/1024);
				String str_data=Float.toString(float_data);
				if(str_data.length()>5)
					str_data=str_data.substring(0,5);
				switch(DevID.ChoiceNode(Integer.parseInt(new String(indata,4,5))))
				{
				case 1:
					switch(cmd)
					{
					case x:
						win.setNode1X(str_data);
						record.Record("x1", float_data);
						win.setNode1Name("Device ID:0X"+Integer.toHexString(Integer.parseInt(new String(indata,4,5))).toUpperCase());//显示节点device id
						canvas.x1.setData(float_data);
						break;
					case y:
						win.setNode1Y(str_data);
						record.Record("y1", float_data);
						canvas.y1.setData(float_data);
						break;						
					case z:
						win.setNode1Z(str_data);
						record.Record("z1", float_data);
						canvas.z1.setData(float_data);
						reconum.update();   //更新计数
						break;
					}
					break;
				case 2:
					switch(cmd)
					{
					case x:
						win.setNode2X(str_data);
						record.Record("x2", float_data);
						win.setNode2Name("Device ID:0X"+Integer.toHexString(Integer.parseInt(new String(indata,4,5))).toUpperCase());//显示节点device id
						canvas.x2.setData(float_data);
						break;
					case y:
						win.setNode2Y(str_data);
						record.Record("y2", float_data);
						canvas.y2.setData(float_data);
						break;
					case z:
						win.setNode2Z(str_data);
						record.Record("z2", float_data);
						canvas.z2.setData(float_data);
						reconum.update();//更新计数
						break;
					}
					break;
				case 3:
					switch(cmd)
					{
					case x:
						win.setNode3X(str_data);
						record.Record("x3", float_data);
						win.setNode3Name("Device ID:0X"+Integer.toHexString(Integer.parseInt(new String(indata,4,5))).toUpperCase());//显示节点device id
						canvas.x3.setData(float_data);
						break;
					case y:
						win.setNode3Y(str_data);
						record.Record("y3", float_data);
						canvas.y3.setData(float_data);
						break;
					case z:
						win.settNode3Z(str_data);
						record.Record("z3", float_data);
						canvas.z3.setData(float_data);
						reconum.update();//更新计数
						break;
					}
					break;
				case 0:  //连接设备数超过系统最大显示数
					break;
				}
			}
			else if(parity!=indata[2])
				log.WriteLog("数据校验出错"); //记录出错日志
				
			
			
		}
	}

}
