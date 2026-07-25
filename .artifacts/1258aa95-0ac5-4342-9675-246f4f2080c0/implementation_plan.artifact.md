# Implementation Plan - Category Color Refresh for User Trust

Overhaul the application's category color palette to be more vibrant, professional, and trustworthy, addressing feedback that the current icons feel "dull."

## User Review Required

> [!IMPORTANT]
> - **Brand Identity**: We are shifting from a muted/pastel palette to a more vivid "Material 3 High Contrast" palette. This is designed to build trust by using clear, intentional colors associated with financial stability and modern tech.
> - **Consistency**: These changes will affect every screen where categories are shown (Dashboard, Money, Reports, Add/Edit sheets).

## Proposed Changes

### Theme & Colors
#### [MODIFY] [Color.kt](file:///C:/Users/charm/Documents/GitHub/PatFlow/app/src/main/java/com/patflow/app/core/theme/Color.kt)
- Redefine `PatFlowCategoryColors` with more vibrant, distinct pairings.
- Ensure `containerColor` has enough saturation to "pop" without being overwhelming.
- Maintain accessibility by keeping high contrast between `onColor` and `containerColor`.

**New Color Strategy (Light Mode Examples):**
| Category | Current Style | New "Trust" Style | Reason |
| :--- | :--- | :--- | :--- |
| **Electricity** | Muted Gold | **Vivid Amber (0xFFFFAB00)** | Energy & Awareness |
| **Water** | Soft Blue | **Deep Azure (0xFF0288D1)** | Reliable Utility |
| **Insurance** | Sky Blue | **Royal Navy (0xFF1A237E)** | Security & Protection |
| **Savings** | Pale Teal | **Emerald Green (0xFF00C853)** | Wealth & Growth |
| **Loan** | Muted Red | **Deep Burgundy (0xFFB71C1C)** | Financial Seriousness |
| **Internet** | Indigo | **Electric Violet (0xFF6200EA)** | Modern Technology |

### UI Polish (Subtle Enhancements)
#### [MODIFY] [BillCard.kt](file:///C:/Users/charm/Documents/GitHub/PatFlow/app/src/main/java/com/patflow/app/core/components/BillCard.kt)
- Ensure the icon background container uses the new vibrant colors.
- (Optional) Slightly increase the icon size or weight if needed for more presence.

#### [MODIFY] [BillSheets.kt](file:///C:/Users/charm/Documents/GitHub/PatFlow/app/src/main/java/com/patflow/app/feature/bills/components/BillSheets.kt)
- Update the category picker grid to use the new palette.
- Ensure the detail view's header icon reflects the high-trust colors.

## Verification Plan

### Automated Tests
- Build check: `gradle_build(":app:assembleDebug")`.

### Manual Verification
- **Visual Audit**: Compare "Before vs After" of the category list in the Add Bill sheet.
- **Accessibility Check**: Verify text readability on all new category containers using standard contrast ratios.
- **Theme Check**: Switch between Light and Dark mode to ensure the "trust" palette remains effective in both.
