import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;

public class Column {
    BufferedImage columnup,columndo;
    int x,yup,ydo,width,heightup,heightdo;
    int gap=200;
    int heightgap=gap+379;//�������¿ռ�ĸ߶�
    Random random;
    int ran;
    boolean getcoin;
    public Column(int x1) throws IOException {
        // TODO �Զ����ɵĹ��캯�����
        x=x1;
        columnup=ImageIO.read(getClass().getResource("images/Pipe_Up.png"));
        columndo=ImageIO.read(getClass().getResource("images/Pipe_Down.png"));
        width=columnup.getWidth();
        heightup=columnup.getHeight();
        heightdo=columndo.getHeight();
        random=new Random();
        yup=-300-150+random.nextInt(200);
        ydo=yup+heightgap;
        ran=random.nextInt(100);
    }
    public void paint(Graphics g) {
        g.drawImage(columnup,x,yup,null);
        g.drawImage(columndo,x,yup+heightgap,null);
    }
    public void step() {
        x--;
        if(x<-width) {//���õ���
            x=360;
            yup=-300-50+random.nextInt(200);
            ran=random.nextInt(100);
            getcoin=false;
        }
    }
}

