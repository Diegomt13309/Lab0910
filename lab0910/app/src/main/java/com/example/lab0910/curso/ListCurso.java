package com.example.lab0910.curso;

import android.content.Intent;
import android.graphics.Color;
import android.media.MicrophoneInfo;
import android.os.Bundle;

import com.example.lab0910.BaseDatos.DataBase;
import com.example.lab0910.data.adapter.AdapterCurso;
import com.example.lab0910.data.helper.cursoHelper;
import com.example.lab0910.estudiante.AddEstudiante;
import com.example.lab0910.model.Curso;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.view.View;

import com.example.lab0910.R;

import java.util.ArrayList;
import java.util.List;

public class ListCurso extends AppCompatActivity implements AdapterCurso.AdapterCursoListener, cursoHelper.RecyclerItemTouchHelperListener{


    private RecyclerView rVLC;
    private AdapterCurso adapterCurso;
    private ArrayList<Curso> listaC;
    private CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_curso);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        coordinatorLayout=findViewById(R.id.coordinator_layout);

        rVLC = findViewById(R.id.recyclerViewCursos);
        rVLC.setItemAnimator(new DefaultItemAnimator());
        rVLC.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        LinearLayoutManager LL = new LinearLayoutManager(this);
        rVLC.setLayoutManager(LL);

        listaC = (ArrayList<Curso>) DataBase.getInstancia(ListCurso.this).listarTodoCurso();
        adapterCurso = new AdapterCurso(listaC, this);
        rVLC.setAdapter(adapterCurso);
        adapterCurso.notifyDataSetChanged();

        //Despues, verificar con un if
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new cursoHelper(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(rVLC);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addCurso();
            }
        });
    }

    @Override
    public void onContactSelected(Curso curso) {

    }

    private void addCurso(){
        Intent intent = new Intent(this, AddCurso.class);
        intent.putExtra("editable", false);
        startActivity(intent);
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (direction == ItemTouchHelper.START) {
            if (viewHolder instanceof AdapterCurso.MyViewHolder) {
                // get the removed item name to display it in snack bar
                String name = listaC.get(viewHolder.getAdapterPosition()).getId();

                // save the index deleted
                final int deletedIndex = viewHolder.getAdapterPosition();
                // remove the item from recyclerView
                adapterCurso.removeItem(viewHolder.getAdapterPosition());

                // showing snack bar with Undo option
                Snackbar snackbar = Snackbar.make(coordinatorLayout, name + " removido!", Snackbar.LENGTH_LONG);
                snackbar.setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // undo is selected, restore the deleted item from adapter
                        adapterCurso.restoreItem(deletedIndex);
                    }
                });
                snackbar.setActionTextColor(Color.YELLOW);
                snackbar.show();
            }
        } else {
            //If is editing a row object
            Curso aux = adapterCurso.getSwipedItem(viewHolder.getAdapterPosition());
            //send data to Edit Activity
            Intent intent = new Intent(this, AddCurso.class);
            intent.putExtra("editable", true);
            intent.putExtra("curso", aux);
            adapterCurso.notifyDataSetChanged(); //restart left swipe view
            startActivity(intent);
        }
    }

    @Override
    public void onItemMove(int source, int target) {
        adapterCurso.onItemMove(source, target);
    }
}