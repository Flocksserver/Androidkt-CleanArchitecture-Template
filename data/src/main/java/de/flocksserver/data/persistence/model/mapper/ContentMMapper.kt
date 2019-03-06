package de.flocksserver.data.persistence.model.mapper

import de.flocksserver.data.persistence.model.ContentDataModel
import de.flocksserver.domain.model.ContentModel
import de.flocksserver.domain.model.ItemModel
import javax.inject.Inject

/**
 * Created by marcel on 27.07.17.
 */
class ContentMMapper @Inject constructor() : BaseMapperDMM<ContentModel, ContentDataModel>() {

    override fun transformDMtoM(dataModel: ContentDataModel?): ContentModel {
        val items = dataModel?.items?.map { ItemModel(it) }?.sortedWith(compareBy {it.name})?.toMutableList()
        return ContentModel(items)
    }

    override fun transformMtoDM(model: ContentModel?): ContentDataModel? {
        val items = model?.items?.map { it.name ?: "" }?.toSet() ?: emptySet()
        return ContentDataModel(items)
    }
}