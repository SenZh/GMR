package com.gmr;

import javax.swing.JLabel;

/*
 * �Խ��յ������ݽ��м���
 * */
public class DataNumber {
	
	private JLabel number;
	private int datanumber=0;
	/*
	 * ���캯��
	 * @param number ������������ʾ��������Jlabel
	 * */
	public DataNumber(JLabel number)
	{
		this.number=number;;
	}
	/*
	 * ���캯��
	 * 
	 * */
	public DataNumber()
	{
		
	}
	/*
	 * ����Jlabel
	 * @param number ������������ʾ��������Jlabel
	 * */
	public void setLabel(JLabel number)
	{
		this.number=number;;
	}
	/* �������������и��£�����ʾ��������
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
