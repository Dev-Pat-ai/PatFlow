package com.patflow.app.core.notifications;

import android.content.Context;
import androidx.work.WorkerParameters;
import dagger.internal.DaggerGenerated;
import dagger.internal.InstanceFactory;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class ReminderSyncWorker_AssistedFactory_Impl implements ReminderSyncWorker_AssistedFactory {
  private final ReminderSyncWorker_Factory delegateFactory;

  ReminderSyncWorker_AssistedFactory_Impl(ReminderSyncWorker_Factory delegateFactory) {
    this.delegateFactory = delegateFactory;
  }

  @Override
  public ReminderSyncWorker create(Context p0, WorkerParameters p1) {
    return delegateFactory.get(p0, p1);
  }

  public static Provider<ReminderSyncWorker_AssistedFactory> create(
      ReminderSyncWorker_Factory delegateFactory) {
    return InstanceFactory.create(new ReminderSyncWorker_AssistedFactory_Impl(delegateFactory));
  }

  public static dagger.internal.Provider<ReminderSyncWorker_AssistedFactory> createFactoryProvider(
      ReminderSyncWorker_Factory delegateFactory) {
    return InstanceFactory.create(new ReminderSyncWorker_AssistedFactory_Impl(delegateFactory));
  }
}
