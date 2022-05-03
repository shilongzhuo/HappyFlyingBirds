/**
 * ClassName: IOUtil.java
 * Author: qiujy
 * CreateTime: 2009-4-24
 * EMAIL: qjyong@gmail.com
 * Copyright 2009 ++YONG All rights reserved.
 */
package common.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/** IO��������ع����� */
public class IOUtil {
	/** �ر��ֽ������� */
	public static void close(InputStream is){
		if(null != is){
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}	
		}
	}
	/** �ر��ֽ������ */
	public static void close(OutputStream os){
		if(null != os){
			try {
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}	
		}
	}
	
	/** �ر��ֽ�������������� */
	public static void close(InputStream is,OutputStream os){
		close(is);
		close(os);
	}
}
