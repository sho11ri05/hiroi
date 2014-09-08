package jp.libroworks.supers;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import jp.libroworks.GraphicsInfo;

public abstract class Stage {

	private double width = 780;
	private double height = 580;

	public abstract GameChara getPlayer();
	public abstract void loadMedia() throws IOException;
	public abstract GameChara getEnemy();

	public abstract void draw(GraphicsInfo ginfo);
	public abstract void init(GraphicsInfo ginfo);


	//敵の弾のデータを取得する
	public abstract ArrayList<BulletChara> getYuki1();
	public abstract ArrayList<BulletChara> getYuki2();
	public abstract ArrayList<BulletChara> getAme();
	//自機の弾のデータを取得する
//	public abstract ArrayList<BulletChara> getBullets_J();

	//弾の画像を取得する
	public abstract BufferedImage getBulletImage(int type);

	//空いている弾を探す
	protected int searchidx = 0;
	public BulletChara searchBullet(){
		ArrayList<BulletChara> yuki1 = this.getYuki1();
		int m = yuki1.size();
		ArrayList<BulletChara> yuki2 = this.getYuki1();
		m = yuki2.size();
		ArrayList<BulletChara> ame = this.getYuki1();
		m = ame.size();
		//空き弾を探す
		for(;this.searchidx < m; this.searchidx++){
			if(yuki1.get(this.searchidx).visible == false) {
				return yuki1.get(this.searchidx);
			}else if(yuki2.get(this.searchidx).visible == false){
				return yuki2.get(this.searchidx);
			}else if(ame.get(this.searchidx).visible == false){
				return ame.get(this.searchidx);
			}
		}
		//空きがないときはnullを返して発射キャンセル
		this.searchidx = 0;
		return null;
	}
	public double getWidth() {
		return width;
	}
	public void setWidth(double width) {
		this.width = width;
	}
	public double getHeight() {
		return height;
	}
	public void setHeight(double height) {
		this.height = height;
	}

	public abstract boolean hitTestAll(GraphicsInfo ginfo);
	public abstract boolean isBossLiving();
	public void show(GraphicsInfo ginfo) {
		// TODO 自動生成されたメソッド・スタブ

	}
}
