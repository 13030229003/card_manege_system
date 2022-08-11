package com.han.view.home.internal_frame;

import com.han.controller.UserController;
import com.han.pojo.User;
import com.han.view.imagepanel.HomePanel;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreditTransferFrame {

	private UserController userController = new UserController();
	private User user;

	private JPanel panel_1;
	private static JButton btn_add_storage_amount;

	private static JTextField textField_account;
	private static JTextField textField_user_name;
	private static JTextField textField_add_storage_amount;

	private JInternalFrame internalFrame;

	public CreditTransferFrame() {
	}

	public CreditTransferFrame(User user) {
		this.user = user;
	}

	/**
	 * 管理员垃圾处理界面的事件监听器
	 */
	private void addActionListener() {

		textField_account.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				textField_user_name.setText("");
				// 根据账号从数据库获取用户名
				if (textField_account.getText().length() > 0) {
					User user = new User();
					user.setAccount(textField_account.getText());
					user = userController.userSelectByAccount(user);
					if (user != null) {
						textField_user_name.setText(user.getName());
					}
				}
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				textField_user_name.setText("");
				// 根据账号从数据库获取用户名
				if (textField_account.getText().length() > 0) {
					User user = new User();
					user.setAccount(textField_account.getText());
					user = userController.userSelectByAccount(user);
					if (user != null) {
						textField_user_name.setText(user.getName());
					}
				}
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				textField_user_name.setText("");
				// 根据账号从数据库获取用户名
				if (textField_account.getText().length() > 0) {
					User user = new User();
					user.setAccount(textField_account.getText());
					user = userController.userSelectByAccount(user);
					if (user != null) {
						textField_user_name.setText(user.getName());
					}
				}
			}
		});
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
//					System.out.println("金额输入为空！！！");
					JOptionPane.showMessageDialog(panel_1, "金额输入为空！！", "温馨提示",JOptionPane.WARNING_MESSAGE);
				} else {
					if (Integer.valueOf(money) < 1) {
//						System.out.println("请输入大于零的数！！！");
						JOptionPane.showMessageDialog(panel_1, "请输入大于零的数！！", "温馨提示",JOptionPane.WARNING_MESSAGE);
					} else {

						if (textField_user_name.getText().length() < 1) {
//							System.out.println("请输入正确的转账用户...");
							JOptionPane.showMessageDialog(panel_1, "请输入正确的转账用户！！", "温馨提示",JOptionPane.WARNING_MESSAGE);
						} else {
							if (textField_account.getText().equals(user.getAccount())) {
//								System.out.println("不能给自己转账....");
								JOptionPane.showMessageDialog(panel_1, "不能给自己转账！！", "温馨提示",JOptionPane.WARNING_MESSAGE);
							} else {
								user = userController.userSelectByAccount(user);
								if (Double.valueOf(user.getAvailableCredit()) < Double.valueOf(textField_add_storage_amount.getText())) {

									JOptionPane.showMessageDialog(panel_1, "可用额度不够！！", "温馨提示",JOptionPane.WARNING_MESSAGE);

								} else {

									// 显示输入对话框, 返回输入的内容
									String inputContent = JOptionPane.showInputDialog(
											panel_1,
											"输入你的密码:",
											""
									);
									if (user.getPassword().equals(inputContent)) {
										User toUser = new User();
										toUser.setAccount(textField_account.getText());
										toUser.setName(textField_user_name.getText());
										int update = userController.userTransfer(user, toUser, textField_add_storage_amount.getText());

										if (update == 1) {

											JOptionPane.showMessageDialog(panel_1, "转账成功！！", "温馨提示",JOptionPane.INFORMATION_MESSAGE);

										} else {
											JOptionPane.showMessageDialog(panel_1, "转账失败！！", "温馨提示",JOptionPane.WARNING_MESSAGE);
										}
									} else {
										JOptionPane.showMessageDialog(panel_1, "密码错误！！", "温馨提示",JOptionPane.WARNING_MESSAGE);
									}

								}
							}
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
		internalFrame = new JInternalFrame("信用转账界面", // title
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

		JLabel lblNewLabel = new JLabel("转账账户：");
		lblNewLabel.setBounds(14, 9, 100, 18);
		panel_1.add(lblNewLabel);

		// 账号
		textField_account = new JTextField();
		textField_account.setBounds(110, 6, 130, 24);
		textField_account.setColumns(10);
		panel_1.add(textField_account);


		JLabel lblNewLabel_7 = new JLabel("转账用户名：");
		lblNewLabel_7.setBounds(14, 59, 100, 18);
		panel_1.add(lblNewLabel_7);

		// 用户名
		textField_user_name = new JTextField();
		textField_user_name.setEditable(false);
		textField_user_name.setBounds(110, 56, 130, 24);
		textField_user_name.setColumns(10);
		panel_1.add(textField_user_name);


		JLabel lblNewLabel_5 = new JLabel("转账金额：");
		lblNewLabel_5.setBounds(504, 9, 200, 20);
		panel_1.add(lblNewLabel_5);

		// 账号充值输入框
		textField_add_storage_amount = new JTextField();
		textField_add_storage_amount.setBounds(610, 6, 130, 30);
		textField_add_storage_amount.setColumns(10);
		panel_1.add(textField_add_storage_amount);

		// 查询按钮
		btn_add_storage_amount = new JButton("转账");
		btn_add_storage_amount.setBounds(630, 50, 110, 30);
		btn_add_storage_amount.setIcon(new ImageIcon("./images/垃圾查询_1.png"));
		panel_1.add(btn_add_storage_amount);


		addActionListener();
		return internalFrame;

	}



}
