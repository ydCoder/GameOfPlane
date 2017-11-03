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
	private  Sky sky;//��Ϸ����
	private SuperFly[] fly;//�л��ĸ����������
	private Myplane  fighter;//�ҷ�ս��
	private  Bullet[] bullets;//�ӵ�
	private GameOver Over;
	private int state = START;//�ж�״̬
	private static final int START=0;
	private static final int OVER=1;
	public GameUI(){
		sky = new Sky();
		fly = new SuperFly[0];//��������Ϊ0�����飬��ʾ�ʼ��ʱ�������û��һ�ܷɻ�
		fighter = new Myplane();
		bullets = new Bullet[0];	
		Over = new GameOver();
	}
	private Timer timer;//TimerTask���������run���ƽ���
	public void execute(){
	MouseAdapter m= new MouseAdapter(){//��ʼ��������¼� 
			public void mouseMoved(MouseEvent e){
			int x = e.getX();//��д����MouseAdapter�ķ���//������ڵ�ǰ������ƶ����Ժ󣬾ͻ�ִ���������
			int y = e.getY();
			fighter.moveTo(x,y);//��д���������¼�����������MouseAdapter���Բ���ʵ�����еķ���������д���ǵ�����ƶ�����Ӣ�۷ɻ���֮�ƶ���moveto��ս��������
				}
			public void mouseExited(MouseEvent e){
				state = OVER;
					try{
						new MyPlayer(new File("over.wav")).start();
					}catch(NoPlayerException|IOException  e1){}
			}
			public void mouseClicked(MouseEvent e){
				if(state==OVER){
					score=0;//����Ϸ����ʱ����������壬��Ϸ����
					repaint();//���¿�ʼ
					state = START;
					try{
					new MyPlayer(new File("start.wav")).start();
				}catch(NoPlayerException|IOException  e1){}
				}
			}
		};
		this.addMouseListener(m);//������¼�,����������ӵ���ǰ�����
		this.addMouseMotionListener(m);//��궯���¼�
		timer = new Timer();//������ʱ��
		timer.schedule(new TimerTask(){
			public void run(){//����ÿ�α�����ʱ��ִ��RUN����,Timer��һ�������࣬��������run�����������������ִ���߼�
				flyIn();//�л�����
				shootTime();//�ӵ�������ʱ��
				flyIn();//�л�����
				flyMove();//�����ƶ�
				shootIng();//�ӵ����ел�����л���ʧ
				ruin();//�������еķɻ���ʧ����
				repaint();//�����ƶ�����,component�м��е�ͼ�η����仯�󲻻�������ʾ����ʹ��repaint�����ػ���Ϸ����
				}
		},0,20);//0�ǳ�ʼִ��ʱ�䣬17��ÿ��17����ִ��һ��run����
	}
	//�л�����
	private long nextTime;
	public void flyIn(){
		long now =System.currentTimeMillis();//�÷����������Ƿ��ص�ǰ�ļ����ʱ�䣬
		if(now<nextTime){
		return;
}
		nextTime = now + 2017/3;//��һ���ɻ����ֵ�ʱ�����
		SuperFly	 obj = randomOne();
		fly= Arrays.copyOf(fly, fly.length+1);//java����������С���ɱ�ģ��������鸴�Ʒ�ʽ����
		fly[fly.length-1] = obj;
		if(fly.length>3){//��������ֵĵл�����5ʱ����Ϸ����
		state=OVER;
		bullets = new Bullet[0]; fly = new SuperFly[0]; 
		try{
			new MyPlayer(new File("over.wav")).start();
			}catch(NoPlayerException|IOException  e1){}
		}
	}
	public static SuperFly randomOne(){
		int random=(int)(Math.random()*8);//��������0С��8��������������ȡ��С�л�
		switch(random){
		case 1:
		case 2:
		default: return new  BossPlane();
		}
	}
		private long nextShootTime = 0;/** ��World�������ʱ����Ƶ�������� */
		public void shootTime(){
		long now = System.currentTimeMillis();
	if(now<nextShootTime){
				return;
			}
			nextShootTime = now +222;
			//���:��hero��������ӵ���ӵ�bullets����
			Bullet[] ary = fighter.shoot();//Myplane��ķ���
			bullets = Arrays.copyOf(bullets, bullets.length+ary.length);//��bullets��ary�ϲ�
			System.arraycopy(ary, 0, bullets, bullets.length-ary.length, ary.length);
		}
	public void shootIng() {//�����ײ------���ÿ���ɻ���ÿ���ӵ����Ƿ�������ײ������ӵ������������ķɻ�(life>0)���ӵ�û�����ɻ�-
		for(Bullet b:bullets){
			iShooted(b);
			}
		}
	private int score = 0;
	private void iShooted(Bullet b) {
		for(SuperFly obj:fly){
			if(obj.knock(b)){//����ӵ��Ƿ���ɻ�������ײ
				try{new MyPlayer(new File("1.wav")).start();}catch(NoPlayerException|IOException  e1){}//*/C�����ص�
					obj.life--;
					if(obj.life==0){//��������Ժ�lifΪ0���ټ���÷�
						int s = BossPlane.getScore();//�����ۼ�
							score+=s;
						}
					}
				}
			}
	public void flyMove() {//�ɻ��ƶ�����
		for(SuperFly obj:fly){
			obj.move();
		}
		for(Bullet bul:bullets){
			bul.move();//�ӵ����ƶ���ʽ��д�ˣ���Ϊ�����ƶ�������л��෴
		}
	}
	public void ruin() {//����û���ķ����� 
		SuperFly[] ary = {};
				for(int i=0;i<fly.length;i++){
					if(fly[i].life>0){
						ary=Arrays.copyOf(ary, ary.length+1);
						ary[ary.length-1]=fly[i];
					}
				}
				fly=ary;
			}
		public void paint(Graphics g){//��д���޸ģ�JPanel���paint����,Graphics g ��Swing�ṩ�Ļ��ʶ���
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
			g.drawString("SCORE:"+score, 366, 600);//�������½ǵ÷���
			}
		 if(state==OVER){
			 g.drawImage(Over.image,35,0,null);
				fighter = new Myplane();
				fly = new SuperFly [0];
				bullets = new Bullet[0];
				if(score>1000)
					g.drawString("���յ÷�:"+score+"    �������", 166, 200);
				else 
				g.drawString("���յ÷�:"+score+"    ����һ�ΰɣ����ͣ�",166, 200);}
			}
		}
		
		