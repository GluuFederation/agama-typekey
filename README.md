# Agama Typekey Project

<!-- These are statistics for this repository-->
[![Contributors][contributors-shield]][contributors-url]
[![Forks][forks-shield]][forks-url]
[![Stargazers][stars-shield]][stars-url]
[![Issues][issues-shield]][issues-url]
[![Apache License][license-shield]][license-url]



## How it works at a glance

### Requirements



### Add Java dependencies


4. Restart Auth Server to load the new jar:

```
systemctl restart jans-auth
````

### Deployment

Download the
latest [agama-passkey.gama](https://github.com/GluuFederation/agama-typekey/releases/latest/download/agama-typekey.gama)
file and deploy it in Auth Sever.

Follow the steps below:

- Copy (SCP/SFTP) the gama file of this project to a location in your `Jans Server`
- Connect (SSH) to your `Jans Server` and open TUI: `python3 /opt/jans/jans-cli/jans_cli_tui.py`
- Navigate to the `Agama` tab and then select `"Upload project"`. Choose the gama file
- Wait for about one minute and then select the row in the table corresponding to this project
- Press `d` and ensure there were not deployment errors
- Pres `ESC` to close the dialog

![TUI_AGAMA_DEPLOY]()

### Configure Jans Scim

- We open TUI and we are located in agama, we select in the table where our application is deployed and press `c`, this will open a configuration panel, where we must first hit `Export Sample Config` and save the file in some path.
- Now we go to the exported file and edit it and enter the credentials

```
{
    "org.gluu.agama.typekey": {
      "keystoreName": "", // name of keystore file
      "keystorePassword": "", // password of keystore file
      "orgId": "", // org_id of the SCAN account to use
      "clientId": "", // Client ID obtained from DCR
      "clientSecret": "", // Client secret obtained from DCR
      "authHost": "https://account.gluu.org", // Authorization server
      "scanHost": "https://cloud.gluu.org" // SCAN host
    }
}
```

- We go back to the TUI and click on `Import Configuration` and select the modified configuration file with our parameters.
- With this, our `agama project` is now configured and we can start testing.

## Testing

You'll need an OpenID Connect test RP. You can try [oidcdebugger](https://oidcdebugger.com/),
[jans-tarp](https://github.com/JanssenProject/jans/tree/main/demos/jans-tarp)
or [jans-tent](https://github.com/JanssenProject/jans/tree/main/demos/jans-tent). Check out this video to see an example
of **agama-typekey** in action:

# Contributors

<table>
<tr>
    <td align="center" style="word-wrap: break-word; width: 150.0; height: 150.0">
        <a href=https://github.com/SafinWasi>
            <img src=https://avatars.githubusercontent.com/u/6601566?v=4 width="100;"  style="border-radius:50%;align-items:center;justify-content:center;overflow:hidden;padding-top:10px" alt=SafinWasi/>
            <br />
            <sub style="font-size:14px"><b>Milton Ch.</b></sub>
        </a>
    </td>
</tr>
</table>

# License

This project is licensed under the [Apache 2.0](https://github.com/GluuFederation/agama-security-key/blob/main/LICENSE)

<!-- This are stats url reference for this repository -->

[contributors-shield]: https://img.shields.io/github/contributors/GluuFederation/agama-passkey.svg?style=for-the-badge

[contributors-url]: https://github.com/GluuFederation/agama-passkey/graphs/contributors

[forks-shield]: https://img.shields.io/github/forks/GluuFederation/agama-passkey.svg?style=for-the-badge

[forks-url]: https://github.com/GluuFederation/agama-passkey/network/members

[stars-shield]: https://img.shields.io/github/stars/GluuFederation/agama-passkey?style=for-the-badge

[stars-url]: https://github.com/GluuFederation/agama-passkey/stargazers

[issues-shield]: https://img.shields.io/github/issues/GluuFederation/agama-passkey.svg?style=for-the-badge

[issues-url]: https://github.com/GluuFederation/agama-passkey/issues

[license-shield]: https://img.shields.io/github/license/GluuFederation/agama-passkey.svg?style=for-the-badge

[license-url]: https://github.com/GluuFederation/agama-passkey/blob/main/LICENSE




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