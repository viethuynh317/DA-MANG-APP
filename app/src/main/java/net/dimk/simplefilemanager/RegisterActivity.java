package net.dimk.simplefilemanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class RegisterActivity extends AppCompatActivity {
    private TextView userName;
    private TextView password;
    private Button register;
    private Button back;
    private ListView lvUsers;

    private static final int MENU_ITEM_DELETE = 444;

    private ArrayList<User> userList = new ArrayList<User>();
    public ArrayAdapter listViewAdapter;
    public static DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        userName = (TextView) findViewById(R.id.userName);
        password = (TextView) findViewById(R.id.password);
        register = (Button) findViewById(R.id.btnRegister);
        back = (Button) findViewById(R.id.btnBack);
        lvUsers = (ListView) findViewById(R.id.lvUsers);

        db = new DatabaseHelper(this, null ,null, 1);

        ArrayList<User> temp = db.getAllUser(null);
        userList.addAll(temp);

        listViewAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, android.R.id.text1, userList);
        lvUsers.setAdapter(listViewAdapter);
        registerForContextMenu(lvUsers);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBack();
            }
        });

        lvUsers.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final User selectedUser = (User) lvUsers.getItemAtPosition(position);
                db.deleteUser(selectedUser);
                userList.remove(position);
                listViewAdapter.notifyDataSetChanged();
                return true;
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 String user = userName.getText().toString().trim();
                 String pass = password.getText().toString().trim();
                 if(user == "" || pass == "") return;

                 //add
                 User userAccount = new User(user, pass);
                 db.addUser(userAccount);
                 userList.add(userAccount);
                 lvUsers.deferNotifyDataSetChanged();
                 listViewAdapter.notifyDataSetChanged();
                 Toast.makeText(RegisterActivity.this, "Register Sucessed", Toast.LENGTH_SHORT).show();
             }
         });
    }
    private void onBack(){
        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
        this.startActivity(intent);
    }

//    @Override
//    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
//        super.onCreateContextMenu(menu, v, menuInfo);
//        menu.setHeaderTitle("Select the action");
//
//        menu.add(0, MENU_ITEM_DELETE, 0, "Delete user");
//    }
//
//    @Override
//    public boolean onContextItemSelected(MenuItem item) {
//        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
//        final User selectedUser = (User) this.lvUsers.getItemAtPosition(info.position);
//        if(item.getItemId() == MENU_ITEM_DELETE) {
//            db.deleteUser(selectedUser);
//            userList.remove(selectedUser);
//            listViewAdapter.notifyDataSetChanged();
//            return true;
//        }
//        return false;
//    }
}