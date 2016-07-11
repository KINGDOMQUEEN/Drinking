package com.mwq.frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import com.mwq.dao.Dao;
import com.mwq.frame.check_out.DayDialog;
import com.mwq.frame.check_out.MonthDialog;
import com.mwq.frame.check_out.YearDialog;
import com.mwq.frame.manage.DeskNumDialog;
import com.mwq.frame.manage.MenuDialog;
import com.mwq.frame.manage.SortDialog;
import com.mwq.frame.user.UpdatePasswordDialog;
import com.mwq.frame.user.UserManagerDialog;
import com.mwq.mwing.MButton;
import com.mwq.mwing.MTable;
import com.mwq.tool.Today;
import com.mwq.tool.Validate;


public class TipWizardFrame extends JFrame {

	private JLabel timeLabel;

	private JTextField amountTextField;

	private JTextField unitTextField;

	private JTextField nameTextField;

	private JTextField codeTextField;

	private JComboBox numComboBox;

	private JTextField changeTextField;

	private JTextField realWagesTextField;

	private JTextField expenditureTextField;

	private ButtonGroup buttonGroup = new ButtonGroup();

	private MTable rightTable;

	private Vector<String> rightTableColumnV;

	private Vector<Vector<Object>> rightTableValueV;

	private DefaultTableModel rightTableModel;

	private MTable leftTable;

	private Vector<String> leftTableColumnV;

	private Vector<Vector<Object>> leftTableValueV;

	private DefaultTableModel leftTableModel;

	private Vector<Vector<Vector<Object>>> menuOfDeskV;

	private Dimension screenSize;

	private final Dao dao = Dao.getInstance();

	/**
	 * Launch the application
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			TipWizardFrame frame = new TipWizardFrame(null);
			frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the frame
	 */
	public TipWizardFrame(final Vector user) {
		super();
		setTitle(" T �Ƽ�");
		setResizable(false);
		setBounds(0, 0, 1024, 768);
		setExtendedState(TipWizardFrame.MAXIMIZED_BOTH);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		screenSize = Toolkit.getDefaultToolkit().getScreenSize();

		final JLabel topLabel = new JLabel();
		topLabel.setPreferredSize(new Dimension(0, 100));
		topLabel.setHorizontalAlignment(SwingConstants.CENTER);
		URL topUrl = this.getClass().getResource("/img/top.jpg");
		ImageIcon topIcon = new ImageIcon(topUrl);
		topLabel.setIcon(topIcon);
		getContentPane().add(topLabel, BorderLayout.NORTH);

		final JSplitPane splitPane = new JSplitPane();// �����ָ�������
		splitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);// ����Ϊˮƽ�ָ�
		splitPane.setDividerLocation(755);// �������Ĭ�ϵķָ�λ��
		splitPane.setDividerSize(10);// ���÷ָ����Ŀ��
		splitPane.setOneTouchExpandable(true);// ����Ϊ֧�ֿ���չ��/�۵��ָ���
		splitPane.setBorder(new TitledBorder(null, "",
				TitledBorder.DEFAULT_JUSTIFICATION,
				TitledBorder.DEFAULT_POSITION, null, null));// �������ı߿�
		getContentPane().add(splitPane, BorderLayout.CENTER);// ���ָ������ӵ��ϼ�������

		final JPanel leftPanel = new JPanel();// �������ڷָ����������ͨ������
		leftPanel.setLayout(new BorderLayout());// �������Ĳ��ֹ�����
		splitPane.setLeftComponent(leftPanel);// ����ͨ��������ӵ��ָ��������

		final JLabel leftTitleLabel = new JLabel();
		leftTitleLabel.setFont(new Font("", Font.BOLD, 14));
		leftTitleLabel.setPreferredSize(new Dimension(0, 25));
		leftTitleLabel.setText(" ǩ���б�");
		leftPanel.add(leftTitleLabel, BorderLayout.NORTH);

		final JScrollPane leftScrollPane = new JScrollPane();
		leftPanel.add(leftScrollPane);

		menuOfDeskV = new Vector<Vector<Vector<Object>>>();

		leftTableColumnV = new Vector<String>();
		String leftTableColumns[] = { "  ", "��    ��", "��Ʒ���", "��Ʒ����", "��    λ",
				"��    ��", "��    ��", "��    ��" };
		for (int i = 0; i < leftTableColumns.length; i++) {
			leftTableColumnV.add(leftTableColumns[i]);
		}

		leftTableValueV = new Vector<Vector<Object>>();

		leftTableModel = new DefaultTableModel(leftTableValueV,
				leftTableColumnV);
		leftTableModel.addTableModelListener(new TableModelListener() {
			public void tableChanged(TableModelEvent e) {// ͨ�����ģ�ͼ�����ʵ���Զ�����
				int rowCount = leftTable.getRowCount();// ���ǩ���б��е�����
				float expenditure = 0.0f;// Ĭ������ 0 Ԫ
				for (int row = 0; row < rowCount; row++) {// ͨ��ѭ���������ѽ��
					expenditure += Float.valueOf(leftTable.getValueAt(row, 7)
							.toString());// �ۼ����ѽ��
				}
				expenditureTextField.setText(expenditure + "0");// ���¡����ѽ��ı���
			}
		});

		leftTable = new MTable(leftTableModel);
		leftScrollPane.setViewportView(leftTable);

		final JPanel rightPanel = new JPanel();// �������ڷָ�����Ҳ����ͨ������
		rightPanel.setLayout(new BorderLayout());
		splitPane.setRightComponent(rightPanel);// ����ͨ��������ӵ��ָ������Ҳ�

		final JLabel rightTitleLabel = new JLabel();
		rightTitleLabel.setFont(new Font("", Font.BOLD, 14));
		rightTitleLabel.setPreferredSize(new Dimension(0, 25));
		rightTitleLabel.setText(" ��̨�б�");
		rightPanel.add(rightTitleLabel, BorderLayout.NORTH);

