/*    类3/7
 *    实现每个表向数据库查询，获取结果，返回Vector到主界面的功能
 */   


package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;
import javax.swing.table.AbstractTableModel;
public class MyTableModel extends AbstractTableModel{
	public static Vector 	rowData;
	public static Vector 		columnNames;
	public MyTableModel(String value) {
		select(value);
	}
public MyTableModel(Vector rowData,Vector columnNames) {
		this.rowData=rowData;
		this.columnNames=columnNames;
	}


//主要实现代码，用来返回数据库的查询信息，并对这些信息处理放进Vector，给其他的类实现功能
	public static Vector select(String value)
	{
		rowData=new Vector();
		columnNames=new Vector();
		Connection connection=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		PreparedStatement ps2=null;
		ResultSet rs2=null;
		String sql = null;
		if(value=="教师信息")
		{
			columnNames.add("教师编号");
			columnNames.add("教师姓名");
			columnNames.add("教授课程");
			columnNames.add("教师职称");
			sql="select * from TeacherInfo";
		}else if(value=="教室信息")
		{
			columnNames.add("教室编号");
			columnNames.add("教室设备");
			columnNames.add("教室容纳人数");
			columnNames.add("教室设备状态");
			columnNames.add("使用状态");
			sql="select * from ClassInfo";
		}else if(value=="上课信息")
		{
			columnNames.add("教室编号");
			columnNames.add("上课开始时间");
			columnNames.add("结束时间");
			columnNames.add("教师信息");
			columnNames.add("课程信息");
			sql="select 教室编号, convert(varchar(20),上课开始时间,111),convert(varchar(20),上课开始时间,108),convert(varchar(20),结束时间,111) ,convert(varchar(20),结束时间,108), 教师姓名,教授课程 from ClassRoomInfo,TeacherInfo where ClassRoomInfo.教师编号=TeacherInfo.教师编号 order by 上课开始时间";
		}
		else if(value=="教室具体使用情况")
		{
			columnNames.add("教室编号");
			columnNames.add("使用事由");
			columnNames.add("使用者编号");
			columnNames.add("开始时间"); 
			columnNames.add("结束时间");
			sql="select ClassInfo.教室编号,TeacherInfo.教师编号, convert(varchar(20),ClassRoomInfo.上课开始时间,111),convert(varchar(20),ClassRoomInfo.上课开始时间,108),convert(varchar(20),ClassRoomInfo.结束时间,111) ,convert(varchar(20),ClassRoomInfo.结束时间,108) from ClassRoomInfo,TeacherInfo,ClassInfo  where ClassInfo.教室编号= ClassRoomInfo.教室编号   and  TeacherInfo.教师编号=ClassRoomInfo.教师编号 order by ClassRoomInfo.上课开始时间";
		}
		else if(value=="管理申请")
		{
			columnNames.add("申请编号");
			columnNames.add("教室编号");
			columnNames.add("开始时间");
			columnNames.add("结束时间");
			columnNames.add("申请者");
			columnNames.add("审核情况");
			sql="select 申请编号,教室编号,convert(varchar(20),开始时间,111),convert(varchar(20),开始时间,108),convert(varchar(20),结束时间,111),convert(varchar(20),结束时间,108),申请者,审核情况 from orderinfo";
		}
		try {
			//加载驱动
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			//得到连接
	        connection=DriverManager.getConnection("jdbc:sqlserver://Localhost:1433;DatabaseName=classroom", "sa", "c5352948");
			//创建访问数据库接口
			ps=connection.prepareStatement(sql);
			rs=ps.executeQuery();
			if(value=="教师信息")
			{
				while(rs.next())
				{
					Vector tem=new Vector();
					tem.add(rs.getString(1));
					tem.add(rs.getString(2));
					tem.add(rs.getString(3));
					tem.add(rs.getString(4));
					rowData.add(tem);
				}
			}else if(value=="教室信息")
			{
				while(rs.next())
				{
					Vector tem=new Vector();
					tem.add(rs.getString(1));
					
					tem.add(rs.getString(2));
					tem.add(rs.getInt(3));
					tem.add(rs.getString(4));
					tem.add(rs.getString(5));
					rowData.add(tem);
				}
			}else if(value=="上课信息")
			{
				while(rs.next())
				{
					Vector tem=new Vector();
					tem.add(rs.getString(1));
					tem.add(rs.getString(2)+"/"+rs.getString(3));
					tem.add(rs.getString(4)+"/"+rs.getString(5));
					tem.add(rs.getString(6));
					tem.add(rs.getString(7));
					rowData.add(tem);
				}
			}else if(value=="教室具体使用情况"){
				while(rs.next())
				{
					Vector tem=new Vector();

					
					tem.add(rs.getString(1));
					tem.add("上课");
					tem.add(rs.getString(2));
					tem.add(rs.getString(3)+"/"+rs.getString(4));
					tem.add(rs.getString(5)+"/"+rs.getString(6));
					rowData.add(tem);
				}
				String sql2 = "select 教室编号,申请者,convert(varchar(20),开始时间,111),convert(varchar(20),开始时间,108),convert(varchar(20),结束时间,111),convert(varchar(20),结束时间,108) from orderinfo where 审核情况='审核通过' and DateDiff(dd,getdate(),开始时间)<=7 and  DateDiff(dd,getdate(),开始时间)>=0 order by 开始时间";
				//加载驱动
				Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
				//得到连接
		        connection=DriverManager.getConnection("jdbc:sqlserver://Localhost:1433;DatabaseName=classroom", "sa", "c5352948");
				//创建访问数据库接口
				ps2=connection.prepareStatement(sql2);
				rs2=ps2.executeQuery();
				while(rs2.next())
				{
					Vector tem=new Vector();

					
					tem.add(rs2.getString(1));
					tem.add("活动");
					tem.add(rs2.getString(2));
					tem.add(rs2.getString(3)+"/"+rs2.getString(4));
					tem.add(rs2.getString(5)+"/"+rs2.getString(6));
					rowData.add(tem);
				}
			}else if(value=="管理申请") {
					while(rs.next())
					{
						Vector tem=new Vector();
						tem.add(rs.getString(1));
						tem.add(rs.getString(2));
						tem.add(rs.getString(3)+"/"+rs.getString(4));
						tem.add(rs.getString(5)+"/"+rs.getString(6));
						tem.add(rs.getString(7));
						tem.add(rs.getString(8));
						rowData.add(tem);
					}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				rs.close();
				ps.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return rowData;
	}

	
	//一些获取信息的函数
	@Override
	public String getColumnName(int arg0) {
		return (String) columnNames.get(arg0);
	}
	@Override
	public int getColumnCount() {
		return columnNames.size();
	}
	@Override
	public int getRowCount() {
		return rowData.size();
	}	
@Override 
	public Object getValueAt(int rowIndex, int columnIndex) {
		return ((Vector)rowData.get(rowIndex)).get(columnIndex);
	}
}

