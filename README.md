# Event Registration System Backend

**Student:** Teta Kevine  
**Student ID:** 27973  
**Project:** Event Registration System  
**Course:** INSY 8322 Web Technology and Internet  

## Description
This is a Spring Boot REST API for managing event registrations. Participants can register for events managed by organizers who are assigned to halls and hold various event types.

## Technologies
- Java 17
- Spring Boot 4.1.1
- Spring Data JPA
- PostgreSQL
- Maven

## How to Run

1. **Install PostgreSQL** and create a database:
   ```sql
   CREATE DATABASE event_db;
   ```

2. **Configure database** in `src/main/resources/application.properties`:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/event_db
   spring.datasource.username=postgres
   spring.datasource.password=12345
   ```

3. **Run the application:**
   ```bash
   ./mvnw spring-boot:run
   ```

4. **Test with Postman** at `http://localhost:8080`

## API Endpoints

### Event Types - `/api/event-types`
- POST `/save` - Create event type
- GET `/all` - Get all event types
- GET `/{id}` - Get by ID
- PUT `/update/{id}` - Update
- DELETE `/delete/{id}` - Delete
- GET `/unused` - Event types nobody holds (C3)

### Halls - `/api/halls`
- POST `/save` - Create hall
- GET `/all` - Get all halls
- GET `/{id}` - Get by ID
- PUT `/update/{id}` - Update
- DELETE `/delete/{id}` - Delete
- GET `/free` - Free halls (C2)

### Organizers - `/api/organizers`
- POST `/save` - Create organizer
- GET `/all` - Get all organizers
- GET `/{id}` - Get by ID
- PUT `/update/{id}` - Update
- DELETE `/delete/{id}` - Delete
- GET `/by-event-type?name=` - Organizers by event type (C1)
- PUT `/{organizerId}/event-types/{eventTypeId}` - Add event type (D3)
- PATCH `/{organizerId}/hall?hallNumber=` - Change hall (D4)

### Participants - `/api/participants`
- POST `/save` - Create participant
- GET `/all` - Get all participants
- GET `/{id}` - Get by ID
- PUT `/update/{id}` - Update
- DELETE `/delete/{id}` - Delete
- GET `/by-last-name?lastName=` - Find by last name (B1)
- GET `/of-organizer/{organizerId}` - Participants of organizer (C4)

### Registrations - `/api/registrations`
- POST `/save` - Create registration with business rules (D1)
- GET `/all` - Get all registrations
- GET `/{id}` - Get by ID
- PUT `/update/{id}` - Update (does not change status)
- DELETE `/delete/{id}` - Delete (protected if has certificates - E3)
- GET `/by-status?status=` - Filter by status (B2)
- GET `/between?start=&end=` - Date range filter (B3)
- PATCH `/{id}/status?status=` - Change status (D2)
- GET `/stats/by-status` - Count per status (F1)
- PATCH `/cancel-day?organizerId=&date=` - Cancel organizer day (F2)
- GET `/page?page=0&size=5&sort=eventDate,desc` - Pagination (Bonus)

### Certificates - `/api/certificates`
- POST `/save?registrationId=` - Write certificate (E1)
- GET `/participant/{email}` - Certificate history (E2)
- DELETE `/delete/{id}` - Delete certificate (E3)

### Dashboard - `/api/dashboard`
- GET `/summary` - Dashboard summary (Bonus)
