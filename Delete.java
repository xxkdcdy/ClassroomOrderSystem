/*    类6/7
 *    实现删除功能
 */   


package database;

import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
public class Delete extends JDialog {
	private String sql;
	public Delete(Frame arg0, String arg1, boolean arg2,final Vector v,final String name) {
		super(arg0, arg1, arg2);
		JLabel content=new JLabel("--提示：是 否 删 除 ？--");
		JLabel content2=new JLabel("--非管理员无法删除他人申请--");
		JButton sure=new JButton("确定");
		sure.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				if(name=="教师信息")
				{
					sql="delete ClassRoomInfo where 教师编号='"+v.get(0).toString().trim()+"'";
					delete2Row(sql);
					System.out.println(v.get(0).toString().trim());
					sql="delete TeacherInfo where 教师编号='"+v.get(0).toString().trim()+"'";
					delete2Row(sql);
				}else if(name=="教室信息")
				{
					sql="delete ClassRoomInfo where 教室编号='"+v.get(0).toString().trim()+"'";
					delete2Row(sql);
					sql="delete ClassInfo where 教室编号='"+v.get(0).toString().trim()+"'";
					delete2Row(sql);
				}else if(name=="上课信息")
				{
String start=v.get(1).toString().substring(0, 10)+" "
+v.get(1).toString().substring(11);
					String end=v.get(2).toString().substring(0, 10)+" "+
v.get(2).toString().substring(11);
					sql="delete ClassRoomInfo where 教室编号='"+v.get(0).toString().trim()+"' and 上课开始时间='"+start+"' and 结束时间='"+end+"' ";
					delete2Row(sql);
				}else if(name=="管理申请")
				{
					if((v.get(4).toString().trim().equals(Login.loginName) && Login.power==0) || Login.power==1)
					{
					sql="delete orderinfo where 申请编号='"+v.get(0).toString().trim()+"'";
					delete2Row(sql);
					}
				}
				Delete.this.dispose();
			}
		});
		JButton cancle=new JButton("取消");
		cancle.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				Delete.this.dispose();
			}
		});
		this.setLayout(new FlowLayout());
		this.add(content);
		if(name == "管理申请")
			this.add(content2);
		this.add(sure);
		this.add(cancle);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setLocation(550, 230);
		if(name == "管理申请")
			this.setSize(250,150);
		else
			this.setSize(150,100);
		this.setVisible(true);
	}
	
	//连接数据库，用获取的sql语句进行删除操作
	private void delete2Row(String sql)
	{
		Connection connection = null;
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
			} catch (Exception e) {
				e.printStackTrace();
			}
}}}
