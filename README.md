# Approval System - Backend

## Descripción

Sistema de flujo de aprobaciones para solicitudes de compra, donde 3  aprobadores validan mediante OTP y se genera un PDF de evidencia al finalizar y se da por completado el flujo.

## Tecnologías

- Java 17
- Spring Boot 3
- Spring Data JPA
- H2 Database
- Mockito + JUnit 5
- Jacoco (cobertura de pruebas)
- iTextPDF


## Flujo de aprobación

### 1. Creación de solicitud
- Se crea con título, descripción y monto
- Estado inicial: `PENDING`

### 2. Generación de aprobadores
- Se crean 3 aprobadores automáticamente
- Cada uno tiene:
  - Email simulado
  - Token único
  - OTP generado

### 3. Aprobación con OTP
- El aprobador ingresa OTP
  Puede:
  - Aprobar → `APPROVED`
  - Rechazar → `REJECTED`

### 4. Estados del proceso

- `PENDING`: en curso
- `REJECTED`: si alguno rechaza
- `COMPLETED`: si todos aprueban

### 5. Generación de PDF

Cuando todos aprueban:
- Se genera un PDF con:
  - Datos de la solicitud
  - Aprobadores
  - Estados
  Se guarda en: mock/pdfs/

## Endpoints

### Crear solicitud
POST /api/purchase-requests

### Obtener solicitud
GET /api/purchase-requests/{id}

### Aprobar solicitud
POST /api/purchase-requests/approval

### Descargar PDF
GET /api/purchase-requests/{id}/evidencia.pdf

## Testing

- Tests unitarios con Mockito
- Cobertura: 80%
Ejecutar:
./gradlew clean test jacocoTestReport

Reporte:
build/reports/jacoco/test/html/index.html

Ejecutar proyecto
./gradlew bootRun
