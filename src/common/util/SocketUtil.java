/**
 * ClassName: SocketUtil.java
 * Author: qiujy
 * CreateTime: 2009-4-24
 * EMAIL: qjyong@gmail.com
 * Copyright 2009 ++YONG All rights reserved.
 */
package common.util;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/** ���������ع����� */
public class SocketUtil {
	/** �ر�Socket */
	public static void close(Socket socket) {
		if (socket != null && !socket.isClosed()) {
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/** �ر�ServerSocket */
	public static void close(ServerSocket ss) {
		if (ss != null && !ss.isClosed()) {
			try {
				ss.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
