package com.example.pc.hocrsstintuc24h;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    ListView lvDocBao;
    DocBaoAdapter docBaoAdapter;
    ArrayList<DocBao> listDocBao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvDocBao = (ListView) findViewById(R.id.lvTintuc);
        listDocBao = new ArrayList<>();

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new ReadData().execute("http://vnexpress.net/rss/the-gioi.rss");
            }
        });
    }
    class ReadData extends AsyncTask<String,Integer,String>{

        @Override
        protected String doInBackground(String... params) {
            return docNoiDung_Tu_URL(params[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            XMLDOMParser parser = new XMLDOMParser();
            Document document = parser.getDocument(s);
            NodeList nodeList = document.getElementsByTagName("item");
            NodeList nodeListDescription = document.getElementsByTagName("description");
            String hinhanh = "";
            String title = "";
            String link = "";
            for(int i=0;i<nodeList.getLength();i++){
                String cdata = nodeListDescription.item(i + 1).getTextContent();
                Pattern pattern = Pattern.compile("<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>");
                Matcher matcher = pattern.matcher(cdata);
                if(matcher.find()){
                    hinhanh = matcher.group(1);
                   // Log.d("hinhanh",hinhanh + "......."+i);
                }
                Element element = (Element) nodeList.item(i);
                title = parser.getValue(element,"title");
                link = parser.getValue(element,"link");
                listDocBao.add(new DocBao(title,link,hinhanh));
               // Log.d("link",link);
               // Toast.makeText(MainActivity.this,title,Toast.LENGTH_LONG).show();
            }
            docBaoAdapter = new DocBaoAdapter(MainActivity.this,android.R.layout.simple_list_item_1,listDocBao);
            lvDocBao.setAdapter(docBaoAdapter);
            super.onPostExecute(s);
           // Toast.makeText(MainActivity.this,s,Toast.LENGTH_LONG).show();
        }
    }
    private static String docNoiDung_Tu_URL(String theUrl)
    {
        StringBuilder content = new StringBuilder();

        try
        {
            // create a url object
            URL url = new URL(theUrl);

            // create a urlconnection object
            URLConnection urlConnection = url.openConnection();

            // wrap the urlconnection in a bufferedreader
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            String line;

            // read from the urlconnection via the bufferedreader
            while ((line = bufferedReader.readLine()) != null)
            {
                content.append(line + "\n");
            }
            bufferedReader.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return content.toString();
    }
}
