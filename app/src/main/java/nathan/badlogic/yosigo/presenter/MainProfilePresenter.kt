package nathan.badlogic.yosigo.presenter

import nathan.badlogic.yosigo.dao.Area
import nathan.badlogic.yosigo.model.MainProfileModel
import nathan.badlogic.yosigo.view.interfaces.MainProfileView

class MainProfilePresenter(private var presenterView: MainProfileView) {

    private val model = MainProfileModel(this)

    fun showCategories(businessObjectId: String) {
        model.showCategories(businessObjectId)
    }

    fun notifyViewShowAllCategories(listCategories: MutableList<Area>) {
        presenterView.notifyViewShowAllCategories(listCategories)
    }

    fun notifyViewShowEmptyCategories() {
        presenterView.notifyViewShowEmptyCategories()
    }

    fun createNewArea(newAreaValue:String,newDescAreaValue:String,newFloorAreaValue:String) {
        model.createNewArea(newAreaValue,newDescAreaValue,newFloorAreaValue)
    }

    fun notifyViewShowSpinkitLoading(enableViews: Boolean) {
        presenterView.notifyViewShowSpinkitLoading(enableViews)
    }

    fun notifyViewSuccessfulAreaCreated(areaName: String,newListUpdated: MutableList<Area>) {
        presenterView.notifyViewSuccessfulAreaCreated(areaName,newListUpdated)
    }

    fun notifyViewShowInfo(msg: String) {
        presenterView.notifyViewShowInfo(msg)

    }


}