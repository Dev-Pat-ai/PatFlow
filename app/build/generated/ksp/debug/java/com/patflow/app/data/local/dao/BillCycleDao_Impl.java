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
import com.patflow.app.data.local.entity.BillCycleEntity;
import java.lang.Class;
import java.lang.Exception;
import java.lang.IllegalStateException;
import java.lang.Integer;
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
public final class BillCycleDao_Impl implements BillCycleDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<BillCycleEntity> __insertionAdapterOfBillCycleEntity;

  private final Converters __converters = new Converters();

  private final EntityInsertionAdapter<BillCycleEntity> __insertionAdapterOfBillCycleEntity_1;

  private final EntityDeletionOrUpdateAdapter<BillCycleEntity> __deletionAdapterOfBillCycleEntity;

  private final EntityDeletionOrUpdateAdapter<BillCycleEntity> __updateAdapterOfBillCycleEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAll;

  public BillCycleDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfBillCycleEntity = new EntityInsertionAdapter<BillCycleEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `bill_cycle` (`id`,`bill_id`,`period_start`,`due_date`,`amount_due`,`amount_paid`,`status`,`created_at`,`updated_at`,`installment_number`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final BillCycleEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getBillId());
        final String _tmp = __converters.fromLocalDate(entity.getPeriodStart());
        if (_tmp == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, _tmp);
        }
        final String _tmp_1 = __converters.fromLocalDate(entity.getDueDate());
        if (_tmp_1 == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, _tmp_1);
        }
        statement.bindDouble(5, entity.getAmountDue());
        statement.bindDouble(6, entity.getAmountPaid());
        statement.bindString(7, entity.getStatus());
        final String _tmp_2 = __converters.fromLocalDateTime(entity.getCreatedAt());
        if (_tmp_2 == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, _tmp_2);
        }
        final String _tmp_3 = __converters.fromLocalDateTime(entity.getUpdatedAt());
        if (_tmp_3 == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, _tmp_3);
        }
        if (entity.getInstallmentNumber() == null) {
          statement.bindNull(10);
        } else {
          statement.bindLong(10, entity.getInstallmentNumber());
        }
      }
    };
    this.__insertionAdapterOfBillCycleEntity_1 = new EntityInsertionAdapter<BillCycleEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `bill_cycle` (`id`,`bill_id`,`period_start`,`due_date`,`amount_due`,`amount_paid`,`status`,`created_at`,`updated_at`,`installment_number`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final BillCycleEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getBillId());
        final String _tmp = __converters.fromLocalDate(entity.getPeriodStart());
        if (_tmp == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, _tmp);
        }
        final String _tmp_1 = __converters.fromLocalDate(entity.getDueDate());
        if (_tmp_1 == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, _tmp_1);
        }
        statement.bindDouble(5, entity.getAmountDue());
        statement.bindDouble(6, entity.getAmountPaid());
        statement.bindString(7, entity.getStatus());
        final String _tmp_2 = __converters.fromLocalDateTime(entity.getCreatedAt());
        if (_tmp_2 == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, _tmp_2);
        }
        final String _tmp_3 = __converters.fromLocalDateTime(entity.getUpdatedAt());
        if (_tmp_3 == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, _tmp_3);
        }
        if (entity.getInstallmentNumber() == null) {
          statement.bindNull(10);
        } else {
          statement.bindLong(10, entity.getInstallmentNumber());
        }
      }
    };
    this.__deletionAdapterOfBillCycleEntity = new EntityDeletionOrUpdateAdapter<BillCycleEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `bill_cycle` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final BillCycleEntity entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfBillCycleEntity = new EntityDeletionOrUpdateAdapter<BillCycleEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `bill_cycle` SET `id` = ?,`bill_id` = ?,`period_start` = ?,`due_date` = ?,`amount_due` = ?,`amount_paid` = ?,`status` = ?,`created_at` = ?,`updated_at` = ?,`installment_number` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final BillCycleEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getBillId());
        final String _tmp = __converters.fromLocalDate(entity.getPeriodStart());
        if (_tmp == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, _tmp);
        }
        final String _tmp_1 = __converters.fromLocalDate(entity.getDueDate());
        if (_tmp_1 == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, _tmp_1);
        }
        statement.bindDouble(5, entity.getAmountDue());
        statement.bindDouble(6, entity.getAmountPaid());
        statement.bindString(7, entity.getStatus());
        final String _tmp_2 = __converters.fromLocalDateTime(entity.getCreatedAt());
        if (_tmp_2 == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, _tmp_2);
        }
        final String _tmp_3 = __converters.fromLocalDateTime(entity.getUpdatedAt());
        if (_tmp_3 == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, _tmp_3);
        }
        if (entity.getInstallmentNumber() == null) {
          statement.bindNull(10);
        } else {
          statement.bindLong(10, entity.getInstallmentNumber());
        }
        statement.bindLong(11, entity.getId());
      }
    };
    this.__preparedStmtOfDeleteAll = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM bill_cycle";
        return _query;
      }
    };
  }

  @Override
  public Object insert(final BillCycleEntity cycle, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfBillCycleEntity.insertAndReturnId(cycle);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertAll(final List<BillCycleEntity> cycles,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfBillCycleEntity_1.insert(cycles);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object delete(final BillCycleEntity cycle, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfBillCycleEntity.handle(cycle);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object update(final BillCycleEntity cycle, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfBillCycleEntity.handle(cycle);
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
  public Object getById(final long id, final Continuation<? super BillCycleEntity> $completion) {
    final String _sql = "SELECT * FROM bill_cycle WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<BillCycleEntity>() {
      @Override
      @Nullable
      public BillCycleEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfBillId = CursorUtil.getColumnIndexOrThrow(_cursor, "bill_id");
          final int _cursorIndexOfPeriodStart = CursorUtil.getColumnIndexOrThrow(_cursor, "period_start");
          final int _cursorIndexOfDueDate = CursorUtil.getColumnIndexOrThrow(_cursor, "due_date");
          final int _cursorIndexOfAmountDue = CursorUtil.getColumnIndexOrThrow(_cursor, "amount_due");
          final int _cursorIndexOfAmountPaid = CursorUtil.getColumnIndexOrThrow(_cursor, "amount_paid");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updated_at");
          final int _cursorIndexOfInstallmentNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "installment_number");
          final BillCycleEntity _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpBillId;
            _tmpBillId = _cursor.getLong(_cursorIndexOfBillId);
            final LocalDate _tmpPeriodStart;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfPeriodStart)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfPeriodStart);
            }
            final LocalDate _tmp_1 = __converters.toLocalDate(_tmp);
            if (_tmp_1 == null) {
              throw new IllegalStateException("Expected NON-NULL 'kotlinx.datetime.LocalDate', but it was NULL.");
            } else {
              _tmpPeriodStart = _tmp_1;
            }
            final LocalDate _tmpDueDate;
            final String _tmp_2;
            if (_cursor.isNull(_cursorIndexOfDueDate)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getString(_cursorIndexOfDueDate);
            }
            final LocalDate _tmp_3 = __converters.toLocalDate(_tmp_2);
            if (_tmp_3 == null) {
              throw new IllegalStateException("Expected NON-NULL 'kotlinx.datetime.LocalDate', but it was NULL.");
            } else {
              _tmpDueDate = _tmp_3;
            }
            final double _tmpAmountDue;
            _tmpAmountDue = _cursor.getDouble(_cursorIndexOfAmountDue);
            final double _tmpAmountPaid;
            _tmpAmountPaid = _cursor.getDouble(_cursorIndexOfAmountPaid);
            final String _tmpStatus;
            _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            final LocalDateTime _tmpCreatedAt;
            final String _tmp_4;
            if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
              _tmp_4 = null;
            } else {
              _tmp_4 = _cursor.getString(_cursorIndexOfCreatedAt);
            }
            final LocalDateTime _tmp_5 = __converters.toLocalDateTime(_tmp_4);
            if (_tmp_5 == null) {
              throw new IllegalStateException("Expected NON-NULL 'kotlinx.datetime.LocalDateTime', but it was NULL.");
            } else {
              _tmpCreatedAt = _tmp_5;
            }
            final LocalDateTime _tmpUpdatedAt;
            final String _tmp_6;
            if (_cursor.isNull(_cursorIndexOfUpdatedAt)) {
              _tmp_6 = null;
            } else {
              _tmp_6 = _cursor.getString(_cursorIndexOfUpdatedAt);
            }
            final LocalDateTime _tmp_7 = __converters.toLocalDateTime(_tmp_6);
            if (_tmp_7 == null) {
              throw new IllegalStateException("Expected NON-NULL 'kotlinx.datetime.LocalDateTime', but it was NULL.");
            } else {
              _tmpUpdatedAt = _tmp_7;
            }
            final Integer _tmpInstallmentNumber;
            if (_cursor.isNull(_cursorIndexOfInstallmentNumber)) {
              _tmpInstallmentNumber = null;
            } else {
              _tmpInstallmentNumber = _cursor.getInt(_cursorIndexOfInstallmentNumber);
            }
            _result = new BillCycleEntity(_tmpId,_tmpBillId,_tmpPeriodStart,_tmpDueDate,_tmpAmountDue,_tmpAmountPaid,_tmpStatus,_tmpCreatedAt,_tmpUpdatedAt,_tmpInstallmentNumber);
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
  public Flow<List<BillCycleEntity>> getByBill(final long billId) {
    final String _sql = "SELECT * FROM bill_cycle WHERE bill_id = ? ORDER BY due_date DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, billId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"bill_cycle"}, new Callable<List<BillCycleEntity>>() {
      @Override
      @NonNull
      public List<BillCycleEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfBillId = CursorUtil.getColumnIndexOrThrow(_cursor, "bill_id");
          final int _cursorIndexOfPeriodStart = CursorUtil.getColumnIndexOrThrow(_cursor, "period_start");
          final int _cursorIndexOfDueDate = CursorUtil.getColumnIndexOrThrow(_cursor, "due_date");
          final int _cursorIndexOfAmountDue = CursorUtil.getColumnIndexOrThrow(_cursor, "amount_due");
          final int _cursorIndexOfAmountPaid = CursorUtil.getColumnIndexOrThrow(_cursor, "amount_paid");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updated_at");
          final int _cursorIndexOfInstallmentNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "installment_number");
          final List<BillCycleEntity> _result = new ArrayList<BillCycleEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final BillCycleEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpBillId;
            _tmpBillId = _cursor.getLong(_cursorIndexOfBillId);
            final LocalDate _tmpPeriodStart;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfPeriodStart)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfPeriodStart);
            }
            final LocalDate _tmp_1 = __converters.toLocalDate(_tmp);
            if (_tmp_1 == null) {
              throw new IllegalStateException("Expected NON-NULL 'kotlinx.datetime.LocalDate', but it was NULL.");
            } else {
              _tmpPeriodStart = _tmp_1;
            }
            final LocalDate _tmpDueDate;
            final String _tmp_2;
            if (_cursor.isNull(_cursorIndexOfDueDate)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getString(_cursorIndexOfDueDate);
            }
            final LocalDate _tmp_3 = __converters.toLocalDate(_tmp_2);
            if (_tmp_3 == null) {
              throw new IllegalStateException("Expected NON-NULL 'kotlinx.datetime.LocalDate', but it was NULL.");
            } else {
              _tmpDueDate = _tmp_3;
            }
            final double _tmpAmountDue;
            _tmpAmountDue = _cursor.getDouble(_cursorIndexOfAmountDue);
            final double _tmpAmountPaid;
            _tmpAmountPaid = _cursor.getDouble(_cursorIndexOfAmountPaid);
            final String _tmpStatus;
            _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            final LocalDateTime _tmpCreatedAt;
            final String _tmp_4;
            if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
              _tmp_4 = null;
            } else {
              _tmp_4 = _cursor.getString(_cursorIndexOfCreatedAt);
            }
            final LocalDateTime _tmp_5 = __converters.toLocalDateTime(_tmp_4);
            if (_tmp_5 == null) {
              throw new IllegalStateException("Expected NON-NULL 'kotlinx.datetime.LocalDateTime', but it was NULL.");
            } else {
              _tmpCreatedAt = _tmp_5;
            }
            final LocalDateTime _tmpUpdatedAt;
            final String _tmp_6;
            if (_cursor.isNull(_cursorIndexOfUpdatedAt)) {
              _tmp_6 = null;
            } else {
              _tmp_6 = _cursor.getString(_cursorIndexOfUpdatedAt);
            }
            final LocalDateTime _tmp_7 = __converters.toLocalDateTime(_tmp_6);
            if (_tmp_7 == null) {
              throw new IllegalStateException("Expected NON-NULL 'kotlinx.datetime.LocalDateTime', but it was NULL.");
            } else {
              _tmpUpdatedAt = _tmp_7;
            }
            final Integer _tmpInstallmentNumber;
            if (_cursor.isNull(_cursorIndexOfInstallmentNumber)) {
              _tmpInstallmentNumber = null;
            } else {
              _tmpInstallmentNumber = _cursor.getInt(_cursorIndexOfInstallmentNumber);
            }
            _item = new BillCycleEntity(_tmpId,_tmpBillId,_tmpPeriodStart,_tmpDueDate,_tmpAmountDue,_tmpAmountPaid,_tmpStatus,_tmpCreatedAt,_tmpUpdatedAt,_tmpInstallmentNumber);
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
  public Flow<List<BillCycleEntity>> getByDueDate(final String date) {
    final String _sql = "SELECT * FROM bill_cycle WHERE due_date = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, date);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"bill_cycle"}, new Callable<List<BillCycleEntity>>() {
      @Override
      @NonNull
      public List<BillCycleEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfBillId = CursorUtil.getColumnIndexOrThrow(_cursor, "bill_id");
          final int _cursorIndexOfPeriodStart = CursorUtil.getColumnIndexOrThrow(_cursor, "period_start");
          final int _cursorIndexOfDueDate = CursorUtil.getColumnIndexOrThrow(_cursor, "due_date");
          final int _cursorIndexOfAmountDue = CursorUtil.getColumnIndexOrThrow(_cursor, "amount_due");
          final int _cursorIndexOfAmountPaid = CursorUtil.getColumnIndexOrThrow(_cursor, "amount_paid");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updated_at");
          final int _cursorIndexOfInstallmentNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "installment_number");
          final List<BillCycleEntity> _result = new ArrayList<BillCycleEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final BillCycleEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpBillId;
            _tmpBillId = _cursor.getLong(_cursorIndexOfBillId);
            final LocalDate _tmpPeriodStart;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfPeriodStart)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfPeriodStart);
            }
            final LocalDate _tmp_1 = __converters.toLocalDate(_tmp);
            if (_tmp_1 == null) {
              throw new IllegalStateException("Expected NON-NULL 'kotlinx.datetime.LocalDate', but it was NULL.");
            } else {
              _tmpPeriodStart = _tmp_1;
            }
            final LocalDate _tmpDueDate;
            final String _tmp_2;
            if (_cursor.isNull(_cursorIndexOfDueDate)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getString(_cursorIndexOfDueDate);
            }
            final LocalDate _tmp_3 = __converters.toLocalDate(_tmp_2);
            if (_tmp_3 == null) {
              throw new IllegalStateException("Expected NON-NULL 'kotlinx.datetime.LocalDate', but it was NULL.");
            } else {
              _tmpDueDate = _tmp_3;
            }
            final double _tmpAmountDue;
            _tmpAmountDue = _cursor.getDouble(_cursorIndexOfAmountDue);
            final double _tmpAmountPaid;
            _tmpAmountPaid = _cursor.getDouble(_cursorIndexOfAmountPaid);
            final String _tmpStatus;
            _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            final LocalDateTime _tmpCreatedAt;
            final String _tmp_4;
            if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
              _tmp_4 = null;
            } else {
              _tmp_4 = _cursor.getString(_cursorIndexOfCreatedAt);
            }
            final LocalDateTime _tmp_5 = __converters.toLocalDateTime(_tmp_4);
            if (_tmp_5 == null) {
              throw new IllegalStateException("Expected NON-NULL 'kotlinx.datetime.LocalDateTime', but it was NULL.");
            } else {
              _tmpCreatedAt = _tmp_5;
            }
            final LocalDateTime _tmpUpdatedAt;
            final String _tmp_6;
            if (_cursor.isNull(_cursorIndexOfUpdatedAt)) {
              _tmp_6 = null;
            } else {
              _tmp_6 = _cursor.getString(_cursorIndexOfUpdatedAt);
            }
            final LocalDateTime _tmp_7 = __converters.toLocalDateTime(_tmp_6);
            if (_tmp_7 == null) {
              throw new IllegalStateException("Expected NON-NULL 'kotlinx.datetime.LocalDateTime', but it was NULL.");
            } else {
              _tmpUpdatedAt = _tmp_7;
            }
            final Integer _tmpInstallmentNumber;
            if (_cursor.isNull(_cursorIndexOfInstallmentNumber)) {
              _tmpInstallmentNumber = null;
            } else {
              _tmpInstallmentNumber = _cursor.getInt(_cursorIndexOfInstallmentNumber);
            }
            _item = new BillCycleEntity(_tmpId,_tmpBillId,_tmpPeriodStart,_tmpDueDate,_tmpAmountDue,_tmpAmountPaid,_tmpStatus,_tmpCreatedAt,_tmpUpdatedAt,_tmpInstallmentNumber);
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
  public Flow<List<BillCycleEntity>> getByStatus(final String status) {
    final String _sql = "SELECT * FROM bill_cycle WHERE status = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, status);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"bill_cycle"}, new Callable<List<BillCycleEntity>>() {
      @Override
      @NonNull
      public List<BillCycleEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfBillId = CursorUtil.getColumnIndexOrThrow(_cursor, "bill_id");
          final int _cursorIndexOfPeriodStart = CursorUtil.getColumnIndexOrThrow(_cursor, "period_start");
          final int _cursorIndexOfDueDate = CursorUtil.getColumnIndexOrThrow(_cursor, "due_date");
          final int _cursorIndexOfAmountDue = CursorUtil.getColumnIndexOrThrow(_cursor, "amount_due");
          final int _cursorIndexOfAmountPaid = CursorUtil.getColumnIndexOrThrow(_cursor, "amount_paid");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updated_at");
          final int _cursorIndexOfInstallmentNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "installment_number");
          final List<BillCycleEntity> _result = new ArrayList<BillCycleEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final BillCycleEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpBillId;
            _tmpBillId = _cursor.getLong(_cursorIndexOfBillId);
            final LocalDate _tmpPeriodStart;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfPeriodStart)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfPeriodStart);
            }
            final LocalDate _tmp_1 = __converters.toLocalDate(_tmp);
            if (_tmp_1 == null) {
              throw new IllegalStateException("Expected NON-NULL 'kotlinx.datetime.LocalDate', but it was NULL.");
            } else {
              _tmpPeriodStart = _tmp_1;
            }
            final LocalDate _tmpDueDate;
            final String _tmp_2;
            if (_cursor.isNull(_cursorIndexOfDueDate)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getString(_cursorIndexOfDueDate);
            }
            final LocalDate _tmp_3 = __converters.toLocalDate(_tmp_2);
            if (_tmp_3 == null) {
              throw new IllegalStateException("Expected NON-NULL 'kotlinx.datetime.LocalDate', but it was NULL.");
            } else {
              _tmpDueDate = _tmp_3;
            }
            final double _tmpAmountDue;
            _tmpAmountDue = _cursor.getDouble(_cursorIndexOfAmountDue);
            final double _tmpAmountPaid;
            _tmpAmountPaid = _cursor.getDouble(_cursorIndexOfAmountPaid);
            final String _tmpStatus;
            _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            final LocalDateTime _tmpCreatedAt;
            final String _tmp_4;
            if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
              _tmp_4 = null;
            } else {
              _tmp_4 = _cursor.getString(_cursorIndexOfCreatedAt);
            }
            final LocalDateTime _tmp_5 = __converters.toLocalDateTime(_tmp_4);
            if (_tmp_5 == null) {
              throw new IllegalStateException("Expected NON-NULL 'kotlinx.datetime.LocalDateTime', but it was NULL.");
            } else {
              _tmpCreatedAt = _tmp_5;
            }
            final LocalDateTime _tmpUpdatedAt;
            final String _tmp_6;
            if (_cursor.isNull(_cursorIndexOfUpdatedAt)) {
              _tmp_6 = null;
            } else {
              _tmp_6 = _cursor.getString(_cursorIndexOfUpdatedAt);
            }
            final LocalDateTime _tmp_7 = __converters.toLocalDateTime(_tmp_6);
            if (_tmp_7 == null) {
              throw new IllegalStateException("Expected NON-NULL 'kotlinx.datetime.LocalDateTime', but it was NULL.");
            } else {
              _tmpUpdatedAt = _tmp_7;
            }
            final Integer _tmpInstallmentNumber;
            if (_cursor.isNull(_cursorIndexOfInstallmentNumber)) {
              _tmpInstallmentNumber = null;
            } else {
              _tmpInstallmentNumber = _cursor.getInt(_cursorIndexOfInstallmentNumber);
            }
            _item = new BillCycleEntity(_tmpId,_tmpBillId,_tmpPeriodStart,_tmpDueDate,_tmpAmountDue,_tmpAmountPaid,_tmpStatus,_tmpCreatedAt,_tmpUpdatedAt,_tmpInstallmentNumber);
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
  public Flow<List<BillCycleEntity>> getByDateRange(final String start, final String end) {
    final String _sql = "SELECT * FROM bill_cycle WHERE due_date BETWEEN ? AND ? ORDER BY due_date ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindString(_argIndex, start);
    _argIndex = 2;
    _statement.bindString(_argIndex, end);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"bill_cycle"}, new Callable<List<BillCycleEntity>>() {
      @Override
      @NonNull
      public List<BillCycleEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfBillId = CursorUtil.getColumnIndexOrThrow(_cursor, "bill_id");
          final int _cursorIndexOfPeriodStart = CursorUtil.getColumnIndexOrThrow(_cursor, "period_start");
          final int _cursorIndexOfDueDate = CursorUtil.getColumnIndexOrThrow(_cursor, "due_date");
          final int _cursorIndexOfAmountDue = CursorUtil.getColumnIndexOrThrow(_cursor, "amount_due");
          final int _cursorIndexOfAmountPaid = CursorUtil.getColumnIndexOrThrow(_cursor, "amount_paid");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updated_at");
          final int _cursorIndexOfInstallmentNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "installment_number");
          final List<BillCycleEntity> _result = new ArrayList<BillCycleEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final BillCycleEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpBillId;
            _tmpBillId = _cursor.getLong(_cursorIndexOfBillId);
            final LocalDate _tmpPeriodStart;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfPeriodStart)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfPeriodStart);
            }
            final LocalDate _tmp_1 = __converters.toLocalDate(_tmp);
            if (_tmp_1 == null) {
              throw new IllegalStateException("Expected NON-NULL 'kotlinx.datetime.LocalDate', but it was NULL.");
            } else {
              _tmpPeriodStart = _tmp_1;
            }
            final LocalDate _tmpDueDate;
            final String _tmp_2;
            if (_cursor.isNull(_cursorIndexOfDueDate)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getString(_cursorIndexOfDueDate);
            }
            final LocalDate _tmp_3 = __converters.toLocalDate(_tmp_2);
            if (_tmp_3 == null) {
              throw new IllegalStateException("Expected NON-NULL 'kotlinx.datetime.LocalDate', but it was NULL.");
            } else {
              _tmpDueDate = _tmp_3;
            }
            final double _tmpAmountDue;
            _tmpAmountDue = _cursor.getDouble(_cursorIndexOfAmountDue);
            final double _tmpAmountPaid;
            _tmpAmountPaid = _cursor.getDouble(_cursorIndexOfAmountPaid);
            final String _tmpStatus;
            _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            final LocalDateTime _tmpCreatedAt;
            final String _tmp_4;
            if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
              _tmp_4 = null;
            } else {
              _tmp_4 = _cursor.getString(_cursorIndexOfCreatedAt);
            }
            final LocalDateTime _tmp_5 = __converters.toLocalDateTime(_tmp_4);
            if (_tmp_5 == null) {
              throw new IllegalStateException("Expected NON-NULL 'kotlinx.datetime.LocalDateTime', but it was NULL.");
            } else {
              _tmpCreatedAt = _tmp_5;
            }
            final LocalDateTime _tmpUpdatedAt;
            final String _tmp_6;
            if (_cursor.isNull(_cursorIndexOfUpdatedAt)) {
              _tmp_6 = null;
            } else {
              _tmp_6 = _cursor.getString(_cursorIndexOfUpdatedAt);
            }
            final LocalDateTime _tmp_7 = __converters.toLocalDateTime(_tmp_6);
            if (_tmp_7 == null) {
              throw new IllegalStateException("Expected NON-NULL 'kotlinx.datetime.LocalDateTime', but it was NULL.");
            } else {
              _tmpUpdatedAt = _tmp_7;
            }
            final Integer _tmpInstallmentNumber;
            if (_cursor.isNull(_cursorIndexOfInstallmentNumber)) {
              _tmpInstallmentNumber = null;
            } else {
              _tmpInstallmentNumber = _cursor.getInt(_cursorIndexOfInstallmentNumber);
            }
            _item = new BillCycleEntity(_tmpId,_tmpBillId,_tmpPeriodStart,_tmpDueDate,_tmpAmountDue,_tmpAmountPaid,_tmpStatus,_tmpCreatedAt,_tmpUpdatedAt,_tmpInstallmentNumber);
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
  public Flow<List<BillCycleEntity>> getUpcoming(final int limit) {
    final String _sql = "\n"
            + "        SELECT * FROM bill_cycle \n"
            + "        WHERE status != 'PAID' \n"
            + "        ORDER BY due_date ASC \n"
            + "        LIMIT ?\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, limit);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"bill_cycle"}, new Callable<List<BillCycleEntity>>() {
      @Override
      @NonNull
      public List<BillCycleEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfBillId = CursorUtil.getColumnIndexOrThrow(_cursor, "bill_id");
          final int _cursorIndexOfPeriodStart = CursorUtil.getColumnIndexOrThrow(_cursor, "period_start");
          final int _cursorIndexOfDueDate = CursorUtil.getColumnIndexOrThrow(_cursor, "due_date");
          final int _cursorIndexOfAmountDue = CursorUtil.getColumnIndexOrThrow(_cursor, "amount_due");
          final int _cursorIndexOfAmountPaid = CursorUtil.getColumnIndexOrThrow(_cursor, "amount_paid");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updated_at");
          final int _cursorIndexOfInstallmentNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "installment_number");
          final List<BillCycleEntity> _result = new ArrayList<BillCycleEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final BillCycleEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpBillId;
            _tmpBillId = _cursor.getLong(_cursorIndexOfBillId);
            final LocalDate _tmpPeriodStart;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfPeriodStart)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfPeriodStart);
            }
            final LocalDate _tmp_1 = __converters.toLocalDate(_tmp);
            if (_tmp_1 == null) {
              throw new IllegalStateException("Expected NON-NULL 'kotlinx.datetime.LocalDate', but it was NULL.");
            } else {
              _tmpPeriodStart = _tmp_1;
            }
            final LocalDate _tmpDueDate;
            final String _tmp_2;
            if (_cursor.isNull(_cursorIndexOfDueDate)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getString(_cursorIndexOfDueDate);
            }
            final LocalDate _tmp_3 = __converters.toLocalDate(_tmp_2);
            if (_tmp_3 == null) {
              throw new IllegalStateException("Expected NON-NULL 'kotlinx.datetime.LocalDate', but it was NULL.");
            } else {
              _tmpDueDate = _tmp_3;
            }
            final double _tmpAmountDue;
            _tmpAmountDue = _cursor.getDouble(_cursorIndexOfAmountDue);
            final double _tmpAmountPaid;
            _tmpAmountPaid = _cursor.getDouble(_cursorIndexOfAmountPaid);
            final String _tmpStatus;
            _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            final LocalDateTime _tmpCreatedAt;
            final String _tmp_4;
            if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
              _tmp_4 = null;
            } else {
              _tmp_4 = _cursor.getString(_cursorIndexOfCreatedAt);
            }
            final LocalDateTime _tmp_5 = __converters.toLocalDateTime(_tmp_4);
            if (_tmp_5 == null) {
              throw new IllegalStateException("Expected NON-NULL 'kotlinx.datetime.LocalDateTime', but it was NULL.");
            } else {
              _tmpCreatedAt = _tmp_5;
            }
            final LocalDateTime _tmpUpdatedAt;
            final String _tmp_6;
            if (_cursor.isNull(_cursorIndexOfUpdatedAt)) {
              _tmp_6 = null;
            } else {
              _tmp_6 = _cursor.getString(_cursorIndexOfUpdatedAt);
            }
            final LocalDateTime _tmp_7 = __converters.toLocalDateTime(_tmp_6);
            if (_tmp_7 == null) {
              throw new IllegalStateException("Expected NON-NULL 'kotlinx.datetime.LocalDateTime', but it was NULL.");
            } else {
              _tmpUpdatedAt = _tmp_7;
            }
            final Integer _tmpInstallmentNumber;
            if (_cursor.isNull(_cursorIndexOfInstallmentNumber)) {
              _tmpInstallmentNumber = null;
            } else {
              _tmpInstallmentNumber = _cursor.getInt(_cursorIndexOfInstallmentNumber);
            }
            _item = new BillCycleEntity(_tmpId,_tmpBillId,_tmpPeriodStart,_tmpDueDate,_tmpAmountDue,_tmpAmountPaid,_tmpStatus,_tmpCreatedAt,_tmpUpdatedAt,_tmpInstallmentNumber);
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
  public Object getAllEntities(final Continuation<? super List<BillCycleEntity>> $completion) {
    final String _sql = "SELECT * FROM bill_cycle";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<BillCycleEntity>>() {
      @Override
      @NonNull
      public List<BillCycleEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfBillId = CursorUtil.getColumnIndexOrThrow(_cursor, "bill_id");
          final int _cursorIndexOfPeriodStart = CursorUtil.getColumnIndexOrThrow(_cursor, "period_start");
          final int _cursorIndexOfDueDate = CursorUtil.getColumnIndexOrThrow(_cursor, "due_date");
          final int _cursorIndexOfAmountDue = CursorUtil.getColumnIndexOrThrow(_cursor, "amount_due");
          final int _cursorIndexOfAmountPaid = CursorUtil.getColumnIndexOrThrow(_cursor, "amount_paid");
          final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updated_at");
          final int _cursorIndexOfInstallmentNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "installment_number");
          final List<BillCycleEntity> _result = new ArrayList<BillCycleEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final BillCycleEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpBillId;
            _tmpBillId = _cursor.getLong(_cursorIndexOfBillId);
            final LocalDate _tmpPeriodStart;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfPeriodStart)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfPeriodStart);
            }
            final LocalDate _tmp_1 = __converters.toLocalDate(_tmp);
            if (_tmp_1 == null) {
              throw new IllegalStateException("Expected NON-NULL 'kotlinx.datetime.LocalDate', but it was NULL.");
            } else {
              _tmpPeriodStart = _tmp_1;
            }
            final LocalDate _tmpDueDate;
            final String _tmp_2;
            if (_cursor.isNull(_cursorIndexOfDueDate)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getString(_cursorIndexOfDueDate);
            }
            final LocalDate _tmp_3 = __converters.toLocalDate(_tmp_2);
            if (_tmp_3 == null) {
              throw new IllegalStateException("Expected NON-NULL 'kotlinx.datetime.LocalDate', but it was NULL.");
            } else {
              _tmpDueDate = _tmp_3;
            }
            final double _tmpAmountDue;
            _tmpAmountDue = _cursor.getDouble(_cursorIndexOfAmountDue);
            final double _tmpAmountPaid;
            _tmpAmountPaid = _cursor.getDouble(_cursorIndexOfAmountPaid);
            final String _tmpStatus;
            _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
            final LocalDateTime _tmpCreatedAt;
            final String _tmp_4;
            if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
              _tmp_4 = null;
            } else {
              _tmp_4 = _cursor.getString(_cursorIndexOfCreatedAt);
            }
            final LocalDateTime _tmp_5 = __converters.toLocalDateTime(_tmp_4);
            if (_tmp_5 == null) {
              throw new IllegalStateException("Expected NON-NULL 'kotlinx.datetime.LocalDateTime', but it was NULL.");
            } else {
              _tmpCreatedAt = _tmp_5;
            }
            final LocalDateTime _tmpUpdatedAt;
            final String _tmp_6;
            if (_cursor.isNull(_cursorIndexOfUpdatedAt)) {
              _tmp_6 = null;
            } else {
              _tmp_6 = _cursor.getString(_cursorIndexOfUpdatedAt);
            }
            final LocalDateTime _tmp_7 = __converters.toLocalDateTime(_tmp_6);
            if (_tmp_7 == null) {
              throw new IllegalStateException("Expected NON-NULL 'kotlinx.datetime.LocalDateTime', but it was NULL.");
            } else {
              _tmpUpdatedAt = _tmp_7;
            }
            final Integer _tmpInstallmentNumber;
            if (_cursor.isNull(_cursorIndexOfInstallmentNumber)) {
              _tmpInstallmentNumber = null;
            } else {
              _tmpInstallmentNumber = _cursor.getInt(_cursorIndexOfInstallmentNumber);
            }
            _item = new BillCycleEntity(_tmpId,_tmpBillId,_tmpPeriodStart,_tmpDueDate,_tmpAmountDue,_tmpAmountPaid,_tmpStatus,_tmpCreatedAt,_tmpUpdatedAt,_tmpInstallmentNumber);
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
