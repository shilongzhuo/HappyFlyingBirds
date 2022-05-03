/**
 * ClassName: DataBuffer.java
 * Author: qiujy
 * CreateTime: 2009-4-15
 * EMAIL: qjyong@gmail.com
 * Copyright 2009 ++YONG All rights reserved.
 */
package server;

import java.awt.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.*;
import java.util.concurrent.ConcurrentSkipListMap;
import server.model.entity.*;
import common.model.entity.User;

/** �����������ݻ��� */
public class DataBuffer {
	/**���������׽��� */
	public static ServerSocket serverSocket;
	/** �����û���IO Map */
	public static Map<Long, OnlineClientIOCache> onlineUserIOCacheMap;
	/** �����û�Map */
	public static Map<Long, User> onlineUsersMap;
	/** ���������ò������Լ� */
	public static Properties configProp;
	/** ��ע���û����Model */
	public static RegistedUserTableModel registedUserTableModel;
	/** ��ǰ�����û����Model */
	public static OnlineUserTableModel onlineUserTableModel;
	/** ��ǰ����������ϵͳ����Ļ�ߴ� */
	public static Dimension screenSize;
	
	static{
		// ��ʼ��
		onlineUserIOCacheMap = new ConcurrentSkipListMap<Long,OnlineClientIOCache>();
		onlineUsersMap = new ConcurrentSkipListMap<Long, User>();
		configProp = new Properties();
		registedUserTableModel = new RegistedUserTableModel();
		onlineUserTableModel = new OnlineUserTableModel();
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		
		// ���ط����������ļ�
		try {
			configProp.load(Thread.currentThread()
								  .getContextClassLoader()
								  .getResourceAsStream("serverconfig.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
