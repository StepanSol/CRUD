package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;

public class DBManager {
    private Connection connection;
    private BufferedReader reader;


    public DBManager(Connection connection, BufferedReader reader) throws SQLException {
        this.connection = connection;
        this.reader = reader;
    }

    public void createTable() throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeUpdate("CREATE TABLE IF NOT EXISTS product(" +
                "id serial primary key, " +
                "name VARCHAR(40) not null, " +
                "price NUMERIC(10,2))");
    }

    public void displayTable() throws SQLException {
        Statement statement = connection.createStatement();
        displayResultSet(statement.executeQuery("SELECT * FROM product"));
    }

    public void addProduct() throws IOException, SQLException {
        System.out.println("Введите название товара");
        String name = reader.readLine();
        System.out.println("Введите стоимость");
        BigDecimal price = BigDecimal.valueOf(Double.parseDouble(reader.readLine()));
        PreparedStatement statement = connection.prepareStatement("INSERT INTO product (name, price)" +
                "VALUES (?, ?)");
        statement.setString(1, name);
        statement.setBigDecimal(2, price);
        System.out.println("Добавлено продуктов: " + statement.executeUpdate());
    }

    public void displayProductByID() throws IOException, SQLException {
        System.out.println("Введите ID товара для отображения");
        int id = Integer.parseInt(reader.readLine());
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM product WHERE id = ?");
        statement.setInt(1, id);
        displayResultSet(statement.executeQuery());
    }

    public void removeProductByID() throws IOException, SQLException {
        System.out.println("Введите ID товара для удаления");
        int id = Integer.parseInt(reader.readLine());
        PreparedStatement statement = connection.prepareStatement("DELETE FROM product WHERE id = ?");
        statement.setInt(1, id);
        System.out.println("Удалено продуктов: " + statement.executeUpdate());
    }

    private void displayResultSet(ResultSet resultSet) throws SQLException {
        if (!resultSet.isBeforeFirst() ) {
            System.out.println("No data");
        }
        while (resultSet.next()){
            System.out.println("ID: " +resultSet.getInt(1) + ", "
                                + "название товара: " + resultSet.getString(2) + ", "
                                + "цена: " + resultSet.getDouble(3));
        }
    }
}
