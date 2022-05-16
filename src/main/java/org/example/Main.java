package org.example;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import java.io.FileOutputStream;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        try{
            getURL();
        }
        catch (Exception e){
            System.out.println(e);
        }

        readSubs();
    }
    public static void getURL() throws Exception {
        URL url = new URL("https://www.reddit.com/r/bostonu.json");
        URLConnection conn = url.openConnection();
        conn.setRequestProperty("User-Agent", "");
        ReadableByteChannel rbc = Channels.newChannel(conn.getInputStream());
        FileOutputStream fos = new FileOutputStream("src/main/resources/sample.json");
        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
    }

    public static void readSubs(){
        try {
            Gson gson = new Gson();
            Reader reader = Files.newBufferedReader(Paths.get("src/main/resources").toAbsolutePath().resolve("sample.json"));
            Map<?, ?> map = gson.fromJson(reader, Map.class);
            LinkedTreeMap<?, ?> data = (LinkedTreeMap<?, ?>) map.get("data");
            ArrayList<?> children = (ArrayList<?>) data.get("children");
            for(int i = 0; i<children.size(); i++){
                LinkedTreeMap<?, ?> entry0 = (LinkedTreeMap<?, ?>) children.get(i);
                LinkedTreeMap<?, ?> entry1 = (LinkedTreeMap<?, ?>) entry0.get("data");
                System.out.println(entry1.get("title"));
            }
            reader.close();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}