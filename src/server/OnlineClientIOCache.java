/**
 * ClassName: OnlineUserIO.java
 * Author: qiujy
 * CreateTime: 2009-4-23
 * EMAIL: qjyong@gmail.com
 * Copyright 2009 ++YONG All rights reserved.
 */
package server;

import java.io.*;

/** ���߿ͻ��˵�IO�������� */
public class OnlineClientIOCache {
	//���ͬһ��Socket�л�ȡ������ȫ�ַ�Χ�����ֻ��װһ��,�������
	private ObjectInputStream ois;  //����������
	private ObjectOutputStream oos; //���������
	
	public OnlineClientIOCache(ObjectInputStream ois, ObjectOutputStream oos) {
		this.ois = ois;
		this.oos = oos;
	}
	
	public ObjectInputStream getOis() {
		return ois;
	}
	
	public ObjectOutputStream getOos() {
		return oos;
	}
}
