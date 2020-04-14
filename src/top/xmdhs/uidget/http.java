package top.xmdhs.uidget;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;


public class http {
    private final URL url;
    public http(URL url){
        this.url = url;
    }

    /**
     * 传入链接，获取 json
     * @return 返回 json（未处理
     */
    public String getJson(){
        try {
            try {
                Thread.sleep(250);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setUseCaches(false);
            connection.setConnectTimeout(5000);
            connection.setRequestProperty("Accept", "*/*");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.163 Safari/537.36");
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
            String current;
            StringBuilder json = new StringBuilder();
            while ((current = in.readLine()) != null) {
                json.append(current);
            }
            return json.toString();


            } catch (IOException e) {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException ee) {
                ee.printStackTrace();
            }
            return "1";
        }
    }
    public uidapi json2Class(String json){
        Gson gson = new Gson();
       try {
           return gson.fromJson(json, uidapi.class);
       }catch (JsonSyntaxException e){
           return null;
       }
    }
}

/**
 * 用于处理 json
 */
class uidapi{
    public variables Variables;
      static class variables{
          public Space space;
          static class Space{
              public int uid;
              public String username;
              public int credits;
              public int extcredits1;
              public int extcredits2;
              public int extcredits3;
              public int extcredits4;
              public int extcredits5;
              public int extcredits6;
              public int extcredits7;
              public int extcredits8;
              public int oltime;
              public int groupid;
              public int posts;
              public int threads;
              public Group group;
              static class Group{
                  public String grouptitle;
              }
              public int friends;
              public int views;
              public int emailstatus;
              public int adminid;
              public String extgroupids;
          }
      }
    }
