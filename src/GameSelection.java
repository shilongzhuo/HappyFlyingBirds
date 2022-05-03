import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import client.ClientMain;


public class GameSelection extends JFrame
{


    private JPanel panel = new JPanel();

    public GameSelection(String username,String password)
    {
        JLabel label = new JLabel()
        {
            protected void paintComponent(Graphics g)
            {
                ImageIcon icon = new ImageIcon("src/images/select.jpg");
                g.drawImage(icon.getImage(), 0, 0, getWidth(), getHeight(), icon.getImageObserver());
            }
        };
        //���ð�ť������������Ϸ��˫����Ϸ������ģʽ
        JButton button1 = new JButton("������Ϸ");
//        button1.setContentAreaFilled(false);
        JButton button2 = new JButton("˫����Ϸ");
//        button2.setContentAreaFilled(false);
        JButton button3 = new JButton("����ģʽ");
//        button3.setContentAreaFilled(false);
        button1.setBounds(200,50,100,40);
        button2.setBounds(200,200,100,40);
        button3.setBounds(200,350,100,40);
        this.setTitle("�˺�ģʽ");
        panel.setLayout(null);
        this.setBounds(0, 0, 500, 500);
        label.setBounds(0, 0, getWidth(), getHeight());

        //��ť��������¼�
        button1.addActionListener(new ActionListener()//������Ϸ
        {
            public void actionPerformed(ActionEvent e)
            {
                try {
                    setVisible(false);
                    World wd=new World(1,username,password);
                    wd.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

                } catch (IOException | InterruptedException d) {
                    // TODO �Զ����ɵ� catch ��
                    d.printStackTrace();
                }
            }
        });
        button2.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                try {
                    setVisible(false);
                    World wd=new World(2, username, password);
                    wd.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

                } catch (IOException | InterruptedException d) {
                    // TODO �Զ����ɵ� catch ��
                    d.printStackTrace();
                }
            }
        });
        button3.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
              ClientMain cmain = new ClientMain();
              //  cmain.start();

            }
        });
        panel.add(button1);
        panel.add(button2);
        panel.add(button3);
        panel.add(label);
        this.add(panel);
        this.setLocationRelativeTo(null);
        setResizable(false);
    }
    public static void main(String[] args)
    {
//        GameSelection frame=new GameSelection(username,password);
////        getBackgroundPicture(bglabel,panel);
//        frame.setVisible(true);
    }
}


