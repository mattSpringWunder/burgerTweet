package com.twitter.bugerbot.service.twitter;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import twitter4j.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;

@Service
public class TwitterService {
    private static final Logger logger = LoggerFactory.getLogger(TwitterService.class);

    @Autowired
    private Twitter twitter;

    /**
     * Method to Post a Character to Twitter
     * @param characterName -- The Name of character to post
     * @param characterImageUrl -- The image of the character to go with the post
     * @param characterOccupation -- The occupation of the character
     * @param firstAppeared -- When the character first appeared
     * @throws TwitterException -- Twitter expection, thrown if unable to post
     * @throws IOException -- IO Exception should we not be able to download the image
     */
    public void postGuessTheCharacter(String characterName, String characterImageUrl, String characterOccupation, String firstAppeared) throws TwitterException, IOException {

        logger.info("Generating text for tweet");
        String statusMessage = "Can you guess this character??? " +
                "They first appeared in Episode " + firstAppeared + "as a " + characterOccupation + "...........................thats right its "+ characterName;

        URL url = new URL(characterImageUrl);
        File destination = new File(characterImageUrl.substring(characterImageUrl.lastIndexOf("/") + 1));

        logger.info("Copying the Image so we can post it to Twitter");
        FileUtils.copyURLToFile(url, destination);

        File imageToPost = new File(destination.getAbsolutePath());

        long[] mediaIds = new long[1];
        UploadedMedia media = twitter.uploadMedia(imageToPost);
        mediaIds[0] = media.getMediaId();

        logger.info("Creating new StatusUpdate Object");
        StatusUpdate statusUpdate = new StatusUpdate(statusMessage);
        statusUpdate.setMediaIds(mediaIds);
        postStatusUpdateToTwitter(statusUpdate);
    }

    public void postGuessTheStoreNextDoor(String storeName, String storeImageUrl, Integer episodeNumber, Integer season) throws IOException, TwitterException {

        // post a tweet link with image
        String statusMessage = "Can you guess which episode this store showed up next to Bobs???????? " +
                "The Store first appeared in Episode...............................................that's right its season "+ season + " episode " + episodeNumber;

        URL url = new URL(storeImageUrl);
        File destination = new File(storeImageUrl.substring(storeImageUrl.lastIndexOf("/") + 1));

        // Copy bytes from the URL to the destination file.
        FileUtils.copyURLToFile(url, destination);

        File imageToPost = new File(destination.getAbsolutePath());

        long[] mediaIds = new long[1];
        UploadedMedia media = twitter.uploadMedia(imageToPost);
        mediaIds[0] = media.getMediaId();

        StatusUpdate statusUpdate = new StatusUpdate(statusMessage);
        statusUpdate.setMediaIds(mediaIds);
        postStatusUpdateToTwitter(statusUpdate);
    }

    public void postGuessTheEpisodeViews(String episodeName, Integer season, String totalViewers) {
        String statusMessage = "Who remembers the episode " + episodeName + " from season " + season +
                "? Well can you guess how many people viewed that episode?? No, didn't think so, it was a staggering " + totalViewers;

        StatusUpdate statusUpdate = new StatusUpdate(statusMessage);
        postStatusUpdateToTwitter(statusUpdate);
    }

    /**
     * Method to Post Tweet to Twtitter
     * @param statusUpdate -- Status Update Object
     */
    private void postStatusUpdateToTwitter(StatusUpdate statusUpdate) {
        try {
            logger.info("Attempting to Post new StatusUpdate Object");
            twitter.updateStatus(statusUpdate);
            logger.info("Posted new StatusUpdate Object");
        } catch (TwitterException e) {
            logger.error("Unable to Post to Twitter {}", e.getErrorMessage());
        }
    }
}
