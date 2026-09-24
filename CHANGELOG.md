## 1.20.0-edval (2026-09-10)

> Derivative work by the **EDNEL-RIOJA** project team for **CNIE-ES**, based on the upstream
> contract-consumption-be `1.20.0` development line (commit `ecf9d4f`). Modified between
> **2026-05-19 and 2026-09-10**, licensed under EUPL-1.2 like the original work. See
> [NOTICE.EDNEL.md](NOTICE.EDNEL.md) for the full modification notice.

### Added (2026-05-19 → 2026-06-15)

- **REST_API data destination** (template id 14): registered as a sharing method in
  `resourceAddress-config.yml` and exposed through the API, with its destination template and UI
  schema. The template uses the `HttpProxy` type instead of the original push-oriented fields, and
  the destination was later moved from the APPLICATION module to the DATA module.
- **AMAZON_S3 data destination** (template id 15): new destination template and UI schema.
- **EDR retrieval**: `GET /transfers/{id}/edr` returns the Endpoint Data Reference for
  `HTTP_DATA_PROXY` transfers, with the `EdrDto` model and the OpenAPI description.
- Unit tests for the REST_API sharing method and for EDR retrieval, and a `Makefile` with the
  testing tools.
- GitHub Actions pipeline building and publishing the container image on tags or on demand, with
  manual platform selection.

### Changed (2026-05-25 → 2026-06-15)

- `HttpData-PULL` method updated in the destination template.
- `BULK_S3` template and UI schema adjusted alongside the new AMAZON_S3 destination.
- `consumerEmail` removed from the API.

### Licence compliance (2026-09-10)

Notices required by Art. 5 of the EUPL-1.2 (Attribution right, Provision of Source Code) for this
derivative work:

- `NOTICE.EDNEL.md`: modification notice stating that the work has been modified, by whom, when and
  what was changed, with the repository where the complete corresponding source code is available.
- `README.md`: prominent notice at the top of the file identifying this repository as a modified
  version of contract-consumption-be, plus a Licence section.
- `LICENSE`: the full official text of the EUPL-1.2 is now reproduced in the file, which previously
  only linked to it, so that a copy of the Licence travels with every copy of the Work. The
  original SIMPL heading and credits line are kept intact.
- `Dockerfile`: OCI image labels (`licenses`, `source`, `vendor`, `description`) and `LICENSE`,
  `NOTICE` and `NOTICE.EDNEL.md` copied into the image, so the notices and the pointer to the
  source code travel with the published container image.
- `pom.xml`: the project coordinates move from `eu.europa.ec.simpl.contract:contract-consumption-be`
  to `es.cnie.simpl.contract:simpl-contract-consumption-be`,
  so that a modified artifact is not identified under a namespace belonging to the licensor
  (Art. 5, Legal Protection); `licenses`, `scm`, `url` and `developers` metadata filled in. The
  `${env.PROJECT_RELEASE_VERSION}` version is deliberately kept: unlike other components, here it
  is functional, resolved from the build argument the Dockerfile and the pipeline pass in.
- `.github/workflows/build-image-on-tag.yml`: the container image namespace is derived from the
  repository owner instead of being hard-coded, so that the published image and the source code it
  is built from always live in the same organisation.
- Helm chart made loadable outside the upstream GitLab pipeline: `Chart.yaml` and `values.yaml`
  carried unsubstituted `${PROJECT_RELEASE_VERSION}` and `${CI_REGISTRY_IMAGE}` placeholders, which
  that pipeline replaced and which made the chart fail to load anywhere else.
- The version of this fork, `1.20.0-edval`, is now stated consistently: in the chart `version` and
  `appVersion`, and in `pipeline.variables.sh`, which the image copies in and which reports the
  running version. Both still declared the upstream `1.20.0`, so the chart offered to deploy, and
  the service would have reported, a version number belonging to the original work.


## 1.19.0 (2026-03-27)

