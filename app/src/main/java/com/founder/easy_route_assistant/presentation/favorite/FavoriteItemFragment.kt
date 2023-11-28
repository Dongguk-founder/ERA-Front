package com.founder.easy_route_assistant.presentation.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.founder.easy_route_assistant.R
import com.founder.easy_route_assistant.databinding.FragmentFavoriteItemBinding

class FavoriteItemFragment : Fragment() {
    private var _binding: FragmentFavoriteItemBinding? = null
    private val binding: FragmentFavoriteItemBinding
        get() = requireNotNull(_binding) { "오류 발생" }
    private val viewModel by viewModels<FavoriteViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentFavoriteItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val favoriteAdapter = FavoriteAdapter(requireContext())
        binding.rvFavorites.adapter = favoriteAdapter
        favoriteAdapter.setFavoriteList(viewModel.mockFavoriteList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
