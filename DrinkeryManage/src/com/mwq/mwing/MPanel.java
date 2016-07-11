package com.mwq.mwing;

import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JPanel;


public class MPanel extends JPanel {

	private ImageIcon imageIcon;// ����һ��ͼƬ����

	public MPanel(URL imgUrl) {
		super();// �̳и���Ĺ��췽��
		setLayout(new GridBagLayout());// �����ֹ������޸�Ϊ�����鲼��
		imageIcon = new ImageIcon(imgUrl);// ���ݴ����URL����ImageIcon����
		setSize(imageIcon.getIconWidth(), imageIcon.getIconHeight());// ���������ͼƬ�ȴ�
	}

	protected void paintComponent(Graphics g) {// ��дJPanel���paintComponent()����
		super.paintComponent(g);// ����JPanel���paintComponent()����
		Image image = imageIcon.getImage();// ͨ��ImageIcon������Image����
		g.drawImage(image, 0, 0, null);// ����Image���󣬼���ͼƬ���Ƶ������
	}

}