### added (2 changes)

- [[SIMPL-20780](https://jira.simplprogramme.eu/browse/SIMPL-20780) [BE] Add...](https://code.europa.eu/simpl/simpl-open/development/data1/contract-consumption-be/-/commit/04129540941fac032e96e84c8dc1c98f769f1fc2) ([merge request](https://code.europa.eu/simpl/simpl-open/development/data1/contract-consumption-be/-/merge_requests/83))
- [[SIMPL-14653](https://jira.simplprogramme.eu/browse/SIMPL-14653) Added new script.](https://code.europa.eu/simpl/simpl-open/development/data1/contract-consumption-be/-/commit/7b12305d0b722867d67d253b25aa150d7296ac99) ([merge request](https://code.europa.eu/simpl/simpl-open/development/data1/contract-consumption-be/-/merge_requests/83))

### fixed (1 change)

- [[SIMPL-25289](https://jira.simplprogramme.eu/browse/SIMPL-25289)...](https://code.europa.eu/simpl/simpl-open/development/data1/contract-consumption-be/-/commit/fa2ad2dac1ed0c7160fe5364b453dd7aa14568e8) ([merge request](https://code.europa.eu/simpl/simpl-open/development/data1/contract-consumption-be/-/merge_requests/83))


## 1.11.0 (2025-09-04)

### added (1 change)

- [[SIMPL-17313](https://jira.simplprogramme.eu/browse/SIMPL-17313) Create...](https://code.europa.eu/simpl/simpl-open/development/data1/contract-consumption-be/-/commit/e23cd93ae8076bd5aeeeffa01628dee0d24bed89) ([merge request](https://code.europa.eu/simpl/simpl-open/development/data1/contract-consumption-be/-/merge_requests/61))



## 1.10.2 (2025-08-06)

### fixed (3 changes)

- [[SIMPL-16125](https://jira.simplprogramme.eu/browse/SIMPL-16125) Entry/Acceptance Criteria Report](https://code.europa.eu/simpl/simpl-open/development/data1/contract-consumption-be/-/commit/087a13a9daae7241842ff48aae43b6889f4e1eef) ([merge request](https://code.europa.eu/simpl/simpl-open/development/data1/contract-consumption-be/-/merge_requests/59))
- [[SIMPL-16125](https://jira.simplprogramme.eu/browse/SIMPL-16125) Entry/Acceptance Criteria Report](https://code.europa.eu/simpl/simpl-open/development/data1/contract-consumption-be/-/commit/498a4037e6b9dcfd57f8fe9c5839fa1796236655) ([merge request](https://code.europa.eu/simpl/simpl-open/development/data1/contract-consumption-be/-/merge_requests/59))
- [[SIMPL-16125](https://jira.simplprogramme.eu/browse/SIMPL-16125) Entry/Acceptance Criteria Report](https://code.europa.eu/simpl/simpl-open/development/data1/contract-consumption-be/-/commit/dff9622f2082ecda3476bf9c81af4dfc08e38cea) ([merge request](https://code.europa.eu/simpl/simpl-open/development/data1/contract-consumption-be/-/merge_requests/59))


## 1.10.1 (2025-08-04)

### changed (1 change)

- [[SIMPL-16125](https://jira.simplprogramme.eu/browse/SIMPL-16125)](https://code.europa.eu/simpl/simpl-open/development/data1/contract-consumption-be/-/commit/1a348ed0015d09cdb0969b2486f7c690ed66197d) ([merge request](https://code.europa.eu/simpl/simpl-open/development/data1/contract-consumption-be/-/merge_requests/56))


## 1.10.0 (2025-08-01)

### fixed (3 changes)

- [[SIMPL-15891](https://jira.simplprogramme.eu/browse/SIMPL-15891) Hardcoded values in ingresses.](https://code.europa.eu/simpl/simpl-open/development/data1/contract-consumption-be/-/commit/dfd8c35ec512446648bcc5270015ad301202968c) ([merge request](https://code.europa.eu/simpl/simpl-open/development/data1/contract-consumption-be/-/merge_requests/45))
- [[SIMPL-14722](https://jira.simplprogramme.eu/browse/SIMPL-14722) Resolve](https://code.europa.eu/simpl/simpl-open/development/data1/contract-consumption-be/-/commit/3e97e48b33766867ffce7133e65896a18e46f1a7) ([merge request](https://code.europa.eu/simpl/simpl-open/development/data1/contract-consumption-be/-/merge_requests/45))
- [[SIMPL-4720](https://jira.simplprogramme.eu/browse/SIMPL-4720) Update](https://code.europa.eu/simpl/simpl-open/development/data1/contract-consumption-be/-/commit/cefc05092a8dbdb31ee0df381e90967dbb1494db) ([merge request](https://code.europa.eu/simpl/simpl-open/development/data1/contract-consumption-be/-/merge_requests/45))


## 1.9.0 (2025-07-10)

### added (2 changes)

- [[SIMPL-10304](https://jira.simplprogramme.eu/browse/SIMPL-10304) Extend the...](https://code.europa.eu/simpl/simpl-open/development/data1/contract-consumption-be/-/commit/082a9df37c2c3af98c236e2efa846af600abfa46) ([merge request](https://code.europa.eu/simpl/simpl-open/development/data1/contract-consumption-be/-/merge_requests/41))
- [[SIMPL-10304](https://jira.simplprogramme.eu/browse/SIMPL-10304) Extend the...](https://code.europa.eu/simpl/simpl-open/development/data1/contract-consumption-be/-/commit/a2ed839da718a7c72f0cc46ef06b259ec8b60ada) ([merge request](https://code.europa.eu/simpl/simpl-open/development/data1/contract-consumption-be/-/merge_requests/41))


## 1.8.0 (2025-06-19)

### added (1 change)

- [[SIMPL-13521](https://jira.simplprogramme.eu/browse/SIMPL-13521) Added ArgoCD manifests.](https://code.europa.eu/simpl/simpl-open/development/data1/contract-consumption-be/-/commit/b1cda24197212e06e5b088482090c1490420c7cc) ([merge request](https://code.europa.eu/simpl/simpl-open/development/data1/contract-consumption-be/-/merge_requests/39))

### changed (2 changes)

- [error responses aligned to belgif problem](https://code.europa.eu/simpl/simpl-open/development/data1/contract-consumption-be/-/commit/afdd57d729e893b7961c8aad57a0252a5d2eacf3) ([merge request](https://code.europa.eu/simpl/simpl-open/development/data1/contract-consumption-be/-/merge_requests/39))
- [aligned to simpl-data1-common version 1.1.0 to support belgif problem](https://code.europa.eu/simpl/simpl-open/development/data1/contract-consumption-be/-/commit/4421b5a930ab03d8cce25dff233cad0fc62494f2) ([merge request](https://code.europa.eu/simpl/simpl-open/development/data1/contract-consumption-be/-/merge_requests/39))


## 1.7.0 (2025-05-29)

### changed (3 changes)

- [[SIMPL-12727](https://jira.simplprogramme.eu/browse/SIMPL-12727)](https://code.europa.eu/simpl/simpl-open/development/data1/contract-consumption-be/-/commit/670b085878c9b99a5ffbd48647af559fca28841a) ([merge request](https://code.europa.eu/simpl/simpl-open/development/data1/contract-consumption-be/-/merge_requests/37))
- [[SIMPL-13575](https://jira.simplprogramme.eu/browse/SIMPL-13575) Service Account management.](https://code.europa.eu/simpl/simpl-open/development/data1/contract-consumption-be/-/commit/753e20a87ea4082861f4047448dd6dfbe1b98214) ([merge request](https://code.europa.eu/simpl/simpl-open/development/data1/contract-consumption-be/-/merge_requests/37))
- [[SIMPL-13575](https://jira.simplprogramme.eu/browse/SIMPL-13575) Service Account management.](https://code.europa.eu/simpl/simpl-open/development/data1/contract-consumption-be/-/commit/7756ed616f30cc1e74f77ce046cc3ec5fe13b615) ([merge request](https://code.europa.eu/simpl/simpl-open/development/data1/contract-consumption-be/-/merge_requests/37))

### added (4 changes)

- [[SIMPL-10304](https://jira.simplprogramme.eu/browse/SIMPL-10304) Extend](https://code.europa.eu/simpl/simpl-open/development/data1/contract-consumption-be/-/commit/fe05132d1323404dcbad2c260d05b0bf31fbbe33) ([merge request](https://code.europa.eu/simpl/simpl-open/development/data1/contract-consumption-be/-/merge_requests/37))
- [[SIMPL-13198](https://jira.simplprogramme.eu/browse/SIMPL-13198) [API](https://code.europa.eu/simpl/simpl-open/development/data1/contract-consumption-be/-/commit/b54922119d8b6a3fe794eb87addc4c78878f9734) ([merge request](https://code.europa.eu/simpl/simpl-open/development/data1/contract-consumption-be/-/merge_requests/37))
- [[SIMPL-13198](https://jira.simplprogramme.eu/browse/SIMPL-13198) Extend](https://code.europa.eu/simpl/simpl-open/development/data1/contract-consumption-be/-/commit/22ac51a370e59dff1a1eff93976ffafd55701407) ([merge request](https://code.europa.eu/simpl/simpl-open/development/data1/contract-consumption-be/-/merge_requests/37))
- [[SIMPL-12999](https://jira.simplprogramme.eu/browse/SIMPL-12999) Config](https://code.europa.eu/simpl/simpl-open/development/data1/contract-consumption-be/-/commit/7e08e56117bece78001462642a63ed1d34a60fc2) ([merge request](https://code.europa.eu/simpl/simpl-open/development/data1/contract-consumption-be/-/merge_requests/37))


## 1.6.2 (2025-05-09)

### added (1 change)

- [[SIMPL-12186](https://jira.simplprogramme.eu/browse/SIMPL-12186) Enable](https://code.europa.eu/simpl/simpl-open/development/data1/contract-consumption-be/-/commit/6868aace8e9bbd8aa64d2178cec58b396f7d18e4) ([merge request](https://code.europa.eu/simpl/simpl-open/development/data1/contract-consumption-be/-/merge_requests/35))


## 1.6.1 (2025-05-08)

No changes.


## 1.6.0 (2025-05-07)

### changed (3 changes)

- [[SIMPL-12215](https://jira.simplprogramme.eu/browse/SIMPL-12215)](https://code.europa.eu/simpl/simpl-open/development/data1/contract-consumption-be/-/commit/4a59ea3c106c58292c82827ccfb9389c94f74abe) ([merge request](https://code.europa.eu/simpl/simpl-open/development/data1/contract-consumption-be/-/merge_requests/30))
- [[SIMPL-12003](https://jira.simplprogramme.eu/browse/SIMPL-12003) Modified...](https://code.europa.eu/simpl/simpl-open/development/data1/contract-consumption-be/-/commit/ee148f7d4411f9f717cb03e6d9d2276f9e3d7534) ([merge request](https://code.europa.eu/simpl/simpl-open/development/data1/contract-consumption-be/-/merge_requests/31))
- [[SIMPL-12726](https://jira.simplprogramme.eu/browse/SIMPL-12726)](https://code.europa.eu/simpl/simpl-open/development/data1/contract-consumption-be/-/commit/ac41c2f66b6a9d994b09b20ada4f754aa7a94b99) ([merge request](https://code.europa.eu/simpl/simpl-open/development/data1/contract-consumption-be/-/merge_requests/30))


## 1.5.1 (2025-03-31)

No changes.


## 1.5.0 (2025-03-28)

### added (7 changes)

- [[SIMPL-11442](https://jira.simplprogramme.eu/browse/SIMPL-11442) Added Spotless and Palantir.](https://code.europa.eu/simpl/simpl-open/development/data1/contract-consumption-be/-/commit/1a9924d57c415863cbd65429243a7a1aa3ef0f5d) ([merge request](https://code.europa.eu/simpl/simpl-open/development/data1/contract-consumption-be/-/merge_requests/23))
- [[SIMPL-11037](https://jira.simplprogramme.eu/browse/SIMPL-11037)](https://code.europa.eu/simpl/simpl-open/development/data1/contract-consumption-be/-/commit/21cf758b8e74e21074793b396030002c4f03274a) ([merge request](https://code.europa.eu/simpl/simpl-open/development/data1/contract-consumption-be/-/merge_requests/23))
- [added new keys in values.yaml for kafka sasl auth](https://code.europa.eu/simpl/simpl-open/development/data1/contract-consumption-be/-/commit/dcd15a08be22c85605b51e79243f852ede94d9f7) ([merge request](https://code.europa.eu/simpl/simpl-open/development/data1/contract-consumption-be/-/merge_requests/23))
- [Implemented the /status endpoint [SIMPL-10614]](https://code.europa.eu/simpl/simpl-open/development/data1/contract-consumption-be/-/commit/43ecb9c483d33f90a06f2d4341d401f766b4172a) ([merge request](https://code.europa.eu/simpl/simpl-open/development/data1/contract-consumption-be/-/merge_requests/23))
- [added new charts values.yaml key 'openapiConfig.servers'](https://code.europa.eu/simpl/simpl-open/development/data1/contract-consumption-be/-/commit/e2d76fd25e75e21c87fc7740ce4a04be2f9864a2) ([merge request](https://code.europa.eu/simpl/simpl-open/development/data1/contract-consumption-be/-/merge_requests/23))
- [added new values.yaml properties for KAFKA integration](https://code.europa.eu/simpl/simpl-open/development/data1/contract-consumption-be/-/commit/7b8448933eee64c0463399fc569930a43fc188eb) ([merge request](https://code.europa.eu/simpl/simpl-open/development/data1/contract-consumption-be/-/merge_requests/23))
- [added kafka deprovisioning](https://code.europa.eu/simpl/simpl-open/development/data1/contract-consumption-be/-/commit/7fb2ae76f56bb2e85ce82540d384caaa9d7e0753) ([merge request](https://code.europa.eu/simpl/simpl-open/development/data1/contract-consumption-be/-/merge_requests/23))

### changed (1 change)

- [README.md updated](https://code.europa.eu/simpl/simpl-open/development/data1/contract-consumption-be/-/commit/3ca5cbbfd3655503e94651f067bca662fd8b3370) ([merge request](https://code.europa.eu/simpl/simpl-open/development/data1/contract-consumption-be/-/merge_requests/23))

### updated (1 change)

- [[SIMPL-4266](https://jira.simplprogramme.eu/browse/SIMPL-10721)](https://code.europa.eu/simpl/simpl-open/development/data1/contract-consumption-be/-/commit/4c1330ca88ffe227f872068e002056cd8e6db763) ([merge request](https://code.europa.eu/simpl/simpl-open/development/data1/contract-consumption-be/-/merge_requests/23))

### fixed (1 change)

- [[SIMPL-4266](https://jira.simplprogramme.eu/browse/SIMPL-4266) Rename](https://code.europa.eu/simpl/simpl-open/development/data1/contract-consumption-be/-/commit/271106bb2e27328e702607b29c9ebb9e6dac8d7b) ([merge request](https://code.europa.eu/simpl/simpl-open/development/data1/contract-consumption-be/-/merge_requests/23))

