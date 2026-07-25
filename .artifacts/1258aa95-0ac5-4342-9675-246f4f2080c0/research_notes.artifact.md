# Phase 14 — App Header & Top App Bar Refinement Research

## Current State Analysis

### `AppTopBar` Implementation
- Located in `AppNavigation.kt`.
- Uses `CenterAlignedTopAppBar`.
- Title style is `titleLarge` with `Bold`.
- `containerColor` is `MaterialTheme.colorScheme.background`.
- Does not explicitly handle `WindowInsets` beyond defaults.

### Dashboard Screen
- Uses `AppTopBar`.
- `DashboardLoading` has 16dp padding.
- `DashboardContent` (LazyColumn) has 16dp content padding.
- Search icon is provided in `actions`.

### Money Screen
- Uses `AppTopBar` + `AppSegmentedControl` inside a `Column` in `topBar` slot.
- `AppSegmentedControl` has 16dp horizontal padding.
- Content Column has 16dp padding (from Scaffold).

### Calendar Screen
- Uses `AppTopBar`.
- `CalendarHeader` has 16dp padding.
- `CalendarGrid` and `AgendaView` have 16dp horizontal padding.

### Reports Screen
- Uses `AppTopBar`.
- `ReportFilterSection` has 16dp horizontal padding.
- `ReportContent` (LazyColumn) has 16dp content padding.

### Settings Screen
- Uses `AppTopBar`.
- Navigation icon (Back) is provided.
- Sections and cards have 16dp horizontal padding.

## Key Requirements for Refinement
- **Alignment**: Titles closer to status bar, left-aligned (not centered).
- **Components**: `TopAppBar`, `MediumTopAppBar`, or `LargeTopAppBar`.
- **Insets**: Use `statusBarsPadding()` or `WindowInsets.safeDrawing`.
- **Spacing**: 24dp horizontal padding (`space5`), 12-16dp spacing below top bar (`space3` or `space4`).
- **Typography**: `headlineMedium` or `titleLarge`, SemiBold/Bold.
- **Audit**: Remove `Spacer(height = 40.dp)` or hardcoded `padding(top = 48.dp)`.

## Identified Issues
1. `CenterAlignedTopAppBar` is used everywhere, which doesn't match the Google Wallet/Calendar style for top-level screens.
2. Horizontal padding is mostly 16dp (`space4`), requirement is 24dp (`space5`).
3. `WindowInsets` handling is implicit; could be improved for "premium" feel.
4. `DashboardLoading` might have excessive padding if it doesn't account for Scaffold padding correctly.
