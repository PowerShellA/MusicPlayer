package application;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.TagException;
import org.jaudiotagger.tag.id3.AbstractID3v2Tag;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditController {
	@FXML
	private TextField Name;
	@FXML
	private TextField Singer;
	@FXML
	private TextField Album;
	private Mp3Model Music;
	private Stage dialogStage;
	
	public void setMusic(Mp3Model toed){
		this.Music = toed;
		this.Name.setText(Music.getName());
		this.Singer.setText(Music.getSinger());
		this.Album.setText(Music.getAlbum());
	}
	
	@FXML
	public void handleOK() throws CannotReadException, IOException, TagException, ReadOnlyFileException, InvalidAudioFrameException{
		MP3File mp3File = (MP3File) AudioFileIO.read(new File(Music.getPath()));
        AbstractID3v2Tag mp3tag = mp3File.getID3v2Tag();
        System.out.println(mp3tag);
        mp3tag.setField(FieldKey.TITLE, Name.getText());
        mp3tag.setField(FieldKey.ARTIST,Singer.getText());
        mp3tag.setField(FieldKey.ALBUM, Album.getText());
        mp3File.setID3v2Tag(mp3tag);
        mp3File.save();
        Music.setAlbum(Album.getText());
        Music.setName(Name.getText());
        Music.setSinger(Singer.getText());
        dialogStage.close();
	}
	
	@FXML
	public void handleCancel(){
		 dialogStage.close();
	}
	
	@FXML
	public void handleDelete(){
		Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
    	confirmation.setTitle("确定删除？");
    	confirmation.setHeaderText("您确实要删除这个音频文件吗？删除后不可恢复！");
    	confirmation.setContentText(Music.getName());
    	
    	Optional<ButtonType> result = confirmation.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK){
        	File file = new File(Music.getPath()); 
			file.delete();
			dialogStage.close();
        }
	}
	
	public void setDialogStage(Stage dialogStage){
		this.dialogStage = dialogStage;
	}
}
