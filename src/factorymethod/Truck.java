package factorymethod;

public class Truck implements Transport {
    @Override
    public void deliver(String cargo, String destination) {
        System.out.printf("Truck delivers %s by road to %s%n", cargo, destination);
    }
}
