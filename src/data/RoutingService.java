package data;

import java.util.ArrayList;
import java.util.List;
import com.graphhopper.util.shapes.GHPoint3D;
import org.jxmapviewer.viewer.GeoPosition;

public class RoutingService {

    private static RoutingService instance;

    private RoutingService() {
    }

    public static RoutingService getInstance() {
        if (instance == null) {
            instance = new RoutingService();
        }
        return instance;
    }

    public List<RoutingData> routing(double startLat, double startLon, double endLat, double endLon) {
        List<RoutingData> list = new ArrayList<>();
        RoutingData data = new RoutingData();

        List<GHPoint3D> points = new ArrayList<>();

        // Mock noktalar ekliyoruz (basit bir düz çizgi gibi düşün)
        points.add(new GHPoint3D(startLat, startLon, 0));
        points.add(new GHPoint3D((startLat + endLat) / 2, (startLon + endLon) / 2, 0)); // Ortadan bir nokta
        points.add(new GHPoint3D(endLat, endLon, 0));

        data.setPointList(points);

        list.add(data);
        return list;
    }
}
