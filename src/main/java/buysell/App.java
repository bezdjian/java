package buysell;

import api.API;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class App {
    public static void main(String[] args) {
        System.out.println("getBuyDay(): " + getBuyDay());
        System.out.println("getSellDay(): " + getSellDay());
    }

    public static int getBuyDay() {
        int numDays = API.getNumDays();
        Map<Integer, Integer> priceForGivenDays = getPricesForGivenDays(numDays);
        //Find the lowest value 'price' and return key 'day'
        return priceForGivenDays.entrySet()
            .stream()
            .min(Map.Entry.comparingByValue())
            .map(Map.Entry::getKey)
            .orElseThrow();
    }

    public static int getSellDay() {
        // Get the buy day to find the best sell day afterwards.
        int buyDay = getBuyDay();
        int numDay = API.getNumDays();
        Map<Integer, Integer> pricesForGivenDays = getPricesForGivenDays(numDay);

        //Display the days with prices in the console along with buy and sell days.
        System.out.println("Gold prices for each day fetched from API: " + pricesForGivenDays);

        int sellingPrice = getSellingPriceAfterBuyDay(buyDay, pricesForGivenDays);
        return getSellDay(pricesForGivenDays, sellingPrice);
    }

    private static Integer getSellDay(Map<Integer, Integer> pricesForGivenDays, int sellDayValue) {
        // Get the key 'day' by the highest value to sell
        return pricesForGivenDays.entrySet()
            .stream()
            .filter(value -> value.getValue().equals(sellDayValue))
            .map(Map.Entry::getKey)
            .findFirst()
            .orElse(0);
    }

    private static int getSellingPriceAfterBuyDay(int buyDay, Map<Integer, Integer> pricesForGivenDays) {
        int sellingPrice = 0;
        int lastDayOfThePriceList = pricesForGivenDays.size();
        // Loop from the buy day until the last day in the list
        // to look for the highest value to sell
        for (int i = buyDay; i < lastDayOfThePriceList; i++) {
            int possibleSellingPrice = pricesForGivenDays.get(i);
            if (possibleSellingPrice > sellingPrice)
                sellingPrice = possibleSellingPrice;
        }

        return sellingPrice;
    }

    private static Map<Integer, Integer> getPricesForGivenDays(int numDays) {
        Map<Integer, Integer> priceForGivenDays = new HashMap<>();
        try {
            for (int day = 0; day < numDays; day++) {
                int priceOnDay = API.getPriceOnDay(day);
                // Add days with prices from API in a hashMap to be able to calculate the buy and sell days.
                priceForGivenDays.put(day, priceOnDay);
            }
        } catch (NoSuchElementException e) {
            e.printStackTrace();
        }

        return priceForGivenDays;
    }
}
