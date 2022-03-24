package com.twitter.bugerbot.model.stores;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Store {

    @JsonProperty("id")
    public Integer id;
    @JsonProperty("name")
    public String name;
    @JsonProperty("image")
    public String image;
    @JsonProperty("season")
    public Integer season;
    @JsonProperty("episode")
    public Integer episode;
    @JsonProperty("episodeUrl")
    public String episodeUrl;
    @JsonProperty("url")
    public String url;}