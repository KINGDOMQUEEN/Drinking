package com.mwq.frame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.net.URL;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import com.mwq.dao.Dao;
import com.mwq.mwing.MPanel;


public class LandFrame extends JFrame {

	private JPasswordField passwordField;// �����

	private JComboBox usernameComboBox;// �û��������˵�

	public static void main(String args[]) {
		try {
			LandFrame frame = new LandFrame();
			frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public LandFrame() {
		// �������ô��ڵ������Ϣ
		super();// ���ø���Ĺ��췽��
		setTitle(" T �Ƽ�");// ���ô��ڵı���
		setResizable(false);// ���ô��ڲ����Ըı��С
		setAlwaysOnTop(true);// ���ô���������ǰ��
		setBounds(100, 100, 428, 292);// ���ô��ڵĴ�С
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// ���õ��رմ���ʱִ�еĶ���

		// ���潫����һ����������ӵ����ڵ�������
		final MPanel panel = new MPanel(this.getClass().getResource(
				"/img/land_background.jpg"));// ����һ��������
		panel.setLayout(new GridBagLayout());// �������Ĳ��ֹ�����Ϊ�����鲼��
		getContentPane().add(panel, BorderLayout.CENTER);// �������ӵ�������

		final JLabel topLabel = new JLabel();
		topLabel.setPreferredSize(new Dimension(0, 126));
		final GridBagConstraints gridBagConstraints_5 = new GridBagConstraints();
		gridBagConstraints_5.gridx = 0;
		gridBagConstraints_5.gridy = 0;
		panel.add(topLabel, gridBagConstraints_5);

		final JLabel leftLabel = new JLabel();
		leftLabel.setPreferredSize(new Dimension(140, 0));
		final GridBagConstraints gridBagConstraints_3 = new GridBagConstraints();
		gridBagConstraints_3.gridy = 1;
		gridBagConstraints_3.gridx = 0;
		panel.add(leftLabel, gridBagConstraints_3);

		final JLabel rightLabel = new JLabel();
		rightLabel.setPreferredSize(new Dimension(55, 0));
		final GridBagConstraints gridBagConstraints_2 = new GridBagConstraints();
		gridBagConstraints_2.gridy = 1;
		gridBagConstraints_2.gridx = 1;
		panel.add(rightLabel, gridBagConstraints_2);

		// �����������û��������˵�
		usernameComboBox = new JComboBox();// �����û��������˵��������
		usernameComboBox.setMaximumRowCount(5);// ���������˵�������ʾ��ѡ����
		usernameComboBox.addItem("��ѡ��");// Ϊ�����˵������ʾ��
		usernameComboBox
				.addActionListener(new UsernameComboBoxActionListener());// Ϊ�����˵�����¼�������
		final GridBagConstraints gridBagConstraints = new GridBagConstraints();// ���������鲼�ֹ���������
		gridBagConstraints.anchor = GridBagConstraints.WEST;// ����Ϊ�������ʾ
		gridBagConstraints.gridy = 1;// ����������Ϊ1
		gridBagConstraints.gridx = 2;// ����������Ϊ2
		panel.add(usernameComboBox, gridBagConstraints);// �������ָ���Ĳ��ֹ�������ӵ������

		// ���������������
		passwordField = new JPasswordField();// ����������������
		passwordField.setColumns(20);// ������������ʾ���ַ���
		passwordField.setText("      ");// ���������Ĭ����ʾ6���ո�
		passwordField.addFocusListener(new PasswordFieldFocusListener());// Ϊ�������ӽ��������
		final GridBagConstraints gridBagConstraints_1 = new GridBagConstraints();// ���������鲼�ֹ���������
		gridBagConstraints_1.insets = new Insets(5, 0, 0, 0);// ��������ⲿ�Ϸ��������Ϊ5����
		gridBagConstraints_1.anchor = GridBagConstraints.WEST;// ����Ϊ�������ʾ
		gridBagConstraints_1.gridy = 2;// ����������Ϊ2
		gridBagConstraints_1.gridx = 2;// ����������Ϊ2
		panel.add(passwordField, gridBagConstraints_1);// �������ָ���Ĳ��ֹ�������ӵ������

		// ����������һ���������������ť�����
		final JPanel buttonPanel = new JPanel();// ����һ��������Ӱ�ť�����
		buttonPanel.setOpaque(false);// �������ı���Ϊ͸��
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));// ����������ˮƽ�䲼��
		final GridBagConstraints gridBagConstraints_4 = new GridBagConstraints();// ���������鲼�ֹ���������
		gridBagConstraints_4.insets = new Insets(10, 0, 0, 0);// ��������ⲿ�Ϸ��������Ϊ10����
		gridBagConstraints_4.gridwidth = 2;// ������ռ����
		gridBagConstraints_4.gridy = 3;// ����������Ϊ3
		gridBagConstraints_4.gridx = 1;// ����������Ϊ1
		panel.add(buttonPanel, gridBagConstraints_4);// �������ָ���Ĳ��ֹ�������ӵ������

		// ����������һ����¼��ť����������ӵ�������Ӱ�ť�������
		final JButton landButton = new JButton();// ������¼��ť�������
		landButton.setMargin(new Insets(0, 0, 0, 0));// ���ð�ť�߿�ͱ�ǩ֮��ļ��
		landButton.setContentAreaFilled(false);// ���ò����ư�ť����������
		landButton.setBorderPainted(false);// ���ò����ư�ť�ı߿�
		URL landUrl = this.getClass().getResource("/img/land_submit.png");// ���Ĭ������µ�¼��ť��ʾͼƬ��URL
		landButton.setIcon(new ImageIcon(landUrl));// ����Ĭ������µ�¼��ť��ʾ��ͼƬ
		URL landOverUrl = this.getClass().getResource(
				"/img/land_submit_over.png");// ��õ���꾭����¼��ťʱ��ʾͼƬ��URL
		landButton.setRolloverIcon(new ImageIcon(landOverUrl));// ���õ���꾭����¼��ťʱ��ʾ��ͼƬ
		URL landPressedUrl = this.getClass().getResource(
				"/img/land_submit_pressed.png");// ��õ���¼��ť������ʱ��ʾͼƬ��URL
		landButton.setPressedIcon(new ImageIcon(landPressedUrl));// ���õ���¼��ť������ʱ��ʾ��ͼƬ
		landButton.addActionListener(new LandButtonActionListener());// Ϊ��¼��ť����¼�������
		buttonPanel.add(landButton);// ����¼��ť��ӵ�������Ӱ�ť�������

		final JButton resetButton = new JButton();
		resetButton.setMargin(new Insets(0, 0, 0, 0));
		resetButton.setContentAreaFilled(false);
		resetButton.setBorderPainted(false);
		URL resetUrl = this.getClass().getResource("/img/land_reset.png");
		resetButton.setIcon(new ImageIcon(resetUrl));
		URL resetOverUrl = this.getClass().getResource(
				"/img/land_reset_over.png");
		resetButton.setRolloverIcon(new ImageIcon(resetOverUrl));
		URL resetPressedUrl = this.getClass().getResource(
				"/img/land_reset_pressed.png");
		resetButton.setPressedIcon(new ImageIcon(resetPressedUrl));
		resetButton.addActionListener(new ResetButtonActionListener());
		buttonPanel.add(resetButton);

		final JButton exitButton = new JButton();
		exitButton.setMargin(new Insets(0, 0, 0, 0));
		exitButton.setContentAreaFilled(false);
		exitButton.setBorderPainted(false);
		URL exitUrl = this.getClass().getResource("/img/land_exit.png");
		exitButton.setIcon(new ImageIcon(exitUrl));
		URL exitOverUrl = this.getClass()
				.getResource("/img/land_exit_over.png");
		exitButton.setRolloverIcon(new ImageIcon(exitOverUrl));
		URL exitPressedUrl = this.getClass().getResource(
				"/img/land_exit_pressed.png");
		exitButton.setPressedIcon(new ImageIcon(exitPressedUrl));
		exitButton.addActionListener(new ExitButtonActionListener());
		buttonPanel.add(exitButton);
		//

		// ��ʼ���û��������˵�
		Vector userNameV = Dao.getInstance().sUserNameOfNotFreeze();
		if (userNameV.size() == 0) {
			usernameComboBox.addItem("TSoft");
		} else {
			for (int i = 0; i < userNameV.size(); i++) {
				usernameComboBox.addItem(userNameV.get(i));
			}
		}

	}

