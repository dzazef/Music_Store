package sh;

import db.LoginManager;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

public class LoadAlbums {
    public static void loadAlbums(){
        Session session = LoginManager.getSession();
        try (BufferedReader br = new BufferedReader(new FileReader("/home/dzazef/Pobrane/file.txt"))) {
            String line;
            String[] temp;
            while ((line = br.readLine()) != null) {
                temp = line.split(";");
                session.beginTransaction();
                Query query = session.
                        createSQLQuery("CALL music_store.add_album((:price), (:title), (:release_year), (:img_link), (:artist_name), (:artist_genre), (:songs_count), (:tracklist), (:duration))")
                        .setParameter("price", ThreadLocalRandom.current().nextInt(30, 50 + 1))
                        .setParameter("title", temp[0])
                        .setParameter("release_year", temp[1])
                        .setParameter("img_link", temp[2])
                        .setParameter("artist_name", temp[3])
                        .setParameter("artist_genre", temp[4])
                        .setParameter("songs_count", temp[5])
                        .setParameter("tracklist", temp[6])
                        .setParameter("duration", temp[7]);
                query.executeUpdate();
                session.getTransaction().commit();
//                System.out.println(temp[0]);
//                System.out.println(temp[1]);
//                System.out.println(temp[2]);
//                System.out.println(temp[3]);
//                System.out.println(temp[4]);
//                System.out.println(temp[5]);
//                System.out.println(temp[6]);
//                System.out.println(temp[7]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        session.close();
    }
}