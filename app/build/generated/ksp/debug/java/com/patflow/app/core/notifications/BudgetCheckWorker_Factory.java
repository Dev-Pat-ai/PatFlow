package com.patflow.app.core.notifications;

import android.content.Context;
import androidx.work.WorkerParameters;
import com.patflow.app.domain.repository.BudgetRepository;
import com.patflow.app.domain.repository.NotificationRepository;
import com.patflow.app.domain.usecase.budget.GetBudgetAnalyticsUseCase;
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
public final class BudgetCheckWorker_Factory {
  private final Provider<BudgetRepository> budgetRepositoryProvider;

  private final Provider<GetBudgetAnalyticsUseCase> getBudgetAnalyticsUseCaseProvider;

  private final Provider<NotificationRepository> notificationRepositoryProvider;

  public BudgetCheckWorker_Factory(Provider<BudgetRepository> budgetRepositoryProvider,
      Provider<GetBudgetAnalyticsUseCase> getBudgetAnalyticsUseCaseProvider,
      Provider<NotificationRepository> notificationRepositoryProvider) {
    this.budgetRepositoryProvider = budgetRepositoryProvider;
    this.getBudgetAnalyticsUseCaseProvider = getBudgetAnalyticsUseCaseProvider;
    this.notificationRepositoryProvider = notificationRepositoryProvider;
  }

  public BudgetCheckWorker get(Context context, WorkerParameters params) {
    return newInstance(context, params, budgetRepositoryProvider.get(), getBudgetAnalyticsUseCaseProvider.get(), notificationRepositoryProvider.get());
  }

  public static BudgetCheckWorker_Factory create(
      Provider<BudgetRepository> budgetRepositoryProvider,
      Provider<GetBudgetAnalyticsUseCase> getBudgetAnalyticsUseCaseProvider,
      Provider<NotificationRepository> notificationRepositoryProvider) {
    return new BudgetCheckWorker_Factory(budgetRepositoryProvider, getBudgetAnalyticsUseCaseProvider, notificationRepositoryProvider);
  }

  public static BudgetCheckWorker newInstance(Context context, WorkerParameters params,
      BudgetRepository budgetRepository, GetBudgetAnalyticsUseCase getBudgetAnalyticsUseCase,
      NotificationRepository notificationRepository) {
    return new BudgetCheckWorker(context, params, budgetRepository, getBudgetAnalyticsUseCase, notificationRepository);
  }
}
