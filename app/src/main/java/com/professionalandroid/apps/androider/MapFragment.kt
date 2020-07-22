//package org.techtown.app
//
//import android.Manifest
//import android.R
//import android.content.pm.PackageManager
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.Toast
//import androidx.core.app.ActivityCompat
//import androidx.fragment.app.Fragment
//import androidx.fragment.app.FragmentTransaction
//import com.google.android.gms.common.api.GoogleApiClient
//import com.google.android.gms.location.FusedLocationProviderClient
//import com.google.android.gms.location.LocationServices
//import com.google.android.gms.maps.CameraUpdateFactory
//import com.google.android.gms.maps.GoogleMap
//import com.google.android.gms.maps.OnMapReadyCallback
//import com.google.android.gms.maps.SupportMapFragment
//import com.google.android.gms.maps.model.LatLng
//import com.google.android.gms.maps.model.MarkerOptions
//import kotlinx.android.synthetic.main.fragment_map.*
//
//
//class MapFragment : Fragment(), OnMapReadyCallback {
//    lateinit var mMap: GoogleMap
//    lateinit var fragment: SupportMapFragment
//    lateinit var mGoogleApiClient: GoogleApiClient
//    lateinit var mFusedLocationClient: FusedLocationProviderClient
//    private val REQUEST_CODE_PERMISSIONS = 1000
//
//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        onRequestPermission() // 사용자에게 위치 권한 허락을 받음
//        //initMapFragment()
//        val view: View = inflater.inflate(R.layout.la)
//        // Inflate the layout for this fragment
//        btn_my_location.setOnClickListener{
//            initLocation()
//        }
//        return view
//    }
//
//    private fun initMapFragment(){
//        fragment = childFragmentManager.findFragmentById(R.id.Main_map_Fragment) as SupportMapFragment
//        fragment.getMapAsync(this) // getMapAsync는 비동기 방식으로 장식
//    }
//
//    //내위치를 마커로 찍음
//
//    override fun onMapReady(googleMap: GoogleMap?) {
//        googleMap?.let { mMap = it }
//        initLocation() // 현재치 마커 표시
//
//        //googldMap에 현재위치 눌렀을떄 작동하는지 확인하는 method
//        //누르면 toastMessage 나타남
//        googleMap?.setOnInfoWindowClickListener {
//            var toastView = Toast.makeText(requireContext(), "확인완료", Toast.LENGTH_LONG)
//            toastView.show()
//        }
//    }
//
//    //내위치를 마커로 찍음
//    private fun initLocation() {
//        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
//        mFusedLocationClient.lastLocation.addOnSuccessListener {
//            val myLocation = LatLng(it.latitude, it.longitude)
//            mMap.addMarker(
//                MarkerOptions()
//                    .position(myLocation)
//                    .title("현재위치")
//            )
//            mMap.moveCamera(CameraUpdateFactory.newLatLng(myLocation))
//            mMap.animateCamera(CameraUpdateFactory.zoomTo(17.0f))
//        }
//    }
//
//    /*내 위치를 얻기위해 사용자에게 권한을 요청하는 메소드*/
//    fun onRequestPermission() {
//        if (ActivityCompat.checkSelfPermission(
//                requireContext(), Manifest.permission.ACCESS_FINE_LOCATION
//            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
//                requireContext(),
//                Manifest.permission.ACCESS_COARSE_LOCATION
//            ) != PackageManager.PERMISSION_GRANTED
//        ) {
//            ActivityCompat.requestPermissions(
//                requireActivity(),
//                arrayOf(
//                    Manifest.permission.ACCESS_FINE_LOCATION,
//                    Manifest.permission.ACCESS_COARSE_LOCATION
//                ),
//                REQUEST_CODE_PERMISSIONS
//            )
//            return
//        }
//    }
//
//    /*사용자에게 권한을 요청한후 (수락이지 거절인지) 확인하는 메소드
//    * 거부 되면 toast message 출력*/
//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        when (requestCode) {
//            REQUEST_CODE_PERMISSIONS ->
//                Toast.makeText(requireContext(), "위치 확인 거부되었습니다", Toast.LENGTH_LONG).show()
//        }
//    }
//
//    private fun setChildFragment(child: Fragment) {
//        val childFt: FragmentTransaction = childFragmentManager.beginTransaction()
//        if (!child.isAdded) {
//            childFt.replace(R.id.fragment_container, child)
//            childFt.addToBackStack(null)
//            childFt.commit()
//        }
//    }
//}
