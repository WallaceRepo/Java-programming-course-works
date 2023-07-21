import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ListIterator;

public class Album {
    private String name;
    private String artist;
    private ArrayList<Song> songs;

    public Album(String name, String artist) {
        this.name = name;
        this.artist = artist;
        this.songs = new ArrayList<>();
    }

    public String getArtist() {
        return artist;
    }

    public boolean addSong(String title, double duration) {
        if (findSong(title) != null)
            return false;
        songs.add(new Song(title, duration));
        return true;
    }

    public boolean addToPlayList(int trackNumber, LinkedList<Song> playList) {
        ListIterator<Song> listIterator = songs.listIterator();
        while (listIterator.hasNext()) {
            if (songs.indexOf(listIterator.next()) == (trackNumber - 1)) {
                playList.add(listIterator.previous());
                return true;
            }
        }
        return false;
    }

    public boolean addToPlayList(String title, LinkedList<Song> playList) {
        Song song = findSong(title);
        if (song == null) {
            return false;
        }
        playList.add(song);
        return true;
    }

    private Song findSong(String title) {
        for (Song song : songs) {
            if (song.getTitle().equals(title))
                return song;
        }
        return null;
    }
    public void showAllSongs() {
        System.out.println("Songs of the album '" + name + "' by " + artist + ":");
        for (Song song : songs) {
            System.out.println(song.getTitle() + " - " + song.getDuration());
        }
    }
    public static void main(String[] args) {

        ArrayList<Album> albums = new ArrayList<>();

        Album album = new Album("Stormbringer", "Deep Purple");
        album.addSong("Stormbringer", 4.6);
        album.addSong("Love don't mean a thing", 4.22);
        album.addSong("Holy man", 4.3);
        album.addSong("Hold on", 5.6);
        album.addSong("Lady double dealer", 3.21);
        album.addSong("You can't do it right", 6.23);
        album.addSong("High ball shooter", 4.27);
        album.addSong("The gypsy", 4.2);
        album.addSong("Soldier of fortune", 3.13);
        albums.add(album);
        // overwriting the album object previously created
        // As a result, the original album with the title "Stormbringer" and artist is lost
        album = new Album("For those about to rock", "AC/DC");
        album.addSong("For those about to rock", 5.44);
        album.addSong("I put the finger on you", 3.25);
        album.addSong("Lets go", 3.45);
        album.addSong("Inject the venom", 3.33);
        album.addSong("Snowballed", 4.51);
        album.addSong("Evil walks", 3.45);
        album.addSong("C.O.D.", 5.25);
        album.addSong("Breaking the rules", 5.32);
        album.addSong("Night of the long knives", 5.12);
        albums.add(album);

        LinkedList<Song> playList = new LinkedList<Song>();
        albums.get(0).addToPlayList("You can't do it right", playList);
        albums.get(0).addToPlayList("Holy man", playList);
        albums.get(0).addToPlayList("Speed king", playList);  // Does not exist
        albums.get(0).addToPlayList(9, playList);
        albums.get(1).addToPlayList(3, playList);
        albums.get(1).addToPlayList(2, playList);
        albums.get(1).addToPlayList(24, playList);  // There is no track 24

        System.out.println(playList);
       // System.out.println(albums);
        album.showAllSongs();
    }

}

///
public class Song {
    private String title;
    private double duration;
    public Song(String title, double duration) {
        this.title = title;
        this.duration = duration;
    }

    public String getTitle() {
        return title;
    }

    public double getDuration() {
        return duration;
    }

    @Override
    public String toString() {
        return title + ":" + duration;
    }
}
/*
[You can't do it right:6.23, Holy man:4.3, Soldier of fortune:3.13, Lets go:3.45, I put the finger on you:3.25]
[Album@16b98e56, Album@7ef20235]
Songs of the album 'For those about to rock' by AC/DC:
For those about to rock - 5.44
I put the finger on you - 3.25
Lets go - 3.45
Inject the venom - 3.33
Snowballed - 4.51
Evil walks - 3.45
C.O.D. - 5.25
Breaking the rules - 5.32
Night of the long knives - 5.12

    */
