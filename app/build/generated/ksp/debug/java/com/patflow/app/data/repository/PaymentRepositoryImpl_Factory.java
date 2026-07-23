package com.patflow.app.data.repository;

import com.patflow.app.data.local.dao.BillCycleDao;
import com.patflow.app.data.local.dao.PaymentDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
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
public final class PaymentRepositoryImpl_Factory implements Factory<PaymentRepositoryImpl> {
  private final Provider<PaymentDao> paymentDaoProvider;

  private final Provider<BillCycleDao> billCycleDaoProvider;

  public PaymentRepositoryImpl_Factory(Provider<PaymentDao> paymentDaoProvider,
      Provider<BillCycleDao> billCycleDaoProvider) {
    this.paymentDaoProvider = paymentDaoProvider;
    this.billCycleDaoProvider = billCycleDaoProvider;
  }

  @Override
  public PaymentRepositoryImpl get() {
    return newInstance(paymentDaoProvider.get(), billCycleDaoProvider.get());
  }

  public static PaymentRepositoryImpl_Factory create(Provider<PaymentDao> paymentDaoProvider,
      Provider<BillCycleDao> billCycleDaoProvider) {
    return new PaymentRepositoryImpl_Factory(paymentDaoProvider, billCycleDaoProvider);
  }

  public static PaymentRepositoryImpl newInstance(PaymentDao paymentDao,
      BillCycleDao billCycleDao) {
    return new PaymentRepositoryImpl(paymentDao, billCycleDao);
  }
}
