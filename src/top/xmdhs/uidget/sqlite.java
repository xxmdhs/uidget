package top.xmdhs.uidget;

import org.sqlite.SQLiteException;

import java.sql.*;

public class sqlite {
    /**
     * 创建数据库
     */
    public void creatSql() {
        try {
            String sql;
            Class.forName("org.sqlite.JDBC");
            Connection c = DriverManager.getConnection("jdbc:sqlite:mcbbs.db");
            c.setAutoCommit(false);
            Statement stmt = c.createStatement();
            sql = "CREATE TABLE MCBBS " +
                    "(UID INT PRIMARY KEY     NOT NULL," +
                    " NAME           TEXT    NOT NULL, " +
                    " credits            INT     NOT NULL )";
            stmt.executeUpdate(sql);
            sql = "INSERT INTO MCBBS VALUES (23333333,'目前进度',0);";
            stmt.executeUpdate(sql);
            sql = "INSERT INTO MCBBS VALUES (23333334,'无效用户',0);";
            stmt.executeUpdate(sql);
            stmt.close();
            c.commit();
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 用于向数据库写入数据
     * @param uid uid
     * @param name 名字（单引号大概要处理成 '' 就可以避免错误了吧
     * @param credits 积分
     */
    public void insertsql(int uid, String name, int credits) {
        //INSERT INTO TABLE_NAME VALUES (value1,value2,value3,...valueN);
        StringBuilder sql = new StringBuilder("INSERT INTO MCBBS VALUES(");
        sql.append(uid).append(",");
        sql.append("\"").append(name).append("\"").append(",");
        sql.append(credits).append(");");
        try {
            Connection c = DriverManager.getConnection("jdbc:sqlite:mcbbs.db");
            Statement stmt = c.createStatement();
            c.setAutoCommit(false);
            stmt.executeUpdate(sql.toString());
            stmt.close();
            c.commit();
            c.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * 用 sql 储存目前爬取进入，方便崩溃后重新爬取，同时存储别的什么东西
     * @param intData 储存的数据
     */
    public void setUid(int intData){
        try {
            Connection c = DriverManager.getConnection("jdbc:sqlite:mcbbs.db");
            Statement stmt = c.createStatement();
            c.setAutoCommit(false);
            String sql = "UPDATE MCBBS set credits = "+ intData +" where UID=23333333;";
            stmt.executeUpdate(sql);
            stmt.close();
            c.commit();
            c.close();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取目前的进度
     * @return 返回爬取到的 uid
     */
    public int getUid(){
        try {
            Connection c = DriverManager.getConnection("jdbc:sqlite:mcbbs.db");
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM MCBBS WHERE UID=23333333;" );
            int i = rs.getInt("credits");
            rs.close();
            stmt.close();
            c.close();
            return i;
        }catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }
}