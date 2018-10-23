# react-native-maps (using open-street-map) WIP

This project is one fork of [react-native-maps](https://github.com/react-community/react-native-maps) to use open-street-map

## RoadMap

 - [x] change use of GoogleMaps to OpenStreetMap
 
 - [x] render map in open-street-maps in android
 
 - [ ] add props OpenStreetMap
  
 - [ ] render open-street-maps to iOS
 
 - [ ] add option to show map with GoogleMap or OpenStreetMap
 
 - [ ] to do PullRequest to [orginal repository](https://github.com/react-community/react-native-maps)


## How to use

this project is done to render maps in open-street-maps on android

## install

`package.json`
```json
  "react-native-open-street-maps": "https://github.com/enieber/react-native-open-street-map.git"
```

```
npm install 
```
or
```
yarn
```

### Android

`setting.gradle`

```gradle
// Open Street Map
include ':react-native-open-street-map'
project(':react-native-open-street-map').projectDir = new File(rootProject.projectDir, '../node_modules/react-native-open-street-map/lib/android')
```

`app/build.gradle`

```gradle

dependencies {
 ...
    compile (project(':react-native-open-street-map')) {
        // if you use the lib gms in the project or libs on project
        exclude group: 'com.google.android.gms'
    }
```

`app/src/main/java/{project_name}/MainApplication.java`
```java

...
import com.airbnb.android.react.maps.MapsPackage;

public class MainApplication extends Application implements ReactApplication {

    @Override
    protected List<ReactPackage> getPackages() {
      new MainReactPackage(),
      new MapsPackage() // insert this line to init module maps
    }
}    
```


### iOS 

the iOS instalation is equals in the lib of react-native-maps because this fork not set iOS to use Open Street Maps, only Apple Maps.

## Props

name | type | iOS  | Android | Info
------ | ---- | ------- | ---- | ----
router | Object | - | OK | router the point A to point B (Android only)
region | Geolocation | - | OK | the initial region render map

## Exaple

### How to make router from point A to point B

```jsx

import MapView from 'react-native-open-street-map';


render() {

  const latitudes = [-15.806553, -15.8202434];
  const longitudes = [-47.8891454, -47.9045093];
  
  return (
    <MapView
      ref="map"
      zoom={5}
      multiTouchControls
      style={{
        flex: 1
      }}
      region={this.state.region}
      showsUserLocation
      router={{
        titleA: "The point A",
        titleB: "The point B",
        descriptionA: "Bank",
        descriptionB: "Scholl",
        coordinates: [
          {
            latitude: latitudes[0],
            longitude: longitudes[1],
          },
          {
            latitude: latitudes[1],
            longitude: longitudes[1],
          }
        ]
      }}
    />
  );
};

```
