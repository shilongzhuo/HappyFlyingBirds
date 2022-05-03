/**
 * ClassName: ServerInfoFrame.java
 * Author: qiujy
 * CreateTime: 2009-4-23
 * EMAIL: qjyong@gmail.com
 * Copyright 2009 ++YONG All rights reserved.
 */
package server.ui;

import java.awt.BorderLayout;
import java.awt.event.*;
import java.text.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.*;

import common.model.entity.User;
import server.DataBuffer;
import server.model.service.UserService;

/** ��������Ϣ���� */
public class ServerInfoFrame extends JFrame {
	private static final long serialVersionUID = 6274443611957724780L;
	
	public ServerInfoFrame() {
		init();
		loadData();
		setVisible(true);
	}
	
	public void init(){  //��ʼ������
		this.setTitle("����������");//���÷�������������
		this.setBounds((DataBuffer.screenSize.width - 500)/2, 
				(DataBuffer.screenSize.height - 375)/2, 500, 375);
		this.setLayout(new BorderLayout());
		
		JPanel panel = new JPanel();
		Border border = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
		panel.setBorder(BorderFactory.createTitledBorder(border, 
				"���������", TitledBorder.LEFT,TitledBorder.TOP));
		this.add(panel, BorderLayout.NORTH);
		
		JLabel label = new JLabel("��������ǰ������: " 
				+ DataBuffer.serverSocket.getLocalPort() + " �˿�      ");
		panel.add(label);
		JButton exitBtn = new JButton("�رշ�����");//�رչرշ�������ť
		panel.add(exitBtn);
		
		//ʹ�÷����������е�TableModel
		JTable onlineUserTable = new JTable(DataBuffer.onlineUserTableModel);
		JTable registedUserTable = new JTable(DataBuffer.registedUserTableModel);
		
		//ѡ�
		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.addTab("�����û��б�", new JScrollPane(onlineUserTable));
		tabbedPane.addTab("��ע���û��б�", new JScrollPane(registedUserTable));
		tabbedPane.setTabComponentAt(0, new JLabel("�����û��б�"));
		this.add(tabbedPane, BorderLayout.CENTER);
		
		final JLabel stateBar = new JLabel("", SwingConstants.RIGHT);
		stateBar.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		//�ö�ʱ��������ʾ��ǰʱ��
		new java.util.Timer().scheduleAtFixedRate(
				new TimerTask(){
					DateFormat df = new SimpleDateFormat("yyyy��MM��dd�� HH:mm:ss");
					public void run() {
						stateBar.setText("��ǰʱ�䣺" + df.format(new Date()) + "  ");
					}
				}, 0, 1000);
		this.add(stateBar, BorderLayout.SOUTH); //��״̬����ӵ�������ϱ�
		
		//�رմ���
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e) {
				logout();
			}
		});
		
		/* ��ӹرշ�������ť�¼������� */
		exitBtn.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent event) {
				logout();
			}
		});
	}
	
	/** ��������ע����û���Ϣ���ص�RegistedUserTableModel�� */
	private void loadData(){
		List<User> users = new UserService().loadAllUser();
		for (User user : users) {
			DataBuffer.registedUserTableModel.add(new String[]{
				String.valueOf(user.getId()),
				user.getPassword(),
				user.getNickname(),
				String.valueOf(user.getSex())
			});
		}
	}

	/** �رշ����� */
	private void logout() {
		int select = JOptionPane.showConfirmDialog(ServerInfoFrame.this, 
				"ȷ���ر���\n\n�رշ��������ж������пͻ��˵�����!", 
				"�رշ�����", 
				JOptionPane.YES_NO_OPTION);
		//����û�������ǹرշ�������ťʱ����ʾ�Ƿ�ȷ�Ϲرա�
		if (select == JOptionPane.YES_OPTION) {
			System.exit(0);//�˳�ϵͳ
		}else{
			//����Ĭ�ϵĴ��ڹر��¼�����
			setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); 
		}
	}
}