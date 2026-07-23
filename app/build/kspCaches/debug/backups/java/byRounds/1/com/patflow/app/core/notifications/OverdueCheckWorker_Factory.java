package com.patflow.app.core.notifications;

import android.content.Context;
import androidx.work.WorkerParameters;
import com.patflow.app.domain.repository.BillRepository;
import com.patflow.app.domain.repository.NotificationRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast",
    "deprecation"
})
public final class OverdueCheckWorker_Factory {
  private final Provider<BillRepository> billRepositoryProvider;

  private final Provider<NotificationRepository> notificationRepositoryProvider;

  public OverdueCheckWorker_Factory(Provider<BillRepository> billRepositoryProvider,
      Provider<NotificationRepository> notificationRepositoryProvider) {
    this.billRepositoryProvider = billRepositoryProvider;
    this.notificationRepositoryProvider = notificationRepositoryProvider;
  }

  public OverdueCheckWorker get(Context context, WorkerParameters params) {
    return newInstance(context, params, billRepositoryProvider.get(), notificationRepositoryProvider.get());
  }

  public static OverdueCheckWorker_Factory create(Provider<BillRepository> billRepositoryProvider,
      Provider<NotificationRepository> notificationRepositoryProvider) {
    return new OverdueCheckWorker_Factory(billRepositoryProvider, notificationRepositoryProvider);
  }

  public static OverdueCheckWorker newInstance(Context context, WorkerParameters params,
      BillRepository billRepository, NotificationRepository notificationRepository) {
    return new OverdueCheckWorker(context, params, billRepository, notificationRepository);
  }
}
