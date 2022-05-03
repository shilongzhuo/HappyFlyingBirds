/**
 * ClassName: JFrameShaker.java
 * Author: qiujy
 * CreateTime: 2009-4-23
 * EMAIL: qjyong@gmail.com
 * Copyright 2009 ++YONG All rights reserved.
 */
package client.util;

import java.awt.Point;
import java.awt.event.*;

import javax.swing.*;

/** �������� */
public class JFrameShaker {
	/** ���ھ����������һζ���������(����) */
	public static final int SHAKE_DISTANCE = 4;
	/** ���ڻζ�һ�����ڣ��м�,��,�м�,��,�м䣩���õ�ʱ��(ms). ���ֵԽС,�ζ��ľ�Խ�� */
	public static final double SHAKE_CYCLE = 10;
	/** �ζ���ʱ��(ms) */
	public static final int SHAKE_DURATION = 600;

	private JFrame frame;
	private Point oldLocation;
	private long startTime;
	private Timer shakeTimer;

	public JFrameShaker(JFrame frame) {
		this.frame = frame;
	}

	public void startShake() {
		oldLocation = frame.getLocation();// ��ȡ���ڵ�ԭʼλ��

		startTime = System.currentTimeMillis(); // ��ʼ��ʱ
		shakeTimer = new Timer((int) SHAKE_CYCLE / 5, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				long elapsed = System.currentTimeMillis() - startTime;// �����񶯵���ʱ
				// ����ʱ������ĳһʱ�̻ζ��ķ���
				double waveOffset = (elapsed % SHAKE_CYCLE) / SHAKE_CYCLE;
				double angle = waveOffset * Math.PI;
				double angley = waveOffset * Math.PI;
				int shakeX = (int) ((Math.sin(angle) * SHAKE_DISTANCE) + oldLocation.x);
				int shakeY = (int) ((Math.sin(angley) * SHAKE_DISTANCE) + oldLocation.y);
				frame.setLocation(shakeX, shakeY);
				if (elapsed >= SHAKE_DURATION) { // ��ʱ�����˾�ֹͣ
					stopShake();
				}
			}
		});
		shakeTimer.start(); // ������ʱ����
	}

	/** ֹͣ�� */
	public void stopShake() {
		shakeTimer.stop();
		frame.setLocation(oldLocation);
	}

	public static void main(String asrg[]) {
		final JFrame frame = new JFrame();
		JButton button = new JButton("��");
		frame.setBounds(300, 200, 500, 500);
		frame.getContentPane().add(button);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFrameShaker dialog1 = new JFrameShaker(frame);
				dialog1.startShake();
			}
		});
	}
}