package com.patflow.app;

import androidx.hilt.work.HiltWorkerFactory;
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
public final class PatFlowApplication_MembersInjector implements MembersInjector<PatFlowApplication> {
  private final Provider<HiltWorkerFactory> workerFactoryProvider;

  public PatFlowApplication_MembersInjector(Provider<HiltWorkerFactory> workerFactoryProvider) {
    this.workerFactoryProvider = workerFactoryProvider;
  }

  public static MembersInjector<PatFlowApplication> create(
      Provider<HiltWorkerFactory> workerFactoryProvider) {
    return new PatFlowApplication_MembersInjector(workerFactoryProvider);
  }

  @Override
  public void injectMembers(PatFlowApplication instance) {
    injectWorkerFactory(instance, workerFactoryProvider.get());
  }

  @InjectedFieldSignature("com.patflow.app.PatFlowApplication.workerFactory")
  public static void injectWorkerFactory(PatFlowApplication instance,
      HiltWorkerFactory workerFactory) {
    instance.workerFactory = workerFactory;
  }
}
