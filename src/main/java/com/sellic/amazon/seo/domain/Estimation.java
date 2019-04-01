package com.sellic.amazon.seo.domain;

/**
 * Entitty for estimation result
 *
 * @author sahin
 */
public class Estimation {

    /**
     * Keyword that estimating Amazon SEO score
     */
    private String keyword;

    /**
     * Estimation score number between 0 - 100
     */
    private int score;


    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
