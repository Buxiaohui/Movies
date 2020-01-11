package com.buxiaohui.movies.net;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.jetbrains.annotations.NotNull;

import com.buxiaohui.movies.BuildConfig;
import com.buxiaohui.movies.Config;
import com.buxiaohui.movies.utils.LogUtils;

import android.os.Handler;
import android.util.Log;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class HttpHelper {
    private static final String TAG = "HttpHelper";
    private static final boolean DEBUG = BuildConfig.DEBUG;
    public static final String debugStr = "{\"Title\":\"Joker\",\"Year\":\"2019\",\"Rated\":\"R\",\"Released\":\"04 "
            + "Oct 2019\","
            + "\"Runtime\":\"122"
            + " min\",\"Genre\":\"Crime, Drama, Thriller\",\"Director\":\"Todd Phillips\",\"Writer\":\"Todd Phillips,"
            + " Scott Silver, Bob Kane (based on characters created by), Bill Finger (based on characters created by)"
            + ", Jerry Robinson (based on characters created by)\",\"Actors\":\"Joaquin Phoenix, Robert De Niro, "
            + "Zazie Beetz, Frances Conroy\",\"Plot\":\"In Gotham City, mentally troubled comedian Arthur Fleck is "
            + "disregarded and mistreated by society. He then embarks on a downward spiral of revolution and bloody "
            + "crime. This path brings him face-to-face with his alter-ego: the Joker.\",\"Language\":\"English\","
            + "\"Country\":\"USA, Canada\",\"Awards\":\"Nominated for 4 Golden Globes. Another 11 wins & 37 "
            + "nominations.\",\"Poster\":\"https://m.media-amazon"
            + ".com/images/M/MV5BNGVjNWI4ZGUtNzE0MS00YTJmLWE0ZDctN2ZiYTk2YmI3NTYyXkEyXkFqcGdeQXVyMTkxNjUyNQ@@"
            + "._V1_SX300.jpg\",\"Ratings\":[{\"Source\":\"Internet Movie Database\",\"Value\":\"8.7/10\"},"
            + "{\"Source\":\"Rotten Tomatoes\",\"Value\":\"69%\"},{\"Source\":\"Metacritic\",\"Value\":\"59/100\"}],"
            + "\"Metascore\":\"59\",\"imdbRating\":\"8.7\",\"imdbVotes\":\"513,667\",\"imdbID\":\"tt7286456\","
            + "\"Type\":\"movie\",\"DVD\":\"17 Dec 2019\",\"BoxOffice\":\"N/A\",\"Production\":\"Warner Bros. "
            + "Pictures\",\"Website\":\"N/A\",\"Response\":\"True\"}\n"
            + "\n";
    private Handler mHandler = new Handler() {

    };

    private static void test() {
        HttpHelper httpHelper = new HttpHelper();
        Map<String, String> map = new HashMap<String, String>();
        map.put("test", "fuck");
        map.put("af", "你好");
        httpHelper.get("dynamic", map, new HttpHelper.NetListener() {
            @Override
            public void onSuccess(String response) {

            }

            @Override
            public void onError(String desc) {

            }
        });
    }

    public void get(final String url, final Map<String, String> params, final NetListener listener) {
        try {
            OkHttpClient client = new OkHttpClient();
            StringBuffer paramStr = new StringBuffer("");
            if (params != null) {
                paramStr.append("?");
                paramStr.append("apikey");
                paramStr.append("=");
                paramStr.append(Config.KEY);
                if (params != null && params.size() > 0) {
                    paramStr.append("&");
                    for (String key : params.keySet()) {
                        paramStr.append(key + "=" + URLEncoder.encode(params.get(key), "utf-8") + "&");
                    }
                }
                paramStr.deleteCharAt(paramStr.length() - 1);
                if (LogUtils.DEBUG) {
                    LogUtils.d(TAG, "get,paramStr:" + paramStr.toString());
                    LogUtils.d(TAG, "get,paramStr:" + url + paramStr);
                }
            }

            final Request request = new Request.Builder()
                    .url(url + paramStr)
                    .get()
                    .build();//创建Request 对象
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    Log.e(TAG, "get,onFailure,e:" + e);
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (listener != null) {
                                listener.onError("onFailure");
                            }
                        }
                    });
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull final Response response) throws IOException {
                    if (listener != null) {
                        ResponseBody s = null;
                        if (response == null) {
                            listener.onError("response is null");

                        } else if ((s = response.body()) == null) {
                            listener.onError("response is null");
                        } else {
                            if (listener != null) {
                                listener.onSuccess(s.string());
                            }
                        }
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void post(final String url, final Map<String, String> params, final NetListener listener) {
        try {
            OkHttpClient client = new OkHttpClient();
            FormBody.Builder formBody = new FormBody.Builder();
            StringBuffer paramStr = new StringBuffer("");

            // TODO
            if (params != null) {
                for (String key : params.keySet()) {
                    formBody.add("key", params.get(key));
                }
            }

            Request request = new Request.Builder()
                    .url(url + paramStr)
                    .post(formBody.build())
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    // TODO
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    // TODO
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void put(final String url, final Map<String, String> params, final NetListener listener) {

        try {
            OkHttpClient client = new OkHttpClient();
            FormBody.Builder formBody = new FormBody.Builder();
            StringBuffer paramStr = new StringBuffer("");
            if (params != null) {
                for (String key : params.keySet()) {
                    formBody.add("key", params.get(key));
                }
            }

            Request request = new Request.Builder()
                    .url(url + paramStr)
                    .put(formBody.build())
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    // TODO
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    // TODO
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void delete(final String url, final Map<String, String> params, final NetListener listener) {

        try {
            OkHttpClient client = new OkHttpClient();
            FormBody.Builder formBody = new FormBody.Builder();
            StringBuffer paramStr = new StringBuffer("");
            if (params != null) {
                for (String key : params.keySet()) {
                    formBody.add("key", params.get(key));
                }
            }

            Request request = new Request.Builder()
                    .url(url + paramStr)
                    .delete(formBody.build())
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    // TODO
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    // TODO
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public interface NetListener {
        void onSuccess(String response);

        void onError(String desc);
    }
}
