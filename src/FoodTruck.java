import java.util.Objects;

public class FoodTruck {
    private String truckName;
    private String address;

    public String getTruckName() {
        return truckName;
    }

    public void setTruckName(String truckName) {
        this.truckName = truckName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public FoodTruck(String truckName, String address) {
        this.truckName = truckName;
        this.address = address;
    }

    public FoodTruck() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FoodTruck foodTruck = (FoodTruck) o;
        return truckName.equals(foodTruck.truckName) &&
                address.equals(foodTruck.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(truckName, address);
    }
}