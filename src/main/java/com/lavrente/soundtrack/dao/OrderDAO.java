package com.lavrente.soundtrack.dao;

import com.lavrente.soundtrack.command.AbstractCommand;
import com.lavrente.soundtrack.entity.Track;
import com.lavrente.soundtrack.exception.DAOException;
import com.lavrente.soundtrack.pool.ProxyConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 123 on 11.01.2017.
 */
public class OrderDAO extends AbstractDAO{
    private static final String SQL_SELECT_LAST_ORDERS = "SELECT audio_track.id, audio_track.name, genre.genre,  audio_track.artist_name, audio_track.price\n" +
            "FROM order JOIN audio_track ON order.audio_track_id=audio_track.id\n" +
            "LEFT JOIN genre ON audio_track.genre_id=genre.id\n" +
            "WHERE track.deleted = 0\n" +
            "GROUP BY audio_track.name ORDER BY order.date DESC LIMIT 5 ";

    public OrderDAO(ProxyConnection connection) {
        super(connection);
    }

}
