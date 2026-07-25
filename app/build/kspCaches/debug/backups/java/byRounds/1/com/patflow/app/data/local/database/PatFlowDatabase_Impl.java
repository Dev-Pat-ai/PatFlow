package com.patflow.app.data.local.database;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.FtsTableInfo;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import com.patflow.app.data.local.dao.BillCycleDao;
import com.patflow.app.data.local.dao.BillCycleDao_Impl;
import com.patflow.app.data.local.dao.BillDao;
import com.patflow.app.data.local.dao.BillDao_Impl;
import com.patflow.app.data.local.dao.BudgetDao;
import com.patflow.app.data.local.dao.BudgetDao_Impl;
import com.patflow.app.data.local.dao.CategoryDao;
import com.patflow.app.data.local.dao.CategoryDao_Impl;
import com.patflow.app.data.local.dao.IncomeDao;
import com.patflow.app.data.local.dao.IncomeDao_Impl;
import com.patflow.app.data.local.dao.PaymentDao;
import com.patflow.app.data.local.dao.PaymentDao_Impl;
import com.patflow.app.data.local.dao.ReminderDao;
import com.patflow.app.data.local.dao.ReminderDao_Impl;
import com.patflow.app.data.local.dao.SavingsGoalDao;
import com.patflow.app.data.local.dao.SavingsGoalDao_Impl;
import com.patflow.app.data.local.dao.SearchDao;
import com.patflow.app.data.local.dao.SearchDao_Impl;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class PatFlowDatabase_Impl extends PatFlowDatabase {
  private volatile CategoryDao _categoryDao;

  private volatile BillDao _billDao;

  private volatile BillCycleDao _billCycleDao;

  private volatile PaymentDao _paymentDao;

  private volatile BudgetDao _budgetDao;

  private volatile ReminderDao _reminderDao;

  private volatile IncomeDao _incomeDao;

  private volatile SavingsGoalDao _savingsGoalDao;

  private volatile SearchDao _searchDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(2) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `bill_category` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT NOT NULL, `icon_key` TEXT NOT NULL, `color_hex` TEXT NOT NULL, `is_custom` INTEGER NOT NULL, `is_deleted` INTEGER NOT NULL DEFAULT 0, `remote_id` TEXT, `sync_status` TEXT NOT NULL DEFAULT 'LOCAL_ONLY')");
        db.execSQL("CREATE TABLE IF NOT EXISTS `bill` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `category_id` INTEGER NOT NULL, `name` TEXT NOT NULL, `default_amount` REAL NOT NULL, `currency_code` TEXT NOT NULL DEFAULT 'PHP', `account_number` TEXT, `bill_reference` TEXT, `merchant` TEXT, `recurrence_type` TEXT NOT NULL, `recurrence_interval` INTEGER NOT NULL DEFAULT 1, `due_day` INTEGER, `start_date` TEXT NOT NULL, `end_date` TEXT, `is_active` INTEGER NOT NULL DEFAULT 1, `is_favorite` INTEGER NOT NULL DEFAULT 0, `notes` TEXT, `is_deleted` INTEGER NOT NULL DEFAULT 0, `created_at` TEXT NOT NULL, `updated_at` TEXT NOT NULL, `is_installment` INTEGER NOT NULL DEFAULT 0, `total_installments` INTEGER, `remote_id` TEXT, `last_synced_at` TEXT, `sync_status` TEXT NOT NULL DEFAULT 'LOCAL_ONLY', `is_dirty` INTEGER NOT NULL DEFAULT 0, FOREIGN KEY(`category_id`) REFERENCES `bill_category`(`id`) ON UPDATE NO ACTION ON DELETE RESTRICT )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_bill_category_id` ON `bill` (`category_id`)");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_bill_merchant` ON `bill` (`merchant`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `bill_cycle` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `bill_id` INTEGER NOT NULL, `period_start` TEXT NOT NULL, `due_date` TEXT NOT NULL, `amount_due` REAL NOT NULL, `amount_paid` REAL NOT NULL DEFAULT 0, `status` TEXT NOT NULL, `created_at` TEXT NOT NULL, `updated_at` TEXT NOT NULL, `installment_number` INTEGER, FOREIGN KEY(`bill_id`) REFERENCES `bill`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_bill_cycle_due_date` ON `bill_cycle` (`due_date`)");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_bill_cycle_status` ON `bill_cycle` (`status`)");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_bill_cycle_bill_id_due_date` ON `bill_cycle` (`bill_id`, `due_date`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `payment` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `bill_cycle_id` INTEGER NOT NULL, `amount` REAL NOT NULL, `payment_date` TEXT NOT NULL, `method` TEXT NOT NULL, `note` TEXT, `created_at` TEXT NOT NULL, FOREIGN KEY(`bill_cycle_id`) REFERENCES `bill_cycle`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_payment_payment_date` ON `payment` (`payment_date`)");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_payment_bill_cycle_id` ON `payment` (`bill_cycle_id`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `budget` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT NOT NULL, `type` TEXT NOT NULL, `total_amount` REAL NOT NULL, `currency_code` TEXT NOT NULL DEFAULT 'PHP', `start_date` TEXT NOT NULL, `end_date` TEXT NOT NULL, `is_active` INTEGER NOT NULL DEFAULT 1, `is_archived` INTEGER NOT NULL DEFAULT 0, `is_deleted` INTEGER NOT NULL DEFAULT 0, `created_at` TEXT NOT NULL, `updated_at` TEXT NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `budget_category_limit` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `budget_id` INTEGER NOT NULL, `category_id` INTEGER NOT NULL, `limit_amount` REAL NOT NULL, FOREIGN KEY(`budget_id`) REFERENCES `budget`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE , FOREIGN KEY(`category_id`) REFERENCES `bill_category`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_budget_category_limit_budget_id` ON `budget_category_limit` (`budget_id`)");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_budget_category_limit_category_id` ON `budget_category_limit` (`category_id`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `reminder` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `bill_cycle_id` INTEGER, `income_source_id` INTEGER, `remind_at` TEXT NOT NULL, `is_sent` INTEGER NOT NULL DEFAULT 0, `offset_days` INTEGER NOT NULL, FOREIGN KEY(`bill_cycle_id`) REFERENCES `bill_cycle`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE , FOREIGN KEY(`income_source_id`) REFERENCES `income_source`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_reminder_remind_at` ON `reminder` (`remind_at`)");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_reminder_bill_cycle_id` ON `reminder` (`bill_cycle_id`)");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_reminder_income_source_id` ON `reminder` (`income_source_id`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `income_category` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT NOT NULL, `icon_key` TEXT NOT NULL, `color_hex` TEXT NOT NULL, `is_custom` INTEGER NOT NULL DEFAULT 0)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `income_source` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `category_id` INTEGER NOT NULL, `name` TEXT NOT NULL, `default_amount` REAL NOT NULL, `recurrence_type` TEXT NOT NULL, `recurrence_interval` INTEGER NOT NULL DEFAULT 1, `start_date` TEXT NOT NULL, `end_date` TEXT, `is_active` INTEGER NOT NULL DEFAULT 1, `is_archived` INTEGER NOT NULL DEFAULT 0, `is_deleted` INTEGER NOT NULL DEFAULT 0, FOREIGN KEY(`category_id`) REFERENCES `income_category`(`id`) ON UPDATE NO ACTION ON DELETE RESTRICT )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_income_source_category_id` ON `income_source` (`category_id`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `income_entry` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `income_source_id` INTEGER, `category_id` INTEGER NOT NULL, `amount` REAL NOT NULL, `currency_code` TEXT NOT NULL DEFAULT 'PHP', `entry_date` TEXT NOT NULL, `note` TEXT, `created_at` TEXT NOT NULL, FOREIGN KEY(`income_source_id`) REFERENCES `income_source`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE , FOREIGN KEY(`category_id`) REFERENCES `income_category`(`id`) ON UPDATE NO ACTION ON DELETE RESTRICT )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_income_entry_entry_date` ON `income_entry` (`entry_date`)");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_income_entry_category_id` ON `income_entry` (`category_id`)");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_income_entry_income_source_id` ON `income_entry` (`income_source_id`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `savings_goal` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT NOT NULL, `target_amount` REAL NOT NULL, `currency_code` TEXT NOT NULL DEFAULT 'PHP', `current_amount` REAL NOT NULL DEFAULT 0, `target_date` TEXT, `icon_key` TEXT NOT NULL, `color_hex` TEXT NOT NULL, `notes` TEXT, `priority` INTEGER NOT NULL, `is_completed` INTEGER NOT NULL DEFAULT 0, `is_archived` INTEGER NOT NULL DEFAULT 0, `is_deleted` INTEGER NOT NULL DEFAULT 0, `created_at` TEXT NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `savings_contribution` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `savings_goal_id` INTEGER NOT NULL, `amount` REAL NOT NULL, `contribution_date` TEXT NOT NULL, `note` TEXT, `created_at` TEXT NOT NULL, FOREIGN KEY(`savings_goal_id`) REFERENCES `savings_goal`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_savings_contribution_savings_goal_id` ON `savings_contribution` (`savings_goal_id`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `recent_search` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `query_text` TEXT NOT NULL, `searched_at` TEXT NOT NULL)");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_recent_search_searched_at` ON `recent_search` (`searched_at`)");
        db.execSQL("CREATE VIRTUAL TABLE IF NOT EXISTS `bill_search_fts` USING FTS4(`billId` INTEGER NOT NULL, `name` TEXT NOT NULL, `notes` TEXT NOT NULL, `merchant` TEXT NOT NULL, `categoryName` TEXT NOT NULL, notindexed=`billId`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '6d73c6de3308a372cbc7dc9d58c2f46a')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `bill_category`");
        db.execSQL("DROP TABLE IF EXISTS `bill`");
        db.execSQL("DROP TABLE IF EXISTS `bill_cycle`");
        db.execSQL("DROP TABLE IF EXISTS `payment`");
        db.execSQL("DROP TABLE IF EXISTS `budget`");
        db.execSQL("DROP TABLE IF EXISTS `budget_category_limit`");
        db.execSQL("DROP TABLE IF EXISTS `reminder`");
        db.execSQL("DROP TABLE IF EXISTS `income_category`");
        db.execSQL("DROP TABLE IF EXISTS `income_source`");
        db.execSQL("DROP TABLE IF EXISTS `income_entry`");
        db.execSQL("DROP TABLE IF EXISTS `savings_goal`");
        db.execSQL("DROP TABLE IF EXISTS `savings_contribution`");
        db.execSQL("DROP TABLE IF EXISTS `recent_search`");
        db.execSQL("DROP TABLE IF EXISTS `bill_search_fts`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        db.execSQL("PRAGMA foreign_keys = ON");
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsBillCategory = new HashMap<String, TableInfo.Column>(8);
        _columnsBillCategory.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBillCategory.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBillCategory.put("icon_key", new TableInfo.Column("icon_key", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBillCategory.put("color_hex", new TableInfo.Column("color_hex", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBillCategory.put("is_custom", new TableInfo.Column("is_custom", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBillCategory.put("is_deleted", new TableInfo.Column("is_deleted", "INTEGER", true, 0, "0", TableInfo.CREATED_FROM_ENTITY));
        _columnsBillCategory.put("remote_id", new TableInfo.Column("remote_id", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBillCategory.put("sync_status", new TableInfo.Column("sync_status", "TEXT", true, 0, "'LOCAL_ONLY'", TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysBillCategory = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesBillCategory = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoBillCategory = new TableInfo("bill_category", _columnsBillCategory, _foreignKeysBillCategory, _indicesBillCategory);
        final TableInfo _existingBillCategory = TableInfo.read(db, "bill_category");
        if (!_infoBillCategory.equals(_existingBillCategory)) {
          return new RoomOpenHelper.ValidationResult(false, "bill_category(com.patflow.app.data.local.entity.BillCategoryEntity).\n"
                  + " Expected:\n" + _infoBillCategory + "\n"
                  + " Found:\n" + _existingBillCategory);
        }
        final HashMap<String, TableInfo.Column> _columnsBill = new HashMap<String, TableInfo.Column>(25);
        _columnsBill.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBill.put("category_id", new TableInfo.Column("category_id", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBill.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBill.put("default_amount", new TableInfo.Column("default_amount", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBill.put("currency_code", new TableInfo.Column("currency_code", "TEXT", true, 0, "'PHP'", TableInfo.CREATED_FROM_ENTITY));
        _columnsBill.put("account_number", new TableInfo.Column("account_number", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBill.put("bill_reference", new TableInfo.Column("bill_reference", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBill.put("merchant", new TableInfo.Column("merchant", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBill.put("recurrence_type", new TableInfo.Column("recurrence_type", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBill.put("recurrence_interval", new TableInfo.Column("recurrence_interval", "INTEGER", true, 0, "1", TableInfo.CREATED_FROM_ENTITY));
        _columnsBill.put("due_day", new TableInfo.Column("due_day", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBill.put("start_date", new TableInfo.Column("start_date", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBill.put("end_date", new TableInfo.Column("end_date", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBill.put("is_active", new TableInfo.Column("is_active", "INTEGER", true, 0, "1", TableInfo.CREATED_FROM_ENTITY));
        _columnsBill.put("is_favorite", new TableInfo.Column("is_favorite", "INTEGER", true, 0, "0", TableInfo.CREATED_FROM_ENTITY));
        _columnsBill.put("notes", new TableInfo.Column("notes", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBill.put("is_deleted", new TableInfo.Column("is_deleted", "INTEGER", true, 0, "0", TableInfo.CREATED_FROM_ENTITY));
        _columnsBill.put("created_at", new TableInfo.Column("created_at", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBill.put("updated_at", new TableInfo.Column("updated_at", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBill.put("is_installment", new TableInfo.Column("is_installment", "INTEGER", true, 0, "0", TableInfo.CREATED_FROM_ENTITY));
        _columnsBill.put("total_installments", new TableInfo.Column("total_installments", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBill.put("remote_id", new TableInfo.Column("remote_id", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBill.put("last_synced_at", new TableInfo.Column("last_synced_at", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBill.put("sync_status", new TableInfo.Column("sync_status", "TEXT", true, 0, "'LOCAL_ONLY'", TableInfo.CREATED_FROM_ENTITY));
        _columnsBill.put("is_dirty", new TableInfo.Column("is_dirty", "INTEGER", true, 0, "0", TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysBill = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysBill.add(new TableInfo.ForeignKey("bill_category", "RESTRICT", "NO ACTION", Arrays.asList("category_id"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesBill = new HashSet<TableInfo.Index>(2);
        _indicesBill.add(new TableInfo.Index("index_bill_category_id", false, Arrays.asList("category_id"), Arrays.asList("ASC")));
        _indicesBill.add(new TableInfo.Index("index_bill_merchant", false, Arrays.asList("merchant"), Arrays.asList("ASC")));
        final TableInfo _infoBill = new TableInfo("bill", _columnsBill, _foreignKeysBill, _indicesBill);
        final TableInfo _existingBill = TableInfo.read(db, "bill");
        if (!_infoBill.equals(_existingBill)) {
          return new RoomOpenHelper.ValidationResult(false, "bill(com.patflow.app.data.local.entity.BillEntity).\n"
                  + " Expected:\n" + _infoBill + "\n"
                  + " Found:\n" + _existingBill);
        }
        final HashMap<String, TableInfo.Column> _columnsBillCycle = new HashMap<String, TableInfo.Column>(10);
        _columnsBillCycle.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBillCycle.put("bill_id", new TableInfo.Column("bill_id", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBillCycle.put("period_start", new TableInfo.Column("period_start", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBillCycle.put("due_date", new TableInfo.Column("due_date", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBillCycle.put("amount_due", new TableInfo.Column("amount_due", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBillCycle.put("amount_paid", new TableInfo.Column("amount_paid", "REAL", true, 0, "0", TableInfo.CREATED_FROM_ENTITY));
        _columnsBillCycle.put("status", new TableInfo.Column("status", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBillCycle.put("created_at", new TableInfo.Column("created_at", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBillCycle.put("updated_at", new TableInfo.Column("updated_at", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBillCycle.put("installment_number", new TableInfo.Column("installment_number", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysBillCycle = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysBillCycle.add(new TableInfo.ForeignKey("bill", "CASCADE", "NO ACTION", Arrays.asList("bill_id"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesBillCycle = new HashSet<TableInfo.Index>(3);
        _indicesBillCycle.add(new TableInfo.Index("index_bill_cycle_due_date", false, Arrays.asList("due_date"), Arrays.asList("ASC")));
        _indicesBillCycle.add(new TableInfo.Index("index_bill_cycle_status", false, Arrays.asList("status"), Arrays.asList("ASC")));
        _indicesBillCycle.add(new TableInfo.Index("index_bill_cycle_bill_id_due_date", false, Arrays.asList("bill_id", "due_date"), Arrays.asList("ASC", "ASC")));
        final TableInfo _infoBillCycle = new TableInfo("bill_cycle", _columnsBillCycle, _foreignKeysBillCycle, _indicesBillCycle);
        final TableInfo _existingBillCycle = TableInfo.read(db, "bill_cycle");
        if (!_infoBillCycle.equals(_existingBillCycle)) {
          return new RoomOpenHelper.ValidationResult(false, "bill_cycle(com.patflow.app.data.local.entity.BillCycleEntity).\n"
                  + " Expected:\n" + _infoBillCycle + "\n"
                  + " Found:\n" + _existingBillCycle);
        }
        final HashMap<String, TableInfo.Column> _columnsPayment = new HashMap<String, TableInfo.Column>(7);
        _columnsPayment.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPayment.put("bill_cycle_id", new TableInfo.Column("bill_cycle_id", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPayment.put("amount", new TableInfo.Column("amount", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPayment.put("payment_date", new TableInfo.Column("payment_date", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPayment.put("method", new TableInfo.Column("method", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPayment.put("note", new TableInfo.Column("note", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPayment.put("created_at", new TableInfo.Column("created_at", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysPayment = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysPayment.add(new TableInfo.ForeignKey("bill_cycle", "CASCADE", "NO ACTION", Arrays.asList("bill_cycle_id"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesPayment = new HashSet<TableInfo.Index>(2);
        _indicesPayment.add(new TableInfo.Index("index_payment_payment_date", false, Arrays.asList("payment_date"), Arrays.asList("ASC")));
        _indicesPayment.add(new TableInfo.Index("index_payment_bill_cycle_id", false, Arrays.asList("bill_cycle_id"), Arrays.asList("ASC")));
        final TableInfo _infoPayment = new TableInfo("payment", _columnsPayment, _foreignKeysPayment, _indicesPayment);
        final TableInfo _existingPayment = TableInfo.read(db, "payment");
        if (!_infoPayment.equals(_existingPayment)) {
          return new RoomOpenHelper.ValidationResult(false, "payment(com.patflow.app.data.local.entity.PaymentEntity).\n"
                  + " Expected:\n" + _infoPayment + "\n"
                  + " Found:\n" + _existingPayment);
        }
        final HashMap<String, TableInfo.Column> _columnsBudget = new HashMap<String, TableInfo.Column>(12);
        _columnsBudget.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBudget.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBudget.put("type", new TableInfo.Column("type", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBudget.put("total_amount", new TableInfo.Column("total_amount", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBudget.put("currency_code", new TableInfo.Column("currency_code", "TEXT", true, 0, "'PHP'", TableInfo.CREATED_FROM_ENTITY));
        _columnsBudget.put("start_date", new TableInfo.Column("start_date", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBudget.put("end_date", new TableInfo.Column("end_date", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBudget.put("is_active", new TableInfo.Column("is_active", "INTEGER", true, 0, "1", TableInfo.CREATED_FROM_ENTITY));
        _columnsBudget.put("is_archived", new TableInfo.Column("is_archived", "INTEGER", true, 0, "0", TableInfo.CREATED_FROM_ENTITY));
        _columnsBudget.put("is_deleted", new TableInfo.Column("is_deleted", "INTEGER", true, 0, "0", TableInfo.CREATED_FROM_ENTITY));
        _columnsBudget.put("created_at", new TableInfo.Column("created_at", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBudget.put("updated_at", new TableInfo.Column("updated_at", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysBudget = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesBudget = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoBudget = new TableInfo("budget", _columnsBudget, _foreignKeysBudget, _indicesBudget);
        final TableInfo _existingBudget = TableInfo.read(db, "budget");
        if (!_infoBudget.equals(_existingBudget)) {
          return new RoomOpenHelper.ValidationResult(false, "budget(com.patflow.app.data.local.entity.BudgetEntity).\n"
                  + " Expected:\n" + _infoBudget + "\n"
                  + " Found:\n" + _existingBudget);
        }
        final HashMap<String, TableInfo.Column> _columnsBudgetCategoryLimit = new HashMap<String, TableInfo.Column>(4);
        _columnsBudgetCategoryLimit.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBudgetCategoryLimit.put("budget_id", new TableInfo.Column("budget_id", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBudgetCategoryLimit.put("category_id", new TableInfo.Column("category_id", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBudgetCategoryLimit.put("limit_amount", new TableInfo.Column("limit_amount", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysBudgetCategoryLimit = new HashSet<TableInfo.ForeignKey>(2);
        _foreignKeysBudgetCategoryLimit.add(new TableInfo.ForeignKey("budget", "CASCADE", "NO ACTION", Arrays.asList("budget_id"), Arrays.asList("id")));
        _foreignKeysBudgetCategoryLimit.add(new TableInfo.ForeignKey("bill_category", "CASCADE", "NO ACTION", Arrays.asList("category_id"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesBudgetCategoryLimit = new HashSet<TableInfo.Index>(2);
        _indicesBudgetCategoryLimit.add(new TableInfo.Index("index_budget_category_limit_budget_id", false, Arrays.asList("budget_id"), Arrays.asList("ASC")));
        _indicesBudgetCategoryLimit.add(new TableInfo.Index("index_budget_category_limit_category_id", false, Arrays.asList("category_id"), Arrays.asList("ASC")));
        final TableInfo _infoBudgetCategoryLimit = new TableInfo("budget_category_limit", _columnsBudgetCategoryLimit, _foreignKeysBudgetCategoryLimit, _indicesBudgetCategoryLimit);
        final TableInfo _existingBudgetCategoryLimit = TableInfo.read(db, "budget_category_limit");
        if (!_infoBudgetCategoryLimit.equals(_existingBudgetCategoryLimit)) {
          return new RoomOpenHelper.ValidationResult(false, "budget_category_limit(com.patflow.app.data.local.entity.BudgetCategoryLimitEntity).\n"
                  + " Expected:\n" + _infoBudgetCategoryLimit + "\n"
                  + " Found:\n" + _existingBudgetCategoryLimit);
        }
        final HashMap<String, TableInfo.Column> _columnsReminder = new HashMap<String, TableInfo.Column>(6);
        _columnsReminder.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsReminder.put("bill_cycle_id", new TableInfo.Column("bill_cycle_id", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsReminder.put("income_source_id", new TableInfo.Column("income_source_id", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsReminder.put("remind_at", new TableInfo.Column("remind_at", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsReminder.put("is_sent", new TableInfo.Column("is_sent", "INTEGER", true, 0, "0", TableInfo.CREATED_FROM_ENTITY));
        _columnsReminder.put("offset_days", new TableInfo.Column("offset_days", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysReminder = new HashSet<TableInfo.ForeignKey>(2);
        _foreignKeysReminder.add(new TableInfo.ForeignKey("bill_cycle", "CASCADE", "NO ACTION", Arrays.asList("bill_cycle_id"), Arrays.asList("id")));
        _foreignKeysReminder.add(new TableInfo.ForeignKey("income_source", "CASCADE", "NO ACTION", Arrays.asList("income_source_id"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesReminder = new HashSet<TableInfo.Index>(3);
        _indicesReminder.add(new TableInfo.Index("index_reminder_remind_at", false, Arrays.asList("remind_at"), Arrays.asList("ASC")));
        _indicesReminder.add(new TableInfo.Index("index_reminder_bill_cycle_id", false, Arrays.asList("bill_cycle_id"), Arrays.asList("ASC")));
        _indicesReminder.add(new TableInfo.Index("index_reminder_income_source_id", false, Arrays.asList("income_source_id"), Arrays.asList("ASC")));
        final TableInfo _infoReminder = new TableInfo("reminder", _columnsReminder, _foreignKeysReminder, _indicesReminder);
        final TableInfo _existingReminder = TableInfo.read(db, "reminder");
        if (!_infoReminder.equals(_existingReminder)) {
          return new RoomOpenHelper.ValidationResult(false, "reminder(com.patflow.app.data.local.entity.ReminderEntity).\n"
                  + " Expected:\n" + _infoReminder + "\n"
                  + " Found:\n" + _existingReminder);
        }
        final HashMap<String, TableInfo.Column> _columnsIncomeCategory = new HashMap<String, TableInfo.Column>(5);
        _columnsIncomeCategory.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsIncomeCategory.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsIncomeCategory.put("icon_key", new TableInfo.Column("icon_key", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsIncomeCategory.put("color_hex", new TableInfo.Column("color_hex", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsIncomeCategory.put("is_custom", new TableInfo.Column("is_custom", "INTEGER", true, 0, "0", TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysIncomeCategory = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesIncomeCategory = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoIncomeCategory = new TableInfo("income_category", _columnsIncomeCategory, _foreignKeysIncomeCategory, _indicesIncomeCategory);
        final TableInfo _existingIncomeCategory = TableInfo.read(db, "income_category");
        if (!_infoIncomeCategory.equals(_existingIncomeCategory)) {
          return new RoomOpenHelper.ValidationResult(false, "income_category(com.patflow.app.data.local.entity.IncomeCategoryEntity).\n"
                  + " Expected:\n" + _infoIncomeCategory + "\n"
                  + " Found:\n" + _existingIncomeCategory);
        }
        final HashMap<String, TableInfo.Column> _columnsIncomeSource = new HashMap<String, TableInfo.Column>(11);
        _columnsIncomeSource.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsIncomeSource.put("category_id", new TableInfo.Column("category_id", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsIncomeSource.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsIncomeSource.put("default_amount", new TableInfo.Column("default_amount", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsIncomeSource.put("recurrence_type", new TableInfo.Column("recurrence_type", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsIncomeSource.put("recurrence_interval", new TableInfo.Column("recurrence_interval", "INTEGER", true, 0, "1", TableInfo.CREATED_FROM_ENTITY));
        _columnsIncomeSource.put("start_date", new TableInfo.Column("start_date", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsIncomeSource.put("end_date", new TableInfo.Column("end_date", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsIncomeSource.put("is_active", new TableInfo.Column("is_active", "INTEGER", true, 0, "1", TableInfo.CREATED_FROM_ENTITY));
        _columnsIncomeSource.put("is_archived", new TableInfo.Column("is_archived", "INTEGER", true, 0, "0", TableInfo.CREATED_FROM_ENTITY));
        _columnsIncomeSource.put("is_deleted", new TableInfo.Column("is_deleted", "INTEGER", true, 0, "0", TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysIncomeSource = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysIncomeSource.add(new TableInfo.ForeignKey("income_category", "RESTRICT", "NO ACTION", Arrays.asList("category_id"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesIncomeSource = new HashSet<TableInfo.Index>(1);
        _indicesIncomeSource.add(new TableInfo.Index("index_income_source_category_id", false, Arrays.asList("category_id"), Arrays.asList("ASC")));
        final TableInfo _infoIncomeSource = new TableInfo("income_source", _columnsIncomeSource, _foreignKeysIncomeSource, _indicesIncomeSource);
        final TableInfo _existingIncomeSource = TableInfo.read(db, "income_source");
        if (!_infoIncomeSource.equals(_existingIncomeSource)) {
          return new RoomOpenHelper.ValidationResult(false, "income_source(com.patflow.app.data.local.entity.IncomeSourceEntity).\n"
                  + " Expected:\n" + _infoIncomeSource + "\n"
                  + " Found:\n" + _existingIncomeSource);
        }
        final HashMap<String, TableInfo.Column> _columnsIncomeEntry = new HashMap<String, TableInfo.Column>(8);
        _columnsIncomeEntry.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsIncomeEntry.put("income_source_id", new TableInfo.Column("income_source_id", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsIncomeEntry.put("category_id", new TableInfo.Column("category_id", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsIncomeEntry.put("amount", new TableInfo.Column("amount", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsIncomeEntry.put("currency_code", new TableInfo.Column("currency_code", "TEXT", true, 0, "'PHP'", TableInfo.CREATED_FROM_ENTITY));
        _columnsIncomeEntry.put("entry_date", new TableInfo.Column("entry_date", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsIncomeEntry.put("note", new TableInfo.Column("note", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsIncomeEntry.put("created_at", new TableInfo.Column("created_at", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysIncomeEntry = new HashSet<TableInfo.ForeignKey>(2);
        _foreignKeysIncomeEntry.add(new TableInfo.ForeignKey("income_source", "CASCADE", "NO ACTION", Arrays.asList("income_source_id"), Arrays.asList("id")));
        _foreignKeysIncomeEntry.add(new TableInfo.ForeignKey("income_category", "RESTRICT", "NO ACTION", Arrays.asList("category_id"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesIncomeEntry = new HashSet<TableInfo.Index>(3);
        _indicesIncomeEntry.add(new TableInfo.Index("index_income_entry_entry_date", false, Arrays.asList("entry_date"), Arrays.asList("ASC")));
        _indicesIncomeEntry.add(new TableInfo.Index("index_income_entry_category_id", false, Arrays.asList("category_id"), Arrays.asList("ASC")));
        _indicesIncomeEntry.add(new TableInfo.Index("index_income_entry_income_source_id", false, Arrays.asList("income_source_id"), Arrays.asList("ASC")));
        final TableInfo _infoIncomeEntry = new TableInfo("income_entry", _columnsIncomeEntry, _foreignKeysIncomeEntry, _indicesIncomeEntry);
        final TableInfo _existingIncomeEntry = TableInfo.read(db, "income_entry");
        if (!_infoIncomeEntry.equals(_existingIncomeEntry)) {
          return new RoomOpenHelper.ValidationResult(false, "income_entry(com.patflow.app.data.local.entity.IncomeEntryEntity).\n"
                  + " Expected:\n" + _infoIncomeEntry + "\n"
                  + " Found:\n" + _existingIncomeEntry);
        }
        final HashMap<String, TableInfo.Column> _columnsSavingsGoal = new HashMap<String, TableInfo.Column>(14);
        _columnsSavingsGoal.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSavingsGoal.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSavingsGoal.put("target_amount", new TableInfo.Column("target_amount", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSavingsGoal.put("currency_code", new TableInfo.Column("currency_code", "TEXT", true, 0, "'PHP'", TableInfo.CREATED_FROM_ENTITY));
        _columnsSavingsGoal.put("current_amount", new TableInfo.Column("current_amount", "REAL", true, 0, "0", TableInfo.CREATED_FROM_ENTITY));
        _columnsSavingsGoal.put("target_date", new TableInfo.Column("target_date", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSavingsGoal.put("icon_key", new TableInfo.Column("icon_key", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSavingsGoal.put("color_hex", new TableInfo.Column("color_hex", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSavingsGoal.put("notes", new TableInfo.Column("notes", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSavingsGoal.put("priority", new TableInfo.Column("priority", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSavingsGoal.put("is_completed", new TableInfo.Column("is_completed", "INTEGER", true, 0, "0", TableInfo.CREATED_FROM_ENTITY));
        _columnsSavingsGoal.put("is_archived", new TableInfo.Column("is_archived", "INTEGER", true, 0, "0", TableInfo.CREATED_FROM_ENTITY));
        _columnsSavingsGoal.put("is_deleted", new TableInfo.Column("is_deleted", "INTEGER", true, 0, "0", TableInfo.CREATED_FROM_ENTITY));
        _columnsSavingsGoal.put("created_at", new TableInfo.Column("created_at", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysSavingsGoal = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesSavingsGoal = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoSavingsGoal = new TableInfo("savings_goal", _columnsSavingsGoal, _foreignKeysSavingsGoal, _indicesSavingsGoal);
        final TableInfo _existingSavingsGoal = TableInfo.read(db, "savings_goal");
        if (!_infoSavingsGoal.equals(_existingSavingsGoal)) {
          return new RoomOpenHelper.ValidationResult(false, "savings_goal(com.patflow.app.data.local.entity.SavingsGoalEntity).\n"
                  + " Expected:\n" + _infoSavingsGoal + "\n"
                  + " Found:\n" + _existingSavingsGoal);
        }
        final HashMap<String, TableInfo.Column> _columnsSavingsContribution = new HashMap<String, TableInfo.Column>(6);
        _columnsSavingsContribution.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSavingsContribution.put("savings_goal_id", new TableInfo.Column("savings_goal_id", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSavingsContribution.put("amount", new TableInfo.Column("amount", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSavingsContribution.put("contribution_date", new TableInfo.Column("contribution_date", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSavingsContribution.put("note", new TableInfo.Column("note", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSavingsContribution.put("created_at", new TableInfo.Column("created_at", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysSavingsContribution = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysSavingsContribution.add(new TableInfo.ForeignKey("savings_goal", "CASCADE", "NO ACTION", Arrays.asList("savings_goal_id"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesSavingsContribution = new HashSet<TableInfo.Index>(1);
        _indicesSavingsContribution.add(new TableInfo.Index("index_savings_contribution_savings_goal_id", false, Arrays.asList("savings_goal_id"), Arrays.asList("ASC")));
        final TableInfo _infoSavingsContribution = new TableInfo("savings_contribution", _columnsSavingsContribution, _foreignKeysSavingsContribution, _indicesSavingsContribution);
        final TableInfo _existingSavingsContribution = TableInfo.read(db, "savings_contribution");
        if (!_infoSavingsContribution.equals(_existingSavingsContribution)) {
          return new RoomOpenHelper.ValidationResult(false, "savings_contribution(com.patflow.app.data.local.entity.SavingsContributionEntity).\n"
                  + " Expected:\n" + _infoSavingsContribution + "\n"
                  + " Found:\n" + _existingSavingsContribution);
        }
        final HashMap<String, TableInfo.Column> _columnsRecentSearch = new HashMap<String, TableInfo.Column>(3);
        _columnsRecentSearch.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRecentSearch.put("query_text", new TableInfo.Column("query_text", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsRecentSearch.put("searched_at", new TableInfo.Column("searched_at", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysRecentSearch = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesRecentSearch = new HashSet<TableInfo.Index>(1);
        _indicesRecentSearch.add(new TableInfo.Index("index_recent_search_searched_at", false, Arrays.asList("searched_at"), Arrays.asList("ASC")));
        final TableInfo _infoRecentSearch = new TableInfo("recent_search", _columnsRecentSearch, _foreignKeysRecentSearch, _indicesRecentSearch);
        final TableInfo _existingRecentSearch = TableInfo.read(db, "recent_search");
        if (!_infoRecentSearch.equals(_existingRecentSearch)) {
          return new RoomOpenHelper.ValidationResult(false, "recent_search(com.patflow.app.data.local.entity.RecentSearchEntity).\n"
                  + " Expected:\n" + _infoRecentSearch + "\n"
                  + " Found:\n" + _existingRecentSearch);
        }
        final HashSet<String> _columnsBillSearchFts = new HashSet<String>(5);
        _columnsBillSearchFts.add("billId");
        _columnsBillSearchFts.add("name");
        _columnsBillSearchFts.add("notes");
        _columnsBillSearchFts.add("merchant");
        _columnsBillSearchFts.add("categoryName");
        final FtsTableInfo _infoBillSearchFts = new FtsTableInfo("bill_search_fts", _columnsBillSearchFts, "CREATE VIRTUAL TABLE IF NOT EXISTS `bill_search_fts` USING FTS4(`billId` INTEGER NOT NULL, `name` TEXT NOT NULL, `notes` TEXT NOT NULL, `merchant` TEXT NOT NULL, `categoryName` TEXT NOT NULL, notindexed=`billId`)");
        final FtsTableInfo _existingBillSearchFts = FtsTableInfo.read(db, "bill_search_fts");
        if (!_infoBillSearchFts.equals(_existingBillSearchFts)) {
          return new RoomOpenHelper.ValidationResult(false, "bill_search_fts(com.patflow.app.data.local.entity.BillSearchFtsEntity).\n"
                  + " Expected:\n" + _infoBillSearchFts + "\n"
                  + " Found:\n" + _existingBillSearchFts);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "6d73c6de3308a372cbc7dc9d58c2f46a", "642522661d2fed74e4e1e8a16f3aede4");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(1);
    _shadowTablesMap.put("bill_search_fts", "bill_search_fts_content");
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "bill_category","bill","bill_cycle","payment","budget","budget_category_limit","reminder","income_category","income_source","income_entry","savings_goal","savings_contribution","recent_search","bill_search_fts");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    final boolean _supportsDeferForeignKeys = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP;
    try {
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = FALSE");
      }
      super.beginTransaction();
      if (_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA defer_foreign_keys = TRUE");
      }
      _db.execSQL("DELETE FROM `bill`");
      _db.execSQL("DELETE FROM `bill_category`");
      _db.execSQL("DELETE FROM `bill_cycle`");
      _db.execSQL("DELETE FROM `payment`");
      _db.execSQL("DELETE FROM `budget`");
      _db.execSQL("DELETE FROM `budget_category_limit`");
      _db.execSQL("DELETE FROM `reminder`");
      _db.execSQL("DELETE FROM `income_source`");
      _db.execSQL("DELETE FROM `income_entry`");
      _db.execSQL("DELETE FROM `income_category`");
      _db.execSQL("DELETE FROM `savings_goal`");
      _db.execSQL("DELETE FROM `savings_contribution`");
      _db.execSQL("DELETE FROM `recent_search`");
      _db.execSQL("DELETE FROM `bill_search_fts`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = TRUE");
      }
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(CategoryDao.class, CategoryDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(BillDao.class, BillDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(BillCycleDao.class, BillCycleDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(PaymentDao.class, PaymentDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(BudgetDao.class, BudgetDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(ReminderDao.class, ReminderDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(IncomeDao.class, IncomeDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(SavingsGoalDao.class, SavingsGoalDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(SearchDao.class, SearchDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public CategoryDao categoryDao() {
    if (_categoryDao != null) {
      return _categoryDao;
    } else {
      synchronized(this) {
        if(_categoryDao == null) {
          _categoryDao = new CategoryDao_Impl(this);
        }
        return _categoryDao;
      }
    }
  }

  @Override
  public BillDao billDao() {
    if (_billDao != null) {
      return _billDao;
    } else {
      synchronized(this) {
        if(_billDao == null) {
          _billDao = new BillDao_Impl(this);
        }
        return _billDao;
      }
    }
  }

  @Override
  public BillCycleDao billCycleDao() {
    if (_billCycleDao != null) {
      return _billCycleDao;
    } else {
      synchronized(this) {
        if(_billCycleDao == null) {
          _billCycleDao = new BillCycleDao_Impl(this);
        }
        return _billCycleDao;
      }
    }
  }

  @Override
  public PaymentDao paymentDao() {
    if (_paymentDao != null) {
      return _paymentDao;
    } else {
      synchronized(this) {
        if(_paymentDao == null) {
          _paymentDao = new PaymentDao_Impl(this);
        }
        return _paymentDao;
      }
    }
  }

  @Override
  public BudgetDao budgetDao() {
    if (_budgetDao != null) {
      return _budgetDao;
    } else {
      synchronized(this) {
        if(_budgetDao == null) {
          _budgetDao = new BudgetDao_Impl(this);
        }
        return _budgetDao;
      }
    }
  }

  @Override
  public ReminderDao reminderDao() {
    if (_reminderDao != null) {
      return _reminderDao;
    } else {
      synchronized(this) {
        if(_reminderDao == null) {
          _reminderDao = new ReminderDao_Impl(this);
        }
        return _reminderDao;
      }
    }
  }

  @Override
  public IncomeDao incomeDao() {
    if (_incomeDao != null) {
      return _incomeDao;
    } else {
      synchronized(this) {
        if(_incomeDao == null) {
          _incomeDao = new IncomeDao_Impl(this);
        }
        return _incomeDao;
      }
    }
  }

  @Override
  public SavingsGoalDao savingsGoalDao() {
    if (_savingsGoalDao != null) {
      return _savingsGoalDao;
    } else {
      synchronized(this) {
        if(_savingsGoalDao == null) {
          _savingsGoalDao = new SavingsGoalDao_Impl(this);
        }
        return _savingsGoalDao;
      }
    }
  }

  @Override
  public SearchDao searchDao() {
    if (_searchDao != null) {
      return _searchDao;
    } else {
      synchronized(this) {
        if(_searchDao == null) {
          _searchDao = new SearchDao_Impl(this);
        }
        return _searchDao;
      }
    }
  }
}
