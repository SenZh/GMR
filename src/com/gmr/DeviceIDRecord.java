package com.gmr;
/* --------------------------------------------------------------------------
@Desc 通过节点的DeviceID判定把数据显示到哪个显示节点上面
@Author ZhangSen
@Date 2015.1.29
---------------------------------------------------------------------------*/
public class DeviceIDRecord {
	final int NODE_NUM=3;   //显示总节点数
	int[] deviceID=new int[NODE_NUM]; //存储设备DeviceID的数组
    int num=0; //目前已连接的设备数
    public DeviceIDRecord()
    {
    	num=0;
    	deviceID=new int[NODE_NUM];
    }
  /* --------------------------------------------------------------------------
  @Desc 输入一个DeviceID，返回一个显示节点标号
  @Para id，DeviceID，int 类型
  @Return int类型，显示节点标号 。0表示超过了最大显示节点数，1-N表示显示节点标号
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
    			return 0;   //节点已满
    	}
    }
    public void Clear()
    {
    	num=0;
    	for(int i=0;i<NODE_NUM;deviceID[i]=0,i++);
    	
    }
}
