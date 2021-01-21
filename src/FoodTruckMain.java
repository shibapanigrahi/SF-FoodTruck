import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.TreeMap;

import static java.lang.System.exit;

public class FoodTruckMain {

    public static void main(String[] args) {
        try {
            //Get the JSON data from the API and convert to JSONArray
            FoodService service = new FoodService();
            JSONArray jsonArray = new JSONArray(service.getFoodServiceData());

            //Header
            System.out.println("Current foodtrucks opened now in San Francisco");
            System.out.println("**************************************************");
            String specifiers = "%-90s %-30s %n";
            System.out.format(specifiers, "NAME", "ADDRESS");


            //Data storage for sorting and storing the values to be displayed
            List<List<FoodTruck>> foodTruckList = new ArrayList<>();
            final int limit = 10;

            TreeMap<String, FoodTruck> map = service.filterFoodTruckData(jsonArray, service.getCurrentTime(), service.getCurrentDay());

            foodTruckList = service.splitValues(map, foodTruckList, limit);

            //If there are less than 10 values just display one page
            if (foodTruckList.size() == 1) {
                service.printFoodTruckDetails(foodTruckList, 1, specifiers);
            } else if (foodTruckList.size() > 1) {
                //Print the default first page
                service.printFoodTruckDetails(foodTruckList, 1, specifiers);
                int selectedOption = 0;
                int page = 1;
                System.out.println("");
                System.out.println("Current Section: " + page);

                while (true) {
                    //Get the input from user for the page number
                    System.out.println("");
                    System.out.println("Press (1) For Next page");
                    System.out.println("Press (2) For Previous page");
                    System.out.println("Press (3) For Exit");
                    Scanner sc = new Scanner(System.in);
                    selectedOption = sc.nextInt();

                    switch (selectedOption) {
                        case 1:
                            if (page < foodTruckList.size()) {
                                page++;
                            }
                            if (page == foodTruckList.size()) {
                                System.out.println("This is the last page");
                            }

                            System.out.format(specifiers, "NAME", "ADDRESS");
                            service.printFoodTruckDetails(foodTruckList, page, specifiers);
                            System.out.println("");
                            System.out.println("Current Page: " + page);
                            break;
                        case 2:
                            if (page > 1) {
                                page--;
                            }
                            if (page == 1) {
                                System.out.println("This is the first page");
                            }

                            System.out.format(specifiers, "NAME", "ADDRESS");
                            service.printFoodTruckDetails(foodTruckList, page, specifiers);
                            System.out.println("");
                            System.out.println("Current Page: " + page);
                            break;
                        case 3:
                            exit(0);
                        default:
                            System.out.println("Please select the correct selectedOption.");
                    }
                }
            } else {
                System.out.println("Sorry! No food trucks are open at this time.");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}