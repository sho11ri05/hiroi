package jp.libroworks;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import jp.libroworks.stage.Stage1;
import jp.libroworks.stage.Stage2;
import jp.libroworks.supers.GameDisplay;
import jp.libroworks.supers.Stage;

public class MyGameDisplay extends GameDisplay {

	GameDisplay title, main;
	public GameDisplay over;
	GameDisplay clear;
	private Font mfont = new Font("Sanserif", Font.BOLD, 50);
	ArrayList<Stage> stagelist = new ArrayList<Stage>();
	Stage stage = null;
	int stagenum = 0;
	int sukoa = 0;

	public MyGameDisplay(){
		this.title = new MyGameTitle();
		this.main = new MyGameMain();
		this.over = new MyGameOver();
		this.clear = new MyStageClear();
		GameDisplay.current = this.title;
		stagelist.add(new Stage1());
		stagelist.add(new Stage2());
		this.stage = stagelist.get(0);
	}

	@Override
	public void show(GraphicsInfo ginfo) {
	}

	@Override
	public void loadMedia() throws IOException {
		this.title.loadMedia();
		this.main.loadMedia();
		this.over.loadMedia();
//		this.clear.loadMedia();
		for(int i=0; i<this.stagelist.size(); i++){
			this.stagelist.get(i).loadMedia();
		}
	}

	//タイトル画面
	class MyGameTitle extends GameDisplay{
		private BufferedImage img_title;

		@Override
		public void show(GraphicsInfo ginfo) {
			ginfo.g.drawImage(this.img_title, 0, 0, null);

			ginfo.g.setColor(Color.RED);
			ginfo.g.setFont(MyGameDisplay.this.mfont);
			String str = "PUSH SPACE";
			FontMetrics fm = ginfo.g.getFontMetrics();
			int strw = fm.stringWidth(str) / 2;
			ginfo.g.drawString(str, 550 - strw, 500);

			if(ginfo.keystate[KEY_STATE.SPACE] == true){
				GameDisplay.current = MyGameDisplay.this.main;
				GameDisplay.current.setStartTime(ginfo.currenttime);
				MyGameDisplay.this.stagenum = 0;
				MyGameDisplay.this.stage = stagelist.get(MyGameDisplay.this.stagenum);
				MyGameDisplay.this.stage.init(ginfo);
			}
		}

		@Override
		public void loadMedia() throws IOException {
			this.img_title = ImageIO.read(new File("gazou/hiroi1.jpg"));
		}
	}

	//ゲーム本編
	class  MyGameMain extends GameDisplay{
		@Override

		public void show(GraphicsInfo ginfo) {
			MyGameDisplay.this.stage.draw(ginfo);
//			if(MyGameDisplay.this.stage.hitTestAll(ginfo) == true){
//				GameDisplay.current = MyGameDisplay.this.over;
//				GameDisplay.current.setStartTime(ginfo.currenttime);
//				SoundBox.singleton.playOneSHot(0);
//			}
//			if(MyGameDisplay.this.stage.isBossLiving() == false){
//				GameDisplay.current = MyGameDisplay.this.clear;
//				GameDisplay.current.setStartTime(ginfo.currenttime);
//				SoundBox.singleton.playOneSHot(3);
//			}
			if(ginfo.currenttime - this.starttime > 180000){
				GameDisplay.current = MyGameDisplay.this.over;
				GameDisplay.current.setStartTime(ginfo.currenttime);
			}

			if(MyGameDisplay.this.stage.hitTestAll(ginfo) == true){
				sukoa += 10;
//				this.bullets_e.clear();
			}
		}


		@Override
		public void loadMedia() throws IOException {
		}
	}

	//ゲームオーバー画面
	class MyGameOver extends GameDisplay{
		@Override
		public void show(GraphicsInfo ginfo) {
			MyGameDisplay.this.stage.draw(ginfo);
			ginfo.g.setColor(Color.RED);
			ginfo.g.setFont(MyGameDisplay.this.mfont);
			String str = "ゲームオーバー";
			FontMetrics fm = ginfo.g.getFontMetrics();
			int strw = fm.stringWidth(str) / 2;
			ginfo.g.drawString(str, 400 - strw, 200);

//			if(ginfo.currenttime - this.starttime > 5000){
//				GameDisplay.current = MyGameDisplay.this.title;
//			}

		}

		@Override
		public void loadMedia() {}
	}

	//ステージクリア画面
	class MyStageClear extends GameDisplay{
		@Override
		public void show(GraphicsInfo ginfo) {
			MyGameDisplay.this.stage.draw(ginfo);
			ginfo.g.setColor(Color.CYAN);
			ginfo.g.setFont(MyGameDisplay.this.mfont);
			String str = "ステージクリア";
			FontMetrics fm = ginfo.g.getFontMetrics();
			int strw = fm.stringWidth(str) / 2;
			ginfo.g.drawString(str, 400 - strw, 200);

			if(ginfo.currenttime - this.starttime > 5000){
				MyGameDisplay.this.stagenum++;
				if(MyGameDisplay.this.stagenum >= MyGameDisplay.this.stagelist.size() ){
					GameDisplay.current = MyGameDisplay.this.title;
				} else {
					GameDisplay.current = MyGameDisplay.this.main;
					MyGameDisplay.this.stage = MyGameDisplay.this.stagelist.get(MyGameDisplay.this.stagenum);
					MyGameDisplay.this.stage.init(ginfo);
				}
			}
		}

		@Override
		public void loadMedia() {}
	}
}
