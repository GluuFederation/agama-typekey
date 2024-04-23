# Deployment

Download the latest [agama-typekey.gama](https://github.com/GluuFederation/agama-typekey/releases/latest/download/agama-typekey.gama) file and deploy it in Auth Sever.

Follow the steps below:

- Copy (SCP/SFTP) the gama file of this project to a location in your `Jans Server`
- Connect (SSH) to your `Jans Server` and open TUI: `python3 /opt/jans/jans-cli/jans_cli_tui.py`
- Navigate to the `Agama` tab and then select `"Upload project"`. Choose the gama file
- Wait for about one minute and then select the row in the table corresponding to this project
- Press `v` and ensure there were not deployment errors
- Pres `ESC` to close the dialog

# Configure Typekey 

- Open TUI and navigate to `Agama`
- Select the deployed project and hit `c`
- Select `Export sample configuration` and select a directory and a filename
- Open the file in an editor
- Enter the details and save the file
- Open TUI, navigate to Agama, select the deployed project and hit `c`
- Select `Import configuration` and select the edited file.
