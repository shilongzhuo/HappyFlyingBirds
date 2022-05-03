import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class TravelWidget extends JFrame
{


    private JPanel panel = new JPanel();

    public TravelWidget()
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
        button1.setBounds(100,220,100,40);
        button2.setBounds(300,220,100,40);
        this.setTitle("�ο�ģʽ");
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
                    World wd=new World(1,null,null);
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
                    World wd=new World(2,null,null);
                    wd.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

                } catch (IOException | InterruptedException d) {
                    // TODO �Զ����ɵ� catch ��
                    d.printStackTrace();
                }
            }
        });

        panel.add(button1);
        panel.add(button2);
        panel.add(label);
        setResizable(false);
        this.add(panel);
        this.setLocationRelativeTo(null);
    }

}
