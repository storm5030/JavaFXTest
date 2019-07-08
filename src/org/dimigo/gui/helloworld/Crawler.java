package org.dimigo.gui.helloworld;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.List;


public class Crawler {
   //public static void main(String[] args) {
   @SuppressWarnings("unchecked")
   public static List<String> getRankList(String type, String conference) throws Exception {
       Elements elements = new Elements();
       String link;
       String id;
       //System.out.println(doc.html());
       if("Team".equals(type)){
           if("EAST".equals(conference)) {
               link = "https://sports.news.naver.com/basketball/record/index.nhn?category=nba&year=2019&conference=EAST";
           } else if("WEST".equals(conference)) {
               link = "https://sports.news.naver.com/basketball/record/index.nhn?category=nba&year=2019&conference=WEST";
           } else{
               link = "https://sports.news.naver.com/basketball/record/index.nhn?category=nba";
           }
           Document doc = Jsoup.connect(link).get();
           id = "[id=regularTeamRecordList_table] tr [class=tm] div span";
           elements = doc.select(id);
       }else if("Player".equals(type)){
           link = "https://sports.news.naver.com/basketball/record/index.nhn?category=nba";
           Document doc = Jsoup.connect(link).get();
           id = "[id=playerRecordTable] tr [class=ply] div span";
           elements = doc.select(id);
       }
       List<String> keywords = elements.eachText();

       for (int i = 0; i < keywords.size(); i++) {
           System.out.print((i + 1) + ". " + keywords.get(i) + " | ");
       }

       return keywords;
   }
}
