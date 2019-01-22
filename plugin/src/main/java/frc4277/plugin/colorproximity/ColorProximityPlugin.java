package frc4277.plugin.colorproximity;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import edu.wpi.first.shuffleboard.api.data.DataType;
import edu.wpi.first.shuffleboard.api.plugin.Description;
import edu.wpi.first.shuffleboard.api.plugin.Plugin;
import edu.wpi.first.shuffleboard.api.widget.ComponentType;
import edu.wpi.first.shuffleboard.api.widget.WidgetType;

import java.util.List;
import java.util.Map;

@Description(
    group = "frc4277.plugin",
    name = "Color Proximity",
    version = "0.0.1",
    summary = "Adds widget for TMD3782x color sensors"
)
public class ColorProximityPlugin extends Plugin {
    @Override
    public List<DataType> getDataTypes() {
        return ImmutableList.of(
            ColorProximityType.instance
        );
    }

    @Override
    public List<ComponentType> getComponents() {
        return ImmutableList.of(
                WidgetType.forAnnotatedWidget(ColorProximityWidget.class)
        );
    }

    @Override
    public Map<DataType, ComponentType> getDefaultComponents() {
        return ImmutableMap.<DataType, ComponentType>builder()
            .put(ColorProximityType.instance, WidgetType.forAnnotatedWidget(ColorProximityWidget.class))
            .build();
    }
}