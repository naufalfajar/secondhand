package id.finalproject.binar.secondhand.repository.network

//import id.finalproject.binar.secondhand.database.dao.NotificationDao
import id.finalproject.binar.secondhand.service.ApiService

class NotificationRepository(private val apiService: ApiService) {

//    fun getNotification() = liveData(Dispatchers.IO) {
//        emit(Resource.loading(null))
//        try {
//            val response = apiService.getNotification("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6ImpvaG5kb2VAbWFpbC5jb20iLCJpYXQiOjE2NTQ5MjcxODZ9.fghFryd8OPEHztZlrN50PtZj0EC7NWFVj2iPPN9xi1M")
//            val notification = response
//            val notificationList = notification.map { notification ->
//                notification.toMovieEntity()
//            }
//            notificationDao.deleteNotification()
//            notificationDao.insertNotification(notificationList)
//
//            emit(Resource.success(repository.getNotification("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6ImpvaG5kb2VAbWFpbC5jb20iLCJpYXQiOjE2NTQ5MjcxODZ9.fghFryd8OPEHztZlrN50PtZj0EC7NWFVj2iPPN9xi1M")))
//        } catch (e: Exception) {
//            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
//        }
//    }
//
//    fun getNotificationById(notificationId: Int) = liveData(Dispatchers.IO) {
//        emit(Resource.loading(null))
//        try {
//            emit(
//                Resource.success(
//                    repository.getNotificationById(
//                        notificationId,
//                        "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6ImpvaG5kb2VAbWFpbC5jb20iLCJpYXQiOjE2NTQ5MjcxODZ9.fghFryd8OPEHztZlrN50PtZj0EC7NWFVj2iPPN9xi1M"
//                    )
//                )
//            )
//        } catch (e: Exception) {
//            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
//        }
//    }

//    fun getNotification(): LiveData<Result<List<Notification>>> = liveData {
//        emit(Status.LOADING)
//        try {
//            val response = apiService.getNotification(access_token)
//            val movies = response.results
//            val movieList = movies.map { movie ->
//                movie.toMovieEntity()
//            }
//            movieDao.deleteMovies()
//            movieDao.insertMovies(movieList)
//        } catch (e: Exception) {
//            emit(Status.ERROR(e.message.toString()))
//        }
//        val localData: LiveData<Result<List<MovieEntity>>> =
//            movieDao.getMovies().map { Result.Success(it) }
//        emitSource(localData)
//    }
//
//    fun getNotificationById(notificationId: Int): LiveData<Result<Notification>> = liveData {
//        emit(Result.Loading)
//        try {
//            val response = apiService.getDetailMovie(API_KEY, movieId)
//            val movie = response.toMovieEntity()
//            movieDao.deleteMovies()
//            movieDao.insertMovie(movie)
//        } catch (e: Exception) {
//            emit(Result.Error(e.message.toString()))
//        }
//        val localData: LiveData<Result<MovieEntity>> =
//            movieDao.getMovie(movieId).map { Result.Success(it) }
//        emitSource(localData)
//    }

    suspend fun getNotification(access_token: String) = apiService.getNotification(access_token)

    suspend fun getNotificationById(notificationId: Int, access_token: String) =
        apiService.getNotificationById(notificationId, access_token)

    suspend fun patchNotificationById(notificationId: Int, access_token: String) =
        apiService.patchNotificationById(notificationId, access_token)
}