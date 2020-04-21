package top.xmdhs.uidget;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class a {
    static Logger logger = Logger.getLogger("LoggerPropreties");
    static LogManager logManager = LogManager.getLogManager();
    public static void main(String[] args) throws IOException {
        InputStream in = papapa.class.getResourceAsStream("/logging.properties");
        logManager.readConfiguration(in);
        in.close();
        logManager.addLogger(logger);
        ScheduledThreadPoolExecutor exec = new ScheduledThreadPoolExecutor(3);
        exec.scheduleAtFixedRate(new papapa("2147483646", "1", "1075816"), 1000, 500, TimeUnit.MILLISECONDS);
        exec.scheduleAtFixedRate(new papapa("2147483645", "1075817", "2151632"), 2000, 500, TimeUnit.MILLISECONDS);
        exec.scheduleAtFixedRate(new papapa("2147483644", "2151633", "3227448"), 3000, 500, TimeUnit.MILLISECONDS);

    }
}

class papapa implements Runnable {
    private final String uid;
    private final String start;
    private final String end;

    public papapa(String uid, String start, String end) {
        this.uid = uid;
        this.start = start;
        this.end = end;
    }


    public void run() {
        int i = sqlite.getUid(uid);
        if (i == -1) {
            sqlite.creatSql();
            sqlite.insertsql(Integer.parseInt(uid), "0", Integer.parseInt(start), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, "0", "0");
        }
        try {
            if (i <= Integer.parseInt(end)) {
                i = sqlite.getUid(uid);
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
                        String username = u.Variables.space.username.replace("'", "''");
                        System.out.println("用户名：" + username + "，uid：" + u.Variables.space.uid);
                        if (u.Variables.space.group != null) {
                            sqlite.insertsql(u.Variables.space.uid, username, u.Variables.space.credits, u.Variables.space.extcredits1,
                                    u.Variables.space.extcredits2, u.Variables.space.extcredits3, u.Variables.space.extcredits4,
                                    u.Variables.space.extcredits5, u.Variables.space.extcredits6, u.Variables.space.extcredits7,
                                    u.Variables.space.extcredits8, u.Variables.space.oltime, u.Variables.space.groupid,
                                    u.Variables.space.posts, u.Variables.space.threads, u.Variables.space.friends,
                                    u.Variables.space.views, u.Variables.space.adminid, u.Variables.space.emailstatus, u.Variables.space.group.grouptitle, u.Variables.space.extgroupids);
                        }
                        i = u.Variables.space.uid;
                        i++;
                    }
                    sqlite.setUid(i, uid);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

