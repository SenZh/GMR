package com.gmr;

import javax.swing.JLabel;

/*
 * 对接收到的数据进行计数
 * */
public class DataNumber {
	
	private JLabel number;
	private int datanumber=0;
	/*
	 * 构造函数
	 * @param number 界面上用于显示数据量的Jlabel
	 * */
	public DataNumber(JLabel number)
	{
		this.number=number;;
	}
	/*
	 * 构造函数
	 * 
	 * */
	public DataNumber()
	{
		
	}
	/*
	 * 设置Jlabel
	 * @param number 界面上用于显示数据量的Jlabel
	 * */
	public void setLabel(JLabel number)
	{
		this.number=number;;
	}
	/* 对数据数量进行更新，并显示到界面上
	 * */
	public void update()
	{
		datanumber++;
		number.setText(String.valueOf(datanumber));
	}
	public void Clear()
	{
		datanumber=0;
		number.setText("0");
	}
	
}
