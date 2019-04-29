package CachedPages;


import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class CachePagesManager{
   Queue<CachedPageModel> cachedPagesQueue;
   int limit;

   public CachePagesManager(int limit){
       cachedPagesQueue= new LinkedList<>();
       this.limit = limit;
   }

   public CachedPageModel isCached(String url){

       for(CachedPageModel c: this.cachedPagesQueue){
           if(c.url.equals(url)){
               return c;
           }
       }
       return null;
   }


   public void addCachedPage(CachedPageModel cachedPage){
       this.cachedPagesQueue.add(cachedPage);
       while(this.cachedPagesQueue.size() > limit) { this.cachedPagesQueue.remove();}
   }


    @Override
    public String toString() {
        String result = "";
        for(CachedPageModel c: this.cachedPagesQueue){
            result += c + "\n";
        }
        return result;
    }
}
