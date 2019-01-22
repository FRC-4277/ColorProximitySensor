package frc4277.plugin.colorproximity;

import java.util.Map;
import java.util.Objects;
import java.util.StringJoiner;

import edu.wpi.first.shuffleboard.api.data.ComplexData;
import edu.wpi.first.shuffleboard.api.util.Maps;
import javafx.scene.paint.Color;

public class ColorProximityData extends ComplexData<ColorProximityData> {
    private final short clear, red, green, blue, proximity;
    private Color color;
    private boolean usedClear;

    public ColorProximityData(short clear, short red, short green, short blue, short proximity) {
        this.clear = clear;
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.proximity = proximity;
    }

    public short getClear() {
        return this.clear;
    }

    public short getRed() {
        return this.red;
    }

    public short getGreen() {
        return this.green;
    }

    public short getBlue() {
        return this.blue;
    }

    public Color getAsColor(boolean useClear) {
        if (color == null || usedClear != useClear) {
            if (clear == -1 || red == -1 || green == -1 || blue == -1 || proximity == -1) {
                color = Color.WHITE;
            } else {
                usedClear = useClear;
                color = useClear ? Color.rgb(red, green, blue, clear / 255d) : Color.rgb(red, green, blue);
            }
        }
        return color;
    }

    public short getProximity() {
        return this.proximity;
    }

    @Override
    public Map<String, Object> asMap() {
        return Maps.<String, Object>builder()
            .put("Clear", clear)
            .put("Red", red)
            .put("Green", green)
            .put("Blue", blue)
            .put("Proximity", proximity)
            .build();
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ColorProximityData)) return false;
        ColorProximityData that = (ColorProximityData) o;
        return clear == that.clear &&
                red == that.red &&
                green == that.green &&
                blue == that.blue &&
                proximity == that.proximity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(clear, red, green, blue, proximity);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", ColorProximityData.class.getSimpleName() + "[", "]")
                .add("clear=" + clear)
                .add("red=" + red)
                .add("green=" + green)
                .add("blue=" + blue)
                .add("proximity=" + proximity)
                .toString();
    }
}