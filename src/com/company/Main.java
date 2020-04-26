package com.company;

import java.sql.*;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;


public class Main {

    public static void main(String[] args) {

        // Nur die Main ist zu betrachten, Klasse MyTimerTask unvollständig

        String url = "jdbc:mysql://localhost:3306/cc jdbc?user=root";

        TimerTask myTask = new TimerTask() {

            int messageFromUser1 = 0;

            @Override
            public void run() {

                Connection connection = null;

                try {
                    connection = DriverManager.getConnection(url);

                    String sql = "Select * from chat where user_id = 1 order by id desc limit 1";
                    Statement statement = connection.createStatement();
                    ResultSet rs = statement.executeQuery(sql);

                    while (rs.next()) {
                        int id = rs.getInt("id");
                        if (messageFromUser1 != id) {
                            messageFromUser1 = id;
                            int user_id = rs.getInt("user_id");
                            String day = rs.getDate("day").toString();
                            String text = rs.getString("text");
                            String time = rs.getTime("time").toString();
                            System.out.println(user_id + "\t" + day + "\t\t" + text + "\t" + time);
                        }
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        };
        Timer timer = new Timer();
        timer.schedule(myTask, 0, 1000);

        Connection connection = null;

        while (true) {

            try {
                connection = DriverManager.getConnection(url);
                Scanner scanner = new Scanner(System.in);
                String message = scanner.nextLine();

                String sql1 = "INSERT INTO chat (id, user_id, day, text, time) " +
                        "VALUES ('0', '2', current_date(), ' " + message + "', current_time());";
                Statement statement = connection.createStatement();
                statement.executeUpdate(sql1);

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}


/*
     MyTimerTask myTask = new MyTimerTask();

        Timer timer = new Timer();
        timer.schedule(myTask, 0, 1000);
 */














