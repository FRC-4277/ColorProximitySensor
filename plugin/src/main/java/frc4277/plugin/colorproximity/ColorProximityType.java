package frc4277.plugin.colorproximity;

import edu.wpi.first.shuffleboard.api.data.ComplexDataType;
import edu.wpi.first.shuffleboard.api.util.Maps;

import java.util.Map;
import java.util.function.Function;

public class ColorProximityType extends ComplexDataType<ColorProximityData> {
    public static ColorProximityType instance = new ColorProximityType();
    private static final Double DEFAULT_VALUE = -1d;

    private ColorProximityType() {
        super("ColorProximity", ColorProximityData.class);
    }

    @Override
    public Function<Map<String, Object>, ColorProximityData> fromMap() {
        return map -> new ColorProximityData(
                Maps.getOrDefault(map, "Clear", DEFAULT_VALUE).shortValue(),
                Maps.getOrDefault(map, "Red", DEFAULT_VALUE).shortValue(),
                Maps.getOrDefault(map, "Green", DEFAULT_VALUE).shortValue(),
                Maps.getOrDefault(map, "Blue", DEFAULT_VALUE).shortValue(),
                Maps.getOrDefault(map, "Proximity", DEFAULT_VALUE).shortValue()
        );
    }

    @Override
    public ColorProximityData getDefaultValue() {
        short defaultShort = DEFAULT_VALUE.shortValue();
        return new ColorProximityData(defaultShort, defaultShort, defaultShort, defaultShort, defaultShort);
    }
}
