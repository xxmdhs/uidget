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
        exec.scheduleAtFixedRate(new papapa("2147483646", "1", "1075816"), 1000, 500, TimeUnit.MILLISECONDS);
        exec.scheduleAtFixedRate(new papapa("2147483645", "1075817", "2151632"), 2000, 500, TimeUnit.MILLISECONDS);
        exec.scheduleAtFixedRate(new papapa("2147483644", "2151633", "3227448"), 3000, 500, TimeUnit.MILLISECONDS);

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
            s.insertsql(Integer.parseInt(uid), "0", Integer.parseInt(start), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,0,"0", "0", "0");
        }
        try {
            if (i <= Integer.parseInt(end)) {
                i = s.getUid(uid);
                URL url = new URL("https://www.mcbbs.net/api/mobile/index.php?module=profile&uid=" + i);
                http h = new http(url);
                if (h.getJson().equals("1") || !h.testjson(h.getJson()).Charset.equals("UTF-8")) {
                    a.logger.warning("网络似乎有什么问题");
                    Thread.sleep(10000);
                } else {
                    if (h.json2Class(h.getJson()) == null) {
                        if(h.getJson().contains("messageval")){
                        a.logger.warning("此用户大概有什么问题，uid：" + i);
                        i++;
                        }else {
                            a.logger.warning("网络似乎有什么问题");
                            Thread.sleep(10000);
                        }
                    } else {
                        uidapi u = h.json2Class(h.getJson());
                        System.out.println("用户名：" + u.Variables.space.username + "，uid：" + u.Variables.space.uid);
                        if (u.Variables.space.group != null) {
                            StringBuilder medals = new StringBuilder();
                            for (int ii = 0 ;ii < u.Variables.space.medals.length;i++){
                                medals.append(u.Variables.space.medals[ii].name);
                            }
                            s.insertsql(u.Variables.space.uid, u.Variables.space.username, u.Variables.space.credits, u.Variables.space.extcredits1,
                                    u.Variables.space.extcredits2, u.Variables.space.extcredits3, u.Variables.space.extcredits4,
                                    u.Variables.space.extcredits5, u.Variables.space.extcredits6, u.Variables.space.extcredits7,
                                    u.Variables.space.extcredits8, u.Variables.space.oltime, u.Variables.space.groupid,
                                    u.Variables.space.posts, u.Variables.space.threads, u.Variables.space.friends,
                                    u.Variables.space.views, u.Variables.space.adminid, u.Variables.space.emailstatus, u.Variables.space.medals.length,medals.toString(), u.Variables.space.group.grouptitle, u.Variables.space.extgroupids);
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

