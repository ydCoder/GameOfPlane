package shoot;
import java.awt.image.BufferedImage;
import java.applet.Applet;
import java.awt.*;
import javax.imageio.ImageIO;
import javax.swing.*;
public class Introduce extends Frame{
	
	JLabel r;
	public Introduce(){
					
		
		
			 Image img=Toolkit.getDefaultToolkit().getImage("src/Img/1.jpg");//������Ϸͼ��
		 JFrame frame = new JFrame("���߼���");
	 frame.setIconImage(img);
			
		
	 r=new JLabel("��ӭ���� Javaս�� ף����ÿ��� ");
			frame. add(r);
			frame.setBounds(520,80,220,200);//xy ��С
			 frame.setVisible(true);//���ڿɼ�
			
			 frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//����رգ��˳�����
		}
	
	
		
}