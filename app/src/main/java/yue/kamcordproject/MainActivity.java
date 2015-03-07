package yue.kamcordproject;

import android.graphics.Bitmap;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.*;
import android.widget.ListView;
import android.os.AsyncTask;
import android.graphics.*;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.content.Intent;
import android.widget.TextView;

import java.util.ArrayList;
import java.net.*;
import java.io.*;

import org.json.*;

public class MainActivity extends ActionBarActivity {

    ArrayList<String> titleList = new ArrayList<String>();
    ArrayList<Bitmap> thumbList = new ArrayList<Bitmap>();
    ArrayList<String> videoList = new ArrayList<String>();
    WatchArrayAdapter adapter;
    DownloadWatchTask w;

    TextView textview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listview = (ListView) findViewById(R.id.listView);
        textview = (TextView) findViewById(R.id.textView);

        w = new DownloadWatchTask(titleList,thumbList);
        adapter = new WatchArrayAdapter(this, titleList, thumbList);

        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                String video_url = videoList.get(position);
                Intent i = new Intent(MainActivity.this, VideoActivity.class);
                i.putExtra("url", video_url);
                MainActivity.this.startActivity(i);
            }
        });

        w.execute("https://www.kamcord.com/app/v2/videos/feed/?feed_id=0");
    }
    private class DownloadWatchTask extends AsyncTask<String, Integer, String> {
        ArrayList<String> title;
        ArrayList<Bitmap> thumb;

        public DownloadWatchTask(ArrayList<String> t, ArrayList<Bitmap> v){
            thumb = v;
            title = t;
        }

        protected String doInBackground(String...url) {
            int i = 0;

            JSONArray video_list = new JSONArray();
            try {
                publishProgress((int)(((double) i)/video_list.length()*100));
                JSONObject parsed = new JSONObject(getStringFromURL(url[0]));
                JSONObject response = parsed.getJSONObject("response");
                video_list = response.getJSONArray("video_list");

            } catch (JSONException e) {
                e.printStackTrace();
            }


            try {
                while(!video_list.isNull(i)) {
                    title.add(video_list.getJSONObject(i).getString("title"));
                    videoList.add(video_list.getJSONObject(i).getString("video_url"));
                    Bitmap icon = getBitmapFromURL(video_list.getJSONObject(i).getJSONObject("thumbnails").getString("REGULAR"));
                    thumb.add(icon);
                    publishProgress((int)((double)i / video_list.length()*100));
                    i++;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return "Complete!";
        }

        @Override
        protected void onProgressUpdate(Integer...i){

            textview.setText(i[0]+"%");
        }

        @Override
        protected void onPostExecute(String s) {
            textview.setText(s);
            adapter.notifyDataSetChanged();
        }

    }

    public static String getStringFromURL(String url) {
        String content="";
        try {
            URL u = new URL(url);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            u.openStream()));

            String inputLine;

            while ((inputLine = in.readLine()) != null)
                content += inputLine;

            in.close();
            return content;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
