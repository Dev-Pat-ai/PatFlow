package com.patflow.app.core.notifications;

import com.patflow.app.domain.repository.ReminderRepository;
import com.patflow.app.domain.usecase.bill.MarkBillAsPaidUseCase;
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
public final class NotificationReceiver_MembersInjector implements MembersInjector<NotificationReceiver> {
  private final Provider<MarkBillAsPaidUseCase> markBillAsPaidUseCaseProvider;

  private final Provider<ReminderRepository> reminderRepositoryProvider;

  public NotificationReceiver_MembersInjector(
      Provider<MarkBillAsPaidUseCase> markBillAsPaidUseCaseProvider,
      Provider<ReminderRepository> reminderRepositoryProvider) {
    this.markBillAsPaidUseCaseProvider = markBillAsPaidUseCaseProvider;
    this.reminderRepositoryProvider = reminderRepositoryProvider;
  }

  public static MembersInjector<NotificationReceiver> create(
      Provider<MarkBillAsPaidUseCase> markBillAsPaidUseCaseProvider,
      Provider<ReminderRepository> reminderRepositoryProvider) {
    return new NotificationReceiver_MembersInjector(markBillAsPaidUseCaseProvider, reminderRepositoryProvider);
  }

  @Override
  public void injectMembers(NotificationReceiver instance) {
    injectMarkBillAsPaidUseCase(instance, markBillAsPaidUseCaseProvider.get());
    injectReminderRepository(instance, reminderRepositoryProvider.get());
  }

  @InjectedFieldSignature("com.patflow.app.core.notifications.NotificationReceiver.markBillAsPaidUseCase")
  public static void injectMarkBillAsPaidUseCase(NotificationReceiver instance,
      MarkBillAsPaidUseCase markBillAsPaidUseCase) {
    instance.markBillAsPaidUseCase = markBillAsPaidUseCase;
  }

  @InjectedFieldSignature("com.patflow.app.core.notifications.NotificationReceiver.reminderRepository")
  public static void injectReminderRepository(NotificationReceiver instance,
      ReminderRepository reminderRepository) {
    instance.reminderRepository = reminderRepository;
  }
}
