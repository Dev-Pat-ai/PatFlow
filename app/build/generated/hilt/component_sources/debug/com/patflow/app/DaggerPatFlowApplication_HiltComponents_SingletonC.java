package com.patflow.app;

import android.app.Activity;
import android.app.Service;
import android.view.View;
import androidx.datastore.core.DataStore;
import androidx.datastore.preferences.core.Preferences;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import androidx.work.WorkManager;
import com.patflow.app.core.notifications.NotificationReceiver;
import com.patflow.app.core.notifications.NotificationReceiver_MembersInjector;
import com.patflow.app.core.notifications.NotificationScheduler;
import com.patflow.app.core.theme.ThemeViewModel;
import com.patflow.app.core.theme.ThemeViewModel_HiltModules;
import com.patflow.app.core.utils.HapticViewModel;
import com.patflow.app.core.utils.HapticViewModel_HiltModules;
import com.patflow.app.data.local.dao.BillCycleDao;
import com.patflow.app.data.local.dao.BillDao;
import com.patflow.app.data.local.dao.BudgetDao;
import com.patflow.app.data.local.dao.CategoryDao;
import com.patflow.app.data.local.dao.IncomeDao;
import com.patflow.app.data.local.dao.PaymentDao;
import com.patflow.app.data.local.dao.ReminderDao;
import com.patflow.app.data.local.dao.SavingsGoalDao;
import com.patflow.app.data.local.database.DatabaseInitializer;
import com.patflow.app.data.local.database.PatFlowDatabase;
import com.patflow.app.data.repository.BillRepositoryImpl;
import com.patflow.app.data.repository.BudgetRepositoryImpl;
import com.patflow.app.data.repository.CategoryRepositoryImpl;
import com.patflow.app.data.repository.DataManagementRepositoryImpl;
import com.patflow.app.data.repository.IncomeRepositoryImpl;
import com.patflow.app.data.repository.NotificationRepositoryImpl;
import com.patflow.app.data.repository.PaymentRepositoryImpl;
import com.patflow.app.data.repository.ReminderRepositoryImpl;
import com.patflow.app.data.repository.SavingsGoalRepositoryImpl;
import com.patflow.app.data.repository.SettingsRepositoryImpl;
import com.patflow.app.di.DataStoreModule_ProvidePreferencesDataStoreFactory;
import com.patflow.app.di.DatabaseModule_ProvideBillCycleDaoFactory;
import com.patflow.app.di.DatabaseModule_ProvideBillDaoFactory;
import com.patflow.app.di.DatabaseModule_ProvideBudgetDaoFactory;
import com.patflow.app.di.DatabaseModule_ProvideCategoryDaoFactory;
import com.patflow.app.di.DatabaseModule_ProvideDatabaseFactory;
import com.patflow.app.di.DatabaseModule_ProvideIncomeDaoFactory;
import com.patflow.app.di.DatabaseModule_ProvidePaymentDaoFactory;
import com.patflow.app.di.DatabaseModule_ProvideReminderDaoFactory;
import com.patflow.app.di.DatabaseModule_ProvideSavingsGoalDaoFactory;
import com.patflow.app.di.DatabaseModule_ProvideWorkManagerFactory;
import com.patflow.app.domain.repository.BillRepository;
import com.patflow.app.domain.repository.BudgetRepository;
import com.patflow.app.domain.repository.CategoryRepository;
import com.patflow.app.domain.repository.DataManagementRepository;
import com.patflow.app.domain.repository.IncomeRepository;
import com.patflow.app.domain.repository.PaymentRepository;
import com.patflow.app.domain.repository.ReminderRepository;
import com.patflow.app.domain.repository.SavingsGoalRepository;
import com.patflow.app.domain.repository.SettingsRepository;
import com.patflow.app.domain.usecase.bill.AddBillUseCase;
import com.patflow.app.domain.usecase.bill.DeleteBillUseCase;
import com.patflow.app.domain.usecase.bill.GetBillDetailUseCase;
import com.patflow.app.domain.usecase.bill.GetBillsUseCase;
import com.patflow.app.domain.usecase.bill.MarkBillAsPaidUseCase;
import com.patflow.app.domain.usecase.bill.UpdateBillUseCase;
import com.patflow.app.domain.usecase.budget.AddBudgetUseCase;
import com.patflow.app.domain.usecase.budget.ArchiveBudgetUseCase;
import com.patflow.app.domain.usecase.budget.DeleteBudgetUseCase;
import com.patflow.app.domain.usecase.budget.GetBudgetAnalyticsUseCase;
import com.patflow.app.domain.usecase.budget.GetBudgetsUseCase;
import com.patflow.app.domain.usecase.budget.UpdateBudgetUseCase;
import com.patflow.app.domain.usecase.dashboard.GetDashboardDataUseCase;
import com.patflow.app.domain.usecase.datamanagement.CreateBackupUseCase;
import com.patflow.app.domain.usecase.datamanagement.ExportCsvUseCase;
import com.patflow.app.domain.usecase.datamanagement.RestoreBackupUseCase;
import com.patflow.app.domain.usecase.income.DeleteIncomeEntryUseCase;
import com.patflow.app.domain.usecase.income.DuplicateIncomeEntryUseCase;
import com.patflow.app.domain.usecase.income.GetIncomeEntriesUseCase;
import com.patflow.app.domain.usecase.insights.GetSmartInsightsUseCase;
import com.patflow.app.domain.usecase.payment.GetPaymentDetailUseCase;
import com.patflow.app.domain.usecase.payment.GetPaymentsUseCase;
import com.patflow.app.domain.usecase.payment.LogPaymentUseCase;
import com.patflow.app.domain.usecase.payment.UndoPaymentUseCase;
import com.patflow.app.domain.usecase.report.GetReportDataUseCase;
import com.patflow.app.domain.usecase.savings.AddSavingsContributionUseCase;
import com.patflow.app.domain.usecase.savings.AddSavingsGoalUseCase;
import com.patflow.app.domain.usecase.savings.DeleteSavingsGoalUseCase;
import com.patflow.app.domain.usecase.savings.GetSavingsGoalAnalyticsUseCase;
import com.patflow.app.domain.usecase.savings.GetSavingsGoalsUseCase;
import com.patflow.app.domain.usecase.savings.UpdateSavingsGoalUseCase;
import com.patflow.app.domain.usecase.settings.GetUserSettingsUseCase;
import com.patflow.app.domain.usecase.settings.UpdateUserPreferenceUseCase;
import com.patflow.app.feature.bills.AddEditBillViewModel;
import com.patflow.app.feature.bills.AddEditBillViewModel_HiltModules;
import com.patflow.app.feature.bills.BillDetailViewModel;
import com.patflow.app.feature.bills.BillDetailViewModel_HiltModules;
import com.patflow.app.feature.bills.BillListViewModel;
import com.patflow.app.feature.bills.BillListViewModel_HiltModules;
import com.patflow.app.feature.budget.AddEditBudgetViewModel;
import com.patflow.app.feature.budget.AddEditBudgetViewModel_HiltModules;
import com.patflow.app.feature.budget.BudgetDetailViewModel;
import com.patflow.app.feature.budget.BudgetDetailViewModel_HiltModules;
import com.patflow.app.feature.budget.BudgetViewModel;
import com.patflow.app.feature.budget.BudgetViewModel_HiltModules;
import com.patflow.app.feature.calendar.CalendarViewModel;
import com.patflow.app.feature.calendar.CalendarViewModel_HiltModules;
import com.patflow.app.feature.dashboard.DashboardViewModel;
import com.patflow.app.feature.dashboard.DashboardViewModel_HiltModules;
import com.patflow.app.feature.income.AddEditIncomeSourceViewModel;
import com.patflow.app.feature.income.AddEditIncomeSourceViewModel_HiltModules;
import com.patflow.app.feature.income.AddEditIncomeViewModel;
import com.patflow.app.feature.income.AddEditIncomeViewModel_HiltModules;
import com.patflow.app.feature.income.IncomeSourceListViewModel;
import com.patflow.app.feature.income.IncomeSourceListViewModel_HiltModules;
import com.patflow.app.feature.income.IncomeViewModel;
import com.patflow.app.feature.income.IncomeViewModel_HiltModules;
import com.patflow.app.feature.payment.PaymentDetailViewModel;
import com.patflow.app.feature.payment.PaymentDetailViewModel_HiltModules;
import com.patflow.app.feature.payment.PaymentHistoryViewModel;
import com.patflow.app.feature.payment.PaymentHistoryViewModel_HiltModules;
import com.patflow.app.feature.reports.ReportsViewModel;
import com.patflow.app.feature.reports.ReportsViewModel_HiltModules;
import com.patflow.app.feature.savings.AddEditSavingsGoalViewModel;
import com.patflow.app.feature.savings.AddEditSavingsGoalViewModel_HiltModules;
import com.patflow.app.feature.savings.SavingsGoalDetailViewModel;
import com.patflow.app.feature.savings.SavingsGoalDetailViewModel_HiltModules;
import com.patflow.app.feature.savings.SavingsGoalViewModel;
import com.patflow.app.feature.savings.SavingsGoalViewModel_HiltModules;
import com.patflow.app.feature.settings.DataManagementViewModel;
import com.patflow.app.feature.settings.DataManagementViewModel_HiltModules;
import com.patflow.app.feature.settings.SettingsViewModel;
import com.patflow.app.feature.settings.SettingsViewModel_HiltModules;
import dagger.hilt.android.ActivityRetainedLifecycle;
import dagger.hilt.android.ViewModelLifecycle;
import dagger.hilt.android.internal.builders.ActivityComponentBuilder;
import dagger.hilt.android.internal.builders.ActivityRetainedComponentBuilder;
import dagger.hilt.android.internal.builders.FragmentComponentBuilder;
import dagger.hilt.android.internal.builders.ServiceComponentBuilder;
import dagger.hilt.android.internal.builders.ViewComponentBuilder;
import dagger.hilt.android.internal.builders.ViewModelComponentBuilder;
import dagger.hilt.android.internal.builders.ViewWithFragmentComponentBuilder;
import dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories;
import dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories_InternalFactoryFactory_Factory;
import dagger.hilt.android.internal.managers.ActivityRetainedComponentManager_LifecycleModule_ProvideActivityRetainedLifecycleFactory;
import dagger.hilt.android.internal.managers.SavedStateHandleHolder;
import dagger.hilt.android.internal.modules.ApplicationContextModule;
import dagger.hilt.android.internal.modules.ApplicationContextModule_ProvideContextFactory;
import dagger.internal.DaggerGenerated;
import dagger.internal.DoubleCheck;
import dagger.internal.IdentifierNameString;
import dagger.internal.KeepFieldType;
import dagger.internal.LazyClassKeyMap;
import dagger.internal.MapBuilder;
import dagger.internal.Preconditions;
import dagger.internal.Provider;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

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
public final class DaggerPatFlowApplication_HiltComponents_SingletonC {
  private DaggerPatFlowApplication_HiltComponents_SingletonC() {
  }

