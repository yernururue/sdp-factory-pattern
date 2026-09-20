package application;

import abstractfactory.Button;
import abstractfactory.Checkbox;
import abstractfactory.GUIFactory;
import factorymethod.Logistics;

import java.util.Objects;

public class DeliveryApplication {
    private final Logistics logistics;
    private final Button button;
    private final Checkbox checkbox;

    public DeliveryApplication(GUIFactory guiFactory, Logistics logistics) {
        GUIFactory requiredFactory = Objects.requireNonNull(guiFactory, "GUI factory must not be null");
        this.logistics = Objects.requireNonNull(logistics, "Logistics must not be null");
        this.button = requiredFactory.createButton();
        this.checkbox = requiredFactory.createCheckbox();
    }

    public void run(String cargo, String destination) {
        renderInterface();
        logistics.planDelivery(cargo, destination);
    }

    private void renderInterface() {
        button.paint();
        checkbox.paint();
    }
}
