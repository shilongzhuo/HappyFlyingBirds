/**
 * ClassName: ClientThread.java
 * Author: qiujy
 * CreateTime: 2009-4-22
 * EMAIL: qjyong@gmail.com
 * Copyright 2009 ++YONG All rights reserved.
 */
package client;

import java.io.*;
import java.net.*;
import javax.swing.*;
import client.ui.ChatFrame;
import client.util.*;
import common.model.entity.*;
import common.util.*;

/** �ͻ����̣߳����ϼ������������͹�������Ϣ */
public class ClientThread extends Thread {
	private JFrame currentFrame;  //��ǰ����
	
	public ClientThread(JFrame frame){
		currentFrame = frame;
	}
	
	public void run() {
		try {
			while (DataBuffer.clientSeocket.isConnected()) {
				Response response = (Response) DataBuffer.ois.readObject();
				ResponseType type = response.getType();
				
				System.out.println("��ȡ����Ӧ���ݣ�" + type);
				if (type == ResponseType.LOGIN) {
					User newUser = (User)response.getData("loginUser");
					DataBuffer.onlineUserListModel.addElement(newUser);
					
					ChatFrame.onlineCountLbl.setText(
							"�����û��б�("+ DataBuffer.onlineUserListModel.getSize() +")");
					ClientUtil.appendTxt2MsgListArea("��ϵͳ��Ϣ���û�"+newUser.getNickname() + "�����ˣ�\n"); 
				}else if(type == ResponseType.LOGOUT){
					User newUser = (User)response.getData("logoutUser");
					DataBuffer.onlineUserListModel.removeElement(newUser);
					
					ChatFrame.onlineCountLbl.setText(
							"�����û��б�("+ DataBuffer.onlineUserListModel.getSize() +")");
					ClientUtil.appendTxt2MsgListArea("��ϵͳ��Ϣ���û�"+newUser.getNickname() + "�����ˣ�\n");

				}else if(type == ResponseType.CHAT){ //����
					Message msg = (Message)response.getData("txtMsg");
					ClientUtil.appendTxt2MsgListArea(msg.getMessage());
				}else if(type == ResponseType.SHAKE){ //��
					Message msg = (Message)response.getData("ShakeMsg");
					ClientUtil.appendTxt2MsgListArea(msg.getMessage());
					new JFrameShaker(this.currentFrame).startShake();
				}else if(type == ResponseType.TOSENDFILE){ //׼�������ļ�
					toSendFile(response);
				}else if(type == ResponseType.AGREERECEIVEFILE){ //�Է�ͬ������ļ�
					sendFile(response);
				}else if(type == ResponseType.REFUSERECEIVEFILE){ //�Է��ܾ������ļ�
					ClientUtil.appendTxt2MsgListArea("���ļ���Ϣ���Է��ܾ����գ��ļ�����ʧ�ܣ�\n");
				}else if(type == ResponseType.RECEIVEFILE){ //��ʼ�����ļ�
					receiveFile(response);
				}
			}
		} catch (IOException e) {
			//e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} 
	}

	/** �����ļ� */
	private void sendFile(Response response) {
		final FileInfo sendFile = (FileInfo)response.getData("sendFile");
		
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		Socket socket = null;
		try {
			socket = new Socket(sendFile.getDestIp(),sendFile.getDestPort());//�׽�������
			bis = new BufferedInputStream(new FileInputStream(sendFile.getSrcName()));//�ļ�����
			bos = new BufferedOutputStream(socket.getOutputStream());//�ļ�д��
			
			byte[] buffer = new byte[1024];
			int n = -1;
			while ((n = bis.read(buffer)) != -1){
				bos.write(buffer, 0, n);
			}
			bos.flush();
			synchronized (this) {
				ClientUtil.appendTxt2MsgListArea("���ļ���Ϣ���ļ��������!\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			IOUtil.close(bis,bos);
			SocketUtil.close(socket);
		}
	}
	
	/** �����ļ� */
	private void receiveFile(Response response) {
		final FileInfo sendFile = (FileInfo)response.getData("sendFile");
		
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		ServerSocket serverSocket = null;
		Socket socket = null;
		try {
			serverSocket = new ServerSocket(sendFile.getDestPort());
			socket = serverSocket.accept(); //����
			bis = new BufferedInputStream(socket.getInputStream());//�����
			bos = new BufferedOutputStream(new FileOutputStream(sendFile.getDestName()));//����д��
			
			byte[] buffer = new byte[1024];
			int n = -1;
			while ((n = bis.read(buffer)) != -1){
				bos.write(buffer, 0, n);
			}
			bos.flush();
			synchronized (this) {
				ClientUtil.appendTxt2MsgListArea("���ļ���Ϣ���ļ��������!�����["
						+ sendFile.getDestName()+"]\n");
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			IOUtil.close(bis,bos);
			SocketUtil.close(socket);
			SocketUtil.close(serverSocket);
		}
	}

	/** ׼�������ļ�	 */
	private void toSendFile(Response response) {
		FileInfo sendFile = (FileInfo)response.getData("sendFile");
		
		String fromName = sendFile.getFromUser().getNickname() 
				+ "(" + sendFile.getFromUser().getId() + ")";
		String fileName = sendFile.getSrcName()
			.substring(sendFile.getSrcName().lastIndexOf(File.separator)+1);
		
		int select = JOptionPane.showConfirmDialog(this.currentFrame, 
				fromName + " ���������ļ� [" + fileName+ "]!\nͬ�������?",
				"�����ļ�", JOptionPane.YES_NO_OPTION);
		try {
			Request request = new Request();
			request.setAttribute("sendFile", sendFile);
			
			if (select == JOptionPane.YES_OPTION) {
				JFileChooser jfc = new JFileChooser();
				jfc.setSelectedFile(new File(fileName));
				int result = jfc.showSaveDialog(this.currentFrame);
				
				if (result == JFileChooser.APPROVE_OPTION){
					//����Ŀ�ĵ��ļ���
					sendFile.setDestName(jfc.getSelectedFile().getCanonicalPath());
					//����Ŀ��ص�IP�ͽ����ļ��Ķ˿�
					sendFile.setDestIp(DataBuffer.ip);
					sendFile.setDestPort(DataBuffer.RECEIVE_FILE_PORT);
					
					request.setAction("agreeReceiveFile");
					ClientUtil.appendTxt2MsgListArea("���ļ���Ϣ������ͬ��������� "
							+ fromName +" ���ļ������ڽ����ļ� ...\n");
				} else {
					request.setAction("refuseReceiveFile");
					ClientUtil.appendTxt2MsgListArea("���ļ���Ϣ�����Ѿܾ��������� "
							+ fromName +" ���ļ�!\n");
				}	
			} else {
				request.setAction("refuseReceiveFile");
				ClientUtil.appendTxt2MsgListArea("���ļ���Ϣ�����Ѿܾ��������� "
						+ fromName +" ���ļ�!\n");
			}
			
			ClientUtil.sendTextRequest2(request);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
