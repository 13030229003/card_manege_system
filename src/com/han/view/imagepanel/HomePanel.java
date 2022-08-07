package com.han.view.imagepanel;

import javax.swing.*;
import java.awt.*;

//设置主页背景图片的JPnel类
public class HomePanel extends JPanel {
	ImageIcon icon;
	Image img;
	public HomePanel(String path) {
		icon=new ImageIcon(path);
		img=icon.getImage();
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		//下面这行是为了背景图片可以跟随窗口自行调整大小，可以自己设置成固定大小
		g.drawImage(img, 0, 0,this.getWidth(), this.getHeight(), this);
	}

}
