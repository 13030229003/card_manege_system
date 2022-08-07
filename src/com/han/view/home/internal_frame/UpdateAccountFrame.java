package com.han.view.home.internal_frame;

import com.han.controller.UserController;
import com.han.pojo.User;
import com.han.view.imagepanel.HomePanel;
import com.han.view.imagepanel.MyTable;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.ActionEvent;

public class UpdateAccountFrame {

	private UserController userController = new UserController();

	private static JTextField textField_name;
	private static JTextField textField_available;
	private static JTable table;
	private static JPanel panel;
	private JPanel panel_1;
	private static JButton btn_delete;
	private static JButton btn_all;
	private static JButton btn_update;
	private static JComboBox comboBox;
	private static JScrollPane scrollPane;

	static int selectedRow;
	static String find_name = "", find_account = "", find_available = "", find_status = "",find_storage = "";
	static DefaultTableModel tabModel;
	private static JTextField textField_account;
	private static JTextField textField_find;
	private static JComboBox comboBox_query;

	/**
	 * 管理员垃圾处理界面的事件监听器
	 */
	private void addactionListener() {
		table.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				if (e.getButton() == e.BUTTON1) {
					selectedRow = table.getSelectedRow(); // 获行的索引
					// 从表格中拉出来数据
					find_name = tabModel.getValueAt(selectedRow, 0).toString();
					find_account = tabModel.getValueAt(selectedRow, 1).toString();
					find_available = tabModel.getValueAt(selectedRow, 3).toString();
					find_storage = tabModel.getValueAt(selectedRow, 6).toString();
					find_status = tabModel.getValueAt(selectedRow, 7).toString();

					textField_name.setText(find_name);
					textField_account.setText(find_account);
					textField_available.setText(find_available);
					comboBox.setSelectedIndex(Integer.valueOf(find_status));
				}
			}
		});
		btn_update.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String new_cla = comboBox.getSelectedItem().toString();
				if(new_cla.equals("----")) {
					JOptionPane.showMessageDialog(panel, "用户状态选择错误！！", "温馨提示",JOptionPane.WARNING_MESSAGE);
				}else {

//					System.out.println(find_name);
//					System.out.println(find_account);
//					System.out.println(textField_available.getText().length());
//					System.out.println(comboBox.getSelectedIndex() == 0 ? "冻结":"开通");

					if (find_name.length() == 0 || find_account.length() == 0 || textField_available.getText().length() == 0) {
						JOptionPane.showMessageDialog(panel, "用户可用额度修改错误！！", "温馨提示",JOptionPane.WARNING_MESSAGE);
					}
					else {
						int n = JOptionPane.showConfirmDialog(panel, "确定修改帐号信息吗?", "温馨提示",JOptionPane.YES_NO_OPTION);//返回的是按钮的index  i=0或者1
						if(n==0) {

							// 更新用户操作
							User user = new User();
							user.setAccount(find_account);
							user.setAvailableCredit(textField_available.getText());
							user.setStorageAmount(find_storage);
							user.setStatus(String.valueOf(comboBox.getSelectedIndex()));
							System.out.println(user);
							int i = userController.userUpdateByAccount(user);

							if (i > 0) {
								Object[] columnNames = { "用户名", "账号", "总信用额", "可用信用额", "可取现用额", "欠款金额", "预存金额", "状态" };
								Object rowData[][] = userController.userList();
								tabModel = new DefaultTableModel(rowData, columnNames);
								table.setModel(tabModel);
								table.setEnabled(true);

							} else {
								JOptionPane.showMessageDialog(panel, "用户修改失败！！", "温馨提示",JOptionPane.WARNING_MESSAGE);
							}



//							dealWith_data.update(new_cla, all);
//							// 表头（列名）
//							Object[] columnNames = { "名称", "数量", "类别", "备注" };
//							Object rowData[][] = dealWith_data.select();
//							tabModel = new DefaultTableModel(rowData, columnNames);
//							table.setModel(tabModel);
//							table.setEnabled(true);
//							textField_name.setText("");
//							textField_sum.setText("");
//							textField_cla.setText("");
//							textField_t.setText("");
						}
					}
				}

			}
		});
		btn_delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
