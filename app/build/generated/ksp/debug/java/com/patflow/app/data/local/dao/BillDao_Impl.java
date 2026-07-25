package com.patflow.app.data.local.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.collection.LongSparseArray;
import androidx.room.AmbiguousColumnResolver;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.room.util.RelationUtil;
import androidx.room.util.StringUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.patflow.app.data.local.database.Converters;
import com.patflow.app.data.local.entity.BillCategoryEntity;
import com.patflow.app.data.local.entity.BillCycleEntity;
import com.patflow.app.data.local.entity.BillEntity;
import com.patflow.app.data.local.entity.BillWithDetailsEntity;
import java.lang.Class;
import java.lang.Exception;
import java.lang.IllegalStateException;
import java.lang.Integer;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.StringBuilder;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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

  private final EntityInsertionAdapter<BillEntity> __insertionAdapterOfBillEntity_1;

  private final EntityDeletionOrUpdateAdapter<BillEntity> __deletionAdapterOfBillEntity;

  private final EntityDeletionOrUpdateAdapter<BillEntity> __updateAdapterOfBillEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAll;

  public BillDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfBillEntity = new EntityInsertionAdapter<BillEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `bill` (`id`,`category_id`,`name`,`default_amount`,`currency_code`,`account_number`,`bill_reference`,`merchant`,`recurrence_type`,`recurrence_interval`,`due_day`,`start_date`,`end_date`,`is_active`,`is_favorite`,`notes`,`is_deleted`,`created_at`,`updated_at`,`is_installment`,`total_installments`,`remote_id`,`last_synced_at`,`sync_status`,`is_dirty`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final BillEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getCategoryId());
        statement.bindString(3, entity.getName());
        statement.bindDouble(4, entity.getDefaultAmount());
        statement.bindString(5, entity.getCurrencyCode());
        if (entity.getAccountNumber() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getAccountNumber());
        }
        if (entity.getBillReference() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getBillReference());
        }
        if (entity.getMerchant() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getMerchant());
        }
        statement.bindString(9, entity.getRecurrenceType());
        statement.bindLong(10, entity.getRecurrenceInterval());
        if (entity.getDueDay() == null) {
          statement.bindNull(11);
        } else {
          statement.bindLong(11, entity.getDueDay());
        }
        final String _tmp = __converters.fromLocalDate(entity.getStartDate());
        if (_tmp == null) {
          statement.bindNull(12);
        } else {
          statement.bindString(12, _tmp);
        }
        final String _tmp_1 = __converters.fromLocalDate(entity.getEndDate());
        if (_tmp_1 == null) {
          statement.bindNull(13);
        } else {
          statement.bindString(13, _tmp_1);
        }
        final int _tmp_2 = entity.isActive() ? 1 : 0;
        statement.bindLong(14, _tmp_2);
        final int _tmp_3 = entity.isFavorite() ? 1 : 0;
        statement.bindLong(15, _tmp_3);
        if (entity.getNotes() == null) {
          statement.bindNull(16);
        } else {
          statement.bindString(16, entity.getNotes());
        }
        final int _tmp_4 = entity.isDeleted() ? 1 : 0;
        statement.bindLong(17, _tmp_4);
        final String _tmp_5 = __converters.fromLocalDateTime(entity.getCreatedAt());
        if (_tmp_5 == null) {
          statement.bindNull(18);
        } else {
          statement.bindString(18, _tmp_5);
        }
        final String _tmp_6 = __converters.fromLocalDateTime(entity.getUpdatedAt());
        if (_tmp_6 == null) {
          statement.bindNull(19);
        } else {
          statement.bindString(19, _tmp_6);
        }
        final int _tmp_7 = entity.isInstallment() ? 1 : 0;
        statement.bindLong(20, _tmp_7);
        if (entity.getTotalInstallments() == null) {
          statement.bindNull(21);
        } else {
          statement.bindLong(21, entity.getTotalInstallments());
        }
        if (entity.getRemoteId() == null) {
          statement.bindNull(22);
        } else {
          statement.bindString(22, entity.getRemoteId());
        }
        final String _tmp_8 = __converters.fromLocalDateTime(entity.getLastSyncedAt());
        if (_tmp_8 == null) {
          statement.bindNull(23);
        } else {
          statement.bindString(23, _tmp_8);
        }
        statement.bindString(24, entity.getSyncStatus());
        final int _tmp_9 = entity.isDirty() ? 1 : 0;
        statement.bindLong(25, _tmp_9);
      }
    };
    this.__insertionAdapterOfBillEntity_1 = new EntityInsertionAdapter<BillEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `bill` (`id`,`category_id`,`name`,`default_amount`,`currency_code`,`account_number`,`bill_reference`,`merchant`,`recurrence_type`,`recurrence_interval`,`due_day`,`start_date`,`end_date`,`is_active`,`is_favorite`,`notes`,`is_deleted`,`created_at`,`updated_at`,`is_installment`,`total_installments`,`remote_id`,`last_synced_at`,`sync_status`,`is_dirty`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final BillEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getCategoryId());
        statement.bindString(3, entity.getName());
        statement.bindDouble(4, entity.getDefaultAmount());
        statement.bindString(5, entity.getCurrencyCode());
        if (entity.getAccountNumber() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getAccountNumber());
        }
        if (entity.getBillReference() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getBillReference());
        }
        if (entity.getMerchant() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getMerchant());
        }
        statement.bindString(9, entity.getRecurrenceType());
        statement.bindLong(10, entity.getRecurrenceInterval());
        if (entity.getDueDay() == null) {
          statement.bindNull(11);
        } else {
          statement.bindLong(11, entity.getDueDay());
        }
        final String _tmp = __converters.fromLocalDate(entity.getStartDate());
        if (_tmp == null) {
          statement.bindNull(12);
        } else {
          statement.bindString(12, _tmp);
        }
        final String _tmp_1 = __converters.fromLocalDate(entity.getEndDate());
        if (_tmp_1 == null) {
          statement.bindNull(13);
        } else {
          statement.bindString(13, _tmp_1);
        }
        final int _tmp_2 = entity.isActive() ? 1 : 0;
        statement.bindLong(14, _tmp_2);
        final int _tmp_3 = entity.isFavorite() ? 1 : 0;
        statement.bindLong(15, _tmp_3);
        if (entity.getNotes() == null) {
          statement.bindNull(16);
        } else {
          statement.bindString(16, entity.getNotes());
        }
        final int _tmp_4 = entity.isDeleted() ? 1 : 0;
        statement.bindLong(17, _tmp_4);
        final String _tmp_5 = __converters.fromLocalDateTime(entity.getCreatedAt());
        if (_tmp_5 == null) {
          statement.bindNull(18);
        } else {
          statement.bindString(18, _tmp_5);
        }
        final String _tmp_6 = __converters.fromLocalDateTime(entity.getUpdatedAt());
        if (_tmp_6 == null) {
          statement.bindNull(19);
        } else {
          statement.bindString(19, _tmp_6);
        }
        final int _tmp_7 = entity.isInstallment() ? 1 : 0;
        statement.bindLong(20, _tmp_7);
        if (entity.getTotalInstallments() == null) {
          statement.bindNull(21);
        } else {
          statement.bindLong(21, entity.getTotalInstallments());
        }
        if (entity.getRemoteId() == null) {
          statement.bindNull(22);
        } else {
          statement.bindString(22, entity.getRemoteId());
        }
        final String _tmp_8 = __converters.fromLocalDateTime(entity.getLastSyncedAt());
        if (_tmp_8 == null) {
          statement.bindNull(23);
        } else {
          statement.bindString(23, _tmp_8);
        }
        statement.bindString(24, entity.getSyncStatus());
        final int _tmp_9 = entity.isDirty() ? 1 : 0;
        statement.bindLong(25, _tmp_9);
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
        return "UPDATE OR ABORT `bill` SET `id` = ?,`category_id` = ?,`name` = ?,`default_amount` = ?,`currency_code` = ?,`account_number` = ?,`bill_reference` = ?,`merchant` = ?,`recurrence_type` = ?,`recurrence_interval` = ?,`due_day` = ?,`start_date` = ?,`end_date` = ?,`is_active` = ?,`is_favorite` = ?,`notes` = ?,`is_deleted` = ?,`created_at` = ?,`updated_at` = ?,`is_installment` = ?,`total_installments` = ?,`remote_id` = ?,`last_synced_at` = ?,`sync_status` = ?,`is_dirty` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final BillEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getCategoryId());
        statement.bindString(3, entity.getName());
        statement.bindDouble(4, entity.getDefaultAmount());
        statement.bindString(5, entity.getCurrencyCode());
        if (entity.getAccountNumber() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getAccountNumber());
        }
        if (entity.getBillReference() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getBillReference());
        }
        if (entity.getMerchant() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getMerchant());
        }
        statement.bindString(9, entity.getRecurrenceType());
        statement.bindLong(10, entity.getRecurrenceInterval());
        if (entity.getDueDay() == null) {
          statement.bindNull(11);
        } else {
          statement.bindLong(11, entity.getDueDay());
        }
        final String _tmp = __converters.fromLocalDate(entity.getStartDate());
        if (_tmp == null) {
          statement.bindNull(12);
        } else {
          statement.bindString(12, _tmp);
        }
        final String _tmp_1 = __converters.fromLocalDate(entity.getEndDate());
        if (_tmp_1 == null) {
          statement.bindNull(13);
        } else {
          statement.bindString(13, _tmp_1);
        }
        final int _tmp_2 = entity.isActive() ? 1 : 0;
        statement.bindLong(14, _tmp_2);
        final int _tmp_3 = entity.isFavorite() ? 1 : 0;
        statement.bindLong(15, _tmp_3);
        if (entity.getNotes() == null) {
          statement.bindNull(16);
        } else {
          statement.bindString(16, entity.getNotes());
        }
        final int _tmp_4 = entity.isDeleted() ? 1 : 0;
        statement.bindLong(17, _tmp_4);
        final String _tmp_5 = __converters.fromLocalDateTime(entity.getCreatedAt());
        if (_tmp_5 == null) {
          statement.bindNull(18);
        } else {
          statement.bindString(18, _tmp_5);
        }
        final String _tmp_6 = __converters.fromLocalDateTime(entity.getUpdatedAt());
        if (_tmp_6 == null) {
          statement.bindNull(19);
        } else {
          statement.bindString(19, _tmp_6);
        }
        final int _tmp_7 = entity.isInstallment() ? 1 : 0;
        statement.bindLong(20, _tmp_7);
        if (entity.getTotalInstallments() == null) {
          statement.bindNull(21);
        } else {
          statement.bindLong(21, entity.getTotalInstallments());
        }
        if (entity.getRemoteId() == null) {
          statement.bindNull(22);
        } else {
          statement.bindString(22, entity.getRemoteId());
        }
        final String _tmp_8 = __converters.fromLocalDateTime(entity.getLastSyncedAt());
        if (_tmp_8 == null) {
          statement.bindNull(23);
        } else {
          statement.bindString(23, _tmp_8);
        }
        statement.bindString(24, entity.getSyncStatus());
        final int _tmp_9 = entity.isDirty() ? 1 : 0;
        statement.bindLong(25, _tmp_9);
        statement.bindLong(26, entity.getId());
      }
    };
    this.__preparedStmtOfDeleteAll = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM bill";
        return _query;
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
  public Object insertAll(final List<BillEntity> bills,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfBillEntity_1.insert(bills);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
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
  public Object getBillWithCategoryById(final long id,
      final Continuation<? super Map<BillEntity, BillCategoryEntity>> $completion) {
    final String _sql = "\n"
            + "        SELECT * FROM bill \n"
            + "        LEFT JOIN bill_category ON bill.category_id = bill_category.id\n"
            + "        WHERE bill.id = ?\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Map<BillEntity, BillCategoryEntity>>() {
      @Override
      @Nullable
      public Map<BillEntity, BillCategoryEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int[][] _cursorIndices = AmbiguousColumnResolver.resolve(_cursor.getColumnNames(), new String[][] {{"id",
              "category_id", "name", "default_amount", "currency_code", "account_number",
              "bill_reference", "merchant", "recurrence_type", "recurrence_interval", "due_day",
              "start_date", "end_date", "is_active", "is_favorite", "notes", "is_deleted",
              "created_at", "updated_at", "is_installment", "total_installments", "remote_id",
              "last_synced_at", "sync_status", "is_dirty"}, {"id", "name", "is_deleted",
              "remote_id", "sync_status", "icon_key", "color_hex", "is_custom"}});
          final Map<BillEntity, BillCategoryEntity> _result = new LinkedHashMap<BillEntity, BillCategoryEntity>();
          while (_cursor.moveToNext()) {
            final BillEntity _key;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndices[0][0]);
            final long _tmpCategoryId;
            _tmpCategoryId = _cursor.getLong(_cursorIndices[0][1]);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndices[0][2]);
            final double _tmpDefaultAmount;
            _tmpDefaultAmount = _cursor.getDouble(_cursorIndices[0][3]);
            final String _tmpCurrencyCode;
            _tmpCurrencyCode = _cursor.getString(_cursorIndices[0][4]);
            final String _tmpAccountNumber;
            if (_cursor.isNull(_cursorIndices[0][5])) {
              _tmpAccountNumber = null;
            } else {
              _tmpAccountNumber = _cursor.getString(_cursorIndices[0][5]);
            }
            final String _tmpBillReference;
            if (_cursor.isNull(_cursorIndices[0][6])) {
              _tmpBillReference = null;
            } else {
              _tmpBillReference = _cursor.getString(_cursorIndices[0][6]);
            }
            final String _tmpMerchant;
            if (_cursor.isNull(_cursorIndices[0][7])) {
              _tmpMerchant = null;
            } else {
              _tmpMerchant = _cursor.getString(_cursorIndices[0][7]);
            }
            final String _tmpRecurrenceType;
            _tmpRecurrenceType = _cursor.getString(_cursorIndices[0][8]);
            final int _tmpRecurrenceInterval;
            _tmpRecurrenceInterval = _cursor.getInt(_cursorIndices[0][9]);
            final Integer _tmpDueDay;
            if (_cursor.isNull(_cursorIndices[0][10])) {
              _tmpDueDay = null;
            } else {
              _tmpDueDay = _cursor.getInt(_cursorIndices[0][10]);
            }
            final LocalDate _tmpStartDate;
            final String _tmp;
            if (_cursor.isNull(_cursorIndices[0][11])) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndices[0][11]);
            }
            final LocalDate _tmp_1 = __converters.toLocalDate(_tmp);
            if (_tmp_1 == null) {
              throw new IllegalStateException("Expected NON-NULL 'kotlinx.datetime.LocalDate', but it was NULL.");
            } else {
              _tmpStartDate = _tmp_1;
            }
            final LocalDate _tmpEndDate;
            final String _tmp_2;
            if (_cursor.isNull(_cursorIndices[0][12])) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getString(_cursorIndices[0][12]);
            }
            _tmpEndDate = __converters.toLocalDate(_tmp_2);
            final boolean _tmpIsActive;
            final int _tmp_3;
            _tmp_3 = _cursor.getInt(_cursorIndices[0][13]);
            _tmpIsActive = _tmp_3 != 0;
            final boolean _tmpIsFavorite;
            final int _tmp_4;
            _tmp_4 = _cursor.getInt(_cursorIndices[0][14]);
            _tmpIsFavorite = _tmp_4 != 0;
            final String _tmpNotes;
            if (_cursor.isNull(_cursorIndices[0][15])) {
              _tmpNotes = null;
            } else {
              _tmpNotes = _cursor.getString(_cursorIndices[0][15]);
            }
            final boolean _tmpIsDeleted;
            final int _tmp_5;
            _tmp_5 = _cursor.getInt(_cursorIndices[0][16]);
            _tmpIsDeleted = _tmp_5 != 0;
            final LocalDateTime _tmpCreatedAt;
            final String _tmp_6;
            if (_cursor.isNull(_cursorIndices[0][17])) {
              _tmp_6 = null;
            } else {
              _tmp_6 = _cursor.getString(_cursorIndices[0][17]);
            }
            final LocalDateTime _tmp_7 = __converters.toLocalDateTime(_tmp_6);
            if (_tmp_7 == null) {
              throw new IllegalStateException("Expected NON-NULL 'kotlinx.datetime.LocalDateTime', but it was NULL.");
            } else {
              _tmpCreatedAt = _tmp_7;
            }
            final LocalDateTime _tmpUpdatedAt;
            final String _tmp_8;
            if (_cursor.isNull(_cursorIndices[0][18])) {
              _tmp_8 = null;
            } else {
              _tmp_8 = _cursor.getString(_cursorIndices[0][18]);
            }
            final LocalDateTime _tmp_9 = __converters.toLocalDateTime(_tmp_8);
            if (_tmp_9 == null) {
              throw new IllegalStateException("Expected NON-NULL 'kotlinx.datetime.LocalDateTime', but it was NULL.");
            } else {
              _tmpUpdatedAt = _tmp_9;
            }
            final boolean _tmpIsInstallment;
            final int _tmp_10;
            _tmp_10 = _cursor.getInt(_cursorIndices[0][19]);
            _tmpIsInstallment = _tmp_10 != 0;
            final Integer _tmpTotalInstallments;
            if (_cursor.isNull(_cursorIndices[0][20])) {
              _tmpTotalInstallments = null;
            } else {
              _tmpTotalInstallments = _cursor.getInt(_cursorIndices[0][20]);
            }
            final String _tmpRemoteId;
            if (_cursor.isNull(_cursorIndices[0][21])) {
              _tmpRemoteId = null;
            } else {
              _tmpRemoteId = _cursor.getString(_cursorIndices[0][21]);
            }
            final LocalDateTime _tmpLastSyncedAt;
            final String _tmp_11;
            if (_cursor.isNull(_cursorIndices[0][22])) {
              _tmp_11 = null;
            } else {
              _tmp_11 = _cursor.getString(_cursorIndices[0][22]);
            }
            _tmpLastSyncedAt = __converters.toLocalDateTime(_tmp_11);
            final String _tmpSyncStatus;
            _tmpSyncStatus = _cursor.getString(_cursorIndices[0][23]);
            final boolean _tmpIsDirty;
            final int _tmp_12;
            _tmp_12 = _cursor.getInt(_cursorIndices[0][24]);
            _tmpIsDirty = _tmp_12 != 0;
            _key = new BillEntity(_tmpId,_tmpCategoryId,_tmpName,_tmpDefaultAmount,_tmpCurrencyCode,_tmpAccountNumber,_tmpBillReference,_tmpMerchant,_tmpRecurrenceType,_tmpRecurrenceInterval,_tmpDueDay,_tmpStartDate,_tmpEndDate,_tmpIsActive,_tmpIsFavorite,_tmpNotes,_tmpIsDeleted,_tmpCreatedAt,_tmpUpdatedAt,_tmpIsInstallment,_tmpTotalInstallments,_tmpRemoteId,_tmpLastSyncedAt,_tmpSyncStatus,_tmpIsDirty);
            if (_cursor.isNull(_cursorIndices[1][0]) && _cursor.isNull(_cursorIndices[1][1]) &&
                _cursor.isNull(_cursorIndices[1][2]) && _cursor.isNull(_cursorIndices[1][3]) &&
                _cursor.isNull(_cursorIndices[1][4]) && _cursor.isNull(_cursorIndices[1][5]) &&
                _cursor.isNull(_cursorIndices[1][6]) && _cursor.isNull(_cursorIndices[1][7])) {
              _result.put(_key, null);
              continue;
            }
            final BillCategoryEntity _value;
            final long _tmpId_1;
            _tmpId_1 = _cursor.getLong(_cursorIndices[1][0]);
            final String _tmpName_1;
            _tmpName_1 = _cursor.getString(_cursorIndices[1][1]);
            final boolean _tmpIsDeleted_1;
            final int _tmp_13;
            _tmp_13 = _cursor.getInt(_cursorIndices[1][2]);
            _tmpIsDeleted_1 = _tmp_13 != 0;
            final String _tmpRemoteId_1;
            if (_cursor.isNull(_cursorIndices[1][3])) {
              _tmpRemoteId_1 = null;
            } else {
              _tmpRemoteId_1 = _cursor.getString(_cursorIndices[1][3]);
            }
            final String _tmpSyncStatus_1;
            _tmpSyncStatus_1 = _cursor.getString(_cursorIndices[1][4]);
            final String _tmpIconKey;
            _tmpIconKey = _cursor.getString(_cursorIndices[1][5]);
            final String _tmpColorHex;
            _tmpColorHex = _cursor.getString(_cursorIndices[1][6]);
            final boolean _tmpIsCustom;
            final int _tmp_14;
            _tmp_14 = _cursor.getInt(_cursorIndices[1][7]);
            _tmpIsCustom = _tmp_14 != 0;
            _value = new BillCategoryEntity(_tmpId_1,_tmpName_1,_tmpIconKey,_tmpColorHex,_tmpIsCustom,_tmpIsDeleted_1,_tmpRemoteId_1,_tmpSyncStatus_1);
            if (!_result.containsKey(_key)) {
              _result.put(_key, _value);
            }
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
  public Flow<Map<BillEntity, BillCategoryEntity>> getAllWithCategory() {
    final String _sql = "\n"
            + "        SELECT * FROM bill \n"
            + "        JOIN bill_category ON bill.category_id = bill_category.id\n"
            + "        WHERE bill.is_deleted = 0 \n"
            + "        ORDER BY bill.name ASC\n"
            + "    ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"bill",
        "bill_category"}, new Callable<Map<BillEntity, BillCategoryEntity>>() {
      @Override
      @NonNull
      public Map<BillEntity, BillCategoryEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int[][] _cursorIndices = AmbiguousColumnResolver.resolve(_cursor.getColumnNames(), new String[][] {{"id",
              "category_id", "name", "default_amount", "currency_code", "account_number",
              "bill_reference", "merchant", "recurrence_type", "recurrence_interval", "due_day",
              "start_date", "end_date", "is_active", "is_favorite", "notes", "is_deleted",
              "created_at", "updated_at", "is_installment", "total_installments", "remote_id",
              "last_synced_at", "sync_status", "is_dirty"}, {"id", "name", "is_deleted",
              "remote_id", "sync_status", "icon_key", "color_hex", "is_custom"}});
          final Map<BillEntity, BillCategoryEntity> _result = new LinkedHashMap<BillEntity, BillCategoryEntity>();
          while (_cursor.moveToNext()) {
            final BillEntity _key;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndices[0][0]);
            final long _tmpCategoryId;
            _tmpCategoryId = _cursor.getLong(_cursorIndices[0][1]);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndices[0][2]);
            final double _tmpDefaultAmount;
            _tmpDefaultAmount = _cursor.getDouble(_cursorIndices[0][3]);
            final String _tmpCurrencyCode;
            _tmpCurrencyCode = _cursor.getString(_cursorIndices[0][4]);
            final String _tmpAccountNumber;
            if (_cursor.isNull(_cursorIndices[0][5])) {
              _tmpAccountNumber = null;
            } else {
              _tmpAccountNumber = _cursor.getString(_cursorIndices[0][5]);
            }
            final String _tmpBillReference;
            if (_cursor.isNull(_cursorIndices[0][6])) {
              _tmpBillReference = null;
            } else {
              _tmpBillReference = _cursor.getString(_cursorIndices[0][6]);
            }
            final String _tmpMerchant;
            if (_cursor.isNull(_cursorIndices[0][7])) {
              _tmpMerchant = null;
            } else {
              _tmpMerchant = _cursor.getString(_cursorIndices[0][7]);
            }
            final String _tmpRecurrenceType;
            _tmpRecurrenceType = _cursor.getString(_cursorIndices[0][8]);
            final int _tmpRecurrenceInterval;
            _tmpRecurrenceInterval = _cursor.getInt(_cursorIndices[0][9]);
            final Integer _tmpDueDay;
            if (_cursor.isNull(_cursorIndices[0][10])) {
              _tmpDueDay = null;
            } else {
              _tmpDueDay = _cursor.getInt(_cursorIndices[0][10]);
            }
            final LocalDate _tmpStartDate;
            final String _tmp;
            if (_cursor.isNull(_cursorIndices[0][11])) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndices[0][11]);
            }
            final LocalDate _tmp_1 = __converters.toLocalDate(_tmp);
            if (_tmp_1 == null) {
              throw new IllegalStateException("Expected NON-NULL 'kotlinx.datetime.LocalDate', but it was NULL.");
            } else {
              _tmpStartDate = _tmp_1;
            }
            final LocalDate _tmpEndDate;
            final String _tmp_2;
            if (_cursor.isNull(_cursorIndices[0][12])) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getString(_cursorIndices[0][12]);
            }
            _tmpEndDate = __converters.toLocalDate(_tmp_2);
            final boolean _tmpIsActive;
            final int _tmp_3;
            _tmp_3 = _cursor.getInt(_cursorIndices[0][13]);
            _tmpIsActive = _tmp_3 != 0;
            final boolean _tmpIsFavorite;
            final int _tmp_4;
            _tmp_4 = _cursor.getInt(_cursorIndices[0][14]);
            _tmpIsFavorite = _tmp_4 != 0;
            final String _tmpNotes;
            if (_cursor.isNull(_cursorIndices[0][15])) {
              _tmpNotes = null;
            } else {
              _tmpNotes = _cursor.getString(_cursorIndices[0][15]);
            }
            final boolean _tmpIsDeleted;
            final int _tmp_5;
            _tmp_5 = _cursor.getInt(_cursorIndices[0][16]);
            _tmpIsDeleted = _tmp_5 != 0;
            final LocalDateTime _tmpCreatedAt;
            final String _tmp_6;
            if (_cursor.isNull(_cursorIndices[0][17])) {
              _tmp_6 = null;
            } else {
              _tmp_6 = _cursor.getString(_cursorIndices[0][17]);
            }
            final LocalDateTime _tmp_7 = __converters.toLocalDateTime(_tmp_6);
            if (_tmp_7 == null) {
              throw new IllegalStateException("Expected NON-NULL 'kotlinx.datetime.LocalDateTime', but it was NULL.");
            } else {
              _tmpCreatedAt = _tmp_7;
            }
            final LocalDateTime _tmpUpdatedAt;
            final String _tmp_8;
            if (_cursor.isNull(_cursorIndices[0][18])) {
              _tmp_8 = null;
            } else {
              _tmp_8 = _cursor.getString(_cursorIndices[0][18]);
            }
            final LocalDateTime _tmp_9 = __converters.toLocalDateTime(_tmp_8);
            if (_tmp_9 == null) {
              throw new IllegalStateException("Expected NON-NULL 'kotlinx.datetime.LocalDateTime', but it was NULL.");
            } else {
              _tmpUpdatedAt = _tmp_9;
            }
            final boolean _tmpIsInstallment;
            final int _tmp_10;
            _tmp_10 = _cursor.getInt(_cursorIndices[0][19]);
            _tmpIsInstallment = _tmp_10 != 0;
            final Integer _tmpTotalInstallments;
            if (_cursor.isNull(_cursorIndices[0][20])) {
              _tmpTotalInstallments = null;
            } else {
              _tmpTotalInstallments = _cursor.getInt(_cursorIndices[0][20]);
            }
            final String _tmpRemoteId;
            if (_cursor.isNull(_cursorIndices[0][21])) {
              _tmpRemoteId = null;
            } else {
              _tmpRemoteId = _cursor.getString(_cursorIndices[0][21]);
            }
            final LocalDateTime _tmpLastSyncedAt;
            final String _tmp_11;
            if (_cursor.isNull(_cursorIndices[0][22])) {
              _tmp_11 = null;
            } else {
              _tmp_11 = _cursor.getString(_cursorIndices[0][22]);
            }
            _tmpLastSyncedAt = __converters.toLocalDateTime(_tmp_11);
            final String _tmpSyncStatus;
            _tmpSyncStatus = _cursor.getString(_cursorIndices[0][23]);
            final boolean _tmpIsDirty;
            final int _tmp_12;
            _tmp_12 = _cursor.getInt(_cursorIndices[0][24]);
            _tmpIsDirty = _tmp_12 != 0;
            _key = new BillEntity(_tmpId,_tmpCategoryId,_tmpName,_tmpDefaultAmount,_tmpCurrencyCode,_tmpAccountNumber,_tmpBillReference,_tmpMerchant,_tmpRecurrenceType,_tmpRecurrenceInterval,_tmpDueDay,_tmpStartDate,_tmpEndDate,_tmpIsActive,_tmpIsFavorite,_tmpNotes,_tmpIsDeleted,_tmpCreatedAt,_tmpUpdatedAt,_tmpIsInstallment,_tmpTotalInstallments,_tmpRemoteId,_tmpLastSyncedAt,_tmpSyncStatus,_tmpIsDirty);
            if (_cursor.isNull(_cursorIndices[1][0]) && _cursor.isNull(_cursorIndices[1][1]) &&
                _cursor.isNull(_cursorIndices[1][2]) && _cursor.isNull(_cursorIndices[1][3]) &&
                _cursor.isNull(_cursorIndices[1][4]) && _cursor.isNull(_cursorIndices[1][5]) &&
                _cursor.isNull(_cursorIndices[1][6]) && _cursor.isNull(_cursorIndices[1][7])) {
              _result.put(_key, null);
              continue;
            }
            final BillCategoryEntity _value;
            final long _tmpId_1;
            _tmpId_1 = _cursor.getLong(_cursorIndices[1][0]);
            final String _tmpName_1;
            _tmpName_1 = _cursor.getString(_cursorIndices[1][1]);
            final boolean _tmpIsDeleted_1;
            final int _tmp_13;
            _tmp_13 = _cursor.getInt(_cursorIndices[1][2]);
            _tmpIsDeleted_1 = _tmp_13 != 0;
            final String _tmpRemoteId_1;
            if (_cursor.isNull(_cursorIndices[1][3])) {
              _tmpRemoteId_1 = null;
            } else {
              _tmpRemoteId_1 = _cursor.getString(_cursorIndices[1][3]);
            }
            final String _tmpSyncStatus_1;
            _tmpSyncStatus_1 = _cursor.getString(_cursorIndices[1][4]);
            final String _tmpIconKey;
            _tmpIconKey = _cursor.getString(_cursorIndices[1][5]);
            final String _tmpColorHex;
            _tmpColorHex = _cursor.getString(_cursorIndices[1][6]);
            final boolean _tmpIsCustom;
            final int _tmp_14;
            _tmp_14 = _cursor.getInt(_cursorIndices[1][7]);
            _tmpIsCustom = _tmp_14 != 0;
            _value = new BillCategoryEntity(_tmpId_1,_tmpName_1,_tmpIconKey,_tmpColorHex,_tmpIsCustom,_tmpIsDeleted_1,_tmpRemoteId_1,_tmpSyncStatus_1);
            if (!_result.containsKey(_key)) {
              _result.put(_key, _value);
            }
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
          final int _cursorIndexOfAccountNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "account_number");
          final int _cursorIndexOfBillReference = CursorUtil.getColumnIndexOrThrow(_cursor, "bill_reference");
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
            final String _tmpAccountNumber;
            if (_cursor.isNull(_cursorIndexOfAccountNumber)) {
              _tmpAccountNumber = null;
            } else {
              _tmpAccountNumber = _cursor.getString(_cursorIndexOfAccountNumber);
            }
            final String _tmpBillReference;
            if (_cursor.isNull(_cursorIndexOfBillReference)) {
              _tmpBillReference = null;
            } else {
              _tmpBillReference = _cursor.getString(_cursorIndexOfBillReference);
            }
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
            _item = new BillEntity(_tmpId,_tmpCategoryId,_tmpName,_tmpDefaultAmount,_tmpCurrencyCode,_tmpAccountNumber,_tmpBillReference,_tmpMerchant,_tmpRecurrenceType,_tmpRecurrenceInterval,_tmpDueDay,_tmpStartDate,_tmpEndDate,_tmpIsActive,_tmpIsFavorite,_tmpNotes,_tmpIsDeleted,_tmpCreatedAt,_tmpUpdatedAt,_tmpIsInstallment,_tmpTotalInstallments,_tmpRemoteId,_tmpLastSyncedAt,_tmpSyncStatus,_tmpIsDirty);
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
  public Flow<List<BillWithDetailsEntity>> getBillsWithDetails() {
    final String _sql = "SELECT * FROM bill WHERE is_deleted = 0 ORDER BY name ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, true, new String[] {"bill_category", "bill_cycle",
        "bill"}, new Callable<List<BillWithDetailsEntity>>() {
      @Override
      @NonNull
      public List<BillWithDetailsEntity> call() throws Exception {
        __db.beginTransaction();
        try {
          final Cursor _cursor = DBUtil.query(__db, _statement, true, null);
          try {
            final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
            final int _cursorIndexOfCategoryId = CursorUtil.getColumnIndexOrThrow(_cursor, "category_id");
            final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
            final int _cursorIndexOfDefaultAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "default_amount");
            final int _cursorIndexOfCurrencyCode = CursorUtil.getColumnIndexOrThrow(_cursor, "currency_code");
            final int _cursorIndexOfAccountNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "account_number");
            final int _cursorIndexOfBillReference = CursorUtil.getColumnIndexOrThrow(_cursor, "bill_reference");
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
            final LongSparseArray<BillCategoryEntity> _collectionCategory = new LongSparseArray<BillCategoryEntity>();
            final LongSparseArray<ArrayList<BillCycleEntity>> _collectionCycles = new LongSparseArray<ArrayList<BillCycleEntity>>();
            while (_cursor.moveToNext()) {
              final long _tmpKey;
              _tmpKey = _cursor.getLong(_cursorIndexOfCategoryId);
              _collectionCategory.put(_tmpKey, null);
              final long _tmpKey_1;
              _tmpKey_1 = _cursor.getLong(_cursorIndexOfId);
              if (!_collectionCycles.containsKey(_tmpKey_1)) {
                _collectionCycles.put(_tmpKey_1, new ArrayList<BillCycleEntity>());
              }
            }
            _cursor.moveToPosition(-1);
            __fetchRelationshipbillCategoryAscomPatflowAppDataLocalEntityBillCategoryEntity(_collectionCategory);
            __fetchRelationshipbillCycleAscomPatflowAppDataLocalEntityBillCycleEntity(_collectionCycles);
            final List<BillWithDetailsEntity> _result = new ArrayList<BillWithDetailsEntity>(_cursor.getCount());
            while (_cursor.moveToNext()) {
              final BillWithDetailsEntity _item;
              final BillEntity _tmpBill;
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
              final String _tmpAccountNumber;
              if (_cursor.isNull(_cursorIndexOfAccountNumber)) {
                _tmpAccountNumber = null;
              } else {
                _tmpAccountNumber = _cursor.getString(_cursorIndexOfAccountNumber);
              }
              final String _tmpBillReference;
              if (_cursor.isNull(_cursorIndexOfBillReference)) {
                _tmpBillReference = null;
              } else {
                _tmpBillReference = _cursor.getString(_cursorIndexOfBillReference);
              }
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
              _tmpBill = new BillEntity(_tmpId,_tmpCategoryId,_tmpName,_tmpDefaultAmount,_tmpCurrencyCode,_tmpAccountNumber,_tmpBillReference,_tmpMerchant,_tmpRecurrenceType,_tmpRecurrenceInterval,_tmpDueDay,_tmpStartDate,_tmpEndDate,_tmpIsActive,_tmpIsFavorite,_tmpNotes,_tmpIsDeleted,_tmpCreatedAt,_tmpUpdatedAt,_tmpIsInstallment,_tmpTotalInstallments,_tmpRemoteId,_tmpLastSyncedAt,_tmpSyncStatus,_tmpIsDirty);
              final BillCategoryEntity _tmpCategory;
              final long _tmpKey_2;
              _tmpKey_2 = _cursor.getLong(_cursorIndexOfCategoryId);
              _tmpCategory = _collectionCategory.get(_tmpKey_2);
              final ArrayList<BillCycleEntity> _tmpCyclesCollection;
              final long _tmpKey_3;
              _tmpKey_3 = _cursor.getLong(_cursorIndexOfId);
              _tmpCyclesCollection = _collectionCycles.get(_tmpKey_3);
              _item = new BillWithDetailsEntity(_tmpBill,_tmpCategory,_tmpCyclesCollection);
              _result.add(_item);
            }
            __db.setTransactionSuccessful();
            return _result;
          } finally {
            _cursor.close();
          }
        } finally {
          __db.endTransaction();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getAllEntities(final Continuation<? super List<BillEntity>> $completion) {
    final String _sql = "SELECT * FROM bill";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<BillEntity>>() {
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
          final int _cursorIndexOfAccountNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "account_number");
          final int _cursorIndexOfBillReference = CursorUtil.getColumnIndexOrThrow(_cursor, "bill_reference");
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
            final String _tmpAccountNumber;
            if (_cursor.isNull(_cursorIndexOfAccountNumber)) {
              _tmpAccountNumber = null;
            } else {
              _tmpAccountNumber = _cursor.getString(_cursorIndexOfAccountNumber);
            }
            final String _tmpBillReference;
            if (_cursor.isNull(_cursorIndexOfBillReference)) {
              _tmpBillReference = null;
            } else {
              _tmpBillReference = _cursor.getString(_cursorIndexOfBillReference);
            }
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
            _item = new BillEntity(_tmpId,_tmpCategoryId,_tmpName,_tmpDefaultAmount,_tmpCurrencyCode,_tmpAccountNumber,_tmpBillReference,_tmpMerchant,_tmpRecurrenceType,_tmpRecurrenceInterval,_tmpDueDay,_tmpStartDate,_tmpEndDate,_tmpIsActive,_tmpIsFavorite,_tmpNotes,_tmpIsDeleted,_tmpCreatedAt,_tmpUpdatedAt,_tmpIsInstallment,_tmpTotalInstallments,_tmpRemoteId,_tmpLastSyncedAt,_tmpSyncStatus,_tmpIsDirty);
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

  private void __fetchRelationshipbillCategoryAscomPatflowAppDataLocalEntityBillCategoryEntity(
      @NonNull final LongSparseArray<BillCategoryEntity> _map) {
    if (_map.isEmpty()) {
      return;
    }
    if (_map.size() > RoomDatabase.MAX_BIND_PARAMETER_CNT) {
      RelationUtil.recursiveFetchLongSparseArray(_map, false, (map) -> {
        __fetchRelationshipbillCategoryAscomPatflowAppDataLocalEntityBillCategoryEntity(map);
        return Unit.INSTANCE;
      });
      return;
    }
    final StringBuilder _stringBuilder = StringUtil.newStringBuilder();
    _stringBuilder.append("SELECT `id`,`name`,`icon_key`,`color_hex`,`is_custom`,`is_deleted`,`remote_id`,`sync_status` FROM `bill_category` WHERE `id` IN (");
    final int _inputSize = _map.size();
    StringUtil.appendPlaceholders(_stringBuilder, _inputSize);
    _stringBuilder.append(")");
    final String _sql = _stringBuilder.toString();
    final int _argCount = 0 + _inputSize;
    final RoomSQLiteQuery _stmt = RoomSQLiteQuery.acquire(_sql, _argCount);
    int _argIndex = 1;
    for (int i = 0; i < _map.size(); i++) {
      final long _item = _map.keyAt(i);
      _stmt.bindLong(_argIndex, _item);
      _argIndex++;
    }
    final Cursor _cursor = DBUtil.query(__db, _stmt, false, null);
    try {
      final int _itemKeyIndex = CursorUtil.getColumnIndex(_cursor, "id");
      if (_itemKeyIndex == -1) {
        return;
      }
      final int _cursorIndexOfId = 0;
      final int _cursorIndexOfName = 1;
      final int _cursorIndexOfIconKey = 2;
      final int _cursorIndexOfColorHex = 3;
      final int _cursorIndexOfIsCustom = 4;
      final int _cursorIndexOfIsDeleted = 5;
      final int _cursorIndexOfRemoteId = 6;
      final int _cursorIndexOfSyncStatus = 7;
      while (_cursor.moveToNext()) {
        final long _tmpKey;
        _tmpKey = _cursor.getLong(_itemKeyIndex);
        if (_map.containsKey(_tmpKey)) {
          final BillCategoryEntity _item_1;
          final long _tmpId;
          _tmpId = _cursor.getLong(_cursorIndexOfId);
          final String _tmpName;
          _tmpName = _cursor.getString(_cursorIndexOfName);
          final String _tmpIconKey;
          _tmpIconKey = _cursor.getString(_cursorIndexOfIconKey);
          final String _tmpColorHex;
          _tmpColorHex = _cursor.getString(_cursorIndexOfColorHex);
          final boolean _tmpIsCustom;
          final int _tmp;
          _tmp = _cursor.getInt(_cursorIndexOfIsCustom);
          _tmpIsCustom = _tmp != 0;
          final boolean _tmpIsDeleted;
          final int _tmp_1;
          _tmp_1 = _cursor.getInt(_cursorIndexOfIsDeleted);
          _tmpIsDeleted = _tmp_1 != 0;
          final String _tmpRemoteId;
          if (_cursor.isNull(_cursorIndexOfRemoteId)) {
            _tmpRemoteId = null;
          } else {
            _tmpRemoteId = _cursor.getString(_cursorIndexOfRemoteId);
          }
          final String _tmpSyncStatus;
          _tmpSyncStatus = _cursor.getString(_cursorIndexOfSyncStatus);
          _item_1 = new BillCategoryEntity(_tmpId,_tmpName,_tmpIconKey,_tmpColorHex,_tmpIsCustom,_tmpIsDeleted,_tmpRemoteId,_tmpSyncStatus);
          _map.put(_tmpKey, _item_1);
        }
      }
    } finally {
      _cursor.close();
    }
  }

  private void __fetchRelationshipbillCycleAscomPatflowAppDataLocalEntityBillCycleEntity(
      @NonNull final LongSparseArray<ArrayList<BillCycleEntity>> _map) {
    if (_map.isEmpty()) {
      return;
    }
    if (_map.size() > RoomDatabase.MAX_BIND_PARAMETER_CNT) {
      RelationUtil.recursiveFetchLongSparseArray(_map, true, (map) -> {
        __fetchRelationshipbillCycleAscomPatflowAppDataLocalEntityBillCycleEntity(map);
        return Unit.INSTANCE;
      });
      return;
    }
    final StringBuilder _stringBuilder = StringUtil.newStringBuilder();
    _stringBuilder.append("SELECT `id`,`bill_id`,`period_start`,`due_date`,`amount_due`,`amount_paid`,`status`,`created_at`,`updated_at`,`installment_number` FROM `bill_cycle` WHERE `bill_id` IN (");
    final int _inputSize = _map.size();
    StringUtil.appendPlaceholders(_stringBuilder, _inputSize);
    _stringBuilder.append(")");
    final String _sql = _stringBuilder.toString();
    final int _argCount = 0 + _inputSize;
    final RoomSQLiteQuery _stmt = RoomSQLiteQuery.acquire(_sql, _argCount);
    int _argIndex = 1;
    for (int i = 0; i < _map.size(); i++) {
      final long _item = _map.keyAt(i);
      _stmt.bindLong(_argIndex, _item);
      _argIndex++;
    }
    final Cursor _cursor = DBUtil.query(__db, _stmt, false, null);
    try {
      final int _itemKeyIndex = CursorUtil.getColumnIndex(_cursor, "bill_id");
      if (_itemKeyIndex == -1) {
        return;
      }
      final int _cursorIndexOfId = 0;
      final int _cursorIndexOfBillId = 1;
      final int _cursorIndexOfPeriodStart = 2;
      final int _cursorIndexOfDueDate = 3;
      final int _cursorIndexOfAmountDue = 4;
      final int _cursorIndexOfAmountPaid = 5;
      final int _cursorIndexOfStatus = 6;
      final int _cursorIndexOfCreatedAt = 7;
      final int _cursorIndexOfUpdatedAt = 8;
      final int _cursorIndexOfInstallmentNumber = 9;
      while (_cursor.moveToNext()) {
        final long _tmpKey;
        _tmpKey = _cursor.getLong(_itemKeyIndex);
        final ArrayList<BillCycleEntity> _tmpRelation = _map.get(_tmpKey);
        if (_tmpRelation != null) {
          final BillCycleEntity _item_1;
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
          _item_1 = new BillCycleEntity(_tmpId,_tmpBillId,_tmpPeriodStart,_tmpDueDate,_tmpAmountDue,_tmpAmountPaid,_tmpStatus,_tmpCreatedAt,_tmpUpdatedAt,_tmpInstallmentNumber);
          _tmpRelation.add(_item_1);
        }
      }
    } finally {
      _cursor.close();
    }
  }
}
