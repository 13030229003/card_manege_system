package com.han.view.login;

import com.han.controller.UserController;
import com.han.pojo.User;
import com.han.service.UserService;
import com.han.view.imagepanel.HomePanel;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;

public class register extends JFrame {
	private JPanel contentPanel;
	private JTextField textField_name;
	private JTextField textField_account;
	private JTextField textField_psw;
	private JButton btnNewButton_register;
	private JButton btnNewButton_return;

	private UserController userController = new UserController();

	public register() {
		setResizable(false);
		String path = "src//img//注册背景.jfif";
		contentPanel = new HomePanel(path);
		//设置标题图片
		setIconImage(Toolkit.getDefaultToolkit().getImage("./src//img//identify_2.png"));
		getContentPane().add(contentPanel);
		contentPanel.setLayout(null);
		setTitle("用户注册");
		setSize(450, 301);
		setLocationRelativeTo(null);// 将窗口显示在屏幕中央


		JLabel lblNewLabel = new JLabel("用户名");
		lblNewLabel.setBounds(70, 55, 75, 18);
		lblNewLabel.setIcon(new ImageIcon("./images/userName.png"));
		contentPanel.add(lblNewLabel);
//
		textField_name = new JTextField();
		textField_name.setBounds(153, 55, 169, 24);
		contentPanel.add(textField_name);
		textField_name.setColumns(10);
//
		JLabel lblNewLabel_1 = new JLabel("账户：");
		lblNewLabel_1.setBounds(70, 93, 75, 18);
		lblNewLabel_1.setIcon(new ImageIcon("./images/userName.png"));
		contentPanel.add(lblNewLabel_1);
//
		textField_account = new JTextField();
		textField_account.setBounds(153, 93, 169, 24);
		contentPanel.add(textField_account);
		textField_account.setColumns(10);

		JLabel lblNewLabel_2 = new JLabel("密码");
		lblNewLabel_2.setBounds(70, 130, 75, 18);
		lblNewLabel_2.setIcon(new ImageIcon("./images/password.png"));
		contentPanel.add(lblNewLabel_2);
//
		textField_psw = new JTextField();
		textField_psw.setBounds(153, 130, 169, 24);
		contentPanel.add(textField_psw);
		textField_psw.setColumns(10);
//
		btnNewButton_register = new JButton("注册");
		btnNewButton_register.setIcon(new ImageIcon("./images/注册_1.png"));
//
		btnNewButton_register.setBounds(179, 170, 113, 27);
		contentPanel.add(btnNewButton_register);
//
		btnNewButton_return = new JButton("返回登录界面");
		btnNewButton_return.setIcon(new ImageIcon("./images/返回_2.png"));
//
		btnNewButton_return.setBounds(153, 214, 169, 27);
		contentPanel.add(btnNewButton_return);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setVisible(true);
		addListener();
	}
	private void addListener() {
		btnNewButton_register.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = textField_name.getText();
				String psw = textField_psw.getText();
				String account = textField_account.getText();
				if (name.length() == 0 || psw.length() == 0) {
					JOptionPane.showMessageDialog(contentPanel, "请输入帐号或密码！！", "温馨提示",JOptionPane.WARNING_MESSAGE);
				} else {
					User user = new User();
					user.setName(name);
					user.setPassword(psw);
					user.setAccount(account);
					User userSelectByAccount = userController.userSelectByAccount(user);

					if (userSelectByAccount != null) {
						JOptionPane.showMessageDialog(contentPanel, "用户名已存在！！", "温馨提示",JOptionPane.WARNING_MESSAGE);
						textField_name.setText("");
					}else {

						int insert = userController.userInsert(user);
						if (insert > 0) {
							JOptionPane.showMessageDialog(contentPanel, "注册成功！！", "温馨提示",JOptionPane.INFORMATION_MESSAGE);
							textField_name.setText("");
							textField_account.setText("");
							textField_psw.setText("");
							dispose();
							new Login();
						} else {
							JOptionPane.showMessageDialog(contentPanel, "注册失败！！", "温馨提示",JOptionPane.WARNING_MESSAGE);
							textField_name.setText("");
						}
					}
				}

			}
		});
		btnNewButton_return.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				new Login();
			}
		});
		textField_psw.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnNewButton_register.doClick();// 回车就点击了登录按钮
			}
		});
	}
}
