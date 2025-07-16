package com.s92075608.s92075608medirememberapp.bottomSheetFragment;

import static android.content.Context.ALARM_SERVICE;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.s92075608.s92075608medirememberapp.Model.Task;
import com.s92075608.s92075608medirememberapp.R;
import com.s92075608.s92075608medirememberapp.broadcastReceiver.AlarmBroadcastReceiver;
import com.s92075608.s92075608medirememberapp.database.DatabaseClient;

import java.util.Calendar;
import java.util.GregorianCalendar;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public class CreateTaskBottomSheetFragment extends BottomSheetDialogFragment {

    private Unbinder unbinder;
    private EditText addTaskTitle, addTaskDescription, taskDate, taskTime, taskEvent;
    private Button addTask;
    private int taskId;
    private boolean isEdit;
    private Task task;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private setRefreshListener setRefreshListener;
    private AlarmManager alarmManager;
    private TimePickerDialog timePickerDialog;
    private DatePickerDialog datePickerDialog;

    private static int count = 0;

    private final BottomSheetBehavior.BottomSheetCallback mBottomSheetBehaviorCallback = new BottomSheetBehavior.BottomSheetCallback() {
        @Override
        public void onStateChanged(@NonNull View bottomSheet, int newState) {
            if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                dismiss();
            }
        }

        @Override
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {
            // Handle the bottom sheet sliding
        }
    };

    public void setTaskId(int taskId, boolean isEdit, setRefreshListener setRefreshListener) {
        this.taskId = taskId;
        this.isEdit = isEdit;
        this.setRefreshListener = setRefreshListener;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint({"RestrictedApi", "ClickableViewAccessibility", "SuspiciousIndentation"})
    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        View contentView = View.inflate(getContext(), R.layout.fragment_create_task, null);
        unbinder = ButterKnife.bind(this, contentView);
        dialog.setContentView(contentView);

        addTaskTitle = contentView.findViewById(R.id.addTaskTitle);
        addTaskDescription = contentView.findViewById(R.id.addTaskDescription);
        taskDate = contentView.findViewById(R.id.taskDate);
        taskTime = contentView.findViewById(R.id.taskTime);
        taskEvent = contentView.findViewById(R.id.taskEvent);
        addTask = contentView.findViewById(R.id.addTask);

        alarmManager = (AlarmManager) getActivity().getSystemService(ALARM_SERVICE);

        addTask.setOnClickListener(view -> {
            if (validateFields()) {
                createTask();
            }
        });

        if (isEdit) {
            showTaskFromId();
        }

        taskDate.setOnTouchListener((view, motionEvent) -> {
            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(getActivity(),
                        (view1, year, monthOfYear, dayOfMonth) -> {
                            taskDate.setText(String.format("%02d-%02d-%04d", dayOfMonth, monthOfYear + 1, year));
                            datePickerDialog.dismiss();
                        }, mYear, mMonth, mDay);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
            }
            return true;
        });

        taskTime.setOnTouchListener((view, motionEvent) -> {
            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                timePickerDialog = new TimePickerDialog(getActivity(),
                        (view12, hourOfDay, minute) -> {
                            taskTime.setText(String.format("%02d:%02d", hourOfDay, minute));
                            timePickerDialog.dismiss();
                        }, mHour, mMinute, false);
                timePickerDialog.show();
            }
            return true;
        });
    }

    public boolean validateFields() {
        if (addTaskTitle.getText().toString().isEmpty()) {
            Toast.makeText(getActivity(), "Please enter a valid medication", Toast.LENGTH_SHORT).show();
            return false;
        } else if (addTaskDescription.getText().toString().isEmpty()) {
            Toast.makeText(getActivity(), "Please enter a valid description", Toast.LENGTH_SHORT).show();
            return false;
        } else if (taskDate.getText().toString().isEmpty()) {
            Toast.makeText(getActivity(), "Please enter date", Toast.LENGTH_SHORT).show();
            return false;
        } else if (taskTime.getText().toString().isEmpty()) {
            Toast.makeText(getActivity(), "Please enter time", Toast.LENGTH_SHORT).show();
            return false;
        } else if (taskEvent.getText().toString().isEmpty()) {
            Toast.makeText(getActivity(), "Please enter medication type", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void createTask() {
        class saveTaskInBackend extends AsyncTask<Void, Void, Void> {
            @SuppressLint("WrongThread")
            @Override
            protected Void doInBackground(Void... voids) {
                Task createTask = new Task();
                createTask.setTaskTitle(addTaskTitle.getText().toString());
                createTask.setTaskDescrption(addTaskDescription.getText().toString());
                createTask.setDate(taskDate.getText().toString());
                createTask.setLastAlarm(taskTime.getText().toString());
                createTask.setEvent(taskEvent.getText().toString());

                if (!isEdit) {
                    DatabaseClient.getInstance(getActivity()).getAppDatabase()
                            .dataBaseAction()
                            .insertDataIntoTaskList(createTask);
                } else {
                    DatabaseClient.getInstance(getActivity()).getAppDatabase()
                            .dataBaseAction()
                            .updateAnExistingRow(taskId, addTaskTitle.getText().toString(),
                                    addTaskDescription.getText().toString(),
                                    taskDate.getText().toString(),
                                    taskTime.getText().toString(),
                                    taskEvent.getText().toString());
                }

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    createAnAlarm();
                }
                setRefreshListener.refresh();
                Toast.makeText(getActivity(), "Your medication has been added", Toast.LENGTH_SHORT).show();
                dismiss();
            }
        }
        new saveTaskInBackend().execute();
    }

    @SuppressLint("ScheduleExactAlarm")
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void createAnAlarm() {
        try {
            String[] dateParts = taskDate.getText().toString().split("-");
            int day = Integer.parseInt(dateParts[0]);
            int month = Integer.parseInt(dateParts[1]) - 1; // Month is 0-based
            int year = Integer.parseInt(dateParts[2]);

            String[] timeParts = taskTime.getText().toString().split(":");
            int hour = Integer.parseInt(timeParts[0]);
            int minute = Integer.parseInt(timeParts[1]);

            Calendar cal = new GregorianCalendar(year, month, day, hour, minute, 0);

            Intent alarmIntent = new Intent(getActivity(), AlarmBroadcastReceiver.class);
            alarmIntent.putExtra("TITLE", addTaskTitle.getText().toString());
            alarmIntent.putExtra("DESC", addTaskDescription.getText().toString());
            alarmIntent.putExtra("DATE", taskDate.getText().toString());
            alarmIntent.putExtra("TIME", taskTime.getText().toString());

            PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), count, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
            count++;

            PendingIntent reminderIntent = PendingIntent.getBroadcast(getActivity(), count, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis() - 600000, reminderIntent);
            count++;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showTaskFromId() {
        class ShowTaskFromId extends AsyncTask<Void, Void, Task> {
            @Override
            protected Task doInBackground(Void... voids) {
                return DatabaseClient.getInstance(getActivity()).getAppDatabase()
                        .dataBaseAction().selectDataFromAnId(taskId);
            }

            @Override
            protected void onPostExecute(Task task) {
                super.onPostExecute(task);
                if (task != null) {
                    setDataInUI(task);
                }
            }
        }
        new ShowTaskFromId().execute();
    }

    private void setDataInUI(Task task) {
        addTaskTitle.setText(task.getTaskTitle());
        addTaskDescription.setText(task.getTaskDescrption());
        taskDate.setText(task.getDate());
        taskTime.setText(task.getLastAlarm());
        taskEvent.setText(task.getEvent());
    }


    public interface setRefreshListener {
        void refresh();
    }


}
