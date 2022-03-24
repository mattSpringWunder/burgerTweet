package com.twitter.bugerbot.model.episodes;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class Episode {

    @JsonProperty("id")
    public Integer id;
    @JsonProperty("name")
    public String name;
    @JsonProperty("productionCode")
    public String productionCode;
    @JsonProperty("airDate")
    public String airDate;
    @JsonProperty("season")
    public Integer season;
    @JsonProperty("episode")
    public Integer episode;
    @JsonProperty("totalViewers")
    public String totalViewers;
    @JsonProperty("url")
    public String url;
}
