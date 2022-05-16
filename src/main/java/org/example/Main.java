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
            readSubs();
        }
        catch (Exception e){
            System.out.println(e);
        }

    }
    public static void getURL() throws Exception {
        URLConnection conn = new URL("https://www.reddit.com/r/bostonu.json").openConnection();
        conn.setRequestProperty("User-Agent", "");
        ReadableByteChannel rbc = Channels.newChannel(conn.getInputStream());
        FileOutputStream fos = new FileOutputStream("src/main/resources/sample.json");
        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
    }

    public static void readSubs() throws Exception{
        Reader reader = Files.newBufferedReader(Paths.get("src/main/resources").toAbsolutePath().resolve("sample.json"));
        Map<?, ?> map = new Gson().fromJson(reader, Map.class);
        LinkedTreeMap<?, ?> data = (LinkedTreeMap<?, ?>) map.get("data");
        ArrayList<?> children = (ArrayList<?>) data.get("children");
        for (Object child : children) {
            LinkedTreeMap<?, ?> entry0 = (LinkedTreeMap<?, ?>) ((LinkedTreeMap<?, ?>) child).get("data");
            System.out.println(entry0.get("title"));
        }
        reader.close();
    }

}