package kr.hs.dgsw.smartschool.fepay_android.view.dialog

import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentManager
import io.reactivex.observers.DisposableSingleObserver
import kr.hs.dgsw.smartschool.fepay_android.R
import kr.hs.dgsw.smartschool.fepay_android.base.BaseDialog
import kr.hs.dgsw.smartschool.fepay_android.database.TokenManager
import kr.hs.dgsw.smartschool.fepay_android.databinding.DialogRoomCodeBinding
import kr.hs.dgsw.smartschool.fepay_android.network.service.UserService
import kr.hs.dgsw.smartschool.fepay_android.util.Utils
import kr.hs.dgsw.smartschool.fepay_android.view.activity.MainActivity

class RoomCodeDialog(val token: String) : BaseDialog<DialogRoomCodeBinding>() {

    override val layoutId: Int
        get() = R.layout.dialog_room_code

    private val service: UserService
            = Utils.RETROFIT.create(UserService::class.java)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnWrite.setOnClickListener {
            addDisposable(service.
                join(
                    token, Integer.parseInt(binding.inputId.text.toString())
                ).map { if (it.isSuccessful) it.message() else throw Exception("코드 오류") },
                object : DisposableSingleObserver<String>() {
                    override fun onSuccess(t: String) {
                        TokenManager(context!!).token = "Bearer $token"
                        startActivityWithFinish(MainActivity::class.java)
                    }

                    override fun onError(e: Throwable) {
                        simpleToast(e.message)
                    }
                })
        }
    }

    fun show(fragmentManager: FragmentManager) {
        super.show(fragmentManager, tag)
    }

}