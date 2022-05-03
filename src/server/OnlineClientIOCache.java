/**
 * ClassName: OnlineUserIO.java
 * Author: qiujy
 * CreateTime: 2009-4-23
 * EMAIL: qjyong@gmail.com
 * Copyright 2009 ++YONG All rights reserved.
 */
package server;

import java.io.*;

/** 在线客户端的IO流缓存类 */
public class OnlineClientIOCache {
	//针对同一个Socket中获取的流在全局范围中最好只包装一次,以免出错
	private ObjectInputStream ois;  //对象输入流
	private ObjectOutputStream oos; //对象输出流
	
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