//				String str_name = textField_name.getText();
//				String str_sum = textField_sum.getText();
//				String str_cla = textField_cla.getText();
//				String str_t = textField_t.getText();
//
//				if (str_name.length() == 0 || str_sum.length() == 0 || str_cla.length() == 0) {
//					JOptionPane.showMessageDialog(panel, "请在表格选择需要删除的垃圾！！", "温馨提示",JOptionPane.WARNING_MESSAGE);
//				} else {
//					int n = JOptionPane.showConfirmDialog(panel, "确定删除垃圾?", "温馨提示",JOptionPane.YES_NO_OPTION);//返回的是按钮的index  i=0或者1
//					if(n==0) {
//						all_Rubbish all = new all_Rubbish(str_name, str_sum, str_cla, str_t);
//						dealWith_data.delete(all);
//						// 表头（列名）
//						Object[] columnNames = { "名称", "数量", "类别", "备注" };
//						Object rowData[][] = dealWith_data.select();
//						tabModel = new DefaultTableModel(rowData, columnNames);
//						table.setModel(tabModel);
//						table.setEnabled(true);
//						textField_name.setText("");
//						textField_sum.setText("");
//						textField_cla.setText("");
//						textField_t.setText("");
//					}
//				}
			}
		});
		btn_all.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Object[] columnNames = { "用户名", "账号", "总信用额", "可用信用额", "可取现用额", "欠款金额", "预存金额", "状态" };
				Object rowData[][] = userController.userList();
				tabModel = new DefaultTableModel(rowData, columnNames);
				table.setModel(tabModel);
				table.setEnabled(true);
//				comboBox.setSelectedIndex(0);
				// 表头（列名）
