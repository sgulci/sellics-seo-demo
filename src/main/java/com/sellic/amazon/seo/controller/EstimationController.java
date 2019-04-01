package com.sellic.amazon.seo.controller;

import com.sellic.amazon.seo.domain.Estimation;
import com.sellic.amazon.seo.service.EstimationService;
import com.sellic.amazon.seo.util.RestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Estimation Rest Controller
 *
 * @author sahin
 */

@RestController
@RequestMapping("estimate")
public class EstimationController {

    @Autowired
    private EstimationService estimationService;

    /**
     * Rest methos for estimating keyword Amazon SEO score
     *
     * @param keyword
     * @return  Returns {@link com.sellic.amazon.seo.domain.Estimation  } class
     */
    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody Estimation estimate(@RequestParam("keyword") String keyword){
        return estimationService.estimate(keyword);
    }
}
