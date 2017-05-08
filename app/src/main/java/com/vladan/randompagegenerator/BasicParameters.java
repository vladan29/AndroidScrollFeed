package com.vladan.randompagegenerator;

/**
 * Created  on 5/8/2017.
 */

public class BasicParameters {
    private String number;
    private String url;
    private String page;
    private int totalItemCount;

    public BasicParameters(){}

    public BasicParameters(String number,String url,String page,int totalItemCount){
        this.number=number;
        this.url=url;
        this.page=page;
        this.totalItemCount=totalItemCount;
    }


    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public int getTotalItemCount() {
        return totalItemCount;
    }

    public void setTotalItemCount(int totalItemCount) {
        this.totalItemCount = totalItemCount;
    }
}
