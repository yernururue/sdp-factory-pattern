# Assignment 2: Factory Method and Abstract Factory

**Student:** TODO: Full name  
**Group:** TODO: Group  
**GitHub repository:** TODO: Repository URL  
**Submitted commit:** TODO: Final commit hash

## 1. Introduction

This project is a Java 17 console logistics application. At runtime, the user independently selects a delivery mode (`ROAD` or `SEA`) and a UI platform (`WINDOWS` or `MACOS`). The application renders a matching button and checkbox and then delivers sample cargo to a destination.

Factory Method fits transport creation because `Logistics` defines one shared delivery workflow while its subclasses decide which member of the `Transport` hierarchy to instantiate. Abstract Factory fits the UI because a platform is a family of related products: choosing one factory must produce both a button and a checkbox from the same family.

A startup `switch` is used only as the composition root that selects concrete creators and factories from external input. Product creation and application behavior still use abstract contracts.

## 2. UML diagrams

### 2.1 Factory Method

Diagram source: [`factory-method.puml`](factory-method.puml).

The `Transport` product contract is implemented by the concrete products `Truck` and `Ship`. The abstract creator `Logistics` declares `createTransport()` and owns `planDelivery()`. `RoadLogistics` and `SeaLogistics` override the factory method. `DeliveryApplication` depends on the `Logistics` abstraction.

TODO: Export the PlantUML diagram as a readable image and insert it in the final PDF.

### 2.2 Abstract Factory

Diagram source: [`abstract-factory.puml`](abstract-factory.puml).

`Button` and `Checkbox` are abstract products. Windows and macOS each provide a complete family of concrete products. `GUIFactory` declares both creation methods; `WindowsFactory` and `MacOSFactory` implement them. `DeliveryApplication` receives the factory through its constructor and uses only product interfaces.

TODO: Export the PlantUML diagram as a readable image and insert it in the final PDF.

## 3. Clean Code evidence

### 3.1 Meaningful names

```java
public interface Transport {
    void deliver(String cargo, String destination);
}

public class RoadLogistics extends Logistics {
    @Override
    public Transport createTransport() {
        return new Truck();
    }
}

public class SeaLogistics extends Logistics {
    @Override
    public Transport createTransport() {
        return new Ship();
    }
}
```

The names identify the domain and pattern responsibilities. `Transport` describes the product contract, while `RoadLogistics` and `SeaLogistics` clearly distinguish the concrete creators. This makes the UML roles easy to locate in the implementation.

### 3.2 Small methods and single responsibilities

```java
private static String normalize(String value) {
    return value.trim().toUpperCase(Locale.ROOT);
}

private static void printUsage() {
    System.out.println("Missing or incorrect input.");
    System.out.println("Usage: java Main <ROAD|SEA> <WINDOWS|MACOS>");
}
```

Startup selection, normalization, and error output are separate methods. A reader can understand or modify one responsibility without navigating one long `main()` method.

### 3.3 Avoid duplicated workflow logic

```java
public void planDelivery(String cargo, String destination) {
    Transport transport = createTransport();
    transport.deliver(cargo, destination);
}
```

The delivery workflow exists once in `Logistics`. Concrete creators only decide which transport to create, so `RoadLogistics` and `SeaLogistics` do not duplicate cargo and destination handling.

### 3.4 Chapter 6: data abstraction

```java
private final Logistics logistics;
private final Button button;
private final Checkbox checkbox;
```

`DeliveryApplication` stores abstractions instead of `Truck`, `Ship`, or platform-specific controls. In the terms of Clean Code Chapter 6, callers interact with behavior exposed by contracts rather than depending on concrete representation or product internals. The client therefore requires no casts or `instanceof` checks.

### 3.5 Chapter 6: objects and encapsulation

```java
private void renderInterface() {
    button.paint();
    checkbox.paint();
}
```

The UI objects expose behavior through `paint()` rather than exposing mutable data for the client to inspect. State in `DeliveryApplication` is private and final, and unnecessary getters are avoided. This follows Chapter 6's object-oriented approach: an object tells its collaborators to perform behavior while keeping representation encapsulated.

## 4. Verification evidence

The project was compiled with:

```bash
javac --release 17 -d out src/Main.java src/application/*.java src/factorymethod/*.java src/abstractfactory/*.java
```

