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
	public static final int YUKI_1 = 2000;
	public static final int YUKI_2 = 2000;
	public static final int AME = 2000;

//	private ArrayList<BulletChara> bullets_1 = new ArrayList<BulletChara>(YUKI_1);
//	private ArrayList<BulletChara> bullets_2 = new ArrayList<BulletChara>(YUKI_2);
//	private ArrayList<BulletChara> bullets_3 = new ArrayList<BulletChara>(AME);

	private ArrayList<BulletChara> yuki1 =
			new ArrayList<BulletChara>(YUKI_1);
	private ArrayList<BulletChara> yuki2 =
			new ArrayList<BulletChara>(YUKI_2);
	private ArrayList<BulletChara> ame =
			new ArrayList<BulletChara>(AME);

	private ArrayList<BufferedImage> img_bullets =
			new ArrayList<BufferedImage>();
	public static int REDBULLET_E = 0 ;
	public static int GREENBULLET_E = 1;
	public static int BLUEBULLET_E = 2;
	//finalを消した

	private int score =0;
	private Font mfont = new Font("Sanserif", Font.BOLD, 30);

	@Override
	public GameChara getPlayer() {
		return this.player;
	}

	@Override
	public void loadMedia() throws IOException {
		this.img_chara = ImageIO.read(new File("gazou/zeusu.png"));
		this.player.setImage(
				this.img_chara.getSubimage(0,  0, 100, 100));
		this.img_back = ImageIO.read(new File("gazou/hiroi2-2.jpg"));
		this.img_shot = ImageIO.read(new File("gazou/yuki.png"));
		this.img_bullets.add(this.img_shot.getSubimage(0, 0, 34, 34));
		this.img_bullets.add(this.img_shot.getSubimage(35, 0, 25, 25));
		this.img_bullets.add(this.img_shot.getSubimage(60, 0, 45, 45));
//		this.img_yuki1 = ImageIO.read(new File("gagou/yuki1.gif"));

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
		for(int i=0; i<YUKI_1; i++){
			if(this.yuki1.get(i).visible == true){
				this.yuki1.get(i).draw(ginfo, this);
			}
		}
		for(int j=0; j<YUKI_2; j++){
			if(this.yuki2.get(j).visible == true){
				this.yuki2.get(j).draw(ginfo, this);
			}
		}
		for(int k=0; k<AME; k++){
			if(this.ame.get(k).visible == true){
				this.ame.get(k).draw(ginfo, this);
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
		this.yuki1.clear();
		for(int i=0; i<YUKI_1; i++){
			this.yuki1.add(new BulletChara());
		}
		this.yuki2.clear();
		for(int j=0; j<YUKI_2; j++){
			this.yuki2.add(new BulletChara());
		}
		this.ame.clear();
		for(int k=0; k<AME; k++){
			this.ame.add(new BulletChara());
		}
//		this.bullets_j.clear();
//		for(int i=0; i<MAX_BULLETS_J; i++){
//			this.bullets_j.add(new BulletChara());
//		}
	}

	@Override
	public ArrayList<BulletChara> getYuki1() {
		return this.yuki1;
	}

	@Override
	public ArrayList<BulletChara> getYuki2() {
		return this.yuki2;
	}

	@Override
	public ArrayList<BulletChara> getAme() {
		return this.ame;
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
		for(int i=0; i<2000; i++){
			if(this.yuki1.get(i).visible == true){
				if(this.yuki1.get(i).hitTest(this.player) == true){
					score = score + 10;
					this.yuki1.get(REDBULLET_E).visible = false;
 //					this.player.life = false;
					return true;
				}
			}else if(this.yuki2.get(i).visible == true){
				if(this.yuki2.get(i).hitTest(this.player) == true){
					score = score +20;
					this.yuki2.get(GREENBULLET_E).visible = false;
					return true;
				}
			}else if(this.ame.get(i).visible == true){
				if(this.ame.get(i).hitTest(this.player) == true){
					score = score + 30;
					this.ame.get(BLUEBULLET_E).visible = false;
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
