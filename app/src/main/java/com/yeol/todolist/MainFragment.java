//프래그먼트 기능을 넣어줄 클래스
package com.yeol.todolist;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import java.util.ArrayList;


public class MainFragment extends Fragment {

    //OnCreateView는 프래그먼트가 화면이 생성된 이후에 호출되는 역할을 한다.
    private static final String TAG = "MainFragment";
    private NoteAdapter.ViewHolder adapter;
    private View recordCount;
    Context context;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_main, container, false);
        initUI(rootView);
        loadNoteListData();
        return rootView;

    }


    private void initUI(ViewGroup rootView) { //layoutManager와 어댑터를 이용하여 리사이클러뷰를 사용하는 역할.

        RecyclerView recyclerView;
        NoteAdapter adapter;

        //fragment main xml 에서 만들었던 recyclerview 연결
        recyclerView = rootView.findViewById(R.id.recyclerView);

        //linearLayout을 이용하여 linearLayout에 recyclerView를 붙임
        //이 후 todo_item들이 세로로 정렬하게 되는 역할을 한다
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        //어댑터 연결
        adapter = new NoteAdapter();
        recyclerView.setAdapter(adapter);

    }

    public int loadNoteListData() {
        String loadSql = "select _id, TODO from " + NoteDatabase.TABLE_NOTE + " order by _id desc";

        int recordCount = -1;
        NoteDatabase database = NoteDatabase.getInstance(context);

        if (database != null) {
            Cursor outCursor = database.rawQuery(loadSql);
            recordCount = outCursor.getCount();
            ArrayList<Note> items = new ArrayList<>();

            for (int i = 0; i < recordCount; i++) {
                outCursor.moveToNext();
                int _id = outCursor.getInt(0);
                String todo = outCursor.getString(1);
                items.add(new Note(_id, todo));
            }

            outCursor.close();
            adapter.setItems(items);
            adapter.notifyDataSetChanged();
        }
        return recordCount;
    }

}