| Check | Input | Actual result | Status |
|---|---|---|---|
| 1 | `ROAD WINDOWS` | Windows button and checkbox rendered; Truck delivered laboratory equipment by road to Aktau warehouse. | PASS |
| 2 | `SEA WINDOWS` | Windows button and checkbox rendered; Ship delivered laboratory equipment by sea to Aktau warehouse. | PASS |
| 3 | `ROAD MACOS` | macOS button and checkbox rendered; Truck delivered laboratory equipment by road to Aktau warehouse. | PASS |
| 4 | `SEA MACOS` | macOS button and checkbox rendered; Ship delivered laboratory equipment by sea to Aktau warehouse. | PASS |
| 5 | `AIR WINDOWS` | Printed `Unsupported delivery mode: AIR` and the supported modes; no UI rendering or delivery occurred. | PASS |
| 6 | `ROAD LINUX` | Printed `Unsupported UI platform: LINUX` and the supported platforms; no UI rendering or delivery occurred. | PASS |
| Missing input | no arguments | Printed a missing/incorrect input message and usage; stopped cleanly. | PASS |

TODO: Add readable terminal screenshots or complete transcripts to the final PDF if requested by the instructor.

## 5. Pattern comparison and design reflection

### 5.1 Simple Factory and Factory Method

A Simple Factory commonly uses one method with an `if` or `switch` to directly create every product. Factory Method moves that creation decision to subclass overrides while all products share one contract. In this project, `RoadLogistics.createTransport()` returns a `Truck`, and `SeaLogistics.createTransport()` returns a `Ship`. The inherited `planDelivery()` workflow remains unchanged.

The startup switches do not replace Factory Method. They select the application-level `Logistics` creator and `GUIFactory` according to external input. After startup, delivery and rendering contain no branches on concrete products.

### 5.2 Factory Method and Abstract Factory

Factory Method creates one product from a hierarchy and allows subclasses to choose its concrete type. It is used here for the `Transport` hierarchy. Abstract Factory creates a family of related products and preserves family consistency. It is used for buttons and checkboxes, ensuring that one execution receives either a complete Windows pair or a complete macOS pair.

### 5.3 Adding another transport

To add air delivery, create `Airplane implements Transport` and `AirLogistics extends Logistics`, whose `createTransport()` returns an `Airplane`. Add `AIR` to the startup selection. `Logistics.planDelivery()` and `DeliveryApplication` remain unchanged because they use the existing abstractions.

### 5.4 Adding another UI family

To add Linux UI, create `LinuxButton`, `LinuxCheckbox`, and `LinuxFactory`. Add `LINUX` to startup selection. `GUIFactory`, `Button`, `Checkbox`, and the rendering logic in `DeliveryApplication` remain unchanged.

### 5.5 Adding another UI product type

To add a text field, define a `TextField` interface and one implementation for every platform. Add `createTextField()` to `GUIFactory`, then implement it in `WindowsFactory`, `MacOSFactory`, and every future family factory. If the application must render the text field, `DeliveryApplication` also needs a `TextField` field and a `paint()` call. Transport-related code remains unchanged. This extension shows the Abstract Factory trade-off: adding a family is straightforward, but adding a new product type affects every family.

## 6. Conclusion

The application combines two creational patterns without mixing their responsibilities. Factory Method provides an extensible transport workflow, while Abstract Factory creates consistent platform component families. Constructor injection allows `DeliveryApplication` to coordinate both patterns through abstractions. Validation is isolated at startup, and unsupported or missing choices stop cleanly.

## References

1. Course Lecture 2, *Factory Method and Abstract Factory*, ShP-2216 Software Design Patterns.
2. Eric Freeman and Elisabeth Robson, *Head First Design Patterns*, Chapter 4.
3. Robert C. Martin, *Clean Code*, Chapter 6: Objects and Data Structures.
4. Software Design Patterns syllabus 2026-2027 and Moodle course calendar.

## Final submission checklist

- [ ] Replace all `TODO` identification fields.
- [ ] Add the accessible GitHub repository URL.
- [ ] Record the final submitted commit hash.
- [ ] Render and insert both UML diagrams into the report.
- [ ] Add screenshots/transcripts if required.
- [ ] Export as `Assignment2_Group_Surname_Name.pdf`.
- [ ] Reopen the PDF and verify readability.
- [ ] Run the project once using the README instructions.
