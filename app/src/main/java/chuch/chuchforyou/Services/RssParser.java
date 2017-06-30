package chuch.chuchforyou.Services;

import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by eli200601 on 30/06/2017.
 */

public class RssParser {

    private final String ns = null;

    private static final String TAG = "RssParser";

    public List<RssItem> parse(InputStream inputStream) throws XmlPullParserException, IOException {
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(inputStream, null);
            parser.nextTag();
            return readFeed(parser);
        } finally {
            inputStream.close();
        }
    }

    private List<RssItem> readFeed(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, null, "rss");
        String image = null;
        List<RssItem> items = new ArrayList<RssItem>();
        while (parser.next() != XmlPullParser.END_DOCUMENT) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            Log.d(TAG, "Name =   + " + name);
            if (name.equals("media:thumbnail")) {
                Log.d(TAG, "description - " + parser.getAttributeValue(ns, "url"));
                image = parser.getAttributeValue(ns, "url");
            }
            if (image != null) {
                RssItem item = new RssItem(image);
                items.add(item);
                image = null;
            }
        }
        return items;
    }

    private String readLink(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, "link");
        String link = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "link");
        return link;
    }

    private String readTitle(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, "title");
        String title = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "title");
        return title;
    }

    private String readDescription(XmlPullParser parser) throws XmlPullParserException, IOException {
        Log.d(TAG, "readDescription");
        parser.require(XmlPullParser.START_TAG, ns, "description");
        String title = getImageURL(parser);
        parser.require(XmlPullParser.END_TAG, ns, "description");
        return title;
    }

    private String getImageURL(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";

        String description = "";

        int startIndex, endIndex;

        String findImageURL = "<img src=";
        String findEndURL = ".jpg";

        if (parser.next() == XmlPullParser.TEXT) {
            description = parser.getText();

            Log.d(TAG, description);

            startIndex = description.lastIndexOf(findImageURL);
            endIndex = description.lastIndexOf(findEndURL);

            if (startIndex+endIndex<0) {
                return result;
            } else {
                result = description.substring(startIndex,endIndex);
                Log.d(TAG, result);
                result = parser.getText();
                parser.nextTag();
            }
        }
        return result;
    }

    // For the tags title and link, extract their text values.
    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }

}
