/*    ��5/7
 *    ʵ�ֲ��빦��
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
		sure=new JButton("--ȷ��--");
		cancle=new JButton("--ȡ��--");
		warn=new JLabel();
		if(name=="��ʦ��Ϣ")
		{
			tVector=MyTableModel.select(name);
			t1=new JLabel("��ʦ���:");
			t2=new JLabel("��ʦ����:");
			t3=new JLabel("���ڿγ�:");
			t4=new JLabel("��ʦְ��:");
			sure.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
					String tNum=e1.getText();
					String tName=e2.getText().trim();
					String tCourse=e3.getText().trim();
					String tTitle=e4.getText().trim();
					if(tNum.length()<=0||tName.length()<=0||tCourse.length()<=0||tTitle.length()<=0)
					{
						warn.setText("------��Ϣ��ȫ------");
						return;
					}
					for(int i=0;i<tVector.size();i++)
					{
						Vector tem=(Vector) tVector.get(i);
						if(tem.get(0).equals(tNum))
						{
							warn.setText("------��ʦ����Ѵ���------");
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
		}else if(name=="������Ϣ")
		{
			tVector=MyTableModel.select(name);
			t1=new JLabel("��    ��    ��    ��   :");
			t2=new JLabel("��    ��    ��    ��   :");
			t3=new JLabel("�� �� �� �� �� ��:");
			t4=new JLabel("�����豸״̬:");
			sure.addActionListener(new ActionListener(){
@Override
				public void actionPerformed(ActionEvent e) {
					String tNum=e1.getText();
					String tName=e2.getText().trim();
					String tCourse=e3.getText().trim();
					String tTitle=e4.getText().trim();
					if(tNum.length()<=0||tName.length()<=0||tCourse.length()<=0||tTitle.length()<=0)
					{
						warn.setText("------��Ϣ��ȫ------");
						return;
					}
					for(int i=0;i<tVector.size();i++)
					{
						Vector tem=(Vector) tVector.get(i);
						if(tem.get(0).equals(tNum))
						{
							warn.setText("------���ұ���Ѵ���------");
							return;
						}
					}
					sql="insert into ClassInfo(���ұ��,�����豸,������������,�����豸״̬) values('"+tNum+"','"+tName+"','"+tCourse+"','"
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
		}else if(name=="�Ͽ���Ϣ")
		{
			JLabel remind=new JLabel("ʱ����д�淶YYYY/MM/DD/HH:MM:SS");
			t1=new JLabel(" ��  ��  ��  �� :");
			t2=new JLabel("�Ͽο�ʼʱ��:");
			t3=new JLabel(" ��  ��  ʱ  �� :");
			t4=new JLabel(" ��  ʦ  ��  �� :");
			//���ұ�Ź̶������������д�������б��ṩ
			final JComboBox rNum;
			final JComboBox tNum;
			Vector tVector=MyTableModel.select("��ʦ��Ϣ");
			Vector rVector=MyTableModel.select("������Ϣ");
			final Vector cVector=MyTableModel.select("�Ͽ���Ϣ");
			final Vector oVector=MyTableModel.select("��������");
			ArrayList tList=new ArrayList();
			for(int i=0;i<tVector.size();i++)
			{
				tList.add( ((Vector)tVector.get(i)).get(0));
			}
			tNum=new JComboBox(tList.toArray());
final ArrayList rList=new ArrayList();//final:�̶���������
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
						warn.setText("��Ϣ��ȫ�淶��YYYY/MM/DD/HH:MM:SS��");
						return;
					}
					if(!(isDigital(start)&&isDigital(end)))
					{
						warn.setText("��д���淶��YYYY/MM/DD/HH:MM:SS��");
						return;
					}
					start=start.substring(0,10)+" "+start.substring(11);
					end=end.substring(0,10)+" "+end.substring(11);
					if(checkCourse(start,end)==1)//--�ж�ʱ���Ⱥ�
					{
						warn.setText("-ʱ��:��û�Ͽξ��¿��ˣ���");
						return;
					}else if(checkCourse(start,end)==0)
					{
						warn.setText("-��ʾ:���¿�ʱ��һ�£���������-");
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
						 * 1. ��ʼ��i�γ̽���֮��1
						 * ����
						 * 2.������i�γ̿�ʼ֮ǰ��-1
						 */
						int startInt=checkCourse(start,tem2);//��ʼ--����
						int endInt=checkCourse(end,tem1);//����--��ʼ
						if(((Vector)cVector.get(i)).get(0).equals(rNum.getSelectedItem().toString().trim())&&!(startInt==1||endInt==-1))
						{
							warn.setText("��ʱ�̸ý������пΣ�������ѡ��");
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
		}else if(name == "��������") {
			JLabel remind=new JLabel("ʱ����д�淶YYYY/MM/DD/HH:MM:SS");
			t1=new JLabel(" ��  ��  ��  �� :");
			t2=new JLabel(" ��  ʼ  ʱ  ��:");
			t3=new JLabel(" ��  ��  ʱ  �� :");
			final JComboBox rNum;
			Vector rVector=MyTableModel.select("������Ϣ");
			final Vector cVector=MyTableModel.select("�Ͽ���Ϣ");
			final Vector oVector=MyTableModel.select("��������");
			
			final ArrayList rList=new ArrayList();//final:�̶���������
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
						warn.setText("��Ϣ��ȫ�淶��YYYY/MM/DD/HH:MM:SS��");
						return;
					}
					if(!(isDigital(start)&&isDigital(end)))
					{
						warn.setText("��д���淶��YYYY/MM/DD/HH:MM:SS��");
						return;
					}
					start=start.substring(0,10)+" "+start.substring(11);
					end=end.substring(0,10)+" "+end.substring(11);
					if(checkCourse(start,end)==1)//--�ж�ʱ���Ⱥ�
					{
						warn.setText("-ʱ��:��û�Ͽξ��¿��ˣ���");
						return;
					}else if(checkCourse(start,end)==0)
					{
						warn.setText("-��ʾ:���¿�ʱ��һ�£���������-");
						return;
					}else if(checkCourse(start,now) == -1)
					{
						warn.setText("ʱ��Ӧ�ڵ�ǰʱ��֮��������ѡ��");
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
						 * 1. ��ʼ��i�γ̽���֮��1
						 * ����
						 * 2.������i�γ̿�ʼ֮ǰ��-1
						 */
						int startInt=checkCourse(start,tem2);//��ʼ--����
						int endInt=checkCourse(end,tem1);//����--��ʼ
						if(((Vector)cVector.get(i)).get(0).equals(rNum.getSelectedItem().toString().trim())&&!(startInt==1||endInt==-1))
						{
							warn.setText("��ʱ�̸ý������пΣ�������ѡ��");
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
						 * 1. ��ʼ��i�γ̽���֮��1
						 * ����
						 * 2.������i�γ̿�ʼ֮ǰ��-1
						 */
						int startInt=checkCourse(start,tem2);//��ʼ--����
						int endInt=checkCourse(end,tem1);//����--��ʼ
						if(((Vector)oVector.get(i)).get(1).equals(rNum.getSelectedItem().toString().trim())&&!(startInt==1||endInt==-1))
						{
							warn.setText("��ʱ�̸ý������������룬������ѡ��");
							return;
						}
					}
					sql="insert into orderinfo(���ұ��,��ʼʱ��,����ʱ��,������) values('"+
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


//�������ݿ⣬��ȡsql�����в������
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
	{//YYYY/MM/DD/HH:MM:SS   �ж�ʱ���Ƿ�淶
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
	private int checkCourse(String start,String end)//�ж����ڴ�С
	{
		DateFormat df=new SimpleDateFormat("yyyy/mm/dd hh:mm:ss");
		try {
			Date one=df.parse(start);
			Date two=df.parse(end);
			if(one.getTime()>two.getTime())
			{
				return 1;//one��two ��
			}else if(one.getTime()<two.getTime())
			{
				return -1;//one��two ǰ
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

