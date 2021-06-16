import com.interactivemesh.jfx.importer.obj.ObjModelImporter;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Point3D;
import javafx.scene.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.MeshView;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;


public class Flashlight extends Application {

    private double mousePosX;
    private double mousePosY;
    private double mouseOldX;
    private double mouseOldY;


    @Override
    public void start(Stage primaryStage) {
        Group sceneRoot = new Group();
        Scene scene = new Scene(sceneRoot, 1600, 800, true, SceneAntialiasing.BALANCED);
        scene.setFill(Color.BLACK);
        PerspectiveCamera camera = new PerspectiveCamera(true);
        camera.setNearClip(0.1);
        camera.setFarClip(10000.0);

        camera.setTranslateZ(-90);
//        camera.setTranslateX(-200);
        scene.setCamera(camera);
        // x + do tylu / - do przodu
        // y + w dól / - w góre
        // z + w prawo / - w lewo

        Box wall = new Box(10, 500, 800);
        wall.getTransforms().add(new Translate(-300, 0, 0));
        PhongMaterial wallMaterial = new PhongMaterial();
        wallMaterial.setDiffuseColor(Color.WHITE);
        wallMaterial.setSpecularColor(Color.ORANGE);
        wall.setMaterial(wallMaterial);

        ArrayList<String> patternFaceF = new ArrayList<String>(Arrays.asList("przycisk.obj", "nakretkaTyl.obj", "nakretkaPrzod.obj", "latarkaBody.obj", "bateria.obj"));
        Group meshGroup = new Group();

        patternFaceF.forEach(p -> {
            ObjModelImporter importer = new ObjModelImporter();
            importer.read(getClass().getResource(p));
            MeshView[] something = importer.getImport();
            Point3D pt = new Point3D(0, 0, 0);
            Arrays.stream(something).forEach(meshView -> {
                meshView.getTransforms().addAll(new Translate(pt.getX(), pt.getY(), pt.getZ()));
                meshGroup.getChildren().add(meshView);
                System.out.println("x");
            });
        });

        for (int i = 0 ; i < meshGroup.getChildren().size(); i++){
            meshGroup.getChildren().get(i).getTransforms().add(new Rotate(-90,0,0,0,Rotate.Y_AXIS));
        }
      meshGroup.getChildren().get(1).getTransforms().add(new Translate(0,0,-5.5));
      meshGroup.getChildren().get(2).getTransforms().add(new Translate(0,0,-5.5));
      meshGroup.getChildren().get(3).getTransforms().add(new Translate(0,0,5));
      meshGroup.getChildren().get(4).getTransforms().add(new Translate(0,0,5));
      meshGroup.getChildren().get(5).getTransforms().add(new Translate(0,0,5));
      meshGroup.getChildren().get(0).getTransforms().add(new Translate(0,-1,0));
      meshGroup.getChildren().get(7).getTransforms().add(new Translate(0,0,-2));


        double pointLightXTransform = -290;
        double pointLightYTransform = 0;

        PointLight pointLight = new PointLight();
        pointLight.setColor(Color.WHITE);
        pointLight.getTransforms().add(new Translate(pointLightXTransform, pointLightYTransform, 0));

        Group myGroup = new Group(wall, pointLight, meshGroup);
        System.out.println(meshGroup.toString());

//        AtomicInteger cont = new AtomicInteger();
//        patternFaceF.forEach(p -> {
//            ObjModelImporter importer = new ObjModelImporter();
//            importer.read(getClass().getResource("latarka.obj"));
//            MeshView meshP = importer.getImport()[0];
//            Point3D pt = pointsFaceF.get(cont.getAndIncrement());
//            meshP.getTransforms().addAll(new Translate(pt.getX(), pt.getY(), pt.getZ()));
//            meshGroup.getChildren().add(meshP);
//        });

        Rotate rotateX = new Rotate(30, 0, 0, 0, Rotate.X_AXIS);
        Rotate rotateY = new Rotate(20, 0, 0, 0, Rotate.Y_AXIS);
        myGroup.getTransforms().addAll(rotateX, rotateY);
//

        sceneRoot.getChildren().addAll(new AmbientLight(new Color(0.2,0.2,0.2,1)), myGroup);
//        sceneRoot.getChildren().addAll(new AmbientLight(Color.DARKGRAY), myGroup);

        scene.setOnMousePressed(me -> {
            mouseOldX = me.getSceneX();
            mouseOldY = me.getSceneY();
        });
        scene.setOnMouseDragged(me -> {
            mousePosX = me.getSceneX();
            mousePosY = me.getSceneY();
            rotateX.setAngle(rotateX.getAngle() - (mousePosY - mouseOldY));
            rotateY.setAngle(rotateY.getAngle() + (mousePosX - mouseOldX));
            mouseOldX = mousePosX;
            mouseOldY = mousePosY;
        });

        // flashlight button click handler
//        EventHandler<MouseEvent> buttonEventHandler = new ButtonEventHandler(myGroup, button, pointLight);
//        button.setOnMouseReleased(buttonEventHandler);
        EventHandler<MouseEvent> buttonEventHandler = new ButtonEventHandler(myGroup, meshGroup.getChildren().get(0), pointLight,new BatteryEventHandler());
        meshGroup.getChildren().get(0).setOnMouseReleased(buttonEventHandler);

        EventHandler<MouseEvent> batteryEventHandler = new BatteryEventHandler(meshGroup.getChildren().get(1),
                meshGroup.getChildren().get(2), meshGroup.getChildren().get(7), (ButtonEventHandler) buttonEventHandler);
        meshGroup.getChildren().get(2).setOnMouseReleased(batteryEventHandler);
        ((ButtonEventHandler) buttonEventHandler).setBatteryHandler((BatteryEventHandler)batteryEventHandler);

        // flashligh head rotation with arrow keys
        HeadRotationHandler headRotationHandler = new HeadRotationHandler(pointLight, new ArrayList<>(Arrays.asList(meshGroup.getChildren().get(3),
                meshGroup.getChildren().get(4),meshGroup.getChildren().get(5))));
        scene.setOnKeyPressed(headRotationHandler);

        primaryStage.setTitle("Flashlight");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
