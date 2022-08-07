package com.han.view.home;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

import com.han.pojo.User;
import com.han.view.home.internal_frame.AddAccountFrame;
import com.han.view.home.internal_frame.UpdateAccountFrame;
import com.han.view.imagepanel.HomePanel;
import com.han.view.login.Login;
import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.Color;

public class Home extends JFrame {

	private User user;
	private JPanel panel;
	private JMenuItem jMenuItemAddAccount;
	private JMenuItem jMenuItemDeleteAccount;
	private JMenuItem jMenuItemUpdateAccount;
	private JMenuItem jMenuItemFindAccount;



	private JMenu mnNewMenu_1;
	private JMenuItem mntmNewMenuItem_pmanage;
	private JMenu mnNewMenu_2;
	private JMenuItem mntmNewMenuItem_3;
	private JMenuItem mntmNewMenuItem_4;
	private JMenuItem mntmNewMenuItem_one;
	private JMenuItem mntmNewMenuItem_exit;

	/**
	 * 获取user对象，用于显示用户个人信息
	 *
	 * @return
	 */
	public User getUs() {
		return user;
	}

	/**
	 * 设置用户
	 *
	 * @param us
	 */
	public void setUs(User us) {
		this.user = us;
	}

	/**
	 * 统一设置字体，父界面设置之后，所有由父界面进入的子界面都不需要再次设置字体
	 */
	private void InitGlobalFont(Font font) {
		FontUIResource fontRes = new FontUIResource(font);
		for (Enumeration<Object> keys = UIManager.getDefaults().keys(); keys.hasMoreElements();) {
			Object key = keys.nextElement();
			Object value = UIManager.get(key);
			if (value instanceof FontUIResource) {
				UIManager.put(key, fontRes);
			}
		}
	}

	/**
	 * 创建管理员界面
	 */
	public Home(User user) {
		this.user = user;
		//设置标题图片
		setIconImage(Toolkit.getDefaultToolkit().getImage(System.getProperty("user.dir")+"\\src"+"\\img\\identify_2.png"));
		InitGlobalFont(new Font("alias", Font.PLAIN, 15)); // 统一设置字体
		String str = "用户";
		if (user.getIdentity().equals("1")) {
			str = "管理员";
		}
		setTitle("信用卡管理系统首页（" + str +"）" + user.getName());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1301, 801);// 设置窗体大小
		getContentPane().setLayout(new BorderLayout(0, 0));

		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(Color.WHITE);
		getContentPane().add(menuBar, BorderLayout.NORTH);
		JMenu mnNewMenu = new JMenu("帐号资料管理");
		menuBar.add(mnNewMenu);
		mnNewMenu.setIcon(new ImageIcon("./images/菜单_1.png"));

		jMenuItemAddAccount = new JMenuItem("新增账户");
		mnNewMenu.add(jMenuItemAddAccount);
		jMenuItemAddAccount.setIcon(new ImageIcon("./images/操作_1.png"));
		mnNewMenu.addSeparator();
		jMenuItemDeleteAccount = new JMenuItem("删除用户");
		mnNewMenu.add(jMenuItemDeleteAccount);
		jMenuItemDeleteAccount.setIcon(new ImageIcon("./images/操作_2.png"));
		mnNewMenu.addSeparator();
		jMenuItemUpdateAccount = new JMenuItem("修改用户");
		mnNewMenu.add(jMenuItemUpdateAccount);
		jMenuItemUpdateAccount.setIcon(new ImageIcon("./images/操作_1.png"));
		mnNewMenu.addSeparator();
		jMenuItemFindAccount = new JMenuItem("查询用户");
		mnNewMenu.add(jMenuItemFindAccount);
		jMenuItemFindAccount.setIcon(new ImageIcon("./images/操作_2.png"));

		mnNewMenu_1 = new JMenu("系统管理");
		menuBar.add(mnNewMenu_1);
		mnNewMenu_1.setIcon(new ImageIcon("./images/菜单_1.png"));

//		mntmNewMenuItem_pmanage = new JMenuItem("人员管理");
//		mnNewMenu_1.add(mntmNewMenuItem_pmanage);
//		mntmNewMenuItem_pmanage.setIcon(new ImageIcon("./images/操作_1.png"));

//		mntmNewMenuItem_one = new JMenuItem("个人信息");
//		mnNewMenu_1.add(mntmNewMenuItem_one);
//		mntmNewMenuItem_one.setIcon(new ImageIcon("./images/操作_2.png"));

		mnNewMenu_2 = new JMenu("系统信息");
		menuBar.add(mnNewMenu_2);
		mnNewMenu_2.setIcon(new ImageIcon("./images/菜单_1.png"));

//		mntmNewMenuItem_3 = new JMenuItem("垃圾站详细");
//		mnNewMenu_2.add(mntmNewMenuItem_3);
//		mntmNewMenuItem_3.setIcon(new ImageIcon("./images/操作_1.png"));

//		mntmNewMenuItem_4 = new JMenuItem("关于系统");
//		mnNewMenu_2.add(mntmNewMenuItem_4);
//		mntmNewMenuItem_4.setIcon(new ImageIcon("./images/操作_2.png"));

		mntmNewMenuItem_exit = new JMenuItem("退出登录");
		mnNewMenu_2.add(mntmNewMenuItem_exit);
		mntmNewMenuItem_exit.setIcon(new ImageIcon("./images/退出_1.png"));

		String path = System.getProperty("user.dir")+"\\src"+"\\img\\模糊背景_1.jpg";
		panel = new HomePanel(path);
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		setVisible(true);
		setLocationRelativeTo(null);// 将窗口显示在屏幕中央
		setOncListener();
	}

	/**
	 * 菜单项的事件监听器
	 */
	public void setOncListener() {
		jMenuItemAddAccount.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("录入成功");
				AddAccountFrame AccountFrame = new AddAccountFrame();
				JInternalFrame addAccountFrame = AccountFrame.init();
				panel.add(addAccountFrame, BorderLayout.CENTER);
				addAccountFrame.setVisible(true);
			}
		});
		jMenuItemUpdateAccount.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("处理成功");
				UpdateAccountFrame jquery = new UpdateAccountFrame();
				JInternalFrame updateAccountFrame = jquery.init();
				panel.add(updateAccountFrame, BorderLayout.CENTER);
				updateAccountFrame.setVisible(true);
			}
		});
//		mntmNewMenuItem_pmanage.addActionListener(new ActionListener() {
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				// TODO Auto-generated method stub
//				System.out.println("进入人员管理成功");
//				jInternalFrame_pManagement jpm = new jInternalFrame_pManagement();
//				JInternalFrame internalFrame = jpm.init();
//				panel.add(internalFrame, BorderLayout.CENTER);
//				internalFrame.setVisible(true);
//			}
//		});
//		mntmNewMenuItem_one.addActionListener(new ActionListener() {
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				// TODO Auto-generated method stub
//				System.out.println("进入个人信息成功");
//				jInternalFrame_pManagement_one internalFrame = new jInternalFrame_pManagement_one();
//				internalFrame.setUs(us);
//				JInternalFrame internalFrame_1 = internalFrame.init();
//				panel.add(internalFrame_1, BorderLayout.CENTER);
//				internalFrame_1.setVisible(true);
//			}
//		});
		mntmNewMenuItem_exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int n = JOptionPane.showConfirmDialog(panel, "确定退出登录?", "温馨提示",JOptionPane.YES_NO_OPTION);//返回的是按钮的index  i=0或者1
				if(n==0) {
					new Login();
					dispose();
				}

			}
		});
	}
}
