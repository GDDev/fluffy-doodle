package extra;

import java.util.Arrays;

public class GoodQuotes extends Quotes {
    private final String[] goodQuotes = {
        "The best way to predict your future is to create it.",
        "With the new day comes new strength and new thoughts.",
        "Life is 10% what happens to you and 90% how you react to it.",
        "Quality is not an act, it is a habit.",
    };
    
    public GoodQuotes(){
        this.quotes.addAll(Arrays.asList(goodQuotes));
    }
}
