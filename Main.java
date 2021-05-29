package CloseRequestBlocker;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application{
	
	private double width = 50;
	private double height = 32;
	private double full_width = -1;
	private Color bgcolor = Color.web("00FFFF");
	
	@Override
	public void start(Stage stage)
	{
		trySetFull_Width();
		
		BorderPane pane = new BorderPane();
		pane.setBackground(new Background(new BackgroundFill(bgcolor, null, null)));
		Scene scene = new Scene(pane, width, height);
		Text text = new Text("請點擊螢幕最右上方");
		text.setFont(Font.font(40));

		
		if(full_width==-1)
		{
			stage.setFullScreen(true);
			pane.setCenter(text);
		}
		else
		{
			stage.setX(full_width-width);
			stage.setY(0);
			stage.setAlwaysOnTop(true);
		}
		
		stage.initStyle(StageStyle.UNDECORATED);
		stage.setScene(scene);

		
		scene.setOnMouseClicked(e->{
			if(stage.isFullScreen())
			{
				full_width = e.getScreenX();
				stage.setFullScreen(false);
				stage.setX(full_width-width);
				stage.setY(0);
				stage.setAlwaysOnTop(true);
				pane.setCenter(null);
				try
				{
					BufferedWriter fw = new BufferedWriter(new FileWriter("config.txt"));
					fw.write(String.valueOf(full_width));
					fw.close();
				}
				catch(Exception ex)
				{
					full_width = -1;
					ex.printStackTrace();
				}
			}
		});
		
		
		stage.show();
		
		
	}
	
	private void trySetFull_Width() {
		try
		{
			BufferedReader fr = new BufferedReader(new FileReader("config.txt"));
			full_width = Double.parseDouble(fr.readLine());
			if(full_width<=0)
				full_width = -1;
			fr.close();
		}
		catch(Exception ex)
		{
			full_width = -1;
			ex.printStackTrace();
		}
	}

	public static void main(String[] args)
	{
		launch();
	}
}
