package top.xmdhs.uidget;

import java.net.URL;

public class a {
//TODO 增加多线程支持
    public static void main(String[] args) {
        String uid = args[0];
        String start = args[1];
        String end = args[2];
        sqlite s = new sqlite();
        new Thread(() -> {
            int i = s.getUid(uid);
            if (i == -1) {
                s.creatSql(uid);
                s.insertsql(Integer.parseInt(uid),"0",Integer.parseInt(start),0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,"0");
            }
            try {
                while (i <= Integer.parseInt(end)) {
                    Thread.sleep(250);
                    i = s.getUid(uid);
                    URL url = new URL("https://www.mcbbs.net/api/mobile/index.php?module=profile&uid=" + i);
                    http h = new http(url);
                    if (h.json2Class(h.getJson()).Integer == 1) {
                        System.out.println("网络似乎有什么问题");
                        Thread.sleep(1000*60*3);
                        continue;
                    }
                    if (h.json2Class(h.getJson()).uidapi == null) {
                        System.out.println("此用户大概有什么问题，uid：" + i);
                    }
                    else {
                        uidapi u = h.json2Class(h.getJson()).uidapi;
                        String username = u.Variables.space.username.replace("'", "''");
                        System.out.println("用户名：" + username + "，uid：" + u.Variables.space.uid);
                        s.insertsql(u.Variables.space.uid, username, u.Variables.space.credits,u.Variables.space.extcredits1,
                                u.Variables.space.extcredits2,u.Variables.space.extcredits3,u.Variables.space.extcredits4,
                                u.Variables.space.extcredits5,u.Variables.space.extcredits6,u.Variables.space.extcredits7,
                                u.Variables.space.extcredits8,u.Variables.space.oltime,u.Variables.space.groupid,
                                u.Variables.space.posts,u.Variables.space.threads,u.Variables.space.friends,
                                u.Variables.space.views,u.Variables.space.adminid,u.Variables.space.emailstatus,u.Variables.space.extgroupids);
                        i = u.Variables.space.uid;
                    }
                    i++;
                    s.setUid(i,uid);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}
