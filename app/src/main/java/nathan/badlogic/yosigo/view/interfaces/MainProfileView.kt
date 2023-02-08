package nathan.badlogic.yosigo.view.interfaces

import nathan.badlogic.yosigo.dao.Area

interface MainProfileView {
    fun notifyViewShowAllCategories(listCategories: MutableList<Area>)
    fun notifyViewShowEmptyCategories()
    fun notifyViewShowSpinkitLoading(enableViews: Boolean)
    fun notifyViewSuccessfulAreaCreated(areaName: String, newListUpdated: MutableList<Area>)
    fun notifyViewShowInfo(msg: String)
    fun notifyViewSuccessfulLogOut()


}