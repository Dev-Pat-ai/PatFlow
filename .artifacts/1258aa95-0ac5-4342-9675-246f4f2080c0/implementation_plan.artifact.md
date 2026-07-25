# Implementation Plan - Money Section Overhaul (Bills Tab)

Overhaul the "Bills" tab in the Money section to match the provided high-fidelity design, including a new overview card, sectioned lists, and comprehensive bottom sheets for adding and viewing bills.

## User Review Required

> [!IMPORTANT]
> - **Schema Change**: I will add `accountNumber` and `billReference` fields to the Bill database table. This will require a database migration or a clear-all-data step if auto-migration is enabled.
> - **Navigation Change**: The top-level tabs (**Bills, Income, Savings, Budgets**) will be styled to match the pill-based design in the screenshot.
> - **UX Change**: Adding a bill will now be handled via a bottom sheet instead of a separate screen (or updating the existing screen to look like the sheet).

## Proposed Changes

### Data & Domain
#### [MODIFY] [BillEntity.kt](file:///C:/Users/charm/Documents/GitHub/PatFlow/app/src/main/java/com/patflow/app/data/local/entity/BillEntity.kt)
- Add `account_number: String?` and `bill_reference: String?` columns.

#### [MODIFY] [Bill.kt](file:///C:/Users/charm/Documents/GitHub/PatFlow/app/src/main/java/com/patflow/app/domain/model/Bill.kt)
- Add `accountNumber: String?` and `billReference: String?` to the domain model.

#### [MODIFY] [BillMappers.kt](file:///C:/Users/charm/Documents/GitHub/PatFlow/app/src/main/java/com/patflow/app/data/mapper/BillMappers.kt) (and related)
- Update mapping logic to include the new fields.

### UI Components
#### [NEW] [BillsOverviewCard.kt](file:///C:/Users/charm/Documents/GitHub/PatFlow/app/src/main/java/com/patflow/app/feature/bills/components/BillsOverviewCard.kt)
- Remaining vs Paid bills stats.
- Linear progress bar.
- Total count.

#### [MODIFY] [BillCard.kt](file:///C:/Users/charm/Documents/GitHub/PatFlow/app/src/main/java/com/patflow/app/core/components/BillCard.kt)
- Update to support the new layout: Icon on left, Title/Category/Amount in center/right, Status chip at the bottom left of text.

#### [NEW] [BillSheets.kt](file:///C:/Users/charm/Documents/GitHub/PatFlow/app/src/main/java/com/patflow/app/feature/bills/components/BillSheets.kt)
- `BillDetailBottomSheet`: Grid of bill metadata + actions.
- `AddBillBottomSheet`: Category grid + form fields.

### Feature: Money
#### [MODIFY] [MoneyScreen.kt](file:///C:/Users/charm/Documents/GitHub/PatFlow/app/src/main/java/com/patflow/app/feature/money/MoneyScreen.kt)
- Refine header layout and tab styling.

#### [MODIFY] [BillListScreen.kt](file:///C:/Users/charm/Documents/GitHub/PatFlow/app/src/main/java/com/patflow/app/feature/bills/BillListScreen.kt)
- Implement the sectioned layout:
    - Search & Filter row.
    - "Due Soon" section (with "View all").
    - "All Bills" section (with sorting).
    - Integrated bottom sheets for Detail and Add actions.

## Verification Plan

### Automated Tests
- Build check: `gradle_build(":app:assembleDebug")`.
- Verify database migration by launching the app and checking for crashes.

### Manual Verification
- **Visual Audit**: Compare the new Bills tab against the provided screenshot (spacing, colors, typography).
- **Functionality**:
    - Tap a bill to open the **Detail Bottom Sheet**.
    - Click "+ Add Bill" to open the **Add Bottom Sheet**.
    - Swipe a bill row to verify **Edit/Delete** actions.
    - Toggle filters (Overdue, Due Soon, Paid) and verify list updates.