//				Object[] columnNames = { "名称", "数量", "类别", "备注" };
//				String str_cla = comboBox_query.getSelectedItem().toString();
//				Object rowData[][] = dealWith_data.select(str_cla);
//				tabModel = new DefaultTableModel(rowData, columnNames);

			}
		});
		comboBox.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				if (e.getStateChange() == ItemEvent.SELECTED) {
					// 选择的下拉框选项
//					textField_cla.setText(e.getItem().toString());
					System.out.println(e.getItem().toString());

				}
			}
		});

	}

	/**
	 * 初始化管理员的垃圾处理界面
	 *
	 */
	public JInternalFrame init() {
		// 创建一个内部窗口
		JInternalFrame internalFrame = new JInternalFrame("账户资料修改", // title
				false, // resizable
				true, // closable
				false, // maximizable
				true // iconifiable
		);

		// 设置窗口的宽高
		internalFrame.setSize(1000, 550);
		// 设置窗口的显示位置
		internalFrame.setLocation(50, 50);
		internalFrame.getContentPane().setLayout(null);

		String path = System.getProperty("user.dir")+"\\src"+"\\img\\注册背景.jfif";
		panel = new HomePanel(path);
		panel.setBounds(0, 0, 999, 257);
		// 表头（列名）
		Object[] columnNames = { "用户名", "账号", "总信用额", "可用信用额", "可取现用额", "欠款金额", "预存金额", "状态" };
		Object rowData[][] = { { " ", " ", " ", " ", " ", " ", " ", " " } };
		table = new MyTable(rowData, columnNames);
		// 设置表格内容颜色
		table.setForeground(Color.BLACK); // 字体颜色
		table.setFont(new Font(null, Font.PLAIN, 14)); // 字体样式
		table.setSelectionForeground(Color.DARK_GRAY); // 选中后字体颜色
		table.setSelectionBackground(Color.green); // 选中后字体背景
		table.setGridColor(Color.GRAY); // 网格颜色
		// 设置表头
		table.getTableHeader().setFont(new Font(null, Font.BOLD, 14)); // 设置表头名称字体样式
		table.getTableHeader().setForeground(Color.RED); // 设置表头名称字体颜色
		table.getTableHeader().setResizingAllowed(false); // 设置不允许手动改变列宽
		table.getTableHeader().setReorderingAllowed(false); // 设置不允许拖动重新排序各列
		// 设置行高
		table.setRowHeight(20);
		// 第一列列宽设置为40
		table.getColumnModel().getColumn(0).setPreferredWidth(20);
		panel.setLayout(new BorderLayout(0, 0));
		// 设置滚动面板视口大小（超过该大小的行数据，需要拖动滚动条才能看到）
		table.setPreferredScrollableViewportSize(new Dimension(999, 252));
		/*
		 * 将表格设置为透明，表格同样包括表格本身和其中的内容项
		 * 仅仅将表格本身设置为透明也没有用，应该将其中的内容项也设置为透明
		 * 内容项的设置是通过设置渲染器的透明来实现
		 */
		table.setOpaque(false);
		DefaultTableCellRenderer render = new DefaultTableCellRenderer();
		render.setOpaque(false); //将渲染器设置为透明
		//将这个渲染器设置到fileTable里。这个设置在没有另外专门对column设置的情况下有效
		//若你对某个column特殊指定了渲染器，则对于这个column，它将不调用render渲染器
		//因此为了保证透明，如果你对column额外指定了渲染器，那么在额外的渲染器里也应该设置透明
		table.setDefaultRenderer(Object.class,render);

		// 把 表格 放到 滚动面板 中（表头将自动添加到滚动面板顶部）
		scrollPane = new JScrollPane(table);
		scrollPane.getViewport().setOpaque(false);//将JScrollPane设置为透明
		scrollPane.setOpaque(false);//将中间的viewport设置为透明
		// 添加 滚动面板 到 内容面板
		panel.add(scrollPane);

		internalFrame.getContentPane().add(panel);

		path = System.getProperty("user.dir")+"\\src"+"\\img\\注册背景.jfif";
		panel_1 = new HomePanel(path);
		panel_1.setBounds(0, 260, 999, 254);
		internalFrame.getContentPane().add(panel_1);
		panel_1.setLayout(null);

		JLabel lblNewLabel = new JLabel("用户名：");
		lblNewLabel.setBounds(14, 9, 90, 18);
		panel_1.add(lblNewLabel);

		// 用户名文本框
		textField_name = new JTextField();
		textField_name.setEditable(false);
		textField_name.setBounds(90, 6, 186, 24);
		textField_name.setColumns(10);
		panel_1.add(textField_name);


		// 查询文本框
		textField_find = new JTextField();
//		textField_t.setEditable(false);
		textField_find.setBounds(600, 6, 200, 30);
		panel_1.add(textField_find);
		textField_find.setColumns(10);


		// 查询按钮
		btn_all = new JButton("查询");
		btn_all.setBounds(600, 50, 111, 27);
		btn_all.setIcon(new ImageIcon("./images/垃圾查询_1.png"));
		panel_1.add(btn_all);





		JLabel lblNewLabel_1 = new JLabel("账  户：");
		lblNewLabel_1.setBounds(14, 58, 90, 18);
		panel_1.add(lblNewLabel_1);

		textField_account = new JTextField();
		textField_account.setEditable(false);
		textField_account.setBounds(90, 55, 186, 24);
		panel_1.add(textField_account);
		textField_account.setColumns(10);

		JLabel lblNewLabel_2 = new JLabel("可用额度：");
		lblNewLabel_2.setBounds(14, 107, 90, 18);
		panel_1.add(lblNewLabel_2);

		textField_available = new JTextField();
//		textField_sum.setEditable(false);availableCredit
		textField_available.setBounds(90, 104, 186, 24);
		textField_available.setColumns(10);
		panel_1.add(textField_available);

		btn_delete = new JButton("删除用户");
		btn_delete.setBounds(300, 200, 130, 27);
		btn_delete.setIcon(new ImageIcon("./images/垃圾删除_2.png"));
		panel_1.add(btn_delete);

		btn_update = new JButton("修改用户");
		btn_update.setBounds(146, 200, 130, 27); //336, 149, 111, 27
		btn_update.setIcon(new ImageIcon("./images/垃圾更新_1.png"));
		panel_1.add(btn_update);


		JLabel lblNewLabel_3 = new JLabel("用户状态：");
		lblNewLabel_3.setBounds(14, 153, 90, 18);
		panel_1.add(lblNewLabel_3);

		comboBox = new JComboBox();
		comboBox.setBounds(90, 150, 186, 24);
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"冻结",  "开通", "----" }));
		// 设置默认选中的条目
		comboBox.setSelectedIndex(2);
		panel_1.add(comboBox);


//		comboBox_query = new JComboBox();
//		comboBox_query.setModel(new DefaultComboBoxModel(
//				new String[] { "全部垃圾", "干垃圾", "湿垃圾",
//						"有害垃圾", "厨余垃圾", "可回收垃圾" }));
//		comboBox_query.setSelectedIndex(0);
//		comboBox_query.setBounds(593, 55, 146, 24);
//		panel_1.add(comboBox_query);
		addactionListener();
		return internalFrame;

	}
}