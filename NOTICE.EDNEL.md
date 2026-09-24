# Modification notice (EUPL 1.2, Art. 5)

**This is a modified version of SIMPL contract-consumption-be. It is not the original work.**

The original work, contract-consumption-be, is part of the SIMPL programme (© European Union /
SIMPL Programme) and is licensed under the **European Union Public Licence v. 1.2 (EUPL-1.2)**. See
[LICENSE](LICENSE) for the licence of the work and [NOTICE](NOTICE) / [NOTICE.json](NOTICE.json) /
[THIRD_PARTY_LICENCES.md](THIRD_PARTY_LICENCES.md) for the third-party components included in it.
All original copyright, licence and disclaimer notices are kept intact and unmodified in this fork.

## Upstream baseline

| | |
|---|---|
| Original work | contract-consumption-be (`eu.europa.ec.simpl.contract:contract-consumption-be`) |
| Upstream repository | https://code.europa.eu/simpl/simpl-open/development/data1/contract-consumption-be |
| Baseline version | `1.20.0` development line (last release in the upstream changelog: `1.19.0`, 2026-03-27) |
| Baseline commit | `ecf9d4f5ed18d8928b1070cd2a5f7cd1a5735516` (2026-04-17) |

## Modifications

| | |
|---|---|
| Modified by | EDNEL-RIOJA project team, for CNIE-ES |
| Public repository of this derivative work | https://github.com/cnie-es/simpl-contract-consumption-be |
| Version of this derivative work | published from this repository's release tags; each container image carries the tag it was built from |
| Dates of modification | **2026-05-19 to 2026-09-10** |

The modifications are licensed under the **EUPL-1.2**, the same licence as the original work.

The complete source code of this derivative work is available at the public repository above as a
vetted release snapshot, and will remain freely available there for as long as the Work is
distributed. The upstream repository and exact baseline commit are recorded above. Each release
snapshot includes `SBOM.cyclonedx.json`, which binds the upstream and work revisions, release tag,
public repository, snapshot hash and image digest. The distribution history contains release
snapshots rather than a copy of the upstream Git history, so the complete set of changes is the
diff between the baseline commit `ecf9d4f`, fetched from its authoritative upstream repository,
and the published snapshot. The tables below record what each file contributed. The published
container images (`ghcr.io/cnie-es/contract-consumption-be`) are built from that snapshot.

### Summary of the changes

Two new **data destination sharing methods** for consumption, plus the plumbing the consumer needs
to use them:

- **REST_API destination** (template id 14): registered as a sharing method in
  `resourceAddress-config.yml` and exposed through the API, with its destination template and UI
  schema. The template uses the `HttpProxy` type instead of the original push-oriented fields, and
  the destination was moved from the APPLICATION module to the DATA module.
- **AMAZON_S3 destination** (template id 15): new destination template and UI schema; the existing
  `BULK_S3` template and schema were adjusted alongside it.
- **EDR retrieval**: new `GET /transfers/{id}/edr` endpoint returning the Endpoint Data Reference
  for `HTTP_DATA_PROXY` transfers, with the `EdrDto` model, the OpenAPI description and unit tests.
- `consumerEmail` removed from the API.
- A **GitHub Actions pipeline** that builds and publishes the container image, on tags or on
  demand, with manual platform selection, and a `Makefile` with the testing tools.

A second, non-functional group of changes (2026-09-10) adds the notices this licence requires of a
derivative work: this file, the notice at the top of [README.md](README.md), the licence and
source-code metadata in `pom.xml` and in the OCI labels of the `Dockerfile`, and the reproduction of
the full official licence text inside [LICENSE](LICENSE), which previously only linked to it. See
[CHANGELOG.md](CHANGELOG.md) for the itemised list.

### Files added

