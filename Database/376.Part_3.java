import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Tuple;
import music.Artist;

import java.util.List;
import java.util.stream.Stream;

public class MainQuery {

    public static void main(String[] args) {
        List <Artist> artists =null;
        try (EntityManagerFactory emf = Persistence.createEntityManagerFactory("dev.lpa.music");
              EntityManager em = emf.createEntityManager();) {

            var transacion = em.getTransaction();
            transacion.begin();
            artists = getArtistsJPQL(em, "%Greatest Hits%");
            artists.forEach(System.out::println);

//            var names = getArtistsNames(em, "%Stev%");
//            names.map ( a -> new Artist(
//                    a.get(0, Integer.class), (String)  a.get(1)
//            )).forEach(System.out::println );
//
//            var names = getArtistsNames(em, "%Stev%");
//            names.map ( a -> new Artist(
//                    a.get( "id", Integer.class), (String)  a.get("Name")
//            )).forEach(System.out::println );

           // names.forEach(System.out::println);
            transacion.commit();

        } catch ( Exception e ) {
            e.printStackTrace();
        }
    }
    private static List<Artist> getArtistsJPQL (EntityManager em, String matchedValue) {
     //   String jpql = "SELECT a FROM Artist a WHERE a.artistName LIKE ?1";
      //  String jpql = "SELECT a FROM Artist a WHERE a.artistName LIKE :partialName";
        String jpql = "SELECT a FROM Artist a JOIN albums album WHERE album.albumName LIKE ?1 OR album.albumName LIKE ?2";
        var query = em.createQuery(jpql, Artist.class);
     //   query.setParameter("partialName", matchedValue);
        query.setParameter( 1, matchedValue);
        query.setParameter(2, "%Best of%");
        return query.getResultList();
    }
//    private static List<String> getArtistsNames (EntityManager em, String matchedValue) {
//        String jpql = "SELECT a.artistName FROM Artist a WHERE a.artistName LIKE ?1";
//        //  String jpql = "SELECT a FROM Artist a WHERE a.artistName LIKE :partialName";
//        var query = em.createQuery(jpql, String.class);
//        //   query.setParameter("partialName", matchedValue);
//        query.setParameter( 1, matchedValue);
//        return query.getResultList();
//    }
//    private static List<Tuple> getArtistsNames (EntityManager em, String matchedValue) {
//        String jpql = "SELECT a.artistId, a.artistName FROM Artist a WHERE a.artistName LIKE ?1";
//        //  String jpql = "SELECT a FROM Artist a WHERE a.artistName LIKE :partialName";
//        var query = em.createQuery(jpql, Tuple.class);
//        //   query.setParameter("partialName", matchedValue);
//        query.setParameter( 1, matchedValue);
//        return query.getResultList();
//    }
        private static Stream <Tuple> getArtistsNames (EntityManager em, String matchedValue) {
        String jpql = "SELECT a.artistId id, a.artistName as Name FROM Artist a WHERE a.artistName LIKE ?1";
        //  String jpql = "SELECT a FROM Artist a WHERE a.artistName LIKE :partialName";
        var query = em.createQuery(jpql, Tuple.class);
        //   query.setParameter("partialName", matchedValue);
        query.setParameter( 1, matchedValue);
        return query.getResultStream();
    }

}

