package com.example.footstamp.data.repository

import com.example.footstamp.data.dao.ProfileDao
import com.example.footstamp.data.model.Profile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ProfileRepository @Inject constructor(private val profileDao: ProfileDao) {

    val profile: Flow<Profile> = profileDao.getProfile().flowOn(Dispatchers.IO).conflate()
}