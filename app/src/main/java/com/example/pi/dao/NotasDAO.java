package com.example.pi.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.pi.model.NotasVO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class NotasDAO extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "PI_pdm"; // Nome do banco de dados atualizado
    private static final int DATABASE_VERSION = 1; // Versão do banco de dados atualizada
    private static final String TB_NOTAS = "tb_notas";
    private static final String KEY_ID = "id";
    private static final String TITULO = "titulo";
    private static final String DESCRICAO = "descricao";
    private static final String ID_CRIADOR = "id_criador";
    private static final String DATA_CRIACAO = "data_criacao";
    private static final String DISCIPLINA = "disciplina";
    private static final String VALOR_NOTA = "valor_nota";

    public NotasDAO(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TB_NOTAS = "CREATE TABLE " + TB_NOTAS + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + TITULO + " TEXT,"
                + DESCRICAO + " TEXT,"
                + ID_CRIADOR + " INTEGER,"
                + DATA_CRIACAO + " INTEGER," // Armazenar data como long (timestamp)
                + DISCIPLINA + " TEXT,"
                + VALOR_NOTA + " REAL" + ")";
        db.execSQL(CREATE_TB_NOTAS);
        Log.d("onCreateNotas", "onCreate chamado");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            // Se a versão anterior for menor que 2, adicione a nova tabela
            onCreate(db);
        } else {
            db.execSQL("DROP TABLE IF EXISTS " + TB_NOTAS);
            onCreate(db);
        }
//        DATABASE_VERSION += 1;
        Log.d("onUpdateNotas", "onUpdate chamado");
    }

    public void addNota(NotasVO nota) {
        SQLiteDatabase db = this.getWritableDatabase();
        String CREATE_MISSING_TB_NOTAS = "CREATE TABLE IF NOT EXISTS " + TB_NOTAS + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + TITULO + " TEXT,"
                + DESCRICAO + " TEXT,"
                + ID_CRIADOR + " INTEGER,"
                + DATA_CRIACAO + " INTEGER," // Armazenar data como long (timestamp)
                + DISCIPLINA + " TEXT,"
                + VALOR_NOTA + " REAL" + ")";
        db.execSQL(CREATE_MISSING_TB_NOTAS);
        ContentValues contentValues = new ContentValues();
        contentValues.put(TITULO, nota.getTitulo());
        contentValues.put(DESCRICAO, nota.getDescricao());
        contentValues.put(ID_CRIADOR, nota.getIdCriador());
        contentValues.put(DATA_CRIACAO, nota.getDataCriacao().getTime()); // Armazena a data como timestamp (long)
        contentValues.put(DISCIPLINA, nota.getDisciplina());
        contentValues.put(VALOR_NOTA, nota.getValorNota());
        db.insert(TB_NOTAS, null, contentValues);
        db.close();
    }

    public List<NotasVO> getAllNotas() {
        List<NotasVO> listaNotas = new ArrayList<>();
        String SELECT_QUERY = "SELECT * FROM " + TB_NOTAS;
        SQLiteDatabase db = this.getReadableDatabase();
         String CREATE_MISSING_TB_NOTAS = "CREATE TABLE IF NOT EXISTS " + TB_NOTAS + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + TITULO + " TEXT,"
                + DESCRICAO + " TEXT,"
                + ID_CRIADOR + " INTEGER,"
                + DATA_CRIACAO + " INTEGER," // Armazenar data como long (timestamp)
                + DISCIPLINA + " TEXT,"
                + VALOR_NOTA + " REAL" + ")";
        db.execSQL(CREATE_MISSING_TB_NOTAS);
        Cursor cursor = db.rawQuery(SELECT_QUERY, null);
        if (cursor.moveToFirst()) {
            do {
                NotasVO nota = new NotasVO();
                nota.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
                nota.setTitulo(cursor.getString(cursor.getColumnIndex(TITULO)));
                nota.setDescricao(cursor.getString(cursor.getColumnIndex(DESCRICAO)));
                nota.setIdCriador(cursor.getInt(cursor.getColumnIndex(ID_CRIADOR)));
                nota.setDataCriacao(new Date(cursor.getLong(cursor.getColumnIndex(DATA_CRIACAO))));
                nota.setDisciplina(cursor.getString(cursor.getColumnIndex(DISCIPLINA)));
                nota.setValorNota(cursor.getDouble(cursor.getColumnIndex(VALOR_NOTA)));
                listaNotas.add(nota);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return listaNotas;
    }

    public void deleteNota(NotasVO nota) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TB_NOTAS, KEY_ID + " = ?", new String[]{String.valueOf(nota.getId())});
        db.close();
    }
}