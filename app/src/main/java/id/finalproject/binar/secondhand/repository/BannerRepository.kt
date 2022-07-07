package id.finalproject.binar.secondhand.repository

import androidx.room.withTransaction
import id.finalproject.binar.secondhand.model.local.SecondHandDatabase
import id.finalproject.binar.secondhand.service.SecondHandApi
import id.finalproject.binar.secondhand.util.networkBoundResource
import kotlinx.coroutines.delay
import javax.inject.Inject

class BannerRepository @Inject constructor(
    private val api: SecondHandApi,
    private val db: SecondHandDatabase
) {
    private val bannerDao = db.bannerDao()

    fun getBanner() = networkBoundResource(
        query = {
            bannerDao.getBanner()
        },
        fetch = {
            delay(2000)
            api.getBanner()
        },
        saveFetchResult = { banner ->
            db.withTransaction {
                bannerDao.deleteBanner()
                bannerDao.insertBanner(banner)
            }

        }
    )
}