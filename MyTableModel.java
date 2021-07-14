/*    ��3/7
 *    ʵ��ÿ���������ݿ��ѯ����ȡ���������Vector��������Ĺ���
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


//��Ҫʵ�ִ��룬�����������ݿ�Ĳ�ѯ��Ϣ��������Щ��Ϣ����Ž�Vector������������ʵ�ֹ���
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
		if(value=="��ʦ��Ϣ")
		{
			columnNames.add("��ʦ���");
			columnNames.add("��ʦ����");
			columnNames.add("���ڿγ�");
			columnNames.add("��ʦְ��");
			sql="select * from TeacherInfo";
		}else if(value=="������Ϣ")
		{
			columnNames.add("���ұ��");
			columnNames.add("�����豸");
			columnNames.add("������������");
			columnNames.add("�����豸״̬");
			columnNames.add("ʹ��״̬");
			sql="select * from ClassInfo";
		}else if(value=="�Ͽ���Ϣ")
		{
			columnNames.add("���ұ��");
			columnNames.add("�Ͽο�ʼʱ��");
			columnNames.add("����ʱ��");
			columnNames.add("��ʦ��Ϣ");
			columnNames.add("�γ���Ϣ");
			sql="select ���ұ��, convert(varchar(20),�Ͽο�ʼʱ��,111),convert(varchar(20),�Ͽο�ʼʱ��,108),convert(varchar(20),����ʱ��,111) ,convert(varchar(20),����ʱ��,108), ��ʦ����,���ڿγ� from ClassRoomInfo,TeacherInfo where ClassRoomInfo.��ʦ���=TeacherInfo.��ʦ��� order by �Ͽο�ʼʱ��";
		}
		else if(value=="���Ҿ���ʹ�����")
		{
			columnNames.add("���ұ��");
			columnNames.add("ʹ������");
			columnNames.add("ʹ���߱��");
			columnNames.add("��ʼʱ��"); 
			columnNames.add("����ʱ��");
			sql="select ClassInfo.���ұ��,TeacherInfo.��ʦ���, convert(varchar(20),ClassRoomInfo.�Ͽο�ʼʱ��,111),convert(varchar(20),ClassRoomInfo.�Ͽο�ʼʱ��,108),convert(varchar(20),ClassRoomInfo.����ʱ��,111) ,convert(varchar(20),ClassRoomInfo.����ʱ��,108) from ClassRoomInfo,TeacherInfo,ClassInfo  where ClassInfo.���ұ��= ClassRoomInfo.���ұ��   and  TeacherInfo.��ʦ���=ClassRoomInfo.��ʦ��� order by ClassRoomInfo.�Ͽο�ʼʱ��";
		}
		else if(value=="��������")
		{
			columnNames.add("������");
			columnNames.add("���ұ��");
			columnNames.add("��ʼʱ��");
			columnNames.add("����ʱ��");
			columnNames.add("������");
			columnNames.add("������");
			sql="select ������,���ұ��,convert(varchar(20),��ʼʱ��,111),convert(varchar(20),��ʼʱ��,108),convert(varchar(20),����ʱ��,111),convert(varchar(20),����ʱ��,108),������,������ from orderinfo";
		}
		try {
			//��������
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			//�õ�����
	        connection=DriverManager.getConnection("jdbc:sqlserver://Localhost:1433;DatabaseName=classroom", "sa", "c5352948");
			//�����������ݿ�ӿ�
			ps=connection.prepareStatement(sql);
			rs=ps.executeQuery();
			if(value=="��ʦ��Ϣ")
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
			}else if(value=="������Ϣ")
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
			}else if(value=="�Ͽ���Ϣ")
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
			}else if(value=="���Ҿ���ʹ�����"){
				while(rs.next())
				{
					Vector tem=new Vector();

					
					tem.add(rs.getString(1));
					tem.add("�Ͽ�");
					tem.add(rs.getString(2));
					tem.add(rs.getString(3)+"/"+rs.getString(4));
					tem.add(rs.getString(5)+"/"+rs.getString(6));
					rowData.add(tem);
				}
				String sql2 = "select ���ұ��,������,convert(varchar(20),��ʼʱ��,111),convert(varchar(20),��ʼʱ��,108),convert(varchar(20),����ʱ��,111),convert(varchar(20),����ʱ��,108) from orderinfo where ������='���ͨ��' and DateDiff(dd,getdate(),��ʼʱ��)<=7 and  DateDiff(dd,getdate(),��ʼʱ��)>=0 order by ��ʼʱ��";
				//��������
				Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
				//�õ�����
		        connection=DriverManager.getConnection("jdbc:sqlserver://Localhost:1433;DatabaseName=classroom", "sa", "c5352948");
				//�����������ݿ�ӿ�
				ps2=connection.prepareStatement(sql2);
				rs2=ps2.executeQuery();
				while(rs2.next())
				{
					Vector tem=new Vector();

					
					tem.add(rs2.getString(1));
					tem.add("�");
					tem.add(rs2.getString(2));
					tem.add(rs2.getString(3)+"/"+rs2.getString(4));
					tem.add(rs2.getString(5)+"/"+rs2.getString(6));
					rowData.add(tem);
				}
			}else if(value=="��������") {
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

	
	//һЩ��ȡ��Ϣ�ĺ���
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

