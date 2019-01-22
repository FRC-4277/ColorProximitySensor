package frc4277.plugin.colorproximity;

import edu.wpi.first.shuffleboard.api.prefs.Group;
import edu.wpi.first.shuffleboard.api.prefs.Setting;
import edu.wpi.first.shuffleboard.api.widget.Description;
import edu.wpi.first.shuffleboard.api.widget.ParametrizedController;
import edu.wpi.first.shuffleboard.api.widget.SimpleAnnotatedWidget;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;

import java.util.List;
import java.util.function.Function;


// TODO : Add options to enable/disable display of SPECIFIC FIELDS (CLEAR, RED, G, B PROX, COLOR)
@Description(name = "Color Proximity", dataTypes = ColorProximityData.class)
@ParametrizedController("ColorProximityWidget.fxml")
public class ColorProximityWidget extends SimpleAnnotatedWidget<ColorProximityData> {
    @FXML
    private VBox root;

    private BooleanProperty colorDisplayUseClear = new SimpleBooleanProperty(false);
    private BooleanProperty colorDisplayVisibility = new SimpleBooleanProperty(true);
    @FXML
    private StackPane colorDisplay;
    @FXML
    private Label noData;

    @FXML
    private GridPane gridPane;

    private BooleanProperty clearVisibility = new SimpleBooleanProperty(true);
    @FXML
    private Label clearLabel;
    @FXML
    private TextField clearField;

    private BooleanProperty redVisibility = new SimpleBooleanProperty(true);
    @FXML
    private Label redLabel;
    @FXML
    private TextField redField;

    private BooleanProperty greenVisibility = new SimpleBooleanProperty(true);
    @FXML
    private Label greenLabel;
    @FXML
    private TextField greenField;

    private BooleanProperty blueVisibility = new SimpleBooleanProperty(true);
    @FXML
    private Label blueLabel;
    @FXML
    private TextField blueField;

    private BooleanProperty proximityVisibility = new SimpleBooleanProperty(true);
    @FXML
    private Label proximityLabel;
    @FXML
    private TextField proximityField;

    @FXML
    private void initialize() {
        noData.visibleProperty().bind(
            Bindings.createObjectBinding(() -> getData() == null, dataProperty())
        );

        colorDisplay.backgroundProperty().bind(
            Bindings.createObjectBinding(this::createBackground, dataProperty(), colorDisplayUseClear)
        );

        colorDisplayVisibility.addListener((observable, oldValue, newValue) -> {
            if (oldValue != newValue) {
                if (newValue) {
                    // Add
                    root.getChildren().add(0, colorDisplay);
                } else {
                    // remove
                    root.getChildren().remove(0);
                }
            }
        });

        ChangeListener<? super Boolean> listener = (observable, oldValue, newValue) -> {
            if (oldValue != newValue) {
                // Value updated, so let's update GridPane contents
                updateVisibility();
            }
        };
        clearVisibility.addListener(listener);
        redVisibility.addListener(listener);
        blueVisibility.addListener(listener);
        greenVisibility.addListener(listener);
        proximityVisibility.addListener(listener);
        updateVisibility();

        bind(clearField, ColorProximityData::getClear);
        bind(redField, ColorProximityData::getRed);
        bind(greenField, ColorProximityData::getGreen);
        bind(blueField, ColorProximityData::getBlue);
        bind(proximityField, ColorProximityData::getProximity);
    }

    private Background createBackground() {
        ColorProximityData data = getData();
        if (data == null) {
            return null;
        }
        return new Background(new BackgroundFill(getData().getAsColor(colorDisplayUseClear.get()), null, null));
    }

    private void bind(TextField textField, Function<ColorProximityData, Short> function) {
        textField.textProperty().bind(dataOrDefault.map(function).map(s -> Short.toString(s)));
    }

    private void updateVisibility() {
        ObservableList<Node> nodes = gridPane.getChildren();
        nodes.clear();

        int row = 0;
        row = updateVisibility(nodes, clearVisibility, clearLabel, clearField, row);
        row = updateVisibility(nodes, redVisibility, redLabel, redField, row);
        row = updateVisibility(nodes, greenVisibility, greenLabel, greenField, row);
        row = updateVisibility(nodes, blueVisibility, blueLabel, blueField, row);
        updateVisibility(nodes, proximityVisibility, proximityLabel, proximityField, row);
    }

    private int updateVisibility(ObservableList<Node> nodes, BooleanProperty property, Label label, TextField field, int currentRow) {
        if (property.get()) {
            nodes.addAll(label, field);
            GridPane.setRowIndex(label, currentRow);
            GridPane.setRowIndex(field, currentRow);
            GridPane.setColumnIndex(label, 0);
            GridPane.setColumnIndex(field, 1);
            return currentRow + 1;
        }
        return currentRow;
    }

    @Override
    public List<Group> getSettings() {
        return List.of(
            Group.of("Color Display",
                Setting.of("Enabled", colorDisplayVisibility, Boolean.class),
                Setting.of("Use Clear", colorDisplayUseClear, Boolean.class)
            ),
            Group.of("Readings",
                Setting.of("Clear", clearVisibility, Boolean.class),
                Setting.of("Red", redVisibility, Boolean.class),
                Setting.of("Green", greenVisibility, Boolean.class),
                Setting.of("Blue", blueVisibility, Boolean.class),
                Setting.of("Proximity", proximityVisibility, Boolean.class)
            )
        );
    }

    @Override
    public Pane getView() {
        return root;
    }

}
