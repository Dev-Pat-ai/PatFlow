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
public final class RecurringIncomeWorker_AssistedFactory_Impl implements RecurringIncomeWorker_AssistedFactory {
  private final RecurringIncomeWorker_Factory delegateFactory;

  RecurringIncomeWorker_AssistedFactory_Impl(RecurringIncomeWorker_Factory delegateFactory) {
    this.delegateFactory = delegateFactory;
  }

  @Override
  public RecurringIncomeWorker create(Context p0, WorkerParameters p1) {
    return delegateFactory.get(p0, p1);
  }

  public static Provider<RecurringIncomeWorker_AssistedFactory> create(
      RecurringIncomeWorker_Factory delegateFactory) {
    return InstanceFactory.create(new RecurringIncomeWorker_AssistedFactory_Impl(delegateFactory));
  }

  public static dagger.internal.Provider<RecurringIncomeWorker_AssistedFactory> createFactoryProvider(
      RecurringIncomeWorker_Factory delegateFactory) {
    return InstanceFactory.create(new RecurringIncomeWorker_AssistedFactory_Impl(delegateFactory));
  }
}
