package de.flocksserver.domain.interactors.content

import de.flocksserver.domain.PostExecutionThread
import de.flocksserver.domain.ThreadExecutor
import de.flocksserver.domain.interactors.usecase.CompletableUseCase
import de.flocksserver.domain.repository.MyRepository
import io.reactivex.Completable
import javax.inject.Inject

/**
 * Created by marcel on 27.07.17.
 */
//TODO: How?
class AddItemUseCase @Inject constructor(threadExecutor: ThreadExecutor,
                                         postExecutionThread: PostExecutionThread,
                                         private val repository: MyRepository) :
        CompletableUseCase(threadExecutor, postExecutionThread) {

    override fun buildUseCaseCompletable(): Completable {
        return this.repository.addItem()
    }

}
