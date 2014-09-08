package jp.libroworks.shooters;

import java.awt.geom.Point2D;
import java.util.Random;

import jp.libroworks.GraphicsInfo;
import jp.libroworks.movers.ChaseMover;
import jp.libroworks.stage.Stage1;
import jp.libroworks.supers.BulletChara;
import jp.libroworks.supers.Shooter;
import jp.libroworks.supers.Stage;

public class BasicShooter extends Shooter {

	public void shoot(GraphicsInfo ginfo, Stage stage, Point2D.Double position) {

        Random r = new Random();


		BulletChara bullet = stage.searchBullet();
		if(bullet == null) return;
		bullet.mover = ChaseMover.singleton;
		bullet.position.x =  r.nextInt(780);          //乱数を取得する
		bullet.position.y = 10;
		bullet.vector.x = 0;
		bullet.vector.y = 400;
		bullet.setImage(stage.getBulletImage(Stage1.GREENBULLET_E));
		bullet.setVisible(ginfo, true);
	}

	public static BasicShooter singleton = new BasicShooter();

}
