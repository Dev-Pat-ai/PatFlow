# PatFlow — Phase 1: Project Foundation

This is the Phase 1 deliverable, built strictly against the locked Phase 0
documents (`PatFlow-Phase0-Architecture.md` and `PatFlow-Design-System.md`).
No screens or business logic yet — this is theme + local data foundation only.

## What's in this phase

- **Gradle project setup** — version catalog-free `build.gradle.kts` files, Hilt,
  Room + KSP, Compose, Vico, kotlinx-datetime, Navigation Compose, WorkManager,
  Security Crypto/Biometric dependencies wired per Architecture §10. minSdk 26,
  targetSdk 36.
- **`core/theme/`** — `Color.kt`, `Type.kt`, `Shape.kt`, `Theme.kt` implementing
  Design System §1–§5 exactly, including the **Loan → Brown** and
  **Savings → Teal** category-color resolution agreed at Phase 0 lock (see
  `Color.kt` for the specific hex values and rationale comments).
- **`core/` scaffolding** — `common/` (Result wrapper, DispatcherProvider,
  BaseUseCase contract), `navigation/` (Destinations + a stub NavGraph),
  `constants/` (AppConstants, PreferenceKeys for DataStore).
- **Complete local data layer** (this phase's main deliverable):
  - **14 Room entities** under `data/local/entity/` — every table from
    Architecture §8.2–§8.7, including the FTS4 search table and all v2 sync
    scaffold / installment-bill reserved columns.
  - **`Converters.kt`** — TypeConverters bridging `kotlinx.datetime.LocalDate`/
    `LocalDateTime` to the ISO-8601 TEXT columns Room persists (Architecture §10
    chose kotlinx-datetime over `java.time` in the domain layer).
  - **9 DAOs** under `data/local/dao/` — basic CRUD only (insert/update/delete/
    getById/getAll + the handful of range/status queries the Architecture doc
    calls out by name, e.g. `bill_cycle(due_date)`). No business logic, no
    status-recalculation, no cycle-generation — that lands with the Bills
    feature.
  - **`PatFlowDatabase.kt`** — registers all entities + DAOs + converters,
    version 1, `exportSchema = true`. An (empty) `PatFlowMigrations` object is
    already in place so the first real schema change adds a migration rather
    than reaching for destructive fallback.
- **Hilt DI modules** — `DatabaseModule` (provides the DB + all DAOs),
  `DispatcherModule`, `DataStoreModule`, and an intentionally-empty
  `RepositoryModule` reserved for the Bills feature.
- **App shell** — `PatFlowApplication` (`@HiltAndroidApp`), `MainActivity`
  hosting a stub `NavGraph` that boots straight to a Dashboard placeholder.

## Explicitly NOT in this phase

- `core/components/` (BillCard, StatusChip, etc.) — next, once this is reviewed.
- Repository implementations, use cases, mappers, ViewModels, real screens.
- Category seeding, cycle generation, status computation — all business logic.
- Roboto Flex font files — `Type.kt` uses `FontFamily.Default` as a placeholder
  until the variable-font assets are added in an asset pass.

## Two items logged as future considerations (per Phase 0 lock — non-blocking)

1. `budget` has no `currency_code` column — noted in a comment on
   `BudgetEntity.kt`.
2. No dedicated one-off income-entry nav route yet — noted in
   `Destinations.kt`'s companion; will be added as `income_entry/{entryId}`
   when the Income feature is built.

## Verification note

This environment doesn't have the Android SDK or network access to Google's/
Maven Central's repositories, so **this project has not been Gradle-built or
run**. Every file was hand-reviewed for consistency (package names, imports,
FK references, DAO/entity/database wiring), but please open it in Android
Studio and let Gradle sync before writing feature code, in case anything
surfaces that only a real compile would catch.

## Opening the project

1. Unzip, open the `PatFlow/` folder in Android Studio (Ladybug or newer).
2. Let Gradle sync — it will pull dependencies from `google()`/`mavenCentral()`.
3. Build once to confirm `PatFlowDatabase` compiles (KSP generates the Room
   implementation) before starting on Bills.
