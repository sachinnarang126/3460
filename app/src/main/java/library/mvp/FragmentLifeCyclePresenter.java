package library.mvp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author Sachin Narang
 */

public interface FragmentLifeCyclePresenter extends LifeCyclePresenter {

    void onAttach(Context context);

    void onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState);

    void onViewCreated(View view, @Nullable Bundle savedInstanceState);

    void onDestroyView();

    void onDetach();

    void onActivityCreated(Bundle savedInstanceState);

}
