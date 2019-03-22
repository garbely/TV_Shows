package db.viewmodel.show;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import db.BaseApp;
import db.entity.Show;
import db.repository.ShowRepository;
import db.util.OnAsyncEventListener;

public class ShowViewModel extends AndroidViewModel {

    private Application application;
    private ShowRepository repository;
    private final MediatorLiveData<Show> observableShow;

    public ShowViewModel(@NonNull Application application,
                         final String showName, ShowRepository repository) {
        super(application);

        this.application = application;
        this.repository = repository;

        observableShow = new MediatorLiveData<>();
        observableShow.setValue(null);

        LiveData<Show> show = repository.getShow(showName, application);

        observableShow.addSource(show, observableShow::setValue);
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;
        private final String showName;
        private final ShowRepository repository;

        public Factory(@NonNull Application application, String showName) {
            this.application = application;
            this.showName = showName;
            repository = ((BaseApp) application).getShowRepository();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new ShowViewModel(application, showName, repository);
        }
    }

    public LiveData<Show> getShow() {
        return observableShow;
    }

    public void createShow(Show show, OnAsyncEventListener callback) {
        repository.insert(show, callback, application);
    }

    public void updateShow(Show show, OnAsyncEventListener callback) {
        repository.update(show, callback, application);
    }
}
