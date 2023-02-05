package nathan.badlogic.yosigo.Utils

import android.os.Parcel
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListAdapter
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import nathan.badlogic.yosigo.R
import nathan.badlogic.yosigo.Utils.AdapterAreas.*
import nathan.badlogic.yosigo.dao.Area

class AdapterAreas(private var onItemClicked: (Area) -> Unit) :
    androidx.recyclerview.widget.ListAdapter<Area, Holder>(AreaDiffCallBack()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_adapter_each_area, parent, false)
        return Holder(view) {
            onItemClicked(currentList[it])
        }
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val area = getItem(position)
        holder.areaName.text = area.name
        holder.descArea.text = area.description
        holder.floorArea.text = area.floor
    }


    class AreaDiffCallBack : DiffUtil.ItemCallback<Area>() {
        override fun areItemsTheSame(oldItem: Area, newItem: Area): Boolean {
            return oldItem==newItem
        }

        override fun areContentsTheSame(oldItem: Area, newItem: Area): Boolean {
           return oldItem.name==newItem.name
        }

    }

    class Holder(itemView: View, onItemClicked: (Int) -> Unit) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                // this will be called only once.
                onItemClicked(adapterPosition)
            }
        }

        var areaName: TextView = itemView.findViewById(R.id.area)
        var descArea: TextView = itemView.findViewById(R.id.descArea)
        var floorArea: TextView = itemView.findViewById(R.id.floorArea)
    }


}
