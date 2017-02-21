package library.basecontroller;

import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import library.mvp.FragmentPresenter;

/**
 * @author Sachin Narang
 */

public abstract class PreferenceCompatFragment<T extends FragmentPresenter> extends PreferenceFragment {

    private T presenter;

    /**
     * @return return the presenter
     */
    public T getPresenter() {
        return presenter;
    }

    /**
     * In child fragment you must provide presenter implementation to this,
     * otherwise it will give a null pointer exception
     *
     * @return return the presenterImp instance
     */
    abstract protected T onAttachPresenter();

    /**
     * initialized the ui component
     *
     * @param view view inflated from xml
     */
    abstract protected void initUI(View view);

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        presenter = onAttachPresenter();
        presenter.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (presenter != null) presenter.onCreate();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        if (presenter != null) presenter.onCreateView(inflater, container, savedInstanceState);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUI(view);
        if (presenter != null) presenter.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (presenter != null) presenter.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (presenter != null) presenter.onStart();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (presenter != null) presenter.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (presenter != null) presenter.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (presenter != null) presenter.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (presenter != null) presenter.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (presenter != null) presenter.onDetach();
    }
}
