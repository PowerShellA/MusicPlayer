package application;

import java.io.FileNotFoundException;
import java.io.IOException;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.audio.mp3.MP3AudioHeader;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.TagException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;

public class ReadFile {
	
	private ObservableList<Mp3Model> MusicData = FXCollections.observableArrayList();
	
    public ReadFile() {
    }

    public boolean readfile(String filepath) throws FileNotFoundException, IOException, CannotReadException, TagException, ReadOnlyFileException, InvalidAudioFrameException {
        File file = new File(filepath);
		if (!file.isDirectory()) {
		    System.out.println("文件");
		    System.out.println("absolutepath=" + file.getAbsolutePath());
		    System.out.println("name=" + file.getName());
		} else if (file.isDirectory()) {
		    String[] filelist = file.list();
		    for (int i = 1; i < filelist.length; i++) {
		    	if(filelist[i].endsWith("mp3")){
		    		File readfile = new File(filepath + "\\" + filelist[i]);
		            Mp3Model mu = new Mp3Model();
		            MP3File mp3File = (MP3File) AudioFileIO.read(new File(readfile.getAbsolutePath()));
		            MP3AudioHeader audioHeader = (MP3AudioHeader) mp3File.getAudioHeader();
		            try{
		                String songname = reg(mp3File.getID3v2Tag().frameMap.get("TIT2").toString());//歌名
		                String artist = reg(mp3File.getID3v2Tag().frameMap.get("TPE1").toString());//歌手
		                String album = reg(mp3File.getID3v2Tag().frameMap.get("TALB").toString());//专辑
		                int duration = audioHeader.getTrackLength();//时长
		                mu.setId(i);
		                mu.setName(songname);
		                mu.setAlbum(album);
		                mu.setPath(readfile.getAbsolutePath());
		                mu.setSinger(artist);
		                mu.setTime1(duration);
		                this.MusicData.add(mu);
		            } catch(Exception e){
		                continue;
		            }
		    	}
		    }
		}
            return true;
        }
    
    public ObservableList<Mp3Model> getMusicData(){
    	return this.MusicData;
    }
    
    private String reg(String input) {
    	return input.substring(input.indexOf('"') + 1, input.lastIndexOf('"'));
    }
}