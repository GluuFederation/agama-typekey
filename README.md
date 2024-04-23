# Agama Typekey 

[![Contributors][contributors-shield]][contributors-url]
[![Forks][forks-shield]][forks-url]
[![Stargazers][stars-shield]][stars-url]
[![Issues][issues-shield]][issues-url]
[![Apache License][license-shield]][license-url]

This project allows you to record behavioral keystroke data and use it as a second factor of authentication by leveraging the Typekey API. For more information you can check:
* [Details](./details.md)
* [SCAN Documentation](.)
* [Dynamic Client Registration specification](https://www.rfc-editor.org/rfc/rfc7591#section-3.1)

## Requirements

* [agama-typekey](https://github.com/GluuFederation/agama-typekey)
* A SCAN subscription. Please visit [Agama Lab](https://gluu.org/agama-lab) and sign up for a free SCAN subscription, which gives you 500 credits. Each successful Typekey API call costs 4 credits.

### Software Statement Assertion

In order to call the Typekey API, you will need an OAuth client. Once you have a SCAN subscription on Agama Lab, navigate to `Market` > `SCAN` and create an SSA with the software claim `typekey`. The Typekey flow will register its own client via DCR with the SSA you provide in the configuration.

## Supported IDPs

| IDP                             | Description                                    |
| :------------------------------ |:-----------------------------------------------| 
| Jans Auth Server                |[Deployment instructions](./jans-deployment.md) | 
| Gluu Flex                       |[Deployment instructions](.)                    | 


## Flows

| Qualified Name           | Description       |
|:------------------------ |:------------------| 
| `org.gluu.agama.typekey` | Full Typekey flow |


## Configuration

| Flow                     | Property        | Value Description            |
| ------------------------ |:---------------:| :---------------------------:|
| `org.gluu.agama.typekey` | keystoreName    | Keystore Name (optional)     |
| `org.gluu.agama.typekey` | keystorePasword | Keystore Password (optional) |
| `org.gluu.agama.typekey` | orgId           | `org_id` claim from SSA      |
| `org.gluu.agama.typekey` | scan_ssa        | SSA string from Agama Lab    |
| `org.gluu.agama.typekey` | authHost        | Authorization Server         |
| `org.gluu.agama.typekey` | scanHost        | SCAN server                  |
| `org.gluu.agama.typekey` | phrases         | Phrases dictionary           |


## Sample JSON
```json
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
- `phrases` is a dictionary of strings from which the phrase used for behavioral metrics is selected. The dictionary pairs must be in format `string:string` where keys are unique numbers in string format and values are unique phrases. 

## Details

The first time a user starts the Typekey flow, Typekey will choose a random phrase from the `phrases` dict in the configuration and store it in persistence. Then, the Typekey API is called to provide the keystroke data recorded during the flow. The first 5 times, Typekey API will train on the data provided. This phase is called "Enrollment". On the 6th attempt onward, Typekey API will validate the provided keystroke data using the training data stored during enrollment. If the behavioral data is sufficiently different from the trained data, Typekey API will deny the request.

In case Typekey API denies the request, Agama Typekey falls back to password authentication, and retrains the API on the provided data.

## Demo 

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

This project is licensed under the [Apache 2.0](./LICENSE)

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
