# Backend training examples

This repository contains a small sample FOLIO backend module (`mod-training-sample`, a book storage
API) used to train new developers. The lessons that use it are in
[ua-folio-docs → training/backend](https://github.com/ualibweb/ua-folio-docs/tree/main/training/backend);
start with `00-prereqs.md`.

## Quick start

You need **Java 21**, **Maven 3.8+**, and **Docker Desktop** (running). See the lessons for install
steps.

```sh
mvn test
```

The integration tests start their own temporary PostgreSQL database in Docker, so Docker must be
running. The first run downloads dependencies and a PostgreSQL image and can take several minutes.

To run the module locally, you also need a PostgreSQL database (the lessons show how to start one in
Docker). Copy `.env.sample` to `.env`, adjust it if needed, then use the **Run API Server**
configuration in VS Code. The module listens on `http://localhost:8081`; every request needs an
`x-okapi-tenant` header (for example `x-okapi-tenant: diku`).

## Notes

- This is a simplified training module. It uses Spring Boot 3.0 and compiles for Java 17 bytecode,
  while current FOLIO modules (Trillium release) use Spring Boot 4 and Java 21. It builds and runs
  on Java 21, and the concepts in the lessons are the same.
- Lombok must be 1.18.30 or later to compile on Java 21, and Testcontainers 1.21.4 or later to work
  with Docker Engine 29+. Keep these in mind if you change dependency versions.
