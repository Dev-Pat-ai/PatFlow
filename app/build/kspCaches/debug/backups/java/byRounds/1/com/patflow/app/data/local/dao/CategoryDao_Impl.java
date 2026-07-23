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
import com.patflow.app.data.local.entity.BillCategoryEntity;
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
public final class CategoryDao_Impl implements CategoryDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<BillCategoryEntity> __insertionAdapterOfBillCategoryEntity;

  private final EntityInsertionAdapter<BillCategoryEntity> __insertionAdapterOfBillCategoryEntity_1;

  private final EntityDeletionOrUpdateAdapter<BillCategoryEntity> __deletionAdapterOfBillCategoryEntity;

  private final EntityDeletionOrUpdateAdapter<BillCategoryEntity> __updateAdapterOfBillCategoryEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAll;

  public CategoryDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfBillCategoryEntity = new EntityInsertionAdapter<BillCategoryEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `bill_category` (`id`,`name`,`icon_key`,`color_hex`,`is_custom`,`is_deleted`,`remote_id`,`sync_status`) VALUES (nullif(?, 0),?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final BillCategoryEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getName());
        statement.bindString(3, entity.getIconKey());
        statement.bindString(4, entity.getColorHex());
        final int _tmp = entity.isCustom() ? 1 : 0;
        statement.bindLong(5, _tmp);
        final int _tmp_1 = entity.isDeleted() ? 1 : 0;
        statement.bindLong(6, _tmp_1);
        if (entity.getRemoteId() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getRemoteId());
        }
        statement.bindString(8, entity.getSyncStatus());
      }
    };
    this.__insertionAdapterOfBillCategoryEntity_1 = new EntityInsertionAdapter<BillCategoryEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `bill_category` (`id`,`name`,`icon_key`,`color_hex`,`is_custom`,`is_deleted`,`remote_id`,`sync_status`) VALUES (nullif(?, 0),?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final BillCategoryEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getName());
        statement.bindString(3, entity.getIconKey());
        statement.bindString(4, entity.getColorHex());
        final int _tmp = entity.isCustom() ? 1 : 0;
        statement.bindLong(5, _tmp);
        final int _tmp_1 = entity.isDeleted() ? 1 : 0;
        statement.bindLong(6, _tmp_1);
        if (entity.getRemoteId() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getRemoteId());
        }
        statement.bindString(8, entity.getSyncStatus());
      }
    };
    this.__deletionAdapterOfBillCategoryEntity = new EntityDeletionOrUpdateAdapter<BillCategoryEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `bill_category` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final BillCategoryEntity entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfBillCategoryEntity = new EntityDeletionOrUpdateAdapter<BillCategoryEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `bill_category` SET `id` = ?,`name` = ?,`icon_key` = ?,`color_hex` = ?,`is_custom` = ?,`is_deleted` = ?,`remote_id` = ?,`sync_status` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final BillCategoryEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getName());
        statement.bindString(3, entity.getIconKey());
        statement.bindString(4, entity.getColorHex());
        final int _tmp = entity.isCustom() ? 1 : 0;
        statement.bindLong(5, _tmp);
        final int _tmp_1 = entity.isDeleted() ? 1 : 0;
        statement.bindLong(6, _tmp_1);
        if (entity.getRemoteId() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getRemoteId());
        }
        statement.bindString(8, entity.getSyncStatus());
        statement.bindLong(9, entity.getId());
      }
    };
    this.__preparedStmtOfDeleteAll = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM bill_category";
        return _query;
      }
    };
  }

  @Override
  public Object insert(final BillCategoryEntity category,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfBillCategoryEntity.insertAndReturnId(category);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertAll(final List<BillCategoryEntity> categories,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfBillCategoryEntity_1.insert(categories);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object delete(final BillCategoryEntity category,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfBillCategoryEntity.handle(category);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object update(final BillCategoryEntity category,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfBillCategoryEntity.handle(category);
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
  public Object getById(final long id, final Continuation<? super BillCategoryEntity> $completion) {
    final String _sql = "SELECT * FROM bill_category WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<BillCategoryEntity>() {
      @Override
      @Nullable
      public BillCategoryEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfIconKey = CursorUtil.getColumnIndexOrThrow(_cursor, "icon_key");
          final int _cursorIndexOfColorHex = CursorUtil.getColumnIndexOrThrow(_cursor, "color_hex");
          final int _cursorIndexOfIsCustom = CursorUtil.getColumnIndexOrThrow(_cursor, "is_custom");
          final int _cursorIndexOfIsDeleted = CursorUtil.getColumnIndexOrThrow(_cursor, "is_deleted");
          final int _cursorIndexOfRemoteId = CursorUtil.getColumnIndexOrThrow(_cursor, "remote_id");
          final int _cursorIndexOfSyncStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "sync_status");
          final BillCategoryEntity _result;
          if (_cursor.moveToFirst()) {
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
            _result = new BillCategoryEntity(_tmpId,_tmpName,_tmpIconKey,_tmpColorHex,_tmpIsCustom,_tmpIsDeleted,_tmpRemoteId,_tmpSyncStatus);
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
  public Flow<List<BillCategoryEntity>> getAll() {
    final String _sql = "SELECT * FROM bill_category WHERE is_deleted = 0 ORDER BY name ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"bill_category"}, new Callable<List<BillCategoryEntity>>() {
      @Override
      @NonNull
      public List<BillCategoryEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfIconKey = CursorUtil.getColumnIndexOrThrow(_cursor, "icon_key");
          final int _cursorIndexOfColorHex = CursorUtil.getColumnIndexOrThrow(_cursor, "color_hex");
          final int _cursorIndexOfIsCustom = CursorUtil.getColumnIndexOrThrow(_cursor, "is_custom");
          final int _cursorIndexOfIsDeleted = CursorUtil.getColumnIndexOrThrow(_cursor, "is_deleted");
          final int _cursorIndexOfRemoteId = CursorUtil.getColumnIndexOrThrow(_cursor, "remote_id");
          final int _cursorIndexOfSyncStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "sync_status");
          final List<BillCategoryEntity> _result = new ArrayList<BillCategoryEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final BillCategoryEntity _item;
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
            _item = new BillCategoryEntity(_tmpId,_tmpName,_tmpIconKey,_tmpColorHex,_tmpIsCustom,_tmpIsDeleted,_tmpRemoteId,_tmpSyncStatus);
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
  public Object getAllEntities(final Continuation<? super List<BillCategoryEntity>> $completion) {
    final String _sql = "SELECT * FROM bill_category";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<BillCategoryEntity>>() {
      @Override
      @NonNull
      public List<BillCategoryEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfIconKey = CursorUtil.getColumnIndexOrThrow(_cursor, "icon_key");
          final int _cursorIndexOfColorHex = CursorUtil.getColumnIndexOrThrow(_cursor, "color_hex");
          final int _cursorIndexOfIsCustom = CursorUtil.getColumnIndexOrThrow(_cursor, "is_custom");
          final int _cursorIndexOfIsDeleted = CursorUtil.getColumnIndexOrThrow(_cursor, "is_deleted");
          final int _cursorIndexOfRemoteId = CursorUtil.getColumnIndexOrThrow(_cursor, "remote_id");
          final int _cursorIndexOfSyncStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "sync_status");
          final List<BillCategoryEntity> _result = new ArrayList<BillCategoryEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final BillCategoryEntity _item;
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
            _item = new BillCategoryEntity(_tmpId,_tmpName,_tmpIconKey,_tmpColorHex,_tmpIsCustom,_tmpIsDeleted,_tmpRemoteId,_tmpSyncStatus);
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
