# Agama Typekey Project

<!-- These are statistics for this repository-->
[![Contributors][contributors-shield]][contributors-url]
[![Forks][forks-shield]][forks-url]
[![Stargazers][stars-shield]][stars-url]
[![Issues][issues-shield]][issues-url]
[![Apache License][license-shield]][license-url]

This project allows you to record behavioral keystroke data and use it as a second factor of authentication by leveraging the Typekey API. 

## How it works at a glance

The project contains one flow: `org.gluu.agama.typekey`. When this is launched, the user is first asked for the username. If the user exists and has not been enrolled, a random phrase is chosen from the list of configured phrases and stored in persistence. Then the user is asked to type the phrase, and the website records keystroke data for that phrase. This data is sent to the Typekey API to enroll the user and used as the first factor. If the user is being enrolled or Typekey API denies authentication, password is used as the second factor.

### Requirements

1. A running instance of Jans Auth Server
1. A new column in `jansdb.jansPerson` to store the phrase metadata in
1. A SCAN subscription. Please visit [https://gluu.org/agama-lab] and sign up for a free SCAN subscription, which gives you 500 credits. Each successful Typekey API call costs 25 credits.

### Add column to database

These instructions are for MySQL. Please follow the [documentation](https://docs.jans.io/v1.0.22/admin/reference/database/) for your persistence type.

1. Log into the server running Jans
2. Log into MySQL with a user that has permission to operate on `jansdb`
3. Add the column:

  ```sql
  ALTER TABLE jansdb.jansPerson ADD COLUMN typekeyData JSON NULL;
  ```

4. Restart MySQL and Auth Server to load the changes:

  ```
  systemctl restart mysql jans-auth
  ````

### Dynamic Client Registration

In order to call the Typekey API, you will need an OAuth client. Once you have a SCAN subscription on Agama Lab, navigate to `Market` > `SCAN` and create an SSA with the software claim `typekey` and an appropriate lifetime. Your client will expire after that time. Once this is done, note down the base64 encoded string, and send a dynamic client registration request to `https://account.gluu.org/jans-auth/restv1/register` to obtain a client ID and secret. You will need this to configure the Typekey flow. Jans Tarp has functionality to automate the registration process.

- [Dynamic Client Registration specification](https://www.rfc-editor.org/rfc/rfc7591#section-3.1)
- [Jans Tarp](https://github.com/JanssenProject/jans/tree/main/demos/jans-tarp)

### Deployment

Download the latest [agama-typekey.gama](https://github.com/GluuFederation/agama-typekey/releases/latest/download/agama-typekey.gama) file and deploy it in Auth Sever.

Follow the steps below:

- Copy (SCP/SFTP) the gama file of this project to a location in your `Jans Server`
- Connect (SSH) to your `Jans Server` and open TUI: `python3 /opt/jans/jans-cli/jans_cli_tui.py`
- Navigate to the `Agama` tab and then select `"Upload project"`. Choose the gama file
- Wait for about one minute and then select the row in the table corresponding to this project
- Press `d` and ensure there were not deployment errors
- Pres `ESC` to close the dialog

### Configure Typekey 

- Open TUI and navigate to `Agama`
- Select the deployed project and hit `c`
- Select `Export sample configuration` and select a directory and a filename
- Open the file in an editor

```
{
    "org.gluu.agama.typekey": {
      "keystoreName": "",
      "keystorePassword": "", 
      "orgId": "", 
      "clientId": "",
      "clientSecret": "",
      "authHost": "https://account.gluu.org",
      "scanHost": "https://cloud.gluu.org"
    }
}
```

### Configuration details

- `keystoreName` and `keystorePassword` are optional, in case you want to include a signature when sending the Typekey data. Leave them as blank otherwise.
- `orgId` is the organization ID that can be obtained by decoding the software statement JWT and looking at the `org_id` claim (You may use `https://jwt.io` to decode the SSA).
- `clientId` and `clientSecret` are the client credentials obtained from Dynamic Client Registration
- `authHost` and `scanHost` can be left as is

- We go back to the TUI and click on `Import Configuration` and select the modified configuration file with our parameters.
- With this, our `agama project` is now configured and we can start testing.

## Testing

You'll need an OpenID Connect test RP. You can try [oidcdebugger](https://oidcdebugger.com/),
[jans-tarp](https://github.com/JanssenProject/jans/tree/main/demos/jans-tarp)
or [jans-tent](https://github.com/JanssenProject/jans/tree/main/demos/jans-tent).

Launch an authorization flow with parameters `acr_values=agama&agama_flow=org.gluu.agama.typekey` with your chosen RP.

Check out this video to see an example of **agama-typekey** in action:

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

This project is licensed under the [Apache 2.0](https://github.com/GluuFederation/agama-typekey/blob/main/LICENSE)

<!-- This are stats url reference for this repository -->

[contributors-shield]: https://img.shields.io/github/contributors/GluuFederation/agama-typekey.svg?style=for-the-badge

[contributors-url]: https://github.com/GluuFederation/agama-typekey/graphs/contributors

[forks-shield]: https://img.shields.io/github/forks/GluuFederation/agama-typekey.svg?style=for-the-badge

[forks-url]: https://github.com/GluuFederation/agama-typekey/network/members

[stars-shield]: https://img.shields.io/github/stars/GluuFederation/agama-typekey?style=for-the-badge

[stars-url]: https://github.com/GluuFederation/agama-typekey/stargazers

[issues-shield]: https://img.shields.io/github/issues/GluuFederation/agama-typekey.svg?style=for-the-badge

[issues-url]: https://github.com/GluuFederation/agama-typekey/issues

[license-shield]: https://img.shields.io/github/license/GluuFederation/agama-typekey.svg?style=for-the-badge

[license-url]: https://github.com/GluuFederation/agama-typekey/blob/main/LICENSE




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
