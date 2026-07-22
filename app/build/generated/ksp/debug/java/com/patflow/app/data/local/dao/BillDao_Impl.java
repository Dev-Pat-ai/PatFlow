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
import com.patflow.app.data.local.entity.BillEntity;
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
public final class BillDao_Impl implements BillDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<BillEntity> __insertionAdapterOfBillEntity;

  private final Converters __converters = new Converters();

  private final EntityDeletionOrUpdateAdapter<BillEntity> __deletionAdapterOfBillEntity;

  private final EntityDeletionOrUpdateAdapter<BillEntity> __updateAdapterOfBillEntity;

  public BillDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfBillEntity = new EntityInsertionAdapter<BillEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `bill` (`id`,`category_id`,`name`,`default_amount`,`currency_code`,`merchant`,`recurrence_type`,`recurrence_interval`,`due_day`,`start_date`,`end_date`,`is_active`,`is_favorite`,`notes`,`is_deleted`,`created_at`,`updated_at`,`is_installment`,`total_installments`,`remote_id`,`last_synced_at`,`sync_status`,`is_dirty`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final BillEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getCategoryId());
        statement.bindString(3, entity.getName());
        statement.bindDouble(4, entity.getDefaultAmount());
        statement.bindString(5, entity.getCurrencyCode());
        if (entity.getMerchant() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getMerchant());
        }
        statement.bindString(7, entity.getRecurrenceType());
        statement.bindLong(8, entity.getRecurrenceInterval());
        if (entity.getDueDay() == null) {
          statement.bindNull(9);
        } else {
          statement.bindLong(9, entity.getDueDay());
        }
        final String _tmp = __converters.fromLocalDate(entity.getStartDate());
        if (_tmp == null) {
          statement.bindNull(10);
        } else {
          statement.bindString(10, _tmp);
        }
        final String _tmp_1 = __converters.fromLocalDate(entity.getEndDate());
        if (_tmp_1 == null) {
          statement.bindNull(11);
        } else {
          statement.bindString(11, _tmp_1);
        }
        final int _tmp_2 = entity.isActive() ? 1 : 0;
        statement.bindLong(12, _tmp_2);
        final int _tmp_3 = entity.isFavorite() ? 1 : 0;
        statement.bindLong(13, _tmp_3);
        if (entity.getNotes() == null) {
          statement.bindNull(14);
        } else {
          statement.bindString(14, entity.getNotes());
        }
        final int _tmp_4 = entity.isDeleted() ? 1 : 0;
        statement.bindLong(15, _tmp_4);
        final String _tmp_5 = __converters.fromLocalDateTime(entity.getCreatedAt());
        if (_tmp_5 == null) {
          statement.bindNull(16);
        } else {
          statement.bindString(16, _tmp_5);
        }
        final String _tmp_6 = __converters.fromLocalDateTime(entity.getUpdatedAt());
        if (_tmp_6 == null) {
          statement.bindNull(17);
        } else {
          statement.bindString(17, _tmp_6);
        }
        final int _tmp_7 = entity.isInstallment() ? 1 : 0;
        statement.bindLong(18, _tmp_7);
        if (entity.getTotalInstallments() == null) {
          statement.bindNull(19);
        } else {
          statement.bindLong(19, entity.getTotalInstallments());
        }
        if (entity.getRemoteId() == null) {
          statement.bindNull(20);
        } else {
          statement.bindString(20, entity.getRemoteId());
        }
        final String _tmp_8 = __converters.fromLocalDateTime(entity.getLastSyncedAt());
        if (_tmp_8 == null) {
          statement.bindNull(21);
        } else {
          statement.bindString(21, _tmp_8);
        }
        statement.bindString(22, entity.getSyncStatus());
        final int _tmp_9 = entity.isDirty() ? 1 : 0;
        statement.bindLong(23, _tmp_9);
      }
    };
    this.__deletionAdapterOfBillEntity = new EntityDeletionOrUpdateAdapter<BillEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `bill` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final BillEntity entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfBillEntity = new EntityDeletionOrUpdateAdapter<BillEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `bill` SET `id` = ?,`category_id` = ?,`name` = ?,`default_amount` = ?,`currency_code` = ?,`merchant` = ?,`recurrence_type` = ?,`recurrence_interval` = ?,`due_day` = ?,`start_date` = ?,`end_date` = ?,`is_active` = ?,`is_favorite` = ?,`notes` = ?,`is_deleted` = ?,`created_at` = ?,`updated_at` = ?,`is_installment` = ?,`total_installments` = ?,`remote_id` = ?,`last_synced_at` = ?,`sync_status` = ?,`is_dirty` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final BillEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getCategoryId());
        statement.bindString(3, entity.getName());
        statement.bindDouble(4, entity.getDefaultAmount());
        statement.bindString(5, entity.getCurrencyCode());
        if (entity.getMerchant() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getMerchant());
        }
        statement.bindString(7, entity.getRecurrenceType());
        statement.bindLong(8, entity.getRecurrenceInterval());
        if (entity.getDueDay() == null) {
          statement.bindNull(9);
        } else {
          statement.bindLong(9, entity.getDueDay());
        }
        final String _tmp = __converters.fromLocalDate(entity.getStartDate());
        if (_tmp == null) {
          statement.bindNull(10);
        } else {
          statement.bindString(10, _tmp);
        }
        final String _tmp_1 = __converters.fromLocalDate(entity.getEndDate());
        if (_tmp_1 == null) {
          statement.bindNull(11);
        } else {
          statement.bindString(11, _tmp_1);
        }
        final int _tmp_2 = entity.isActive() ? 1 : 0;
        statement.bindLong(12, _tmp_2);
        final int _tmp_3 = entity.isFavorite() ? 1 : 0;
        statement.bindLong(13, _tmp_3);
        if (entity.getNotes() == null) {
          statement.bindNull(14);
        } else {
          statement.bindString(14, entity.getNotes());
        }
        final int _tmp_4 = entity.isDeleted() ? 1 : 0;
        statement.bindLong(15, _tmp_4);
        final String _tmp_5 = __converters.fromLocalDateTime(entity.getCreatedAt());
        if (_tmp_5 == null) {
          statement.bindNull(16);
        } else {
          statement.bindString(16, _tmp_5);
        }
        final String _tmp_6 = __converters.fromLocalDateTime(entity.getUpdatedAt());
        if (_tmp_6 == null) {
          statement.bindNull(17);
        } else {
          statement.bindString(17, _tmp_6);
        }
        final int _tmp_7 = entity.isInstallment() ? 1 : 0;
        statement.bindLong(18, _tmp_7);
        if (entity.getTotalInstallments() == null) {
          statement.bindNull(19);
        } else {
          statement.bindLong(19, entity.getTotalInstallments());
        }
        if (entity.getRemoteId() == null) {
          statement.bindNull(20);
        } else {
          statement.bindString(20, entity.getRemoteId());
        }
        final String _tmp_8 = __converters.fromLocalDateTime(entity.getLastSyncedAt());
        if (_tmp_8 == null) {
          statement.bindNull(21);
        } else {
          statement.bindString(21, _tmp_8);
        }
        statement.bindString(22, entity.getSyncStatus());
        final int _tmp_9 = entity.isDirty() ? 1 : 0;
        statement.bindLong(23, _tmp_9);
        statement.bindLong(24, entity.getId());
      }
    };
  }

  @Override
  public Object insert(final BillEntity bill, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfBillEntity.insertAndReturnId(bill);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object delete(final BillEntity bill, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfBillEntity.handle(bill);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object update(final BillEntity bill, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfBillEntity.handle(bill);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object getById(final long id, final Continuation<? super BillEntity> $completion) {
    final String _sql = "SELECT * FROM bill WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<BillEntity>() {
      @Override
      @Nullable
      public BillEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfCategoryId = CursorUtil.getColumnIndexOrThrow(_cursor, "category_id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfDefaultAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "default_amount");
          final int _cursorIndexOfCurrencyCode = CursorUtil.getColumnIndexOrThrow(_cursor, "currency_code");
          final int _cursorIndexOfMerchant = CursorUtil.getColumnIndexOrThrow(_cursor, "merchant");
          final int _cursorIndexOfRecurrenceType = CursorUtil.getColumnIndexOrThrow(_cursor, "recurrence_type");
          final int _cursorIndexOfRecurrenceInterval = CursorUtil.getColumnIndexOrThrow(_cursor, "recurrence_interval");
          final int _cursorIndexOfDueDay = CursorUtil.getColumnIndexOrThrow(_cursor, "due_day");
          final int _cursorIndexOfStartDate = CursorUtil.getColumnIndexOrThrow(_cursor, "start_date");
          final int _cursorIndexOfEndDate = CursorUtil.getColumnIndexOrThrow(_cursor, "end_date");
          final int _cursorIndexOfIsActive = CursorUtil.getColumnIndexOrThrow(_cursor, "is_active");
          final int _cursorIndexOfIsFavorite = CursorUtil.getColumnIndexOrThrow(_cursor, "is_favorite");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfIsDeleted = CursorUtil.getColumnIndexOrThrow(_cursor, "is_deleted");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updated_at");
          final int _cursorIndexOfIsInstallment = CursorUtil.getColumnIndexOrThrow(_cursor, "is_installment");
          final int _cursorIndexOfTotalInstallments = CursorUtil.getColumnIndexOrThrow(_cursor, "total_installments");
          final int _cursorIndexOfRemoteId = CursorUtil.getColumnIndexOrThrow(_cursor, "remote_id");
          final int _cursorIndexOfLastSyncedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "last_synced_at");
          final int _cursorIndexOfSyncStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "sync_status");
          final int _cursorIndexOfIsDirty = CursorUtil.getColumnIndexOrThrow(_cursor, "is_dirty");
          final BillEntity _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpCategoryId;
            _tmpCategoryId = _cursor.getLong(_cursorIndexOfCategoryId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final double _tmpDefaultAmount;
            _tmpDefaultAmount = _cursor.getDouble(_cursorIndexOfDefaultAmount);
            final String _tmpCurrencyCode;
            _tmpCurrencyCode = _cursor.getString(_cursorIndexOfCurrencyCode);
            final String _tmpMerchant;
            if (_cursor.isNull(_cursorIndexOfMerchant)) {
              _tmpMerchant = null;
            } else {
              _tmpMerchant = _cursor.getString(_cursorIndexOfMerchant);
            }
            final String _tmpRecurrenceType;
            _tmpRecurrenceType = _cursor.getString(_cursorIndexOfRecurrenceType);
            final int _tmpRecurrenceInterval;
            _tmpRecurrenceInterval = _cursor.getInt(_cursorIndexOfRecurrenceInterval);
            final Integer _tmpDueDay;
            if (_cursor.isNull(_cursorIndexOfDueDay)) {
              _tmpDueDay = null;
            } else {
              _tmpDueDay = _cursor.getInt(_cursorIndexOfDueDay);
            }
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
            _tmpEndDate = __converters.toLocalDate(_tmp_2);
            final boolean _tmpIsActive;
            final int _tmp_3;
            _tmp_3 = _cursor.getInt(_cursorIndexOfIsActive);
            _tmpIsActive = _tmp_3 != 0;
            final boolean _tmpIsFavorite;
            final int _tmp_4;
            _tmp_4 = _cursor.getInt(_cursorIndexOfIsFavorite);
            _tmpIsFavorite = _tmp_4 != 0;
            final String _tmpNotes;
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _tmpNotes = null;
            } else {
              _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            }
            final boolean _tmpIsDeleted;
            final int _tmp_5;
            _tmp_5 = _cursor.getInt(_cursorIndexOfIsDeleted);
            _tmpIsDeleted = _tmp_5 != 0;
            final LocalDateTime _tmpCreatedAt;
            final String _tmp_6;
            if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
              _tmp_6 = null;
            } else {
              _tmp_6 = _cursor.getString(_cursorIndexOfCreatedAt);
            }
            final LocalDateTime _tmp_7 = __converters.toLocalDateTime(_tmp_6);
            if (_tmp_7 == null) {
              throw new IllegalStateException("Expected NON-NULL 'kotlinx.datetime.LocalDateTime', but it was NULL.");
            } else {
              _tmpCreatedAt = _tmp_7;
            }
            final LocalDateTime _tmpUpdatedAt;
            final String _tmp_8;
            if (_cursor.isNull(_cursorIndexOfUpdatedAt)) {
              _tmp_8 = null;
            } else {
              _tmp_8 = _cursor.getString(_cursorIndexOfUpdatedAt);
            }
            final LocalDateTime _tmp_9 = __converters.toLocalDateTime(_tmp_8);
            if (_tmp_9 == null) {
              throw new IllegalStateException("Expected NON-NULL 'kotlinx.datetime.LocalDateTime', but it was NULL.");
            } else {
              _tmpUpdatedAt = _tmp_9;
            }
            final boolean _tmpIsInstallment;
            final int _tmp_10;
            _tmp_10 = _cursor.getInt(_cursorIndexOfIsInstallment);
            _tmpIsInstallment = _tmp_10 != 0;
            final Integer _tmpTotalInstallments;
            if (_cursor.isNull(_cursorIndexOfTotalInstallments)) {
              _tmpTotalInstallments = null;
            } else {
              _tmpTotalInstallments = _cursor.getInt(_cursorIndexOfTotalInstallments);
            }
            final String _tmpRemoteId;
            if (_cursor.isNull(_cursorIndexOfRemoteId)) {
              _tmpRemoteId = null;
            } else {
              _tmpRemoteId = _cursor.getString(_cursorIndexOfRemoteId);
            }
            final LocalDateTime _tmpLastSyncedAt;
            final String _tmp_11;
            if (_cursor.isNull(_cursorIndexOfLastSyncedAt)) {
              _tmp_11 = null;
            } else {
              _tmp_11 = _cursor.getString(_cursorIndexOfLastSyncedAt);
            }
            _tmpLastSyncedAt = __converters.toLocalDateTime(_tmp_11);
            final String _tmpSyncStatus;
            _tmpSyncStatus = _cursor.getString(_cursorIndexOfSyncStatus);
            final boolean _tmpIsDirty;
            final int _tmp_12;
            _tmp_12 = _cursor.getInt(_cursorIndexOfIsDirty);
            _tmpIsDirty = _tmp_12 != 0;
            _result = new BillEntity(_tmpId,_tmpCategoryId,_tmpName,_tmpDefaultAmount,_tmpCurrencyCode,_tmpMerchant,_tmpRecurrenceType,_tmpRecurrenceInterval,_tmpDueDay,_tmpStartDate,_tmpEndDate,_tmpIsActive,_tmpIsFavorite,_tmpNotes,_tmpIsDeleted,_tmpCreatedAt,_tmpUpdatedAt,_tmpIsInstallment,_tmpTotalInstallments,_tmpRemoteId,_tmpLastSyncedAt,_tmpSyncStatus,_tmpIsDirty);
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
  public Flow<List<BillEntity>> getAll() {
    final String _sql = "SELECT * FROM bill WHERE is_deleted = 0 ORDER BY name ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"bill"}, new Callable<List<BillEntity>>() {
      @Override
      @NonNull
      public List<BillEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfCategoryId = CursorUtil.getColumnIndexOrThrow(_cursor, "category_id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfDefaultAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "default_amount");
          final int _cursorIndexOfCurrencyCode = CursorUtil.getColumnIndexOrThrow(_cursor, "currency_code");
          final int _cursorIndexOfMerchant = CursorUtil.getColumnIndexOrThrow(_cursor, "merchant");
          final int _cursorIndexOfRecurrenceType = CursorUtil.getColumnIndexOrThrow(_cursor, "recurrence_type");
          final int _cursorIndexOfRecurrenceInterval = CursorUtil.getColumnIndexOrThrow(_cursor, "recurrence_interval");
          final int _cursorIndexOfDueDay = CursorUtil.getColumnIndexOrThrow(_cursor, "due_day");
          final int _cursorIndexOfStartDate = CursorUtil.getColumnIndexOrThrow(_cursor, "start_date");
          final int _cursorIndexOfEndDate = CursorUtil.getColumnIndexOrThrow(_cursor, "end_date");
          final int _cursorIndexOfIsActive = CursorUtil.getColumnIndexOrThrow(_cursor, "is_active");
          final int _cursorIndexOfIsFavorite = CursorUtil.getColumnIndexOrThrow(_cursor, "is_favorite");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfIsDeleted = CursorUtil.getColumnIndexOrThrow(_cursor, "is_deleted");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updated_at");
          final int _cursorIndexOfIsInstallment = CursorUtil.getColumnIndexOrThrow(_cursor, "is_installment");
          final int _cursorIndexOfTotalInstallments = CursorUtil.getColumnIndexOrThrow(_cursor, "total_installments");
          final int _cursorIndexOfRemoteId = CursorUtil.getColumnIndexOrThrow(_cursor, "remote_id");
          final int _cursorIndexOfLastSyncedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "last_synced_at");
          final int _cursorIndexOfSyncStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "sync_status");
          final int _cursorIndexOfIsDirty = CursorUtil.getColumnIndexOrThrow(_cursor, "is_dirty");
          final List<BillEntity> _result = new ArrayList<BillEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final BillEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpCategoryId;
            _tmpCategoryId = _cursor.getLong(_cursorIndexOfCategoryId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final double _tmpDefaultAmount;
            _tmpDefaultAmount = _cursor.getDouble(_cursorIndexOfDefaultAmount);
            final String _tmpCurrencyCode;
            _tmpCurrencyCode = _cursor.getString(_cursorIndexOfCurrencyCode);
            final String _tmpMerchant;
            if (_cursor.isNull(_cursorIndexOfMerchant)) {
              _tmpMerchant = null;
            } else {
              _tmpMerchant = _cursor.getString(_cursorIndexOfMerchant);
            }
            final String _tmpRecurrenceType;
            _tmpRecurrenceType = _cursor.getString(_cursorIndexOfRecurrenceType);
            final int _tmpRecurrenceInterval;
            _tmpRecurrenceInterval = _cursor.getInt(_cursorIndexOfRecurrenceInterval);
            final Integer _tmpDueDay;
            if (_cursor.isNull(_cursorIndexOfDueDay)) {
              _tmpDueDay = null;
            } else {
              _tmpDueDay = _cursor.getInt(_cursorIndexOfDueDay);
            }
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
            _tmpEndDate = __converters.toLocalDate(_tmp_2);
            final boolean _tmpIsActive;
            final int _tmp_3;
            _tmp_3 = _cursor.getInt(_cursorIndexOfIsActive);
            _tmpIsActive = _tmp_3 != 0;
            final boolean _tmpIsFavorite;
            final int _tmp_4;
            _tmp_4 = _cursor.getInt(_cursorIndexOfIsFavorite);
            _tmpIsFavorite = _tmp_4 != 0;
            final String _tmpNotes;
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _tmpNotes = null;
            } else {
              _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            }
            final boolean _tmpIsDeleted;
            final int _tmp_5;
            _tmp_5 = _cursor.getInt(_cursorIndexOfIsDeleted);
            _tmpIsDeleted = _tmp_5 != 0;
            final LocalDateTime _tmpCreatedAt;
            final String _tmp_6;
            if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
              _tmp_6 = null;
            } else {
              _tmp_6 = _cursor.getString(_cursorIndexOfCreatedAt);
            }
            final LocalDateTime _tmp_7 = __converters.toLocalDateTime(_tmp_6);
            if (_tmp_7 == null) {
              throw new IllegalStateException("Expected NON-NULL 'kotlinx.datetime.LocalDateTime', but it was NULL.");
            } else {
              _tmpCreatedAt = _tmp_7;
            }
            final LocalDateTime _tmpUpdatedAt;
            final String _tmp_8;
            if (_cursor.isNull(_cursorIndexOfUpdatedAt)) {
              _tmp_8 = null;
            } else {
              _tmp_8 = _cursor.getString(_cursorIndexOfUpdatedAt);
            }
            final LocalDateTime _tmp_9 = __converters.toLocalDateTime(_tmp_8);
            if (_tmp_9 == null) {
              throw new IllegalStateException("Expected NON-NULL 'kotlinx.datetime.LocalDateTime', but it was NULL.");
            } else {
              _tmpUpdatedAt = _tmp_9;
            }
            final boolean _tmpIsInstallment;
            final int _tmp_10;
            _tmp_10 = _cursor.getInt(_cursorIndexOfIsInstallment);
            _tmpIsInstallment = _tmp_10 != 0;
            final Integer _tmpTotalInstallments;
            if (_cursor.isNull(_cursorIndexOfTotalInstallments)) {
              _tmpTotalInstallments = null;
            } else {
              _tmpTotalInstallments = _cursor.getInt(_cursorIndexOfTotalInstallments);
            }
            final String _tmpRemoteId;
            if (_cursor.isNull(_cursorIndexOfRemoteId)) {
              _tmpRemoteId = null;
            } else {
              _tmpRemoteId = _cursor.getString(_cursorIndexOfRemoteId);
            }
            final LocalDateTime _tmpLastSyncedAt;
            final String _tmp_11;
            if (_cursor.isNull(_cursorIndexOfLastSyncedAt)) {
              _tmp_11 = null;
            } else {
              _tmp_11 = _cursor.getString(_cursorIndexOfLastSyncedAt);
            }
            _tmpLastSyncedAt = __converters.toLocalDateTime(_tmp_11);
            final String _tmpSyncStatus;
            _tmpSyncStatus = _cursor.getString(_cursorIndexOfSyncStatus);
            final boolean _tmpIsDirty;
            final int _tmp_12;
            _tmp_12 = _cursor.getInt(_cursorIndexOfIsDirty);
            _tmpIsDirty = _tmp_12 != 0;
            _item = new BillEntity(_tmpId,_tmpCategoryId,_tmpName,_tmpDefaultAmount,_tmpCurrencyCode,_tmpMerchant,_tmpRecurrenceType,_tmpRecurrenceInterval,_tmpDueDay,_tmpStartDate,_tmpEndDate,_tmpIsActive,_tmpIsFavorite,_tmpNotes,_tmpIsDeleted,_tmpCreatedAt,_tmpUpdatedAt,_tmpIsInstallment,_tmpTotalInstallments,_tmpRemoteId,_tmpLastSyncedAt,_tmpSyncStatus,_tmpIsDirty);
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
  public Flow<List<BillEntity>> getByCategory(final long categoryId) {
    final String _sql = "SELECT * FROM bill WHERE category_id = ? AND is_deleted = 0";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, categoryId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"bill"}, new Callable<List<BillEntity>>() {
      @Override
      @NonNull
      public List<BillEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfCategoryId = CursorUtil.getColumnIndexOrThrow(_cursor, "category_id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfDefaultAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "default_amount");
          final int _cursorIndexOfCurrencyCode = CursorUtil.getColumnIndexOrThrow(_cursor, "currency_code");
          final int _cursorIndexOfMerchant = CursorUtil.getColumnIndexOrThrow(_cursor, "merchant");
          final int _cursorIndexOfRecurrenceType = CursorUtil.getColumnIndexOrThrow(_cursor, "recurrence_type");
          final int _cursorIndexOfRecurrenceInterval = CursorUtil.getColumnIndexOrThrow(_cursor, "recurrence_interval");
          final int _cursorIndexOfDueDay = CursorUtil.getColumnIndexOrThrow(_cursor, "due_day");
          final int _cursorIndexOfStartDate = CursorUtil.getColumnIndexOrThrow(_cursor, "start_date");
          final int _cursorIndexOfEndDate = CursorUtil.getColumnIndexOrThrow(_cursor, "end_date");
          final int _cursorIndexOfIsActive = CursorUtil.getColumnIndexOrThrow(_cursor, "is_active");
          final int _cursorIndexOfIsFavorite = CursorUtil.getColumnIndexOrThrow(_cursor, "is_favorite");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfIsDeleted = CursorUtil.getColumnIndexOrThrow(_cursor, "is_deleted");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updated_at");
          final int _cursorIndexOfIsInstallment = CursorUtil.getColumnIndexOrThrow(_cursor, "is_installment");
          final int _cursorIndexOfTotalInstallments = CursorUtil.getColumnIndexOrThrow(_cursor, "total_installments");
          final int _cursorIndexOfRemoteId = CursorUtil.getColumnIndexOrThrow(_cursor, "remote_id");
          final int _cursorIndexOfLastSyncedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "last_synced_at");
          final int _cursorIndexOfSyncStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "sync_status");
          final int _cursorIndexOfIsDirty = CursorUtil.getColumnIndexOrThrow(_cursor, "is_dirty");
          final List<BillEntity> _result = new ArrayList<BillEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final BillEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpCategoryId;
            _tmpCategoryId = _cursor.getLong(_cursorIndexOfCategoryId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final double _tmpDefaultAmount;
            _tmpDefaultAmount = _cursor.getDouble(_cursorIndexOfDefaultAmount);
            final String _tmpCurrencyCode;
            _tmpCurrencyCode = _cursor.getString(_cursorIndexOfCurrencyCode);
            final String _tmpMerchant;
            if (_cursor.isNull(_cursorIndexOfMerchant)) {
              _tmpMerchant = null;
            } else {
              _tmpMerchant = _cursor.getString(_cursorIndexOfMerchant);
            }
            final String _tmpRecurrenceType;
            _tmpRecurrenceType = _cursor.getString(_cursorIndexOfRecurrenceType);
            final int _tmpRecurrenceInterval;
            _tmpRecurrenceInterval = _cursor.getInt(_cursorIndexOfRecurrenceInterval);
            final Integer _tmpDueDay;
            if (_cursor.isNull(_cursorIndexOfDueDay)) {
              _tmpDueDay = null;
            } else {
              _tmpDueDay = _cursor.getInt(_cursorIndexOfDueDay);
            }
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
            _tmpEndDate = __converters.toLocalDate(_tmp_2);
            final boolean _tmpIsActive;
            final int _tmp_3;
            _tmp_3 = _cursor.getInt(_cursorIndexOfIsActive);
            _tmpIsActive = _tmp_3 != 0;
            final boolean _tmpIsFavorite;
            final int _tmp_4;
            _tmp_4 = _cursor.getInt(_cursorIndexOfIsFavorite);
            _tmpIsFavorite = _tmp_4 != 0;
            final String _tmpNotes;
            if (_cursor.isNull(_cursorIndexOfNotes)) {
              _tmpNotes = null;
            } else {
              _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            }
            final boolean _tmpIsDeleted;
            final int _tmp_5;
            _tmp_5 = _cursor.getInt(_cursorIndexOfIsDeleted);
            _tmpIsDeleted = _tmp_5 != 0;
            final LocalDateTime _tmpCreatedAt;
            final String _tmp_6;
            if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
              _tmp_6 = null;
            } else {
              _tmp_6 = _cursor.getString(_cursorIndexOfCreatedAt);
            }
            final LocalDateTime _tmp_7 = __converters.toLocalDateTime(_tmp_6);
            if (_tmp_7 == null) {
              throw new IllegalStateException("Expected NON-NULL 'kotlinx.datetime.LocalDateTime', but it was NULL.");
            } else {
              _tmpCreatedAt = _tmp_7;
            }
            final LocalDateTime _tmpUpdatedAt;
            final String _tmp_8;
            if (_cursor.isNull(_cursorIndexOfUpdatedAt)) {
              _tmp_8 = null;
            } else {
              _tmp_8 = _cursor.getString(_cursorIndexOfUpdatedAt);
            }
            final LocalDateTime _tmp_9 = __converters.toLocalDateTime(_tmp_8);
            if (_tmp_9 == null) {
              throw new IllegalStateException("Expected NON-NULL 'kotlinx.datetime.LocalDateTime', but it was NULL.");
            } else {
              _tmpUpdatedAt = _tmp_9;
            }
            final boolean _tmpIsInstallment;
            final int _tmp_10;
            _tmp_10 = _cursor.getInt(_cursorIndexOfIsInstallment);
            _tmpIsInstallment = _tmp_10 != 0;
            final Integer _tmpTotalInstallments;
            if (_cursor.isNull(_cursorIndexOfTotalInstallments)) {
              _tmpTotalInstallments = null;
            } else {
              _tmpTotalInstallments = _cursor.getInt(_cursorIndexOfTotalInstallments);
            }
            final String _tmpRemoteId;
            if (_cursor.isNull(_cursorIndexOfRemoteId)) {
              _tmpRemoteId = null;
            } else {
              _tmpRemoteId = _cursor.getString(_cursorIndexOfRemoteId);
            }
            final LocalDateTime _tmpLastSyncedAt;
            final String _tmp_11;
            if (_cursor.isNull(_cursorIndexOfLastSyncedAt)) {
              _tmp_11 = null;
            } else {
              _tmp_11 = _cursor.getString(_cursorIndexOfLastSyncedAt);
            }
            _tmpLastSyncedAt = __converters.toLocalDateTime(_tmp_11);
            final String _tmpSyncStatus;
            _tmpSyncStatus = _cursor.getString(_cursorIndexOfSyncStatus);
            final boolean _tmpIsDirty;
            final int _tmp_12;
            _tmp_12 = _cursor.getInt(_cursorIndexOfIsDirty);
            _tmpIsDirty = _tmp_12 != 0;
            _item = new BillEntity(_tmpId,_tmpCategoryId,_tmpName,_tmpDefaultAmount,_tmpCurrencyCode,_tmpMerchant,_tmpRecurrenceType,_tmpRecurrenceInterval,_tmpDueDay,_tmpStartDate,_tmpEndDate,_tmpIsActive,_tmpIsFavorite,_tmpNotes,_tmpIsDeleted,_tmpCreatedAt,_tmpUpdatedAt,_tmpIsInstallment,_tmpTotalInstallments,_tmpRemoteId,_tmpLastSyncedAt,_tmpSyncStatus,_tmpIsDirty);
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
