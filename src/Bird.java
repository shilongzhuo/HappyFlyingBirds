import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Bird {
    int x,y;//ΪС��ͼƬ����ת����
    BufferedImage bird;
    BufferedImage [] birds;
    int index=0;
    int g;//�������ٶ�
    double t;//���ʱ����
    double v0;//��ʼ�ٶ� ����/��
    double vt;//����ʱ���ٶ�
    double s;//�˶��ľ���
    double angle;//���нǶ�
    double size=26;
    public Bird(int x,int y,int l) throws IOException {
        this.x=x;
        this.y=y;
        bird=ImageIO.read(getClass().getResource("images/player"+l+"_0.png"));
        birds=new BufferedImage[8];
        for(int i=0;i<8;i++) {
            String s="images/player"+l+"_"+i+".png";
            birds[i]=ImageIO.read(getClass().getResource(s));
        }
        g=4;
        t=0.25;
        v0=20;

    }
    public Bird() {
        // TODO �Զ����ɵĹ��캯�����
    }
    public void paint(Graphics g)
    {
        //g.drawImage(bird, x, y, null);
        Graphics2D g2d=(Graphics2D)g;
        g2d.rotate(angle,x,y);
        int x1=x-bird.getWidth()/2;//����ͼƬ
        int y1=y-bird.getHeight()/2;
        g.drawImage(bird, x1, y1, null);
        g2d.rotate(-angle, x, y);
    }
    public void step() {
        double vt1=vt;//��ǰ�ٶ�
        double v=vt1-g*t;//���㴹ֱ�����˶�������ʱ��t�Ժ���ٶ�
        vt=v;//��Ϊ��һ�μ���λ��ʱ�ĳ��ٶ�
        s=vt1*t-0.5*g*t*t;//���о���
        y=y-(int)s;
        angle=-Math.atan(s/15);
        index++;
        bird=birds[index/8%8];//����8��index=0,1,2,3,4,5,6��7,8,��index/8%6=00000000111111
    }
    public void flappy() {
        vt=v0;
        new AudioPlayWave("src/images/��.wav").start();
    }
    public boolean hit(Column col1,Column col2,Ground gro) {
        if(y-size/2<=0) {
            new AudioPlayWave("src/images/����.wav").start();
            return true;
        }
        if(y+size/2>=gro.y) {
            new AudioPlayWave("src/images/����.wav").start();
            return true;
        }
        if(hitColumn(col1)||hitColumn(col2)) {
            new AudioPlayWave("src/images/����.wav").start();
            return true;
        }
        return false;
    }
    public boolean hitColumn(Column col) {
        if(x>col.x-size/2&&x<col.x+col.width+size/2) {
            if(y<col.yup+col.heightgap-size/2&&y>col.yup+420+size/2) {
                return false;
            }
            return true;
        }
        return false;
    }
    public boolean pass(Column col1,Column col2) {
        return col1.x==x||col2.x==x;//����ר��
    }
    public boolean hitCoin(GoldCoin coin) {
        if(x+size/2>coin.x&&x<coin.x+coin.size&&y>coin.y-coin.size&&y-size/2<coin.y) {
            new AudioPlayWave("src/images/���.wav").start();
            return true;
        }
        return false;
    }
}


