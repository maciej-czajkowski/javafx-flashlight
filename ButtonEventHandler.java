import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.LightBase;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

public class ButtonEventHandler implements EventHandler<MouseEvent> {
    private enum LightSequence {
        OFF,
        ON,
        BLINK,
    }

    final static double animationDurationMs = 500;
    final static double translationLength = 0.25;
    final static double blinkDurationMs = 100;
    private BatteryEventHandler batteryHandler;

    private Group group;
    private Node button;
    private LightBase pointLight;
    private LightSequence lightSequence = LightSequence.ON;
    private Timeline blink = new Timeline(
            new KeyFrame(Duration.millis(blinkDurationMs),
                    new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            if (group.getChildren().contains(pointLight)) {
                                group.getChildren().remove(pointLight);
                            } else {
                                group.getChildren().add(pointLight);
                            }
                        }
                    }));

    public ButtonEventHandler(Group group, Node button, LightBase pointLight, BatteryEventHandler batteryHandler) {
        this.group = group;
        this.button = button;
        this.pointLight = pointLight;
        this.blink.setCycleCount(Timeline.INDEFINITE);
        this.batteryHandler = batteryHandler;
    }

    public void turnOff() {
        lightSequence = lightSequence.OFF;
        blink.stop();
        setLightOff();
    }
//    public ButtonEventHandler(Group group, Shape3D button, LightBase pointLight) {
//        this.group = group;
//        this.button = button;
//        this.pointLight = pointLight;
//        this.blink.setCycleCount(Timeline.INDEFINITE);
//    }

    public void setBatteryHandler(BatteryEventHandler batteryHandler) {
        this.batteryHandler = batteryHandler;
    }

    @Override
    public void handle(MouseEvent mouseEvent) {
        System.out.println(mouseEvent.getEventType());
        animateClick();
        if (batteryHandler.isBatteryIn()) {
            switch (lightSequence) {
                case OFF:
                    lightSequence = LightSequence.ON;
                    setLightOn();
                    break;
                case ON:
                    lightSequence = lightSequence.BLINK;
                    setLightBlink();
                    break;
                case BLINK:
                    lightSequence = lightSequence.OFF;
                    blink.stop();
                    setLightOff();
                    break;
            }
        }
        mouseEvent.consume();
    }

    private void animateClick() {
        TranslateTransition tt = new TranslateTransition();
        tt.setFromY(translationLength);
        tt.setToY(0);
        tt.setDuration(Duration.millis(animationDurationMs));
        tt.setNode(button);
        tt.setAutoReverse(false);
        tt.play();
    }

    private void setLightOff() {
        System.out.println("Seting light to OFF");

        if (group.getChildren().contains(pointLight)) {
            group.getChildren().remove(pointLight);
        }
    }

    private void setLightOn() {
        System.out.println("Seting light to ON");
        if (!group.getChildren().contains(pointLight)) {
            group.getChildren().add(pointLight);
        }
    }

    private void setLightBlink() {
        System.out.println("Seting light to BLINK");
        blink.setCycleCount(Timeline.INDEFINITE);
        blink.play();
    }
}
