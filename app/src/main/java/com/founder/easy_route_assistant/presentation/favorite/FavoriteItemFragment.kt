package com.founder.easy_route_assistant.presentation.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.founder.easy_route_assistant.Utils.MyApplication
import com.founder.easy_route_assistant.Utils.showToast
import com.founder.easy_route_assistant.data.model.response.ResponseFavoriteListDto
import com.founder.easy_route_assistant.data.service.ServicePool
import com.founder.easy_route_assistant.databinding.FragmentFavoriteItemBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FavoriteItemFragment : Fragment() {
    private var _binding: FragmentFavoriteItemBinding? = null
    private val binding: FragmentFavoriteItemBinding
        get() = requireNotNull(_binding) { "오류 발생" }

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

        setFavoriteItemList()
        onClickBack()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setFavoriteItemList() {
        val header = MyApplication.prefs.getString("jwt", "")
        ServicePool.favoriteListService.getFavoriteList(header)
            .enqueue(object : Callback<ResponseFavoriteListDto> {
                override fun onResponse(
                    call: Call<ResponseFavoriteListDto>,
                    response: Response<ResponseFavoriteListDto>,
                ) {
                    when (response.code()) {
                        200 -> {
                            context!!.showToast("즐겨찾기 목록입니다.")
                            val data: ResponseFavoriteListDto = response.body()!!
                            setFavoriteItemAdapter(data.favoriteList)
                        }

                        404 -> context!!.showToast("즐겨찾기 추가한 값이 없습니다.")
                        else -> context!!.showToast("서버 에러 발생")
                    }
                }

                override fun onFailure(call: Call<ResponseFavoriteListDto>, t: Throwable) {
                    context!!.showToast("네트워크 오류 발생")
                }
            })
    }

    private fun setFavoriteItemAdapter(favoriteItemList: List<ResponseFavoriteListDto.FavoriteList>) {
        val favoriteItemAdapter = FavoriteAdapter(requireContext(), ::deleteOnClick)
        favoriteItemAdapter.setFavoriteList(favoriteItemList)
        binding.rvFavorites.adapter = favoriteItemAdapter
    }

    private fun deleteOnClick(
        id: Long
    ){
        val header = MyApplication.prefs.getString("jwt", "")
        ServicePool.favoriteListService.deleteFavorite(header, id)
            .enqueue(object : Callback<ResponseFavoriteListDto> {
                override fun onResponse(
                    call: Call<ResponseFavoriteListDto>,
                    response: Response<ResponseFavoriteListDto>,
                ) {
                    when (response.code()) {
                        200 -> {
                            context!!.showToast("즐겨찾기에서 삭제했습니다.")
                            val data: ResponseFavoriteListDto = response.body()!!
                            setFavoriteItemAdapter(data.favoriteList)
                        }

                        else -> context!!.showToast("서버 에러 발생")
                    }
                }

                override fun onFailure(call: Call<ResponseFavoriteListDto>, t: Throwable) {
                    context!!.showToast("네트워크 오류 발생")
                }
            })
    }

    private fun onClickBack(){
        binding.btnFavoriteBack.setOnClickListener {
            activity?.supportFragmentManager
                ?.beginTransaction()
                ?.remove(this)
                ?.commit()
        }
    }
}
