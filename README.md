# agama-typekey
An Agama flow designed to authenticate against the Typekey API to test behavioral metrics

## Setup

Modify the configuration with the following values:

```json
...
  "configs": {
    "io.jans.typekey": {
      "keystoreName": "", // name of keystore file
      "keystorePassword": "", // password of keystore file
      "orgId": "", // org_id of the SCAN account to use
      "clientId": "", // Client ID obtained from DCR
      "clientSecret": "", // Client secret obtained from DCR
      "authHost": "https://account-dev.gluu.cloud", // Authorization server
      "scanHost": "https://cloud-dev.gluu.cloud" // SCAN host
    }
  }
```