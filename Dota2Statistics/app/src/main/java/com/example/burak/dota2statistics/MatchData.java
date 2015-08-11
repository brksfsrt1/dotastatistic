package com.example.burak.dota2statistics;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by burak on 08.08.2015.
 */
public class MatchData{

    private String heroImageUrl;
    private String heroName;
    private String date;
    private String matchId;


    public MatchData(){

    }

    //takes unix time stamp and calculates the widely-used format
    public void setDate(String unixTimeStamp){

        int unixSeconds = Integer.parseInt(unixTimeStamp);
        Date matchDate = new Date(unixSeconds*1000L);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:s z");
        sdf.setTimeZone(TimeZone.getDefault());
        date = sdf.format(matchDate);

    }

    public void setHeroImageUrl(String hero){
        this.heroImageUrl = hero;
    }

    public void setMatchId(String matchId){this.matchId = matchId;}

    public void setHeroName(String heroName) {this.heroName = heroName; }

    public String getHeroName() { return heroName; }

    public String getHeroImageUrl(){
        return heroImageUrl;
    }

    public String getDate(){
        return date;
    }

    public String getMatchId(){
        return matchId;
    }

}
