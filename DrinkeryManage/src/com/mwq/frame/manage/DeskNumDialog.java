package com.mwq.frame.manage;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import com.mwq.tool.Validate;


public class DeskNumDialog extends JDialog {

	private JTable table;

	private JTextField seatingTextField;

	private JTextField numTextField;

	private final Vector columnNameV = new Vector();

	private final DefaultTableModel tableModel = new DefaultTableModel();

	private final Dao dao = Dao.getInstance();

	private JTable openedDeskTable;

	/**
	 * Launch the application
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			DeskNumDialog dialog = new DeskNumDialog(null);
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
	public DeskNumDialog(JTable rightTable) {
		super();
		setModal(true);
		getContentPane().setLayout(new BorderLayout());
		setResizable(false);
		setTitle("̨�Ź���");
		setBounds(100, 100, 500, 375);

		this.openedDeskTable = rightTable;

		final JPanel operatePanel = new JPanel();
		getContentPane().add(operatePanel, BorderLayout.NORTH);

		final JLabel numLabel = new JLabel();
		operatePanel.add(numLabel);
		numLabel.setText("̨  �ţ�");

		numTextField = new JTextField();
		numTextField.setColumns(6);
		operatePanel.add(numTextField);

		final JLabel seatingLabel = new JLabel();
		operatePanel.add(seatingLabel);
		seatingLabel.setText("  ��λ����");

		seatingTextField = new JTextField();
		seatingTextField.setColumns(5);
		operatePanel.add(seatingTextField);

		final JLabel topPlaceholderLabel = new JLabel();
		topPlaceholderLabel.setPreferredSize(new Dimension(20, 40));
		operatePanel.add(topPlaceholderLabel);

		final JButton addButton = new JButton();//�������̨�Ű�ť����
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String num = numTextField.getText().trim();// ��ȡ̨�ţ���ȥ����β�ո�
				String seating = seatingTextField.getText().trim();// ��ȡ��λ������ȥ����β�ո�
				if (num.equals("") || seating.equals("")) {// �鿴�û��Ƿ�������̨�ź���λ��
					JOptionPane.showMessageDialog(null, "������̨�ź���λ����", "������ʾ",
							JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				if (num.length() > 5) {// �鿴̨�ŵĳ����Ƿ񳬹���5λ
					JOptionPane.showMessageDialog(null, "̨�����ֻ��Ϊ5���ַ���", "������ʾ",
							JOptionPane.INFORMATION_MESSAGE);
					numTextField.requestFocus();// Ϊ̨���ı��������ý���
					return;
				}
				if (!Validate.execute("[1-9]{1}([0-9]{0,1})", seating)) {// ��֤��λ���Ƿ���1����19֮��
					String[] infos = { "��λ���������", "��λ�������� 1����99 ֮�䣡" };
					JOptionPane.showMessageDialog(null, infos, "������ʾ",
							JOptionPane.INFORMATION_MESSAGE);
					seatingTextField.requestFocus();// Ϊ��λ���ı��������ý���
					return;
				}
				if (dao.sDeskByNum(num) != null) {// �鿴��̨���Ƿ��Ѿ�����
					JOptionPane.showMessageDialog(null, "��̨���Ѿ����ڣ�", "������ʾ",
							JOptionPane.INFORMATION_MESSAGE);
					numTextField.requestFocus();// Ϊ̨���ı��������ý���
					return;
				}
				int row = table.getRowCount();// ��õ�ǰӵ��̨�ŵĸ���
				Vector newDeskNumV = new Vector();// ����һ��������̨�ŵ�����
				newDeskNumV.add(new Integer(row + 1));// ���������
				newDeskNumV.add(num);// ���̨��
				newDeskNumV.add(seating);// �����λ��
				tableModel.addRow(newDeskNumV);// ����̨����Ϣ��ӵ������
				table.setRowSelectionInterval(row, row);// ��������ӵ�̨��Ϊѡ�е�
				numTextField.setText(null);// ��̨���ı�������Ϊ��
				seatingTextField.setText(null);// ����λ���ı�������Ϊ��
				//
				dao.iDesk(num, seating);// ������ӵ�̨����Ϣ���浽���ݿ���
				JDBC.closeConnection();// �ر����ݿ�����
			}
		});
		addButton.setText("���");
		operatePanel.add(addButton);

		final JButton delButton = new JButton();//����ɾ��̨�Ű�ť����
		delButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = table.getSelectedRow();// ���ѡ�еĲ�̨
				if (selectedRow == -1) {// δѡ���κβ�̨
					JOptionPane.showMessageDialog(null, "��ѡ��Ҫɾ���Ĳ�̨��", "������ʾ",
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					String deskNum = table.getValueAt(selectedRow, 1)
							.toString();// ���ѡ�в�̨�ı��
					for (int row = 0; row < openedDeskTable.getRowCount(); row++) {// �鿴�ò�̨�Ƿ����ڱ�ʹ��
						if (deskNum.equals(openedDeskTable.getValueAt(row, 1))) {
							JOptionPane.showMessageDialog(null,
									"�ò�̨����ʹ�ã�����ɾ����", "������ʾ",
									JOptionPane.INFORMATION_MESSAGE);
							return;// �ò�̨���ڱ�ʹ�ã�����ɾ��������
						}
					}
					String infos[] = new String[] {// ��֯ȷ����Ϣ
							"ȷ��Ҫɾ����̨��",
							"    ̨  �ţ�" + deskNum,
							"    ��λ����"
									+ table.getValueAt(selectedRow, 2)
											.toString() };
					int i = JOptionPane.showConfirmDialog(null, infos, "������ʾ",
							JOptionPane.YES_NO_OPTION);// ����ȷ����ʾ
					if (i == 0) {// ȷ��ɾ��
						dao.dDeskByNum(deskNum);// �����ݿ���ɾ��
						tableModel.setDataVector(dao.sDesk(), columnNameV);// ˢ�±��
						int rowCount = table.getRowCount();// ���ɾ����ӵ�еĲ�̨��
						if (rowCount > 0) {// ��ӵ�в�̨
							if (selectedRow == rowCount)// ɾ����Ϊ���һ����̨
								selectedRow -= 1;// ��ѡ�еĲ�̨ǰ��һ��
							table.setRowSelectionInterval(selectedRow,
									selectedRow);// ���õ�ǰѡ�еĲ�̨
						}
						JDBC.closeConnection();// �ر����ݿ�����
					}
				}
			}
		});
		delButton.setText("ɾ��");
		operatePanel.add(delButton);

		final JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane);

		String columnNames[] = new String[] { "��  ��", "̨  ��", "��λ��" };
		for (int i = 0; i < columnNames.length; i++) {
			columnNameV.add(columnNames[i]);
		}

		tableModel.setDataVector(dao.sDesk(), columnNameV);
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
