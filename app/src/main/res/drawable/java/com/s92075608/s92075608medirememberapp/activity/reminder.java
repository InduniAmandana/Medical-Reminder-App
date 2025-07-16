package com.s92075608.s92075608medirememberapp.activity;

import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.s92075608.s92075608medirememberapp.Model.Task;
import com.s92075608.s92075608medirememberapp.R;
import com.s92075608.s92075608medirememberapp.adapter.TaskAdapter;
import com.s92075608.s92075608medirememberapp.bottomSheetFragment.CreateTaskBottomSheetFragment;
import com.s92075608.s92075608medirememberapp.bottomSheetFragment.ShowCalendarViewBottomSheet;
import com.s92075608.s92075608medirememberapp.broadcastReceiver.AlarmBroadcastReceiver;
import com.s92075608.s92075608medirememberapp.database.DatabaseClient;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
//displays the saved tasks in a RecyclerView and provides options to add new tasks and view the calendar
public class reminder extends BaseActivity implements CreateTaskBottomSheetFragment.setRefreshListener {


    RecyclerView taskRecycler;

    TextView addTask;
    TaskAdapter taskAdapter;
    List<Task> tasks = new ArrayList<>();

    ImageView noDataImage;
    ImageView calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);
        ButterKnife.bind(this);
        setUpAdapter();
//sets a flag to keep the screen on
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
//enables the AlarmBroadcastReceiver using the PackageManager
        ComponentName receiver = new ComponentName(this, AlarmBroadcastReceiver.class);
        PackageManager pm = getPackageManager();
        pm.setComponentEnabledSetting(receiver, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
        Glide.with(getApplicationContext()).load(R.drawable.mainpic1).into(noDataImage);//initializes the noDataImage and sets an image using Glide
//add a new task
        addTask.setOnClickListener(view -> {
            CreateTaskBottomSheetFragment createTaskBottomSheetFragment = new CreateTaskBottomSheetFragment();
            createTaskBottomSheetFragment.setTaskId(0, false, this);
            createTaskBottomSheetFragment.show(getSupportFragmentManager(), createTaskBottomSheetFragment.getTag());
        });
// tasks are retrieved from the database
        getSavedTasks();
//opens a ShowCalendarViewBottomSheet fragment to display the calendar view
        calendar.setOnClickListener(view -> {
            ShowCalendarViewBottomSheet showCalendarViewBottomSheet = new ShowCalendarViewBottomSheet();
            showCalendarViewBottomSheet.show(getSupportFragmentManager(), showCalendarViewBottomSheet.getTag());
        });
    }

    public void setUpAdapter() {
        taskAdapter = new TaskAdapter(this, tasks, this);
        taskRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        taskRecycler.setAdapter(taskAdapter);
    }
    //retrieves the saved tasks from the database and updates the UI
    private void getSavedTasks() {

        class GetSavedTasks extends AsyncTask<Void, Void, List<Task>> {
            @Override
            protected List<Task> doInBackground(Void... voids) {
                tasks = DatabaseClient
                        .getInstance(getApplicationContext())
                        .getAppDatabase()
                        .dataBaseAction()
                        .getAllTasksList();
                return tasks;
            }
            //update main thread
            @Override
            protected void onPostExecute(List<Task> tasks) {
                super.onPostExecute(tasks);
                noDataImage.setVisibility(tasks.isEmpty() ? View.VISIBLE : View.GONE);
                setUpAdapter();
            }
        }

        GetSavedTasks savedTasks = new GetSavedTasks();
        savedTasks.execute();
    }
    //refreshing when a new task is added
    @Override
    public void refresh() {
        getSavedTasks();
    }
}
