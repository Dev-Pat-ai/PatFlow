# PatFlow — Complete Personal Finance Management

PatFlow is a comprehensive, production-ready Android application for managing bills, income, budgets, and savings goals. Built with a clean architecture and an offline-first philosophy, it provides users with secure, local-first financial tracking.

## Core Features

- **Bill Management**: Track recurring and one-off bills with automatic cycle generation and reminders.
- **Income Tracking**: Log one-off income or create recurring templates with automatic background entry generation.
- **Budget Planner**: Create monthly, weekly, or custom budgets with category-level spending limits and real-time usage forecasting.
- **Savings Goals**: Plan for the future with goal-oriented savings, contribution tracking, and progress analytics.
- **Dashboard & Insights**: At-a-glance financial status with smart, on-device insights and automated spending trends.
- **Financial Calendar**: A unified timeline of all financial events including due dates, payments, and income.
- **Data Portability**: Secure JSON Backup/Restore and CSV export for all modules.
- **Modern UI**: Full Material 3 support with Light/Dark modes, Material You dynamic coloring, and haptic feedback.

## Tech Stack (Architecture §10)

- **UI**: Jetpack Compose with Material 3.
- **Navigation**: Navigation Compose.
- **DI**: Hilt with Hilt-Work integration.
- **Database**: Room (14+ entities, FTS4 search).
- **Background**: WorkManager for reminders, overdue checks, and auto-generation.
- **State**: Kotlin Coroutines & Flow (StateFlow/SharedFlow).
- **Date/Time**: `kotlinx-datetime` for domain-pure date management.
- **Charts**: Vico (v2.0 Cartesian API).
- **Preferences**: DataStore (Proto-backed).
- **Security**: Biometric-ready and encrypted backup support foundation.

## Quality Assurance

- **Unit Testing**: Mappers, UseCases, and Domain logic.
- **UI Testing**: Compose UI rules for critical screens.
- **Database Testing**: Room in-memory testing for DAOs and transactions.
- **Production Hardening**: Passes Android Lint, adheres to standard Kotlin formatting, and includes KDoc coverage for all core repositories.

## Getting Started

1. Open the project in **Android Studio (Ladybug or newer)**.
2. Let Gradle sync dependencies.
3. Run `app:assembleDebug` to verify the build.
4. Run `./gradlew test` to execute the unit test suite.

## Project History

This project was developed in 12 distinct phases, strictly adhering to the initial Architecture and Design System requirements locked at Phase 0.

- **Phase 1-2**: Foundation & Bills
- **Phase 3-4**: Payments & History
- **Phase 5-6**: Search, Filters & Reports
- **Phase 7**: Settings & Data Management
- **Phase 8-9**: Notifications & Income
- **Phase 10-11**: Budgets, Savings & Insights
- **Phase 12**: QA & Production Release

PatFlow v1.0 is stable, secure, and ready for deployment.
