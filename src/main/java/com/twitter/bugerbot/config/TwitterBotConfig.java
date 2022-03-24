package com.twitter.bugerbot.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

@Configuration
public class TwitterBotConfig {

    @Value("${twitter.auth-api-key}")
    private String twitterApiKey;

    @Value("${twitter.auth-api-secret}")
    private String twitterApiSecret;

    @Value("${twitter.auth-api-access-token-secret}")
    private String twitterAccessTokenSecret;

    @Value("${twitter.auth-api-access-token}")
    private String twitterAccessToken;

    @Bean
    public Twitter twitter() {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setOAuthConsumerKey(twitterApiKey);
        cb.setOAuthConsumerSecret(twitterApiSecret);
        cb.setOAuthAccessTokenSecret(twitterAccessTokenSecret);
        cb.setOAuthAccessToken(twitterAccessToken);
        TwitterFactory tf = new TwitterFactory(cb.build());
        return tf.getInstance();
    }
}