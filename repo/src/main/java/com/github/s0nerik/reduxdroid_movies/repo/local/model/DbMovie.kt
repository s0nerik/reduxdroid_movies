package com.github.s0nerik.reduxdroid_movies.repo.local.model

import com.github.s0nerik.reduxdroid_movies.model.Movie
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import org.joda.time.DateTime

@Entity
internal class DbMovie {
    @Id(assignable = true)
    var id: Long = 0

    var name: String = ""
    var description: String = ""
    var coverUrl: String = ""

    var rating: Float = 0f
    var releaseDateTime: Long = 0

    var isFavorite: Boolean = false

    fun toLocal() = Movie(
            id = id,
            name = name,
            description = description,
            rating = rating,
            releaseDate = DateTime(releaseDateTime),
            coverUrl = coverUrl,
            isFavorite = isFavorite
    )

    companion object {
        fun fromLocal(m: Movie) = DbMovie().apply {
            id = m.id
            name = m.name
            description = m.description
            rating = m.rating
            releaseDateTime = m.releaseDate.millis
            coverUrl = m.coverUrl
            isFavorite = m.isFavorite
        }
    }
}