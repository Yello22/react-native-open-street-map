package com.airbnb.android.react.maps.open;

import android.view.View;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.facebook.react.uimanager.LayoutShadowNode;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.uimanager.events.RCTEventEmitter;

import org.osmdroid.tileprovider.tilesource.OnlineTileSourceBase;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;

import java.util.Map;

import javax.annotation.Nullable;

public class OpenAirMapManager extends ViewGroupManager<OpenAirMapView> {

    private static final String REACT_CLASS = "OpenAIRMap";
    private static final int ANIMATE_TO_REGION = 1;
    private static final int ANIMATE_TO_COORDINATE = 2;
    private static final int ANIMATE_TO_VIEWING_ANGLE = 3;
    private static final int ANIMATE_TO_BEARING = 4;
    private static final int FIT_TO_ELEMENTS = 5;
    private static final int FIT_TO_SUPPLIED_MARKERS = 6;
    private static final int FIT_TO_COORDINATES = 7;
    private static final int SET_MAP_BOUNDARIES = 8;

    private final Map<String, OnlineTileSourceBase> MAP_TYPES = MapBuilder.of(
            "standard", TileSourceFactory.MAPNIK,
            "satellite", TileSourceFactory.OPEN_SEAMAP
    );

    private final ReactApplicationContext appContext;

    public OpenAirMapManager(ReactApplicationContext context) {
        this.appContext = context;
    }

    @Override
    public String getName() {
        return REACT_CLASS;
    }

    @Override
    protected OpenAirMapView createViewInstance(ThemedReactContext context) {
        return new OpenAirMapView(context, this.appContext, this);
    }

    private void emitMapError(ThemedReactContext context, String message, String type) {
        WritableMap error = Arguments.createMap();
        error.putString("message", message);
        error.putString("type", type);

        context
                .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit("onError", error);
    }

    @ReactProp(name = "region")
    public void setRegion(OpenAirMapView view, ReadableMap region) {
        view.setRegion(region);
    }

    @ReactProp(name = "initialRegion")
    public void setInitialRegion(OpenAirMapView view, ReadableMap initialRegion) {
        view.setInitialRegion(initialRegion);
    }

    @ReactProp(name = "mapType")
    public void setMapType(OpenAirMapView view, @Nullable String mapType) {
        OnlineTileSourceBase titleMap = MAP_TYPES.get(mapType);
        view.map.setTileSource(titleMap);
    }

//    No customMapStyle
//    @ReactProp(name = "customMapStyleString")
//    public void setMapStyle(OpenAirMapView view, @Nullable String customMapStyleString) {
//        view.map(new MapStyleOptions(customMapStyleString));
//    }

    @ReactProp(name = "mapPadding")
    public void setMapPadding(OpenAirMapView view, @Nullable ReadableMap padding) {
        int left = 0;
        int top = 0;
        int right = 0;
        int bottom = 0;
        double density = (double) view.getResources().getDisplayMetrics().density;

        if (padding != null) {
            if (padding.hasKey("left")) {
                left = (int) (padding.getDouble("left") * density);
            }

            if (padding.hasKey("top")) {
                top = (int) (padding.getDouble("top") * density);
            }

            if (padding.hasKey("right")) {
                right = (int) (padding.getDouble("right") * density);
            }

            if (padding.hasKey("bottom")) {
                bottom = (int) (padding.getDouble("bottom") * density);
            }
        }

        view.map.setPadding(left, top, right, bottom);
    }

    @ReactProp(name = "showsUserLocation", defaultBoolean = false)
    public void setShowsUserLocation(OpenAirMapView view, boolean showUserLocation) {
        view.setShowsUserLocation(showUserLocation);
    }

    @ReactProp(name = "showsMyLocationButton", defaultBoolean = true)
    public void setShowsMyLocationButton(OpenAirMapView view, boolean showMyLocationButton) {
        view.setVerticalScrollBarEnabled(showMyLocationButton);
    }

    @ReactProp(name = "toolbarEnabled", defaultBoolean = true)
    public void setToolbarEnabled(OpenAirMapView view, boolean toolbarEnabled) {
        view.setFlingEnabled(toolbarEnabled);
    }

