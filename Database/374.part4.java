//Main.java
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import music.Artist;

public class Main {
    public static void main ( String[] args ) {

         try ( var sessionFactory = Persistence.createEntityManagerFactory (
                  "dev.lpa.music" );
               EntityManager entityManager = sessionFactory.createEntityManager();
         ) {
             var transaction = entityManager.getTransaction();
             transaction.begin();
           //  Artist artist = new Artist(202, "Muddy Water");
             Artist artist = entityManager.find(Artist.class, 202);
             System.out.println(artist);
             artist.addAlbum( "The Best of Muddy Waters");

           //  artist.removeDuplicates();
             System.out.println(artist);
          //   entityManager.remove(artist);
           //  entityManager.persist( new Artist("Muddy Water"));
             entityManager.merge(artist);
             transaction.commit();

         } catch (Exception e ) {
             e.printStackTrace();
         }
    }
}
// Artist.java
package music;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

@Entity
@Table(name = "artists")
public class Artist {
    @Id
    @Column (name = "artist_id")
    private int artistId;

    @Column (name = "artist_name")
    private String artistName;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true )
    @JoinColumn(name= "artist_id")
    private List<Album> albums = new ArrayList<>();

    public Artist() {
    }

    public Artist(int artistId, String artistName) {
        this.artistId = artistId;
        this.artistName = artistName;
    }

    public List<Album> getAlbums() {
        return albums;
    }

    public Artist(String artistName) {
        this.artistName = artistName;
    }

    public String getArtistName() {
        return artistName;
    }
    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }
    public void addAlbum ( String albumName ) {
        albums.add(new Album(albumName));
    }
    public void removeDuplicates() {
        var set = new TreeSet<>(albums);
        albums.clear();
        albums.addAll(set);
    }
    @Override
    public String toString() {
        return "Artist{" +
                "artistId=" + artistId +
                ", artistName='" + artistName + '\'' +
                ", albums=" + albums +
                '}';
    }
}
// Album.java
package music;

import jakarta.persistence.*;

@Entity
@Table(name = "albums")
public class Album implements Comparable<Album> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "album_id")
    private int albumId;

    @Column (name = "album_name")
    private String albumName;

    public Album() {
    }

    public Album(int albumId, String albumName) {
        this.albumId = albumId;
        this.albumName = albumName;
    }

    public Album(String albumName) {
        this.albumName = albumName;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    @Override
    public String toString() {
        return "Album{" +
                "albumId=" + albumId +
                ", albumName='" + albumName + '\'' +
                '}';
    }

    @Override
    public int compareTo(Album o) {
        return this.albumName.compareTo(o.getAlbumName());
    }

}
/*
Nov 08, 2024 9:41:24 AM org.hibernate.jpa.internal.util.LogHelper logPersistenceUnitInformation
INFO: HHH000204: Processing PersistenceUnitInfo [name: dev.lpa.music]
Nov 08, 2024 9:41:24 AM org.hibernate.Version logVersion
INFO: HHH000412: Hibernate ORM core version 6.6.1.Final
Nov 08, 2024 9:41:24 AM org.hibernate.cache.internal.RegionFactoryInitiator initiateService
INFO: HHH000026: Second-level cache disabled
Nov 08, 2024 9:41:24 AM org.hibernate.engine.jdbc.connections.internal.DriverManagerConnectionProviderImpl configure
WARN: HHH10001002: Using built-in connection pool (not intended for production use)
Nov 08, 2024 9:41:25 AM org.hibernate.engine.jdbc.env.internal.JdbcEnvironmentInitiator initiateService
INFO: HHH10001005: Database info:
	Database JDBC URL [jdbc:mysql://localhost:3306/music]
	Database driver: com.mysql.cj.jdbc.Driver
	Database version: 8.0.39
	Autocommit mode: false
	Isolation level: undefined/unknown
	Minimum pool size: 1
	Maximum pool size: 20
Nov 08, 2024 9:41:26 AM org.hibernate.engine.transaction.jta.platform.internal.JtaPlatformInitiator initiateService
INFO: HHH000489: No JTA platform available (set 'hibernate.transaction.jta.platform' to enable JTA platform integration)
Hibernate: select a1_0.artist_id,a1_0.artist_name from artists a1_0 where a1_0.artist_id=?
Hibernate: select a1_0.artist_id,a1_0.album_id,a1_0.album_name from albums a1_0 where a1_0.artist_id=?
Artist{artistId=202, artistName='Muddy Water', albums=[]}
Artist{artistId=202, artistName='Muddy Water', albums=[Album{albumId=0, albumName='The Best of Muddy Waters'}]}
Hibernate: insert into albums (album_name) values (?)
Hibernate: update albums set artist_id=? where album_id=?
*/
