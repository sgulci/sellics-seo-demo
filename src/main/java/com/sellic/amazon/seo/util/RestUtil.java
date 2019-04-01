package com.sellic.amazon.seo.util;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 *Java  class for making rest calls
 *
 * @author sahin
 */

@Component
public class RestUtil {

    /**
     * Method for making rest calls
     *
     * @param url Url address that calling with rest
     * @param param  Querystring paramater
     * @return   Returns response body
     */
    public  String makeGetCall(String url,String param){

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response
                = restTemplate.getForEntity(url + param, String.class);

        return response.getBody();
    }
}
