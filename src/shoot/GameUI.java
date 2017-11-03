package shoot;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;
import javax.media.NoPlayerException;
import javax.swing.JPanel;
public class GameUI extends JPanel{
	private  Sky sky;//游戏背景
	private SuperFly[] fly;//敌机的父类对象数组
	private Myplane  fighter;//我方战机
	private  Bullet[] bullets;//子弹
	private GameOver Over;
	private int state = START;//判断状态
	private static final int START=0;
	private static final int OVER=1;
	public GameUI(){
		sky = new Sky();
		fly = new SuperFly[0];//创建长度为0的数组，表示最开始的时候天空中没有一架飞机
		fighter = new Myplane();
		bullets = new Bullet[0];	
		Over = new GameOver();
	}
	private Timer timer;//TimerTask的子类对象run控制进程
	public void execute(){
	MouseAdapter m= new MouseAdapter(){//开始监听鼠标事件 
			public void mouseMoved(MouseEvent e){
			int x = e.getX();//重写父类MouseAdapter的方法//当鼠标在当前面板上移动了以后，就会执行这个方法
			int y = e.getY();
			fighter.moveTo(x,y);//重写。添加鼠标事件，用适配器MouseAdapter可以不用实现所有的方法，这里写的是当鼠标移动到哪英雄飞机随之移动，moveto在战机方法内
				}
			public void mouseExited(MouseEvent e){
				state = OVER;
					try{
						new MyPlayer(new File("over.wav")).start();
					}catch(NoPlayerException|IOException  e1){}
			}
			public void mouseClicked(MouseEvent e){
				if(state==OVER){
					score=0;//当游戏结束时，鼠标点击窗体，游戏继续
					repaint();//重新开始
					state = START;
					try{
					new MyPlayer(new File("start.wav")).start();
				}catch(NoPlayerException|IOException  e1){}
				}
			}
		};
		this.addMouseListener(m);//鼠标点击事件,将监听器添加到当前面板中
		this.addMouseMotionListener(m);//鼠标动作事件
		timer = new Timer();//启动定时器
		timer.schedule(new TimerTask(){
			public void run(){//任务每次被调度时将执行RUN方法,Timer是一个抽象类，它的子类run方法用来安排任务的执行逻辑
				flyIn();//敌机进场
				shootTime();//子弹射击间隔时间
				flyIn();//敌机出现
				flyMove();//物体移动
				shootIng();//子弹射中敌机，则敌机消失
				ruin();//当被射中的飞机消失不见
				repaint();//画面移动必须,component中己有的图形发生变化后不会立刻显示，须使用repaint方法重绘游戏对象
				}
		},0,20);//0是初始执行时间，17是每隔17毫秒执行一次run方法
	}
	//敌机进场
	private long nextTime;
	public void flyIn(){
		long now =System.currentTimeMillis();//该方法的作用是返回当前的计算机时间，
		if(now<nextTime){
		return;
}
		nextTime = now + 2017/3;//下一波飞机出现的时间调控
		SuperFly	 obj = randomOne();
		fly= Arrays.copyOf(fly, fly.length+1);//java中数组对象大小不可变的，利用数组复制方式扩容
		fly[fly.length-1] = obj;
		if(fly.length>3){//当界面出现的敌机大于5时，游戏结束
		state=OVER;
		bullets = new Bullet[0]; fly = new SuperFly[0]; 
		try{
			new MyPlayer(new File("over.wav")).start();
			}catch(NoPlayerException|IOException  e1){}
		}
	}
	public static SuperFly randomOne(){
		int random=(int)(Math.random()*8);//产生大于0小于8的整数，用来获取大小敌机
		switch(random){
		case 1:
		case 2:
		default: return new  BossPlane();
		}
	}
		private long nextShootTime = 0;/** 在World中添加有时间控制的射击方法 */
		public void shootTime(){
		long now = System.currentTimeMillis();
	if(now<nextShootTime){
				return;
			}
			nextShootTime = now +222;
			//射击:将hero射出来的子弹添加到bullets数组
			Bullet[] ary = fighter.shoot();//Myplane里的方法
			bullets = Arrays.copyOf(bullets, bullets.length+ary.length);//将bullets和ary合并
			System.arraycopy(ary, 0, bullets, bullets.length-ary.length, ary.length);
		}
	public void shootIng() {//检测碰撞------检查每个飞机和每个子弹，是否发生了碰撞，如果子弹碰到了有命的飞机(life>0)：子弹没命，飞机-
		for(Bullet b:bullets){
			iShooted(b);
			}
		}
	private int score = 0;
	private void iShooted(Bullet b) {
		for(SuperFly obj:fly){
			if(obj.knock(b)){//检查子弹是否与飞机发生碰撞
				try{new MyPlayer(new File("1.wav")).start();}catch(NoPlayerException|IOException  e1){}//*/C声音重叠
					obj.life--;
					if(obj.life==0){//如果击中以后，lif为0，再计算得分
						int s = BossPlane.getScore();//分数累计
							score+=s;
						}
					}
				}
			}
	public void flyMove() {//飞机移动坐标
		for(SuperFly obj:fly){
			obj.move();
		}
		for(Bullet bul:bullets){
			bul.move();//子弹的移动方式重写了，因为它的移动方向与敌机相反
		}
	}
	public void ruin() {//销毁没命的飞行体 
		SuperFly[] ary = {};
				for(int i=0;i<fly.length;i++){
					if(fly[i].life>0){
						ary=Arrays.copyOf(ary, ary.length+1);
						ary[ary.length-1]=fly[i];
					}
				}
				fly=ary;
			}
		public void paint(Graphics g){//重写（修改）JPanel类的paint方法,Graphics g 是Swing提供的画笔对象
		if(state==START)
		{
			sky.paint(g);
			fighter.paint(g);
			for(int i=0;i<fly.length;i++){
				fly[i].paint(g);
			}
			for (int i=0; i<bullets.length;i++){
				bullets[i].paint(g);
			}
			g.drawString("SCORE:"+score, 366, 600);//绘制右下角得分数
			}
		 if(state==OVER){
			 g.drawImage(Over.image,35,0,null);
				fighter = new Myplane();
				fly = new SuperFly [0];
				bullets = new Bullet[0];
				if(score>1000)
					g.drawString("最终得分:"+score+"    你真棒！", 166, 200);
				else 
				g.drawString("最终得分:"+score+"    再来一次吧，加油！",166, 200);}
			}
		}
		
		