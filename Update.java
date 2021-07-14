/*    类4/7
 *    实现更新功能
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
private JLabel warn=new JLabel();//提示文本
	private JButton sure=new JButton("-确定-");
	private JButton cancle=new JButton("-取消-");
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
		if(name=="教师信息")
		{
			JLabel t1=new JLabel("教师编号:");
			JLabel t2=new JLabel("教师姓名:");
			JLabel t3=new JLabel("教授课程:");
			JLabel t4=new JLabel("教师职称:");
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
						warn.setText("------信息不全------");
						return;
					}
					sql="UPDATE TeacherInfo SET 教师姓名='"+tName+"',教授课程 ='"+tCourse+"',教师职称='"+tTitle+"' WHERE 教师编号 = '"+tNum+"'";
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
		}else if(name=="教室信息")
		{
			JLabel t1=new JLabel(" 教    室    编    号:");
			JLabel t2=new JLabel(" 教    室    设    备:");
			JLabel t3=new JLabel("教 室 容 纳 人 数:");
			JLabel t4=new JLabel("教室设备状态:");
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
						warn.setText("------信息不全------");
						return;
					}
sql="UPDATE ClassInfo SET 教室设备='"+tName+"', 教室容纳人数='"+tCourse+"',教室设备状态='"+tTitle+"' WHERE  教室编号= '"+tNum+"'";
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
		}else if(name=="上课信息")
		{
			JLabel remind=new JLabel("时间填写形式YYYY/MM/DD/HH:MM:SS");
			JLabel t1=new JLabel(" 教  室  编  号 :");
			JLabel t2=new JLabel("上课开始时间:");
			JLabel t3=new JLabel(" 结  束  时  间 :");
			JLabel t4=new JLabel(" 教  师  编  号 :");
			//教师编号固定不可以随便填写，下拉列表提供
			final JComboBox tNum;
			Vector tVector=MyTableModel.select("教师信息");
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
						warn.setText("------信息不全------");
						return;
					}
					//判断时间是否符合规范
					if(!isDigital(start))
					{
						warn.setText("提示：上课开始时间书写不规范");
						return;
					}
					if(!isDigital(end))		
					{
						warn.setText("提示：结束时间时间书写不规范");
						return;
					}
					start=start.replace('/', ' ');
					end=end.replace('/', ' ');
					System.out.print(tTitle);
					sql="UPDATE ClassRoomInfo SET 上课开始时间='"+start+"', 结束时间='"+end+"',教师编号='"+tTitle+"' WHERE  教室编号= '"+num+"'";
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
			sql="UPDATE orderinfo SET 审核情况='审核通过' WHERE  申请编号= "+num;
			update2Table(sql);
			Update.this.dispose();
		}
		else
		{
			sql="UPDATE orderinfo SET 审核情况='审核不通过' WHERE  申请编号= "+num;
			update2Table(sql);
			Update.this.dispose();
		}
	}
	void update2Option(boolean order,String num)
	{
		
		if(order)
		{
			sql="UPDATE ClassInfo SET 教室设备状态='设备正常' WHERE  教室编号='"+num+"'";
			update2Table(sql);
			Update.this.dispose();
		}
		else
		{
			sql="UPDATE ClassInfo SET 教室设备状态='正在维护' WHERE  教室编号='"+num+"'";
			update2Table(sql);
			Update.this.dispose();
		}
	}
	
	//连接数据库，用获得的sql语句进行更新
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
	{//YYYY/MM/DD/HH:MM:SS   用来检查时间是否规范
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

