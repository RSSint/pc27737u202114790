# AquaMonitor Sampling Plans

## Overview

AquaMonitor provides a REST API for registering water quality sampling plans for monitoring stations. The service validates each plan and stores it in MySQL.

## Technology

- Java 25
- Spring Boot and Spring Data JPA
- MySQL
- Jakarta Bean Validation

## Run locally

Requirements: Java 25 and Docker Compose.

From the project root, run the Gradle wrapper:

```powershell
.\gradlew.bat bootRun
```

The Spring Boot Docker Compose integration uses the MySQL service in `compose.yaml`. The API listens on `http://localhost:8080`.

## API

### Create a sampling plan

`POST /api/v1/sampling-plans`

Send `Content-Type: application/json`. Do not include `id`; the database generates it.

```json
{
  "planCode": "SAM-001",
  "stationId": "550e8400-e29b-41d4-a716-446655440000",
  "samplingWindow": {
    "startDate": "2026-11-21",
    "endDate": "2026-11-25"
  },
  "sampleVolume": {
    "quantity": 10,
    "unit": "LITER"
  },
  "priorityLevel": 3,
  "status": "PENDING",
  "samplingNotes": "Water quality sampling for the north river station."
}
```

`status` is optional and defaults to `PENDING`. Supported values are `PENDING`, `APPROVED`, `COMPLETED`, and `CANCELLED`. Dates use the ISO 8601 `yyyy-MM-dd` format.

The endpoint returns `201 Created` with the saved plan and generated `id`. Invalid input returns `400 Bad Request`, an existing `planCode` returns `409 Conflict`, and unexpected errors return `500 Internal Server Error`. Error responses include a localized `message` property.

Set `Accept-Language` to `en-US` or `es-PE` to receive English or Spanish validation and error messages.

## Architecture

The `monitoring` bounded context separates REST interfaces, application commands and services, domain model and repository port, and JPA persistence adapters. Reusable platform components are provided by `shared`.

## Authorship

- Author: RSSint
- Student code: U202114790
- Section: 7737
- GitHub repository: [https://github.com/RSSint/pc27737u202114790.git](https://github.com/RSSint/pc27737u202114790.git)
