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
1. A SCAN subscription. Please visit [Agama Lab](https://gluu.org/agama-lab) and sign up for a free SCAN subscription, which gives you 500 credits. Each successful Typekey API call costs 4 credits.

### Add column to database

These instructions are for PostgreSQL. Please follow the [documentation](https://docs.jans.io/v1.0.22/admin/reference/database/) for your persistence type.

1. Log into the server running Jans
2. Log into PostgreSQL with a user that has permission to operate on `jansdb`
3. Connect to `jansdb`: `\c jansdb`
4. Add the column:

  ```sql
  ALTER TABLE "jansPerson" ADD COLUMN "typekeyData" JSON;
  ```

4. Restart PostgreSQL and Auth Server to load the changes:

  ```
  systemctl restart postgresql jans-auth
  ````

### Dynamic Client Registration

In order to call the Typekey API, you will need an OAuth client. Once you have a SCAN subscription on Agama Lab, navigate to `Market` > `SCAN` and create an SSA with the software claim `typekey`. The Typekey flow will register its own client via DCR with the SSA you provide in the configuration.

- [Dynamic Client Registration specification](https://www.rfc-editor.org/rfc/rfc7591#section-3.1)

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
      "scan_ssa": "",
      "authHost": "https://account.gluu.org",
      "scanHost": "https://cloud.gluu.org",
      "phrases": {
        "1": "itwasthebestoftimes",
        "2": "itwastheworstoftimes"
      }
    }
}
```

### Configuration details

- `keystoreName` and `keystorePassword` are optional, in case you want to include a signature when sending the Typekey data. Leave them as blank otherwise.
- `orgId` is the organization ID that can be obtained by decoding the software statement JWT and looking at the `org_id` claim (You may use `https://jwt.io` to decode the SSA).
- `scan_ssa` is the JWT string you obtain from Agama Lab
- `authHost` and `scanHost` can be left as is
- `phrases` is explained in the [Details](#details) section

- We go back to the TUI and click on `Import Configuration` and select the modified configuration file with our parameters.
- With this, our `agama project` is now configured and we can start testing.

## Testing

You'll need an OpenID Connect test RP. You can try [oidcdebugger](https://oidcdebugger.com/),
[jans-tarp](https://github.com/JanssenProject/jans/tree/main/demos/jans-tarp)
or [jans-tent](https://github.com/JanssenProject/jans/tree/main/demos/jans-tent).

Launch an authorization flow with parameters `acr_values=agama&agama_flow=org.gluu.agama.typekey` with your chosen RP.

## Details
The first time a user starts the Typekey flow, Typekey will choose a random phrase from the `phrases` dict in the configuration and store it in persistence. Then, the Typekey API is called to provide the keystroke data recorded during the flow. The first 5 times, Typekey API will train on the data provided. This phase is called "Enrollment". On the 6th attempt onward, Typekey API will validate the provided keystroke data using the training data stored during enrollment. If the behavioral data is sufficiently different from the trained data, Typekey API will deny the request.
In case Typekey API denies the request, Agama Typekey falls back to password authentication, and retrains the API on the provided data.

## Examples

Enrollment:


https://github.com/SafinWasi/agama-typekey/assets/6601566/2256877b-3b49-48d8-b292-3d9da4a3a4c5



Typekey API approval:



https://github.com/SafinWasi/agama-typekey/assets/6601566/de5dcb19-9fbb-41f3-b897-606fc52fce85




Typekey API denied, fallback to password:



https://github.com/SafinWasi/agama-typekey/assets/6601566/b0288f5c-6a84-4ea0-b6a4-ac9052409189



# Contributors

<table>
<tr>
    <td align="center" style="word-wrap: break-word; width: 150.0; height: 150.0">
        <a href=https://github.com/SafinWasi>
            <img src=https://avatars.githubusercontent.com/u/6601566?v=4 width="100;"  style="border-radius:50%;align-items:center;justify-content:center;overflow:hidden;padding-top:10px" alt=SafinWasi/>
            <br />
            <sub style="font-size:14px"><b>SafinWasi</b></sub>
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
