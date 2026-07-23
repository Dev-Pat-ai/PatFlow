package hilt_aggregated_deps;

import dagger.hilt.processor.internal.aggregateddeps.AggregatedDeps;
import javax.annotation.processing.Generated;

/**
 * This class should only be referenced by generated code! This class aggregates information across multiple compilations.
 */
@AggregatedDeps(
    components = "dagger.hilt.components.SingletonComponent",
    modules = "com.patflow.app.core.notifications.ReminderSyncWorker_HiltModule"
)
@Generated("dagger.hilt.processor.internal.aggregateddeps.AggregatedDepsGenerator")
public class _com_patflow_app_core_notifications_ReminderSyncWorker_HiltModule {
}
