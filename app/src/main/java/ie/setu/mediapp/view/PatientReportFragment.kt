package ie.setu.mediapp.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ie.setu.mediapp.Model.Reports
import ie.setu.mediapp.R
import ie.setu.mediapp.ViewModels.ReportActivityVM
import ie.setu.mediapp.databinding.FragmentDrReportBinding
import ie.setu.mediapp.view.Adapter.ItemReportAdapter

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
/**
 * A simple [Fragment] subclass.
 * Use the [PatientReportFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PatientReportFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    val repoprtList = mutableListOf<Reports>()
    private val viewModel: ReportActivityVM by viewModels()
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ItemReportAdapter
    private lateinit var sharedPreferences: SharedPreferences

    private var _binding: FragmentDrReportBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //val homeViewModel = ViewModelProvider(this).get(HomeDrViewModel::class.java)

        _binding = FragmentDrReportBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Get the SharedPreferences instance
        sharedPreferences = requireActivity().getSharedPreferences("auth_pref", Context.MODE_PRIVATE)
        // Initialize your RecyclerView and Adapter
        recyclerView = binding.reportRecyclerView
        ///recyclerView = findViewById(R.id.slotRecyclerView)
        adapter = ItemReportAdapter(repoprtList, ::onSelectAction)
        //recyclerView.layoutManager = LinearLayoutManager(this)
        // Set a horizontal LinearLayoutManager on the RecyclerView
        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter

        getReportList()

        return root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment PatientReportFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PatientReportFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
    private fun getReportList() {
        val drId = sharedPreferences.getString("id", "id").toString()
        viewModel.getMyReportPtList(drId) { items ->
            if (items != null) {
                // Successfully retrieved items of type 1
                repoprtList.removeAll(repoprtList)
                repoprtList.addAll(items)
                adapter.notifyDataSetChanged()
            } else {
                // Failed to retrieve items
                println("Failed to retrieve items of type 1.")
            }
        }
    }

    private fun onSelectAction(position: Int) {
        val intent = Intent(requireContext(), ReportDetailsActivity::class.java)
        //val user = Users("John Doe", "john.doe@example.com", "1234567890", 1, "password", "catId", "place")
        intent.putExtra("ptName", repoprtList[position].ptName)
        intent.putExtra("ptId", repoprtList[position].ptId)
        intent.putExtra("drName", repoprtList[position].drName)
        intent.putExtra("drId", repoprtList[position].drId)
        intent.putExtra("rpId", repoprtList[position].rpId)
        intent.putExtra("rpId", repoprtList[position].rpId)
        intent.putExtra("rpDate", repoprtList[position].rpDate)
        intent.putExtra("report", repoprtList[position].report)
        startActivity(intent)
    }
}