    // This is a private prop to improve performance of panDrag by disabling it when the callback
    // is not set
    @ReactProp(name = "handlePanDrag", defaultBoolean = false)
    public void setHandlePanDrag(OpenAirMapView view, boolean handlePanDrag) {
        view.setHandlePanDrag(handlePanDrag);
    }

//    No ShowTraffic
//    @ReactProp(name = "showsTraffic", defaultBoolean = false)
//    public void setShowTraffic(OpenAirMapView view, boolean showTraffic) {
//        view.map.setTrafficEnabled(showTraffic);
//    }

//
//    @ReactProp(name = "showsBuildings", defaultBoolean = false)
//    public void setShowBuildings(OpenAirMapView view, boolean showBuildings) {
//        view.map.setBuildingsEnabled(showBuildings);
//    }

//    @ReactProp(name = "showsIndoors", defaultBoolean = false)
//    public void setShowIndoors(OpenAirMapView view, boolean showIndoors) {
//        view.map.setInd(showIndoors);
//    }

//    @ReactProp(name = "showsIndoorLevelPicker", defaultBoolean = false)
//    public void setShowsIndoorLevelPicker(OpenAirMapView view, boolean showsIndoorLevelPicker) {
//        view.map.getUiSettings().setIndoorLevelPickerEnabled(showsIndoorLevelPicker);
//    }

//    @ReactProp(name = "showsCompass", defaultBoolean = false)
//    public void setShowsCompass(OpenAirMapView view, boolean showsCompass) {
//        view.map.getUiSettings().setCompassEnabled(showsCompass);
//    }

    @ReactProp(name = "scrollEnabled", defaultBoolean = false)
    public void setScrollEnabled(OpenAirMapView view, boolean scrollEnabled) {
        view.map.setScrollbarFadingEnabled(scrollEnabled);
    }

//    @ReactProp(name = "zoomEnabled", defaultBoolean = false)
//    public void setZoomEnabled(OpenAirMapView view, boolean zoomEnabled) {
//        view.map.setEnabled(zoomEnabled);
//    }

//    @ReactProp(name = "zoomControlEnabled", defaultBoolean = true)
//    public void setZoomControlEnabled(OpenAirMapView view, boolean zoomControlEnabled) {
//        view.map.setZ(zoomControlEnabled);
//    }

    @ReactProp(name = "rotateEnabled", defaultBoolean = false)
    public void setRotateEnabled(OpenAirMapView view, boolean rotateEnabled) {
        view.map.setMotionEventSplittingEnabled(rotateEnabled);
    }

    @ReactProp(name = "cacheEnabled", defaultBoolean = false)
    public void setCacheEnabled(OpenAirMapView view, boolean cacheEnabled) {
        view.setCacheEnabled(cacheEnabled);
    }

    @ReactProp(name = "loadingEnabled", defaultBoolean = false)
    public void setLoadingEnabled(OpenAirMapView view, boolean loadingEnabled) {
        view.enableMapLoading(loadingEnabled);
    }

    @ReactProp(name = "moveOnMarkerPress", defaultBoolean = true)
    public void setMoveOnMarkerPress(OpenAirMapView view, boolean moveOnPress) {
        view.setMoveOnMarkerPress(moveOnPress);
    }

    @ReactProp(name = "loadingBackgroundColor", customType = "Color")
    public void setLoadingBackgroundColor(OpenAirMapView view, @Nullable Integer loadingBackgroundColor) {
        view.setLoadingBackgroundColor(loadingBackgroundColor);
    }

    @ReactProp(name = "loadingIndicatorColor", customType = "Color")
    public void setLoadingIndicatorColor(OpenAirMapView view, @Nullable Integer loadingIndicatorColor) {
        view.setLoadingIndicatorColor(loadingIndicatorColor);
    }

//    @ReactProp(name = "pitchEnabled", defaultBoolean = false)
//    public void setPitchEnabled(OpenAirMapView view, boolean pitchEnabled) {
//        view.map.getUiSettings().setTiltGesturesEnabled(pitchEnabled);
//    }

    @ReactProp(name = "minZoomLevel")
    public void setMinZoomLevel(OpenAirMapView view, Integer minZoomLevel) {
        view.map.setMinZoomLevel(minZoomLevel);
    }

    @ReactProp(name = "maxZoomLevel")
    public void setMaxZoomLevel(OpenAirMapView view, Integer maxZoomLevel) {
        view.map.setMaxZoomLevel(maxZoomLevel);
    }

