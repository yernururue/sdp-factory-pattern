import factorymethod.Ship;
import factorymethod.Transport;
import factorymethod.Truck;

public class Main {
    public static void main(String[] args) {
        Transport truck = new Truck();
        Transport ship = new Ship();

        truck.deliver("laboratory equipment", "Astana warehouse");
        ship.deliver("construction materials", "Aktau port");
    }
}