package model;

import model.entities.AlbumViewEntity;
import model.entities.InstrumentViewEntity;
import model.entities.OtherViewEntity;
import org.hibernate.Session;

import javax.persistence.Query;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

public final class FilterProductFunctions {
    /**
     * @param filters is map of 'column name' and 'user input'
     */
    public static List<AlbumViewEntity> filterAlbums(Map<String, String> filters) {
        try (Session session = SessionFactoryManager.getNewSession()) {
            String queryString = buildQueryString("FROM model.entities.AlbumViewEntity", filters);
            Query query = session.createQuery(queryString);
            return query.getResultList();
        }
    }

    public static List<InstrumentViewEntity> filterInstruments(Map<String, String> filters) {
        try (Session session = SessionFactoryManager.getNewSession()) {
            String queryString = buildQueryString("FROM model.entities.InstrumentViewEntity", filters);
            Query query = session.createQuery(queryString);
            return query.getResultList();
        }
    }

    public static List<OtherViewEntity> filterOther(Map<String, String> filters) {
        try (Session session = SessionFactoryManager.getNewSession()) {
            String queryString = buildQueryString("FROM model.entities.OtherViewEntity", filters);
            Query query = session.createQuery(queryString);
            return query.getResultList();
        }
    }

    private static String buildQueryString(String initialQueryString, Map<String, String> filters) {
        StringJoiner joiner = new StringJoiner(" AND ", " WHERE ", "");
        for (Map.Entry<String, String> entry : filters.entrySet()) {
            joiner.add(" "+entry.getKey()+" LIKE '%"+entry.getValue()+"%' ");
        }
        joiner.setEmptyValue("");
//        Iterator it = filters.entrySet().iterator();
//        if (it.hasNext()) initialQueryString = initialQueryString.concat(" WHERE ");
//        while (it.hasNext()) {
//            Map.Entry pair = (Map.Entry)it.next();
//            initialQueryString = initialQueryString.concat(" "+pair.getKey()+" LIKE '%"+pair.getValue()+"%' ");
//            if (it.hasNext()) initialQueryString = initialQueryString.concat(" AND ");
//        }
//        System.out.println(initialQueryString);
        return initialQueryString+joiner.toString();
    }
}
