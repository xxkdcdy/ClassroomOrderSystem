/*    ��7/7
 *    ʵ��ĳЩ����¶Ի���ĵ������
 */   


package database;

import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
public class RemindDialog extends JDialog {
	public RemindDialog(Frame owner, String title, boolean modal,int i) {
		super(owner, title, modal);
		switch(i)
		{
			case 0:     //��ʾ�㵽�ı����Խ��а�ť��ʾ�Ĳ���
				JLabel warnning0=new JLabel("�ñ�֧�ֲ���");
				this.add(warnning0);
				JButton button0=new JButton("ȷ��");
				button0.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent arg0) {
						RemindDialog.this.dispose();
					}
				});
				this.add(button0);
				this.setResizable(false);
				this.setLayout(new FlowLayout());
				this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
				this.setLocation(550, 230);
				this.setSize(150,100);
				this.setVisible(true);
				break;
			case 1:     //��ⲻ��JTable��ѡ��״̬
				JLabel warnning1=new JLabel("δѡ���κ��У���");
				this.add(warnning1);
				JButton button1=new JButton("ȷ��");
				button1.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent arg0) {
						RemindDialog.this.dispose();
					}
				});
				this.add(button1);
				this.setResizable(false);
				this.setLayout(new FlowLayout());
				this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
				this.setLocation(550, 230);
				this.setSize(150,100);
				this.setVisible(true);
				break;
		}
	}}

