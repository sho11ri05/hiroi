package jp.libroworks.shooters;

import java.awt.geom.Point2D;
import java.util.Random;

import jp.libroworks.GraphicsInfo;
import jp.libroworks.movers.StraightMover;
import jp.libroworks.stage.Stage1;
import jp.libroworks.supers.BulletChara;
import jp.libroworks.supers.Shooter;
import jp.libroworks.supers.Stage;

public class TwinShooter extends Shooter {

	@Override
	public void shoot(GraphicsInfo ginfo, Stage stage, Point2D.Double position) {

        Random r = new Random();

		for(int i=0; i<2; i++){
			BulletChara bullet = stage.searchBullet();
			if(bullet == null) return;
			bullet.mover = StraightMover.singleton;
			bullet.position.x =    r.nextInt(780);          //乱数を取得する
					//position.x -20 + (40 * i);
			bullet.position.y = 10;
			bullet.vector.x = 0;
			bullet.vector.y = 400;
			bullet.setImage(stage.getBulletImage(Stage1.REDBULLET_E));
			bullet.setVisible(ginfo, true);
		}

	}

	public static TwinShooter singleton = new TwinShooter();

}
