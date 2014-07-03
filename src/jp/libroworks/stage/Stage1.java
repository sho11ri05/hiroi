package jp.libroworks.stage;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import jp.libroworks.GraphicsInfo;
import jp.libroworks.PlayerChara;
import jp.libroworks.supers.BulletChara;
import jp.libroworks.supers.GameChara;
import jp.libroworks.supers.SoundBox;
import jp.libroworks.supers.Stage;

public class Stage1 extends Stage {
	private PlayerChara player = new PlayerChara();
	private BufferedImage img_chara, img_back, img_shot;
	private BossA enemy = new BossA();
	public static final int MAX_BULLETS_E = 200;
	private ArrayList<BulletChara> bullets_e =
			new ArrayList<BulletChara>(MAX_BULLETS_E);
//	public static final int MAX_BULLETS_J = 20;　自機からの弾の発射
//	private ArrayList<BulletChara> bullets_j =
//			new ArrayList<BulletChara>(MAX_BULLETS_J);
	private ArrayList<BufferedImage> img_bullets =
			new ArrayList<BufferedImage>();
	public static final int REDBULLET_E = 0;
	public static final int GREENBULLET_E = 1;
	public static final int BLUEBULLET_E = 2;
	public static final int PURPLEBULLET_E = 3;
	public static final int BULLET_J = 4;
	int score =0;
	private Font mfont = new Font("Sanserif", Font.BOLD, 30);


	@Override
	public GameChara getPlayer() {
		return this.player;
	}

	@Override
	public void loadMedia() throws IOException {
		this.img_chara = ImageIO.read(new File("media/chara.png"));
		this.player.setImage(
				this.img_chara.getSubimage(0,  0, 48, 48));
		this.enemy.setImage(
				this.img_chara.getSubimage(0, 72, 192, 192));
		this.img_back = ImageIO.read(new File("gazou/hiroi2-2.jpg"));
		this.img_shot = ImageIO.read(new File("media/shot.png"));
		this.img_bullets.add(this.img_shot.getSubimage(0, 0, 24, 24));
		this.img_bullets.add(this.img_shot.getSubimage(48, 0, 24, 24));
		this.img_bullets.add(this.img_shot.getSubimage(24, 0, 24, 24));
		this.img_bullets.add(this.img_shot.getSubimage(72, 0, 24, 24));
		this.img_bullets.add(this.img_shot.getSubimage(0, 24, 24, 48));
		//サウンド
		try {
			//自機が当たった時の音
			SoundBox.singleton.loadSound(
					new File("media/bom34.wav"));
			SoundBox.singleton.loadSound(
					new File("media/fall01.wav"));
			SoundBox.singleton.loadSound(
					new File("media/fm005.wav"));
			SoundBox.singleton.loadSound(
					new File("media/smoke03.wav"));
			SoundBox.singleton.loadSound(
					new File("media/burst01.wav"));
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
	}

	@Override
	public GameChara getEnemy() {
		return this.enemy;
	}

	@Override
	public void draw(GraphicsInfo ginfo) {
		ginfo.g.drawImage(this.img_back, 0, 0, null);
		this.enemy.draw(ginfo, this);
		this.player.draw(ginfo, this);
		for(int i=0; i<MAX_BULLETS_E; i++){
			if(this.bullets_e.get(i).visible == true){
				this.bullets_e.get(i).draw(ginfo, this);
			}
		}
//		for(int i=0; i<MAX_BULLETS_J; i++){  自機からの弾の発射
//			if(this.bullets_j.get(i).visible == true){
//				this.bullets_j.get(i).draw(ginfo, this);
//			}
//		}
	}

	@Override
	public void init(GraphicsInfo ginfo) {
//		this.player.position.x = 400;　プレイヤー横配置
		this.player.position.x = 50;
//		this.player.position.y = 520;　プレイヤー縦配置
		this.player.position.y = 320;
//		this.player.life = true;
		this.enemy.position.x = 400;
		this.enemy.position.y = -100;
		this.enemy.init();
		//弾の初期化
		this.bullets_e.clear();
		for(int i=0; i<MAX_BULLETS_E; i++){
			this.bullets_e.add(new BulletChara());
		}
//		this.bullets_j.clear();
//		for(int i=0; i<MAX_BULLETS_J; i++){
//			this.bullets_j.add(new BulletChara());
//		}
	}

	@Override
	public ArrayList<BulletChara> getBullets_E() {
		return this.bullets_e;
	}

	@Override
	public BufferedImage getBulletImage(int type) {
		return this.img_bullets.get(type);
	}

//	@Override
//	public ArrayList<BulletChara> getBullets_J() {
//		return this.bullets_j;
//	}

	@Override
	public boolean hitTestAll(GraphicsInfo ginfo) {
//		for(int i=0; i<MAX_BULLETS_J; i++){
//			if(this.bullets_j.get(i).visible == true){
//				if(this.bullets_j.get(i).hitTest(this.enemy) == true){
//					this.enemy.life--;
//					this.bullets_j.get(i).setVisible(ginfo, false);
//					SoundBox.singleton.playOneSHot(4);
//				}
//			}
//		}
		if(this.getEnemy().hitTest(this.player) == true){
//			this.player.life = false;
			return true;
		}
		//敵の弾の当たり値
		for(int i=0; i<MAX_BULLETS_E; i++){
			if(this.bullets_e.get(i).visible == true){
				if(this.bullets_e.get(i).hitTest(this.player) == true){
					score =score + 10;
					this.bullets_e.get(i).visible = false;
//					this.player.life = false;
					return true;
				}
			}
		}

		//スコア
		ginfo.g.setColor(Color.BLUE);
		ginfo.g.setFont(Stage1.this.mfont);
		String str = "Total Score："+score;
		FontMetrics fm = ginfo.g.getFontMetrics();
		int strw = fm.stringWidth(str) / 2;
		ginfo.g.drawString(str, 650 - strw, 100);

		return false;
	}

	@Override
	public boolean isBossLiving() {
		if(this.enemy.life < 1) return false;
		return true;
	}

}
