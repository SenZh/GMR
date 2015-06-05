package com.gmr;

import javax.swing.JFrame;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Iterator;

import javax.swing.JPanel;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JCheckBox;

import java.awt.GridLayout;

public class WaveUI {

	private JFrame frame;
	private Graph canvas;
	private ReFresh refresh=new ReFresh();
	JButton btnNewButton,btnNewButton_1,button,btnNewButton_2;
	private JPanel panel_3;
	 JCheckBox chckbxX_1;
	 JCheckBox chckbxY_1;
	 JCheckBox chckbxZ_1;
	 JCheckBox chckbxX_2;
	 JCheckBox chckbxY_2;
	 JCheckBox chckbxZ_2;
	 JCheckBox chckbxX_3;
	 JCheckBox chckbxY_3;
	 JCheckBox chckbxZ_3;
	public WaveUI() {
		initialize();
		initializeAction();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize() {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setTitle("                           ");
		frame.setBounds(100, 100, 727, 444);
		canvas=new Graph(this);
		frame.getContentPane().add(canvas, BorderLayout.CENTER);
		
		refresh.setCanvas(canvas);
		new Thread(refresh).start();
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.WEST);
		panel.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_1 = new JPanel();
		panel.add(panel_1, BorderLayout.NORTH);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		btnNewButton = new JButton("+");
		panel_1.add(btnNewButton, BorderLayout.NORTH);
		
		btnNewButton_1 = new JButton("-");
		panel_1.add(btnNewButton_1);
		
		JPanel panel_2 = new JPanel();
		panel.add(panel_2, BorderLayout.SOUTH);
		panel_2.setLayout(new BorderLayout(0, 0));
		
		button = new JButton("+");
		panel_2.add(button, BorderLayout.NORTH);
		
		btnNewButton_2 = new JButton("-");
		panel_2.add(btnNewButton_2, BorderLayout.SOUTH);
		
		panel_3 = new JPanel();
		panel.add(panel_3, BorderLayout.CENTER);
		panel_3.setLayout(new GridLayout(9, 1, 0, 0));
		
		chckbxX_1 = new JCheckBox("x1");
		chckbxX_1.setSelected(true);
		panel_3.add(chckbxX_1);
		
		chckbxY_1 = new JCheckBox("y1");
		chckbxY_1.setSelected(true);
		panel_3.add(chckbxY_1);
		
		chckbxZ_1 = new JCheckBox("z1");
		chckbxZ_1.setSelected(true);
		panel_3.add(chckbxZ_1);
		
		chckbxX_2 = new JCheckBox("x2");
		panel_3.add(chckbxX_2);
		
		chckbxY_2 = new JCheckBox("y2");
		panel_3.add(chckbxY_2);
		
		chckbxZ_2 = new JCheckBox("z2");
		panel_3.add(chckbxZ_2);
		
		chckbxX_3 = new JCheckBox("x3");
		panel_3.add(chckbxX_3);
		
		chckbxY_3 = new JCheckBox("y3");
		panel_3.add(chckbxY_3);
		
		chckbxZ_3 = new JCheckBox("z3");
		panel_3.add(chckbxZ_3);
		
	

	}
	public void setVisible(boolean flag)
	{
		frame.setVisible(flag);
		
	}
	public boolean isShowing()
	{
		return frame.isShowing();
	}
	public Graph getCanvas()
	{
		return canvas;
	}
	public void initializeAction()
	{
		btnNewButton.addActionListener(new ActionListener() {
			

			public void actionPerformed(ActionEvent e) {
				
				   canvas.y_max+=0.1;			
				if(canvas.y_max>=3.3)
					btnNewButton.setEnabled(false);
				if(canvas.y_max>canvas.y_min+0.2)
					btnNewButton_1.setEnabled(true);
					canvas.repaint();
				
			}
		});
		btnNewButton_1.addActionListener(new ActionListener() {
			
		
			public void actionPerformed(ActionEvent e) {
				
				 canvas.y_max-=0.1;			
				 if(canvas.y_max<3.3)
					 btnNewButton.setEnabled(true);
				 if(canvas.y_max<=canvas.y_min+0.2)
					 btnNewButton_1.setEnabled(false);
				canvas.repaint();
			}
		});
		button.addActionListener(new ActionListener() {
			
			
			public void actionPerformed(ActionEvent e) {			
				canvas.y_min+=0.1;
				if(canvas.y_min>=0.1)
					btnNewButton_2.setEnabled(true);
				if(canvas.y_min>=canvas.y_max-0.2)
					button.setEnabled(false);
				canvas.repaint();
			}
		});
		btnNewButton_2.addActionListener(new ActionListener() {
			
			
			public void actionPerformed(ActionEvent e) {
				canvas.y_min-=0.1;
				if(canvas.y_min<=0)
					btnNewButton_2.setEnabled(false);
				if(canvas.y_min<canvas.y_max-0.2)
					button.setEnabled(true);
				
			}
		});
	}



}
class Graph extends Canvas
{	
	int h,w,ox,oy,a;
	float xstep,ystep;
	int x_muti=300; //x锟斤拷锟斤拷锟斤拷锟斤拷锟�
	int y_muti=200;//y锟斤拷锟斤拷锟斤拷锟斤拷锟�
	int x0,y0;
	float y_min=(float) 1.1; //y锟斤拷锟斤拷小值
	float y_max=(float) 2;//y锟斤拷锟斤拷锟街�
	int y_grid_num=50;
	private java.awt.Image bufferImage;
	private WaveUI waveui;
	public Wave x1,y1,z1;
	public Wave x2,y2,z2;
	public Wave x3,y3,z3;
	public Font ft=new Font(Font.SANS_SERIF, Font.BOLD, 12);
	public Graph(WaveUI ui)
	{
		super();
		x1=new Wave(x_muti,"X1");
		y1=new Wave(x_muti,"Y1");
		z1=new Wave(x_muti,"Z1");
		x2=new Wave(x_muti,"X2");
		y2=new Wave(x_muti,"Y2");
		z2=new Wave(x_muti,"Z2");
		x3=new Wave(x_muti,"X3");
		y3=new Wave(x_muti,"Y3");
		z3=new Wave(x_muti,"Z3");
		x1.setColor(Color.black);
		y1.setColor(Color.gray);
		z1.setColor(Color.RED);
		x2.setColor(Color.ORANGE);
		y2.setColor(Color.GREEN);
		z2.setColor(Color.PINK);
		x3.setColor(Color.WHITE);
		y3.setColor(Color.MAGENTA);
		z3.setColor(Color.YELLOW);
		waveui=ui;
	}
	public void update(Graphics g)
	{
		bufferImage=createImage(getWidth(), getHeight());
		Graphics gBuffer=bufferImage.getGraphics();
		if(gBuffer!=null)
			paint(gBuffer);
		else
			paint(g);
		gBuffer.dispose();
		g.drawImage(bufferImage, 0, 0,null);
	}
	public void paint(Graphics g)
	{
		paintBackGround(g);	
		if(waveui.chckbxX_1.isSelected())
		x1.paint(g);	
		if(waveui.chckbxY_1.isSelected())
		y1.paint(g);
		if(waveui.chckbxZ_1.isSelected())
		z1.paint(g);
		if(waveui.chckbxX_2.isSelected())
		x2.paint(g);
		if(waveui.chckbxY_2.isSelected())
		y2.paint(g);
		if(waveui.chckbxZ_2.isSelected())
		z2.paint(g);
		if(waveui.chckbxX_3.isSelected())
		x3.paint(g);
		if(waveui.chckbxY_3.isSelected())
		y3.paint(g);
		if(waveui.chckbxZ_3.isSelected())
		z3.paint(g);
		
	}

