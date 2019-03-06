package de.flocksserver.data.persistence

import android.content.Context
import android.content.SharedPreferences
import de.flocksserver.data.persistence.model.ContentDataModel
import de.flocksserver.data.persistence.model.mapper.ContentMMapper
import de.flocksserver.data.service.TextService
import de.flocksserver.domain.model.ContentModel
import de.flocksserver.domain.model.ItemModel
import de.flocksserver.domain.repository.MyRepository
import io.reactivex.Completable
import io.reactivex.Single
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import javax.inject.Inject

/**
 * Created by marcel on 08.08.17.
 */
class DataStore @Inject constructor(private val contentMMapper: ContentMMapper) : MyRepository {

    @Inject lateinit var logger: AnkoLogger
    @Inject lateinit var context: Context
    @Inject lateinit var sharedPrefs: SharedPreferences
    @Inject lateinit var textService: TextService


    override fun getContent(): Single<ContentModel> {
        logger.info { "DATASTORE -> get content" }
        return Single.fromCallable {
            val contentData = getContentData()
            contentMMapper.transformDMtoM(contentData)
        }
    }

    override fun addItem(): Completable {
        logger.info { "DATASTORE -> add random string item" }
        return Completable.fromCallable {
            val contentData = getContentData()
            val content = contentMMapper.transformDMtoM(contentData)
            val item = ItemModel(textService.generateText())
            content.items?.add(item)
            sharedPrefs.edit().putStringSet("content",contentMMapper.transformMtoDM(content)?.items).apply()
        }
    }

    override fun deleteItem(itemModel: ItemModel): Completable {
        logger.info { "DATASTORE -> delete item $itemModel" }
        return Completable.fromCallable {
            val contentData = getContentData()
            val content = contentMMapper.transformDMtoM(contentData)
            content.items?.remove(itemModel)
            sharedPrefs.edit().putStringSet("content",contentMMapper.transformMtoDM(content)?.items).apply()
        }
    }


    private fun getContentData() = ContentDataModel(sharedPrefs.getStringSet("content", emptySet<String>()))


}