	class UsernameComboBoxActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String userName = (String) usernameComboBox.getSelectedItem();
			if (userName.equals("TSoft"))
				passwordField.setText("111");
		}
	}

	class PasswordFieldFocusListener implements FocusListener {
		public void focusGained(FocusEvent e) {
			passwordField.setText("");
		}

		public void focusLost(FocusEvent e) {
			char[] passwords = passwordField.getPassword();
			String password = turnCharsToString(passwords);
			if (password.length() == 0) {
				passwordField.setText("      ");
			}
		}
	}

	class ExitButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			System.exit(0);
		}
	}

	class ResetButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			usernameComboBox.setSelectedIndex(0);
			passwordField.setText("      ");
		}
	}

	class LandButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String username = usernameComboBox.getSelectedItem().toString();// ��õ�¼�û�������
			if (username.equals("��ѡ��")) {// �鿴�Ƿ�ѡ���˵�¼�û�
				JOptionPane.showMessageDialog(null, "��ѡ���¼�û���", "������ʾ",
						JOptionPane.INFORMATION_MESSAGE);// ������ʾ
				resetUsernameAndPassword();// �ָ���¼�û��͵�¼����
			}
			char[] passwords = passwordField.getPassword();// ��õ�¼�û�������
			String inputPassword = turnCharsToString(passwords);// �������char������ת�����ַ���
			if (username.equals("TSoft")) {// �鿴�Ƿ�ΪĬ���û���¼
				if (inputPassword.equals("111")) {// �鿴�����Ƿ�ΪĬ������
					land(null);// ��¼�ɹ�
					String infos[] = { "�����̵������û�������ť����û���",
							"����û�����Ҫ���µ�¼����ϵͳ��������ʹ�ã�" };// ��֯��ʾ��Ϣ
					JOptionPane.showMessageDialog(null, infos, "������ʾ",
							JOptionPane.INFORMATION_MESSAGE);// ������ʾ
				} else {// �������
					JOptionPane.showMessageDialog(null,
							"Ĭ���û���TSoft���ĵ�¼����Ϊ��111����", "������ʾ",
							JOptionPane.INFORMATION_MESSAGE);// ������ʾ
					passwordField.setText("111");// ����������ΪĬ������
				}
			} else {
				if (inputPassword.length() == 0) {// �û�δ�����¼����
					JOptionPane.showMessageDialog(null, "�������¼���룡", "������ʾ",
							JOptionPane.INFORMATION_MESSAGE);// ������ʾ
					resetUsernameAndPassword();// �ָ���¼�û��͵�¼����
				}
				Vector user = Dao.getInstance().sUserByName(username);// ��ѯ��¼�û�
				String password = user.get(5).toString();// ��õ�¼�û�������
				if (inputPassword.equals(password)) {// �鿴��¼�����Ƿ���ȷ
					land(user);// ��¼�ɹ�
				} else {// ��¼�������
					JOptionPane.showMessageDialog(null, "��¼���������ȷ�Ϻ����µ�¼��",
							"������ʾ", JOptionPane.INFORMATION_MESSAGE);// ������ʾ
					resetUsernameAndPassword();// �ָ���¼�û��͵�¼����
				}
			}
		}

		private void resetUsernameAndPassword() {// �ָ���¼�û��͵�¼����
			usernameComboBox.setSelectedIndex(0);// �ָ�ѡ�еĵ�¼�û�Ϊ����ѡ����
			passwordField.setText("      ");// �ָ�������Ĭ��ֵΪ6���ո�
			return;// ֱ�ӷ���
		}

		private void land(Vector user) {// ��¼�ɹ�
			TipWizardFrame tipWizard = new TipWizardFrame(user);// �������������
			tipWizard.setVisible(true);// ����������ɼ�
			setVisible(false);// ���õ�¼���ڲ��ɼ�
		}

	}

	private String turnCharsToString(char[] chars) {
		StringBuffer strBuf = new StringBuffer();
		for (int i = 0; i < chars.length; i++) {
			strBuf.append(chars[i]);
		}
		return strBuf.toString().trim();
	}
}
