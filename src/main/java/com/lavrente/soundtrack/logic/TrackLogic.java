package com.lavrente.soundtrack.logic;

import com.lavrente.soundtrack.dao.TrackDAO;
import com.lavrente.soundtrack.entity.Comment;
import com.lavrente.soundtrack.entity.Track;
import com.lavrente.soundtrack.exception.DAOException;
import com.lavrente.soundtrack.exception.LogicException;
import com.lavrente.soundtrack.pool.ConnectionPool;
import com.lavrente.soundtrack.pool.ProxyConnection;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 123 on 11.01.2017.
 */
public class TrackLogic {
    public List<Track> lastTracks() throws LogicException {
        ProxyConnection connection = ConnectionPool.getInstance().getConnection();
        TrackDAO trackDAO = new TrackDAO(connection);
        try {
            return trackDAO.findLastOrderedTracks();
        } catch (DAOException e) {
            throw new LogicException("Can't find last ordered tracks", e);
        } finally {
            trackDAO.closeConnection(connection);
        }
    }

    public ArrayList<Comment> findTrackComments(int trackId) throws LogicException {
        ProxyConnection connection = ConnectionPool.getInstance().getConnection();
        TrackDAO trackDAO = new TrackDAO(connection);
        try {
            return (ArrayList<Comment>) trackDAO.findTrackComments(trackId);
        } catch (DAOException e) {
            throw new LogicException("Can't find all comments by track id", e);
        } finally {
            trackDAO.closeConnection(connection);
        }
    }

    public void addTrack(String name, String artist, float price, String genre)throws LogicException{
        ProxyConnection connection = ConnectionPool.getInstance().getConnection();
        TrackDAO trackDAO = new TrackDAO(connection);
        GenreLogic genreLogic=new GenreLogic();
        try {
            int genreId=genreLogic.findGenreId(genre);
            trackDAO.addTrack(name, artist, price, genreId);
        } catch (DAOException e) {
            throw new LogicException("Error during track addition", e);
        } finally {
            trackDAO.closeConnection(connection);
        }
    }
}
