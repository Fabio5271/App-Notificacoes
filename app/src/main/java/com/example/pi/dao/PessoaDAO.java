package com.example.pi.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.pi.model.PessoaVO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PessoaDAO extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "PI_pdm"; // Nome do banco de dados atualizado
    private static final int DATABASE_VERSION = 1;
    private static final String TB_PESSOAS = "tb_pessoas";
    private static final String KEY_ID = "id";
    private static final String NOME = "nome";
    private static final String EMAIL = "email";
    private static final String SENHA = "senha";
    private static final String MATRICULA = "matricula";
    private static final String DISCIPLINAS = "disciplinas";

    public PessoaDAO(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TB_PESSOAS = "CREATE TABLE " + TB_PESSOAS + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + NOME + " TEXT,"
                + EMAIL + " TEXT,"
                + SENHA + " TEXT,"
                + MATRICULA + " TEXT,"
                + DISCIPLINAS + " TEXT" + ")";
        db.execSQL(CREATE_TB_PESSOAS);
         Log.d("onCreatePessoa", "onCreate chamado");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TB_PESSOAS);
        onCreate(db);
//        DATABASE_VERSION += 1;
        Log.d("onUpdatePessoa", "onUpdate chamado");
    }

    public void addPessoa(PessoaVO pessoa) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NOME, pessoa.getNome());
        contentValues.put(EMAIL, pessoa.getEmail());
        contentValues.put(SENHA, pessoa.getSenha());
        contentValues.put(MATRICULA, pessoa.getMatricula());
        contentValues.put(DISCIPLINAS, String.join(",", pessoa.getDisciplinas())); // Concatena as disciplinas em uma string separada por vírgula
        db.insert(TB_PESSOAS, null, contentValues); // Insere os valores na tabela
        db.close();
    }

    public int getCountPessoas() {
        // Consulta SQL para selecionar todos os registros da tabela de pessoas
        String countQuerySQL = "SELECT * FROM " + TB_PESSOAS;

        // Obtém uma instância legível do banco de dados
        SQLiteDatabase db = this.getReadableDatabase();

        // Executa a consulta e obtém um cursor para percorrer os resultados
        Cursor cursor = db.rawQuery(countQuerySQL, null);

        // Obtém o número total de registros retornados pela consulta
        int count = cursor.getCount();

        // Fecha o cursor para liberar recursos
        cursor.close();

        // Retorna o número total de registros
        return count;
    }

    public List<PessoaVO> getAllPessoas() {
        List<PessoaVO> ltPessoas = new ArrayList<>();
        String SELECT_QUERY = "SELECT * FROM " + TB_PESSOAS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(SELECT_QUERY, null);
        if (cursor.moveToFirst()) {
            do {
                PessoaVO pessoa = new PessoaVO();
                pessoa.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
                pessoa.setNome(cursor.getString(cursor.getColumnIndex(NOME)));
                pessoa.setEmail(cursor.getString(cursor.getColumnIndex(EMAIL)));
                pessoa.setSenha(cursor.getString(cursor.getColumnIndex(SENHA)));
                pessoa.setMatricula(cursor.getString(cursor.getColumnIndex(MATRICULA)));
                pessoa.setDisciplinas(Arrays.asList(cursor.getString(cursor.getColumnIndex(DISCIPLINAS)).split(",")));
                ltPessoas.add(pessoa);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return ltPessoas;
    }

    public PessoaVO getPessoa(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        PessoaVO pessoa = new PessoaVO();
        Cursor cursor = db.query(TB_PESSOAS,
                new String[]{KEY_ID, NOME, EMAIL, SENHA, MATRICULA, DISCIPLINAS},
                KEY_ID + " = ?",
                new String[]{String.valueOf(id)},
                null,
                null,
                null);
        if (cursor != null && cursor.moveToFirst()) {
            pessoa.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
            pessoa.setNome(cursor.getString(cursor.getColumnIndex(NOME)));
            pessoa.setEmail(cursor.getString(cursor.getColumnIndex(EMAIL)));
            pessoa.setSenha(cursor.getString(cursor.getColumnIndex(SENHA)));
            pessoa.setMatricula(cursor.getString(cursor.getColumnIndex(MATRICULA)));
            pessoa.setDisciplinas(Arrays.asList(cursor.getString(cursor.getColumnIndex(DISCIPLINAS)).split(",")));
            cursor.close();
        }
        db.close();
        return pessoa;
    }

    public void deletePessoa(PessoaVO pessoa) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TB_PESSOAS, KEY_ID + " = ?", new String[]{String.valueOf(pessoa.getId())});
        db.close();
    }

    public PessoaVO getPessoaPorMatricula(String matricula) {
        SQLiteDatabase db = this.getReadableDatabase();
        PessoaVO pessoa = null;
        Cursor cursor = db.query(TB_PESSOAS,
                new String[]{KEY_ID, NOME, EMAIL, SENHA, MATRICULA, DISCIPLINAS},
                MATRICULA + " = ?",
                new String[]{matricula},
                null,
                null,
                null);
        if (cursor != null && cursor.moveToFirst()) {
            pessoa = new PessoaVO();
            pessoa.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
            pessoa.setNome(cursor.getString(cursor.getColumnIndex(NOME)));
            pessoa.setEmail(cursor.getString(cursor.getColumnIndex(EMAIL)));
            pessoa.setSenha(cursor.getString(cursor.getColumnIndex(SENHA)));
            pessoa.setMatricula(cursor.getString(cursor.getColumnIndex(MATRICULA)));
            pessoa.setDisciplinas(Arrays.asList(cursor.getString(cursor.getColumnIndex(DISCIPLINAS)).split(",")));
            cursor.close();
        }
        db.close();
        return pessoa;
    }

    public PessoaVO getPessoaLogada() {
        SQLiteDatabase db = this.getReadableDatabase();
        PessoaVO pessoa = null;

        // Query para obter a pessoa logada cuja matrícula começa com "p"
        Cursor cursor = db.query(TB_PESSOAS,
                new String[]{KEY_ID, NOME, EMAIL, SENHA, MATRICULA, DISCIPLINAS},
                MATRICULA + " LIKE ?",
                new String[]{"p%"}, // Matrícula começa com "p"
                null,
                null,
                null);

        if (cursor != null && cursor.moveToFirst()) {
            pessoa = new PessoaVO();
            pessoa.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
            pessoa.setNome(cursor.getString(cursor.getColumnIndex(NOME)));
            pessoa.setEmail(cursor.getString(cursor.getColumnIndex(EMAIL)));
            pessoa.setSenha(cursor.getString(cursor.getColumnIndex(SENHA)));
            pessoa.setMatricula(cursor.getString(cursor.getColumnIndex(MATRICULA)));
            pessoa.setDisciplinas(Arrays.asList(cursor.getString(cursor.getColumnIndex(DISCIPLINAS)).split(",")));
            cursor.close();
        }
        db.close();
        return pessoa;
    }
}
