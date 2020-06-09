package com.example.lab0910.curso;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.lab0910.R;
import com.example.lab0910.model.Curso;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

public class AddCurso extends AppCompatActivity {
    private TextInputEditText idCurso;
    private TextInputEditText descriCurso;
    private EditText crediCursos;
    private FloatingActionButton addCurso;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_curso);
        idCurso = findViewById(R.id.idCursoAdd);
        descriCurso = findViewById(R.id.descripcionAddCur);
        crediCursos = findViewById(R.id.creditosAddCur);
        addCurso = findViewById(R.id.addCursoBtn);

        addCurso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    Curso curso = new Curso(idCurso.getText().toString(), descriCurso.getText().toString(), Integer.parseInt(crediCursos.getText().toString()));
                    Toast.makeText(AddCurso.this, curso.toString(),Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    Toast.makeText(AddCurso.this, "No se agregó el curso",Toast.LENGTH_SHORT).show();

                }
            }
        });



    }
}