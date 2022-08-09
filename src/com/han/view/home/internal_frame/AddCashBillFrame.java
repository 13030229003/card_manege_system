package com.han.view.home.internal_frame;

import com.han.controller.BillController;
import com.han.pojo.Bill;
import com.han.pojo.User;
import com.han.view.imagepanel.HomePanel;
import com.han.view.imagepanel.MyTable;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddCashBillFrame {

	private User user;

	private static JTextField textField_add_bill;
	private static JPanel panel;
	private static JPanel panel_1;
	private static JButton btn_add_bill;
	private static JTable table;
	static DefaultTableModel tabModel;
	private static JScrollPane scrollPane;

	private BillController billController = new BillController();

	public AddCashBillFrame() {

	}

	public AddCashBillFrame(User user) {
		this.user = user;
	}

	/**
	 * 录入界面按钮注册事件监听器
	 */
	private void addActionListener() {
		btn_add_bill.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {


				if (textField_add_bill.getText().length() < 1) {
					JOptionPane.showMessageDialog(panel, "输入为空！！", "温馨提示",JOptionPane.WARNING_MESSAGE);
				} else {
					int n = JOptionPane.showConfirmDialog(panel, "确定取现吗?", "温馨提示",JOptionPane.YES_NO_OPTION);//返回的是按钮的index  i=0或者1
					if(n==0) {
						Bill bill = new Bill();
						bill.setUserName(user.getName());
						bill.setAccount(user.getAccount());
						bill.setAmount(textField_add_bill.getText());
						bill.setType("3");
						bill = billController.billInsert(bill);

						if (bill != null) {
							bill.setStatus("1");
							Object[] columnNames = { "账单号", "用户名", "账号", "金额", "账单类型", "状态" };
//							Object rowData[][] = { { "11","11","11","11","11","11" } };
							Object rowData[][] = { { bill.getId(), bill.getUserName(), bill.getAccount(), bill.getAmount(), bill.getType(), bill.getStatus() }};
							tabModel = new DefaultTableModel(rowData, columnNames);
							table.setModel(tabModel);
							table.setEnabled(true);

							textField_add_bill.setText("");

							JOptionPane.showMessageDialog(panel, "取现成功！！", "温馨提示",JOptionPane.INFORMATION_MESSAGE);
						} else {
							JOptionPane.showMessageDialog(panel, "取现失败！！", "温馨提示",JOptionPane.WARNING_MESSAGE);
						}
					}
				}
			}
		});
	}

	/**
	 * 初始化录入界面
	 *
	 */
	public JInternalFrame init() {
		// 创建一个内部窗口
		JInternalFrame internalFrame = new JInternalFrame("取现界面", // title
				false, // resizable改变大小
				true, // closable
				false, // maximizable最大最小化
				true // iconifiable
		);

		// 设置窗口的宽高
		internalFrame.setSize(1000, 500);
		// 设置窗口的显示位置
		internalFrame.setLocation(50, 50);
		internalFrame.getContentPane().setLayout(null);

		String path = System.getProperty("user.dir")+"\\src"+"\\img\\注册背景.jfif";
		panel = new HomePanel(path);
//		panel = new JPanel();
		panel.setBounds(0, 0, 999, 157);
		// 表头（列名）
		Object[] columnNames = { "账单号", "用户名", "账号", "金额", "账单类型", "状态" };
		Object rowData[][] = { { " ", " ", " ", " ", " ", " " } };
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
		table.setPreferredScrollableViewportSize(new Dimension(999, 152));
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
//		panel.add(table);
		internalFrame.getContentPane().add(panel);

		String path_1 = System.getProperty("user.dir")+"\\src"+"\\img\\注册背景.jfif";
		panel_1 = new HomePanel(path_1);
		panel_1.setBounds(0, 160, 999, 304);
		internalFrame.getContentPane().add(panel_1);
		panel_1.setLayout(null);

		btn_add_bill = new JButton("取现");
		btn_add_bill.setBounds(356, 152, 150, 27);
		btn_add_bill.setIcon(new ImageIcon("./images/录入_1.png"));
		panel_1.add(btn_add_bill);

		JLabel lblNewLabel_3 = new JLabel("取现金额：");
		lblNewLabel_3.setBounds(48, 156, 90, 18);
		panel_1.add(lblNewLabel_3);

		textField_add_bill = new JTextField();
		textField_add_bill.setBounds(127, 153, 194, 24);
		panel_1.add(textField_add_bill);
		textField_add_bill.setColumns(10);

		addActionListener();
		return internalFrame;

	}
}
