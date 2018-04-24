package cn.invonate.ygoa3.Task.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.yonggang.liyangyang.ios_dialog.widget.AlertDialog;
import com.yonggang.liyangyang.lazyviewpagerlibrary.LazyFragmentPagerAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.invonate.ygoa3.Contacts.Select.SelectDepartment4Activity;
import cn.invonate.ygoa3.Entry.Contacts;
import cn.invonate.ygoa3.Entry.Task;
import cn.invonate.ygoa3.Entry.TaskDetail;
import cn.invonate.ygoa3.R;
import cn.invonate.ygoa3.YGApplication;
import cn.invonate.ygoa3.httpUtil.HttpUtil;
import cn.invonate.ygoa3.httpUtil.ProgressSubscriber;
import cn.invonate.ygoa3.httpUtil.SubscriberOnNextListener;
import cn.invonate.ygoa3.main.BasePicActivity;
import cn.invonate.ygoa3.main.FileWebActivity;
import drawthink.expandablerecyclerview.adapter.BaseRecyclerViewAdapter;
import drawthink.expandablerecyclerview.bean.RecyclerViewData;
import drawthink.expandablerecyclerview.holder.BaseViewHolder;

/**
 * Created by liyangyang on 2018/1/15.
 */

public class TaskDetailFragment1 extends Fragment implements LazyFragmentPagerAdapter.Laziable {

    @BindView(R.id.list_input)
    RecyclerView listInput;
    Unbinder unbinder;
    private static String img[] = {"bmp", "jpg", "jpeg", "png", "tiff", "gif", "pcx", "tga", "exif", "fpx", "svg", "psd",
            "cdr", "pcd", "dxf", "ufo", "eps", "ai", "raw", "wmf",
            "BMP", "JPG", "JPEG", "PNG", "TIFF", "GIF", "PCX", "TGA", "EXIF", "FPX", "SVG", "PSD",
            "CDR", "PCD", "DXF", "UFO", "EPS", "AI", "RAW", "WMF"};
    private static String windows[] = {
            "doc", "xls", "ppt",
            "docx", "xls", "pptx",
            "DOC", "XLS", "PPT",
            "DOCX", "XLSX", "PPTX",
    };

    private YGApplication app;

    private List<TaskDetail.Input> inputs;

    private TaskDetailAdapter adapter;

