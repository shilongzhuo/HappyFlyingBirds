/**
 * ClassName: UserService.java
 * Author: qiujy
 * CreateTime: 2009-4-15
 * EMAIL: qjyong@gmail.com
 * Copyright 2009 ++YONG All rights reserved.
 */
package server.model.service;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import server.DataBuffer;

import common.model.entity.User;
import common.util.IOUtil;

/** �û�������ص�ҵ���߼��� */
public class UserService {
	private static int idCount = 4; //id
	
	/** �����û� */
	public void addUser(User user){
		user.setId(++idCount);
		List<User> users = loadAllUser();
		users.add(user);
		saveAllUser(users);
	}
	
	/** �û���¼ */
	public User login(long id, String password){
		User result = null;
		List<User> users = loadAllUser();
		for (User user : users) {
			if(id == user.getId() && password.equals(user.getPassword())){
				result = user;
				break;
			}
		}
		return result;
	}
	
	/** ����ID�����û� */
	public User loadUser(long id){
		User result = null;
		List<User> users = loadAllUser();
		for (User user : users) {
			if(id == user.getId()){
				result = user;
				break;
			}
		}
		return result;
	}
	
	
	/** ���������û� */
	@SuppressWarnings("unchecked")
	public List<User> loadAllUser() {
		List<User> list = null;
		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(
					new FileInputStream(
							DataBuffer.configProp.getProperty("dbpath")));
			
			list = (List<User>)ois.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			IOUtil.close(ois);
		}
		return list;
	}
	
	private void saveAllUser(List<User> users) {
		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(
						new FileOutputStream(
								DataBuffer.configProp.getProperty("dbpath")));
			//д���û���Ϣ
			oos.writeObject(users);
			oos.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			IOUtil.close(oos);
		}
	}
	

	
	/** ��ʼ�����������û� */
	public void initUser(){
		User user = new User("123", "׿����", 'm', 0);
		user.setId(1);
		
		User user2 = new User("123", "�ſ˻�", 'm', 1);
		user2.setId(2);
		
		User user3 = new User("123", "����", 'f', 2);
		user3.setId(3);

		User user4 = new User("123", "갷���", 'f', 3);
		user4.setId(4);

		List<User> users = new CopyOnWriteArrayList<User>();
		users.add(user);
		users.add(user2);
		users.add(user3);
		users.add(user4);
		
		this.saveAllUser(users);
	}
	
	public static void main(String[] args){
		new UserService().initUser();
		List<User> users = new UserService().loadAllUser();
		for (User user : users) {
			System.out.println(user);
		}
	}
}
