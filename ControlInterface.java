/*    ��2/7
 *    ʵ�����������ƹ���
 */   

package database;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import database.Delete;
import database.Insert;
import database.Update;
import database.Login;
import database.RemindDialog;
import database.MyTableModel;
import database.Login;
import javax.swing.Timer;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
public class ControlInterface extends JFrame implements ActionListener{
	//��¼����
	Login login;
	//�����ƽ���
	private JPanel 		topBar,rightBar;
	//topBar
	private JLabel 		userName;
	private JButton		quit;
	//rightBar
	private JPanel 		operationP,buttonP,searchP;
	private JLabel 		operationT,search,myTime,search2;
	private JComboBox 	operation;
	private JButton 	searchB,searchB2,alert,insert,delete,yes,no,mynew;
	private JTextField	searchT,searchT2;//��������	 		
	//centerBar
	private	JScrollPane scroll;
	private	JTable 		content;
	private MyTableModel model;
	//JTextField ��ʽ���
	public void textSet(JTextField field) {
		field.setBackground(new Color(255, 255, 255));
		field.setPreferredSize(new Dimension(150, 28));
		MatteBorder border = new MatteBorder(0, 0, 2, 0, new Color(192, 192,192));
		field.setBorder(border);
		}
		
		//JButton ��ʽ���
	public void buttonSet(JButton btn) {
		btn.setForeground(new Color(0, 0, 139));
		btn .setFont(new  java.awt.Font("�����п�",  1,  15));
		btn.setBackground(new Color(84,255,159));
		}
	class Time implements Runnable{
		private JLabel lab = new JLabel();
		public Time(JLabel lab) {
			this.lab = lab;
		}
		public void run() {
			while(true) {
				DateFormat d1=DateFormat.getDateTimeInstance();
				lab.setText("                ��ǰʱ�䣺"+d1.format(new Date()));
				try {
					Thread.sleep(1000);
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
	//���ÿ��Բ�ѯ���б�
	private String[] list=new String[]{"������Ϣ","��ʦ��Ϣ","�Ͽ���Ϣ","���Ҿ���ʹ�����"};
	private JLabel remind;
	public static void main(String[] args)
	{
		ControlInterface myCon = new ControlInterface();  
	}
	public ControlInterface()
	{
		//��¼���� --------------------------------------------
		login=new Login(this, "��¼",true);
		//��½�������֮��
		//topBar
		topBar		=	new JPanel();
		topBar.setBackground(new Color(197,228,251));
		userName	=	new JLabel();

		//��ʼ��ʱ���̺߳ͽ���ʹ����Ϣ
		myTime = new JLabel();
		try {
			checkClassRoom( );
		} catch (SQLException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
		
		
		//�������ʼ��
		if(Login.power == 0) {
			userName.setText(Login.loginName);
		} else if(Login.power == 1) {
			userName.setText("����Ա"+Login.loginName);
		}
		userName.setFont(new Font("����",1,25));//����,1������ʽ���ֺ�
		userName.setLocation(this.getWidth()/2-userName.getWidth()/2, userName.getHeight()/3);
		myTime.setFont(new Font("����",1,25));//����,1������ʽ���ֺ�
		myTime.setForeground(new Color(153,50,204));

		topBar.add(userName,BorderLayout.CENTER);
		quit = new JButton("�˳�");
		buttonSet(quit);
		quit.setActionCommand("quit");
		quit.addActionListener(this);
		quit.setLocation((int) (this.getWidth()-quit.getWidth()*1.5),userName.getHeight()/3);
		topBar.add(quit,BorderLayout.CENTER);
		topBar.add(myTime,BorderLayout.EAST);
		this.add(topBar,BorderLayout.NORTH);
		//rightBar
		rightBar=new JPanel();
		rightBar.setBackground(new Color(197,228,251));
		rightBar.setLayout(new GridLayout(4,1));
		this.add(rightBar,BorderLayout.EAST);
		operationP=new JPanel();
		operationP.setBackground(new Color(197,228,251));
		operationP.setLayout(new FlowLayout());
		operationT=new JLabel("ѡ���");
		operation=new JComboBox(list);
		operation.addItem("��������");
		operation.addItemListener(new ItemListener(){
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				remind.setText("");
				if(operation.getSelectedItem() == "���Ҿ���ʹ�����")
					remind.setText("---�ñ��֧�ֲ鿴��һ������---");
				searchT.setText("������"+ 
				operation.getSelectedItem().toString().substring(0,2)+ "���");
				model=new MyTableModel(operation.getSelectedItem().toString());
				content.setModel(model);
				FitTableColumns(content);
				alert.setEnabled(false);
				insert.setEnabled(false);
				delete.setEnabled(false);
				if(Login.power == 1)
				{
						alert.setEnabled(true);
						insert.setEnabled(true);
						delete.setEnabled(true);
				}
				if(operation.getSelectedItem() == "��������")
				{
					insert.setEnabled(true);
					delete.setEnabled(true);
				}
			}
		}) ;
		operationP.add(operationT);
		operationP.add(operation);
		buttonP=new JPanel();
		buttonP.setBackground(new Color(197,228,251));
		buttonP.setLayout(new GridLayout(3,1));
		alert=new JButton("�޸�");
		buttonSet(alert);
		alert.addActionListener(this);
		alert.setActionCommand("alert");
		insert=new JButton("����");
		buttonSet(insert);
		insert.addActionListener(this);
		insert.setActionCommand("insert");
		delete=new JButton("ɾ��");
		buttonSet(delete);
		delete.addActionListener(this);
		delete.setActionCommand("delete");
		mynew=new JButton("ˢ��");
		buttonSet(mynew);
		mynew.addActionListener(this);
		mynew.setActionCommand("mynew");
		if(Login.power == 0)
		{
		alert.setEnabled(false);
		insert.setEnabled(false);
		delete.setEnabled(false);
		buttonP.add(insert);
		buttonP.add(delete);
		
		buttonP.add(mynew);
		}
		
		
		if(Login.power==1) {
			yes=new JButton("ȷ��");
			buttonSet(yes);
			yes.addActionListener(this);
			yes.setActionCommand("yes");
			no=new JButton("ȡ��");
			buttonSet(no);
			no.addActionListener(this);
			no.setActionCommand("no");
			mynew=new JButton("ˢ��");
			buttonSet(mynew);
			mynew.addActionListener(this);
			mynew.setActionCommand("mynew");
			buttonP.add(alert);
			buttonP.add(yes);
			buttonP.add(insert);
			buttonP.add(no);
			buttonP.add(delete);
			buttonP.add(mynew);
		
			
		}
		operationP.add(buttonP);
		operationP.setPreferredSize(new Dimension(250, 0));
		rightBar.add(operationP);
		search=new JLabel("�ؼ��֣�");
		searchT=new JTextField(10);
		textSet(searchT);
		searchT.setText("������"+operation.getSelectedItem().toString().substring(0,2)+"���");
		search2=new JLabel("λ��������:");
		searchT2=new JTextField(8);
		textSet(searchT2);
		searchB2=new JButton("����");
		buttonSet(searchB2);
		searchB2.addActionListener(this);
		searchB2.setActionCommand("search2week");
		searchB=new JButton("����");
		buttonSet(searchB);
		searchB.addActionListener(this);
		searchB.setActionCommand("search");
		searchP=new JPanel();
		searchP.setBackground(new Color(197,228,251));
		searchP.setLayout(new FlowLayout());
		searchP.setPreferredSize(new Dimension(250, 0));
		searchP.add(search);
		searchP.add(searchT);
		searchP.add(searchB);
		searchP.add(search2);
		searchP.add(searchT2);
		searchP.add(searchB2);
		rightBar.add(searchP);
		remind=new JLabel();
		rightBar.add(remind);
		
		//centerBar
		model=new MyTableModel(operation.getSelectedItem().toString());
		content=new JTable(model);
		
		
		
		//�����ʽ���
		content.setRowHeight(40);
		content.setRowMargin(0);
		content.setSelectionBackground(new Color(189,252,201));
		content.setGridColor(new Color(200,200,200));
		content.setShowGrid(true);
		content.setShowHorizontalLines(true);
		content.setShowVerticalLines(true);
		content.setBackground(new Color(204,255,255));
		content.setFont(new Font("Menu.font", Font.PLAIN, 20));
		DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();// ����table���ݾ���
		tcr.setHorizontalAlignment(SwingConstants.CENTER);
		content.setDefaultRenderer(Object.class, tcr);
		FitTableColumns(content);
		
		
		//��������ʽ���
		scroll=new JScrollPane(content);
		this.add(scroll,BorderLayout.CENTER);
		this.setTitle("���ҹ���ϵͳ");
		this.setResizable(false);
		this.setLocation(80,60);
		this.setSize(1200, 600);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		new Thread(new Time(myTime)).start();
	}
	@Override
	//�ұ߰�ť����ѡ����¼��������
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getActionCommand()=="quit")
		{
			System.exit(0);
		}
		if(arg0.getActionCommand()=="search")
		{
			search2UnionTable();
			return;
		}
		if(arg0.getActionCommand()=="search2week")
		{
			if(searchT2.getText().toString().trim().equals("����+"))
			{
				String sql = "update ClassRoomInfo set �Ͽο�ʼʱ��=DATEADD(day,7,�Ͽο�ʼʱ��)";
				update2Class(sql);
				sql = "update ClassRoomInfo set ����ʱ��=DATEADD(day,7,����ʱ��)";
				update2Class(sql);
				return;
			}
			if(searchT2.getText().toString().trim().equals("����-"))
			{
				String sql = "update ClassRoomInfo set �Ͽο�ʼʱ��=DATEADD(day,-7,�Ͽο�ʼʱ��)";
				update2Class(sql);
				sql = "update ClassRoomInfo set ����ʱ��=DATEADD(day,-7,����ʱ��)";
				update2Class(sql);
				return;
			}
			search2WeekTable();
			return;
		}
		if(((String)operation.getSelectedItem())=="���Ҿ���ʹ�����")
		{
			new RemindDialog(this, "��ʾ",true,0);
			return;
		}
		if(((String)operation.getSelectedItem())=="��������" && arg0.getActionCommand()=="alert")
		{
			new RemindDialog(this, "��ʾ",true,0);
			return;
		}
		if(arg0.getActionCommand()=="insert")
		{
			new Insert(this,"����������",true,operation.getSelectedItem().toString());
			model=new MyTableModel(operation.getSelectedItem().toString());
			content.setModel(model);
			FitTableColumns(content);
			return;
		}
		if(arg0.getActionCommand()=="mynew")
		{
			try {
				checkClassRoom( );
			} catch (SQLException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			}
			model=new MyTableModel(operation.getSelectedItem().toString());
			content.setModel(model);
			FitTableColumns(content);
			return;
		}
		if(content.getSelectedRow()==-1)
		{
			new RemindDialog(this, "��ʾ",true,1);
			return;
		}
		if(arg0.getActionCommand()=="alert")
		{
			Vector v=(Vector)MyTableModel.rowData.get(content.getSelectedRow());
			new Update(this,"�޸�",true,operation.getSelectedItem().toString(),v);
			model=new MyTableModel(operation.getSelectedItem().toString());
			content.setModel(model);
			FitTableColumns(content);
			return;
		}
		if(arg0.getActionCommand()=="delete")
		{
			Vector v=(Vector) MyTableModel.rowData.get(content.getSelectedRow());
			new Delete(this,"ɾ��",true,v,operation.getSelectedItem().toString());
			model=new MyTableModel(operation.getSelectedItem().toString());
			content.setModel(model);
			FitTableColumns(content);
			return;
		}
		if(arg0.getActionCommand()=="yes")
		{
			if(((String)operation.getSelectedItem())=="��������")
			{
				Vector v=(Vector)MyTableModel.rowData.get(content.getSelectedRow());
				String num = v.get(0).toString();
				Update up = new Update(this,"�޸�",true,operation.getSelectedItem().toString(),v);
				up.update2Order(true, num);
				model=new MyTableModel(operation.getSelectedItem().toString());
				content.setModel(model);
				FitTableColumns(content);
				return;
			}
			if(((String)operation.getSelectedItem())=="������Ϣ")
			{
				Vector v=(Vector)MyTableModel.rowData.get(content.getSelectedRow());
				String num = v.get(0).toString();
				Update up = new Update(this,"�޸�",true,"",v);
				up.update2Option(true, num);
				model=new MyTableModel(operation.getSelectedItem().toString());
				content.setModel(model);
				FitTableColumns(content);
				return;
			}
		}
		if(arg0.getActionCommand()=="no")
		{
			if(((String)operation.getSelectedItem())=="��������")
			{
				Vector v=(Vector)MyTableModel.rowData.get(content.getSelectedRow());
				String num = v.get(0).toString();
				Update up = new Update(this,"�޸�",true,operation.getSelectedItem().toString(),v);
				up.update2Order(false, num);
				model=new MyTableModel(operation.getSelectedItem().toString());
				content.setModel(model);
				FitTableColumns(content);
				return;
			}
			if(((String)operation.getSelectedItem())=="������Ϣ")
			{
				Vector v=(Vector)MyTableModel.rowData.get(content.getSelectedRow());
				String num = v.get(0).toString();
				Update up = new Update(this,"�޸�",true,"",v);
				up.update2Option(false, num);
				model=new MyTableModel(operation.getSelectedItem().toString());
				content.setModel(model);
				FitTableColumns(content);
				return;
			}
		}
		
	}


//ʵ��JTable���п������ݵ�����Ӧ
	public void FitTableColumns(JTable myTable){
		  JTableHeader header = myTable.getTableHeader();
		     int rowCount = myTable.getRowCount();
		     Enumeration columns = myTable.getColumnModel().getColumns();
		     while(columns.hasMoreElements()){
		         TableColumn column = (TableColumn)columns.nextElement();
		         int col = header.getColumnModel().getColumnIndex(column.getIdentifier());
		         int width = (int)myTable.getTableHeader().getDefaultRenderer()
		                 .getTableCellRendererComponent(myTable, column.getIdentifier()
		                         , false, false, -1, col).getPreferredSize().getWidth();
		         for(int row = 0; row<rowCount; row++){
		             int preferedWidth = (int)myTable.getCellRenderer(row, col).getTableCellRendererComponent(myTable,
		               myTable.getValueAt(row, col), false, false, row, col).getPreferredSize().getWidth();
		             width = Math.max(width, preferedWidth);
		         }
		         header.setResizingColumn(column); // ���к���Ҫ
		         column.setWidth(width+myTable.getIntercellSpacing().width+30);
		     }
	}
	
	//һ��������ʵ�ֿα���������������
	private void search2WeekTable()
	{
		if(operation.getSelectedItem().toString() == "�Ͽ���Ϣ")
		{
		Vector v=MyTableModel.select(operation.getSelectedItem().toString());
		Vector rowData=new Vector();
		Iterator iterator=v.iterator();
		String rNum=searchT2.getText().toString().trim();
		int i=0;
		try {
			i = Integer.valueOf(rNum).intValue();
		}catch(NumberFormatException e) {
			remind.setText("-----��������ʽ����-----");
			return;
		}
		while(iterator.hasNext())
		{
			Vector tem=(Vector)iterator.next();
			if(i!=0)
			{
				Object object=tem.remove(1);
				tem.insertElementAt(datetoWeek(object.toString(),i), 1);
				object=tem.remove(2);
				tem.insertElementAt(datetoWeek(object.toString(),i), 2);
				rowData.add(tem);
			}else
			{
				rowData.add(tem);
			}
			remind.setText("");
		}
		MyTableModel my=new MyTableModel(rowData,MyTableModel.columnNames);
		content.setModel(my);
		FitTableColumns(content);
		}else {
			remind.setText("-----λ�ƶԸñ���Ч��-----");
			return;
		}
	}
	
	
	//ʵ���ض���Ϣ��ѯ����
	private void search2UnionTable()
	{
		boolean fac = false;
		String rNum2=searchT2.getText().toString().trim();
		System.out.println(rNum2);
		int i=0;
		if(operation.getSelectedItem().toString() == "�Ͽ���Ϣ" && !searchT2.getText().trim().equals(""))
		{
			fac = true;
			try {
				i = Integer.valueOf(rNum2).intValue();
			}catch(NumberFormatException e) {
				remind.setText("-----��������ʽ����-----");
				return;
			}
		}
		Vector v=MyTableModel.select(operation.getSelectedItem().toString());
		Vector rowData=new Vector();
		Iterator iterator=v.iterator();
		String rNum=searchT.getText().toString().trim();
		while(iterator.hasNext())
		{
			Vector tem=(Vector)iterator.next();
			if(((String)tem.get(0)).equals(rNum))
			{
				if(fac)
				{
					Object object=tem.remove(1);
					tem.insertElementAt(datetoWeek(object.toString(),i), 1);
					object=tem.remove(2);
					tem.insertElementAt(datetoWeek(object.toString(),i), 2);
				}
				rowData.add(tem);
			}
		}
		if(rowData.size()==0)
		{
			remind.setText("-----û�м�¼-----");
			return;
		}else
		{
			remind.setText("");
		}
		MyTableModel my=new MyTableModel(rowData,MyTableModel.columnNames);
		content.setModel(my);
		FitTableColumns(content);
	}
	
	
	//���������ڵĺ���
	private static String datetoWeek(String date,int i) {
		String newDate=null;
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sd = new SimpleDateFormat("yyyy/MM/dd/HH:mm:ss");
		Date mydate;
		try {
		 mydate = sd.parse(date);
		} catch (ParseException e) {
		 mydate = null;
		 e.printStackTrace();
		}
		cal.setTime(mydate);
		cal.set(Calendar.DAY_OF_YEAR, cal.get(Calendar.DAY_OF_YEAR) + i*7);
		Date today = cal.getTime();  
	    SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd/HH:mm:ss");
	    newDate = format.format(today);
		return newDate;
	}
	
	
	//һ�������� �������µ�ǰ���ҵ�ʹ������������ұ��е�ʹ��������
	private void checkClassRoom( ) throws SQLException {
		boolean fac=false;
		
		String sql = null;
		String sql2 = null;
		final Vector cVector=MyTableModel.select("���Ҿ���ʹ�����");
		final Vector classVector=MyTableModel.select("������Ϣ");
		Date date = new Date();
		SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String now=dateFormat.format(date);

		for(int i=0;i<classVector.size();i++) {
			sql2="update ClassInfo set ʹ��״̬='δʹ��' where ���ұ��='"+((Vector)classVector.get(i)).get(0)+"'";
			update2Class(sql2);
		}
			for(int i=0;i<cVector.size();i++)
			{
				String tem1=(String) ((Vector)cVector.get(i)).get(3);
				tem1=tem1.substring(0,10)+" "+tem1.substring(11);
				String tem2=(String) ((Vector)cVector.get(i)).get(4);
				tem2=tem2.substring(0,10)+" "+tem2.substring(11);
				DateFormat df=new SimpleDateFormat("yyyy/mm/dd hh:mm:ss");
				try {
					Date start=df.parse(tem1);
					Date end=df.parse(tem2);
					Date myNowDay=df.parse(now);
					if(myNowDay.getTime()>start.getTime())
					{
						if(myNowDay.getTime()<end.getTime())
						{
							fac=true;
						}
					}
					else
						fac=false;
				} catch (ParseException e) {
					e.printStackTrace();
				}
				if(fac)
				{
					System.out.println(2);
					sql="update ClassInfo set ʹ��״̬='����ʹ��' where ���ұ��='"+((Vector)cVector.get(i)).get(0)+"'";
					update2Class(sql);
				}
				fac=false;
			} 
	}
	
	
	//ˢ�½���ʹ��״̬���ܵľ���ʵ��
	private void update2Class(String sql)
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
	
}

