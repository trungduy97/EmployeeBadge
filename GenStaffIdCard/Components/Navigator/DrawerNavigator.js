import { AppRegistry, Dimensions } from 'react-native';
import { createDrawerNavigator } from 'react-navigation';
import App from '../../App';
import Demo1 from './Demo1';

var {height, width} = Dimensions.get('window');
let routeConfigs = {
  'App': {
    screen: App,
  },
  'Demo1': {
    screen: Demo1,
  },
};
let drawerNavigatorConfig = {
  initialRouteName: 'App',
  drawerWidth: width / 2,
  drawerPosition: 'left',
  contentOptions: {
    activeTintColor: 'red',
  },
  order: ["App", "Demo1"],
};
export const DrawerNavigator = createDrawerNavigator(routeConfigs, drawerNavigatorConfig);