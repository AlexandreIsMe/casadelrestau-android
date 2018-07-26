package com.cheerz.casadelrestau.login.singIn

import android.content.Context
import com.cheerz.casadelrestau.login.LoginDataChecker
import com.cheerz.casadelrestau.login.signUp.SignUp
import com.cheerz.casadelrestau.login.signUp.SignUpView
import io.reactivex.disposables.CompositeDisposable

class SignInPresenter(private val view: SignInView, private val listener: SignIn.Listener) : SignIn.Presenter {

    private val disposables = CompositeDisposable()

    override fun onSignInClicked(email : String, password : String) {
        if (LoginDataChecker.checkData(email, password))
            TODO()
        else
            view.signInNotValid()
    }

    override fun onSignUpClicked() {
        listener.onSignUpClicked()
    }

}