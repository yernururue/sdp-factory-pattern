package factorymethod;

public class Ship implements Transport {
    @Override
    public void deliver(String cargo, String destination) {
        System.out.printf("Ship delivers %s by sea to %s%n", cargo, destination);
    }
}
