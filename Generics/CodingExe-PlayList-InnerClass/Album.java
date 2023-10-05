package Playlist;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ListIterator;

public class Album {
    private String name;
    private String artist;
    private SongList songs;

    public Album(String name, String artist) {
        this.name = name;
        this.artist = artist;
        this.songs = new SongList();
    }
          /// Inner Class SongList
          class SongList {
                private ArrayList<Song> songs;

              public SongList() {
                  this.songs = new ArrayList<Song>();
              }
              private boolean add(Song song){
                  if(songs.contains(song)){
                      return false;
                  }
                  this.songs.add(song);
                  return true;
              }
              private Song findSong(String title) {
                  for (Song checkedSong : this.songs) {
                      if (checkedSong.getTitle().equals(title)) {
                          return checkedSong;
                      }
                  }
                  return null;
              }

              private Song findSong(int trackNumber){
                  int index = trackNumber - 1;
                  if((index >= 0) && (index < songs.size())){
                      return  songs.get(index);
                  }
                  return null;
              }
              private ArrayList<Song> getSongs() {
                  return songs;
              }
          }

          ////////////
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
      //  Song song = songs.findSong(trackNumber);
        Song song = this.songs.findSong(trackNumber);
        if (song == null) {
            System.out.println("This album does not have a track " + trackNumber);
            return false;
        }
        playList.add(song);
        return true;
    }

    public boolean addToPlayList(String title, LinkedList<Song> playList) {
        //  Song song = this.songs.findSong(title);
        Song song = songs.findSong(title);
        if (song == null) {
            System.out.println("This song " + title + " not in this album");
            return false;
        }
        playList.add(song);
        return true;
    }

    private Song findSong(String title) {
        for (Song song : songs.getSongs()) {
            if (song.getTitle().equals(title))
                return song;
        }
        return null;
    }
    public void showAllSongs() {
        System.out.println("Songs of the album '" + name + "' by " + artist + ":");
        for (Song song : songs.getSongs()) {
            System.out.println(song.getTitle() + " - " + song.getDuration());
        }
    }
    public static void main(String[] args) {

       ArrayList<Album> albums = new ArrayList<>();
//
//        Album album = new Album("Stormbringer", "Deep Purple");
//        album.addSong("Stormbringer", 4.6);
//        album.addSong("Love don't mean a thing", 4.22);
//        album.addSong("Holy man", 4.3);
//        album.addSong("Hold on", 5.6);
//        album.addSong("Lady double dealer", 3.21);
//        album.addSong("You can't do it right", 6.23);
//        album.addSong("High ball shooter", 4.27);
//        album.addSong("The gypsy", 4.2);
//        album.addSong("Soldier of fortune", 3.13);
//        albums.add(album);
//        // overwriting the album object previously created
//        // As a result, the original album with the title "Stormbringer" and artist is lost
//        album = new Album("For those about to rock", "AC/DC");
//        album.addSong("For those about to rock", 5.44);
//        album.addSong("I put the finger on you", 3.25);
//        album.addSong("Lets go", 3.45);
//        album.addSong("Inject the venom", 3.33);
//        album.addSong("Snowballed", 4.51);
//        album.addSong("Evil walks", 3.45);
//        album.addSong("C.O.D.", 5.25);
//        album.addSong("Breaking the rules", 5.32);
//        album.addSong("Night of the long knives", 5.12);
//        albums.add(album);
//
//        LinkedList<Song> playList = new LinkedList<Song>();
//        albums.get(0).addToPlayList("You can't do it right", playList);
//        albums.get(0).addToPlayList("Holy man", playList);
//        albums.get(0).addToPlayList("Speed king", playList);  // Does not exist
//        albums.get(0).addToPlayList(9, playList);
//        albums.get(1).addToPlayList(3, playList);
//        albums.get(1).addToPlayList(2, playList);
//        albums.get(1).addToPlayList(24, playList);  // There is no track 24
//
//        System.out.println(playList);
//        // System.out.println(albums);
//        album.showAllSongs();

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
        albums.get(1).addToPlayList(8, playList);
        albums.get(1).addToPlayList(3, playList);
        albums.get(1).addToPlayList(2, playList);
        albums.get(1).addToPlayList(24, playList);  // There is no track 24
    }
//    This song Speed king not in this album
//    This album does not have a track 24
}
