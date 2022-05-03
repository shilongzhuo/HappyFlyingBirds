/**
 * ClassName: ServerMain.java
 * Author: qiujy
 * CreateTime: 2009-4-15
 * EMAIL: qjyong@gmail.com
 * Copyright 2009 ++YONG All rights reserved.
 */
package server;

import java.io.IOException;
import java.net.*;
import javax.swing.*;
import server.controller.RequestProcessor;
import server.ui.ServerInfoFrame;

/** ��������ڳ��� */
public class ServerMain {
	public static void main(String[] args) {
		int port = Integer.parseInt(DataBuffer.configProp.getProperty("port"));
		//��ʼ���������׽���
		try {
			DataBuffer.serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		new Thread(new Runnable() {//�������߳̽��пͻ������Ӽ���
			public void run() {
				try {
					while (true) {
						// �����ͻ��˵�����
						Socket socket = DataBuffer.serverSocket.accept();
						System.out.println("�ͻ����ˣ�" 
								+ socket.getInetAddress().getHostAddress()
								+ ":" + socket.getPort());
						
						//���ÿ���ͻ�������һ���̣߳����߳��е�����������������ÿ���ͻ��˵�����
						new Thread(new RequestProcessor(socket)).start();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
		
		//������۸о�
		JFrame.setDefaultLookAndFeelDecorated(true);
		JDialog.setDefaultLookAndFeelDecorated(true);
		try {
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		//������������ش���
		new ServerInfoFrame(); 
	}
}