package chuch.chuchforyou;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Iterator;


/**
 * Created by eliran.alon on 25-Jun-17.
 */

public class GetHTTPService extends IntentService {

    String TAG = "Service";
    String url = "https://www.instagram.com/sapir_yulie/";


    public GetHTTPService(String name) {
        super(name);
    }

    public GetHTTPService() {
        super(null);
    }

    private static void print(String msg, Object... args) {
        System.out.println(String.format(msg, args));
    }

    private static String trim(String s, int width) {
        return s.length() > width ? s.substring(0, width - 1) + "." : s;
    }
    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(TAG, " Starting service");

            print("Fetching %s...", new Object[]{url});
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements links = doc.select("a[href]");
            Elements media = doc.select("[src]");
            Elements imports = doc.select("link[href]");
            print("\nMedia: (%d)", new Object[]{Integer.valueOf(media.size())});
            Iterator var6 = media.iterator();

            Element link;
            while(var6.hasNext()) {
                link = (Element)var6.next();
                if(link.tagName().equals("img")) {
                    print(" * %s: <%s> %sx%s (%s)", new Object[]{link.tagName(), link.attr("abs:src"), link.attr("width"), link.attr("height"), trim(link.attr("alt"), 20)});
                } else {
                    print(" * %s: <%s>", new Object[]{link.tagName(), link.attr("abs:src")});
                }
            }

            print("\nImports: (%d)", new Object[]{Integer.valueOf(imports.size())});
            var6 = imports.iterator();

            while(var6.hasNext()) {
                link = (Element)var6.next();
                print(" * %s <%s> (%s)", new Object[]{link.tagName(), link.attr("abs:href"), link.attr("rel")});
            }

            print("\nLinks: (%d)", new Object[]{Integer.valueOf(links.size())});
            var6 = links.iterator();

            while(var6.hasNext()) {
                link = (Element)var6.next();
                print(" * a: <%s>  (%s)", new Object[]{link.attr("abs:href"), trim(link.text(), 35)});
            }

//        StringRequest req = new StringRequest(url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                Log.d(TAG, " onResponse" + response.toString());
////                VolleyLog.v("Response:%n %s", response);
//                str = response.toString();
//                Document doc = Jsoup.parse(str);
////                Element image = str.select("img").first();
//                Elements pngs = null;
//                pngs = doc.select("img[src~=(?i)\\.(png|jpe?g|gif)]");
//////            pngs = doc.select("img");
//////            pngs = doc.select("a[href*=https]");
//                Log.d(TAG, " pngs" + pngs.size());
//                for (Element elm: pngs){
//                    Log.d(TAG, elm.absUrl("src"));
//                    Log.d(TAG, elm.attr("src"));
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.d(TAG, " onErrorResponse");
//                VolleyLog.e("Error: ", error.getMessage());
//            }
//        });

// add the request object to the queue to be executed
//        Volley.newRequestQueue(this).add(req);


//        try {
//            Document doc = Jsoup.connect("https://www.google.co.il/search?q=dsfsdf&source=lnms&tbm=isch&sa=X&ved=0ahUKEwj69N61m9vUAhXFtBQKHWqdAQEQ_AUIBigB&biw=1920&bih=1061").userAgent("Jsoup client").validateTLSCertificates(true).get();
////            Element image = doc.select("img").first();
////            if (image != null) url = image.absUrl("src");
//            pngs = doc.select("img[src~=(?i)\\.(png|jpe?g|gif)]");
////            pngs = doc.select("img");
////            pngs = doc.select("a[href*=https]");
//            for (Element elm: pngs){
//                Log.d(TAG, elm.absUrl("src"));
//                Log.d(TAG, elm.attr("src"));
//            }
//
//            Log.d(TAG, " Eliran - " + pngs.toString());
//            Elements links = doc.select("a[href]"); // a with href
//        } catch (IOException e) {
//            Log.d(TAG, " catch!!! ");
//            Log.d("Main", e.getMessage());
//        }

        Log.d("Main", "eliran");
        this.stopSelf();
    }
}
