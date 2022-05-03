/**
 * ClassName: ServerListener.java
 * Author: qiujy
 * CreateTime: 2009-4-15
 * EMAIL: qjyong@gmail.com
 * Copyright 2009 ++YONG All rights reserved.
 */
package server.controller;

import java.io.*;
import java.net.Socket;
import java.text.*;
import java.util.concurrent.CopyOnWriteArrayList;
import server.*;
import server.model.service.UserService;
import common.model.entity.*;

/** ���������������� */
public class RequestProcessor implements Runnable{
	private Socket currentClientSocket;  //��ǰ��������������Ŀͻ���Socket
	
	public RequestProcessor(Socket currentClientSocket){
		this.currentClientSocket = currentClientSocket;
	}
	
	public void run() {
		boolean flag = true; //�Ƿ񲻼�ϼ���
		try{
			OnlineClientIOCache currentClientIOCache = new OnlineClientIOCache(
					new ObjectInputStream(currentClientSocket.getInputStream()), 
					new ObjectOutputStream(currentClientSocket.getOutputStream()));
			while(flag){ //��ͣ�ض�ȡ�ͻ��˷��������������
				//�������������ж�ȡ���ͻ����ύ���������
				Request request = (Request)currentClientIOCache.getOis().readObject();
				System.out.println("Server��ȡ�˿ͻ��˵�����:" + request.getAction());

				String actionName = request.getAction();   //��ȡ�����еĶ���
				if(actionName.equals("userRegiste")){      //�û�ע��
					registe(currentClientIOCache, request);
				}else if(actionName.equals("userLogin")){  //�û���¼
					login(currentClientIOCache, request);
				}else if("exit".equals(actionName)){       //����Ͽ�����
					flag = logout(currentClientIOCache, request);
				}else if("chat".equals(actionName)){       //����
					chat(request);
				}else if("shake".equals(actionName)){      //��
					shake(request);
				}else if("toSendFile".equals(actionName)){ //׼�������ļ�
					toSendFile(request);
				}else if("agreeReceiveFile".equals(actionName)){ //ͬ������ļ�
					agreeReceiveFile(request);
				}else if("refuseReceiveFile".equals(actionName)){ //�ܾ������ļ�
					refuseReceiveFile(request);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	/** �ܾ������ļ� */
	private void refuseReceiveFile(Request request) throws IOException {
		FileInfo sendFile = (FileInfo)request.getAttribute("sendFile");
		Response response = new Response();  //����һ����Ӧ����
		response.setType(ResponseType.REFUSERECEIVEFILE);
		response.setData("sendFile", sendFile);
		response.setStatus(ResponseStatus.OK);
		//�����󷽵�����������Ӧ
		OnlineClientIOCache ocic = DataBuffer.onlineUserIOCacheMap.get(sendFile.getFromUser().getId());
		this.sendResponse(ocic, response);
	}

	/** ͬ������ļ� */
	private void agreeReceiveFile(Request request) throws IOException {
		FileInfo sendFile = (FileInfo)request.getAttribute("sendFile");
		//������(���ͷ�)������������Ӧ
		Response response = new Response();  //����һ����Ӧ����
		response.setType(ResponseType.AGREERECEIVEFILE);
		response.setData("sendFile", sendFile);
		response.setStatus(ResponseStatus.OK);
		OnlineClientIOCache sendIO = DataBuffer.onlineUserIOCacheMap.get(sendFile.getFromUser().getId());
		this.sendResponse(sendIO, response);
		
		//����շ����������ļ�����Ӧ
		Response response2 = new Response();  //����һ����Ӧ����
		response2.setType(ResponseType.RECEIVEFILE);
		response2.setData("sendFile", sendFile);
		response2.setStatus(ResponseStatus.OK);
		OnlineClientIOCache receiveIO = DataBuffer.onlineUserIOCacheMap.get(sendFile.getToUser().getId());
		this.sendResponse(receiveIO, response2);
	}
	
	/** �ͻ����˳� */
	public boolean logout(OnlineClientIOCache oio, Request request) throws IOException{
		System.out.println(currentClientSocket.getInetAddress().getHostAddress()
				+ ":" + currentClientSocket.getPort() + "����");

		User user = (User)request.getAttribute("user");
		//�ѵ�ǰ���߿ͻ��˵�IO��Map��ɾ��
		DataBuffer.onlineUserIOCacheMap.remove(user.getId());
		//�������û�����Map��ɾ����ǰ�û�
		DataBuffer.onlineUsersMap.remove(user.getId());
			
		Response response = new Response();  //����һ����Ӧ����
		response.setType(ResponseType.LOGOUT);
		response.setData("logoutUser", user);
		oio.getOos().writeObject(response);  //����Ӧ�������ͻ���д
		oio.getOos().flush();
		currentClientSocket.close();  //�ر�����ͻ���Socket
		
		DataBuffer.onlineUserTableModel.remove(user.getId()); //�ѵ�ǰ�����û��������û���Model��ɾ��
		iteratorResponse(response);//֪ͨ�����������߿ͻ���
		
		return false;  //�Ͽ�����
	}
	/** ע�� */
	public void registe(OnlineClientIOCache oio, Request request) throws IOException {
		User user = (User)request.getAttribute("user");
		UserService userService = new UserService();
		userService.addUser(user);
		
		Response response = new Response();  //����һ����Ӧ����
		response.setStatus(ResponseStatus.OK);
		response.setData("user", user);
		
		oio.getOos().writeObject(response);  //����Ӧ�������ͻ���д
		oio.getOos().flush();
		
		//����ע���û���ӵ�RegistedUserTableModel��
		DataBuffer.registedUserTableModel.add(new String[]{
			String.valueOf(user.getId()),
			user.getPassword(),
			user.getNickname(),
			String.valueOf(user.getSex())
		});
	}
	
	/** ��¼ */
	public void login(OnlineClientIOCache currentClientIO, Request request) throws IOException {
		String idStr = (String)request.getAttribute("id");
		String password = (String) request.getAttribute("password");
		UserService userService = new UserService();
		User user = userService.login(Long.parseLong(idStr), password);
		
		Response response = new Response();  //����һ����Ӧ����
		if(null != user){
			if(DataBuffer.onlineUsersMap.containsKey(user.getId())){ //�û��Ѿ���¼��
				response.setStatus(ResponseStatus.OK);
				response.setData("msg", "�� �û��Ѿ��ڱ������ˣ�");
				currentClientIO.getOos().writeObject(response);  //����Ӧ�������ͻ���д
				currentClientIO.getOos().flush();
			}else { //��ȷ��¼
				DataBuffer.onlineUsersMap.put(user.getId(), user); //��ӵ������û�
				//���������û�
				response.setData("onlineUsers", 
						new CopyOnWriteArrayList<User>(DataBuffer.onlineUsersMap.values()));
				
				response.setStatus(ResponseStatus.OK);
				response.setData("user", user);
				currentClientIO.getOos().writeObject(response);  //����Ӧ�������ͻ���д
				currentClientIO.getOos().flush();
				
				//֪ͨ�����û�����������
				Response response2 = new Response();
				response2.setType(ResponseType.LOGIN);
				response2.setData("loginUser", user);
				iteratorResponse(response2);
				
				//�ѵ�ǰ���ߵ��û�IO��ӵ�����Map��
				DataBuffer.onlineUserIOCacheMap.put(user.getId(),currentClientIO);
				
				//�ѵ�ǰ�����û���ӵ�OnlineUserTableModel��
				DataBuffer.onlineUserTableModel.add(
						new String[]{String.valueOf(user.getId()), 
										user.getNickname(), 
										String.valueOf(user.getSex())});
			}
		}else{ //��¼ʧ��
			response.setStatus(ResponseStatus.OK);
			response.setData("msg", "�˺Ż����벻��ȷ��");
			currentClientIO.getOos().writeObject(response);
			currentClientIO.getOos().flush();
		}
	}
	
	/** ���� */
	public void chat(Request request) throws IOException {
		Message msg = (Message)request.getAttribute("msg");
		Response response = new Response();
		response.setStatus(ResponseStatus.OK);
		response.setType(ResponseType.CHAT);
		response.setData("txtMsg", msg);
		
		if(msg.getToUser() != null){ //˽��:ֻ��˽�ĵĶ��󷵻���Ӧ
			OnlineClientIOCache io = DataBuffer.onlineUserIOCacheMap.get(msg.getToUser().getId());
			sendResponse(io, response);
		}else{  //Ⱥ��:�����˷���Ϣ�����пͻ��˶�������Ӧ
			for(Long id : DataBuffer.onlineUserIOCacheMap.keySet()){
				if(msg.getFromUser().getId() == id ){	continue; }
				sendResponse(DataBuffer.onlineUserIOCacheMap.get(id), response);
			}
		}
	}
	
	/** ������ */
	public void shake(Request request)throws IOException {
		Message msg = (Message)request.getAttribute("msg");
		
		DateFormat df = new SimpleDateFormat("HH:mm:ss");
		StringBuffer sb = new StringBuffer();
		sb.append(" ").append(msg.getFromUser().getNickname())
			.append("(").append(msg.getFromUser().getId()).append(") ")
			.append(df.format(msg.getSendTime())).append("\n  ����������һ�����ڶ���\n");
		msg.setMessage(sb.toString());
		
		Response response = new Response();
		response.setStatus(ResponseStatus.OK);
		response.setType(ResponseType.SHAKE);
		response.setData("ShakeMsg", msg);
		
		OnlineClientIOCache io = DataBuffer.onlineUserIOCacheMap.get(msg.getToUser().getId());
		sendResponse(io, response);
	}
	
	/** ׼�������ļ� */
	public void toSendFile(Request request)throws IOException{
		Response response = new Response();
		response.setStatus(ResponseStatus.OK);
		response.setType(ResponseType.TOSENDFILE);
		FileInfo sendFile = (FileInfo)request.getAttribute("file");
		response.setData("sendFile", sendFile);
		//���ļ����շ�ת���ļ����ͷ�������
		OnlineClientIOCache ioCache = DataBuffer.onlineUserIOCacheMap.get(sendFile.getToUser().getId());
		sendResponse(ioCache, response);
	}
	
	/** ���������߿ͻ���������Ӧ */
	private void iteratorResponse(Response response) throws IOException {
		for(OnlineClientIOCache onlineUserIO : DataBuffer.onlineUserIOCacheMap.values()){
			ObjectOutputStream oos = onlineUserIO.getOos();
			oos.writeObject(response);
			oos.flush();
		}
	}
	
	/** ��ָ���ͻ���IO������������ָ����Ӧ */
	private void sendResponse(OnlineClientIOCache onlineUserIO, Response response)throws IOException {
		ObjectOutputStream oos = onlineUserIO.getOos();
		oos.writeObject(response);
		oos.flush();
	}
}