package chuch.chuchforyou.Services;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;


/**
 * Created by eli200601 on 28/06/2017.
 */

public class GetChuchService  extends IntentService {

    private static final String TAG = "TheChuchService";
    private static int TIME_OUT_SEC = 30;
    // https://www.instagram.com/the.squat.girls/
    // Example: https://web.stagram.com/rss/n/the.squat.girls
    private static final String rssAdress = "https://web.stagram.com/rss/n/";

    String instaUserName = "the.squat.girls";

    List<RssItem> rssItems = null;

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(TAG, "Starting Chuch injection");

        Log.d(TAG, "Loading the userName");


        loadRSS();


    }

    public void loadRSS(){
        Log.d(TAG, "loadRSS");
        try {
            RssParser parser = new RssParser();
            rssItems = parser.parse(getInputStream(rssAdress + instaUserName));
            if (rssItems.size() > 0) {
                printRssList(rssItems);
            } else {
                Log.d(TAG, "Cannot retrieve images");
                // Should finish the service hare when its happened

            }

        } catch (XmlPullParserException | IOException e) {
            Log.d(TAG, e.getMessage());
        }

    }

    public void printRssList(List<RssItem> rssItems){
        Log.d(TAG, " ");
        Log.d(TAG, " ");
        Log.d(TAG, " ");
        Log.d(TAG, "====== The Chuch provider supply the following list: ========");
        for(RssItem item: rssItems) {
            Log.d(TAG, "URL: " + item.getUrl());
        }
        Log.d(TAG, "=============================================================");
    }

    public InputStream getInputStream(String link) {
        try {
            URL url = new URL(link);
            return url.openConnection().getInputStream();
        } catch (IOException e) {
            Log.d(TAG, "Exception while retrieving the input stream", e);
            return null;
        }
    }


    public GetChuchService(String name) {
        super(name);
        setIntentRedelivery(true);
    }

    public GetChuchService() {
        super("GetChuchService");
    }
}
