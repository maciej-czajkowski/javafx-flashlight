
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.AmbientLight;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.PointLight;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.*;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;


public class Flashlight extends Application {

    private double mousePosX;
    private double mousePosY;
    private double mouseOldX;
    private double mouseOldY;


    @Override
    public void start(Stage primaryStage) {
        Group sceneRoot = new Group();
        Scene scene = new Scene(sceneRoot, 600, 600, true, SceneAntialiasing.BALANCED);
        scene.setFill(Color.BLACK);
        PerspectiveCamera camera = new PerspectiveCamera(true);
        camera.setNearClip(0.1);
        camera.setFarClip(10000.0);

        camera.setTranslateZ(-900);
//        camera.setTranslateX(-200);
        scene.setCamera(camera);

        // x + do tylu / - do przodu
        // y + w dól / - w góre
        // z + w prawo / - w lewo

//        Group meshGroup = new Group();
        Box wall = new Box(10, 500, 500);
        wall.getTransforms().add(new Translate(-300, 0, 0));
        PhongMaterial wallMaterial = new PhongMaterial();
        wallMaterial.setDiffuseColor(Color.WHITE);
        wallMaterial.setSpecularColor(Color.ORANGE);
        wall.setMaterial(wallMaterial);

        Box button = new Box(10, 10, 10);
        PhongMaterial buttonMaterial = new PhongMaterial();
        buttonMaterial.setDiffuseColor(Color.YELLOW);
        buttonMaterial.setSpecularColor(Color.YELLOW);
        button.setMaterial(buttonMaterial);
        button.getTransforms().add(new Translate(60, -100, 0));

        Cylinder base = new Cylinder(10, 100);
        base.getTransforms().addAll(new Rotate(90, 90, 0));

        Cylinder head = new Cylinder(13, 30);
        head.getTransforms().addAll(new Rotate(90, 90, 0), new Translate(0, 55, 0)
                                    );
        PhongMaterial headMat = new PhongMaterial();
        headMat.setDiffuseMap(new Image(getClass().getResourceAsStream("pallete.png")));
        head.setMaterial(headMat);


        double pointLightXTransform = -250;
        double pointLightYTransform = -90;
//        Box trailer = new Box(30, 30, 30);
//        PhongMaterial trailerMaterial = new PhongMaterial();
//        trailerMaterial.setDiffuseColor(Color.RED);
//        trailerMaterial.setSpecularColor(Color.RED);
//        trailer.setMaterial(buttonMaterial);
//        trailer.getTransforms().add(new Translate(pointLightXTransform, y, 0));

        PointLight pointLight = new PointLight();
        pointLight.setColor(Color.WHITE);
        pointLight.getTransforms().add(new Translate(pointLightXTransform, pointLightYTransform, 0));

        Group myGroup = new Group(base, head, wall, button, pointLight);

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



        sceneRoot.getChildren().addAll(new AmbientLight(Color.DARKGRAY), myGroup);
//        sceneRoot.getChildren().addAll(meshGroup, new AmbientLight(Color.DARKGRAY), pointLight, base);

        scene.setOnMousePressed(me -> {
            mouseOldX = me.getSceneX();
            mouseOldY = me.getSceneY();
        });
        scene.setOnMouseDragged(me -> {
            mousePosX = me.getSceneX();
            mousePosY = me.getSceneY();
            rotateX.setAngle(rotateX.getAngle()-(mousePosY - mouseOldY));
            rotateY.setAngle(rotateY.getAngle()+(mousePosX - mouseOldX));
            mouseOldX = mousePosX;
            mouseOldY = mousePosY;
        });

        // flashlight button click handler
        EventHandler<MouseEvent> buttonEventHandler = new ButtonEventHandler(myGroup, button, pointLight);
        button.setOnMouseReleased(buttonEventHandler);

        // flashligh head rotation with arrow keys
        HeadRotationHandler headRotationHandler = new HeadRotationHandler(pointLight, head);
        scene.setOnKeyPressed(headRotationHandler);

        primaryStage.setTitle("Flashlight");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}