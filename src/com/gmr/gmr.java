package com.gmr;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class gmr {
	gui window=null;
	Uart uart=null;
	DataRecord datarecord=null;
	private boolean isopen=false;
	WaveUI waveui;
	public static void main(String[] args) {
		gmr main_thread=new gmr();
		main_thread.initializeGUI();
		main_thread.initializeUART();
		main_thread.initializeACTION();
	}
private void initializeGUI()
	{
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		window=new gui();
		waveui=new WaveUI();		
	}


private void initializeUART()
{
	uart=new Uart();
	datarecord=new DataRecord();
	datarecord.setWindow(window);
	uart.setDataRecord(datarecord);
	uart.setGui(window,waveui);
	uart.listPort();
}
private void initializeACTION()
{
	window.Button_Open.addActionListener(new ActionListener() {
		
	
		public void actionPerformed(ActionEvent e) {
			if(!isopen)
			{
				uart.ClearRecordDataNumber();
				uart.open();
				window.Button_Open.setText("关闭串口");
				window.comboBox.setEnabled(isopen);
				window.checkBox.setEnabled(isopen);
				isopen=true;
			}
			else
			{
				uart.close();
				datarecord.close();
				window.checkBox.setSelected(false);
				window.Button_Open.setText("打开串口");
				window.comboBox.setEnabled(isopen);
				window.checkBox.setEnabled(isopen);
				isopen=false;
			}
		}
	});
	window.checkBox.addActionListener(new ActionListener() {
		
	
		public void actionPerformed(ActionEvent e) {
			if(window.checkBox.isSelected())
			{
				datarecord.start();
				
			}
			else
			{
				datarecord.close();
			}
			
		}
	});
	window.button.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			if(window.button.getText()=="显示波形")
			{
				waveui.setVisible(true);
			
			}
		}
	});

}

}
