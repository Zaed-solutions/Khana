package org.zaed.khana.data.source.local.model

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class RecentSearchEntity : RealmObject {
    @PrimaryKey
    var query: String = ""
}
