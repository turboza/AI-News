import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class Filter implements Serializable{
    private Date begin=null;
    private Date end=null;
    private String word=null;
    
    Filter(String word){
    	//this.begin = วันนี้
    	this.word = word;
    }
    
    Filter(Date begin,Date end, String word) {
        this.begin = begin;
        this.end = end;
        this.word = word;
    }
    
    public Date getBegin(){
        return begin;
    }
    public Date getEnd(){
        return end;
    }
    public String getWord(){
        return word;
    }
}
