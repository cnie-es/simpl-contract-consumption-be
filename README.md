# Contract Consumption BE

> ⚠️ **Modified work — CNIE-ES fork.**
> This repository is **not** the original SIMPL contract-consumption-be. It is a derivative work
> based on the upstream `1.20.0` development line (commit `ecf9d4f`,
> [upstream](https://code.europa.eu/simpl/simpl-open/development/data1/contract-consumption-be)),
> modified by the EDNEL-RIOJA project team for CNIE-ES between **2026-05-19 and 2026-09-10** to add
> the REST_API and AMAZON_S3 data destinations and EDR retrieval for consumption. Distributed under
> the **EUPL-1.2**, the same licence as the original work. Full details of what was changed and
> when: [NOTICE.EDNEL.md](NOTICE.EDNEL.md).

> **Purpose**: The ContractConsumption BE component exposes the API services that allow the consumer to initiate ContractNegotiation and TransferProcess workflows.

---

## 📑 Table of Contents

1. [Overview](#overview)
2. [Prerequisites](#prerequisites)
3. [⚡ Quick Start](#-quick-start)
   - [Use as a Dependency](#use-as-a-dependency)
   - [Run Locally](#run-locally)
4. [Installation guide](#installation--guide)
5. [Configuration](#configuration)
6. [User Guide](#user--guide)
7. [Testing](#testing)
8. [Contributing](#contributing)
9. [Contact & Support](#contact--support)

---

## Overview

The **ContractConsumption BE** component provides backend services that expose API endpoints enabling the **initiation** and **monitoring** of ContractNegotiation and TransferProcess workflows. These APIs are consumed by the client-catalogue frontend, allowing users to start new negotiations or transfers and to track their status and outcomes in real time.

**Key Features**
- API endpoints to initiate and manage contract negotiation and data transfer processes.
- Status and monitoring APIs for retrieving the real-time state of ongoing negotiations and transfers.
- Integration with the EDC Consumer Connector to handle the technical execution of negotiation and data exchange protocols.
- Process tracking and auditability, ensuring full lifecycle visibility and compliance with SIMPL governance policies.
- Error management and logging for operational traceability and diagnostic support.
- Secure access control, allowing only authorized frontend clients or services to initiate or query contractual operations.

**Relation to other Simpl-Open agents or modules**

The component is deployed within the **Consumer Agent** and exposes its APIs through the **Tier-1 Gateway**. It directly invokes the services of the **EDC Consumer Connector**, which is installed within the same agent, to execute the underlying negotiation and transfer processes. This setup enables seamless interaction between the client-catalogue frontend, the contract management functions, and the data exchange infrastructure, ensuring end-to-end automation and governance within the SIMPL ecosystem.

---

## Prerequisites

```bash
Java 21+
Maven 3.9+
Access to EU GitLab Package Registry (for repo declared in POM file)
IDE with plugin Lombok enabled (IntelliJ/Eclipse/VS Code)
Enabled connectivity with Kafka cluster
Enabled connectivity with EDC Consumer Connector
```

---

## ⚡ Quick Start

## Installation Guide

The instructions for running the application locally can be found in the following file → [Installation Guide](documents/installation-guide/Installation%20Guide.md)

---

## Configuration

The instructions for setting config parameters can be found in the following file → [Configuration Parameters](documents/installation-guide/Installation%20Guide.md#configuration)

---

## Deployment Guide

The instructions for setting up configuration and deploy in Kubernetes cluster can be found in the following file → [Deployment Guide](documents/deployment-guide/Deployment%20Guide.md)

---

## Upgrade Guide

At the following link, you can find the guide that outlines the changes made in the latest version, including configuration updates, integrations with other systems, and new or modified functionalities, to facilitate the setup of the application within the target environment. → [Upgrade Guide](documents/upgrade-guide/Upgrade%20Guide.md)

---

## User guide

At the following link, you can see how the catalog’s front-end interface, accessed by a consumer participant, uses the APIs exposed by this microservice. → [User Guide](documents/user-manual/User%20Manual.md)

---

## Testing

Testing is covered through the CI/CD pipeline associated with the GIT repository.
This pipeline automatically runs Unit Tests, SAST (Static Application Security Testing) using SonarQube, and security tests performed with Fortify.

---

## Contributing

At the following link, you can find all the information related to the delivery process management adopted for Simpl-Open across its various components.
[Release Management](https://confluence.simplprogramme.eu/display/SIMPL/2050+-+Release+mgnt)



---

## Contact & Support

- **Maintainers**: `Data1 Team`

---

## Licence

The original work, contract-consumption-be, is © European Union / SIMPL Programme and is licensed
under the **European Union Public Licence v. 1.2 (EUPL-1.2)**, whose full official text is
reproduced in [LICENSE](LICENSE). Third-party components included in the product are listed in
[NOTICE](NOTICE) / [NOTICE.json](NOTICE.json) / [THIRD_PARTY_LICENCES.md](THIRD_PARTY_LICENCES.md).

This fork is a **modified version** of that work, distributed under the same licence. The
modification notice required by Art. 5 of the EUPL (what was modified, by whom and when) is in
[NOTICE.EDNEL.md](NOTICE.EDNEL.md). The complete corresponding source code, including the revision
history, is available at https://github.com/cnie-es/simpl-contract-consumption-be.

---
