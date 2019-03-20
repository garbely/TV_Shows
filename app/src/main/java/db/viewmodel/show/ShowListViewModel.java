package db.viewmodel.show;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import java.util.List;

import db.entity.Show;
import db.repository.ShowRepository;

public class ShowListViewModel extends AndroidViewModel {

    private ShowRepository repository;

    private Application application;

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<List<Show>> observableShows;

    public ShowListViewModel(@NonNull Application application,
                             ShowRepository repository) {
        super(application);

        this.repository = repository;
        this.application = application;

        observableShows = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        observableShows.setValue(null);

        LiveData<List<Show>> clients = repository.getAllShows(application);

        // observe the changes of the entities from the database and forward them
        observableShows.addSource(clients, observableShows::setValue);
    }

    /**
     * A creator is used to inject the account id into the ViewModel
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;

        private final ShowRepository repository;

        public Factory(@NonNull Application application) {
            this.application = application;
            repository = ShowRepository.getInstance();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new ShowListViewModel(application, repository);
        }
    }

    /**
     * Expose the LiveData ClientEntities query so the UI can observe it.
     */
    public LiveData<List<Show>> getShows() {
        return observableShows;
    }
}
