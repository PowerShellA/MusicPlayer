package application;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class RootController {

	@FXML
	public void about(){
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("音乐管理器");
		alert.setHeaderText("这个音乐管理器是我们2018-2019学年Java课的结课作业。");
		alert.setContentText("开发力量: \n软件171 刘守岳"
							+ "\n软件171 梁灿"
							+ "\n软件171 刘贺"
							+ "\n\n采用Java(FX)开发");
		alert.showAndWait();
	}
}