| File | Date |
|---|---|
| `Makefile` | 2026-05-19 |
| `src/main/java/eu/europa/ec/simpl/contract/consumption/model/transfer/EdrDto.java` | 2026-05-19 |
| `.github/workflows/build-image-on-tag.yml` | 2026-05-20 (updated 2026-09-10) |
| `src/main/resources/resourceaddress/template/TEMPLATE_DATA_REST_API_DESTINATION_14.json` | 2026-05-22 |
| `src/main/resources/resourceaddress/ui-schema/UI_SCHEMA_DATA_REST_API_DESTINATION_14.json` | 2026-05-22 |
| `src/main/resources/resourceaddress/template/TEMPLATE_DATA_AMAZON_S3_DESTINATION_15.json` | 2026-06-15 |
| `src/main/resources/resourceaddress/ui-schema/UI_SCHEMA_DATA_AMAZON_S3_DESTINATION_15.json` | 2026-06-15 |
| `NOTICE.EDNEL.md` (this file) | 2026-09-10 |

### Files modified

| File | Date |
|---|---|
| `openapi/openapi-contract-consumption-be-tier1-v1.yaml` | 2026-05-19 |
| `src/main/java/eu/europa/ec/simpl/contract/consumption/client/ConnectorAdapterClient.java` | 2026-05-19 |
| `src/main/java/eu/europa/ec/simpl/contract/consumption/controller/v1/ResourceAddressController.java` | 2026-05-19 |
| `src/main/java/eu/europa/ec/simpl/contract/consumption/controller/v1/TransferProcessController.java` | 2026-05-19 |
| `src/main/java/eu/europa/ec/simpl/contract/consumption/controller/v1/TransferProcessControllerImpl.java` | 2026-05-19 |
| `src/main/java/eu/europa/ec/simpl/contract/consumption/service/transferprocess/TransferProcessService.java` | 2026-05-19 |
| `src/main/java/eu/europa/ec/simpl/contract/consumption/service/transferprocess/TransferProcessServiceImpl.java` | 2026-05-19 |
| `src/test/java/eu/europa/ec/simpl/contract/consumption/controller/v1/ResourceAddressControllerTest.java` | 2026-05-19 |
| `src/test/java/eu/europa/ec/simpl/contract/consumption/controller/v1/TransferProcessControllerTest.java` | 2026-05-19 |
| `src/test/java/eu/europa/ec/simpl/contract/consumption/service/transferprocess/TransferProcessServiceTest.java` | 2026-05-19 |
| `src/test/java/eu/europa/ec/simpl/contract/consumption/service/resourceaddress/ResourceAddressServiceTest.java` | 2026-05-19, 2026-05-25 |
| `src/main/resources/resourceAddress-config.yml` | 2026-05-19, 2026-05-21, 2026-06-15 |
| `Dockerfile` | 2026-05-20, 2026-09-10 |
| `src/main/resources/resourceaddress/template/TEMPLATE_DATA_BULK_S3_DESTINATION_7.json` | 2026-06-15 |
| `src/main/resources/resourceaddress/ui-schema/UI_SCHEMA_DATA_BULK_S3_DESTINATION_7.json` | 2026-06-15 |
| `LICENSE` | 2026-09-10 |
| `README.md` | 2026-09-10 |
| `CHANGELOG.md` | 2026-09-10 |
| `pom.xml` | 2026-09-10 |
| `charts/Chart.yaml` | 2026-09-10 |
| `charts/templates/deployment.yaml` | 2026-09-10 |
| `charts/values.yaml` | 2026-09-10 |
| `pipeline.variables.sh` | 2026-09-10 |

No file of the original work has been removed, and no copyright, licence or disclaimer notice of
the original work has been altered. The only change to [LICENSE](LICENSE) is the addition, below
the original heading and credits line, of the full official text of the EUPL-1.2 that the file
previously referenced only by hyperlink; nothing in the original file was removed or reworded.

The per-file diff for every change is obtainable with:

```
git diff ecf9d4f5ed18d8928b1070cd2a5f7cd1a5735516..ednel
```

For a published image, substitute the release tag it was built from for `ednel`.
