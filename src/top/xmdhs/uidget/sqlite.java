package top.xmdhs.uidget;


import java.sql.*;

public class sqlite {
    /**
     * 创建数据库
     */
    static Connection c;

    static {
        try {
            c = DriverManager.getConnection("jdbc:sqlite:mcbbs.db");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void creatSql() {
        try {
            String sql;
            c.setAutoCommit(false);
            try (Statement stmt = c.createStatement()) {
                sql = "CREATE TABLE MCBBS " +
                        "(UID INT PRIMARY KEY     NOT NULL," +
                        " NAME           TEXT    NOT NULL, " +
                        " credits            INT     NOT NULL," +
                        "extcredits1 INT," +
                        "extcredits2 INT," +
                        "extcredits3 INT," +
                        "extcredits4 INT," +
                        "extcredits5 INT," +
                        "extcredits6 INT," +
                        "extcredits7 INT," +
                        "extcredits8 INT," +
                        "oltime INT," +
                        "groupid INT," +
                        "posts INT," +
                        "threads INT," +
                        "friends INT," +
                        "views INT," +
                        "adminid INT," +
                        "emailstatus INT," +
                        "grouptitle TEXT," +
                        "extgroupids TEXT)";
                stmt.executeUpdate(sql);
            } finally {
                c.commit();
            }
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
    public static void insertsql(int uid, String name, int credits,int extcredits1,int extcredits2,
                          int extcredits3,int extcredits4,int extcredits5,int extcredits6,int extcredits7,int extcredits8,
                          int oltime,int groupid,int posts,int threads,int friends,int views,int adminid,
                          int emailstatus,String grouptitle,String extgroupids) {
        //INSERT INTO TABLE_NAME VALUES (value1,value2,value3,...valueN);
        StringBuilder sql = new StringBuilder("INSERT INTO MCBBS VALUES(");
        sql.append(uid).append(",");
        sql.append("'").append(name).append("'").append(",");
        sql.append(credits).append(",");
        sql.append(extcredits1).append(",");
        sql.append(extcredits2).append(",");
        sql.append(extcredits3).append(",");
        sql.append(extcredits4).append(",");
        sql.append(extcredits5).append(",");
        sql.append(extcredits6).append(",");
        sql.append(extcredits7).append(",");
        sql.append(extcredits8).append(",");
        sql.append(oltime).append(",");
        sql.append(groupid).append(",");
        sql.append(posts).append(",");
        sql.append(threads).append(",");
        sql.append(friends).append(",");
        sql.append(views).append(",");
        sql.append(adminid).append(",");
        sql.append(emailstatus).append(",");
        sql.append("'").append(grouptitle).append("'").append(",");
        sql.append("'").append(extgroupids).append("'").append(");");
        try {
            try (Statement stmt = c.createStatement()) {
                c.setAutoCommit(false);
                stmt.executeUpdate(sql.toString());
            } finally {
                c.commit();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * 用 sql 储存目前爬取进入，方便崩溃后重新爬取，同时存储别的什么东西
     * @param intData 储存的数据
     */
    public static void setUid(int intData,String uid){
        try {
            try (Statement stmt = c.createStatement()) {
                c.setAutoCommit(false);
                String sql = "UPDATE MCBBS set credits = " + intData + " where UID=" + uid + ";";
                stmt.executeUpdate(sql);
            } finally {
                c.commit();
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取目前的进度
     * @return 返回爬取到的 uid
     */
    public static int getUid(String uid){
        try {
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM MCBBS WHERE UID="+uid+";" );
            int i = rs.getInt("credits");
            rs.close();
            stmt.close();
            return i;
        }catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }
}