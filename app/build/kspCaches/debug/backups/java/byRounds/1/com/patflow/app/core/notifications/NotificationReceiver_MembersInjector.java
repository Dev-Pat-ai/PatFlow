package com.patflow.app.core.notifications;

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

  public NotificationReceiver_MembersInjector(
      Provider<MarkBillAsPaidUseCase> markBillAsPaidUseCaseProvider) {
    this.markBillAsPaidUseCaseProvider = markBillAsPaidUseCaseProvider;
  }

  public static MembersInjector<NotificationReceiver> create(
      Provider<MarkBillAsPaidUseCase> markBillAsPaidUseCaseProvider) {
    return new NotificationReceiver_MembersInjector(markBillAsPaidUseCaseProvider);
  }

  @Override
  public void injectMembers(NotificationReceiver instance) {
    injectMarkBillAsPaidUseCase(instance, markBillAsPaidUseCaseProvider.get());
  }

  @InjectedFieldSignature("com.patflow.app.core.notifications.NotificationReceiver.markBillAsPaidUseCase")
  public static void injectMarkBillAsPaidUseCase(NotificationReceiver instance,
      MarkBillAsPaidUseCase markBillAsPaidUseCase) {
    instance.markBillAsPaidUseCase = markBillAsPaidUseCase;
  }
}
