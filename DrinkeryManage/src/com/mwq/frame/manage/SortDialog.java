package com.mwq.frame.manage;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.mwq.dao.Dao;
import com.mwq.dao.JDBC;
import com.mwq.mwing.MTable;


public class SortDialog extends JDialog {

	private JTable table;

	private JTextField sortNameTextField;

	private final Vector columnNameV = new Vector();

	private final DefaultTableModel tableModel = new DefaultTableModel();

	private final Dao dao = Dao.getInstance();

	/**
	 * Launch the application
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			SortDialog dialog = new SortDialog();
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
	public SortDialog() {
		super();
		setModal(true);
		getContentPane().setLayout(new BorderLayout());
		setResizable(false);
		setTitle("��ϵ����");
		setBounds(100, 100, 500, 375);

		final JPanel operatePanel = new JPanel();
		getContentPane().add(operatePanel, BorderLayout.NORTH);

		final JLabel sortNameLabel = new JLabel();
		operatePanel.add(sortNameLabel);
		sortNameLabel.setText("��ϵ���ƣ�");

		sortNameTextField = new JTextField();
		operatePanel.add(sortNameTextField);
		sortNameTextField.setColumns(20);

		final JLabel topPlaceholderLabel = new JLabel();
		topPlaceholderLabel.setPreferredSize(new Dimension(20, 40));
		operatePanel.add(topPlaceholderLabel);

		final JButton addButton = new JButton();// ������Ӳ�ϵ���ư�ť����
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String sortName = sortNameTextField.getText().trim(); // ��ò�ϵ���ƣ���ȥ����β�ո�
				if (sortName.equals("")) {// �鿴�Ƿ������˲�ϵ����
					JOptionPane.showMessageDialog(null, "�������ϵ���ƣ�", "������ʾ",
							JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				if (sortName.length() > 10) {// �鿴��ϵ���Ƶĳ����Ƿ񳬹���10������
					JOptionPane.showMessageDialog(null, "��ϵ�������ֻ��Ϊ10�����֣�",
							"������ʾ", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				if (dao.sSortByName(sortName).size() > 0) {// �鿴�ò�ϵ�����Ƿ��Ѿ�����
					JOptionPane.showMessageDialog(null, "�ò�ϵ�Ѿ����ڣ�", "������ʾ",
							JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				int row = tableModel.getRowCount();// ��õ�ǰӵ�в�ϵ���Ƶĸ���
				Vector newSortV = new Vector();// ����һ�������²�ϵ���Ƶ�����
				newSortV.add(new Integer(row + 1));// ������
				newSortV.add(sortName);// ��Ӳ�ϵ����
				tableModel.addRow(newSortV);// ���²�ϵ������Ϣ��ӵ������
				table.setRowSelectionInterval(row, row);// ��������ӵĲ�ϵ����Ϊѡ�е�
				sortNameTextField.setText(null);// ����ϵ�����ı�������Ϊ��
				//
				dao.iSort(sortName);// ������ӵĲ�ϵ������Ϣ���浽���ݿ���
				JDBC.closeConnection();// �ر����ݿ�����
			}
		});
		addButton.setText("���");
		operatePanel.add(addButton);

		final JButton delButton = new JButton();// ����ɾ����ϵ���ư�ť����
		delButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();// ���ѡ�еĲ�ϵ
				String delSortName = (String) table.getValueAt(row, 1);// ���ѡ�еĲ�ϵ����
				int j = JOptionPane
						.showConfirmDialog(null, "ȷ��Ҫɾ����ϵ��" + delSortName
								+ "����", "������ʾ", JOptionPane.YES_NO_OPTION);// ����ȷ����ʾ
				if (j == 0) {// ȷ��ɾ��
					tableModel.removeRow(row);// �ӱ�����Ƴ���ϵ��Ϣ
					int rowCount = table.getRowCount();// ���ɾ����ӵ�еĲ�ϵ��
					if (rowCount > 0) {// ��ӵ�в�ϵ
						if (row < table.getRowCount()) {// ɾ���Ĳ���λ�ڱ�����Ĳ�ϵ
							for (int i = row; i < table.getRowCount(); i++) {
								table.setValueAt(i + 1 + "", i, 0);// �޸�λ��ɾ����ϵ֮������
							}
							table.setRowSelectionInterval(row, row);// �������Ƶ�ɾ���������Ĳ�ϵΪ��ѡ��
						} else {// ɾ������λ�ڱ�����Ĳ�ϵ
							table.setRowSelectionInterval(row - 1, row - 1);// ���õ�ǰλ�ڱ�����Ĳ�ϵ��ѡ��
						}
					}
					//
					dao.dSortByName(delSortName);// �����ݿ���ɾ����ϵ
					JDBC.closeConnection();// �ر����ݿ�����
				}
			}
		});
		delButton.setText("ɾ��");
		operatePanel.add(delButton);

		final JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane);

		columnNameV.add("��    ��");
		columnNameV.add("��ϵ����");
		tableModel.setDataVector(dao.sSortName(), columnNameV);
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
}