/*
Nov 08, 2024 4:26:04 PM org.hibernate.jpa.internal.util.LogHelper logPersistenceUnitInformation
INFO: HHH000204: Processing PersistenceUnitInfo [name: dev.lpa.music]
Nov 08, 2024 4:26:04 PM org.hibernate.Version logVersion
INFO: HHH000412: Hibernate ORM core version 6.6.1.Final
Nov 08, 2024 4:26:04 PM org.hibernate.cache.internal.RegionFactoryInitiator initiateService
INFO: HHH000026: Second-level cache disabled
Nov 08, 2024 4:26:05 PM org.hibernate.engine.jdbc.connections.internal.DriverManagerConnectionProviderImpl configure
WARN: HHH10001002: Using built-in connection pool (not intended for production use)
Nov 08, 2024 4:26:05 PM org.hibernate.engine.jdbc.env.internal.JdbcEnvironmentInitiator initiateService
INFO: HHH10001005: Database info:
	Database JDBC URL [jdbc:mysql://localhost:3306/music]
	Database driver: com.mysql.cj.jdbc.Driver
	Database version: 8.0.39
	Autocommit mode: false
	Isolation level: undefined/unknown
	Minimum pool size: 1
	Maximum pool size: 20
Nov 08, 2024 4:26:06 PM org.hibernate.engine.transaction.jta.platform.internal.JtaPlatformInitiator initiateService
INFO: HHH000489: No JTA platform available (set 'hibernate.transaction.jta.platform' to enable JTA platform integration)
Hibernate: select a1_0.artist_id,a1_0.artist_name from artists a1_0 join albums a2_0 on a1_0.artist_id=a2_0.artist_id where a2_0.album_name like ? escape '' or a2_0.album_name like ? escape ''
Hibernate: select a1_0.artist_id,a1_0.album_id,a1_0.album_name from albums a1_0 where a1_0.artist_id=?
Artist{artistId=198, artistName='Tom Petty & The Heartbreakers', albums=[Album{albumId=47, albumName='Greatest Hits'}, Album{albumId=486, albumName='Greatest Hits'}]}
Hibernate: select a1_0.artist_id,a1_0.album_id,a1_0.album_name from albums a1_0 where a1_0.artist_id=?
Artist{artistId=153, artistName='Troggs', albums=[Album{albumId=46, albumName='Athens Andover'}, Album{albumId=97, albumName='Wild Thing - The Greatest Hits'}, Album{albumId=422, albumName='Greatest Hits'}, Album{albumId=485, albumName='Athens Andover'}, Album{albumId=536, albumName='Wild Thing - The Greatest Hits'}, Album{albumId=861, albumName='Greatest Hits'}]}
Hibernate: select a1_0.artist_id,a1_0.album_id,a1_0.album_name from albums a1_0 where a1_0.artist_id=?
Artist{artistId=10, artistName='Procol Harum', albums=[Album{albumId=195, albumName='The Best of PROCOL HARUM Halcyon Daze'}, Album{albumId=634, albumName='The Best of PROCOL HARUM Halcyon Daze'}]}
Hibernate: select a1_0.artist_id,a1_0.album_id,a1_0.album_name from albums a1_0 where a1_0.artist_id=?
Artist{artistId=137, artistName='Doors', albums=[Album{albumId=202, albumName='Best Of The Doors'}, Album{albumId=641, albumName='Best Of The Doors'}]}
Hibernate: select a1_0.artist_id,a1_0.album_id,a1_0.album_name from albums a1_0 where a1_0.artist_id=?
Artist{artistId=92, artistName='Fleetwood Mac', albums=[Album{albumId=41, albumName='The Dance'}, Album{albumId=232, albumName='The Very Best Of'}, Album{albumId=238, albumName='Greatest Hits'}, Album{albumId=324, albumName='The Best of'}, Album{albumId=416, albumName='Rumours'}, Album{albumId=480, albumName='The Dance'}, Album{albumId=671, albumName='The Very Best Of'}, Album{albumId=677, albumName='Greatest Hits'}, Album{albumId=763, albumName='The Best of'}, Album{albumId=855, albumName='Rumours'}]}
Hibernate: select a1_0.artist_id,a1_0.album_id,a1_0.album_name from albums a1_0 where a1_0.artist_id=?
Artist{artistId=175, artistName='Seasick Steve', albums=[Album{albumId=256, albumName='Walkin' Man The Best of Seasick Steve'}, Album{albumId=436, albumName='Man From Another Time'}, Album{albumId=695, albumName='Walkin' Man The Best of Seasick Steve'}, Album{albumId=875, albumName='Man From Another Time'}]}
Hibernate: select a1_0.artist_id,a1_0.album_id,a1_0.album_name from albums a1_0 where a1_0.artist_id=?
Artist{artistId=86, artistName='10cc', albums=[Album{albumId=320, albumName='The Best Of The Early Years'}, Album{albumId=759, albumName='The Best Of The Early Years'}]}
Hibernate: select a1_0.artist_id,a1_0.album_id,a1_0.album_name from albums a1_0 where a1_0.artist_id=?
Artist{artistId=119, artistName='Bob Marley', albums=[Album{albumId=26, albumName='The Collection Volume One'}, Album{albumId=77, albumName='Natty Dread'}, Album{albumId=95, albumName='Stir it Up - Vol 4'}, Album{albumId=251, albumName='Soul Shakeddown Party - Vol 3'}, Album{albumId=315, albumName='The Collection Vol 2 (Riding High)'}, Album{albumId=323, albumName='The Very Best Of The Early Years 1968-74'}, Album{albumId=465, albumName='The Collection Volume One'}, Album{albumId=516, albumName='Natty Dread'}, Album{albumId=534, albumName='Stir it Up - Vol 4'}, Album{albumId=690, albumName='Soul Shakeddown Party - Vol 3'}, Album{albumId=754, albumName='The Collection Vol 2 (Riding High)'}, Album{albumId=762, albumName='The Very Best Of The Early Years 1968-74'}]}
Hibernate: select a1_0.artist_id,a1_0.album_id,a1_0.album_name from albums a1_0 where a1_0.artist_id=?
Artist{artistId=148, artistName='Yardbirds', albums=[Album{albumId=328, albumName='The Very Best of the Yardbirds'}, Album{albumId=767, albumName='The Very Best of the Yardbirds'}]}
Hibernate: select a1_0.artist_id,a1_0.album_id,a1_0.album_name from albums a1_0 where a1_0.artist_id=?
Artist{artistId=188, artistName='Billy Joel', albums=[Album{albumId=345, albumName='Greatest Hits Vol. III'}, Album{albumId=784, albumName='Greatest Hits Vol. III'}]}
Hibernate: select a1_0.artist_id,a1_0.album_id,a1_0.album_name from albums a1_0 where a1_0.artist_id=?
Artist{artistId=47, artistName='Manfred Mann', albums=[Album{albumId=139, albumName='The Ascent Of Mann'}, Album{albumId=370, albumName='The Very Best Of'}, Album{albumId=578, albumName='The Ascent Of Mann'}, Album{albumId=809, albumName='The Very Best Of'}]}
Hibernate: select a1_0.artist_id,a1_0.album_id,a1_0.album_name from albums a1_0 where a1_0.artist_id=?
Artist{artistId=176, artistName='Billy Idol', albums=[Album{albumId=399, albumName='Greatest Hits'}, Album{albumId=838, albumName='Greatest Hits'}]}
Hibernate: select a1_0.artist_id,a1_0.album_id,a1_0.album_name from albums a1_0 where a1_0.artist_id=?
Artist{artistId=197, artistName='T.Rex', albums=[Album{albumId=404, albumName='The Very Best Of Marc Bolan And T. Rex'}, Album{albumId=843, albumName='The Very Best Of Marc Bolan And T. Rex'}]}
Hibernate: select a1_0.artist_id,a1_0.album_id,a1_0.album_name from albums a1_0 where a1_0.artist_id=?
Artist{artistId=54, artistName='Bon Jovi', albums=[Album{albumId=437, albumName='Cross Road - The Best Of'}, Album{albumId=876, albumName='Cross Road - The Best Of'}]}
Hibernate: select a1_0.artist_id,a1_0.album_id,a1_0.album_name from albums a1_0 where a1_0.artist_id=?
Artist{artistId=202, artistName='Muddy Water', albums=[Album{albumId=899, albumName='The Best of Muddy Waters'}]}
*/
