package com.vorax.client;

import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import lombok.Getter;

public class Database implements AutoCloseable {
    private static final String DRIVER = "jdbc:sqlite:";

    private Connection connection;
    private PreparedStatement loaded;

    @Getter
    private boolean closed;
    @Getter
    private boolean connected;

    public Database() {
        this.closed = false;
        this.connected = false;
    }

    public Database(String file) throws SQLException {
        this();
        this.connection = DriverManager.getConnection(DRIVER + file);
    }

    public void connect(String file) throws SQLException {
        if (closed || connected) {
            return;
        }

        connection = DriverManager.getConnection(DRIVER + file);
        connected = true;
    }

    public Statement create() throws SQLException {
        return connection.createStatement();
    }

    public void execute(String sql) throws SQLException {
        create().execute(sql);
    }

    public ResultSet query(String sql) throws SQLException {
        return create().executeQuery(sql);
    }

    public int update(String sql) throws SQLException {
        return create().executeUpdate(sql);
    }

    public void cache(String sql) throws SQLException {
        load(prepare(sql));
    }

    public PreparedStatement prepare(String sql) throws SQLException {
        return connection.prepareStatement(sql);
    }

    public void load(PreparedStatement statement) {
        loaded = statement;
    }

    public ResultSet query() throws SQLException {
        return loaded.executeQuery();
    }

    public int update() throws SQLException {
        return loaded.executeUpdate();
    }

    public Database set(int index, String val) throws SQLException {
        loaded.setString(index, val);
        return this;
    }

    public Database set(int index, int val) throws SQLException {
        loaded.setInt(index, val);
        return this;
    }

    public Database set(int index, long val) throws SQLException {
        loaded.setLong(index, val);
        return this;
    }

    public Database set(int index, Object val) throws SQLException {
        loaded.setObject(index, val);
        return this;
    }

    public Database set(int index, byte val) throws SQLException {
        loaded.setByte(index, val);
        return this;
    }

    public Database set(int index, double val) throws SQLException {
        loaded.setDouble(index, val);
        return this;
    }

    public Database set(int index, float val) throws SQLException {
        loaded.setFloat(index, val);
        return this;
    }

    public Database set(int index, short val) throws SQLException {
        loaded.setShort(index, val);
        return this;
    }

    public Database set(int index, Array val) throws SQLException {
        loaded.setArray(index, val);
        return this;
    }

    public Database set(int index, Blob val) throws SQLException {
        loaded.setBlob(index, val);
        return this;
    }

    public Database set(int index, Clob val) throws SQLException {
        loaded.setClob(index, val);
        return this;
    }

    public Database set(int index, boolean val) throws SQLException {
        loaded.setBoolean(index, val);
        return this;
    }

    @Override
    public void close() {
        if (closed) {
            return;
        }

        try {
            if (connection != null) {
                connection.close();
                connection = null;
            }
            
            connected = false;
            closed = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
