import java.text.*;
import java.util.*;
public class BlogStdTime{
    public BlogStdTime(){
    }
    //for getting blogStdTime
    //this project use following time format as stander
    public String toString(){
        DateFormat dt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        return dt.format(new Date());
    }
}
