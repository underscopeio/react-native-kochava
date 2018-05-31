import { NativeModules } from 'react-native'

const RNKochava = NativeModules.RNKochava

export default {
  identityLink: identity => {
    return RNKochava.identityLink(identity)
  },
  sendEvent: (name, options = {}) => {
    return RNKochava.sendEvent(name, options)
  },
}
