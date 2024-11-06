// src/main.java
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import music.Artist;

public class Main {
    public static void main ( String[] args ) {

         try ( var sessionFactory = Persistence.createEntityManagerFactory(
                  "dev.lpa.music" );
               EntityManager entityManager = sessionFactory.createEntityManager();
         ) {
             var transaction = entityManager.getTransaction();
             transaction.begin();
             entityManager.persist( new Artist("Muddy Water"));
             transaction.commit();

         } catch (Exception e ) {
             e.printStackTrace();
         }
    }
}

//  src/META-INF/persistance.xml

<persistence xmlns="http://java.sun.com/xml/ns/persistence" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://java.sun.com/xml/ns/persistence http://java.sun.com/xml/ns/persistence/persistence_2_0.xsd" version="2.0">

    <persistence-unit name="dev.lpa.music">

        <properties>
            <property name="jakarta.persistence.jdbc.driver" value="com.mysql.cj.jdbc.Driver"/>
            <property name="jakarta.persistence.jdbc.url" value="jdbc:mysql://localhost:3306/music"/>
            <property name="jakarta.persistence.jdbc.user" value="devuser"/>
            <property name="jakarta.persistence.jdbc.password" value="0802"/>
            <property name="hibernate.show_sql" value="true" />
        </properties>
    </persistence-unit>
</persistence>

// src/music/Artist.java
package music;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "artists")
public class Artist {
    @Id
    @Column (name = "artist_id")
    private int artistId;

    @Column (name = "artist_name")
    private String artistName;

    public Artist() {
    }

    public Artist(int artistId, String artistName) {
        this.artistId = artistId;
        this.artistName = artistName;
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

    @Override
    public String toString() {
        return "Artist{" +
                "artistId=" + artistId +
                ", artistName='" + artistName + '\'' +
                '}';
    }
}


/*
Nov 05, 2024 8:27:04 PM org.hibernate.jpa.internal.util.LogHelper logPersistenceUnitInformation
INFO: HHH000204: Processing PersistenceUnitInfo [name: dev.lpa.music]
Nov 05, 2024 8:27:04 PM org.hibernate.Version logVersion
INFO: HHH000412: Hibernate ORM core version 6.6.1.Final
Nov 05, 2024 8:27:04 PM org.hibernate.cache.internal.RegionFactoryInitiator initiateService
INFO: HHH000026: Second-level cache disabled
Nov 05, 2024 8:27:04 PM org.hibernate.engine.jdbc.connections.internal.DriverManagerConnectionProviderImpl configure
WARN: HHH10001002: Using built-in connection pool (not intended for production use)
Nov 05, 2024 8:27:05 PM org.hibernate.engine.jdbc.env.internal.JdbcEnvironmentInitiator initiateService
INFO: HHH10001005: Database info:
	Database JDBC URL [jdbc:mysql://localhost:3306/music]
	Database driver: com.mysql.cj.jdbc.Driver
	Database version: 8.0.39
	Autocommit mode: false
	Isolation level: undefined/unknown
	Minimum pool size: 1
	Maximum pool size: 20
Nov 05, 2024 8:27:06 PM org.hibernate.engine.transaction.jta.platform.internal.JtaPlatformInitiator initiateService
INFO: HHH000489: No JTA platform available (set 'hibernate.transaction.jta.platform' to enable JTA platform integration)
Hibernate: insert into artists (artist_name,artist_id) values (?,?)
*/

// using find() method
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import music.Artist;

public class Main {
    public static void main ( String[] args ) {

         try ( var sessionFactory = Persistence.createEntityManagerFactory(
                  "dev.lpa.music" );
               EntityManager entityManager = sessionFactory.createEntityManager();
         ) {
             var transaction = entityManager.getTransaction();
             transaction.begin();
             Artist artist = entityManager.find(Artist.class, 203);
             System.out.println(artist);
           //  entityManager.persist( new Artist("Muddy Water"));
             transaction.commit();

         } catch (Exception e ) {
             e.printStackTrace();
         }
    }
}

// Using remove()
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import music.Artist;

public class Main {
    public static void main ( String[] args ) {

         try ( var sessionFactory = Persistence.createEntityManagerFactory(
                  "dev.lpa.music" );
               EntityManager entityManager = sessionFactory.createEntityManager();
         ) {
             var transaction = entityManager.getTransaction();
             transaction.begin();
             Artist artist = entityManager.find(Artist.class, 203);
             System.out.println(artist);
             artist.setArtistName("Muddy Waters");
          //   entityManager.remove(artist);
           //  entityManager.persist( new Artist("Muddy Water"));
             transaction.commit();

         } catch (Exception e ) {
             e.printStackTrace();
         }
    }
}
/*
Nov 05, 2024 8:36:30 PM org.hibernate.jpa.internal.util.LogHelper logPersistenceUnitInformation
INFO: HHH000204: Processing PersistenceUnitInfo [name: dev.lpa.music]
Nov 05, 2024 8:36:30 PM org.hibernate.Version logVersion
INFO: HHH000412: Hibernate ORM core version 6.6.1.Final
Nov 05, 2024 8:36:30 PM org.hibernate.cache.internal.RegionFactoryInitiator initiateService
INFO: HHH000026: Second-level cache disabled
Nov 05, 2024 8:36:30 PM org.hibernate.engine.jdbc.connections.internal.DriverManagerConnectionProviderImpl configure
WARN: HHH10001002: Using built-in connection pool (not intended for production use)
Nov 05, 2024 8:36:31 PM org.hibernate.engine.jdbc.env.internal.JdbcEnvironmentInitiator initiateService
INFO: HHH10001005: Database info:
	Database JDBC URL [jdbc:mysql://localhost:3306/music]
	Database driver: com.mysql.cj.jdbc.Driver
	Database version: 8.0.39
	Autocommit mode: false
	Isolation level: undefined/unknown
	Minimum pool size: 1
	Maximum pool size: 20
Nov 05, 2024 8:36:32 PM org.hibernate.engine.transaction.jta.platform.internal.JtaPlatformInitiator initiateService
INFO: HHH000489: No JTA platform available (set 'hibernate.transaction.jta.platform' to enable JTA platform integration)
Hibernate: select a1_0.artist_id,a1_0.artist_name from artists a1_0 where a1_0.artist_id=?
Artist{artistId=203, artistName='Muddy Water'}
*/

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import music.Artist;

public class Main {
    public static void main ( String[] args ) {

         try ( var sessionFactory = Persistence.createEntityManagerFactory(
                  "dev.lpa.music" );
               EntityManager entityManager = sessionFactory.createEntityManager();
         ) {
             var transaction = entityManager.getTransaction();
             transaction.begin();
             Artist artist = new Artist(202, "Muddy Water");
//             Artist artist = entityManager.find(Artist.class, 203);
//             System.out.println(artist);
//             artist.setArtistName("Muddy Waters");
          //   entityManager.remove(artist);
           //  entityManager.persist( new Artist("Muddy Water"));
             entityManager.merge(artist);
             transaction.commit();

         } catch (Exception e ) {
             e.printStackTrace();
         }
    }
}
