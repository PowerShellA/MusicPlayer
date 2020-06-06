package application;

import java.io.*;

import javafx.scene.control.*;
import javafx.stage.DirectoryChooser;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.TagException;
import org.jaudiotagger.tag.id3.AbstractID3v2Frame;
import org.jaudiotagger.tag.id3.AbstractID3v2Tag;
import org.jaudiotagger.tag.id3.framebody.FrameBodyAPIC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class HomeController {

	private ObservableList<Mp3Model> MusicData = FXCollections.observableArrayList();
	private ReadFile re = new ReadFile();
	private MainApp mainApp;
	@FXML
	private TextField mulu;
	@FXML
	private Label NowPlaying;
	@FXML
	private TableView<Mp3Model> UsersTable;
	@FXML
	private TableColumn<Mp3Model, String> Singer;
	@FXML
	private TableColumn<Mp3Model, String> Name;
	@FXML
	private TableColumn<Mp3Model, String> Format;
	@FXML
	private TableColumn<Mp3Model, String> Path;
	@FXML
	private TableColumn<Mp3Model, String> Album;
	@FXML
	private TableColumn<Mp3Model, Number> Time1;
	@FXML
	private ImageView NowAlbum;
	@FXML
	private TextField SearchText;
	@FXML
	private ComboBox SearchField;

	private Thread ply;

	@FXML
	private void initialize() throws ClassNotFoundException {
		Singer.setCellValueFactory(cellData -> cellData.getValue().SingerProperty());
		Name.setCellValueFactory(cellData -> cellData.getValue().NameProperty());
		Format.setCellValueFactory(cellData -> cellData.getValue().FormatProperty());
		Path.setCellValueFactory(cellData -> cellData.getValue().PathProperty());
		Album.setCellValueFactory(cellData -> cellData.getValue().AlbumProperty());
		Time1.setCellValueFactory(cellData -> cellData.getValue().Time1Property());


		SearchField.getItems().addAll("“Ù¿÷√˚","—›≥™’ﬂ","◊®º≠");
		SearchField.setPromptText("“Ù¿÷√˚");
		SearchField.setValue("“Ù¿÷√˚");
		SearchField.setEditable(false);


		UsersTable.setRowFactory(tableview -> {
			TableRow<Mp3Model> row = new TableRow<Mp3Model>();
			row.setOnMouseClicked(event -> {
				if(event.getClickCount()==2 && (!row.isEmpty())){
					try {
						doPlay(row.getItem());
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			});
			return row;
		 });  
	  }
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
        mulu.setText("D:\\p");
    }
    
    @FXML
    public void read() throws IOException, CannotReadException, TagException, ReadOnlyFileException, InvalidAudioFrameException{
    	MusicData.clear();
    	re.readfile(mulu.getText());
    	this.MusicData = re.getMusicData();
    	this.UsersTable.setItems(MusicData);
    }

	@FXML
	public void SelectDir() throws IOException, CannotReadException, TagException, ReadOnlyFileException, InvalidAudioFrameException{
		MusicData.clear();
		DirectoryChooser directoryChooser=new DirectoryChooser();
		File filee = directoryChooser.showDialog(mainApp.getStage());
		this.mulu.setText(filee.getPath());
		re.readfile(mulu.getText());
		this.MusicData = re.getMusicData();
		this.UsersTable.setItems(MusicData);
	}
    @FXML
    public void doEdit(){
    	doEdit(this.UsersTable.getSelectionModel().getSelectedItem());
    }
    
    public void doEdit(Mp3Model toed){
    	this.mainApp.showEdit(toed);
    }
    
	@FXML
    public void doPlay() throws IOException {
    	Mp3Model now = this.UsersTable.getSelectionModel().getSelectedItem();
    	doPlay(now);
    }
    
    @SuppressWarnings("deprecation")
	public void doPlay(Mp3Model now) throws IOException {
    	if(ply!=null){
    		ply.stop();
    	}
    	this.ply = new Thread(new PlayMusic(now));
    	ply.start();

    	NowPlaying.setText(now.getName());
    	byte[] imageData = this.getMP3Image(now);
    	InputStream buffin = new ByteArrayInputStream(imageData);
    	Image im = new Image(buffin);
    	NowAlbum.setImage(im);
    }
    
    @SuppressWarnings("deprecation")
	@FXML
    public void doPause(){
    	if(ply!=null){
    		ply.stop();
    	}
    	NowPlaying.setText("“—Õ£÷π");
    }
    
    public class PlayMusic extends Thread{
    	Mp3Model now ;
    	private Player player;
    	public PlayMusic(Mp3Model now){
    		this.now = now;
    	}
    	
    	@Override
    	public void run(){
            try {
            	BufferedInputStream buffer = new BufferedInputStream(new FileInputStream(now.getPath()));
            	this.player = new Player(buffer);
				player.play();
			} catch (JavaLayerException e) {
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
    	}
    	
    	public void sstop(){
    		player.close();
    	}
    }
    
    public byte[] getMP3Image(Mp3Model p3) throws IOException {
        byte[] imageData = null;
    	MP3File mp3File;
		try {
			mp3File = (MP3File) AudioFileIO.read(new File(p3.getPath()));
	        AbstractID3v2Tag tag = mp3File.getID3v2Tag();
	        AbstractID3v2Frame frame = (AbstractID3v2Frame) tag.getFrame("APIC");
	        FrameBodyAPIC body = (FrameBodyAPIC) frame.getBody();
	        imageData = body.getImageData();
		} catch (Exception e) {
			File file = new File(".\\resources\\Image\\err.png");
			imageData = new byte[(int) file.length()];
			FileInputStream fis = new FileInputStream(file);
			BufferedInputStream bis = new BufferedInputStream(fis);
			bis.read(imageData);
			return imageData;
		}
        return imageData;
    }

	@FXML
	public void doSearch() {
		String filed = SearchField.getValue().toString().trim();
		String key = SearchText.getText().trim();
		ObservableList<Mp3Model> filted = FXCollections.observableArrayList();
		if(filed.equals("“Ù¿÷√˚")){
			for(int i =0;i<this.MusicData.size();i++){
				if(this.MusicData.get(i).getName().contains(key)){
					filted.add(this.MusicData.get(i));
				}
			}
			this.UsersTable.setItems(filted);
		}
		if(filed.equals("—›≥™’ﬂ")){
			for(int i =0;i<this.MusicData.size();i++){
				if(this.MusicData.get(i).getSinger().contains(key)){
					filted.add(this.MusicData.get(i));
				}
			}
			this.UsersTable.setItems(filted);
		}
		if(filed.equals("◊®º≠")){
			for(int i =0;i<this.MusicData.size();i++){
				if(this.MusicData.get(i).getAlbum().contains(key)){
					filted.add(this.MusicData.get(i));
				}
			}
			this.UsersTable.setItems(filted);
		}
	}

}
