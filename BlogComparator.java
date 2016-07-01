import java.util.*;
public class BlogComparator implements Comparator<Blog>{
    //Comparator for Blog order by postTime
    public int compare(Blog a, Blog b){
        return b.getPostTime().compareTo(a.getPostTime());
    }
}
