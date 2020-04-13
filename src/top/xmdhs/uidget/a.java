package top.xmdhs.uidget;

import java.net.MalformedURLException;
import java.net.URL;

public class a {
    static final public int start = 1;
    static final public int end = 100;
    public static void main(String[] args){
        sqlite s = new sqlite();
        s.creatSql();
        new Thread(() -> {
        try {
            Thread.sleep(500);
            int i = start;
            while (i <= end){
                URL url = new URL("https://www.mcbbs.net/api/mobile/index.php?module=profile&uid="+ i);
                http h = new http(url);
                uidapi u = h.json2Class(h.getJson());
               if(u == null) {
                   System.out.println("此用户大概有什么问题，uid："+ i);
               }else {
                 String username = u.Variables.space.username.replace("'","''");
                System.out.println("用户名："+username+"uid："+u.Variables.space.uid);
                s.insertsql(u.Variables.space.uid,username,u.Variables.space.credits);
               }
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        }).start();
    }
}
