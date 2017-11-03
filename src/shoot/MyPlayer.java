package shoot;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.media.ControllerEvent;
import javax.media.ControllerListener; 
import javax.media.Manager;
import javax.media.NoPlayerException;
import javax.media.Player;
public class MyPlayer implements ControllerListener {
	private Player player = null;
	public MyPlayer(File path) throws NoPlayerException, MalformedURLException,
			IOException {
		System.out.println(path.getAbsolutePath());
		player = Manager.createPlayer(path.toURL());
		player.addControllerListener(this);
		player.prefetch();
		}
	public void start() {
		player.start();
		}
	public void controllerUpdate(ControllerEvent arg0) {
	}
}
