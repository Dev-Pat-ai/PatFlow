# Walkthrough - Phase 3: Domain & Data Layer Implementation

I have successfully implemented the Domain and Data layers for the Bills feature, following Clean Architecture and the approved Phase 0 Architecture.

## Changes Made

### 1. Domain Layer
- **Models**: Created foundational domain models that are independent of any Android framework classes.
    - [Bill.kt](file:///C:/Users/charm/Documents/GitHub/PatFlow/app/src/main/java/com/patflow/app/domain/model/Bill.kt): Represents the recurring bill template.
    - [BillCycle.kt](file:///C:/Users/charm/Documents/GitHub/PatFlow/app/src/main/java/com/patflow/app/domain/model/BillCycle.kt): Represents a specific instance of a bill (e.g., this month's electricity bill).
    - [Category.kt](file:///C:/Users/charm/Documents/GitHub/PatFlow/app/src/main/java/com/patflow/app/domain/model/Category.kt): Represents bill/income categories.
    - [Enums.kt](file:///C:/Users/charm/Documents/GitHub/PatFlow/app/src/main/java/com/patflow/app/domain/model/Enums.kt): Moved `RecurrenceType`, `BillStatus`, and `PaymentMethod` to the domain layer.
- **Repositories**: Defined interfaces for data operations.
    - [BillRepository.kt](file:///C:/Users/charm/Documents/GitHub/PatFlow/app/src/main/java/com/patflow/app/domain/repository/BillRepository.kt)
    - [CategoryRepository.kt](file:///C:/Users/charm/Documents/GitHub/PatFlow/app/src/main/java/com/patflow/app/domain/repository/CategoryRepository.kt)
- **Use Cases**: Implemented business logic for bill management.
    - `GetBillsUseCase`, `GetBillDetailUseCase`, `AddBillUseCase`, `UpdateBillUseCase`, `DeleteBillUseCase`, `MarkBillAsPaidUseCase`.

### 2. Data Layer
- **Mappers**: [BillMapper.kt](file:///C:/Users/charm/Documents/GitHub/PatFlow/app/src/main/java/com/patflow/app/data/mapper/BillMapper.kt) handles conversion between Room Entities and Domain Models.
- **Repository Implementations**:
    - [BillRepositoryImpl.kt](file:///C:/Users/charm/Documents/GitHub/PatFlow/app/src/main/java/com/patflow/app/data/repository/BillRepositoryImpl.kt): Implements complex operations like `markCycleAsPaid` and handles initial cycle generation on bill creation.
    - [CategoryRepositoryImpl.kt](file:///C:/Users/charm/Documents/GitHub/PatFlow/app/src/main/java/com/patflow/app/data/repository/CategoryRepositoryImpl.kt).
- **Database Seeding**:
    - [DatabaseSeeder.kt](file:///C:/Users/charm/Documents/GitHub/PatFlow/app/src/main/java/com/patflow/app/data/local/database/DatabaseSeeder.kt): Contains the list of 11 predefined categories.
    - [DatabaseModule.kt](file:///C:/Users/charm/Documents/GitHub/PatFlow/app/src/main/java/com/patflow/app/di/DatabaseModule.kt): Updated with a `RoomDatabase.Callback` to seed categories on first database creation.

### 3. Build & DI
- **RepositoryModule**: [RepositoryModule.kt](file:///C:/Users/charm/Documents/GitHub/PatFlow/app/src/main/java/com/patflow/app/di/RepositoryModule.kt) now binds the repository implementations to their interfaces.
- The project builds successfully with these changes.

## Database Schema & Relationships

The database uses a **Template-Instance** pattern:
- `BillEntity`: Stores the recurring template (recurrence rule, default amount).
- `BillCycleEntity`: Stores specific occurrences generated from a `Bill`. Each cycle has its own `amount_due`, `amount_paid`, and `status`.
- `PaymentEntity`: Stores individual payments logged against a `BillCycle`.

**Relationships:**
- `BillCategory` (1) <-> (*) `Bill`
- `Bill` (1) <-> (*) `BillCycle`
- `BillCycle` (1) <-> (*) `Payment`

## Repository Flow

The flow follows standard Clean Architecture patterns:
1. **Room (DAOs)**: Provides raw entity access.
2. **Repository Implementation**:
    - Fetches data from DAOs.
    - Uses **Mappers** to transform Entities into Domain Models.
    - Coordinates multiple DAO calls (e.g., `markCycleAsPaid` updates both `Payment` and `BillCycle` tables).
3. **Use Cases**:
    - Request data from the **Repository Interface**.
    - Encapsulate specific business rules (e.g., combining bill info with its cycles).
4. **Presentation Layer** (to be implemented): Will interact only with Use Cases.

---

I have verified the build and the core logic is now in place. We can now proceed to the **Presentation Layer** to build the UI for the Bills feature.
