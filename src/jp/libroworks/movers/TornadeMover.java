package jp.libroworks.movers;

import jp.libroworks.GraphicsInfo;
import jp.libroworks.supers.BulletChara;
import jp.libroworks.supers.BulletMover;
import jp.libroworks.supers.Effect;
import jp.libroworks.supers.Stage;

public class TornadeMover extends BulletMover {

	@Override
	public void move(GraphicsInfo ginfo, Stage stage, BulletChara bullet) {
		if( bullet.position.x < -200 ||
				bullet.position.x > stage.getWidth() + 200 ||
				bullet.position.y < -200 ||
				bullet.position.y > stage.getHeight() + 200)
		{
			bullet.visible = false;
		}
		long l = ginfo.currenttime - bullet.getStartTime();
		bullet.vector.x = 0;
		bullet.vector.y = l / 50;
		bullet.vector.rotateVector(-l*0.001);
		bullet.position.x = stage.getEnemy().position.x + bullet.vector.x;
		bullet.position.y = stage.getEnemy().position.y + bullet.vector.y;
	}

	public static TornadeMover singleton = new TornadeMover();

}
