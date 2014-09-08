package jp.libroworks.shooters;

import java.awt.geom.Point2D;
import java.util.Random;

import jp.libroworks.GraphicsInfo;
import jp.libroworks.movers.StraightMover;
import jp.libroworks.stage.Stage1;
import jp.libroworks.supers.BulletChara;
import jp.libroworks.supers.Shooter;
import jp.libroworks.supers.Stage;
import jp.libroworks.supers.Vector2D;

public class CrossShooter extends Shooter {

	private Vector2D v = new Vector2D();

	@Override
	public void shoot(GraphicsInfo ginfo, Stage stage, Point2D.Double position) {

        Random r = new Random();

//		double r = 90.0 / 180.0 * Math.PI;
		this.v.x = 0.0;
		this.v.y = 200.0;
		for(int i=0; i<1; i++){
			BulletChara bullet = stage.searchBullet();
			if(bullet == null) return;
			bullet.mover = StraightMover.singleton;
			bullet.position.x = r.nextInt(780);          //乱数を取得する //position.x;
			bullet.position.y = 10;   //position.y;
			bullet.vector.x = this.v.x;
			bullet.vector.y = this.v.y;
//			bullet.setImage(stage.getBulletImage(Stage1.GREENBULLET_E));
			bullet.setImage(stage.getBulletImage(Stage1.BLUEBULLET_E));
			bullet.setVisible(ginfo, true);
//			this.v.rotateVector(r);
		}

		for(int i=0; i<1; i++){
			BulletChara bullet = stage.searchBullet();
			if(bullet == null) return;
			bullet.mover = StraightMover.singleton;
			bullet.position.x = r.nextInt(780);          //乱数を取得する //position.x;
			bullet.position.y = 10;   //position.y;
			bullet.vector.x = this.v.x;
			bullet.vector.y = this.v.y;
			bullet.setImage(stage.getBulletImage(Stage1.GREENBULLET_E));
			bullet.setVisible(ginfo, true);
//			this.v.rotateVector(r);
		}
	}

	public static CrossShooter singleton = new CrossShooter();

}
