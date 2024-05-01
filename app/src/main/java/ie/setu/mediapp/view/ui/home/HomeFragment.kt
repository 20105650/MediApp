package ie.setu.mediapp.view.ui.home

import android.os.Bundle
import android.content.Intent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ie.setu.mediapp.R
import androidx.lifecycle.ViewModelProvider
import ie.setu.mediapp.view.CategoryActivity
//import ie.setu.mediapp.view.ReportListActivity
//import ie.setu.mediapp.view.SlotActivity
//import ie.setu.mediapp.view.UsersListActivity
import ie.setu.mediapp.databinding.FragmentHomeBinding

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        ////val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner) {
            ///textView.text = it
        }
        binding.catButton.setOnClickListener {
            val intent = Intent(activity, CategoryActivity::class.java)
            startActivity(intent)
        }

        binding.slotButton.setOnClickListener {
          //  val intent = Intent(activity, SlotActivity::class.java)
          //  startActivity(intent)
        }

        binding.reportButton.setOnClickListener {
          //  val intent = Intent(activity, ReportListActivity::class.java)
          //  startActivity(intent)
        }

        binding.bookingButton.setOnClickListener {
          //  val intent = Intent(activity, BookingListActivity::class.java)
          //  startActivity(intent)
        }
        binding.usersButton.setOnClickListener {
           // val intent = Intent(activity, UsersListActivity::class.java)
           // startActivity(intent)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}