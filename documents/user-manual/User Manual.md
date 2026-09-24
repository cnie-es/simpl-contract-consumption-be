# User Manual

After completing the catalog search in either Quick or Advanced mode, the user can proceed by initiating a **Contract Negotiation** process, followed by the **Data Transfer** through the steps described below.

### 1. Initiate a Contract Offer
- Click **Request resource**.
- A contract offer will appear after a short loading time.

![RequestResource](images/request_resource.png)

### 2. Respond to the Contract Offer
- **Decline**: Closes the modal.
- **Accept**: Starts the contract negotiation process and redirects to the **Contract Negotiation Status Page**.

The page refreshes every 3 seconds to update the status until it becomes **FINALIZED**. Once finalized, the **Start Transfer** button will appear.

![Initiate](images/initiate.png)

### 3. Start the Transfer
Click **Start Transfer** to open the **Data Destination Form**, which varies by offering type:

![Start the Transfer](images/start_the_transfer.png)

#### Data Offerings
- Fill out the fields manually or use the hidden field at the top to input a full JSON payload.
- Example JSON:
  ```json
  {
    "type": "IonosS3",
    "region": "de",
    "consumerEmail": "",
    "storage": "s3-eu-central-1.ionoscloud.com",
    "bucketName": "simpl-cons",
    "objectName": "transfer_process_test.csv",
    "path": "folder123456789/",
    "keyName": "test-key-name"
  }
- You can modify the path, and it'll change what folder the asset will be sent. ObjectName doesn't have an effect, the filename will be what the asset was registered with. Then scroll down to the bottom of the form, and click the "Start Transfer" button.

### 4. Transfer process status
You'll be redirected to the transfer process status page:

![Transfer process status](images/transfer_process_status.png)

The page refreshes every 3 seconds automatically until the "DEPROVISIONED" or "TERMINATED" states are reached. You can refresh the page manually too.

## Things to Note

### Infrastructure Offering Data Destination Form
An infrastructure offering will show this form for the Data Destination. It only needs an email address.

![Infrastructure Email Address](images/infrastructure_email_address.png)

### Keeping State in the URLs
The contract negotiation and transfer processes have their own page with the ID in the URL. This is a straightforward and simple solution to keep "persistent state" without using cookies or browser local storage. This way, the browser "keeps the state" in the URL, and the user can navigate between states by just clicking "back" in the browser. You can also check your browser history for previous processes.

This is not an ideal solution, but it was fast. It can be changed in the future. Here are some example pages for previous negotiations and transfers:

- [Contract Negotiation - Infrastructure](https://catalogue-ui.dev.simpl-europe.eu/contract-negotiation/infrastructure/e025d7f6-71e8-4a65-8489-8271e2d19e15)
- [Contract Negotiation - Data](https://catalogue-ui.dev.simpl-europe.eu/contract-negotiation/data/f8551e3d-da6a-4741-b9d9-659a790b18bb)
- [Transfer Status](https://catalogue-ui.dev.simpl-europe.eu/transfer/status/2d697755-9449-4348-b980-ef1f18779fb3)

These are only available after the processes were started with their respective POST requests. After that, they're open to the public and not tied to the logged-in user, which could be a security issue in the future.

### Improvement Needed: Contract Offers Accept
When clicking the "Accept" button on the contract offer, it'll take a few seconds, and there's no indication in the UI. There should be something indicating that the user has done something. Without feedback, users might click the button more than once and start multiple contract negotiations.

Possible improvements:
- The button should be disabled after clicking it once.
- A loading animation should appear to indicate the transfer process is being started.
- After a few seconds, the user should be redirected to the contract negotiation page.

### Possible Issues
When inputting the data address in the "Data transfer" window, none of the fields are checked for completeness, even though most of them are required.

Following the OPENAPI description related to the API exposed by microservice
[OpenAPI](../../openapi/openapi-v1.yaml)
