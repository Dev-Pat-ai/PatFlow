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
public final class OverdueCheckWorker_AssistedFactory_Impl implements OverdueCheckWorker_AssistedFactory {
  private final OverdueCheckWorker_Factory delegateFactory;

  OverdueCheckWorker_AssistedFactory_Impl(OverdueCheckWorker_Factory delegateFactory) {
    this.delegateFactory = delegateFactory;
  }

  @Override
  public OverdueCheckWorker create(Context p0, WorkerParameters p1) {
    return delegateFactory.get(p0, p1);
  }

  public static Provider<OverdueCheckWorker_AssistedFactory> create(
      OverdueCheckWorker_Factory delegateFactory) {
    return InstanceFactory.create(new OverdueCheckWorker_AssistedFactory_Impl(delegateFactory));
  }

  public static dagger.internal.Provider<OverdueCheckWorker_AssistedFactory> createFactoryProvider(
      OverdueCheckWorker_Factory delegateFactory) {
    return InstanceFactory.create(new OverdueCheckWorker_AssistedFactory_Impl(delegateFactory));
  }
}
