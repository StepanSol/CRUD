package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException, IOException {

        Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost/postgres",
                "postgres", "postgres");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        DBManager manager = new DBManager(connection, reader);
        manager.createTable();


        System.out.println("Напишите в консоль номер действия: \n" +
                "1. Вывести список товаров \n" +
                "2. Добавление товара в базу данных \n" +
                "3. Вывод информации о товаре по id \n" +
                "4. Удаление товара по id \n" +
                "Для завершения работы введите 0");


        while(true){
            try {
                byte numberOfAction = Byte.parseByte(reader.readLine());
                if (numberOfAction == 1){
                    manager.displayTable();
                } else if (numberOfAction == 2) {
                    manager.addProduct();
                } else if (numberOfAction == 3) {
                    manager.displayProductByID();
                } else if (numberOfAction == 4) {
                    manager.removeProductByID();
                } else if (numberOfAction == 0) {
                    connection.close();
                    reader.close();
                    break;
                }else {
                    System.out.println("Ведите число от 0 до 4");
                }
            }catch (NumberFormatException ex){
                System.out.println("Введены неверные данные");
            }
        }
    }
}