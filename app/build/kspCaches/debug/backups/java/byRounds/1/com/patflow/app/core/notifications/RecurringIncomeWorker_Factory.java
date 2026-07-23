package com.patflow.app.core.notifications;

import android.content.Context;
import androidx.work.WorkerParameters;
import com.patflow.app.domain.repository.IncomeRepository;
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
public final class RecurringIncomeWorker_Factory {
  private final Provider<IncomeRepository> repositoryProvider;

  public RecurringIncomeWorker_Factory(Provider<IncomeRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  public RecurringIncomeWorker get(Context context, WorkerParameters params) {
    return newInstance(context, params, repositoryProvider.get());
  }

  public static RecurringIncomeWorker_Factory create(
      Provider<IncomeRepository> repositoryProvider) {
    return new RecurringIncomeWorker_Factory(repositoryProvider);
  }

  public static RecurringIncomeWorker newInstance(Context context, WorkerParameters params,
      IncomeRepository repository) {
    return new RecurringIncomeWorker(context, params, repository);
  }
}
