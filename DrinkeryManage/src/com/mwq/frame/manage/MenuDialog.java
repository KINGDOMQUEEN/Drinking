package com.mwq.frame.manage;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.reflect.Field;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import com.mwq.dao.Dao;
import com.mwq.dao.JDBC;
import com.mwq.mwing.MTable;
import com.mwq.tool.Today;
import com.mwq.tool.Validate;


public class MenuDialog extends JDialog {

	private JTextField numTextField;

	private JTextField nameTextField;

	private JTextField unitTextField;

	private JTextField codeTextField;

	private JComboBox sortComboBox;

	private JTextField unitPriceTextField;

	private JTable table;

	private final Vector tableColumnV = new Vector();

	private final DefaultTableModel tableModel = new DefaultTableModel();

	private final Dao dao = Dao.getInstance();

	/**
	 * Launch the application
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			MenuDialog dialog = new MenuDialog();
			dialog.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					System.exit(0);
				}
			});
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog
	 */
	public MenuDialog() {
		super();
		setModal(true);
		getContentPane().setLayout(new BorderLayout());
		setResizable(false);
		setTitle("��Ʒ����");
		setBounds(100, 100, 500, 375);

		final JPanel operatePanel = new JPanel();
		operatePanel.setLayout(new GridBagLayout());
		getContentPane().add(operatePanel, BorderLayout.NORTH);

		final JLabel numLabel = new JLabel();
		numLabel.setText("��  �ţ�");
		final GridBagConstraints gridBagConstraints_6 = new GridBagConstraints();
		gridBagConstraints_6.insets = new Insets(15, 0, 0, 0);
		gridBagConstraints_6.gridx = 0;
		gridBagConstraints_6.gridy = 0;
		operatePanel.add(numLabel, gridBagConstraints_6);

		numTextField = new JTextField();
		numTextField.setText(getNextNum(dao.sMenuOfMaxId()));
		numTextField.setHorizontalAlignment(SwingConstants.CENTER);
		numTextField.setEditable(false);
		numTextField.setColumns(10);
		final GridBagConstraints gridBagConstraints_15 = new GridBagConstraints();
		gridBagConstraints_15.insets = new Insets(15, 0, 0, 0);
		gridBagConstraints_15.gridy = 0;
		gridBagConstraints_15.gridx = 1;
		operatePanel.add(numTextField, gridBagConstraints_15);

		final JLabel nameLabel = new JLabel();
		final GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(15, 15, 0, 0);
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 0;
		operatePanel.add(nameLabel, gridBagConstraints);
		nameLabel.setText("���ƣ�");

		nameTextField = new JTextField();
		// nameTextField.setName("����");
		final GridBagConstraints gridBagConstraints_1 = new GridBagConstraints();
		gridBagConstraints_1.insets = new Insets(15, 0, 0, 0);
		gridBagConstraints_1.gridx = 3;
		gridBagConstraints_1.gridy = 0;
		operatePanel.add(nameTextField, gridBagConstraints_1);
		nameTextField.setColumns(21);

		final JLabel unitPriceLabel = new JLabel();
		unitPriceLabel.setText("���ۣ�");
		final GridBagConstraints gridBagConstraints_9 = new GridBagConstraints();
		gridBagConstraints_9.insets = new Insets(10, 15, 0, 0);
		gridBagConstraints_9.gridy = 1;
		gridBagConstraints_9.gridx = 4;
		operatePanel.add(unitPriceLabel, gridBagConstraints_9);

		final JLabel unitLabel = new JLabel();
		unitLabel.setText("��λ��");
		final GridBagConstraints gridBagConstraints_8 = new GridBagConstraints();
		gridBagConstraints_8.insets = new Insets(10, 15, 0, 0);
		gridBagConstraints_8.gridy = 0;
		gridBagConstraints_8.gridx = 4;
		operatePanel.add(unitLabel, gridBagConstraints_8);

		unitTextField = new JTextField();
		unitTextField.setName("��λ");
		unitTextField.setColumns(10);
		final GridBagConstraints gridBagConstraints_11 = new GridBagConstraints();
		gridBagConstraints_11.gridwidth = 2;
		gridBagConstraints_11.insets = new Insets(10, 0, 0, 0);
		gridBagConstraints_11.gridy = 0;
		gridBagConstraints_11.gridx = 5;
		operatePanel.add(unitTextField, gridBagConstraints_11);

		final JLabel codeLabel = new JLabel();
		final GridBagConstraints gridBagConstraints_2 = new GridBagConstraints();
		gridBagConstraints_2.insets = new Insets(15, 0, 0, 0);
		gridBagConstraints_2.gridx = 0;
		gridBagConstraints_2.gridy = 1;
		operatePanel.add(codeLabel, gridBagConstraints_2);
		codeLabel.setText("�����룺");

		codeTextField = new JTextField();
		codeTextField.setName("������");
		codeTextField.setColumns(10);
		final GridBagConstraints gridBagConstraints_3 = new GridBagConstraints();
		gridBagConstraints_3.insets = new Insets(15, 0, 0, 0);
		gridBagConstraints_3.gridx = 1;
		gridBagConstraints_3.gridy = 1;
		operatePanel.add(codeTextField, gridBagConstraints_3);

		final JLabel sortLabel = new JLabel();
		sortLabel.setText("��ϵ��");
		final GridBagConstraints gridBagConstraints_4 = new GridBagConstraints();
		gridBagConstraints_4.insets = new Insets(10, 15, 0, 0);
		gridBagConstraints_4.gridy = 1;
		gridBagConstraints_4.gridx = 2;
		operatePanel.add(sortLabel, gridBagConstraints_4);

		sortComboBox = new JComboBox();
		sortComboBox.addItem("��ѡ��");
		final GridBagConstraints gridBagConstraints_7 = new GridBagConstraints();
		gridBagConstraints_7.anchor = GridBagConstraints.WEST;
		gridBagConstraints_7.insets = new Insets(10, 0, 0, 0);
		gridBagConstraints_7.gridy = 1;
		gridBagConstraints_7.gridx = 3;
		operatePanel.add(sortComboBox, gridBagConstraints_7);

		unitPriceTextField = new JTextField();
		unitPriceTextField.setName("����");
		unitPriceTextField.setColumns(8);
		final GridBagConstraints gridBagConstraints_12 = new GridBagConstraints();
		gridBagConstraints_12.insets = new Insets(10, 0, 0, 0);
		gridBagConstraints_12.gridy = 1;
		gridBagConstraints_12.gridx = 5;
		operatePanel.add(unitPriceTextField, gridBagConstraints_12);

		final JLabel label = new JLabel();
		label.setText("Ԫ");
		final GridBagConstraints gridBagConstraints_5 = new GridBagConstraints();
		gridBagConstraints_5.insets = new Insets(10, 0, 0, 0);
		gridBagConstraints_5.gridy = 1;
		gridBagConstraints_5.gridx = 6;
		operatePanel.add(label, gridBagConstraints_5);
		Vector vector = dao.sSortName();
		for (int i = 0; i < vector.size(); i++) {
			Vector v = (Vector) vector.get(i);
			sortComboBox.addItem(v.get(1));
		}

		final JPanel panel = new JPanel();
		final FlowLayout flowLayout_1 = new FlowLayout();
		panel.setLayout(flowLayout_1);
		final GridBagConstraints gridBagConstraints_14 = new GridBagConstraints();
		gridBagConstraints_14.anchor = GridBagConstraints.EAST;
		gridBagConstraints_14.insets = new Insets(5, 0, 10, 0);
		gridBagConstraints_14.gridwidth = 7;
		gridBagConstraints_14.gridy = 2;
		gridBagConstraints_14.gridx = 0;
		operatePanel.add(panel, gridBagConstraints_14);

		final JButton addButton = new JButton();
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Field[] fields = MenuDialog.class.getDeclaredFields();// ͨ��Java�����ȡMenuDialog�����������
				for (int i = 0; i < fields.length; i++) {
					Field field = fields[i];// ���ָ������
					if (field.getType().equals(JTextField.class)) { // ֻ��֤JTextField���͵�����
						field.setAccessible(true); // ˽�����Ա�����Ϊtrue���������
						JTextField textField = null;// ����һ��JTextField���͵Ķ���
						try {
							textField = (JTextField) field.get(MenuDialog.this); // ��ñ����е���Ӧ����
						} catch (Exception exception) {
							exception.printStackTrace();
						}
						if (textField.getText().trim().equals("")) { // �ı���Ϊ��
							JOptionPane.showMessageDialog(null, "����д��Ʒ��"
									+ textField.getName() + "����", "������ʾ",
									JOptionPane.INFORMATION_MESSAGE);// ������Ҫ������Ϣ����ʾ
							textField.requestFocus(); // ���ı����ý���
							return;// ����
						}
					}
				}
				if (sortComboBox.getSelectedIndex() == 0) {// ������֤�����˵�
					JOptionPane.showMessageDialog(null, "��ѡ����Ʒ��������ϵ����", "������ʾ",
							JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				//
				String menu[] = new String[7];// ����һ�����飬���������Ʒ��Ϣ
				menu[0] = numTextField.getText().trim();// ��ò�Ʒ���
				menu[1] = nameTextField.getText().trim();// ��ò�Ʒ����
				menu[2] = codeTextField.getText().trim();// ��ò�Ʒ������
				menu[3] = sortComboBox.getSelectedItem().toString();// ��ò�Ʒ������ϵ
				menu[4] = unitTextField.getText().trim();// ��ò�Ʒ��λ
				menu[5] = unitPriceTextField.getText().trim();// ��ò�Ʒ����
				menu[6] = "����";
				if (menu[1].length() > 10) {
					JOptionPane.showMessageDialog(null, "��Ʒ�������ֻ��Ϊ 10 �����֣�",
							"������ʾ", JOptionPane.INFORMATION_MESSAGE);
					nameTextField.requestFocus();
					return;
				}
				if (menu[2].length() > 10) {
					JOptionPane.showMessageDialog(null, "���������ֻ��Ϊ 10 ���ַ���",
							"������ʾ", JOptionPane.INFORMATION_MESSAGE);
					codeTextField.requestFocus();
					return;
				}
				if (menu[4].length() > 2) {
					JOptionPane.showMessageDialog(null, "��λ���ֻ��Ϊ 2 �����֣�",
							"������ʾ", JOptionPane.INFORMATION_MESSAGE);
					unitTextField.requestFocus();
					return;
				}
				if (!Validate.execute("[1-9]{1}[0-9]{0,3}", menu[5])) {
					String infos[] = { "�����������", "���۱����� 1����9999" };
					JOptionPane.showMessageDialog(null, infos, "������ʾ",
							JOptionPane.INFORMATION_MESSAGE);
					unitPriceTextField.requestFocus();
					return;
				}
				if (dao.sMenuByNameAndState(menu[1], "����") != null) {
					JOptionPane.showMessageDialog(null, "�ò�Ʒ�Ѿ����ڣ�", "������ʾ",
							JOptionPane.INFORMATION_MESSAGE);
					nameTextField.requestFocus();
					return;
				}
				int row = tableModel.getRowCount();// ��õ�ǰӵ�еĲ�Ʒ����
				Vector newMenuV = new Vector();
				newMenuV.add(row + 1);// ������
				for (int i = 0; i < menu.length; i++) {
					newMenuV.add(menu[i]);// ��Ӳ�Ʒ��Ϣ
				}

				Vector sortVector = (Vector) dao.sSortByName(menu[3]).get(0);// ���������ϵ
				menu[3] = sortVector.get(1).toString();// ���ò�ϵ����
				Vector homonymyMenuOfDel = dao.sMenuByNameAndState(menu[1],
						"ɾ��");
				if (homonymyMenuOfDel == null) {
					dao.iMenu(menu);// ���²�Ʒ��Ϣ���浽���ݿ�
				} else {
					newMenuV.set(1, homonymyMenuOfDel.get(0));
					dao.uMenu(menu);
				}

				tableModel.addRow(newMenuV);// ���²�Ʒ��ӵ������
				table.setRowSelectionInterval(row, row);// ѡ������ӵĲ�Ʒ
				numTextField.setText(getNextNum(menu[0]));
				nameTextField.setText(null);
				codeTextField.setText(null);
				sortComboBox.setSelectedIndex(0);
				unitTextField.setText(null);
				unitPriceTextField.setText(null);
				//
			}
		});
		addButton.setText("���");
		panel.add(addButton);

