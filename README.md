# react-native-maps (using open-street-map) WIP

This project is one fork of [react-native-maps](https://github.com/react-community/react-native-maps) to use open-street-map

## RoadMap

 - [x] change use of GoogleMaps to OpenStreetMap
 
 - [ ] add props OpenStreetMap
 
 - [ ] add option to show map with GoogleMap or OpenStreetMap
 
 - [ ] to do PullRequest to [orginal repository](https://github.com/react-community/react-native-maps)


## Props

name | type | iOS  | Android | Info
------ | ---- | ------- | ---- | ----
router | Object | - | OK | router the point A to point B (Android only)
region | Geolocation | - | OK | the initial region render map

### router

```js
router: {
  titleA: "The point A"
  titleB: "The point B",
  descriptionA: "Bank",
  descriptionB: "Scholl",
  coordinates: [
   {
     latitude: -15.806553,
     longitude: -47.8891454,
   },
   {
     latitude: -15.8202434,
     longitude: -47.9045093,
   }
 ],
}
```
