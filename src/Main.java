import abstractfactory.GUIFactory;
import abstractfactory.MacOSFactory;
import abstractfactory.WindowsFactory;
import application.DeliveryApplication;
import factorymethod.Logistics;
import factorymethod.RoadLogistics;
import factorymethod.SeaLogistics;

import java.util.Locale;

public class Main {
    private static final String SAMPLE_CARGO = "laboratory equipment";
    private static final String SAMPLE_DESTINATION = "Aktau warehouse";

    public static void main(String[] args) {
        if (args.length != 2) {
            printUsage();
            return;
        }

        String deliveryMode = normalize(args[0]);
        String uiPlatform = normalize(args[1]);
        Logistics logistics = selectLogistics(deliveryMode);
        GUIFactory guiFactory = selectGUIFactory(uiPlatform);

        if (logistics == null || guiFactory == null) {
            return;
        }

        System.out.println("Delivery mode: " + deliveryMode);
        System.out.println("UI platform: " + uiPlatform);

        DeliveryApplication application = new DeliveryApplication(guiFactory, logistics);
        application.run(SAMPLE_CARGO, SAMPLE_DESTINATION);
    }

    private static Logistics selectLogistics(String deliveryMode) {
        return switch (deliveryMode) {
            case "ROAD" -> new RoadLogistics();
            case "SEA" -> new SeaLogistics();
            default -> {
                System.out.println("Unsupported delivery mode: " + deliveryMode);
                System.out.println("Supported delivery modes: ROAD, SEA");
                yield null;
            }
        };
    }

    private static GUIFactory selectGUIFactory(String uiPlatform) {
        return switch (uiPlatform) {
            case "WINDOWS" -> new WindowsFactory();
            case "MACOS" -> new MacOSFactory();
            default -> {
                System.out.println("Unsupported UI platform: " + uiPlatform);
                System.out.println("Supported UI platforms: WINDOWS, MACOS");
                yield null;
            }
        };
    }

    private static String normalize(String value) {
        return value.trim().toUpperCase(Locale.ROOT);
    }

    private static void printUsage() {
        System.out.println("Missing or incorrect input.");
        System.out.println("Usage: java Main <ROAD|SEA> <WINDOWS|MACOS>");
    }
}