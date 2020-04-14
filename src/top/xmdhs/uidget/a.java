package top.xmdhs.uidget;

import java.net.URL;

public class a {
    public static void main(String[] args){
        papapa[] pa = new papapa[3];
        pa[0] = new papapa("2147483646","1","1075816",0);
        pa[1] = new papapa("2147483645","1075817","2151632",2500);
        pa[2] = new papapa("2147483644","2151633","3227448",5000);
        for (top.xmdhs.uidget.papapa papapa : pa) {
            papapa.start();
        }
    }
}

class papapa extends Thread{
    private final String uid;
    private final String start;
    private final String end;
    private final int time;
    sqlite s = new sqlite();
    public papapa(String uid,String start,String end,int time){
        this.uid = uid;
        this.start = start;
        this.end = end;
        this.time = time;
    }
    public void run(){
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int i = s.getUid(uid);
        if (i == -1) {
            s.creatSql();
            s.insertsql(Integer.parseInt(uid),"0",Integer.parseInt(start),0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,"0","0");
        }
        try {
            while (i <= Integer.parseInt(end)) {
                Thread.sleep(250);
                i = s.getUid(uid);
                URL url = new URL("https://www.mcbbs.net/api/mobile/index.php?module=profile&uid="+ i);
                http h = new http(url);
                if (h.json2Class(h.getJson()).Integer == 1) {
                    System.out.println("网络似乎有什么问题");
                    Thread.sleep(30000);
                    continue;
                }
                if (h.json2Class(h.getJson()).uidapi == null) {
                    System.out.println("此用户大概有什么问题，uid：" + i);
                }
                else {
                    uidapi u = h.json2Class(h.getJson()).uidapi;
                    String username = u.Variables.space.username.replace("'", "''");
                    System.out.println("用户名：" + username + "，uid：" + u.Variables.space.uid);
                    if(u.Variables.space.group == null){
                        i = u.Variables.space.uid;
                        i++;
                        s.setUid(i,uid);
                        continue;
                    }else {
                    s.insertsql(u.Variables.space.uid, username, u.Variables.space.credits,u.Variables.space.extcredits1,
                            u.Variables.space.extcredits2,u.Variables.space.extcredits3,u.Variables.space.extcredits4,
                            u.Variables.space.extcredits5,u.Variables.space.extcredits6,u.Variables.space.extcredits7,
                            u.Variables.space.extcredits8,u.Variables.space.oltime,u.Variables.space.groupid,
                            u.Variables.space.posts,u.Variables.space.threads,u.Variables.space.friends,
                            u.Variables.space.views,u.Variables.space.adminid,u.Variables.space.emailstatus, u.Variables.space.group.grouptitle,u.Variables.space.extgroupids);
                    }
                    i = u.Variables.space.uid;
                }
                i++;
                s.setUid(i,uid);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
