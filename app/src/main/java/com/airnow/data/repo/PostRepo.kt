package com.airnow.data.repo

import android.content.SharedPreferences
import com.airnow.data.DataStatus
import com.airnow.data.db.dao.PostDao
import com.airnow.data.model.Post
import com.airnow.data.model.Source
import com.airnow.data.parser.NewsXmlParser
import com.airnow.util.extention.selectedItem
import com.airnow.util.logThrowable
import kotlinx.coroutines.*
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository that is responsible for operating posts.
 */
@Suppress("UNCHECKED_CAST")
@Singleton
class PostRepo @Inject constructor(
        private val okHttpClient: OkHttpClient,
        private val xmlParser: NewsXmlParser,
        private val postDao: PostDao,
        private val sharedPref: SharedPreferences
) {

    fun getAll() = postDao.getAll()

    fun getBookmarked() = postDao.getBookmarked()

    fun getPostsFromSource() = postDao.getSourcePosts(sharedPref.selectedItem)

    fun getBookmarkedCount() = postDao.getBookmarkedCount()

    fun updatePost(post: Post) = postDao.update(post)

    private fun addPosts(posts: List<Post>) = postDao.insert(posts)


    /**
     * Refresh the posts of the given sources. Sources are fetched and parsed asynchronously. After
     * fetching and parsing, the posts are added to the database.
     *
     * @param coroutineScope
     * @param sources to refresh
     */
    suspend fun refresh(
        coroutineScope: CoroutineScope,
        sources: List<Source>
    ): List<DataStatus<Nothing>> = runBlocking {
        val allPosts = mutableListOf<Post>()

        val asyncFetches = sources.map { source ->
            coroutineScope.async(Dispatchers.IO) {
                val result = fetchAndParsePosts(source)

                when (result) {
                    is DataStatus.Successful -> {
                        result.data?.let { allPosts.addAll(it) }
                        DataStatus.Successful()
                    }
                    else -> result as DataStatus<Nothing>
                }
            }
        }

        val results = asyncFetches.awaitAll()
        addPosts(allPosts)

        results
    }

    private fun fetchAndParsePosts(source: Source): DataStatus<List<Post>> {
        val request = Request.Builder()
            .url(source.url)
            .build()

        return try {
            val response = okHttpClient.newCall(request).execute()

            if (response.isSuccessful) {
                val posts = response.body()?.string()?.let {
                    xmlParser.parse(it, source)
                }
                DataStatus.Successful(posts)
            } else {
                DataStatus.HttpFailed(response.code())
            }
        } catch (e: IOException) {
            logThrowable(e)
            DataStatus.Failed(e)
        }
    }
}