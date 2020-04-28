package top.xmdhs.uidget;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.concurrent.*;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class a {
    static Logger logger = Logger.getLogger("LoggerPropreties");
    static LogManager logManager = LogManager.getLogManager();
    static DataSource ds;

    public static void main(String[] args) throws IOException {
        InputStream in = papapa.class.getResourceAsStream("/logging.properties");
        logManager.readConfiguration(in);
        in.close();
        logManager.addLogger(logger);
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:sqlite:mcbbs.db");
        config.addDataSourceProperty("connectionTimeout", "1000");
        config.addDataSourceProperty("idleTimeout", "60000");
        config.addDataSourceProperty("maximumPoolSize", "10");
        ds = new HikariDataSource(config);
        ScheduledExecutorService exec = Executors.newScheduledThreadPool(3);
        exec.scheduleAtFixedRate(new papapa("2147483646", "1", "654100"), 1000, 100, TimeUnit.MILLISECONDS);
        exec.scheduleAtFixedRate(new papapa("2147483645", "654100", "1308200"), 2000, 100, TimeUnit.MILLISECONDS);
        exec.scheduleAtFixedRate(new papapa("2147483644", "1308200", "1962300"), 3000, 100, TimeUnit.MILLISECONDS);
        exec.scheduleAtFixedRate(new papapa("2147483643", "1962300", "2616400"), 3000, 100, TimeUnit.MILLISECONDS);
        exec.scheduleAtFixedRate(new papapa("2147483641", "2616400", "3270500"), 3000, 100, TimeUnit.MILLISECONDS);
    }
}

class papapa implements Runnable {
    private final String uid;
    private final String start;
    private final String end;
    sqlite s = new sqlite();

    public papapa(String uid, String start, String end) {
        this.uid = uid;
        this.start = start;
        this.end = end;
    }


    public void run() {
        int i = s.getUid(uid);
        if (i == -1) {
            s.creatSql();
            s.insertsql(Integer.parseInt(uid), "0", Integer.parseInt(start), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,0,"0","0","0", 0, "0", "0");
        }
        try {
            if (i <= Integer.parseInt(end)) {
                i = s.getUid(uid);
                URL url = new URL("https://www.mcbbs.net/api/mobile/index.php?module=profile&uid=" + i);
                http h = new http(url);
                String json = h.getJson();
                if (json.equals("1") || !h.testjson(json).Charset.equals("UTF-8")) {
                    a.logger.warning("网络似乎有什么问题");
                    Thread.sleep(10000);
                } else {
                    if (h.json2Class(json) == null) {
                        if (json.contains("messageval")) {
                            a.logger.warning("此用户大概有什么问题，uid：" + i);
                            i++;
                        } else {
                            a.logger.warning("网络似乎有什么问题");
                            Thread.sleep(10000);
                        }
                    } else {
                        uidapi u = h.json2Class(json);
                        System.out.println("用户名：" + u.Variables.space.username + "，uid：" + u.Variables.space.uid);
                        String[] medals = h.getmedals(json);
                        String medalsint = medals[1];
                        String Medals = medals[0];
                       if( u.Variables.space.lastvisit != null) {
                           s.insertsql(u.Variables.space.uid, u.Variables.space.username, u.Variables.space.credits, u.Variables.space.extcredits1,
                                   u.Variables.space.extcredits2, u.Variables.space.extcredits3, u.Variables.space.extcredits4,
                                   u.Variables.space.extcredits5, u.Variables.space.extcredits6, u.Variables.space.extcredits7,
                                   u.Variables.space.extcredits8, u.Variables.space.oltime, u.Variables.space.groupid,
                                   u.Variables.space.posts, u.Variables.space.threads, u.Variables.space.friends,
                                   u.Variables.space.views, u.Variables.space.adminid, u.Variables.space.emailstatus, medalsint, Medals,
                                   String.valueOf(u.Variables.space.lastvisit.contains("2020")), u.Variables.space.digestposts,
                                   u.Variables.space.group.grouptitle, u.Variables.space.extgroupids);
                       }
                        i = u.Variables.space.uid;
                        i++;
                    }
                    s.setUid(i, uid);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

