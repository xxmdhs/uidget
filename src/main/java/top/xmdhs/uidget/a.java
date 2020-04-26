package top.xmdhs.uidget;

import java.sql.*;

public class a {
    public static void main(String[] args){
        StringBuilder s = new StringBuilder();
        String type = args[0];
        try(Connection c = DriverManager.getConnection("jdbc:sqlite:mcbbs.db")) {
            try(Statement stmt = c.createStatement()) {
                try (ResultSet rs = stmt.executeQuery( "SELECT * FROM mcbbs ORDER BY credits DESC LIMIT 107;")){
                    s.append("| 排名 | uid | 用户名 | 积分 | 人气 | 金粒 | 金锭 | 绿宝石 | 下界之星 | 贡献 | 爱心 | 钻石 | 在线时间 | 回帖数 | 主题数 | 好友数 | 空间查看次数 | 用户组 | 扩展用户组 |\n" +
                            "| - | - | - | - | - | - | - | - | - | - | - | - | - | - | - | - | - | - | - |\n");
                    int i = 0;
                    while (rs.next()){
                        i++;
                        String uid = rs.getString("UID");
                        String id = rs.getString("NAME");
                        String credits = rs.getString("credits");
                        String extcredits1 = rs.getString("extcredits1");
                        String extcredits2 = rs.getString("extcredits2");
                        String extcredits3 = rs.getString("extcredits3");
                        String extcredits4 = rs.getString("extcredits4");
                        String extcredits5 = rs.getString("extcredits5");
                        String extcredits6 = rs.getString("extcredits6");
                        String extcredits7 = rs.getString("extcredits7");
                        String extcredits8 = rs.getString("extcredits8");
                        String oltime = rs.getString("oltime");
                        String posts = rs.getString("posts");
                        String threads = rs.getString("threads");
                        String friends = rs.getString("friends");
                        String views = rs.getString("views");
                        String grouptitle = rs.getString("grouptitle").replace("<font color=\"#0099FF\">Lv.Inf 艺术家</font>","艺术家").replace("<font color=\"#946CE6\">电鳗</font>","电鳗").replace("<font color=\"#FF5555\">管理员</font>","管理员");
                        String extgroupids = rs.getString("extgroupids").replace("<font color=\"#0099FF\">Lv.Inf 艺术家</font>","艺术家").replace("<font color=\"#946CE6\">电鳗</font>","电鳗").replace("<font color=\"#FF5555\">管理员</font>","管理员");
                        if(Integer.parseInt(uid) >= 2147483641){
                            i--;
                        }else {
                            s.append("| ").append(i).append(" | ").append(uid).append(" | ").append(id).append(" | ").append(credits).append(" | ").append(extcredits1).append(" | ").append(extcredits2)
                                    .append(" | ").append(extcredits3).append(" | ").append(extcredits4).append(" | ").append(extcredits5).append(" | ")
                                    .append(extcredits6).append(" | ").append(extcredits7).append(" | ").append(extcredits8).append(" | ")
                                    .append(oltime).append(" | ").append(posts).append(" | ").append(threads).append(" | ").append(friends)
                                    .append(" | ").append(views).append(" | ").append(grouptitle).append(" | ").append(extgroupids).append(" |\n");
                        }

                    }
                    System.out.println(s);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
