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
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.patflow.app.data.local.entity.BudgetCategoryLimitEntity;
import com.patflow.app.data.local.entity.BudgetEntity;
import java.lang.Class;
import java.lang.Exception;
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

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class BudgetDao_Impl implements BudgetDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<BudgetEntity> __insertionAdapterOfBudgetEntity;

  private final EntityInsertionAdapter<BudgetCategoryLimitEntity> __insertionAdapterOfBudgetCategoryLimitEntity;

  private final EntityDeletionOrUpdateAdapter<BudgetEntity> __deletionAdapterOfBudgetEntity;

  private final EntityDeletionOrUpdateAdapter<BudgetCategoryLimitEntity> __deletionAdapterOfBudgetCategoryLimitEntity;

  private final EntityDeletionOrUpdateAdapter<BudgetEntity> __updateAdapterOfBudgetEntity;

  private final EntityDeletionOrUpdateAdapter<BudgetCategoryLimitEntity> __updateAdapterOfBudgetCategoryLimitEntity;

  public BudgetDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfBudgetEntity = new EntityInsertionAdapter<BudgetEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `budget` (`id`,`month`,`year`,`total_amount`,`is_recurring_default`) VALUES (nullif(?, 0),?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final BudgetEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getMonth());
        statement.bindLong(3, entity.getYear());
        statement.bindDouble(4, entity.getTotalAmount());
        final int _tmp = entity.isRecurringDefault() ? 1 : 0;
        statement.bindLong(5, _tmp);
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
        return "UPDATE OR ABORT `budget` SET `id` = ?,`month` = ?,`year` = ?,`total_amount` = ?,`is_recurring_default` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final BudgetEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getMonth());
        statement.bindLong(3, entity.getYear());
        statement.bindDouble(4, entity.getTotalAmount());
        final int _tmp = entity.isRecurringDefault() ? 1 : 0;
        statement.bindLong(5, _tmp);
        statement.bindLong(6, entity.getId());
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
  public Object getForPeriod(final int month, final int year,
      final Continuation<? super BudgetEntity> $completion) {
    final String _sql = "SELECT * FROM budget WHERE month = ? AND year = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, month);
    _argIndex = 2;
    _statement.bindLong(_argIndex, year);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<BudgetEntity>() {
      @Override
      @Nullable
      public BudgetEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfMonth = CursorUtil.getColumnIndexOrThrow(_cursor, "month");
          final int _cursorIndexOfYear = CursorUtil.getColumnIndexOrThrow(_cursor, "year");
          final int _cursorIndexOfTotalAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "total_amount");
          final int _cursorIndexOfIsRecurringDefault = CursorUtil.getColumnIndexOrThrow(_cursor, "is_recurring_default");
          final BudgetEntity _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final int _tmpMonth;
            _tmpMonth = _cursor.getInt(_cursorIndexOfMonth);
            final int _tmpYear;
            _tmpYear = _cursor.getInt(_cursorIndexOfYear);
            final double _tmpTotalAmount;
            _tmpTotalAmount = _cursor.getDouble(_cursorIndexOfTotalAmount);
            final boolean _tmpIsRecurringDefault;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsRecurringDefault);
            _tmpIsRecurringDefault = _tmp != 0;
            _result = new BudgetEntity(_tmpId,_tmpMonth,_tmpYear,_tmpTotalAmount,_tmpIsRecurringDefault);
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
  public Flow<List<BudgetEntity>> getAll() {
    final String _sql = "SELECT * FROM budget ORDER BY year DESC, month DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"budget"}, new Callable<List<BudgetEntity>>() {
      @Override
      @NonNull
      public List<BudgetEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfMonth = CursorUtil.getColumnIndexOrThrow(_cursor, "month");
          final int _cursorIndexOfYear = CursorUtil.getColumnIndexOrThrow(_cursor, "year");
          final int _cursorIndexOfTotalAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "total_amount");
          final int _cursorIndexOfIsRecurringDefault = CursorUtil.getColumnIndexOrThrow(_cursor, "is_recurring_default");
          final List<BudgetEntity> _result = new ArrayList<BudgetEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final BudgetEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final int _tmpMonth;
            _tmpMonth = _cursor.getInt(_cursorIndexOfMonth);
            final int _tmpYear;
            _tmpYear = _cursor.getInt(_cursorIndexOfYear);
            final double _tmpTotalAmount;
            _tmpTotalAmount = _cursor.getDouble(_cursorIndexOfTotalAmount);
            final boolean _tmpIsRecurringDefault;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsRecurringDefault);
            _tmpIsRecurringDefault = _tmp != 0;
            _item = new BudgetEntity(_tmpId,_tmpMonth,_tmpYear,_tmpTotalAmount,_tmpIsRecurringDefault);
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

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
