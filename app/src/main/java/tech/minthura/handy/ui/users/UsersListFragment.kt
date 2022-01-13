package tech.minthura.handy.ui.users

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import org.koin.androidx.viewmodel.ext.android.viewModel
import tech.minthura.handy.R
import tech.minthura.handy.domain.models.Result

class UsersListFragment : Fragment() {

    // Lazy Inject ViewModel
    private val viewModel: UsersListViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.users_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getUsers().observe(viewLifecycleOwner, Observer {
            when(it.status){
                Result.Status.SUCCESS -> Log.d("UsersListFragment", "SUCCESS")
                Result.Status.ERROR -> Log.d("UsersListFragment", "ERROR")
                Result.Status.FETCHING -> Log.d("UsersListFragment", "FETCHING")
            }
        })
    }

}