  public static Builder builder() {
    return new Builder();
  }

  public static final class Builder {
    private ApplicationContextModule applicationContextModule;

    private Builder() {
    }

    public Builder applicationContextModule(ApplicationContextModule applicationContextModule) {
      this.applicationContextModule = Preconditions.checkNotNull(applicationContextModule);
      return this;
    }

    public PatFlowApplication_HiltComponents.SingletonC build() {
      Preconditions.checkBuilderRequirement(applicationContextModule, ApplicationContextModule.class);
      return new SingletonCImpl(applicationContextModule);
    }
  }

  private static final class ActivityRetainedCBuilder implements PatFlowApplication_HiltComponents.ActivityRetainedC.Builder {
    private final SingletonCImpl singletonCImpl;

    private SavedStateHandleHolder savedStateHandleHolder;

    private ActivityRetainedCBuilder(SingletonCImpl singletonCImpl) {
      this.singletonCImpl = singletonCImpl;
    }

    @Override
    public ActivityRetainedCBuilder savedStateHandleHolder(
        SavedStateHandleHolder savedStateHandleHolder) {
      this.savedStateHandleHolder = Preconditions.checkNotNull(savedStateHandleHolder);
      return this;
    }

    @Override
    public PatFlowApplication_HiltComponents.ActivityRetainedC build() {
      Preconditions.checkBuilderRequirement(savedStateHandleHolder, SavedStateHandleHolder.class);
      return new ActivityRetainedCImpl(singletonCImpl, savedStateHandleHolder);
    }
  }

  private static final class ActivityCBuilder implements PatFlowApplication_HiltComponents.ActivityC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private Activity activity;

