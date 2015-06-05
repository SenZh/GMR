package com.gmr;
/* --------------------------------------------------------------------------
@Desc ͨ���ڵ��DeviceID�ж���������ʾ���ĸ���ʾ�ڵ�����
@Author ZhangSen
@Date 2015.1.29
---------------------------------------------------------------------------*/
public class DeviceIDRecord {
	final int NODE_NUM=3;   //��ʾ�ܽڵ���
	int[] deviceID=new int[NODE_NUM]; //�洢�豸DeviceID������
    int num=0; //Ŀǰ�����ӵ��豸��
    public DeviceIDRecord()
    {
    	num=0;
    	deviceID=new int[NODE_NUM];
    }
  /* --------------------------------------------------------------------------
  @Desc ����һ��DeviceID������һ����ʾ�ڵ���
  @Para id��DeviceID��int ����
  @Return int���ͣ���ʾ�ڵ��� ��0��ʾ�����������ʾ�ڵ�����1-N��ʾ��ʾ�ڵ���
  @Author ZhangSen
  @Date 2015.1.29
  ---------------------------------------------------------------------------*/    
    public int ChoiceNode(int id)
    {
    	if(num==0)
    	{
    		deviceID[0]=id;
    		num++;
    		return 1;
    	}
    	else 
    	{
    		for(int i=0;i<num;i++)
    		{
    			if(deviceID[i]==id)
    			{
    				return(i+1);
    			}
    		}
    		if(num!=NODE_NUM)
    		{
    			deviceID[num]=id;
    			num++;
    			return num;
    		}
    		else
    			return 0;   //�ڵ�����
    	}
    }
    public void Clear()
    {
    	num=0;
    	for(int i=0;i<NODE_NUM;deviceID[i]=0,i++);
    	
    }
}
