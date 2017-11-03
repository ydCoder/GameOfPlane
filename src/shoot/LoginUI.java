package shoot;
import java.awt.Image;
import java.awt.Toolkit;
import javax.media.NoPlayerException;
import javax.swing.*;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;//misic输入输出异常
	public class LoginUI extends JFrame implements ActionListener{
	private	JButton  b1;
	public LoginUI(){
		 super("SUSE软件");//设置游戏标题
		 Image img=Toolkit.getDefaultToolkit().getImage("src/Img/1.jpg");//设置游戏图标
		 this.setIconImage(img);
		ImageIcon bg= new ImageIcon("src/Img/bg1.jpg"); //要设置的背景图片 
		JLabel imgLabel = new JLabel(bg); //将背景图放在标签里。 
		this.getLayeredPane().add(imgLabel, new Integer(Integer.MIN_VALUE)); //将背景标签添加到jfram的LayeredPane面板里。 
		imgLabel.setBounds(0, 0, bg.getIconWidth(), bg.getIconHeight()); // 设置背景标签的位置
		Container contain = getContentPane(); ((JPanel) contain).setOpaque(false); // 将内容面板设为透明。将LayeredPane面板中的背景显示出来。
		JLabel f= new JLabel("SUSE版权所有");//编程疑问，如不创建JLabel标签，则添加的按钮部件会覆盖之前设置别的游戏背景
		/**采用绝对布局的方式在指定位置添加按钮*/
		
		JButton  b1=new JButton("开始游戏");
		JButton b2=new JButton("切换音乐");
		JButton b3=new JButton("关于游戏");
		JButton b4=new JButton("退出游戏");
		
		b1.setBounds(180,250,100,30);
		b1.addActionListener(this);
		b2.setBounds(180,300,100,30);
		b3.setBounds(180,350,100,30);
		b4.setBounds(180,400,100,30);
		
		//用内部类给b3添加关闭当前窗口，并退出游戏
		b1.addActionListener(new ActionListener(){
			 public void actionPerformed(ActionEvent e2) {
				 try{
						new MyPlayer(new File("start.wav")).start();//播放游戏音乐
						}catch(NoPlayerException|IOException  e1){}
					};
			 }
		);
		b2.addActionListener(new ActionListener(){
			 public void actionPerformed(ActionEvent e) {
				 try{
						new MyPlayer(new File("6.wav")).start();//播放切换游戏音乐
						}catch(NoPlayerException|IOException  e1){}
					};
				}
			
		);
		b3.addActionListener(new ActionListener(){
			 public void actionPerformed(ActionEvent e) {
				// dispose();
				 new Introduce();}
			}
			
		);
		b4.addActionListener(new ActionListener(){
			 public void actionPerformed(ActionEvent e) {
				 dispose();}}
			
		);
		contain.add(b1);
		contain.add(b2);
		contain.add(b3);
		contain.add(b4);
		//JLabel f= new JLabel("SUSE版权所有");//编程疑问，如不创建JLabel标签，则添加的按钮部件会覆盖之前设置别的游戏背景
		contain.add(f);//以上两行程序最大疑问，不写则无法运行
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(480,700);
		 this.setVisible(true);
	 }
	/**用于给开始游戏添加的动事件，点击关闭当前窗口，并进入游戏窗口*/
	public void actionPerformed(ActionEvent e){
			dispose();
			JFrame frame = new JFrame("Java战机");
			Image img=Toolkit.getDefaultToolkit().getImage("src/Img/1.jpg");//设置游戏图标
			 frame.setIconImage(img);
			GameUI gamepanel= new GameUI();
			frame.add(gamepanel);
			frame.setSize(460,860);//设置大小：设置窗框在屏幕的显示大小
			frame.setVisible(true);//窗口可见
			frame.setLocationRelativeTo(null);//将窗口定位到屏幕正中间
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//点击关闭，退出程序
			gamepanel.execute();//启动定时器
			}
	public static void main(String args[]){
	JFrame f=new LoginUI();
	f.setLocationRelativeTo(null);//使登录菜单窗体居中
	try{
		new MyPlayer(new File("start.wav")).start();//播放游戏音乐
		}catch(NoPlayerException|IOException  e1){}
	}
}
	
	