	private void paintBackGround(Graphics g) //锟斤拷锟狡憋拷锟斤拷锟斤拷锟较�
	{
		h=getHeight();
		w=getWidth();
		 a=h/10; //锟斤拷锟斤拷原锟斤拷偏锟斤拷锟斤拷
		ox=a;       //锟斤拷锟斤拷锟斤拷锟�             原锟斤拷
		oy=h-a;
		ystep=(float) (oy/((y_max-y_min)*y_muti));
		xstep=(w-ox)/x_muti;
		
		g.drawLine(ox, oy, w, oy);//锟斤拷x锟斤拷
		g.drawLine(ox, oy-1, w, oy-1);	
		g.drawLine(ox, oy, ox, 0);//锟斤拷y锟斤拷
		g.drawLine(ox+1, oy, ox+1, 0);
		
		for(int i=0, q=oy/y_grid_num;i<y_grid_num+1;i++)   //锟斤拷Y锟斤拷锟斤拷锟�
		{
			if(i%5==0)
			{
				g.drawLine(ox, oy-i*q, ox+13,oy-i*q);
				if(i!=0)
				g.drawString(Float.toString((float)(y_max-y_min)/y_grid_num*i+y_min).substring(0, 3),ox-20,oy-i*q+5);
			}
			else
				g.drawLine(ox, oy-i*q, ox+8,oy-i*q);
		}
		for(int i=0,q=(w-ox)/100;i<101;i++) //锟斤拷X锟斤拷锟斤拷锟�
		{
			if(i%5==0)
			{	
				g.drawLine(ox+q*i, oy, ox+q*i, oy-13);
				g.drawString(Integer.toString(i), ox+q*i-5, oy+15);
			}
			else
			g.drawLine(ox+q*i, oy, ox+q*i, oy-8);
		}
	}
	class Wave
	{
		private GraphData graphdata;
		private Color penColor=Color.BLACK;
		private float data;
		private String str;
		public Wave(int number,String str)
		{
			graphdata=new GraphData(number);
			this.str=str;
		}
		public void paint(Graphics g)
		{
			int i=0;
			int x0=ox;
			int y0=oy;
			g.setColor(penColor);
			g.setFont(ft);
			Iterator itr=graphdata.iterator();
			while(itr.hasNext())
			{
				data=y_muti*((float)(itr.next())-y_min);
				if(x0==ox){
					y0=(int) (oy-data*ystep);
					g.drawString(str, ox-40, y0+3);
				}
				g.drawLine(x0, y0, (int) (ox+i*xstep),(int) (oy-data*ystep) );
				x0=(int) (ox+i*xstep);
				y0=(int) (oy-data*ystep);
				i++;
					
			}
		}
		public void setColor(Color col)
		{
			penColor=col;
		}
		public void setData(float data)
		{
			graphdata.setData(data);
		}
	}

}
class GraphData implements Iterable
{
	float data[];
	int cursor_write=0;
	int number=0;
	boolean isfull=false;
	public GraphData()
	{
	}
	public GraphData(int number)
	{
		this.number=number;
		data=new float[number];
	}
	public void setNumber(int number)
	{
		this.number=number;
		data=new float[number];
	}
	public void setData(float data)
	{
		if(number!=0)
		{
		this.data[cursor_write]=data;
		if(cursor_write==number-1)
		{
			cursor_write=0;
			isfull=true;
		}
		else
			cursor_write++;
		}
		else
			System.out.println("锟斤拷锟斤拷锟斤拷锟斤拷莩锟斤拷锟�");
	}
	
	public int getDataLength()
	{
		return data.length;
	}
	@Override
	public Iterator iterator() {
		
		return new itr();
	}
	private class itr implements Iterator
	{

		int cursor;
		boolean isend=false;
		public itr()
		{
			if(isfull)
			{
				cursor=cursor_write;
			}
			else
			{
				cursor=0;
			}
		}
		public boolean hasNext() {
			if(isfull)
			{
				if(cursor<cursor_write||(cursor_write==0&&cursor==number-1))
					isend=true;
				if(isend)
					return cursor!=cursor_write;
				else
					return true;			
			}
			else
			{
				return cursor!=cursor_write;
			}
				
		
		}

		@Override
		public Object next() {
			cursor++;
			if(cursor==number)
			{
				cursor=0;
				return data[number-1];
			}
			else
			{
				return data[cursor-1];
			}
			

	
			
		}
		@Override
		public void remove() {
			// TODO Auto-generated method stub
			
		}


	
	}

	
}
class ReFresh implements Runnable
{
	private Graph canvas;
	public void setCanvas(Graph canvas)
	{
		this.canvas=canvas;
	}
	public void run() {
		while(true)
		{
		//	System.out.println("123");
		canvas.repaint();    
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	}
	}
