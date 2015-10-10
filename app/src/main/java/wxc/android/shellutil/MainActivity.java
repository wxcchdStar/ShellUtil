package wxc.android.shellutil;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText commandEt = (EditText) findViewById(R.id.et_shell_command);
        final TextView resultTv = (TextView) findViewById(R.id.tv_result);
        Button executeBtn = (Button) findViewById(R.id.btn_execute);

        // 点击执行命令
        executeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String commandContent = commandEt.getText().toString();
                if (!TextUtils.isEmpty(commandContent)) {
                    new AsyncTask<String, Void, Command>() {

                        @Override
                        protected Command doInBackground(String... params) {
                            Command command = new Command(params[0]);
                            command.execute();
                            return command;
                        }

                        @Override
                        protected void onPostExecute(Command command) {
                            if (command.getExitCode() == 0) {
                                resultTv.setText("");
                                for (String str : command.getMessageList()) {
                                    resultTv.append(str + "\n");
                                }
                            } else {
                                resultTv.setText(command.getErrorMsg());
                            }
                        }
                    }.execute(commandContent);
                } else {
                    Toast.makeText(MainActivity.this, "Input is empty!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // 长按终止ROOT权限
        executeBtn.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AsyncTask<Void, Void, Void>() {

                    @Override
                    protected Void doInBackground(Void... params) {
                        RootShell.getInstance().terminal();
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void aVoid) {
                        Toast.makeText(MainActivity.this, "Terminated", Toast.LENGTH_SHORT).show();
                    }
                }.execute();
                return true;
            }
        });
    }

}
