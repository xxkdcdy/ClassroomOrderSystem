/*    ��4/7
 *    ʵ�ָ��¹���
 */   


package database;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
import javax.swing.JPanel;
import javax.swing.JTextField;
import database.MyTableModel;
public class Update extends JDialog{
private JLabel warn=new JLabel();//��ʾ�ı�
	private JButton sure=new JButton("-ȷ��-");
	private JButton cancle=new JButton("-ȡ��-");
	private String sql;
	final JTextField t51=new JTextField(15);
	final JTextField t52=new JTextField(15);
	final JTextField t53=new JTextField(15);
	final JTextField t54=new JTextField(15);
	public Update(Frame owner, String title, boolean modal,String name,final Vector v) {
		super(owner, title, modal);
		warn.setBounds(0, 0, 300, 50);
t51.setText(v.get(0).toString());
		t51.setEditable(false);
		t52.setText(v.get(1).toString());
		t53.setText(v.get(2).toString());
		t54.setText(v.get(3).toString());
		if(name=="��ʦ��Ϣ")
		{
			JLabel t1=new JLabel("��ʦ���:");
			JLabel t2=new JLabel("��ʦ����:");
			JLabel t3=new JLabel("���ڿγ�:");
			JLabel t4=new JLabel("��ʦְ��:");
			sure.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent e) {
					String tNum=t51.getText();
					String tName=t52.getText().trim();
					String tCourse=t53.getText().trim();
					String tTitle=t54.getText().trim();
					if(tNum.length()<=0||tName.length()<=0||tCourse.length()<=0||tTitle.length()<=0)
					{
						warn.setText("------��Ϣ��ȫ------");
						return;
					}
					sql="UPDATE TeacherInfo SET ��ʦ����='"+tName+"',���ڿγ� ='"+tCourse+"',��ʦְ��='"+tTitle+"' WHERE ��ʦ��� = '"+tNum+"'";
					update2Table(sql);
					Update.this.dispose();
				}
			});
			cancle.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent e) {
					Update.this.dispose();
				}
			});
			this.add(t1);
			this.add(t51);
			this.add(t2);
			this.add(t52);
			this.add(t3);
			this.add(t53);
			this.add(t4);
			this.add(t54);
			this.add(sure);
			this.add(cancle);
			this.add(warn);
			this.setLayout(new FlowLayout());
			this.setSize(250,300);
			this.setLocation(550, 100);
			this.setResizable(false);
			this.setVisible(true);
			this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		}else if(name=="������Ϣ")
		{
			JLabel t1=new JLabel(" ��    ��    ��    ��:");
			JLabel t2=new JLabel(" ��    ��    ��    ��:");
			JLabel t3=new JLabel("�� �� �� �� �� ��:");
			JLabel t4=new JLabel("�����豸״̬:");
			sure.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent e) {
					String tNum=t51.getText();
					String tName=t52.getText().trim();
					String tCourse=t53.getText().trim();
					String tTitle=t54.getText().trim();
					if(tNum.length()<=0||tName.length()<=0||tCourse.length()<=0||tTitle.length()<=0)
					{
						warn.setText("------��Ϣ��ȫ------");
						return;
					}
sql="UPDATE ClassInfo SET �����豸='"+tName+"', ������������='"+tCourse+"',�����豸״̬='"+tTitle+"' WHERE  ���ұ��= '"+tNum+"'";
					update2Table(sql);
					Update.this.dispose();
				}
			});
			cancle.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent e) {
					Update.this.dispose();
				}
			});
			this.add(t1);
			this.add(t51);
			this.add(t2);
			this.add(t52);
			this.add(t3);
			this.add(t53);
			this.add(t4);
			this.add(t54);
			this.add(sure);
			this.add(cancle);
			this.add(warn);
			this.setLayout(new FlowLayout());
			this.setSize(220,300);
			this.setLocation(550, 100);
			this.setResizable(false);
			this.setVisible(true);
			this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		}else if(name=="�Ͽ���Ϣ")
		{
			JLabel remind=new JLabel("ʱ����д��ʽYYYY/MM/DD/HH:MM:SS");
			JLabel t1=new JLabel(" ��  ��  ��  �� :");
			JLabel t2=new JLabel("�Ͽο�ʼʱ��:");
			JLabel t3=new JLabel(" ��  ��  ʱ  �� :");
			JLabel t4=new JLabel(" ��  ʦ  ��  �� :");
			//��ʦ��Ź̶������������д�������б��ṩ
			final JComboBox tNum;
			Vector tVector=MyTableModel.select("��ʦ��Ϣ");
			ArrayList tList=new ArrayList();
			for(int i=0;i<tVector.size();i++)
			{
				tList.add( ((Vector)tVector.get(i)).get(0));
			}
			tNum=new JComboBox(tList.toArray());
			sure.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent e) {
					String num=t51.getText().trim();
					String start=t52.getText().trim();
					String end=t53.getText().trim();
					String tTitle=tNum.getSelectedItem().toString().trim();
					if(num.length()<=0||start.length()<=0||end.length()<=0)
					{
						warn.setText("------��Ϣ��ȫ------");
						return;
					}
					//�ж�ʱ���Ƿ���Ϲ淶
					if(!isDigital(start))
					{
						warn.setText("��ʾ���Ͽο�ʼʱ����д���淶");
						return;
					}
					if(!isDigital(end))		
					{
						warn.setText("��ʾ������ʱ��ʱ����д���淶");
						return;
					}
					start=start.replace('/', ' ');
					end=end.replace('/', ' ');
					System.out.print(tTitle);
					sql="UPDATE ClassRoomInfo SET �Ͽο�ʼʱ��='"+start+"', ����ʱ��='"+end+"',��ʦ���='"+tTitle+"' WHERE  ���ұ��= '"+num+"'";
					update2Table(sql);
					Update.this.dispose();
				}
				
			});
			cancle.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent e) {
					Update.this.dispose();
				}
			});
			this.add(t1);
			this.add(t51);
			this.add(t2);
			this.add(t52);
			this.add(t3);
			this.add(t53);
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
		}
	}
	void update2Order(boolean order,String num)
	{
		
		if(order)
		{
			sql="UPDATE orderinfo SET ������='���ͨ��' WHERE  ������= "+num;
			update2Table(sql);
			Update.this.dispose();
		}
		else
		{
			sql="UPDATE orderinfo SET ������='��˲�ͨ��' WHERE  ������= "+num;
			update2Table(sql);
			Update.this.dispose();
		}
	}
	void update2Option(boolean order,String num)
	{
		
		if(order)
		{
			sql="UPDATE ClassInfo SET �����豸״̬='�豸����' WHERE  ���ұ��='"+num+"'";
			update2Table(sql);
			Update.this.dispose();
		}
		else
		{
			sql="UPDATE ClassInfo SET �����豸״̬='����ά��' WHERE  ���ұ��='"+num+"'";
			update2Table(sql);
			Update.this.dispose();
		}
	}
	
	//�������ݿ⣬�û�õ�sql�����и���
	private void update2Table(String sql)
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
	{//YYYY/MM/DD/HH:MM:SS   �������ʱ���Ƿ�淶
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
}

