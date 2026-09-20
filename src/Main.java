import factorymethod.Logistics;
import factorymethod.RoadLogistics;
import factorymethod.SeaLogistics;

public class Main {
    public static void main(String[] args) {
        Logistics roadLogistics = new RoadLogistics();
        Logistics seaLogistics = new SeaLogistics();

        roadLogistics.planDelivery("laboratory equipment", "Astana warehouse");
        seaLogistics.planDelivery("construction materials", "Aktau port");
    }
}