		final JScrollPane rightScrollPane = new JScrollPane();
		rightPanel.add(rightScrollPane);

		rightTableColumnV = new Vector<String>();
		rightTableColumnV.add("��    ��");
		rightTableColumnV.add("̨    ��");
		rightTableColumnV.add("��̨ʱ��");

		rightTableValueV = new Vector<Vector<Object>>();

		rightTableModel = new DefaultTableModel(rightTableValueV,
				rightTableColumnV);

		rightTable = new MTable(rightTableModel);
		rightTable.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int rSelectedRow = rightTable.getSelectedRow();// ��á���̨�б��е�ѡ����
				leftTableValueV.removeAllElements();// ��ա�ǩ���б��е�������
				leftTableValueV.addAll(menuOfDeskV.get(rSelectedRow));// ��ѡ��̨�ŵ�ǩ���б���ӵ���ǩ���б���
				leftTableModel.setDataVector(leftTableValueV, leftTableColumnV);// ˢ�¡�ǩ���б�
				leftTable.setRowSelectionInterval(0);// ѡ�С�ǩ���б��еĵ�һ��
				numComboBox.setSelectedItem(rightTable.getValueAt(rSelectedRow,
						1));// ͬ��ѡ�С�̨�š������˵��е���Ӧ̨��
			}
		});
		rightScrollPane.setViewportView(rightTable);

		final JPanel bottomPanel = new JPanel();
		bottomPanel.setPreferredSize(new Dimension(0, 230));
		bottomPanel.setLayout(new BorderLayout());
		getContentPane().add(bottomPanel, BorderLayout.SOUTH);

		final JPanel orderDishesPanel = new JPanel();
		orderDishesPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
		bottomPanel.add(orderDishesPanel, BorderLayout.NORTH);

		final JLabel numLabel = new JLabel();
		numLabel.setFont(new Font("", Font.BOLD, 12));
		numLabel.setText("̨�ţ�");
		orderDishesPanel.add(numLabel);

		numComboBox = new JComboBox();
		numComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int rowCount = rightTable.getRowCount();// ��ÿ�̨�б��е����������ѿ�̨��
				if (rowCount > 0) {// �Ѿ��п�̨
					String selectedDeskNum = numComboBox.getSelectedItem()
							.toString();// ��á�̨�š������˵���ѡ�е�̨��
					int needSelectedRow = -1;// Ĭ��ѡ�е�̨��δ��̨
					opened: for (int row = 0; row < rowCount; row++) {// ͨ��ѭ���鿴ѡ�е�̨���Ƿ��Ѿ���̨
						String openedDeskNum = rightTable.getValueAt(row, 1)
								.toString();// ����ѿ�̨��̨��
						if (selectedDeskNum.equals(openedDeskNum)) {// �鿴ѡ�е�̨���Ƿ��Ѿ���̨
							needSelectedRow = row;// ѡ�е�̨���Ѿ���̨
							break opened;// ����ѭ��
						}
					}
					if (needSelectedRow == -1) {// ѡ�е�̨����δ��̨�����¿�̨
						rightTable.clearSelection();// ȡ��ѡ�񡰿�̨�б��е�ѡ����
						leftTableValueV.removeAllElements();// ��ա�ǩ���б��е�������
						leftTableModel.setDataVector(leftTableValueV,
								leftTableColumnV);// ˢ�¡�ǩ���б�
					} else {// ѡ�е�̨���Ѿ���̨������Ӳ�Ʒ
						if (needSelectedRow != rightTable.getSelectedRow()) {
							// ��̨�š������˵���ѡ�е�̨���ڡ���̨�б���δ��ѡ��
							rightTable.setRowSelectionInterval(needSelectedRow);// �ڡ���̨�б���ѡ�и�̨��
							leftTableValueV.removeAllElements();// ��ա�ǩ���б��е�������
							leftTableValueV.addAll(menuOfDeskV
									.get(needSelectedRow));// ��ѡ��̨�ŵ�ǩ���б���ӵ���ǩ���б���
							leftTableModel.setDataVector(leftTableValueV,
									leftTableColumnV);// ˢ�¡�ǩ���б�
							leftTable.setRowSelectionInterval(0);// ѡ�С�ǩ���б��еĵ�һ��
						}
					}
				}
			}
		});
		initNumComboBox();
		orderDishesPanel.add(numComboBox);

		final JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		orderDishesPanel.add(panel);

		final JLabel codeALabel = new JLabel();
		codeALabel.setFont(new Font("", Font.BOLD, 12));
		codeALabel.setText("  ��Ʒ��");
		final GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridy = 0;
		gridBagConstraints.gridx = 0;
		panel.add(codeALabel, gridBagConstraints);

		final JRadioButton numRadioButton = new JRadioButton();
		numRadioButton.setFont(new Font("", Font.BOLD, 12));
		buttonGroup.add(numRadioButton);
		numRadioButton.setText("���");
		final GridBagConstraints gridBagConstraints_1 = new GridBagConstraints();
		gridBagConstraints_1.gridy = 0;
		gridBagConstraints_1.gridx = 1;
		panel.add(numRadioButton, gridBagConstraints_1);

		final JLabel codeBLabel = new JLabel();
		codeBLabel.setFont(new Font("", Font.BOLD, 12));
		codeBLabel.setText("/");
		final GridBagConstraints gridBagConstraints_2 = new GridBagConstraints();
		gridBagConstraints_2.gridy = 0;
		gridBagConstraints_2.gridx = 2;
		panel.add(codeBLabel, gridBagConstraints_2);

		final JRadioButton codeRadioButton = new JRadioButton();
		codeRadioButton.setFont(new Font("", Font.BOLD, 12));
		buttonGroup.add(codeRadioButton);
		codeRadioButton.setSelected(true);
		codeRadioButton.setText("������");
		final GridBagConstraints gridBagConstraints_3 = new GridBagConstraints();
		gridBagConstraints_3.gridy = 0;
		gridBagConstraints_3.gridx = 3;
		panel.add(codeRadioButton, gridBagConstraints_3);

		final JLabel codeCLabel = new JLabel();
		codeCLabel.setText("����");
		final GridBagConstraints gridBagConstraints_4 = new GridBagConstraints();
		gridBagConstraints_4.gridy = 0;
		gridBagConstraints_4.gridx = 4;
		panel.add(codeCLabel, gridBagConstraints_4);

		codeTextField = new JTextField();
		codeTextField.addKeyListener(new KeyAdapter() {

			public void keyTyped(KeyEvent e) {
				if (e.getKeyChar() == ' ')// �ж��û�������Ƿ�Ϊ�ո�
					e.consume();// ����ǿո������ٴ˴ΰ����¼�
			}

			public void keyReleased(KeyEvent e) {// ͨ�����̼�����ʵ�����ܻ�ȡ��Ʒ
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {// ���»س���
					makeOutAnInvoice();// ����
				} else {
					String input = codeTextField.getText().trim();// �����������
					Vector vector = null;// ���������Ĳ�Ʒ����
					if (input.length() > 0) {// �������ݲ�Ϊ��
						if (codeRadioButton.isSelected()) {// ���������ѯ
							vector = dao.sMenuByCode(input);// ��ѯ���������Ĳ�Ʒ
							if (vector.size() > 0)// ���ڷ��������Ĳ�Ʒ
								vector = (Vector) vector.get(0);// ��õ�һ�����������Ĳ�Ʒ
							else
								// �����ڷ��������Ĳ�Ʒ
								vector = null;
						} else {// ����Ų�ѯ
							vector = dao.sMenuById(input);// ��ѯ���������Ĳ�Ʒ
							if (vector.size() > 0)// ���ڷ��������Ĳ�Ʒ
								vector = (Vector) vector.get(0);// ��õ�һ�����������Ĳ�Ʒ
							else
								// �����ڷ��������Ĳ�Ʒ
								vector = null;
						}
					}
					if (vector == null) {// �����ڷ��������Ĳ�Ʒ
						nameTextField.setText(null);// ���á���Ʒ���ơ��ı���Ϊ��
						unitTextField.setText(null);// ���á���λ���ı���Ϊ��
					} else {// ���ڷ��������Ĳ�Ʒ
						nameTextField.setText(vector.get(3).toString());// ���á���Ʒ���ơ��ı���Ϊ���������Ĳ�Ʒ����
						unitTextField.setText(vector.get(5).toString());// ���á���λ���ı���Ϊ���������Ĳ�Ʒ��λ
					}
				}
			}
		});
		codeTextField.setColumns(10);
		final GridBagConstraints gridBagConstraints_5 = new GridBagConstraints();
		gridBagConstraints_5.gridy = 0;
		gridBagConstraints_5.gridx = 5;
		panel.add(codeTextField, gridBagConstraints_5);

		final JLabel nameLabel = new JLabel();
		nameLabel.setFont(new Font("", Font.BOLD, 12));
		nameLabel.setText("  ��Ʒ���ƣ�");
		orderDishesPanel.add(nameLabel);

		nameTextField = new JTextField();
		nameTextField.setHorizontalAlignment(SwingConstants.CENTER);
		nameTextField.setEditable(false);
		nameTextField.setFocusable(false);
		nameTextField.setColumns(20);
		orderDishesPanel.add(nameTextField);

		final JLabel unitLabel = new JLabel();
		unitLabel.setFont(new Font("", Font.BOLD, 12));
		unitLabel.setText("  ��λ��");
		orderDishesPanel.add(unitLabel);

		unitTextField = new JTextField();
		unitTextField.setHorizontalAlignment(SwingConstants.CENTER);
		unitTextField.setEditable(false);
		unitTextField.setFocusable(false);
		unitTextField.setColumns(5);
		orderDishesPanel.add(unitTextField);

		final JLabel amountLabel = new JLabel();
		amountLabel.setFont(new Font("", Font.BOLD, 12));
		amountLabel.setText("  ������");
		orderDishesPanel.add(amountLabel);

		amountTextField = new JTextField();// �������������ı���
		amountTextField.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e) {// ���ı����ý���ʱִ��
				amountTextField.setText(null);// ���á��������ı���Ϊ��
			}

			public void focusLost(FocusEvent e) {// ���ı���ʧȥ����ʱִ��
				String amount = amountTextField.getText();// ������������
				if (amount.length() == 0)// δ��������
					amountTextField.setText("1"); // �ָ�ΪĬ������1
			}
		});
		amountTextField.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				int length = amountTextField.getText().length();// ��ȡ��ǰ������λ��
				if (length < 2) {// λ��С����λ
					String num = (length == 0 ? "123456789" : "0123456789"); // ������������ַ�������ַ���
					if (num.indexOf(e.getKeyChar()) < 0)// �鿴�����ַ��Ƿ����������������ַ���
						e.consume(); // ���������������������ַ��������ٴ˴ΰ����¼�
				} else {
					e.consume(); // �����С��������������λ�������ٴ˴ΰ����¼�
				}
			}
		});
		amountTextField.setText("1");// Ĭ������Ϊ1
		amountTextField.setHorizontalAlignment(SwingConstants.CENTER);
		amountTextField.setColumns(3);
		orderDishesPanel.add(amountTextField);

		final JLabel emptyLabel = new JLabel();
		emptyLabel.setText(null);
		emptyLabel.setPreferredSize(new Dimension(10, 20));
		orderDishesPanel.add(emptyLabel);

		final JButton addButton = new JButton();
		addButton.setFont(new Font("", Font.BOLD, 12));
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				makeOutAnInvoice();
				codeTextField.requestFocus();
			}
		});
		addButton.setText("�� ��");
		orderDishesPanel.add(addButton);

		final JButton subButton = new JButton();
		subButton.setFont(new Font("", Font.BOLD, 12));
		subButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = rightTable.getSelectedRow();
				if (selectedRow == -1) {
					JOptionPane.showMessageDialog(null, "��ѡ��Ҫǩ����̨�ţ�", "������ʾ",
							JOptionPane.INFORMATION_MESSAGE);
					return;
				} else {
					int row = leftTable.getRowCount() - 1;
					String NEW = leftTable.getValueAt(row, 0).toString();
					if (NEW.equals("NEW")) {
						leftTableValueV.removeAllElements();
						leftTableValueV.addAll(menuOfDeskV.get(selectedRow));
						for (; row >= 0; row--) {
							leftTableValueV.get(row).set(0, "");
						}
						leftTableModel.setDataVector(leftTableValueV,
								leftTableColumnV);
						leftTable.setRowSelectionInterval(0, 0);
					}
				}
			}
		});
		subButton.setText("ǩ ��");
		orderDishesPanel.add(subButton);

		final JButton delButton = new JButton();
		delButton.setFont(new Font("", Font.BOLD, 12));
		delButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int lSelectedRow = leftTable.getSelectedRow();// ��á�ǩ���б��е�ѡ���У���Ҫȡ���Ĳ�Ʒ
				if (lSelectedRow == -1) {// δѡ���κ���
					JOptionPane.showMessageDialog(null, "��ѡ��Ҫȡ������Ʒ��", "������ʾ",
							JOptionPane.INFORMATION_MESSAGE);
					return;
				} else {
					String NEW = leftTable.getValueAt(lSelectedRow, 0)
							.toString();// ���ѡ�в�Ʒ���µ�˱��
					if (NEW.equals("")) {// û���µ�˱�ǣ�������ȡ��
						JOptionPane.showMessageDialog(null, "�ܱ�Ǹ������Ʒ�Ѿ�����ȡ����",
								"������ʾ", JOptionPane.INFORMATION_MESSAGE);
						return;
					} else {
						int rSelectedRow = rightTable.getSelectedRow();// ��á���̨�б��е�ѡ���У���ȡ����Ʒ��̨��
						int i = JOptionPane.showConfirmDialog(null, "ȷ��Ҫȡ����"
								+ rightTable.getValueAt(rSelectedRow, 1)
								+ "���е���Ʒ��"
								+ leftTable.getValueAt(lSelectedRow, 3) + "����",
								"������ʾ", JOptionPane.YES_NO_OPTION);// ������ʾ��Ϣȷ���Ƿ�ȡ��
						if (i == 0) {// ȷ��ȡ��
							leftTableModel.removeRow(lSelectedRow);// �ӡ�ǩ���б���ȡ����Ʒ
							int rowCount = leftTable.getRowCount();// ���ȡ����ĵ������
							if (rowCount == 0) {// δ���κβ�Ʒ
								rightTableModel.removeRow(rSelectedRow);// ȡ����̨
								menuOfDeskV.remove(rSelectedRow);// �Ƴ�ǩ���б�
							} else {
								if (lSelectedRow == rowCount) {// ȡ����ƷΪ���һ��
									lSelectedRow -= 1;// �������һ����ƷΪѡ�е�
								} else {// ȡ����Ʒ�������һ��
									Vector<Vector<Object>> menus = menuOfDeskV
											.get(rSelectedRow);
									for (int row = lSelectedRow; row < rowCount; row++) {// �޸ĵ�����
										leftTable.setValueAt(row + 1, row, 1);
										menus.get(row).set(1, row + 1);
									}
								}
								leftTable.setRowSelectionInterval(lSelectedRow);// ����ѡ����
							}
						}
					}
				}
			}
		});
		delButton.setText("ȡ ��");
		orderDishesPanel.add(delButton);

		final JPanel clueOnPanel = new JPanel();
		clueOnPanel.setPreferredSize(new Dimension(220, 0));
		clueOnPanel.setBorder(new TitledBorder(null, "",
				TitledBorder.DEFAULT_JUSTIFICATION,
				TitledBorder.DEFAULT_POSITION, null, null));
		clueOnPanel.setLayout(new GridLayout(0, 1));
		bottomPanel.add(clueOnPanel, BorderLayout.WEST);

		final JLabel aClueOnLabel = new JLabel();
		clueOnPanel.add(aClueOnLabel);
		aClueOnLabel.setFont(new Font("", Font.BOLD, 12));
		aClueOnLabel.setText("  �����ǣ�");

		final JLabel bClueOnLabel = new JLabel();
		bClueOnLabel.setFont(new Font("", Font.BOLD, 12));
		clueOnPanel.add(bClueOnLabel);
		bClueOnLabel.setHorizontalAlignment(SwingConstants.CENTER);
		bClueOnLabel.setText(Today.getDateOfShow());

		final JLabel cClueOnLabel = new JLabel();
		cClueOnLabel.setFont(new Font("", Font.BOLD, 12));
		clueOnPanel.add(cClueOnLabel);
		cClueOnLabel.setHorizontalAlignment(SwingConstants.CENTER);
		cClueOnLabel.setText(Today.getDayOfWeek());

		timeLabel = new JLabel();// ����������ʾʱ��ı�ǩ����
		timeLabel.setFont(new Font("����", Font.BOLD, 14));// ���ñ�ǩ�е�����Ϊ���塢���塢14��
		timeLabel.setForeground(new Color(255, 0, 0));// ���ñ�ǩ�е�����Ϊ��ɫ
		timeLabel.setHorizontalAlignment(SwingConstants.CENTER);// ���ñ�ǩ�е����־�����ʾ
		clueOnPanel.add(timeLabel);// ����ǩ��ӵ��ϼ�������
		new Time().start();// �����߳�

		final JLabel eClueOnLabel = new JLabel();
		clueOnPanel.add(eClueOnLabel);
		eClueOnLabel.setFont(new Font("", Font.BOLD, 12));
		eClueOnLabel.setText("  ��ǰ����Ա��");

		final JLabel fClueOnLabel = new JLabel();
		fClueOnLabel.setFont(new Font("", Font.BOLD, 12));
		clueOnPanel.add(fClueOnLabel);
		fClueOnLabel.setHorizontalAlignment(SwingConstants.CENTER);
		if (user == null)
			fClueOnLabel.setText("ϵͳĬ���û�");
		else
			fClueOnLabel.setText(user.get(1).toString());

		final JPanel checkOutPanel = new JPanel();
		checkOutPanel.setPreferredSize(new Dimension(500, 0));
		bottomPanel.add(checkOutPanel);
		checkOutPanel.setBorder(new TitledBorder(null, "",
				TitledBorder.DEFAULT_JUSTIFICATION,
				TitledBorder.DEFAULT_POSITION, null, null));
		checkOutPanel.setLayout(new GridBagLayout());

		final JLabel label = new JLabel();
		label.setPreferredSize(new Dimension(72, 70));
		URL rmbUrl = this.getClass().getResource("/img/rmb.jpg");
		ImageIcon rmbIcon = new ImageIcon(rmbUrl);
		label.setIcon(rmbIcon);
		final GridBagConstraints gridBagConstraints_9 = new GridBagConstraints();
		gridBagConstraints_9.insets = new Insets(0, 0, 0, 15);
		gridBagConstraints_9.gridheight = 4;
		gridBagConstraints_9.gridy = 0;
		gridBagConstraints_9.gridx = 0;
		checkOutPanel.add(label, gridBagConstraints_9);

		final JLabel expenditureLabel = new JLabel();
		expenditureLabel.setFont(new Font("", Font.BOLD, 16));
		expenditureLabel.setText("���ѽ�");
		final GridBagConstraints gridBagConstraints_13 = new GridBagConstraints();
		gridBagConstraints_13.gridx = 1;
		gridBagConstraints_13.gridy = 0;
		gridBagConstraints_13.insets = new Insets(0, 10, 0, 0);
		checkOutPanel.add(expenditureLabel, gridBagConstraints_13);

		expenditureTextField = new JTextField();
		expenditureTextField.setHorizontalAlignment(SwingConstants.RIGHT);
		expenditureTextField.setText("0.00");
		expenditureTextField.setForeground(new Color(255, 0, 0));
		expenditureTextField.setFont(new Font("", Font.BOLD, 15));
		expenditureTextField.setColumns(7);
		expenditureTextField.setEditable(false);
		final GridBagConstraints gridBagConstraints_6 = new GridBagConstraints();
		gridBagConstraints_6.gridy = 0;
		gridBagConstraints_6.gridx = 2;
		checkOutPanel.add(expenditureTextField, gridBagConstraints_6);

		final JLabel expenditureUnitLabel = new JLabel();
		expenditureUnitLabel.setForeground(new Color(255, 0, 0));
		expenditureUnitLabel.setFont(new Font("", Font.BOLD, 15));
		expenditureUnitLabel.setText(" Ԫ");
		final GridBagConstraints gridBagConstraints_14 = new GridBagConstraints();
		gridBagConstraints_14.gridy = 0;
		gridBagConstraints_14.gridx = 3;
		checkOutPanel.add(expenditureUnitLabel, gridBagConstraints_14);

		final JLabel realWagesLabel = new JLabel();
		realWagesLabel.setFont(new Font("", Font.BOLD, 16));
		realWagesLabel.setText("ʵ�ս�");
		final GridBagConstraints gridBagConstraints_7 = new GridBagConstraints();
		gridBagConstraints_7.insets = new Insets(10, 10, 0, 0);
		gridBagConstraints_7.gridy = 1;
		gridBagConstraints_7.gridx = 1;
		checkOutPanel.add(realWagesLabel, gridBagConstraints_7);

		realWagesTextField = new JTextField();
		realWagesTextField.addKeyListener(new KeyListener() {

			public void keyPressed(KeyEvent e) {
				// TODO �Զ����ɷ������

			}

			public void keyReleased(KeyEvent e) {
				// TODO �Զ����ɷ������

			}

			public void keyTyped(KeyEvent e) {
				int length = realWagesTextField.getText().length();// ��ȡ��ǰ������λ��
				if (length < 8) {// λ��С����λ
					String num = (length == 4 ? "123456789" : "0123456789"); // ������������ַ�������ַ���
					if (num.indexOf(e.getKeyChar()) < 0)// �鿴�����ַ��Ƿ����������������ַ���
						e.consume(); // ���������������������ַ��������ٴ˴ΰ����¼�
				} else {
					e.consume(); // �����С��������������λ�������ٴ˴ΰ����¼�
				}
			}
		});
		realWagesTextField.setHorizontalAlignment(SwingConstants.RIGHT);
		realWagesTextField.setText("0.00");
		realWagesTextField.setForeground(new Color(0, 128, 0));
		realWagesTextField.setFont(new Font("", Font.BOLD, 15));
		realWagesTextField.setColumns(7);
		final GridBagConstraints gridBagConstraints_8 = new GridBagConstraints();
		gridBagConstraints_8.insets = new Insets(10, 0, 0, 0);
		gridBagConstraints_8.gridy = 1;
		gridBagConstraints_8.gridx = 2;
		checkOutPanel.add(realWagesTextField, gridBagConstraints_8);

		final JLabel realWagesUnitLabel = new JLabel();
		realWagesUnitLabel.setForeground(new Color(0, 128, 0));
		realWagesUnitLabel.setFont(new Font("", Font.BOLD, 15));
		realWagesUnitLabel.setText(" Ԫ");
		final GridBagConstraints gridBagConstraints_15 = new GridBagConstraints();
		gridBagConstraints_15.insets = new Insets(10, 0, 0, 0);
		gridBagConstraints_15.gridy = 1;
		gridBagConstraints_15.gridx = 3;
		checkOutPanel.add(realWagesUnitLabel, gridBagConstraints_15);

		final JButton checkOutButton = new JButton();
		checkOutButton.setFont(new Font("", Font.BOLD, 12));
		checkOutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = rightTable.getSelectedRow();
				if (selectedRow == -1) {
					JOptionPane.showMessageDialog(null, "��ѡ��Ҫ���˵�̨�ţ�", "������ʾ",
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					int rowCount = leftTable.getRowCount();// ��ý��˲�̨�ĵ������
					String NEW = leftTable.getValueAt(rowCount - 1, 0)
							.toString();// �������˵ı��
					if (NEW.equals("NEW")) {// �������˱����Ϊ��NEW��,�򵯳���ʾ
						JOptionPane.showMessageDialog(null, "����ȷ��δǩ����Ʒ�Ĵ���ʽ��",
								"������ʾ", JOptionPane.INFORMATION_MESSAGE);
					} else {
						float expenditure = Float.valueOf(expenditureTextField
								.getText());// ������ѽ��
						float realWages = Float.valueOf(realWagesTextField
								.getText());// ���ʵ�ս��
						if (realWages < expenditure) {// ���ʵ�ս��С�����ѽ��򵯳���ʾ
							if (realWages == 0.0f)
								JOptionPane
										.showMessageDialog(null, "������ʵ�ս�",
												"������ʾ",
												JOptionPane.INFORMATION_MESSAGE);
							else
								JOptionPane.showMessageDialog(null,
										"ʵ�ս���С�����ѽ�", "������ʾ",
										JOptionPane.INFORMATION_MESSAGE);
							realWagesTextField.requestFocus();// ���ʵ�ս��ı����ý���
						} else {
							changeTextField.setText(realWages - expenditure
									+ "0");// ���㲢���á������
							String[] values = {
									getNum(),
									rightTable.getValueAt(selectedRow, 1)
											.toString(),
									Today.getDate()
											+ " "
											+ rightTable.getValueAt(
													selectedRow, 2),
									expenditureTextField.getText(),
									user.get(0).toString() };// ��֯���ѵ���Ϣ
							dao.iOrderForm(values);// �־û������ݿ�
							values[0] = dao.sOrderFormOfMaxId();// ������ѵ����
							for (int i = 0; i < rowCount; i++) {// ͨ��ѭ����ø���������Ŀ����Ϣ
								values[1] = leftTable.getValueAt(i, 2)
										.toString();// �����Ʒ���
								values[2] = leftTable.getValueAt(i, 5)
										.toString();// �����Ʒ����
								values[3] = leftTable.getValueAt(i, 7)
										.toString();// �����Ʒ���ѽ��
								dao.iOrderItem(values);// �־û������ݿ�
							}
							JOptionPane.showMessageDialog(null, rightTable
									.getValueAt(selectedRow, 1)
									+ " ������ɣ�", "������ʾ",
									JOptionPane.INFORMATION_MESSAGE);// �������������ʾ
							rightTableModel.removeRow(selectedRow);// �ӡ���̨�б���ȡ����̨
							leftTableValueV.removeAllElements();// ��ա�ǩ���б�
							leftTableModel.setDataVector(leftTableValueV,
									leftTableColumnV);// ˢ�¡�ǩ���б�
							realWagesTextField.setText("0.00");// ��ա�ʵ�ս��ı���
							changeTextField.setText("0.00");// ��ա�������ı���
							menuOfDeskV.remove(selectedRow);// �ӡ�ǩ���б��������Ƴ��ѽ��˵�ǩ���б�
						}
					}
				}
			}
		});
		checkOutButton.setMargin(new Insets(2, 14, 2, 14));
		checkOutButton.setText("�� ��");
		final GridBagConstraints gridBagConstraints_10 = new GridBagConstraints();
		gridBagConstraints_10.anchor = GridBagConstraints.EAST;
		gridBagConstraints_10.gridwidth = 2;
		gridBagConstraints_10.insets = new Insets(10, 0, 0, 0);
		gridBagConstraints_10.gridy = 2;
		gridBagConstraints_10.gridx = 2;
		checkOutPanel.add(checkOutButton, gridBagConstraints_10);

		final JLabel changeLabel = new JLabel();
		changeLabel.setFont(new Font("", Font.BOLD, 16));
		changeLabel.setText("�����");
		final GridBagConstraints gridBagConstraints_11 = new GridBagConstraints();
		gridBagConstraints_11.insets = new Insets(10, 10, 0, 0);
		gridBagConstraints_11.gridy = 3;
		gridBagConstraints_11.gridx = 1;
		checkOutPanel.add(changeLabel, gridBagConstraints_11);

		changeTextField = new JTextField();
		changeTextField.setHorizontalAlignment(SwingConstants.RIGHT);
		changeTextField.setText("0.00");
		changeTextField.setForeground(new Color(255, 0, 255));
		changeTextField.setFont(new Font("", Font.BOLD, 15));
		changeTextField.setEditable(false);
		changeTextField.setColumns(7);
		final GridBagConstraints gridBagConstraints_12 = new GridBagConstraints();
		gridBagConstraints_12.insets = new Insets(10, 0, 0, 0);
		gridBagConstraints_12.gridy = 3;
		gridBagConstraints_12.gridx = 2;
		checkOutPanel.add(changeTextField, gridBagConstraints_12);

		final JLabel changeUnitLabel = new JLabel();
		changeUnitLabel.setForeground(new Color(255, 0, 255));
		changeUnitLabel.setFont(new Font("", Font.BOLD, 15));
		changeUnitLabel.setText(" Ԫ");
		final GridBagConstraints gridBagConstraints_16 = new GridBagConstraints();
		gridBagConstraints_16.insets = new Insets(10, 0, 0, 0);
		gridBagConstraints_16.gridy = 3;
		gridBagConstraints_16.gridx = 3;
		checkOutPanel.add(changeUnitLabel, gridBagConstraints_16);

		final JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(1, 0));
		bottomPanel.add(buttonPanel, BorderLayout.EAST);

		final JPanel aButtonPanel = new JPanel();
		aButtonPanel.setBorder(new TitledBorder(null, "",
				TitledBorder.DEFAULT_JUSTIFICATION,
				TitledBorder.DEFAULT_POSITION, null, null));
		aButtonPanel.setLayout(new GridLayout(0, 1));
		buttonPanel.add(aButtonPanel);

		final JButton aTopButton = new MButton();
		aTopButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				MenuDialog menuDialog = new MenuDialog();
				menuDialog.setLocation((screenSize.width - menuDialog
						.getWidth()) / 2, (screenSize.height - menuDialog
						.getHeight()) / 2);
				menuDialog.setVisible(true);
			}
		});
		URL menuUrl = this.getClass().getResource("/img/menu.jpg");
		ImageIcon menuIcon = new ImageIcon(menuUrl);
		aTopButton.setIcon(menuIcon);
		aButtonPanel.add(aTopButton);

		final JButton aCenterButton = new MButton();
		aCenterButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				SortDialog sortDialog = new SortDialog();
				sortDialog.setLocation((screenSize.width - sortDialog
						.getWidth()) / 2, (screenSize.height - sortDialog
						.getHeight()) / 2);
				sortDialog.setVisible(true);
			}
		});
		URL sortUrl = this.getClass().getResource("/img/sort.jpg");
		ImageIcon sortIcon = new ImageIcon(sortUrl);
		aCenterButton.setIcon(sortIcon);
		aButtonPanel.add(aCenterButton);

		final JButton aBottomButton = new MButton();
		aBottomButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				DeskNumDialog deskNumDialog = new DeskNumDialog(rightTable);
				deskNumDialog.setLocation((screenSize.width - deskNumDialog
						.getWidth()) / 2, (screenSize.height - deskNumDialog
						.getHeight()) / 2);
				deskNumDialog.setVisible(true);
				initNumComboBox();
			}
		});
		URL deskUrl = this.getClass().getResource("/img/desk.jpg");
		ImageIcon deskIcon = new ImageIcon(deskUrl);
		aBottomButton.setIcon(deskIcon);
		aButtonPanel.add(aBottomButton);

		final JPanel dButtonPanel = new JPanel();
		dButtonPanel.setPreferredSize(new Dimension(0, 0));
		dButtonPanel.setBorder(new TitledBorder(null, "",
				TitledBorder.DEFAULT_JUSTIFICATION,
				TitledBorder.DEFAULT_POSITION, null, null));
		dButtonPanel.setLayout(new GridLayout(0, 1));
		buttonPanel.add(dButtonPanel);

		final JButton dTopButton = new MButton();
		dTopButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DayDialog dayDialog = new DayDialog();
				dayDialog.setVisible(true);
			}
		});
		URL dayUrl = this.getClass().getResource("/img/day.png");
		ImageIcon dayIcon = new ImageIcon(dayUrl);
		dTopButton.setIcon(dayIcon);
		dButtonPanel.add(dTopButton);

		final JButton dCenterButton = new MButton();
		dCenterButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MonthDialog monthDialog = new MonthDialog();
				monthDialog.setVisible(true);
			}
		});
		URL monthUrl = this.getClass().getResource("/img/month.png");
		ImageIcon monthIcon = new ImageIcon(monthUrl);
		dCenterButton.setIcon(monthIcon);
		dButtonPanel.add(dCenterButton);

		final JButton dBottomButton = new MButton();
		dBottomButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				YearDialog yearDialog = new YearDialog();
				yearDialog.setVisible(true);
			}
		});
		URL yearUrl = this.getClass().getResource("/img/year.png");
		ImageIcon yearIcon = new ImageIcon(yearUrl);
		dBottomButton.setIcon(yearIcon);

		dButtonPanel.add(dBottomButton);
		final JPanel cButtonPanel = new JPanel();
		cButtonPanel.setBorder(new TitledBorder(null, "",
				TitledBorder.DEFAULT_JUSTIFICATION,
				TitledBorder.DEFAULT_POSITION, null, null));
		cButtonPanel.setLayout(new GridLayout(0, 1));
		buttonPanel.add(cButtonPanel);

		final JButton cTopButton = new MButton();
		cTopButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UpdatePasswordDialog upDialog = new UpdatePasswordDialog();
				upDialog.setLocation(
						(screenSize.width - upDialog.getWidth()) / 2,
						(screenSize.height - upDialog.getHeight()) / 2);
				upDialog.setUser(user);
				upDialog.setVisible(true);
			}
		});
		URL passwordUrl = this.getClass().getResource("/img/password.jpg");
		ImageIcon passwordIcon = new ImageIcon(passwordUrl);
		cTopButton.setIcon(passwordIcon);
		cButtonPanel.add(cTopButton);

		final JButton cCenterButton = new MButton();
		cCenterButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UserManagerDialog umDialog = new UserManagerDialog();
				umDialog.setLocation(
						(screenSize.width - umDialog.getWidth()) / 2,
						(screenSize.height - umDialog.getHeight()) / 2);
				umDialog.setVisible(true);
			}
		});
		URL userUrl = this.getClass().getResource("/img/user.jpg");
		ImageIcon userIcon = new ImageIcon(userUrl);
		cCenterButton.setIcon(userIcon);
		cButtonPanel.add(cCenterButton);

		final JButton cBottomButton = new MButton();
		cBottomButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		URL exitUrl = this.getClass().getResource("/img/exit.jpg");
		ImageIcon exitIcon = new ImageIcon(exitUrl);
		cBottomButton.setIcon(exitIcon);
		cButtonPanel.add(cBottomButton);

		if (user == null) {
			numComboBox.setEnabled(false);
			numRadioButton.setEnabled(false);
			codeRadioButton.setEnabled(false);
			codeTextField.setEnabled(false);
			amountTextField.setEnabled(false);
			addButton.setEnabled(false);
			subButton.setEnabled(false);
			delButton.setEnabled(false);
			realWagesTextField.setEnabled(false);
			checkOutButton.setEnabled(false);
			aTopButton.setEnabled(false);
			aCenterButton.setEnabled(false);
			aBottomButton.setEnabled(false);
			cTopButton.setEnabled(false);
			dTopButton.setEnabled(false);
			dCenterButton.setEnabled(false);
			dBottomButton.setEnabled(false);
		}
	}

	private void makeOutAnInvoice() {
		String deskNum = numComboBox.getSelectedItem().toString();// ���̨��
		String menuName = nameTextField.getText();// �����Ʒ����
		String menuAmount = amountTextField.getText();// �������
		// ��֤
		if (deskNum.equals("��ѡ��")) {// ��֤�Ƿ��Ѿ�ѡ��̨��
			JOptionPane.showMessageDialog(null, "��ѡ��̨�ţ�", "������ʾ",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		if (menuName.length() == 0) {// ��֤�Ƿ��Ѿ�ȷ����Ʒ
			JOptionPane.showMessageDialog(null, "��¼����Ʒ���ƣ�", "������ʾ",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		if (!Validate.execute("[1-9]{1}([0-9]{0,1})", menuAmount)) {// ��֤�����Ƿ���Ч������������1-99֮��
			String info[] = new String[] { "���������������", "����������1-99֮�䣡" };
			JOptionPane.showMessageDialog(null, info, "������ʾ",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		// ����̨��Ϣ
		int rightSelectedRow = rightTable.getSelectedRow();// ��ñ�ѡ�е�̨��
		int leftRowCount = 0;// Ĭ�ϵ������Ϊ0
		if (rightSelectedRow == -1) {// û�б�ѡ�е�̨�ţ����¿�̨
			rightSelectedRow = rightTable.getRowCount();// ��ѡ�е�̨��Ϊ�¿���̨
			Vector deskV = new Vector();// ����һ�������¿�̨����������
			deskV.add(rightSelectedRow + 1);// ��ӿ�̨���
			deskV.add(deskNum);// ��ӿ�̨��
			deskV.add(Today.getTime());// ��ӿ�̨ʱ��
			rightTableModel.addRow(deskV);// ����̨��Ϣ��ӵ�����̨�б���
			rightTable.setRowSelectionInterval(rightSelectedRow);// ѡ���¿���̨
			menuOfDeskV.add(new Vector());// ���һ����Ӧ��ǩ���б�
		} else { // ѡ�е�̨���Ѿ���̨������Ӳ�Ʒ
			leftRowCount = leftTable.getRowCount();// ����ѵ�˵�����
		}
		// ��������Ϣ
		Vector vector = dao.sMenuByNameAndState(menuName, "����");// ��ñ����Ʒ
		int amount = Integer.valueOf(menuAmount);// ����Ʒ����תΪint��
		int unitPrice = Integer.valueOf(vector.get(5).toString()); // ����Ʒ����תΪint��
		int money = unitPrice * amount;// �����Ʒ���Ѷ�
		Vector<Object> menuV = new Vector<Object>();
		menuV.add("NEW");// ����µ�˱��
		menuV.add(leftRowCount + 1);// ��ӵ�����
		menuV.add(vector.get(0));// ��Ӳ�Ʒ���
		menuV.add(menuName);// ��Ӳ�Ʒ����
		menuV.add(vector.get(4));// ��Ӳ�Ʒ��λ
		menuV.add(amount);// ��Ӳ�Ʒ����
		menuV.add(unitPrice);// ��Ӳ�Ʒ����
		menuV.add(money);// ��Ӳ�Ʒ���Ѷ�
		leftTableModel.addRow(menuV);// �������Ϣ��ӵ���ǩ���б���
		leftTable.setRowSelectionInterval(leftRowCount);// ���µ������Ϊѡ����
		menuOfDeskV.get(rightSelectedRow).add(menuV);// ���µ����Ϣ��ӵ���Ӧ��ǩ���б�
		//
		codeTextField.setText(null);
		nameTextField.setText(null);
		unitTextField.setText(null);
		amountTextField.setText("1");
	}

	private String getNum() {
		String maxNum = dao.sOrderFormOfMaxId();
		String date = Today.getDateOfNum();
		if (maxNum == null) {
			maxNum = date + "001";
		} else {
			if (maxNum.subSequence(0, 8).equals(date)) {
				maxNum = maxNum.substring(8);
				int nextNum = Integer.valueOf(maxNum) + 1;
				if (nextNum < 10)
					maxNum = date + "00" + nextNum;
				else if (nextNum < 100)
					maxNum = date + "0" + nextNum;
				else
					maxNum = date + nextNum;
			} else {
				maxNum = date + "001";
			}
		}
		return maxNum;
	}

	private void initNumComboBox() {
		numComboBox.removeAllItems();
		numComboBox.addItem("��ѡ��");
		Vector allSortV = dao.sDesk();
		for (int i = 0; i < allSortV.size(); i++) {
			Vector sortV = (Vector) allSortV.get(i);
			numComboBox.addItem(sortV.get(1));
		}
	}

	private void a(JLabel dClueOnLabel) {
		Calendar now;
		int hour;
		int minute;
		int second;
		while (true) {
			now = Calendar.getInstance();
			hour = now.get(Calendar.HOUR_OF_DAY);
			minute = now.get(Calendar.MINUTE);
			second = now.get(Calendar.SECOND);
			dClueOnLabel.setText(hour + ":" + minute + ":" + second);
		}
	}

	class Time extends Thread {// �����ڲ���
		public void run() {// �ع�����ķ���
			while (true) {
				Date date = new Date();// �������ڶ���
				timeLabel.setText(date.toString().substring(11, 19));// ��ȡ��ǰʱ�䣬����ʾ��ʱ���ǩ��
				try {
					Thread.sleep(1000);// ���߳�����1��
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

}