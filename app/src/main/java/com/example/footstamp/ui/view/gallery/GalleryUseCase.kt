package com.example.footstamp.ui.view.gallery

import android.content.Context
import com.example.footstamp.data.model.Diary
import com.example.footstamp.data.repository.DiaryRepository
import javax.inject.Inject

class FetchDiariesUseCase @Inject constructor(
    private val diaryRepository: DiaryRepository
) {
    suspend operator fun invoke(): List<Diary>? {
        return diaryRepository.fetchDiaries()
    }
}

class WriteDiaryUseCase @Inject constructor(
    private val diaryRepository: DiaryRepository
) {
    suspend operator fun invoke(diary: Diary, context: Context): Boolean {
        return diaryRepository.writeDiary(diary, context)
    }
}

class ReadDiaryUseCase @Inject constructor(
    private val diaryRepository: DiaryRepository
) {
    suspend operator fun invoke(diaryId: String): Diary? {
        return diaryRepository.readDiary(diaryId)
    }
}

class DeleteDiaryUseCase @Inject constructor(
    private val diaryRepository: DiaryRepository
) {
    suspend operator fun invoke(diaryId: String): Boolean {
        return diaryRepository.deleteDiary(diaryId)
    }
}

class UpdateDiaryUseCase @Inject constructor(
    private val diaryRepository: DiaryRepository
) {
    suspend operator fun invoke(diary: Diary, context: Context): Boolean {
        return diaryRepository.updateDiary(diary, context)
    }
}

class ShareDiaryUseCase @Inject constructor(
    private val diaryRepository: DiaryRepository
) {
    suspend operator fun invoke(diaryId: String): Boolean {
        return diaryRepository.shareDiary(diaryId)
    }
}

class GetAllDiaryDaoUseCase @Inject constructor(
    private val diaryRepository: DiaryRepository
) {
    suspend operator fun invoke(): List<Diary> {
        return diaryRepository.getAllDao()
    }
}

class DeleteAllDaoUseCase @Inject constructor(
    private val diaryRepository: DiaryRepository
) {
    suspend operator fun invoke() {
        return diaryRepository.deleteAllDao()
    }
}