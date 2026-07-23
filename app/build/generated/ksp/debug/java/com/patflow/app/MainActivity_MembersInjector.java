package com.patflow.app;

import com.patflow.app.core.notifications.NotificationScheduler;
import com.patflow.app.data.local.database.DatabaseInitializer;
import dagger.MembersInjector;
import dagger.internal.DaggerGenerated;
import dagger.internal.InjectedFieldSignature;
import dagger.internal.QualifierMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class MainActivity_MembersInjector implements MembersInjector<MainActivity> {
  private final Provider<DatabaseInitializer> databaseInitializerProvider;

  private final Provider<NotificationScheduler> notificationSchedulerProvider;

  public MainActivity_MembersInjector(Provider<DatabaseInitializer> databaseInitializerProvider,
      Provider<NotificationScheduler> notificationSchedulerProvider) {
    this.databaseInitializerProvider = databaseInitializerProvider;
    this.notificationSchedulerProvider = notificationSchedulerProvider;
  }

  public static MembersInjector<MainActivity> create(
      Provider<DatabaseInitializer> databaseInitializerProvider,
      Provider<NotificationScheduler> notificationSchedulerProvider) {
    return new MainActivity_MembersInjector(databaseInitializerProvider, notificationSchedulerProvider);
  }

  @Override
  public void injectMembers(MainActivity instance) {
    injectDatabaseInitializer(instance, databaseInitializerProvider.get());
    injectNotificationScheduler(instance, notificationSchedulerProvider.get());
  }

  @InjectedFieldSignature("com.patflow.app.MainActivity.databaseInitializer")
  public static void injectDatabaseInitializer(MainActivity instance,
      DatabaseInitializer databaseInitializer) {
    instance.databaseInitializer = databaseInitializer;
  }

  @InjectedFieldSignature("com.patflow.app.MainActivity.notificationScheduler")
  public static void injectNotificationScheduler(MainActivity instance,
      NotificationScheduler notificationScheduler) {
    instance.notificationScheduler = notificationScheduler;
  }
}
