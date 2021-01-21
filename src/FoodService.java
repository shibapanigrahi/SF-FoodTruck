import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class FoodService {

    private final String SF_FOOD_SERVICE_URL = "http://data.sfgov.org/resource/bbb8-hzi6.json";
    DateFormat dateFormat = new SimpleDateFormat("HH:mm");

    /**
     * Call the Food Service URL and return in string
     * @return
     * @throws IOException
     */
    public String getFoodServiceData() throws IOException {
        StringBuilder result = new StringBuilder();
        URL url = new URL(SF_FOOD_SERVICE_URL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        try (
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()))
        ) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                result.append(line);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return result.toString();
    }

    /**
     * Get the current time using calender API
     * @return
     * @throws ParseException
     */
    public Date getCurrentTime() throws ParseException {
        Calendar calendar = Calendar.getInstance();
        String d = dateFormat.format(calendar.getTime());
        return dateFormat.parse(d);
    }

    /**
     * return current date
     * @return
     */
    public String getCurrentDay() {
        String days = new SimpleDateFormat("EEEE").format(new Date());
        return days;
    }

    /**
     *
     * Method filter the json data from the FoodService map
     * @param jsonArray
     * @param currentTime
     * @param days
     * @return
     * @throws JSONException
     * @throws ParseException
     */
    public TreeMap<String, FoodTruck> filterFoodTruckData(JSONArray jsonArray,
                                                          Date currentTime, String days)
            throws JSONException, ParseException {

        TreeMap<String, FoodTruck> map = new TreeMap<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject object = (JSONObject) jsonArray.get(i);

            String day = object.getString("dayofweekstr");
            Date endTime = dateFormat.parse(object.getString("end24"));
            Date startTime = dateFormat.parse(object.getString("start24"));
            long startAfter = currentTime.getTime() - startTime.getTime();
            long endBefore = endTime.getTime() - currentTime.getTime();
            if (startAfter >= 0 && endBefore >= 0 && days.equalsIgnoreCase(day)) {
                FoodTruck fT = new FoodTruck();
                fT.setTruckName(object.getString("applicant"));
                fT.setAddress(object.getString("location"));
                map.put(fT.getTruckName(), fT);
            }
        }

        return map;
    }

    public List<List<FoodTruck>> splitValues(Map<String, FoodTruck> truckMap, List<List<FoodTruck>> foodTruckList, int limit) {
        for (Map.Entry<String, FoodTruck> food : truckMap.entrySet()) {
            if (foodTruckList.size() == 0 || foodTruckList.get(foodTruckList.size() - 1).size() >= limit) {
                List<FoodTruck> foodTruck = new ArrayList<>();
                foodTruck.add(food.getValue());
                foodTruckList.add(foodTruck);
            } else if (foodTruckList.get(foodTruckList.size() - 1).size() < limit) {
                foodTruckList.get(foodTruckList.size() - 1).add(food.getValue());
            }
        }
        return foodTruckList;
    }

    /**
     * Print food truck entries
     * @param foodTruckList
     * @param index
     * @param specifiers
     */
    public void printFoodTruckDetails(List<List<FoodTruck>> foodTruckList, int index, String specifiers) {
        for (FoodTruck food : foodTruckList.get(index - 1)) {
            System.out.format(specifiers, food.getTruckName(), food.getAddress());
        }
    }
}