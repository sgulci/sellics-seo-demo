package com.sellic.amazon.seo.domain;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Entity for keeping Amazon autocomplete result with searching keyword
 *
 * @author sahin
 */
public class AmazonKeywordResult {

    /**
     * Parameter for  search word
     */
    private String keyword;


    /**
     * Parameter for autocomplete result list
     */
    private List<String> resultList;

    public List<String> getResultList() {
        return resultList;
    }

    public void setResultList(List<String> resultList) {
        this.resultList = resultList;
    }

    public void setResultList(JSONArray resultList) {
        List<String> list = new ArrayList<String>();
        for (int i=0; i<resultList.length(); i++) {
            list.add( resultList.getString(i) );
        }
        this.resultList = list;

    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
