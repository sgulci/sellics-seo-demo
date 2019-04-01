package com.sellic.amazon.seo.service;

import com.sellic.amazon.seo.domain.AmazonKeywordResult;
import com.sellic.amazon.seo.domain.Estimation;
import com.sellic.amazon.seo.util.RestUtil;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Estimation service that resides main estimation algorithm and methods
 *
 * @author sahin
 */

@Component
public class EstimationService {

    @Autowired
    private RestUtil rest;

    /**
     * Amazon autocomplete service url
     */
    @Value("${amazon.autocomplete.url}")
    private String AMAZON_URL;


    /**
     * Main method for estimating keyword
     *
     * @param keyword Keyword that we are trying to estimate seo score
     * @return Returns {@link com.sellic.amazon.seo.domain.Estimation  } class
     */
    public Estimation estimate(String keyword) {

        int len = keyword.length();
        List<String> searchList = new ArrayList<>();

        // search words for sending amazon autocomplete service
        for (int i = 1; i <= len; i++) {
            searchList.add(keyword.substring(0, i));
        }


        //making parallel rest calls
        List<CompletableFuture<AmazonKeywordResult>> futures =
                searchList.stream()
                        .map(word -> getAmazonKeywordResultAsync(word))
                        .collect(Collectors.toList());

        // collecting parallel rest calls results
        List<AmazonKeywordResult> result =
                futures.stream()
                        .map(CompletableFuture::join)
                        .collect(Collectors.toList());


        List<AmazonKeywordResult> searchResultsThatContainsKeyword = new ArrayList<>();

        for (AmazonKeywordResult res : result) {

            if (res.getResultList().contains(keyword))
                searchResultsThatContainsKeyword.add(res);
        }

        return calculatEstimation(keyword, searchResultsThatContainsKeyword);
    }

    /**
     * Main method for estimate calculation
     *
     * @param keyword
     * @param searchResultsThatContainsKeyword
     * @return
     */
    private Estimation calculatEstimation(String keyword, List<AmazonKeywordResult> searchResultsThatContainsKeyword) {
        Estimation estimation = new Estimation();
        estimation.setKeyword(keyword);

        // if keyword is not appeared in results return score zero
        if (searchResultsThatContainsKeyword.size() == 0) {

            estimation.setScore(0);
            return estimation;
        }

        int medianValueForLetter = 100 / keyword.length();

        int minLengthForSearchWord = searchResultsThatContainsKeyword.get(0).getKeyword().length();

        for (AmazonKeywordResult res : searchResultsThatContainsKeyword) {
            if (minLengthForSearchWord > res.getKeyword().length())
                minLengthForSearchWord = res.getKeyword().length();

        }

        int score = 100 - (medianValueForLetter * (minLengthForSearchWord - 1));
        estimation.setScore(score);

        return estimation;
    }


    /**
     * Async method for making Amazon autocomplete rest service
     *
     * @param word word that sending to Amazon autocomplete service
     * @return
     */
    private CompletableFuture<AmazonKeywordResult> getAmazonKeywordResultAsync(String word) {

        CompletableFuture<AmazonKeywordResult> future = CompletableFuture.supplyAsync(() -> {

            AmazonKeywordResult result = new AmazonKeywordResult();

            String json = rest.makeGetCall(AMAZON_URL, word);
            JSONArray arrayJson = new JSONArray(json);

            JSONArray keywordListJson = arrayJson.getJSONArray(1);

            result.setKeyword(word);
            result.setResultList(keywordListJson);


            return result;
        });

        return future;
    }
}
