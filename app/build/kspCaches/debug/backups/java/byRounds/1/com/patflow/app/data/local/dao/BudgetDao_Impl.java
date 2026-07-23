package com.patflow.app.data.local.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.patflow.app.data.local.database.Converters;
import com.patflow.app.data.local.entity.BudgetCategoryLimitEntity;
import com.patflow.app.data.local.entity.BudgetEntity;
import java.lang.Class;
import java.lang.Exception;
import java.lang.IllegalStateException;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;
import kotlinx.datetime.LocalDate;
import kotlinx.datetime.LocalDateTime;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class BudgetDao_Impl implements BudgetDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<BudgetEntity> __insertionAdapterOfBudgetEntity;

  private final Converters __converters = new Converters();

  private final EntityInsertionAdapter<BudgetCategoryLimitEntity> __insertionAdapterOfBudgetCategoryLimitEntity;

  private final EntityInsertionAdapter<BudgetCategoryLimitEntity> __insertionAdapterOfBudgetCategoryLimitEntity_1;

  private final EntityDeletionOrUpdateAdapter<BudgetEntity> __deletionAdapterOfBudgetEntity;

  private final EntityDeletionOrUpdateAdapter<BudgetCategoryLimitEntity> __deletionAdapterOfBudgetCategoryLimitEntity;

  private final EntityDeletionOrUpdateAdapter<BudgetEntity> __updateAdapterOfBudgetEntity;

  private final EntityDeletionOrUpdateAdapter<BudgetCategoryLimitEntity> __updateAdapterOfBudgetCategoryLimitEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAll;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAllLimits;

  public BudgetDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfBudgetEntity = new EntityInsertionAdapter<BudgetEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `budget` (`id`,`name`,`type`,`total_amount`,`currency_code`,`start_date`,`end_date`,`is_active`,`is_archived`,`is_deleted`,`created_at`,`updated_at`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final BudgetEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getName());
        statement.bindString(3, entity.getType());
        statement.bindDouble(4, entity.getTotalAmount());
        statement.bindString(5, entity.getCurrencyCode());
        final String _tmp = __converters.fromLocalDate(entity.getStartDate());
        if (_tmp == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, _tmp);
        }
        final String _tmp_1 = __converters.fromLocalDate(entity.getEndDate());
        if (_tmp_1 == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, _tmp_1);
        }
        final int _tmp_2 = entity.isActive() ? 1 : 0;
        statement.bindLong(8, _tmp_2);
        final int _tmp_3 = entity.isArchived() ? 1 : 0;
        statement.bindLong(9, _tmp_3);
        final int _tmp_4 = entity.isDeleted() ? 1 : 0;
        statement.bindLong(10, _tmp_4);
        final String _tmp_5 = __converters.fromLocalDateTime(entity.getCreatedAt());
        if (_tmp_5 == null) {
          statement.bindNull(11);
        } else {
          statement.bindString(11, _tmp_5);
        }
        final String _tmp_6 = __converters.fromLocalDateTime(entity.getUpdatedAt());
        if (_tmp_6 == null) {
          statement.bindNull(12);
        } else {
          statement.bindString(12, _tmp_6);
        }
      }
    };
    this.__insertionAdapterOfBudgetCategoryLimitEntity = new EntityInsertionAdapter<BudgetCategoryLimitEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `budget_category_limit` (`id`,`budget_id`,`category_id`,`limit_amount`) VALUES (nullif(?, 0),?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final BudgetCategoryLimitEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getBudgetId());
        statement.bindLong(3, entity.getCategoryId());
        statement.bindDouble(4, entity.getLimitAmount());
      }
    };
    this.__insertionAdapterOfBudgetCategoryLimitEntity_1 = new EntityInsertionAdapter<BudgetCategoryLimitEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `budget_category_limit` (`id`,`budget_id`,`category_id`,`limit_amount`) VALUES (nullif(?, 0),?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final BudgetCategoryLimitEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getBudgetId());
        statement.bindLong(3, entity.getCategoryId());
        statement.bindDouble(4, entity.getLimitAmount());
      }
    };
    this.__deletionAdapterOfBudgetEntity = new EntityDeletionOrUpdateAdapter<BudgetEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `budget` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final BudgetEntity entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__deletionAdapterOfBudgetCategoryLimitEntity = new EntityDeletionOrUpdateAdapter<BudgetCategoryLimitEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `budget_category_limit` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final BudgetCategoryLimitEntity entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfBudgetEntity = new EntityDeletionOrUpdateAdapter<BudgetEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `budget` SET `id` = ?,`name` = ?,`type` = ?,`total_amount` = ?,`currency_code` = ?,`start_date` = ?,`end_date` = ?,`is_active` = ?,`is_archived` = ?,`is_deleted` = ?,`created_at` = ?,`updated_at` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final BudgetEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getName());
        statement.bindString(3, entity.getType());
        statement.bindDouble(4, entity.getTotalAmount());
        statement.bindString(5, entity.getCurrencyCode());
        final String _tmp = __converters.fromLocalDate(entity.getStartDate());
        if (_tmp == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, _tmp);
        }
        final String _tmp_1 = __converters.fromLocalDate(entity.getEndDate());
        if (_tmp_1 == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, _tmp_1);
        }
        final int _tmp_2 = entity.isActive() ? 1 : 0;
        statement.bindLong(8, _tmp_2);
        final int _tmp_3 = entity.isArchived() ? 1 : 0;
        statement.bindLong(9, _tmp_3);
        final int _tmp_4 = entity.isDeleted() ? 1 : 0;
        statement.bindLong(10, _tmp_4);
        final String _tmp_5 = __converters.fromLocalDateTime(entity.getCreatedAt());
        if (_tmp_5 == null) {
          statement.bindNull(11);
        } else {
          statement.bindString(11, _tmp_5);
        }
        final String _tmp_6 = __converters.fromLocalDateTime(entity.getUpdatedAt());
        if (_tmp_6 == null) {
          statement.bindNull(12);
        } else {
          statement.bindString(12, _tmp_6);
        }
        statement.bindLong(13, entity.getId());
      }
    };
    this.__updateAdapterOfBudgetCategoryLimitEntity = new EntityDeletionOrUpdateAdapter<BudgetCategoryLimitEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `budget_category_limit` SET `id` = ?,`budget_id` = ?,`category_id` = ?,`limit_amount` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final BudgetCategoryLimitEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getBudgetId());
        statement.bindLong(3, entity.getCategoryId());
        statement.bindDouble(4, entity.getLimitAmount());
        statement.bindLong(5, entity.getId());
      }
    };
    this.__preparedStmtOfDeleteAll = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM budget";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteAllLimits = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM budget_category_limit";
        return _query;
      }
    };
  }

  @Override
  public Object insert(final BudgetEntity budget, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfBudgetEntity.insertAndReturnId(budget);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertCategoryLimit(final BudgetCategoryLimitEntity limit,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfBudgetCategoryLimitEntity.insertAndReturnId(limit);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertAll(final List<BudgetEntity> budgets,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfBudgetEntity.insert(budgets);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertAllLimits(final List<BudgetCategoryLimitEntity> limits,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfBudgetCategoryLimitEntity_1.insert(limits);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object delete(final BudgetEntity budget, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfBudgetEntity.handle(budget);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteCategoryLimit(final BudgetCategoryLimitEntity limit,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfBudgetCategoryLimitEntity.handle(limit);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object update(final BudgetEntity budget, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfBudgetEntity.handle(budget);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateCategoryLimit(final BudgetCategoryLimitEntity limit,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfBudgetCategoryLimitEntity.handle(limit);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteAll(final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAll.acquire();
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteAll.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteAllLimits(final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAllLimits.acquire();
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteAllLimits.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object getById(final long id, final Continuation<? super BudgetEntity> $completion) {
    final String _sql = "SELECT * FROM budget WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<BudgetEntity>() {
      @Override
      @Nullable
      public BudgetEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfTotalAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "total_amount");
          final int _cursorIndexOfCurrencyCode = CursorUtil.getColumnIndexOrThrow(_cursor, "currency_code");
          final int _cursorIndexOfStartDate = CursorUtil.getColumnIndexOrThrow(_cursor, "start_date");
          final int _cursorIndexOfEndDate = CursorUtil.getColumnIndexOrThrow(_cursor, "end_date");
          final int _cursorIndexOfIsActive = CursorUtil.getColumnIndexOrThrow(_cursor, "is_active");
          final int _cursorIndexOfIsArchived = CursorUtil.getColumnIndexOrThrow(_cursor, "is_archived");
          final int _cursorIndexOfIsDeleted = CursorUtil.getColumnIndexOrThrow(_cursor, "is_deleted");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updated_at");
          final BudgetEntity _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpType;
            _tmpType = _cursor.getString(_cursorIndexOfType);
            final double _tmpTotalAmount;
            _tmpTotalAmount = _cursor.getDouble(_cursorIndexOfTotalAmount);
            final String _tmpCurrencyCode;
            _tmpCurrencyCode = _cursor.getString(_cursorIndexOfCurrencyCode);
            final LocalDate _tmpStartDate;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfStartDate)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfStartDate);
            }
            final LocalDate _tmp_1 = __converters.toLocalDate(_tmp);
            if (_tmp_1 == null) {
              throw new IllegalStateException("Expected NON-NULL 'kotlinx.datetime.LocalDate', but it was NULL.");
            } else {
              _tmpStartDate = _tmp_1;
            }
            final LocalDate _tmpEndDate;
            final String _tmp_2;
            if (_cursor.isNull(_cursorIndexOfEndDate)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getString(_cursorIndexOfEndDate);
            }
            final LocalDate _tmp_3 = __converters.toLocalDate(_tmp_2);
            if (_tmp_3 == null) {
              throw new IllegalStateException("Expected NON-NULL 'kotlinx.datetime.LocalDate', but it was NULL.");
            } else {
              _tmpEndDate = _tmp_3;
            }
            final boolean _tmpIsActive;
            final int _tmp_4;
            _tmp_4 = _cursor.getInt(_cursorIndexOfIsActive);
            _tmpIsActive = _tmp_4 != 0;
            final boolean _tmpIsArchived;
            final int _tmp_5;
            _tmp_5 = _cursor.getInt(_cursorIndexOfIsArchived);
            _tmpIsArchived = _tmp_5 != 0;
            final boolean _tmpIsDeleted;
            final int _tmp_6;
            _tmp_6 = _cursor.getInt(_cursorIndexOfIsDeleted);
            _tmpIsDeleted = _tmp_6 != 0;
            final LocalDateTime _tmpCreatedAt;
            final String _tmp_7;
            if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
              _tmp_7 = null;
            } else {
              _tmp_7 = _cursor.getString(_cursorIndexOfCreatedAt);
            }
            final LocalDateTime _tmp_8 = __converters.toLocalDateTime(_tmp_7);
            if (_tmp_8 == null) {
              throw new IllegalStateException("Expected NON-NULL 'kotlinx.datetime.LocalDateTime', but it was NULL.");
            } else {
              _tmpCreatedAt = _tmp_8;
            }
            final LocalDateTime _tmpUpdatedAt;
            final String _tmp_9;
            if (_cursor.isNull(_cursorIndexOfUpdatedAt)) {
              _tmp_9 = null;
            } else {
              _tmp_9 = _cursor.getString(_cursorIndexOfUpdatedAt);
            }
            final LocalDateTime _tmp_10 = __converters.toLocalDateTime(_tmp_9);
            if (_tmp_10 == null) {
              throw new IllegalStateException("Expected NON-NULL 'kotlinx.datetime.LocalDateTime', but it was NULL.");
            } else {
              _tmpUpdatedAt = _tmp_10;
            }
            _result = new BudgetEntity(_tmpId,_tmpName,_tmpType,_tmpTotalAmount,_tmpCurrencyCode,_tmpStartDate,_tmpEndDate,_tmpIsActive,_tmpIsArchived,_tmpIsDeleted,_tmpCreatedAt,_tmpUpdatedAt);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<BudgetEntity>> getAllActive() {
    final String _sql = "SELECT * FROM budget WHERE is_deleted = 0 ORDER BY start_date DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"budget"}, new Callable<List<BudgetEntity>>() {
      @Override
      @NonNull
      public List<BudgetEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfTotalAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "total_amount");
          final int _cursorIndexOfCurrencyCode = CursorUtil.getColumnIndexOrThrow(_cursor, "currency_code");
          final int _cursorIndexOfStartDate = CursorUtil.getColumnIndexOrThrow(_cursor, "start_date");
          final int _cursorIndexOfEndDate = CursorUtil.getColumnIndexOrThrow(_cursor, "end_date");
          final int _cursorIndexOfIsActive = CursorUtil.getColumnIndexOrThrow(_cursor, "is_active");
          final int _cursorIndexOfIsArchived = CursorUtil.getColumnIndexOrThrow(_cursor, "is_archived");
          final int _cursorIndexOfIsDeleted = CursorUtil.getColumnIndexOrThrow(_cursor, "is_deleted");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updated_at");
          final List<BudgetEntity> _result = new ArrayList<BudgetEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final BudgetEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpType;
            _tmpType = _cursor.getString(_cursorIndexOfType);
            final double _tmpTotalAmount;
            _tmpTotalAmount = _cursor.getDouble(_cursorIndexOfTotalAmount);
            final String _tmpCurrencyCode;
            _tmpCurrencyCode = _cursor.getString(_cursorIndexOfCurrencyCode);
            final LocalDate _tmpStartDate;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfStartDate)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfStartDate);
            }
            final LocalDate _tmp_1 = __converters.toLocalDate(_tmp);
            if (_tmp_1 == null) {
              throw new IllegalStateException("Expected NON-NULL 'kotlinx.datetime.LocalDate', but it was NULL.");
            } else {
              _tmpStartDate = _tmp_1;
            }
            final LocalDate _tmpEndDate;
            final String _tmp_2;
            if (_cursor.isNull(_cursorIndexOfEndDate)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getString(_cursorIndexOfEndDate);
            }
            final LocalDate _tmp_3 = __converters.toLocalDate(_tmp_2);
            if (_tmp_3 == null) {
              throw new IllegalStateException("Expected NON-NULL 'kotlinx.datetime.LocalDate', but it was NULL.");
            } else {
              _tmpEndDate = _tmp_3;
            }
            final boolean _tmpIsActive;
            final int _tmp_4;
            _tmp_4 = _cursor.getInt(_cursorIndexOfIsActive);
            _tmpIsActive = _tmp_4 != 0;
            final boolean _tmpIsArchived;
            final int _tmp_5;
            _tmp_5 = _cursor.getInt(_cursorIndexOfIsArchived);
            _tmpIsArchived = _tmp_5 != 0;
            final boolean _tmpIsDeleted;
            final int _tmp_6;
            _tmp_6 = _cursor.getInt(_cursorIndexOfIsDeleted);
            _tmpIsDeleted = _tmp_6 != 0;
            final LocalDateTime _tmpCreatedAt;
            final String _tmp_7;
            if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
              _tmp_7 = null;
            } else {
              _tmp_7 = _cursor.getString(_cursorIndexOfCreatedAt);
            }
            final LocalDateTime _tmp_8 = __converters.toLocalDateTime(_tmp_7);
            if (_tmp_8 == null) {
              throw new IllegalStateException("Expected NON-NULL 'kotlinx.datetime.LocalDateTime', but it was NULL.");
            } else {
              _tmpCreatedAt = _tmp_8;
            }
            final LocalDateTime _tmpUpdatedAt;
            final String _tmp_9;
            if (_cursor.isNull(_cursorIndexOfUpdatedAt)) {
              _tmp_9 = null;
            } else {
              _tmp_9 = _cursor.getString(_cursorIndexOfUpdatedAt);
            }
            final LocalDateTime _tmp_10 = __converters.toLocalDateTime(_tmp_9);
            if (_tmp_10 == null) {
              throw new IllegalStateException("Expected NON-NULL 'kotlinx.datetime.LocalDateTime', but it was NULL.");
            } else {
              _tmpUpdatedAt = _tmp_10;
            }
            _item = new BudgetEntity(_tmpId,_tmpName,_tmpType,_tmpTotalAmount,_tmpCurrencyCode,_tmpStartDate,_tmpEndDate,_tmpIsActive,_tmpIsArchived,_tmpIsDeleted,_tmpCreatedAt,_tmpUpdatedAt);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<BudgetEntity>> getAll() {
    final String _sql = "SELECT * FROM budget ORDER BY start_date DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"budget"}, new Callable<List<BudgetEntity>>() {
      @Override
      @NonNull
      public List<BudgetEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfTotalAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "total_amount");
          final int _cursorIndexOfCurrencyCode = CursorUtil.getColumnIndexOrThrow(_cursor, "currency_code");
          final int _cursorIndexOfStartDate = CursorUtil.getColumnIndexOrThrow(_cursor, "start_date");
          final int _cursorIndexOfEndDate = CursorUtil.getColumnIndexOrThrow(_cursor, "end_date");
          final int _cursorIndexOfIsActive = CursorUtil.getColumnIndexOrThrow(_cursor, "is_active");
          final int _cursorIndexOfIsArchived = CursorUtil.getColumnIndexOrThrow(_cursor, "is_archived");
          final int _cursorIndexOfIsDeleted = CursorUtil.getColumnIndexOrThrow(_cursor, "is_deleted");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updated_at");
          final List<BudgetEntity> _result = new ArrayList<BudgetEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final BudgetEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpType;
            _tmpType = _cursor.getString(_cursorIndexOfType);
            final double _tmpTotalAmount;
            _tmpTotalAmount = _cursor.getDouble(_cursorIndexOfTotalAmount);
            final String _tmpCurrencyCode;
            _tmpCurrencyCode = _cursor.getString(_cursorIndexOfCurrencyCode);
            final LocalDate _tmpStartDate;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfStartDate)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfStartDate);
            }
            final LocalDate _tmp_1 = __converters.toLocalDate(_tmp);
            if (_tmp_1 == null) {
              throw new IllegalStateException("Expected NON-NULL 'kotlinx.datetime.LocalDate', but it was NULL.");
            } else {
              _tmpStartDate = _tmp_1;
            }
            final LocalDate _tmpEndDate;
            final String _tmp_2;
            if (_cursor.isNull(_cursorIndexOfEndDate)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getString(_cursorIndexOfEndDate);
            }
            final LocalDate _tmp_3 = __converters.toLocalDate(_tmp_2);
            if (_tmp_3 == null) {
              throw new IllegalStateException("Expected NON-NULL 'kotlinx.datetime.LocalDate', but it was NULL.");
            } else {
              _tmpEndDate = _tmp_3;
            }
            final boolean _tmpIsActive;
            final int _tmp_4;
            _tmp_4 = _cursor.getInt(_cursorIndexOfIsActive);
            _tmpIsActive = _tmp_4 != 0;
            final boolean _tmpIsArchived;
            final int _tmp_5;
            _tmp_5 = _cursor.getInt(_cursorIndexOfIsArchived);
            _tmpIsArchived = _tmp_5 != 0;
            final boolean _tmpIsDeleted;
            final int _tmp_6;
            _tmp_6 = _cursor.getInt(_cursorIndexOfIsDeleted);
            _tmpIsDeleted = _tmp_6 != 0;
            final LocalDateTime _tmpCreatedAt;
            final String _tmp_7;
            if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
              _tmp_7 = null;
            } else {
              _tmp_7 = _cursor.getString(_cursorIndexOfCreatedAt);
            }
            final LocalDateTime _tmp_8 = __converters.toLocalDateTime(_tmp_7);
            if (_tmp_8 == null) {
              throw new IllegalStateException("Expected NON-NULL 'kotlinx.datetime.LocalDateTime', but it was NULL.");
            } else {
              _tmpCreatedAt = _tmp_8;
            }
            final LocalDateTime _tmpUpdatedAt;
            final String _tmp_9;
            if (_cursor.isNull(_cursorIndexOfUpdatedAt)) {
              _tmp_9 = null;
            } else {
              _tmp_9 = _cursor.getString(_cursorIndexOfUpdatedAt);
            }
            final LocalDateTime _tmp_10 = __converters.toLocalDateTime(_tmp_9);
            if (_tmp_10 == null) {
              throw new IllegalStateException("Expected NON-NULL 'kotlinx.datetime.LocalDateTime', but it was NULL.");
            } else {
              _tmpUpdatedAt = _tmp_10;
            }
            _item = new BudgetEntity(_tmpId,_tmpName,_tmpType,_tmpTotalAmount,_tmpCurrencyCode,_tmpStartDate,_tmpEndDate,_tmpIsActive,_tmpIsArchived,_tmpIsDeleted,_tmpCreatedAt,_tmpUpdatedAt);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<BudgetCategoryLimitEntity>> getCategoryLimits(final long budgetId) {
    final String _sql = "SELECT * FROM budget_category_limit WHERE budget_id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, budgetId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"budget_category_limit"}, new Callable<List<BudgetCategoryLimitEntity>>() {
      @Override
      @NonNull
      public List<BudgetCategoryLimitEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfBudgetId = CursorUtil.getColumnIndexOrThrow(_cursor, "budget_id");
          final int _cursorIndexOfCategoryId = CursorUtil.getColumnIndexOrThrow(_cursor, "category_id");
          final int _cursorIndexOfLimitAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "limit_amount");
          final List<BudgetCategoryLimitEntity> _result = new ArrayList<BudgetCategoryLimitEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final BudgetCategoryLimitEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpBudgetId;
            _tmpBudgetId = _cursor.getLong(_cursorIndexOfBudgetId);
            final long _tmpCategoryId;
            _tmpCategoryId = _cursor.getLong(_cursorIndexOfCategoryId);
            final double _tmpLimitAmount;
            _tmpLimitAmount = _cursor.getDouble(_cursorIndexOfLimitAmount);
            _item = new BudgetCategoryLimitEntity(_tmpId,_tmpBudgetId,_tmpCategoryId,_tmpLimitAmount);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getLimitById(final long id,
      final Continuation<? super BudgetCategoryLimitEntity> $completion) {
    final String _sql = "SELECT * FROM budget_category_limit WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<BudgetCategoryLimitEntity>() {
      @Override
      @Nullable
      public BudgetCategoryLimitEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfBudgetId = CursorUtil.getColumnIndexOrThrow(_cursor, "budget_id");
          final int _cursorIndexOfCategoryId = CursorUtil.getColumnIndexOrThrow(_cursor, "category_id");
          final int _cursorIndexOfLimitAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "limit_amount");
          final BudgetCategoryLimitEntity _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpBudgetId;
            _tmpBudgetId = _cursor.getLong(_cursorIndexOfBudgetId);
            final long _tmpCategoryId;
            _tmpCategoryId = _cursor.getLong(_cursorIndexOfCategoryId);
            final double _tmpLimitAmount;
            _tmpLimitAmount = _cursor.getDouble(_cursorIndexOfLimitAmount);
            _result = new BudgetCategoryLimitEntity(_tmpId,_tmpBudgetId,_tmpCategoryId,_tmpLimitAmount);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getAllEntities(final Continuation<? super List<BudgetEntity>> $completion) {
    final String _sql = "SELECT * FROM budget";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<BudgetEntity>>() {
      @Override
      @NonNull
      public List<BudgetEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfTotalAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "total_amount");
          final int _cursorIndexOfCurrencyCode = CursorUtil.getColumnIndexOrThrow(_cursor, "currency_code");
          final int _cursorIndexOfStartDate = CursorUtil.getColumnIndexOrThrow(_cursor, "start_date");
          final int _cursorIndexOfEndDate = CursorUtil.getColumnIndexOrThrow(_cursor, "end_date");
          final int _cursorIndexOfIsActive = CursorUtil.getColumnIndexOrThrow(_cursor, "is_active");
          final int _cursorIndexOfIsArchived = CursorUtil.getColumnIndexOrThrow(_cursor, "is_archived");
          final int _cursorIndexOfIsDeleted = CursorUtil.getColumnIndexOrThrow(_cursor, "is_deleted");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updated_at");
          final List<BudgetEntity> _result = new ArrayList<BudgetEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final BudgetEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpType;
            _tmpType = _cursor.getString(_cursorIndexOfType);
            final double _tmpTotalAmount;
            _tmpTotalAmount = _cursor.getDouble(_cursorIndexOfTotalAmount);
            final String _tmpCurrencyCode;
            _tmpCurrencyCode = _cursor.getString(_cursorIndexOfCurrencyCode);
            final LocalDate _tmpStartDate;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfStartDate)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfStartDate);
            }
            final LocalDate _tmp_1 = __converters.toLocalDate(_tmp);
            if (_tmp_1 == null) {
              throw new IllegalStateException("Expected NON-NULL 'kotlinx.datetime.LocalDate', but it was NULL.");
            } else {
              _tmpStartDate = _tmp_1;
            }
            final LocalDate _tmpEndDate;
            final String _tmp_2;
            if (_cursor.isNull(_cursorIndexOfEndDate)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getString(_cursorIndexOfEndDate);
            }
            final LocalDate _tmp_3 = __converters.toLocalDate(_tmp_2);
            if (_tmp_3 == null) {
              throw new IllegalStateException("Expected NON-NULL 'kotlinx.datetime.LocalDate', but it was NULL.");
            } else {
              _tmpEndDate = _tmp_3;
            }
            final boolean _tmpIsActive;
            final int _tmp_4;
            _tmp_4 = _cursor.getInt(_cursorIndexOfIsActive);
            _tmpIsActive = _tmp_4 != 0;
            final boolean _tmpIsArchived;
            final int _tmp_5;
            _tmp_5 = _cursor.getInt(_cursorIndexOfIsArchived);
            _tmpIsArchived = _tmp_5 != 0;
            final boolean _tmpIsDeleted;
            final int _tmp_6;
            _tmp_6 = _cursor.getInt(_cursorIndexOfIsDeleted);
            _tmpIsDeleted = _tmp_6 != 0;
            final LocalDateTime _tmpCreatedAt;
            final String _tmp_7;
            if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
              _tmp_7 = null;
            } else {
              _tmp_7 = _cursor.getString(_cursorIndexOfCreatedAt);
            }
            final LocalDateTime _tmp_8 = __converters.toLocalDateTime(_tmp_7);
            if (_tmp_8 == null) {
              throw new IllegalStateException("Expected NON-NULL 'kotlinx.datetime.LocalDateTime', but it was NULL.");
            } else {
              _tmpCreatedAt = _tmp_8;
            }
            final LocalDateTime _tmpUpdatedAt;
            final String _tmp_9;
            if (_cursor.isNull(_cursorIndexOfUpdatedAt)) {
              _tmp_9 = null;
            } else {
              _tmp_9 = _cursor.getString(_cursorIndexOfUpdatedAt);
            }
            final LocalDateTime _tmp_10 = __converters.toLocalDateTime(_tmp_9);
            if (_tmp_10 == null) {
              throw new IllegalStateException("Expected NON-NULL 'kotlinx.datetime.LocalDateTime', but it was NULL.");
            } else {
              _tmpUpdatedAt = _tmp_10;
            }
            _item = new BudgetEntity(_tmpId,_tmpName,_tmpType,_tmpTotalAmount,_tmpCurrencyCode,_tmpStartDate,_tmpEndDate,_tmpIsActive,_tmpIsArchived,_tmpIsDeleted,_tmpCreatedAt,_tmpUpdatedAt);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getAllLimitEntities(
      final Continuation<? super List<BudgetCategoryLimitEntity>> $completion) {
    final String _sql = "SELECT * FROM budget_category_limit";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<BudgetCategoryLimitEntity>>() {
      @Override
      @NonNull
      public List<BudgetCategoryLimitEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfBudgetId = CursorUtil.getColumnIndexOrThrow(_cursor, "budget_id");
          final int _cursorIndexOfCategoryId = CursorUtil.getColumnIndexOrThrow(_cursor, "category_id");
          final int _cursorIndexOfLimitAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "limit_amount");
          final List<BudgetCategoryLimitEntity> _result = new ArrayList<BudgetCategoryLimitEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final BudgetCategoryLimitEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpBudgetId;
            _tmpBudgetId = _cursor.getLong(_cursorIndexOfBudgetId);
            final long _tmpCategoryId;
            _tmpCategoryId = _cursor.getLong(_cursorIndexOfCategoryId);
            final double _tmpLimitAmount;
            _tmpLimitAmount = _cursor.getDouble(_cursorIndexOfLimitAmount);
            _item = new BudgetCategoryLimitEntity(_tmpId,_tmpBudgetId,_tmpCategoryId,_tmpLimitAmount);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
