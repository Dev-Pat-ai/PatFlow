package com.patflow.app.data.repository;

import com.patflow.app.data.local.dao.BillDao;
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

  private final Provider<BillDao> billDaoProvider;

  public PaymentRepositoryImpl_Factory(Provider<PaymentDao> paymentDaoProvider,
      Provider<BillDao> billDaoProvider) {
    this.paymentDaoProvider = paymentDaoProvider;
    this.billDaoProvider = billDaoProvider;
  }

  @Override
  public PaymentRepositoryImpl get() {
    return newInstance(paymentDaoProvider.get(), billDaoProvider.get());
  }

  public static PaymentRepositoryImpl_Factory create(Provider<PaymentDao> paymentDaoProvider,
      Provider<BillDao> billDaoProvider) {
    return new PaymentRepositoryImpl_Factory(paymentDaoProvider, billDaoProvider);
  }

  public static PaymentRepositoryImpl newInstance(PaymentDao paymentDao, BillDao billDao) {
    return new PaymentRepositoryImpl(paymentDao, billDao);
  }
}
