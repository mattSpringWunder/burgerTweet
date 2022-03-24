package com.twitter.bugerbot.config;

import com.twitter.bugerbot.service.burger.BurgerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import twitter4j.TwitterException;

import java.io.IOException;

@Component
public class ScheduledPost {
    private static final Logger logger = LoggerFactory.getLogger(ScheduledPost.class);

    @Autowired
    BurgerService burgerService;

    @Scheduled(fixedRate = 5000)
    public void postRandomStore() throws IOException, TwitterException {
        burgerService.fetchRandomStore();
    }

    @Scheduled(fixedRate = 5000)
    public void postRandomCharacter() throws IOException, TwitterException {
        burgerService.fetchRandomCharacter();
    }

    @Scheduled(fixedRate = 5000)
    public void postRandomEpisodeFact() throws IOException, TwitterException {
        burgerService.fetchRandomEpisodeFact();
    }
}
