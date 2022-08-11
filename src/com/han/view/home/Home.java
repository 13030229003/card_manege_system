package com.han.view.home;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;

import com.han.pojo.User;
import com.han.view.home.internal_frame.*;
import com.han.view.imagepanel.HomePanel;
import com.han.view.login.Login;


public class Home extends JFrame {

	private User user;
	private JPanel panel;
	private JMenuItem jMenuItemAddAccount;
	private JMenuItem jMenuItemUpdateAccount;
	private JMenuItem jMenuItemUpdateCredit;

	private JMenu mnNewMenu_1;
	private JMenu mnNewMenu_2;
	private JMenu mnNewMenu_3;
	private JMenu mnNewMenu_4;
	private JMenu mnNewMenu_5;
	private JMenu mnNewMenu_6;

	private JMenuItem jMenuItemAddBill;
	private JMenuItem jMenuItemFindBill;

	private JMenuItem jMenuItemCash;
	private JMenuItem jMenuItemRepayment;
	private JMenuItem jMenuItemCallForMoney;


	private JMenuItem jMenuItem_exit;
	private JMenuItem jMenuItem_credit_transfer;
	private JMenuItem jMenuItem_personal_center;

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
		if ("1".equals(user.getIdentity())){
			menuBar.add(mnNewMenu);
		}
		mnNewMenu.setIcon(new ImageIcon("./images/菜单_1.png"));

		jMenuItemAddAccount = new JMenuItem("新增账户");
		mnNewMenu.add(jMenuItemAddAccount);
		jMenuItemAddAccount.setIcon(new ImageIcon("./images/操作_1.png"));
		mnNewMenu.addSeparator();

		jMenuItemUpdateAccount = new JMenuItem("修改用户");
		mnNewMenu.add(jMenuItemUpdateAccount);
		jMenuItemUpdateAccount.setIcon(new ImageIcon("./images/操作_1.png"));

		mnNewMenu_1 = new JMenu("账户信用管理");

//		if ("1".equals(user.getIdentity())){
//			menuBar.add(mnNewMenu_1);
//		}
		mnNewMenu_1.setIcon(new ImageIcon("./images/菜单_1.png"));

		jMenuItemUpdateCredit = new JMenuItem("信用修改");
		mnNewMenu_1.add(jMenuItemUpdateCredit);
		jMenuItemUpdateCredit.setIcon(new ImageIcon("./images/操作_1.png"));

		mnNewMenu_2 = new JMenu("账单管理");
		menuBar.add(mnNewMenu_2);
		mnNewMenu_2.setIcon(new ImageIcon("./images/菜单_1.png"));

		jMenuItemAddBill = new JMenuItem("新增账单");
		if ("0".equals(user.getIdentity())){
			mnNewMenu_2.add(jMenuItemAddBill);
		}

		jMenuItemAddBill.setIcon(new ImageIcon("./images/操作_1.png"));

		jMenuItemFindBill = new JMenuItem("账单查询");
		mnNewMenu_2.add(jMenuItemFindBill);
		jMenuItemFindBill.setIcon(new ImageIcon("./images/操作_2.png"));


		mnNewMenu_3 = new JMenu("取现管理");
		if ("0".equals(user.getIdentity())){
			menuBar.add(mnNewMenu_3);
		}
		mnNewMenu_3.setIcon(new ImageIcon("./images/菜单_1.png"));

		jMenuItemCash = new JMenuItem("取现");
		mnNewMenu_3.add(jMenuItemCash);
		jMenuItemCash.setIcon(new ImageIcon("./images/操作_1.png"));



		mnNewMenu_4 = new JMenu("还款管理");
		if ("0".equals(user.getIdentity())){
			menuBar.add(mnNewMenu_4);
		}
		mnNewMenu_4.setIcon(new ImageIcon("./images/菜单_1.png"));

		jMenuItemRepayment = new JMenuItem("还款");
		mnNewMenu_4.add(jMenuItemRepayment);
		jMenuItemRepayment.setIcon(new ImageIcon("./images/操作_1.png"));


		mnNewMenu_5 = new JMenu("催款管理");
		if ("1".equals(user.getIdentity())){
			menuBar.add(mnNewMenu_5);
		}
		mnNewMenu_5.setIcon(new ImageIcon("./images/菜单_1.png"));

		jMenuItemCallForMoney = new JMenuItem("催款");
		mnNewMenu_5.add(jMenuItemCallForMoney);
		jMenuItemCallForMoney.setIcon(new ImageIcon("./images/操作_1.png"));



