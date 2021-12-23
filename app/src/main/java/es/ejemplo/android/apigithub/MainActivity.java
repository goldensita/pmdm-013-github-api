package es.ejemplo.android.apigithub;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    public void makeRequest(View _) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                URL githubEndpoint = null;

                try {
                    githubEndpoint = new URL("https://api.github.com");
                    HttpsURLConnection myConnection = (HttpsURLConnection) githubEndpoint.openConnection();
                    myConnection.setRequestProperty("User-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.110 Safari/537.36\n");
                    if (myConnection.getResponseCode() == 200) {
                        InputStream inputStream = myConnection.getInputStream();
                        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                        JsonReader jsonReader = new JsonReader(inputStreamReader);

                        jsonReader.beginObject();
                        while (jsonReader.hasNext()) {
                            String key = jsonReader.nextName();
                            if (key.equals("repository_url")) {
                                Log.i("HTTP", "repository_url = " + jsonReader.nextString());

                            }
                            jsonReader.skipValue();
                        }


                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}