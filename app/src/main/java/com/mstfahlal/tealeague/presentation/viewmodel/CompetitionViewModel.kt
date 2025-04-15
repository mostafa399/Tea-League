package com.mstfahlal.tealeague.presentation.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    var isInitialLoad = true

    init {
        loadCompetitions()
    }

    fun loadCompetitions() {
        viewModelScope.launch {
            // Only emit Loading if not initial load
            if (!isInitialLoad) {
                _competitions.emit(Resource.Loading())
            }
            _competitions.emit(repo.getCompetition())
            isInitialLoad = false
        }
    }

    fun refreshCompetitions() {
        viewModelScope.launch {
            _competitions.emit(Resource.Loading())
            _competitions.emit(repo.getCompetition(forceRefresh = true))
        }
    }

    fun setCompetitionSelected(competition: DomainCompetition) {
        _selectedCompetition.value = competition
    }

    fun setCompetitionSelected(competitionId: String) {
        viewModelScope.launch {
            _competitions.value.data?.competitions?.find { it.id.toString() == competitionId }
                ?.let { competition ->
                    _selectedCompetition.value = competition
                }
        }
    }
}