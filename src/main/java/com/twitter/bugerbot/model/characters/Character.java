package com.twitter.bugerbot.model.characters;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Character {

    @JsonProperty("id")
    public Integer id;
    @JsonProperty("name")
    public String name;
    @JsonProperty("image")
    public String image;
    @JsonProperty("gender")
    public String gender;
    @JsonProperty("hairColor")
    public String hairColor;
    @JsonProperty("occupation")
    public String occupation;
    @JsonProperty("firstEpisode")
    public String firstEpisode;
    @JsonProperty("voicedBy")
    public String voicedBy;
    @JsonProperty("url")
    public String url;
    @JsonProperty("age")
    public String age;
}