package com.gmr;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.JPanel;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JLabel;
import javax.swing.JCheckBox;
import java.awt.Toolkit;

public class gui {

	private JFrame frame;
	JComboBox<String> comboBox;
	JButton Button_Open;
	JCheckBox checkBox;
private 	JLabel label_datanumber;
private JLabel label_1;
JLabel node2x;
private JLabel label_3;
private JLabel label_4;
JLabel node2y;
JLabel node2z;
private JLabel lblNewLabel;
private JPanel panel_node3;
private JLabel label_7;
JLabel node3x;
private JLabel label_9;
JLabel node3y;
private JLabel label_11;
JLabel node3z;
private JLabel lblNode;
private JPanel panel_node1;
private JLabel label_13;
JLabel node1x;
private JLabel label_15;
JLabel node1y;
private JLabel label_17;
JLabel node1z;
private JLabel lblNode_1;
public JButton button;
	public gui() {
		initialize();
		frame.setVisible(true);
	}
	public JFrame getFrame()
	{
		return frame;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(gui.class.getResource("/image/ico.png")));
		frame.setResizable(false);
		frame.setBounds(100, 100, 624, 322);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		Button_Open = new JButton("\u6253\u5F00\u4E32\u53E3");
		Button_Open.setBounds(17, 118, 76, 36);
		panel.add(Button_Open);
		
		button = new JButton("\u663E\u793A\u6CE2\u5F62");
		button.setBounds(17, 181, 76, 36);
		panel.add(button);
		
		comboBox = new JComboBox<String>();
		comboBox.setBounds(17, 53, 76, 36);
		panel.add(comboBox);
		
		checkBox = new JCheckBox("\u8BB0\u5F55\u6570\u636E");
		checkBox.setBounds(15, 256, 104, 18);
		panel.add(checkBox);
		
		JLabel label = new JLabel("\u6570\u636E\u91CF\uFF1A");
		label.setBounds(17, 226, 55, 18);
		panel.add(label);
		
		label_datanumber = new JLabel("0");
		label_datanumber.setBounds(64, 226, 55, 18);
		panel.add(label_datanumber);
		
		JPanel panel_node2 = new JPanel();
		panel_node2.setBounds(270, 29, 170, 235);
		panel.add(panel_node2);
		panel_node2.setLayout(null);
		
		label_1 = new JLabel("X\u8F74\uFF1A");
		label_1.setBounds(20, 22, 37, 26);
		panel_node2.add(label_1);
		
		node2x = new JLabel("0");
		node2x.setBounds(70, 22, 76, 26);
		panel_node2.add(node2x);
		
		label_3 = new JLabel("Y\u8F74\uFF1A");
		label_3.setBounds(20, 92, 37, 26);
		panel_node2.add(label_3);
		
		node2y = new JLabel("0");
		node2y.setBounds(70, 92, 76, 26);
		panel_node2.add(node2y);
		
		label_4 = new JLabel("Z\u8F74\uFF1A");
		label_4.setBounds(20, 162, 37, 26);
		panel_node2.add(label_4);
		
		node2z = new JLabel("0");
		node2z.setBounds(70, 162, 76, 26);
		panel_node2.add(node2z);
		
		lblNewLabel = new JLabel("Node 2");
		lblNewLabel.setBounds(20, 200, 126, 38);
		panel_node2.add(lblNewLabel);
		
		panel_node3 = new JPanel();
		panel_node3.setLayout(null);
		panel_node3.setBounds(440, 29, 170, 235);
		panel.add(panel_node3);
		
		label_7 = new JLabel("X\u8F74\uFF1A");
		label_7.setBounds(20, 22, 37, 26);
		panel_node3.add(label_7);
		
		node3x = new JLabel("0");
		node3x.setBounds(70, 22, 76, 26);
		panel_node3.add(node3x);
		
		label_9 = new JLabel("Y\u8F74\uFF1A");
		label_9.setBounds(20, 92, 37, 26);
		panel_node3.add(label_9);
		
		node3y = new JLabel("0");
		node3y.setBounds(70, 92, 76, 26);
		panel_node3.add(node3y);
		
		label_11 = new JLabel("Z\u8F74\uFF1A");
		label_11.setBounds(20, 162, 37, 26);
		panel_node3.add(label_11);
		
		node3z = new JLabel("0");
		node3z.setBounds(70, 162, 76, 26);
		panel_node3.add(node3z);
		
		lblNode = new JLabel("Node 3");
		lblNode.setBounds(20, 200, 126, 38);
		panel_node3.add(lblNode);
		
		panel_node1 = new JPanel();
		panel_node1.setLayout(null);
		panel_node1.setBounds(110, 29, 170, 235);
		panel.add(panel_node1);
		
		label_13 = new JLabel("X\u8F74\uFF1A");
		label_13.setBounds(20, 22, 37, 26);
		panel_node1.add(label_13);
		
		node1x = new JLabel("0");
		node1x.setBounds(70, 22, 76, 26);
		panel_node1.add(node1x);
		
		label_15 = new JLabel("Y\u8F74\uFF1A");
		label_15.setBounds(20, 92, 37, 26);
		panel_node1.add(label_15);
		
		node1y = new JLabel("0");
		node1y.setBounds(70, 92, 76, 26);
		panel_node1.add(node1y);
		
		label_17 = new JLabel("Z\u8F74\uFF1A");
		label_17.setBounds(20, 162, 37, 26);
		panel_node1.add(label_17);
		
		node1z = new JLabel("0");
		node1z.setBounds(70, 162, 76, 26);
		panel_node1.add(node1z);
		
		lblNode_1 = new JLabel("Node 1");
		lblNode_1.setBounds(20, 200, 126, 38);
		panel_node1.add(lblNode_1);
	
	}
	public void setNode1X(String x){
		node1x.setText(x);
	}
	public void setNode1Y(String y){
		node1y.setText(y);
	}
	public void setNode1Z(String z){
		node1z.setText(z);
	}
	public void setNode2X(String x)
	{
		node2x.setText(x);
	}
	public void setNode2Y(String y)
	{
		node2y.setText(y);
	}
	public void setNode2Z(String z)
	{
		node2z.setText(z);
	}
	public void setNode3X(String x)
	{
		node3x.setText(x);
	}
	public void setNode3Y(String y)
	{
		node3y.setText(y);
	}
	public void settNode3Z(String z)
	{
		node3z.setText(z);
	}
	public String getPort(){
		return (String)comboBox.getSelectedItem();
	}
	public JLabel getDataNumberLabel()
	{
		return label_datanumber;
	}
	public void setNode1Name(String str)
	{
		lblNode_1.setText(str);
	}
	public void setNode2Name(String str)
	{
		lblNewLabel.setText(str);
	}
	public void setNode3Name(String str)
	{
		lblNode.setText(str);
	}
	public void ClearNodeData()
	{
		node1x.setText("0");
		node1y.setText("0");
		node1z.setText("0");
		node2x.setText("0");
		node2y.setText("0");
		node2z.setText("0");
		node3x.setText("0");
		node3y.setText("0");
		node3z.setText("0");

	}
}
