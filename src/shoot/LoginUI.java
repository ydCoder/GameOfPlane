package shoot;
import java.awt.Image;
import java.awt.Toolkit;
import javax.media.NoPlayerException;
import javax.swing.*;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;//misic��������쳣
	public class LoginUI extends JFrame implements ActionListener{
	private	JButton  b1;
	public LoginUI(){
		 super("SUSE���");//������Ϸ����
		 Image img=Toolkit.getDefaultToolkit().getImage("src/Img/1.jpg");//������Ϸͼ��
		 this.setIconImage(img);
		ImageIcon bg= new ImageIcon("src/Img/bg1.jpg"); //Ҫ���õı���ͼƬ 
		JLabel imgLabel = new JLabel(bg); //������ͼ���ڱ�ǩ� 
		this.getLayeredPane().add(imgLabel, new Integer(Integer.MIN_VALUE)); //��������ǩ��ӵ�jfram��LayeredPane���� 
		imgLabel.setBounds(0, 0, bg.getIconWidth(), bg.getIconHeight()); // ���ñ�����ǩ��λ��
		Container contain = getContentPane(); ((JPanel) contain).setOpaque(false); // �����������Ϊ͸������LayeredPane����еı�����ʾ������
		JLabel f= new JLabel("SUSE��Ȩ����");//������ʣ��粻����JLabel��ǩ������ӵİ�ť�����Ḳ��֮ǰ���ñ����Ϸ����
		/**���þ��Բ��ֵķ�ʽ��ָ��λ����Ӱ�ť*/
		
		JButton  b1=new JButton("��ʼ��Ϸ");
		JButton b2=new JButton("�л�����");
		JButton b3=new JButton("������Ϸ");
		JButton b4=new JButton("�˳���Ϸ");
		
		b1.setBounds(180,250,100,30);
		b1.addActionListener(this);
		b2.setBounds(180,300,100,30);
		b3.setBounds(180,350,100,30);
		b4.setBounds(180,400,100,30);
		
		//���ڲ����b3��ӹرյ�ǰ���ڣ����˳���Ϸ
		b1.addActionListener(new ActionListener(){
			 public void actionPerformed(ActionEvent e2) {
				 try{
						new MyPlayer(new File("start.wav")).start();//������Ϸ����
						}catch(NoPlayerException|IOException  e1){}
					};
			 }
		);
		b2.addActionListener(new ActionListener(){
			 public void actionPerformed(ActionEvent e) {
				 try{
						new MyPlayer(new File("6.wav")).start();//�����л���Ϸ����
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
		//JLabel f= new JLabel("SUSE��Ȩ����");//������ʣ��粻����JLabel��ǩ������ӵİ�ť�����Ḳ��֮ǰ���ñ����Ϸ����
		contain.add(f);//�������г���������ʣ���д���޷�����
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(480,700);
		 this.setVisible(true);
	 }
	/**���ڸ���ʼ��Ϸ��ӵĶ��¼�������رյ�ǰ���ڣ���������Ϸ����*/
	public void actionPerformed(ActionEvent e){
			dispose();
			JFrame frame = new JFrame("Javaս��");
			Image img=Toolkit.getDefaultToolkit().getImage("src/Img/1.jpg");//������Ϸͼ��
			 frame.setIconImage(img);
			GameUI gamepanel= new GameUI();
			frame.add(gamepanel);
			frame.setSize(460,860);//���ô�С�����ô�������Ļ����ʾ��С
			frame.setVisible(true);//���ڿɼ�
			frame.setLocationRelativeTo(null);//�����ڶ�λ����Ļ���м�
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//����رգ��˳�����
			gamepanel.execute();//������ʱ��
			}
	public static void main(String args[]){
	JFrame f=new LoginUI();
	f.setLocationRelativeTo(null);//ʹ��¼�˵��������
	try{
		new MyPlayer(new File("start.wav")).start();//������Ϸ����
		}catch(NoPlayerException|IOException  e1){}
	}
}
	
	