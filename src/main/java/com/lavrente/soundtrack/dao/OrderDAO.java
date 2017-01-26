package com.lavrente.soundtrack.dao;

import com.lavrente.soundtrack.entity.Track;
import com.lavrente.soundtrack.exception.DAOException;
import com.lavrente.soundtrack.pool.ProxyConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Created by 123 on 11.01.2017.
 */
public class OrderDAO extends AbstractDAO {

    /** The date pattern. */
    private final String DATE_PATTERN="yyyy-MM-dd HH:mm:ss";

    /** The Constant SQL_ADD_ORDER. */
    private static final String SQL_ADD_ORDER = "INSERT INTO `order`(price,`date` ,user_id, audio_track_id) VALUES(?,?,?,?);";

    /** The Constant SQL_SELECT_USER_ORDERS. */
    private static final String SQL_SELECT_USER_ORDERS = "SELECT audio_track.id, audio_track.name, genre.genre , audio_track.artist_name, audio_track.price\n" +
            "FROM `order`\n" +
            "JOIN audio_track ON audio_track.id=`order`.audio_track_id\n" +
            "LEFT JOIN genre ON audio_track.genre_id=genre.id\n" +
            "WHERE `order`.user_id=? AND audio_track.deleted=0\n" +
            "ORDER BY audio_track.name";

    /** The Constant SQL_SELECT_EXISTS. */
    private static final String  SQL_SELECT_EXISTS="SELECT EXISTS(SELECT id FROM `order` WHERE user_id = ? AND audio_track_id=?)";

    /**
     * Instantiates a new order DAO.
     *
     * @param connection the connection
     */
    public OrderDAO(ProxyConnection connection) {
        super(connection);
    }

    /**
     * Adds the order.
     *
     * @param trackId the track id
     * @param price the price
     * @param userId the user id
     * @throws DAOException the DAO exception
     */
    public void addOrder(int trackId, double price, int userId) throws DAOException {
        PreparedStatement statement = null;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_PATTERN);
            LocalDateTime now=LocalDateTime.now();
            String dateTime = now.format(formatter);
            statement = connection.prepareStatement(SQL_ADD_ORDER);
            statement.setDouble(1, price);
            statement.setString(2, dateTime);
            statement.setInt(3, userId);
            statement.setInt(4, trackId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Exception during order addition",e);
        } finally {
            closeStatement(statement);
        }
    }

    /**
     * Checks if is ordered.
     *
     * @param userId the user id
     * @param trackId the track id
     * @return true, if is ordered
     * @throws DAOException the DAO exception
     */
    public boolean isOrdered(int userId, int trackId) throws DAOException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_SELECT_EXISTS);
            statement.setInt(1, userId);
            statement.setInt(2, trackId);
            ResultSet set = statement.executeQuery();
            return set.next() && set.getInt(1) == 1;
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeStatement(statement);
        }
    }

    /**
     * Find orders.
     *
     * @param userId the user id
     * @return the list
     * @throws DAOException the DAO exception
     */
    public List<Track> findOrders(int userId) throws DAOException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_SELECT_USER_ORDERS);
            statement.setInt(1,userId);
            ResultSet set = statement.executeQuery();
            TrackDAO trackDAO=new TrackDAO(connection);
            return trackDAO.formTrackList(set);
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeStatement(statement);
        }
    }
}