		mnNewMenu_6 = new JMenu("系统管理");
		menuBar.add(mnNewMenu_6);
		mnNewMenu_6.setIcon(new ImageIcon("./images/菜单_1.png"));

		jMenuItem_personal_center = new JMenuItem("个人中心");

		if ("0".equals(user.getIdentity())){
			mnNewMenu_6.add(jMenuItem_personal_center);
		}
		jMenuItem_personal_center.setIcon(new ImageIcon("./images/操作_1.png"));


		jMenuItem_credit_transfer = new JMenuItem("信用转账");
		if ("0".equals(user.getIdentity())){
			mnNewMenu_6.add(jMenuItem_credit_transfer);
		}
		jMenuItem_credit_transfer.setIcon(new ImageIcon("./images/操作_1.png"));

		jMenuItem_exit = new JMenuItem("退出登录");
		mnNewMenu_6.add(jMenuItem_exit);
		jMenuItem_exit.setIcon(new ImageIcon("./images/退出_1.png"));



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


		jMenuItem_credit_transfer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
//				System.out.println("打开信用转账页面....");
				CreditTransferFrame jquery = new CreditTransferFrame(user);
				JInternalFrame repaymentFrame = jquery.init();
				panel.add(repaymentFrame, BorderLayout.CENTER);
				repaymentFrame.setVisible(true);
			}
		});

		jMenuItem_personal_center.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
//				System.out.println("进入个人中心......");
				PersonalCenterFrame jquery = new PersonalCenterFrame(user);
				JInternalFrame repaymentFrame = jquery.init();
				panel.add(repaymentFrame, BorderLayout.CENTER);
				repaymentFrame.setVisible(true);
			}
		});

		jMenuItemCallForMoney.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
//				System.out.println("打开催款界面，催款成功....");
				FindArrearsBillFrame jquery = new FindArrearsBillFrame(user);
				JInternalFrame repaymentFrame = jquery.init();
				panel.add(repaymentFrame, BorderLayout.CENTER);
				repaymentFrame.setVisible(true);
			}
		});

		jMenuItemRepayment.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
//				System.out.println("打开还款页面，还款成功....");
				RepaymentFrame jquery = new RepaymentFrame(user);
				JInternalFrame repaymentFrame = jquery.init();
				panel.add(repaymentFrame, BorderLayout.CENTER);
				repaymentFrame.setVisible(true);
			}
		});

		jMenuItemCash.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AddCashBillFrame jquery = new AddCashBillFrame(user);
				JInternalFrame addCashBillFrame = jquery.init();
				panel.add(addCashBillFrame, BorderLayout.CENTER);
				addCashBillFrame.setVisible(true);
			}
		});

		jMenuItemAddAccount.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
//				System.out.println("录入成功");
				AddAccountFrame AccountFrame = new AddAccountFrame();
				JInternalFrame addAccountFrame = AccountFrame.init();
				panel.add(addAccountFrame, BorderLayout.CENTER);
				addAccountFrame.setVisible(true);
			}
		});

		/**
		 * x新增账单按钮，
		 */
		jMenuItemAddBill.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AddBillFrame jquery = new AddBillFrame(user);
				JInternalFrame addBillFrame = jquery.init();
				panel.add(addBillFrame, BorderLayout.CENTER);
				addBillFrame.setVisible(true);
			}
		});

		jMenuItemFindBill.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				FindBillFrame jquery = new FindBillFrame(user);
				JInternalFrame findBillFrame = jquery.init();
				panel.add(findBillFrame, BorderLayout.CENTER);
				findBillFrame.setVisible(true);
			}
		});

		jMenuItemUpdateAccount.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
//				System.out.println("处理成功");
				UpdateAccountFrame jquery = new UpdateAccountFrame();
				JInternalFrame updateAccountFrame = jquery.init();
				panel.add(updateAccountFrame, BorderLayout.CENTER);
				updateAccountFrame.setVisible(true);
			}
		});
		jMenuItemUpdateCredit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				UpdateAccountCreditFrame jquery = new UpdateAccountCreditFrame();
				JInternalFrame updateAccountCredit = jquery.init();
				panel.add(updateAccountCredit, BorderLayout.CENTER);
				updateAccountCredit.setVisible(true);
			}
		});

		jMenuItem_exit.addActionListener(new ActionListener() {
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
