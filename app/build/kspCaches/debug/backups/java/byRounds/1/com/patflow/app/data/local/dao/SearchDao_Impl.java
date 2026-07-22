package com.patflow.app.data.local.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
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
import com.patflow.app.data.local.entity.BillSearchFtsEntity;
import com.patflow.app.data.local.entity.RecentSearchEntity;
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
public final class SearchDao_Impl implements SearchDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<BillSearchFtsEntity> __insertionAdapterOfBillSearchFtsEntity;

  private final EntityInsertionAdapter<RecentSearchEntity> __insertionAdapterOfRecentSearchEntity;

  private final Converters __converters = new Converters();

  private final EntityDeletionOrUpdateAdapter<RecentSearchEntity> __deletionAdapterOfRecentSearchEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeleteFtsEntry;

  private final SharedSQLiteStatement __preparedStmtOfClearRecentSearches;

  public SearchDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfBillSearchFtsEntity = new EntityInsertionAdapter<BillSearchFtsEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `bill_search_fts` (`billId`,`name`,`notes`,`merchant`,`categoryName`) VALUES (?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final BillSearchFtsEntity entity) {
        statement.bindLong(1, entity.getBillId());
        statement.bindString(2, entity.getName());
        statement.bindString(3, entity.getNotes());
        statement.bindString(4, entity.getMerchant());
        statement.bindString(5, entity.getCategoryName());
      }
    };
    this.__insertionAdapterOfRecentSearchEntity = new EntityInsertionAdapter<RecentSearchEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `recent_search` (`id`,`query_text`,`searched_at`) VALUES (nullif(?, 0),?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final RecentSearchEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getQueryText());
        final String _tmp = __converters.fromLocalDateTime(entity.getSearchedAt());
        if (_tmp == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, _tmp);
        }
      }
    };
    this.__deletionAdapterOfRecentSearchEntity = new EntityDeletionOrUpdateAdapter<RecentSearchEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `recent_search` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final RecentSearchEntity entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__preparedStmtOfDeleteFtsEntry = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM bill_search_fts WHERE billId = ?";
        return _query;
      }
    };
    this.__preparedStmtOfClearRecentSearches = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM recent_search";
        return _query;
      }
    };
  }

  @Override
  public Object upsertFtsEntry(final BillSearchFtsEntity entry,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfBillSearchFtsEntity.insert(entry);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertRecentSearch(final RecentSearchEntity search,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfRecentSearchEntity.insertAndReturnId(search);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteRecentSearch(final RecentSearchEntity search,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfRecentSearchEntity.handle(search);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteFtsEntry(final long billId, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteFtsEntry.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, billId);
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
          __preparedStmtOfDeleteFtsEntry.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object clearRecentSearches(final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfClearRecentSearches.acquire();
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
          __preparedStmtOfClearRecentSearches.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object matchFts(final String query,
      final Continuation<? super List<BillSearchFtsEntity>> $completion) {
    final String _sql = "SELECT * FROM bill_search_fts WHERE bill_search_fts MATCH ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, query);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<BillSearchFtsEntity>>() {
      @Override
      @NonNull
      public List<BillSearchFtsEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfBillId = CursorUtil.getColumnIndexOrThrow(_cursor, "billId");
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfNotes = CursorUtil.getColumnIndexOrThrow(_cursor, "notes");
          final int _cursorIndexOfMerchant = CursorUtil.getColumnIndexOrThrow(_cursor, "merchant");
          final int _cursorIndexOfCategoryName = CursorUtil.getColumnIndexOrThrow(_cursor, "categoryName");
          final List<BillSearchFtsEntity> _result = new ArrayList<BillSearchFtsEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final BillSearchFtsEntity _item;
            final long _tmpBillId;
            _tmpBillId = _cursor.getLong(_cursorIndexOfBillId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            final String _tmpNotes;
            _tmpNotes = _cursor.getString(_cursorIndexOfNotes);
            final String _tmpMerchant;
            _tmpMerchant = _cursor.getString(_cursorIndexOfMerchant);
            final String _tmpCategoryName;
            _tmpCategoryName = _cursor.getString(_cursorIndexOfCategoryName);
            _item = new BillSearchFtsEntity(_tmpBillId,_tmpName,_tmpNotes,_tmpMerchant,_tmpCategoryName);
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
  public Flow<List<RecentSearchEntity>> getRecentSearches(final int limit) {
    final String _sql = "SELECT * FROM recent_search ORDER BY searched_at DESC LIMIT ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, limit);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"recent_search"}, new Callable<List<RecentSearchEntity>>() {
      @Override
      @NonNull
      public List<RecentSearchEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfQueryText = CursorUtil.getColumnIndexOrThrow(_cursor, "query_text");
          final int _cursorIndexOfSearchedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "searched_at");
          final List<RecentSearchEntity> _result = new ArrayList<RecentSearchEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final RecentSearchEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpQueryText;
            _tmpQueryText = _cursor.getString(_cursorIndexOfQueryText);
            final LocalDateTime _tmpSearchedAt;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfSearchedAt)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfSearchedAt);
            }
            final LocalDateTime _tmp_1 = __converters.toLocalDateTime(_tmp);
            if (_tmp_1 == null) {
              throw new IllegalStateException("Expected NON-NULL 'kotlinx.datetime.LocalDateTime', but it was NULL.");
            } else {
              _tmpSearchedAt = _tmp_1;
            }
            _item = new RecentSearchEntity(_tmpId,_tmpQueryText,_tmpSearchedAt);
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
