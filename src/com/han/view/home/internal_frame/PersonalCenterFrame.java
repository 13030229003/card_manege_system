package com.han.view.home.internal_frame;

import com.han.controller.UserController;
import com.han.pojo.User;
import com.han.view.imagepanel.HomePanel;

import javax.swing.*;
import java.awt.event.*;

public class PersonalCenterFrame {

	private UserController userController = new UserController();
	private User user;

	private JPanel panel_1;
	private static JButton btn_add_storage_amount;

	private static JTextField textField_account;
	private static JTextField textField_total_credit;
	private static JTextField textField_desirable_credit;
	private static JTextField textField_arrears_amount;
	private static JTextField textField_available_credit;


	private static JTextField textField_storage_amount;
	private static JTextField textField_add_storage_amount;

	public PersonalCenterFrame() {
	}

	public PersonalCenterFrame(User user) {
		this.user = user;
	}

	/**
	 * 管理员垃圾处理界面的事件监听器
	 */
	private void addActionListener() {

		textField_add_storage_amount.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btn_add_storage_amount.doClick();
			}
		});

		btn_add_storage_amount.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				String money = textField_add_storage_amount.getText();

				if (money.length() < 1) {
					System.out.println("输入为空！！！");
				} else {
					if (Integer.valueOf(money) < 1) {
						System.out.println("请输入大于零的数！！！");
					} else {

						String inputContent = JOptionPane.showInputDialog(
								panel_1,
								"输入你的密码:",
								""
						);
						if (user.getPassword().equals(inputContent)) {
							int i = userController.userStorageAmountCharge(user, money);
							if (i > 0) {
//							System.out.println("充值成功。。充值金额：" + money);
								textField_add_storage_amount.setText("");
								user = userController.userSelectByAccount(user);
								textField_storage_amount.setText(user.getStorageAmount());
								textField_total_credit.setText(user.getTotalCredit());
							} else {
								System.out.println("预存失败!!!");
							}
						} else {
							JOptionPane.showMessageDialog(panel_1, "密码错误！！", "温馨提示",JOptionPane.WARNING_MESSAGE);
						}


					}
				}
			}
		});

	}

	/**
	 * 初始化管理员的垃圾处理界面
	 *
	 */
	public JInternalFrame init() {

		// 更新用户信息。
		user = userController.userSelectByAccount(user);


		// 创建一个内部窗口
		JInternalFrame internalFrame = new JInternalFrame("个人信息界面", // title
				false, // resizable
				true, // closable
				false, // maximizable
				true // iconifiable
		);

		// 设置窗口的宽高
		internalFrame.setSize(800, 400);
		// 设置窗口的显示位置
		internalFrame.setLocation(50, 50);
		internalFrame.getContentPane().setLayout(null);


		String path = System.getProperty("user.dir")+"\\src"+"\\img\\注册背景.jfif";
		panel_1 = new HomePanel(path);
		panel_1.setBounds(0, 10, 799, 400);
		internalFrame.getContentPane().add(panel_1);
		panel_1.setLayout(null);

		JLabel lblNewLabel = new JLabel("账户：");
		lblNewLabel.setBounds(14, 9, 100, 18);
		panel_1.add(lblNewLabel);

		// 账号
		textField_account = new JTextField();
		textField_account.setText(user.getAccount());
		textField_account.setEditable(false);
		textField_account.setBounds(110, 6, 130, 24);
		textField_account.setColumns(10);
		panel_1.add(textField_account);


		JLabel lblNewLabel_5 = new JLabel("预存金额：");
		lblNewLabel_5.setBounds(504, 9, 200, 20);
		panel_1.add(lblNewLabel_5);

		// 账号充值输入框
		textField_add_storage_amount = new JTextField();
		textField_add_storage_amount.setBounds(610, 6, 130, 30);
		textField_add_storage_amount.setColumns(10);
		panel_1.add(textField_add_storage_amount);

		// 查询按钮
		btn_add_storage_amount = new JButton("预存");
		btn_add_storage_amount.setBounds(630, 50, 110, 30);
		btn_add_storage_amount.setIcon(new ImageIcon("./images/垃圾查询_1.png"));
		panel_1.add(btn_add_storage_amount);

		JLabel lblNewLabel_1 = new JLabel("总信用额：");
		lblNewLabel_1.setBounds(14, 58, 100, 18);
		panel_1.add(lblNewLabel_1);

		textField_total_credit = new JTextField();
		textField_total_credit.setText(user.getTotalCredit());
		textField_total_credit.setEditable(false);
		textField_total_credit.setBounds(110, 55, 130, 24);
		panel_1.add(textField_total_credit);
		textField_total_credit.setColumns(10);

		JLabel lblNewLabel_2 = new JLabel("可取现信用：");
		lblNewLabel_2.setBounds(14, 107, 100, 18);
		panel_1.add(lblNewLabel_2);

		textField_desirable_credit = new JTextField();
		textField_desirable_credit.setText(user.getDesirableCredit());
		textField_desirable_credit.setEditable(false);
		textField_desirable_credit.setBounds(110, 104, 130, 24);
		textField_desirable_credit.setColumns(10);
		panel_1.add(textField_desirable_credit);

		JLabel lblNewLabel_3 = new JLabel("欠款金额：");
		lblNewLabel_3.setBounds(14, 156, 100, 18);
		panel_1.add(lblNewLabel_3);

		textField_arrears_amount = new JTextField();
		textField_arrears_amount.setText(user.getArrearsAmount());
		textField_arrears_amount.setEditable(false);
		textField_arrears_amount.setBounds(110, 153, 130, 24);
		textField_arrears_amount.setColumns(10);
		panel_1.add(textField_arrears_amount);


		JLabel lblNewLabel_4 = new JLabel("可用信用额：");
		lblNewLabel_4.setBounds(14, 206, 100, 18);
		panel_1.add(lblNewLabel_4);

		textField_available_credit = new JTextField();
		textField_available_credit.setText(user.getAvailableCredit());
		textField_available_credit.setEditable(false);
		textField_available_credit.setBounds(110, 203, 130, 24);
		textField_available_credit.setColumns(10);
		panel_1.add(textField_available_credit);

		JLabel lblNewLabel_6 = new JLabel("预存金额：");
		lblNewLabel_6.setBounds(14, 256, 100, 18);
		panel_1.add(lblNewLabel_6);

		textField_storage_amount = new JTextField();
		textField_storage_amount.setText(user.getStorageAmount());
		textField_storage_amount.setEditable(false);
		textField_storage_amount.setBounds(110, 253, 130, 24);
		textField_storage_amount.setColumns(10);
		panel_1.add(textField_storage_amount);

		addActionListener();
		return internalFrame;

	}
}
