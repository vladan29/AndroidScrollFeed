package com.vladan.randompagegenerator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ViewConfiguration;
import android.widget.AbsListView;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
      ListView list;
    int[] numbers=new int [10];
    CustomListAdapter listAdapter;
    List<BasicParameters> bpItems=new ArrayList<>();
    public static String BASIC_REQUEST_URL = "https://pagegeneratorapi.herokuapp.com/api?page=";
    public static int scrollFlag = 0;
    public int mTotalItemCount;
    public int mScrollState;
    boolean scrollingDown = true;
    boolean scrollingUp;
    int fVItem;
    public int pageDown = 0;
    public int pageUp = 0;
    String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        list=(ListView)findViewById(R.id.list);
        list.setFriction(ViewConfiguration.getScrollFriction()*100);
        listAdapter=new CustomListAdapter(this,bpItems);
        list.setAdapter(listAdapter);
        fetchData(BASIC_REQUEST_URL+String.valueOf(0));

        list.setOnScrollListener(new AbsListView.OnScrollListener() {

            private int mLastFirstVisibleItem;
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                mScrollState = scrollState;
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                mTotalItemCount = totalItemCount;
                fVItem = firstVisibleItem;
                if (mLastFirstVisibleItem < firstVisibleItem) {
                    scrollingDown = true;
                    scrollingUp = false;
                    Log.i("SCROLLING DOWN", "TRUE");
                }
                if (mLastFirstVisibleItem > firstVisibleItem) {
                    scrollingUp = true;
                    scrollingDown = false;
                    Log.i("SCROLLING UP", "TRUE");
                }
                mLastFirstVisibleItem = firstVisibleItem;
                int startItem = visibleItemCount + 3;

                if (scrollFlag == 0 && scrollingDown && totalItemCount != 0 && (totalItemCount - firstVisibleItem) <= startItem && totalItemCount % 10 == 0) {
                    pageDown++;

                    url = BASIC_REQUEST_URL  + String.valueOf(pageDown);
                    fetchData(url);

                    scrollFlag = 1;

                }
                Log.d("TotalItemCount", String.valueOf(totalItemCount));
                Log.d("FirstVisibleItem", String.valueOf(firstVisibleItem));
                Log.d("VisibleItemCount", String.valueOf(visibleItemCount));

                if (scrollFlag == 0 && scrollingUp && totalItemCount != 0 && firstVisibleItem <= 3 && pageUp > 0) {

                    pageUp--;
                    url = BASIC_REQUEST_URL +  String.valueOf(pageUp);
                    fetchData(url);
                    scrollFlag = 1;

                    Log.d("TotalItemCount", String.valueOf(totalItemCount));
                    Log.d("FirstVisibleItem", String.valueOf(firstVisibleItem));
                    Log.d("VisibleItemCount", String.valueOf(visibleItemCount));
                }
            }
        });
    }

    public int fetchData(String url) {
        final String page=url.substring(48);
        StringRequest getRequest = new StringRequest(Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Response", response);

                        try {
                            JSONArray jsonArray = new JSONArray(response);

                            for (int i = 0; i < jsonArray.length(); i++) {

                                BasicParameters basicParameters = new BasicParameters();
                               String number =jsonArray.getString(i);
                                basicParameters.setNumber(number);
                                String url="https://unsplash.it/200/300/?image=" + String.valueOf(i*(Integer.parseInt(page)+1));
                                basicParameters.setUrl(url);
                                basicParameters.setPage(String.valueOf((Integer.parseInt(page)+1)));
                                if (mTotalItemCount<30){
                                basicParameters.setTotalItemCount(mTotalItemCount+10);
                                }else basicParameters.setTotalItemCount(mTotalItemCount);

                                if (scrollingDown) {

                                    bpItems.add(basicParameters);
                                }
                                if (scrollingUp) {

                                    bpItems.add(i, basicParameters);
                                    fVItem++;
                                }
                            }
                            if (scrollingDown && bpItems.size() > 30) {
                                while (bpItems.size() > 30) {
                                    bpItems.remove(0);
                                    fVItem--;
                                }
                                pageUp++;
                            }
                            if (scrollingUp && bpItems.size() > 30) {
                                while (bpItems.size() > 30) {
                                    bpItems.remove(bpItems.size() - 1);
                                }
                                pageDown--;
                            }

                            listAdapter.notifyDataSetChanged();
                            list.setSelection(fVItem);
                            scrollFlag = 0;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }

        });
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(getRequest);

        return scrollFlag;
    }
}
