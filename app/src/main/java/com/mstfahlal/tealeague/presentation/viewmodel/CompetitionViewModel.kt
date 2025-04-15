package com.mstfahlal.tealeague.presentation.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mstfahlal.tealeague.data.mappers.toDomainCompetition
import com.mstfahlal.tealeague.domain.model.DomainCompetition
import com.mstfahlal.tealeague.domain.model.DomainCompetitions
import com.mstfahlal.tealeague.domain.repository.ICompetitionRepository
import com.mstfahlal.tealeague.domain.usecase.IGetAllCompetition
import com.mstfahlal.tealeague.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompetitionViewModel @Inject constructor(
    private val repo: ICompetitionRepository
) : ViewModel() {
    private val _competitions = MutableStateFlow<Resource<DomainCompetitions>>(Resource.Unspecified())
    val competitions: StateFlow<Resource<DomainCompetitions>> = _competitions

    private val _selectedCompetition = MutableStateFlow<DomainCompetition?>(null)
    val selectedCompetition: StateFlow<DomainCompetition?> = _selectedCompetition

    private var hasLoadedInitially = false
//
//    init {
//        loadCompetitions()
//    }

    fun loadCompetitions() {
        viewModelScope.launch {
            // Only emit Loading if not initial load
            if (hasLoadedInitially) {
                _competitions.emit(Resource.Loading())
            }
            _competitions.emit(repo.getCompetition())
            hasLoadedInitially  = true
        }
    }

    fun refreshCompetitions() {
        viewModelScope.launch {
            _competitions.emit(Resource.Loading())
            _competitions.emit(repo.getCompetition(forceRefresh = true))
        }
    }

    fun setCompetitionSelected(competitionId: String) {
        viewModelScope.launch {
            // Get from local cache instead of making API call
            val allCompetitions = repo.getFromLocal().competitions
            allCompetitions?.find { it.id.toString() == competitionId }?.let { competition ->
                _selectedCompetition.value = competition.toDomainCompetition()
            }
        }
    }

}