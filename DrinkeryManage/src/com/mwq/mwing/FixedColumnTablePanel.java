package com.mwq.mwing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;


public class FixedColumnTablePanel extends JPanel {

	private MTable fixedColumnTable;

	private FixedColumnTableModel fixedColumnTableModel;

	private MTable floatingColumnTable;

	private FloatingColumnTableModel floatingColumnTableModel;

	private Vector<String> tableColumnV;

	private Vector<Vector<Object>> tableValueV;

	private int fixedColumn = 2;

	public FixedColumnTablePanel(Vector<String> tableColumnV,
			Vector<Vector<Object>> tableValueV, int fixedColumn) {
		super();
		setLayout(new BorderLayout());

		this.tableColumnV = tableColumnV;
		this.tableValueV = tableValueV;
		this.fixedColumn = fixedColumn;

		fixedColumnTableModel = new FixedColumnTableModel();// �����̶��б��ģ�Ͷ���

		fixedColumnTable = new MTable(fixedColumnTableModel);// �����̶��б�����
		ListSelectionModel fixed = fixedColumnTable.getSelectionModel();// ���ѡ��ģ�Ͷ���
		fixed.addListSelectionListener(new MListSelectionListener(true));// ����б�ѡ�е��¼�������

		floatingColumnTableModel = new FloatingColumnTableModel();// �������ƶ��б��ģ�Ͷ���

		floatingColumnTable = new MTable(floatingColumnTableModel);// �������ƶ��б�����
		ListSelectionModel floating = floatingColumnTable.getSelectionModel();// ���ѡ��ģ�Ͷ���
		floating.addListSelectionListener(new MListSelectionListener(false));// ����б�ѡ�е��¼�������

		JScrollPane scrollPane = new JScrollPane();// ����һ������������
		scrollPane.setCorner(JScrollPane.UPPER_LEFT_CORNER, fixedColumnTable
				.getTableHeader()); // ���̶��б��ͷ�ŵ������������Ϸ�

		JViewport viewport = new JViewport();// ����һ��������ʾ������Ϣ���ӿڶ���
		viewport.setView(fixedColumnTable);// ���̶��б����ӵ��ӿ���
		viewport.setPreferredSize(fixedColumnTable.getPreferredSize());// �����ӿڵ���ѡ��СΪ�̶��б�����ѡ��С
		scrollPane.setRowHeaderView(viewport);// ���ӿ���ӵ��������ı����ӿ���

		scrollPane.setViewportView(floatingColumnTable);// �����ƶ������ӵ�Ĭ���ӿ�

		add(scrollPane, BorderLayout.CENTER);
		//
	}

	class MTable extends JTable {

		public MTable(AbstractTableModel tableModel) {
			super(tableModel);
			setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		}

		// ���������Ϣ
		@Override
		public JTableHeader getTableHeader() {
			// ��ñ��ͷ����
			JTableHeader tableHeader = super.getTableHeader();
			tableHeader.setReorderingAllowed(false);// ���ñ���в�������
			// ��ñ��ͷ�ĵ�Ԫ�����
			DefaultTableCellRenderer defaultRenderer = (DefaultTableCellRenderer) tableHeader
					.getDefaultRenderer();
			// ���õ�Ԫ�����ݣ���������������ʾ
			defaultRenderer
					.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
			return tableHeader;
		}

		// �����ֵ������ʾ
		@Override
		public TableCellRenderer getDefaultRenderer(Class<?> columnClass) {
			DefaultTableCellRenderer defaultRenderer = (DefaultTableCellRenderer) super
					.getDefaultRenderer(columnClass);
			defaultRenderer
					.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
			return defaultRenderer;
		}

		// �����ֻ�ɵ�ѡ
		@Override
		public ListSelectionModel getSelectionModel() {
			ListSelectionModel selectionModel = super.getSelectionModel();
			selectionModel
					.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			return selectionModel;
		}

		// �������ñ���ѡ����
		@Override
		public void setRowSelectionInterval(int fromRow, int toRow) {// �ع�����ķ���
			super.setRowSelectionInterval(fromRow, toRow);
		}

		// �������ñ���Ψһѡ����
		public void setRowSelectionInterval(int row) {// ͨ������ʵ���Լ��ķ���
			setRowSelectionInterval(row, row);
		}

	}

	class FixedColumnTableModel extends AbstractTableModel {

		public int getColumnCount() {// ���ع̶��е�����
			return fixedColumn;
		}

		public int getRowCount() {// ��������
			return tableValueV.size();
		}

		public Object getValueAt(int rowIndex, int columnIndex) {// ����ָ����Ԫ���ֵ
			return tableValueV.get(rowIndex).get(columnIndex);
		}

		@Override
		public String getColumnName(int columnIndex) {// ����ָ���е�����
			return tableColumnV.get(columnIndex);
		}

	}

	class FloatingColumnTableModel extends AbstractTableModel {

		public int getColumnCount() {// ���ؿ��ƶ��е�����
			return tableColumnV.size() - fixedColumn;// ��Ҫ�۳��̶��е�����
		}

		public int getRowCount() {// ��������
			return tableValueV.size();
		}

		public Object getValueAt(int rowIndex, int columnIndex) {// ����ָ����Ԫ���ֵ
			return tableValueV.get(rowIndex).get(columnIndex + fixedColumn);// ��ҪΪ���������Ϲ̶��е�����
		}

		@Override
		public String getColumnName(int columnIndex) {// ����ָ���е�����
			return tableColumnV.get(columnIndex + fixedColumn);// ��ҪΪ���������Ϲ̶��е�����
		}

	}

	class MListSelectionListener implements ListSelectionListener {
		boolean isFixedColumnTable = true; // Ĭ����ѡ�й̶��б���е��д���

		public MListSelectionListener(boolean isFixedColumnTable) {
			this.isFixedColumnTable = isFixedColumnTable;
		}

		public void valueChanged(ListSelectionEvent e) {
			if (isFixedColumnTable) { // ��ѡ�й̶��б���е��д���
				int selectedRow = fixedColumnTable.getSelectedRow(); // ��ù̶��б���е�ѡ����
				floatingColumnTable.setRowSelectionInterval(selectedRow); // ͬʱѡ�п��ƶ��б���е�ѡ����
			} else { // ��ѡ�п��ƶ��б���е��д���
				int selectedRow = floatingColumnTable.getSelectedRow(); // ��ÿ��ƶ��б���е�ѡ����
				fixedColumnTable.setRowSelectionInterval(selectedRow); // ͬʱѡ�й̶��б���е�ѡ����
			}
		}
	}

}
