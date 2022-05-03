/**
 * ClassName: DataBuffer.java
 * Author: qiujy
 * CreateTime: 2009-4-15
 * EMAIL: qjyong@gmail.com
 * Copyright 2009 ++YONG All rights reserved.
 */
package client;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.*;
import java.net.*;
import java.util.*;
import client.model.entity.OnlineUserListModel;
import common.model.entity.User;

/** �ͻ������ݻ��� */
public class DataBuffer {
	/** ��ǰ�ͻ��˵��û���Ϣ */
	public static User currentUser;
	/** �����û��б� */
	public static List<User> onlineUsers;
	/** ��ǰ�ͻ������ӵ����������׽��� */
	public static Socket clientSeocket;
	/** ��ǰ�ͻ������ӵ�������������� */
	public static ObjectOutputStream oos;
	/** ��ǰ�ͻ������ӵ��������������� */
	public static ObjectInputStream ois;
	/** ���������ò������Լ� */
	public static Properties configProp;
	/** ��ǰ�ͻ��˵���Ļ�ߴ� */
	public static Dimension screenSize;
	/** ���ͻ��˵�IP��ַ */
	public static String ip ;
	/** ���������ļ��Ķ˿� */
	public static final int RECEIVE_FILE_PORT = 6666;
	/** �����û�JList��Model */
	public static OnlineUserListModel onlineUserListModel;

	static{
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		//���ط����������ļ�
		configProp = new Properties();
		try {
			ip = InetAddress.getLocalHost().getHostAddress();
			configProp.load(Thread.currentThread()
									.getContextClassLoader()
									.getResourceAsStream("serverconfig.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private DataBuffer(){}
}
