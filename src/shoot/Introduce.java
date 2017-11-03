package shoot;
import java.awt.image.BufferedImage;
import java.applet.Applet;
import java.awt.*;
import javax.imageio.ImageIO;
import javax.swing.*;
public class Introduce extends Frame{
	
	JLabel r;
	public Introduce(){
					
		
		
			 Image img=Toolkit.getDefaultToolkit().getImage("src/Img/1.jpg");//设置游戏图标
		 JFrame frame = new JFrame("作者寄语");
	 frame.setIconImage(img);
			
		
	 r=new JLabel("欢迎来到 Java战机 祝您玩得开心 ");
			frame. add(r);
			frame.setBounds(520,80,220,200);//xy 大小
			 frame.setVisible(true);//窗口可见
			
			 frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//点击关闭，退出程序
		}
	
	
		
}