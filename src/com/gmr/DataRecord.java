package com.gmr;
import java.awt.FileDialog;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.sun.corba.se.impl.ior.WireObjectKeyTemplate;

import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class DataRecord {
	private DataNode x1=new DataNode(0, 0);
	private DataNode y1=new DataNode(1, 0);
	private DataNode z1=new DataNode(2, 0);
	private DataNode x2=new DataNode(3, 0);
	private DataNode y2=new DataNode(4, 0);
	private DataNode z2=new DataNode(5, 0);
	private DataNode x3=new DataNode(6, 0);
	private DataNode y3=new DataNode(7, 0);
	private DataNode z3=new DataNode(8, 0);
	
	private boolean isrecord=false;
	private boolean wb_is_closed=true;
	private File file=null;	
	private gui window=null;
	Workbook wb=null;
	WritableSheet wsheet=null;
	WritableWorkbook wbook=null;
	public void setWindow(gui win)
	{
		window=win;
	}
	public void CreateFile()
	{
	
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");//设置日期格式
			Xls filter=new Xls(".xls");                                                                                               //awt选择框
			 FileDialog fdDialog=new FileDialog(window.getFrame(), "保存为", FileDialog.SAVE);
			 fdDialog.setFilenameFilter(filter);
			 fdDialog.setFile(df.format(new Date())+".xls");
			 fdDialog.setVisible(true);
			if(fdDialog.getFile()==null)
			{
				window.checkBox.setSelected(false);
				isrecord=false;
				return;
			}
			file=new File(fdDialog.getDirectory()+fdDialog.getFile());
			fdDialog.dispose();			  
			/*                                                      SWING  选择框	
			 FileNameExtensionFilter filter = new FileNameExtensionFilter( ".xls", "xls");         
			 JFileChooser chooser=new JFileChooser();
			 chooser.addChoosableFileFilter(filter);
			 chooser.removeChoosableFileFilter(chooser.getAcceptAllFileFilter());
			 chooser.showSaveDialog(window.getFrame());
			 file=chooser.getSelectedFile();
				if(file==null)
				{
					window.checkBox.setSelected(false);
					isrecord=false;
					return;
				}                                                                                                        */
			 
			WritableWorkbook book1=Workbook.createWorkbook(file);
			WritableSheet sheet=book1.createSheet("Data", 0);
			x1.addLabel(sheet, "X1");
			y1.addLabel(sheet, "Y1");
			z1.addLabel(sheet, "Z1");
			x2.addLabel(sheet, "X2");
			y2.addLabel(sheet, "Y2");
			z2.addLabel(sheet, "Z2");
			x3.addLabel(sheet, "X3");
			y3.addLabel(sheet, "Y3");
			z3.addLabel(sheet, "Z3");
			book1.write();		
			book1.close();
		} catch (IOException | WriteException e) {
			e.printStackTrace();
		}
	}
	public void Record(String node,float data)
	{
		if(isrecord)
		{
			if(file==null)
				CreateFile();
			else
			{
				try {
						if(wb_is_closed)
						{
						wb=Workbook.getWorkbook(file);
						wbook=Workbook.createWorkbook(file, wb);
						wsheet=wbook.getSheet(0);
						wb_is_closed=false;
						}
						switch(node)
						{
						case "x1":
							x1.addNumber(wsheet, data);
							break;
						case "y1":
							y1.addNumber(wsheet, data);
							break;
						case "z1":
							z1.addNumber(wsheet, data);
							break;
						case "x2":
							x2.addNumber(wsheet, data);
							break;
						case "y2":
							y2.addNumber(wsheet, data);
							break;
						case "z2":
							z2.addNumber(wsheet, data);
							break;
						case "x3":
							x3.addNumber(wsheet, data);
							break;
						case "y3":
							y3.addNumber(wsheet, data);
							break;
						case "z3":
							z3.addNumber(wsheet, data);
							break;
						}
						if(wsheet.getRows()%100==50)
						{
							wbook.write();
							wbook.close();
							wb_is_closed=true;
						}
						
					} catch (BiffException  | IOException | WriteException e) {
						e.printStackTrace();
					}
				
				
				
			}
				
		}
	}

	
	/*结束数据记录
	 * */
	public void close()
	{
		isrecord=false;
		if(wbook!=null&&!wb_is_closed)
			try {
				wbook.write();
				wbook.close();
		
			} catch (IOException | WriteException e) {
				e.printStackTrace();
			}finally
			{
				
			}
		
			
		
	}
	/*  初始化参数，创建数据文件，开始记录数据
	 * */
	public void start()
	{
		isrecord=true;
		CreateFile();
	}

}
class Xls implements FilenameFilter
{
	String type=null;
	public Xls(String type)
	{
		this.type=type;
	}

	public boolean accept(File dir, String name) {
		return name.endsWith(type);
	}

}
/* @DataNode,记录节点数据的数据集合
 * @para row,数据节点所在excel表格中的行号
 * @para col,数据节点所在excel表格中的列号
 * 
  */

class DataNode
{
	private int row=0;
	private int col=0;
	//构造函数
	public DataNode(int column,int row)
	{
		col=column;
		this.row=row;
	}
	//数据节点增加标签数据
	public void addLabel(WritableSheet wsheet,String str) 
	{
		try {
			wsheet.addCell(new Label(col, row, str));
		} catch (RowsExceededException e) {
			e.printStackTrace();
		} catch (WriteException e) {
			e.printStackTrace();
		}
		row++;
	}
	//数据节点增加数字数据
	public void addNumber(WritableSheet wsheet,float data)
	{
		try {
			wsheet.addCell(new Number(col, row, data));
		} catch (RowsExceededException e) {
			e.printStackTrace();
		} catch (WriteException e) {
			e.printStackTrace();
		}
		row++;
	}
	
}