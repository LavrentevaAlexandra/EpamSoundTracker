package com.lavrente.soundtrack.dao;

import com.lavrente.soundtrack.entity.Comment;
import com.lavrente.soundtrack.entity.Track;
import com.lavrente.soundtrack.exception.DAOException;
import com.lavrente.soundtrack.pool.ProxyConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 123 on 03.01.2017.
 */
@SuppressWarnings("Duplicates")
public class TrackDAO extends AbstractDAO {
    private static final String SQL_ADD_TRACK = "INSERT INTO audio_track (`name`, `artist_name`, `genre_id`, `price`, `path`) VALUES ( ?,?,?,?,?)";
    private static final String SQL_DELETE_TRACK = "UPDATE audio_track SET audio_track.deleted=1 WHERE id=?";
    private static final String SQL_RECOVER_TRACK = "UPDATE tracks SET visible=1 WHERE id=?";
    private static final String SQL_SELECT_LAST_ORDERS = "SELECT audio_track.id, audio_track.name, genre.genre,  audio_track.artist_name, audio_track.price\n" +
            "FROM `order` JOIN audio_track ON `order`.audio_track_id=audio_track.id\n" +
            "LEFT JOIN genre ON audio_track.genre_id=genre.id\n" +
            "WHERE audio_track.deleted = 0\n" +
            "GROUP BY audio_track.name ORDER BY `order`.date DESC LIMIT 5 ";
    private static final String SQL_SELECT_TRACK_BY_ID = "SELECT audio_track.id, audio_track.name, genre.genre, audio_track.artist_name, audio_track.price\n" +
            "FROM audio_track\n" +
            "LEFT JOIN genre ON audio_track.genre_id=genre.id\n" +
            "WHERE audio_track.id=?";
    private static final String SQL_SELECT_TRACK_COMMENTS = "SELECT user.login, comment.date, comment.text\n" +
            "FROM comment JOIN user ON user.id = comment.user_id WHERE comment.audio_track_id=? ORDER BY comment.date DESC;";
    private static final String SQL_SELECT_DELETED_TRACKS= "SELECT audio_track.id, audio_track.name, genre.genre,  audio_track.artist_name, audio_track.price\n" +
            "FROM `audio_track` JOIN genre ON audio_track.genre_id=genre.id\n" +
            "WHERE audio_track.deleted = 1 ORDER BY audio_track.name";

    public TrackDAO(ProxyConnection connection) {
        super(connection);
    }

    public void addTrack(String name, String artist, double price, int genreId, String path) throws DAOException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_ADD_TRACK);
            statement.setString(1, name);
            statement.setString(2, artist);
            statement.setInt(3, genreId);
            statement.setDouble(4, price);
            statement.setString(5, path);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Exception during track addition", e);
        } finally {
            closeStatement(statement);
        }
    }

    public void deleteTrackById(int id) throws DAOException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_DELETE_TRACK);
            statement.setString(1, Integer.toString(id));
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Exception during track removal", e);
        } finally {
            closeStatement(statement);
        }
    }

    public Track findTrackById(int id) throws DAOException {
        Track track;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_SELECT_TRACK_BY_ID);
            statement.setInt(1, id);
            ResultSet set = statement.executeQuery();
            track = formTrackList(set).get(0);
        } catch (SQLException e) {
            throw new DAOException("Exception during track by id search", e);
        } finally {
            closeStatement(statement);
        }
        return track;
    }

    public List<Comment> findTrackComments(int trackId) throws DAOException {
        List<Comment> comments = new ArrayList<>();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_SELECT_TRACK_COMMENTS);
            statement.setInt(1, trackId);
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                String login = set.getString("login");
                String dateTime = set.getString("date");
                String text = set.getString("text");
                comments.add(new Comment(login, dateTime, text));
            }
        } catch (SQLException e) {
            throw new DAOException("Exception during track comments search ", e);
        } finally {
            closeStatement(statement);
        }
        return comments;
    }

    public List<Track> findLastOrderedTracks() throws DAOException {
        List<Track> trackList;
        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet set = statement.executeQuery(SQL_SELECT_LAST_ORDERS);
            trackList = formTrackList(set);
        } catch (SQLException e) {
            throw new DAOException("Exception during last ordered tracks search", e);
        } finally {
            closeStatement(statement);
        }
        return trackList;
    }

    private List<Track> formTrackList(ResultSet set) throws DAOException {
        List<Track> trackList = new ArrayList<>();
        try {
            while (set.next()) {
                int id = set.getInt("id");
                String name = set.getString("name");
                String genre = set.getString("genre");
                String artist = set.getString("artist_name");
                double price = set.getDouble("price");
                trackList.add(new Track(id, name, artist, price, genre));
            }
        } catch (SQLException e) {
            throw new DAOException("Exception during track list formation ", e);
        }
        return trackList;
    }

    public List<Track> findDeletedTracks() throws DAOException {
        List<Track> trackList;
        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet set = statement.executeQuery(SQL_SELECT_DELETED_TRACKS);
            trackList = formTrackList(set);
        } catch (SQLException e) {
            throw new DAOException("Exception during deleted tracks search", e);
        } finally {
            closeStatement(statement);
        }
        return trackList;
    }

    public void recoverTrackById(int id) throws DAOException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_RECOVER_TRACK);
            statement.setString(1, Integer.toString(id));
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Exception during track recover",e);
        } finally {
            closeStatement(statement);
        }
    }
}
