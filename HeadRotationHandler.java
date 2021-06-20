import javafx.event.EventHandler;
import javafx.scene.LightBase;
import javafx.scene.Node;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

import java.util.ArrayList;
import java.util.Arrays;


public class HeadRotationHandler implements EventHandler<KeyEvent> {
    private final static double MAX_ROTATION_NUMBER = 10;
    private final static double MIN_ROTATION_NUMBER = 0;
    private final static double ROTATION_ANGLE = 30;
    private final static double LIGHT_MOVE = 10;
    private final Rotate ROTATE_RIGHT = new Rotate(ROTATION_ANGLE, 0, 0, 0, Rotate.Z_AXIS);
    private final Rotate ROTATE_LEFT = new Rotate(-ROTATION_ANGLE, 0, 0, 0, Rotate.Z_AXIS);

    private LightBase pointLight;
    private ArrayList<Node> head;

    private int value = 0;
    private ArrayList<Double> lightHue = new ArrayList<>(Arrays.asList(1.0, 0.99, 0.98, 0.97, 0.96, 0.95, 0.94, 0.93, 0.92, 0.91, 0.9));

    public HeadRotationHandler(LightBase pointLight, ArrayList<Node> head) {
        this.pointLight = pointLight;
        this.head = head;
    }

    @Override
    public void handle(KeyEvent keyEvent) {
        double xPos = ((Translate) pointLight.getTransforms().get(0)).getX();
        double yPos = ((Translate) pointLight.getTransforms().get(0)).getY();

        switch (keyEvent.getCode()) {
            case RIGHT:
                if (value < MAX_ROTATION_NUMBER) {
                    value++;
                    for (Node node : head
                    ) {
                        node.getTransforms().addAll(ROTATE_RIGHT);
                    }
                    pointLight.getTransforms().remove(0);
                    pointLight.getTransforms().add(new Translate(xPos + LIGHT_MOVE, yPos, 0));
                }
                pointLight.setColor(new Color(lightHue.get(value), lightHue.get(value), lightHue.get(value), 1));
//                System.out.println(pointLight.getColor());
                System.out.println("ROTATE RIGHT | " + value);
                break;
            case LEFT:
                if (value > MIN_ROTATION_NUMBER) {
                    value--;
                    for (Node node : head
                    ) {
                        node.getTransforms().addAll(ROTATE_LEFT);
                    }
                    pointLight.getTransforms().remove(0);
                    pointLight.getTransforms().add(new Translate(xPos - LIGHT_MOVE, yPos, 0));
                }
                pointLight.setColor(new Color(lightHue.get(value), lightHue.get(value), lightHue.get(value), 1));
//                System.out.println(pointLight.getColor());
                System.out.println("ROTATE LEFT | " + value);
                break;
        }

        keyEvent.consume();
    }
}
