/*    类5/7
 *    实现插入功能
 */   


package database;

import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;
import database.MyTableModel;
public class Insert extends JDialog {
private JLabel t1,t2,t3,t4,warn;
	private JTextField e1,e2,e3,e4;
	private JButton sure,cancle;
	private String sql;
	private Vector tVector=new Vector();
	private Vector rVector=new Vector();
public Insert(Frame owner, String title, boolean modal,String name) {
		super(owner, title, modal);
		e1=new JTextField(15);
		e2=new JTextField(15);
		e3=new JTextField(15);
		e4=new JTextField(15);
		sure=new JButton("--确定--");
		cancle=new JButton("--取消--");
		warn=new JLabel();
		if(name=="教师信息")
		{
			tVector=MyTableModel.select(name);
			t1=new JLabel("教师编号:");
			t2=new JLabel("教师姓名:");
			t3=new JLabel("教授课程:");
			t4=new JLabel("教师职称:");
			sure.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
					String tNum=e1.getText();
					String tName=e2.getText().trim();
					String tCourse=e3.getText().trim();
					String tTitle=e4.getText().trim();
					if(tNum.length()<=0||tName.length()<=0||tCourse.length()<=0||tTitle.length()<=0)
					{
						warn.setText("------信息不全------");
						return;
					}
					for(int i=0;i<tVector.size();i++)
					{
						Vector tem=(Vector) tVector.get(i);
						if(tem.get(0).equals(tNum))
						{
							warn.setText("------教师编号已存在------");
							return;
						}
					}
					sql="insert into TeacherInfo values('"+tNum+"','"+tName+"','"+tCourse+"','"
+tTitle+"')";
					insert2Table(sql);
					Insert.this.dispose();
					return;
				}
			});
			cancle.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					Insert.this.dispose();
				}
			});
			this.add(t1);
			this.add(e1);
			this.add(t2);
			this.add(e2);
			this.add(t3);
			this.add(e3);
			this.add(t4);
			this.add(e4);
			this.add(sure);
			this.add(cancle);
			this.add(warn);
			this.setLayout(new FlowLayout());
			this.setSize(270,300);
			this.setLocation(550, 100);
			this.setResizable(false);
			this.setVisible(true);
			this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		}else if(name=="教室信息")
		{
			tVector=MyTableModel.select(name);
			t1=new JLabel("教    室    编    号   :");
			t2=new JLabel("教    室    设    备   :");
			t3=new JLabel("教 室 容 纳 人 数:");
			t4=new JLabel("教室设备状态:");
			sure.addActionListener(new ActionListener(){
@Override
				public void actionPerformed(ActionEvent e) {
					String tNum=e1.getText();
					String tName=e2.getText().trim();
					String tCourse=e3.getText().trim();
					String tTitle=e4.getText().trim();
					if(tNum.length()<=0||tName.length()<=0||tCourse.length()<=0||tTitle.length()<=0)
					{
						warn.setText("------信息不全------");
						return;
					}
					for(int i=0;i<tVector.size();i++)
					{
						Vector tem=(Vector) tVector.get(i);
						if(tem.get(0).equals(tNum))
						{
							warn.setText("------教室编号已存在------");
							return;
						}
					}
					sql="insert into ClassInfo(教室编号,教室设备,教室容纳人数,教室设备状态) values('"+tNum+"','"+tName+"','"+tCourse+"','"
+tTitle+"')";
					insert2Table(sql);
					Insert.this.dispose();
					return;
				}
			});
			cancle.addActionListener(new ActionListener(){
@Override
				public void actionPerformed(ActionEvent e) {
					Insert.this.dispose();
				}
});
			this.add(t1);
			this.add(e1);
			this.add(t2);
			this.add(e2);
			this.add(t3);
			this.add(e3);
			this.add(t4);
			this.add(e4);
			this.add(sure);
			this.add(cancle);
			this.add(warn);
			this.setLayout(new FlowLayout());
			this.setSize(200,300);
			this.setLocation(550, 100);
			this.setResizable(false);
			this.setVisible(true);
			this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		}else if(name=="上课信息")
		{
			JLabel remind=new JLabel("时间填写规范YYYY/MM/DD/HH:MM:SS");
			t1=new JLabel(" 教  室  编  号 :");
			t2=new JLabel("上课开始时间:");
			t3=new JLabel(" 结  束  时  间 :");
			t4=new JLabel(" 教  师  编  号 :");
			//教室编号固定不可以随便填写，下拉列表提供
			final JComboBox rNum;
			final JComboBox tNum;
			Vector tVector=MyTableModel.select("教师信息");
			Vector rVector=MyTableModel.select("教室信息");
			final Vector cVector=MyTableModel.select("上课信息");
			final Vector oVector=MyTableModel.select("管理申请");
			ArrayList tList=new ArrayList();
			for(int i=0;i<tVector.size();i++)
			{
				tList.add( ((Vector)tVector.get(i)).get(0));
			}
			tNum=new JComboBox(tList.toArray());
final ArrayList rList=new ArrayList();//final:固定基本类型
			for(int i=0;i<rVector.size();i++)
			{
				rList.add( ((Vector)rVector.get(i)).get(0));
			}
			rNum=new JComboBox(rList.toArray());
			sure.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					String start=e2.getText().trim();
					String end=e3.getText().trim();
					if(start.length()<=0||end.length()<=0)
					{
						warn.setText("信息不全规范（YYYY/MM/DD/HH:MM:SS）");
						return;
					}
					if(!(isDigital(start)&&isDigital(end)))
					{
						warn.setText("书写不规范（YYYY/MM/DD/HH:MM:SS）");
						return;
					}
					start=start.substring(0,10)+" "+start.substring(11);
					end=end.substring(0,10)+" "+end.substring(11);
					if(checkCourse(start,end)==1)//--判断时间先后
					{
						warn.setText("-时间:还没上课就下课了！！");
						return;
					}else if(checkCourse(start,end)==0)
					{
						warn.setText("-提示:上下课时间一致！！！！！-");
						return;
					}
					System.out.println(checkCourse(start,end));
					for(int i=0;i<cVector.size();i++)
					{
						String tem1=(String) ((Vector)cVector.get(i)).get(1);
						tem1=tem1.substring(0,10)+" "+tem1.substring(11);
						String tem2=(String) ((Vector)cVector.get(i)).get(2);
						tem2=tem2.substring(0,10)+" "+tem2.substring(11);
						/*
						 * 1. 开始在i课程结束之后，1
						 * 或者
						 * 2.结束在i课程开始之前，-1
						 */
						int startInt=checkCourse(start,tem2);//开始--结束
						int endInt=checkCourse(end,tem1);//结束--开始
						if(((Vector)cVector.get(i)).get(0).equals(rNum.getSelectedItem().toString().trim())&&!(startInt==1||endInt==-1))
						{
							warn.setText("该时刻该教室已有课，请重新选择！");
							return;
						}
					}
					
					sql="insert into ClassRoomInfo values('"+
rNum.getSelectedItem().toString().trim()+"','"+start+"','"+end+"','"
+tNum.getSelectedItem().toString().trim()+"')";
					insert2Table(sql);
					Insert.this.dispose();
					return;
				}
			});
			cancle.addActionListener(new ActionListener(){							
@Override
				public void actionPerformed(ActionEvent e) {
					Insert.this.dispose();
				}
			});
			this.add(t1);
			this.add(rNum);
			this.add(t2);
			this.add(e2);
			this.add(t3);
			this.add(e3);
			this.add(t4);
			this.add(tNum);
			this.add(sure);
			this.add(cancle);
			this.add(remind);
			this.add(warn);
			this.setLayout(new FlowLayout());
			this.setSize(230,300);
			this.setLocation(550, 100);
			this.setResizable(false);
			this.setVisible(true);
			this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		}else if(name == "管理申请") {
			JLabel remind=new JLabel("时间填写规范YYYY/MM/DD/HH:MM:SS");
			t1=new JLabel(" 教  室  编  号 :");
			t2=new JLabel(" 开  始  时  间:");
			t3=new JLabel(" 结  束  时  间 :");
			final JComboBox rNum;
			Vector rVector=MyTableModel.select("教室信息");
			final Vector cVector=MyTableModel.select("上课信息");
			final Vector oVector=MyTableModel.select("管理申请");
			
			final ArrayList rList=new ArrayList();//final:固定基本类型
			for(int i=0;i<rVector.size();i++)
			{
				rList.add( ((Vector)rVector.get(i)).get(0));
			}
			rNum=new JComboBox(rList.toArray());
			sure.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					String start=e2.getText().trim();
					
					String end=e3.getText().trim();

					Date date = new Date();
					SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
					String now=dateFormat.format(date);
					
					
					if(start.length()<=0||end.length()<=0)
					{
						warn.setText("信息不全规范（YYYY/MM/DD/HH:MM:SS）");
						return;
					}
					if(!(isDigital(start)&&isDigital(end)))
					{
						warn.setText("书写不规范（YYYY/MM/DD/HH:MM:SS）");
						return;
					}
					start=start.substring(0,10)+" "+start.substring(11);
					end=end.substring(0,10)+" "+end.substring(11);
					if(checkCourse(start,end)==1)//--判断时间先后
					{
						warn.setText("-时间:还没上课就下课了！！");
						return;
					}else if(checkCourse(start,end)==0)
					{
						warn.setText("-提示:上下课时间一致！！！！！-");
						return;
					}else if(checkCourse(start,now) == -1)
					{
						warn.setText("时间应在当前时间之后，请重新选择！");
						return;
					}
					System.out.println(checkCourse(start,end));
					for(int i=0;i<cVector.size();i++)
					{
						String tem1=(String) ((Vector)cVector.get(i)).get(1);
						tem1=tem1.substring(0,10)+" "+tem1.substring(11);
						String tem2=(String) ((Vector)cVector.get(i)).get(2);
						tem2=tem2.substring(0,10)+" "+tem2.substring(11);
						/*
						 * 1. 开始在i课程结束之后，1
						 * 或者
						 * 2.结束在i课程开始之前，-1
						 */
						int startInt=checkCourse(start,tem2);//开始--结束
						int endInt=checkCourse(end,tem1);//结束--开始
						if(((Vector)cVector.get(i)).get(0).equals(rNum.getSelectedItem().toString().trim())&&!(startInt==1||endInt==-1))
						{
							warn.setText("该时刻该教室已有课，请重新选择！");
							return;
						}
					}
					for(int i=0;i<oVector.size();i++)
					{
						String tem1=(String) ((Vector)oVector.get(i)).get(2);
						tem1=tem1.substring(0,10)+" "+tem1.substring(11);
						
						String tem2=(String) ((Vector)oVector.get(i)).get(3);
						tem2=tem2.substring(0,10)+" "+tem2.substring(11);
						
						/*
						 * 1. 开始在i课程结束之后，1
						 * 或者
						 * 2.结束在i课程开始之前，-1
						 */
						int startInt=checkCourse(start,tem2);//开始--结束
						int endInt=checkCourse(end,tem1);//结束--开始
						if(((Vector)oVector.get(i)).get(1).equals(rNum.getSelectedItem().toString().trim())&&!(startInt==1||endInt==-1))
						{
							warn.setText("该时刻该教室有其他申请，请重新选择！");
							return;
						}
					}
					sql="insert into orderinfo(教室编号,开始时间,结束时间,申请者) values('"+
rNum.getSelectedItem().toString().trim()+"','"+start+"','"+end+"','"
+Login.loginName+"')";
					insert2Table(sql);
					Insert.this.dispose();
					return;
				}
			});
			cancle.addActionListener(new ActionListener(){							
@Override
				public void actionPerformed(ActionEvent e) {
					Insert.this.dispose();
				}
			});
			this.add(t1);
			this.add(rNum);
			this.add(t2);
			this.add(e2);
			Date date = new Date();
			SimpleDateFormat textdateFormat= new SimpleDateFormat("yyyy/MM/dd/HH:mm:ss");
			String textNow = textdateFormat.format(date);
			e2.setText(textNow);
			this.add(t3);
			this.add(e3);
			this.add(sure);
			this.add(cancle);
			this.add(remind);
			this.add(warn);
			this.setLayout(new FlowLayout());
			this.setSize(230,300);
			this.setLocation(550, 100);
			this.setResizable(false);
			this.setVisible(true);
			this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		}
	}


