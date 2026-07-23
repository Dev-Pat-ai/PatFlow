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
import com.patflow.app.data.local.entity.ReminderEntity;
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
import kotlinx.datetime.LocalDateTime;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class ReminderDao_Impl implements ReminderDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<ReminderEntity> __insertionAdapterOfReminderEntity;

  private final Converters __converters = new Converters();

  private final EntityDeletionOrUpdateAdapter<ReminderEntity> __deletionAdapterOfReminderEntity;

  private final EntityDeletionOrUpdateAdapter<ReminderEntity> __updateAdapterOfReminderEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeleteByCycle;

  public ReminderDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfReminderEntity = new EntityInsertionAdapter<ReminderEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `reminder` (`id`,`bill_cycle_id`,`remind_at`,`is_sent`,`offset_days`) VALUES (nullif(?, 0),?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final ReminderEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getBillCycleId());
        final String _tmp = __converters.fromLocalDateTime(entity.getRemindAt());
        if (_tmp == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, _tmp);
        }
        final int _tmp_1 = entity.isSent() ? 1 : 0;
        statement.bindLong(4, _tmp_1);
        statement.bindLong(5, entity.getOffsetDays());
      }
    };
    this.__deletionAdapterOfReminderEntity = new EntityDeletionOrUpdateAdapter<ReminderEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `reminder` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final ReminderEntity entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfReminderEntity = new EntityDeletionOrUpdateAdapter<ReminderEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `reminder` SET `id` = ?,`bill_cycle_id` = ?,`remind_at` = ?,`is_sent` = ?,`offset_days` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final ReminderEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getBillCycleId());
        final String _tmp = __converters.fromLocalDateTime(entity.getRemindAt());
        if (_tmp == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, _tmp);
        }
        final int _tmp_1 = entity.isSent() ? 1 : 0;
        statement.bindLong(4, _tmp_1);
        statement.bindLong(5, entity.getOffsetDays());
        statement.bindLong(6, entity.getId());
      }
    };
    this.__preparedStmtOfDeleteByCycle = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM reminder WHERE bill_cycle_id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insert(final ReminderEntity reminder,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfReminderEntity.insertAndReturnId(reminder);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object delete(final ReminderEntity reminder,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfReminderEntity.handle(reminder);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object update(final ReminderEntity reminder,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfReminderEntity.handle(reminder);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteByCycle(final long cycleId, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteByCycle.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, cycleId);
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
          __preparedStmtOfDeleteByCycle.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<ReminderEntity>> getByBillCycle(final long billCycleId) {
    final String _sql = "SELECT * FROM reminder WHERE bill_cycle_id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, billCycleId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"reminder"}, new Callable<List<ReminderEntity>>() {
      @Override
      @NonNull
      public List<ReminderEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfBillCycleId = CursorUtil.getColumnIndexOrThrow(_cursor, "bill_cycle_id");
          final int _cursorIndexOfRemindAt = CursorUtil.getColumnIndexOrThrow(_cursor, "remind_at");
          final int _cursorIndexOfIsSent = CursorUtil.getColumnIndexOrThrow(_cursor, "is_sent");
          final int _cursorIndexOfOffsetDays = CursorUtil.getColumnIndexOrThrow(_cursor, "offset_days");
          final List<ReminderEntity> _result = new ArrayList<ReminderEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ReminderEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpBillCycleId;
            _tmpBillCycleId = _cursor.getLong(_cursorIndexOfBillCycleId);
            final LocalDateTime _tmpRemindAt;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfRemindAt)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfRemindAt);
            }
            final LocalDateTime _tmp_1 = __converters.toLocalDateTime(_tmp);
            if (_tmp_1 == null) {
              throw new IllegalStateException("Expected NON-NULL 'kotlinx.datetime.LocalDateTime', but it was NULL.");
            } else {
              _tmpRemindAt = _tmp_1;
            }
            final boolean _tmpIsSent;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsSent);
            _tmpIsSent = _tmp_2 != 0;
            final int _tmpOffsetDays;
            _tmpOffsetDays = _cursor.getInt(_cursorIndexOfOffsetDays);
            _item = new ReminderEntity(_tmpId,_tmpBillCycleId,_tmpRemindAt,_tmpIsSent,_tmpOffsetDays);
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
  public Object getDuePending(final String nowIso,
      final Continuation<? super List<ReminderEntity>> $completion) {
    final String _sql = "SELECT * FROM reminder WHERE is_sent = 0 AND remind_at <= ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, nowIso);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<ReminderEntity>>() {
      @Override
      @NonNull
      public List<ReminderEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfBillCycleId = CursorUtil.getColumnIndexOrThrow(_cursor, "bill_cycle_id");
          final int _cursorIndexOfRemindAt = CursorUtil.getColumnIndexOrThrow(_cursor, "remind_at");
          final int _cursorIndexOfIsSent = CursorUtil.getColumnIndexOrThrow(_cursor, "is_sent");
          final int _cursorIndexOfOffsetDays = CursorUtil.getColumnIndexOrThrow(_cursor, "offset_days");
          final List<ReminderEntity> _result = new ArrayList<ReminderEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ReminderEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpBillCycleId;
            _tmpBillCycleId = _cursor.getLong(_cursorIndexOfBillCycleId);
            final LocalDateTime _tmpRemindAt;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfRemindAt)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfRemindAt);
            }
            final LocalDateTime _tmp_1 = __converters.toLocalDateTime(_tmp);
            if (_tmp_1 == null) {
              throw new IllegalStateException("Expected NON-NULL 'kotlinx.datetime.LocalDateTime', but it was NULL.");
            } else {
              _tmpRemindAt = _tmp_1;
            }
            final boolean _tmpIsSent;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsSent);
            _tmpIsSent = _tmp_2 != 0;
            final int _tmpOffsetDays;
            _tmpOffsetDays = _cursor.getInt(_cursorIndexOfOffsetDays);
            _item = new ReminderEntity(_tmpId,_tmpBillCycleId,_tmpRemindAt,_tmpIsSent,_tmpOffsetDays);
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
  public Object getById(final long id, final Continuation<? super ReminderEntity> $completion) {
    final String _sql = "SELECT * FROM reminder WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<ReminderEntity>() {
      @Override
      @Nullable
      public ReminderEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfBillCycleId = CursorUtil.getColumnIndexOrThrow(_cursor, "bill_cycle_id");
          final int _cursorIndexOfRemindAt = CursorUtil.getColumnIndexOrThrow(_cursor, "remind_at");
          final int _cursorIndexOfIsSent = CursorUtil.getColumnIndexOrThrow(_cursor, "is_sent");
          final int _cursorIndexOfOffsetDays = CursorUtil.getColumnIndexOrThrow(_cursor, "offset_days");
          final ReminderEntity _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpBillCycleId;
            _tmpBillCycleId = _cursor.getLong(_cursorIndexOfBillCycleId);
            final LocalDateTime _tmpRemindAt;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfRemindAt)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfRemindAt);
            }
            final LocalDateTime _tmp_1 = __converters.toLocalDateTime(_tmp);
            if (_tmp_1 == null) {
              throw new IllegalStateException("Expected NON-NULL 'kotlinx.datetime.LocalDateTime', but it was NULL.");
            } else {
              _tmpRemindAt = _tmp_1;
            }
            final boolean _tmpIsSent;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfIsSent);
            _tmpIsSent = _tmp_2 != 0;
            final int _tmpOffsetDays;
            _tmpOffsetDays = _cursor.getInt(_cursorIndexOfOffsetDays);
            _result = new ReminderEntity(_tmpId,_tmpBillCycleId,_tmpRemindAt,_tmpIsSent,_tmpOffsetDays);
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

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
