package ml.melun.mangaview.mangaview;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Title {
    public Title(String n, String t, String a, List<String> tg) {
        name = n;
        thumb = t;
        author = a;
        tags = tg;
    }
    public String getName() {
        return name;
    }
    public String getThumb() {
        return thumb;
    }
    public ArrayList<Manga> getEps(){
        return eps;
    }
    public void fetchEps() {
        //fetch episodes
        try {
            eps = new ArrayList<>();
            Document items = Jsoup.connect("https://mangashow.me/bbs/page.php?hid=manga_detail&manga_name="+name)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36")
                    .get();
            for(Element e:items.select("div.slot")) {
                eps.add(new Manga(Integer.parseInt(e.attr("data-wrid")),e.selectFirst("div.title").text()));
            }
            if(thumb.length()==0){
                thumb = items.selectFirst("div.manga-thumbnail").attr("style").split("\\(")[1].split("\\)")[0];
                System.out.println("ppppp"+thumb);
            }
            if(author.length()==0){
                author = items.selectFirst("a.author").text();
            }
            if(tags.size()==0){
                for(Element e:items.selectFirst("div.manga-tags").select("a.tag")){
                    tags.add(e.text());
                }
            }

        }catch(Exception e) {
            e.printStackTrace();
        }
    }
    public String getAuthor(){
        if(author==null) return "";
        return author;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public int getEpsCount(){ return eps.size();}
    public int getBookmark(){ return bookmark;}
    public void setBookmark(int id){bookmark = id;}
    public List<String> getTags(){
        if(tags==null) return new ArrayList<>();
        return tags;
    }

    public void setEps(JSONArray list){
        eps = new ArrayList<>();
        for(int i=0; i<list.length(); i++){
            try{
                JSONObject tmp = new JSONObject(list.get(i).toString());
                eps.add(new Manga(tmp.getInt("id"),tmp.getString("name")));
            }catch (Exception e){

            }
        }
    }

    private String name;
    private String thumb;
    private ArrayList<Manga> eps;
    private int bookmark=-1;
    private ArrayList<Integer> viewed;
    String author;
    List<String> tags;
}

