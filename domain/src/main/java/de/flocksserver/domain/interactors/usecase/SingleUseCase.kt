package de.flocksserver.domain.interactors.usecase

import dagger.internal.Preconditions
import de.flocksserver.domain.PostExecutionThread
import de.flocksserver.domain.ThreadExecutor
import io.reactivex.Single
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers

/**
 * Created by marcel on 01.08.17.
 */
abstract class SingleUseCase<T> internal constructor(private val threadExecutor: ThreadExecutor,
                                                     private val postExecutionThread: PostExecutionThread) : BaseUseCase() {

    internal abstract fun buildUseCaseObservable(): Single<T>

    fun execute(success: Consumer<T>, error: Consumer<Throwable>) {
        Preconditions.checkNotNull(success)
        Preconditions.checkNotNull(error)
        val observable = this.buildUseCaseObservable()
                .subscribeOn(Schedulers.from(threadExecutor))
                .observeOn(postExecutionThread.scheduler)
        addDisposable(observable.subscribe(success, error))
    }

}