    @Override
    public void receiveCommand(OpenAirMapView view,
                               int commandId,
                               @Nullable ReadableArray args) {
        Integer duration;
        Double lat;
        Double lng;
        Double lngDelta;
        Double latDelta;
        float bearing;
        float angle;
        ReadableMap region;

        switch (commandId) {
            case ANIMATE_TO_REGION:
                region = args.getMap(0);
                duration = args.getInt(1);
                lng = region.getDouble("longitude");
                lat = region.getDouble("latitude");
                lngDelta = region.getDouble("longitudeDelta");
                latDelta = region.getDouble("latitudeDelta");

                GeoPoint southwest = new GeoPoint(lat + latDelta / 2, lng + lngDelta / 2);
                GeoPoint northeast = new GeoPoint(lat + latDelta / 2, lng + lngDelta / 2);
                GeoPoint point = new GeoPoint(lat, lng);
                view.animateToRegion(point, duration);
                break;

            case ANIMATE_TO_COORDINATE:
                region = args.getMap(0);
                duration = args.getInt(1);
                lng = region.getDouble("longitude");
                lat = region.getDouble("latitude");
                GeoPoint pointCord = new GeoPoint(lat, lng);
                view.animateToCoordinate(pointCord, duration);
                break;

            case ANIMATE_TO_VIEWING_ANGLE:
                angle = (float)args.getDouble(0);
                duration = args.getInt(1);
//                view.animateToViewingAngle(angle, duration);
                break;

            case ANIMATE_TO_BEARING:
                bearing = (float)args.getDouble(0);
                duration = args.getInt(1);
//                view.animateToBearing(bearing, duration);
                break;

            case FIT_TO_ELEMENTS:
//                view.fitToElements(args.getBoolean(0));
                break;

            case FIT_TO_SUPPLIED_MARKERS:
//                view.fitToSuppliedMarkers(args.getArray(0), args.getBoolean(1));
                break;

            case FIT_TO_COORDINATES:
//                view.fitToCoordinates(args.getArray(0), args.getMap(1), args.getBoolean(2));
                break;

            case SET_MAP_BOUNDARIES:
//                view.setMapBoundaries(args.getMap(0), args.getMap(1));
                break;
        }
    }

    @Override
    @Nullable
    public Map getExportedCustomDirectEventTypeConstants() {
        Map<String, Map<String, String>> map = MapBuilder.of(
                "onMapReady", MapBuilder.of("registrationName", "onMapReady"),
                "onPress", MapBuilder.of("registrationName", "onPress"),
                "onLongPress", MapBuilder.of("registrationName", "onLongPress"),
                "onMarkerPress", MapBuilder.of("registrationName", "onMarkerPress"),
                "onMarkerSelect", MapBuilder.of("registrationName", "onMarkerSelect"),
                "onMarkerDeselect", MapBuilder.of("registrationName", "onMarkerDeselect"),
                "onCalloutPress", MapBuilder.of("registrationName", "onCalloutPress")
        );

        map.putAll(MapBuilder.of(
                "onMarkerDragStart", MapBuilder.of("registrationName", "onMarkerDragStart"),
                "onMarkerDrag", MapBuilder.of("registrationName", "onMarkerDrag"),
                "onMarkerDragEnd", MapBuilder.of("registrationName", "onMarkerDragEnd"),
                "onPanDrag", MapBuilder.of("registrationName", "onPanDrag")
        ));

        return map;
    }

    @Nullable
    @Override
    public Map<String, Integer> getCommandsMap() {
        Map<String, Integer> map = MapBuilder.of(
                "animateToRegion", ANIMATE_TO_REGION,
                "animateToCoordinate", ANIMATE_TO_COORDINATE,
                "animateToViewingAngle", ANIMATE_TO_VIEWING_ANGLE,
                "animateToBearing", ANIMATE_TO_BEARING,
                "fitToElements", FIT_TO_ELEMENTS,
                "fitToSuppliedMarkers", FIT_TO_SUPPLIED_MARKERS,
                "fitToCoordinates", FIT_TO_COORDINATES
        );

        map.putAll(MapBuilder.of(
                "setMapBoundaries", SET_MAP_BOUNDARIES
        ));

        return map;
    }

    @Override
    public LayoutShadowNode createShadowNodeInstance() {
        // A custom shadow node is needed in order to pass back the width/height of the map to the
        // view manager so that it can start applying camera moves with bounds.
        return new SizeReportingShadowNode();
    }

    @Override
    public void addView(OpenAirMapView parent, View child, int index) {
//        parent.addFeature(child, index);
    }

    @Override
    public int getChildCount(OpenAirMapView view) {
        return view.getFeatureCount();
    }

    @Override
    public View getChildAt(OpenAirMapView view, int index) {
        return view.getFeatureAt(index);
    }

    @Override
    public void removeViewAt(OpenAirMapView parent, int index) {
        parent.removeFeatureAt(index);
    }

    @Override
    public void updateExtraData(OpenAirMapView view, Object extraData) {
//        view.updateExtraData(extraData);
    }

    void pushEvent(ThemedReactContext context, View view, String name, WritableMap data) {
        context.getJSModule(RCTEventEmitter.class)
                .receiveEvent(view.getId(), name, data);
    }

    @Override
    public void onDropViewInstance(OpenAirMapView view) {
        view.doDestroy();
        super.onDropViewInstance(view);
    }

}
