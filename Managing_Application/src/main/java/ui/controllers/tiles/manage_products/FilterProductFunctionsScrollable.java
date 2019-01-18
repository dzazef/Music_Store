package ui.controllers.tiles.manage_products;

import db.LoginManager;
import model.entities.AlbumViewEntity;
import model.entities.InstrumentViewEntity;
import model.entities.OtherViewEntity;
import model.entities.StatusLogsEntity;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;

import javax.persistence.Query;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;


public final class FilterProductFunctionsScrollable {
    public static final int MAX_RECORDS = 40;
    /**
     * @param filters is map of 'column name' and 'user input'
     */
    public static ScrollableResults filterAlbums(Map<String, String> filters) {
        try (Session session = LoginManager.getSession()) {
            String queryString = buildQueryString(
                    "FROM model.entities.AlbumViewEntity WHERE storageEntity.productsAvailable > 0 ",filters);
                            //"StorageEntity st ON al.productId = st.productId WHERE st.productsAvailable > 0 ",
            ScrollableResults scrollableResults = session.createQuery(queryString).
                    setReadOnly(true).setFetchSize(MAX_RECORDS).scroll(ScrollMode.SCROLL_SENSITIVE);
            return scrollableResults;
        }
    }

    public static ScrollableResults filterInstruments(Map<String, String> filters) {
        try (Session session = LoginManager.getSession()) {
            String queryString = buildQueryString(
                    "FROM model.entities.InstrumentViewEntity WHERE storageEntity.productsAvailable > 0 ", filters);
            ScrollableResults scrollableResults = session.createQuery(queryString).
                    setReadOnly(true).setFetchSize(MAX_RECORDS).scroll(ScrollMode.FORWARD_ONLY);
            return scrollableResults;
        }
    }

    public static ScrollableResults filterOther(Map<String, String> filters) {
        try (Session session = LoginManager.getSession()) {
            String queryString = buildQueryString(
                    "FROM model.entities.OtherViewEntity WHERE storageEntity.productsAvailable > 0 ", filters);
            ScrollableResults scrollableResults = session.createQuery(queryString).
                    setReadOnly(true).setFetchSize(MAX_RECORDS).scroll(ScrollMode.FORWARD_ONLY);
            return scrollableResults;
        }
    }

    private static String buildQueryString(String initialQueryString, Map<String, String> filters) {
        StringJoiner joiner = new StringJoiner(" AND ", "AND", "");
        for (Map.Entry<String, String> entry : filters.entrySet()) {
            joiner.add(" "+entry.getKey()+" LIKE '%"+entry.getValue()+"%' ");
        }
        joiner.setEmptyValue("");
        return initialQueryString+joiner.toString();
    }
}
