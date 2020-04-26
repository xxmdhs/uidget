package top.xmdhs.uidget;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class b {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        try {
            try (Connection c = DriverManager.getConnection("jdbc:sqlite:mcbbs.db")) {
                try (Statement stmt = c.createStatement()) {
                    try (ResultSet rs = stmt.executeQuery("SELECT DISTINCT grouptitle FROM mcbbs;")) {
                        while (rs.next()) {
                            list.add(rs.getString("grouptitle"));
                        }
                        for (String s : list){
                            try (ResultSet rss = stmt.executeQuery("SELECT COUNT(UID) FROM mcbbs WHERE grouptitle LIKE '%"+ s +"%' OR extgroupids LIKE '%"+ s +"%';")){
                                System.out.println(s + ": " + rss.getString("COUNT(UID)"));
                            }
                        }
                    }
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
