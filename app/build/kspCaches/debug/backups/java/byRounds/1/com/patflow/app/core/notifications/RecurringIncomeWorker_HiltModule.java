package com.patflow.app.core.notifications;

import androidx.hilt.work.WorkerAssistedFactory;
import androidx.work.ListenableWorker;
import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.codegen.OriginatingElement;
import dagger.hilt.components.SingletonComponent;
import dagger.multibindings.IntoMap;
import dagger.multibindings.StringKey;
import javax.annotation.processing.Generated;

@Generated("androidx.hilt.AndroidXHiltProcessor")
@Module
@InstallIn(SingletonComponent.class)
@OriginatingElement(
    topLevelClass = RecurringIncomeWorker.class
)
public interface RecurringIncomeWorker_HiltModule {
  @Binds
  @IntoMap
  @StringKey("com.patflow.app.core.notifications.RecurringIncomeWorker")
  WorkerAssistedFactory<? extends ListenableWorker> bind(
      RecurringIncomeWorker_AssistedFactory factory);
}
