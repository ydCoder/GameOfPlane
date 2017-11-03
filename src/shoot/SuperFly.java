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
	protected BufferedImage image;// 飞行物的当前显示的图片(贴图) 
	protected double down;//每次下移动的距离  在FlyingObject中添加两个构造器 (都有图片参数)
	public SuperFly(BufferedImage image){//根据图片的宽高，计算出飞机出场位置//调用java提供的方法获取图片的宽
		w = image.getWidth();
		h = image.getHeight();
		Random random = new Random();
		x = random.nextInt(480-(int)w);
		y = -h;
		this.image = image;
		down = random.nextDouble()*3+0.8;//返回0-3之间的数 0-1*3 = 0-3.0+0.8,大小飞机移动的向下移动的距离
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
		y += down;//累加下移距离
	}
	public boolean knock(SuperFly b){// 在父类 类上添加碰撞检查方法  子弹
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
	}//利用重写，修改paint方法
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
	public void moveTo(int x,int y){//鼠标控制我机的位置点
		this.x = x-w/2;//用飞行对象的坐标减去它本身的宽度则为中点坐标
		this.y = y-h/2;
	}
	public Myplane(){
	super(188,516,img);//战机出场时的位置
	}
		public Bullet[] shoot(){/** 在fighter中添加设计的方法 */
		double x = this.x+w/2;//飞机子弹发射的位置
		double y = this.y-16;
		Bullet bulletLocation = new Bullet((int)x,(int)y);
		return new Bullet[]{bulletLocation};//返回一个数组类型,不能返回b，Bullet所需的是一个数组类型
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
	public void move(){//** 重写子弹的飞行方向为上
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