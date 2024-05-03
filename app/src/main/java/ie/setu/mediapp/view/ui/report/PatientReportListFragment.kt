package ie.setu.mediapp.view.ui.report

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ie.setu.mediapp.databinding.FragmentPatientReportListBinding

/**
 * A simple [Fragment] subclass. * Use the [PatientReportListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PatientReportListFragment : Fragment() {

    private var _binding: FragmentPatientReportListBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val slideshowViewModel =
            ViewModelProvider(this).get(ReportsViewModel::class.java)

        _binding = FragmentPatientReportListBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textSlideshow
        slideshowViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}