    private StringBuilder copy = new StringBuilder();
    private StringBuilder coordination = new StringBuilder();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inputs = (List<TaskDetail.Input>) getArguments().getSerializable("inputs");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_detail1, container, false);
        app = (YGApplication) getActivity().getApplication();
        unbinder = ButterKnife.bind(this, view);
        Log.i("inputs123", JSON.toJSONString(inputs));
        listInput.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new TaskDetailAdapter(inputs, getActivity());
        listInput.setAdapter(adapter);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public class TaskDetailAdapter extends RecyclerView.Adapter<TaskDetailAdapter.ViewHolder> {
        public static final int LABEL = 1;
        public static final int TEXT = 2;
        public static final int HIDDEN = 3;
        public static final int DATE = 4;
        public static final int SELECT = 5;
        public static final int ACCORDION = 6;
        public static final int FILE = 7;
        public static final int FOUR_SINGLE = 8;
        public static final int PICKER = 9;
        public static final int ALERT = 10;
        public static final int NULL = 11;

        private List<TaskDetail.Input> data;
        private LayoutInflater inflater;

        int[] isExpand;

        public TaskDetailAdapter(List<TaskDetail.Input> data, Context context) {
            this.data = data;
            this.inflater = LayoutInflater.from(context);
        }

        @Override
        public TaskDetailAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            switch (viewType) {
                case LABEL:
                    return new ViewHolder(inflater.inflate(R.layout.item_detail_label, parent, false));
                case TEXT:
                    return new ViewHolder(inflater.inflate(R.layout.item_detail_text, parent, false));
                case HIDDEN:
                    return new ViewHolder(inflater.inflate(R.layout.item_detail_hidden, parent, false));
                case DATE:
                    return new ViewHolder(inflater.inflate(R.layout.item_detail_date, parent, false));
                case SELECT:
                    return new ViewHolder(inflater.inflate(R.layout.item_detail_select, parent, false));
                case ACCORDION:
                    return new ViewHolder(inflater.inflate(R.layout.item_detail_accordion, parent, false));
                case FOUR_SINGLE:
                    return new ViewHolder(inflater.inflate(R.layout.item_detail_four, parent, false));
                case PICKER:
                    return new ViewHolder(inflater.inflate(R.layout.item_detail_picker, parent, false));
                case FILE:
                    return new ViewHolder(inflater.inflate(R.layout.item_detail_file, parent, false));
                case ALERT:
                    return new ViewHolder(inflater.inflate(R.layout.item_detail_alert, parent, false));
                case NULL:
                    return new ViewHolder(inflater.inflate(R.layout.item_detail_alert, parent, false));
            }
            return new ViewHolder(inflater.inflate(R.layout.item_detail_alert, parent, false));
        }

        @Override
        public void onBindViewHolder(final TaskDetailAdapter.ViewHolder holder, final int position) {
            switch (getItemViewType(position)) {
                case LABEL:
                    holder.label.setText(data.get(position).getLabel());
                    holder.value.setText(data.get(position).getValue());
                    break;
                case TEXT:
                    holder.label.setText(data.get(position).getLabel());
                    holder.text.setText(data.get(position).getValue() == null ? "" : data.get(position).getValue());
                    if (data.get(position).isReadonly() != null) {
                        holder.text.setFocusable(false);
                        holder.text.setFocusableInTouchMode(false);
                    } else {
                        holder.text.setFocusableInTouchMode(true);
                        holder.text.setFocusable(true);
                        holder.text.requestFocus();
                    }

                    holder.text.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            data.get(position).setValue(holder.text.getText().toString().trim());
                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                        }
                    });
                    break;
                case DATE:
                    holder.label.setText(data.get(position).getLabel());
                    holder.value.setText(data.get(position).getValue());
                    break;
                case SELECT:
                    holder.label.setText(data.get(position).getLabel());
                    holder.select.setAdapter(new SpinnerAdapter(data.get(position).getOptions(), inflater));
                    for (int i = 0; i < data.get(position).getOptions().size(); i++) {
                        if (data.get(position).getOptions().get(i).isSelected()) {
                            holder.select.setSelection(i);
                            data.get(position).setValue(data.get(position).getOptions().get(i).getValue());
                        }
                    }
                    holder.select.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int index, long id) {
                            data.get(position).setValue(data.get(position).getOptions().get(index).getValue());
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                        }
                    });
                    break;
                case ACCORDION:
                    List<TaskDetail.Accordion> values = JSON.parseArray(data.get(position).getValue(), TaskDetail.Accordion.class);
                    for (TaskDetail.Accordion bean : values) {
                        bean.initMaps();
                    }
                    Log.i("values", JSON.toJSONString(values));
                    holder.label.setText(data.get(position).getLabel());
                    List<RecyclerViewData> list_data = new ArrayList<>();
                    for (TaskDetail.Accordion ac : values) {
                        list_data.add(new RecyclerViewData(ac, ac.getMaps(), true));
                    }
                    holder.list_accordion.setLayoutManager(new LinearLayoutManager(getActivity()));
                    holder.list_accordion.setAdapter(new AccordionAdapter(getActivity(), list_data));
                    break;
                case FOUR_SINGLE:
                    List<String> value = JSON.parseArray(data.get(position).getValue(), String.class);
                    holder.text1.setText(value.get(0));
                    holder.text2.setText(value.get(1));
                    holder.text3.setText(value.get(2));
                    holder.text4.setText(value.get(3));
                    String color1 = data.get(position).getColor().get(0);
                    if ("".equals(color1)) {
                        holder.text1.setTextColor(Color.parseColor("#000000"));
                    } else {
                        if (color1.startsWith("#")) {
                            holder.text1.setTextColor(Color.parseColor(color1));
                        } else {
                            holder.text1.setTextColor(Color.parseColor("#" + color1));
                        }
                    }

                    String color2 = data.get(position).getColor().get(1);
                    if ("".equals(color2)) {
                        holder.text2.setTextColor(Color.parseColor("#000000"));
                    } else {
                        if (color2.startsWith("#")) {
                            holder.text2.setTextColor(Color.parseColor(color2));
                        } else {
                            holder.text2.setTextColor(Color.parseColor("#" + color2));
                        }
                    }

                    String color3 = data.get(position).getColor().get(2);
                    if ("".equals(color3)) {
                        holder.text3.setTextColor(Color.parseColor("#000000"));
                    } else {
                        if (color3.startsWith("#")) {
                            holder.text3.setTextColor(Color.parseColor(color3));
                        } else {
                            holder.text3.setTextColor(Color.parseColor("#" + color3));
                        }
                    }

                    String color4 = data.get(position).getColor().get(3);
                    if ("".equals(color4)) {
                        holder.text4.setTextColor(Color.parseColor("#000000"));
                    } else {
                        if (color4.startsWith("#")) {
                            holder.text4.setTextColor(Color.parseColor(color4));
                        } else {
                            holder.text4.setTextColor(Color.parseColor("#" + color4));
                        }
                    }
                    break;
                case FILE:
                    if (data.get(position).getLabel() != null) {
                        holder.label.setText(data.get(position).getLabel());
                        holder.label.setVisibility(View.VISIBLE);
                    } else {
                        holder.label.setVisibility(View.GONE);
                    }
                    holder.name.setText(data.get(position).getName());
                    holder.size.setText(data.get(position).getSize());

                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.i("holder.itemView", "holder.itemView");
                            Intent intent = null;
                            Bundle bundle = new Bundle();
                            String url = "";
                            switch (data.get(position).getType()) {
                                case "fujian"://
                                case "wjpsfujian"://
                                case "attachment"://
                                    if (is_img(data.get(position).getName())) {
                                        ArrayList<String> pic = new ArrayList<>();
                                        pic.add(HttpUtil.URL_FILE + data.get(position).getUrl());
                                        Log.i("file_pic", HttpUtil.URL_FILE + data.get(position).getUrl());
                                        intent = new Intent(getActivity(), BasePicActivity.class);
                                        bundle.putStringArrayList("imgs", pic);
                                        bundle.putInt("index", 0);
                                    } else {
                                        intent = new Intent(getActivity(), FileWebActivity.class);
                                        bundle.putString("url", HttpUtil.URL_FILE + data.get(position).getUrl());
                                    }
                                    break;
                                case "ifbreport":
                                    intent = new Intent(getActivity(), FileWebActivity.class);
                                    url = HttpUtil.BASE_URL + "/ygoa/DownloadAttachmentOther?url="
                                            + data.get(position).getPk()
                                            + "&system_lb=5"
                                            + "&wdlx=" + data.get(position).getWdlx();
                                    bundle.putString("url", url);
                                    break;
                                case "ifbdownload":
                                    intent = new Intent(getActivity(), FileWebActivity.class);
                                    url = HttpUtil.BASE_URL + "/ygoa/DownloadAttachmentOther?url="
                                            + data.get(position).getPk()
                                            + "&system_lb=6"
                                            + "&wdlx=" + data.get(position).getWdlx();
                                    bundle.putString("url", url);
                                    break;
                                case "ifbdownloadFromDisk":
                                    intent = new Intent(getActivity(), FileWebActivity.class);
                                    url = HttpUtil.BASE_URL + "/ygoa/DownloadAttachmentOther?url="
                                            + data.get(position).getPk()
                                            + "&system_lb=7"
                                            + "&wdlx=" + data.get(position).getWdlx();
                                    bundle.putString("url", url);
                                    break;
                                case "jzcwdownload":
                                    intent = new Intent(getActivity(), FileWebActivity.class);
                                    url = HttpUtil.BASE_URL + "/ygoa/DownloadAttachmentOther?url="
                                            + data.get(position).getPk()
                                            + "&system_lb=8"
                                            + "&wdlx=" + data.get(position).getWdlx();
                                    bundle.putString("url", url);
                                    break;
                                case "cwcwdownload":
                                    intent = new Intent(getActivity(), FileWebActivity.class);
                                    url = HttpUtil.BASE_URL + "/ygoa/DownloadAttachmentOther?url="
                                            + data.get(position).getPk()
                                            + "&system_lb=0"
                                            + "&wdlx=" + data.get(position).getWdlx();
                                    bundle.putString("url", url);
                                    break;
                                case "sbazgsdownload":
                                    intent = new Intent(getActivity(), FileWebActivity.class);
                                    url = HttpUtil.BASE_URL + "/ygoa/DownloadAttachmentOther?url="
                                            + data.get(position).getPk()
                                            + "&system_lb="
                                            + "&wdlx=" + data.get(position).getWdlx();
                                    bundle.putString("url", url);
                                    break;
                                case "ifbdownloadHtDisk":
                                    intent = new Intent(getActivity(), FileWebActivity.class);
                                    url = HttpUtil.BASE_URL + "/ygoa/DownloadAttachmentOther?url="
                                            + data.get(position).getPk()
                                            + "&system_lb="
                                            + "&wdlx=" + data.get(position).getWdlx();
                                    bundle.putString("url", url);
                                    break;
                                case "download":
                                    intent = new Intent(getActivity(), FileWebActivity.class);
                                    url = HttpUtil.BASE_URL + "/ygoa/DownloadAttachmentOther?url="
                                            + data.get(position).getPk()
                                            + "&system_lb=10"
                                            + "&wdlx=" + data.get(position).getWdlx();
                                    bundle.putString("url", url);
                                    break;
                                case "ygsbDownload":
                                    intent = new Intent(getActivity(), FileWebActivity.class);
                                    url = HttpUtil.BASE_URL + "/ygoa/DownloadAttachmentOther?url="
                                            + data.get(position).getPk()
                                            + "&system_lb=4"
                                            + "&wdlx=" + data.get(position).getWdlx();
                                    bundle.putString("url", url);
                                    break;
                            }
                            if (intent != null) {
                                intent.putExtras(bundle);
                                startActivity(intent);
                            }
                        }
                    });
                    break;
                case PICKER:
                    holder.label.setText(data.get(position).getLabel());
                    holder.add.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getActivity(), SelectDepartment4Activity.class);
                            Bundle bundle = new Bundle();
                            bundle.putInt("index", position);
                            bundle.putInt("limit", data.get(position).getLimitCount());
                            intent.putExtras(bundle);
                            startActivityForResult(intent, 0x888);
                        }
                    });

                    String s = "";
                    List<Contacts> list_picker = data.get(position).getPickValue();
                    if (list_picker != null) {
                        for (Contacts c : list_picker) {
                            s = s + c.getUser_name() + ",";
                        }
                        holder.text_picker.setText(s);
                    }
                    break;
                case ALERT:
                    AlertDialog dialog = new AlertDialog(getActivity()).builder();
                    dialog.setMsg(data.get(position).getLabel())
                            .setPositiveButton("确定", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                }
                            }).show();
                    break;
            }
        }

        /**
         * 判断后缀是否图片
         *
         * @param name
         * @return
         */
        private boolean is_img(String name) {
            if (name.contains(".")) {
                String type = name.substring(name.lastIndexOf(".") + 1, name.length());
                for (String img : img) {
                    if (type.equals(img)) {
                        return true;
                    }
                }
            }
            return false;
        }

        /**
         * 判断后缀是否为windows
         *
         * @param name
         * @return
         */
        private boolean is_windows(String name) {
            for (String window : windows) {
                if (name.contains(window)) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        @Override
        public int getItemViewType(int position) {
            TaskDetail.Input input = data.get(position);
            if (input == null) {
                return NULL;
            }
            String type = input.getType();
            if (type == null) {
                return NULL;
            }
            switch (type) {
                case "label":
                    return LABEL;
                case "text":
                    return TEXT;
                case "hidden":
                    return HIDDEN;
                case "date":
                    return DATE;
                case "select":
                    return SELECT;
                case "accordion":
                    return ACCORDION;
                case "accordion2":
                    return ACCORDION;
                case "accordion3":
                    return ACCORDION;
                case "fourSingle":
                    return FOUR_SINGLE;
                case "picker":
                    return PICKER;
                //附件
                case "fujian"://
                case "wjpsfujian"://
                case "download":
                case "ygsbDownload":
                case "ifbreport":
                case "ifbdownload":
                case "ifbdownloadFromDisk":
                case "jzcwdownload":
                case "cwcwdownload":
                case "sbazgsdownload":
                case "ifbdownloadHtDisk":
                case "attachment"://
                    return FILE;
                case "alert":
                    return ALERT;
                default:
                    return NULL;
            }
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            /**
             * label
             */
            @Nullable
            @BindView(R.id.label)
            TextView label;
            @Nullable
            @BindView(R.id.value)
            TextView value;

            /**
             * text
             */
            @Nullable
            @BindView(R.id.text)
            EditText text;

            /**
             * select
             */

            @Nullable
            @BindView(R.id.select)
            Spinner select;

            /**
             * accordion
             */
            @Nullable
            @BindView(R.id.list_accordion)
            RecyclerView list_accordion;

            /**
             * foursingle
             */
            @Nullable
            @BindView(R.id.text1)
            TextView text1;

            @Nullable
            @BindView(R.id.text2)
            TextView text2;

            @Nullable
            @BindView(R.id.text3)
            TextView text3;

            @Nullable
            @BindView(R.id.text4)
            TextView text4;

            /**
             * file
             */
            @Nullable
            @BindView(R.id.layout_file)
            LinearLayout layout_file;

            @Nullable
            @BindView(R.id.name)
            TextView name;

            @Nullable
            @BindView(R.id.size)
            TextView size;

            @Nullable
            @BindView(R.id.text_picker)
            TextView text_picker;

            @Nullable
            @BindView(R.id.add)
            ImageView add;

            public ViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }

        class AccordionAdapter extends BaseRecyclerViewAdapter<TaskDetail.Accordion, TaskDetail.Accordion.MapEntry, AccordionAdapter.AccordionHolder> {

            private LayoutInflater mInflater;

            public AccordionAdapter(Context ctx, List<RecyclerViewData> datas) {
                super(ctx, datas);
                this.mInflater = LayoutInflater.from(ctx);
            }

            @Override
            public View getGroupView(ViewGroup parent) {
                return mInflater.inflate(R.layout.item_detail_accordion_group, parent, false);
            }

            @Override
            public View getChildView(ViewGroup parent) {
                return mInflater.inflate(R.layout.item_detail_accordion_child, parent, false);
            }

            @Override
            public AccordionHolder createRealViewHolder(Context ctx, View view, int viewType) {
                return new AccordionHolder(ctx, view, viewType);
            }

            @Override
            public void onBindGroupHolder(AccordionHolder holder, int groupPos, int position, final TaskDetail.Accordion groupData) {
                if (groupData.getDelurl() == null) {
                    holder.btn.setVisibility(View.GONE);
                } else {
                    holder.btn.setVisibility(View.VISIBLE);
                }
                holder.btn.setText(groupData.getDeltext());
                holder.value.setText(groupData.getHeader());
                holder.btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String url = groupData.getDelurl();
                        if (url.contains("comment=")) {
                            AlertDialog dialog = new AlertDialog(getActivity()).builder();
                            View view = LayoutInflater.from(getActivity()).inflate(R.layout.item_input_message, null);
                            final EditText input = view.findViewById(R.id.input);
                            dialog.setView(view)
                                    .setTitle("请输入理由")
                                    .setNegativeButton("取消", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                        }
                                    }).setPositiveButton("确定", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (input.getText().toString().length() != 0) {
                                        singlePost(groupData.getDelurl() + input.getText().toString());
                                    }
                                }
                            }).show();
                        } else {
                            singlePost(url);
                        }
                    }
                });
            }

            @Override
            public void onBindChildpHolder(AccordionHolder holder, int groupPos, int childPos, int position, TaskDetail.Accordion.MapEntry childData) {
                holder.key.setText(childData.getKey());
                holder.value.setText(childData.getValue());
                if (childPos % 2 == 0) {
                    holder.key.setBackgroundColor(Color.parseColor("#f6fbff"));
                    holder.value.setBackgroundColor(Color.parseColor("#f6fbff"));
                } else {
                    holder.key.setBackgroundColor(Color.parseColor("#ffffff"));
                    holder.value.setBackgroundColor(Color.parseColor("#ffffff"));
                }
            }

            class AccordionHolder extends BaseViewHolder {

                private TextView key;
                private TextView value;
                private Button btn;

                public AccordionHolder(Context ctx, View itemView, int viewType) {
                    super(ctx, itemView, viewType);
                    key = itemView.findViewById(R.id.key);
                    value = itemView.findViewById(R.id.value);
                    btn = itemView.findViewById(R.id.btn);
                }

                @Override
                public int getChildViewResId() {
                    return R.id.child;
                }

                @Override
                public int getGroupViewResId() {
                    return R.id.group;
                }
            }

            @Override
            public boolean canExpandAll() {
                return true;
            }
        }


        class SpinnerAdapter extends BaseAdapter {

            private List<TaskDetail.Option> data;
            private LayoutInflater inflater;

            public SpinnerAdapter(List<TaskDetail.Option> data, LayoutInflater inflater) {
                this.data = data;
                this.inflater = inflater;
            }

            @Override
            public int getCount() {
                return data.size();
            }

            @Override
            public Object getItem(int position) {
                return data.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                ViewHolder holder = null;
                if (convertView == null) {
                    convertView = inflater.inflate(R.layout.item_detail_select_text, null);
                    holder = new ViewHolder(convertView);
                    convertView.setTag(holder);
                } else {
                    holder = (ViewHolder) convertView.getTag();
                }
                holder.text.setText(data.get(position).getLabel());
                return convertView;
            }

            class ViewHolder {
                @BindView(R.id.text)
                TextView text;

                ViewHolder(View view) {
                    ButterKnife.bind(this, view);
                }
            }
        }
    }

    /**
     * 单条驳回
     *
     * @param url
     */
    private void singlePost(String url) {
        SubscriberOnNextListener onNextListener = new SubscriberOnNextListener<Task>() {
            @Override
            public void onNext(Task data) {
                if (data.getSuccess() == 0) {
                    getActivity().finish();
                } else {
                    Toast.makeText(app, "", Toast.LENGTH_SHORT).show();
                }
            }
        };
        HttpUtil.getInstance(getActivity(), false).singlePost(new ProgressSubscriber(onNextListener, getActivity(), "请求中"), url);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i("SelectDepartment", requestCode + "------" + resultCode);
        if (data != null) {
            if (resultCode == 0x888) {
                int index = data.getExtras().getInt("index");
                inputs.get(index).setPickValue((List<Contacts>) data.getExtras().getSerializable("list"));
                adapter.notifyDataSetChanged();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 获取信息
     *
     * @return
     */
    public Map<String, String> getMessage() {
        Map<String, String> params = new HashMap<>();
        for (TaskDetail.Input i : inputs) {
            if (i != null && i.getType() != null) {
                switch (i.getType()) {
                    case "text":
                        if (i.isRequired() && i.getValue() == null) {
                            Toast.makeText(app, i.getLabel() + "不能为空", Toast.LENGTH_SHORT).show();
                            return null;
                        }
                        params.put(i.getName(), i.getValue());
                        break;
                    case "hidden":
                        if (i.isRequired() && i.getValue() == null) {
                            Toast.makeText(app, i.getLabel() + "不能为空", Toast.LENGTH_SHORT).show();
                            return null;
                        }
                        params.put(i.getName(), i.getValue());
                        break;
                    case "date":
                        if (i.isRequired() && i.getValue() == null) {
                            Toast.makeText(app, i.getLabel() + "不能为空", Toast.LENGTH_SHORT).show();
                            return null;
                        }
                        params.put(i.getName(), i.getValue());
                        break;
                    case "select":
                        if (i.isRequired() && i.getValue() == null) {
                            Toast.makeText(app, i.getLabel() + "不能为空", Toast.LENGTH_SHORT).show();
                            return null;
                        }
                        params.put(i.getName(), i.getValue());
                        break;
                    case "picker":
                        if (i.isRequired() && i.getPickValue() == null) {
                            Toast.makeText(app, i.getLabel() + "不能为空", Toast.LENGTH_SHORT).show();
                            return null;
                        }
                        String s = "";
                        List<Contacts> list_contacts = i.getPickValue();
                        for (Contacts c : list_contacts) {
                            s = s + c.getUser_code() + ",";
                        }
                        params.put(i.getName(), s);
                }
            }
        }
        return params;
    }
}
