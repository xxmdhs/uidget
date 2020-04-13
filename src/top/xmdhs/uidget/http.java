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
    private URL url;
    public http(URL url){
        this.url = url;
    }

    /**
     * 传入链接，获取 json
     * @return 返回 json（未处理
     */
    public String getJson(){
        try {
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
            e.printStackTrace();
            return "1";
        }
    }
    public ReturnTwo<uidapi, Integer> json2Class(String json){
        Gson gson = new Gson();
        if(json.equals("1")){
            return new ReturnTwo<>(null, 1);
        }
       try {
           uidapi u = gson.fromJson(json, uidapi.class);
           return new ReturnTwo<>(u, 0);
       }catch (JsonSyntaxException e){
           return new ReturnTwo<>(null, 2);
       }
    }
}

/**
 * 用于处理 json
 */
class uidapi{
    public String Version;
    public variables Variables;
      class variables{
          public Space space;
          class Space{
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
              public int friends;
              public Group group;
              class Group{
                  public String grouptitle;
              }
              public int views;
              public int emailstatus;
              public int adminid;
              public String extgroupids;
          }
      }
    }
class ReturnTwo<A,B> {

    public final A uidapi;
    public final B Integer;

    public ReturnTwo(A a,B b) {
        uidapi = a;
        Integer = b;
    }

}
