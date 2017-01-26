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

    /** The Constant SQL_ADD_TRACK. */
    private static final String SQL_ADD_TRACK = "INSERT INTO audio_track (`name`, `artist_name`, `genre_id`, `price`, `path`) VALUES ( ?,?,?,?,?)";

    /** The Constant SQL_CHANGE_ARTIST. */
    private static final String SQL_CHANGE_ARTIST = "UPDATE audio_track SET artist_name=? WHERE id=?";

    /** The Constant SQL_CHANGE_GENRE. */
    private static final String SQL_CHANGE_GENRE = "UPDATE audio_track SET genre_id=? WHERE id=?";

    /** The Constant SQL_CHANGE_NAME. */
    private static final String SQL_CHANGE_NAME = "UPDATE audio_track SET name=? WHERE id=?";

    /** The Constant SQL_CHANGE_PRICE. */
    private static final String SQL_CHANGE_PRICE = "UPDATE audio_track SET price=? WHERE id=?";

    /** The Constant SQL_DELETE_TRACK. */
    private static final String SQL_DELETE_TRACK = "UPDATE audio_track SET audio_track.deleted=1 WHERE id=?";

    /** The Constant SQL_RECOVER_TRACK. */
    private static final String SQL_RECOVER_TRACK = "UPDATE audio_track SET audio_track.deleted=0 WHERE id=?";

    /** The Constant SQL_SELECT_ALL_TRACKS. */
    private static final String SQL_SELECT_ALL_TRACKS = "SELECT audio_track.id, audio_track.name, genre.genre,  audio_track.artist_name, audio_track.price\n" +
            "FROM audio_track\n" +
            "LEFT JOIN genre ON audio_track.genre_id=genre.id\n" +
            "WHERE audio_track.deleted =0 ORDER BY audio_track.name";

    /** The Constant SQL_SELECT_LAST_ORDERS. */
    private static final String SQL_SELECT_LAST_ORDERS = "SELECT audio_track.id, audio_track.name, genre.genre,  audio_track.artist_name, audio_track.price\n" +
            "FROM `order` JOIN audio_track ON `order`.audio_track_id=audio_track.id\n" +
            "LEFT JOIN genre ON audio_track.genre_id=genre.id\n" +
            "WHERE audio_track.deleted = 0\n" +
            "GROUP BY audio_track.name ORDER BY `order`.date DESC LIMIT 5 ";

    /** The Constant SQL_SELECT_TRACKS_BY_GENRE. */
    private static final String SQL_SELECT_TRACKS_BY_GENRE = "SELECT audio_track.id, audio_track.name, genre.genre, audio_track.artist_name, audio_track.price\n" +
            "FROM audio_track\n" +
            "LEFT JOIN genre ON audio_track.genre_id=genre.id\n" +
            "WHERE genre=? AND audio_track.deleted = 0 ORDER BY audio_track.name";

    /** The Constant SQL_SELECT_TRACK_BY_ID. */
    private static final String SQL_SELECT_TRACK_BY_ID = "SELECT audio_track.id, audio_track.name, genre.genre, audio_track.artist_name, audio_track.price\n" +
            "FROM audio_track\n" +
            "LEFT JOIN genre ON audio_track.genre_id=genre.id\n" +
            "WHERE audio_track.id=?";

    /** The Constant SQL_SELECT_TRACK_PATH. */
    private static final String SQL_SELECT_TRACK_PATH= "SELECT audio_track.path FROM audio_track WHERE id=?";

    /** The Constant SQL_SELECT_TRACK_COMMENTS. */
    private static final String SQL_SELECT_TRACK_COMMENTS = "SELECT user.login, comment.date, comment.text\n" +
            "FROM comment JOIN user ON user.id = comment.user_id WHERE comment.audio_track_id=? ORDER BY comment.date DESC;";

    /** The Constant SQL_SELECT_DELETED_TRACKS. */
    private static final String SQL_SELECT_DELETED_TRACKS= "SELECT audio_track.id, audio_track.name, genre.genre,  audio_track.artist_name, audio_track.price\n" +
            "FROM `audio_track` JOIN genre ON audio_track.genre_id=genre.id\n" +
            "WHERE audio_track.deleted = 1 ORDER BY audio_track.name";

    /**
     * Instantiates a new track DAO.
     *
     * @param connection the connection
     */
    public TrackDAO(ProxyConnection connection) {
        super(connection);
    }

    /**
     * Adds the track.
     *
     * @param name the name
     * @param artist the artist
     * @param price the price
     * @param genreId the genre id
     * @param path the path
     * @throws DAOException the DAO exception
     */
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

    /**
     * Change artist.
     *
     * @param trackId the track id
     * @param newArtist the new artist
     * @throws DAOException the DAO exception
     */
    public void changeArtist(int trackId, String newArtist) throws DAOException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_CHANGE_ARTIST);
            statement.setString(1, newArtist);
            statement.setInt(2, trackId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Exception during changing track artist", e);
        } finally {
            closeStatement(statement);
        }
    }

    /**
     * Change genre.
     *
     * @param trackId the track id
     * @param newGenreId the new genre id
     * @throws DAOException the DAO exception
     */
    public void changeGenre(int trackId, int newGenreId) throws DAOException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_CHANGE_GENRE);
            statement.setInt(1, newGenreId);
            statement.setInt(2, trackId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Exception during changing track genre", e);
        } finally {
            closeStatement(statement);
        }
    }

    /**
     * Change name.
     *
     * @param trackId the track id
     * @param newName the new name
     * @throws DAOException the DAO exception
     */
    public void changeName(int trackId, String newName) throws DAOException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_CHANGE_NAME);
            statement.setString(1, newName);
            statement.setInt(2, trackId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Exception during changing track name", e);
        } finally {
            closeStatement(statement);
        }
    }

    /**
     * Change price.
     *
     * @param trackId the track id
     * @param newPrice the new price
     * @throws DAOException the DAO exception
     */
    public void changePrice(int trackId, double newPrice) throws DAOException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_CHANGE_PRICE);
            statement.setDouble(1, newPrice);
            statement.setInt(2, trackId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Exception during changing track price", e);
        } finally {
            closeStatement(statement);
        }
    }

    /**
     * Delete track by id.
     *
     * @param id the id
     * @throws DAOException the DAO exception
     */
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

    /**
     * Find all.
     *
     * @return the list
     * @throws DAOException the DAO exception
     */
    public List<Track> findAll() throws DAOException {
        List<Track> trackList;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_SELECT_ALL_TRACKS);
            ResultSet set = statement.executeQuery();
            trackList = formTrackList(set);
        } catch (SQLException e) {
            throw new DAOException("Exception during all tracks search",e);
        } finally {
            closeStatement(statement);
        }
        return trackList;
    }

    /**
     * Find deleted tracks.
     *
     * @return the list
     * @throws DAOException the DAO exception
     */
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

    /**
     * Find last ordered tracks.
     *
     * @return the list
     * @throws DAOException the DAO exception
     */
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

    /**
     * Find tracks by genre.
     *
     * @param genre the genre
     * @return the list
     * @throws DAOException the DAO exception
     */
    public List<Track> findTracksByGenre( String genre) throws DAOException {
        List<Track> trackList;
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_SELECT_TRACKS_BY_GENRE);
            statement.setString(1,genre.toUpperCase());
            ResultSet set = statement.executeQuery();
            trackList = formTrackList(set);
        } catch (SQLException e) {
            throw new DAOException("Exception during tracks by genre search",e);
        } finally {
            closeStatement(statement);
        }
        return trackList;
    }

    /**
     * Find track by id.
     *
     * @param id the id
     * @return the track
     * @throws DAOException the DAO exception
     */
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

    /**
     * Find track comments.
     *
     * @param trackId the track id
     * @return the list
     * @throws DAOException the DAO exception
     */
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
                dateTime=dateTime.substring(0,dateTime.length()-2);
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

    /**
     * Find track path.
     *
     * @param trackId the track id
     * @return the string
     * @throws DAOException the DAO exception
     */
    public String findTrackPath(int trackId) throws DAOException {
        String path = "";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_SELECT_TRACK_PATH);
            statement.setString(1, Integer.toString(trackId));
            ResultSet set = statement.executeQuery();
            if (set.next()) {
                path = set.getString(1);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            closeStatement(statement);
        }
        return path;
    }

    /**
     * Recover track by id.
     *
     * @param id the id
     * @throws DAOException the DAO exception
     */
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

    /**
     * Form track list.
     *
     * @param set the set
     * @return the list
     * @throws DAOException the DAO exception
     */
    List<Track> formTrackList(ResultSet set) throws DAOException {
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
}
