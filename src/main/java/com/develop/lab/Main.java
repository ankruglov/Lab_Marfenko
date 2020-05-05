package com.develop.lab;



import redis.clients.jedis.Jedis;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Scanner;

public class Main {
    private static String ch;
    private static int userId;

    public static void main(String[] args) throws ClassNotFoundException {
        ConnectionMaker maker=new ConnectionMaker();
        Jedis jedis=new Jedis("redis",6379);
        final String SELECT="SELECT * from users where userId";

        System.out.println("Write Y if you want to continue ");
        Scanner in = new Scanner(System.in);
        String command = in.nextLine();
        while (command.toLowerCase().equals("y")) {
            try {
                HashMap<String, String> table = new HashMap<String, String>();
                System.out.println("Write symbol (>,<,=)");
                ch=in.nextLine();
                System.out.println("Write the value of userId");
                userId=Integer.parseInt(in.nextLine());
                if (!jedis.hgetAll(ch+userId).isEmpty()){
                    System.out.println("This was got from cache of Redis: "+jedis.hgetAll(ch+userId));
                }
                else {
                    Class.forName("com.mysql.jdbc.Driver");
                    Statement statement = maker.getConnection().createStatement();
                    ResultSet resultSet = statement.executeQuery(SELECT + ch + userId);
                    while (resultSet.next()) {
                        table.put(resultSet.getString(1), resultSet.getString(2));
                        jedis.hset(ch + userId, resultSet.getString(1), resultSet.getString(2));
                        jedis.expire(ch + userId, 20);
                    }
                    System.out.println("This was got from mySQL: " + table);
                    System.out.println("Write Y if you want to continue ");
                    command = in.nextLine();
                }
            }
             catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
