/**
 * ClassName: LoginFrame.java
 * Author: qiujy
 * CreateTime: 2009-4-22
 * EMAIL: qjyong@gmail.com
 * Copyright 2009 ++YONG All rights reserved.
 */
package client.ui;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.List;
import javax.swing.*;
import javax.swing.border.*;
import client.DataBuffer;
import client.util.ClientUtil;
import common.model.entity.*;


/** ��¼���� */
public class LoginFrame extends JFrame {
	private static final long serialVersionUID = -3426717670093483287L;

	private JTextField idTxt;
	private JPasswordField pwdFld;
	
	public LoginFrame(){
		this.init();
		setVisible(true);
	}


	public void init(){
		this.setTitle("Happy Fly Bird��¼");
		this.setSize(330, 230);
		//����Ĭ�ϴ�������Ļ����
		int x = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		int y = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((x - this.getWidth()) / 2, (y-this.getHeight())/ 2);
		this.setResizable(false);

		this.getContentPane().setLayout(null);
		JPanel imPanel = (JPanel)(this.getContentPane());
		imPanel.setOpaque(false);//�����������Ϊ͸��
		imPanel.setLayout(new BorderLayout());
		ImageIcon iconbg = new ImageIcon("src/images/bg1.jpg");
		JLabel labelbg = new JLabel(iconbg);//��һ����ǩ�м���ͼƬ
		labelbg.setBounds(0, 0, this.getWidth(), this.getHeight());
		//iconbg.setImage(iconbg.getImage().getScaledInstance(labelbg.getWidth(), labelbg.getHeight(), Image.SCALE_DEFAULT));//ͼƬ����Ӧ��ǩ��С
		this.getLayeredPane().add(labelbg, Integer.valueOf(Integer.MIN_VALUE));//



		//��Logo���õ�JFrame�ı���
		Icon icon = new ImageIcon("images/logo.png");
		JLabel label = new JLabel(icon);
		this.getContentPane().add(label, BorderLayout.NORTH);

		//��¼��Ϣ���
		JPanel mainPanel = new JPanel();
         mainPanel.setOpaque(false);
		Border border = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
		mainPanel.setBorder(BorderFactory.createTitledBorder(border, "�����¼��Ϣ", TitledBorder.CENTER,TitledBorder.TOP));
		this.getContentPane().add(mainPanel, BorderLayout.CENTER);
		mainPanel.setLayout(null);
		
		JLabel nameLbl = new JLabel("�˺�:");
		nameLbl.setBounds(50, 30, 40, 22);
		mainPanel.add(nameLbl);
		idTxt = new JTextField();
		idTxt.setBounds(95, 30, 150, 22);
		idTxt.requestFocusInWindow();//�û�����ý���
		mainPanel.add(idTxt);
		
		JLabel pwdLbl = new JLabel("����:");
		pwdLbl.setBounds(50, 60, 40, 22);
		mainPanel.add(pwdLbl);
		pwdFld = new JPasswordField();
		pwdFld.setBounds(95, 60, 150, 22);
		mainPanel.add(pwdFld);
		
		//��ť��������JFrame���ϱ�
		JPanel btnPanel = new JPanel();
		btnPanel.setOpaque(false);
		this.getContentPane().add(btnPanel, BorderLayout.SOUTH);
		btnPanel.setLayout(new BorderLayout());
		btnPanel.setBorder(new EmptyBorder(2, 8, 4, 8)); 

		JButton registeBtn = new JButton("ע��");
		btnPanel.add(registeBtn, BorderLayout.WEST);
		JButton submitBtn = new JButton("��¼");
		btnPanel.add(submitBtn, BorderLayout.EAST);
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);



		//�رմ���
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e) {
				Request req = new Request();
				req.setAction("exit");
				try {
					ClientUtil.sendTextRequest(req);
				} catch (IOException ex) {
					ex.printStackTrace();
				}finally{
					System.exit(0);
				}
			}
		});
		
		//ע��
		registeBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				new RegisterFrame();  //��ע�ᴰ��
			}
		});
		
		//"��¼"
		submitBtn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				login();
			}
		});
	}

	/** ��¼ */
	@SuppressWarnings("unchecked")
	private void login() {
		if (idTxt.getText().length() == 0 
				|| pwdFld.getPassword().length == 0) {
			JOptionPane.showMessageDialog(LoginFrame.this, 
					"�˺ź������Ǳ����",
					"��������",JOptionPane.ERROR_MESSAGE);
			idTxt.requestFocusInWindow();
			return;
		}
		
		if(!idTxt.getText().matches("^\\d*$")){
			JOptionPane.showMessageDialog(LoginFrame.this, 
					"�˺ű���������",
					"��������",JOptionPane.ERROR_MESSAGE);
			idTxt.requestFocusInWindow();
			return;
		}
		
		Request req = new Request();
		req.setAction("userLogin");
		req.setAttribute("id", idTxt.getText());
		req.setAttribute("password", new String(pwdFld.getPassword()));
		
		//��ȡ��Ӧ
		Response response = null;
		try {
			response = ClientUtil.sendTextRequest(req);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		if(response.getStatus() == ResponseStatus.OK){
			//��ȡ��ǰ�û�
			User user2 = (User)response.getData("user");
			if(user2!= null){ //��¼�ɹ�
				DataBuffer.currentUser = user2;
				//��ȡ��ǰ�����û��б�
				DataBuffer.onlineUsers = (List<User>)response.getData("onlineUsers");
				
				LoginFrame.this.dispose();
				new ChatFrame();  //�����촰��
			}else{ //��¼ʧ��
				String str = (String)response.getData("msg");
				JOptionPane.showMessageDialog(LoginFrame.this, 
							str,
							"��¼ʧ��",JOptionPane.ERROR_MESSAGE);
			}
		}else{
			JOptionPane.showMessageDialog(LoginFrame.this, 
					"�������ڲ��������Ժ����ԣ�����","��¼ʧ��",JOptionPane.ERROR_MESSAGE);
		}
	}
}