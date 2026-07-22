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
import com.patflow.app.data.local.entity.PaymentEntity;
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
public final class PaymentDao_Impl implements PaymentDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<PaymentEntity> __insertionAdapterOfPaymentEntity;

  private final Converters __converters = new Converters();

  private final EntityDeletionOrUpdateAdapter<PaymentEntity> __deletionAdapterOfPaymentEntity;

  private final EntityDeletionOrUpdateAdapter<PaymentEntity> __updateAdapterOfPaymentEntity;

  public PaymentDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfPaymentEntity = new EntityInsertionAdapter<PaymentEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `payment` (`id`,`bill_cycle_id`,`amount`,`payment_date`,`method`,`note`,`created_at`) VALUES (nullif(?, 0),?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final PaymentEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getBillCycleId());
        statement.bindDouble(3, entity.getAmount());
        final String _tmp = __converters.fromLocalDate(entity.getPaymentDate());
        if (_tmp == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, _tmp);
        }
        statement.bindString(5, entity.getMethod());
        if (entity.getNote() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getNote());
        }
        final String _tmp_1 = __converters.fromLocalDateTime(entity.getCreatedAt());
        if (_tmp_1 == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, _tmp_1);
        }
      }
    };
    this.__deletionAdapterOfPaymentEntity = new EntityDeletionOrUpdateAdapter<PaymentEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `payment` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final PaymentEntity entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfPaymentEntity = new EntityDeletionOrUpdateAdapter<PaymentEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `payment` SET `id` = ?,`bill_cycle_id` = ?,`amount` = ?,`payment_date` = ?,`method` = ?,`note` = ?,`created_at` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final PaymentEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getBillCycleId());
        statement.bindDouble(3, entity.getAmount());
        final String _tmp = __converters.fromLocalDate(entity.getPaymentDate());
        if (_tmp == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, _tmp);
        }
        statement.bindString(5, entity.getMethod());
        if (entity.getNote() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getNote());
        }
        final String _tmp_1 = __converters.fromLocalDateTime(entity.getCreatedAt());
        if (_tmp_1 == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, _tmp_1);
        }
        statement.bindLong(8, entity.getId());
      }
    };
  }

  @Override
  public Object insert(final PaymentEntity payment, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfPaymentEntity.insertAndReturnId(payment);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object delete(final PaymentEntity payment, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfPaymentEntity.handle(payment);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object update(final PaymentEntity payment, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfPaymentEntity.handle(payment);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object getById(final long id, final Continuation<? super PaymentEntity> $completion) {
    final String _sql = "SELECT * FROM payment WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<PaymentEntity>() {
      @Override
      @Nullable
      public PaymentEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfBillCycleId = CursorUtil.getColumnIndexOrThrow(_cursor, "bill_cycle_id");
          final int _cursorIndexOfAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "amount");
          final int _cursorIndexOfPaymentDate = CursorUtil.getColumnIndexOrThrow(_cursor, "payment_date");
          final int _cursorIndexOfMethod = CursorUtil.getColumnIndexOrThrow(_cursor, "method");
          final int _cursorIndexOfNote = CursorUtil.getColumnIndexOrThrow(_cursor, "note");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
          final PaymentEntity _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpBillCycleId;
            _tmpBillCycleId = _cursor.getLong(_cursorIndexOfBillCycleId);
            final double _tmpAmount;
            _tmpAmount = _cursor.getDouble(_cursorIndexOfAmount);
            final LocalDate _tmpPaymentDate;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfPaymentDate)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfPaymentDate);
            }
            final LocalDate _tmp_1 = __converters.toLocalDate(_tmp);
            if (_tmp_1 == null) {
              throw new IllegalStateException("Expected NON-NULL 'kotlinx.datetime.LocalDate', but it was NULL.");
            } else {
              _tmpPaymentDate = _tmp_1;
            }
            final String _tmpMethod;
            _tmpMethod = _cursor.getString(_cursorIndexOfMethod);
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
            _result = new PaymentEntity(_tmpId,_tmpBillCycleId,_tmpAmount,_tmpPaymentDate,_tmpMethod,_tmpNote,_tmpCreatedAt);
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
  public Flow<List<PaymentEntity>> getByBillCycle(final long billCycleId) {
    final String _sql = "SELECT * FROM payment WHERE bill_cycle_id = ? ORDER BY payment_date DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, billCycleId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"payment"}, new Callable<List<PaymentEntity>>() {
      @Override
      @NonNull
      public List<PaymentEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfBillCycleId = CursorUtil.getColumnIndexOrThrow(_cursor, "bill_cycle_id");
          final int _cursorIndexOfAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "amount");
          final int _cursorIndexOfPaymentDate = CursorUtil.getColumnIndexOrThrow(_cursor, "payment_date");
          final int _cursorIndexOfMethod = CursorUtil.getColumnIndexOrThrow(_cursor, "method");
          final int _cursorIndexOfNote = CursorUtil.getColumnIndexOrThrow(_cursor, "note");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
          final List<PaymentEntity> _result = new ArrayList<PaymentEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final PaymentEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpBillCycleId;
            _tmpBillCycleId = _cursor.getLong(_cursorIndexOfBillCycleId);
            final double _tmpAmount;
            _tmpAmount = _cursor.getDouble(_cursorIndexOfAmount);
            final LocalDate _tmpPaymentDate;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfPaymentDate)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfPaymentDate);
            }
            final LocalDate _tmp_1 = __converters.toLocalDate(_tmp);
            if (_tmp_1 == null) {
              throw new IllegalStateException("Expected NON-NULL 'kotlinx.datetime.LocalDate', but it was NULL.");
            } else {
              _tmpPaymentDate = _tmp_1;
            }
            final String _tmpMethod;
            _tmpMethod = _cursor.getString(_cursorIndexOfMethod);
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
            _item = new PaymentEntity(_tmpId,_tmpBillCycleId,_tmpAmount,_tmpPaymentDate,_tmpMethod,_tmpNote,_tmpCreatedAt);
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
  public Flow<List<PaymentEntity>> getByDateRange(final String start, final String end) {
    final String _sql = "SELECT * FROM payment WHERE payment_date BETWEEN ? AND ? ORDER BY payment_date DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindString(_argIndex, start);
    _argIndex = 2;
    _statement.bindString(_argIndex, end);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"payment"}, new Callable<List<PaymentEntity>>() {
      @Override
      @NonNull
      public List<PaymentEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfBillCycleId = CursorUtil.getColumnIndexOrThrow(_cursor, "bill_cycle_id");
          final int _cursorIndexOfAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "amount");
          final int _cursorIndexOfPaymentDate = CursorUtil.getColumnIndexOrThrow(_cursor, "payment_date");
          final int _cursorIndexOfMethod = CursorUtil.getColumnIndexOrThrow(_cursor, "method");
          final int _cursorIndexOfNote = CursorUtil.getColumnIndexOrThrow(_cursor, "note");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
          final List<PaymentEntity> _result = new ArrayList<PaymentEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final PaymentEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpBillCycleId;
            _tmpBillCycleId = _cursor.getLong(_cursorIndexOfBillCycleId);
            final double _tmpAmount;
            _tmpAmount = _cursor.getDouble(_cursorIndexOfAmount);
            final LocalDate _tmpPaymentDate;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfPaymentDate)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfPaymentDate);
            }
            final LocalDate _tmp_1 = __converters.toLocalDate(_tmp);
            if (_tmp_1 == null) {
              throw new IllegalStateException("Expected NON-NULL 'kotlinx.datetime.LocalDate', but it was NULL.");
            } else {
              _tmpPaymentDate = _tmp_1;
            }
            final String _tmpMethod;
            _tmpMethod = _cursor.getString(_cursorIndexOfMethod);
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
            _item = new PaymentEntity(_tmpId,_tmpBillCycleId,_tmpAmount,_tmpPaymentDate,_tmpMethod,_tmpNote,_tmpCreatedAt);
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
