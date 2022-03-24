package com.twitter.bugerbot.service.burger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.twitter.bugerbot.exception.RestTemplateResponseErrorHandler;
import com.twitter.bugerbot.model.characters.Character;
import com.twitter.bugerbot.model.episodes.Episode;
import com.twitter.bugerbot.model.stores.Store;
import com.twitter.bugerbot.service.twitter.TwitterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import twitter4j.TwitterException;

import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

@Service
public class BurgerService {
    private static final Logger logger = LoggerFactory.getLogger(BurgerService.class);

    @Value("${bobapi.root-uri}")
    private String bobsBurgerApi;

    @Autowired
    RestTemplate restTemplateWithErrorHandler;

    @Autowired
    TwitterService twitterService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    public BurgerService(RestTemplateBuilder restTemplateBuilder) {
        RestTemplate restTemplateWithErrorHandler = restTemplateBuilder
                .errorHandler(new RestTemplateResponseErrorHandler())
                .build();
    }

    /**
     * Method to fetch a random character from the bobs burger API and post it to twitter
     * @throws IOException -- Exception thrown if we cannot create a local copy of the Character image -> This is inherited from the Twitter Service
     * @throws TwitterException --  Exception thrown if we post to Twitter -> This is inherited from the Twitter Service
     */
    public void fetchRandomCharacter() throws IOException, TwitterException {
        //Firstly lets fetch all characters from the show:
        ResponseEntity<Character[]> allCharacterResponse = restTemplateWithErrorHandler.exchange(
                bobsBurgerApi + "/characters/",
                HttpMethod.GET, null, Character[].class);
        long totalNumberOfCharacters = 0;

        if (allCharacterResponse.getBody() != null && allCharacterResponse.getBody().length > 0) {
            totalNumberOfCharacters = Arrays.stream(allCharacterResponse.getBody()).count();
        }

        Random random = new Random();
        int randomCharacterIndex = random.ints(1, Math.toIntExact(totalNumberOfCharacters))
                .findFirst()
                .getAsInt();

        //Now lets pick a random one from the Stream Array, and we'll request that
        ResponseEntity<Character> characterResponse = restTemplateWithErrorHandler.exchange(
                bobsBurgerApi + "/characters/" + randomCharacterIndex,
                HttpMethod.GET, null, Character.class);

        if(characterResponse.getBody() != null) {
            ObjectMapper mapper = new ObjectMapper();
            Character character =  mapper.convertValue(characterResponse.getBody(), Character.class);
            twitterService.postGuessTheCharacter(character.getName(), character.getImage(), character.getOccupation(), character.getFirstEpisode());
        }
    }

    /**
     * Method to fetch a random store from the bobs burger API and post it to twitter
     * @throws IOException -- Exception thrown if we cannot create a local copy of the Character image -> This is inherited from the Twitter Service
     * @throws TwitterException --  Exception thrown if we post to Twitter -> This is inherited from the Twitter Service
     */
    public void fetchRandomStore() throws IOException, TwitterException {
        //Firstly lets fetch all characters from the show:
        ResponseEntity<Store[]> allStoresNextDoorResponse = restTemplateWithErrorHandler.exchange(
                bobsBurgerApi + "/storeNextDoor/",
                HttpMethod.GET, null, Store[].class);
        long totalNumberOfStores = 0;

        if (allStoresNextDoorResponse.getBody() != null && allStoresNextDoorResponse.getBody().length > 0) {
            totalNumberOfStores = Arrays.stream(allStoresNextDoorResponse.getBody()).count();
        }

        Random random = new Random();
        int randomStoreIndex = random.ints(1, Math.toIntExact(totalNumberOfStores))
                .findFirst()
                .getAsInt();

        //Now lets pick a random one from the Stream Array, and we'll request that
        ResponseEntity<Store> storeResponse = restTemplateWithErrorHandler.exchange(
                bobsBurgerApi + "/storeNextDoor/" + randomStoreIndex,
                HttpMethod.GET, null, Store.class);

        if(storeResponse.getBody() != null) {
            ObjectMapper mapper = new ObjectMapper();
            Store store =  mapper.convertValue(storeResponse.getBody(), Store.class);
            twitterService.postGuessTheStoreNextDoor(store.getName(), store.getImage(), store.getEpisode(), store.getSeason());
        }
    }

    public void fetchRandomEpisodeFact() throws IOException, TwitterException {
        //Firstly lets fetch all episodes from the show:
        ResponseEntity<Episode[]> allEpisodesResponse = restTemplateWithErrorHandler.exchange(
                bobsBurgerApi + "/storeNextDoor/",
                HttpMethod.GET, null, Episode[].class);
        long totalNumberOfEpisodes = 0;

        if (allEpisodesResponse.getBody() != null && allEpisodesResponse.getBody().length > 0) {
            totalNumberOfEpisodes = Arrays.stream(allEpisodesResponse.getBody()).count();
        }

        Random random = new Random();
        int randomEpisodeIndex = random.ints(1, Math.toIntExact(totalNumberOfEpisodes))
                .findFirst()
                .getAsInt();

        //Now lets pick a random one from the Stream Array, and we'll request that
        ResponseEntity<Episode> episodeResponse = restTemplateWithErrorHandler.exchange(
                bobsBurgerApi + "/episodes/" + randomEpisodeIndex,
                HttpMethod.GET, null, Episode.class);

        if(episodeResponse.getBody() != null) {
            ObjectMapper mapper = new ObjectMapper();
            Episode episode =  mapper.convertValue(episodeResponse.getBody(), Episode.class);
            twitterService.postGuessTheEpisodeViews(episode.getName(), episode.getSeason(), episode.getTotalViewers());
        }
    }
}
