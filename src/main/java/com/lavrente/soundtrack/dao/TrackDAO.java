package com.lavrente.soundtrack.dao;

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
public class TrackDAO extends AbstractDAO {
    private static final String SQL_SELECT_LAST_ORDERS = "SELECT audio_track.id, audio_track.name, genre.genre,  audio_track.artist_name, audio_track.price\n" +
            "FROM `order` JOIN audio_track ON `order`.audio_track_id=audio_track.id\n" +
            "LEFT JOIN genre ON audio_track.genre_id=genre.id\n" +
            "WHERE audio_track.deleted = 0\n" +
            "GROUP BY audio_track.name ORDER BY `order`.date DESC LIMIT 5 ";
    private static final String SQL_ADD_TRACK="INSERT INTO audio_track (`name`, `artist_name`, `genre_id`, `price`) VALUES ( ?,?,?,?)";

    public TrackDAO(ProxyConnection connection) {
        super(connection);
    }


    public List<Track> findLastOrderedTracks() throws DAOException {
        List<Track> trackList = new ArrayList<>();
        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet set = statement.executeQuery(SQL_SELECT_LAST_ORDERS);
            trackList = formTrackList(set);
        } catch (SQLException e) {
            throw new DAOException("Can't find last ordered tracks",e);
        } finally {
            closeStatement(statement);
        }
        return trackList;
    }
    public List<Track>formTrackList(ResultSet set) throws SQLException{
        List<Track> trackList;
        trackList = new ArrayList<>();
        while (set.next()) {
            int id = set.getInt("id");
            String name = set.getString("name");
            String genre = set.getString("genre");
            String artist = set.getString("artist_name");
            float price = set.getFloat("price");
            trackList.add(new Track(id, name, artist, price, genre));
        }
        return trackList;
    }
     public void addTrack(String name, String artist, float price, int genreId) throws DAOException{
         PreparedStatement statement = null;
         try {
             statement = connection.prepareStatement(SQL_ADD_TRACK);
             statement.setString(1,name);
             statement.setString(2,artist);
             statement.setInt(3,genreId);
             statement.setFloat(4,price);
             statement.executeUpdate();
         }catch (SQLException e){
             throw new DAOException("Error during track addition",e);
         }finally {
             closeStatement(statement);
         }
     }
}
