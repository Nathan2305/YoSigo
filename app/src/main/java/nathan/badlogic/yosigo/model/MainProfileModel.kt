package nathan.badlogic.yosigo.model

import android.content.Intent
import com.backendless.Backendless
import com.backendless.async.callback.AsyncCallback
import com.backendless.exceptions.BackendlessFault
import com.backendless.persistence.DataQueryBuilder
import nathan.badlogic.yosigo.Utils.StaticMethods
import nathan.badlogic.yosigo.dao.Area
import nathan.badlogic.yosigo.presenter.MainProfilePresenter
import nathan.badlogic.yosigo.view.activities.LoginActivity

class MainProfileModel(private var presenter: MainProfilePresenter) {
    var listCategories = mutableListOf<Area>()

    fun showCategories(businessObjectId: String) {
        val dataQuery: DataQueryBuilder = DataQueryBuilder.create()
        dataQuery.setPageSize(100)
        dataQuery.whereClause = "ownerId='$businessObjectId'"
        dataQuery.setSortBy("name ASC")
        loadCategories(dataQuery)

    }

    private fun loadCategories(dataQuery: DataQueryBuilder) {
        presenter.notifyViewShowSpinkitLoading(true)
        Backendless.Persistence.of(Area::class.java)
            .find(dataQuery, object : AsyncCallback<List<Area>> {
                override fun handleResponse(listFound: List<Area>) {
                    if (listFound.isNotEmpty()) {
                        listCategories.addAll(listFound)
                        dataQuery.prepareNextPage()
                        loadCategories(dataQuery)
                    } else {
                        if (listCategories.isNotEmpty()) {
                            presenter.notifyViewShowAllCategories(listCategories)
                        } else {
                            presenter.notifyViewShowEmptyCategories()
                        }
                    }
                    presenter.notifyViewShowSpinkitLoading(false)
                }

                override fun handleFault(fault: BackendlessFault?) {
                    presenter.notifyViewShowSpinkitLoading(false)
                    val msg = "Error cargando áreas: ${fault!!.message}"
                    presenter.notifyViewShowInfo(msg)
                }
            }
            )
    }

    fun createNewArea(newAreaValue: String, newDescAreaValue: String, newFloorAreaValue: String) {
        presenter.notifyViewShowSpinkitLoading(true)
        val area = Area()
        area.name = newAreaValue
        area.description = newDescAreaValue
        area.floor = newFloorAreaValue
        Backendless.Persistence.of(Area::class.java).save(area, object : AsyncCallback<Area> {
            override fun handleResponse(response: Area) {
                presenter.notifyViewShowSpinkitLoading(false)
                val areaName = area.name
                val updatedListCategories = mutableListOf<Area>()
                if (listCategories.isNotEmpty()) {
                    updatedListCategories.addAll(listCategories)
                }
                updatedListCategories.add(area)
                presenter.notifyViewSuccessfulAreaCreated(areaName, updatedListCategories)
                listCategories = updatedListCategories
            }

            override fun handleFault(fault: BackendlessFault?) {
                presenter.notifyViewShowSpinkitLoading(false)
                val msg = "Error creando área: ${fault!!.message}"
                presenter.notifyViewShowInfo(msg)
            }
        })
    }

    fun initLogOut() {
        presenter.notifyViewShowSpinkitLoading(true)
        Backendless.UserService.logout(object : AsyncCallback<Void> {
            override fun handleResponse(response: Void?) {
                presenter.notifyViewSuccessfulLogOut()
                presenter.notifyViewShowSpinkitLoading(false)
            }

            override fun handleFault(fault: BackendlessFault?) {
                presenter.notifyViewShowSpinkitLoading(false)
                val msg = "Error cerrando sesión: ${fault!!.message}"
                presenter.notifyViewShowInfo(msg)
            }
        })
    }

}