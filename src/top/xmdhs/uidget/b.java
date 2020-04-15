package top.xmdhs.uidget;

import java.io.InputStream;
import java.net.URL;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class b {
    static Logger logger = Logger.getLogger("LoggerPropreties");
    static LogManager logManager = LogManager.getLogManager();
    public static void main(String[] args) {
        sqlite s = new sqlite();
        int i = s.getUid("2147483643");
        if (i == -1) {
            s.creatSql();
            s.insertsql(2147483643, "0", 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, "0", "0");
        }

        while (i <= 3227448){
            if(s.getUid(Integer.toString(i)) == -1){
                try {
                    Thread.sleep(500);
                    InputStream in = papapa.class.getResourceAsStream("/logging.properties");
                    logManager.readConfiguration(in);
                    logManager.addLogger(logger);
                        URL url = new URL("https://www.mcbbs.net/api/mobile/index.php?module=profile&uid=" + i);
                        http h = new http(url);
                        if (h.getJson().equals("1") || !h.testjson(h.getJson()).Charset.equals("UTF-8")) {
                            logger.warning("网络似乎有什么问题");
                            Thread.sleep(10000);
                        } else {
                            if (h.json2Class(h.getJson()) == null) {
                                if(h.getJson().contains("messageval")){
                                    System.out.println("此用户大概有什么问题，uid：" + i);
                                }else {
                                    logger.warning("网络似乎有什么问题");
                                    Thread.sleep(10000);
                                }
                            } else {
                                uidapi u = h.json2Class(h.getJson());
                                String username = u.Variables.space.username.replace("'", "''");
                                System.out.println("用户名：" + username + "，uid：" + u.Variables.space.uid);
                                if (u.Variables.space.group != null) {
                                    s.insertsql(u.Variables.space.uid, username, u.Variables.space.credits, u.Variables.space.extcredits1,
                                            u.Variables.space.extcredits2, u.Variables.space.extcredits3, u.Variables.space.extcredits4,
                                            u.Variables.space.extcredits5, u.Variables.space.extcredits6, u.Variables.space.extcredits7,
                                            u.Variables.space.extcredits8, u.Variables.space.oltime, u.Variables.space.groupid,
                                            u.Variables.space.posts, u.Variables.space.threads, u.Variables.space.friends,
                                            u.Variables.space.views, u.Variables.space.adminid, u.Variables.space.emailstatus, u.Variables.space.group.grouptitle, u.Variables.space.extgroupids);
                                }
                                i = u.Variables.space.uid;
                            }
                            if (!h.getJson().equals("1")) {
                                i++;
                                s.setUid(i,"2147483643");
                            }
                        }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else {
                i++;
                s.setUid(i,"2147483643");
            }
        }
       // System.out.println(s.getUid("1222222"));
    }
}
