import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
public class Ground {
    BufferedImage ground;
    int x,y;
    public Ground() throws IOException {
        // TODO �Զ����ɵĹ��캯�����
        ground=ImageIO.read(getClass().getResource("images/ground.png"));
        y=448-60;

    }
    public void step() {
        x--;
        if(x<-100) {//���õ���
            x=0;
        }
    }
    public void paint(Graphics g) {
        //����ͼƬ
        g.drawImage(ground, x, y,null);
    }


}
