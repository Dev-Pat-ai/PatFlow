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
import com.patflow.app.data.local.entity.IncomeCategoryEntity;
import com.patflow.app.data.local.entity.IncomeEntryEntity;
import com.patflow.app.data.local.entity.IncomeSourceEntity;
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
public final class IncomeDao_Impl implements IncomeDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<IncomeCategoryEntity> __insertionAdapterOfIncomeCategoryEntity;

  private final EntityInsertionAdapter<IncomeSourceEntity> __insertionAdapterOfIncomeSourceEntity;

  private final Converters __converters = new Converters();

  private final EntityInsertionAdapter<IncomeEntryEntity> __insertionAdapterOfIncomeEntryEntity;

  private final EntityDeletionOrUpdateAdapter<IncomeCategoryEntity> __deletionAdapterOfIncomeCategoryEntity;

  private final EntityDeletionOrUpdateAdapter<IncomeSourceEntity> __deletionAdapterOfIncomeSourceEntity;

  private final EntityDeletionOrUpdateAdapter<IncomeEntryEntity> __deletionAdapterOfIncomeEntryEntity;

  private final EntityDeletionOrUpdateAdapter<IncomeCategoryEntity> __updateAdapterOfIncomeCategoryEntity;

  private final EntityDeletionOrUpdateAdapter<IncomeSourceEntity> __updateAdapterOfIncomeSourceEntity;

  private final EntityDeletionOrUpdateAdapter<IncomeEntryEntity> __updateAdapterOfIncomeEntryEntity;

  public IncomeDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfIncomeCategoryEntity = new EntityInsertionAdapter<IncomeCategoryEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `income_category` (`id`,`name`,`icon_key`,`color_hex`,`is_custom`) VALUES (nullif(?, 0),?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final IncomeCategoryEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getName());
        statement.bindString(3, entity.getIconKey());
        statement.bindString(4, entity.getColorHex());
        final int _tmp = entity.isCustom() ? 1 : 0;
        statement.bindLong(5, _tmp);
      }
    };
    this.__insertionAdapterOfIncomeSourceEntity = new EntityInsertionAdapter<IncomeSourceEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `income_source` (`id`,`category_id`,`name`,`default_amount`,`recurrence_type`,`recurrence_interval`,`start_date`,`end_date`,`is_active`,`is_deleted`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final IncomeSourceEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getCategoryId());
        statement.bindString(3, entity.getName());
        statement.bindDouble(4, entity.getDefaultAmount());
        statement.bindString(5, entity.getRecurrenceType());
        statement.bindLong(6, entity.getRecurrenceInterval());
        final String _tmp = __converters.fromLocalDate(entity.getStartDate());
        if (_tmp == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, _tmp);
        }
        final String _tmp_1 = __converters.fromLocalDate(entity.getEndDate());
        if (_tmp_1 == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, _tmp_1);
        }
        final int _tmp_2 = entity.isActive() ? 1 : 0;
        statement.bindLong(9, _tmp_2);
        final int _tmp_3 = entity.isDeleted() ? 1 : 0;
        statement.bindLong(10, _tmp_3);
      }
    };
    this.__insertionAdapterOfIncomeEntryEntity = new EntityInsertionAdapter<IncomeEntryEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `income_entry` (`id`,`income_source_id`,`category_id`,`amount`,`currency_code`,`entry_date`,`note`,`created_at`) VALUES (nullif(?, 0),?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final IncomeEntryEntity entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getIncomeSourceId() == null) {
          statement.bindNull(2);
        } else {
          statement.bindLong(2, entity.getIncomeSourceId());
        }
        statement.bindLong(3, entity.getCategoryId());
        statement.bindDouble(4, entity.getAmount());
        statement.bindString(5, entity.getCurrencyCode());
        final String _tmp = __converters.fromLocalDate(entity.getEntryDate());
        if (_tmp == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, _tmp);
        }
        if (entity.getNote() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getNote());
        }
        final String _tmp_1 = __converters.fromLocalDateTime(entity.getCreatedAt());
        if (_tmp_1 == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, _tmp_1);
        }
      }
    };
    this.__deletionAdapterOfIncomeCategoryEntity = new EntityDeletionOrUpdateAdapter<IncomeCategoryEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `income_category` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final IncomeCategoryEntity entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__deletionAdapterOfIncomeSourceEntity = new EntityDeletionOrUpdateAdapter<IncomeSourceEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `income_source` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final IncomeSourceEntity entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__deletionAdapterOfIncomeEntryEntity = new EntityDeletionOrUpdateAdapter<IncomeEntryEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `income_entry` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final IncomeEntryEntity entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfIncomeCategoryEntity = new EntityDeletionOrUpdateAdapter<IncomeCategoryEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `income_category` SET `id` = ?,`name` = ?,`icon_key` = ?,`color_hex` = ?,`is_custom` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final IncomeCategoryEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getName());
        statement.bindString(3, entity.getIconKey());
        statement.bindString(4, entity.getColorHex());
        final int _tmp = entity.isCustom() ? 1 : 0;
        statement.bindLong(5, _tmp);
        statement.bindLong(6, entity.getId());
      }
    };
    this.__updateAdapterOfIncomeSourceEntity = new EntityDeletionOrUpdateAdapter<IncomeSourceEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `income_source` SET `id` = ?,`category_id` = ?,`name` = ?,`default_amount` = ?,`recurrence_type` = ?,`recurrence_interval` = ?,`start_date` = ?,`end_date` = ?,`is_active` = ?,`is_deleted` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final IncomeSourceEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindLong(2, entity.getCategoryId());
        statement.bindString(3, entity.getName());
        statement.bindDouble(4, entity.getDefaultAmount());
        statement.bindString(5, entity.getRecurrenceType());
        statement.bindLong(6, entity.getRecurrenceInterval());
        final String _tmp = __converters.fromLocalDate(entity.getStartDate());
        if (_tmp == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, _tmp);
        }
        final String _tmp_1 = __converters.fromLocalDate(entity.getEndDate());
        if (_tmp_1 == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, _tmp_1);
        }
        final int _tmp_2 = entity.isActive() ? 1 : 0;
        statement.bindLong(9, _tmp_2);
        final int _tmp_3 = entity.isDeleted() ? 1 : 0;
        statement.bindLong(10, _tmp_3);
        statement.bindLong(11, entity.getId());
      }
    };
    this.__updateAdapterOfIncomeEntryEntity = new EntityDeletionOrUpdateAdapter<IncomeEntryEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `income_entry` SET `id` = ?,`income_source_id` = ?,`category_id` = ?,`amount` = ?,`currency_code` = ?,`entry_date` = ?,`note` = ?,`created_at` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final IncomeEntryEntity entity) {
        statement.bindLong(1, entity.getId());
        if (entity.getIncomeSourceId() == null) {
          statement.bindNull(2);
        } else {
          statement.bindLong(2, entity.getIncomeSourceId());
        }
        statement.bindLong(3, entity.getCategoryId());
        statement.bindDouble(4, entity.getAmount());
        statement.bindString(5, entity.getCurrencyCode());
        final String _tmp = __converters.fromLocalDate(entity.getEntryDate());
        if (_tmp == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, _tmp);
        }
        if (entity.getNote() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getNote());
        }
        final String _tmp_1 = __converters.fromLocalDateTime(entity.getCreatedAt());
        if (_tmp_1 == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, _tmp_1);
        }
        statement.bindLong(9, entity.getId());
      }
    };
  }

  @Override
  public Object insertCategory(final IncomeCategoryEntity category,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfIncomeCategoryEntity.insertAndReturnId(category);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertSource(final IncomeSourceEntity source,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfIncomeSourceEntity.insertAndReturnId(source);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertEntry(final IncomeEntryEntity entry,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfIncomeEntryEntity.insertAndReturnId(entry);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteCategory(final IncomeCategoryEntity category,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfIncomeCategoryEntity.handle(category);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteSource(final IncomeSourceEntity source,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfIncomeSourceEntity.handle(source);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteEntry(final IncomeEntryEntity entry,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfIncomeEntryEntity.handle(entry);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateCategory(final IncomeCategoryEntity category,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfIncomeCategoryEntity.handle(category);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateSource(final IncomeSourceEntity source,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfIncomeSourceEntity.handle(source);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateEntry(final IncomeEntryEntity entry,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfIncomeEntryEntity.handle(entry);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<IncomeCategoryEntity>> getAllCategories() {
    final String _sql = "SELECT * FROM income_category ORDER BY name ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"income_category"}, new Callable<List<IncomeCategoryEntity>>() {
      @Override
      @NonNull
      public List<IncomeCategoryEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfIconKey = CursorUtil.getColumnIndexOrThrow(_cursor, "icon_key");
          final int _cursorIndexOfColorHex = CursorUtil.getColumnIndexOrThrow(_cursor, "color_hex");
          final int _cursorIndexOfIsCustom = CursorUtil.getColumnIndexOrThrow(_cursor, "is_custom");
          final List<IncomeCategoryEntity> _result = new ArrayList<IncomeCategoryEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final IncomeCategoryEntity _item;
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
            _item = new IncomeCategoryEntity(_tmpId,_tmpName,_tmpIconKey,_tmpColorHex,_tmpIsCustom);
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
  public Object getSourceById(final long id,
      final Continuation<? super IncomeSourceEntity> $completion) {
    final String _sql = "SELECT * FROM income_source WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<IncomeSourceEntity>() {
      @Override
      @Nullable
      public IncomeSourceEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfCategoryId = CursorUtil.getColumnIndexOrThrow(_cursor, "category_id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfDefaultAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "default_amount");
          final int _cursorIndexOfRecurrenceType = CursorUtil.getColumnIndexOrThrow(_cursor, "recurrence_type");
          final int _cursorIndexOfRecurrenceInterval = CursorUtil.getColumnIndexOrThrow(_cursor, "recurrence_interval");
          final int _cursorIndexOfStartDate = CursorUtil.getColumnIndexOrThrow(_cursor, "start_date");
          final int _cursorIndexOfEndDate = CursorUtil.getColumnIndexOrThrow(_cursor, "end_date");
          final int _cursorIndexOfIsActive = CursorUtil.getColumnIndexOrThrow(_cursor, "is_active");
          final int _cursorIndexOfIsDeleted = CursorUtil.getColumnIndexOrThrow(_cursor, "is_deleted");
          final IncomeSourceEntity _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpCategoryId;
            _tmpCategoryId = _cursor.getLong(_cursorIndexOfCategoryId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final double _tmpDefaultAmount;
            _tmpDefaultAmount = _cursor.getDouble(_cursorIndexOfDefaultAmount);
            final String _tmpRecurrenceType;
            _tmpRecurrenceType = _cursor.getString(_cursorIndexOfRecurrenceType);
            final int _tmpRecurrenceInterval;
            _tmpRecurrenceInterval = _cursor.getInt(_cursorIndexOfRecurrenceInterval);
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
            final boolean _tmpIsDeleted;
            final int _tmp_4;
            _tmp_4 = _cursor.getInt(_cursorIndexOfIsDeleted);
            _tmpIsDeleted = _tmp_4 != 0;
            _result = new IncomeSourceEntity(_tmpId,_tmpCategoryId,_tmpName,_tmpDefaultAmount,_tmpRecurrenceType,_tmpRecurrenceInterval,_tmpStartDate,_tmpEndDate,_tmpIsActive,_tmpIsDeleted);
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
  public Flow<List<IncomeSourceEntity>> getAllSources() {
    final String _sql = "SELECT * FROM income_source WHERE is_deleted = 0 ORDER BY name ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"income_source"}, new Callable<List<IncomeSourceEntity>>() {
      @Override
      @NonNull
      public List<IncomeSourceEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfCategoryId = CursorUtil.getColumnIndexOrThrow(_cursor, "category_id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfDefaultAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "default_amount");
          final int _cursorIndexOfRecurrenceType = CursorUtil.getColumnIndexOrThrow(_cursor, "recurrence_type");
          final int _cursorIndexOfRecurrenceInterval = CursorUtil.getColumnIndexOrThrow(_cursor, "recurrence_interval");
          final int _cursorIndexOfStartDate = CursorUtil.getColumnIndexOrThrow(_cursor, "start_date");
          final int _cursorIndexOfEndDate = CursorUtil.getColumnIndexOrThrow(_cursor, "end_date");
          final int _cursorIndexOfIsActive = CursorUtil.getColumnIndexOrThrow(_cursor, "is_active");
          final int _cursorIndexOfIsDeleted = CursorUtil.getColumnIndexOrThrow(_cursor, "is_deleted");
          final List<IncomeSourceEntity> _result = new ArrayList<IncomeSourceEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final IncomeSourceEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final long _tmpCategoryId;
            _tmpCategoryId = _cursor.getLong(_cursorIndexOfCategoryId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final double _tmpDefaultAmount;
            _tmpDefaultAmount = _cursor.getDouble(_cursorIndexOfDefaultAmount);
            final String _tmpRecurrenceType;
            _tmpRecurrenceType = _cursor.getString(_cursorIndexOfRecurrenceType);
            final int _tmpRecurrenceInterval;
            _tmpRecurrenceInterval = _cursor.getInt(_cursorIndexOfRecurrenceInterval);
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
            final boolean _tmpIsDeleted;
            final int _tmp_4;
            _tmp_4 = _cursor.getInt(_cursorIndexOfIsDeleted);
            _tmpIsDeleted = _tmp_4 != 0;
            _item = new IncomeSourceEntity(_tmpId,_tmpCategoryId,_tmpName,_tmpDefaultAmount,_tmpRecurrenceType,_tmpRecurrenceInterval,_tmpStartDate,_tmpEndDate,_tmpIsActive,_tmpIsDeleted);
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
  public Object getEntryById(final long id,
      final Continuation<? super IncomeEntryEntity> $completion) {
    final String _sql = "SELECT * FROM income_entry WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<IncomeEntryEntity>() {
      @Override
      @Nullable
      public IncomeEntryEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfIncomeSourceId = CursorUtil.getColumnIndexOrThrow(_cursor, "income_source_id");
          final int _cursorIndexOfCategoryId = CursorUtil.getColumnIndexOrThrow(_cursor, "category_id");
          final int _cursorIndexOfAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "amount");
          final int _cursorIndexOfCurrencyCode = CursorUtil.getColumnIndexOrThrow(_cursor, "currency_code");
          final int _cursorIndexOfEntryDate = CursorUtil.getColumnIndexOrThrow(_cursor, "entry_date");
          final int _cursorIndexOfNote = CursorUtil.getColumnIndexOrThrow(_cursor, "note");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
          final IncomeEntryEntity _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final Long _tmpIncomeSourceId;
            if (_cursor.isNull(_cursorIndexOfIncomeSourceId)) {
              _tmpIncomeSourceId = null;
            } else {
              _tmpIncomeSourceId = _cursor.getLong(_cursorIndexOfIncomeSourceId);
            }
            final long _tmpCategoryId;
            _tmpCategoryId = _cursor.getLong(_cursorIndexOfCategoryId);
            final double _tmpAmount;
            _tmpAmount = _cursor.getDouble(_cursorIndexOfAmount);
            final String _tmpCurrencyCode;
            _tmpCurrencyCode = _cursor.getString(_cursorIndexOfCurrencyCode);
            final LocalDate _tmpEntryDate;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfEntryDate)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfEntryDate);
            }
            final LocalDate _tmp_1 = __converters.toLocalDate(_tmp);
            if (_tmp_1 == null) {
              throw new IllegalStateException("Expected NON-NULL 'kotlinx.datetime.LocalDate', but it was NULL.");
            } else {
              _tmpEntryDate = _tmp_1;
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
            _result = new IncomeEntryEntity(_tmpId,_tmpIncomeSourceId,_tmpCategoryId,_tmpAmount,_tmpCurrencyCode,_tmpEntryDate,_tmpNote,_tmpCreatedAt);
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
  public Flow<List<IncomeEntryEntity>> getAllEntries() {
    final String _sql = "SELECT * FROM income_entry ORDER BY entry_date DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"income_entry"}, new Callable<List<IncomeEntryEntity>>() {
      @Override
      @NonNull
      public List<IncomeEntryEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfIncomeSourceId = CursorUtil.getColumnIndexOrThrow(_cursor, "income_source_id");
          final int _cursorIndexOfCategoryId = CursorUtil.getColumnIndexOrThrow(_cursor, "category_id");
          final int _cursorIndexOfAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "amount");
          final int _cursorIndexOfCurrencyCode = CursorUtil.getColumnIndexOrThrow(_cursor, "currency_code");
          final int _cursorIndexOfEntryDate = CursorUtil.getColumnIndexOrThrow(_cursor, "entry_date");
          final int _cursorIndexOfNote = CursorUtil.getColumnIndexOrThrow(_cursor, "note");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
          final List<IncomeEntryEntity> _result = new ArrayList<IncomeEntryEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final IncomeEntryEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final Long _tmpIncomeSourceId;
            if (_cursor.isNull(_cursorIndexOfIncomeSourceId)) {
              _tmpIncomeSourceId = null;
            } else {
              _tmpIncomeSourceId = _cursor.getLong(_cursorIndexOfIncomeSourceId);
            }
            final long _tmpCategoryId;
            _tmpCategoryId = _cursor.getLong(_cursorIndexOfCategoryId);
            final double _tmpAmount;
            _tmpAmount = _cursor.getDouble(_cursorIndexOfAmount);
            final String _tmpCurrencyCode;
            _tmpCurrencyCode = _cursor.getString(_cursorIndexOfCurrencyCode);
            final LocalDate _tmpEntryDate;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfEntryDate)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfEntryDate);
            }
            final LocalDate _tmp_1 = __converters.toLocalDate(_tmp);
            if (_tmp_1 == null) {
              throw new IllegalStateException("Expected NON-NULL 'kotlinx.datetime.LocalDate', but it was NULL.");
            } else {
              _tmpEntryDate = _tmp_1;
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
            _item = new IncomeEntryEntity(_tmpId,_tmpIncomeSourceId,_tmpCategoryId,_tmpAmount,_tmpCurrencyCode,_tmpEntryDate,_tmpNote,_tmpCreatedAt);
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
  public Flow<List<IncomeEntryEntity>> getEntriesByDateRange(final String start, final String end) {
    final String _sql = "SELECT * FROM income_entry WHERE entry_date BETWEEN ? AND ? ORDER BY entry_date DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindString(_argIndex, start);
    _argIndex = 2;
    _statement.bindString(_argIndex, end);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"income_entry"}, new Callable<List<IncomeEntryEntity>>() {
      @Override
      @NonNull
      public List<IncomeEntryEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfIncomeSourceId = CursorUtil.getColumnIndexOrThrow(_cursor, "income_source_id");
          final int _cursorIndexOfCategoryId = CursorUtil.getColumnIndexOrThrow(_cursor, "category_id");
          final int _cursorIndexOfAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "amount");
          final int _cursorIndexOfCurrencyCode = CursorUtil.getColumnIndexOrThrow(_cursor, "currency_code");
          final int _cursorIndexOfEntryDate = CursorUtil.getColumnIndexOrThrow(_cursor, "entry_date");
          final int _cursorIndexOfNote = CursorUtil.getColumnIndexOrThrow(_cursor, "note");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
          final List<IncomeEntryEntity> _result = new ArrayList<IncomeEntryEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final IncomeEntryEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final Long _tmpIncomeSourceId;
            if (_cursor.isNull(_cursorIndexOfIncomeSourceId)) {
              _tmpIncomeSourceId = null;
            } else {
              _tmpIncomeSourceId = _cursor.getLong(_cursorIndexOfIncomeSourceId);
            }
            final long _tmpCategoryId;
            _tmpCategoryId = _cursor.getLong(_cursorIndexOfCategoryId);
            final double _tmpAmount;
            _tmpAmount = _cursor.getDouble(_cursorIndexOfAmount);
            final String _tmpCurrencyCode;
            _tmpCurrencyCode = _cursor.getString(_cursorIndexOfCurrencyCode);
            final LocalDate _tmpEntryDate;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfEntryDate)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfEntryDate);
            }
            final LocalDate _tmp_1 = __converters.toLocalDate(_tmp);
            if (_tmp_1 == null) {
              throw new IllegalStateException("Expected NON-NULL 'kotlinx.datetime.LocalDate', but it was NULL.");
            } else {
              _tmpEntryDate = _tmp_1;
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
            _item = new IncomeEntryEntity(_tmpId,_tmpIncomeSourceId,_tmpCategoryId,_tmpAmount,_tmpCurrencyCode,_tmpEntryDate,_tmpNote,_tmpCreatedAt);
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
  public Flow<List<IncomeEntryEntity>> getEntriesByCategory(final long categoryId) {
    final String _sql = "SELECT * FROM income_entry WHERE category_id = ? ORDER BY entry_date DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, categoryId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"income_entry"}, new Callable<List<IncomeEntryEntity>>() {
      @Override
      @NonNull
      public List<IncomeEntryEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfIncomeSourceId = CursorUtil.getColumnIndexOrThrow(_cursor, "income_source_id");
          final int _cursorIndexOfCategoryId = CursorUtil.getColumnIndexOrThrow(_cursor, "category_id");
          final int _cursorIndexOfAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "amount");
          final int _cursorIndexOfCurrencyCode = CursorUtil.getColumnIndexOrThrow(_cursor, "currency_code");
          final int _cursorIndexOfEntryDate = CursorUtil.getColumnIndexOrThrow(_cursor, "entry_date");
          final int _cursorIndexOfNote = CursorUtil.getColumnIndexOrThrow(_cursor, "note");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "created_at");
          final List<IncomeEntryEntity> _result = new ArrayList<IncomeEntryEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final IncomeEntryEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final Long _tmpIncomeSourceId;
            if (_cursor.isNull(_cursorIndexOfIncomeSourceId)) {
              _tmpIncomeSourceId = null;
            } else {
              _tmpIncomeSourceId = _cursor.getLong(_cursorIndexOfIncomeSourceId);
            }
            final long _tmpCategoryId;
            _tmpCategoryId = _cursor.getLong(_cursorIndexOfCategoryId);
            final double _tmpAmount;
            _tmpAmount = _cursor.getDouble(_cursorIndexOfAmount);
            final String _tmpCurrencyCode;
            _tmpCurrencyCode = _cursor.getString(_cursorIndexOfCurrencyCode);
            final LocalDate _tmpEntryDate;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfEntryDate)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfEntryDate);
            }
            final LocalDate _tmp_1 = __converters.toLocalDate(_tmp);
            if (_tmp_1 == null) {
              throw new IllegalStateException("Expected NON-NULL 'kotlinx.datetime.LocalDate', but it was NULL.");
            } else {
              _tmpEntryDate = _tmp_1;
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
            _item = new IncomeEntryEntity(_tmpId,_tmpIncomeSourceId,_tmpCategoryId,_tmpAmount,_tmpCurrencyCode,_tmpEntryDate,_tmpNote,_tmpCreatedAt);
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
