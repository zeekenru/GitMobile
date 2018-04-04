package com.kovapss.gitmobile.domain

import com.kovapss.gitmobile.HttpCodes
import com.kovapss.gitmobile.Scopes
import com.kovapss.gitmobile.entities.repository.Collaborator
import com.kovapss.gitmobile.model.repositories.CollaboratorsRepository
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException
import toothpick.Toothpick
import javax.inject.Inject


class CollaboratorsInteractor {

    @Inject
    lateinit var repository: CollaboratorsRepository

    init {
        Toothpick.inject(this, Toothpick.openScopes(Scopes.APP_SCOPE, Scopes.NETWORK_SCOPE))
    }

    fun getRepositoryCollaborators(ownerLogin: String, repositoryName: String, page : Int): Single<List<Collaborator>>
            = repository.getRepositoryCollaborators(ownerLogin, repositoryName, page)
            .subscribeOn(Schedulers.io())
            .map { it.body() }

    fun addRepositoryCollaborator(ownerLogin: String, repositoryName: String, username: String)
            = repository.addRepositoryCollaborator(ownerLogin, repositoryName, username)
            .subscribeOn(Schedulers.io())
            .map { it.code().toString() == HttpCodes.CODE_204 }
            .onErrorReturn { t ->
                t as HttpException
                t.code().toString() == HttpCodes.CODE_204
            }

    fun searchUserAsCollaborator(query : String) : Single<List<String>> =
            repository.findUsers(query)
                    .subscribeOn(Schedulers.io())
                    .map { it.body() }
                    .flatMapObservable({Observable.fromIterable(it.results)})
                    .map { it.login }
                    .toList()



    fun deleteRepositoryCollaborator(ownerLogin: String, repositoryName: String, username: String): Single<Boolean>
            = repository.deleteRepositoryCollaborator(ownerLogin, repositoryName, username)
            .subscribeOn(Schedulers.io())
            .map { it.code().toString() == HttpCodes.CODE_204 }
            .onErrorReturn { t ->
                t as HttpException
                t.code().toString() == HttpCodes.CODE_204
            }


}