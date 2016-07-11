package com.mwq.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;


public class BaseDao {

	// ��ѯ�����¼
	protected Vector selectSomeNote(String sql) {
		Vector<Vector<Object>> vector = new Vector<Vector<Object>>();// �������������
		Connection conn = JDBC.getConnection();// ������ݿ�����
		try {
			Statement stmt = conn.createStatement();// ��������״̬����
			ResultSet rs = stmt.executeQuery(sql);// ִ��SQL����ò�ѯ���
			int columnCount = rs.getMetaData().getColumnCount();// ��ò�ѯ���ݱ������
			int row = 1;// ���������
			while (rs.next()) {// ���������
				Vector<Object> rowV = new Vector<Object>();// ����������
				rowV.add(new Integer(row++));// ��������
				for (int column = 1; column <= columnCount; column++) {
					rowV.add(rs.getObject(column));// �����ֵ
				}
				vector.add(rowV);// ����������ӵ������������
			}
			rs.close();// �رս��������
			stmt.close();// �ر�����״̬����
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return vector;// ���ؽ��������
	}

	// ��ѯ������¼
	protected Vector selectOnlyNote(String sql) {
		Vector<Object> vector = null;
		Connection conn = JDBC.getConnection();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			int columnCount = rs.getMetaData().getColumnCount();
			while (rs.next()) {
				vector = new Vector<Object>();
				for (int column = 1; column <= columnCount; column++) {
					vector.add(rs.getObject(column));
				}
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return vector;
	}

	// ��ѯ���ֵ
	protected Vector selectSomeValue(String sql) {
		Vector<Object> vector = new Vector<Object>();
		Connection conn = JDBC.getConnection();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				vector.add(rs.getObject(1));
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return vector;
	}

	// ��ѯ����ֵ
	protected Object selectOnlyValue(String sql) {
		Object value = null;
		Connection conn = JDBC.getConnection();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				value = rs.getObject(1);
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return value;
	}

	// ���롢�޸ġ�ɾ����¼
	public boolean longHaul(String sql) {
		boolean isLongHaul = true;// Ĭ�ϳ־û��ɹ�
		Connection conn = JDBC.getConnection();// ������ݿ�����
		try {
			conn.setAutoCommit(false);// ����Ϊ�ֶ��ύ
			Statement stmt = conn.createStatement();// ��������״̬����
			stmt.executeUpdate(sql);// ִ��SQL���
			stmt.close();// �ر�����״̬����
			conn.commit();// �ύ�־û�
		} catch (SQLException e) {
			isLongHaul = false;// �־û�ʧ��
			try {
				conn.rollback();// �ع�
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		return isLongHaul;// ���س־û����
	}
	public static void main(String[] args) {
		BaseDao d=new BaseDao();
		d.longHaul("delete from tb_order_item");
		d.longHaul("delete from tb_order_form");
		d.longHaul("delete from tb_user");
	}
}
