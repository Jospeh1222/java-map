package data;

import com.graphhopper.util.shapes.GHPoint3D;
import java.util.List;

public class RoutingData {

    private List<GHPoint3D> pointList;

    public List<GHPoint3D> getPointList() {
        return pointList;
    }

    public void setPointList(List<GHPoint3D> pointList) {
        this.pointList = pointList;
    }
}
