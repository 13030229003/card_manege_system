package com.han.view.login;

import com.han.controller.UserController;
import com.han.pojo.User;
import com.han.view.home.Home;
import com.han.view.imagepanel.HomePanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JFrame {

	private JPanel contentPanel;
	private JTextField textField_name;
	private JPasswordField textField_psw;

	private UserController userController = new UserController();

	JButton btn_login;
	JButton btn_register;
	JButton btn_forget;
	private JLabel lblNewLabel_2;
	private JComboBox comboBox;

	/**
	 * 构造方法，初始化登录窗体
	 */
	public Login() {
		init();
	}
	private void init() {
		setResizable(false);
		// "src//img//注册背景.jfif"
		//System.getProperty("user.dir")+"\\src"+"\\img\\注册背景.jfif"
		String path = "src//img//注册背景.jfif";
		contentPanel = new HomePanel(path);
		//设置标题图片System.getProperty("user.dir")+"\\src"+"\\img\\identify_2.png"
		setIconImage(Toolkit.getDefaultToolkit().getImage("./src//img//identify_2.png"));

		setTitle("信用卡管理系统");
		setBounds(100, 100, 450, 301);
		setLocationRelativeTo(null);// 将窗口显示在屏幕中央
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);

		JLabel lblNewLabel = new JLabel("账户:");
		lblNewLabel.setIcon(new ImageIcon("./images/userName.png"));
		lblNewLabel.setBounds(60, 39, 66, 18);

		JSeparator separator = new JSeparator();
		separator.setBounds(362, 100, 1, 2);

		textField_name = new JTextField();
		textField_name.setBounds(138, 39, 194, 24);
		textField_name.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("密码:");
		lblNewLabel_1.setBounds(60, 84, 66, 18);
		lblNewLabel_1.setIcon(new ImageIcon("./images/password.png"));

		btn_login = new JButton("登录");
		btn_login.setBounds(164, 168, 133, 27);
		btn_login.setIcon(new ImageIcon("./images/登录_1.png"));

		btn_register = new JButton("免费注册");
		btn_register.setBounds(14, 214, 133, 27);
		btn_register.setIcon(new ImageIcon("./images/注册_1.png"));

		btn_forget = new JButton("忘记密码");
		btn_forget.setBounds(285, 214, 133, 27);
		btn_forget.setIcon(new ImageIcon("./images/忘记密码_1.png"));
		contentPanel.setLayout(null);
		contentPanel.add(lblNewLabel);
		contentPanel.add(separator);
		contentPanel.add(textField_name);
		contentPanel.add(lblNewLabel_1);
		contentPanel.add(btn_login);
		contentPanel.add(btn_register);
		contentPanel.add(btn_forget);

		textField_psw = new JPasswordField();
		textField_psw.setBounds(138, 84, 194, 24);
		contentPanel.add(textField_psw);

		lblNewLabel_2 = new JLabel("\u8EAB\u4EFD\uFF1A");
		lblNewLabel_2.setBounds(60, 124, 66, 18);
		lblNewLabel_2.setIcon(new ImageIcon("./images/identify_2.png"));
		contentPanel.add(lblNewLabel_2);

		comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] { "普通用户", "管理员" }));
		comboBox.setSelectedIndex(0);
		comboBox.setBounds(138, 124, 194, 24);
		contentPanel.add(comboBox);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		addListener();
		setVisible(true);

	}

	/**
	 * 给按钮和文本框注册事件监听器
	 */
	private void addListener() {

		// TODO Auto-generated method stub
		btn_login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String account = textField_name.getText();
				String psw = new String(textField_psw.getPassword());
				String identify = comboBox.getSelectedItem().toString();
				if (identify == "管理员") {
//					System.out.println("管理员");
					if (account.length() == 0 || psw.length() == 0) {
						JOptionPane.showMessageDialog(contentPanel, "请输入帐号或密码！！", "温馨提示",JOptionPane.WARNING_MESSAGE);
					} else {
						User login = userController.login(new User(account, psw, "1"));

						if (login != null) {
							if ("0".equals(login.getStatus())) {
								JOptionPane.showMessageDialog(contentPanel, "用户已被冻结！！", "温馨提示",JOptionPane.WARNING_MESSAGE);
							} else {
								dispose();
								Home home = new Home(login);
							}

						}else {
							JOptionPane.showMessageDialog(contentPanel, "密码输入错误！！", "温馨提示",JOptionPane.WARNING_MESSAGE);
							textField_psw.setText("");
						}
					}
				} else {
//					System.out.println("普通用户");
					if (account.length() == 0 || psw.length() == 0) {
						JOptionPane.showMessageDialog(contentPanel, "请输入帐号或密码！！", "温馨提示",JOptionPane.WARNING_MESSAGE);
					} else {
						User login = userController.login(new User(account, psw, "0"));
						if (login != null) {
							if ("0".equals(login.getStatus())) {
								JOptionPane.showMessageDialog(contentPanel, "用户已被冻结！！", "温馨提示",JOptionPane.WARNING_MESSAGE);
							} else {
								dispose();
								Home home = new Home(login);
							}
						}else {
							JOptionPane.showMessageDialog(contentPanel, "密码输入错误！！", "温馨提示",JOptionPane.WARNING_MESSAGE);
							textField_psw.setText("");
						}
					}

				}
			}
		});
		btn_register.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				register reg = new register();
				dispose();
			}
		});
		btn_forget.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		textField_name.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField_psw.requestFocus();// 跳转到密码输入框
			}
		});
		textField_psw.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btn_login.doClick();// 回车就点击了登录按钮
			}
		});

	}

}
