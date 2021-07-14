/*    ��1/7
 *    ʵ�ֵ�¼�������ƹ���
 */   


package database;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
public class Login extends JDialog implements ActionListener {
	private JLabel name;
	private JLabel psw;
	private JTextField nameV;
	private JPasswordField pswV;
	private JButton login,quit;
	private JPanel top;
	private JPanel center;
	private JPanel bottom;
	private JLabel prompt;
	public static String loginName;   //�����û���¼��
	public static int power;    //�����û�Ȩ��
	public Login(Frame owner, String title, boolean modal) {
		super(owner, title, modal);
		init();
	}
	
	//JTextField ��ʽ���
	public void textSet(JTextField field) {
		field.setBackground(new Color(255, 255, 255));
		field.setPreferredSize(new Dimension(150, 28));
		MatteBorder border = new MatteBorder(0, 0, 2, 0, new Color(192, 192,
				192));
		field.setBorder(border);
	}
	
	//JButton ��ʽ���
	public void buttonSet(JButton btn) {
		btn.setForeground(new Color(0x33, 0x66, 0xcc));
		btn .setFont(new  java.awt.Font("�����п�",  1,  15));
		btn.setBackground(new Color(187,255,255));
	}

	
	//��¼����ĸ��ֳ�ʼ��
	private void init() {
		name = new JLabel("��¼��:");
		nameV = new JTextField(10);
		textSet(nameV);
		top=new JPanel();
		top.setBackground(new Color(197,228,251));
		top.add(name);
		top.add(nameV);
		this.add(top);
		psw=new JLabel("��    ��:");
		pswV=new JPasswordField(10);
		textSet(pswV);
		center=new JPanel();
		center.setBackground(new Color(197,228,251));
		center.add(psw);
		center.add(pswV);
		this.add(center);
		login=new JButton();
		login.setText("��¼");
		buttonSet(login);
		login.setActionCommand("login");
		login.addActionListener(this);
		quit=new JButton("�˳�");
		buttonSet(quit);
		quit.setActionCommand("quit");
		quit.addActionListener(this);
		bottom=new JPanel();
		bottom.setBackground(new Color(197,228,251));
		bottom.add(login);
		bottom.add(quit);
		this.add(bottom);
		prompt=new JLabel();
		prompt.setText("---���޷���¼����ϵ88788788---");
		bottom.add(prompt);
		this.setLayout(new GridLayout(3, 1));
		this.setLocation(550, 230);
		this.setSize(300,220);
		this.setBackground(new Color(197,228,251));
		this.setVisible(true);
	}
	@Override
	
	//��ť���¼����������
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getActionCommand()=="login")
		{
			String name=nameV.getText();
			String psw=pswV.getText();
			if(name.length()<=0||psw.length()<=0)
			{
				prompt.setText("��ʾ���û���������Ϊ�գ�");
				return;
			}
			if(checkCount(name,psw))
			{
				this.dispose();
			}else
			{
				prompt.setText("��ʾ���û������������");
				return;
			}
		}else if(arg0.getActionCommand()=="quit")
		{
			System.exit(0);
		}
	}
	
	
	//����û���������
	private boolean checkCount(String name,String psw)
	{
		Connection connection=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			//��������
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			//�õ�����
			connection=DriverManager.getConnection("jdbc:sqlserver://Localhost:1433;DatabaseName=classroom", "sa", "c5352948");
			//�����������ݿ�ӿ�
			ps=connection.prepareStatement("select * from login");
			rs=ps.executeQuery();
			while(rs.next())
			{
				String namet=rs.getString(1);
				String pswt=rs.getString(2);
				int powert=rs.getInt(3);
				System.out.println(namet+"<>"+pswt+"-------"+name+"<>"+psw+"---"+"<>"+powert+"---");
				if(namet.equals(name)&&pswt.equals(psw))
				{
					loginName=namet;
					power=powert;
					return true;
				}
			}
			System.out.println("false");
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
		return false;
	}
	@Override
	protected void processWindowEvent(WindowEvent arg0) {
		super.processWindowEvent(arg0);
		if(arg0.getID()==WindowEvent.WINDOW_CLOSING)
		{
			System.exit(0);
		}
	}
}


