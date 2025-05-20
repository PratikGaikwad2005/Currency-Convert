package currencyConvert;

import java.util.HashMap;
import java.util.Map;

public class CurrencyRates {
	private static final Map<String, Double> rates = new HashMap<String, Double>();
    static {
        rates.put("USD", 1.0);
        rates.put("EUR", 0.93);
        rates.put("INR", 83.12);
        rates.put("JPY", 155.13);
        rates.put("GBP", 0.80);
    }
    
    /** 
     * Get the rate of the given currency relative to USD.
     * If not found, returns 1.0 (i.e. assumes USD).
     */
    public static double getRate(String currencyCode) {
        Double r = rates.get(currencyCode);
        return (r != null ? r : 1.0);
    }
    
    /** List of supported currency codes */
    public static String[] getCurrencies() {
        return rates.keySet().toArray(new String[rates.size()]);
    }
}
