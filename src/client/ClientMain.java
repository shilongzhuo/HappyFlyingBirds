/**
 * ClassName: ClientMain.java
 * Author: qiujy
 * CreateTime: 2009-4-15
 * EMAIL: qjyong@gmail.com
 * Copyright 2009 ++YONG All rights reserved.
 */
package client;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import org.jvnet.substance.skin.*;
import client.ui.LoginFrame;

/** �ͻ�������� */
public class ClientMain {


	public ClientMain() {
		connection(); //���ӵ�������

		//������۸о�
		JFrame.setDefaultLookAndFeelDecorated(true);
		JDialog.setDefaultLookAndFeelDecorated(true);
		try {
			//UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
			UIManager.setLookAndFeel(new SubstanceOfficeSilver2007LookAndFeel());
			UIManager.setLookAndFeel(new SubstanceBusinessLookAndFeel());

		} catch (Exception e) {
			e.printStackTrace();
		}
		new LoginFrame();  //������¼����
}
		/*{
		connection(); //���ӵ�������
		
		//������۸о�
		JFrame.setDefaultLookAndFeelDecorated(true);
		JDialog.setDefaultLookAndFeelDecorated(true);
		try {
			//UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
			UIManager.setLookAndFeel(new SubstanceOfficeSilver2007LookAndFeel());
			UIManager.setLookAndFeel(new SubstanceBusinessLookAndFeel());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		new LoginFrame();  //������¼����
	}*/

	/** ���ӵ������� */
	public static void connection() {
		String ip = DataBuffer.configProp.getProperty("ip");
		int port = Integer.parseInt(DataBuffer.configProp.getProperty("port"));
		try {
			DataBuffer.clientSeocket = new Socket(ip, port);
			DataBuffer.oos = new ObjectOutputStream(DataBuffer.clientSeocket.getOutputStream());
			DataBuffer.ois = new ObjectInputStream(DataBuffer.clientSeocket.getInputStream());
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(new JFrame(), 
					"���ӷ�����ʧ��,����!","������δ����", JOptionPane.ERROR_MESSAGE);//��������ʧ��
			System.exit(0);
		}
	}
}
