package app.country.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import app.country.R
import app.country.databinding.FragmentHomeBinding
import app.country.model.Status
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.koin.androidx.navigation.koinNavGraphViewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by koinNavGraphViewModel(R.id.mobile_navigation)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_home_to_filterDialogFragment)
        }

        viewModel.status.observe(viewLifecycleOwner) { status ->
            binding.progressBar.isVisible = status == Status.LOADING
        }
        val countryAdapter = CountryAdapter {

        }

        binding.countriesRv.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = countryAdapter
        }

        viewModel.countries.observe(viewLifecycleOwner) {
            countryAdapter.submitList(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}