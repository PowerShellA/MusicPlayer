package application;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Mp3Model {
	private final IntegerProperty Id;
	private final StringProperty Singer;
    private final StringProperty Name;
    private final StringProperty Format;
    private final StringProperty Path;
    private final StringProperty Time2; 
    private final IntegerProperty Time1;
    private final StringProperty Album;
    
    public Mp3Model() {
    	this.Id = new SimpleIntegerProperty(-1);
    	this.Singer = new SimpleStringProperty("");
    	this.Name = new SimpleStringProperty("");
    	this.Format = new SimpleStringProperty("mp3");
    	this.Path = new SimpleStringProperty("");
    	this.Time2 = new SimpleStringProperty("");
    	this.Time1 = new SimpleIntegerProperty(0);
    	this.Album = new SimpleStringProperty("");
    }
    
    public String getAlbum() {
        return Album.get();
    }

    public void setAlbum(String Album) {
        this.Album.set(Album);
    }
    
    public StringProperty AlbumProperty() {
        return Album;
    }
    
    public int getTime1() {
        return Time1.get();
    }

    public void setTime1(int Time1) {
        this.Time1.set(Time1);
    }
    
    public IntegerProperty Time1Property() {
        return Time1;
    }
    
    public String getSinger() {
        return Singer.get();
    }

    public void setSinger(String Singer) {
        this.Singer.set(Singer);
    }
    
    public StringProperty SingerProperty() {
        return Singer;
    }
    
    public int getId() {
        return Id.get();
    }

    public void setId(int Id) {
        this.Id.set(Id);
    }
    
    public IntegerProperty IdProperty() {
        return Id;
    }
    
    public String getName() {
        return Name.get();
    }

    public void setName(String Name) {
        this.Name.set(Name);
    }
    
    public StringProperty NameProperty() {
        return Name;
    }
    
    public String getFormat() {
        return Format.get();
    }

    public void setFormat(String Format) {
        this.Format.set(Format);
    }
    
    public StringProperty FormatProperty() {
        return Format;
    }
    
    public String getPath() {
        return Path.get();
    }

    public void setPath(String Path) {
        this.Path.set(Path);
    }
    
    public StringProperty PathProperty() {
        return Path;
    }
    
    public String getTime2() {
        return Time2.get();
    }
    
    public void setTime2(String Time2) {
        this.Time2.set(Time2);
    }
    
    public StringProperty Time2Property() {
        return Time2;
    }
}
