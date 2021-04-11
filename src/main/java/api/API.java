package api;

import lombok.experimental.UtilityClass;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@UtilityClass
public class API {

    private final int MOCK_NUM_DAYS = 10;
    // Change the seed number to be able to test the App with different sequence of numbers (day:price).
    private final int RANDOM_SEED_STATE = 20;

    /**
     * Mock API method: getPriceOnDay
     * Returns the price 'value' for the given day 'key'
     *
     * @param day day to receive the price for
     * @return int: the price
     */
    public int getPriceOnDay(int day) {
        Map<Integer, Integer> priceList = getPriceList();

        return priceList.entrySet()
            .stream()
            .filter(entry -> entry.getKey().equals(day))
            .findFirst()
            .map(Map.Entry::getValue)
            .orElseThrow();
    }

    /**
     * Mock API method: getNumDays
     * Returns the number of days
     *
     * @return int
     */
    public int getNumDays() {
        return MOCK_NUM_DAYS;
    }

    /**
     * Returns a mocked price list, used in getPriceOnDay for mocking the data
     *
     * @return day as key and price as value Map
     */
    private Map<Integer, Integer> getPriceList() {
        Map<Integer, Integer> pricesOnDays = new HashMap<>();
        Random random = new Random(RANDOM_SEED_STATE);
        for (int i = 0; i < getNumDays(); i++) {
            pricesOnDays.put(i, random.nextInt(20));
        }

        return pricesOnDays;
    }
}