    private ActivityCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
    }

    @Override
    public ActivityCBuilder activity(Activity activity) {
      this.activity = Preconditions.checkNotNull(activity);
      return this;
    }

    @Override
    public PatFlowApplication_HiltComponents.ActivityC build() {
      Preconditions.checkBuilderRequirement(activity, Activity.class);
      return new ActivityCImpl(singletonCImpl, activityRetainedCImpl, activity);
    }
  }

  private static final class FragmentCBuilder implements PatFlowApplication_HiltComponents.FragmentC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private Fragment fragment;

    private FragmentCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
    }

    @Override
    public FragmentCBuilder fragment(Fragment fragment) {
      this.fragment = Preconditions.checkNotNull(fragment);
      return this;
    }

    @Override
    public PatFlowApplication_HiltComponents.FragmentC build() {
      Preconditions.checkBuilderRequirement(fragment, Fragment.class);
      return new FragmentCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, fragment);
    }
  }

  private static final class ViewWithFragmentCBuilder implements PatFlowApplication_HiltComponents.ViewWithFragmentC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl;

    private View view;

    private ViewWithFragmentCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        FragmentCImpl fragmentCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
      this.fragmentCImpl = fragmentCImpl;
    }

    @Override
    public ViewWithFragmentCBuilder view(View view) {
      this.view = Preconditions.checkNotNull(view);
      return this;
    }

    @Override
    public PatFlowApplication_HiltComponents.ViewWithFragmentC build() {
      Preconditions.checkBuilderRequirement(view, View.class);
      return new ViewWithFragmentCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, fragmentCImpl, view);
    }
  }

  private static final class ViewCBuilder implements PatFlowApplication_HiltComponents.ViewC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private View view;

    private ViewCBuilder(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
        ActivityCImpl activityCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
    }

    @Override
    public ViewCBuilder view(View view) {
      this.view = Preconditions.checkNotNull(view);
      return this;
    }

    @Override
    public PatFlowApplication_HiltComponents.ViewC build() {
      Preconditions.checkBuilderRequirement(view, View.class);
      return new ViewCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, view);
    }
  }

  private static final class ViewModelCBuilder implements PatFlowApplication_HiltComponents.ViewModelC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private SavedStateHandle savedStateHandle;

    private ViewModelLifecycle viewModelLifecycle;

    private ViewModelCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
    }

    @Override
    public ViewModelCBuilder savedStateHandle(SavedStateHandle handle) {
      this.savedStateHandle = Preconditions.checkNotNull(handle);
      return this;
    }

    @Override
    public ViewModelCBuilder viewModelLifecycle(ViewModelLifecycle viewModelLifecycle) {
      this.viewModelLifecycle = Preconditions.checkNotNull(viewModelLifecycle);
      return this;
    }

    @Override
    public PatFlowApplication_HiltComponents.ViewModelC build() {
      Preconditions.checkBuilderRequirement(savedStateHandle, SavedStateHandle.class);
      Preconditions.checkBuilderRequirement(viewModelLifecycle, ViewModelLifecycle.class);
      return new ViewModelCImpl(singletonCImpl, activityRetainedCImpl, savedStateHandle, viewModelLifecycle);
    }
  }

  private static final class ServiceCBuilder implements PatFlowApplication_HiltComponents.ServiceC.Builder {
    private final SingletonCImpl singletonCImpl;

    private Service service;

    private ServiceCBuilder(SingletonCImpl singletonCImpl) {
      this.singletonCImpl = singletonCImpl;
    }

    @Override
    public ServiceCBuilder service(Service service) {
      this.service = Preconditions.checkNotNull(service);
      return this;
    }

    @Override
    public PatFlowApplication_HiltComponents.ServiceC build() {
      Preconditions.checkBuilderRequirement(service, Service.class);
      return new ServiceCImpl(singletonCImpl, service);
    }
  }

  private static final class ViewWithFragmentCImpl extends PatFlowApplication_HiltComponents.ViewWithFragmentC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl;

    private final ViewWithFragmentCImpl viewWithFragmentCImpl = this;

    private ViewWithFragmentCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        FragmentCImpl fragmentCImpl, View viewParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
      this.fragmentCImpl = fragmentCImpl;


    }
  }

  private static final class FragmentCImpl extends PatFlowApplication_HiltComponents.FragmentC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl = this;

    private FragmentCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        Fragment fragmentParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;


    }

    @Override
    public DefaultViewModelFactories.InternalFactoryFactory getHiltInternalFactoryFactory() {
      return activityCImpl.getHiltInternalFactoryFactory();
    }

    @Override
    public ViewWithFragmentComponentBuilder viewWithFragmentComponentBuilder() {
      return new ViewWithFragmentCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl, fragmentCImpl);
    }
  }

  private static final class ViewCImpl extends PatFlowApplication_HiltComponents.ViewC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final ViewCImpl viewCImpl = this;

    private ViewCImpl(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
        ActivityCImpl activityCImpl, View viewParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;


    }
  }

  private static final class ActivityCImpl extends PatFlowApplication_HiltComponents.ActivityC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl = this;

    private ActivityCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, Activity activityParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;


    }

    @Override
    public void injectMainActivity(MainActivity mainActivity) {
      injectMainActivity2(mainActivity);
    }

    @Override
    public DefaultViewModelFactories.InternalFactoryFactory getHiltInternalFactoryFactory() {
      return DefaultViewModelFactories_InternalFactoryFactory_Factory.newInstance(getViewModelKeys(), new ViewModelCBuilder(singletonCImpl, activityRetainedCImpl));
    }

    @Override
    public Map<Class<?>, Boolean> getViewModelKeys() {
      return LazyClassKeyMap.<Boolean>of(MapBuilder.<String, Boolean>newMapBuilder(22).put(LazyClassKeyProvider.com_patflow_app_feature_bills_AddEditBillViewModel, AddEditBillViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_patflow_app_feature_budget_AddEditBudgetViewModel, AddEditBudgetViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_patflow_app_feature_income_AddEditIncomeSourceViewModel, AddEditIncomeSourceViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_patflow_app_feature_income_AddEditIncomeViewModel, AddEditIncomeViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_patflow_app_feature_savings_AddEditSavingsGoalViewModel, AddEditSavingsGoalViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_patflow_app_feature_bills_BillDetailViewModel, BillDetailViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_patflow_app_feature_bills_BillListViewModel, BillListViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_patflow_app_feature_budget_BudgetDetailViewModel, BudgetDetailViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_patflow_app_feature_budget_BudgetViewModel, BudgetViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_patflow_app_feature_calendar_CalendarViewModel, CalendarViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_patflow_app_feature_dashboard_DashboardViewModel, DashboardViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_patflow_app_feature_settings_DataManagementViewModel, DataManagementViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_patflow_app_core_utils_HapticViewModel, HapticViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_patflow_app_feature_income_IncomeSourceListViewModel, IncomeSourceListViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_patflow_app_feature_income_IncomeViewModel, IncomeViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_patflow_app_feature_payment_PaymentDetailViewModel, PaymentDetailViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_patflow_app_feature_payment_PaymentHistoryViewModel, PaymentHistoryViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_patflow_app_feature_reports_ReportsViewModel, ReportsViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_patflow_app_feature_savings_SavingsGoalDetailViewModel, SavingsGoalDetailViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_patflow_app_feature_savings_SavingsGoalViewModel, SavingsGoalViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_patflow_app_feature_settings_SettingsViewModel, SettingsViewModel_HiltModules.KeyModule.provide()).put(LazyClassKeyProvider.com_patflow_app_core_theme_ThemeViewModel, ThemeViewModel_HiltModules.KeyModule.provide()).build());
    }

    @Override
    public ViewModelComponentBuilder getViewModelComponentBuilder() {
      return new ViewModelCBuilder(singletonCImpl, activityRetainedCImpl);
    }

    @Override
    public FragmentComponentBuilder fragmentComponentBuilder() {
      return new FragmentCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl);
    }

    @Override
    public ViewComponentBuilder viewComponentBuilder() {
      return new ViewCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl);
    }

    private MainActivity injectMainActivity2(MainActivity instance) {
      MainActivity_MembersInjector.injectDatabaseInitializer(instance, singletonCImpl.databaseInitializerProvider.get());
      MainActivity_MembersInjector.injectNotificationScheduler(instance, singletonCImpl.notificationSchedulerProvider.get());
      return instance;
    }

    @IdentifierNameString
    private static final class LazyClassKeyProvider {
      static String com_patflow_app_feature_income_AddEditIncomeViewModel = "com.patflow.app.feature.income.AddEditIncomeViewModel";

      static String com_patflow_app_feature_payment_PaymentHistoryViewModel = "com.patflow.app.feature.payment.PaymentHistoryViewModel";

      static String com_patflow_app_feature_reports_ReportsViewModel = "com.patflow.app.feature.reports.ReportsViewModel";

      static String com_patflow_app_feature_payment_PaymentDetailViewModel = "com.patflow.app.feature.payment.PaymentDetailViewModel";

      static String com_patflow_app_feature_budget_BudgetViewModel = "com.patflow.app.feature.budget.BudgetViewModel";

      static String com_patflow_app_feature_savings_AddEditSavingsGoalViewModel = "com.patflow.app.feature.savings.AddEditSavingsGoalViewModel";

      static String com_patflow_app_feature_income_AddEditIncomeSourceViewModel = "com.patflow.app.feature.income.AddEditIncomeSourceViewModel";

      static String com_patflow_app_feature_calendar_CalendarViewModel = "com.patflow.app.feature.calendar.CalendarViewModel";

      static String com_patflow_app_feature_savings_SavingsGoalDetailViewModel = "com.patflow.app.feature.savings.SavingsGoalDetailViewModel";

      static String com_patflow_app_feature_budget_AddEditBudgetViewModel = "com.patflow.app.feature.budget.AddEditBudgetViewModel";

      static String com_patflow_app_feature_settings_DataManagementViewModel = "com.patflow.app.feature.settings.DataManagementViewModel";

      static String com_patflow_app_feature_settings_SettingsViewModel = "com.patflow.app.feature.settings.SettingsViewModel";

      static String com_patflow_app_feature_dashboard_DashboardViewModel = "com.patflow.app.feature.dashboard.DashboardViewModel";

      static String com_patflow_app_feature_bills_BillDetailViewModel = "com.patflow.app.feature.bills.BillDetailViewModel";

      static String com_patflow_app_core_utils_HapticViewModel = "com.patflow.app.core.utils.HapticViewModel";

      static String com_patflow_app_feature_bills_BillListViewModel = "com.patflow.app.feature.bills.BillListViewModel";

      static String com_patflow_app_feature_income_IncomeSourceListViewModel = "com.patflow.app.feature.income.IncomeSourceListViewModel";

      static String com_patflow_app_core_theme_ThemeViewModel = "com.patflow.app.core.theme.ThemeViewModel";

      static String com_patflow_app_feature_savings_SavingsGoalViewModel = "com.patflow.app.feature.savings.SavingsGoalViewModel";

      static String com_patflow_app_feature_bills_AddEditBillViewModel = "com.patflow.app.feature.bills.AddEditBillViewModel";

      static String com_patflow_app_feature_income_IncomeViewModel = "com.patflow.app.feature.income.IncomeViewModel";

      static String com_patflow_app_feature_budget_BudgetDetailViewModel = "com.patflow.app.feature.budget.BudgetDetailViewModel";

      @KeepFieldType
      AddEditIncomeViewModel com_patflow_app_feature_income_AddEditIncomeViewModel2;

      @KeepFieldType
      PaymentHistoryViewModel com_patflow_app_feature_payment_PaymentHistoryViewModel2;

      @KeepFieldType
      ReportsViewModel com_patflow_app_feature_reports_ReportsViewModel2;

      @KeepFieldType
      PaymentDetailViewModel com_patflow_app_feature_payment_PaymentDetailViewModel2;

      @KeepFieldType
      BudgetViewModel com_patflow_app_feature_budget_BudgetViewModel2;

      @KeepFieldType
      AddEditSavingsGoalViewModel com_patflow_app_feature_savings_AddEditSavingsGoalViewModel2;

      @KeepFieldType
      AddEditIncomeSourceViewModel com_patflow_app_feature_income_AddEditIncomeSourceViewModel2;

      @KeepFieldType
      CalendarViewModel com_patflow_app_feature_calendar_CalendarViewModel2;

      @KeepFieldType
      SavingsGoalDetailViewModel com_patflow_app_feature_savings_SavingsGoalDetailViewModel2;

      @KeepFieldType
      AddEditBudgetViewModel com_patflow_app_feature_budget_AddEditBudgetViewModel2;

      @KeepFieldType
      DataManagementViewModel com_patflow_app_feature_settings_DataManagementViewModel2;

      @KeepFieldType
      SettingsViewModel com_patflow_app_feature_settings_SettingsViewModel2;

      @KeepFieldType
      DashboardViewModel com_patflow_app_feature_dashboard_DashboardViewModel2;

      @KeepFieldType
      BillDetailViewModel com_patflow_app_feature_bills_BillDetailViewModel2;

      @KeepFieldType
      HapticViewModel com_patflow_app_core_utils_HapticViewModel2;

      @KeepFieldType
      BillListViewModel com_patflow_app_feature_bills_BillListViewModel2;

      @KeepFieldType
      IncomeSourceListViewModel com_patflow_app_feature_income_IncomeSourceListViewModel2;

      @KeepFieldType
      ThemeViewModel com_patflow_app_core_theme_ThemeViewModel2;

      @KeepFieldType
      SavingsGoalViewModel com_patflow_app_feature_savings_SavingsGoalViewModel2;

      @KeepFieldType
      AddEditBillViewModel com_patflow_app_feature_bills_AddEditBillViewModel2;

      @KeepFieldType
      IncomeViewModel com_patflow_app_feature_income_IncomeViewModel2;

      @KeepFieldType
      BudgetDetailViewModel com_patflow_app_feature_budget_BudgetDetailViewModel2;
    }
  }

  private static final class ViewModelCImpl extends PatFlowApplication_HiltComponents.ViewModelC {
    private final SavedStateHandle savedStateHandle;

    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ViewModelCImpl viewModelCImpl = this;

    private Provider<AddEditBillViewModel> addEditBillViewModelProvider;

    private Provider<AddEditBudgetViewModel> addEditBudgetViewModelProvider;

    private Provider<AddEditIncomeSourceViewModel> addEditIncomeSourceViewModelProvider;

    private Provider<AddEditIncomeViewModel> addEditIncomeViewModelProvider;

    private Provider<AddEditSavingsGoalViewModel> addEditSavingsGoalViewModelProvider;

    private Provider<BillDetailViewModel> billDetailViewModelProvider;

    private Provider<BillListViewModel> billListViewModelProvider;

    private Provider<BudgetDetailViewModel> budgetDetailViewModelProvider;

    private Provider<BudgetViewModel> budgetViewModelProvider;

    private Provider<CalendarViewModel> calendarViewModelProvider;

    private Provider<DashboardViewModel> dashboardViewModelProvider;

    private Provider<DataManagementViewModel> dataManagementViewModelProvider;

    private Provider<HapticViewModel> hapticViewModelProvider;

    private Provider<IncomeSourceListViewModel> incomeSourceListViewModelProvider;

    private Provider<IncomeViewModel> incomeViewModelProvider;

    private Provider<PaymentDetailViewModel> paymentDetailViewModelProvider;

    private Provider<PaymentHistoryViewModel> paymentHistoryViewModelProvider;

    private Provider<ReportsViewModel> reportsViewModelProvider;

    private Provider<SavingsGoalDetailViewModel> savingsGoalDetailViewModelProvider;

    private Provider<SavingsGoalViewModel> savingsGoalViewModelProvider;

    private Provider<SettingsViewModel> settingsViewModelProvider;

    private Provider<ThemeViewModel> themeViewModelProvider;

    private ViewModelCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, SavedStateHandle savedStateHandleParam,
        ViewModelLifecycle viewModelLifecycleParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.savedStateHandle = savedStateHandleParam;
      initialize(savedStateHandleParam, viewModelLifecycleParam);

    }

    private AddBillUseCase addBillUseCase() {
      return new AddBillUseCase(singletonCImpl.bindBillRepositoryProvider.get());
    }

    private UpdateBillUseCase updateBillUseCase() {
      return new UpdateBillUseCase(singletonCImpl.bindBillRepositoryProvider.get());
    }

    private GetBillDetailUseCase getBillDetailUseCase() {
      return new GetBillDetailUseCase(singletonCImpl.bindBillRepositoryProvider.get());
    }

    private AddBudgetUseCase addBudgetUseCase() {
      return new AddBudgetUseCase(singletonCImpl.bindBudgetRepositoryProvider.get());
    }

    private UpdateBudgetUseCase updateBudgetUseCase() {
      return new UpdateBudgetUseCase(singletonCImpl.bindBudgetRepositoryProvider.get());
    }

    private AddSavingsGoalUseCase addSavingsGoalUseCase() {
      return new AddSavingsGoalUseCase(singletonCImpl.bindSavingsGoalRepositoryProvider.get());
    }

    private UpdateSavingsGoalUseCase updateSavingsGoalUseCase() {
      return new UpdateSavingsGoalUseCase(singletonCImpl.bindSavingsGoalRepositoryProvider.get());
    }

    private DeleteBillUseCase deleteBillUseCase() {
      return new DeleteBillUseCase(singletonCImpl.bindBillRepositoryProvider.get());
    }

    private GetBillsUseCase getBillsUseCase() {
      return new GetBillsUseCase(singletonCImpl.bindBillRepositoryProvider.get());
    }

    private GetBudgetAnalyticsUseCase getBudgetAnalyticsUseCase() {
      return new GetBudgetAnalyticsUseCase(singletonCImpl.bindBudgetRepositoryProvider.get(), singletonCImpl.bindPaymentRepositoryProvider.get(), singletonCImpl.bindBillRepositoryProvider.get());
    }

    private DeleteBudgetUseCase deleteBudgetUseCase() {
      return new DeleteBudgetUseCase(singletonCImpl.bindBudgetRepositoryProvider.get());
    }

    private ArchiveBudgetUseCase archiveBudgetUseCase() {
      return new ArchiveBudgetUseCase(singletonCImpl.bindBudgetRepositoryProvider.get());
    }

    private GetBudgetsUseCase getBudgetsUseCase() {
      return new GetBudgetsUseCase(singletonCImpl.bindBudgetRepositoryProvider.get());
    }

    private GetSavingsGoalAnalyticsUseCase getSavingsGoalAnalyticsUseCase() {
      return new GetSavingsGoalAnalyticsUseCase(singletonCImpl.bindSavingsGoalRepositoryProvider.get());
    }

    private GetDashboardDataUseCase getDashboardDataUseCase() {
      return new GetDashboardDataUseCase(singletonCImpl.bindBillRepositoryProvider.get(), singletonCImpl.bindPaymentRepositoryProvider.get(), singletonCImpl.bindIncomeRepositoryProvider.get(), singletonCImpl.bindBudgetRepositoryProvider.get(), singletonCImpl.bindSavingsGoalRepositoryProvider.get(), getBudgetAnalyticsUseCase(), getSavingsGoalAnalyticsUseCase());
    }

    private GetSmartInsightsUseCase getSmartInsightsUseCase() {
      return new GetSmartInsightsUseCase(singletonCImpl.bindBillRepositoryProvider.get(), singletonCImpl.bindPaymentRepositoryProvider.get(), singletonCImpl.bindIncomeRepositoryProvider.get(), singletonCImpl.bindBudgetRepositoryProvider.get(), singletonCImpl.bindSavingsGoalRepositoryProvider.get());
    }

    private CreateBackupUseCase createBackupUseCase() {
      return new CreateBackupUseCase(singletonCImpl.bindDataManagementRepositoryProvider.get());
    }

    private RestoreBackupUseCase restoreBackupUseCase() {
      return new RestoreBackupUseCase(singletonCImpl.bindDataManagementRepositoryProvider.get());
    }

    private ExportCsvUseCase exportCsvUseCase() {
      return new ExportCsvUseCase(singletonCImpl.bindDataManagementRepositoryProvider.get());
    }

    private GetIncomeEntriesUseCase getIncomeEntriesUseCase() {
      return new GetIncomeEntriesUseCase(singletonCImpl.bindIncomeRepositoryProvider.get());
    }

    private DeleteIncomeEntryUseCase deleteIncomeEntryUseCase() {
      return new DeleteIncomeEntryUseCase(singletonCImpl.bindIncomeRepositoryProvider.get());
    }

    private DuplicateIncomeEntryUseCase duplicateIncomeEntryUseCase() {
      return new DuplicateIncomeEntryUseCase(singletonCImpl.bindIncomeRepositoryProvider.get());
    }

    private GetPaymentDetailUseCase getPaymentDetailUseCase() {
      return new GetPaymentDetailUseCase(singletonCImpl.bindPaymentRepositoryProvider.get());
    }

    private UndoPaymentUseCase undoPaymentUseCase() {
      return new UndoPaymentUseCase(singletonCImpl.bindPaymentRepositoryProvider.get());
    }

    private GetPaymentsUseCase getPaymentsUseCase() {
      return new GetPaymentsUseCase(singletonCImpl.bindPaymentRepositoryProvider.get());
    }

    private GetReportDataUseCase getReportDataUseCase() {
      return new GetReportDataUseCase(singletonCImpl.bindBillRepositoryProvider.get(), singletonCImpl.bindPaymentRepositoryProvider.get(), singletonCImpl.bindIncomeRepositoryProvider.get(), singletonCImpl.bindBudgetRepositoryProvider.get(), singletonCImpl.bindSavingsGoalRepositoryProvider.get());
    }

    private AddSavingsContributionUseCase addSavingsContributionUseCase() {
      return new AddSavingsContributionUseCase(singletonCImpl.bindSavingsGoalRepositoryProvider.get());
    }

    private DeleteSavingsGoalUseCase deleteSavingsGoalUseCase() {
      return new DeleteSavingsGoalUseCase(singletonCImpl.bindSavingsGoalRepositoryProvider.get());
    }

    private GetSavingsGoalsUseCase getSavingsGoalsUseCase() {
      return new GetSavingsGoalsUseCase(singletonCImpl.bindSavingsGoalRepositoryProvider.get());
    }

    private UpdateUserPreferenceUseCase updateUserPreferenceUseCase() {
      return new UpdateUserPreferenceUseCase(singletonCImpl.bindSettingsRepositoryProvider.get());
    }

    @SuppressWarnings("unchecked")
    private void initialize(final SavedStateHandle savedStateHandleParam,
        final ViewModelLifecycle viewModelLifecycleParam) {
      this.addEditBillViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 0);
      this.addEditBudgetViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 1);
      this.addEditIncomeSourceViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 2);
      this.addEditIncomeViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 3);
      this.addEditSavingsGoalViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 4);
      this.billDetailViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 5);
      this.billListViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 6);
      this.budgetDetailViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 7);
      this.budgetViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 8);
      this.calendarViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 9);
      this.dashboardViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 10);
      this.dataManagementViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 11);
      this.hapticViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 12);
      this.incomeSourceListViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 13);
      this.incomeViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 14);
      this.paymentDetailViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 15);
      this.paymentHistoryViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 16);
      this.reportsViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 17);
      this.savingsGoalDetailViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 18);
      this.savingsGoalViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 19);
      this.settingsViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 20);
      this.themeViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, viewModelCImpl, 21);
    }

    @Override
    public Map<Class<?>, javax.inject.Provider<ViewModel>> getHiltViewModelMap() {
      return LazyClassKeyMap.<javax.inject.Provider<ViewModel>>of(MapBuilder.<String, javax.inject.Provider<ViewModel>>newMapBuilder(22).put(LazyClassKeyProvider.com_patflow_app_feature_bills_AddEditBillViewModel, ((Provider) addEditBillViewModelProvider)).put(LazyClassKeyProvider.com_patflow_app_feature_budget_AddEditBudgetViewModel, ((Provider) addEditBudgetViewModelProvider)).put(LazyClassKeyProvider.com_patflow_app_feature_income_AddEditIncomeSourceViewModel, ((Provider) addEditIncomeSourceViewModelProvider)).put(LazyClassKeyProvider.com_patflow_app_feature_income_AddEditIncomeViewModel, ((Provider) addEditIncomeViewModelProvider)).put(LazyClassKeyProvider.com_patflow_app_feature_savings_AddEditSavingsGoalViewModel, ((Provider) addEditSavingsGoalViewModelProvider)).put(LazyClassKeyProvider.com_patflow_app_feature_bills_BillDetailViewModel, ((Provider) billDetailViewModelProvider)).put(LazyClassKeyProvider.com_patflow_app_feature_bills_BillListViewModel, ((Provider) billListViewModelProvider)).put(LazyClassKeyProvider.com_patflow_app_feature_budget_BudgetDetailViewModel, ((Provider) budgetDetailViewModelProvider)).put(LazyClassKeyProvider.com_patflow_app_feature_budget_BudgetViewModel, ((Provider) budgetViewModelProvider)).put(LazyClassKeyProvider.com_patflow_app_feature_calendar_CalendarViewModel, ((Provider) calendarViewModelProvider)).put(LazyClassKeyProvider.com_patflow_app_feature_dashboard_DashboardViewModel, ((Provider) dashboardViewModelProvider)).put(LazyClassKeyProvider.com_patflow_app_feature_settings_DataManagementViewModel, ((Provider) dataManagementViewModelProvider)).put(LazyClassKeyProvider.com_patflow_app_core_utils_HapticViewModel, ((Provider) hapticViewModelProvider)).put(LazyClassKeyProvider.com_patflow_app_feature_income_IncomeSourceListViewModel, ((Provider) incomeSourceListViewModelProvider)).put(LazyClassKeyProvider.com_patflow_app_feature_income_IncomeViewModel, ((Provider) incomeViewModelProvider)).put(LazyClassKeyProvider.com_patflow_app_feature_payment_PaymentDetailViewModel, ((Provider) paymentDetailViewModelProvider)).put(LazyClassKeyProvider.com_patflow_app_feature_payment_PaymentHistoryViewModel, ((Provider) paymentHistoryViewModelProvider)).put(LazyClassKeyProvider.com_patflow_app_feature_reports_ReportsViewModel, ((Provider) reportsViewModelProvider)).put(LazyClassKeyProvider.com_patflow_app_feature_savings_SavingsGoalDetailViewModel, ((Provider) savingsGoalDetailViewModelProvider)).put(LazyClassKeyProvider.com_patflow_app_feature_savings_SavingsGoalViewModel, ((Provider) savingsGoalViewModelProvider)).put(LazyClassKeyProvider.com_patflow_app_feature_settings_SettingsViewModel, ((Provider) settingsViewModelProvider)).put(LazyClassKeyProvider.com_patflow_app_core_theme_ThemeViewModel, ((Provider) themeViewModelProvider)).build());
    }

    @Override
    public Map<Class<?>, Object> getHiltViewModelAssistedMap() {
      return Collections.<Class<?>, Object>emptyMap();
    }

    @IdentifierNameString
    private static final class LazyClassKeyProvider {
      static String com_patflow_app_feature_savings_SavingsGoalDetailViewModel = "com.patflow.app.feature.savings.SavingsGoalDetailViewModel";

      static String com_patflow_app_feature_budget_AddEditBudgetViewModel = "com.patflow.app.feature.budget.AddEditBudgetViewModel";

      static String com_patflow_app_feature_payment_PaymentHistoryViewModel = "com.patflow.app.feature.payment.PaymentHistoryViewModel";

      static String com_patflow_app_feature_income_AddEditIncomeViewModel = "com.patflow.app.feature.income.AddEditIncomeViewModel";

      static String com_patflow_app_feature_bills_BillDetailViewModel = "com.patflow.app.feature.bills.BillDetailViewModel";

      static String com_patflow_app_feature_settings_DataManagementViewModel = "com.patflow.app.feature.settings.DataManagementViewModel";

      static String com_patflow_app_feature_budget_BudgetDetailViewModel = "com.patflow.app.feature.budget.BudgetDetailViewModel";

      static String com_patflow_app_core_utils_HapticViewModel = "com.patflow.app.core.utils.HapticViewModel";

      static String com_patflow_app_feature_payment_PaymentDetailViewModel = "com.patflow.app.feature.payment.PaymentDetailViewModel";

      static String com_patflow_app_feature_income_AddEditIncomeSourceViewModel = "com.patflow.app.feature.income.AddEditIncomeSourceViewModel";

      static String com_patflow_app_feature_bills_AddEditBillViewModel = "com.patflow.app.feature.bills.AddEditBillViewModel";

      static String com_patflow_app_feature_dashboard_DashboardViewModel = "com.patflow.app.feature.dashboard.DashboardViewModel";

      static String com_patflow_app_feature_income_IncomeSourceListViewModel = "com.patflow.app.feature.income.IncomeSourceListViewModel";

      static String com_patflow_app_feature_income_IncomeViewModel = "com.patflow.app.feature.income.IncomeViewModel";

      static String com_patflow_app_feature_budget_BudgetViewModel = "com.patflow.app.feature.budget.BudgetViewModel";

      static String com_patflow_app_feature_bills_BillListViewModel = "com.patflow.app.feature.bills.BillListViewModel";

      static String com_patflow_app_feature_calendar_CalendarViewModel = "com.patflow.app.feature.calendar.CalendarViewModel";

      static String com_patflow_app_feature_savings_AddEditSavingsGoalViewModel = "com.patflow.app.feature.savings.AddEditSavingsGoalViewModel";

      static String com_patflow_app_feature_savings_SavingsGoalViewModel = "com.patflow.app.feature.savings.SavingsGoalViewModel";

      static String com_patflow_app_core_theme_ThemeViewModel = "com.patflow.app.core.theme.ThemeViewModel";

      static String com_patflow_app_feature_reports_ReportsViewModel = "com.patflow.app.feature.reports.ReportsViewModel";

      static String com_patflow_app_feature_settings_SettingsViewModel = "com.patflow.app.feature.settings.SettingsViewModel";

      @KeepFieldType
      SavingsGoalDetailViewModel com_patflow_app_feature_savings_SavingsGoalDetailViewModel2;

      @KeepFieldType
      AddEditBudgetViewModel com_patflow_app_feature_budget_AddEditBudgetViewModel2;

      @KeepFieldType
      PaymentHistoryViewModel com_patflow_app_feature_payment_PaymentHistoryViewModel2;

      @KeepFieldType
      AddEditIncomeViewModel com_patflow_app_feature_income_AddEditIncomeViewModel2;

      @KeepFieldType
      BillDetailViewModel com_patflow_app_feature_bills_BillDetailViewModel2;

      @KeepFieldType
      DataManagementViewModel com_patflow_app_feature_settings_DataManagementViewModel2;

      @KeepFieldType
      BudgetDetailViewModel com_patflow_app_feature_budget_BudgetDetailViewModel2;

      @KeepFieldType
      HapticViewModel com_patflow_app_core_utils_HapticViewModel2;

      @KeepFieldType
      PaymentDetailViewModel com_patflow_app_feature_payment_PaymentDetailViewModel2;

      @KeepFieldType
      AddEditIncomeSourceViewModel com_patflow_app_feature_income_AddEditIncomeSourceViewModel2;

      @KeepFieldType
      AddEditBillViewModel com_patflow_app_feature_bills_AddEditBillViewModel2;

      @KeepFieldType
      DashboardViewModel com_patflow_app_feature_dashboard_DashboardViewModel2;

      @KeepFieldType
      IncomeSourceListViewModel com_patflow_app_feature_income_IncomeSourceListViewModel2;

      @KeepFieldType
      IncomeViewModel com_patflow_app_feature_income_IncomeViewModel2;

      @KeepFieldType
      BudgetViewModel com_patflow_app_feature_budget_BudgetViewModel2;

      @KeepFieldType
      BillListViewModel com_patflow_app_feature_bills_BillListViewModel2;

      @KeepFieldType
      CalendarViewModel com_patflow_app_feature_calendar_CalendarViewModel2;

      @KeepFieldType
      AddEditSavingsGoalViewModel com_patflow_app_feature_savings_AddEditSavingsGoalViewModel2;

      @KeepFieldType
      SavingsGoalViewModel com_patflow_app_feature_savings_SavingsGoalViewModel2;

      @KeepFieldType
      ThemeViewModel com_patflow_app_core_theme_ThemeViewModel2;

      @KeepFieldType
      ReportsViewModel com_patflow_app_feature_reports_ReportsViewModel2;

      @KeepFieldType
      SettingsViewModel com_patflow_app_feature_settings_SettingsViewModel2;
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final ActivityRetainedCImpl activityRetainedCImpl;

      private final ViewModelCImpl viewModelCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
          ViewModelCImpl viewModelCImpl, int id) {
        this.singletonCImpl = singletonCImpl;
        this.activityRetainedCImpl = activityRetainedCImpl;
        this.viewModelCImpl = viewModelCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // com.patflow.app.feature.bills.AddEditBillViewModel 
          return (T) new AddEditBillViewModel(viewModelCImpl.addBillUseCase(), viewModelCImpl.updateBillUseCase(), viewModelCImpl.getBillDetailUseCase(), singletonCImpl.bindCategoryRepositoryProvider.get(), singletonCImpl.getUserSettingsUseCase(), viewModelCImpl.savedStateHandle);

          case 1: // com.patflow.app.feature.budget.AddEditBudgetViewModel 
          return (T) new AddEditBudgetViewModel(singletonCImpl.bindBudgetRepositoryProvider.get(), viewModelCImpl.addBudgetUseCase(), viewModelCImpl.updateBudgetUseCase(), singletonCImpl.getUserSettingsUseCase(), singletonCImpl.bindCategoryRepositoryProvider.get(), viewModelCImpl.savedStateHandle);

          case 2: // com.patflow.app.feature.income.AddEditIncomeSourceViewModel 
          return (T) new AddEditIncomeSourceViewModel(singletonCImpl.bindIncomeRepositoryProvider.get(), viewModelCImpl.savedStateHandle);

          case 3: // com.patflow.app.feature.income.AddEditIncomeViewModel 
          return (T) new AddEditIncomeViewModel(singletonCImpl.bindIncomeRepositoryProvider.get(), singletonCImpl.getUserSettingsUseCase(), viewModelCImpl.savedStateHandle);

          case 4: // com.patflow.app.feature.savings.AddEditSavingsGoalViewModel 
          return (T) new AddEditSavingsGoalViewModel(singletonCImpl.bindSavingsGoalRepositoryProvider.get(), viewModelCImpl.addSavingsGoalUseCase(), viewModelCImpl.updateSavingsGoalUseCase(), singletonCImpl.getUserSettingsUseCase(), viewModelCImpl.savedStateHandle);

          case 5: // com.patflow.app.feature.bills.BillDetailViewModel 
          return (T) new BillDetailViewModel(viewModelCImpl.getBillDetailUseCase(), viewModelCImpl.deleteBillUseCase(), singletonCImpl.markBillAsPaidUseCase(), viewModelCImpl.savedStateHandle);

          case 6: // com.patflow.app.feature.bills.BillListViewModel 
          return (T) new BillListViewModel(viewModelCImpl.getBillsUseCase(), viewModelCImpl.deleteBillUseCase(), singletonCImpl.markBillAsPaidUseCase());

          case 7: // com.patflow.app.feature.budget.BudgetDetailViewModel 
          return (T) new BudgetDetailViewModel(viewModelCImpl.getBudgetAnalyticsUseCase(), viewModelCImpl.deleteBudgetUseCase(), viewModelCImpl.archiveBudgetUseCase(), viewModelCImpl.savedStateHandle);

          case 8: // com.patflow.app.feature.budget.BudgetViewModel 
          return (T) new BudgetViewModel(viewModelCImpl.getBudgetsUseCase(), viewModelCImpl.getBudgetAnalyticsUseCase());

          case 9: // com.patflow.app.feature.calendar.CalendarViewModel 
          return (T) new CalendarViewModel(singletonCImpl.bindBillRepositoryProvider.get(), singletonCImpl.bindPaymentRepositoryProvider.get(), singletonCImpl.bindIncomeRepositoryProvider.get(), singletonCImpl.bindSavingsGoalRepositoryProvider.get());

          case 10: // com.patflow.app.feature.dashboard.DashboardViewModel 
          return (T) new DashboardViewModel(viewModelCImpl.getDashboardDataUseCase(), viewModelCImpl.getSmartInsightsUseCase(), singletonCImpl.getUserSettingsUseCase());

          case 11: // com.patflow.app.feature.settings.DataManagementViewModel 
          return (T) new DataManagementViewModel(viewModelCImpl.createBackupUseCase(), viewModelCImpl.restoreBackupUseCase(), viewModelCImpl.exportCsvUseCase());

          case 12: // com.patflow.app.core.utils.HapticViewModel 
          return (T) new HapticViewModel(singletonCImpl.getUserSettingsUseCase());

          case 13: // com.patflow.app.feature.income.IncomeSourceListViewModel 
          return (T) new IncomeSourceListViewModel(singletonCImpl.bindIncomeRepositoryProvider.get());

          case 14: // com.patflow.app.feature.income.IncomeViewModel 
          return (T) new IncomeViewModel(viewModelCImpl.getIncomeEntriesUseCase(), viewModelCImpl.deleteIncomeEntryUseCase(), viewModelCImpl.duplicateIncomeEntryUseCase(), singletonCImpl.bindIncomeRepositoryProvider.get());

          case 15: // com.patflow.app.feature.payment.PaymentDetailViewModel 
          return (T) new PaymentDetailViewModel(viewModelCImpl.getPaymentDetailUseCase(), viewModelCImpl.undoPaymentUseCase(), viewModelCImpl.savedStateHandle);

          case 16: // com.patflow.app.feature.payment.PaymentHistoryViewModel 
          return (T) new PaymentHistoryViewModel(viewModelCImpl.getPaymentsUseCase());

          case 17: // com.patflow.app.feature.reports.ReportsViewModel 
          return (T) new ReportsViewModel(viewModelCImpl.getReportDataUseCase(), singletonCImpl.getUserSettingsUseCase());

          case 18: // com.patflow.app.feature.savings.SavingsGoalDetailViewModel 
          return (T) new SavingsGoalDetailViewModel(viewModelCImpl.getSavingsGoalAnalyticsUseCase(), viewModelCImpl.addSavingsContributionUseCase(), viewModelCImpl.deleteSavingsGoalUseCase(), viewModelCImpl.savedStateHandle);

          case 19: // com.patflow.app.feature.savings.SavingsGoalViewModel 
          return (T) new SavingsGoalViewModel(viewModelCImpl.getSavingsGoalsUseCase(), viewModelCImpl.getSavingsGoalAnalyticsUseCase());

          case 20: // com.patflow.app.feature.settings.SettingsViewModel 
          return (T) new SettingsViewModel(singletonCImpl.getUserSettingsUseCase(), viewModelCImpl.updateUserPreferenceUseCase(), singletonCImpl.notificationRepositoryImplProvider.get());

          case 21: // com.patflow.app.core.theme.ThemeViewModel 
          return (T) new ThemeViewModel(singletonCImpl.getUserSettingsUseCase());

          default: throw new AssertionError(id);
        }
      }
    }
  }

  private static final class ActivityRetainedCImpl extends PatFlowApplication_HiltComponents.ActivityRetainedC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl = this;

    private Provider<ActivityRetainedLifecycle> provideActivityRetainedLifecycleProvider;

    private ActivityRetainedCImpl(SingletonCImpl singletonCImpl,
        SavedStateHandleHolder savedStateHandleHolderParam) {
      this.singletonCImpl = singletonCImpl;

      initialize(savedStateHandleHolderParam);

    }

    @SuppressWarnings("unchecked")
    private void initialize(final SavedStateHandleHolder savedStateHandleHolderParam) {
      this.provideActivityRetainedLifecycleProvider = DoubleCheck.provider(new SwitchingProvider<ActivityRetainedLifecycle>(singletonCImpl, activityRetainedCImpl, 0));
    }

    @Override
    public ActivityComponentBuilder activityComponentBuilder() {
      return new ActivityCBuilder(singletonCImpl, activityRetainedCImpl);
    }

    @Override
    public ActivityRetainedLifecycle getActivityRetainedLifecycle() {
      return provideActivityRetainedLifecycleProvider.get();
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final ActivityRetainedCImpl activityRetainedCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
          int id) {
        this.singletonCImpl = singletonCImpl;
        this.activityRetainedCImpl = activityRetainedCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // dagger.hilt.android.ActivityRetainedLifecycle 
          return (T) ActivityRetainedComponentManager_LifecycleModule_ProvideActivityRetainedLifecycleFactory.provideActivityRetainedLifecycle();

          default: throw new AssertionError(id);
        }
      }
    }
  }

  private static final class ServiceCImpl extends PatFlowApplication_HiltComponents.ServiceC {
    private final SingletonCImpl singletonCImpl;

    private final ServiceCImpl serviceCImpl = this;

    private ServiceCImpl(SingletonCImpl singletonCImpl, Service serviceParam) {
      this.singletonCImpl = singletonCImpl;


    }
  }

  private static final class SingletonCImpl extends PatFlowApplication_HiltComponents.SingletonC {
    private final ApplicationContextModule applicationContextModule;

    private final SingletonCImpl singletonCImpl = this;

    private Provider<PatFlowDatabase> provideDatabaseProvider;

    private Provider<PaymentRepositoryImpl> paymentRepositoryImplProvider;

    private Provider<PaymentRepository> bindPaymentRepositoryProvider;

    private Provider<WorkManager> provideWorkManagerProvider;

    private Provider<ReminderRepositoryImpl> reminderRepositoryImplProvider;

    private Provider<ReminderRepository> bindReminderRepositoryProvider;

    private Provider<DataStore<Preferences>> providePreferencesDataStoreProvider;

    private Provider<SettingsRepositoryImpl> settingsRepositoryImplProvider;

    private Provider<SettingsRepository> bindSettingsRepositoryProvider;

    private Provider<BillRepositoryImpl> billRepositoryImplProvider;

    private Provider<BillRepository> bindBillRepositoryProvider;

    private Provider<DatabaseInitializer> databaseInitializerProvider;

    private Provider<NotificationScheduler> notificationSchedulerProvider;

    private Provider<CategoryRepositoryImpl> categoryRepositoryImplProvider;

    private Provider<CategoryRepository> bindCategoryRepositoryProvider;

    private Provider<BudgetRepositoryImpl> budgetRepositoryImplProvider;

    private Provider<BudgetRepository> bindBudgetRepositoryProvider;

    private Provider<IncomeRepositoryImpl> incomeRepositoryImplProvider;

    private Provider<IncomeRepository> bindIncomeRepositoryProvider;

    private Provider<SavingsGoalRepositoryImpl> savingsGoalRepositoryImplProvider;

    private Provider<SavingsGoalRepository> bindSavingsGoalRepositoryProvider;

    private Provider<DataManagementRepositoryImpl> dataManagementRepositoryImplProvider;

    private Provider<DataManagementRepository> bindDataManagementRepositoryProvider;

    private Provider<NotificationRepositoryImpl> notificationRepositoryImplProvider;

    private SingletonCImpl(ApplicationContextModule applicationContextModuleParam) {
      this.applicationContextModule = applicationContextModuleParam;
      initialize(applicationContextModuleParam);

    }

    private PaymentDao paymentDao() {
      return DatabaseModule_ProvidePaymentDaoFactory.providePaymentDao(provideDatabaseProvider.get());
    }

    private BillCycleDao billCycleDao() {
      return DatabaseModule_ProvideBillCycleDaoFactory.provideBillCycleDao(provideDatabaseProvider.get());
    }

    private BillDao billDao() {
      return DatabaseModule_ProvideBillDaoFactory.provideBillDao(provideDatabaseProvider.get());
    }

    private ReminderDao reminderDao() {
      return DatabaseModule_ProvideReminderDaoFactory.provideReminderDao(provideDatabaseProvider.get());
    }

    private GetUserSettingsUseCase getUserSettingsUseCase() {
      return new GetUserSettingsUseCase(bindSettingsRepositoryProvider.get());
    }

    private LogPaymentUseCase logPaymentUseCase() {
      return new LogPaymentUseCase(bindPaymentRepositoryProvider.get(), bindBillRepositoryProvider.get());
    }

    private MarkBillAsPaidUseCase markBillAsPaidUseCase() {
      return new MarkBillAsPaidUseCase(logPaymentUseCase());
    }

    private CategoryDao categoryDao() {
      return DatabaseModule_ProvideCategoryDaoFactory.provideCategoryDao(provideDatabaseProvider.get());
    }

    private IncomeDao incomeDao() {
      return DatabaseModule_ProvideIncomeDaoFactory.provideIncomeDao(provideDatabaseProvider.get());
    }

    private BudgetDao budgetDao() {
      return DatabaseModule_ProvideBudgetDaoFactory.provideBudgetDao(provideDatabaseProvider.get());
    }

    private SavingsGoalDao savingsGoalDao() {
      return DatabaseModule_ProvideSavingsGoalDaoFactory.provideSavingsGoalDao(provideDatabaseProvider.get());
    }

    @SuppressWarnings("unchecked")
    private void initialize(final ApplicationContextModule applicationContextModuleParam) {
      this.provideDatabaseProvider = DoubleCheck.provider(new SwitchingProvider<PatFlowDatabase>(singletonCImpl, 1));
      this.paymentRepositoryImplProvider = new SwitchingProvider<>(singletonCImpl, 0);
      this.bindPaymentRepositoryProvider = DoubleCheck.provider((Provider) paymentRepositoryImplProvider);
      this.provideWorkManagerProvider = DoubleCheck.provider(new SwitchingProvider<WorkManager>(singletonCImpl, 4));
      this.reminderRepositoryImplProvider = new SwitchingProvider<>(singletonCImpl, 3);
      this.bindReminderRepositoryProvider = DoubleCheck.provider((Provider) reminderRepositoryImplProvider);
      this.providePreferencesDataStoreProvider = DoubleCheck.provider(new SwitchingProvider<DataStore<Preferences>>(singletonCImpl, 6));
      this.settingsRepositoryImplProvider = new SwitchingProvider<>(singletonCImpl, 5);
      this.bindSettingsRepositoryProvider = DoubleCheck.provider((Provider) settingsRepositoryImplProvider);
      this.billRepositoryImplProvider = new SwitchingProvider<>(singletonCImpl, 2);
      this.bindBillRepositoryProvider = DoubleCheck.provider((Provider) billRepositoryImplProvider);
      this.databaseInitializerProvider = DoubleCheck.provider(new SwitchingProvider<DatabaseInitializer>(singletonCImpl, 7));
      this.notificationSchedulerProvider = DoubleCheck.provider(new SwitchingProvider<NotificationScheduler>(singletonCImpl, 8));
      this.categoryRepositoryImplProvider = new SwitchingProvider<>(singletonCImpl, 9);
      this.bindCategoryRepositoryProvider = DoubleCheck.provider((Provider) categoryRepositoryImplProvider);
      this.budgetRepositoryImplProvider = new SwitchingProvider<>(singletonCImpl, 10);
      this.bindBudgetRepositoryProvider = DoubleCheck.provider((Provider) budgetRepositoryImplProvider);
      this.incomeRepositoryImplProvider = new SwitchingProvider<>(singletonCImpl, 11);
      this.bindIncomeRepositoryProvider = DoubleCheck.provider((Provider) incomeRepositoryImplProvider);
      this.savingsGoalRepositoryImplProvider = new SwitchingProvider<>(singletonCImpl, 12);
      this.bindSavingsGoalRepositoryProvider = DoubleCheck.provider((Provider) savingsGoalRepositoryImplProvider);
      this.dataManagementRepositoryImplProvider = new SwitchingProvider<>(singletonCImpl, 13);
      this.bindDataManagementRepositoryProvider = DoubleCheck.provider((Provider) dataManagementRepositoryImplProvider);
      this.notificationRepositoryImplProvider = DoubleCheck.provider(new SwitchingProvider<NotificationRepositoryImpl>(singletonCImpl, 14));
    }

    @Override
    public void injectPatFlowApplication(PatFlowApplication patFlowApplication) {
    }

    @Override
    public void injectNotificationReceiver(NotificationReceiver notificationReceiver) {
      injectNotificationReceiver2(notificationReceiver);
    }

    @Override
    public Set<Boolean> getDisableFragmentGetContextFix() {
      return Collections.<Boolean>emptySet();
    }

    @Override
    public ActivityRetainedComponentBuilder retainedComponentBuilder() {
      return new ActivityRetainedCBuilder(singletonCImpl);
    }

    @Override
    public ServiceComponentBuilder serviceComponentBuilder() {
      return new ServiceCBuilder(singletonCImpl);
    }

    private NotificationReceiver injectNotificationReceiver2(NotificationReceiver instance) {
      NotificationReceiver_MembersInjector.injectMarkBillAsPaidUseCase(instance, markBillAsPaidUseCase());
      return instance;
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, int id) {
        this.singletonCImpl = singletonCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // com.patflow.app.data.repository.PaymentRepositoryImpl 
          return (T) new PaymentRepositoryImpl(singletonCImpl.paymentDao(), singletonCImpl.billCycleDao());

          case 1: // com.patflow.app.data.local.database.PatFlowDatabase 
          return (T) DatabaseModule_ProvideDatabaseFactory.provideDatabase(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          case 2: // com.patflow.app.data.repository.BillRepositoryImpl 
          return (T) new BillRepositoryImpl(singletonCImpl.billDao(), singletonCImpl.billCycleDao(), singletonCImpl.bindReminderRepositoryProvider.get(), singletonCImpl.getUserSettingsUseCase());

          case 3: // com.patflow.app.data.repository.ReminderRepositoryImpl 
          return (T) new ReminderRepositoryImpl(singletonCImpl.reminderDao(), singletonCImpl.provideWorkManagerProvider.get());

          case 4: // androidx.work.WorkManager 
          return (T) DatabaseModule_ProvideWorkManagerFactory.provideWorkManager(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          case 5: // com.patflow.app.data.repository.SettingsRepositoryImpl 
          return (T) new SettingsRepositoryImpl(singletonCImpl.providePreferencesDataStoreProvider.get());

          case 6: // androidx.datastore.core.DataStore<androidx.datastore.preferences.core.Preferences> 
          return (T) DataStoreModule_ProvidePreferencesDataStoreFactory.providePreferencesDataStore(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          case 7: // com.patflow.app.data.local.database.DatabaseInitializer 
          return (T) new DatabaseInitializer(singletonCImpl.categoryDao(), singletonCImpl.incomeDao());

          case 8: // com.patflow.app.core.notifications.NotificationScheduler 
          return (T) new NotificationScheduler(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          case 9: // com.patflow.app.data.repository.CategoryRepositoryImpl 
          return (T) new CategoryRepositoryImpl(singletonCImpl.categoryDao());

          case 10: // com.patflow.app.data.repository.BudgetRepositoryImpl 
          return (T) new BudgetRepositoryImpl(singletonCImpl.budgetDao(), singletonCImpl.categoryDao());

          case 11: // com.patflow.app.data.repository.IncomeRepositoryImpl 
          return (T) new IncomeRepositoryImpl(singletonCImpl.incomeDao());

          case 12: // com.patflow.app.data.repository.SavingsGoalRepositoryImpl 
          return (T) new SavingsGoalRepositoryImpl(singletonCImpl.savingsGoalDao());

          case 13: // com.patflow.app.data.repository.DataManagementRepositoryImpl 
          return (T) new DataManagementRepositoryImpl(singletonCImpl.provideDatabaseProvider.get(), singletonCImpl.billDao(), singletonCImpl.billCycleDao(), singletonCImpl.paymentDao(), singletonCImpl.categoryDao(), singletonCImpl.incomeDao(), singletonCImpl.budgetDao(), singletonCImpl.savingsGoalDao(), singletonCImpl.bindSettingsRepositoryProvider.get());

          case 14: // com.patflow.app.data.repository.NotificationRepositoryImpl 
          return (T) new NotificationRepositoryImpl(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));

          default: throw new AssertionError(id);
        }
      }
    }
  }
}
