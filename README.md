# Agama Typekey

[![Contributors][contributors-shield]][contributors-url]
[![Forks][forks-shield]][forks-url]
[![Stargazers][stars-shield]][stars-url]
[![Issues][issues-shield]][issues-url]
[![Apache License][license-shield]][license-url]

## About Agama-typekey

This repo is home to the Gluu Agama-typekey project. This project allows you to
Record behavioral keystroke data and use it as a second factor of authentication
by leveraging the Typekey API.




## Where To Deploy

The project can be deployed to any IAM server that runs an implementation of 
the [Agama Framework](https://docs.jans.io/head/agama/introduction/) like 
[Janssen Server](https://jans.io) and [Gluu Flex](https://gluu.org/flex/).


## How To Deploy

Different IAM servers may provide different methods and 
user interfaces from where an Agama project can be deployed on that server. 
The steps below show how the Agama-typekey project can be deployed on the 
[Janssen Server](https://jans.io). 

Deployment of an Agama project involves three steps.

- [Downloading the `.gama` package from the project repository](#download-the-project)
- [Adding the `.gama` package to the IAM server](#add-the-project-to-the-server)
- [Configure the project](#configure-the-project)


#### Pre-Requisites

* SCAN subscription: Visit [Agama Lab](https://gluu.org/agama-lab) 
and sign up for a free SCAN subscription, which gives you 500 credits. 
Each successful Typekey API call costs 4 credits.ts. 


##### Software Statement Assertion

In order to call the Typekey API, you will need an OAuth client. Once you have 
a SCAN subscription on Agama Lab, navigate to `Market` > `SCAN` and create an 
SSA with the software claim `typekey`. The Typekey flow will register its own 
client via DCR with the SSA you provide in the configuration. 


### Download the Project

> [!TIP]
> Skip this step if you use the Janssen Server TUI tool to 
> configure this project. The TUI tool enables the download and adding of this 
> project directly from the tool, as part of the `community projects` listing. 

The project is bundled as 
[.gama package](https://docs.jans.io/head/agama/gama-format/). 
Visit the `Assets` section of the 
[Releases](https://github.com/GluuFederation/agama-typekey/releases) to download 
the `.gama` package.

### Add The Project To The Server

The Janssen Server provides multiple ways an Agama project can be 
deployed and configured. Either use the command-line tool, REST API, or a 
TUI (text-based UI). Refer to 
[Agama project configuration page](https://docs.jans.io/head/admin/config-guide/auth-server-config/agama-project-configuration/) in the Janssen Server documentation for more details.

### Configure The Project

The Agama project accepts configuration parameters in the JSON format. Every Agama 
project comes with a basic sample configuration file for reference.

Below is a typical configuration of the Agama-typekey project. As shown, it contains
configuration parameters for the [flows contained in it](#flows-in-the-project):

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
Check the flow detail section for details about configuration parameters.


### Test The Flow

Use any relying party implementation (like [jans-tarp](https://github.com/JanssenProject/jans/tree/main/demos/jans-tarp)) 
to send an authentication request that triggers the flow.

From the incoming authentication request, the Janssen Server reads the `ACR` 
parameter value to identify which authentication method should be used. 
To invoke the `org.gluu.agama.typekey` flow contained in the Agama-typekey project, 
specify the ACR value as `agama_<qualified-name-of-the-top-level-flow>`, 
i.e `agama_org.gluu.agama.typekey`.


## Customize and Make It Your Own

Fork this repo to start customizing the Agama-PW project. It is possible to 
customize the user interface provided by the flow to suit your organization's 
branding 
guidelines. Or customize the overall flow behavior. Follow the best 
practices and steps listed 
[here](https://docs.jans.io/head/admin/developer/agama/agama-best-practices/#project-reuse-and-customizations) 
to achieve these customizations in the best possible way.
This project can be reused in other Agama projects to create more complex
authentication journeys. To re-use, trigger the [org.gluu.agama.typekey](#orggluuagamatypekey) 
flow from other Agama projects.

To make it easier to visualize and customize the Agama Project, use 
[Agama Lab](https://cloud.gluu.org/agama-lab/login).


## Flows In The Project

List of the flows: 

- [org.gluu.agama.typekey](#orggluuagamatypekey)


### org.gluu.agama.typekey

The first time a user starts the Typekey flow, Typekey will choose a random 
phrase from the `phrases` dict in the configuration and store it in persistence. 
Then, the Typekey API is called to provide the keystroke data recorded during 
the flow. The first 5 times, the Typekey API will train on the data provided. 
This phase is called "Enrollment". On the 6th attempt onward, Typekey API will 
validate the provided keystroke data using the training data stored during 
enrollment. If the behavioral data is sufficiently different from the trained 
data, Typekey API will deny the request.

In case the Typekey API denies the request, Agama Typekey falls back to password authentication, and retrains the API on the provided data.


#### Parameter Details


| Name | Description | Notes |
| ----------------- | --------------------------------------------------------------------- | --------------------------------------- |
| `keystoreName` | Optional name for the keystore if you want to include a signature when sending Typekey data. | Leave blank if not needed.|
| `keystorePassword` | Optional password for the keystore for signature inclusion. | Leave blank if not needed.|
| `orgId` | Organization ID obtained by decoding the software statement JWT.| Look for the org_id claim; use https://jwt.io to decode.|
| `scan_ssa` | JWT string obtained from Agama Lab.| |
| `authHost` | Host URL for authentication. | Can be left as is.|
| `scanHost` | Host URL for scanning. | Can be left as is.|
| `phrases` | Dictionary of strings for selecting phrases used in behavioral metrics. | Use format string:string with unique keys and values.|



## Demo


Check out this video to see the **agama-typekey** authentication flow in action.
Also check the
[Agama Project Of The Week](https://gluu.org/agama-project-of-the-week/) video
series for a quick demo on this flow.

*Note:*
While the video shows how the flow works overall, it may be dated. Do check the
[Test the Flow](#test-the-flow) section to understand the current
method of passing the ACR parameter when invoking the flow.

### Enrollment

https://github.com/SafinWasi/agama-typekey/assets/6601566/2256877b-3b49-48d8-b292-3d9da4a3a4c5



### Typekey API approval



https://github.com/SafinWasi/agama-typekey/assets/6601566/de5dcb19-9fbb-41f3-b897-606fc52fce85




### Typekey API denied, fallback to password


https://github.com/SafinWasi/agama-typekey/assets/6601566/b0288f5c-6a84-4ea0-b6a4-ac9052409189



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