//连接数据库，获取sql语句进行插入操作
	public void insert2Table(String sql)
	{
		Connection connection=null;
		PreparedStatement ps=null;
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			connection=DriverManager.getConnection("jdbc:sqlserver://Localhost:1433;DatabaseName=classroom", "sa", "c5352948");
			ps=connection.prepareStatement(sql);
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				ps.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	private boolean isDigital(String value)
	{//YYYY/MM/DD/HH:MM:SS   判断时间是否规范
		if(value.length()!=19){
			return false;
		}
		String tem=value.substring(0,4);
		for(int i=0;i<tem.length();i++)
		{
			char c=tem.charAt(i);
			if(i>9||i<0)
			{
				return false;
			}
		}
		if(value.charAt(4)!='/')
		{
			return false;
		}
		tem=value.substring(5,7);
		for(int i=0;i<tem.length();i++)
		{
			char c=tem.charAt(i);
			if(i>9||i<0)
			{
				return false;
			}
		}
		if(value.charAt(7)!='/')
		{
			return false;
		}
		tem=value.substring(8,10);
		for(int i=0;i<tem.length();i++)
		{
			char c=tem.charAt(i);
			if(i>9||i<0)
			{
				return false;
			}
		}
		if(value.charAt(10)!='/')
		{
			return false;
		}
		tem=value.substring(11,13);
		for(int i=0;i<tem.length();i++)
		{
			char c=tem.charAt(i);
			if(i>9||i<0)
			{
				return false;
			}
		}
		if(value.charAt(13)!=':')
		{
			return false;
		}
		tem=value.substring(14,16);
		for(int i=0;i<tem.length();i++)
		{
			char c=tem.charAt(i);
			if(i>9||i<0)
			{
				return false;
			}
		}
		if(value.charAt(16)!=':')
		{
			return false;
		}
		tem=value.substring(17);
		for(int i=0;i<tem.length();i++)
		{
			char c=tem.charAt(i);
			if(i>9||i<0)
			{
				return false;
			}
		}
		return true;
	}
	private int checkCourse(String start,String end)//判断日期大小
	{
		DateFormat df=new SimpleDateFormat("yyyy/mm/dd hh:mm:ss");
		try {
			Date one=df.parse(start);
			Date two=df.parse(end);
			if(one.getTime()>two.getTime())
			{
				return 1;//one在two 后
			}else if(one.getTime()<two.getTime())
			{
				return -1;//one在two 前
			}else if(one.getTime()==two.getTime())
			{
				return 0;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 100;
	}
}

