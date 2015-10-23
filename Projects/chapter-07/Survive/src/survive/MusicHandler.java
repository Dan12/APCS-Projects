package survive;

import java.io.File;

public class MusicHandler {
    File[] songs;
    int songAt = 5;
    int numSongs;
    
    public MusicHandler(){
        File directory = new File(new File("").getAbsolutePath().concat("/src/assets"));
        File[] tempFiles = directory.listFiles();
        for(File f : tempFiles){
            if(f.getName().contains(".mp3"))
                numSongs++;
        }
        songs = new File[numSongs];
        int i = 0;
        for(File f : tempFiles){
            if(f.getName().contains(".mp3")){
                songs[i] = f;
                i++;
            }
        }
    }
    
    public void setSongAt(String fn){
        for(int i = 0; i < numSongs; i++){
            if(songs[i].getName().equals(fn)){
                songAt = i;
                break;
            }
        }
    }
    
    public File getNextFile(){
        songAt++;
        if(songAt >= numSongs)
            songAt = 0;
        return songs[songAt];
    }
}
