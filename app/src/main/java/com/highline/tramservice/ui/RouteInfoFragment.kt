package com.highline.tramservice.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.highline.tramservice.MainActivity
import com.highline.tramservice.R
import com.highline.tramservice.adapter.RouteInfoRecyclerViewAdapter
import com.highline.tramservice.databinding.RouteInfoFragmentBinding
import com.highline.tramservice.helper.Constants
import com.highline.tramservice.helper.Helper.isNetworkAvailable
import com.highline.tramservice.network.SessionManager
import com.highline.tramservice.viewmodel.MyTramViewModel
import kotlinx.coroutines.*
import java.util.*
import kotlin.collections.ArrayList

class RouteInfoFragment : Fragment() {

    private val TAG: String = "MyTramFragment"

    private var mBinding: RouteInfoFragmentBinding? = null
    private lateinit var mViewModel: MyTramViewModel
    private lateinit var mSessionManager: SessionManager
    private val mIOScope = CoroutineScope(Dispatchers.IO + Job())
    private lateinit var mRouteInfoNorthRecyclerViewAdapter: RouteInfoRecyclerViewAdapter
    private lateinit var mRouteInfoSouthRecyclerViewAdapter: RouteInfoRecyclerViewAdapter
    private var mProgressStateStack: Stack<Constants.APP_STATE> = Stack<Constants.APP_STATE>()

