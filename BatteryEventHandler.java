import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;


public class BatteryEventHandler implements EventHandler<MouseEvent> {
    private final static double TRANSLATION_LENGTH = 8;
    private final static double ANIMATION_DURATION_MS = 2500;
    private boolean batteryIn;
    private Node inCap;
    private Node cap;
    private Node battery;
    private ButtonEventHandler buttonHandler;

    public BatteryEventHandler() {
    }

    public BatteryEventHandler(Node batteryCapInside, Node batteryCap, Node battery, ButtonEventHandler  buttonEventHandler) {
        this.batteryIn = true;
        this.cap = batteryCap;
        this.inCap = batteryCapInside;
        this.battery = battery;
        this.buttonHandler = buttonEventHandler;
    }

    public boolean isBatteryIn() {
        return batteryIn;
    }

    @Override
    public void handle(MouseEvent event) {
        System.out.println("Battery out + " + batteryIn);
        if (batteryIn) {
            animateOut();
            buttonHandler.turnOff();
            batteryIn = false;

        } else {
            animateIn();
//            batteryIn = true;
        }
    }

    private void animateOut() {
        TranslateTransition tCap = new TranslateTransition();
        tCap.setFromX(0);
        tCap.setToX(TRANSLATION_LENGTH);
        tCap.setDuration(Duration.millis(ANIMATION_DURATION_MS));
        tCap.setNode(cap);
        tCap.setAutoReverse(false);

        TranslateTransition tInCap = new TranslateTransition();
        tInCap.setFromX(0);
        tInCap.setToX(TRANSLATION_LENGTH);
        tInCap.setDuration(Duration.millis(ANIMATION_DURATION_MS));
        tInCap.setNode(inCap);
        tInCap.setAutoReverse(false);

        TranslateTransition tBattery = new TranslateTransition();
        tBattery.setFromX(0);
        tBattery.setToX(TRANSLATION_LENGTH);
        tBattery.setDuration(Duration.millis(ANIMATION_DURATION_MS));
        tBattery.setDelay(Duration.millis(ANIMATION_DURATION_MS));
        tBattery.setNode(battery);
        tBattery.setAutoReverse(false);

        ParallelTransition wholeCap = new ParallelTransition(tCap, tInCap, tBattery);
        wholeCap.play();
    }

    private void animateIn() {
        TranslateTransition tCap = new TranslateTransition();
        tCap.setFromX(TRANSLATION_LENGTH);
        tCap.setToX(0);
        tCap.setDuration(Duration.millis(ANIMATION_DURATION_MS));
        tCap.setDelay(Duration.millis(ANIMATION_DURATION_MS));
        tCap.setNode(cap);
        tCap.setAutoReverse(false);

        TranslateTransition tInCap = new TranslateTransition();
        tInCap.setFromX(TRANSLATION_LENGTH);
        tInCap.setToX(0);
        tInCap.setDuration(Duration.millis(ANIMATION_DURATION_MS));
        tInCap.setDelay(Duration.millis(ANIMATION_DURATION_MS));
        tInCap.setNode(inCap);
        tInCap.setAutoReverse(false);

        TranslateTransition tBattery = new TranslateTransition();
        tBattery.setFromX(TRANSLATION_LENGTH);
        tBattery.setToX(0);
        tBattery.setDuration(Duration.millis(ANIMATION_DURATION_MS));
        tBattery.setNode(battery);
        tBattery.setAutoReverse(false);

        ParallelTransition wholeCap = new ParallelTransition(tCap, tInCap, tBattery);
        wholeCap.setOnFinished(e -> batteryIn = true);

        wholeCap.play();
    }

}
