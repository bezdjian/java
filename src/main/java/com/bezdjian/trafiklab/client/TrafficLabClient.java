package com.bezdjian.trafiklab.client;

import com.bezdjian.trafiklab.model.JourneyPatternPointOnLine;
import com.bezdjian.trafiklab.model.StopPoint;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "trafiklab", url = "${trafiklab.api.url}")
public interface TrafficLabClient {

    @GetMapping(value = "/LineData.json?model=stop&key={key}")
    StopPoint getStopPoints(@PathVariable("key") String key);

    @GetMapping(value = "/LineData.json?model=jour&key={key}")
    JourneyPatternPointOnLine getJourneyPatternPointOnLine(@PathVariable("key") String key);
}
