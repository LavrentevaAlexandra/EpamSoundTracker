package com.lavrente.soundtrack.dao;

import com.lavrente.soundtrack.exception.DAOException;
import com.lavrente.soundtrack.pool.ProxyConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 123 on 12.01.2017.
 */
@SuppressWarnings("Duplicates")
public class GenreDAO extends AbstractDAO {
    private static final String SQL_INSERT_GENRE = "INSERT INTO genre (`genre`) VALUES (?);";
    private static final String SQL_SELECT_GENRE_ID = "SELECT id FROM genre WHERE genre=?";
    private static final String SQL_SELECT_GENRES = "SELECT `genre` FROM genre";

    public GenreDAO(ProxyConnection connection) {
        super(connection);
    }

    private int addGenre(String genre) throws DAOException{
        PreparedStatement statement=null;
        try {
            statement = connection.prepareStatement(SQL_INSERT_GENRE);
            statement.setString(1,genre.toUpperCase());
            statement.executeUpdate();
            return findGenreId(genre);
        }catch (SQLException e){
            throw new DAOException("Exception during genre addition ",e);
        }finally {
            closeStatement(statement);
        }
    }

    public int findGenreId(String genre) throws DAOException{
        int id;
        PreparedStatement statement=null;
        try {
            statement = connection.prepareStatement(SQL_SELECT_GENRE_ID);
            statement.setString(1,genre.toUpperCase());
            ResultSet set = statement.executeQuery();
            if(set.next()){
                id = set.getInt("id");
            }else{
                id = addGenre(genre);
            }
        }catch (SQLException e){
            throw new DAOException("Exception during genre id search",e);
        }finally {
            closeStatement(statement);
        }
        return id;
    }

    public List<String> findGenres() throws DAOException{
        List<String> genreList=new ArrayList<>();
        PreparedStatement statement=null;
        try {
            statement = connection.prepareStatement(SQL_SELECT_GENRES);
            ResultSet set = statement.executeQuery();
            while (set.next()){
                genreList.add(set.getString("genre"));
            }
        }catch (SQLException e){
            throw new DAOException("Exception during genre id search",e);
        }finally {
            closeStatement(statement);
        }
        return genreList;
    }

}