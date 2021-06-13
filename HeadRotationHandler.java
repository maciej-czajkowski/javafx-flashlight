import javafx.event.EventHandler;
import javafx.scene.LightBase;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Shape3D;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;


public class HeadRotationHandler implements EventHandler<KeyEvent> {
    private final static double MAX_ROTATION_NUMBER = 10;
    private final static double MIN_ROTATION_NUMBER = 0;
    private final static double ROTATION_ANGLE = 30;
    private final static double LIGHT_MOVE = 10;
    private final Rotate ROTATE_RIGHT = new Rotate(ROTATION_ANGLE, 0, 0, 0, Rotate.Y_AXIS);
    private final Rotate ROTATE_LEFT = new Rotate(-ROTATION_ANGLE, 0, 0, 0, Rotate.Y_AXIS);

    private LightBase pointLight;
    private Shape3D head;

    private double value = 0;

    public HeadRotationHandler(LightBase pointLight, Shape3D head) {
        this.pointLight = pointLight;
        this.head = head;
    }

    @Override
    public void handle(KeyEvent keyEvent) {
        double xPos = ((Translate) pointLight.getTransforms().get(0)).getX();
        double yPos = ((Translate) pointLight.getTransforms().get(0)).getY();

        switch (keyEvent.getCode()) {
            case RIGHT:
                if ( value < MAX_ROTATION_NUMBER) {
                    value++;
                    head.getTransforms().addAll(ROTATE_RIGHT);
                    pointLight.getTransforms().remove(0);
                    pointLight.getTransforms().add(new Translate(xPos + LIGHT_MOVE, yPos, 0));
                }
                System.out.println("ROTATE RIGHT");
                break;
            case LEFT:
                if ( value > MIN_ROTATION_NUMBER) {
                    value--;
                    head.getTransforms().addAll(ROTATE_LEFT);
                    pointLight.getTransforms().remove(0);
                    pointLight.getTransforms().add(new Translate(xPos - LIGHT_MOVE, yPos, 0));
                }
                System.out.println("ROTATE LEFT");
                break;
        };

        keyEvent.consume();
    }
}
