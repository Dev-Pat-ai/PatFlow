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
import com.patflow.app.data.local.database.Converters;
import com.patflow.app.data.local.entity.SavingsContributionEntity;
import com.patflow.app.data.local.entity.SavingsGoalEntity;
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
public final class SavingsGoalDao_Impl implements SavingsGoalDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<SavingsGoalEntity> __insertionAdapterOfSavingsGoalEntity;

  private final Converters __converters = new Converters();

  private final EntityInsertionAdapter<SavingsContributionEntity> __insertionAdapterOfSavingsContributionEntity;

  private final EntityDeletionOrUpdateAdapter<SavingsGoalEntity> __deletionAdapterOfSavingsGoalEntity;

  private final EntityDeletionOrUpdateAdapter<SavingsContributionEntity> __deletionAdapterOfSavingsContributionEntity;

  private final EntityDeletionOrUpdateAdapter<SavingsGoalEntity> __updateAdapterOfSavingsGoalEntity;

  private final EntityDeletionOrUpdateAdapter<SavingsContributionEntity> __updateAdapterOfSavingsContributionEntity;

  public SavingsGoalDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfSavingsGoalEntity = new EntityInsertionAdapter<SavingsGoalEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `savings_goal` (`id`,`name`,`target_amount`,`currency_code`,`current_amount`,`target_date`,`icon_key`,`color_hex`,`is_completed`,`is_deleted`,`created_at`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final SavingsGoalEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getName());
        statement.bindDouble(3, entity.getTargetAmount());
        statement.bindString(4, entity.getCurrencyCode());
        statement.bindDouble(5, entity.getCurrentAmount());
        final String _tmp = __converters.fromLocalDate(entity.getTargetDate());
        if (_tmp == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, _tmp);
        }
        statement.bindString(7, entity.getIconKey());
        statement.bindString(8, entity.getColorHex());
        final int _tmp_1 = entity.isCompleted() ? 1 : 0;
        statement.bindLong(9, _tmp_1);
        final int _tmp_2 = entity.isDeleted() ? 1 : 0;
        statement.bindLong(10, _tmp_2);
        final String _tmp_3 = __converters.fromLocalDateTime(entity.getCreatedAt());
        if (_tmp_3 == null) {
          statement.bindNull(11);
        } else {
          statement.bindString(11, _tmp_3);
        }
      }
    };
    this.__insertionAdapterOfSavingsContributionEntity = new EntityInsertionAdapter<SavingsContributionEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `savings_contribution` (`id`,`savings_goal_id`,`amount`,`contribution_date`,`note`,`created_at`) VALUES (nullif(?, 0),?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final SavingsContributionEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getSavingsGoalId());
        statement.bindDouble(3, entity.getAmount());
        final String _tmp = __converters.fromLocalDate(entity.getContributionDate());
        if (_tmp == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, _tmp);
        }
        if (entity.getNote() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getNote());
        }
        final String _tmp_1 = __converters.fromLocalDateTime(entity.getCreatedAt());
        if (_tmp_1 == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, _tmp_1);
        }
      }
    };
    this.__deletionAdapterOfSavingsGoalEntity = new EntityDeletionOrUpdateAdapter<SavingsGoalEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `savings_goal` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final SavingsGoalEntity entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__deletionAdapterOfSavingsContributionEntity = new EntityDeletionOrUpdateAdapter<SavingsContributionEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `savings_contribution` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final SavingsContributionEntity entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfSavingsGoalEntity = new EntityDeletionOrUpdateAdapter<SavingsGoalEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `savings_goal` SET `id` = ?,`name` = ?,`target_amount` = ?,`currency_code` = ?,`current_amount` = ?,`target_date` = ?,`icon_key` = ?,`color_hex` = ?,`is_completed` = ?,`is_deleted` = ?,`created_at` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final SavingsGoalEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getName());
        statement.bindDouble(3, entity.getTargetAmount());
        statement.bindString(4, entity.getCurrencyCode());
        statement.bindDouble(5, entity.getCurrentAmount());
        final String _tmp = __converters.fromLocalDate(entity.getTargetDate());
        if (_tmp == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, _tmp);
        }
        statement.bindString(7, entity.getIconKey());
        statement.bindString(8, entity.getColorHex());
        final int _tmp_1 = entity.isCompleted() ? 1 : 0;
        statement.bindLong(9, _tmp_1);
        final int _tmp_2 = entity.isDeleted() ? 1 : 0;
        statement.bindLong(10, _tmp_2);
        final String _tmp_3 = __converters.fromLocalDateTime(entity.getCreatedAt());
        if (_tmp_3 == null) {
          statement.bindNull(11);
        } else {
          statement.bindString(11, _tmp_3);
        }
        statement.bindLong(12, entity.getId());
      }
    };
    this.__updateAdapterOfSavingsContributionEntity = new EntityDeletionOrUpdateAdapter<SavingsContributionEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `savings_contribution` SET `id` = ?,`savings_goal_id` = ?,`amount` = ?,`contribution_date` = ?,`note` = ?,`created_at` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final SavingsContributionEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getSavingsGoalId());
        statement.bindDouble(3, entity.getAmount());
        final String _tmp = __converters.fromLocalDate(entity.getContributionDate());
        if (_tmp == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, _tmp);
        }
        if (entity.getNote() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getNote());
        }
        final String _tmp_1 = __converters.fromLocalDateTime(entity.getCreatedAt());
        if (_tmp_1 == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, _tmp_1);
        }
        statement.bindLong(7, entity.getId());
      }
    };
  }

  @Override
  public Object insertGoal(final SavingsGoalEntity goal,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfSavingsGoalEntity.insertAndReturnId(goal);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertContribution(final SavingsContributionEntity contribution,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfSavingsContributionEntity.insertAndReturnId(contribution);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteGoal(final SavingsGoalEntity goal,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfSavingsGoalEntity.handle(goal);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteContribution(final SavingsContributionEntity contribution,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfSavingsContributionEntity.handle(contribution);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateGoal(final SavingsGoalEntity goal,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfSavingsGoalEntity.handle(goal);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateContribution(final SavingsContributionEntity contribution,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfSavingsContributionEntity.handle(contribution);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object getGoalById(final long id,
      final Continuation<? super SavingsGoalEntity> $completion) {
    final String _sql = "SELECT * FROM savings_goal WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<SavingsGoalEntity>() {
      @Override
      @Nullable
      public SavingsGoalEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfTargetAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "target_amount");
          final int _cursorIndexOfCurrencyCode = CursorUtil.getColumnIndexOrThrow(_cursor, "currency_code");
          final int _cursorIndexOfCurrentAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "current_amount");
          final int _cursorIndexOfTargetDate = CursorUtil.getColumnIndexOrThrow(_cursor, "target_date");
          final int _cursorIndexOfIconKey = CursorUtil.getColumnIndexOrThrow(_cursor, "icon_key");
          final int _cursorIndexOfColorHex = CursorUtil.getColumnIndexOrThrow(_cursor, "color_hex");
          final int _cursorIndexOfIsCompleted = CursorUtil.getColumnIndexOrThrow(_cursor, "is_completed");
          final int _cursorIndexOfIsDeleted = CursorUtil.getColumnIndexOrThrow(_cursor, "is_deleted");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
          final SavingsGoalEntity _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final double _tmpTargetAmount;
            _tmpTargetAmount = _cursor.getDouble(_cursorIndexOfTargetAmount);
            final String _tmpCurrencyCode;
            _tmpCurrencyCode = _cursor.getString(_cursorIndexOfCurrencyCode);
            final double _tmpCurrentAmount;
            _tmpCurrentAmount = _cursor.getDouble(_cursorIndexOfCurrentAmount);
            final LocalDate _tmpTargetDate;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfTargetDate)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfTargetDate);
            }
            _tmpTargetDate = __converters.toLocalDate(_tmp);
            final String _tmpIconKey;
            _tmpIconKey = _cursor.getString(_cursorIndexOfIconKey);
            final String _tmpColorHex;
            _tmpColorHex = _cursor.getString(_cursorIndexOfColorHex);
            final boolean _tmpIsCompleted;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsCompleted);
            _tmpIsCompleted = _tmp_1 != 0;
            final boolean _tmpIsDeleted;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsDeleted);
            _tmpIsDeleted = _tmp_2 != 0;
            final LocalDateTime _tmpCreatedAt;
            final String _tmp_3;
            if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
              _tmp_3 = null;
            } else {
              _tmp_3 = _cursor.getString(_cursorIndexOfCreatedAt);
            }
            final LocalDateTime _tmp_4 = __converters.toLocalDateTime(_tmp_3);
            if (_tmp_4 == null) {
              throw new IllegalStateException("Expected NON-NULL 'kotlinx.datetime.LocalDateTime', but it was NULL.");
            } else {
              _tmpCreatedAt = _tmp_4;
            }
            _result = new SavingsGoalEntity(_tmpId,_tmpName,_tmpTargetAmount,_tmpCurrencyCode,_tmpCurrentAmount,_tmpTargetDate,_tmpIconKey,_tmpColorHex,_tmpIsCompleted,_tmpIsDeleted,_tmpCreatedAt);
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
  public Flow<List<SavingsGoalEntity>> getAllGoals() {
    final String _sql = "SELECT * FROM savings_goal WHERE is_deleted = 0 ORDER BY created_at DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"savings_goal"}, new Callable<List<SavingsGoalEntity>>() {
      @Override
      @NonNull
      public List<SavingsGoalEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfTargetAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "target_amount");
          final int _cursorIndexOfCurrencyCode = CursorUtil.getColumnIndexOrThrow(_cursor, "currency_code");
          final int _cursorIndexOfCurrentAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "current_amount");
          final int _cursorIndexOfTargetDate = CursorUtil.getColumnIndexOrThrow(_cursor, "target_date");
          final int _cursorIndexOfIconKey = CursorUtil.getColumnIndexOrThrow(_cursor, "icon_key");
          final int _cursorIndexOfColorHex = CursorUtil.getColumnIndexOrThrow(_cursor, "color_hex");
          final int _cursorIndexOfIsCompleted = CursorUtil.getColumnIndexOrThrow(_cursor, "is_completed");
          final int _cursorIndexOfIsDeleted = CursorUtil.getColumnIndexOrThrow(_cursor, "is_deleted");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
          final List<SavingsGoalEntity> _result = new ArrayList<SavingsGoalEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final SavingsGoalEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final double _tmpTargetAmount;
            _tmpTargetAmount = _cursor.getDouble(_cursorIndexOfTargetAmount);
            final String _tmpCurrencyCode;
            _tmpCurrencyCode = _cursor.getString(_cursorIndexOfCurrencyCode);
            final double _tmpCurrentAmount;
            _tmpCurrentAmount = _cursor.getDouble(_cursorIndexOfCurrentAmount);
            final LocalDate _tmpTargetDate;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfTargetDate)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfTargetDate);
            }
            _tmpTargetDate = __converters.toLocalDate(_tmp);
            final String _tmpIconKey;
            _tmpIconKey = _cursor.getString(_cursorIndexOfIconKey);
            final String _tmpColorHex;
            _tmpColorHex = _cursor.getString(_cursorIndexOfColorHex);
            final boolean _tmpIsCompleted;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfIsCompleted);
            _tmpIsCompleted = _tmp_1 != 0;
            final boolean _tmpIsDeleted;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsDeleted);
            _tmpIsDeleted = _tmp_2 != 0;
            final LocalDateTime _tmpCreatedAt;
            final String _tmp_3;
            if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
              _tmp_3 = null;
            } else {
              _tmp_3 = _cursor.getString(_cursorIndexOfCreatedAt);
            }
            final LocalDateTime _tmp_4 = __converters.toLocalDateTime(_tmp_3);
            if (_tmp_4 == null) {
              throw new IllegalStateException("Expected NON-NULL 'kotlinx.datetime.LocalDateTime', but it was NULL.");
            } else {
              _tmpCreatedAt = _tmp_4;
            }
            _item = new SavingsGoalEntity(_tmpId,_tmpName,_tmpTargetAmount,_tmpCurrencyCode,_tmpCurrentAmount,_tmpTargetDate,_tmpIconKey,_tmpColorHex,_tmpIsCompleted,_tmpIsDeleted,_tmpCreatedAt);
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
  public Flow<List<SavingsContributionEntity>> getContributionsForGoal(final long goalId) {
    final String _sql = "SELECT * FROM savings_contribution WHERE savings_goal_id = ? ORDER BY contribution_date DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, goalId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"savings_contribution"}, new Callable<List<SavingsContributionEntity>>() {
      @Override
      @NonNull
      public List<SavingsContributionEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfSavingsGoalId = CursorUtil.getColumnIndexOrThrow(_cursor, "savings_goal_id");
          final int _cursorIndexOfAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "amount");
          final int _cursorIndexOfContributionDate = CursorUtil.getColumnIndexOrThrow(_cursor, "contribution_date");
          final int _cursorIndexOfNote = CursorUtil.getColumnIndexOrThrow(_cursor, "note");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
          final List<SavingsContributionEntity> _result = new ArrayList<SavingsContributionEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final SavingsContributionEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpSavingsGoalId;
            _tmpSavingsGoalId = _cursor.getLong(_cursorIndexOfSavingsGoalId);
            final double _tmpAmount;
            _tmpAmount = _cursor.getDouble(_cursorIndexOfAmount);
            final LocalDate _tmpContributionDate;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfContributionDate)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfContributionDate);
            }
            final LocalDate _tmp_1 = __converters.toLocalDate(_tmp);
            if (_tmp_1 == null) {
              throw new IllegalStateException("Expected NON-NULL 'kotlinx.datetime.LocalDate', but it was NULL.");
            } else {
              _tmpContributionDate = _tmp_1;
            }
            final String _tmpNote;
            if (_cursor.isNull(_cursorIndexOfNote)) {
              _tmpNote = null;
            } else {
              _tmpNote = _cursor.getString(_cursorIndexOfNote);
            }
            final LocalDateTime _tmpCreatedAt;
            final String _tmp_2;
            if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getString(_cursorIndexOfCreatedAt);
            }
            final LocalDateTime _tmp_3 = __converters.toLocalDateTime(_tmp_2);
            if (_tmp_3 == null) {
              throw new IllegalStateException("Expected NON-NULL 'kotlinx.datetime.LocalDateTime', but it was NULL.");
            } else {
              _tmpCreatedAt = _tmp_3;
            }
            _item = new SavingsContributionEntity(_tmpId,_tmpSavingsGoalId,_tmpAmount,_tmpContributionDate,_tmpNote,_tmpCreatedAt);
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