    /**
     * Function initialises the functions and variables
     */
    private fun initialise() {
        (this.activity as MainActivity).title

        mSessionManager = SessionManager(requireContext())
        mRouteInfoNorthRecyclerViewAdapter = RouteInfoRecyclerViewAdapter()
        mRouteInfoSouthRecyclerViewAdapter = RouteInfoRecyclerViewAdapter()
        with(mBinding) {
            this?.let {
                idTextviewNorthTitle.text = resources.getString(R.string.north)
                with(idRecyclerNorthRoute) {
                    apply {
                        layoutManager = LinearLayoutManager(requireActivity())
                        adapter = mRouteInfoNorthRecyclerViewAdapter
                    }
                }
                idTextviewSouthTitle.text = resources.getString(R.string.south)
                with(idRecyclerSouthRoute) {
                    apply {
                        layoutManager = LinearLayoutManager(requireActivity())
                        adapter = mRouteInfoSouthRecyclerViewAdapter
                    }
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.route_info_fragment,
            container,
            false
        )
        mBinding?.apply {
            viewModel = mViewModel
            lifecycleOwner = this@RouteInfoFragment
        }
        initialise()
        setupLiveData()
        return mBinding?.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mViewModel = ViewModelProvider(this).get(MyTramViewModel::class.java)
    }

    /**
     * Function sets up LiveData /Observers
     */
    private fun setupLiveData() {
        // Read from session manager and check first.
        with(mViewModel) {
            applicationState.observe(viewLifecycleOwner, Observer {
                handleApplicationState(it)
            })

            // Fetches route information.
            getRouteInformation()

            // On Clear Observer
            onClickClearLiveData.observe(viewLifecycleOwner, Observer {
                clearRouteInfo();
            })

            // On Refresh Observer
            onClickRefreshLiveData.observe(viewLifecycleOwner, Observer {
                getRouteInformation()
                showToastMessage(resources.getString(R.string.refresh_message))
            })
        }
    }

    /**
     * Function request Device Token from Server through RESTApi service
     */
    private fun getRouteInformation() {
        with(mViewModel) {
            if (requireContext().isNetworkAvailable()) {
                var deviceToken: String? = mSessionManager.fetchDeviceToken()
                if (!deviceToken.isNullOrEmpty()) {
                    deviceToken?.let {
                        getTramRouteInformation(it)
                    }
                } else {
                    with(getDeviceToken()) {
                        observe(viewLifecycleOwner, Observer { data ->
                            if (data != null && data.hasResponse) {
                                for (responseObject in data.responseObject) {
                                    if (responseObject.__type == Constants.DEVICE_TOKEN_INFO &&
                                        !responseObject.DeviceToken.isNullOrEmpty()
                                    ) {
                                        with(responseObject.DeviceToken) {
                                            mSessionManager.saveDeviceToken(this)
                                            getTramRouteInformation(this)
                                        }
                                    }
                                }
                            } else if (data != null && data.hasError) { // Show Error Message
                                showToastMessage(
                                    data.errorMessage.toString()
                                        .plus(resources.getString(R.string.message_please_refresh))
                                )
                            }
                        })
                    }
                }
            } else {
                Toast.makeText(
                    activity,
                    resources.getString(R.string.error_message_no_internet_connection),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    /**
     * Function clears RouteInfo from display.
     */
    private fun clearRouteInfo() {
        with(mRouteInfoNorthRecyclerViewAdapter) {
            updateRoutesInfoList(null)
            notifyDataSetChanged()
        }
        with(mRouteInfoSouthRecyclerViewAdapter) {
            updateRoutesInfoList(null)
            notifyDataSetChanged()
        }
        showToastMessage(resources.getString(R.string.clear_message))
    }

    /**
     * Function retrieves Tran Route Information using RESTApi
     */
    private fun getTramRouteInformation(deviceToken: String) {
        with(mViewModel) {
            northBoundTramRouteInfo.observe(viewLifecycleOwner, Observer { data ->
                with(mRouteInfoNorthRecyclerViewAdapter) {
                    updateRoutesInfoList(data)
                    notifyDataSetChanged()
                }
            })
            southBoundTramRouteInfo.observe(viewLifecycleOwner, Observer { data ->
                with(mRouteInfoSouthRecyclerViewAdapter) {
                    updateRoutesInfoList(data)
                    notifyDataSetChanged()
                }
            })
            dataNotFoundErrorMessage.observe(viewLifecycleOwner, Observer {
                Log.d(TAG, "Error Received Data : ${it}")
                showToastMessage(it.plus(resources.getString(R.string.message_please_refresh)))
            })
            mIOScope.launch {
                val job = ArrayList<Job>()
                mProgressStateStack.push(Constants.APP_STATE.PROCESSING)
                for (stopId in Constants.STOP_ID_LISTS) {
                    job.add(launch {
                        getTramRouteInfo(stopId, deviceToken)
                    })
                }
                job.joinAll()
            }
        }
    }

    /**
     * Function displays progress of the application.
     */
    private fun handleApplicationState(appState: Constants.APP_STATE) {
        when (appState) {
            Constants.APP_STATE.REQUEST_SENT -> {
            }
            Constants.APP_STATE.RESPONSE_SUCCESS,
            Constants.APP_STATE.RESPONSE_SUCCESS01,
            Constants.APP_STATE.RESPONSE_SUCCESS02 -> {
                if (!mProgressStateStack.isEmpty()) mProgressStateStack.pop()
            }
            Constants.APP_STATE.RESPONSE_FAILED -> {
                if (!mProgressStateStack.isEmpty()) mProgressStateStack.pop()
                // Show Error Message
            }
            Constants.APP_STATE.REQUEST_TO_CLEAR -> {
            }
            Constants.APP_STATE.REQUEST_TO_REFRESH -> {
                mProgressStateStack.push(Constants.APP_STATE.PROCESSING)
            }
            Constants.APP_STATE.UN_KNOW -> {
                // No action. May be for later
            }
        }
        showAppProgressBar()
    }

    /**
     * Functions shows and hides spinning progress
     */
    private fun showAppProgressBar() {
        with(mBinding) {
            if (mProgressStateStack.isEmpty()) {
                this?.idProgressbar?.visibility = View.GONE
            } else {
                this?.idProgressbar?.visibility = View.VISIBLE
            }
        }
    }

    /**
     * Function shows Error message in case fails to get Device Token
     * Show error message on UI Thread
     */
    private fun showToastMessage(errorMessage: Any) {
        CoroutineScope(Dispatchers.Main).launch {
            Toast.makeText(requireActivity(), "${errorMessage}", Toast.LENGTH_LONG).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mBinding = null
    }
}