package top.xmdhs.uidget;


import java.sql.*;

public class sqlite {
    /**
     * 创建数据库
     */
    public void creatSql() {
        try(Connection c = a.ds.getConnection()) {
            try(Statement stmt = c.createStatement()) {
                c.setAutoCommit(false);
                String sql = "CREATE TABLE MCBBS " +
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
                        "medalsint INT," +
                        "medals TEXT," +
                        "isalive TEXT," +
                        "digestposts INT," +
                        "grouptitle TEXT," +
                        "extgroupids TEXT)";
                stmt.executeUpdate(sql);
                c.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 用于向数据库写入数据
     * @param uid uid
     * @param name 名字
     * @param credits 积分
     */
    public void insertsql(int uid, String name, int credits,int extcredits1,int extcredits2,
                          int extcredits3,int extcredits4,int extcredits5,int extcredits6,int extcredits7,int extcredits8,
                          int oltime,int groupid,int posts,int threads,int friends,int views,int adminid,
                          int emailstatus,String medalsint,String medals,String isalive,int digestposts,String grouptitle,String extgroupids) {
        //INSERT INTO TABLE_NAME VALUES (value1,value2,value3,...valueN);
        try(Connection c = a.ds.getConnection()) {
            try (PreparedStatement ps = c.prepareStatement("INSERT INTO MCBBS VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)")){
                c.setAutoCommit(false);
                ps.setObject(1,uid);
                ps.setObject(2,name);
                ps.setObject(3,credits);
                ps.setObject(4,extcredits1);
                ps.setObject(5,extcredits2);
                ps.setObject(6,extcredits3);
                ps.setObject(7,extcredits4);
                ps.setObject(8,extcredits5);
                ps.setObject(9,extcredits6);
                ps.setObject(10,extcredits7);
                ps.setObject(11,extcredits8);
                ps.setObject(12,oltime);
                ps.setObject(13,groupid);
                ps.setObject(14,posts);
                ps.setObject(15,threads);
                ps.setObject(16,friends);
                ps.setObject(17,views);
                ps.setObject(18,adminid);
                ps.setObject(19,emailstatus);
                ps.setObject(20,medalsint);
                ps.setObject(21,medals);
                ps.setObject(22,isalive);
                ps.setObject(23,digestposts);
                ps.setObject(24,grouptitle);
                ps.setObject(25,extgroupids);
                ps.executeUpdate();
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
    public void setUid(int intData,String uid){
        try (Connection c = a.ds.getConnection()){
            try (Statement stmt = c.createStatement()) {
                c.setAutoCommit(false);
                String sql = "UPDATE MCBBS set credits = " + intData + " where UID=" + uid + ";";
                stmt.executeUpdate(sql);
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
    public int getUid(String uid){
        try(Connection c = a.ds.getConnection()) {
           try (Statement stmt = c.createStatement()){
               try(ResultSet rs = stmt.executeQuery( "SELECT * FROM MCBBS WHERE UID="+uid+";" )) {
                   return rs.getInt("credits");
               }
           }
        }catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }
}