		final JButton delButton = new JButton();
		delButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();// ���ѡ�еĲ�Ʒ
				String delMenuName = table.getValueAt(row, 2).toString();
				String info = "ȷ��Ҫɾ����Ʒ��" + delMenuName + "����";
				int j = JOptionPane.showConfirmDialog(null, info, "������ʾ",
						JOptionPane.YES_NO_OPTION);// ����ȷ����ʾ��
				if (j == 0) {// ȷ��ɾ��
					tableModel.removeRow(row);// �ӱ�����Ƴ���Ʒ��Ϣ
					int rowCount = table.getRowCount();// ���ɾ����ӵ�еĲ�Ʒ��
					if (rowCount > 0) {// ��ӵ�в�Ʒ
						if (row < table.getRowCount()) {// ɾ���Ĳ���λ�ڱ�����Ĳ�ϵ
							for (int i = row; i < table.getRowCount(); i++) {
								table.setValueAt(i + 1 + "", i, 0);// �޸�λ��ɾ����ϵ֮������
							}
							table.setRowSelectionInterval(row, row);// �������Ƶ�ɾ���������Ĳ�ϵΪ��ѡ��
						} else {
							table.setRowSelectionInterval(row - 1, row - 1);// ���õ�ǰλ�ڱ�����Ĳ�ϵ��ѡ��
						}
					}
					//
					dao.uMenuStateByName(delMenuName, "ɾ��");
					JDBC.closeConnection();
				}
			}
		});
		delButton.setText("ɾ��");
		panel.add(delButton);

		final JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane);

		String columnNames[] = new String[] { "�� ��", "�� ��", "�� ��", "������",
				"�� ϵ", "�� λ", "�� ��" };
		for (int i = 0; i < columnNames.length; i++) {
			tableColumnV.add(columnNames[i]);
		}

		tableModel.setDataVector(dao.sMenuOfSell(), tableColumnV);
		JDBC.closeConnection();

		table = new MTable(tableModel);
		if (table.getRowCount() > 0)
			table.setRowSelectionInterval(0, 0);
		scrollPane.setViewportView(table);

		final JLabel leftPlaceholderLabel = new JLabel();
		leftPlaceholderLabel.setPreferredSize(new Dimension(20, 20));
		getContentPane().add(leftPlaceholderLabel, BorderLayout.WEST);

		final JPanel exitPanel = new JPanel();
		final FlowLayout flowLayout = new FlowLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		exitPanel.setLayout(flowLayout);
		getContentPane().add(exitPanel, BorderLayout.SOUTH);

		final JButton exitButton = new JButton();
		exitPanel.add(exitButton);
		exitButton.setText("�˳�");
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		final JLabel bottomPlaceholderLabel = new JLabel();
		bottomPlaceholderLabel.setPreferredSize(new Dimension(10, 40));
		exitPanel.add(bottomPlaceholderLabel);

		final JLabel rightPlaceholderLabel = new JLabel();
		rightPlaceholderLabel.setPreferredSize(new Dimension(20, 20));
		getContentPane().add(rightPlaceholderLabel, BorderLayout.EAST);
		//
	}

	private String getNextNum(String maxNum) {
		String date = Today.getDateOfNum().substring(2);
		if (maxNum == null) {
			maxNum = date + "01";
		} else {
			if (maxNum.subSequence(0, 6).equals(date)) {
				maxNum = maxNum.substring(6);
				int nextNum = Integer.valueOf(maxNum) + 1;
				if (nextNum < 10)
					maxNum = date + "0" + nextNum;
				else
					maxNum = date + nextNum;
			} else {
				maxNum = date + "01";
			}
		}
		return maxNum;
	}

}
