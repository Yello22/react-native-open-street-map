package com.airbnb.android.react.maps;

import com.facebook.react.bridge.ReactApplicationContext;
import com.google.android.gms.maps.GoogleMapOptions;

public class OpenAirMapLiteManager extends AirMapManager {

  private static final String REACT_CLASS = "AIRMapLite";

  @Override
  public String getName() {
    return REACT_CLASS;
  }

  public OpenAirMapLiteManager(ReactApplicationContext context) {
    super(context);
    this.googleMapOptions = new GoogleMapOptions().liteMode(true);
  }

}
