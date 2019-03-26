package com.airnow.data.repo

import androidx.annotation.WorkerThread
import com.airnow.data.db.dao.SourceDao
import com.airnow.data.model.Source
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository of feed sources.
 */
@Singleton
class SourceRepo @Inject constructor(
    private val sourceDao: SourceDao
) {

    fun getAll() = sourceDao.getSources()

    fun getActives() = sourceDao.getActiveSources()

    fun getActiveCount() = sourceDao.getActiveSourceCount()

    /**
     * Updates a source in the DB.
     *
     * @param source
     */
    @WorkerThread
    fun update(source: Source) = sourceDao.updateSource(source)

    @WorkerThread
    fun insert(sources: List<Source>) = sourceDao.insertSources(sources)
}