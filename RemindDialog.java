/*    类7/7
 *    实现某些情况下对话框的弹出设计
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
			case 0:     //表示点到的表不可以进行按钮所示的操作
				JLabel warnning0=new JLabel("该表不支持操作");
				this.add(warnning0);
				JButton button0=new JButton("确定");
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
			case 1:     //检测不到JTable中选中状态
				JLabel warnning1=new JLabel("未选择任何行！！");
				this.add(warnning1);
				JButton button1=new JButton("确定");
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

