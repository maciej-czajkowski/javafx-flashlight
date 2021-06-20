import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;


public class BatteryEventHandler implements EventHandler<MouseEvent> {
    private boolean batteryIn;
    private Node inCap;
    private Node cap;
    private Node battery;
    final static double translationLength = 8;
    final static double animationDurationMs = 2500;
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
        tCap.setToX(translationLength);
        tCap.setDuration(Duration.millis(animationDurationMs));
        tCap.setNode(cap);
        tCap.setAutoReverse(false);

        TranslateTransition tInCap = new TranslateTransition();
        tInCap.setFromX(0);
        tInCap.setToX(translationLength);
        tInCap.setDuration(Duration.millis(animationDurationMs));
        tInCap.setNode(inCap);
        tInCap.setAutoReverse(false);

        TranslateTransition tBattery = new TranslateTransition();
        tBattery.setFromX(0);
        tBattery.setToX(translationLength);
        tBattery.setDuration(Duration.millis(animationDurationMs));
        tBattery.setDelay(Duration.millis(animationDurationMs));
        tBattery.setNode(battery);
        tBattery.setAutoReverse(false);

        ParallelTransition wholeCap = new ParallelTransition(tCap, tInCap, tBattery);
        wholeCap.play();
    }

    private void animateIn() {
        TranslateTransition tCap = new TranslateTransition();
        tCap.setFromX(translationLength);
        tCap.setToX(0);
        tCap.setDuration(Duration.millis(animationDurationMs));
        tCap.setDelay(Duration.millis(animationDurationMs));
        tCap.setNode(cap);
        tCap.setAutoReverse(false);

        TranslateTransition tInCap = new TranslateTransition();
        tInCap.setFromX(translationLength);
        tInCap.setToX(0);
        tInCap.setDuration(Duration.millis(animationDurationMs));
        tInCap.setDelay(Duration.millis(animationDurationMs));
        tInCap.setNode(inCap);
        tInCap.setAutoReverse(false);

        TranslateTransition tBattery = new TranslateTransition();
        tBattery.setFromX(translationLength);
        tBattery.setToX(0);
        tBattery.setDuration(Duration.millis(animationDurationMs));
        tBattery.setNode(battery);
        tBattery.setAutoReverse(false);

        ParallelTransition wholeCap = new ParallelTransition(tCap, tInCap, tBattery);
        wholeCap.setOnFinished(e -> batteryIn = true);

        wholeCap.play();
    }

}
