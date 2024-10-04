package extra;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class Quotes {
    protected List<String> quotes = new ArrayList<>();
    Random rd = new Random();
    
    public String getRandomQuote() {
//        int i = random.nextInt(this.quotes.length);   
        if (this.quotes.isEmpty()){
            return "Unfortunately there are no quotes available for you :(";
        }
        int i = rd.nextInt(this.quotes.size());
                
        return this.quotes.get(i);
    }
}
