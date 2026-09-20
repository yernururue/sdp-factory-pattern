# Assignment 2: Factory Method and Abstract Factory

A Java 17 console logistics application that combines two creational design patterns:

- **Factory Method** selects road (`Truck`) or sea (`Ship`) transport.
- **Abstract Factory** creates a matching Windows or macOS button and checkbox.

## Package structure

```text
src/
├── Main.java                         # input validation and startup selection
├── application/
│   └── DeliveryApplication.java      # client that combines both patterns
├── factorymethod/
│   ├── Transport.java                # product contract
│   ├── Truck.java                    # concrete road product
│   ├── Ship.java                     # concrete sea product
│   ├── Logistics.java                # abstract creator and shared workflow
│   ├── RoadLogistics.java            # concrete road creator
│   └── SeaLogistics.java             # concrete sea creator
└── abstractfactory/
    ├── Button.java                    # abstract button product
    ├── Checkbox.java                  # abstract checkbox product
    ├── GUIFactory.java                # abstract UI factory
    ├── WindowsButton.java
    ├── WindowsCheckbox.java
    ├── WindowsFactory.java
    ├── MacOSButton.java
    ├── MacOSCheckbox.java
    └── MacOSFactory.java
```

UML sources and the report draft are in [`docs/`](docs/).

## Prerequisites

- JDK 17 or newer
- A terminal that supports standard shell commands, or IntelliJ IDEA

Check the installed Java version:

```bash
java -version
javac -version
```

## Build

Run from the repository root:

```bash
rm -rf out
mkdir out
javac --release 17 -d out src/Main.java src/application/*.java src/factorymethod/*.java src/abstractfactory/*.java
```

## Run

```bash
java -cp out Main <DELIVERY_MODE> <UI_PLATFORM>
```

Supported values are case-insensitive:

- Delivery mode: `ROAD`, `SEA`
- UI platform: `WINDOWS`, `MACOS`

All supported configurations:

```bash
java -cp out Main ROAD WINDOWS
java -cp out Main SEA WINDOWS
java -cp out Main ROAD MACOS
java -cp out Main SEA MACOS
```

## Sample run

```text
$ java -cp out Main ROAD WINDOWS
Delivery mode: ROAD
UI platform: WINDOWS
Rendering Windows button
Rendering Windows checkbox
Truck delivers laboratory equipment by road to Aktau warehouse
```

Invalid or missing values produce a clear message and stop before rendering or delivery:

```text
$ java -cp out Main ROAD LINUX
Unsupported UI platform: LINUX
Supported UI platforms: WINDOWS, MACOS
```

## UML diagrams

- [`docs/factory-method.puml`](docs/factory-method.puml)
- [`docs/abstract-factory.puml`](docs/abstract-factory.puml)

The files can be rendered with a PlantUML plugin in IntelliJ IDEA, VS Code, or an offline PlantUML installation.
