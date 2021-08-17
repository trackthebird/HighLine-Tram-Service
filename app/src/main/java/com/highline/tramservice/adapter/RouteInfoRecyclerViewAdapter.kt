package com.highline.tramservice.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.highline.tramservice.R
import com.highline.tramservice.databinding.RouteInfoItemBinding
import com.highline.tramservice.helper.Helper
import com.highline.tramservice.model.RoutesInfo

class RouteInfoRecyclerViewAdapter :
    RecyclerView.Adapter<RouteInfoRecyclerViewAdapter.ViewHolder>() {

    private val TAG by lazy {
        "RouteInfoRecyclerViewAdapter"
    }

    private var mRouteInfo: RoutesInfo? = null

    /**
     * Function updated RecyclerView List
     */
    fun updateRoutesInfoList(routeInfo: RoutesInfo?) {
        mRouteInfo = routeInfo
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewBinding: RouteInfoItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.route_info_item,
            parent,
            false
        )
        return ViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.bind()) {
            mRouteInfo?.let {
                with(it.responseObject[position]) {
                    idTextviewTime.text = Helper.dateFormatDotNetDate(this.PredictedArrivalDateTime)
                    idTextviewDestination.text = this.Destination
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return mRouteInfo?.responseObject?.size ?: 0
    }

    /**
     * View Holder class
     */
    class ViewHolder(val viewBinding: RouteInfoItemBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {
        fun bind(): RouteInfoItemBinding {
            return viewBinding
        }
    }
}