// MainQuery.java


import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Tuple;
import music.Artist;

import java.util.List;

public class MainQuery {

    public static void main(String[] args) {
        List <Artist> artists =null;
        try (EntityManagerFactory emf = Persistence.createEntityManagerFactory("dev.lpa.music");
              EntityManager em = emf.createEntityManager();) {

            var transacion = em.getTransaction();
            transacion.begin();
            artists = getArtistsJPQL(em, "%Stev%");
            artists.forEach(System.out::println);

            var names = getArtistsNames(em, "%Stev%");
            names.forEach(System.out::println);
            transacion.commit();

        } catch ( Exception e ) {
            e.printStackTrace();
        }
    }
    private static List<Artist> getArtistsJPQL (EntityManager em, String matchedValue) {
        String jpql = "SELECT a FROM Artist a WHERE a.artistName LIKE ?1";
      //  String jpql = "SELECT a FROM Artist a WHERE a.artistName LIKE :partialName";
        var query = em.createQuery(jpql, Artist.class);
     //   query.setParameter("partialName", matchedValue);
        query.setParameter( 1, matchedValue);
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
    private static List<Tuple> getArtistsNames (EntityManager em, String matchedValue) {
        String jpql = "SELECT a.artistId, a.artistName FROM Artist a WHERE a.artistName LIKE ?1";
        //  String jpql = "SELECT a FROM Artist a WHERE a.artistName LIKE :partialName";
        var query = em.createQuery(jpql, Tuple.class);
        //   query.setParameter("partialName", matchedValue);
        query.setParameter( 1, matchedValue);
        return query.getResultList();
    }

}

/*

Hibernate: select a1_0.artist_id,a1_0.artist_name from artists a1_0 where a1_0.artist_name like ? escape ''
Hibernate: select a1_0.artist_id,a1_0.album_id,a1_0.album_name from albums a1_0 where a1_0.artist_id=?
Artist{artistId=61, artistName='Steve Vai', albums=[Album{albumId=165, albumName='Sex & Religion'}, Album{albumId=604, albumName='Sex & Religion'}]}
Hibernate: select a1_0.artist_id,a1_0.album_id,a1_0.album_name from albums a1_0 where a1_0.artist_id=?
Artist{artistId=170, artistName='Steve Harley & Cockney Rebel', albums=[Album{albumId=277, albumName='Live and Unleashed'}, Album{albumId=716, albumName='Live and Unleashed'}]}
Hibernate: select a1_0.artist_id,a1_0.album_id,a1_0.album_name from albums a1_0 where a1_0.artist_id=?
Artist{artistId=173, artistName='Steve Hillage', albums=[Album{albumId=219, albumName='Motivation Radio'}, Album{albumId=658, albumName='Motivation Radio'}]}
Hibernate: select a1_0.artist_id,a1_0.album_id,a1_0.album_name from albums a1_0 where a1_0.artist_id=?
Artist{artistId=175, artistName='Seasick Steve', albums=[Album{albumId=256, albumName='Walkin' Man The Best of Seasick Steve'}, Album{albumId=436, albumName='Man From Another Time'}, Album{albumId=695, albumName='Walkin' Man The Best of Seasick Steve'}, Album{albumId=875, albumName='Man From Another Time'}]}
Hibernate: select a1_0.artist_id,a1_0.album_id,a1_0.album_name from albums a1_0 where a1_0.artist_id=?
Artist{artistId=180, artistName='Steve Hackett', albums=[Album{albumId=222, albumName='Voyage of the Acolyte'}, Album{albumId=243, albumName='Please Don't Touch!'}, Album{albumId=661, albumName='Voyage of the Acolyte'}, Album{albumId=682, albumName='Please Don't Touch!'}]}
Hibernate: select a1_0.artist_id,a1_0.album_id,a1_0.album_name from albums a1_0 where a1_0.artist_id=?
Artist{artistId=200, artistName='Stevie Ray Vaughan', albums=[Album{albumId=373, albumName='Collections'}, Album{albumId=812, albumName='Collections'}]}
Hibernate: select a1_0.artist_id,a1_0.artist_name from artists a1_0 where a1_0.artist_name like ? escape ''
[61, Steve Vai]
[170, Steve Harley & Cockney Rebel]
[173, Steve Hillage]
[175, Seasick Steve]
[180, Steve Hackett]
[200, Stevie Ray Vaughan]

*/
