package shoot;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.util.Random;
public abstract class  SuperFly{
	protected double x;
	protected double y;
	protected double w;
	protected double h;
	protected int life = 30;
	protected BufferedImage image;// ������ĵ�ǰ��ʾ��ͼƬ(��ͼ) 
	protected double down;//ÿ�����ƶ��ľ���  ��FlyingObject��������������� (����ͼƬ����)
	public SuperFly(BufferedImage image){//����ͼƬ�Ŀ�ߣ�������ɻ�����λ��//����java�ṩ�ķ�����ȡͼƬ�Ŀ�
		w = image.getWidth();
		h = image.getHeight();
		Random random = new Random();
		x = random.nextInt(480-(int)w);
		y = -h;
		this.image = image;
		down = random.nextDouble()*3+0.8;//����0-3֮����� 0-1*3 = 0-3.0+0.8,��С�ɻ��ƶ��������ƶ��ľ���
}
	public SuperFly(int x,int y,BufferedImage image){
		this.x = x;
		this.y = y;
		this.w = image.getWidth();
		this.h = image.getHeight();
		this.image = image;
	}
	public void paint(Graphics g){
		g.drawImage(image,(int)x,(int)y,null);
	}
	public void move(){
		y += down;//�ۼ����ƾ���
	}
	public boolean knock(SuperFly b){// �ڸ��� ���������ײ��鷽��  �ӵ�
		double x1 = this.x-b.w;
		double x2 = this.x+this.w;
		double y1 = this.y-b.h;
		double y2 = this.y+this.h;
		return x1<b.x && b.x<x2 && y1<b.y && b.y<y2;
	}
}
 class Sky extends SuperFly  {
	private static BufferedImage img;
	static{
		try{
			img = ImageIO.read(Sky.class.getClassLoader().getResource("shoot/background.jpg"));
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public Sky(){
	super(0,0,img);
	}//������д���޸�paint����
}
	class Myplane extends SuperFly{
	private static BufferedImage img;
	static{
		try{
			img = ImageIO.read(Myplane.class.getClassLoader().getResource("shoot/Myplane.png"));
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public void moveTo(int x,int y){//�������һ���λ�õ�
		this.x = x-w/2;//�÷��ж���������ȥ������Ŀ����Ϊ�е�����
		this.y = y-h/2;
	}
	public Myplane(){
	super(188,516,img);//ս������ʱ��λ��
	}
		public Bullet[] shoot(){/** ��fighter�������Ƶķ��� */
		double x = this.x+w/2;//�ɻ��ӵ������λ��
		double y = this.y-16;
		Bullet bulletLocation = new Bullet((int)x,(int)y);
		return new Bullet[]{bulletLocation};//����һ����������,���ܷ���b��Bullet�������һ����������
		}
	}
	
	 class Bullet extends SuperFly{
		private static BufferedImage img;
		static{
			try{
				img = ImageIO.read(Bullet.class.getClassLoader().getResource("shoot/Bullet.png"));
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		public Bullet(int x,int y){
			super(x,y,img);
		}
	public void move(){//** ��д�ӵ��ķ��з���Ϊ��
			this.y-=5;
		}
	}
	 
	  class GameOver extends SuperFly{
	 	private static BufferedImage img;
	 	static{
	 		try{
	 			img = ImageIO.read(GameOver.class.getClassLoader().getResource("shoot/gameover.png"));
	 		}catch(Exception e){
	 			e.getStackTrace();
	 		}
	 	}
	 	public GameOver(){
	 		super(img);
	 	}
	 }
	 
	 class BossPlane extends SuperFly{
	  	private static BufferedImage img;
	  	static{
	  		try{
	  			img = ImageIO.read(BossPlane.class.getClassLoader().getResource("shoot/BossPlane.png"));
	  		}catch(Exception e){
	  			e.printStackTrace();
	  		}
	  	}
	  	public BossPlane(){
	  		super(img);
	  		life = 10;
	  	}
	  	public  static int getScore() {
	  		return 50;
	  	}
	  }