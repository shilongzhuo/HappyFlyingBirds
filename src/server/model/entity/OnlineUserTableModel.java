/**
 * ClassName: UserTableModel.java
 * Author: qiujy
 * CreateTime: 2009-4-23
 * EMAIL: qjyong@gmail.com
 * Copyright 2009 ++YONG All rights reserved.
 */
package server.model.entity;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

/** �����û��б�ģ�� */
public class OnlineUserTableModel extends AbstractTableModel {

	private static final long serialVersionUID = -444245379288364831L;
	/** �������� */
	private String[] title = {"�˺�", "�ǳ�", "�Ա�"};
	/** ������ */
	private List<String[]> rows = new ArrayList<String[]>();
	
	public int getRowCount() {
		return rows.size();
	}
		
	public int getColumnCount() {
		return title.length;
	}
	
	public String getColumnName(int column) {
		return title[column];
	}
	
	public String getValueAt(int row, int column) {
		return (rows.get(row))[column];
	}
	
	public void add(String[] value) {
		int row = rows.size();
		rows.add(value);
		fireTableRowsInserted(row, row);
	}
	
	public void remove(long id) {
		int row = -1;
		for (int i = 0; i <= rows.size(); i++) {
			if (String.valueOf(id).equals(getValueAt(i , 0))) {
				row = i;
				break;
			}
		}
		rows.remove(row);
		fireTableRowsDeleted(2, 3);
	}
}