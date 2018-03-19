package cn.invonate.ygoa3.Contacts.Select;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.invonate.ygoa3.Adapter.DepartmentAdapter;
import cn.invonate.ygoa3.BaseActivity;
import cn.invonate.ygoa3.Entry.Contacts;
import cn.invonate.ygoa3.Entry.Department;
import cn.invonate.ygoa3.R;
import cn.invonate.ygoa3.View.LYYPullToRefreshListView;
import cn.invonate.ygoa3.httpUtil.HttpUtil;
import rx.Subscriber;

public class SelectDepartmentActivity extends BaseActivity {

    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.list_connect)
    LYYPullToRefreshListView listConnect;

    private DepartmentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_department);
        ButterKnife.bind(this);
        if (getIntent().getExtras() == null) {
            name.setText("通讯录");
            getDepartment("0", "");
        } else {
            name.setText(getIntent().getExtras().getString("name"));
            final List<Department> data = (List<Department>) getIntent().getExtras().getSerializable("list");
            listConnect.setAdapter(new DepartmentAdapter(data, this));
            listConnect.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    getDepartment(data.get(position - 1).getId_(), data.get(position - 1).getDepartment_name());
                }
            });
        }
    }

    /**
     * 通讯录查询部门
     */
    private void getDepartment(final String id, final String name) {
        Subscriber subscriber = new Subscriber<ArrayList<Department>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.i("error", e.toString());
                listConnect.onRefreshComplete();
            }

            @Override
            public void onNext(final ArrayList<Department> data) {
                Log.i("getDepartment", data.toString());
                if ("0".equals(id)) {
                    adapter = new DepartmentAdapter(data, SelectDepartmentActivity.this);
                    listConnect.setAdapter(adapter);
                    listConnect.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            getDepartment(data.get(position - 1).getId_(), data.get(position - 1).getDepartment_name());
                        }
                    });
                    listConnect.onRefreshComplete();
                } else {
                    if (data.isEmpty()) {// 为空  跳转至成员列表界面
                        Intent intent = new Intent(SelectDepartmentActivity.this, SelectContactsActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("id", id);
                        bundle.putString("name", name);
                        intent.putExtras(bundle);
                        startActivityForResult(intent, 0x123);
                    } else {// 还有子部门  跳转至部门界面
                        Intent intent = new Intent(SelectDepartmentActivity.this, SelectDepartmentActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("name", name);
                        bundle.putSerializable("list", data);
                        intent.putExtras(bundle);
                        startActivityForResult(intent, 0x123);
                    }
                }
            }
        };
        HttpUtil.getInstance(this,false).getDepartment(subscriber, id);
    }

    @OnClick(R.id.pic_back)
    public void onViewClicked() {
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("SelectDepartment", requestCode + "------" + resultCode);
        if (data!= null) {
            ArrayList<Contacts> select = (ArrayList<Contacts>) data.getSerializableExtra("list");
            Intent intent = getIntent();
            intent.putExtras(data.getExtras());
            setResult(0x321, intent);
            finish();
        }

    }
}
