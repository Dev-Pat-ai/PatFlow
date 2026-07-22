package com.patflow.app.data.repository;

import com.patflow.app.data.local.dao.BillCycleDao;
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
public final class BillRepositoryImpl_Factory implements Factory<BillRepositoryImpl> {
  private final Provider<BillDao> billDaoProvider;

  private final Provider<BillCycleDao> billCycleDaoProvider;

  private final Provider<PaymentDao> paymentDaoProvider;

  public BillRepositoryImpl_Factory(Provider<BillDao> billDaoProvider,
      Provider<BillCycleDao> billCycleDaoProvider, Provider<PaymentDao> paymentDaoProvider) {
    this.billDaoProvider = billDaoProvider;
    this.billCycleDaoProvider = billCycleDaoProvider;
    this.paymentDaoProvider = paymentDaoProvider;
  }

  @Override
  public BillRepositoryImpl get() {
    return newInstance(billDaoProvider.get(), billCycleDaoProvider.get(), paymentDaoProvider.get());
  }

  public static BillRepositoryImpl_Factory create(Provider<BillDao> billDaoProvider,
      Provider<BillCycleDao> billCycleDaoProvider, Provider<PaymentDao> paymentDaoProvider) {
    return new BillRepositoryImpl_Factory(billDaoProvider, billCycleDaoProvider, paymentDaoProvider);
  }

  public static BillRepositoryImpl newInstance(BillDao billDao, BillCycleDao billCycleDao,
      PaymentDao paymentDao) {
    return new BillRepositoryImpl(billDao, billCycleDao, paymentDao);
  }
}
