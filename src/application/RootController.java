package application;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class RootController {

	@FXML
	public void about(){
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("���ֹ�����");
		alert.setHeaderText("������ֹ�����������2018-2019ѧ��Java�εĽ����ҵ��");
		alert.setContentText("��������: \n���171 ������"
							+ "\n���171 ����"
							+ "\n���171 ����"
							+ "\n\n����Java(FX)����");
		alert.showAndWait();
	}
}
