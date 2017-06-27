package com.kevin.jokeji.features.text;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.kevin.jokeji.R;
import com.kevin.jokeji.base.BaseFragment;
import com.kevin.jokeji.beans.JokeItem;
import com.kevin.jokeji.config.URLS;
import com.kevin.jokeji.features.base.BaseView;
import com.kevin.jokeji.features.base.CommonPresenter;
import com.kevin.jokeji.features.hotjoke.JokeDetailActivity;
import com.kevin.jokeji.view.PinnedSectionListView.PinnedSectionListAdapter;

import java.util.ArrayList;

public class AwardsFragment extends BaseFragment implements BaseView<ArrayList<JokeItem>>
        , OnClickListener, AdapterView.OnItemClickListener {

    private ListView mAwardsLv;

    private JokeAdapter mJokeAdapter;

    CommonPresenter<ArrayList<JokeItem>> mPresenter;


    @Override
    protected void initView() {
        mJokeAdapter = new JokeAdapter(getActivity());
        mAwardsLv = findViewById(R.id.list_view);
        mAwardsLv.setAdapter(mJokeAdapter);
    }

    @Override
    protected void initData() {
        mPresenter = new CommonPresenter<>(new AwardsModel(), this);
    }

    @Override
    protected void loadData() {
        showLoading();
        mPresenter.loadData(URLS.AWARDS_URL, true);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.frg_award_layout;
    }


    @Override
    public void onClick(View v) {
        Toast.makeText(getActivity(), "Item: " + v.getTag(), Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    protected void setListener() {
        mAwardsLv.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        JokeItem item = (JokeItem) mAwardsLv.getAdapter().getItem(position);

        if (item.type == JokeItem.ITEM) {
            Intent intent = new Intent(getActivity(), JokeDetailActivity.class);
            intent.putExtra(JokeDetailActivity.JOKE, item);
            startActivity(intent);
        } else if (item.type == JokeItem.MORE) {
            Intent intent = new Intent(getActivity(), JokeListActivity.class);
            intent.putExtra(JokeListActivity.CATEGORY, "/" + item.getUrl());
            startActivity(intent);
        }
    }

    @Override
    public void showData(ArrayList<JokeItem> jokeItems, boolean isRefresh) {
        showContent();
        mJokeAdapter.bindData(jokeItems);
        mJokeAdapter.notifyDataSetChanged();
    }

    @Override
    public void showError() {

    }


    static class JokeAdapter extends BaseAdapter implements
            PinnedSectionListAdapter {

        private ArrayList<JokeItem> jokes = new ArrayList<JokeItem>();
        private LayoutInflater inflater;

        private class ViewHolder {
            TextView title;
            TextView count;
        }

        public JokeAdapter(Context context) {
            inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public void bindData(ArrayList<JokeItem> jokes) {

            if (jokes != null) {
                this.jokes = jokes;
            }

        }

        @Override
        public int getCount() {
            return jokes.size();
        }

        @Override
        public Object getItem(int position) {
            return jokes.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            JokeItem item = jokes.get(position);
            ViewHolder viewHolder = null;

            if (item.type == JokeItem.SECTION) {

                if (convertView == null) {
                    convertView = inflater.inflate(
                            R.layout.award_list_section_item, parent, false);

                    viewHolder = new ViewHolder();

                    viewHolder.title = (TextView) convertView
                            .findViewById(R.id.name);
                    convertView.setTag(viewHolder);
                }

                viewHolder = (ViewHolder) convertView.getTag();
                viewHolder.title.setText(item.getTitle());

            } else if (item.type == JokeItem.ITEM) {

                if (convertView == null) {
                    convertView = inflater.inflate(
                            R.layout.frg_new_item_layout, parent, false);

                    viewHolder = new ViewHolder();

                    viewHolder.title = (TextView) convertView
                            .findViewById(R.id.title);
                    viewHolder.count = (TextView) convertView
                            .findViewById(R.id.count);

                    convertView.setTag(viewHolder);
                }

                viewHolder = (ViewHolder) convertView.getTag();

                viewHolder.title.setText(item.getTitle());
                viewHolder.count.setText(item.getCount());

            } else if (item.type == JokeItem.MORE) {

                if (convertView == null) {
                    convertView = inflater.inflate(
                            R.layout.award_list_more_item, parent, false);

                    viewHolder = new ViewHolder();

                    viewHolder.title = (TextView) convertView
                            .findViewById(R.id.more);

                    convertView.setTag(viewHolder);
                }

                viewHolder = (ViewHolder) convertView.getTag();

                viewHolder.title.setText(item.getTitle());

            }

            return convertView;
        }

        @Override
        public int getViewTypeCount() {
            return 3;
        }

        @Override
        public int getItemViewType(int position) {
            return jokes.get(position).type;
        }

        @Override
        public boolean isItemViewTypePinned(int viewType) {
            return viewType == JokeItem.SECTION;
        }

    }

}