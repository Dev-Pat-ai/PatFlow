package com.patflow.app.di;

import com.patflow.app.data.local.dao.PaymentDao;
import com.patflow.app.data.local.database.PatFlowDatabase;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
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
public final class DatabaseModule_ProvidePaymentDaoFactory implements Factory<PaymentDao> {
  private final Provider<PatFlowDatabase> dbProvider;

  public DatabaseModule_ProvidePaymentDaoFactory(Provider<PatFlowDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public PaymentDao get() {
    return providePaymentDao(dbProvider.get());
  }

  public static DatabaseModule_ProvidePaymentDaoFactory create(
      Provider<PatFlowDatabase> dbProvider) {
    return new DatabaseModule_ProvidePaymentDaoFactory(dbProvider);
  }

  public static PaymentDao providePaymentDao(PatFlowDatabase db) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.providePaymentDao(db));
  }
}
